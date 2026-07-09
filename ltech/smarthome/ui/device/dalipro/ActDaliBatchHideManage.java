package com.ltech.smarthome.ui.device.dalipro;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.databinding.ViewCgdLightTitleDaliBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.CgdProGroupExtParam;
import com.ltech.smarthome.model.device_param.CgdProLightExtParam;
import com.ltech.smarthome.model.device_param.CgdProSceneExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActDaliBatchHideManage extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private boolean alreadyAdd;
    private long deviceId;
    private ViewCgdLightTitleDaliBinding headViewBinding;
    private Floor lastFloor;
    private TextView okTv;
    private List<Long> lastSelectRoleIds = new ArrayList();
    private int lastRoomPosition = -1;
    private int showType = 1;
    private List<Role> needSaveRoleList = new ArrayList();
    private MutableLiveData<Integer> roleCount = new MutableLiveData<>();

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDaliSubDeviceGroup(Role role) {
        return true;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected int itemLayout() {
        return R.layout.item_dali_manage;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelect3Binding) this.mViewBinding).setTitleGone(true);
        ((ActSelect3Binding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.listType = 5;
        this.selectSortType = 11;
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = false;
        this.includeScene = true;
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutDaliType.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).radioDaliType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda6
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup, int i) {
                ActDaliBatchHideManage.this.lambda$initView$0(radioGroup, i);
            }
        });
        this.deviceId = getIntent().getLongExtra("device_id", 0L);
        ((ActSelect3Binding) this.mViewBinding).layoutTab.setVisibility(0);
        TabLayout.Tab text = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.str_not_add);
        ViewHelpUtil.createTabCustomView(this, text, getString(R.string.str_not_add), true);
        TabLayout.Tab text2 = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.str_added);
        ViewHelpUtil.createTabCustomView(this, text2, getString(R.string.str_added), false);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text2);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActDaliBatchHideManage.this.activity, tab, tab.getText().toString(), true);
                ActDaliBatchHideManage.this.alreadyAdd = tab.getPosition() != 0;
                if (ActDaliBatchHideManage.this.alreadyAdd) {
                    ActDaliBatchHideManage.this.okTv.setText(String.format(ActDaliBatchHideManage.this.getResources().getString(R.string.str_cancel_add_with_num), Integer.valueOf(ActDaliBatchHideManage.this.selectRoleIds.size())));
                } else {
                    ActDaliBatchHideManage.this.okTv.setText(String.format(ActDaliBatchHideManage.this.getResources().getString(R.string.str_add_with_num), Integer.valueOf(ActDaliBatchHideManage.this.selectRoleIds.size())));
                }
                if (ActDaliBatchHideManage.this.selectFt != null) {
                    ActDaliBatchHideManage.this.selectFt.getData();
                }
                List<Long> list = ActDaliBatchHideManage.this.lastSelectRoleIds;
                ActDaliBatchHideManage actDaliBatchHideManage = ActDaliBatchHideManage.this;
                actDaliBatchHideManage.lastSelectRoleIds = actDaliBatchHideManage.selectRoleIds;
                ActDaliBatchHideManage.this.selectRoleIds = list;
                ActDaliBatchHideManage.this.selectCountLiveData.setValue(Integer.valueOf(ActDaliBatchHideManage.this.selectRoleIds.size()));
                int i = ((FtDeviceGroupVM) ActDaliBatchHideManage.this.mViewModel).roomPosition;
                if (ActDaliBatchHideManage.this.lastFloor != null) {
                    Floor floor = ActDaliBatchHideManage.this.lastFloor;
                    ((FtDeviceGroupVM) ActDaliBatchHideManage.this.mViewModel).setFloorPosition(floor, ActDaliBatchHideManage.this.mFloorList);
                    ActDaliBatchHideManage actDaliBatchHideManage2 = ActDaliBatchHideManage.this;
                    actDaliBatchHideManage2.lastFloor = ((FtDeviceGroupVM) actDaliBatchHideManage2.mViewModel).getCurFloor();
                    ((FtDeviceGroupVM) ActDaliBatchHideManage.this.mViewModel).selectFloor.setValue(floor);
                } else {
                    ActDaliBatchHideManage actDaliBatchHideManage3 = ActDaliBatchHideManage.this;
                    actDaliBatchHideManage3.lastFloor = ((FtDeviceGroupVM) actDaliBatchHideManage3.mViewModel).getCurFloor();
                    ((FtDeviceGroupVM) ActDaliBatchHideManage.this.mViewModel).selectFloor.setValue(ActDaliBatchHideManage.this.mFloorList.get(0));
                }
                ActDaliBatchHideManage.this.lastRoomPosition = i;
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActDaliBatchHideManage.this.activity, tab, tab.getText().toString(), false);
            }
        });
        ViewCgdLightTitleDaliBinding viewCgdLightTitleDaliBinding = (ViewCgdLightTitleDaliBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.view_cgd_light_title_dali, ((ActSelect3Binding) this.mViewBinding).headView, true);
        this.headViewBinding = viewCgdLightTitleDaliBinding;
        int i = this.showType;
        if (i == 1) {
            viewCgdLightTitleDaliBinding.tvTip.setText(R.string.scene_number);
        } else if (i == 2) {
            viewCgdLightTitleDaliBinding.tvTip.setText(R.string.group_number);
        } else {
            viewCgdLightTitleDaliBinding.tvTip.setText(R.string.address_number);
        }
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
        this.okTv.setText(String.format(getResources().getString(R.string.str_add_with_num), 0));
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(44.0f));
        layoutParams2.leftMargin = Utils.dip2px(this, 30.0f);
        layoutParams2.rightMargin = Utils.dip2px(this, 30.0f);
        layoutParams2.topMargin = Utils.dip2px(this, 15.0f);
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.okTv, layoutParams2);
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ActDaliBatchHideManage.this.lambda$initView$1(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RadioGroup radioGroup, int i) {
        if (i == R.id.radio_scene) {
            this.showType = 1;
        } else if (i == R.id.radio_group) {
            this.showType = 2;
        } else if (i == R.id.radio_add) {
            this.showType = 3;
        }
        int i2 = this.showType;
        if (i2 == 1) {
            this.headViewBinding.tvTip.setText(R.string.scene_number);
        } else if (i2 == 2) {
            this.headViewBinding.tvTip.setText(R.string.group_number);
        } else {
            this.headViewBinding.tvTip.setText(R.string.address_number);
        }
        if (this.selectFt != null) {
            this.selectFt.getData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        if (this.selectRoleIds.size() == 0) {
            SmartToast.showShort(R.string.app_str_select_no_device_hint);
            return;
        }
        this.needSaveRoleList.clear();
        for (int i = 0; i < this.selectRoleIds.size(); i++) {
            Role roleByRoleIdIncludeScene = ((FtDeviceGroupVM) this.mViewModel).getRoleByRoleIdIncludeScene(this.selectRoleIds.get(i).longValue());
            if (roleByRoleIdIncludeScene != null) {
                this.needSaveRoleList.add(roleByRoleIdIncludeScene);
            }
        }
        this.selectRoleIds.clear();
        this.roleCount.setValue(0);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.roleCount.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliBatchHideManage.this.lambda$startObserve$2((Integer) obj);
            }
        });
        ((FtDeviceGroupVM) this.mViewModel).refreshRoleItem.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDaliBatchHideManage.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Integer num) {
        int intValue = this.roleCount.getValue().intValue();
        if (intValue < this.needSaveRoleList.size()) {
            showLoadingDialog(getString(R.string.batch_saving, new Object[]{String.valueOf(intValue + 1), String.valueOf(this.needSaveRoleList.size())}));
            changeRoleExtParam(this.needSaveRoleList.get(intValue), this.alreadyAdd);
        } else {
            dismissLoadingDialog();
            this.needSaveRoleList.clear();
            ((FtDeviceGroupVM) this.mViewModel).refreshRoleItem.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        this.selectFt.getData();
        this.selectFt.refreshList.call();
    }

    public void changeRoleExtParam(Role role, boolean z) {
        if (role instanceof Device) {
            final Device device = (Device) role;
            final CgdProLightExtParam cgdProLightExtParam = (CgdProLightExtParam) device.getExtParam(CgdProLightExtParam.class);
            cgdProLightExtParam.setDaliHidden(z ? 1 : 0);
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), GsonUtils.toJson(cgdProLightExtParam)).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliBatchHideManage.this.lambda$changeRoleExtParam$4(device, cgdProLightExtParam, obj);
                }
            }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage.2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    ActDaliBatchHideManage.this.roleCount.setValue(Integer.valueOf(((Integer) ActDaliBatchHideManage.this.roleCount.getValue()).intValue() + 1));
                }
            });
            return;
        }
        if (role instanceof Group) {
            final Group group = (Group) role;
            final CgdProGroupExtParam cgdProGroupExtParam = (CgdProGroupExtParam) group.getExtParam(CgdProGroupExtParam.class);
            cgdProGroupExtParam.setDaliHidden(z ? 1 : 0);
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), GsonUtils.toJson(cgdProGroupExtParam)).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActDaliBatchHideManage.this.lambda$changeRoleExtParam$5(group, cgdProGroupExtParam, obj);
                }
            }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage.3
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    ActDaliBatchHideManage.this.roleCount.setValue(Integer.valueOf(((Integer) ActDaliBatchHideManage.this.roleCount.getValue()).intValue() + 1));
                }
            });
            return;
        }
        final Scene scene = (Scene) role;
        final CgdProSceneExtParam cgdProSceneExtParam = (CgdProSceneExtParam) scene.getExtParam(CgdProSceneExtParam.class);
        cgdProSceneExtParam.setDaliHidden(z ? 1 : 0);
        ((ObservableSubscribeProxy) Injection.net().updateSceneExtParam(scene.getSceneId(), GsonUtils.toJson(cgdProSceneExtParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActDaliBatchHideManage.this.lambda$changeRoleExtParam$6(scene, cgdProSceneExtParam, obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActDaliBatchHideManage.this.roleCount.setValue(Integer.valueOf(((Integer) ActDaliBatchHideManage.this.roleCount.getValue()).intValue() + 1));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleExtParam$4(Device device, CgdProLightExtParam cgdProLightExtParam, Object obj) throws Exception {
        device.setExtParam(GsonUtils.toJson(cgdProLightExtParam));
        Injection.repo().device().saveDevice(device);
        MutableLiveData<Integer> mutableLiveData = this.roleCount;
        mutableLiveData.setValue(Integer.valueOf(mutableLiveData.getValue().intValue() + 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleExtParam$5(Group group, CgdProGroupExtParam cgdProGroupExtParam, Object obj) throws Exception {
        group.setExtParam(GsonUtils.toJson(cgdProGroupExtParam));
        Injection.repo().group().saveGroup(group);
        MutableLiveData<Integer> mutableLiveData = this.roleCount;
        mutableLiveData.setValue(Integer.valueOf(mutableLiveData.getValue().intValue() + 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleExtParam$6(Scene scene, CgdProSceneExtParam cgdProSceneExtParam, Object obj) throws Exception {
        scene.setExtParam(cgdProSceneExtParam);
        Injection.repo().scene().saveScene(scene);
        MutableLiveData<Integer> mutableLiveData = this.roleCount;
        mutableLiveData.setValue(Integer.valueOf(mutableLiveData.getValue().intValue() + 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$7() {
        ((ActSelect3Binding) this.mViewBinding).headView.setVisibility(this.allRoleData.size() > 0 ? 0 : 8);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void initData() {
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ActDaliBatchHideManage.this.lambda$initData$7();
            }
        }, 100L);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void convertView(final BaseViewHolder helper, final Role role) {
        helper.setText(R.id.tv_device_name, role.getName());
        helper.setGone(R.id.tv_place_info, !TextUtils.isEmpty(r0)).setText(R.id.tv_place_info, ((FtDeviceGroupVM) this.mViewModel).getPlaceInfo(role.getFloorId(), role.getRoomId()));
        if ((role instanceof Group) || (role instanceof Device)) {
            helper.setText(R.id.tv_num, String.valueOf(DaliProHelper.getDaliAddress(role))).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(role));
        } else {
            Scene scene = (Scene) role;
            helper.setText(R.id.tv_num, String.valueOf(DaliProHelper.getSceneNum(scene))).setImageResource(R.id.iv_icon, SceneHelper.getSceneIcon(this, scene.getIconPos()));
        }
        if (this.selectRoleIds.contains(Long.valueOf(role.getObjectId()))) {
            helper.setImageResource(R.id.iv_go, R.mipmap.ic_tick_sel);
        } else {
            helper.setImageResource(R.id.iv_go, R.mipmap.ic_tick_default);
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.dalipro.ActDaliBatchHideManage$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActDaliBatchHideManage.this.lambda$convertView$8(role, helper, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convertView$8(Role role, BaseViewHolder baseViewHolder, View view) {
        if (this.selectRoleIds.contains(Long.valueOf(role.getObjectId()))) {
            this.selectRoleIds.remove(Long.valueOf(role.getObjectId()));
            baseViewHolder.setImageResource(R.id.iv_go, R.mipmap.ic_tick_default);
        } else {
            this.selectRoleIds.add(Long.valueOf(role.getObjectId()));
            baseViewHolder.setImageResource(R.id.iv_go, R.mipmap.ic_tick_sel);
        }
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectRoleIds.size()));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int selectCount) {
        if (this.selectAll) {
            ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_cancel_select_all));
        } else {
            ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        }
        if (this.alreadyAdd) {
            this.okTv.setText(String.format(getResources().getString(R.string.str_cancel_add_with_num), Integer.valueOf(selectCount)));
        } else {
            this.okTv.setText(String.format(getResources().getString(R.string.str_add_with_num), Integer.valueOf(selectCount)));
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
        if (this.alreadyAdd) {
            this.okTv.setText(String.format(getResources().getString(R.string.str_cancel_add_with_num), Integer.valueOf(this.selectRoleIds.size())));
        } else {
            this.okTv.setText(String.format(getResources().getString(R.string.str_add_with_num), Integer.valueOf(this.selectRoleIds.size())));
        }
        if (this.selectFt != null) {
            this.selectFt.getData();
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        if (this.showType == 3 && device.getMacdeviceid() == this.deviceId) {
            if (this.alreadyAdd) {
                return device.getDaliHidden() == 0;
            }
            if (device.getDaliHidden() == 1) {
                return true;
            }
        }
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        if (this.showType == 2 && group.getMacdeviceid() == this.deviceId) {
            if (this.alreadyAdd) {
                return group.getDaliHidden() == 0;
            }
            if (group.getDaliHidden() == 1) {
                return true;
            }
        }
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterScene(Scene scene) {
        if (this.showType == 1 && scene.getMacdeviceid() == this.deviceId) {
            if (this.alreadyAdd) {
                return scene.getDaliHidden() == 0;
            }
            if (scene.getDaliHidden() == 1) {
                return true;
            }
        }
        return false;
    }
}