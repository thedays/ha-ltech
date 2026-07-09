package com.ltech.smarthome.ui.device.knobpanel;

import android.content.Intent;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActKnobPanelSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.config.ActMeshScanIcon;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.screenpanel.ActScreenPanelElderlyMode;
import com.ltech.smarthome.ui.my.ActLanguageSelect;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActKnobPanelSetting extends BaseDeviceSetActivity<ActKnobPanelSettingBinding, ActknobPanelSettingVM> {
    private int colorType;
    private int mSelectHour;
    private int mSelectMin;
    private RelateInfoAssistant relateInfoAssistant;
    private boolean searching = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_knob_panel_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
        ((ActKnobPanelSettingBinding) this.mViewBinding).sbEngravedText.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActknobPanelSettingVM) ActKnobPanelSetting.this.mViewModel).setEngravedText(isChecked);
            }
        });
        ((ActKnobPanelSettingBinding) this.mViewBinding).sbNightMode.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting.2
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (Injection.repo().device().getSuperPanel() != null) {
                    ((ActknobPanelSettingVM) ActKnobPanelSetting.this.mViewModel).setNightMode(isChecked);
                    ((ActKnobPanelSettingBinding) ActKnobPanelSetting.this.mViewBinding).sbAutoTurnOff.setChecked(false);
                } else {
                    ((ActKnobPanelSettingBinding) ActKnobPanelSetting.this.mViewBinding).sbNightMode.setCheckedNotByUser(false);
                    ActKnobPanelSetting.this.showNoSuperPanelDialog();
                }
            }
        });
        ((ActKnobPanelSettingBinding) this.mViewBinding).sbAutoTurnOff.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting.3
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActknobPanelSettingVM) ActKnobPanelSetting.this.mViewModel).setAutoTurnOff(isChecked);
            }
        });
        ((ActKnobPanelSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActKnobPanelSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActKnobPanelSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActKnobPanelSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda6
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActKnobPanelSetting.this.lambda$initView$0(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((ActknobPanelSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            ((ActknobPanelSettingVM) this.mViewModel).isFirst = true;
            ((ActknobPanelSettingVM) this.mViewModel).loadDeviceStatus(((ActknobPanelSettingVM) this.mViewModel).controlDevice.getValue());
            ((ActknobPanelSettingVM) this.mViewModel).queryScene(((ActknobPanelSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId());
        }
        ((ActKnobPanelSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActknobPanelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        final Device deviceById = Injection.repo().device().getDeviceById(((ActknobPanelSettingVM) this.mViewModel).controlId);
        if (deviceById.getProductId().equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
            ((ActknobPanelSettingVM) this.mViewModel).isProPanel.setValue(true);
        }
        ((ActknobPanelSettingVM) this.mViewModel).controlDevice.setValue(deviceById);
        ((ActknobPanelSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$1(deviceById, (Device) obj);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).sensitivity.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$2((Integer) obj);
            }
        });
        ((ActKnobPanelSettingBinding) this.mViewBinding).sbModeMemorize.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActKnobPanelSetting.this.lambda$startObserve$5(switchButton, z);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).showSensitivityDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda14
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$6((Void) obj);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).showModeOrderDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda15
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$7((Void) obj);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).selectEndTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActKnobPanelSetting.this.showTimeDialog(false);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).elderlyModeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda16
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$8((Void) obj);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).changeLanguageEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$9((Void) obj);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).selectStarTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActKnobPanelSetting.this.showTimeDialog(true);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).upgradeIconEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$10((Void) obj);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$11((Integer) obj);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).getBatteryEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$13((Void) obj);
            }
        });
        ((ActknobPanelSettingVM) this.mViewModel).adjustKRange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKnobPanelSetting.this.lambda$startObserve$14(deviceById, (Void) obj);
            }
        });
        if (((ActknobPanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
            MessageManager.getInstance().setBatteryStatusCallBack(new MessageManager.BatteryStatusCallBack() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda11
                @Override // com.smart.message.MessageManager.BatteryStatusCallBack
                public final void onDataReceive(ResponseMsg responseMsg) {
                    ActKnobPanelSetting.this.lambda$startObserve$15(responseMsg);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$startObserve$1(com.ltech.smarthome.model.bean.Device r12, com.ltech.smarthome.model.bean.Device r13) {
        /*
            Method dump skipped, instructions count: 357
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting.lambda$startObserve$1(com.ltech.smarthome.model.bean.Device, com.ltech.smarthome.model.bean.Device):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        String[] stringArray = getResources().getStringArray(R.array.sq_sensitivity_array);
        if (num.intValue() <= 0 || num.intValue() > stringArray.length) {
            return;
        }
        ((ActKnobPanelSettingBinding) this.mViewBinding).tvSensitivity.setText(stringArray[num.intValue() - 1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(final SwitchButton switchButton, final boolean z) {
        if (((ActknobPanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
            ImageTipDialog.asDefault().setTitle(getString(R.string.sq_click_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.pic_click_tip).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActKnobPanelSetting.this.lambda$startObserve$3(switchButton, z, imageTipDialog);
                }
            }).setCancelCallback(new ImageTipDialog.OnCancelCallback() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda8
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnCancelCallback
                public final void onCancel() {
                    SwitchButton.this.setCheckedNotByUser(!z);
                }
            }).showDialog(this);
        } else {
            ((ActknobPanelSettingVM) this.mViewModel).setPowerOffStatus(switchButton, z, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(SwitchButton switchButton, boolean z, ImageTipDialog imageTipDialog) {
        ((ActknobPanelSettingVM) this.mViewModel).setPowerOffStatus(switchButton, z, imageTipDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r1) {
        showSensitivityDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r4) {
        NavUtils.destination(ActKnobSortDoubleMode.class).withLong(Constants.CONTROL_ID, ((ActknobPanelSettingVM) this.mViewModel).controlDevice.getValue().getId()).withInt(Constants.LIGHT_TYPE, this.colorType).withIntArray(Constants.MODE_SET, ((ActknobPanelSettingVM) this.mViewModel).orderArray).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Void r4) {
        NavUtils.destination(ActScreenPanelElderlyMode.class).withLong(Constants.CONTROL_ID, ((ActknobPanelSettingVM) this.mViewModel).controlId).withInt(Constants.ELDERLY_MODE, this.relateInfoAssistant.getSwitchScreenBigIcon()).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Void r4) {
        NavUtils.destination(ActLanguageSelect.class).withLong(Constants.CONTROL_ID, ((ActknobPanelSettingVM) this.mViewModel).controlId).withInt(Constants.LANGUAGE_TPYE, this.relateInfoAssistant.getSwitchScreenLanguage()).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Void r4) {
        NavUtils.destination(ActMeshScanIcon.class).withLong(Constants.CONTROL_ID, 9999L).withLong(Constants.PLACE_ID, ((ActknobPanelSettingVM) this.mViewModel).getCurPlace().getPlaceId()).withBoolean(Constants.ICON_UPGRADE, true).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(Integer num) {
        ((ActKnobPanelSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Void r2) {
        this.dialog = ImageTipDialog.asDefault().setTitle(getString(R.string.sq_click_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.pic_click_tip).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActKnobPanelSetting.this.lambda$startObserve$12(imageTipDialog);
            }
        });
        this.dialog.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(ImageTipDialog imageTipDialog) {
        this.searching = true;
        getMainHandler().postDelayed(this.getBatteryRunnable, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$14(Device device, Void r4) {
        ((ActknobPanelSettingVM) this.mViewModel).clickAdjustKRange(this, device, this.relateInfoAssistant, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$15(ResponseMsg responseMsg) {
        if (this.mViewBinding != 0) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(12, 14), 16);
            if (parseInt > 10) {
                ((ActKnobPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(parseInt + "%");
            } else {
                ((ActKnobPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(getString(R.string.low_battery_tip));
            }
            getMainHandler().removeCallbacks(this.getBatteryRunnable);
            ((ActknobPanelSettingVM) this.mViewModel).uploadData(parseInt);
            if (this.searching) {
                SmartToast.showCenterShort(getResources().getString(R.string.search_success));
                if (this.dialog != null) {
                    this.dialog.dismissDialog();
                }
                this.searching = false;
            }
        }
    }

    public void refreshRelateInfo(Device device) {
        RelateInfoUtils.initRelateInfoList(device);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        ((ActknobPanelSettingVM) this.mViewModel).isShowBindKRange.setValue(Boolean.valueOf(this.relateInfoAssistant.isKnobShowKRange()));
        ((ActKnobPanelSettingBinding) this.mViewBinding).tvElderlyModeTip.setText(getString(this.relateInfoAssistant.getSwitchScreenBigIcon() == 2 ? R.string.app_str_turned_on : R.string.app_str_turned_off));
        ((ActKnobPanelSettingBinding) this.mViewBinding).tvLanguageTip.setText(getString(this.relateInfoAssistant.getSwitchScreenLanguage() == 2 ? R.string.language_english : R.string.language_chinese));
    }

    public void showTimeDialog(final boolean isStart) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            arrayList.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < 60; i2++) {
            arrayList2.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ActknobPanelSettingVM actknobPanelSettingVM = (ActknobPanelSettingVM) this.mViewModel;
        this.mSelectHour = isStart ? actknobPanelSettingVM.getStarH() : actknobPanelSettingVM.getEndH();
        ActknobPanelSettingVM actknobPanelSettingVM2 = (ActknobPanelSettingVM) this.mViewModel;
        this.mSelectMin = isStart ? actknobPanelSettingVM2.getStarM() : actknobPanelSettingVM2.getEndM();
        TimeSelectDialog.asDefault().setTitle(getString(isStart ? R.string.start_time : R.string.end_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectMinPosition(this.mSelectHour).setSelectSecPosition(this.mSelectMin).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActKnobPanelSetting.this.lambda$showTimeDialog$16(isStart, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeDialog$16(boolean z, int i, int i2) {
        if (this.mSelectHour == i && this.mSelectMin == i2) {
            return;
        }
        this.mSelectHour = i;
        this.mSelectMin = i2;
        if (z) {
            ((ActknobPanelSettingVM) this.mViewModel).setStarTime(this.mSelectHour, this.mSelectMin);
        } else {
            ((ActknobPanelSettingVM) this.mViewModel).setEndTime(this.mSelectHour, this.mSelectMin);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Device deviceById = Injection.repo().device().getDeviceById(((ActknobPanelSettingVM) this.mViewModel).controlId);
        refreshRelateInfo(deviceById);
        if (deviceById != null) {
            ((ActknobPanelSettingVM) this.mViewModel).queryScene(deviceById.getDeviceId());
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3005 || data == null) {
            return;
        }
        ((ActknobPanelSettingVM) this.mViewModel).orderArray = data.getIntArrayExtra(Constants.MODE_SET);
    }
}