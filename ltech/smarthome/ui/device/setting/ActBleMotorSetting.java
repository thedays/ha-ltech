package com.ltech.smarthome.ui.device.setting;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActBleMotorSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSetting;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleMotorModeSet;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainRepository;
import com.ltech.smarthome.ui.device.setting.ActBleMotorSettingVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActBleMotorSetting extends BaseDeviceSetActivity<ActBleMotorSettingBinding, ActBleMotorSettingVM> {
    int currentDirection = 0;
    protected List<ActBleMotorSettingVM.ModeInfoItem> dataList = new ArrayList();
    protected BaseQuickAdapter<ActBleMotorSettingVM.ModeInfoItem, BaseViewHolder> modeAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ble_motor_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setEditString("             ");
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActBleMotorSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActBleMotorSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActBleMotorSettingVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        ((ActBleMotorSettingVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActBleMotorSettingVM) this.mViewModel).samePlace = getIntent().getBooleanExtra(Constants.SAME_PLACE, true);
        Injection.repo().device().getDeviceFromDb(((ActBleMotorSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSetting$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotorSetting.this.lambda$startObserve$0((Device) obj);
            }
        });
        ((ActBleMotorSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSetting$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotorSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActBleMotorSettingVM) this.mViewModel).showChangeMotorDirectionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSetting$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotorSetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActBleMotorSettingVM) this.mViewModel).showMoreSettingDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActBleMotorSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActBleMotorSettingVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        LHomeLog.i("Test", getClass(), "mViewModel.controlDevice enter");
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActBleMotorSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActBleMotorSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
        initDataList(device);
        initData();
        if (device.getProductId().equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
            if ((((ActBleMotorSettingVM) this.mViewModel).getCurrentPlace().isManager() || ((ActBleMotorSettingVM) this.mViewModel).getCurrentPlace().isOwner()) && ((ActBleMotorSettingVM) this.mViewModel).isFirstGetMotorVersion.booleanValue()) {
                ((ActBleMotorSettingBinding) this.mViewBinding).layoutMotorVersion.setVisibility(0);
                ((ActBleMotorSettingVM) this.mViewModel).motorCheckVersion();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showTipDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r4) {
        NavUtils.destination(ActBleCurtainMotorMoreSetting.class).withLong(Constants.CONTROL_ID, ((ActBleMotorSettingVM) this.mViewModel).controlId).navigation(this);
    }

    private void initDataList(Device device) {
        if (CurtainRepository.getModeList() == null || CurtainRepository.getModeList().isEmpty()) {
            return;
        }
        this.dataList.clear();
        for (int i = 0; i < CurtainRepository.getModeList().size(); i++) {
            ActBleMotorSettingVM.ModeInfoItem modeInfoItem = new ActBleMotorSettingVM.ModeInfoItem();
            if (device.getDeviceState().getCurtainMotorState().getModeInfoList().get(i) != null) {
                modeInfoItem.modeIcon = IconRepository.getSceneIcons(this)[device.getDeviceState().getCurtainMotorState().getModeInfoList().get(i).getModeIconNum()];
                modeInfoItem.iconIndex = device.getDeviceState().getCurtainMotorState().getModeInfoList().get(i).getModeIconNum();
            } else {
                modeInfoItem.modeIcon = IconRepository.getSceneIcons(this)[CurtainRepository.getModeList().get(i).getIconIndex()];
                modeInfoItem.iconIndex = CurtainRepository.getModeList().get(i).getIconIndex();
            }
            modeInfoItem.modeName = CurtainRepository.getModeList().get(i).getName();
            int modePositionPercent = device.getDeviceState().getCurtainMotorState() != null ? device.getDeviceState().getCurtainMotorState().getModeInfoList().get(i).getModePositionPercent() : 0;
            modeInfoItem.actionString = "";
            if (modePositionPercent == 100) {
                modeInfoItem.actionString = getString(R.string.app_str_all_close);
            } else if (modePositionPercent == 0) {
                modeInfoItem.actionString = getString(R.string.app_str_all_open);
            } else {
                modeInfoItem.actionString = getString(R.string.app_str_curtain_open) + (100 - modePositionPercent) + "%";
            }
            this.dataList.add(modeInfoItem);
        }
    }

    private void showTipDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_change_ble_motor_tips)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSetting$$ExternalSyntheticLambda5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showTipDialog$5;
                lambda$showTipDialog$5 = ActBleMotorSetting.this.lambda$showTipDialog$5(baseDialog, view);
                return lambda$showTipDialog$5;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showTipDialog$5(BaseDialog baseDialog, View view) {
        if (((ActBleMotorSettingVM) this.mViewModel).controlDevice.getValue() != null) {
            this.currentDirection = ((ActBleMotorSettingVM) this.mViewModel).controlDevice.getValue().getDeviceState().getCurtainMotorState().getMotorDirection();
        }
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        final int i = this.currentDirection == 0 ? 1 : 0;
        CmdAssistant.getSettingCmdAssistant(((ActBleMotorSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).changeCurtainMotorDirection(this, this.currentDirection != 0 ? 0 : 1, new IAction() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActBleMotorSetting.this.lambda$showTipDialog$4(i, (Boolean) obj);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTipDialog$4(int i, Boolean bool) {
        dismissLoadingDialog();
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this.activity, ((ActBleMotorSettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.CURTAIN_SETTING, CmdAssistant.getSettingCmdAssistant(((ActBleMotorSettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).changeCurtainMotorDirection(this.currentDirection == 0 ? 1 : 0));
            ((ActBleMotorSettingVM) this.mViewModel).controlDevice.getValue().getDeviceState().getCurtainMotorState().setMotorDirection(i);
            this.currentDirection = i;
            SmartToast.showShort(getString(R.string.app_str_setting_success));
            return;
        }
        SmartToast.showShort(getString(R.string.app_str_setting_failed));
    }

    private void initData() {
        BaseQuickAdapter<ActBleMotorSettingVM.ModeInfoItem, BaseViewHolder> baseQuickAdapter = this.modeAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<ActBleMotorSettingVM.ModeInfoItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<ActBleMotorSettingVM.ModeInfoItem, BaseViewHolder>(R.layout.item_smart_panel_key_set, this.dataList) { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSetting.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, ActBleMotorSettingVM.ModeInfoItem item) {
                    helper.setText(R.id.tv_main, item.modeName);
                    helper.setText(R.id.tv_sub_text, item.actionString);
                    helper.setGone(R.id.iv_go, ((ActBleMotorSettingVM) ActBleMotorSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActBleMotorSettingVM) ActBleMotorSetting.this.mViewModel).getCurrentPlace().isManager());
                    helper.itemView.setEnabled(((ActBleMotorSettingVM) ActBleMotorSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActBleMotorSettingVM) ActBleMotorSetting.this.mViewModel).getCurrentPlace().isManager());
                }
            };
            this.modeAdapter = baseQuickAdapter2;
            baseQuickAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.setting.ActBleMotorSetting$$ExternalSyntheticLambda0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter3, View view, int i) {
                    ActBleMotorSetting.this.lambda$initData$6(baseQuickAdapter3, view, i);
                }
            });
            this.modeAdapter.bindToRecyclerView(((ActBleMotorSettingBinding) this.mViewBinding).rvModes);
            ((ActBleMotorSettingBinding) this.mViewBinding).rvModes.setLayoutManager(new LinearLayoutManager(this));
        } else {
            baseQuickAdapter.setNewData(this.dataList);
        }
        this.modeAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$6(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NavUtils.destination(ActBleMotorModeSet.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withLong(Constants.CONTROL_ID, ((ActBleMotorSettingVM) this.mViewModel).controlId).withInt(Constants.ICON_POSITION, this.modeAdapter.getData().get(i).iconIndex).withInt(Constants.MODE_POSITION, i).withString(Constants.MODE_NAME, this.modeAdapter.getData().get(i).modeName).withString(Constants.PRODUCT_ID, ((ActBleMotorSettingVM) this.mViewModel).productId).withDefaultRequestCode().navigation(this);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3005) {
            ((ActBleMotorSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActBleMotorSettingVM) this.mViewModel).controlId));
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity
    protected void showEnginnerDialog() {
        showEditTTLDialog();
    }
}