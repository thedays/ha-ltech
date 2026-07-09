package com.ltech.smarthome.ui.device.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSmartPanelSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.automation.ActAddAutomation;
import com.ltech.smarthome.ui.config.ActMeshScanIcon;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.screenpanel.ActScreenPanelElderlyMode;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleDeviceAndGroupNew;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectScene;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.my.ActLanguageSelect;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SensingDistanceDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSetting extends BaseDeviceSetActivity<ActSmartPanelSettingBinding, ActSmartPanelSettingVM> {
    private int controlType;
    protected List<RelateInfoItem> dataList;
    protected BaseQuickAdapter<RelateInfoItem, BaseViewHolder> keyAdapter;
    private int mSelectHour;
    private int mSelectMin;
    public long placeId;
    public String productId;
    public RelateInfoAssistant relateInfoAssistant;
    public Listing<Group> request;
    public List<RelateInfoItem> relatedInfoList = new ArrayList();
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public MediatorLiveData<List<Group>> groupList = new MediatorLiveData<>();
    public MediatorLiveData<List<Scene>> sceneList = new MediatorLiveData<>();
    private boolean searching = false;

    static /* synthetic */ boolean lambda$initData$12(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$initData$13(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$initData$14(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ void lambda$showUpNightTimes$11(int i, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_smart_panel_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
        ((ActSmartPanelSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.controlType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 0);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.request = Injection.repo().group().getGroupList(this, this.placeId);
        boolean z = true;
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbEngravedText.setButtonEnable(((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isManager());
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbEngravedText.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.1
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).setEngravedText(isChecked);
            }
        });
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setButtonEnable(((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isManager());
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbMemorizePowerOnTip.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.2
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).setpowerOffStatus(isChecked);
            }
        });
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbNightMode.setButtonEnable(((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isManager());
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbNightMode.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.3
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (Injection.repo().device().getSuperPanel() != null) {
                    ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).setNightMode(isChecked);
                } else {
                    ((ActSmartPanelSettingBinding) ActSmartPanelSetting.this.mViewBinding).sbNightMode.setCheckedNotByUser(false);
                    ActSmartPanelSetting.this.showNoSuperPanelDialog();
                }
            }
        });
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbAutoTurnOff.setButtonEnable(((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isManager());
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbAutoTurnOff.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.4
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).setAutoTurnOff(isChecked);
            }
        });
        SwitchButton switchButton = ((ActSmartPanelSettingBinding) this.mViewBinding).sbDistance;
        if (!((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isOwner() && !((ActSmartPanelSettingVM) this.mViewModel).getCurrentPlace().isManager()) {
            z = false;
        }
        switchButton.setButtonEnable(z);
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbDistance.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.5
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).setSensingDistanceOn(isChecked);
                if (isChecked) {
                    ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).distanceText.setValue(((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).distanceValue.getValue() + "%");
                    return;
                }
                ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).distanceText.setValue(ActSmartPanelSetting.this.getString(R.string.sensing_distance_tip));
            }
        });
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbTouchVolume.setButtonEnable(isOwnerOrManager());
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbTouchVolume.setOnCheckedChangeListener(new AnonymousClass6());
        ((ActSmartPanelSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActSmartPanelSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActSmartPanelSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActSmartPanelSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda11
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActSmartPanelSetting.this.lambda$initView$0(refreshLayout);
            }
        });
    }

    /* renamed from: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$6, reason: invalid class name */
    class AnonymousClass6 implements SwitchButton.OnCheckedChangeListener {
        AnonymousClass6() {
        }

        @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
        public void onCheckedChanged(SwitchButton view, final boolean isChecked) {
            ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).setBuzzerState(isChecked, 1, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$6$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSetting.AnonymousClass6.this.lambda$onCheckedChanged$0(isChecked, (Boolean) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCheckedChanged$0(boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            ((ActSmartPanelSettingBinding) ActSmartPanelSetting.this.mViewBinding).sbTouchVolume.setCheckedNotByUser(!z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            ((ActSmartPanelSettingVM) this.mViewModel).isFirst = true;
            ((ActSmartPanelSettingVM) this.mViewModel).loadDeviceStatus(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue());
            ((ActSmartPanelSettingVM) this.mViewModel).queryScene(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId());
        }
        ((ActSmartPanelSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX WARN: Removed duplicated region for block: B:79:0x0327  */
    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void startObserve() {
        /*
            Method dump skipped, instructions count: 1162
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.startObserve():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(JSONObject jSONObject) {
        if (((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue().isVirtual()) {
            showBackSettingData(jSONObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r4) {
        NavUtils.destination(ActScreenPanelElderlyMode.class).withLong(Constants.CONTROL_ID, ((ActSmartPanelSettingVM) this.mViewModel).controlId).withInt(Constants.ELDERLY_MODE, this.relateInfoAssistant.getSwitchScreenBigIcon()).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r4) {
        NavUtils.destination(ActLanguageSelect.class).withLong(Constants.CONTROL_ID, ((ActSmartPanelSettingVM) this.mViewModel).controlId).withInt(Constants.LANGUAGE_TPYE, this.relateInfoAssistant.getSwitchScreenLanguage()).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r4) {
        NavUtils.destination(ActMeshScanIcon.class).withLong(Constants.CONTROL_ID, 9999L).withLong(Constants.PLACE_ID, ((ActSmartPanelSettingVM) this.mViewModel).getCurPlace().getPlaceId()).withBoolean(Constants.ICON_UPGRADE, true).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Integer num) {
        ((ActSmartPanelSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(final Device device, Void r4) {
        if (ProductId.ID_SMART_PANEL_S6B.equals(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue().getProductId())) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda16
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActSmartPanelSetting.this.lambda$startObserve$6(device, imageTipDialog);
                }
            });
        } else {
            ((ActSmartPanelSettingVM) this.mViewModel).clickAdjustKRange(this, device, this.relateInfoAssistant, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Device device, ImageTipDialog imageTipDialog) {
        ((ActSmartPanelSettingVM) this.mViewModel).clickAdjustKRange(this, device, this.relateInfoAssistant, false);
        imageTipDialog.dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Void r4) {
        if (RelateInfoUtils.needShowTipDialog()) {
            this.dialog = ImageTipDialog.asDefault().setTitle(getString(R.string.s6b_click_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.pic_click_tip_s6b).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActSmartPanelSetting.this.lambda$startObserve$8(imageTipDialog);
                }
            });
            this.dialog.showDialog(this);
        } else {
            this.searching = true;
            getMainHandler().postDelayed(this.getBatteryRunnable, 5000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(ImageTipDialog imageTipDialog) {
        this.searching = true;
        getMainHandler().postDelayed(this.getBatteryRunnable, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Boolean bool) {
        ((ActSmartPanelSettingBinding) this.mViewBinding).sbTouchVolume.setCheckedNotByUser(bool.booleanValue());
    }

    private void setPanelColor(String panelColor) {
        String[] stringArray = getResources().getStringArray(R.array.g4_bg_panel_color_name);
        try {
            int parseInt = Integer.parseInt(panelColor) - 1;
            if (parseInt < stringArray.length) {
                ((ActSmartPanelSettingBinding) this.mViewBinding).tvPanelBackTip.setText(stringArray[parseInt]);
            }
        } catch (Exception e) {
            LHomeLog.e(getClass(), e.toString());
        }
    }

    private void showUpNightTimes() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        TimeSelectDialog.asDefault().setTitle(getString(R.string.app_str_execution_times)).setMinList(arrayList).setSelectMinPosition(0).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i, int i2) {
                ActSmartPanelSetting.lambda$showUpNightTimes$11(i, i2);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSensingDistanceDialog() {
        SensingDistanceDialog.asDefault().setCallback(new SensingDistanceDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.15
            @Override // com.ltech.smarthome.view.dialog.SensingDistanceDialog.OnConfirmCallback
            public void onConfirmClick(SensingDistanceDialog dialog) {
                ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).distanceValue.setValue(Integer.valueOf(dialog.getProgress()));
                ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).changeSensingDistance();
            }
        }).setProgress(((ActSmartPanelSettingVM) this.mViewModel).distanceValue.getValue() != null ? ((ActSmartPanelSettingVM) this.mViewModel).distanceValue.getValue().intValue() : 25).showDialog(this);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle extras;
        Bundle bundleExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            refreshRelateInfoList(Injection.repo().device().getDeviceByDeviceId(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId()));
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.keyAdapter;
            if (baseQuickAdapter != null) {
                baseQuickAdapter.notifyDataSetChanged();
            }
            if ((!this.productId.equals(ProductId.ID_SMART_PANEL_G4) && !this.productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) || this.relateInfoAssistant.getRelateInfo(-1) == null || this.relateInfoAssistant.getRelateInfo(-1).objectId == 0) {
                return;
            }
            ((ActSmartPanelSettingBinding) this.mViewBinding).tvGetUpNightModeTip.setText(getString(R.string.app_str_turned_on));
            return;
        }
        if (resultCode == 5008) {
            if (data == null || (bundleExtra = data.getBundleExtra("data")) == null) {
                return;
            }
            ((ActSmartPanelSettingVM) this.mViewModel).switchOn.setValue(Boolean.valueOf(bundleExtra.getBoolean("isOn", false)));
            return;
        }
        if (resultCode != 5009) {
            if (requestCode == 5010 && resultCode == -1) {
                ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActSmartPanelSettingVM) this.mViewModel).controlId));
                return;
            }
            return;
        }
        if (data == null || (extras = data.getExtras()) == null) {
            return;
        }
        setPanelColor(extras.getInt(Constants.BACK_COLOR, 7) + "");
    }

    private void initData(final Device device) {
        refreshRelateInfoList(device);
        if (!device.getProductId().equals(ProductId.ID_SMART_PANEL_G4) && !device.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO) && !device.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
            BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter = this.keyAdapter;
            if (baseQuickAdapter == null) {
                BaseQuickAdapter<RelateInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<RelateInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key_set, this.relatedInfoList) { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.16
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter
                    public void convert(BaseViewHolder helper, RelateInfoItem item) {
                        if (item.infoName != null && !TextUtils.isEmpty(item.infoName)) {
                            helper.setText(R.id.tv_main, item.infoName);
                        } else if (device.getProductId().equals(ProductId.ID_SCENE_PANEL_S8) || device.getProductId().equals(ProductId.ID_SMART_PANEL_S6B)) {
                            if (ActSmartPanelSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()) != null) {
                                if (ActSmartPanelSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name == null || ActSmartPanelSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name.equals("")) {
                                    helper.setText(R.id.tv_main, ActSmartPanelSetting.this.getResources().getStringArray(R.array.smart_scene_panel_s8_key_select)[helper.getAdapterPosition()]);
                                } else {
                                    helper.setText(R.id.tv_main, ActSmartPanelSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name);
                                }
                            } else {
                                helper.setText(R.id.tv_main, ActSmartPanelSetting.this.getResources().getStringArray(R.array.smart_scene_panel_s8_key_select)[helper.getAdapterPosition()]);
                            }
                        } else {
                            String[] stringArray = ActSmartPanelSetting.this.getResources().getStringArray(R.array.smart_panel_s4_key_select);
                            if (ActSmartPanelSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()) != null) {
                                if (ActSmartPanelSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name == null || ActSmartPanelSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name.equals("")) {
                                    helper.setText(R.id.tv_main, stringArray[helper.getAdapterPosition()]);
                                } else {
                                    helper.setText(R.id.tv_main, ActSmartPanelSetting.this.relateInfoAssistant.getRelateInfo(helper.getAdapterPosition()).name);
                                }
                            } else {
                                helper.setText(R.id.tv_main, stringArray[helper.getAdapterPosition()]);
                            }
                        }
                        if (item.infoName == null || item.type == 0) {
                            helper.setText(R.id.tv_sub_text, ActSmartPanelSetting.this.getString(R.string.no_bind_object));
                        } else {
                            helper.setText(R.id.tv_sub_text, item.actionInfo);
                        }
                        boolean z = true;
                        helper.setGone(R.id.iv_go, ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).getCurrentPlace().isManager());
                        View view = helper.itemView;
                        if (!((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).getCurrentPlace().isOwner() && !((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).getCurrentPlace().isManager()) {
                            z = false;
                        }
                        view.setEnabled(z);
                        helper.addOnClickListener(R.id.tv_main);
                        ((TextView) helper.getView(R.id.tv_main)).setCompoundDrawablesWithIntrinsicBounds((((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).getCurrentPlace().isManager()) ? R.mipmap.ic_edit_black : 0, 0, 0, 0);
                    }
                };
                this.keyAdapter = baseQuickAdapter2;
                baseQuickAdapter2.setOnItemChildClickListener(new AnonymousClass17());
                this.keyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda24
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                    public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                        ActSmartPanelSetting.this.lambda$initData$15(baseQuickAdapter3, view, i);
                    }
                });
                this.keyAdapter.bindToRecyclerView(((ActSmartPanelSettingBinding) this.mViewBinding).rvKeys);
                ((ActSmartPanelSettingBinding) this.mViewBinding).rvKeys.setLayoutManager(new LinearLayoutManager(this));
                return;
            }
            baseQuickAdapter.setNewData(this.relatedInfoList);
            return;
        }
        if ((!this.productId.equals(ProductId.ID_SMART_PANEL_G4) && !this.productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) || this.relateInfoAssistant.getRelateInfo(-1) == null || this.relateInfoAssistant.getRelateInfo(-1).objectId == 0) {
            return;
        }
        ((ActSmartPanelSettingBinding) this.mViewBinding).tvGetUpNightModeTip.setText(getString(R.string.app_str_turned_on));
    }

    /* renamed from: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$17, reason: invalid class name */
    class AnonymousClass17 implements BaseQuickAdapter.OnItemChildClickListener {
        static /* synthetic */ boolean lambda$onItemChildClick$0(BaseDialog baseDialog, View view) {
            return false;
        }

        AnonymousClass17() {
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            if (((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActSmartPanelSettingVM) ActSmartPanelSetting.this.mViewModel).getCurrentPlace().isManager()) {
                if (ActSmartPanelSetting.this.keyAdapter.getData().get(position).infoName == null || ActSmartPanelSetting.this.keyAdapter.getData().get(position).type == 0) {
                    RelateInfoItem relateInfoItem = ActSmartPanelSetting.this.keyAdapter.getData().get(position);
                    if (relateInfoItem.infoName == null || TextUtils.isEmpty(relateInfoItem.infoName)) {
                        ActSmartPanelSetting actSmartPanelSetting = ActSmartPanelSetting.this;
                        actSmartPanelSetting.showEditKeyNameDialog(actSmartPanelSetting.getResources().getStringArray(R.array.smart_scene_panel_s8_key_select)[position], position);
                        return;
                    } else {
                        ActSmartPanelSetting.this.showEditKeyNameDialog(relateInfoItem.infoName, position);
                        return;
                    }
                }
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActSmartPanelSetting.this.getString(R.string.app_str_operation_failure), ActSmartPanelSetting.this.getString(R.string.app_str_smart_panel_rename_tip)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_accept), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$17$$ExternalSyntheticLambda0
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view2) {
                        return ActSmartPanelSetting.AnonymousClass17.lambda$onItemChildClick$0(baseDialog, view2);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$15(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
        if (this.keyAdapter.getData().get(i).infoName == null || this.keyAdapter.getData().get(i).type == 0) {
            if (this.productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                if (((ActSmartPanelSettingVM) this.mViewModel).isNeedUpgrade()) {
                    MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.tips), getString(R.string.app_str_s8_upgrade_tips)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_accept), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda3
                        @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                        public final boolean onClick(BaseDialog baseDialog, View view2) {
                            return ActSmartPanelSetting.lambda$initData$12(baseDialog, view2);
                        }
                    });
                    return;
                } else {
                    showBindDialog(i);
                    return;
                }
            }
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_smart_panel_bind_tip)).setCancelable(false).setOkButton(getString(R.string.app_str_continue_to_bind), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.18
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    ActSmartPanelSetting.this.showBindDialog(i);
                    return false;
                }
            }).setCancelButton(getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda4
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    return ActSmartPanelSetting.lambda$initData$13(baseDialog, view2);
                }
            });
            return;
        }
        if (((ActSmartPanelSettingVM) this.mViewModel).isNeedUpgrade()) {
            MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.tips), getString(R.string.app_str_s8_upgrade_tips)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_accept), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda5
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view2) {
                    return ActSmartPanelSetting.lambda$initData$14(baseDialog, view2);
                }
            });
        } else {
            showUnbindDialog(i);
        }
    }

    private void showUnbindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.reset_relate));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSetting.this.lambda$showUnbindDialog$16(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnbindDialog$16(int i, Integer num) {
        unBindRelateInfo(i);
    }

    public void unBindRelateInfo(final int position) {
        int i;
        int i2;
        if (Injection.state().isConnectOuterNet()) {
            int unicastAddress = ((BleParam) ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue().getParam(BleParam.class)).getUnicastAddress();
            RelatedInfoExtParam.RelateInfo relateInfo = this.relateInfoAssistant.getRelateInfo(position);
            if (relateInfo != null) {
                if (relateInfo.isRelateGroupInfo()) {
                    if (this.groupList.getValue() != null) {
                        i2 = 0;
                        for (Group group : this.groupList.getValue()) {
                            if (group.getGroupId() == relateInfo.objectId) {
                                i2 = ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType());
                                group.getGroupAddress();
                            }
                        }
                        i = i2;
                    }
                } else if (!relateInfo.isRelateDeviceInfo()) {
                    i = 2;
                } else if (this.deviceList.getValue() != null) {
                    i2 = 0;
                    for (Device device : this.deviceList.getValue()) {
                        if (device.getDeviceId() == relateInfo.objectId) {
                            i2 = ProductRepository.getAgreementIdByPid(device.getProductId());
                            ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
                        }
                    }
                    i = i2;
                }
                showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
                CmdAssistant.getSettingCmdAssistant(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), unicastAddress, 1 << position, i, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda19
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSmartPanelSetting.this.lambda$unBindRelateInfo$17(position, (Boolean) obj);
                    }
                });
                return;
            }
            i = 0;
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.unsubscribing));
            CmdAssistant.getSettingCmdAssistant(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).unSubscribeInSwitchPanel(ActivityUtils.getTopActivity(), unicastAddress, 1 << position, i, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda19
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSmartPanelSetting.this.lambda$unBindRelateInfo$17(position, (Boolean) obj);
                }
            });
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindRelateInfo$17(int i, Boolean bool) {
        if (bool.booleanValue()) {
            this.relateInfoAssistant.resetSmartPanelRelateInfo(this.productId, i);
            uploadData(i);
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.BIND, null);
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, null);
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.K_VALUE, null);
            ReplaceHelper.instance().backupIndexData(this, ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), i + 1);
            return;
        }
        dismissLoadingDialog();
        ViewHelpUtil.showUnBindFailDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    private void uploadData(final int position) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSetting.this.lambda$uploadData$18((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelSetting$$ExternalSyntheticLambda14(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSetting.this.lambda$uploadData$19(position, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$18(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadData$19(int i, Object obj) throws Exception {
        ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue().setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue());
        ((ActSmartPanelSettingVM) this.mViewModel).showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        refreshRelateInfoList(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue());
        this.keyAdapter.notifyItemChanged(i);
    }

    private void uploadDataS8(final int position) {
        final Device value = ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(value.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda21
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSetting.this.lambda$uploadDataS8$20((Disposable) obj);
            }
        }).doFinally(new ActSmartPanelSetting$$ExternalSyntheticLambda14(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda23
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSetting.this.lambda$uploadDataS8$21(value, position, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDataS8$20(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDataS8$21(Device device, int i, Object obj) throws Exception {
        device.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(device);
        ((ActSmartPanelSettingVM) this.mViewModel).showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        refreshRelateInfoList(((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue());
        this.keyAdapter.notifyItemChanged(i);
    }

    public void refreshRelateInfoList(Device device) {
        String string;
        String string2;
        RelateInfoUtils.initRelateInfoList(device);
        this.relateInfoAssistant = RelateInfoUtils.relateInfoAssistant;
        this.relatedInfoList.clear();
        this.relatedInfoList.addAll(RelateInfoUtils.relatedInfoList);
        ((ActSmartPanelSettingVM) this.mViewModel).isShowBindKRange.setValue(Boolean.valueOf(this.relateInfoAssistant.isShowKRange()));
        AppCompatTextView appCompatTextView = ((ActSmartPanelSettingBinding) this.mViewBinding).tvElderlyModeTip;
        if (this.relateInfoAssistant.getSwitchScreenBigIcon() == 2) {
            string = getString(R.string.app_str_turned_on);
        } else {
            string = getString(R.string.app_str_turned_off);
        }
        appCompatTextView.setText(string);
        if (this.relateInfoAssistant.getSwitchScreenLanguage() == 0 && (ProductId.ID_SMART_SWITCH_S6_PRO.equalsIgnoreCase(device.getProductId()) || ProductId.ID_SMART_PANEL_G4.equalsIgnoreCase(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equalsIgnoreCase(device.getProductId()) || ProductId.ID_SMART_PANEL_G4TE.equalsIgnoreCase(device.getProductId()))) {
            ((ActSmartPanelSettingBinding) this.mViewBinding).tvLanguageTip.setText("");
            return;
        }
        AppCompatTextView appCompatTextView2 = ((ActSmartPanelSettingBinding) this.mViewBinding).tvLanguageTip;
        if (this.relateInfoAssistant.getSwitchScreenLanguage() == 2) {
            string2 = getString(R.string.language_english);
        } else {
            string2 = getString(R.string.language_chinese);
        }
        appCompatTextView2.setText(string2);
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
        ActSmartPanelSettingVM actSmartPanelSettingVM = (ActSmartPanelSettingVM) this.mViewModel;
        this.mSelectHour = isStart ? actSmartPanelSettingVM.getStarH() : actSmartPanelSettingVM.getEndH();
        ActSmartPanelSettingVM actSmartPanelSettingVM2 = (ActSmartPanelSettingVM) this.mViewModel;
        this.mSelectMin = isStart ? actSmartPanelSettingVM2.getStarM() : actSmartPanelSettingVM2.getEndM();
        TimeSelectDialog.asDefault().setTitle(getString(isStart ? R.string.start_time : R.string.end_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(false).setMinUnit(getString(R.string.hour)).setSecUnit(getString(R.string.min)).setSelectMinPosition(this.mSelectHour).setSelectSecPosition(this.mSelectMin).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActSmartPanelSetting.this.lambda$showTimeDialog$22(isStart, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimeDialog$22(boolean z, int i, int i2) {
        if (this.mSelectHour == i && this.mSelectMin == i2) {
            return;
        }
        this.mSelectHour = i;
        this.mSelectMin = i2;
        if (z) {
            ((ActSmartPanelSettingVM) this.mViewModel).setStarTime(this.mSelectHour, this.mSelectMin);
        } else {
            ((ActSmartPanelSettingVM) this.mViewModel).setEndTime(this.mSelectHour, this.mSelectMin);
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void showEnginnerDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.setting)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.app_str_change_device_send_times));
        arrayList.add(getString(R.string.app_str_change_device_ttl));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSetting.this.lambda$showEnginnerDialog$23((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEnginnerDialog$23(Integer num) {
        if (num.intValue() == 0) {
            showEditTimesDialog();
        } else if (num.intValue() == 1) {
            showEditTTLDialog();
        } else if (num.intValue() == 2) {
            cleanDeviceCache();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelSettingVM) this.mViewModel).controlId);
        if (deviceById != null) {
            initData(deviceById);
            if (deviceById.getProductId().equals(ProductId.ID_SMART_PANEL_S6B)) {
                initS6B();
            }
            ((ActSmartPanelSettingVM) this.mViewModel).queryScene(deviceById.getDeviceId());
            ((ActSmartPanelSettingVM) this.mViewModel).queryBackData(deviceById.getDeviceId());
        }
    }

    private void initS6B() {
        ((ActSmartPanelSettingBinding) this.mViewBinding).layoutBattery.setVisibility(0);
        ((ActSmartPanelSettingBinding) this.mViewBinding).rvKeys.setVisibility(8);
        ((ActSmartPanelSettingBinding) this.mViewBinding).tvTip.setVisibility(8);
        ((ActSmartPanelSettingBinding) this.mViewBinding).layoutCreateGroup.setVisibility(8);
        ((ActSmartPanelSettingBinding) this.mViewBinding).layoutSceneAndAutomation.setVisibility(8);
        ((ActSmartPanelSettingVM) this.mViewModel).showMore.setValue(true);
        RelateInfoAssistant relateInfoAssistant = this.relateInfoAssistant;
        if (relateInfoAssistant != null && relateInfoAssistant.getBattery() != -1) {
            if (this.relateInfoAssistant.getBattery() > 10) {
                ((ActSmartPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(this.relateInfoAssistant.getBattery() + "%");
            } else {
                ((ActSmartPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(getString(R.string.low_battery_tip));
            }
        }
        MessageManager.getInstance().setBatteryStatusCallBack(new MessageManager.BatteryStatusCallBack() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda6
            @Override // com.smart.message.MessageManager.BatteryStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActSmartPanelSetting.this.lambda$initS6B$24(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initS6B$24(ResponseMsg responseMsg) {
        if (this.mViewBinding != 0) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(12, 14), 16);
            if (parseInt > 10) {
                ((ActSmartPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(parseInt + "%");
            } else {
                ((ActSmartPanelSettingBinding) this.mViewBinding).tvBatteryTip.setText(getString(R.string.low_battery_tip));
            }
            getMainHandler().removeCallbacks(this.getBatteryRunnable);
            ((ActSmartPanelSettingVM) this.mViewModel).uploadData(parseInt);
            if (this.searching) {
                SmartToast.showCenterShort(getResources().getString(R.string.search_success));
                if (this.dialog != null) {
                    this.dialog.dismissDialog();
                }
                this.searching = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditKeyNameDialog(String name, final int position) {
        EditDialog.asDefault().setContent(name).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction<String>() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting.19
            @Override // com.ltech.smarthome.base.IAction
            public void act(String s) {
                ActSmartPanelSetting.this.changeKeyName(s, position);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeKeyName(String s, int position) {
        RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
        relateInfo.name = s;
        if (this.relateInfoAssistant.getRelateInfo(position) != null) {
            relateInfo.type = this.relateInfoAssistant.getRelateInfo(position).type;
            relateInfo.action = this.relateInfoAssistant.getRelateInfo(position).action;
            relateInfo.objectId = this.relateInfoAssistant.getRelateInfo(position).objectId;
            relateInfo.bindingZone = this.relateInfoAssistant.getRelateInfo(position).bindingZone;
        }
        this.relateInfoAssistant.setRelateInfo(position, relateInfo);
        uploadDeviceData(s, position);
    }

    private void uploadDeviceData(final String s, final int position) {
        final Device value = ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue();
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(value.getDeviceId(), this.relateInfoAssistant.getExtParamString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSetting.this.lambda$uploadDeviceData$25((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSetting.this.lambda$uploadDeviceData$26(value, position, s, obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSmartPanelSetting.this.lambda$uploadDeviceData$27((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$25(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$26(Device device, int i, String str, Object obj) throws Exception {
        device.setExtParam(this.relateInfoAssistant.getExtParamString());
        Injection.repo().device().saveDevice(device);
        ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.setValue(device);
        showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        this.keyAdapter.getData().get(i).infoName = str;
        this.keyAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceData$27(Throwable th) throws Exception {
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    private void showNightBindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.local_scene));
        arrayList.add(getString(R.string.dali_scene));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSetting.this.lambda$showNightBindDialog$28(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNightBindDialog$28(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelSettingVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.GROUP_CONTROL, false).withBoolean(Constants.IS_NIGHT_UP, true).withDefaultRequestCode().navigation(this);
        } else {
            if (intValue != 1) {
                return;
            }
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelSettingVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, false).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.IS_NIGHT_UP, true).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBindDialog(final int position) {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.device));
        arrayList.add(getString(R.string.local_scene));
        arrayList.add(getString(R.string.dali_scene));
        arrayList.add(getString(R.string.link_automation));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting$$ExternalSyntheticLambda22
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSetting.this.lambda$showBindDialog$29(position, (Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBindDialog$29(int i, Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSmartPanelSelectBleDeviceAndGroupNew.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelSettingVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, i).withString(Constants.PRODUCT_ID, this.productId).withBoolean(Constants.GROUP_CONTROL, false).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, this.controlType).withDefaultRequestCode().navigation(this);
            return;
        }
        if (intValue == 1) {
            NavUtils.destination(ActSmartPanelSelectScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelSettingVM) this.mViewModel).controlId).withInt(Constants.RELATED_POSITION, i).withBoolean(Constants.GROUP_CONTROL, false).withDefaultRequestCode().navigation(this);
        } else if (intValue == 2) {
            NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActSmartPanelSettingVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, false).withInt(Constants.RELATED_POSITION, i).withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        } else {
            if (intValue != 3) {
                return;
            }
            NavUtils.destination(ActAddAutomation.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.RELATED_POSITION, i).withLong(Constants.CONTROL_ID, ((ActSmartPanelSettingVM) this.mViewModel).controlId).navigation(this);
        }
    }

    private void showBackSettingData(JSONObject jsonObject) {
        showData(jsonObject, UpdateBackDataRequest.POWER_STATUS);
        showData(jsonObject, UpdateBackDataRequest.NIGHT_MODE);
    }

    private void showData(JSONObject jsonObject, String key) {
        int[] parseBackupData = ReplaceHelper.instance().parseBackupData(jsonObject, key, ((ActSmartPanelSettingVM) this.mViewModel).controlDevice.getValue());
        ((ActSmartPanelSettingVM) this.mViewModel).state = new SmartPanelSettingState();
        key.hashCode();
        if (key.equals(UpdateBackDataRequest.NIGHT_MODE)) {
            ((ActSmartPanelSettingVM) this.mViewModel).state.setNightMode(parseBackupData[0]);
            ((ActSmartPanelSettingVM) this.mViewModel).state.setStartH(parseBackupData[1]);
            ((ActSmartPanelSettingVM) this.mViewModel).state.setStartM(parseBackupData[2]);
            ((ActSmartPanelSettingVM) this.mViewModel).state.setEndH(parseBackupData[3]);
            ((ActSmartPanelSettingVM) this.mViewModel).state.setEndM(parseBackupData[4]);
            ((ActSmartPanelSettingVM) this.mViewModel).state.setEngravedTextMode(parseBackupData[5]);
        } else if (key.equals(UpdateBackDataRequest.POWER_STATUS)) {
            ((ActSmartPanelSettingVM) this.mViewModel).memorizePowerOff.setValue(Boolean.valueOf(parseBackupData[0] == 3));
        }
        ((ActSmartPanelSettingVM) this.mViewModel).showStateData();
    }
}