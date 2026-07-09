package com.ltech.smarthome.ui.device.central.air;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.core.content.ContextCompat;
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
import com.ltech.smarthome.databinding.ActAcCentralBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.CircleBar;
import com.smart.message.MessageManager;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActCentralAc extends VMActivity<ActAcCentralBinding, ActAcCentralVM> implements ISelectAction {
    private boolean comeFromCentralGateway;
    private BaseQuickAdapter<IrKeyItem, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ac_central;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$9(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        BaseQuickAdapter<IrKeyItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<IrKeyItem, BaseViewHolder>(this, R.layout.item_ir_key, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, IrKeyItem item) {
                helper.setImageResource(R.id.iv_icon, item.getIconRes()).setText(R.id.tv_name, item.getName());
                helper.setEnabled(R.id.tv_name, item.isEnable());
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActCentralAc.this.lambda$initView$0(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActAcCentralBinding) this.mViewBinding).rvContent);
        ((ActAcCentralBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActAcCentralBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((ActAcCentralBinding) this.mViewBinding).circleBar.setProgressChangeListener(new CircleBar.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.CircleBar.OnProgressChangeListener
            public final void onProgressChanged(float f, int i, boolean z) {
                ActCentralAc.this.lambda$initView$1(f, i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.mAdapter.getData().get(i).isEnable()) {
            if (i == 0) {
                ((ActAcCentralVM) this.mViewModel).changeMode();
                ((ActAcCentralVM) this.mViewModel).param.getValue().setMode(((ActAcCentralVM) this.mViewModel).currentMode);
            } else if (i == 1) {
                ((ActAcCentralVM) this.mViewModel).changeWindSpeed();
                ((ActAcCentralVM) this.mViewModel).param.getValue().setSpeed(((ActAcCentralVM) this.mViewModel).currentWindSpeed);
            }
            ((ActAcCentralVM) this.mViewModel).setSendState();
            updateUi(((ActAcCentralVM) this.mViewModel).param.getValue());
            this.mAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getKeyItemList(((ActAcCentralVM) this.mViewModel).param.getValue().getMode(), ((ActAcCentralVM) this.mViewModel).param.getValue().getSpeed()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(float f, int i, boolean z) {
        ((ActAcCentralBinding) this.mViewBinding).tvTemp.setText(String.valueOf(i));
        if (z) {
            LHomeLog.i(getClass(), "percent -->" + f + "  progress -->" + i);
            ((ActAcCentralVM) this.mViewModel).currentTempure = i;
            ((ActAcCentralVM) this.mViewModel).tempChange.setValue(Integer.valueOf(i));
            ((ActAcCentralVM) this.mViewModel).changeTemperature();
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
            ((ActAcCentralVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActCentralAc.this.lambda$startObserve$2((Device) obj);
                }
            });
        } else if (((ActAcCentralVM) this.mViewModel).controlId != -1) {
            setEditImage(R.mipmap.ic_setting);
            final Device deviceById = Injection.repo().device().getDeviceById(((ActAcCentralVM) this.mViewModel).controlId);
            MessageManager.getInstance().setCentralAirStatusCallback(new MessageManager.CentralAirStatusCallback() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda3
                @Override // com.smart.message.MessageManager.CentralAirStatusCallback
                public final void onDataReceive(int i, int i2, int i3, String str) {
                    ActCentralAc.this.lambda$startObserve$3(deviceById, i, i2, i3, str);
                }
            });
            ((ActAcCentralVM) this.mViewModel).controlObject.setValue(deviceById);
            ((ActAcCentralVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActCentralAc.this.lambda$startObserve$4((Device) obj);
                }
            });
        }
        ((ActAcCentralVM) this.mViewModel).param.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAc.this.lambda$startObserve$5((CentralAirSubDeviceParam) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).tempChange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAc.this.lambda$startObserve$6((Integer) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).powerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAc.this.lambda$startObserve$7((Boolean) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).sendStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralAc.this.lambda$startObserve$8((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            setEditString(getString(R.string.save));
            ((ActAcCentralVM) this.mViewModel).initAcFeature(device);
            CentralAirSubDeviceParam centralAirSubDeviceParam = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
            String stringExtra = getIntent().getStringExtra(SceneHelper.SCENE_PARAM_EXT);
            if (!TextUtils.isEmpty(stringExtra)) {
                List list = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<Integer>>(this) { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc.2
                }.getType());
                centralAirSubDeviceParam.setMode(new int[]{1, 8, 0, 4, 2}[((Integer) list.get(0)).intValue()]);
                centralAirSubDeviceParam.setOn(((Integer) list.get(1)).intValue() == 0 ? 1 : 0);
                centralAirSubDeviceParam.setTemperature(((Integer) list.get(2)).intValue());
                centralAirSubDeviceParam.setSpeed(new int[]{4, 2, 1, 5, 3, 0}[((Integer) list.get(3)).intValue() - 1]);
            }
            if (centralAirSubDeviceParam == null) {
                return;
            }
            ((ActAcCentralVM) this.mViewModel).param.setValue(centralAirSubDeviceParam);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device, int i, int i2, int i3, String str) {
        if (((ActAcCentralVM) this.mViewModel).canChange && device != null) {
            CentralAirSubDeviceParam centralAirSubDeviceParam = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
            if (i == centralAirSubDeviceParam.getUnicastAddress() && i2 == centralAirSubDeviceParam.outAddr && i3 == centralAirSubDeviceParam.inAddr) {
                if (str.length() >= 28) {
                    ((ActAcCentralVM) this.mViewModel).setRefreshDelay(((Long.parseLong(str.substring(0, 8), 16) * 1000) + 5200) - System.currentTimeMillis());
                    setStatus(str.substring(8));
                } else if (str.length() >= 20) {
                    setStatus(str);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Device device) {
        setTitle(device.getDeviceName());
        ((ActAcCentralVM) this.mViewModel).initAcFeature(device);
        CentralAirSubDeviceParam centralAirSubDeviceParam = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
        if (centralAirSubDeviceParam == null) {
            return;
        }
        ((ActAcCentralVM) this.mViewModel).param.setValue(centralAirSubDeviceParam);
        ((ActAcCentralVM) this.mViewModel).selectState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(CentralAirSubDeviceParam centralAirSubDeviceParam) {
        ((ActAcCentralVM) this.mViewModel).currentOpen = centralAirSubDeviceParam.getOn();
        ((ActAcCentralVM) this.mViewModel).currentMode = centralAirSubDeviceParam.getMode();
        ((ActAcCentralVM) this.mViewModel).currentWindSpeed = centralAirSubDeviceParam.getSpeed();
        ((ActAcCentralVM) this.mViewModel).currentTempure = centralAirSubDeviceParam.getTemperature();
        if (centralAirSubDeviceParam.getOn() == 0) {
            ((ActAcCentralVM) this.mViewModel).powerOn.setValue(false);
            ((ActAcCentralVM) this.mViewModel).tempControl.setValue(false);
        } else {
            ((ActAcCentralVM) this.mViewModel).powerOn.setValue(true);
            ((ActAcCentralVM) this.mViewModel).tempControl.setValue(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Integer num) {
        ((ActAcCentralVM) this.mViewModel).setSendState();
        ((ActAcCentralVM) this.mViewModel).param.getValue().setTemperature(num.intValue());
        ((ActAcCentralBinding) this.mViewBinding).tvTemp.setText(num + "");
        ((ActAcCentralBinding) this.mViewBinding).circleBar.setProgress(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Boolean bool) {
        LHomeLog.i(getClass(), "on=" + bool);
        if (bool.booleanValue()) {
            updateUi(((ActAcCentralVM) this.mViewModel).param.getValue());
        }
        this.mAdapter.setNewData(((ActAcCentralVM) this.mViewModel).getKeyItemList(((ActAcCentralVM) this.mViewModel).param.getValue().getMode(), ((ActAcCentralVM) this.mViewModel).param.getValue().getSpeed()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActAcCentralBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        } else {
            ((ActAcCentralBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (((ActAcCentralVM) this.mViewModel).selectAction) {
            return;
        }
        Device value = ((ActAcCentralVM) this.mViewModel).controlObject.getValue();
        value.setParam(((ActAcCentralVM) this.mViewModel).param.getValue());
        Injection.repo().device().saveDevice(value);
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        MessageManager.getInstance().setCentralAirStatusCallback(null);
    }

    private void updateUi(CentralAirSubDeviceParam centralAirSubDeviceParam) {
        if (centralAirSubDeviceParam.getTemperature() == 0) {
            centralAirSubDeviceParam.setTemperature(((ActAcCentralVM) this.mViewModel).MIN_TEMPURE);
        }
        ((ActAcCentralBinding) this.mViewBinding).tvTemp.setText(centralAirSubDeviceParam.getTemperature() + "");
        if (centralAirSubDeviceParam.getMode() == 8) {
            ((ActAcCentralBinding) this.mViewBinding).circleBar.setBarColor(ContextCompat.getColor(this, R.color.bar_blue_1), ContextCompat.getColor(this, R.color.bar_red));
        } else {
            ((ActAcCentralBinding) this.mViewBinding).circleBar.setBarColor(ContextCompat.getColor(this, R.color.bar_green), ContextCompat.getColor(this, R.color.bar_blue));
        }
        ((ActAcCentralBinding) this.mViewBinding).circleBar.setMax(((ActAcCentralVM) this.mViewModel).MAX_TEMPURE);
        ((ActAcCentralBinding) this.mViewBinding).circleBar.setMin(((ActAcCentralVM) this.mViewModel).MIN_TEMPURE);
        LHomeLog.i(getClass(), "on=progerss=" + centralAirSubDeviceParam.getTemperature());
        ((ActAcCentralBinding) this.mViewBinding).circleBar.setProgress(centralAirSubDeviceParam.getTemperature());
        ((ActAcCentralBinding) this.mViewBinding).tvState.setText(((ActAcCentralVM) this.mViewModel).getStateString());
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
            if (((ActAcCentralVM) this.mViewModel).currentMode == 1) {
                arrayList.add(0);
            } else if (((ActAcCentralVM) this.mViewModel).currentMode == 2) {
                arrayList.add(4);
            } else if (((ActAcCentralVM) this.mViewModel).currentMode == 4) {
                arrayList.add(3);
            } else if (((ActAcCentralVM) this.mViewModel).currentMode == 8) {
                arrayList.add(1);
            }
            arrayList.add(Integer.valueOf(((ActAcCentralVM) this.mViewModel).currentOpen == 1 ? 0 : 1));
            arrayList.add(Integer.valueOf(((ActAcCentralVM) this.mViewModel).currentTempure));
            if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 1) {
                arrayList.add(3);
            } else if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 2) {
                arrayList.add(2);
            } else if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 3) {
                arrayList.add(5);
            } else if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 4) {
                arrayList.add(1);
            } else if (((ActAcCentralVM) this.mViewModel).currentWindSpeed == 5) {
                arrayList.add(4);
            }
            arrayList.add(0);
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, getString(((ActAcCentralVM) this.mViewModel).currentOpen == 1 ? R.string.app_zdy_status : R.string.off_1));
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.SCENE_PARAM_EXT, GsonUtils.toJson(arrayList));
            if (getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false)) {
                SceneHelper.instance().maskType = MaskType.LOCAL;
                setResult(3001);
                finishActivity();
                return;
            }
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAc$$ExternalSyntheticLambda9
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActCentralAc.this.lambda$edit$9((Boolean) obj);
                }
            });
            return;
        }
        NavHelper.goSetting(((ActAcCentralVM) this.mViewModel).controlObject.getValue());
    }

    private void setStatus(String deviceData) {
        CentralAirSubDeviceParam value = ((ActAcCentralVM) this.mViewModel).param.getValue();
        ((ActAcCentralVM) this.mViewModel).param.setValue(new CentralAirSubDeviceParam(value.getUnicastAddress(), deviceData, value.getName(), value.getPosition()));
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