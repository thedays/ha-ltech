package com.ltech.smarthome.ui.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDeviceManage extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private View.OnClickListener bottomClick = new View.OnClickListener() { // from class: com.ltech.smarthome.ui.home.ActDeviceManage.2
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel /* 2131296488 */:
                    ((ActSelect3Binding) ActDeviceManage.this.mViewBinding).bottom.btnCancel.setVisibility(8);
                    ((ActSelect3Binding) ActDeviceManage.this.mViewBinding).bottom.btnStartLocate.setVisibility(8);
                    ((ActSelect3Binding) ActDeviceManage.this.mViewBinding).bottom.btnFindDevice.setVisibility(0);
                    ActDeviceManage.this.selectRoleIds.clear();
                    if (ActDeviceManage.this.locateDevice) {
                        ActDeviceManage.this.locateDevice = false;
                        if (ActDeviceManage.this.selectFt != null) {
                            ActDeviceManage.this.selectFt.getData();
                            break;
                        }
                    }
                    break;
                case R.id.btn_change_room /* 2131296490 */:
                    if (!ActDeviceManage.this.canCancel() || ActDeviceManage.this.selectRoleIds.size() <= 0) {
                        ((ActSelect3Binding) ActDeviceManage.this.mViewBinding).bottom.btnCancel.setVisibility(0);
                        ((ActSelect3Binding) ActDeviceManage.this.mViewBinding).bottom.btnFindDevice.setVisibility(8);
                        break;
                    } else {
                        ActDeviceManage.this.showEditRoomDialog();
                        break;
                    }
                    break;
                case R.id.btn_find_device /* 2131296493 */:
                    ((ActSelect3Binding) ActDeviceManage.this.mViewBinding).bottom.btnCancel.setVisibility(0);
                    ((ActSelect3Binding) ActDeviceManage.this.mViewBinding).bottom.btnFindDevice.setVisibility(8);
                    ((ActSelect3Binding) ActDeviceManage.this.mViewBinding).bottom.btnStartLocate.setVisibility(0);
                    if (!ActDeviceManage.this.locateDevice) {
                        ActDeviceManage.this.locateDevice = true;
                        if (ActDeviceManage.this.selectFt != null) {
                            ActDeviceManage.this.selectFt.getData();
                            break;
                        }
                    }
                    break;
                case R.id.btn_start_locate /* 2131296503 */:
                    for (int i = 0; i < ActDeviceManage.this.selectRoleIds.size(); i++) {
                        Role roleByRoleId = ((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).getRoleByRoleId(ActDeviceManage.this.selectRoleIds.get(i).longValue());
                        if (roleByRoleId != null) {
                            CmdAssistant.getSettingCmdAssistant(roleByRoleId, new int[0]).testDeviceLocation(ActDeviceManage.this);
                        }
                    }
                    break;
            }
            ActDeviceManage.this.changeTitleView();
            ActDeviceManage actDeviceManage = ActDeviceManage.this;
            actDeviceManage.changeSelectCount(actDeviceManage.selectRoleIds.size());
            int i2 = ActDeviceManage.this.listType;
            ActDeviceManage actDeviceManage2 = ActDeviceManage.this;
            actDeviceManage2.listType = actDeviceManage2.canCancel() ? 1 : 2;
            if (ActDeviceManage.this.listType != i2) {
                ActDeviceManage.this.selectFt.refreshList.call();
            }
        }
    };
    private boolean locateDevice;

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.device_manage_title));
        changeTitleView();
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        ((FtDeviceGroupVM) this.mViewModel).showUnconfigRoom = getIntent().getBooleanExtra(Constants.SHOW_UNCONFIG_ROOM, false);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 40.0f);
        ((ActSelect3Binding) this.mViewBinding).title.ivDoubt.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivDoubt.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.home.ActDeviceManage$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDeviceManage.this.lambda$initView$0(view);
            }
        });
        this.listType = 2;
        ((ActSelect3Binding) this.mViewBinding).bottom.layoutDeviceManage.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).bottom.btnCancel.setOnClickListener(this.bottomClick);
        ((ActSelect3Binding) this.mViewBinding).bottom.btnFindDevice.setOnClickListener(this.bottomClick);
        ((ActSelect3Binding) this.mViewBinding).bottom.btnStartLocate.setOnClickListener(this.bottomClick);
        ((ActSelect3Binding) this.mViewBinding).bottom.btnChangeRoom.setOnClickListener(this.bottomClick);
        if (((FtDeviceGroupVM) this.mViewModel).showUnconfigRoom) {
            ((ObservableSubscribeProxy) Injection.repo().role().getRoleList(this.placeId, -1L, -1L).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<List<Role>>() { // from class: com.ltech.smarthome.ui.home.ActDeviceManage.1
                @Override // io.reactivex.functions.Consumer
                public void accept(List<Role> roles) throws Exception {
                    ((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).placeId.setValue(Long.valueOf(ActDeviceManage.this.getIntent().getLongExtra(Constants.PLACE_ID, -1L)));
                }
            }, new Consumer() { // from class: com.ltech.smarthome.ui.home.ActDeviceManage$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDeviceManage.this.lambda$initView$1((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        showPopup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(Throwable th) throws Exception {
        ((FtDeviceGroupVM) this.mViewModel).placeId.setValue(Long.valueOf(getIntent().getLongExtra(Constants.PLACE_ID, -1L)));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        if (LanguageUtils.isRussian(this.activity)) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((ActSelect3Binding) this.mViewBinding).title.tvTitle.getLayoutParams();
            layoutParams.endToStart = ((ActSelect3Binding) this.mViewBinding).title.ivDoubt.getId();
            ((ActSelect3Binding) this.mViewBinding).title.tvTitle.setLayoutParams(layoutParams);
        }
        ((FtDeviceGroupVM) this.mViewModel).roomPickHelper.startObserve(this, this.placeId, -1L);
        ((FtDeviceGroupVM) this.mViewModel).refreshRoleItem.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActDeviceManage$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDeviceManage.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        this.selectFt.getData();
        this.selectFt.refreshList.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeTitleView() {
        if (canCancel()) {
            setEditImage(0);
            setEditString(getString(R.string.app_str_select_all));
            this.selectCountLiveData.setValue(Integer.valueOf(this.selectRoleIds.size()));
            ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(8);
            if (this.locateDevice) {
                return;
            }
            ((ActSelect3Binding) this.mViewBinding).title.ivDoubt.setVisibility(8);
            return;
        }
        setEditImage(R.mipmap.ic_sort);
        setEditString("");
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivDoubt.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canCancel() {
        return ((ActSelect3Binding) this.mViewBinding).bottom.btnCancel.getVisibility() == 0;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (canCancel()) {
            super.edit();
        } else {
            NavUtils.destination(ActSortDeviceAndGroup.class).withLong(Constants.PLACE_ID, this.placeId).withLong(Constants.FLOOR_ID, ((FtDeviceGroupVM) this.mViewModel).selectFloor.getValue().getFloorId()).withLong(Constants.ROOM_ID, ((FtDeviceGroupVM) this.mViewModel).selectRoom.getValue() == null ? -1L : ((FtDeviceGroupVM) this.mViewModel).selectRoom.getValue().getRoomId()).navigation(this);
        }
    }

    private void showPopup() {
        PopupWindow popupWindow = new PopupWindow(LayoutInflater.from(this).inflate(R.layout.view_location_tip, (ViewGroup) null), Utils.dip2px(this, 235.0f), -2, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(((ActSelect3Binding) this.mViewBinding).title.ivDoubt, Utils.dip2px(this, -150.0f), 0);
    }

    protected void showEditRoomDialog() {
        if (((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            RoomSelectDialog.asDefault().setFloorList(((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.home.ActDeviceManage.3
                @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
                public void confirm(int floorPosition, int roomPosition) {
                    ((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < ActDeviceManage.this.selectRoleIds.size(); i++) {
                        Role roleByRoleId = ((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).getRoleByRoleId(ActDeviceManage.this.selectRoleIds.get(i).longValue());
                        if (roleByRoleId != null) {
                            arrayList.add(((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).changeRolePlace(roleByRoleId, ((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).roomPickHelper.getSelectFloorId(), ((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).roomPickHelper.getSelectRoomId()));
                        }
                    }
                    ActDeviceManage.this.selectRoleIds.clear();
                    if (arrayList.size() > 0) {
                        ((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).batchControl(arrayList);
                    }
                }

                @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
                public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                    dialog.setRoomList(((FtDeviceGroupVM) ActDeviceManage.this.mViewModel).roomPickHelper.getRoomNames(floorPosition));
                    dialog.notifyDialog();
                }
            }).showDialog(this);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int selectCount) {
        ((ActSelect3Binding) this.mViewBinding).bottom.ivStartLocate.setEnabled(!canCancel() || selectCount > 0);
        ((ActSelect3Binding) this.mViewBinding).bottom.tvStartLocate.setEnabled(!canCancel() || selectCount > 0);
        ((ActSelect3Binding) this.mViewBinding).bottom.ivChangeRoom.setEnabled(!canCancel() || selectCount > 0);
        ((ActSelect3Binding) this.mViewBinding).bottom.tvChangeRoom.setEnabled(!canCancel() || selectCount > 0);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return !this.locateDevice || ((FtDeviceGroupVM) this.mViewModel).needLocation(device);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return !this.locateDevice || ((FtDeviceGroupVM) this.mViewModel).needLocation(group);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.selectFt != null) {
            this.selectFt.getData();
        }
    }
}