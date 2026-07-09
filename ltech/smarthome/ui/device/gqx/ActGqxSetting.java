package com.ltech.smarthome.ui.device.gqx;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActGqxSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.gqx.ActGqxSetting;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SingleSelectItemDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGqxSetting extends BaseDeviceSetActivity<ActGqxSettingBinding, ActGqxSettingVM> {
    protected List<RelateInfoItem> dataList;
    public long placeId;
    public String productId;
    public Listing<Group> request;
    public List<RelateInfoItem> relatedInfoList = new ArrayList();
    public MediatorLiveData<List<Device>> deviceList = new MediatorLiveData<>();
    public MediatorLiveData<List<Group>> groupList = new MediatorLiveData<>();
    public MediatorLiveData<List<Scene>> sceneList = new MediatorLiveData<>();
    private boolean searching = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_gqx_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
        ((ActGqxSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.request = Injection.repo().group().getGroupList(this, this.placeId);
        ((ActGqxSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActGqxSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActGqxSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActGqxSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda0
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActGqxSetting.this.lambda$initView$0(refreshLayout);
            }
        });
        ((ActGqxSettingBinding) this.mViewBinding).sbVolumePowerOnTip.setOnCheckedChangeListener(new AnonymousClass1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((ActGqxSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            ((ActGqxSettingVM) this.mViewModel).isFirst = true;
            ((ActGqxSettingVM) this.mViewModel).loadDeviceStatus(((ActGqxSettingVM) this.mViewModel).controlDevice.getValue());
            ((ActGqxSettingVM) this.mViewModel).queryScene(((ActGqxSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId());
        }
        ((ActGqxSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* renamed from: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$1, reason: invalid class name */
    class AnonymousClass1 implements SwitchButton.OnCheckedChangeListener {
        AnonymousClass1() {
        }

        @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
        public void onCheckedChanged(SwitchButton view, final boolean isChecked) {
            ActGqxSetting.this.dialog = ImageTipDialog.asDefault().setTitle(ActGqxSetting.this.getString(R.string.gqx_click_tip)).setConfirmString(ActGqxSetting.this.getString(R.string.get_it)).setImageRes(ProductId.ID_SMART_PANEL_GQ.equals(ActGqxSetting.this.productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActGqxSetting.AnonymousClass1.this.lambda$onCheckedChanged$0(isChecked, imageTipDialog);
                }
            }).setCancelCallback(new ImageTipDialog.OnCancelCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting.1.1
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnCancelCallback
                public void onCancel() {
                    ((ActGqxSettingBinding) ActGqxSetting.this.mViewBinding).sbVolumePowerOnTip.setCheckedNotByUser(!isChecked);
                }
            });
            ActGqxSetting.this.dialog.showDialog(ActGqxSetting.this.activity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCheckedChanged$0(final boolean z, final ImageTipDialog imageTipDialog) {
            ((ActGqxSettingVM) ActGqxSetting.this.mViewModel).setBuzzerEnable(z, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting.1.2
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    if (!aBoolean.booleanValue()) {
                        ((ActGqxSettingBinding) ActGqxSetting.this.mViewBinding).sbVolumePowerOnTip.setCheckedNotByUser(!z);
                    }
                    imageTipDialog.dismissDialog();
                }
            });
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        Device deviceById = Injection.repo().device().getDeviceById(((ActGqxSettingVM) this.mViewModel).controlId);
        ((ActGqxSettingVM) this.mViewModel).controlDevice.setValue(deviceById);
        List<Group> groupListByPlaceId = Injection.repo().group().getGroupListByPlaceId(this.placeId);
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(this.placeId);
        List<Scene> sceneListByPlaceId = Injection.repo().scene().getSceneListByPlaceId(this.placeId, true);
        this.deviceList.setValue(deviceListByPlaceId);
        this.groupList.setValue(groupListByPlaceId);
        this.sceneList.setValue(sceneListByPlaceId);
        ((ActGqxSettingVM) this.mViewModel).roomPickHelper.startObserve(this, deviceById.getPlaceId(), deviceById.getRoomId());
        ((ActGqxSettingVM) this.mViewModel).loadDeviceStatus(deviceById);
        ((ActGqxSettingVM) this.mViewModel).refreshRelateInfoList();
        ((ActGqxSettingVM) this.mViewModel).controlDevice.observe(this, new Observer<Device>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Device device) {
                if (device != null) {
                    ((ActGqxSettingBinding) ActGqxSetting.this.mViewBinding).tvRoomName.setText(String.format("%s %s", device.getFloorName(), device.getRoomName()));
                }
            }
        });
        ((ActGqxSettingVM) this.mViewModel).adjustKRange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActGqxSetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActGqxSettingVM) this.mViewModel).getBatteryEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActGqxSetting.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActGqxSettingVM) this.mViewModel).SensitivitySelectedEvent.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                ActGqxSetting.this.showSensitivityDialog(integer.intValue());
            }
        });
        ((ActGqxSettingVM) this.mViewModel).batteryNum.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (integer.intValue() > 10) {
                    ((ActGqxSettingBinding) ActGqxSetting.this.mViewBinding).tvBatteryTip.setText(integer + "%");
                    return;
                }
                ((ActGqxSettingBinding) ActGqxSetting.this.mViewBinding).tvBatteryTip.setText(ActGqxSetting.this.getString(R.string.gqx_low_battery_tip));
            }
        });
        ((ActGqxSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActGqxSetting.this.lambda$startObserve$5((Integer) obj);
            }
        });
        ((ActGqxSettingVM) this.mViewModel).queryBuzzerState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r3) {
        RelateInfoUtils.showImageTipDialog(getString(R.string.gqx_click_tip), ProductId.ID_SMART_PANEL_GQ.equals(this.productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActGqxSetting.this.lambda$startObserve$1(imageTipDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(ImageTipDialog imageTipDialog) {
        ((ActGqxSettingVM) this.mViewModel).clickAdjustKRange(this, false);
        imageTipDialog.dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r3) {
        this.dialog = ImageTipDialog.asDefault().setTitle(getString(R.string.gqx_click_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(ProductId.ID_SMART_PANEL_GQ.equals(this.productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActGqxSetting.this.lambda$startObserve$3(imageTipDialog);
            }
        });
        this.dialog.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(ImageTipDialog imageTipDialog) {
        this.searching = true;
        CmdAssistant.getQueryCmdAssistant(((ActGqxSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryPanelState(this);
        getMainHandler().postDelayed(this.getBatteryRunnable, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Integer num) {
        ((ActGqxSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    private void initData() {
        MessageManager.getInstance().setBatteryStatusCallBack(new MessageManager.BatteryStatusCallBack() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda9
            @Override // com.smart.message.MessageManager.BatteryStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActGqxSetting.this.lambda$initData$6(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$6(ResponseMsg responseMsg) {
        if (this.mViewBinding != 0) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(12, 14), 16);
            if (parseInt > 10) {
                ((ActGqxSettingBinding) this.mViewBinding).tvBatteryTip.setText(parseInt + "%");
            } else {
                ((ActGqxSettingBinding) this.mViewBinding).tvBatteryTip.setText(getString(R.string.gqx_low_battery_tip));
            }
            getMainHandler().removeCallbacks(this.getBatteryRunnable);
            ((ActGqxSettingVM) this.mViewModel).uploadData(parseInt);
            if (this.searching) {
                SmartToast.showCenterShort(getResources().getString(R.string.search_success));
                if (this.dialog != null) {
                    this.dialog.dismissDialog();
                }
                this.searching = false;
            }
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void showEnginnerDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.setting)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add(getString(R.string.app_str_change_device_send_times));
        arrayList.add(getString(R.string.app_str_change_device_ttl));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGqxSetting.this.lambda$showEnginnerDialog$7((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.setOutCancel(false);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEnginnerDialog$7(Integer num) {
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
        Device deviceById = Injection.repo().device().getDeviceById(((ActGqxSettingVM) this.mViewModel).controlId);
        initData();
        if (deviceById != null) {
            ((ActGqxSettingVM) this.mViewModel).queryScene(deviceById.getDeviceId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSensitivityDialog(final int keyPos) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new SingleSelectItemDialog.ListItem(getString(R.string.gqx_setting_low_sensitivity), getString(R.string.gqx_setting_low_sensitivity_tip)));
        arrayList.add(new SingleSelectItemDialog.ListItem(getString(R.string.gqx_setting_mid_sensitivity), getString(R.string.gqx_setting_mid_sensitivity_tip)));
        arrayList.add(new SingleSelectItemDialog.ListItem(getString(R.string.gqx_setting_high_sensitivity), getString(R.string.gqx_setting_high_sensitivity_tip)));
        SingleSelectItemDialog.asDefault().setItemLayout(R.layout.item_select2).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectList(arrayList).setTitle(keyPos != 1 ? keyPos != 2 ? keyPos != 3 ? String.format("%s-%s", getString(R.string.gqx_setting_sensitivity), getString(R.string.gqx_main_b1)) : String.format("%s-%s", getString(R.string.gqx_setting_sensitivity), getString(R.string.gqx_main_b4)) : String.format("%s-%s", getString(R.string.gqx_setting_sensitivity), getString(R.string.gqx_main_b3)) : String.format("%s-%s", getString(R.string.gqx_setting_sensitivity), getString(R.string.gqx_main_b2))).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActGqxSetting.this.lambda$showSensitivityDialog$9(keyPos, (Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSensitivityDialog$9(final int i, final Integer num) {
        this.dialog = ImageTipDialog.asDefault().setTitle(getString(R.string.gqx_click_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(ProductId.ID_SMART_PANEL_GQ.equals(this.productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActGqxSetting.this.lambda$showSensitivityDialog$8(i, num, imageTipDialog);
            }
        });
        this.dialog.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSensitivityDialog$8(int i, Integer num, ImageTipDialog imageTipDialog) {
        imageTipDialog.dismissDialog();
        ((ActGqxSettingVM) this.mViewModel).setSensitivity(i, num.intValue() + 1, new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.device.gqx.ActGqxSetting.5
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
            }
        });
    }
}