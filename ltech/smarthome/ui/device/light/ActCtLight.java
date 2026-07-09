package com.ltech.smarthome.ui.device.light;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.loc.at;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActCtLightBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.KValue;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.light.CtControlHelper;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.mode.ActMode;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.RectProgressBar2;
import com.smart.message.MessageManager;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class ActCtLight extends BaseControlActivity<ActCtLightBinding, ActCtLightVM> {
    private BaseQuickAdapter<KValue, BaseViewHolder> colorAdapter;
    private List<KValue> colorList;
    private CtControlHelper ctControlHelper;
    private int wy1 = 1;
    private boolean firstIn = true;
    private int curIndex = -1;
    int onNum = 0;
    int offNum = 0;
    int offline = 0;
    int rhythmNum = 0;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_ct_light;
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        ((ActCtLightVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActCtLightVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
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
        ((ActCtLightBinding) this.mViewBinding).sbBrt.setOnProgressChangeListener(new RectProgressBar2.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight.1
            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStartProgressChanged(RectProgressBar2 bar) {
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onProgressChanged(RectProgressBar2 bar) {
                ((ActCtLightVM) ActCtLight.this.mViewModel).getLightAssistant().sendCtBrtHas1to9(ActCtLight.this, bar.getProgress(), false);
                ((ActCtLightBinding) ActCtLight.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }

            @Override // com.ltech.smarthome.view.RectProgressBar2.OnProgressChangeListener
            public void onStopProgressChanged(RectProgressBar2 bar) {
                ((ActCtLightVM) ActCtLight.this.mViewModel).getLightAssistant().sendCtBrtHas1to9(ActCtLight.this, bar.getProgress(), true);
                if (ActCtLight.this.mViewBinding != null) {
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
                    ActCtLight.this.wy1 = bar.getProgress();
                }
            }
        });
        ((ActCtLightBinding) this.mViewBinding).tvBrt.setText(((ActCtLightBinding) this.mViewBinding).sbBrt.getProgressPercent());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        NavHelper.goSetting(((ActCtLightVM) this.mViewModel).controlObject.getValue());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActCtLightVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActCtLight.this.lambda$startObserve$0(obj);
            }
        });
        ((ActCtLightVM) this.mViewModel).stateOn.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                ((ActCtLightBinding) ActCtLight.this.mViewBinding).ctsb.setEnabled(aBoolean.booleanValue());
                if (aBoolean.booleanValue()) {
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.setProgressColor(ActCtLight.this.getResources().getColor(R.color.progress_color));
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.setCanChangeProgress(true);
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).ivOpen.setBackgroundResource(R.drawable.selector_power_on_bg);
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).ctView.setVisibility(8);
                } else {
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.setProgressColor(ActCtLight.this.getResources().getColor(R.color.progress_color_close));
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.setCanChangeProgress(false);
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).ivOpen.setBackgroundResource(R.drawable.selector_power_off_bg);
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).ctView.setVisibility(0);
                }
                int progress = ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.getProgress();
                ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.setCurrentProgress(aBoolean.booleanValue() ? ActCtLight.this.wy1 : 0);
                ((ActCtLightBinding) ActCtLight.this.mViewBinding).tvBrt.setText(((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.getProgressPercent());
                if (!ActCtLight.this.firstIn) {
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.setAnimation(progress, ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.getProgress());
                } else {
                    ((ActCtLightBinding) ActCtLight.this.mViewBinding).sbBrt.invalidate();
                }
                ActCtLight.this.firstIn = false;
            }
        });
        ((ActCtLightVM) this.mViewModel).stateOnUI.observe(this, new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ActCtLight.this.onNum += ActCtLight.this.offNum;
                    ActCtLight.this.offNum = 0;
                } else {
                    ActCtLight actCtLight = ActCtLight.this;
                    actCtLight.offNum = actCtLight.onNum + ActCtLight.this.offNum;
                    ActCtLight.this.onNum = 0;
                }
                StringBuilder sb = new StringBuilder();
                if (ActCtLight.this.onNum > 0) {
                    sb.append(String.format(ActCtLight.this.getString(R.string.app_str_group_sub_status_title_on), Integer.valueOf(ActCtLight.this.onNum)));
                }
                if (ActCtLight.this.offNum > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActCtLight.this.getString(R.string.app_str_group_sub_status_title_off), Integer.valueOf(ActCtLight.this.offNum)));
                }
                if (ActCtLight.this.offline > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActCtLight.this.getString(R.string.app_str_group_sub_status_title_offline), Integer.valueOf(ActCtLight.this.offline)));
                }
                if (aBoolean.booleanValue() && ActCtLight.this.rhythmNum > 0) {
                    if (sb.length() > 0) {
                        sb.append(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SP);
                        sb.append(" ");
                    }
                    sb.append(String.format(ActCtLight.this.getString(R.string.app_str_group_sub_status_title_rhythm), Integer.valueOf(ActCtLight.this.rhythmNum)));
                }
                ActCtLight.this.setSubTitle(sb.toString());
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
            this.wy1 = group.getGroupState().getWyBrt();
            ((ActCtLightVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(group.getGroupState().isOn()));
        } else {
            Device device = (Device) obj;
            if (!isE6()) {
                setTitle(device.getDeviceName());
            }
            this.wy1 = device.getDeviceState().getWyBrt();
            ((ActCtLightVM) this.mViewModel).stateOn.setValue(Boolean.valueOf(device.getDeviceState().isOn()));
        }
        this.ctControlHelper = new CtControlHelper(((ActCtLightBinding) this.mViewBinding).ctsb, ((ActCtLightBinding) this.mViewBinding).tvWy, obj, new CtControlHelper.OnCtChangedListener() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight.2
            @Override // com.ltech.smarthome.ui.device.light.CtControlHelper.OnCtChangedListener
            public void onRangeChanged(int v, boolean finish) {
                ((ActCtLightVM) ActCtLight.this.mViewModel).getLightAssistant().sendCW(ActCtLight.this, v, finish);
            }
        });
        setColorView();
        setActionListView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        startObjectObserve();
    }

    private void startObjectObserve() {
        if (((ActCtLightVM) this.mViewModel).groupControl) {
            this.offline = 0;
            this.onNum = 0;
            this.offNum = 0;
            this.rhythmNum = 0;
            final Group groupById = Injection.repo().group().getGroupById(((ActCtLightVM) this.mViewModel).controlId);
            if (groupById != null) {
                if (!groupById.getDeviceIds().isEmpty()) {
                    if (groupById.getLeaderSup() == 1) {
                        MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight$$ExternalSyntheticLambda4
                            @Override // com.smart.message.MessageManager.LightStatusCallBack
                            public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                                ActCtLight.this.lambda$startObjectObserve$1(groupById, i, z, i2, i3, i4, z2, z3, z4, i5);
                            }
                        });
                        CmdAssistant.getQueryCmdAssistant(groupById, new int[0]).queryLightState(ActivityUtils.getTopActivity());
                    } else {
                        List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(groupById.getDeviceIds());
                        final Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(groupById.getDeviceIds().get(0).longValue());
                        if (deviceByDeviceId != null) {
                            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight$$ExternalSyntheticLambda5
                                @Override // com.smart.message.MessageManager.LightStatusCallBack
                                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                                    ActCtLight.this.lambda$startObjectObserve$2(deviceByDeviceId, groupById, i, z, i2, i3, i4, z2, z3, z4, i5);
                                }
                            });
                            CmdAssistant.getQueryCmdAssistant(deviceByDeviceId, new int[0]).queryLightState(ActivityUtils.getTopActivity());
                        }
                        for (Device device : devicesByIds) {
                            if (!device.getDeviceState().isOnline()) {
                                this.offline++;
                            } else if (device.getDeviceState().isOn()) {
                                this.onNum++;
                                if (device.getDeviceState().isRhythmPlay()) {
                                    this.rhythmNum++;
                                }
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
                ((ActCtLightVM) this.mViewModel).controlObject.setValue(groupById);
                return;
            }
            return;
        }
        final Device deviceById = Injection.repo().device().getDeviceById(((ActCtLightVM) this.mViewModel).controlId);
        if (deviceById != null) {
            if (deviceById.getDeviceState().isOn() && deviceById.getDeviceState().isRhythmPlay()) {
                setSubTitle(String.format(getString(R.string.app_str_rhythm_playing), new Object[0]));
            }
            ((ActCtLightVM) this.mViewModel).controlObject.setValue(deviceById);
            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight$$ExternalSyntheticLambda6
                @Override // com.smart.message.MessageManager.LightStatusCallBack
                public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                    ActCtLight.this.lambda$startObjectObserve$3(deviceById, i, z, i2, i3, i4, z2, z3, z4, i5);
                }
            });
            CmdAssistant.getQueryCmdAssistant(deviceById, new int[0]).queryLightState(ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$1(Group group, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Group groupByGroupId;
        if (i != group.getGroupAddress() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId())) == null) {
            return;
        }
        groupByGroupId.getGroupState().setOn(z);
        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        groupByGroupId.getGroupState().setWy(i3);
        Injection.repo().group().saveGroup(groupByGroupId);
        ((ActCtLightVM) this.mViewModel).controlObject.setValue(groupByGroupId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$2(Device device, Group group, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Group groupByGroupId;
        if (i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId())) == null) {
            return;
        }
        groupByGroupId.getGroupState().setOn(z);
        groupByGroupId.getGroupState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        groupByGroupId.getGroupState().setWy(i3);
        Injection.repo().group().saveGroup(groupByGroupId);
        ((ActCtLightVM) this.mViewModel).controlObject.setValue(groupByGroupId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObjectObserve$3(Device device, int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        Device deviceByDeviceId;
        if (i != ((BleParam) device.getParam(BleParam.class)).getUnicastAddress() || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId())) == null) {
            return;
        }
        deviceByDeviceId.getDeviceState().setOn(z);
        deviceByDeviceId.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(i2));
        deviceByDeviceId.getDeviceState().setWy(i3);
        Injection.repo().device().saveDevice(deviceByDeviceId);
        ((ActCtLightVM) this.mViewModel).controlObject.setValue(deviceByDeviceId);
    }

    private void getColorList() {
        int i;
        Map<String, KValue> presetKValues;
        int[] iArr = {2700, 3500, 4000, 5000, 6000, 6500};
        int[] intArray = getResources().getIntArray(R.array.k_value_color);
        this.colorList = new ArrayList();
        int i2 = 0;
        while (true) {
            if (i2 >= 6) {
                break;
            }
            this.colorList.add(new KValue(iArr[i2], String.format("#%06X", Integer.valueOf(intArray[i2] & 16777215))));
            i2++;
        }
        if (((ActCtLightVM) this.mViewModel).groupControl) {
            presetKValues = ((Group) ((ActCtLightVM) this.mViewModel).controlObject.getValue()).getPresetKValues();
        } else {
            presetKValues = ((Device) ((ActCtLightVM) this.mViewModel).controlObject.getValue()).getPresetKValues();
        }
        if (presetKValues != null) {
            for (i = 1; i <= 6; i++) {
                if (presetKValues.containsKey(String.valueOf(i))) {
                    this.colorList.set(i - 1, presetKValues.get(String.valueOf(i)));
                }
            }
        }
    }

    private void setColorView() {
        getColorList();
        this.curIndex = -1;
        if (this.colorAdapter == null) {
            BaseQuickAdapter<KValue, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<KValue, BaseViewHolder>(R.layout.item_ct_color_edit) { // from class: com.ltech.smarthome.ui.device.light.ActCtLight.5
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, KValue k) {
                    helper.setText(R.id.sel_tv, k.getValue() + at.k);
                    CircleImageView circleImageView = (CircleImageView) helper.getView(R.id.iv);
                    circleImageView.getLayoutParams().width = SizeUtils.dp2px(40.0f);
                    circleImageView.getLayoutParams().height = SizeUtils.dp2px(40.0f);
                    circleImageView.setImageDrawable(new ColorDrawable(Color.parseColor(((KValue) ActCtLight.this.colorList.get(helper.getLayoutPosition())).getColor())));
                    CircleImageView circleImageView2 = (CircleImageView) helper.getView(R.id.iv_circle);
                    circleImageView2.setImageDrawable(new ColorDrawable(0));
                    circleImageView2.setVisibility(ActCtLight.this.curIndex != helper.getLayoutPosition() ? 8 : 0);
                    if (ActCtLight.this.curIndex == helper.getLayoutPosition()) {
                        helper.setText(R.id.sel_tv, R.string.edit);
                    }
                }
            };
            this.colorAdapter = baseQuickAdapter;
            baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight$$ExternalSyntheticLambda7
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                    ActCtLight.this.lambda$setColorView$4(baseQuickAdapter2, view, i);
                }
            });
            this.colorAdapter.bindToRecyclerView(((ActCtLightBinding) this.mViewBinding).rvColor);
            ((ActCtLightBinding) this.mViewBinding).rvColor.setLayoutManager(new LinearLayoutManager(this, 0, false));
            ((ActCtLightBinding) this.mViewBinding).rvColor.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.light.ActCtLight.6
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(ConvertUtils.dp2px(10.0f), 0, ConvertUtils.dp2px(10.0f), 0);
                }
            });
        }
        this.colorAdapter.replaceData(this.colorList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setColorView$4(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int ctK2Y = 255 - LightUtils.ctK2Y(Integer.parseInt(this.colorAdapter.getData().get(i).getValue()), this.ctControlHelper.getKMax(), this.ctControlHelper.getkMin());
        this.ctControlHelper.setProgress(ctK2Y);
        ((ActCtLightVM) this.mViewModel).getLightAssistant().sendCW(this.activity, ctK2Y, true);
        if (isE6()) {
            return;
        }
        if (this.curIndex != i) {
            this.curIndex = i;
            this.colorAdapter.notifyDataSetChanged();
        } else {
            NavUtils.destination(ActCtEdit.class).withLong(Constants.CONTROL_ID, ((ActCtLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActCtLightVM) this.mViewModel).groupControl).withInt("index", this.curIndex + 1).navigation(this);
        }
    }

    private void setActionListView() {
        if (!ProductRepository.supportDynamicMode(((ActCtLightVM) this.mViewModel).controlObject.getValue())) {
            ((ActCtLightBinding) this.mViewBinding).rvMode.setVisibility(8);
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new GoItem().setImageRes(R.drawable.selector_theme_bg).setMainText(getString(R.string.theme)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActCtLight.this.lambda$setActionListView$5();
            }
        })));
        arrayList.add(new GoItem().setImageRes(R.drawable.selector_model_bg).setMainText(getString(R.string.ir_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActCtLight.this.lambda$setActionListView$6();
            }
        })));
        if (((ActCtLightVM) this.mViewModel).groupControl) {
            arrayList.add(new GoItem().setImageRes(R.drawable.selector_group_bg).setMainText(getString(R.string.group_device)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.ActCtLight$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActCtLight.this.lambda$setActionListView$7();
                }
            })));
        }
        final BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_light_action, arrayList) { // from class: com.ltech.smarthome.ui.device.light.ActCtLight.7
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_content, item.getMainText()).setImageResource(R.id.iv_icon, item.getImageRes());
            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(this) { // from class: com.ltech.smarthome.ui.device.light.ActCtLight.8
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((GoItem) baseQuickAdapter.getData().get(position)).getAction().execute();
            }
        });
        baseQuickAdapter.bindToRecyclerView(((ActCtLightBinding) this.mViewBinding).rvMode);
        ((ActCtLightBinding) this.mViewBinding).rvMode.setLayoutManager(new GridLayoutManager(this, arrayList.size()));
        ((ActCtLightBinding) this.mViewBinding).rvMode.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionListView$5() {
        NavUtils.destination(ActDefaultMode.class).withLong(Constants.CONTROL_ID, ((ActCtLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActCtLightVM) this.mViewModel).groupControl).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActCtLightVM) this.mViewModel).controlObject.getValue())).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionListView$6() {
        NavUtils.destination(ActMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(((ActCtLightVM) this.mViewModel).controlObject.getValue())).withLong(Constants.CONTROL_ID, ((ActCtLightVM) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, ((ActCtLightVM) this.mViewModel).groupControl).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setActionListView$7() {
        NavUtils.destination(ActLightGroupSubItemControl.class).withLong(Constants.CONTROL_ID, ((ActCtLightVM) this.mViewModel).controlId).navigation(this);
    }
}