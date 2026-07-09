package com.ltech.smarthome.ui.automation;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.auto.DeviceConditionParam;
import com.ltech.smarthome.model.auto.EvnConditionParam;
import com.ltech.smarthome.model.auto.HumidityConditionParam;
import com.ltech.smarthome.model.auto.Pm25ConditionParam;
import com.ltech.smarthome.model.auto.ReachLeaveParam;
import com.ltech.smarthome.model.auto.SunConditionParam;
import com.ltech.smarthome.model.auto.TemperatureConditionParam;
import com.ltech.smarthome.model.auto.TimerConditionParam;
import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.AutomationParam;
import com.ltech.smarthome.model.scene_param.DeviceParam;
import com.ltech.smarthome.model.scene_param.GroupParam;
import com.ltech.smarthome.model.scene_param.SceneParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.automation.AddAutomationResponse;
import com.ltech.smarthome.ui.automation.local.ActSelectSuperPanelForAutomation;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ActSelectDeviceForCondition;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.local.BaseSceneActionVM;
import com.ltech.smarthome.ui.select.ActSelectSceneForAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.mozilla.javascript.ES6Iterator;

/* loaded from: classes4.dex */
public class ActAddAutomationVM extends BaseSceneActionVM {
    public List<Automation.Action> actionList;
    public Automation automation;
    public long automationId;
    public List<Automation.Condition> conditionList;
    public int conditionType;
    public long controlId;
    public String endTime;
    public boolean isEdit;
    public Listing<Scene> request;
    public String startTime;
    public List<Automation.Condition> statusConditionList;
    public String timeZone;
    public String weeks;
    public MutableLiveData<String> automationName = new MutableLiveData<>("");
    public int editPosition = -1;
    public MediatorLiveData<List<Scene>> sceneList = new MediatorLiveData<>();
    public MediatorLiveData<List<Automation>> automationList = new MediatorLiveData<>();
    public MutableLiveData<Integer> picIndex = new MutableLiveData<>(0);
    public SingleLiveEvent<Void> showEditNameDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showSelectConditionTypeEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showDelDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showTimesDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showIntervalDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<List<NoActionDevice>> noSetActionDeviceEvent = new MutableLiveData<>();
    public MutableLiveData<List<NoActionDevice>> showErrorEvent = new MutableLiveData<>();
    public boolean isLocal = true;
    public MutableLiveData<Long> gatewayId = new MutableLiveData<>(0L);
    public MutableLiveData<Integer> cycleIndex = new MutableLiveData<>(0);
    public MutableLiveData<Integer> intervalTime = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> isExpand = new MutableLiveData<>(false);
    public MutableLiveData<String> intervalTimeStr = new MutableLiveData<>();
    public SingleLiveEvent<Void> showStatusConditionTipDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showAddStatusConditionEvent = new SingleLiveEvent<>();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomationVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActAddAutomationVM.this.lambda$new$0((View) obj);
        }
    });
    public GoItem effectPeriod = new GoItem().setMainText(ActivityUtils.getTopActivity().getString(R.string.effective_period)).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomationVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            ActAddAutomationVM.this.lambda$new$1();
        }
    }));
    public MutableLiveData<GoItem> effectTimeLiveData = new MutableLiveData<>(this.effectPeriod);

    public static class DisplayState {
        public String action;
        public String errorTip;
        public String hint;
        public int iconRes;
        public boolean isVirtual;
        public String name = "";
        public String location = "";
        public int rgbColor = Integer.MIN_VALUE;
        public String state = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_change_pic /* 2131296973 */:
                navigation(NavUtils.destination(ActSelectCover.class).withDefaultRequestCode());
                break;
            case R.id.iv_status_condition /* 2131297270 */:
                this.showStatusConditionTipDialogEvent.call();
                break;
            case R.id.layout_expand /* 2131297468 */:
                this.isExpand.setValue(Boolean.valueOf(Boolean.FALSE.equals(this.isExpand.getValue())));
                break;
            case R.id.layout_interval /* 2131297499 */:
                this.showIntervalDialogEvent.call();
                break;
            case R.id.layout_select_gateway /* 2131297624 */:
                navigation(NavUtils.destination(ActSelectSuperPanelForAutomation.class).withLong(Constants.PLACE_ID, this.placeId).withLong("device_id", this.gatewayId.getValue().longValue()).withDefaultRequestCode());
                break;
            case R.id.layout_status_condition_bg /* 2131297660 */:
                this.showAddStatusConditionEvent.call();
                break;
            case R.id.layout_times /* 2131297682 */:
                this.showTimesDialogEvent.call();
                break;
            case R.id.tv_condition_type /* 2131298525 */:
                this.showSelectConditionTypeEvent.call();
                break;
            case R.id.tv_name /* 2131298805 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.tv_remove /* 2131298912 */:
                this.showDelDialogEvent.call();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        navigation(NavUtils.destination(ActSelectEffectPeriod.class).withString(Constants.START_TIME, this.startTime).withString(Constants.END_TIME, this.endTime).withString(Constants.WEEKS, this.weeks).withDefaultRequestCode());
    }

    public DisplayState getConditionState(Context context, Automation.Condition condition) {
        DisplayState displayState;
        if (Automation.Condition.isTimerCondition(condition)) {
            displayState = new DisplayState();
            displayState.iconRes = R.mipmap.ic_auto_timing;
            displayState.name = context.getString(R.string.condition_timing_tip);
            TimerConditionParam timerConditionParam = (TimerConditionParam) condition.getParams(TimerConditionParam.class);
            if (timerConditionParam != null) {
                displayState.state = timerConditionParam.getStateString(context);
            }
        } else if (Automation.Condition.isSunCondition(condition)) {
            displayState = new DisplayState();
            displayState.iconRes = R.mipmap.icon_sun;
            displayState.name = context.getString(R.string.sunrise_sunset);
            SunConditionParam sunConditionParam = (SunConditionParam) condition.getParams(SunConditionParam.class);
            if (sunConditionParam != null) {
                displayState.state = sunConditionParam.getStateString(context);
            }
        } else if (Automation.Condition.isHumidityCondition(condition)) {
            displayState = new DisplayState();
            displayState.iconRes = R.mipmap.icon_humidity;
            displayState.name = context.getString(R.string.humidity);
            HumidityConditionParam humidityConditionParam = (HumidityConditionParam) condition.getParams(HumidityConditionParam.class);
            if (humidityConditionParam != null) {
                displayState.state = humidityConditionParam.getStateString(context);
            }
        } else if (Automation.Condition.isTemperatureCondition(condition)) {
            displayState = new DisplayState();
            displayState.iconRes = R.mipmap.icon_temperature;
            displayState.name = context.getString(R.string.temperature);
            TemperatureConditionParam temperatureConditionParam = (TemperatureConditionParam) condition.getParams(TemperatureConditionParam.class);
            if (temperatureConditionParam != null) {
                displayState.state = temperatureConditionParam.getStateString(context);
            }
        } else if (Automation.Condition.isPm25Condition(condition)) {
            displayState = new DisplayState();
            displayState.iconRes = R.mipmap.icon_pm25;
            displayState.name = context.getString(R.string.pm_25);
            Pm25ConditionParam pm25ConditionParam = (Pm25ConditionParam) condition.getParams(Pm25ConditionParam.class);
            if (pm25ConditionParam != null) {
                displayState.state = pm25ConditionParam.getStateString(context);
            }
        } else if (Automation.Condition.isAddressCondition(condition)) {
            displayState = new DisplayState();
            displayState.iconRes = R.mipmap.ic_auto_address;
            ReachLeaveParam reachLeaveParam = (ReachLeaveParam) condition.getParams(ReachLeaveParam.class);
            if (reachLeaveParam != null) {
                displayState.name = reachLeaveParam.getStateString(context);
                displayState.state = reachLeaveParam.city;
            }
            if (this.isLocal) {
                displayState.errorTip = context.getString(R.string.not_support_local_tip);
            }
        } else {
            displayState = null;
        }
        if (Automation.Condition.isDeviceCondition(condition)) {
            displayState = new DisplayState();
            DeviceConditionParam deviceConditionParam = (DeviceConditionParam) condition.getParams(DeviceConditionParam.class);
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceConditionParam.deviceid);
            if (deviceConditionParam.getProductid() == null && deviceByDeviceId != null) {
                deviceConditionParam.deviceName = deviceByDeviceId.getDeviceName();
                deviceConditionParam.floorRoom = deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName();
                deviceConditionParam.setProductid(deviceByDeviceId.getProductId());
            }
            if (this.isLocal && deviceByDeviceId != null && TextUtils.isEmpty(deviceConditionParam.mac)) {
                deviceConditionParam.mac = deviceByDeviceId.getWifiMac();
                if (deviceConditionParam.operator == 0) {
                    condition.setParams(HelpUtils.removeObjectKey(deviceConditionParam, new ArrayList(Arrays.asList("operator", ES6Iterator.VALUE_PROPERTY, "value1", "subIndex"))));
                } else {
                    condition.setParams(GsonUtils.toJson(deviceConditionParam));
                }
            }
            if (ProductId.ID_KEY_SWITCH_1.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_key_switch_01;
            } else if (ProductId.ID_KEY_SWITCH_2.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_key_switch_02;
            } else if (ProductId.ID_KEY_SWITCH_3.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_key_switch_03;
            } else if (ProductId.ID_KEY_SWITCH_4.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_key_switch_04;
            } else if (ProductId.ID_BODY_SENSOR.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.icon_device_sensor;
            } else if (ProductId.ID_SENSOR_MR03.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.icon_device_mr03;
            } else if (ProductId.ID_SENSOR_MR04.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.icon_device_mr04;
            } else if (ProductId.ID_SMART_SWITCH_S1C.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_s1c;
            } else if (ProductId.ID_SMART_SWITCH_S2C.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_s2c;
            } else if (ProductId.ID_SMART_SWITCH_S3C.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_s3c;
            } else if (ProductId.ID_SMART_SWITCH_S4.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_s4;
            } else if (ProductId.ID_SWITCH_PANEL_S4M.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_switch_panel_s4m;
            } else if (ProductId.ID_SCENE_PANEL_S8.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_scene_panel_s8;
            } else if (ProductId.ID_SMART_PANEL_S6B.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_s6b;
            } else if (ProductId.ID_SMART_SWITCH_S1_PRO.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_s1_pro;
            } else if (ProductId.ID_SMART_SWITCH_S2_PRO.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_s2_pro;
            } else if (ProductId.ID_SMART_SWITCH_S3_PRO.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_s3_pro;
            } else if (ProductId.ID_SMART_SWITCH_SQ.equals(deviceConditionParam.productid) || ProductId.ID_SMART_SWITCH_SQB.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_sq;
            } else if (ProductId.ID_SMART_SWITCH_SQ_PRO.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_sqpro;
            } else if (ProductId.ID_DRY_CONTACT_TO_BLE.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.trig_icon_normal;
            } else if (ProductId.ID_ANDROID_SUPER_PANEL.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_super_panel;
            } else if (ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_6s;
            } else if (ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_12s;
            } else if (ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_super_panel;
            } else if (ProductId.ID_SMART_PANEL_G4.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.icon_g4;
            } else if (ProductId.ID_SMART_PANEL_G4_PRO.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.icon_g4pro;
            } else if (ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.ic_device_g4max;
            } else if (ProductId.ID_SMART_PANEL_GQ.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_gq;
            } else if (ProductId.ID_SMART_PANEL_GQX.equals(deviceConditionParam.productid)) {
                displayState.iconRes = R.mipmap.device_icon_gqx;
            } else {
                displayState.iconRes = ProductRepository.getProductIcon(deviceByDeviceId);
            }
            if ((ProductId.ID_SENSOR_MR03.equals(deviceConditionParam.productid) || ProductId.ID_SENSOR_MR04.equals(deviceConditionParam.productid) || ProductId.ID_SENSOR_MS03.equals(deviceConditionParam.productid)) && deviceConditionParam.index == 1) {
                displayState.hint = context.getString(R.string.automation_illuminance_tip);
            }
            displayState.name = deviceConditionParam.getDeviceName();
            displayState.state = deviceConditionParam.getState(context);
        }
        return displayState;
    }

    public DisplayState getStatusConditionState(Context context, Automation.Condition condition) {
        if (Automation.Condition.isEnv(condition)) {
            DisplayState displayState = new DisplayState();
            EvnConditionParam evnConditionParam = (EvnConditionParam) condition.getParams(EvnConditionParam.class);
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(evnConditionParam.deviceid);
            if (evnConditionParam.getProductid() == null && deviceByDeviceId != null) {
                evnConditionParam.deviceName = deviceByDeviceId.getDeviceName();
                evnConditionParam.floorRoom = deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName();
                evnConditionParam.setProductid(deviceByDeviceId.getProductId());
            }
            if (deviceByDeviceId != null && TextUtils.isEmpty(evnConditionParam.mac)) {
                evnConditionParam.mac = deviceByDeviceId.getWifiMac();
            }
            if (evnConditionParam.operator == 0) {
                condition.setParams(HelpUtils.removeObjectKey(evnConditionParam, new ArrayList(Arrays.asList("operator", ES6Iterator.VALUE_PROPERTY, "value1", "subIndex"))));
            } else {
                condition.setParams(GsonUtils.toJson(evnConditionParam));
            }
            displayState.iconRes = R.mipmap.icon_environment;
            displayState.name = evnConditionParam.getConditionName(getContext());
            displayState.state = evnConditionParam.getStatusConditionName(getContext());
            return displayState;
        }
        if (!Automation.Condition.isDeviceCondition(condition)) {
            return null;
        }
        DisplayState displayState2 = new DisplayState();
        DeviceConditionParam deviceConditionParam = (DeviceConditionParam) condition.getParams(DeviceConditionParam.class);
        Device deviceByDeviceId2 = Injection.repo().device().getDeviceByDeviceId(deviceConditionParam.deviceid);
        if (deviceConditionParam.getProductid() == null && deviceByDeviceId2 != null) {
            deviceConditionParam.deviceName = deviceByDeviceId2.getDeviceName();
            deviceConditionParam.floorRoom = deviceByDeviceId2.getFloorName() + deviceByDeviceId2.getRoomName();
            deviceConditionParam.setProductid(deviceByDeviceId2.getProductId());
        }
        if (deviceByDeviceId2 != null && TextUtils.isEmpty(deviceConditionParam.mac)) {
            deviceConditionParam.mac = deviceByDeviceId2.getWifiMac();
            if (deviceConditionParam.operator == 0) {
                condition.setParams(HelpUtils.removeObjectKey(deviceConditionParam, new ArrayList(Arrays.asList("operator", ES6Iterator.VALUE_PROPERTY, "value1", "subIndex"))));
            } else {
                condition.setParams(GsonUtils.toJson(deviceConditionParam));
            }
        }
        if (ProductId.ID_KEY_SWITCH_1.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_key_switch_01;
        } else if (ProductId.ID_KEY_SWITCH_2.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_key_switch_02;
        } else if (ProductId.ID_KEY_SWITCH_3.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_key_switch_03;
        } else if (ProductId.ID_KEY_SWITCH_4.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_key_switch_04;
        } else if (ProductId.ID_BODY_SENSOR.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.icon_device_sensor;
        } else if (ProductId.ID_SENSOR_MR03.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.icon_device_mr03;
        } else if (ProductId.ID_SENSOR_MR04.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.icon_device_mr04;
        } else if (ProductId.ID_SMART_SWITCH_S1C.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_s1c;
        } else if (ProductId.ID_SMART_SWITCH_S2C.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_s2c;
        } else if (ProductId.ID_SMART_SWITCH_S3C.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_s3c;
        } else if (ProductId.ID_SMART_SWITCH_S4.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_s4;
        } else if (ProductId.ID_SWITCH_PANEL_S4M.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_switch_panel_s4m;
        } else if (ProductId.ID_SCENE_PANEL_S8.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_scene_panel_s8;
        } else if (ProductId.ID_SMART_PANEL_S6B.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_s6b;
        } else if (ProductId.ID_SMART_SWITCH_S1_PRO.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_s1_pro;
        } else if (ProductId.ID_SMART_SWITCH_S2_PRO.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_s2_pro;
        } else if (ProductId.ID_SMART_SWITCH_S3_PRO.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_s3_pro;
        } else if (ProductId.ID_SMART_SWITCH_SQ.equals(deviceConditionParam.productid) || ProductId.ID_SMART_SWITCH_SQB.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_sq;
        } else if (ProductId.ID_SMART_SWITCH_SQ_PRO.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_sqpro;
        } else if (ProductId.ID_DRY_CONTACT_TO_BLE.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.trig_icon_normal;
        } else if (ProductId.ID_ANDROID_SUPER_PANEL.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_super_panel;
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_6s;
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_12s;
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_super_panel;
        } else if (ProductId.ID_SMART_PANEL_G4.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.icon_g4;
        } else if (ProductId.ID_SMART_PANEL_G4_PRO.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.icon_g4pro;
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.ic_device_g4max;
        } else if (ProductId.ID_SMART_PANEL_GQ.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_gq;
        } else if (ProductId.ID_SMART_PANEL_GQX.equals(deviceConditionParam.productid)) {
            displayState2.iconRes = R.mipmap.device_icon_gqx;
        } else {
            displayState2.iconRes = ProductRepository.getProductIcon(deviceByDeviceId2);
        }
        displayState2.name = deviceConditionParam.getDeviceName();
        displayState2.state = deviceConditionParam.getStatusConditionName(context);
        return displayState2;
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x0230  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.ltech.smarthome.ui.automation.ActAddAutomationVM.DisplayState getActionState(android.content.Context r20, com.ltech.smarthome.model.bean.Automation.Action r21) {
        /*
            Method dump skipped, instructions count: 928
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.automation.ActAddAutomationVM.getActionState(android.content.Context, com.ltech.smarthome.model.bean.Automation$Action):com.ltech.smarthome.ui.automation.ActAddAutomationVM$DisplayState");
    }

    public void editAction(Automation.Action action) {
        if (MaskType.isSceneAction(action.getActiontype())) {
            SceneHelper.instance().controlObject = getCurActionScene(action);
            NavUtils.destination(ActSelectSceneForAction.class).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.IS_LOCAL_AUTOMATION, this.isLocal).withBoolean(Constants.AUTOMATION_SELECT_SCENE, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
            return;
        }
        if (MaskType.isAutomationAction(action.getActiontype())) {
            SceneHelper.instance().automationAction = true;
            SceneHelper.instance().controlObject = getCurActionAutomation();
            if (this.isLocal) {
                NavUtils.destination(ActSelectAutomationForAction.class).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.SELECT_ACTION, true).withBoolean(Constants.IS_LOCAL_AUTOMATION, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
                return;
            } else {
                SceneHelper.goSelectAction(ActivityUtils.getTopActivity(), 3, this.placeId);
                return;
            }
        }
        if (MaskType.isGroupAction(action.getActiontype())) {
            GroupParam groupParam = (GroupParam) action.getParams(GroupParam.class);
            SceneHelper.instance().selectInstruct = groupParam.instruct;
            NavUtils.Builder goSelectAction = SceneHelper.instance().goSelectAction(getGroup(groupParam.groupid));
            if (goSelectAction != null) {
                navigation(goSelectAction.withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode());
                return;
            }
            return;
        }
        if (MaskType.isDeviceAction(action.getActiontype())) {
            DeviceParam deviceParam = (DeviceParam) action.getParams(DeviceParam.class);
            SceneHelper.instance().selectInstruct = deviceParam.instruct;
            Device device = getDevice(deviceParam.deviceid);
            if (device != null && device.getProductId().equals(ProductId.CG485_SUB_DEVICE)) {
                SceneHelper.instance().maskType = MaskType.DEVICE;
                SceneHelper.instance().controlObject = device;
                Cg485Helper.showSceneActionDialog((FragmentActivity) ActivityUtils.getTopActivity(), device, false);
                return;
            }
            NavUtils.Builder goSelectAction2 = SceneHelper.instance().goSelectAction(getDevice(deviceParam.deviceid));
            if (goSelectAction2 != null) {
                navigation(goSelectAction2.withLong(Constants.PLACE_ID, this.placeId).withString(SceneHelper.SCENE_PARAM_EXT, deviceParam.sceneParamExt).withDefaultRequestCode());
            }
        }
    }

    @Override // com.ltech.smarthome.ui.scene.local.BaseSceneActionVM
    public List<Automation> getCurActionAutomation() {
        AutomationParam automationParam;
        Automation automation;
        ArrayList arrayList = new ArrayList();
        for (Automation.Action action : this.actionList) {
            if (MaskType.isAutomationAction(action.getActiontype()) && (automationParam = (AutomationParam) action.getParams(AutomationParam.class)) != null && (automation = getAutomation(automationParam.automationid)) != null) {
                automation.setEnable(automationParam.enable);
                arrayList.add(automation);
            }
        }
        return arrayList;
    }

    public List<Scene> getCurActionScene() {
        SceneParam sceneParam;
        Scene scene;
        ArrayList arrayList = new ArrayList();
        for (Automation.Action action : this.actionList) {
            if (MaskType.isSceneAction(action.getActiontype()) && (sceneParam = (SceneParam) action.getParams(SceneParam.class)) != null && (scene = getScene(sceneParam.sceneid)) != null) {
                arrayList.add(scene);
            }
        }
        return arrayList;
    }

    public List<Scene> getCurActionScene(Automation.Action action) {
        SceneParam sceneParam;
        Scene scene;
        ArrayList arrayList = new ArrayList();
        if (MaskType.isSceneAction(action.getActiontype()) && (sceneParam = (SceneParam) action.getParams(SceneParam.class)) != null && (scene = getScene(sceneParam.sceneid)) != null) {
            arrayList.add(scene);
        }
        return arrayList;
    }

    public void editCondition(Automation.Condition condition) {
        editCondition(condition, false);
    }

    public void editCondition(Automation.Condition condition, boolean isStatus) {
        DeviceConditionParam deviceConditionParam;
        SceneHelper.instance().conditionParamType = condition.getParamtype();
        if (Automation.Condition.isTimerCondition(condition)) {
            TimerConditionParam timerConditionParam = (TimerConditionParam) condition.getParams(TimerConditionParam.class);
            if (timerConditionParam != null) {
                SceneHelper.instance().conditionParam = timerConditionParam;
                navigation(NavUtils.destination(ActSelectTime.class).withDefaultRequestCode());
                return;
            }
            return;
        }
        if (Automation.Condition.isSunCondition(condition)) {
            SunConditionParam sunConditionParam = (SunConditionParam) condition.getParams(SunConditionParam.class);
            if (sunConditionParam != null) {
                SceneHelper.instance().conditionParam = sunConditionParam;
                navigation(NavUtils.destination(ActSelectSunWeather.class).withDefaultRequestCode());
                return;
            }
            return;
        }
        if (Automation.Condition.isHumidityCondition(condition)) {
            HumidityConditionParam humidityConditionParam = (HumidityConditionParam) condition.getParams(HumidityConditionParam.class);
            if (humidityConditionParam != null) {
                SceneHelper.instance().conditionParam = humidityConditionParam;
                navigation(NavUtils.destination(ActSelectHumidityWeather.class).withDefaultRequestCode());
                return;
            }
            return;
        }
        if (Automation.Condition.isPm25Condition(condition)) {
            Pm25ConditionParam pm25ConditionParam = (Pm25ConditionParam) condition.getParams(Pm25ConditionParam.class);
            if (pm25ConditionParam != null) {
                SceneHelper.instance().conditionParam = pm25ConditionParam;
                navigation(NavUtils.destination(ActSelectPmWeather.class).withDefaultRequestCode());
                return;
            }
            return;
        }
        if (Automation.Condition.isTemperatureCondition(condition)) {
            TemperatureConditionParam temperatureConditionParam = (TemperatureConditionParam) condition.getParams(TemperatureConditionParam.class);
            if (temperatureConditionParam != null) {
                SceneHelper.instance().conditionParam = temperatureConditionParam;
                navigation(NavUtils.destination(ActSelectTemperatureWeather.class).withDefaultRequestCode());
                return;
            }
            return;
        }
        if (Automation.Condition.isAddressCondition(condition)) {
            ReachLeaveParam reachLeaveParam = (ReachLeaveParam) condition.getParams(ReachLeaveParam.class);
            if (reachLeaveParam == null || this.isLocal) {
                return;
            }
            SceneHelper.instance().conditionParam = reachLeaveParam;
            navigation(NavUtils.destination(ActSelectAddress.class).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode());
            return;
        }
        if (Automation.Condition.isDeviceCondition(condition)) {
            DeviceConditionParam deviceConditionParam2 = (DeviceConditionParam) condition.getParams(DeviceConditionParam.class);
            if (deviceConditionParam2 != null) {
                SceneHelper.instance().conditionParam = deviceConditionParam2;
                Device device = getDevice(deviceConditionParam2.deviceid);
                if (device == null) {
                    navigation(NavUtils.destination(ActSelectDeviceForCondition.class).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.CHOOSE_DEVICE, true).withDefaultRequestCode());
                    return;
                } else if (isStatus) {
                    SceneHelper.instance().goSelectStatusCondition(ActivityUtils.getTopActivity(), device, deviceConditionParam2.index);
                    return;
                } else {
                    SceneHelper.instance().goSelectCondition(ActivityUtils.getTopActivity(), device, deviceConditionParam2.index);
                    return;
                }
            }
            return;
        }
        if (!Automation.Condition.isEnv(condition) || (deviceConditionParam = (DeviceConditionParam) condition.getParams(DeviceConditionParam.class)) == null) {
            return;
        }
        SceneHelper.instance().conditionParam = deviceConditionParam;
        navigation(NavUtils.destination(ActSelectEnvStatusCondition.class).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.CHOOSE_DEVICE, true).withDefaultRequestCode());
    }

    public int getIconRes(Object object) {
        return ProductRepository.getProductIcon(object);
    }

    public void addAutomation() {
        Automation automation = new Automation();
        this.automation = automation;
        automation.setIntervaltime(this.intervalTime.getValue().intValue());
        this.automation.setCycleindex(this.cycleIndex.getValue().intValue());
        this.automation.setPlaceId(this.placeId);
        this.automation.setName(this.automationName.getValue());
        this.automation.setStartTime(this.startTime);
        this.automation.setEndTime(this.endTime);
        this.automation.setWeeks(this.weeks);
        this.automation.setTimeZone(HelpUtils.getGmtTimeZone());
        this.automation.setPicIndex(this.picIndex.getValue().intValue());
        this.automation.setEnable(true);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.conditionList);
        arrayList.addAll(this.statusConditionList);
        this.automation.setConditions(arrayList);
        for (int i = 0; i < this.actionList.size(); i++) {
            if (this.actionList.get(i).getActiontype() == 3) {
                this.actionList.get(i).setParams(SceneHelper.createAppNoticeParam(ActivityUtils.getTopActivity(), this.automationName.getValue()));
            }
        }
        this.automation.setActions(this.actionList);
        this.automation.setConditionType(this.conditionType);
        this.automation.setIndex(Integer.MAX_VALUE);
        this.automation.setAutomationType(this.isLocal ? 2 : 1);
        if (this.isLocal) {
            this.automation.setGatewayDeviceId(this.gatewayId.getValue().longValue());
        }
        ((ObservableSubscribeProxy) Injection.net().addAutomation(this.automation).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomationVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddAutomationVM.this.lambda$addAutomation$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddAutomationVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomationVM$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddAutomationVM.this.lambda$addAutomation$3((AddAutomationResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addAutomation$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addAutomation$3(AddAutomationResponse addAutomationResponse) throws Exception {
        this.automation.setAutomationId(addAutomationResponse.getAutomationid());
        Injection.repo().auto().saveAutomation(this.automation);
        finishActivity();
    }

    public void editAutomation() {
        this.automation.setName(this.automationName.getValue());
        this.automation.setIntervaltime(this.intervalTime.getValue().intValue());
        this.automation.setCycleindex(this.cycleIndex.getValue().intValue());
        this.automation.setStartTime(this.startTime);
        this.automation.setEndTime(this.endTime);
        this.automation.setWeeks(this.weeks);
        this.automation.setTimeZone(this.timeZone);
        this.automation.setPicIndex(this.picIndex.getValue().intValue());
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.conditionList);
        arrayList.addAll(this.statusConditionList);
        this.automation.setConditions(arrayList);
        this.automation.setAutomationType(this.isLocal ? 2 : 1);
        if (this.isLocal) {
            this.automation.setGatewayDeviceId(this.gatewayId.getValue().longValue());
        }
        for (int i = 0; i < this.actionList.size(); i++) {
            if (this.actionList.get(i).getActiontype() == 3) {
                this.actionList.get(i).setParams(SceneHelper.createAppNoticeParam(ActivityUtils.getTopActivity(), this.automationName.getValue()));
            }
        }
        this.automation.setActions(this.actionList);
        this.automation.setConditionType(this.conditionType);
        ((ObservableSubscribeProxy) Injection.net().editAutomation(this.automation).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomationVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddAutomationVM.this.lambda$editAutomation$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddAutomationVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomationVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddAutomationVM.this.lambda$editAutomation$5(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editAutomation$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editAutomation$5(Object obj) throws Exception {
        Injection.repo().auto().saveAutomation(this.automation);
        finishActivity();
    }

    public void removeAutomation(final long automationId1) {
        ((ObservableSubscribeProxy) Injection.net().removeAutomation(automationId1).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomationVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddAutomationVM.this.lambda$removeAutomation$6((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddAutomationVM$$ExternalSyntheticLambda3(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.automation.ActAddAutomationVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddAutomationVM.this.lambda$removeAutomation$7(automationId1, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeAutomation$6(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeAutomation$7(long j, Object obj) throws Exception {
        Injection.repo().auto().removeAutomation(j);
        this.automationId = 0L;
        finishActivity();
    }

    public boolean hasAction() {
        List<Automation.Action> list = this.actionList;
        if (list == null || list.size() == 0) {
            return false;
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (Automation.Action action : this.actionList) {
            if (MaskType.isDeviceAction(action.getActiontype())) {
                DeviceParam deviceParam = (DeviceParam) action.getParams(DeviceParam.class);
                if (deviceParam == null) {
                    return false;
                }
                if (TextUtils.isEmpty(deviceParam.option) && TextUtils.isEmpty(deviceParam.instruct)) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(deviceParam.deviceid);
                    String str = deviceByDeviceId.getFloorName() + deviceByDeviceId.getRoomName();
                    if (hashMap.containsKey(str)) {
                        hashMap.put(str, ((String) hashMap.get(str)) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + deviceByDeviceId.getDeviceName());
                    } else {
                        hashMap.put(str, deviceByDeviceId.getDeviceName());
                    }
                }
            } else if (MaskType.isGroupAction(action.getActiontype())) {
                GroupParam groupParam = (GroupParam) action.getParams(GroupParam.class);
                if (groupParam == null) {
                    return false;
                }
                if (TextUtils.isEmpty(groupParam.option) && TextUtils.isEmpty(groupParam.instruct)) {
                    Group groupByGroupId = Injection.repo().group().getGroupByGroupId(groupParam.groupid);
                    String str2 = groupByGroupId.getFloorName() + groupByGroupId.getRoomName();
                    if (hashMap.containsKey(str2)) {
                        hashMap.put(str2, ((String) hashMap.get(str2)) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP + groupByGroupId.getGroupName());
                    } else {
                        hashMap.put(str2, groupByGroupId.getGroupName());
                    }
                }
            } else {
                continue;
            }
        }
        if (hashMap.size() <= 0) {
            return true;
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            arrayList.add(new NoActionDevice((String) entry.getKey(), (String) entry.getValue()));
        }
        this.noSetActionDeviceEvent.postValue(arrayList);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x01c7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0117 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean checkError(android.content.Context r9) {
        /*
            Method dump skipped, instructions count: 556
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.automation.ActAddAutomationVM.checkError(android.content.Context):boolean");
    }

    static class NoActionDevice {
        private String name;
        private String room;

        public NoActionDevice(String room, String name) {
            this.name = name;
            this.room = room;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoom() {
            return this.room;
        }

        public void setRoom(String room) {
            this.room = room;
        }
    }
}