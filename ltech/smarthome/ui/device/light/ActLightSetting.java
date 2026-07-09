package com.ltech.smarthome.ui.device.light;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.nfc.source.SourceHelper;
import com.ltech.nfc.source.SourceModel;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActLightSettingNewBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.LightExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.nfc.ActCurrentSet;
import com.ltech.smarthome.nfc.ActCurrentSetFive;
import com.ltech.smarthome.nfc.WriteVirtualHelper;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.light.ActLightSetting;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.TextClickUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectDimDepthDialog;
import com.ltech.smarthome.view.dialog.SelectDmxDialog;
import com.ltech.smarthome.view.dialog.SelectKValueDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SelectListWheelDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.ltech.smarthome.view.dialog.SinglePickerDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.ltech.smarthome.view.dialog.WhiteBalanceDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActLightSetting extends BaseDeviceSetActivity<ActLightSettingNewBinding, ActLightSettingVM> {
    public List<BaseDeviceSetActivity.LightSettingState> dataList;
    private int depthMode;
    private int dimDepth;
    private int dmxType;
    private MultipleItemRvAdapter<BaseDeviceSetActivity.LightSettingState, BaseViewHolder> mAdapter;
    private int mSelectHour;
    private int mSelectMin;
    private int outputType;
    private boolean queryTimeSuccess;
    protected boolean[] selectArray;
    private boolean handleActivityResult = false;
    private int onSecPos = 0;
    private int onMsPos = 0;
    private int offSecPos = 0;
    private int offMsPos = 0;
    private int powerOnSecPos = 0;
    private int powerOnMsPos = 0;
    private int sceneSecPos = 0;
    private int sceneMsPos = 0;
    private boolean elecTimeSupport = true;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_light_setting_new;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
        ((ActLightSettingNewBinding) this.mViewBinding).sbRhythmsText.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (Injection.repo().device().getExistGateway() == null) {
                    ((ActLightSettingNewBinding) ActLightSetting.this.mViewBinding).sbRhythmsText.setCheckedNotByUser(false);
                    ActLightSetting actLightSetting = ActLightSetting.this;
                    actLightSetting.showErrorDialog(actLightSetting.getString(R.string.no_super_panel));
                } else {
                    ((ActLightSettingVM) ActLightSetting.this.mViewModel).showRhythmsSetting.setValue(Boolean.valueOf(isChecked));
                    ((ActLightSettingVM) ActLightSetting.this.mViewModel).onOffRhythms(ActLightSetting.this.activity, isChecked, false);
                }
            }
        });
        ((ActLightSettingNewBinding) this.mViewBinding).sbConstantPower.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.2
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActLightSettingVM) ActLightSetting.this.mViewModel).setConstantPower(isChecked);
            }
        });
        ((ActLightSettingVM) this.mViewModel).getSunTime();
        if (isOwnerOrManager()) {
            ((ActLightSettingVM) this.mViewModel).showLog.setValue(true);
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActLightSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActLightSettingVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActLightSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda20
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$3((Device) obj);
            }
        });
        initRv();
        ((ActLightSettingNewBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActLightSettingNewBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActLightSettingNewBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActLightSettingNewBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda23
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActLightSetting.this.lambda$startObserve$4(refreshLayout);
            }
        });
        ((ActLightSettingVM) this.mViewModel).showDmxTypeDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda24
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((ActLightSettingVM) this.mViewModel).showDimDepthDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda25
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$6((Void) obj);
            }
        });
        ((ActLightSettingVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda26
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$7((Void) obj);
            }
        });
        ((ActLightSettingVM) this.mViewModel).repeatTimeLiveData.observe(this, new Observer<String>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(String s) {
                if (TextUtils.isEmpty(s)) {
                    ((ActLightSettingNewBinding) ActLightSetting.this.mViewBinding).repeatWeekTv.setText(ActLightSetting.this.getString(R.string.only_once));
                } else {
                    ((ActLightSettingNewBinding) ActLightSetting.this.mViewBinding).repeatWeekTv.setText(HelpUtils.getWeeksStringNew(ActLightSetting.this.activity, s));
                }
            }
        });
        ((ActLightSettingVM) this.mViewModel).selectEndTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightSetting.this.showTimeDialog(false);
            }
        });
        ((ActLightSettingVM) this.mViewModel).selectStarTimeDialogEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightSetting.this.showTimeDialog(true);
            }
        });
        ((ActLightSettingVM) this.mViewModel).rhythmsIsPlay.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.6
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ((ActLightSettingNewBinding) ActLightSetting.this.mViewBinding).ivPlayAnim.start();
                } else {
                    ((ActLightSettingNewBinding) ActLightSetting.this.mViewBinding).ivPlayAnim.stop();
                }
            }
        });
        ((ActLightSettingVM) this.mViewModel).showPlanListEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightSetting.this.showPlanListDialog();
            }
        });
        ((ActLightSettingVM) this.mViewModel).showKValueEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActLightSetting.this.showKValueDialog();
            }
        });
        ((ActLightSettingVM) this.mViewModel).productName.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda27
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$8((String) obj);
            }
        });
        ((ActLightSettingVM) this.mViewModel).showLineOrderEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda28
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$9((Void) obj);
            }
        });
        ((ActLightSettingVM) this.mViewModel).refreshLineOrder.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda29
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$10((Void) obj);
            }
        });
        ((ActLightSettingVM) this.mViewModel).showWhiteBalanceEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda30
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$13((Void) obj);
            }
        });
        ((ActLightSettingVM) this.mViewModel).showControlTypeEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(ActLightSetting.this.getString(R.string.app_dry_to_ble_type1));
                arrayList.add(ActLightSetting.this.getString(R.string.app_dry_to_ble_type2));
                SelectListDialog.asDefault(true).setSelectList(arrayList).setSelectPosition(((ActLightSettingVM) ActLightSetting.this.mViewModel).controlType.getValue().intValue() != 0 ? 0 : 1).setTitle(ActLightSetting.this.getString(R.string.app_str_switch_control_type)).setCancelString(ActLightSetting.this.getString(R.string.cancel)).setConfirmString(ActLightSetting.this.getString(R.string.confirm)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.9.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Integer integer) {
                        ((ActLightSettingVM) ActLightSetting.this.mViewModel).changeControlType(integer);
                    }
                }).showDialog(ActLightSetting.this.activity);
            }
        });
        ((ActLightSettingVM) this.mViewModel).backDataResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda31
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.showBackSettingData((JSONObject) obj);
            }
        });
        ((ActLightSettingVM) this.mViewModel).queryControlType();
        ((ActLightSettingVM) this.mViewModel).selectSceneDialogEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if ((!aBoolean.booleanValue() || TextUtils.isEmpty(((ActLightSettingVM) ActLightSetting.this.mViewModel).showPowerOnScene.getValue())) && (aBoolean.booleanValue() || TextUtils.isEmpty(((ActLightSettingVM) ActLightSetting.this.mViewModel).showPowerOffScene.getValue()))) {
                    NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, ((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurPlace().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActLightSettingVM) ActLightSetting.this.mViewModel).controlId).withBoolean(Constants.IS_POWER_SCENE, true).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_EXC, aBoolean.booleanValue()).withInt(Constants.ZONE_NUM, 1).withDefaultRequestCode().navigation(ActLightSetting.this.activity);
                } else {
                    ActLightSetting.this.showUnbindDialog(aBoolean.booleanValue());
                }
            }
        });
        ((ActLightSettingVM) this.mViewModel).selectSceneDelayDialogEvent.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.11
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                ActLightSetting.this.showSceneDelayTime(aBoolean);
            }
        });
        ((ActLightSettingVM) this.mViewModel).controlType.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.12
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                ProductInfo bleProductInfoByType;
                if (integer.intValue() != 1 || ((ActLightSettingVM) ActLightSetting.this.mViewModel).controlDevice.getValue() == null || (bleProductInfoByType = ProductRepository.getBleProductInfoByType(((ActLightSettingVM) ActLightSetting.this.mViewModel).controlDevice.getValue())) == null || !bleProductInfoByType.getSubProductKey().equals(ProductId.BLE_SWITCH_SUB_TYPE_KBS1)) {
                    return;
                }
                ((ActLightSettingVM) ActLightSetting.this.mViewModel).showPowerOnOffScene.setValue(true);
                ((ActLightSettingVM) ActLightSetting.this.mViewModel).queryPowerOnOffScene();
            }
        });
        ((ActLightSettingVM) this.mViewModel).onState.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda21
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActLightSetting.this.lambda$startObserve$14((PowerState) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        if (device == null) {
            return;
        }
        if (device.getExtParam(LightExtParam.class) != null) {
            ((ActLightSettingVM) this.mViewModel).extParam = (LightExtParam) device.getExtParam(LightExtParam.class);
            SourceHelper.sourceModel = SourceModel.getSourceModel(((ActLightSettingVM) this.mViewModel).extParam.getBinNum());
        }
        ((ActLightSettingVM) this.mViewModel).imageIndex = device.getImageIndex();
        ((ActLightSettingVM) this.mViewModel).deviceId = device.getDeviceId();
        ((ActLightSettingNewBinding) this.mViewBinding).tvRoomName.setText(getPlaceInfo(device));
        ((ActLightSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        int imageIndex = device.getImageIndex();
        int[] lightIconValue = IconRepository.getLightIconValue(this);
        int i = 0;
        while (true) {
            if (i >= lightIconValue.length) {
                break;
            }
            if (i == imageIndex) {
                ((ActLightSettingNewBinding) this.mViewBinding).ivIcon.setImageResource(lightIconValue[i]);
                break;
            }
            i++;
        }
        querySettings();
        if (device.getProductId().equals(ProductId.ID_BLE_SWITCH) || device.getProductId().equals(ProductId.ID_BLE_KBS)) {
            ((ActLightSettingNewBinding) this.mViewBinding).layoutBatchSet.setVisibility(8);
            ((ActLightSettingNewBinding) this.mViewBinding).tvLightOnState.setText(getString(R.string.light_on_state));
            ((ActLightSettingNewBinding) this.mViewBinding).rvLightSetting.setVisibility(8);
        }
        if (ProductRepository.getLightColorType((Object) device) == 2 && device.getMaxkelvin() > 0) {
            ((ActLightSettingNewBinding) this.mViewBinding).layoutKRange.setVisibility(0);
            ((ActLightSettingNewBinding) this.mViewBinding).tvKRange.setText(String.format(Locale.getDefault(), "%dK-%dK", Integer.valueOf(device.getMinkelvin()), Integer.valueOf(device.getMaxkelvin())));
        }
        ProductInfo bleProductInfoByType = ProductRepository.getBleProductInfoByType(device);
        if (bleProductInfoByType != null && (bleProductInfoByType.getSubProductKey().equals("23") || bleProductInfoByType.getSubProductKey().equals(ProductId.BLE_SWITCH_SUB_TYPE_KBS1))) {
            ((ActLightSettingNewBinding) this.mViewBinding).layoutControlType.setVisibility(0);
        }
        ((ActLightSettingNewBinding) this.mViewBinding).layoutDeviceReplace.setVisibility((!isOwnerOrManager() || isVirtual()) ? 8 : 0);
        if (isVirtual()) {
            if (SourceHelper.sourceModel.isBleSource()) {
                if (SourceHelper.sourceModel.isConstantVoltage()) {
                    if (SourceHelper.sourceModel.supportPwmFrequency()) {
                        ((ActLightSettingNewBinding) this.mViewBinding).layoutPwmFrequency.setVisibility(0);
                        ((ActLightSettingNewBinding) this.mViewBinding).layoutCurrent.setVisibility(8);
                        ((ActLightSettingNewBinding) this.mViewBinding).tvPwmFrequency.setText((CharSequence) Arrays.asList(SourceHelper.sourceModel.getFrequencyArray(this)).get(((ActLightSettingVM) this.mViewModel).extParam.getPwmFrequency()));
                        ((ActLightSettingNewBinding) this.mViewBinding).layoutPwmFrequency.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda15
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActLightSetting.this.lambda$startObserve$0(view);
                            }
                        });
                    }
                } else {
                    ((ActLightSettingNewBinding) this.mViewBinding).layoutPwmFrequency.setVisibility(8);
                    ((ActLightSettingNewBinding) this.mViewBinding).layoutCurrent.setVisibility(0);
                    if (((ActLightSettingVM) this.mViewModel).extParam.isW5B()) {
                        ((ActLightSettingNewBinding) this.mViewBinding).layoutCurrent.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda16
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActLightSetting.this.lambda$startObserve$1(view);
                            }
                        });
                    } else {
                        ((ActLightSettingNewBinding) this.mViewBinding).tvCurrent.setText(SourceHelper.sourceModel.getOutputCurrent(((ActLightSettingVM) this.mViewModel).extParam.getCurrentValue()[0]) + "mA");
                        ((ActLightSettingNewBinding) this.mViewBinding).layoutCurrent.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda17
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                ActLightSetting.this.lambda$startObserve$2(view);
                            }
                        });
                    }
                }
            }
            ((ActLightSettingNewBinding) this.mViewBinding).layoutDimRange.setVisibility(0);
            if (((ActLightSettingVM) this.mViewModel).lightType == 2 || ((ActLightSettingVM) this.mViewModel).lightType == 1) {
                ((ActLightSettingNewBinding) this.mViewBinding).layoutDimDepth.setVisibility(0);
            }
            if (((ActLightSettingVM) this.mViewModel).lightType == 2) {
                ((ActLightSettingNewBinding) this.mViewBinding).layoutSetDmxType.setVisibility(0);
                ((ActLightSettingNewBinding) this.mViewBinding).layoutKRange.setVisibility(0);
            }
            if (((ActLightSettingVM) this.mViewModel).supportRhythm()) {
                ((ActLightSettingVM) this.mViewModel).syncRhythmsList();
                ((ActLightSettingVM) this.mViewModel).showRhythms.setValue(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(View view) {
        showPwmFrequencyDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(View view) {
        NavUtils.destination(ActCurrentSetFive.class).withIntArray(Constants.CURRENT, ((ActLightSettingVM) this.mViewModel).extParam.getCurrentValue()).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(View view) {
        NavUtils.destination(ActCurrentSet.class).withInt(Constants.CURRENT, SourceHelper.sourceModel.getOutputCurrent(((ActLightSettingVM) this.mViewModel).extParam.getCurrentValue()[0])).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(RefreshLayout refreshLayout) {
        if (((ActLightSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            querySettings();
        }
        ((ActLightSettingNewBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        showDmxTypeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r1) {
        showDimDepthDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(String str) {
        if (str.equalsIgnoreCase("B5-DMX-4A-S")) {
            ((ActLightSettingNewBinding) this.mViewBinding).layoutConstantPower.setVisibility(0);
            ((ActLightSettingVM) this.mViewModel).queryConstantPower();
        }
        if (isOwnerOrManager() && WriteVirtualHelper.instance().isSupportNfc(str)) {
            ((ActLightSettingNewBinding) this.mViewBinding).layoutRestoreFactory.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Void r4) {
        NavUtils.destination(ActSetLightChannel.class).withLong(Constants.CONTROL_ID, ((ActLightSettingVM) this.mViewModel).controlId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) ((ActLightSettingVM) this.mViewModel).controlDevice.getValue())).withInt(Constants.LINE_ORDER, ((ActLightSettingVM) this.mViewModel).lineOrder).withBoolean(Constants.IS_HB4, ((ActLightSettingVM) this.mViewModel).isHB4()).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Void r2) {
        ((ActLightSettingNewBinding) this.mViewBinding).tvLine.setText(LineOrder.getShortLineStr(LineOrder.getList(((ActLightSettingVM) this.mViewModel).lineOrder)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Void r2) {
        WhiteBalanceDialog.asDefault().setControlObject(((ActLightSettingVM) this.mViewModel).controlDevice.getValue()).setValue(((ActLightSettingVM) this.mViewModel).whiteBalance).setSelectListener(new WhiteBalanceDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.view.dialog.WhiteBalanceDialog.SelectListener
            public final void confirm(int i, int i2, int i3) {
                ActLightSetting.this.lambda$startObserve$12(i, i2, i3);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(final int i, final int i2, final int i3) {
        getCmdHelper().setWhiteBalance(this, i, i2, i3, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda33
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$startObserve$11(i, i2, i3, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(int i, int i2, int i3, Boolean bool) {
        if (bool.booleanValue()) {
            ((ActLightSettingVM) this.mViewModel).whiteBalance = (i << 16) | (i2 << 8) | i3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$14(PowerState powerState) {
        List<SelectPowerOnStateDialog.OnOffState> generateContentList;
        if (powerState != null) {
            if (((ActLightSettingVM) this.mViewModel).controlDevice.getValue() != null && ((ActLightSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_SWITCH) && ((ActLightSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_KBS)) {
                generateContentList = SelectPowerOnStateDialog.generateSwitchList();
            } else {
                generateContentList = SelectPowerOnStateDialog.generateContentList();
            }
            int i = powerState.state;
            int size = generateContentList.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (i == generateContentList.get(i2).getMainValue()) {
                    if (i == 4) {
                        String stateContent = powerState.getStateContent(this, ((ActLightSettingVM) this.mViewModel).controlDevice.getValue());
                        if (stateContent.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                            TextClickUtil.setCircleText(((ActLightSettingNewBinding) this.mViewBinding).tvState, stateContent.substring(0, 7), stateContent.substring(7));
                            return;
                        } else {
                            ((ActLightSettingNewBinding) this.mViewBinding).tvState.setText(stateContent);
                            return;
                        }
                    }
                    ((ActLightSettingNewBinding) this.mViewBinding).tvState.setText(generateContentList.get(i2).getName());
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSceneDelayTime(final Boolean b2) {
        int i = b2.booleanValue() ? ((ActLightSettingVM) this.mViewModel).showPowerOnSceneDelay : ((ActLightSettingVM) this.mViewModel).showPowerOffSceneDelay;
        final ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (int i3 = 0; i3 < 61; i3++) {
            arrayList.add(i3 + "");
            if (i == i3) {
                i2 = i3;
            }
        }
        SelectListWheelDialog.asDefault(true).setTitle(getString(R.string.delay_time)).setSelectList(arrayList).setSpecify(getString(R.string.sec)).setSelectPosition(i2).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.13
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer integer) {
                ((ActLightSettingVM) ActLightSetting.this.mViewModel).bindSceneDelayTime(b2.booleanValue(), Integer.parseInt((String) arrayList.get(integer.intValue())));
            }
        }).showDialog(this.activity);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.handleActivityResult) {
            this.handleActivityResult = false;
        } else {
            ((ActLightSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActLightSettingVM) this.mViewModel).controlId));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKValueDialog() {
        Device value = ((ActLightSettingVM) this.mViewModel).controlDevice.getValue();
        if (value != null) {
            int maxkelvin = value.getMaxkelvin();
            int minkelvin = value.getMinkelvin();
            SelectKValueDialog.asDefault().controlObject(value).setMax(maxkelvin).setMin(minkelvin).setOnSaveListener(new AnonymousClass14(value, minkelvin, maxkelvin)).showDialog(this);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.light.ActLightSetting$14, reason: invalid class name */
    class AnonymousClass14 implements SelectKValueDialog.OnSaveListener {
        final /* synthetic */ Device val$device;
        final /* synthetic */ int val$kMax;
        final /* synthetic */ int val$kMin;

        @Override // com.ltech.smarthome.view.dialog.SelectKValueDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass14(final Device val$device, final int val$kMin, final int val$kMax) {
            this.val$device = val$device;
            this.val$kMin = val$kMin;
            this.val$kMax = val$kMax;
        }

        @Override // com.ltech.smarthome.view.dialog.SelectKValueDialog.OnSaveListener
        public boolean onSave(final SelectKValueDialog.OnOffState onOffState, int selectPos) {
            LightAssistant cmdHelper = ActLightSetting.this.getCmdHelper();
            ActLightSetting actLightSetting = ActLightSetting.this;
            int min = onOffState.getMin();
            int max = onOffState.getMax();
            final Device device = this.val$device;
            final int i = this.val$kMin;
            final int i2 = this.val$kMax;
            cmdHelper.setKRange(actLightSetting, min, max, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$14$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.AnonymousClass14.this.lambda$onSave$0(onOffState, device, i, i2, (Boolean) obj);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(SelectKValueDialog.OnOffState onOffState, Device device, int i, int i2, Boolean bool) {
            if (bool.booleanValue()) {
                ActLightSetting.this.setKRange(onOffState, device, i, i2);
            } else {
                ActLightSetting actLightSetting = ActLightSetting.this;
                actLightSetting.showErrorDialog(actLightSetting.getString(R.string.save_fail));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setKRange(final SelectKValueDialog.OnOffState onOffState, final Device device, int kMin, int kMax) {
        ((ObservableSubscribeProxy) Injection.net().updateCtRange(device.getDeviceId(), onOffState.getMax(), onOffState.getMin()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda42
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActLightSetting.this.lambda$setKRange$15(device, onOffState, obj);
            }
        }, new AnonymousClass15(kMin, kMax));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setKRange$15(Device device, SelectKValueDialog.OnOffState onOffState, Object obj) throws Exception {
        dismissLoadingDialog();
        device.setMinkelvin(onOffState.getMin());
        device.setMaxkelvin(onOffState.getMax());
        Injection.repo().device().saveDevice(device);
        ((ActLightSettingNewBinding) this.mViewBinding).tvKRange.setText(String.format(Locale.getDefault(), "%dK-%dK", Integer.valueOf(device.getMinkelvin()), Integer.valueOf(device.getMaxkelvin())));
        showSaveKValueDialog();
        ReplaceHelper.instance().backupData(this, ((ActLightSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.CT_RANGE, getCmdHelper().setKRange(onOffState.getMin(), onOffState.getMax()));
    }

    /* renamed from: com.ltech.smarthome.ui.device.light.ActLightSetting$15, reason: invalid class name */
    class AnonymousClass15 implements Consumer<Throwable> {
        final /* synthetic */ int val$kMax;
        final /* synthetic */ int val$kMin;

        AnonymousClass15(final int val$kMin, final int val$kMax) {
            this.val$kMin = val$kMin;
            this.val$kMax = val$kMax;
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Throwable throwable) throws Exception {
            ActLightSetting.this.getCmdHelper().setKRange(ActLightSetting.this, this.val$kMin, this.val$kMax, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$15$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.AnonymousClass15.this.lambda$accept$0((Boolean) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$0(Boolean bool) {
            if (!Injection.state().isConnectOuterNet()) {
                SmartToast.showShort(R.string.no_network);
            }
            ActLightSetting actLightSetting = ActLightSetting.this;
            actLightSetting.showErrorDialog(actLightSetting.getString(R.string.save_fail));
        }
    }

    private void showSaveKValueDialog() {
        MessageDialog.show(this, getString(R.string.app_str_setting_success), getString(R.string.k_range_save_tip));
    }

    private void initRv() {
        List<BaseDeviceSetActivity.LightSettingState> contentList = getContentList();
        this.dataList = contentList;
        this.selectArray = new boolean[contentList.size()];
        MultipleItemRvAdapter<BaseDeviceSetActivity.LightSettingState, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<BaseDeviceSetActivity.LightSettingState, BaseViewHolder>(this.dataList) { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.16
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(BaseDeviceSetActivity.LightSettingState lightSettingState) {
                return lightSettingState.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<BaseDeviceSetActivity.LightSettingState, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.16.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, BaseDeviceSetActivity.LightSettingState data, int position) {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_on_off_time_normal;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return BaseDeviceSetActivity.LightSettingState.TYPE_DEFAULT;
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<BaseDeviceSetActivity.LightSettingState, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.16.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_on_off_time_diy;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return BaseDeviceSetActivity.LightSettingState.TYPE_DIY;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, BaseDeviceSetActivity.LightSettingState data, int position) {
                        helper.setText(R.id.tv_name, data.getTitleName());
                        ActLightSetting.this.refreshTimeView(helper);
                        boolean z = true;
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        if (ActLightSetting.this.selectArray[position]) {
                            helper.setGone(R.id.layout_elec, true);
                            helper.setGone(R.id.layout_on, true);
                            helper.setGone(R.id.layout_off, true);
                            helper.setGone(R.id.layout_scene, true);
                            helper.setImageResource(R.id.iv_select, R.mipmap.ic_up_gray);
                        } else {
                            helper.setGone(R.id.layout_elec, false);
                            helper.setGone(R.id.layout_on, false);
                            helper.setGone(R.id.layout_off, false);
                            helper.setGone(R.id.layout_scene, false);
                            helper.setImageResource(R.id.iv_select, R.mipmap.ic_down_gray);
                        }
                        helper.setGone(R.id.iv_elec_select, ((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace() != null && (((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isManager()));
                        helper.setGone(R.id.iv_on_select, ((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace() != null && (((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isManager()));
                        helper.setGone(R.id.iv_off_select, ((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace() != null && (((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isManager()));
                        if (((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace() == null || (!((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isOwner() && !((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isManager())) {
                            z = false;
                        }
                        helper.setGone(R.id.iv_scene_select, z);
                        if (((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace() != null) {
                            if (((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActLightSettingVM) ActLightSetting.this.mViewModel).getCurrentPlace().isManager()) {
                                helper.addOnClickListener(R.id.layout_elec, R.id.layout_on, R.id.layout_off, R.id.layout_scene);
                            }
                        }
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda37
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActLightSetting.this.lambda$initRv$16(baseQuickAdapter, view, i);
            }
        });
        ((ActLightSettingNewBinding) this.mViewBinding).rvLightSetting.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.17
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layout_elec /* 2131297460 */:
                        ActLightSetting.this.showSelectTimeDialog();
                        break;
                    case R.id.layout_off /* 2131297557 */:
                        ActLightSetting.this.showPickTimeDialog(false);
                        break;
                    case R.id.layout_on /* 2131297558 */:
                        ActLightSetting.this.showPickTimeDialog(true);
                        break;
                    case R.id.layout_scene /* 2131297611 */:
                        ActLightSetting.this.showSceneSelectTimeDialog();
                        break;
                }
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActLightSettingNewBinding) this.mViewBinding).rvLightSetting);
        ((ActLightSettingNewBinding) this.mViewBinding).rvLightSetting.setLayoutManager(new LinearLayoutManager(this));
        ((ActLightSettingNewBinding) this.mViewBinding).rvLightSetting.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActLightSettingNewBinding) this.mViewBinding).rvLightSetting.getItemAnimator()).setSupportsChangeAnimations(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$16(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyDataSetChanged();
    }

    private List<BaseDeviceSetActivity.LightSettingState> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BaseDeviceSetActivity.LightSettingState(BaseDeviceSetActivity.LightSettingState.TYPE_DIY, getString(R.string.gradual_time)));
        return arrayList;
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void updatePowerStateDialogData(SelectPowerOnStateDialog.OnOffState state) {
        ((ActLightSettingVM) this.mViewModel).onState.setValue(state.getState());
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void refreshTimeView(BaseViewHolder helper) {
        if (this.queryTimeSuccess) {
            helper.setText(R.id.tv_scene_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.sceneSecPos + ((this.sceneMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            helper.setText(R.id.tv_on_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.onSecPos + ((this.onMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            helper.setText(R.id.tv_off_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.offSecPos + ((this.offMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            if (this.elecTimeSupport) {
                helper.setText(R.id.tv_elec_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.powerOnSecPos + ((this.powerOnMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            } else {
                helper.setText(R.id.tv_elec_sub_name, "");
            }
        }
    }

    private void querySettings() {
        ((ActLightSettingVM) this.mViewModel).queryBackData(((ActLightSettingVM) this.mViewModel).deviceId);
        CmdAssistant.getLightRhythmsCmdAssistant(((ActLightSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryDeviceSupport(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$querySettings$17((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$querySettings$17(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 26) {
                LHomeLog.i(getClass(), "querySupport=" + responseMsg.getResData());
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(18, 26), 16);
                ((ActLightSettingVM) this.mViewModel).supportDmxType = (parseInt & 8) != 0;
                ((ActLightSettingVM) this.mViewModel).supportDimDepth = (parseInt & 16) != 0;
                ((ActLightSettingVM) this.mViewModel).supportLineOrder = (parseInt & 128) != 0;
                ((ActLightSettingVM) this.mViewModel).supportDimRange = (parseInt & 256) != 0;
                boolean z = (parseInt & 32) != 0;
                if (((ActLightSettingVM) this.mViewModel).supportRhythm() && z) {
                    ((ActLightSettingVM) this.mViewModel).queryRhythmsSetting();
                } else {
                    ((ActLightSettingVM) this.mViewModel).setRhythmsGone();
                }
            } else {
                ((ActLightSettingVM) this.mViewModel).setRhythmsGone();
            }
        }
        int i = this.outputType;
        if (i != 0) {
            String outputString = getOutputString(i, ((ActLightSettingVM) this.mViewModel).lightType);
            if (!outputString.isEmpty()) {
                ((ActLightSettingVM) this.mViewModel).outputType.setValue(outputString);
                ((ActLightSettingVM) this.mViewModel).hasOutputType.setValue(true);
                queryState();
                return;
            }
            queryOutputType();
            return;
        }
        queryOutputType();
    }

    private void queryOutputType() {
        CmdAssistant.getQueryCmdAssistant(((ActLightSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryOutPutType(this, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting.18
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg msg) {
                String str;
                if (msg != null && msg.getStateCode() == 0 && msg.getResData() != null && msg.getResData().length() > 16) {
                    int parseInt = Integer.parseInt(msg.getResData().substring(16), 16);
                    if (parseInt > 0) {
                        switch (parseInt) {
                            case 1:
                                str = "DMX(" + ActLightSetting.this.getString(R.string.type_dim) + ")";
                                break;
                            case 2:
                                str = "DMX(" + ActLightSetting.this.getString(R.string.type_ct) + ")";
                                break;
                            case 3:
                                str = "DMX(" + ActLightSetting.this.getString(R.string.type_rgb) + ")";
                                break;
                            case 4:
                                str = "DMX(" + ActLightSetting.this.getString(R.string.type_rgbw) + ")";
                                break;
                            case 5:
                                str = "DMX(" + ActLightSetting.this.getString(R.string.type_rgbwy) + ")";
                                break;
                            case 6:
                                str = "0-10V(" + ActLightSetting.this.getString(R.string.type_dim) + ")";
                                break;
                            case 7:
                                str = "0-10V(" + ActLightSetting.this.getString(R.string.type_ct) + ")";
                                break;
                            case 8:
                                str = "DALI(" + ActLightSetting.this.getString(R.string.type_dim) + ")";
                                break;
                            case 9:
                                str = "DALI(" + ActLightSetting.this.getString(R.string.type_ct) + ")";
                                break;
                            case 10:
                                str = "DALI(" + ActLightSetting.this.getString(R.string.type_rgb) + ")";
                                break;
                            case 11:
                                str = "DALI(" + ActLightSetting.this.getString(R.string.type_rgbw) + ")";
                                break;
                            case 12:
                                str = "DALI(" + ActLightSetting.this.getString(R.string.type_rgbwy) + ")";
                                break;
                            case 13:
                                str = "DMX(" + ActLightSetting.this.getString(R.string.type_512) + ")";
                                break;
                            default:
                                str = "";
                                break;
                        }
                        ((ActLightSettingVM) ActLightSetting.this.mViewModel).outputType.setValue(str);
                        ((ActLightSettingVM) ActLightSetting.this.mViewModel).hasOutputType.setValue(true);
                    } else {
                        ((ActLightSettingVM) ActLightSetting.this.mViewModel).hasOutputType.setValue(false);
                    }
                } else {
                    ((ActLightSettingVM) ActLightSetting.this.mViewModel).hasOutputType.setValue(false);
                }
                ActLightSetting.this.queryState();
            }
        });
    }

    private String getOutputString(int outputType, int lightType) {
        StringBuilder sb = new StringBuilder();
        if (outputType == 3) {
            sb.append("DMX(");
        } else if (outputType == 2) {
            sb.append("DALI(");
        } else if (outputType == 1) {
            sb.append("0-10V(");
        }
        sb.append(((ActLightSettingVM) this.mViewModel).getLightTypeName(lightType));
        sb.append(")");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryState() {
        getCmdHelper().queryOnState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda38
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$queryState$18((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$18(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        ((ActLightSettingVM) this.mViewModel).onState.setValue(new PowerState(responseMsg.getResData().substring(16)));
        queryOnOffTime();
    }

    private void queryOnOffTime() {
        getCmdHelper().queryOnOffTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda39
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$queryOnOffTime$19((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryOnOffTime$19(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 24) {
                LHomeLog.i(getClass(), "queryState=" + responseMsg.getResData());
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
                this.onSecPos = parseInt / 1000;
                this.onMsPos = (parseInt % 1000) / 100;
                int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16) * 100;
                this.offSecPos = parseInt2 / 1000;
                this.offMsPos = (parseInt2 % 1000) / 100;
                this.queryTimeSuccess = true;
            }
            if (((ActLightSettingVM) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_SWITCH)) {
                return;
            }
            queryLightPowerOnTime();
        }
    }

    private void queryLightPowerOnTime() {
        getCmdHelper().queryPowerOnTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$queryLightPowerOnTime$20((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLightPowerOnTime$20(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResFunCode() == 192 && responseMsg.getResData().substring(12, 14).equalsIgnoreCase(ProductId.BLE_SMART_PANEL_SUB_TYPE_S2PRO)) {
                try {
                    int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
                    this.powerOnSecPos = parseInt / 1000;
                    this.powerOnMsPos = (parseInt % 1000) / 100;
                    this.mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            queryLightSceneTime();
        } else {
            this.elecTimeSupport = false;
            this.mAdapter.notifyDataSetChanged();
        }
        queryLightSceneTime();
    }

    private void queryLightSceneTime() {
        getCmdHelper().querySceneOnTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda40
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$queryLightSceneTime$21((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLightSceneTime$21(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResFunCode() == 192) {
                try {
                    int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
                    this.sceneSecPos = parseInt / 1000;
                    this.sceneMsPos = (parseInt % 1000) / 100;
                    this.mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            queryDimRange();
        } else {
            this.mAdapter.notifyDataSetChanged();
        }
        queryDimRange();
    }

    private void queryDimRange() {
        if (!((ActLightSettingVM) this.mViewModel).supportDimRange) {
            queryDimDepth();
        } else if (((ActLightSettingVM) this.mViewModel).isOwnerOrManager()) {
            getCmdHelper().queryDimRange(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.this.lambda$queryDimRange$22((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDimRange$22(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0 && responseMsg.getResData().length() >= 20) {
            ((ActLightSettingVM) this.mViewModel).brtRange[0] = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
            ((ActLightSettingVM) this.mViewModel).brtRange[1] = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16);
            ((ActLightSettingNewBinding) this.mViewBinding).layoutDimRange.setVisibility(0);
            ((ActLightSettingVM) this.mViewModel).dimmingRange.setValue(((ActLightSettingVM) this.mViewModel).getBrtString(((ActLightSettingVM) this.mViewModel).brtRange[0]) + " - " + ((ActLightSettingVM) this.mViewModel).getBrtString(((ActLightSettingVM) this.mViewModel).brtRange[1]));
        }
        queryDimDepth();
    }

    private void queryDimDepth() {
        if (!((ActLightSettingVM) this.mViewModel).supportDimDepth || (((ActLightSettingVM) this.mViewModel).lightType != 2 && ((ActLightSettingVM) this.mViewModel).lightType != 1)) {
            queryCtMode();
        } else if (((ActLightSettingVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActLightSettingVM) this.mViewModel).getCurrentPlace().isManager()) {
            getCmdHelper().queryDimDepth(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.this.lambda$queryDimDepth$23((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDimDepth$23(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0 && responseMsg.getResData().length() >= 20 && responseMsg.getResData().substring(12, 14).equalsIgnoreCase(ProductId.BLE_SMART_PANEL_SUB_TYPE_G4)) {
            this.depthMode = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
            this.dimDepth = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16);
            ((ActLightSettingNewBinding) this.mViewBinding).layoutDimDepth.setVisibility(0);
            if (this.depthMode != 2) {
                ((ActLightSettingNewBinding) this.mViewBinding).tvDimDepth.setText(R.string.mode_default);
            } else {
                String[] stringArray = getResources().getStringArray(R.array.dim_depth);
                if (this.dimDepth >= 1) {
                    ((ActLightSettingNewBinding) this.mViewBinding).tvDimDepth.setText(stringArray[this.dimDepth - 1]);
                } else {
                    ((ActLightSettingNewBinding) this.mViewBinding).tvDimDepth.setText(R.string.mode_default);
                }
            }
        }
        queryCtMode();
    }

    private void queryCtMode() {
        if (!((ActLightSettingVM) this.mViewModel).supportDmxType || ((ActLightSettingVM) this.mViewModel).lightType != 2) {
            queryLineOrder();
        } else {
            getCmdHelper().queryCtLightMode(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.this.lambda$queryCtMode$24((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryCtMode$24(ResponseMsg responseMsg) {
        int parseInt;
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getStateCode() != 0 && responseMsg.getResData().length() >= 18 && responseMsg.getResData().substring(12, 14).equalsIgnoreCase("0C") && ((parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16)) == 1 || parseInt == 2)) {
                this.dmxType = parseInt;
                ((ActLightSettingNewBinding) this.mViewBinding).layoutSetDmxType.setVisibility(0);
                ((ActLightSettingNewBinding) this.mViewBinding).tvDmxType.setText(this.dmxType == 1 ? R.string.ct_dmx_address_C_name : R.string.ct_dmx_address_BRT_name);
            }
            queryLineOrder();
        }
    }

    private void queryLineOrder() {
        if (((ActLightSettingVM) this.mViewModel).lightType == 1) {
            queryWhiteBalance();
        } else if (isHB4()) {
            getCmdHelper().queryLineOrderMulti(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda44
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.this.lambda$queryLineOrder$25((ResponseMsg) obj);
                }
            });
        } else {
            getCmdHelper().queryLineOrder(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda45
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.this.lambda$queryLineOrder$26((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLineOrder$25(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0 && responseMsg.getResData().length() >= 24) {
            ((ActLightSettingVM) this.mViewModel).lineOrder = Integer.parseInt(responseMsg.getResData().substring(18, 22), 16);
            ((ActLightSettingNewBinding) this.mViewBinding).layoutLineSet.setVisibility(0);
        }
        queryWhiteBalance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLineOrder$26(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0 && responseMsg.getResData().length() >= 20) {
            ((ActLightSettingVM) this.mViewModel).lineOrder = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16);
            ((ActLightSettingNewBinding) this.mViewBinding).layoutLineSet.setVisibility(0);
            if (((ActLightSettingVM) this.mViewModel).lightType == 20) {
                ((ActLightSettingVM) this.mViewModel).refreshLineOrder.call();
            }
        }
        queryWhiteBalance();
    }

    private boolean isHB4() {
        BleParam bleParam = (BleParam) ((ActLightSettingVM) this.mViewModel).controlDevice.getValue().getParam(BleParam.class);
        return bleParam != null && bleParam.getBleType() == 526;
    }

    private void queryWhiteBalance() {
        if (((ActLightSettingVM) this.mViewModel).lightType != 20) {
            return;
        }
        getCmdHelper().queryWhiteBalance(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$queryWhiteBalance$27((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryWhiteBalance$27(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0 && responseMsg.getResData().length() >= 22) {
            ((ActLightSettingVM) this.mViewModel).whiteBalance = Integer.parseInt(responseMsg.getResData().substring(16, 22), 16);
            ((ActLightSettingNewBinding) this.mViewBinding).layoutWhiteBalance.setVisibility(0);
        }
        queryDuvSupport();
    }

    private void queryDuvSupport() {
        if (isOwnerOrManager()) {
            getCmdHelper().queryDuvSupport(this, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.this.lambda$queryDuvSupport$28((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDuvSupport$28(ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0 && Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 1) {
            ((ActLightSettingNewBinding) this.mViewBinding).layoutDuv.setVisibility(0);
        }
    }

    private void showDmxTypeDialog() {
        SelectDmxDialog.asDefault().setTitle(getString(R.string.ct_dmx_address_type)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setSelectPosition(this.dmxType - 1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda32
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showDmxTypeDialog$30((Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDmxTypeDialog$30(final Integer num) {
        getCmdHelper().setCtLightMode(this, num.intValue() + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda35
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showDmxTypeDialog$29(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDmxTypeDialog$29(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            this.dmxType = num.intValue() + 1;
            ((ActLightSettingNewBinding) this.mViewBinding).tvDmxType.setText(this.dmxType == 1 ? R.string.ct_dmx_address_C_name : R.string.ct_dmx_address_BRT_name);
            ReplaceHelper.instance().backupData(this, ((ActLightSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.CT_DIM_TYPE, getCmdHelper().setCtLightMode(this.dmxType));
        }
    }

    private void showDimDepthDialog() {
        SelectDimDepthDialog.asDefault().setTitle(getString(R.string.dim_depth)).setConfirmString(getString(R.string.save)).setSelectPosition(this.depthMode - 1).setProgress(21 - this.dimDepth).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda43
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showDimDepthDialog$32((Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimDepthDialog$32(final Integer num) {
        getCmdHelper().setDimDepth(this, num.intValue(), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showDimDepthDialog$31(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDimDepthDialog$31(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            this.depthMode = num.intValue() == 0 ? 1 : 2;
            this.dimDepth = num.intValue();
            if (this.depthMode == 1) {
                ((ActLightSettingNewBinding) this.mViewBinding).tvDimDepth.setText(R.string.mode_default);
            } else {
                ((ActLightSettingNewBinding) this.mViewBinding).tvDimDepth.setText(getResources().getStringArray(R.array.dim_depth)[this.dimDepth - 1]);
            }
            ReplaceHelper.instance().backupData(this, ((ActLightSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.DIM_DEPTH, getCmdHelper().setDimDepth(num.intValue()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPickTimeDialog(final boolean on) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(on ? this.onSecPos : this.offSecPos).setSelectSecPosition(on ? this.onMsPos : this.offMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda41
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActLightSetting.this.lambda$showPickTimeDialog$34(on, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPickTimeDialog$34(boolean z, int i, int i2) {
        if (z) {
            this.onSecPos = i;
            this.onMsPos = i2;
        } else {
            this.offSecPos = i;
            this.offMsPos = i2;
        }
        getCmdHelper().setOnOffTime(this, (this.onSecPos * 1000) + (this.onMsPos * 100), (this.offSecPos * 1000) + (this.offMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showPickTimeDialog$33((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPickTimeDialog$33(Boolean bool) {
        if (bool.booleanValue()) {
            this.mAdapter.notifyDataSetChanged();
            ReplaceHelper.instance().backupData(this, ((ActLightSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.FADE_TIME, getCmdHelper().setOnOffTime((this.onSecPos * 1000) + (this.onMsPos * 100), (this.offSecPos * 1000) + (this.offMsPos * 100)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSelectTimeDialog() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.powerOnSecPos).setSelectSecPosition(this.powerOnMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActLightSetting.this.lambda$showSelectTimeDialog$36(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$36(int i, int i2) {
        this.powerOnSecPos = i;
        this.powerOnMsPos = i2;
        getCmdHelper().setPowerOnTime(this, (this.powerOnSecPos * 1000) + (this.powerOnMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showSelectTimeDialog$35((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$35(Boolean bool) {
        if (bool.booleanValue()) {
            this.mAdapter.notifyDataSetChanged();
            ReplaceHelper.instance().backupData(this, ((ActLightSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.POWER_FADE_TIME, getCmdHelper().setPowerOnTime((this.powerOnSecPos * 1000) + (this.powerOnMsPos * 100)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSceneSelectTimeDialog() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.sceneSecPos).setSelectSecPosition(this.sceneMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda34
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActLightSetting.this.lambda$showSceneSelectTimeDialog$38(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSceneSelectTimeDialog$38(int i, int i2) {
        this.sceneSecPos = i;
        this.sceneMsPos = i2;
        getCmdHelper().setSceneOnTime(this, (this.sceneSecPos * 1000) + (this.sceneMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showSceneSelectTimeDialog$37((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSceneSelectTimeDialog$37(Boolean bool) {
        if (bool.booleanValue()) {
            this.mAdapter.notifyDataSetChanged();
            ReplaceHelper.instance().backupData(this, ((ActLightSettingVM) this.mViewModel).deviceId, UpdateBackDataRequest.SCENE_FADE_TIME, getCmdHelper().setSceneOnTime((this.sceneSecPos * 1000) + (this.sceneMsPos * 100)));
        }
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
        ActLightSettingVM actLightSettingVM = (ActLightSettingVM) this.mViewModel;
        this.mSelectHour = isStart ? actLightSettingVM.getStarH() : actLightSettingVM.getEndH();
        ActLightSettingVM actLightSettingVM2 = (ActLightSettingVM) this.mViewModel;
        this.mSelectMin = isStart ? actLightSettingVM2.getStarM() : actLightSettingVM2.getEndM();
        TimeSelectDialog.asDefault().setTitle(getString(isStart ? R.string.start_time : R.string.end_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectMinPosition(this.mSelectHour).setSelectSecPosition(this.mSelectMin).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActLightSetting.this.lambda$showTimeDialog$39(isStart, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeDialog$39(boolean z, int i, int i2) {
        if (this.mSelectHour == i && this.mSelectMin == i2) {
            return;
        }
        this.mSelectHour = i;
        this.mSelectMin = i2;
        if (z) {
            ((ActLightSettingVM) this.mViewModel).setStarTime(this.mSelectHour, this.mSelectMin);
        } else {
            ((ActLightSettingVM) this.mViewModel).setEndTime(this.mSelectHour, this.mSelectMin);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPlanListDialog() {
        SelectListDialog selectPosition = SelectListDialog.asDefault(true).setTitle(getString(R.string.setting)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setSelectPosition(((ActLightSettingVM) this.mViewModel).mCurPlan - 1);
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showPlanListDialog$40((Integer) obj);
            }
        }).setSelectList(((ActLightSettingVM) this.mViewModel).rhythmsNameList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPlanListDialog$40(Integer num) {
        ((ActLightSettingVM) this.mViewModel).setPlan(num.intValue() + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBackSettingData(JSONObject jsonObject) {
        this.queryTimeSuccess = true;
        showData(jsonObject, UpdateBackDataRequest.OUTPUT_TYPE);
        if (isVirtual()) {
            showData(jsonObject, UpdateBackDataRequest.POWER_STATUS);
            showData(jsonObject, UpdateBackDataRequest.FADE_TIME);
            showData(jsonObject, UpdateBackDataRequest.POWER_FADE_TIME);
            showData(jsonObject, UpdateBackDataRequest.SCENE_FADE_TIME);
            showData(jsonObject, UpdateBackDataRequest.DIM_RANGE);
            showData(jsonObject, UpdateBackDataRequest.DIM_DEPTH);
            if (((ActLightSettingVM) this.mViewModel).supportRhythm()) {
                showData(jsonObject, UpdateBackDataRequest.CT_DIM_TYPE);
                showData(jsonObject, UpdateBackDataRequest.CIRCADIAN_PLAN);
            }
            showData(jsonObject, UpdateBackDataRequest.CONTROL_INPUT_TYPE);
            showData(jsonObject, "trigger_scene_1_1");
            showData(jsonObject, "trigger_scene_1_2");
            showData(jsonObject, "trigger_delay_1_1");
            showData(jsonObject, "trigger_delay_1_2");
        }
    }

    private void showData(JSONObject jsonObject, String key) {
        String str;
        int[] parseBackupData;
        str = (String) jsonObject.get(key);
        parseBackupData = ReplaceHelper.instance().parseBackupData(jsonObject, key);
        key.hashCode();
        switch (key) {
            case "trigger_delay_1_1":
                ((ActLightSettingVM) this.mViewModel).showPowerOnSceneDelayStr.setValue(parseBackupData[0] + getString(R.string.sec));
                ((ActLightSettingVM) this.mViewModel).showPowerOnSceneDelay = parseBackupData[0];
                break;
            case "trigger_delay_1_2":
                ((ActLightSettingVM) this.mViewModel).showPowerOffSceneDelayStr.setValue(parseBackupData[0] + getString(R.string.sec));
                ((ActLightSettingVM) this.mViewModel).showPowerOffSceneDelay = parseBackupData[0];
                break;
            case "ctDimmerType":
                int i = parseBackupData[0];
                if (i == 1 || i == 2) {
                    this.dmxType = i;
                    ((ActLightSettingNewBinding) this.mViewBinding).tvDmxType.setText(this.dmxType == 1 ? R.string.ct_dmx_address_C_name : R.string.ct_dmx_address_BRT_name);
                    break;
                }
            case "outputSignal":
                this.outputType = parseBackupData[0];
                break;
            case "circadianLighting":
                if (str != null) {
                    ((ActLightSettingVM) this.mViewModel).parseRhythmsSetting(str.substring(8));
                    break;
                }
                break;
            case "trigger_scene_1_1":
                Scene localSceneBySceneNum = Injection.repo().scene().getLocalSceneBySceneNum(parseBackupData[0]);
                if (localSceneBySceneNum != null) {
                    ((ActLightSettingVM) this.mViewModel).showPowerOnScene.setValue(localSceneBySceneNum.getName());
                    break;
                }
                break;
            case "trigger_scene_1_2":
                Scene localSceneBySceneNum2 = Injection.repo().scene().getLocalSceneBySceneNum(parseBackupData[0]);
                if (localSceneBySceneNum2 != null) {
                    ((ActLightSettingVM) this.mViewModel).showPowerOffScene.setValue(localSceneBySceneNum2.getName());
                    break;
                }
                break;
            case "powerStatus":
                ((ActLightSettingVM) this.mViewModel).onState.setValue(new PowerState(parseBackupData, true));
                break;
            case "fadeTime":
                int i2 = parseBackupData[0];
                this.onSecPos = i2 / 1000;
                this.onMsPos = (i2 % 1000) / 100;
                int i3 = parseBackupData[1];
                this.offSecPos = i3 / 1000;
                this.offMsPos = (i3 % 1000) / 100;
                this.mAdapter.notifyDataSetChanged();
                break;
            case "dimmerDepth":
                int i4 = parseBackupData[0];
                this.depthMode = i4;
                this.dimDepth = parseBackupData[1];
                if (i4 != 2) {
                    ((ActLightSettingNewBinding) this.mViewBinding).tvDimDepth.setText(R.string.mode_default);
                    break;
                } else {
                    String[] stringArray = getResources().getStringArray(R.array.dim_depth);
                    if (this.dimDepth >= 1) {
                        ((ActLightSettingNewBinding) this.mViewBinding).tvDimDepth.setText(stringArray[this.dimDepth - 1]);
                        break;
                    } else {
                        ((ActLightSettingNewBinding) this.mViewBinding).tvDimDepth.setText(R.string.mode_default);
                        break;
                    }
                }
            case "dimmerRange":
                ((ActLightSettingVM) this.mViewModel).brtRange[0] = parseBackupData[0];
                ((ActLightSettingVM) this.mViewModel).brtRange[1] = parseBackupData[1];
                ((ActLightSettingVM) this.mViewModel).dimmingRange.setValue(((ActLightSettingVM) this.mViewModel).getBrtString(((ActLightSettingVM) this.mViewModel).brtRange[0]) + " - " + ((ActLightSettingVM) this.mViewModel).getBrtString(((ActLightSettingVM) this.mViewModel).brtRange[1]));
                break;
            case "powerFadeTime":
                int i5 = parseBackupData[0];
                this.powerOnSecPos = i5 / 1000;
                this.powerOnMsPos = (i5 % 1000) / 100;
                this.mAdapter.notifyDataSetChanged();
                break;
            case "lightSequence":
                if (str == null) {
                    ((ActLightSettingVM) this.mViewModel).lineOrder = LineOrder.getDefaultOrder(((ActLightSettingVM) this.mViewModel).lightType);
                } else {
                    ((ActLightSettingVM) this.mViewModel).lineOrder = Integer.parseInt(str.substring(10, 14), 16);
                }
                if (((ActLightSettingVM) this.mViewModel).lightType == 20) {
                    ((ActLightSettingVM) this.mViewModel).refreshLineOrder.call();
                    break;
                }
                break;
            case "control_input_type":
                ((ActLightSettingVM) this.mViewModel).controlType.setValue(Integer.valueOf(parseBackupData[0]));
                break;
            case "sceneFadeTime":
                int i6 = parseBackupData[0];
                this.sceneSecPos = i6 / 1000;
                this.sceneMsPos = (i6 % 1000) / 100;
                this.mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void showPwmFrequencyDialog() {
        final List<String> asList = Arrays.asList(SourceHelper.sourceModel.getFrequencyArray(this));
        SinglePickerDialog.asDefault().setTitle(getString(R.string.pwm_frequency)).setSelectList(asList).setSelectPosition(((ActLightSettingVM) this.mViewModel).extParam.getPwmFrequency()).setSelectListener(new SinglePickerDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda22
            @Override // com.ltech.smarthome.view.dialog.SinglePickerDialog.SelectListener
            public final void confirm(int i) {
                ActLightSetting.this.lambda$showPwmFrequencyDialog$42(asList, i);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPwmFrequencyDialog$42(final List list, final int i) {
        ((ActLightSettingVM) this.mViewModel).extParam.setPwmFrequency(i);
        ((ActLightSettingVM) this.mViewModel).updateParamExt(((ActLightSettingVM) this.mViewModel).controlDevice.getValue(), GsonUtils.toJson(((ActLightSettingVM) this.mViewModel).extParam), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda36
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showPwmFrequencyDialog$41(list, i, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPwmFrequencyDialog$41(List list, int i, Boolean bool) {
        ((ActLightSettingNewBinding) this.mViewBinding).tvPwmFrequency.setText((CharSequence) list.get(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnbindDialog(final boolean isExc) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActLightSetting.this.lambda$showUnbindDialog$43(isExc, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$43(boolean z, Integer num) {
        ((ActLightSettingVM) this.mViewModel).unbindScene(z);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3004) {
            if (data != null) {
                ((ActLightSettingVM) this.mViewModel).setWeek(data.getStringExtra(Constants.WEEKS));
            }
        } else if (resultCode == 3023) {
            this.isfirst = true;
        } else if (resultCode == 5011) {
            this.handleActivityResult = true;
            final int[] intArrayExtra = data.getIntArrayExtra(Constants.CURRENT);
            ((ActLightSettingVM) this.mViewModel).extParam.setCurrentValue(intArrayExtra);
            ((ActLightSettingVM) this.mViewModel).updateParamExt(((ActLightSettingVM) this.mViewModel).controlDevice.getValue(), GsonUtils.toJson(((ActLightSettingVM) this.mViewModel).extParam), new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActLightSetting$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActLightSetting.this.lambda$onActivityResult$44(intArrayExtra, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$44(int[] iArr, Boolean bool) {
        SmartToast.showShort(getString(bool.booleanValue() ? R.string.save_success : R.string.save_fail));
        if (!bool.booleanValue() || ((ActLightSettingVM) this.mViewModel).extParam.isW5B()) {
            return;
        }
        ((ActLightSettingNewBinding) this.mViewBinding).tvCurrent.setText(iArr[0] + "mA");
    }
}