package com.ltech.smarthome.ui.device.eurpanel;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActEurPanelSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobSortDoubleMode;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectDmxDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActEurPanelSetting extends BaseDeviceSetActivity<ActEurPanelSettingBinding, ActEurPanelSettingVM> {
    private int dmxType;

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
        ((ActEurPanelSettingBinding) this.mViewBinding).sbBuzzer.setOnCheckedChangeListener(new AnonymousClass1());
        ((ActEurPanelSettingBinding) this.mViewBinding).sbBuzzer.setButtonEnable(isOwnerOrManager());
    }

    /* renamed from: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$1, reason: invalid class name */
    class AnonymousClass1 implements SwitchButton.OnCheckedChangeListener {
        AnonymousClass1() {
        }

        @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
        public void onCheckedChanged(SwitchButton view, final boolean isChecked) {
            ((ActEurPanelSettingVM) ActEurPanelSetting.this.mViewModel).setBuzzerState(isChecked, 1, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelSetting.AnonymousClass1.this.lambda$onCheckedChanged$0(isChecked, (Boolean) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCheckedChanged$0(boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                ((ActEurPanelSettingVM) ActEurPanelSetting.this.mViewModel).updateBuzzerState(z);
            } else {
                ((ActEurPanelSettingBinding) ActEurPanelSetting.this.mViewBinding).sbBuzzer.setCheckedNotByUser(!z);
            }
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActEurPanelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEurPanelSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda17
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        initRv();
        ((ActEurPanelSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActEurPanelSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActEurPanelSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActEurPanelSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda18
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActEurPanelSetting.this.lambda$startObserve$1(refreshLayout);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).sensitivity.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda19
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelSetting.this.lambda$startObserve$2((Integer) obj);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).showSensitivityDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda20
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).showModeOrderDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda21
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelSetting.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).adjustKRange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda22
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelSetting.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).buzzerIsOpen.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelSetting.this.lambda$startObserve$6((Boolean) obj);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).showDmxTypeDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelSetting.this.lambda$startObserve$7((Void) obj);
            }
        });
        ((ActEurPanelSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelSetting.this.lambda$startObserve$8((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        String floorName;
        if (device == null) {
            return;
        }
        ((ActEurPanelSettingVM) this.mViewModel).deviceId = device.getDeviceId();
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
        queryState();
        if (isOwnerOrManager()) {
            ((ActEurPanelSettingVM) this.mViewModel).showLog.setValue(true);
        }
        ((ActEurPanelSettingVM) this.mViewModel).queryBackData(device.getDeviceId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(RefreshLayout refreshLayout) {
        if (((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            queryState();
        }
        ((ActEurPanelSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        String[] stringArray = getResources().getStringArray(R.array.sq_sensitivity_array);
        if (num.intValue() <= 0 || num.intValue() > stringArray.length) {
            return;
        }
        ((ActEurPanelSettingBinding) this.mViewBinding).tvSensitivity.setText(stringArray[num.intValue() - 1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showSensitivityDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r4) {
        NavUtils.destination(ActKnobSortDoubleMode.class).withLong(Constants.CONTROL_ID, ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue().getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue())).withIntArray(Constants.MODE_SET, ((ActEurPanelSettingVM) this.mViewModel).orderArray).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r4) {
        ((ActEurPanelSettingVM) this.mViewModel).clickAdjustKRange(this, ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue(), ((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Boolean bool) {
        ((ActEurPanelSettingBinding) this.mViewBinding).sbBuzzer.setCheckedNotByUser(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r1) {
        showDmxTypeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Integer num) {
        ((ActEurPanelSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    private void initRv() {
        initTimeAdapter(((ActEurPanelSettingBinding) this.mViewBinding).rvLightSetting, getString(R.string.gradual_time) + "(DMX)", true);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void refreshTimeView(BaseViewHolder helper) {
        helper.setText(R.id.tv_scene_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.sceneSecPos + ((this.sceneMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
        helper.setText(R.id.tv_elec_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.powerOnSecPos + ((this.powerOnMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
        helper.setText(R.id.tv_on_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.onSecPos + ((this.onMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
        helper.setText(R.id.tv_off_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.offSecPos + ((this.offMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
    }

    private void initRelatedInfoView(final Device device) {
        ((ActEurPanelSettingVM) this.mViewModel).initRelateInfoList(device);
        ((ActEurPanelSettingBinding) this.mViewBinding).sbBuzzer.setChecked(((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.getBuzzerState() == 1);
        ((ActEurPanelSettingBinding) this.mViewBinding).sbKeySave.setChecked(((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.getSceneLongPress() == 1);
        ((ActEurPanelSettingBinding) this.mViewBinding).tvZoneControlState.setText(((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.getZoneNumber() == 1 ? R.string.single_zone_control : R.string.multi_zone_control);
        if (EurHelper.convertType(device) == 2) {
            ((ActEurPanelSettingVM) this.mViewModel).isShowBindKRange.setValue(Boolean.valueOf(((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.isKnobShowKRange()));
        } else {
            ((ActEurPanelSettingVM) this.mViewModel).isShowBindKRange.setValue(false);
        }
        if (device.getProductId().equals(ProductId.ID_EUR_PANEL_EB5) || device.getProductId().equals(ProductId.ID_AS_PANEL_U4S)) {
            ((ActEurPanelSettingBinding) this.mViewBinding).tvFunctionState.setText(R.string.scene_function);
            showControlType();
        } else if (device.getProductId().equals(ProductId.ID_EUR_PANEL_EB6)) {
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutKnobSetting.setVisibility(0);
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutZoneControl.setVisibility(8);
            showEb6Settings();
            ((ActEurPanelSettingBinding) this.mViewBinding).tvEurFunctionTips.setText(R.string.eur6_button_tips);
            showControlType();
        } else if (device.getProductId().equals(ProductId.ID_EUR_PANEL_EB8) || device.getProductId().equals(ProductId.ID_AS_PANEL_UB8)) {
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutBuzzer.setVisibility(device.getProductId().equals(ProductId.ID_EUR_PANEL_EB8) ? 0 : 8);
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutBrtButton.setVisibility(0);
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutSetOnState.setVisibility(8);
            ((ActEurPanelSettingBinding) this.mViewBinding).rvLightSetting.setVisibility(8);
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutZoneControl.setVisibility(8);
            ((ActEurPanelSettingBinding) this.mViewBinding).tvEurFunctionTips.setVisibility(8);
            ((ActEurPanelSettingBinding) this.mViewBinding).tvRelatedTip.setText(R.string.related_automation);
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutControlType.setVisibility(8);
        } else {
            ((ActEurPanelSettingBinding) this.mViewBinding).tvEurFunctionTips.setText(R.string.eur1_2_button_tips);
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutControlType.setVisibility(8);
        }
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutCreateGroup.setVisibility((EurHelper.isEb125(device) || AsHelper.isNewUb(device)) ? 0 : 8);
        if (EurHelper.isEb125(device) || ProductId.ID_EUR_PANEL_EB6.equals(device.getProductId())) {
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutKeySave.setVisibility(0);
            ((ActEurPanelSettingBinding) this.mViewBinding).tvKeySaveTip.setText(AsHelper.isNewUb(device) ? R.string.key_save_tip : R.string.key_save_tip_eb);
            ((ActEurPanelSettingBinding) this.mViewBinding).sbKeySave.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    ActEurPanelSetting.this.lambda$initRelatedInfoView$10(device, switchButton, z);
                }
            });
        }
        if (AsHelper.isNewUb(device)) {
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutBuzzer.setVisibility(8);
            ((ActEurPanelSettingBinding) this.mViewBinding).tvEurFunctionTips.setVisibility(8);
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutZoneControl.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$10(final Device device, SwitchButton switchButton, final boolean z) {
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).setKeySave(this, z, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$initRelatedInfoView$9(device, z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$9(Device device, boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this, ((ActEurPanelSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.KEY_SAVE, CmdAssistant.getSettingCmdAssistant(device, new int[0]).setKeySave(z));
            ((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.setSceneLongPress(z ? 1 : 0);
            ((ActEurPanelSettingVM) this.mViewModel).updateParamExt(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue(), ((ActEurPanelSettingVM) this.mViewModel).relateInfoAssistant.getExtParamString(), null);
        } else {
            ((ActEurPanelSettingBinding) this.mViewBinding).sbKeySave.setCheckedNotByUser(!z);
            showErrorDialog(getString(R.string.save_fail));
        }
    }

    private void showControlType() {
        ((ActEurPanelSettingBinding) this.mViewBinding).tvControlTypeState.setText(((ActEurPanelSettingVM) this.mViewModel).getLightTypeName(ProductRepository.isAsPanel(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId()) ? AsHelper.convertType(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue()) : EurHelper.convertType(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue())));
    }

    private void showEb6Settings() {
        ((ActEurPanelSettingBinding) this.mViewBinding).layoutKnobSetting.setVisibility(0);
        ((ActEurPanelSettingBinding) this.mViewBinding).sbModeMemorize.setButtonEnable(isOwnerOrManager());
        if (isOwnerOrManager()) {
            int lightColorType = ProductRepository.getLightColorType((Object) ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue());
            if (lightColorType != 4 && lightColorType != 5) {
                ((ActEurPanelSettingBinding) this.mViewBinding).layoutModeMemorize.setVisibility(8);
                ((ActEurPanelSettingBinding) this.mViewBinding).layoutModeOrder.setVisibility(8);
            }
            ((ActEurPanelSettingBinding) this.mViewBinding).sbModeMemorize.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    ActEurPanelSetting.this.lambda$showEb6Settings$12(switchButton, z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEb6Settings$12(SwitchButton switchButton, final boolean z) {
        getCmdHelper().setModeMemorize(this, z ? 1 : 0, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$showEb6Settings$11(z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEb6Settings$11(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this, ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.KNOB_DOUBLE_MEMORY, getCmdHelper().setModeMemorize(z ? 1 : 0));
        } else {
            ((ActEurPanelSettingBinding) this.mViewBinding).sbModeMemorize.setCheckedNotByUser(!z);
        }
    }

    private void queryState() {
        getCmdHelper().queryOnState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$queryState$13((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$13(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        List<SelectPowerOnStateDialog.OnOffState> generateContentList = SelectPowerOnStateDialog.generateContentList();
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        int size = generateContentList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            if (parseInt != generateContentList.get(i).getMainValue()) {
                i++;
            } else if (parseInt == 4) {
                ((ActEurPanelSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(Integer.parseInt(responseMsg.getResData().substring(18, 20), 16))) + getString(R.string.brt));
            } else {
                ((ActEurPanelSettingBinding) this.mViewBinding).tvState.setText(generateContentList.get(i).getName());
            }
        }
        queryOnOffTime();
    }

    private void queryOnOffTime() {
        getCmdHelper().queryOnOffTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$queryOnOffTime$14((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryOnOffTime$14(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 24) {
            return;
        }
        LHomeLog.i(getClass(), "queryState=" + responseMsg.getResData());
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
        this.onSecPos = parseInt / 1000;
        this.onMsPos = (parseInt % 1000) / 100;
        int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16) * 100;
        this.offSecPos = parseInt2 / 1000;
        this.offMsPos = (parseInt2 % 1000) / 100;
        queryLightPowerOnTime();
    }

    private void queryLightPowerOnTime() {
        getCmdHelper().queryPowerOnTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$queryLightPowerOnTime$15((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLightPowerOnTime$15(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getResData().length() >= 20) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
            this.powerOnSecPos = parseInt / 1000;
            this.powerOnMsPos = (parseInt % 1000) / 100;
            this.mAdapter.notifyDataSetChanged();
            queryLightSceneTime();
            return;
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void queryLightSceneTime() {
        getCmdHelper().querySceneOnTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$queryLightSceneTime$16((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLightSceneTime$16(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getResData().length() >= 20) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
            this.sceneSecPos = parseInt / 1000;
            this.sceneMsPos = (parseInt % 1000) / 100;
            this.mAdapter.notifyDataSetChanged();
            queryKnobStatus();
            return;
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void queryKnobStatus() {
        if (ProductId.ID_EUR_PANEL_EB6.equals(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId())) {
            CmdAssistant.getQueryCmdAssistant(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryKnobPanelSetting(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelSetting.this.lambda$queryKnobStatus$17((ResponseMsg) obj);
                }
            });
        }
        if (AsHelper.isNewUb(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue())) {
            queryKeySave();
        } else {
            queryBuzzerState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryKnobStatus$17(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData().length() < 24) {
            return;
        }
        ((ActEurPanelSettingVM) this.mViewModel).sensitivity.setValue(Integer.valueOf(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16)));
        for (int i = 0; i < ((ActEurPanelSettingVM) this.mViewModel).orderArray.length; i++) {
            int i2 = i * 2;
            ((ActEurPanelSettingVM) this.mViewModel).orderArray[i] = Integer.parseInt(responseMsg.getResData().substring(18 + i2, i2 + 20), 16);
        }
        ((ActEurPanelSettingVM) this.mViewModel).memorizeMode.setValue(Boolean.valueOf(Integer.parseInt(responseMsg.getResData().substring(24, 26), 16) == 1));
        ((ActEurPanelSettingBinding) this.mViewBinding).sbModeMemorize.setCheckedNotByUser(((ActEurPanelSettingVM) this.mViewModel).memorizeMode.getValue().booleanValue());
    }

    private void queryKeySave() {
        CmdAssistant.getQueryCmdAssistant(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryKeySave(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$queryKeySave$18((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryKeySave$18(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 18) {
                ((ActEurPanelSettingVM) this.mViewModel).keySave.setValue(Boolean.valueOf(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 1));
            }
            queryDmxType();
        }
    }

    private void queryBuzzerState() {
        getCmdHelper().queryBuzzerState(this, 1, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$queryBuzzerState$19((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryBuzzerState$19(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 18) {
                ((ActEurPanelSettingVM) this.mViewModel).buzzerIsOpen.setValue(Boolean.valueOf(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 1));
            }
            queryDmxType();
        }
    }

    private void queryDmxType() {
        boolean z = ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_EUR_PANEL_EB2) || ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_AS_PANEL_U2S);
        if (ProductId.ID_EUR_PANEL_EB6.equals(((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId())) {
            z = ProductRepository.getLightColorType((Object) ((ActEurPanelSettingVM) this.mViewModel).controlDevice.getValue()) == 2;
        }
        if (z) {
            getCmdHelper().queryCtLightMode(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelSetting.this.lambda$queryDmxType$20((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDmxType$20(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0 || responseMsg.getResData() == null || responseMsg.getResData().length() < 18 || !responseMsg.getResData().substring(12, 14).equalsIgnoreCase("0C")) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        if (parseInt == 1 || parseInt == 2) {
            this.dmxType = parseInt;
            ((ActEurPanelSettingBinding) this.mViewBinding).layoutSetDmxType.setVisibility(0);
            ((ActEurPanelSettingBinding) this.mViewBinding).tvDmxType.setText(this.dmxType == 1 ? R.string.ct_dmx_address_C_name : R.string.ct_dmx_address_BRT_name);
        }
    }

    private void showDmxTypeDialog() {
        SelectDmxDialog.asDefault().setTitle(getString(R.string.ct_dmx_address_type)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setSelectPosition(this.dmxType - 1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$showDmxTypeDialog$22((Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDmxTypeDialog$22(final Integer num) {
        getCmdHelper().setCtLightMode(this, num.intValue() + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelSetting.this.lambda$showDmxTypeDialog$21(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDmxTypeDialog$21(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            this.dmxType = num.intValue() + 1;
            ((ActEurPanelSettingBinding) this.mViewBinding).tvDmxType.setText(this.dmxType == 1 ? R.string.ct_dmx_address_C_name : R.string.ct_dmx_address_BRT_name);
            ReplaceHelper.instance().backupData(this, ((ActEurPanelSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.CT_DIM_TYPE, getCmdHelper().setCtLightMode(num.intValue() + 1));
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void updatePowerStateDialogData(SelectPowerOnStateDialog.OnOffState state) {
        if (state.getMainValue() == 4) {
            ((ActEurPanelSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(state.getSubValue()) + getString(R.string.brt));
            return;
        }
        ((ActEurPanelSettingBinding) this.mViewBinding).tvState.setText(state.getName());
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

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 3005 || data == null) {
            return;
        }
        ((ActEurPanelSettingVM) this.mViewModel).orderArray = data.getIntArrayExtra(Constants.MODE_SET);
    }
}