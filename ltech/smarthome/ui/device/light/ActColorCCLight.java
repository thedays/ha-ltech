package com.ltech.smarthome.ui.device.light;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActColorLightCcBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.singleton.PathManager;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelClipPhoto;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectPhoto;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.mode.ActMode;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.VersionUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageHelper;
import com.yuyh.library.imgsel.config.ISListConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActColorCCLight extends BaseControlActivity<ActColorLightCcBinding, ActColorCCLightVM> {
    private static final int REQUEST_CAPTURE = 100;
    private static final int REQUEST_WRITE_STORAGE = 3;
    private BaseQuickAdapter<GoItem, BaseViewHolder> actionAdapter;
    private List<GoItem> actionList;
    private File camerafile;
    private int currentTab;
    private MutableLiveData<Integer> chooseTabEvent = new MutableLiveData<>(0);
    private List<BaseNormalFragment> fragmentList = new ArrayList();
    int onNum = 0;
    int offNum = 0;
    int offline = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_color_light_cc;
    }

    public void initFragmentList() {
        this.fragmentList.clear();
        this.fragmentList.add(new FtColorCircle());
        this.fragmentList.add(new FtColorPushrod());
        this.fragmentList.add(new FtColorCCT());
        this.fragmentList.add(new FtColorHSL());
        this.fragmentList.add(new FtColorXXY());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActColorCCLightVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActColorCCLightVM) this.mViewModel).colorType = getIntent().getIntExtra(Constants.LIGHT_TYPE, 3);
        ((ActColorCCLightVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        setActionView();
        initFragmentList();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, this.fragmentList.get(0)).commit();
        ((ActColorLightCcBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActColorCCLight.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        switch (view.getId()) {
            case R.id.tv_cct /* 2131298506 */:
                this.chooseTabEvent.setValue(2);
                break;
            case R.id.tv_general /* 2131298674 */:
                this.chooseTabEvent.setValue(0);
                ((ActColorCCLightVM) this.mViewModel).refreshObject.setValue(false);
                break;
            case R.id.tv_gray /* 2131298676 */:
                this.chooseTabEvent.setValue(1);
                ((ActColorCCLightVM) this.mViewModel).refreshObject.setValue(false);
                break;
            case R.id.tv_hsl /* 2131298693 */:
                this.chooseTabEvent.setValue(3);
                break;
            case R.id.tv_xyy /* 2131299113 */:
                this.chooseTabEvent.setValue(4);
                break;
        }
    }

    private void changeFragment(int tabPosition) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.hide(this.fragmentList.get(this.currentTab));
        this.currentTab = tabPosition;
        if (this.fragmentList.get(tabPosition).isAdded()) {
            beginTransaction.show(this.fragmentList.get(this.currentTab));
        } else {
            beginTransaction.add(R.id.fragment_container, this.fragmentList.get(this.currentTab));
        }
        beginTransaction.commit();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActColorCCLightVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActColorCCLightVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActColorCCLight.this.lambda$startObserve$1(obj);
            }
        });
        ((ActColorCCLightVM) this.mViewModel).stateOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActColorCCLight.this.lambda$startObserve$2((Boolean) obj);
            }
        });
        this.chooseTabEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActColorCCLight.this.lambda$startObserve$3((Integer) obj);
            }
        });
        ((ActColorCCLightVM) this.mViewModel).stateOnUI.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActColorCCLight.this.onNum += ActColorCCLight.this.offNum;
                    ActColorCCLight.this.offNum = 0;
                } else {
                    ActColorCCLight actColorCCLight = ActColorCCLight.this;
                    actColorCCLight.offNum = actColorCCLight.onNum + ActColorCCLight.this.offNum;
                    ActColorCCLight.this.onNum = 0;
                }
                StringBuilder sb = new StringBuilder();
                if (ActColorCCLight.this.onNum > 0) {
                    sb.append(String.format(ActColorCCLight.this.getString(R.string.app_str_group_sub_status_title_on), Integer.valueOf(ActColorCCLight.this.onNum)));
                }
                if (ActColorCCLight.this.offNum > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActColorCCLight.this.getString(R.string.app_str_group_sub_status_title_off), Integer.valueOf(ActColorCCLight.this.offNum)));
                }
                if (ActColorCCLight.this.offline > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActColorCCLight.this.getString(R.string.app_str_group_sub_status_title_offline), Integer.valueOf(ActColorCCLight.this.offline)));
                }
                ActColorCCLight.this.setSubTitle(sb.toString());
            }
        });
        ((ActColorCCLightVM) this.mViewModel).gradientMode.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActColorCCLight.this.setGradientActionView();
                } else {
                    ActColorCCLight.this.setActionView();
                }
            }
        });
        ((ActColorCCLightVM) this.mViewModel).showPhotoAlbumEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                if (ActColorCCLight.this.checkWriteStoragePermission(3)) {
                    ActColorCCLight.this.goPhotoSelect();
                }
            }
        });
        ((ActColorCCLightVM) this.mViewModel).showTakePicEvent.observe(this, new Observer<Void>() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Void unused) {
                ActColorCCLight.this.goCamera();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Object obj) {
        if (obj instanceof Group) {
            Group group = (Group) obj;
            setTitle(group.getGroupName());
            ((ActColorCCLightVM) this.mViewModel).deviceState = group.getGroupState();
            ((ActColorCCLightVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(group.getGroupState().isOn()));
        } else {
            Device device = (Device) obj;
            setTitle(device.getDeviceName());
            ((ActColorCCLightVM) this.mViewModel).deviceState = device.getDeviceState();
            ((ActColorCCLightVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
        }
        ((ActColorCCLightVM) this.mViewModel).refreshObject.setValue(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Boolean bool) {
        if (bool.booleanValue()) {
            this.actionList.get(0).setImageRes(R.drawable.selector_power_on_bg_rgb);
        } else {
            this.actionList.get(0).setImageRes(R.drawable.selector_power_off_bg_rgb);
        }
        this.actionAdapter.setData(0, this.actionList.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Integer num) {
        AppCompatTextView appCompatTextView = ((ActColorLightCcBinding) this.mViewBinding).tvGeneral;
        int intValue = num.intValue();
        int i = R.drawable.shape_white_round_bg_10;
        appCompatTextView.setBackgroundResource(intValue == 0 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActColorLightCcBinding) this.mViewBinding).tvGeneral.setTypeface(num.intValue() == 0 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActColorLightCcBinding) this.mViewBinding).tvGray.setBackgroundResource(num.intValue() == 1 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActColorLightCcBinding) this.mViewBinding).tvGray.setTypeface(num.intValue() == 1 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActColorLightCcBinding) this.mViewBinding).tvCct.setBackgroundResource(num.intValue() == 2 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActColorLightCcBinding) this.mViewBinding).tvCct.setTypeface(num.intValue() == 2 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActColorLightCcBinding) this.mViewBinding).tvHsl.setBackgroundResource(num.intValue() == 3 ? R.drawable.shape_white_round_bg_10 : 0);
        ((ActColorLightCcBinding) this.mViewBinding).tvHsl.setTypeface(num.intValue() == 3 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        AppCompatTextView appCompatTextView2 = ((ActColorLightCcBinding) this.mViewBinding).tvXyy;
        if (num.intValue() != 4) {
            i = 0;
        }
        appCompatTextView2.setBackgroundResource(i);
        ((ActColorLightCcBinding) this.mViewBinding).tvXyy.setTypeface(num.intValue() == 4 ? Typeface.defaultFromStyle(1) : Typeface.defaultFromStyle(0));
        ((ActColorLightCcBinding) this.mViewBinding).line1.setVisibility((num.intValue() == 0 || num.intValue() == 1) ? 8 : 0);
        ((ActColorLightCcBinding) this.mViewBinding).line2.setVisibility((num.intValue() == 1 || num.intValue() == 2) ? 8 : 0);
        ((ActColorLightCcBinding) this.mViewBinding).line3.setVisibility((num.intValue() == 2 || num.intValue() == 3) ? 8 : 0);
        ((ActColorLightCcBinding) this.mViewBinding).line4.setVisibility((num.intValue() == 3 || num.intValue() == 4) ? 8 : 0);
        changeFragment(num.intValue());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActColorCCLightVM) this.mViewModel).groupControl) {
            this.offline = 0;
            this.onNum = 0;
            this.offNum = 0;
            final Group groupById = Injection.repo().group().getGroupById(((ActColorCCLightVM) this.mViewModel).controlId);
            if (groupById != null) {
                if (!groupById.getDeviceIds().isEmpty()) {
                    if (groupById.getLeaderSup() == 1) {
                        MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda14
                            @Override // com.smart.message.MessageManager.LightStatusCallBack
                            public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                                ActColorCCLight.this.lambda$startObjectObserve$4(groupById, i, z, i2, i3, i4, z2, z3, z4, i5);
                            }
                        });
                        CmdAssistant.getQueryCmdAssistant(groupById, new int[0]).queryLightState(ActivityUtils.getTopActivity());
                    } else {
                        List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(groupById.getDeviceIds());
                        final Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(groupById.getDeviceIds().get(0).longValue());
                        if (deviceByDeviceId != null) {
                            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda15
                                @Override // com.smart.message.MessageManager.LightStatusCallBack
                                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                                    ActColorCCLight.this.lambda$startObjectObserve$5(deviceByDeviceId, groupById, i, z, i2, i3, i4, z2, z3, z4, i5);
                                }
                            });
                            CmdAssistant.getQueryCmdAssistant(deviceByDeviceId, new int[0]).queryLightState(this);
                        }
                        for (Device device : devicesByIds) {
                            if (!device.getDeviceState().isOnline()) {
                                this.offline++;
                            } else if (device.getDeviceState().isOn()) {
                                this.onNum++;
                            } else {
                                this.offNum++;
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        if (this.onNum > 0) {
                            sb.append(String.format(getString(R.string.app_str_group_sub_status_title_on), Integer.valueOf(this.onNum)));
                        }
                        if (this.offNum > 0) {
                            if (sb.length() > 0) {
                                sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                                sb.append(" ");
                            }
                            sb.append(String.format(getString(R.string.app_str_group_sub_status_title_off), Integer.valueOf(this.offNum)));
                        }
                        if (this.offline > 0) {
                            if (sb.length() > 0) {
                                sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                                sb.append(" ");
                            }
                            sb.append(String.format(getString(R.string.app_str_group_sub_status_title_offline), Integer.valueOf(this.offline)));
                        }
                        setSubTitle(sb.toString());
                    }
                }
                ((ActColorCCLightVM) this.mViewModel).controlObject.setValue(groupById);
                return;
            }
            return;
        }
        final Device deviceById = Injection.repo().device().getDeviceById(((ActColorCCLightVM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActColorCCLightVM) this.mViewModel).controlObject.setValue(deviceById);
            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda1
                @Override // com.smart.message.MessageManager.LightStatusCallBack
                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                    ActColorCCLight.this.lambda$startObjectObserve$6(deviceById, i, z, i2, i3, i4, z2, z3, z4, i5);
                }
            });
            CmdAssistant.getQueryCmdAssistant(deviceById, new int[0]).queryLightState(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$4(Group group, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Group groupByGroupId;
        if (Boolean.TRUE.equals(((ActColorCCLightVM) this.mViewModel).gradientMode.getValue()) || i != group.getGroupAddress() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId())) == null) {
            return;
        }
        groupByGroupId.getGroupState().setOn(z);
        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        groupByGroupId.getGroupState().setWOn(i2 != 0);
        groupByGroupId.getGroupState().setRGBOn(i4 != 0);
        groupByGroupId.getGroupState().setWy(i3);
        groupByGroupId.getGroupState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        groupByGroupId.getGroupState().setTotalBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        Injection.repo().group().saveGroup(groupByGroupId);
        ((ActColorCCLightVM) this.mViewModel).controlObject.setValue(groupByGroupId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$5(Device device, Group group, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Group groupByGroupId;
        if (Boolean.TRUE.equals(((ActColorCCLightVM) this.mViewModel).gradientMode.getValue()) || i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId())) == null) {
            return;
        }
        groupByGroupId.getGroupState().setOn(z);
        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        groupByGroupId.getGroupState().setWOn(i2 != 0);
        groupByGroupId.getGroupState().setRGBOn(i4 != 0);
        groupByGroupId.getGroupState().setWy(i3);
        groupByGroupId.getGroupState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        groupByGroupId.getGroupState().setTotalBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        Injection.repo().group().saveGroup(groupByGroupId);
        ((ActColorCCLightVM) this.mViewModel).controlObject.setValue(groupByGroupId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$6(Device device, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Device deviceByDeviceId;
        if (i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId())) == null) {
            return;
        }
        deviceByDeviceId.getDeviceState().setOn(z);
        deviceByDeviceId.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        deviceByDeviceId.getDeviceState().setWOn(i2 != 0);
        deviceByDeviceId.getDeviceState().setWy(i3);
        deviceByDeviceId.getDeviceState().setRGBOn(i4 != 0);
        deviceByDeviceId.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        deviceByDeviceId.getDeviceState().setTotalBrt(LightUtils.brt2ProgressHasBelowZero(i4));
        Injection.repo().device().saveDevice(deviceByDeviceId);
        ((ActColorCCLightVM) this.mViewModel).controlObject.setValue(deviceByDeviceId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGradientActionView() {
        ArrayList arrayList = new ArrayList();
        this.actionList = arrayList;
        arrayList.add(new GoItem().setImageRes(R.drawable.selector_power_off_bg_rgb).setMainText(getString(R.string.on_off)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorCCLight.this.lambda$setGradientActionView$7();
            }
        })));
        this.actionList.add(new GoItem().setImageRes(R.drawable.selector_collect_bg_rgb).setMainText(getString(R.string.collection)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorCCLight.this.lambda$setGradientActionView$8();
            }
        })));
        this.actionList.add(new GoItem().setImageRes(R.drawable.selector_scene_bg_rgb).setMainText(getString(R.string.app_str_gradient_scene)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorCCLight.this.lambda$setGradientActionView$9();
            }
        })));
        ((ActColorLightCcBinding) this.mViewBinding).rvAction.setLayoutManager(new GridLayoutManager(this, this.actionList.size()));
        this.actionAdapter.setNewData(this.actionList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGradientActionView$7() {
        if (Boolean.TRUE.equals(((ActColorCCLightVM) this.mViewModel).stateOn.getValue())) {
            ((ActColorCCLightVM) this.mViewModel).getLightCmdHelper().sendOnOff(this.activity, false);
            ((ActColorCCLightVM) this.mViewModel).stateOn.setValue(false);
            if (((ActColorCCLightVM) this.mViewModel).groupControl) {
                ((ActColorCCLightVM) this.mViewModel).stateOnUI.setValue(false);
                return;
            }
            return;
        }
        ((ActColorCCLightVM) this.mViewModel).getLightCmdHelper().sendOnOff(this.activity, true);
        ((ActColorCCLightVM) this.mViewModel).stateOn.setValue(true);
        if (((ActColorCCLightVM) this.mViewModel).groupControl) {
            ((ActColorCCLightVM) this.mViewModel).stateOnUI.setValue(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGradientActionView$8() {
        ((ActColorCCLightVM) this.mViewModel).addGradientSceneEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGradientActionView$9() {
        NavUtils.destination(ActGradientScene.class).withLong(Constants.CONTROL_ID, ((ActColorCCLightVM) this.mViewModel).controlId).withDefaultRequestCode().navigation(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setActionView() {
        ArrayList arrayList = new ArrayList();
        this.actionList = arrayList;
        arrayList.add(new GoItem().setImageRes(R.drawable.selector_power_off_bg_rgb).setMainText(getString(R.string.on_off)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorCCLight.this.lambda$setActionView$10();
            }
        })));
        this.actionList.add(new GoItem().setImageRes(R.drawable.selector_theme_bg).setMainText(getString(R.string.theme)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorCCLight.this.lambda$setActionView$11();
            }
        })));
        this.actionList.add(new GoItem().setImageRes(R.drawable.selector_model_bg).setMainText(getString(R.string.mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActColorCCLight.this.lambda$setActionView$12();
            }
        })));
        if (((ActColorCCLightVM) this.mViewModel).groupControl) {
            this.actionList.add(new GoItem().setImageRes(R.drawable.selector_group_bg).setMainText(getString(R.string.group_device)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActColorCCLight.this.lambda$setActionView$13();
                }
            })));
        }
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_light_action, this.actionList) { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_content, item.getMainText()).setImageResource(R.id.iv_icon, item.getImageRes());
            }
        };
        this.actionAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight.6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((GoItem) ActColorCCLight.this.actionAdapter.getData().get(position)).getAction().execute();
            }
        });
        this.actionAdapter.bindToRecyclerView(((ActColorLightCcBinding) this.mViewBinding).rvAction);
        ((ActColorLightCcBinding) this.mViewBinding).rvAction.setLayoutManager(new GridLayoutManager(this, this.actionList.size()));
        ((DefaultItemAnimator) ((ActColorLightCcBinding) this.mViewBinding).rvAction.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$10() {
        if (Boolean.TRUE.equals(((ActColorCCLightVM) this.mViewModel).stateOn.getValue())) {
            ((ActColorCCLightVM) this.mViewModel).getLightCmdHelper().sendOnOff(this.activity, false);
            ((ActColorCCLightVM) this.mViewModel).stateOn.setValue(false);
            if (((ActColorCCLightVM) this.mViewModel).groupControl) {
                ((ActColorCCLightVM) this.mViewModel).stateOnUI.setValue(false);
                return;
            }
            return;
        }
        ((ActColorCCLightVM) this.mViewModel).getLightCmdHelper().sendOnOff(this.activity, true);
        ((ActColorCCLightVM) this.mViewModel).stateOn.setValue(true);
        if (((ActColorCCLightVM) this.mViewModel).groupControl) {
            ((ActColorCCLightVM) this.mViewModel).stateOnUI.setValue(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$11() {
        NavUtils.destination(ActDefaultMode.class).withLong(Constants.CONTROL_ID, ((ActColorCCLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActColorCCLightVM) this.mViewModel).groupControl).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActColorCCLightVM) this.mViewModel).controlObject.getValue())).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$12() {
        NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActColorCCLightVM) this.mViewModel).controlObject.getValue())).withLong(Constants.CONTROL_ID, ((ActColorCCLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActColorCCLightVM) this.mViewModel).groupControl).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionView$13() {
        NavUtils.destination(ActLightGroupSubItemControl.class).withLong(Constants.CONTROL_ID, ((ActColorCCLightVM) this.mViewModel).controlId).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goCamera() {
        String[] strArr;
        Object value = ((ActColorCCLightVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Group) {
            final Group group = (Group) value;
            if (VersionUtils.isAndroidQ()) {
                strArr = new String[]{Permission.CAMERA};
            } else {
                strArr = new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA};
            }
            AndPermission.with((Activity) this).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda12
                @Override // com.yanzhenjie.permission.Action
                public final void onAction(Object obj) {
                    ActColorCCLight.this.lambda$goCamera$14(group, (List) obj);
                }
            }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight$$ExternalSyntheticLambda13
                @Override // com.yanzhenjie.permission.Action
                public final void onAction(Object obj) {
                    ActColorCCLight.this.lambda$goCamera$15((List) obj);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$14(Group group, List list) {
        String cacheDir = PathManager.getCacheDir(this.activity);
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.getSuperPanelPicName(group.getGroupId() + ""));
        sb.append(System.currentTimeMillis());
        sb.append(".jpg");
        this.camerafile = new File(cacheDir, sb.toString());
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri uriForFile = FileProvider.getUriForFile(this, "com.ltech.smarthome.provider", this.camerafile);
        intent.addFlags(1);
        intent.putExtra("output", uriForFile);
        startActivityForResult(intent, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$goCamera$15(List list) {
        SmartToast.showShort(getString(R.string.permission_deny));
    }

    private void goClipPhoto() {
        Object value = ((ActColorCCLightVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            NavUtils.destination(ActSuperPanelClipPhoto.class).withBoolean(Constants.GROUP_CONTROL, true).withLong(Constants.GROUP_ID, group.getGroupId()).withString(Constants.MAC_ADDRESS, group.getGroupId() + "").withBoolean(Constants.ROUND_CUT, true).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goPhotoSelect() {
        Object value = ((ActColorCCLightVM) this.mViewModel).controlObject.getValue();
        if (value instanceof Group) {
            Group group = (Group) value;
            ISNav.getInstance().setConfig(new ISListConfig.Builder().multiSelect(true).maxNum(1).rememberSelected(false).needCamera(false).checkRes(R.mipmap.ic_img_checked, R.mipmap.ic_img_uncheck).build());
            NavUtils.destination(ActSuperPanelSelectPhoto.class).withBoolean(Constants.GROUP_CONTROL, true).withLong(Constants.GROUP_ID, group.getGroupId()).withString(Constants.MAC_ADDRESS, group.getGroupId() + "").withBoolean(Constants.ROUND_CUT, true).withDefaultRequestCode().navigation(this);
        }
    }

    @Override // com.ltech.smarthome.ui.device.base.BaseControlActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        long[] longArrayExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && 100 == requestCode) {
            ImageHelper.imageList.clear();
            ImageHelper.imageList.add(this.camerafile.getAbsolutePath());
            ImageHelper.tempImageList.clear();
            ImageHelper.tempImageList.add(this.camerafile.getAbsolutePath());
            ISNav.getInstance().setConfig(new ISListConfig.Builder().multiSelect(false).maxNum(1).rememberSelected(false).needCamera(false).build());
            goClipPhoto();
            return;
        }
        if (resultCode == 3021) {
            if (data != null) {
                ((ActColorCCLightVM) this.mViewModel).bgUrl = data.getStringExtra("data");
                Glide.with((FragmentActivity) this.activity).asBitmap().load(((ActColorCCLightVM) this.mViewModel).bgUrl).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.light.ActColorCCLight.7
                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(Drawable placeholder) {
                    }

                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object bitmap, Transition transition) {
                        onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        ((ActColorCCLightVM) ActColorCCLight.this.mViewModel).paletteBitmap.setValue(bitmap);
                    }
                });
                return;
            }
            return;
        }
        if (resultCode != 5012 || data == null || (longArrayExtra = data.getLongArrayExtra("data")) == null) {
            return;
        }
        ((ActColorCCLightVM) this.mViewModel).shortDevices.setValue(longArrayExtra);
    }
}