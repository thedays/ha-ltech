package com.ltech.smarthome.ui.device.setting;

import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.musicplayer.ActMusicPlayerPowerOnStateSetting;
import com.ltech.smarthome.ui.device.musicplayer.ActPlaylistManage;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.BleMusicPlayerAssistant;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActBleMusicPlayerSettingVM extends BaseDeviceSetViewModel {
    public long deviceId;
    public long placeId;
    public SingleLiveEvent<Void> showIgnoreCurrentConnectionsEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> showBluetoothStateEvent = new SingleLiveEvent<>();
    public MutableLiveData<String> bleState = new MutableLiveData<>();
    public MutableLiveData<String> powerState = new MutableLiveData<>();
    public int curBleState = 1;
    public int onOffState = 1;
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSettingVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActBleMusicPlayerSettingVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.layout_bluetooth_state /* 2131297373 */:
                this.showBluetoothStateEvent.call();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                this.showEditRoomDialogEvent.call();
                break;
            case R.id.layout_device_name /* 2131297433 */:
                this.showEditNameDialogEvent.call();
                break;
            case R.id.layout_device_status_after_power_on /* 2131297435 */:
                int i = this.onOffState;
                int i2 = 1;
                if (i != 1) {
                    i2 = 2;
                    if (i != 2) {
                        i2 = i != 5 ? 0 : 3;
                    }
                }
                navigation(NavUtils.destination(ActMusicPlayerPowerOnStateSetting.class).withInt(Constants.SELECT_POSITION, i2).withDefaultRequestCode());
                break;
            case R.id.layout_ignore_current_connections /* 2131297493 */:
                this.showIgnoreCurrentConnectionsEvent.call();
                break;
            case R.id.layout_manager_playlist /* 2131297533 */:
                navigation(NavUtils.destination(ActPlaylistManage.class).withLong("device_id", this.deviceId));
                break;
            case R.id.layout_upgrade /* 2131297687 */:
                this.upgradeEvent.call();
                break;
            case R.id.tv_delete_device /* 2131298576 */:
                this.showDeleteDialogEvent.call();
                break;
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void ignoreConnect() {
        changeBluetoothModel(3);
    }

    public void changeBluetoothModel(final int pos) {
        showLoadingDialog();
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).changeBluetoothModel(ActivityUtils.getTopActivity(), 1 << (pos - 1), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSettingVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    ActBleMusicPlayerSettingVM.this.curBleState = pos;
                    int i = pos;
                    if (i == 1) {
                        ActBleMusicPlayerSettingVM.this.bleState.setValue(ActivityUtils.getTopActivity().getString(R.string.bluetooth_search));
                    } else if (i == 2) {
                        ActBleMusicPlayerSettingVM.this.bleState.setValue(ActivityUtils.getTopActivity().getString(R.string.bluetooth_receive));
                    } else if (i == 3) {
                        ActBleMusicPlayerSettingVM.this.bleState.setValue(ActivityUtils.getTopActivity().getString(R.string.bluetooth_close));
                    }
                    ActBleMusicPlayerSettingVM.this.dismissLoadingDialog();
                    return;
                }
                ActBleMusicPlayerSettingVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
            }
        });
    }

    public void regDeviceStateLisener() {
        MessageManager.getInstance().setMusicPlayerBleStatusCallBack(new MessageManager.MusicPlayerBleStatusCallBack() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSettingVM$$ExternalSyntheticLambda2
            @Override // com.smart.message.MessageManager.MusicPlayerBleStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActBleMusicPlayerSettingVM.this.lambda$regDeviceStateLisener$1(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$regDeviceStateLisener$1(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            int bleState = BleMusicPlayerAssistant.coverToPlayerState(responseMsg.getResData()).getBleState();
            if (bleState == 1) {
                this.curBleState = 1;
                this.bleState.setValue(ActivityUtils.getTopActivity().getString(R.string.bluetooth_search));
            } else if (bleState == 2) {
                this.curBleState = 2;
                this.bleState.setValue(ActivityUtils.getTopActivity().getString(R.string.bluetooth_receive));
            } else if (bleState == 3) {
                this.bleState.setValue("");
            } else if (bleState == 4) {
                this.curBleState = 3;
                this.bleState.setValue(ActivityUtils.getTopActivity().getString(R.string.bluetooth_close));
            }
        }
        queryState();
    }

    public void checkDeviceState() {
        CmdAssistant.getQueryCmdAssistant(this.controlDevice.getValue(), new int[0]).queryPanelDeviceMotorState(ActivityUtils.getTopActivity());
    }

    public void queryState() {
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).queryOnState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSettingVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleMusicPlayerSettingVM.this.lambda$queryState$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$2(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        SelectPowerOnStateDialog.generateContentList();
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        this.onOffState = parseInt;
        if (parseInt == 1) {
            this.powerState.setValue(ActivityUtils.getTopActivity().getString(R.string.music_player_power_state2));
            return;
        }
        if (parseInt == 2) {
            this.powerState.setValue(ActivityUtils.getTopActivity().getString(R.string.music_player_power_state3));
        } else if (parseInt == 3) {
            this.powerState.setValue(ActivityUtils.getTopActivity().getString(R.string.music_player_power_state1));
        } else {
            if (parseInt != 5) {
                return;
            }
            this.powerState.setValue(ActivityUtils.getTopActivity().getString(R.string.music_player_power_state4));
        }
    }

    public void changePowerOnModel(final int model) {
        CmdAssistant.getBleMusicPlayerAssistant(this.controlDevice.getValue(), new int[0]).changePowerOnModel(ActivityUtils.getTopActivity(), model, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSettingVM.2
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null) {
                    ActBleMusicPlayerSettingVM.this.onOffState = model;
                    int i = model;
                    if (i == 1) {
                        ActBleMusicPlayerSettingVM.this.powerState.setValue(ActivityUtils.getTopActivity().getString(R.string.music_player_power_state2));
                        return;
                    }
                    if (i == 2) {
                        ActBleMusicPlayerSettingVM.this.powerState.setValue(ActivityUtils.getTopActivity().getString(R.string.music_player_power_state3));
                        return;
                    } else if (i == 3) {
                        ActBleMusicPlayerSettingVM.this.powerState.setValue(ActivityUtils.getTopActivity().getString(R.string.music_player_power_state1));
                        return;
                    } else {
                        if (i != 5) {
                            return;
                        }
                        ActBleMusicPlayerSettingVM.this.powerState.setValue(ActivityUtils.getTopActivity().getString(R.string.music_player_power_state4));
                        return;
                    }
                }
                ActBleMusicPlayerSettingVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
            }
        });
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel
    public Place getCurPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }
}