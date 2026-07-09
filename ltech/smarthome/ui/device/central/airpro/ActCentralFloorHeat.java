package com.ltech.smarthome.ui.device.central.airpro;

import android.content.Intent;
import android.text.TextUtils;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActFloorHeatBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.FloorHeatSubDeviceParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.central.air.ActAcCentralVM;
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
public class ActCentralFloorHeat extends VMActivity<ActFloorHeatBinding, ActAcCentralVM> implements ISelectAction {
    private boolean comeFromCentralGateway;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_floor_heat;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$8(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActFloorHeatBinding) this.mViewBinding).circleBar.setProgressChangeListener(new CircleBar.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.CircleBar.OnProgressChangeListener
            public final void onProgressChanged(float f, int i, boolean z) {
                ActCentralFloorHeat.this.lambda$initView$0(f, i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(float f, int i, boolean z) {
        ((ActFloorHeatBinding) this.mViewBinding).tvTemp.setText(String.valueOf(i));
        if (z) {
            LHomeLog.i(getClass(), "percent -->" + f + "  progress -->" + i);
            ((ActAcCentralVM) this.mViewModel).currentTempure = i;
            ((ActAcCentralVM) this.mViewModel).tempChange.setValue(Integer.valueOf(i));
            ((ActAcCentralVM) this.mViewModel).floorHeatChangeTemp();
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
            ((ActAcCentralVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActCentralFloorHeat.this.lambda$startObserve$1((Device) obj);
                }
            });
        } else if (((ActAcCentralVM) this.mViewModel).controlId != -1) {
            setEditImage(R.mipmap.ic_setting);
            final Device deviceById = Injection.repo().device().getDeviceById(((ActAcCentralVM) this.mViewModel).controlId);
            MessageManager.getInstance().setCentralAirStatusCallback(new MessageManager.CentralAirStatusCallback() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda3
                @Override // com.smart.message.MessageManager.CentralAirStatusCallback
                public final void onDataReceive(int i, int i2, int i3, String str) {
                    ActCentralFloorHeat.this.lambda$startObserve$2(deviceById, i, i2, i3, str);
                }
            });
            ((ActAcCentralVM) this.mViewModel).controlObject.setValue(deviceById);
            ((ActAcCentralVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda4
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActCentralFloorHeat.this.lambda$startObserve$3((Device) obj);
                }
            });
        }
        ((ActAcCentralVM) this.mViewModel).floorHeatParam.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralFloorHeat.this.lambda$startObserve$4((FloorHeatSubDeviceParam) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).tempChange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralFloorHeat.this.lambda$startObserve$5((Integer) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).powerOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralFloorHeat.this.lambda$startObserve$6((Boolean) obj);
            }
        });
        ((ActAcCentralVM) this.mViewModel).sendStateLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCentralFloorHeat.this.lambda$startObserve$7((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        if (device != null) {
            setTitle(device.getDeviceName());
            setEditString(getString(R.string.save));
            FloorHeatSubDeviceParam floorHeatSubDeviceParam = (FloorHeatSubDeviceParam) device.getParam(FloorHeatSubDeviceParam.class);
            String stringExtra = getIntent().getStringExtra(SceneHelper.SCENE_PARAM_EXT);
            if (!TextUtils.isEmpty(stringExtra)) {
                List list = (List) GsonUtils.fromJson(stringExtra, new TypeToken<List<Integer>>(this) { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat.1
                }.getType());
                floorHeatSubDeviceParam.setOn(((Integer) list.get(0)).intValue() == 0 ? 1 : 0);
                floorHeatSubDeviceParam.setTemperature(((Integer) list.get(1)).intValue());
            }
            if (floorHeatSubDeviceParam == null) {
                return;
            }
            ((ActAcCentralVM) this.mViewModel).floorHeatParam.setValue(floorHeatSubDeviceParam);
            ((ActAcCentralVM) this.mViewModel).selectFloorHeatState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Device device, int i, int i2, int i3, String str) {
        if (device == null) {
            return;
        }
        FloorHeatSubDeviceParam floorHeatSubDeviceParam = (FloorHeatSubDeviceParam) device.getParam(FloorHeatSubDeviceParam.class);
        if (i == floorHeatSubDeviceParam.getUnicastAddress() && i2 == floorHeatSubDeviceParam.outAddr && i3 == floorHeatSubDeviceParam.inAddr) {
            setStatus(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        setTitle(device.getDeviceName());
        FloorHeatSubDeviceParam floorHeatSubDeviceParam = (FloorHeatSubDeviceParam) device.getParam(FloorHeatSubDeviceParam.class);
        if (floorHeatSubDeviceParam == null) {
            return;
        }
        ((ActAcCentralVM) this.mViewModel).floorHeatParam.setValue(floorHeatSubDeviceParam);
        ((ActAcCentralVM) this.mViewModel).selectFloorHeatState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(FloorHeatSubDeviceParam floorHeatSubDeviceParam) {
        ((ActAcCentralVM) this.mViewModel).currentOpen = floorHeatSubDeviceParam.getOn();
        ((ActAcCentralVM) this.mViewModel).currentMode = floorHeatSubDeviceParam.getMode();
        ((ActAcCentralVM) this.mViewModel).currentTempure = floorHeatSubDeviceParam.getTemperature();
        if (floorHeatSubDeviceParam.getOn() == 0) {
            ((ActAcCentralVM) this.mViewModel).powerOn.setValue(false);
            ((ActAcCentralVM) this.mViewModel).tempControl.setValue(false);
        } else {
            ((ActAcCentralVM) this.mViewModel).powerOn.setValue(true);
            ((ActAcCentralVM) this.mViewModel).tempControl.setValue(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Integer num) {
        ((ActAcCentralVM) this.mViewModel).setSendState();
        ((ActAcCentralVM) this.mViewModel).floorHeatParam.getValue().setTemperature(num.intValue());
        ((ActFloorHeatBinding) this.mViewBinding).tvTemp.setText(num + "");
        ((ActFloorHeatBinding) this.mViewBinding).circleBar.setProgress(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Boolean bool) {
        LHomeLog.i(getClass(), "on=" + bool);
        if (bool.booleanValue()) {
            updateUi(((ActAcCentralVM) this.mViewModel).floorHeatParam.getValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActFloorHeatBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_red);
        } else {
            ((ActFloorHeatBinding) this.mViewBinding).title.ivTip.setBackgroundResource(R.drawable.shape_circle_gray);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (((ActAcCentralVM) this.mViewModel).selectAction) {
            return;
        }
        Device value = ((ActAcCentralVM) this.mViewModel).controlObject.getValue();
        value.setParam(((ActAcCentralVM) this.mViewModel).floorHeatParam.getValue());
        Injection.repo().device().saveDevice(value);
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        MessageManager.getInstance().setCentralAirStatusCallback(null);
    }

    private void updateUi(FloorHeatSubDeviceParam floorHeatSubDeviceParam) {
        if (floorHeatSubDeviceParam.getTemperature() == 0) {
            floorHeatSubDeviceParam.setTemperature(5);
        }
        ((ActFloorHeatBinding) this.mViewBinding).tvTemp.setText(floorHeatSubDeviceParam.getTemperature() + "");
        CircleBar circleBar = ((ActFloorHeatBinding) this.mViewBinding).circleBar;
        circleBar.setMax(90);
        CircleBar circleBar2 = ((ActFloorHeatBinding) this.mViewBinding).circleBar;
        circleBar2.setMin(5);
        LHomeLog.i(getClass(), "on=progerss=" + floorHeatSubDeviceParam.getTemperature());
        ((ActFloorHeatBinding) this.mViewBinding).circleBar.setProgress(floorHeatSubDeviceParam.getTemperature());
        ((ActFloorHeatBinding) this.mViewBinding).tvState.setText(getString(R.string.floor_heat_temp) + floorHeatSubDeviceParam.getRoomTemp() + "℃");
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
            arrayList.add(Integer.valueOf(((ActAcCentralVM) this.mViewModel).currentTempure));
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, getString(((ActAcCentralVM) this.mViewModel).currentOpen == 1 ? R.string.app_zdy_status : R.string.off_1));
            SceneHelper.instance().cmdParam.addExtParam(SceneHelper.SCENE_PARAM_EXT, GsonUtils.toJson(arrayList));
            if (getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false)) {
                SceneHelper.instance().maskType = MaskType.LOCAL;
                setResult(3001);
                finishActivity();
                return;
            }
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActCentralFloorHeat.this.lambda$edit$8((Boolean) obj);
                }
            });
            return;
        }
        NavHelper.goSetting(((ActAcCentralVM) this.mViewModel).controlObject.getValue());
    }

    private void setStatus(String deviceData) {
        FloorHeatSubDeviceParam value = ((ActAcCentralVM) this.mViewModel).floorHeatParam.getValue();
        ((ActAcCentralVM) this.mViewModel).floorHeatParam.setValue(new FloorHeatSubDeviceParam(value.getUnicastAddress(), deviceData, value.getName(), value.getPosition()));
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