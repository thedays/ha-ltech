package com.ltech.smarthome.ui.device.eurpanel;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActEurPanelGroupSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting;
import com.ltech.smarthome.ui.group.ActAddWiFiGroup;
import com.ltech.smarthome.ui.group.ActSelectDeviceNew;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.dialog.SelectDmxDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.EurPanelSettingState;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActEurPanelGroupSetting extends VMActivity<ActEurPanelGroupSettingBinding, ActEurPanelGroupSettingVM> {
    public List<BaseDeviceSetActivity.LightSettingState> dataList;
    private MultipleItemRvAdapter<BaseDeviceSetActivity.LightSettingState, BaseViewHolder> mAdapter;
    private boolean queryTimeSuccess;
    protected boolean[] selectArray;
    private EurPanelSettingState settingState = new EurPanelSettingState();

    static /* synthetic */ void lambda$showPickTimeDialog$20(Boolean bool) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_eur_panel_group_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).sbBuzzer.setOnCheckedChangeListener(new AnonymousClass1());
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).sbBuzzer.setButtonEnable(isOwnerOrManager());
    }

    /* renamed from: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$1, reason: invalid class name */
    class AnonymousClass1 implements SwitchButton.OnCheckedChangeListener {
        AnonymousClass1() {
        }

        @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
        public void onCheckedChanged(SwitchButton view, final boolean isChecked) {
            ActEurPanelGroupSetting.this.setBuzzerState(isChecked, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelGroupSetting.AnonymousClass1.this.lambda$onCheckedChanged$0(isChecked, (Boolean) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCheckedChanged$0(boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            ((ActEurPanelGroupSettingBinding) ActEurPanelGroupSetting.this.mViewBinding).sbBuzzer.setCheckedNotByUser(!z);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActEurPanelGroupSettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelGroupSetting.this.lambda$startObserve$0((Group) obj);
            }
        });
        initRv();
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda5
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActEurPanelGroupSetting.this.lambda$startObserve$1(refreshLayout);
            }
        });
        ((ActEurPanelGroupSettingVM) this.mViewModel).showPowerStateDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelGroupSetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActEurPanelGroupSettingVM) this.mViewModel).adjustKRange.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelGroupSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActEurPanelGroupSettingVM) this.mViewModel).buzzerIsOpen.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelGroupSetting.this.lambda$startObserve$4((Boolean) obj);
            }
        });
        ((ActEurPanelGroupSettingVM) this.mViewModel).showDmxTypeDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelGroupSetting.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((ActEurPanelGroupSettingVM) this.mViewModel).getSceneAutomationOver.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelGroupSetting.this.lambda$startObserve$6((Integer) obj);
            }
        });
        ((ActEurPanelGroupSettingVM) this.mViewModel).showEditGroupEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelGroupSetting.this.lambda$startObserve$7((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Group group) {
        String floorName;
        if (group == null) {
            return;
        }
        ((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup = group;
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvGroupName.setText(group.getGroupName());
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvDeviceCount.setText(String.format(Locale.US, "%d", Integer.valueOf(group.getDeviceIds() != null ? group.getDeviceIds().size() : 0)));
        Floor floor = Injection.repo().home().getFloor(group.getFloorId());
        Room room = Injection.repo().home().getRoom(group.getRoomId());
        AppCompatTextView appCompatTextView = ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvRoomName;
        if (room != null) {
            floorName = floor.getFloorName() + " " + room.getRoomName();
        } else {
            floorName = floor != null ? floor.getFloorName() : "";
        }
        appCompatTextView.setText(floorName);
        ((ActEurPanelGroupSettingVM) this.mViewModel).roomPickHelper.startObserve(this, group.getPlaceId(), group.getRoomId());
        initRelatedInfoView(group);
        queryState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(RefreshLayout refreshLayout) {
        if (((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.getValue() != null) {
            queryState();
        }
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showPowerStateDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r4) {
        ((ActEurPanelGroupSettingVM) this.mViewModel).clickAdjustKRange(this, ((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.getValue(), ((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Boolean bool) {
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).sbBuzzer.setCheckedNotByUser(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        showDmxTypeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Integer num) {
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvRelatedNum.setText(String.valueOf(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r4) {
        EurHelper.settingState = this.settingState;
        NavUtils.destination(ProductRepository.isBleGroup(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, ((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup.getPlaceId()).withLong(Constants.FLOOR_ID, ((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup.getFloorId()).withLong(Constants.ROOM_ID, ((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup.getRoomId()).withLong(Constants.GROUP_ID, ((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup.getId()).navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup = Injection.repo().group().getGroupById(((ActEurPanelGroupSettingVM) this.mViewModel).controlId);
        ((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.setValue(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup);
        if (((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup == null) {
            finishActivity();
        } else {
            ((ActEurPanelGroupSettingVM) this.mViewModel).queryScene(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup.getGroupId());
        }
    }

    private void initRelatedInfoView(final Group group) {
        ((ActEurPanelGroupSettingVM) this.mViewModel).initRelateInfoList(group);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).sbBuzzer.setChecked(((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant.getBuzzerState() == 1);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvZoneControlState.setText(((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant.getZoneNumber() == 1 ? R.string.single_zone_control : R.string.multi_zone_control);
        if (EurHelper.convertType(group) == 2 || AsHelper.convertType(group) == 2) {
            ((ActEurPanelGroupSettingVM) this.mViewModel).isShowBindKRange.setValue(Boolean.valueOf(((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant.isKnobShowKRange()));
        } else {
            ((ActEurPanelGroupSettingVM) this.mViewModel).isShowBindKRange.setValue(false);
        }
        if (ProductRepository.getLightColorType((Object) group) == 24 || ProductRepository.getLightColorType((Object) group) == 29 || ProductRepository.getLightColorType((Object) group) == 30) {
            showControlType();
        } else {
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).layoutControlType.setVisibility(8);
        }
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvKeySaveTip.setText(EurHelper.isEb125(group) ? R.string.key_save_tip_eb : R.string.key_save_tip);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).sbKeySave.setChecked(((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant.getSceneLongPress() == 1);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).sbKeySave.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda17
            @Override // com.ltech.smarthome.view.SwitchButton.OnCheckedChangeListener
            public final void onCheckedChanged(SwitchButton switchButton, boolean z) {
                ActEurPanelGroupSetting.this.lambda$initRelatedInfoView$9(group, switchButton, z);
            }
        });
        if (AsHelper.isUb1245(group)) {
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).layoutBuzzer.setVisibility(8);
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).layoutZoneControl.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$9(Group group, SwitchButton switchButton, final boolean z) {
        CmdAssistant.getSettingCmdAssistant(group, new int[0]).setKeySave(this, z, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$initRelatedInfoView$8(z, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRelatedInfoView$8(boolean z, Boolean bool) {
        if (bool.booleanValue()) {
            ((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant.setSceneLongPress(z ? 1 : 0);
            ((ActEurPanelGroupSettingVM) this.mViewModel).updateParamExt(((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.getValue(), ((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant.getExtParamString());
        } else {
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).sbKeySave.setCheckedNotByUser(!z);
            showErrorDialog(getString(R.string.save_fail));
        }
    }

    private void initRv() {
        List<BaseDeviceSetActivity.LightSettingState> contentList = getContentList();
        this.dataList = contentList;
        this.selectArray = new boolean[contentList.size()];
        MultipleItemRvAdapter<BaseDeviceSetActivity.LightSettingState, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<BaseDeviceSetActivity.LightSettingState, BaseViewHolder>(this.dataList) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(BaseDeviceSetActivity.LightSettingState lightSettingState) {
                return lightSettingState.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<BaseDeviceSetActivity.LightSettingState, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting.2.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, BaseDeviceSetActivity.LightSettingState data, int position) {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_on_off_time_normal;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return BaseDeviceSetActivity.LightSettingState.TYPE_DEFAULT;
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<BaseDeviceSetActivity.LightSettingState, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting.2.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_on_off_time_diy;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return BaseDeviceSetActivity.LightSettingState.TYPE_DIY;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, BaseDeviceSetActivity.LightSettingState data, int position) {
                        helper.setText(R.id.tv_name, data.getTitleName());
                        ActEurPanelGroupSetting.this.refreshTimeView(helper);
                        boolean z = true;
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        if (ActEurPanelGroupSetting.this.selectArray[position]) {
                            helper.setGone(R.id.layout_elec, true);
                            helper.setGone(R.id.layout_on, true);
                            helper.setGone(R.id.layout_off, true);
                            helper.setGone(R.id.layout_scene, true);
                            helper.setImageResource(R.id.iv_select, R.mipmap.ic_up_gray);
                        } else {
                            helper.setGone(R.id.layout_elec, false);
                            helper.setGone(R.id.layout_on, false);
                            helper.setGone(R.id.layout_off, false);
                            helper.setGone(R.id.layout_scene, false);
                            helper.setImageResource(R.id.iv_select, R.mipmap.ic_down_gray);
                        }
                        helper.setGone(R.id.iv_elec_select, ((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).getCurrentPlace().isManager());
                        helper.setGone(R.id.iv_on_select, ((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).getCurrentPlace().isManager());
                        if (!((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).getCurrentPlace().isOwner() && !((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).getCurrentPlace().isManager()) {
                            z = false;
                        }
                        helper.setGone(R.id.iv_off_select, z);
                        if (((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).getCurrentPlace().isOwner() || ((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).getCurrentPlace().isManager()) {
                            helper.addOnClickListener(R.id.layout_elec, R.id.layout_on, R.id.layout_off, R.id.layout_scene);
                        }
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda25
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActEurPanelGroupSetting.this.lambda$initRv$10(baseQuickAdapter, view, i);
            }
        });
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).rvLightSetting.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting.3
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layout_elec /* 2131297460 */:
                        ActEurPanelGroupSetting.this.showSelectTimeDialog();
                        break;
                    case R.id.layout_off /* 2131297557 */:
                        ActEurPanelGroupSetting.this.showPickTimeDialog(false);
                        break;
                    case R.id.layout_on /* 2131297558 */:
                        ActEurPanelGroupSetting.this.showPickTimeDialog(true);
                        break;
                    case R.id.layout_scene /* 2131297611 */:
                        ActEurPanelGroupSetting.this.showSceneSelectTimeDialog();
                        break;
                }
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(((ActEurPanelGroupSettingBinding) this.mViewBinding).rvLightSetting);
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).rvLightSetting.setLayoutManager(new LinearLayoutManager(this));
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).rvLightSetting.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActEurPanelGroupSettingBinding) this.mViewBinding).rvLightSetting.getItemAnimator()).setSupportsChangeAnimations(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$10(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyDataSetChanged();
    }

    private List<BaseDeviceSetActivity.LightSettingState> getContentList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BaseDeviceSetActivity.LightSettingState(BaseDeviceSetActivity.LightSettingState.TYPE_DIY, getString(R.string.gradual_time) + "(DMX)"));
        return arrayList;
    }

    private void showControlType() {
        int convertType = AsHelper.isNewUb(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup) ? AsHelper.convertType(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup) : EurHelper.convertType(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup);
        if (convertType == 1) {
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvControlTypeState.setText(R.string.type_dim);
            return;
        }
        if (convertType == 2) {
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvControlTypeState.setText(R.string.type_ct);
            return;
        }
        if (convertType == 3) {
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvControlTypeState.setText(R.string.type_rgb);
        } else if (convertType == 4) {
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvControlTypeState.setText(R.string.type_rgbw);
        } else {
            if (convertType != 5) {
                return;
            }
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvControlTypeState.setText(R.string.type_rgbwy);
        }
    }

    private void queryState() {
        getCmdHelper().queryOnState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$queryState$11((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$11(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        List<SelectPowerOnStateDialog.OnOffState> generateContentList = SelectPowerOnStateDialog.generateContentList();
        this.settingState.powerOnState = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        int size = generateContentList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            if (this.settingState.powerOnState != generateContentList.get(i).getMainValue()) {
                i++;
            } else if (this.settingState.powerOnState == 4) {
                this.settingState.powerOnBrt = Integer.parseInt(responseMsg.getResData().substring(18, 20), 16);
                ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(LightUtils.brt2ProgressHasBelowZero(this.settingState.powerOnBrt)) + getString(R.string.brt));
            } else {
                ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvState.setText(generateContentList.get(i).getName());
            }
        }
        queryOnOffTime();
    }

    private void queryOnOffTime() {
        getCmdHelper().queryOnOffTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda15
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$queryOnOffTime$12((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryOnOffTime$12(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 24) {
            return;
        }
        LHomeLog.i(getClass(), "queryState=" + responseMsg.getResData());
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
        this.settingState.onSecPos = parseInt / 1000;
        this.settingState.onMsPos = (parseInt % 1000) / 100;
        int parseInt2 = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16) * 100;
        this.settingState.offSecPos = parseInt2 / 1000;
        this.settingState.offMsPos = (parseInt2 % 1000) / 100;
        queryLightPowerOnTime();
        this.queryTimeSuccess = true;
    }

    private void queryLightPowerOnTime() {
        getCmdHelper().queryPowerOnTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda27
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$queryLightPowerOnTime$13((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLightPowerOnTime$13(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 20) {
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
                this.settingState.powerOnSecPos = parseInt / 1000;
                this.settingState.powerOnMsPos = (parseInt % 1000) / 100;
                this.mAdapter.notifyDataSetChanged();
            }
            queryLightSceneTime();
            return;
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void queryLightSceneTime() {
        getCmdHelper().querySceneOnTime(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda23
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$queryLightSceneTime$14((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryLightSceneTime$14(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 20) {
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16) * 100;
                this.settingState.sceneSecPos = parseInt / 1000;
                this.settingState.sceneMsPos = (parseInt % 1000) / 100;
                this.mAdapter.notifyDataSetChanged();
            }
            queryKeySave();
            return;
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void queryKeySave() {
        CmdAssistant.getQueryCmdAssistant(((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.getValue(), new int[0]).queryKeySave(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$queryKeySave$15((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryKeySave$15(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 18) {
                ((ActEurPanelGroupSettingVM) this.mViewModel).keySave.setValue(Boolean.valueOf(Integer.parseInt(responseMsg.getResData().substring(16, 18), 16) == 1));
            }
            queryBuzzerState();
        }
    }

    private void queryBuzzerState() {
        if (EurHelper.isEb125(((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.getValue())) {
            getCmdHelper().queryBuzzerState(this, 1, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelGroupSetting.this.lambda$queryBuzzerState$16((ResponseMsg) obj);
                }
            });
        } else {
            queryDmxType();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryBuzzerState$16(ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getResData() != null && responseMsg.getResData().length() >= 18) {
                this.settingState.buzzerState = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
                ((ActEurPanelGroupSettingVM) this.mViewModel).buzzerIsOpen.setValue(Boolean.valueOf(this.settingState.buzzerState == 1));
            }
            queryDmxType();
        }
    }

    private void queryDmxType() {
        if (AsHelper.convertType(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup) == 2 || EurHelper.convertType(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup) == 2) {
            getCmdHelper().queryCtLightMode(this, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda24
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelGroupSetting.this.lambda$queryDmxType$17((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDmxType$17(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getStateCode() != 0 || responseMsg.getResData() == null || responseMsg.getResData().length() < 18 || !responseMsg.getResData().substring(12, 14).equalsIgnoreCase("0C")) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        if (parseInt == 1 || parseInt == 2) {
            this.settingState.dmxType = parseInt;
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).layoutSetDmxType.setVisibility(0);
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvDmxType.setText(this.settingState.dmxType == 1 ? R.string.ct_dmx_address_C_name : R.string.ct_dmx_address_BRT_name);
        }
    }

    private void showPowerStateDialog() {
        SelectPowerOnStateDialog.asDefault().controlObject(((ActEurPanelGroupSettingVM) this.mViewModel).controlGroup).setOnSaveListener(new AnonymousClass4()).showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$4, reason: invalid class name */
    class AnonymousClass4 implements SelectPowerOnStateDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass4() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnSaveListener
        public boolean onSave(final SelectPowerOnStateDialog.OnOffState onOffState) {
            CmdAssistant.getLightCmdAssistant(((ActEurPanelGroupSettingVM) ActEurPanelGroupSetting.this.mViewModel).controlGroup, new int[0]).setOnState(ActEurPanelGroupSetting.this.activity, onOffState.getMainValue(), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$4$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelGroupSetting.AnonymousClass4.this.lambda$onSave$0(onOffState, (Boolean) obj);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(SelectPowerOnStateDialog.OnOffState onOffState, Boolean bool) {
            if (bool.booleanValue()) {
                ActEurPanelGroupSetting.this.updatePowerStateDialogData(onOffState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePowerStateDialogData(SelectPowerOnStateDialog.OnOffState state) {
        if (state.getMainValue() == 4) {
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(state.getSubValue()) + getString(R.string.brt));
            return;
        }
        ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvState.setText(state.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshTimeView(BaseViewHolder helper) {
        if (this.queryTimeSuccess) {
            helper.setText(R.id.tv_scene_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.settingState.sceneSecPos + ((this.settingState.sceneMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            helper.setText(R.id.tv_on_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.settingState.onSecPos + ((this.settingState.onMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            helper.setText(R.id.tv_off_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.settingState.offSecPos + ((this.settingState.offMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
            helper.setText(R.id.tv_elec_sub_name, String.format(Locale.US, "%.1f%s", Float.valueOf(this.settingState.powerOnSecPos + ((this.settingState.powerOnMsPos * 100.0f) / 1000.0f)), getString(R.string.sec)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSelectTimeDialog() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.settingState.powerOnSecPos).setSelectSecPosition(this.settingState.powerOnMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActEurPanelGroupSetting.this.lambda$showSelectTimeDialog$19(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$19(int i, int i2) {
        this.settingState.powerOnSecPos = i;
        this.settingState.powerOnMsPos = i2;
        getCmdHelper().setPowerOnTime(this, (this.settingState.powerOnSecPos * 1000) + (this.settingState.powerOnMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$showSelectTimeDialog$18((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectTimeDialog$18(Boolean bool) {
        if (bool.booleanValue()) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPickTimeDialog(final boolean on) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog secUnit = TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms));
        EurPanelSettingState eurPanelSettingState = this.settingState;
        TimeSelectDialog selectMinPosition = secUnit.setSelectMinPosition(on ? eurPanelSettingState.onSecPos : eurPanelSettingState.offSecPos);
        EurPanelSettingState eurPanelSettingState2 = this.settingState;
        selectMinPosition.setSelectSecPosition(on ? eurPanelSettingState2.onMsPos : eurPanelSettingState2.offMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActEurPanelGroupSetting.this.lambda$showPickTimeDialog$22(on, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPickTimeDialog$22(boolean z, int i, int i2) {
        if (z) {
            this.settingState.onSecPos = i;
            this.settingState.onMsPos = i2;
        } else {
            this.settingState.offSecPos = i;
            this.settingState.offMsPos = i2;
        }
        getCmdHelper().setOnOffTime(this, (this.settingState.onSecPos * 1000) + (this.settingState.onMsPos * 100), (this.settingState.offSecPos * 1000) + (this.settingState.offMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda18
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$showPickTimeDialog$21((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPickTimeDialog$21(Boolean bool) {
        if (bool.booleanValue()) {
            getCmdHelper().setSceneOnTime(this, (this.settingState.onSecPos * 1000) + (this.settingState.onMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda22
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActEurPanelGroupSetting.lambda$showPickTimeDialog$20((Boolean) obj);
                }
            });
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSceneSelectTimeDialog() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < 100; i2++) {
            arrayList.add(i2 < 10 ? "0" + i2 : String.valueOf(i2));
        }
        ArrayList arrayList2 = new ArrayList();
        while (i < 10) {
            arrayList2.add(i == 0 ? "000" : String.valueOf(i * 100));
            i++;
        }
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.settingState.sceneSecPos).setSelectSecPosition(this.settingState.sceneMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                ActEurPanelGroupSetting.this.lambda$showSceneSelectTimeDialog$24(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSceneSelectTimeDialog$24(int i, int i2) {
        this.settingState.sceneSecPos = i;
        this.settingState.sceneMsPos = i2;
        getCmdHelper().setSceneOnTime(this, (this.settingState.sceneSecPos * 1000) + (this.settingState.sceneMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$showSceneSelectTimeDialog$23((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSceneSelectTimeDialog$23(Boolean bool) {
        if (bool.booleanValue()) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    private void showDmxTypeDialog() {
        SelectDmxDialog.asDefault().setTitle(getString(R.string.ct_dmx_address_type)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.save)).setSelectPosition(this.settingState.dmxType - 1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda21
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$showDmxTypeDialog$26((Integer) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDmxTypeDialog$26(final Integer num) {
        getCmdHelper().setCtLightMode(this, num.intValue() + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$showDmxTypeDialog$25(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDmxTypeDialog$25(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            this.settingState.dmxType = num.intValue() + 1;
            ((ActEurPanelGroupSettingBinding) this.mViewBinding).tvDmxType.setText(this.settingState.dmxType == 1 ? R.string.ct_dmx_address_C_name : R.string.ct_dmx_address_BRT_name);
        }
    }

    public void setBuzzerState(final boolean z, final IAction<Boolean> iAction) {
        getCmdHelper().setBuzzerState(this, 1, z ? 1 : 0, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting$$ExternalSyntheticLambda26
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActEurPanelGroupSetting.this.lambda$setBuzzerState$27(z, iAction, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBuzzerState$27(boolean z, IAction iAction, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            ((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant.setBuzzerState(z ? 1 : 0);
            ((ActEurPanelGroupSettingVM) this.mViewModel).updateParamExt(((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.getValue(), ((ActEurPanelGroupSettingVM) this.mViewModel).relateInfoAssistant.getExtParamString());
        } else {
            iAction.act(false);
        }
    }

    private LightAssistant getCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(((ActEurPanelGroupSettingVM) this.mViewModel).controlObject.getValue(), new int[0]);
    }
}