package com.ltech.smarthome.ui.device.light;

import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActDimLightBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.mode.ActMode;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.RectProgressBar2;
import com.smart.message.MessageManager;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDimLight extends BaseControlActivity<ActDimLightBinding, ActDimLightVM> {
    private boolean hasRead;
    private int wy_brt = 1;
    private boolean firstIn = true;
    int onNum = 0;
    int offNum = 0;
    int offline = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dim_light;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        ((ActDimLightVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDimLightVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        if (isE6()) {
            setTitle(getString(R.string.knob_control));
        } else {
            setEditImage(R.mipmap.ic_setting);
        }
        ((ActDimLightBinding) this.mViewBinding).progressBar.setOnProgressChangeListener(new RectProgressBar2.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight.1
            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStartProgressChanged(RectProgressBar2 bar) {
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onProgressChanged(RectProgressBar2 bar) {
                LHomeLog.i(getClass(), "onProgressChanged brt-->" + bar.getProgressPercent());
                ((ActDimLightVM) ActDimLight.this.mViewModel).getLightAssistant().sendDimBrtHas1to9(ActDimLight.this, bar.getProgress(), false);
                ((ActDimLightBinding) ActDimLight.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStopProgressChanged(RectProgressBar2 bar) {
                ((ActDimLightVM) ActDimLight.this.mViewModel).getLightAssistant().sendDimBrtHas1to9(ActDimLight.this, bar.getProgress(), true);
                ((ActDimLightBinding) ActDimLight.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }
        });
        ((ActDimLightBinding) this.mViewBinding).tvBrt.setText(((ActDimLightBinding) this.mViewBinding).progressBar.getProgressPercent());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActDimLightVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActDimLightVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDimLight.this.lambda$startObserve$0(obj);
            }
        });
        ((ActDimLightVM) this.mViewModel).stateOn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDimLight.this.lambda$startObserve$1((Boolean) obj);
            }
        });
        ((ActDimLightVM) this.mViewModel).stateOnUI.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActDimLight.this.onNum += ActDimLight.this.offNum;
                    ActDimLight.this.offNum = 0;
                } else {
                    ActDimLight actDimLight = ActDimLight.this;
                    actDimLight.offNum = actDimLight.onNum + ActDimLight.this.offNum;
                    ActDimLight.this.onNum = 0;
                }
                StringBuilder sb = new StringBuilder();
                if (ActDimLight.this.onNum > 0) {
                    sb.append(String.format(ActDimLight.this.getString(R.string.app_str_group_sub_status_title_on), Integer.valueOf(ActDimLight.this.onNum)));
                }
                if (ActDimLight.this.offNum > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActDimLight.this.getString(R.string.app_str_group_sub_status_title_off), Integer.valueOf(ActDimLight.this.offNum)));
                }
                if (ActDimLight.this.offline > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActDimLight.this.getString(R.string.app_str_group_sub_status_title_offline), Integer.valueOf(ActDimLight.this.offline)));
                }
                ActDimLight.this.setSubTitle(sb.toString());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        if (obj instanceof Group) {
            Group group = (Group) obj;
            if (!isE6()) {
                setTitle(group.getGroupName());
            }
            this.wy_brt = group.getGroupState().getWyBrt();
            ((ActDimLightVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(group.getGroupState().isOn()));
        } else {
            Device device = (Device) obj;
            if (!isE6()) {
                setTitle(device.getDeviceName());
            }
            this.wy_brt = device.getDeviceState().getWyBrt();
            ((ActDimLightVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
        }
        int progress = ((ActDimLightBinding) this.mViewBinding).progressBar.getProgress();
        ((ActDimLightBinding) this.mViewBinding).progressBar.setCurrentProgress(this.wy_brt);
        ((ActDimLightBinding) this.mViewBinding).tvBrt.setText(((ActDimLightBinding) this.mViewBinding).progressBar.getProgressPercent());
        if (!this.firstIn) {
            ((ActDimLightBinding) this.mViewBinding).progressBar.setAnimation(progress, ((ActDimLightBinding) this.mViewBinding).progressBar.getProgress());
        } else {
            ((ActDimLightBinding) this.mViewBinding).progressBar.invalidate();
        }
        this.firstIn = false;
        setActionListView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Boolean bool) {
        if (bool.booleanValue()) {
            ((ActDimLightBinding) this.mViewBinding).progressBar.setProgressColor(getResources().getColor(R.color.progress_color));
            ((ActDimLightBinding) this.mViewBinding).progressBar.setCanChangeProgress(true);
            ((ActDimLightBinding) this.mViewBinding).ivOpen.setBackgroundResource(R.drawable.selector_power_on_bg);
        } else {
            ((ActDimLightBinding) this.mViewBinding).progressBar.setProgressColor(getResources().getColor(R.color.progress_color_close));
            ((ActDimLightBinding) this.mViewBinding).progressBar.setCanChangeProgress(false);
            ((ActDimLightBinding) this.mViewBinding).ivOpen.setBackgroundResource(R.drawable.selector_power_off_bg);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActDimLightVM) this.mViewModel).groupControl) {
            this.offline = 0;
            this.onNum = 0;
            this.offNum = 0;
            final Group groupById = Injection.repo().group().getGroupById(((ActDimLightVM) this.mViewModel).controlId);
            if (groupById != null) {
                if (!groupById.getDeviceIds().isEmpty()) {
                    if (groupById.getLeaderSup() == 1) {
                        MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight$$ExternalSyntheticLambda3
                            @Override // com.smart.message.MessageManager.LightStatusCallBack
                            public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                                ActDimLight.this.lambda$startObjectObserve$2(groupById, i, z, i2, i3, i4, z2, z3, z4, i5);
                            }
                        });
                        CmdAssistant.getQueryCmdAssistant(groupById, new int[0]).queryLightState(ActivityUtils.getTopActivity());
                    } else {
                        List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(groupById.getDeviceIds());
                        final Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(groupById.getDeviceIds().get(0).longValue());
                        if (deviceByDeviceId != null) {
                            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight$$ExternalSyntheticLambda4
                                @Override // com.smart.message.MessageManager.LightStatusCallBack
                                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                                    ActDimLight.this.lambda$startObjectObserve$3(deviceByDeviceId, groupById, i, z, i2, i3, i4, z2, z3, z4, i5);
                                }
                            });
                            CmdAssistant.getQueryCmdAssistant(deviceByDeviceId, new int[0]).queryLightState(ActivityUtils.getTopActivity());
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
                ((ActDimLightVM) this.mViewModel).controlObject.setValue(groupById);
                return;
            }
            return;
        }
        final Device deviceById = Injection.repo().device().getDeviceById(((ActDimLightVM) this.mViewModel).controlId);
        if (deviceById != null) {
            ((ActDimLightVM) this.mViewModel).controlObject.setValue(deviceById);
            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight$$ExternalSyntheticLambda5
                @Override // com.smart.message.MessageManager.LightStatusCallBack
                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                    ActDimLight.this.lambda$startObjectObserve$4(deviceById, i, z, i2, i3, i4, z2, z3, z4, i5);
                }
            });
            CmdAssistant.getQueryCmdAssistant(deviceById, new int[0]).queryLightState(ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$2(Group group, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Group groupByGroupId;
        if (i != group.getGroupAddress() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId())) == null) {
            return;
        }
        groupByGroupId.getGroupState().setOn(z);
        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        Injection.repo().group().saveGroup(groupByGroupId);
        ((ActDimLightVM) this.mViewModel).controlObject.setValue(groupByGroupId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$3(Device device, Group group, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Group groupByGroupId;
        if (i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId())) == null) {
            return;
        }
        groupByGroupId.getGroupState().setOn(z);
        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        Injection.repo().group().saveGroup(groupByGroupId);
        ((ActDimLightVM) this.mViewModel).controlObject.setValue(groupByGroupId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$4(Device device, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Device deviceByDeviceId;
        if (i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId())) == null) {
            return;
        }
        deviceByDeviceId.getDeviceState().setOn(z);
        deviceByDeviceId.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        Injection.repo().device().saveDevice(deviceByDeviceId);
        ((ActDimLightVM) this.mViewModel).controlObject.setValue(deviceByDeviceId);
    }

    private void setActionListView() {
        if (!ProductRepository.supportDynamicMode(((ActDimLightVM) this.mViewModel).controlObject.getValue())) {
            ((ActDimLightBinding) this.mViewBinding).rvMode.setVisibility(8);
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new GoItem().setImageRes(R.drawable.selector_theme_bg).setMainText(getString(R.string.theme)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActDimLight.this.lambda$setActionListView$5();
            }
        })));
        arrayList.add(new GoItem().setImageRes(R.drawable.selector_model_bg).setMainText(getString(R.string.ir_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActDimLight.this.lambda$setActionListView$6();
            }
        })));
        if (((ActDimLightVM) this.mViewModel).groupControl) {
            arrayList.add(new GoItem().setImageRes(R.drawable.selector_group_bg).setMainText(getString(R.string.group_device)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActDimLight$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActDimLight.this.lambda$setActionListView$7();
                }
            })));
        }
        final BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_light_action, arrayList) { // from class: com.ltech.smarthome.ui.device.light.ActDimLight.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_content, item.getMainText()).setImageResource(R.id.iv_icon, item.getImageRes());
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(this) { // from class: com.ltech.smarthome.ui.device.light.ActDimLight.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((GoItem) baseQuickAdapter.getData().get(position)).getAction().execute();
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActDimLightBinding) this.mViewBinding).rvMode);
        ((ActDimLightBinding) this.mViewBinding).rvMode.setLayoutManager(new GridLayoutManager(this, arrayList.size()));
        ((ActDimLightBinding) this.mViewBinding).rvMode.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionListView$5() {
        NavUtils.destination(ActDefaultMode.class).withLong(Constants.CONTROL_ID, ((ActDimLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActDimLightVM) this.mViewModel).groupControl).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActDimLightVM) this.mViewModel).controlObject.getValue())).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionListView$6() {
        NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActDimLightVM) this.mViewModel).controlObject.getValue())).withLong(Constants.CONTROL_ID, ((ActDimLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActDimLightVM) this.mViewModel).groupControl).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionListView$7() {
        NavUtils.destination(ActLightGroupSubItemControl.class).withLong(Constants.CONTROL_ID, ((ActDimLightVM) this.mViewModel).controlId).navigation(this);
    }
}