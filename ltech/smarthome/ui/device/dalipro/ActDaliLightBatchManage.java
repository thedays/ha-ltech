package com.ltech.smarthome.ui.device.dalipro;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.databinding.ViewCgdLightTitleSelectBinding;
import com.ltech.smarthome.databinding.ViewDaliManageBottomBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActDaliLightBatchManage extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private MutableLiveData<Boolean> batchMode = new MutableLiveData<>(false);
    private long deviceId;

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDaliSubDeviceGroup(Role role) {
        return true;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected int itemLayout() {
        return R.layout.item_dali_light_manage;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_batch_set));
        setEditString(getString(R.string.str_batch));
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        this.deviceId = getIntent().getLongExtra("device_id", 0L);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(8);
        this.listType = 5;
        this.selectSortType = 11;
        this.batchMode.setValue(false);
        ViewDaliManageBottomBinding viewDaliManageBottomBinding = (ViewDaliManageBottomBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_dali_manage_bottom, ((ActSelect3Binding) this.mViewBinding).footerView, true);
        viewDaliManageBottomBinding.btnFindDevice.setVisibility(8);
        viewDaliManageBottomBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightBatchManage$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActDaliLightBatchManage.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.selectRoleIds.size() == 0) {
            return;
        }
        int i = 0;
        switch (view.getId()) {
            case R.id.btn_change_room /* 2131296490 */:
                showEditRoomDialog();
                return;
            case R.id.btn_edit /* 2131296491 */:
            case R.id.btn_enable_or_disable_elderly_mode /* 2131296492 */:
            default:
                return;
            case R.id.btn_find_device /* 2131296493 */:
                break;
            case R.id.btn_modify_param /* 2131296494 */:
                long[] jArr = new long[this.selectRoleIds.size()];
                int size = this.selectRoleIds.size();
                while (i < size) {
                    jArr[i] = this.selectRoleIds.get(i).longValue();
                    i++;
                }
                NavUtils.destination(ActDaliBatchModifyParam.class).withLongArray(Constants.DEVICE_ID_ARRAY, jArr).navigation(ActivityUtils.getTopActivity());
                return;
        }
        while (i < this.selectRoleIds.size()) {
            Role roleByRoleId = ((FtDeviceGroupVM) this.mViewModel).getRoleByRoleId(this.selectRoleIds.get(i).longValue());
            if (roleByRoleId != null) {
                CmdAssistant.getSettingCmdAssistant(roleByRoleId, DaliProHelper.getZoneNum(roleByRoleId)).testDeviceLocation(this);
            }
            i++;
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void convertView(final BaseViewHolder helper, final Role role) {
        final Device device = (Device) role;
        helper.setText(R.id.tv_device_name, device.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(device)).setText(R.id.tv_place_info, ((FtDeviceGroupVM) this.mViewModel).getPlaceInfo(device.getFloorId(), device.getRoomId()));
        final boolean equals = Boolean.TRUE.equals(this.batchMode.getValue());
        helper.setGone(R.id.iv_location, !equals).setGone(R.id.iv_edit, !equals).setGone(R.id.iv_select, equals);
        if (this.selectRoleIds.contains(Long.valueOf(device.getObjectId()))) {
            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel);
        } else {
            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_default);
        }
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
        CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) device.getExtParam(CgdProLightExtParam.class);
        helper.setText(R.id.tv_add, String.valueOf(cgdProLightExtParam.getDaliAddr())).setText(R.id.tv_type, String.valueOf(cgdProLightExtParam.getTypeStr()));
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightBatchManage$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDaliLightBatchManage.this.lambda$convertView$1(equals, role, helper, view);
            }
        });
        helper.getView(R.id.iv_location).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightBatchManage$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDaliLightBatchManage.this.lambda$convertView$2(device, view);
            }
        });
        helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightBatchManage$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDaliLightBatchManage.this.lambda$convertView$3(device, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$1(boolean z, Role role, BaseViewHolder baseViewHolder, View view) {
        if (z) {
            if (this.selectRoleIds.contains(Long.valueOf(role.getObjectId()))) {
                this.selectRoleIds.remove(Long.valueOf(role.getObjectId()));
                baseViewHolder.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_default);
            } else {
                this.selectRoleIds.add(Long.valueOf(role.getObjectId()));
                baseViewHolder.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_sel);
            }
            this.selectCountLiveData.setValue(Integer.valueOf(this.selectRoleIds.size()));
            return;
        }
        Device device = (Device) role;
        NavUtils.destination(ActDaliLightSetting.class).withLong("device_id", device.getMacdeviceid()).withLong(Constants.CONTROL_ID, device.getId()).withInt(Constants.LIGHT_TYPE, DaliProHelper.convertDaliType(device)).navigation(ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$2(Device device, View view) {
        CmdAssistant.getSettingCmdAssistant(device, DaliProHelper.getZoneNum(device)).testDeviceLocation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$3(Device device, View view) {
        ((FtDeviceGroupVM) this.mViewModel).showEditDialog(device);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void initData() {
        ((ActSelect3Binding) this.mViewBinding).headView.setVisibility(this.allRoleData.size() > 0 ? 0 : 8);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((FtDeviceGroupVM) this.mViewModel).roomPickHelper.startObserve(this, this.placeId, -1L);
        ((FtDeviceGroupVM) this.mViewModel).refreshRoleItem.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightBatchManage$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightBatchManage.this.lambda$startObserve$4((Void) obj);
            }
        });
        this.batchMode.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightBatchManage$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliLightBatchManage.this.lambda$startObserve$5((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        this.selectFt.getData();
        this.selectFt.refreshList.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Boolean bool) {
        changeHeaderView(bool.booleanValue());
        ((ActSelect3Binding) this.mViewBinding).footerView.setVisibility(bool.booleanValue() ? 0 : 8);
        if (bool.booleanValue()) {
            setTitle(getString(R.string.str_batch));
            setBackImage(0);
            setBackString(getString(R.string.cancel));
            setEditString(getString(R.string.app_str_select_all));
            return;
        }
        setTitle(getString(R.string.app_str_batch_set));
        setBackImage(R.mipmap.icon_back);
        setBackString("");
        setEditString(getString(R.string.str_batch));
    }

    private void changeHeaderView(boolean isBatch) {
        if (isBatch) {
            final ViewCgdLightTitleSelectBinding viewCgdLightTitleSelectBinding = (ViewCgdLightTitleSelectBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_cgd_light_title_select, ((ActSelect3Binding) this.mViewBinding).headView, true);
            viewCgdLightTitleSelectBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightBatchManage$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ActDaliLightBatchManage.this.lambda$changeHeaderView$6(viewCgdLightTitleSelectBinding, (View) obj);
                }
            }));
        } else {
            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_cgd_light_title_manage, ((ActSelect3Binding) this.mViewBinding).headView, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeHeaderView$6(ViewCgdLightTitleSelectBinding viewCgdLightTitleSelectBinding, View view) {
        switch (view.getId()) {
            case R.id.iv_add /* 2131296933 */:
            case R.id.tv_add /* 2131298439 */:
                if (this.selectSortType != 11) {
                    this.selectSortType = 11;
                    viewCgdLightTitleSelectBinding.tvAdd.setTextColor(getResources().getColor(R.color.color_text_black));
                    viewCgdLightTitleSelectBinding.ivAdd.setImageResource(R.mipmap.ic_ascending_sel);
                    viewCgdLightTitleSelectBinding.tvType.setTextColor(getResources().getColor(R.color.color_text_dark_gray));
                    viewCgdLightTitleSelectBinding.ivType.setImageResource(R.mipmap.ic_ascending_def);
                    ((FtDeviceGroupVM) this.mViewModel).mRoomList.get(((FtDeviceGroupVM) this.mViewModel).roomPosition).getFtDevice().sortType.setValue(Integer.valueOf(this.selectSortType));
                    break;
                }
                break;
            case R.id.iv_type /* 2131297303 */:
            case R.id.tv_type /* 2131299075 */:
                if (this.selectSortType != 12) {
                    this.selectSortType = 12;
                    viewCgdLightTitleSelectBinding.tvAdd.setTextColor(getResources().getColor(R.color.color_text_dark_gray));
                    viewCgdLightTitleSelectBinding.ivAdd.setImageResource(R.mipmap.ic_ascending_def);
                    viewCgdLightTitleSelectBinding.tvType.setTextColor(getResources().getColor(R.color.color_text_black));
                    viewCgdLightTitleSelectBinding.ivType.setImageResource(R.mipmap.ic_ascending_sel);
                    ((FtDeviceGroupVM) this.mViewModel).mRoomList.get(((FtDeviceGroupVM) this.mViewModel).roomPosition).getFtDevice().sortType.setValue(Integer.valueOf(this.selectSortType));
                    break;
                }
                break;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (Boolean.TRUE.equals(this.batchMode.getValue())) {
            this.batchMode.setValue(false);
            getAdapter().notifyDataSetChanged();
            this.selectRoleIds.clear();
            return;
        }
        super.back();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (Boolean.FALSE.equals(this.batchMode.getValue())) {
            this.batchMode.setValue(true);
            getAdapter().notifyDataSetChanged();
        } else {
            super.edit();
        }
    }

    protected void showEditRoomDialog() {
        if (((FtDeviceGroupVM) this.mViewModel).roomPickHelper.canSetRoom()) {
            RoomSelectDialog.asDefault().setTitle(getString(R.string.batch_modify_room)).setSaveText(getString(R.string.save)).setFloorList(((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((FtDeviceGroupVM) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliLightBatchManage.1
                @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
                public void confirm(int floorPosition, int roomPosition) {
                    ((FtDeviceGroupVM) ActDaliLightBatchManage.this.mViewModel).roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < ActDaliLightBatchManage.this.selectRoleIds.size(); i++) {
                        Role roleByRoleId = ((FtDeviceGroupVM) ActDaliLightBatchManage.this.mViewModel).getRoleByRoleId(ActDaliLightBatchManage.this.selectRoleIds.get(i).longValue());
                        if (roleByRoleId != null) {
                            arrayList.add(((FtDeviceGroupVM) ActDaliLightBatchManage.this.mViewModel).changeRolePlace(roleByRoleId, ((FtDeviceGroupVM) ActDaliLightBatchManage.this.mViewModel).roomPickHelper.getSelectFloorId(), ((FtDeviceGroupVM) ActDaliLightBatchManage.this.mViewModel).roomPickHelper.getSelectRoomId()));
                        }
                    }
                    ActDaliLightBatchManage.this.selectRoleIds.clear();
                    if (arrayList.size() > 0) {
                        ((FtDeviceGroupVM) ActDaliLightBatchManage.this.mViewModel).batchControl(arrayList);
                    }
                }

                @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
                public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                    dialog.setRoomList(((FtDeviceGroupVM) ActDaliLightBatchManage.this.mViewModel).roomPickHelper.getRoomNames(floorPosition));
                    dialog.notifyDialog();
                }
            }).showDialog(this);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        return device.getMacdeviceid() == this.deviceId;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.selectFt != null) {
            this.selectFt.getData();
        }
    }
}