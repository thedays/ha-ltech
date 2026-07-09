package com.ltech.smarthome.ui.device.dalipro;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActCgdProLightSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.CenterSelectListDialog;
import com.ltech.smarthome.view.dialog.CenterTipDialog;
import com.ltech.smarthome.view.dialog.DaliDetectDialog;
import com.ltech.smarthome.view.dialog.DaliDetectTipDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActCgdProLightSetting extends BaseDeviceSetActivity<ActCgdProLightSettingBinding, ActCgdProLightSettingVM> {
    private int allDeviceCount;
    private DaliDetectDialog daliDetectDialog;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_cgd_pro_light_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActCgdProLightSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActCgdProLightSettingVM) this.mViewModel).samePlace = getIntent().getBooleanExtra(Constants.SAME_PLACE, true);
        ((ActCgdProLightSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        if (((ActCgdProLightSettingVM) this.mViewModel).samePlace) {
            Injection.repo().device().getDeviceFromDb(((ActCgdProLightSettingVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ActCgdProLightSetting.this.lambda$startObserve$0((Device) obj);
                }
            });
        } else {
            ((ActCgdProLightSettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDevice(((ActCgdProLightSettingVM) this.mViewModel).placeId, ((ActCgdProLightSettingVM) this.mViewModel).deviceId));
        }
        ((ActCgdProLightSettingVM) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCgdProLightSetting.this.lambda$startObserve$1((Device) obj);
            }
        });
        ((ActCgdProLightSettingVM) this.mViewModel).showBatchSetEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCgdProLightSetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActCgdProLightSettingVM) this.mViewModel).showSearchAddressEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCgdProLightSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        ((ActCgdProLightSettingVM) this.mViewModel).controlDevice.setValue(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Device device) {
        String floorName;
        ((ActCgdProLightSettingVM) this.mViewModel).deviceId = device.getDeviceId();
        Floor floor = Injection.repo().home().getFloor(device.getFloorId());
        Room room = Injection.repo().home().getRoom(device.getRoomId());
        AppCompatTextView appCompatTextView = ((ActCgdProLightSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActCgdProLightSettingVM) this.mViewModel).roomPickHelper.startObserve(this, device.getPlaceId(), device.getRoomId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r4) {
        NavUtils.destination(ActDaliLightBatchManage.class).withLong(Constants.PLACE_ID, ((ActCgdProLightSettingVM) this.mViewModel).placeId).withLong("device_id", ((ActCgdProLightSettingVM) this.mViewModel).deviceId).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showSearchAddressDialog();
    }

    private void showSearchAddressDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.reserve_device_data));
        arrayList.add(getString(R.string.reset_device_data));
        CenterSelectListDialog.asDefault(true).setConfirmString(getString(R.string.app_str_start_search)).setTitle(getString(R.string.app_str_search_address)).setCancelString(getString(R.string.cancel)).setSelectList(arrayList).setSelectPosition(0).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActCgdProLightSetting.this.lambda$showSearchAddressDialog$5((Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSearchAddressDialog$5(Integer num) {
        if (num.intValue() == 0) {
            getDaliData(false);
        } else {
            CenterTipDialog.asDefault().setTitle(getString(R.string.sure_reset_device_data)).setMessageString(getString(R.string.reset_device_data_message)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setOnConfirmListener(new CenterTipDialog.OnConfirmListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.dialog.CenterTipDialog.OnConfirmListener
                public final void onConfirm() {
                    ActCgdProLightSetting.this.lambda$showSearchAddressDialog$4();
                }
            }).showDialog(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSearchAddressDialog$4() {
        getDaliData(true);
    }

    private void getDaliData(boolean isClean) {
        final DaliDetectTipDialog onCreateDetectDialogListener = DaliDetectTipDialog.asDefault().setOnCreateDetectDialogListener(new DaliDetectTipDialog.OnCreateDetectDialogListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.DaliDetectTipDialog.OnCreateDetectDialogListener
            public final void onCreateDetectDialog(DaliDetectDialog daliDetectDialog) {
                ActCgdProLightSetting.this.lambda$getDaliData$6(daliDetectDialog);
            }
        });
        onCreateDetectDialogListener.showDialog(this);
        ((ActCgdProLightSettingVM) this.mViewModel).initData(Boolean.valueOf(isClean), new ActCgdProLightSettingVM.OnCgpProGetDataListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting.1
            @Override // com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM.OnCgpProGetDataListener
            public void onGetFirstFrameData() {
                onCreateDetectDialogListener.toDetectDialog();
            }

            @Override // com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM.OnCgpProGetDataListener
            public void onDismissDialog() {
                onCreateDetectDialogListener.dismissDialog();
                if (ActCgdProLightSetting.this.daliDetectDialog != null) {
                    ActCgdProLightSetting.this.daliDetectDialog.dismissDialog();
                }
            }

            @Override // com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM.OnCgpProGetDataListener
            public void onGetAllData() {
                if (ActCgdProLightSetting.this.daliDetectDialog != null) {
                    ActCgdProLightSetting.this.daliDetectDialog.dismissDialog();
                }
                CenterTipDialog asDefault = CenterTipDialog.asDefault();
                ActCgdProLightSetting actCgdProLightSetting = ActCgdProLightSetting.this;
                asDefault.setTitle(actCgdProLightSetting.getString(R.string.success_detect_count, new Object[]{String.valueOf(actCgdProLightSetting.allDeviceCount)})).setCancelString(ActCgdProLightSetting.this.getString(R.string.app_str_guide_know)).showDialog(ActCgdProLightSetting.this);
            }

            @Override // com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM.OnCgpProGetDataListener
            public void updateProgress(int progress) {
                ActCgdProLightSetting.this.allDeviceCount = progress;
                onCreateDetectDialogListener.setProgress(progress);
                if (ActCgdProLightSetting.this.daliDetectDialog != null) {
                    ActCgdProLightSetting.this.daliDetectDialog.setProgress(progress);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDaliData$6(DaliDetectDialog daliDetectDialog) {
        this.daliDetectDialog = daliDetectDialog;
    }
}