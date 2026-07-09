package com.ltech.smarthome.ui.device.curtain_motor;

import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.device.ir.MotorKeyItem;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.base.BaseCmdParam;
import com.smart.product_agreement.bean.CurtainMotorState;

/* loaded from: classes4.dex */
public class ActBleMotorVM extends BaseViewModel {
    public String cmdName;
    public long controlId;
    public boolean groupControl;
    public BaseCmdParam mMotorCmdParam;
    public boolean selectAction;
    public SingleLiveEvent<Object> controlDevice = new SingleLiveEvent<>();
    public MutableLiveData<CurtainMotorState> stateMutableLiveData = new MutableLiveData<>();

    public void sendMotorCmd(MotorKeyItem keyItem) {
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).controlCurtain(ActivityUtils.getTopActivity(), Integer.parseInt(keyItem.getKey()));
    }

    public void sendMotorModeCmd(MotorKeyItem keyItem) {
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).sendCurtainMode(ActivityUtils.getTopActivity(), Integer.parseInt(keyItem.getKey()), Integer.parseInt(keyItem.getExtraData()));
    }

    public void sendMotorPercentCmd(int progress) {
        CmdAssistant.getDeviceAssistant(this.controlDevice.getValue(), new int[0]).controlCurtainWithPercent(ActivityUtils.getTopActivity(), 6, 100 - (progress - 50));
    }

    public void queryBleMotor(Device device) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryCurtainMotorState(ActivityUtils.getTopActivity());
    }

    public void queryBleMotor(Group group) {
        CmdAssistant.getQueryCmdAssistant(group, new int[0]).queryCurtainMotorState(ActivityUtils.getTopActivity());
    }

    public void setCurtainState(CurtainMotorState parserCurtainMotorState) {
        this.stateMutableLiveData.setValue(parserCurtainMotorState);
    }
}