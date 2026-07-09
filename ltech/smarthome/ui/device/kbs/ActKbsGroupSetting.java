package com.ltech.smarthome.ui.device.kbs;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActKbsGroupSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.KbsExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting;
import com.ltech.smarthome.ui.device.light.ActDiyLightName;
import com.ltech.smarthome.ui.group.ActAddWiFiGroup;
import com.ltech.smarthome.ui.group.ActSelectDeviceNew;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.IconRepository;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.dialog.SelectDeviceIconDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActKbsGroupSetting extends VMActivity<ActKbsGroupSettingBinding, ActKbsGroupSettingVM> {
    public List<LightSettingState> dataList;
    private BaseQuickAdapter<KbsKey, BaseViewHolder> mAdapter;
    protected boolean[] selectArray;

    static /* synthetic */ boolean lambda$showDelFailDialog$6(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_kbs_group_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.setting));
        setEditString("             ");
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActKbsGroupSettingVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActKbsGroupSettingVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActKbsGroupSettingVM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKbsGroupSetting.this.lambda$startObserve$0((Group) obj);
            }
        });
        initRv();
        ((ActKbsGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((ActKbsGroupSettingBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActKbsGroupSettingBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActKbsGroupSettingBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda6
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActKbsGroupSetting.this.lambda$startObserve$1(refreshLayout);
            }
        });
        ((ActKbsGroupSettingVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKbsGroupSetting.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActKbsGroupSettingVM) this.mViewModel).refreshRoleItem.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActKbsGroupSetting.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActKbsGroupSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActKbsGroupSetting.this.lambda$startObserve$4((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Group group) {
        if (group == null) {
            return;
        }
        int i = 0;
        if (((ActKbsGroupSettingVM) this.mViewModel).getCurrentPlace() != null && (((ActKbsGroupSettingVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActKbsGroupSettingVM) this.mViewModel).getCurrentPlace().isManager())) {
            ((ActKbsGroupSettingBinding) this.mViewBinding).setManagerOrOwner(true);
        } else {
            ((ActKbsGroupSettingBinding) this.mViewBinding).setManagerOrOwner(false);
        }
        ((ActKbsGroupSettingVM) this.mViewModel).controlGroup = group;
        ((ActKbsGroupSettingVM) this.mViewModel).groupId = group.getGroupId();
        ((ActKbsGroupSettingVM) this.mViewModel).imageIndex = group.getImgindex();
        ((ActKbsGroupSettingBinding) this.mViewBinding).tvRoomName.setText(group.getRoomName());
        ((ActKbsGroupSettingBinding) this.mViewBinding).tvGroupName.setText(group.getName());
        ((ActKbsGroupSettingBinding) this.mViewBinding).tvDeviceCount.setText(String.format(Locale.US, "%d", Integer.valueOf(group.getDeviceIds() != null ? group.getDeviceIds().size() : 0)));
        ((ActKbsGroupSettingVM) this.mViewModel).roomPickHelper.startObserve(this, group.getPlaceId(), group.getRoomId());
        int imgindex = group.getImgindex();
        int[] lightIconValue = IconRepository.getLightIconValue(this);
        while (true) {
            if (i >= lightIconValue.length) {
                break;
            }
            if (i == imgindex) {
                ((ActKbsGroupSettingBinding) this.mViewBinding).ivIcon.setImageResource(lightIconValue[i]);
                break;
            }
            i++;
        }
        queryState();
        ((ActKbsGroupSettingVM) this.mViewModel).refreshRoleItem.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(RefreshLayout refreshLayout) {
        if (((ActKbsGroupSettingVM) this.mViewModel).controlObject.getValue() != null) {
            queryState();
        }
        ((ActKbsGroupSettingBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r8) {
        String string;
        String string2;
        KbsExtParam kbsExtParam = (KbsExtParam) ((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getExtParam(KbsExtParam.class);
        int[] lightGroupIconValue = IconRepository.getLightGroupIconValue(this);
        int imgindex1 = kbsExtParam == null ? 0 : kbsExtParam.getImgindex1();
        int i = R.mipmap.icon_light_group;
        int i2 = (imgindex1 < 0 || imgindex1 > lightGroupIconValue.length) ? R.mipmap.icon_light_group : lightGroupIconValue[imgindex1];
        int imgindex2 = kbsExtParam != null ? kbsExtParam.getImgindex2() : 0;
        if (imgindex2 >= 0 && imgindex2 <= lightGroupIconValue.length) {
            i = lightGroupIconValue[imgindex2];
        }
        ArrayList arrayList = new ArrayList();
        if (kbsExtParam == null || StringUtils.isEmpty(kbsExtParam.getName1())) {
            string = getString(R.string.app_str_smart_panel_switch1);
        } else {
            string = kbsExtParam.getName1();
        }
        arrayList.add(new KbsKey(string, imgindex1, i2));
        if (kbsExtParam == null || StringUtils.isEmpty(kbsExtParam.getName2())) {
            string2 = getString(R.string.app_str_smart_panel_switch2);
        } else {
            string2 = kbsExtParam.getName2();
        }
        arrayList.add(new KbsKey(string2, imgindex2, i));
        this.mAdapter.setNewData(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(View view) {
        switch (view.getId()) {
            case R.id.layout_change_icon /* 2131297387 */:
                showSelectGroupIconDialog();
                break;
            case R.id.layout_change_room /* 2131297392 */:
                showEditRoomDialog();
                break;
            case R.id.layout_edit_group /* 2131297457 */:
                NavUtils.destination(ProductRepository.isBleGroup(((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, ((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getPlaceId()).withLong(Constants.FLOOR_ID, ((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getFloorId()).withLong(Constants.ROOM_ID, ((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getRoomId()).withLong(Constants.GROUP_ID, ((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getId()).navigation(this);
                break;
            case R.id.layout_group_name /* 2131297487 */:
                String groupKey = ((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getGroupKey();
                if (groupKey.equals(ProductId.BLE_GROUP_DIM_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_CT_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGB_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGBW_LIGHT) || groupKey.equals(ProductId.BLE_GROUP_RGBWY_LIGHT)) {
                    NavUtils.destination(ActDiyLightName.class).withLong(Constants.CONTROL_ID, ((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getId()).withBoolean(Constants.GROUP_CONTROL, true).navigation(ActivityUtils.getTopActivity());
                    break;
                } else {
                    showEditNameDialog();
                    break;
                }
                break;
            case R.id.layout_set_on_state /* 2131297646 */:
                showPowerStateDialog();
                break;
            case R.id.tv_delete /* 2131298575 */:
                if (ProductRepository.isBleGroup(((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getModuleType()) && !((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getDeviceIds().isEmpty()) {
                    showDelFailDialog();
                    break;
                } else {
                    showDelDialog();
                    break;
                }
                break;
        }
    }

    private void showDelDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_group)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showDelDialog$5;
                lambda$showDelDialog$5 = ActKbsGroupSetting.this.lambda$showDelDialog$5(baseDialog, view);
                return lambda$showDelDialog$5;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDelDialog$5(BaseDialog baseDialog, View view) {
        delGroupFromNet(((ActKbsGroupSettingVM) this.mViewModel).controlGroup);
        return false;
    }

    private void showDelFailDialog() {
        MessageDialog.show(this, getString(R.string.del_fail), getString(R.string.del_fail_tip)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda10
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActKbsGroupSetting.lambda$showDelFailDialog$6(baseDialog, view);
            }
        });
    }

    private void delGroupFromNet(final Group group) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(group.getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSetting.this.lambda$delGroupFromNet$7((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActKbsGroupSetting$$ExternalSyntheticLambda12(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSetting.this.lambda$delGroupFromNet$8(group, obj);
            }
        }, new SmartErrorComsumer());
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 13 || lightColorType == 15 || lightColorType == 25) {
            return;
        }
        unSubscribePublicationAddress(group);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$7(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$8(Group group, Object obj) throws Exception {
        Injection.repo().group().removeGroupFromDb(group.getId());
        Injection.repo().group().removeSubGroupFromDb(group.getPlaceId(), group.getGroupId());
        setResult(5002);
        finishActivity();
    }

    private void unSubscribePublicationAddress(Group group) {
        LHomeLog.i(getClass(), "delete device--->" + group);
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), group.getGroupAddress(), ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()), new int[0]);
    }

    private void showEditNameDialog() {
        EditDialog.asDefault().setContent(((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getGroupName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKbsGroupSetting.this.lambda$showEditNameDialog$9((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$9(String str) {
        updateName(((ActKbsGroupSettingVM) this.mViewModel).controlGroup, str);
    }

    private void updateName(final Group group, final String name) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupName(group.getGroupId(), name).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSetting.this.lambda$updateName$10((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActKbsGroupSetting$$ExternalSyntheticLambda12(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActKbsGroupSetting.this.lambda$updateName$11(group, name, (UpdateGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$10(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateName$11(Group group, String str, UpdateGroupResponse updateGroupResponse) throws Exception {
        group.setGroupName(str);
        Injection.repo().group().saveGroup(group);
        ((ActKbsGroupSettingVM) this.mViewModel).controlObject.setValue(group);
        SmartToast.showShort(getString(R.string.save_success));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActKbsGroupSettingVM) this.mViewModel).controlObject.setValue(Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L)));
    }

    private void initRv() {
        ((ActKbsGroupSettingBinding) this.mViewBinding).rvLightSetting.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = ((ActKbsGroupSettingBinding) this.mViewBinding).rvLightSetting;
        BaseQuickAdapter<KbsKey, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<KbsKey, BaseViewHolder>(this, R.layout.item_kbs_switch_setting) { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, KbsKey key) {
                baseViewHolder.setImageResource(R.id.iv, key.getIco());
                baseViewHolder.addOnClickListener(R.id.tv_setting);
                baseViewHolder.setText(R.id.tv, key.getName());
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                Device device = new Device();
                device.setDeviceName(((KbsKey) ActKbsGroupSetting.this.mAdapter.getData().get(i)).getName());
                device.setImageIndex(((KbsKey) ActKbsGroupSetting.this.mAdapter.getData().get(i)).getImgIndex());
                device.setProductId(ProductId.ID_BLE_KBS);
                ((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).showEditDialog(i, device);
            }
        });
    }

    private void showPowerStateDialog() {
        ((ActKbsGroupSettingVM) this.mViewModel).powerOnStateDialog = SelectPowerOnStateDialog.asDefault().controlObject(((ActKbsGroupSettingVM) this.mViewModel).controlGroup).setOnSaveListener(new AnonymousClass3());
        ((ActKbsGroupSettingVM) this.mViewModel).powerOnStateDialog.showDialog(this.activity);
    }

    /* renamed from: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$3, reason: invalid class name */
    class AnonymousClass3 implements SelectPowerOnStateDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass3() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnSaveListener
        public boolean onSave(final SelectPowerOnStateDialog.OnOffState onOffState) {
            CmdAssistant.getLightCmdAssistant(((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).controlGroup, new int[0]).setOnState(ActKbsGroupSetting.this.activity, onOffState.getMainValue(), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$3$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActKbsGroupSetting.AnonymousClass3.this.lambda$onSave$0(onOffState, (Boolean) obj);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(SelectPowerOnStateDialog.OnOffState onOffState, Boolean bool) {
            if (bool.booleanValue()) {
                ActKbsGroupSetting.this.updatePowerStateDialogData(onOffState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePowerStateDialogData(SelectPowerOnStateDialog.OnOffState state) {
        if (state.getMainValue() == 4) {
            ((ActKbsGroupSettingBinding) this.mViewBinding).tvState.setText(LightUtils.getProgressHasBelowOne(state.getSubValue()) + getString(R.string.brt));
            return;
        }
        ((ActKbsGroupSettingBinding) this.mViewBinding).tvState.setText(state.getName());
    }

    private void queryState() {
        CmdAssistant.getLightCmdAssistant(((ActKbsGroupSettingVM) this.mViewModel).controlGroup, new int[0]).queryOnState(this, new IAction() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActKbsGroupSetting.this.lambda$queryState$12((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryState$12(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData() == null || responseMsg.getResData().length() < 18) {
            return;
        }
        List<SelectPowerOnStateDialog.OnOffState> generateSwitchList = SelectPowerOnStateDialog.generateSwitchList();
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
        int size = generateSwitchList.size();
        for (int i = 0; i < size; i++) {
            if (parseInt == generateSwitchList.get(i).getMainValue()) {
                ((ActKbsGroupSettingBinding) this.mViewBinding).tvState.setText(generateSwitchList.get(i).getName());
                return;
            }
        }
    }

    public static final class LightSettingState {
        private static int TYPE_DEFAULT = 1;
        private static int TYPE_DIY = 2;
        private String name;
        private String subName;
        private String titleName;
        private int type;

        LightSettingState(int type, String titleName) {
            this.type = type;
            this.titleName = titleName;
        }

        LightSettingState(int type, String titleName, String name, String subName) {
            this.type = type;
            this.titleName = titleName;
            this.name = name;
            this.subName = subName;
        }

        int getType() {
            return this.type;
        }

        String getName() {
            return this.name;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getTitleName() {
            return this.titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }
    }

    protected void showEditRoomDialog() {
        if (((ActKbsGroupSettingVM) this.mViewModel).roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            RoomSelectDialog.asDefault().setFloorList(((ActKbsGroupSettingVM) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((ActKbsGroupSettingVM) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(((ActKbsGroupSettingVM) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((ActKbsGroupSettingVM) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting.4
                @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
                public void confirm(int floorPosition, int roomPosition) {
                    ((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                    ((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).changeGroupPlace(((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).roomPickHelper.getSelectFloorId(), ((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).roomPickHelper.getSelectRoomId());
                }

                @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
                public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                    dialog.setRoomList(((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).roomPickHelper.getRoomNames(floorPosition));
                    dialog.notifyDialog();
                }
            }).showDialog(this);
        }
    }

    protected void showSelectGroupIconDialog() {
        SelectDeviceIconDialog.asDefault().setGroupTag(true).imageIndex(((ActKbsGroupSettingVM) this.mViewModel).controlGroup.getImgindex()).setOnSaveListener(new SelectDeviceIconDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting.5
            @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
            public boolean onSave(int selectPos) {
                ((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).updateGroupImageIndex(((ActKbsGroupSettingVM) ActKbsGroupSetting.this.mViewModel).controlGroup.getGroupId(), selectPos);
                return true;
            }
        }).showDialog(this);
    }

    private static class KbsKey {
        private int ico;
        private int imgIndex;
        private String name;

        public KbsKey(String name, int imgIndex, int ico) {
            this.name = name;
            this.imgIndex = imgIndex;
            this.ico = ico;
        }

        public int getIco() {
            return this.ico;
        }

        public void setIco(int ico) {
            this.ico = ico;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImgIndex() {
            return this.imgIndex;
        }

        public void setImgIndex(int imgIndex) {
            this.imgIndex = imgIndex;
        }
    }
}