package com.ltech.smarthome.ui.device.setting;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActDeviceSettingDefaultBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActDeviceSettingDefault extends BaseDeviceSetActivity<ActDeviceSettingDefaultBinding, ActDeviceSettingDefaultVM> {
    private boolean searching = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_device_setting_default;
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
        ((ActDeviceSettingDefaultVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDeviceSettingDefaultVM) this.mViewModel).samePlace = getIntent().getBooleanExtra(Constants.SAME_PLACE, true);
        ((ActDeviceSettingDefaultVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActDeviceSettingDefaultVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        if (((ActDeviceSettingDefaultVM) this.mViewModel).samePlace) {
            Injection.repo().device().getDeviceFromDb(((ActDeviceSettingDefaultVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActDeviceSettingDefault.this.lambda$startObserve$0((Device) obj);
                }
            });
        } else {
            ((ActDeviceSettingDefaultVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDevice(((ActDeviceSettingDefaultVM) this.mViewModel).placeId, ((ActDeviceSettingDefaultVM) this.mViewModel).deviceId));
        }
        ((ActDeviceSettingDefaultVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDeviceSettingDefault.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActDeviceSettingDefaultVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDeviceSettingDefault.this.lambda$startObserve$2((Integer) obj);
            }
        });
        ((ActDeviceSettingDefaultVM) this.mViewModel).getBatteryEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDeviceSettingDefault.this.lambda$startObserve$4((Void) obj);
            }
        });
        MessageManager.getInstance().setBatteryStatusCallBack(new MessageManager.BatteryStatusCallBack() { // from class: com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault$$ExternalSyntheticLambda4
            @Override // com.smart.message.MessageManager.BatteryStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActDeviceSettingDefault.this.lambda$startObserve$5(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActDeviceSettingDefaultVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActDeviceSettingDefaultBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActDeviceSettingDefaultVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        if (ProductId.ID_SMART_PANEL_GQ_PRO.equals(device.getProductId()) || ProductId.ID_KEY_SWITCH_1.equals(device.getProductId()) || ProductId.ID_KEY_SWITCH_2.equals(device.getProductId()) || ProductId.ID_KEY_SWITCH_4.equals(device.getProductId())) {
            ((ActDeviceSettingDefaultBinding) this.mViewBinding).layoutSceneAndAutomation.setVisibility(8);
        } else if (ProductId.ID_DOOR_SENSOR.equals(device.getProductId())) {
            ((ActDeviceSettingDefaultBinding) this.mViewBinding).tvRelatedTip.setText(R.string.related_automation);
            ((ActDeviceSettingDefaultVM) this.mViewModel).queryAutomation(device.getDeviceId());
        } else {
            ((ActDeviceSettingDefaultVM) this.mViewModel).queryScene(device.getDeviceId());
        }
        if (ProductId.ID_RC4S.equals(device.getProductId())) {
            ((ActDeviceSettingDefaultBinding) this.mViewBinding).layoutBattery.setVisibility(0);
        }
        if (ProductId.ID_DRY_CONTACT_TO_BLE.equalsIgnoreCase(device.getProductId())) {
            ((ActDeviceSettingDefaultBinding) this.mViewBinding).layoutDeviceReplace.setVisibility(isOwnerOrManager() ? 0 : 8);
            isOwnerOrManager();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        ((ActDeviceSettingDefaultBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r2) {
        this.dialog = RelateInfoUtils.showImageTipDialog(((ActDeviceSettingDefaultVM) this.mViewModel).controlDevice.getValue(), this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActDeviceSettingDefault.this.lambda$startObserve$3(imageTipDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(ImageTipDialog imageTipDialog) {
        this.searching = true;
        CmdAssistant.getQueryCmdAssistant(((ActDeviceSettingDefaultVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryPanelState(this);
        getMainHandler().postDelayed(this.getBatteryRunnable, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(ResponseMsg responseMsg) {
        if (this.mViewBinding != 0) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(12, 14), 16);
            if (parseInt > 10) {
                ((ActDeviceSettingDefaultBinding) this.mViewBinding).tvBatteryTip.setText(parseInt + "%");
            } else {
                ((ActDeviceSettingDefaultBinding) this.mViewBinding).tvBatteryTip.setText(getString(R.string.low_battery_tip));
            }
            getMainHandler().removeCallbacks(this.getBatteryRunnable);
            if (this.searching) {
                ((ActDeviceSettingDefaultVM) this.mViewModel).uploadData(parseInt);
                SmartToast.showCenterShort(getResources().getString(R.string.search_success));
                if (this.dialog != null) {
                    this.dialog.dismissDialog();
                }
                this.searching = false;
            }
        }
    }
}