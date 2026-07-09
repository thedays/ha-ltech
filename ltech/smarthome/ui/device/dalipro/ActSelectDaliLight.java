package com.ltech.smarthome.ui.device.dalipro;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.databinding.ViewCgdLightTitleSelectGroupBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.CgdProGroupExtParam;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectDaliLight extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private boolean alreadyJoin;
    private long deviceId;
    private Group editGroup;
    private Floor lastFloor;
    private TextView okTv;
    private List<Long> lastSelectRoleIds = new ArrayList();
    private int lastRoomPosition = -1;

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
        return R.layout.item_select_dali_with_place;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelect3Binding) this.mViewBinding).setTitleGone(true);
        ((ActSelect3Binding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        this.listType = 5;
        this.selectSortType = 11;
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        this.deviceId = getIntent().getLongExtra("device_id", 0L);
        this.editGroup = Injection.repo().group().getGroupByGroupId(getIntent().getLongExtra(Constants.GROUP_ID, 0L));
        ((ActSelect3Binding) this.mViewBinding).layoutTab.setVisibility(0);
        TabLayout.Tab text = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.app_str_not_join);
        ViewHelpUtil.createTabCustomView(this, text, getString(R.string.app_str_not_join), true);
        TabLayout.Tab text2 = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.app_str_already_join);
        ViewHelpUtil.createTabCustomView(this, text2, getString(R.string.app_str_already_join), false);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text2);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActSelectDaliLight.this.activity, tab, tab.getText().toString(), true);
                ActSelectDaliLight.this.alreadyJoin = tab.getPosition() != 0;
                if (ActSelectDaliLight.this.alreadyJoin) {
                    ActSelectDaliLight.this.okTv.setText(String.format(ActSelectDaliLight.this.getResources().getString(R.string.out_of_dali_group), Integer.valueOf(ActSelectDaliLight.this.selectRoleIds.size())));
                } else {
                    ActSelectDaliLight.this.okTv.setText(String.format(ActSelectDaliLight.this.getResources().getString(R.string.join_dali_group), Integer.valueOf(ActSelectDaliLight.this.selectRoleIds.size())));
                }
                if (ActSelectDaliLight.this.selectFt != null) {
                    ActSelectDaliLight.this.selectFt.getData();
                }
                List<Long> list = ActSelectDaliLight.this.lastSelectRoleIds;
                ActSelectDaliLight actSelectDaliLight = ActSelectDaliLight.this;
                actSelectDaliLight.lastSelectRoleIds = actSelectDaliLight.selectRoleIds;
                ActSelectDaliLight.this.selectRoleIds = list;
                ActSelectDaliLight.this.selectCountLiveData.setValue(Integer.valueOf(ActSelectDaliLight.this.selectRoleIds.size()));
                int i = ((FtDeviceGroupVM) ActSelectDaliLight.this.mViewModel).roomPosition;
                if (ActSelectDaliLight.this.lastFloor != null) {
                    Floor floor = ActSelectDaliLight.this.lastFloor;
                    ((FtDeviceGroupVM) ActSelectDaliLight.this.mViewModel).setFloorPosition(floor, ActSelectDaliLight.this.mFloorList);
                    ActSelectDaliLight actSelectDaliLight2 = ActSelectDaliLight.this;
                    actSelectDaliLight2.lastFloor = ((FtDeviceGroupVM) actSelectDaliLight2.mViewModel).getCurFloor();
                    ((FtDeviceGroupVM) ActSelectDaliLight.this.mViewModel).selectFloor.setValue(floor);
                } else {
                    ActSelectDaliLight actSelectDaliLight3 = ActSelectDaliLight.this;
                    actSelectDaliLight3.lastFloor = ((FtDeviceGroupVM) actSelectDaliLight3.mViewModel).getCurFloor();
                    ((FtDeviceGroupVM) ActSelectDaliLight.this.mViewModel).selectFloor.setValue(ActSelectDaliLight.this.mFloorList.get(0));
                }
                ActSelectDaliLight.this.lastRoomPosition = i;
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActSelectDaliLight.this.activity, tab, tab.getText().toString(), false);
            }
        });
        final ViewCgdLightTitleSelectGroupBinding viewCgdLightTitleSelectGroupBinding = (ViewCgdLightTitleSelectGroupBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_cgd_light_title_select_group, ((ActSelect3Binding) this.mViewBinding).headView, true);
        viewCgdLightTitleSelectGroupBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSelectDaliLight.this.lambda$initView$0(viewCgdLightTitleSelectGroupBinding, (View) obj);
            }
        }));
        View view = new View(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(74.0f));
        view.setBackgroundColor(getResources().getColor(R.color.white));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(view, layoutParams);
        TextView textView = new TextView(this);
        this.okTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.okTv.setTextSize(14.0f);
        this.okTv.setBackground(getDrawable(R.drawable.shape_red_bt_22));
        this.okTv.setGravity(17);
        this.okTv.setText(String.format(getResources().getString(R.string.join_dali_group), 0));
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(44.0f));
        layoutParams2.leftMargin = Utils.dip2px(this, 30.0f);
        layoutParams2.rightMargin = Utils.dip2px(this, 30.0f);
        layoutParams2.topMargin = Utils.dip2px(this, 15.0f);
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.okTv, layoutParams2);
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ActSelectDaliLight.this.lambda$initView$3(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(ViewCgdLightTitleSelectGroupBinding viewCgdLightTitleSelectGroupBinding, View view) {
        switch (view.getId()) {
            case R.id.iv_add /* 2131296933 */:
            case R.id.tv_add /* 2131298439 */:
                if (this.selectSortType != 11) {
                    this.selectSortType = 11;
                    viewCgdLightTitleSelectGroupBinding.ivAdd.setImageResource(R.mipmap.ic_ascending_sel);
                    viewCgdLightTitleSelectGroupBinding.ivType.setImageResource(R.mipmap.ic_ascending_def);
                    ((FtDeviceGroupVM) this.mViewModel).mRoomList.get(((FtDeviceGroupVM) this.mViewModel).roomPosition).getFtDevice().sortType.setValue(Integer.valueOf(this.selectSortType));
                    break;
                }
                break;
            case R.id.iv_type /* 2131297303 */:
            case R.id.tv_type /* 2131299075 */:
                if (this.selectSortType != 12) {
                    this.selectSortType = 12;
                    viewCgdLightTitleSelectGroupBinding.ivAdd.setImageResource(R.mipmap.ic_ascending_def);
                    viewCgdLightTitleSelectGroupBinding.ivType.setImageResource(R.mipmap.ic_ascending_sel);
                    ((FtDeviceGroupVM) this.mViewModel).mRoomList.get(((FtDeviceGroupVM) this.mViewModel).roomPosition).getFtDevice().sortType.setValue(Integer.valueOf(this.selectSortType));
                    break;
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view) {
        if (this.selectRoleIds.size() == 0) {
            SmartToast.showShort(R.string.app_str_select_no_device_hint);
            return;
        }
        final Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
        showLoadingDialog(String.format(getResources().getString(R.string.app_str_process), new Object[0]));
        final int groupAddress = DaliProHelper.getGroupAddress(this.editGroup);
        final List<Integer> daliDeviceAddress = DaliProHelper.getDaliDeviceAddress(this.selectRoleIds);
        if (this.alreadyJoin) {
            CmdAssistant.getSettingCmdAssistant(deviceByDeviceId, new int[0]).daliOutGroup(ActivityUtils.getTopActivity(), groupAddress, daliDeviceAddress, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDaliLight.this.lambda$initView$1(deviceByDeviceId, groupAddress, daliDeviceAddress, (Boolean) obj);
                }
            });
        } else {
            CmdAssistant.getSettingCmdAssistant(deviceByDeviceId, new int[0]).daliInGroup(ActivityUtils.getTopActivity(), groupAddress, daliDeviceAddress, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDaliLight.this.lambda$initView$2(deviceByDeviceId, groupAddress, daliDeviceAddress, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(Device device, int i, List list, Boolean bool) {
        if (bool.booleanValue()) {
            saveData(this.alreadyJoin, device, i, list);
            refreshData();
        } else {
            showErrorDialog(getResources().getString(R.string.left_group_fail2));
            dismissLoadingDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(Device device, int i, List list, Boolean bool) {
        if (bool.booleanValue()) {
            saveData(this.alreadyJoin, device, i, list);
        } else {
            showErrorDialog(getResources().getString(R.string.add_group_fail));
            dismissLoadingDialog();
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void initData() {
        ((ActSelect3Binding) this.mViewBinding).headView.setVisibility(this.allRoleData.size() > 0 ? 0 : 8);
    }

    private void saveData(final boolean alreadyJoin, final Device controlDevice, final int groupAddr, final List<Integer> lightAddrList) {
        if (alreadyJoin) {
            this.editGroup.getDeviceIds().removeAll(this.selectRoleIds);
        } else {
            this.editGroup.getDeviceIds().addAll(this.selectRoleIds);
        }
        CgdProGroupExtParam cgdProGroupExtParam = (CgdProGroupExtParam) this.editGroup.getExtParam(CgdProGroupExtParam.class);
        cgdProGroupExtParam.setMultiTypeBit(DaliProHelper.getDaliGroupType(this.editGroup));
        this.editGroup.setExtParam(cgdProGroupExtParam);
        ((ObservableSubscribeProxy) Injection.net().updateDaliGroupDevices(this.editGroup.getGroupId(), this.editGroup.getDeviceIds(), this.editGroup.getExtParam()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDaliLight.this.lambda$saveData$4((UpdateGroupResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDaliLight.this.lambda$saveData$7(alreadyJoin, controlDevice, groupAddr, lightAddrList, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$4(UpdateGroupResponse updateGroupResponse) throws Exception {
        Injection.repo().group().saveGroup(this.editGroup);
        this.selectRoleIds.clear();
        refreshData();
        SmartToast.showCenterShort(getString(R.string.save_success));
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$7(boolean z, Device device, int i, List list, Throwable th) throws Exception {
        if (z) {
            showErrorDialog(getResources().getString(R.string.left_group_fail2));
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).daliInGroup(ActivityUtils.getTopActivity(), i, list, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDaliLight.this.lambda$saveData$5((Boolean) obj);
                }
            });
        } else {
            showErrorDialog(getResources().getString(R.string.add_group_fail));
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).daliOutGroup(ActivityUtils.getTopActivity(), i, list, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDaliLight.this.lambda$saveData$6((Boolean) obj);
                }
            });
        }
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$5(Boolean bool) {
        this.editGroup.getDeviceIds().addAll(this.selectRoleIds);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$6(Boolean bool) {
        this.editGroup.getDeviceIds().removeAll(this.selectRoleIds);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void convertView(final BaseViewHolder helper, final Role role) {
        final Device device = (Device) role;
        helper.setText(R.id.tv_device_name, device.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(device)).setText(R.id.tv_place_info, ((FtDeviceGroupVM) this.mViewModel).getPlaceInfo(device.getFloorId(), device.getRoomId()));
        if (this.selectRoleIds.contains(Long.valueOf(device.getObjectId()))) {
            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_sel);
        } else {
            helper.setBackgroundRes(R.id.iv_select, R.mipmap.ic_tick_default);
        }
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
        CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) device.getExtParam(CgdProLightExtParam.class);
        helper.setText(R.id.tv_add, String.valueOf(cgdProLightExtParam.getDaliAddr())).setText(R.id.tv_type, String.valueOf(cgdProLightExtParam.getTypeStr()));
        helper.setGone(R.id.iv_location, ((FtDeviceGroupVM) this.mViewModel).needLocation(device));
        helper.getView(R.id.iv_location).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSelectDaliLight.this.lambda$convertView$8(device, view);
            }
        });
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActSelectDaliLight$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSelectDaliLight.this.lambda$convertView$9(role, helper, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$8(Device device, View view) {
        CmdAssistant.getSettingCmdAssistant(device, new int[0]).testDeviceLocation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$9(Role role, BaseViewHolder baseViewHolder, View view) {
        if (this.selectRoleIds.contains(Long.valueOf(role.getObjectId()))) {
            this.selectRoleIds.remove(Long.valueOf(role.getObjectId()));
            baseViewHolder.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_default);
        } else {
            this.selectRoleIds.add(Long.valueOf(role.getObjectId()));
            baseViewHolder.getView(R.id.iv_select).setBackgroundResource(R.mipmap.ic_tick_sel);
        }
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectRoleIds.size()));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int selectCount) {
        if (this.alreadyJoin) {
            this.okTv.setText(String.format(getResources().getString(R.string.out_of_dali_group), Integer.valueOf(this.selectRoleIds.size())));
        } else {
            this.okTv.setText(String.format(getResources().getString(R.string.join_dali_group), Integer.valueOf(this.selectRoleIds.size())));
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected int getFloorIndex(List<Floor> floorList) {
        int i = this.lastRoomPosition;
        return i == -1 ? super.getFloorIndex(floorList) : i;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected int getRoomIndex(List<Room> roomList) {
        int i = this.lastRoomPosition;
        if (i != -1) {
            return i;
        }
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.deviceId);
        if (deviceByDeviceId != null) {
            for (int i2 = 0; i2 < roomList.size(); i2++) {
                if (roomList.get(i2).getRoomId() == deviceByDeviceId.getRoomId()) {
                    return i2;
                }
            }
        }
        return 0;
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        if (this.alreadyJoin) {
            this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_out_device), Integer.valueOf(this.selectRoleIds.size())));
        } else {
            this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_join_device), Integer.valueOf(this.selectRoleIds.size())));
        }
        if (this.selectFt != null) {
            this.selectFt.getData();
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        if (this.alreadyJoin) {
            return this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId()));
        }
        if (device.isSubDevice() && device.getMacdeviceid() == this.deviceId) {
            return !this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId()));
        }
        return false;
    }
}