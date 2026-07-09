package com.ltech.smarthome.ui.device.cg485;

import android.content.Context;
import android.view.View;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActCg485SettingVM extends BaseDeviceSetViewModel {
    public long placeId;
    public String productId;
    public SingleLiveEvent<Boolean> queryParamResult = new SingleLiveEvent<>();
    public int format = 1;
    public int baudRate = 1;
    public int parity = 1;
    public int dataBits = 1;
    public int stopBits = 1;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485SettingVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActCg485SettingVM.this.lambda$new$0((View) obj);
        }
    });
    public final IAction<ResponseMsg> iQuery = new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActCg485SettingVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.base.IAction
        public final void act(Object obj) {
            ActCg485SettingVM.this.lambda$new$1((ResponseMsg) obj);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_create_group /* 2131297408 */:
                this.showCreateGroupDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_serial_param /* 2131297632 */:
                showLoadingDialog();
                querySettings(ActivityUtils.getTopActivity());
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    public void goSerialSettings() {
        NavUtils.destination(ActSerialSetting.class).withLong(Constants.CONTROL_ID, this.controlId).withInt(Constants.BAUD_RATE, this.baudRate).withInt(Constants.PARITY, this.parity).withInt(Constants.DATA_BITS, this.dataBits).withInt(Constants.STOP_BITS, this.stopBits).navigation(ActivityUtils.getTopActivity());
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            this.format = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
            this.baudRate = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16);
            this.parity = Integer.parseInt(responseMsg.getResData().substring(20, 22), 16);
            this.dataBits = Integer.parseInt(responseMsg.getResData().substring(22, 24), 16);
            this.stopBits = Integer.parseInt(responseMsg.getResData().substring(24, 26), 16);
            this.queryParamResult.setValue(true);
            return;
        }
        this.queryParamResult.setValue(false);
    }

    public void querySettings(Context context) {
        CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryCg485Setting(context, this.iQuery);
    }
}