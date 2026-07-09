package com.ltech.smarthome.ui.device.remote_controller;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActEurPanelSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSettingVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;

/* loaded from: classes4.dex */
public class ActRemoteBatterySetting extends BaseDeviceSetActivity<ActEurPanelSettingBinding, ActEurPanelSettingVM> {
    private boolean searching = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_eur_panel_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutBattery.setVisibility(0);
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutSetOnState.setVisibility(8);
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutSetDmxType.setVisibility(8);
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutBuzzer.setVisibility(8);
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutZoneControl.setVisibility(8);
        ((ActEurPanelSettingBinding) this.mViewBinding).tvEurFunctionTips.setVisibility(8);
        ((ActEurPanelSettingBinding) this.mViewBinding).tvRelatedTip.setText(R.string.related_automation);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActEurPanelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEurPanelSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBatterySetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBatterySetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActEurPanelSettingBinding) this.mViewBinding).refreshLayout.setEnableRefresh(false);
        ((ActEurPanelSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActEurPanelSettingVM) this.mViewModel).adjustKRange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBatterySetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBatterySetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBatterySetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBatterySetting.this.lambda$startObserve$3((Integer) obj);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).getBatteryEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBatterySetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActRemoteBatterySetting.this.lambda$startObserve$5((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActEurPanelSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActEurPanelSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        initRelatedInfoView(device);
        if (((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.getBattery() != -1) {
            if (((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.getBattery() > 10) {
                ((ActEurPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.getBattery() + "%");
            } else {
                ((ActEurPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(getString(R.string.low_battery_tip));
            }
        }
        MessageManager.getInstance().setBatteryStatusCallBack(new MessageManager.BatteryStatusCallBack() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBatterySetting$$ExternalSyntheticLambda5
            @Override // com.smart.message.MessageManager.BatteryStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActRemoteBatterySetting.this.lambda$startObserve$0(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(ResponseMsg responseMsg) {
        if (this.mViewBinding != 0) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(12, 14), 16);
            if (parseInt > 10) {
                ((ActEurPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(parseInt + "%");
            } else {
                ((ActEurPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(getString(R.string.low_battery_tip));
            }
            getMainHandler().removeCallbacks(this.getBatteryRunnable);
            if (this.searching) {
                ((ActEurPanelSettingVM) this.mViewModel).uploadData(parseInt);
                SmartToast.showCenterShort(getResources().getString(R.string.search_success));
                if (this.dialog != null) {
                    this.dialog.dismissDialog();
                }
                this.searching = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r4) {
        ((ActEurPanelSettingVM) this.mViewModel).clickAdjustKRange(this, ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue(), ((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        ((ActEurPanelSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r2) {
        this.dialog = RelateInfoUtils.showImageTipDialog(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue(), this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActRemoteBatterySetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActRemoteBatterySetting.this.lambda$startObserve$4(imageTipDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(ImageTipDialog imageTipDialog) {
        this.searching = true;
        CmdAssistant.getQueryCmdAssistant(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryPanelState(this);
        getMainHandler().postDelayed(this.getBatteryRunnable, 5000L);
    }

    private void initRelatedInfoView(Device device) {
        ((ActEurPanelSettingVM) this.mViewModel).initRelateInfoList(device);
        if (device.getProductId().equals(ProductId.ID_RC_B2)) {
            ((ActEurPanelSettingVM) this.mViewModel).isShowBindKRange.setValue(Boolean.valueOf(((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.isKnobShowKRange()));
        } else {
            ((ActEurPanelSettingVM) this.mViewModel).isShowBindKRange.setValue(false);
        }
        if (device.getProductId().equals(ProductId.ID_RC_B5)) {
            ((ActEurPanelSettingBinding) this.mViewBinding).tvFunctionState.setText(R.string.scene_function);
            int lightColorType = ProductRepository.getLightColorType((Object) ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue());
            if (lightColorType == 3) {
                ((ActEurPanelSettingBinding) this.mViewBinding).tvControlTypeState.setText(R.string.type_rgb);
                return;
            } else if (lightColorType == 4) {
                ((ActEurPanelSettingBinding) this.mViewBinding).tvControlTypeState.setText(R.string.type_rgbw);
                return;
            } else {
                if (lightColorType != 5) {
                    return;
                }
                ((ActEurPanelSettingBinding) this.mViewBinding).tvControlTypeState.setText(R.string.type_rgbwy);
                return;
            }
        }
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutControlType.setVisibility(8);
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutBrtButton.setVisibility(device.getProductId().equals(ProductId.ID_RC_B8) ? 0 : 8);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Device deviceById = Injection.repo().device().getDeviceById(((ActEurPanelSettingVM) this.mViewModel).controlId);
        ((ActEurPanelSettingVM) this.mViewModel).controlDevice.setValue(deviceById);
        if (deviceById != null) {
            ((ActEurPanelSettingVM) this.mViewModel).queryScene(deviceById.getDeviceId());
        }
    }
}