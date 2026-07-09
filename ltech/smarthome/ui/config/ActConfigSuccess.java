package com.ltech.smarthome.ui.config;

import android.view.View;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActConfigSuccessBinding;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;

/* loaded from: classes4.dex */
public class ActConfigSuccess extends VMActivity<ActConfigSuccessBinding, ActConfigSuccessVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_config_success;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        ((ActConfigSuccessVM) this.mViewModel).deviceName.postValue(ConfigHelper.instance().productInfo.getAddName(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        showEditNameDialog(((ActConfigSuccessVM) this.mViewModel).deviceName.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActConfigSuccessVM) this.mViewModel).editNameEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActConfigSuccess.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((ActConfigSuccessVM) this.mViewModel).editRoomEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActConfigSuccess.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((ActConfigSuccessVM) this.mViewModel).roomPickHelper.startObserve(this, ConfigHelper.instance().placeId, ConfigHelper.instance().roomId);
        ((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getCanSetRoom().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActConfigSuccess.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        ((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getSelectRoomEvent().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActConfigSuccess.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActConfigSuccessVM) this.mViewModel).deviceName.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ConfigHelper.instance().deviceName = (String) obj;
            }
        });
        ((ActConfigSuccessVM) this.mViewModel).showAddSuperPanelSuccessDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActConfigSuccess.this.lambda$startObserve$6((Long) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showEditRoomDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        ((ActConfigSuccessBinding) this.mViewBinding).groupSetRoom.setVisibility(bool.booleanValue() ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r3) {
        ConfigHelper.instance().floorId = ((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getSelectFloorId();
        ConfigHelper.instance().roomId = ((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getSelectRoomId();
        ((ActConfigSuccessVM) this.mViewModel).roomName.postValue(((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getSelectFullRoomName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(final Long l) {
        MessageDialog.show(this, getString(R.string.add_success), getString(R.string.add_super_panel_success_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$startObserve$5;
                lambda$startObserve$5 = ActConfigSuccess.this.lambda$startObserve$5(l, baseDialog, view);
                return lambda$startObserve$5;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$startObserve$5(Long l, BaseDialog baseDialog, View view) {
        NavUtils.destination(ActMeshScan.class).withLong("device_id", l.longValue()).navigation(this);
        finish();
        return false;
    }

    private void showEditNameDialog(String content) {
        EditDialog.asDefault().setContent(content).setTitle(getString(R.string.device_name)).setInputEmptyTip(getString(R.string.device_name_empty)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActConfigSuccess.this.lambda$showEditNameDialog$7((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$7(String str) {
        ((ActConfigSuccessVM) this.mViewModel).deviceName.postValue(str);
    }

    private void showEditRoomDialog() {
        RoomSelectDialog.asDefault().setFloorList(((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((ActConfigSuccessVM) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccess.1
            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void confirm(int floorPosition, int roomPosition) {
                ((ActConfigSuccessVM) ActConfigSuccess.this.mViewModel).roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                ((ActConfigSuccessVM) ActConfigSuccess.this.mViewModel).roomPickHelper.getSelectRoomEvent().call();
            }

            @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
            public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                dialog.setRoomList(((ActConfigSuccessVM) ActConfigSuccess.this.mViewModel).roomPickHelper.getRoomNames(floorPosition));
                dialog.notifyDialog();
            }
        }).showDialog(getSupportFragmentManager());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        NavUtils.destination(ActAddDevice.class).navigation(this);
    }
}