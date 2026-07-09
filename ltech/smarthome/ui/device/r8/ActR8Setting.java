package com.ltech.smarthome.ui.device.r8;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActR8SettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActR8Setting extends BaseDeviceSetActivity<ActR8SettingBinding, ActR8SettingVM> {
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
        return R.layout.act_r8_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
        ((ActR8SettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.request = Injection.repo().group().getGroupList(this, this.placeId);
        ((ActR8SettingBinding) this.mViewBinding).layoutVolume.setVisibility(8);
        ((ActR8SettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActR8SettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActR8SettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActR8SettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting$$ExternalSyntheticLambda7
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActR8Setting.this.lambda$initView$0(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((ActR8SettingVM) this.mViewModel).controlDevice.getValue() != null) {
            ((ActR8SettingVM) this.mViewModel).isFirst = true;
            ((ActR8SettingVM) this.mViewModel).loadDeviceStatus(((ActR8SettingVM) this.mViewModel).controlDevice.getValue());
            ((ActR8SettingVM) this.mViewModel).queryScene(((ActR8SettingVM) this.mViewModel).controlDevice.getValue().getDeviceId());
        }
        ((ActR8SettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        Device deviceById = Injection.repo().device().getDeviceById(((ActR8SettingVM) this.mViewModel).controlId);
        ((ActR8SettingVM) this.mViewModel).controlDevice.setValue(deviceById);
        List<Group> groupListByPlaceId = Injection.repo().group().getGroupListByPlaceId(this.placeId);
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(this.placeId);
        List<Scene> sceneListByPlaceId = Injection.repo().scene().getSceneListByPlaceId(this.placeId, true);
        this.deviceList.setValue(deviceListByPlaceId);
        this.groupList.setValue(groupListByPlaceId);
        this.sceneList.setValue(sceneListByPlaceId);
        ((ActR8SettingVM) this.mViewModel).roomPickHelper.startObserve(this, deviceById.getPlaceId(), deviceById.getRoomId());
        ((ActR8SettingVM) this.mViewModel).loadDeviceStatus(deviceById);
        ((ActR8SettingVM) this.mViewModel).refreshRelateInfoList();
        ((ActR8SettingVM) this.mViewModel).controlDevice.observe(this, new Observer<Device>() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Device device) {
                if (device != null) {
                    ((ActR8SettingBinding) ActR8Setting.this.mViewBinding).tvRoomName.setText(String.format("%s %s", device.getFloorName(), device.getRoomName()));
                }
            }
        });
        ((ActR8SettingVM) this.mViewModel).adjustKRange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActR8Setting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActR8SettingVM) this.mViewModel).getBatteryEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActR8Setting.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActR8SettingVM) this.mViewModel).batteryNum.observe(this, new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Integer integer) {
                if (integer.intValue() > 10) {
                    ((ActR8SettingBinding) ActR8Setting.this.mViewBinding).tvBatteryTip.setText(integer + "%");
                    return;
                }
                ((ActR8SettingBinding) ActR8Setting.this.mViewBinding).tvBatteryTip.setText(ActR8Setting.this.getString(R.string.gqx_low_battery_tip));
            }
        });
        ((ActR8SettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActR8Setting.this.lambda$startObserve$5((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r3) {
        RelateInfoUtils.showImageTipDialog(getString(R.string.gqx_click_tip), ProductId.ID_SMART_PANEL_GQ.equals(this.productId) ? R.mipmap.gq_pic_click1 : R.mipmap.gqx_pic_click1, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActR8Setting.this.lambda$startObserve$1(imageTipDialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(ImageTipDialog imageTipDialog) {
        ((ActR8SettingVM) this.mViewModel).clickAdjustKRange(this, false);
        imageTipDialog.dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r2) {
        this.dialog = ImageTipDialog.asDefault().setTitle(getString(R.string.app_str_rc4s_activate_tip)).setConfirmString(getString(R.string.get_it)).setImageRes(R.mipmap.rc4sble_2).setCallback(new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
            public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                ActR8Setting.this.lambda$startObserve$3(imageTipDialog);
            }
        });
        this.dialog.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(ImageTipDialog imageTipDialog) {
        this.searching = true;
        CmdAssistant.getQueryCmdAssistant(((ActR8SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).queryPanelState(this);
        getMainHandler().postDelayed(this.getBatteryRunnable, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Integer num) {
        ((ActR8SettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    private void initData() {
        MessageManager.getInstance().setBatteryStatusCallBack(new MessageManager.BatteryStatusCallBack() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting$$ExternalSyntheticLambda6
            @Override // com.smart.message.MessageManager.BatteryStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActR8Setting.this.lambda$initData$6(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$6(ResponseMsg responseMsg) {
        if (this.mViewBinding != 0) {
            int parseInt = Integer.parseInt(responseMsg.getResData().substring(12, 14), 16);
            if (parseInt > 10) {
                ((ActR8SettingBinding) this.mViewBinding).tvBatteryTip.setText(parseInt + "%");
            } else {
                ((ActR8SettingBinding) this.mViewBinding).tvBatteryTip.setText(getString(R.string.gqx_low_battery_tip));
            }
            getMainHandler().removeCallbacks(this.getBatteryRunnable);
            ((ActR8SettingVM) this.mViewModel).uploadData(parseInt);
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
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.r8.ActR8Setting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActR8Setting.this.lambda$showEnginnerDialog$7((Integer) obj);
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
        Device deviceById = Injection.repo().device().getDeviceById(((ActR8SettingVM) this.mViewModel).controlId);
        initData();
        if (deviceById != null) {
            ((ActR8SettingVM) this.mViewModel).queryScene(deviceById.getDeviceId());
        }
    }
}