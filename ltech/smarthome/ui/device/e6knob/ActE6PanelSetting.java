package com.ltech.smarthome.ui.device.e6knob;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActE6PanelSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.E6ExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobSortDoubleMode;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditNumberDialog;
import com.ltech.smarthome.view.dialog.SelectKValueDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActE6PanelSetting extends BaseDeviceSetActivity<ActE6PanelSettingBinding, ActE6PanelSettingVM> {
    private E6ExtParam extParam = new E6ExtParam();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_e6_panel_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
        ((ActE6PanelSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActE6PanelSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActE6PanelSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActE6PanelSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda23
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActE6PanelSetting.this.lambda$initView$0(refreshLayout);
            }
        });
        initRv();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            querySettings();
        }
        ((ActE6PanelSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActE6PanelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActE6PanelSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda25
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$11((Device) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).backDataResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda29
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$12((JSONObject) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).sensitivity.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda30
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$13((Integer) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).showPowerStateEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda31
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$14((Void) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).showSensitivityDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$15((Void) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).showModeOrderDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$16((Void) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).showKValueEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$17((Void) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).showRgbInterfaceEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$18((Void) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).rgbInterface.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$19((Integer) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).showDimSignalEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$20((Void) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).dimSignal.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda26
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$21((Integer) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).onState.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda27
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$22((PowerState) obj);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).timeData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda28
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$23((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(final Device device) {
        String floorName;
        if (device == null) {
            return;
        }
        ((ActE6PanelSettingVM) this.mViewModel).deviceId = device.getDeviceId();
        this.extParam.fillMapWithString(device.getExtParam());
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActE6PanelSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActE6PanelSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        ((ActE6PanelSettingBinding) this.mViewBinding).layoutDeviceReplace.setVisibility((!isOwnerOrManager() || isVirtual()) ? 8 : 0);
        ((ActE6PanelSettingBinding) this.mViewBinding).layoutKnobSetting.setVisibility(0);
        ((ActE6PanelSettingBinding) this.mViewBinding).sbModeMemorize.setButtonEnable(isOwnerOrManager());
        int lightColorType = ProductRepository.getLightColorType((Object) device);
        if (lightColorType < 2) {
            ((ActE6PanelSettingBinding) this.mViewBinding).layoutModeMemorize.setVisibility(8);
        }
        if (lightColorType < 4) {
            ((ActE6PanelSettingBinding) this.mViewBinding).layoutModeOrder.setVisibility(8);
        }
        ((ActE6PanelSettingBinding) this.mViewBinding).sbModeMemorize.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActE6PanelSetting.this.lambda$startObserve$2(device, switchButton, z);
            }
        });
        ((ActE6PanelSettingBinding) this.mViewBinding).sbIndicator.setButtonEnable(isOwnerOrManager());
        ((ActE6PanelSettingBinding) this.mViewBinding).sbIndicator.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActE6PanelSetting.this.lambda$startObserve$4(switchButton, z);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).indicator.setValue(Boolean.valueOf(this.extParam.getIndicatorStatus() == 1));
        ((ActE6PanelSettingBinding) this.mViewBinding).sbBuzzer.setButtonEnable(isOwnerOrManager());
        ((ActE6PanelSettingBinding) this.mViewBinding).sbBuzzer.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActE6PanelSetting.this.lambda$startObserve$6(switchButton, z);
            }
        });
        ((ActE6PanelSettingVM) this.mViewModel).buzzer.setValue(Boolean.valueOf(this.extParam.getBuzzerStatus() == 1));
        if (ProductId.ID_KNOB_PANEL_E6D.equals(device.getProductId())) {
            if (lightColorType == 2 && device.getMaxkelvin() > 0) {
                ((ActE6PanelSettingBinding) this.mViewBinding).layoutKRange.setVisibility(0);
                ((ActE6PanelSettingBinding) this.mViewBinding).tvKRange.setText(String.format(Locale.getDefault(), "%dK-%dK", Integer.valueOf(device.getMinkelvin()), Integer.valueOf(device.getMaxkelvin())));
            } else if (lightColorType == 3) {
                ((ActE6PanelSettingBinding) this.mViewBinding).layoutRgbInterface.setVisibility(0);
            }
            ((ActE6PanelSettingBinding) this.mViewBinding).layoutBusPower.setVisibility(0);
            ((ActE6PanelSettingBinding) this.mViewBinding).sbBusPower.setButtonEnable(isOwnerOrManager());
            ((ActE6PanelSettingBinding) this.mViewBinding).sbBusPower.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda20
                @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
                public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                    ActE6PanelSetting.this.lambda$startObserve$8(device, switchButton, z);
                }
            });
        } else if (ProductId.ID_KNOB_PANEL_E6T.equals(device.getProductId())) {
            ((ActE6PanelSettingBinding) this.mViewBinding).layoutDimSignal.setVisibility(0);
            ((ActE6PanelSettingBinding) this.mViewBinding).layoutControlType.setVisibility(8);
        } else if (ProductId.ID_KNOB_PANEL_E6M.equals(device.getProductId())) {
            ((ActE6PanelSettingBinding) this.mViewBinding).layoutE6mAdd.setVisibility(0);
            if (this.extParam.getAction(0) != null) {
                ((ActE6PanelSettingBinding) this.mViewBinding).tvFirstAdd.setText(getObjectName());
            }
            ((ActE6PanelSettingBinding) this.mViewBinding).layoutE6mAdd.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda21
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActE6PanelSetting.this.lambda$startObserve$10(device, view);
                }
            });
        }
        ((ActE6PanelSettingBinding) this.mViewBinding).tvControlTypeState.setText(E6Helper.instance().getTypeName(this, lightColorType));
        if (((ActE6PanelSettingVM) this.mViewModel).backDataResult.getValue() == null) {
            ((ActE6PanelSettingVM) this.mViewModel).queryBackData(device.getDeviceId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(final Device device, SwitchButton switchButton, final boolean z) {
        CmdAssistant.getLightCmdAssistant(device, new int[0]).setModeMemorize(this, z ? 1 : 0, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$1(device, z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device, boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this, ((ActE6PanelSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.KNOB_DOUBLE_MEMORY, CmdAssistant.getLightCmdAssistant(device, new int[0]).setModeMemorize(z ? 1 : 0));
        } else {
            ((ActE6PanelSettingBinding) this.mViewBinding).sbModeMemorize.setCheckedNotByUser(!z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(SwitchButton switchButton, final boolean z) {
        ((ActE6PanelSettingVM) this.mViewModel).setBuzzerState(z, 2, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$3(z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            this.extParam.setIndicatorStatus(z ? 1 : 0);
            ((ActE6PanelSettingVM) this.mViewModel).updateParamExt(((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue(), this.extParam.getParamString());
        } else {
            ((ActE6PanelSettingBinding) this.mViewBinding).sbIndicator.setCheckedNotByUser(!z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(SwitchButton switchButton, final boolean z) {
        ((ActE6PanelSettingVM) this.mViewModel).setBuzzerState(z, 1, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$5(z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            this.extParam.setBuzzerStatus(z ? 1 : 0);
            ((ActE6PanelSettingVM) this.mViewModel).updateParamExt(((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue(), this.extParam.getParamString());
        } else {
            ((ActE6PanelSettingBinding) this.mViewBinding).sbBuzzer.setCheckedNotByUser(!z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(final Device device, SwitchButton switchButton, final boolean z) {
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).setDaliBusPower(this, z ? 1 : 0, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda22
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$startObserve$7(device, z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Device device, boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this, ((ActE6PanelSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.DALI_ON_OFF, CmdAssistant.getSettingCmdAssistant(device, new int[0]).setDaliBusPower(z ? 1 : 0));
        } else {
            ((ActE6PanelSettingBinding) this.mViewBinding).sbBusPower.setCheckedNotByUser(!z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(final Device device, View view) {
        if (isOwnerOrManager()) {
            EditNumberDialog.asDefault().setTitle(getString(R.string.e6m_add_tip_1)).setRange(1, 512).setErrorTip(getString(R.string.app_str_out_of_range)).setContent(String.valueOf(Math.max(1, Math.min(E6Helper.instance().getObjectArray(this.extParam, 0)[1], 512)))).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActE6PanelSetting.this.lambda$startObserve$9(device, (Integer) obj);
                }
            }).showDialog(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Device device, Integer num) {
        setObject(device, "00" + StringUtils.demToHex(num.intValue(), 4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(JSONObject jSONObject) {
        if (((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue().isVirtual()) {
            showBackSettingData(jSONObject);
        } else {
            querySettings();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Integer num) {
        String[] stringArray = getResources().getStringArray(R.array.sq_sensitivity_array);
        if (num.intValue() <= 0 || num.intValue() > stringArray.length) {
            return;
        }
        ((ActE6PanelSettingBinding) this.mViewBinding).tvSensitivity.setText(stringArray[num.intValue() - 1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$14(Void r2) {
        if (((ActE6PanelSettingVM) this.mViewModel).onState.getValue() != null) {
            showPowerStateDialog(((ActE6PanelSettingVM) this.mViewModel).onState.getValue().brt, ((ActE6PanelSettingVM) this.mViewModel).onState.getValue().wyBrt);
        } else {
            showPowerStateDialog(((ActE6PanelSettingVM) this.mViewModel).onState.getValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$15(Void r1) {
        showSensitivityDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$16(Void r4) {
        NavUtils.destination(ActKnobSortDoubleMode.class).withLong(Constants.CONTROL_ID, ((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue().getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) ((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue())).withIntArray(Constants.MODE_SET, ((ActE6PanelSettingVM) this.mViewModel).orderArray).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$17(Void r1) {
        showKValueDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$18(Void r1) {
        showRgbInterfaceDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$19(Integer num) {
        ((ActE6PanelSettingBinding) this.mViewBinding).tvRgbInterface.setText(num.intValue() == 0 ? "RGB" : "RGB(XY)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$20(Void r1) {
        showDimSignalDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$21(Integer num) {
        if (num.intValue() > 0) {
            ((ActE6PanelSettingBinding) this.mViewBinding).tvDimSignal.setText(getString(num.intValue() == 2 ? R.string.front_edge : R.string.back_edge));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$22(PowerState powerState) {
        List<SelectPowerOnStateDialog.OnOffState> generateContentList;
        if (powerState != null) {
            if (((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue() != null && ((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_SWITCH) && ((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_KBS)) {
                generateContentList = SelectPowerOnStateDialog.generateSwitchList();
            } else {
                generateContentList = SelectPowerOnStateDialog.generateContentList();
            }
            int i = powerState.state;
            int size = generateContentList.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (i == generateContentList.get(i2).getMainValue()) {
                    if (i == 4) {
                        ((ActE6PanelSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(powerState.brt)) + getString(R.string.brt));
                        return;
                    }
                    ((ActE6PanelSettingBinding) this.mViewBinding).tvState.setText(generateContentList.get(i2).getName());
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$23(String str) {
        int parseInt = Integer.parseInt(str.substring(0, 4), 16) * 100;
        this.powerOnSecPos = parseInt / 1000;
        this.powerOnMsPos = (parseInt % 1000) / 100;
        int parseInt2 = Integer.parseInt(str.substring(4, 8), 16) * 100;
        this.onSecPos = parseInt2 / 1000;
        this.onMsPos = (parseInt2 % 1000) / 100;
        int parseInt3 = Integer.parseInt(str.substring(8, 12), 16) * 100;
        this.offSecPos = parseInt3 / 1000;
        this.offMsPos = (parseInt3 % 1000) / 100;
        int parseInt4 = Integer.parseInt(str.substring(12, 16), 16) * 100;
        this.sceneSecPos = parseInt4 / 1000;
        this.sceneMsPos = (parseInt4 % 1000) / 100;
        this.mAdapter.notifyDataSetChanged();
    }

    private void initRv() {
        initTimeAdapter(((ActE6PanelSettingBinding) this.mViewBinding).rvLightSetting, getString(R.string.gradual_time), true);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void refreshTimeView(BaseViewHolder helper) {
        helper.setText(R.id.tv_scene_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.sceneSecPos + ((this.sceneMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
        helper.setText(R.id.tv_elec_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.powerOnSecPos + ((this.powerOnMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
        helper.setText(R.id.tv_on_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.onSecPos + ((this.onMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
        helper.setText(R.id.tv_off_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.offSecPos + ((this.offMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Device deviceById = Injection.repo().device().getDeviceById(((ActE6PanelSettingVM) this.mViewModel).controlId);
        ((ActE6PanelSettingVM) this.mViewModel).controlDevice.setValue(deviceById);
        if (deviceById != null) {
            ((ActE6PanelSettingVM) this.mViewModel).queryScene(deviceById.getDeviceId());
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void updatePowerStateDialogData(SelectPowerOnStateDialog.OnOffState state) {
        if (state.getMainValue() == 4) {
            ((ActE6PanelSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(state.getSubValue()) + getString(R.string.brt));
            return;
        }
        ((ActE6PanelSettingBinding) this.mViewBinding).tvState.setText(state.getName());
    }

    private void querySettings() {
        CmdAssistant.getQueryCmdAssistant(((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryE6Setting(this, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda24
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$querySettings$24((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$querySettings$24(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            ((ActE6PanelSettingVM) this.mViewModel).parseResponseMsg(responseMsg.getResData());
        }
    }

    private void showKValueDialog() {
        Device value = ((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue();
        if (value != null) {
            int maxkelvin = value.getMaxkelvin();
            int minkelvin = value.getMinkelvin();
            SelectKValueDialog.asDefault().controlObject(value).setMax(maxkelvin).setMin(minkelvin).setOnSaveListener(new AnonymousClass1(value, minkelvin, maxkelvin)).showDialog(this);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$1, reason: invalid class name */
    class AnonymousClass1 implements SelectKValueDialog.OnSaveListener {
        final /* synthetic */ Device val$device;
        final /* synthetic */ int val$kMax;
        final /* synthetic */ int val$kMin;

        @Override // com.ltech.smarthome.view.dialog.SelectKValueDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass1(final Device val$device, final int val$kMin, final int val$kMax) {
            this.val$device = val$device;
            this.val$kMin = val$kMin;
            this.val$kMax = val$kMax;
        }

        @Override // com.ltech.smarthome.view.dialog.SelectKValueDialog.OnSaveListener
        public boolean onSave(final SelectKValueDialog.OnOffState onOffState, int selectPos) {
            LightAssistant cmdHelper = ActE6PanelSetting.this.getCmdHelper();
            BaseNormalActivity baseNormalActivity = ActE6PanelSetting.this.activity;
            int min = onOffState.getMin();
            int max = onOffState.getMax();
            final Device device = this.val$device;
            final int i = this.val$kMin;
            final int i2 = this.val$kMax;
            cmdHelper.setKRange(baseNormalActivity, min, max, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActE6PanelSetting.AnonymousClass1.this.lambda$onSave$1(device, onOffState, i, i2, (Boolean) obj);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$1(final Device device, final SelectKValueDialog.OnOffState onOffState, int i, int i2, Boolean bool) {
            if (bool.booleanValue()) {
                ((ObservableSubscribeProxy) Injection.net().updateCtRange(device.getDeviceId(), onOffState.getMax(), onOffState.getMin()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ActE6PanelSetting.this.getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$1$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActE6PanelSetting.AnonymousClass1.this.lambda$onSave$0(device, onOffState, obj);
                    }
                }, new C01191(i, i2));
            } else {
                ActE6PanelSetting actE6PanelSetting = ActE6PanelSetting.this;
                actE6PanelSetting.showErrorDialog(actE6PanelSetting.getString(R.string.save_fail));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(Device device, SelectKValueDialog.OnOffState onOffState, Object obj) throws Exception {
            ActE6PanelSetting.this.dismissLoadingDialog();
            device.setMinkelvin(onOffState.getMin());
            device.setMaxkelvin(onOffState.getMax());
            Injection.repo().device().saveDevice(device);
            ((ActE6PanelSettingBinding) ActE6PanelSetting.this.mViewBinding).tvKRange.setText(String.format(Locale.getDefault(), "%dK-%dK", Integer.valueOf(device.getMinkelvin()), Integer.valueOf(device.getMaxkelvin())));
            MessageDialog.show(ActE6PanelSetting.this.activity, ActE6PanelSetting.this.getString(R.string.app_str_setting_success), ActE6PanelSetting.this.getString(R.string.k_range_save_tip));
            ReplaceHelper.instance().backupData(ActE6PanelSetting.this.activity, ((ActE6PanelSettingVM) ActE6PanelSetting.this.mViewModel).deviceId, UpdateBackDataRequest.CT_RANGE, ActE6PanelSetting.this.getCmdHelper().setKRange(onOffState.getMin(), onOffState.getMax()));
        }

        /* renamed from: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$1$1, reason: invalid class name and collision with other inner class name */
        class C01191 implements Consumer<Throwable> {
            final /* synthetic */ int val$kMax;
            final /* synthetic */ int val$kMin;

            C01191(final int val$kMin, final int val$kMax) {
                this.val$kMin = val$kMin;
                this.val$kMax = val$kMax;
            }

            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActE6PanelSetting.this.getCmdHelper().setKRange(ActE6PanelSetting.this.activity, this.val$kMin, this.val$kMax, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$1$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActE6PanelSetting.AnonymousClass1.C01191.this.lambda$accept$0((Boolean) obj);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$accept$0(Boolean bool) {
                if (!Injection.state().isConnectOuterNet()) {
                    SmartToast.showShort(R.string.no_network);
                }
                ActE6PanelSetting.this.showErrorDialog(ActE6PanelSetting.this.getString(R.string.save_fail));
            }
        }
    }

    private void showDimSignalDialog() {
        SelectListDialog.asDefault(true).setTitle(getString(R.string.output_signal)).setSelectList(Arrays.asList(getString(R.string.front_edge), getString(R.string.back_edge))).setSelectPosition(((ActE6PanelSettingVM) this.mViewModel).dimSignal.getValue().intValue() == 2 ? 0 : 1).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$showDimSignalDialog$27((Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimSignalDialog$27(Integer num) {
        final int i = num.intValue() == 0 ? 2 : 1;
        if (((ActE6PanelSettingVM) this.mViewModel).dimSignal.getValue().intValue() != i) {
            MessageDialog.show(this, "", getString(R.string.add_e6t_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda14
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$showDimSignalDialog$26;
                    lambda$showDimSignalDialog$26 = ActE6PanelSetting.this.lambda$showDimSignalDialog$26(i, baseDialog, view);
                    return lambda$showDimSignalDialog$26;
                }
            }).setCancelButton(getString(R.string.cancel));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDimSignalDialog$26(final int i, BaseDialog baseDialog, View view) {
        CmdAssistant.getSettingCmdAssistant(((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setDimSignal(this, i, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$showDimSignalDialog$25(i, (Boolean) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimSignalDialog$25(int i, Boolean bool) {
        if (bool.booleanValue()) {
            ((ActE6PanelSettingVM) this.mViewModel).dimSignal.setValue(Integer.valueOf(i));
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.LIGHT_TYPE, CmdAssistant.getSettingCmdAssistant(null, new int[0]).setDimSignal(i));
        }
    }

    protected void showRgbInterfaceDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("RGB");
        arrayList.add("RGB(XY)");
        SelectListDialog.asDefault(true).setTitle(getString(R.string.dim_interface)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(((ActE6PanelSettingVM) this.mViewModel).rgbInterface.getValue().intValue()).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$showRgbInterfaceDialog$29((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRgbInterfaceDialog$29(final Integer num) {
        CmdAssistant.getSettingCmdAssistant(((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRgbInterface(this, num.intValue(), new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$showRgbInterfaceDialog$28(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRgbInterfaceDialog$28(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            ((ActE6PanelSettingVM) this.mViewModel).rgbInterface.setValue(num);
            ReplaceHelper.instance().backupData(this, ((ActE6PanelSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.RGB_TYPE, CmdAssistant.getSettingCmdAssistant(((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRgbInterface(num.intValue()));
        }
    }

    private void setObject(final Device device, final String objectInstruct) {
        CmdAssistant.getDeviceAssistant(device, new int[0]).setE6Object(this, objectInstruct, new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActE6PanelSetting.this.lambda$setObject$31(device, objectInstruct, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setObject$31(Device device, String str, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupIndexData(this, device.getDeviceId(), UpdateBackDataRequest.CONTROL_OBJECT, CmdAssistant.getDeviceAssistant(device, new int[0]).setE6Object(str), 0);
            E6ExtParam.E6Action action = this.extParam.getAction(0);
            action.setObjectInstruct(str);
            this.extParam.setAction(0, action);
            ((ActE6PanelSettingVM) this.mViewModel).updateParamExt(device, this.extParam.getParamString(), new IAction() { // from class: com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActE6PanelSetting.this.lambda$setObject$30((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setObject$30(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActE6PanelSettingBinding) this.mViewBinding).tvFirstAdd.setText(getObjectName());
        }
    }

    private String getObjectName() {
        return this.extParam.getAction(0).getObjectName(this, ProductId.ID_KNOB_PANEL_E6M);
    }

    private void showBackSettingData(JSONObject jsonObject) {
        showGeneralData(jsonObject, UpdateBackDataRequest.KNOB_SENSITIVITY);
        showGeneralData(jsonObject, UpdateBackDataRequest.KNOB_SORT);
        showGeneralData(jsonObject, UpdateBackDataRequest.KNOB_DOUBLE_MEMORY);
        showData(jsonObject, UpdateBackDataRequest.INDICATOR_STATUS);
        showData(jsonObject, UpdateBackDataRequest.BUZZER_STATUS);
        showData(jsonObject, UpdateBackDataRequest.DALI_ON_OFF);
        showData(jsonObject, UpdateBackDataRequest.POWER_STATUS);
        showGeneralData(jsonObject, UpdateBackDataRequest.POWER_FADE_TIME);
        showGeneralData(jsonObject, UpdateBackDataRequest.FADE_TIME);
        showGeneralData(jsonObject, UpdateBackDataRequest.SCENE_FADE_TIME);
        showData(jsonObject, UpdateBackDataRequest.RGB_TYPE);
        showData(jsonObject, UpdateBackDataRequest.LIGHT_TYPE);
    }

    private void showData(JSONObject jsonObject, String key) {
        int[] parseBackupData;
        parseBackupData = ReplaceHelper.instance().parseBackupData(jsonObject, key, ((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue());
        key.hashCode();
        switch (key) {
            case "daliRgbType":
                ((ActE6PanelSettingVM) this.mViewModel).rgbInterface.setValue(Integer.valueOf(parseBackupData[0]));
                break;
            case "indicatorStatus":
                ((ActE6PanelSettingVM) this.mViewModel).indicator.setValue(Boolean.valueOf(parseBackupData[0] == 1));
                break;
            case "powerStatus":
                ((ActE6PanelSettingVM) this.mViewModel).onState.setValue(new PowerState(parseBackupData));
                break;
            case "lightType":
                if (ProductId.ID_KNOB_PANEL_E6T.equals(((ActE6PanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId())) {
                    ((ActE6PanelSettingVM) this.mViewModel).dimSignal.setValue(Integer.valueOf(parseBackupData[0]));
                    break;
                }
                break;
            case "buzzerStatus":
                ((ActE6PanelSettingVM) this.mViewModel).buzzer.setValue(Boolean.valueOf(parseBackupData[0] == 1));
                break;
            case "daliOnOff":
                ((ActE6PanelSettingVM) this.mViewModel).busPower.setValue(Boolean.valueOf(parseBackupData[0] == 1));
                break;
        }
    }
}