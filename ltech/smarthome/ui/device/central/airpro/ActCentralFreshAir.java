package com.ltech.smarthome.ui.device.central.airpro;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActFreshAirBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.FreshAirSubDeviceParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.central.air.ActAcCentralVM;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.message.MessageManager;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCentralFreshAir extends VMActivity<ActFreshAirBinding, ActAcCentralVM> implements ISelectAction {
    private boolean comeFromCentralGateway;
    private BaseQuickAdapter<FreshAirInfoItem, BaseViewHolder> infoAdapter;
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_fresh_air;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$8(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        initRvInfo();
        initRvContent();
    }

    private void initRvInfo() {
        BaseQuickAdapter<FreshAirInfoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<FreshAirInfoItem, BaseViewHolder>(this, R.layout.item_fresh_air_info, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, FreshAirInfoItem item) {
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName()).setText(R.id.tv_value, item.getValue());
            }
        };
        this.infoAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActFreshAirBinding) this.mViewBinding).rvInfo);
        ((ActFreshAirBinding) this.mViewBinding).rvInfo.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActFreshAirBinding) this.mViewBinding).rvInfo.setHasFixedSize(true);
    }

    private void initRvContent() {
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_key, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                helper.setEnabled(R.id.tv_name, item.isEnable());
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActCentralFreshAir.this.lambda$initRvContent$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActFreshAirBinding) this.mViewBinding).rvContent);
        ((ActFreshAirBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 4));
        ((ActFreshAirBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRvContent$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (Boolean.TRUE.equals(((ActAcCentralVM) this.mViewModel).powerOn.getValue())) {
            ((ActAcCentralVM) this.mViewModel).freshAirChangeWindSpeed(i);
            ((ActAcCentralVM) this.mViewModel).freshAirParam.getValue().setSpeed(((ActAcCentralVM) this.mViewModel).currentWindSpeed);
            ((ActAcCentralVM) this.mViewModel).setSendState();
            updateUi(((ActAcCentralVM) this.mViewModel).freshAirParam.getValue());
            this.mAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getFreshAirKeyItemList(((ActAcCentralVM) this.mViewModel).freshAirParam.getValue().getSpeed()));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActAcCentralVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActAcCentralVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActAcCentralVM) this.mViewModel).selectAction = getIntent().getBooleanExtra(Constants.SELECT_ACTION, false);
        this.comeFromCentralGateway = getIntent().getBooleanExtra(Constants.CENTRAL_GATEWAY, false);
        if (((ActAcCentralVM) this.mViewModel).selectAction) {
            ((ActAcCentralVM) this.mViewModel).controlObject.setValue((Device) SceneHelper.instance().controlObject);
            ((ActAcCentralVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActCentralFreshAir.this.lambda$startObserve$1((Device) obj);
                }
            });
        } else if (((ActAcCentralVM) this.mViewModel).controlId != -1) {
            setEditImage(R.mipmap.ic_setting);
            final Device deviceById = Injection.repo().device().getDeviceById(((ActAcCentralVM) this.mViewModel).controlId);
            MessageManager.getInstance().setCentralAirStatusCallback(new MessageManager.CentralAirStatusCallback() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda3
                @Override // com.smart.message.MessageManager.CentralAirStatusCallback
                public final void onDataReceive(int i, int i2, int i3, String str) {
                    ActCentralFreshAir.this.lambda$startObserve$2(deviceById, i, i2, i3, str);
                }
            });
            ((ActAcCentralVM) this.mViewModel).controlObject.setValue(deviceById);
            ((ActAcCentralVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActCentralFreshAir.this.lambda$startObserve$3((Device) obj);
                }
            });
        }
        ((ActAcCentralVM) this.mViewModel).freshAirParam.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralFreshAir.this.lambda$startObserve$4((FreshAirSubDeviceParam) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).tempChange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralFreshAir.this.lambda$startObserve$5((Integer) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).powerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralFreshAir.this.lambda$startObserve$6((Boolean) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).sendStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralFreshAir.this.lambda$startObserve$7((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            setEditString(getString(R.string.save));
            FreshAirSubDeviceParam freshAirSubDeviceParam = (FreshAirSubDeviceParam) device.getParam(FreshAirSubDeviceParam.class);
            String stringExtra = getIntent().getStringExtra(SceneHelper.SCENE_PARAM_EXT);
            if (!TextUtils.isEmpty(stringExtra)) {
                List list = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<Integer>>(this) { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir.3
                }.getType());
                freshAirSubDeviceParam.setOn(((Integer) list.get(0)).intValue() == 0 ? 1 : 0);
                freshAirSubDeviceParam.setSpeed(new int[]{0, 4, 2, 1}[((Integer) list.get(1)).intValue()]);
            }
            if (freshAirSubDeviceParam == null) {
                return;
            }
            ((ActAcCentralVM) this.mViewModel).freshAirParam.setValue(freshAirSubDeviceParam);
            ((ActAcCentralVM) this.mViewModel).selectFreshAirState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device, int i, int i2, int i3, String str) {
        if (device == null) {
            return;
        }
        FreshAirSubDeviceParam freshAirSubDeviceParam = (FreshAirSubDeviceParam) device.getParam(FreshAirSubDeviceParam.class);
        if (i == freshAirSubDeviceParam.getUnicastAddress() && i2 == freshAirSubDeviceParam.outAddr && i3 == freshAirSubDeviceParam.inAddr) {
            setStatus(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        setTitle(device.getDeviceName());
        FreshAirSubDeviceParam freshAirSubDeviceParam = (FreshAirSubDeviceParam) device.getParam(FreshAirSubDeviceParam.class);
        if (freshAirSubDeviceParam == null) {
            return;
        }
        ((ActAcCentralVM) this.mViewModel).freshAirParam.setValue(freshAirSubDeviceParam);
        ((ActAcCentralVM) this.mViewModel).selectFreshAirState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(FreshAirSubDeviceParam freshAirSubDeviceParam) {
        ((ActAcCentralVM) this.mViewModel).currentOpen = freshAirSubDeviceParam.getOn();
        ((ActAcCentralVM) this.mViewModel).currentMode = freshAirSubDeviceParam.getMode();
        ((ActAcCentralVM) this.mViewModel).currentWindSpeed = freshAirSubDeviceParam.getSpeed();
        ((ActAcCentralVM) this.mViewModel).currentTempure = freshAirSubDeviceParam.getTemperature();
        if (freshAirSubDeviceParam.getOn() == 0) {
            ((ActAcCentralVM) this.mViewModel).powerOn.setValue(false);
            ((ActAcCentralVM) this.mViewModel).tempControl.setValue(false);
            this.mAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getFreshAirKeyItemList(-1));
        } else {
            ((ActAcCentralVM) this.mViewModel).powerOn.setValue(true);
            ((ActAcCentralVM) this.mViewModel).tempControl.setValue(true);
            this.mAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getFreshAirKeyItemList(freshAirSubDeviceParam.getSpeed()));
        }
        this.infoAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getFreshAirInfoItemList(freshAirSubDeviceParam));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Integer num) {
        ((ActAcCentralVM) this.mViewModel).setSendState();
        ((ActAcCentralVM) this.mViewModel).freshAirParam.getValue().setTemperature(num.intValue());
        ((ActFreshAirBinding) this.mViewBinding).tvTemp.setText(num + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Boolean bool) {
        LHomeLog.i(getClass(), "on=" + bool);
        if (bool.booleanValue()) {
            updateUi(((ActAcCentralVM) this.mViewModel).freshAirParam.getValue());
            ((ActFreshAirBinding) this.mViewBinding).ivCircle.setImageResource(R.mipmap.pic_fresh_air_open);
            this.mAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getFreshAirKeyItemList(((ActAcCentralVM) this.mViewModel).freshAirParam.getValue().getSpeed()));
        } else {
            this.mAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getFreshAirKeyItemList(-1));
            ((ActFreshAirBinding) this.mViewBinding).ivCircle.setImageResource(R.mipmap.pic_fresh_air_close);
        }
        this.infoAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getFreshAirInfoItemList(((ActAcCentralVM) this.mViewModel).freshAirParam.getValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActFreshAirBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        } else {
            ((ActFreshAirBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (((ActAcCentralVM) this.mViewModel).selectAction) {
            return;
        }
        Device value = ((ActAcCentralVM) this.mViewModel).controlObject.getValue();
        value.setParam(((ActAcCentralVM) this.mViewModel).freshAirParam.getValue());
        Injection.repo().device().saveDevice(value);
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        MessageManager.getInstance().setCentralAirStatusCallback(null);
    }

    private void updateUi(FreshAirSubDeviceParam freshAirSubDeviceParam) {
        if (freshAirSubDeviceParam.getTemperature() == 0) {
            freshAirSubDeviceParam.setTemperature(((ActAcCentralVM) this.mViewModel).MIN_TEMPURE);
        }
        ((ActFreshAirBinding) this.mViewBinding).tvTemp.setText(freshAirSubDeviceParam.getPmValue() + "");
        ((ActFreshAirBinding) this.mViewBinding).tvState.setText(R.string.pm25_unit);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (((ActAcCentralVM) this.mViewModel).selectAction) {
            if (SceneHelper.instance().baseCmd == null) {
                SmartToast.showShort(R.string.please_choose);
                return;
            }
            SceneHelper.instance().cmdParam = new BaseCmdParam();
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "");
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(((ActAcCentralVM) this.mViewModel).currentOpen == 1 ? 0 : 1));
            if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 0) {
                arrayList.add(0);
            } else if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 1) {
                arrayList.add(3);
            } else if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 2) {
                arrayList.add(2);
            } else if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 4) {
                arrayList.add(1);
            }
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, getString(((ActAcCentralVM) this.mViewModel).currentOpen == 1 ? R.string.app_zdy_status : R.string.off_1));
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.SCENE_PARAM_EXT, GsonUtils.toJson(arrayList));
            if (getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false)) {
                SceneHelper.instance().maskType = MaskType.LOCAL;
                setResult(3001);
                finishActivity();
                return;
            }
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActCentralFreshAir.this.lambda$edit$8((Boolean) obj);
                }
            });
            return;
        }
        NavHelper.goSetting(((ActAcCentralVM) this.mViewModel).controlObject.getValue());
    }

    private void setStatus(String deviceData) {
        FreshAirSubDeviceParam value = ((ActAcCentralVM) this.mViewModel).freshAirParam.getValue();
        ((ActAcCentralVM) this.mViewModel).freshAirParam.setValue(new FreshAirSubDeviceParam(value.getUnicastAddress(), deviceData, value.getName(), value.getPosition()));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (5002 == resultCode || 5001 == resultCode) {
            if (this.comeFromCentralGateway) {
                setResult(5001);
            }
            finishActivity();
        }
    }
}