package com.ltech.smarthome.ui.device.microwave_sensor.test;

import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.device.microwave_sensor.ActSensitivitySetting;
import com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant;
import com.smart.product_agreement.bean.WaveSensorState;

/* loaded from: classes4.dex */
public class ActWaveSensorTestVM extends BaseViewModel {
    public int sensitivity;
    public int testMode;
    public MutableLiveData<Boolean> testResult = new MutableLiveData<>(false);
    public SingleLiveEvent<Void> finishEvent = new SingleLiveEvent<>();
    public MutableLiveData<Integer> refreshSentivity = new MutableLiveData<>();

    public void convertState(String resData, WaveSensorState sensorState) {
        if (resData.length() > 20) {
            sensorState.setEnable(Integer.parseInt(resData.substring(12, 14), 16) != 0);
            sensorState.setCurState(Integer.parseInt(resData.substring(14, 16), 16));
            int parseInt = Integer.parseInt(resData.substring(16, 18), 16);
            sensorState.setIndicatorLight(parseInt & 1);
            sensorState.setRelayPowerOn((parseInt & 2) == 2);
            sensorState.setSensitivity(Integer.parseInt(resData.substring(18, 20), 16));
            sensorState.setIlluminance(Integer.parseInt(resData.substring(20, 22), 16));
        }
    }

    public void getSensitivity() {
        if (WaveSensorHelper.instance().controlObject instanceof Group) {
            this.sensitivity = ((Group) WaveSensorHelper.instance().controlObject).getDeviceState().getWaveSensorState().getSensitivity();
        } else {
            this.sensitivity = ((Device) WaveSensorHelper.instance().controlObject).getDeviceState().getWaveSensorState().getSensitivity();
        }
    }

    public void goSensitivitySetting() {
        navigation(NavUtils.destination(ActSensitivitySetting.class).withBoolean(Constants.SETTING_PAGE, false).withInt(Constants.SELECT_POSITION, this.sensitivity).withDefaultRequestCode());
    }

    public DeviceAssistant getCmdHelper() {
        return CmdAssistant.getDeviceAssistant(WaveSensorHelper.instance().controlObject, new int[0]);
    }
}