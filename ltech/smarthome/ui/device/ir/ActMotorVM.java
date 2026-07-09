package com.ltech.smarthome.ui.device.ir;

import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.ir.Device433Repository;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmdParam;

/* loaded from: classes4.dex */
public class ActMotorVM extends BaseViewModel {
    public String cmdName;
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public long controlId;
    public BaseCmdParam mMotorCmdParam;
    public Device433Repository.MotorCodeLib motorCodeLib;
    public boolean selectAction;

    public void sendMotorCmd(MotorKeyItem keyItem) {
        Device value = this.controlDevice.getValue();
        if (this.motorCodeLib == null || value == null) {
            return;
        }
        this.cmdName = keyItem.getName();
        if (!this.selectAction) {
            showLoadingDialog();
        }
        this.mMotorCmdParam = CmdAssistant.getGatewayAssistant(this.controlDevice.getValue(), new int[0]).sendMotorControl(ActivityUtils.getTopActivity(), this.motorCodeLib.getCodeByKey(keyItem.getKey()), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActMotorVM.this.dismissLoadingDialog();
                if (responseMsg == null) {
                    ActMotorVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                }
            }
        }, !this.selectAction);
    }
}