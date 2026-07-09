package com.ltech.smarthome.ui.device.setting;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActDmx512SettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.Channel512Dialog;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActDmx512Setting extends BaseDeviceSetActivity<ActDmx512SettingBinding, ActDmx512SettingVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dmx_512_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        setEditString("             ");
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDmx512SettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActDmx512SettingVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((ActDmx512SettingVM) this.mViewModel).channelNum = getIntent().getIntExtra(Constants.ZONE_NUM, 6);
        ((ActDmx512SettingVM) this.mViewModel).firstAddress = getIntent().getIntExtra(Constants.ADDRESS, 1);
        ((ActDmx512SettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDevice(((ActDmx512SettingVM) this.mViewModel).placeId, ((ActDmx512SettingVM) this.mViewModel).deviceId));
        ((ActDmx512SettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActDmx512Setting$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDmx512Setting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActDmx512SettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActDmx512Setting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDmx512Setting.this.lambda$startObserve$1((Integer) obj);
            }
        });
        ((ActDmx512SettingVM) this.mViewModel).showAddressDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.setting.ActDmx512Setting.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActDmx512Setting.this.showAddressSettingDialog();
            }
        });
        showData();
        queryOutputType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActDmx512SettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActDmx512SettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        ((ActDmx512SettingVM) this.mViewModel).queryAutomation(device.getDeviceId());
        int imageIndex = device.getImageIndex();
        int[] lightIconValue = IconRepository.getLightIconValue(this);
        for (int i = 0; i < lightIconValue.length; i++) {
            if (i == imageIndex) {
                ((ActDmx512SettingBinding) this.mViewBinding).ivIcon.setImageResource(lightIconValue[i]);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Integer num) {
        ((ActDmx512SettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showData() {
        ((ActDmx512SettingBinding) this.mViewBinding).tvAddress.setText(getString(R.string.app_str_first_address) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + ((((ActDmx512SettingVM) this.mViewModel).firstAddress < 100 ? "00".substring(0, 3 - String.valueOf(((ActDmx512SettingVM) this.mViewModel).firstAddress).length()) : "") + ((ActDmx512SettingVM) this.mViewModel).firstAddress) + " " + getString(R.string.app_str_channel_num) + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + ((ActDmx512SettingVM) this.mViewModel).channelNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAddressSettingDialog() {
        Channel512Dialog.asDefault().setCallback(new Channel512Dialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.setting.ActDmx512Setting.2
            @Override // com.ltech.smarthome.view.dialog.Channel512Dialog.OnConfirmCallback
            public void onConfirmClick(Channel512Dialog dialog) {
                ((ActDmx512SettingVM) ActDmx512Setting.this.mViewModel).channelNum = dialog.getProgress();
                ((ActDmx512SettingVM) ActDmx512Setting.this.mViewModel).firstAddress = dialog.getHeadAddress();
                ActDmx512Setting.this.showData();
                ((ActDmx512SettingVM) ActDmx512Setting.this.mViewModel).setChannel();
                ActDmx512Setting.this.setResult(-1);
            }
        }).setProgress(((ActDmx512SettingVM) this.mViewModel).channelNum).setHeadAddress(((ActDmx512SettingVM) this.mViewModel).firstAddress).showDialog(this);
    }

    private void queryOutputType() {
        CmdAssistant.getQueryCmdAssistant(((ActDmx512SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryOutPutType(this, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.setting.ActDmx512Setting.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                String str;
                if (msg != null && msg.getStateCode() == 0 && msg.getResData() != null && msg.getResData().length() > 16) {
                    int parseInt = Integer.parseInt(msg.getResData().substring(16), 16);
                    if (parseInt > 0) {
                        switch (parseInt) {
                            case 1:
                                str = "DMX(" + ActDmx512Setting.this.getString(R.string.type_dim) + ")";
                                break;
                            case 2:
                                str = "DMX(" + ActDmx512Setting.this.getString(R.string.type_ct) + ")";
                                break;
                            case 3:
                                str = "DMX(" + ActDmx512Setting.this.getString(R.string.type_rgb) + ")";
                                break;
                            case 4:
                                str = "DMX(" + ActDmx512Setting.this.getString(R.string.type_rgbw) + ")";
                                break;
                            case 5:
                                str = "DMX(" + ActDmx512Setting.this.getString(R.string.type_rgbwy) + ")";
                                break;
                            case 6:
                                str = "0-10V(" + ActDmx512Setting.this.getString(R.string.type_dim) + ")";
                                break;
                            case 7:
                                str = "0-10V(" + ActDmx512Setting.this.getString(R.string.type_ct) + ")";
                                break;
                            case 8:
                                str = "DALI(" + ActDmx512Setting.this.getString(R.string.type_dim) + ")";
                                break;
                            case 9:
                                str = "DALI(" + ActDmx512Setting.this.getString(R.string.type_ct) + ")";
                                break;
                            case 10:
                                str = "DALI(" + ActDmx512Setting.this.getString(R.string.type_rgb) + ")";
                                break;
                            case 11:
                                str = "DALI(" + ActDmx512Setting.this.getString(R.string.type_rgbw) + ")";
                                break;
                            case 12:
                                str = "DALI(" + ActDmx512Setting.this.getString(R.string.type_rgbwy) + ")";
                                break;
                            case 13:
                                str = "DMX(" + ActDmx512Setting.this.getString(R.string.type_512) + ")";
                                break;
                            default:
                                str = "";
                                break;
                        }
                        ((ActDmx512SettingVM) ActDmx512Setting.this.mViewModel).outputType.setValue(str);
                        ((ActDmx512SettingVM) ActDmx512Setting.this.mViewModel).hasOutputType.setValue(true);
                        return;
                    }
                    ((ActDmx512SettingVM) ActDmx512Setting.this.mViewModel).hasOutputType.setValue(false);
                    return;
                }
                ((ActDmx512SettingVM) ActDmx512Setting.this.mViewModel).hasOutputType.setValue(false);
            }
        });
    }
}