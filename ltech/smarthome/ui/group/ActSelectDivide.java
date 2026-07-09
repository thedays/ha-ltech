package com.ltech.smarthome.ui.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSelectDeviceDivideActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActSelectDivideBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.LocalSceneParam;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.device_param.WaveSensorExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.BaseLocalParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.net.response.scene.QuerySceneActionResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.smart.product_agreement.bean.WaveSensorState;
import com.smart.product_agreement.param.SettingCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSelectDivide extends BaseSelectDeviceDivideActivity {
    public Group editGroup;
    Map<Integer, List<String>> errorTipMap;
    private boolean isReceiveState;
    private ArrayList<Integer> mSceneNums;
    private Map<Long, Integer> saveSceneStep;
    private int syncTotalNum;
    public List<Long> addDeviceIdList = new ArrayList();
    public List<Long> notAddDeviceIdList = new ArrayList();
    public MutableLiveData<List<Long>> addDeviceIdsLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Long>> notAddDeviceIdsLiveData = new MutableLiveData<>();
    List<Device> notAddDeviceList = new ArrayList();
    List<Device> addDeviceList = new ArrayList();
    private int mSyncNum = 0;

    static /* synthetic */ void lambda$addToMesh$11(Boolean bool) {
    }

    static /* synthetic */ void lambda$addToMesh$9(Boolean bool) {
    }

    static /* synthetic */ boolean lambda$showConfirmDialog$3(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ boolean lambda$showProRemoveDialog$18(BaseDialog baseDialog, View view) {
        return false;
    }

    static /* synthetic */ void lambda$synSensorGroupSettingToDevice$27(Boolean bool) {
    }

    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity
    protected int itemLayout() {
        return R.layout.item_select_device_in_group;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity
    public void convertNotSelectDataView(BaseViewHolder helper, Device item) {
        helper.setText(R.id.tv_device_name, item.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId())).setBackgroundRes(R.id.iv_select, R.mipmap.spgroup_edit_add).setGone(R.id.sync_tv, false).addOnClickListener(R.id.iv_select);
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity
    public void convertSelectedDataView(BaseViewHolder helper, Device item) {
        helper.setText(R.id.tv_device_name, item.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId())).setBackgroundRes(R.id.iv_select, R.mipmap.spgroup_edit_delete).setGone(R.id.sync_tv, this.syncTotalNum > 0).addOnClickListener(R.id.sync_tv).addOnClickListener(R.id.iv_select);
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceDivideActivity, com.ltech.smarthome.base.BaseSelectDivideListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelectDivideBinding) this.mViewBinding).tvTip.setText(R.string.app_str_device_not_in_this_group);
        ((ActSelectDivideBinding) this.mViewBinding).tvTip.setVisibility(0);
        ((ActSelectDivideBinding) this.mViewBinding).rvNotSelectContent.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.1
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() != R.id.iv_select) {
                    return;
                }
                if (Injection.state().isConnectOuterNet()) {
                    Device device = (Device) ActSelectDivide.this.mNotAddAdapter.getData().get(position);
                    if (ActSelectDivide.this.isSmartPanelDevice(device)) {
                        ActSelectDivide actSelectDivide = ActSelectDivide.this;
                        actSelectDivide.showConfirmDialog(device, actSelectDivide.getString(R.string.app_str_add_smart_panel_group_tip));
                        return;
                    } else if (device.getProductId().equals(ProductId.ID_SENSOR_MR03) || device.getProductId().equals(ProductId.ID_SENSOR_MR04)) {
                        ActSelectDivide actSelectDivide2 = ActSelectDivide.this;
                        actSelectDivide2.showConfirmDialog(device, actSelectDivide2.getString(R.string.app_str_add_wave_sensor_group_tip));
                        return;
                    } else {
                        ActSelectDivide.this.add(device);
                        return;
                    }
                }
                SmartToast.showShort(R.string.no_network);
            }
        });
        ((ActSelectDivideBinding) this.mViewBinding).rvSelectContent.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.2
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int id = view.getId();
                if (id != R.id.iv_select) {
                    if (id != R.id.sync_tv) {
                        return;
                    }
                    ActSelectDivide actSelectDivide = ActSelectDivide.this;
                    actSelectDivide.syncLocalScene((Device) actSelectDivide.mAddedAdapter.getItem(position));
                    return;
                }
                if (Injection.state().isConnectOuterNet()) {
                    ActSelectDivide actSelectDivide2 = ActSelectDivide.this;
                    actSelectDivide2.notAdd((Device) actSelectDivide2.mAddedAdapter.getData().get(position));
                } else {
                    SmartToast.showShort(R.string.no_network);
                }
            }
        });
        getTitleBar().setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public void call() {
                boolean booleanExtra = ActSelectDivide.this.getIntent().getBooleanExtra(Constants.CREATE, false);
                if (ActSelectDivide.this.addDeviceList.size() == 0 && booleanExtra) {
                    ActSelectDivide.this.showEmptyGroupTip();
                } else {
                    ActSelectDivide.this.finishActivity();
                }
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmptyGroupTip() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), ActivityUtils.getTopActivity().getString(R.string.app_str_empty_group_dissolve_tip)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_dissolve), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda29
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showEmptyGroupTip$0;
                lambda$showEmptyGroupTip$0 = ActSelectDivide.this.lambda$showEmptyGroupTip$0(baseDialog, view);
                return lambda$showEmptyGroupTip$0;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda30
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showEmptyGroupTip$1;
                lambda$showEmptyGroupTip$1 = ActSelectDivide.this.lambda$showEmptyGroupTip$1(baseDialog, view);
                return lambda$showEmptyGroupTip$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showEmptyGroupTip$0(BaseDialog baseDialog, View view) {
        delGroupFromNet(this.editGroup);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showEmptyGroupTip$1(BaseDialog baseDialog, View view) {
        finishActivity();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showConfirmDialog(final Device device, String tipContent) {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), tipContent).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda9
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showConfirmDialog$2;
                lambda$showConfirmDialog$2 = ActSelectDivide.this.lambda$showConfirmDialog$2(device, baseDialog, view);
                return lambda$showConfirmDialog$2;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda10
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActSelectDivide.lambda$showConfirmDialog$3(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showConfirmDialog$2(Device device, BaseDialog baseDialog, View view) {
        add(device);
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceDivideActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(Transformations.switchMap(Injection.repo().group().getGroupFromDb(getIntent().getLongExtra(Constants.GROUP_ID, 0L)), new Function1() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$4;
                lambda$startObserve$4 = ActSelectDivide.this.lambda$startObserve$4((Group) obj);
                return lambda$startObserve$4;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDivide.this.lambda$startObserve$5((List) obj);
            }
        });
        ((ObservableSubscribeProxy) Injection.net().getGroupLocalSceneAction(Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.GROUP_ID, 0L)).getGroupId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$startObserve$6((QuerySceneActionResponse) obj);
            }
        });
        this.addDeviceIdsLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectDivide.this.lambda$startObserve$7((List) obj);
            }
        });
        this.notAddDeviceIdsLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectDivide.this.lambda$startObserve$8((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$4(Group group) {
        Injection.repo().group().checkCodeLibrary(this, group.getGroupId());
        this.editGroup = group;
        return this.deviceResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(List list) {
        ArrayList arrayList = new ArrayList();
        this.addDeviceList.clear();
        this.notAddDeviceList.clear();
        this.addDeviceIdList.clear();
        this.notAddDeviceIdList.clear();
        if (list != null && !list.isEmpty()) {
            String groupKey = this.editGroup.getGroupKey();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Device device = (Device) it.next();
                if (this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S1C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S2C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S3C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S4) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S4M) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR03) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR04)) {
                    if (groupKey.equals(ProductRepository.getGroupKey(device)) && !filterPanel(device) && (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0 || ((BleParam) device.getParam(BleParam.class)).getGroupId() == this.editGroup.getGroupId())) {
                        if (!this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId()))) {
                            this.notAddDeviceIdList.add(Long.valueOf(device.getDeviceId()));
                            this.notAddDeviceList.add(device);
                        }
                        arrayList.add(device);
                    }
                } else if (groupKey.equals(ProductRepository.getGroupKey(device)) && !filterPanel(device)) {
                    if (!this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId()))) {
                        this.notAddDeviceIdList.add(Long.valueOf(device.getDeviceId()));
                        this.notAddDeviceList.add(device);
                    }
                    arrayList.add(device);
                }
            }
        }
        if (((ActSelectDivideBinding) this.mViewBinding).spinnerFloor.getSelectedItemPosition() == 0 && ((ActSelectDivideBinding) this.mViewBinding).spinnerRoom.getSelectedItemPosition() == 0 && arrayList.isEmpty()) {
            showEmpty();
            return;
        }
        setNotSelectDataList(this.notAddDeviceList);
        getInGroupDevice();
        setSelectDataList(this.addDeviceList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(QuerySceneActionResponse querySceneActionResponse) throws Exception {
        this.syncTotalNum = querySceneActionResponse.getTotal();
        refreshData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(List list) {
        if (this.mAddedAdapter != null) {
            this.mAddedAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(List list) {
        if (this.mNotAddAdapter != null) {
            this.mNotAddAdapter.notifyDataSetChanged();
        }
    }

    private void getInGroupDevice() {
        this.addDeviceIdList.addAll(this.editGroup.getDeviceIds());
        for (int i = 0; i < this.addDeviceIdList.size(); i++) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.addDeviceIdList.get(i).longValue());
            if (deviceByDeviceId != null) {
                this.addDeviceList.add(deviceByDeviceId);
            }
        }
    }

    private boolean filterPanel(Device device) {
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122051609304501":
            case "120042616091901":
            case "120042616094101":
            case "122111615282701":
            case "120042616101901":
            case "120042616103901":
            case "122110709484501":
                return true;
            default:
                return false;
        }
    }

    private void addToMesh(final Device device) {
        if (isSmartPanelDevice(device)) {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda23
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDivide.this.lambda$addToMesh$10(device, (Boolean) obj);
                }
            });
        } else {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda24
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDivide.lambda$addToMesh$11((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToMesh$10(Device device, Boolean bool) {
        if (bool.booleanValue()) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setGroupRelateInfo(this, ProductRepository.getZoneCount(device.getProductId()), generateRelateInfoList(this.editGroup), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDivide.lambda$addToMesh$9((Boolean) obj);
                }
            });
        }
    }

    public void add(final Device device) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.adding));
        if (isSmartPanelDevice(device)) {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda19
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDivide.this.lambda$add$13(device, (Boolean) obj);
                }
            });
        } else {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda20
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDivide.this.lambda$add$14(device, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$add$13(final Device device, Boolean bool) {
        if (bool.booleanValue()) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setGroupRelateInfo(this, ProductRepository.getZoneCount(device.getProductId()), generateRelateInfoList(this.editGroup), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda15
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDivide.this.lambda$add$12(device, (Boolean) obj);
                }
            });
        } else {
            outGroup(device);
            showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.add_fail));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$add$12(Device device, Boolean bool) {
        if (bool.booleanValue()) {
            this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.addDeviceList.add(device);
            this.notAddDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
            this.notAddDeviceList.remove(device);
            refreshData();
            uploadDeviceInGroupData(device);
            return;
        }
        outGroup(device);
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.add_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$add$14(Device device, Boolean bool) {
        if (bool.booleanValue()) {
            this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.addDeviceList.add(device);
            this.notAddDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
            this.notAddDeviceList.remove(device);
            refreshData();
            saveData(device, 1);
            return;
        }
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.add_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void outGroup(Device device) {
        if (this.addDeviceIdList.contains(Long.valueOf(device.getDeviceId()))) {
            this.addDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
            this.addDeviceList.remove(device);
        }
        if (!this.notAddDeviceIdList.contains(Long.valueOf(device.getDeviceId()))) {
            this.notAddDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.notAddDeviceList.add(device);
        }
        refreshData();
        Injection.mesh().outGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
            }
        });
    }

    private List<SettingCmdParam.RelateInfo> generateRelateInfoList(Group group) {
        ArrayList arrayList = new ArrayList();
        RelateInfoAssistant relateInfoAssistant = new RelateInfoAssistant(group);
        List<Boolean> onOffStates = group.getGroupState().getOnOffStates();
        for (int i = 0; i < ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group)); i++) {
            SettingCmdParam.RelateInfo relateInfo = new SettingCmdParam.RelateInfo();
            RelatedInfoExtParam.RelateInfo relateInfo2 = relateInfoAssistant.getRelateInfo(i);
            int relateAddress = getRelateAddress(relateInfo2);
            int relateZone = getRelateZone(relateInfo2);
            int i2 = relateInfo2 != null ? relateInfo2.type : 0;
            int i3 = relateInfo2 != null ? relateInfo2.action : 0;
            int relateDeviceType = getRelateDeviceType(relateInfo2);
            int i4 = (onOffStates == null || onOffStates.isEmpty() || onOffStates.size() < ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group)) || !onOffStates.get(i).booleanValue()) ? 0 : 1;
            relateInfo.setRelateAddress(relateAddress);
            relateInfo.setRelateZone(relateZone);
            relateInfo.setRelateType(i2);
            relateInfo.setRelateExtData(i3);
            relateInfo.setRelateLightType(relateDeviceType);
            relateInfo.setZoneOnOffState(i4);
            arrayList.add(relateInfo);
        }
        return arrayList;
    }

    private int getRelateZone(RelatedInfoExtParam.RelateInfo relateInfoExt) {
        if (relateInfoExt != null) {
            return 1 << relateInfoExt.bindingZone;
        }
        return 0;
    }

    private int getRelateAddress(RelatedInfoExtParam.RelateInfo relateInfoExt) {
        if (relateInfoExt != null) {
            if (relateInfoExt.type == 2) {
                return Injection.repo().group().getGroupByGroupId(relateInfoExt.objectId).getGroupAddress();
            }
            if (relateInfoExt.type != 1 && relateInfoExt.type != 4) {
                return relateInfoExt.type == 3 ? 65025 : 0;
            }
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfoExt.objectId);
            if (deviceByDeviceId.getParam(BleParam.class) != null) {
                return ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getUnicastAddress();
            }
        }
        return 0;
    }

    private int getRelateDeviceType(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo != null) {
            if (relateInfo.action >= 16) {
                return 1 << relateInfo.keyActionExtra;
            }
            if (relateInfo.type == 2) {
                return Integer.parseInt(Injection.repo().group().getGroupByGroupId(relateInfo.objectId).getControlType());
            }
            if (relateInfo.type == 1) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
                if (deviceByDeviceId.getParam(BleParam.class) != null) {
                    return ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getDeviceType();
                }
                return 0;
            }
            if (relateInfo.type == 3) {
                return relateInfo.keyActionExtra;
            }
        }
        return 0;
    }

    public void notAdd(final Device device) {
        if (isSmartPanelDevice(device) || device.getProductId().equals(ProductId.ID_SENSOR_MR03) || device.getProductId().equals(ProductId.ID_SENSOR_MR04)) {
            if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                int i = 0;
                for (Device device2 : this.addDeviceList) {
                    if (device2.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device2.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device2.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                        i++;
                    }
                }
                if (i == 1) {
                    showProRemoveDialog(device);
                    return;
                }
            }
            removeSmartPanel(device);
            return;
        }
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
        Injection.mesh().outGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda31
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDivide.this.lambda$notAdd$15(device, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notAdd$15(Device device, Boolean bool) {
        if (bool.booleanValue()) {
            this.addDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
            this.addDeviceList.remove(device);
            this.notAddDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.notAddDeviceList.add(device);
            refreshData();
            saveData(null, 2);
            return;
        }
        addToMesh(device);
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.remove_fail));
    }

    private void removeSmartPanel(final Device device) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
        Injection.mesh().outGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDivide.this.lambda$removeSmartPanel$16(device, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeSmartPanel$16(Device device, Boolean bool) {
        if (bool.booleanValue()) {
            this.addDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
            this.addDeviceList.remove(device);
            this.notAddDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.notAddDeviceList.add(device);
            refreshData();
            saveData(device, 2);
            return;
        }
        addToMesh(device);
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.remove_fail));
    }

    private void showProRemoveDialog(final Device device) {
        MessageDialog.show(this, getString(R.string.tips), ActivityUtils.getTopActivity().getString(R.string.group_remove_tip)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.pro_remove), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda32
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showProRemoveDialog$17;
                lambda$showProRemoveDialog$17 = ActSelectDivide.this.lambda$showProRemoveDialog$17(device, baseDialog, view);
                return lambda$showProRemoveDialog$17;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.pro_not_remove), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda33
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return ActSelectDivide.lambda$showProRemoveDialog$18(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showProRemoveDialog$17(Device device, BaseDialog baseDialog, View view) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
        removeSmartPanel(device);
        return false;
    }

    private void refreshData() {
        this.editGroup.setDeviceIds(this.addDeviceIdList);
        Injection.repo().group().saveGroup(this.editGroup);
        this.addDeviceIdsLiveData.setValue(this.addDeviceIdList);
        this.notAddDeviceIdsLiveData.setValue(this.notAddDeviceIdList);
    }

    private void saveData(final Device device, final int type) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupDevices(this.editGroup.getGroupId(), this.addDeviceIdList).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda25
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$saveData$19((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda26
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$saveData$20(device, type, (UpdateGroupResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda27
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$saveData$21(device, type, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$19(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$20(Device device, int i, UpdateGroupResponse updateGroupResponse) throws Exception {
        showSuccessDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
        refreshData();
        if (device != null && i == 2) {
            uploadDeviceOutGroupData(device);
        }
        if (this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S1C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S2C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S3C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S4) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S4M)) {
            synGroupSettingToDevice(device, device != null && i == 2);
        } else if (this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR03) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR04)) {
            synSensorGroupSettingToDevice(device, device != null && i == 2);
        }
        if (i != 2) {
            syncLocalScene(device);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$21(Device device, int i, Throwable th) throws Exception {
        if (device != null && i == 1) {
            outGroup(device);
        } else if (device != null && i == 2) {
            add(device);
        }
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    private void uploadDeviceInGroupData(final Device device) {
        if (device.getParam(BleParam.class) != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            bleParam.setGroupId(this.editGroup.getGroupId());
            device.setParam(bleParam);
        }
        device.setExtParam((String) null);
        ((ObservableSubscribeProxy) Injection.net().updateParams(device.getDeviceId(), device.getParam(), device.getExtParam()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda34
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$uploadDeviceInGroupData$22((Disposable) obj);
            }
        }).doFinally(new ActSelectDivide$$ExternalSyntheticLambda35(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$uploadDeviceInGroupData$23(device, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActSelectDivide.this.showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
                ActSelectDivide.this.outGroup(device);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceInGroupData$22(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceInGroupData$23(Device device, Object obj) throws Exception {
        SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_CHANGED, true);
        Injection.repo().device().saveDevice(device);
        saveData(device, 1);
    }

    private void uploadDeviceOutGroupData(final Device device) {
        String extParamString;
        if (device.getParam(BleParam.class) != null) {
            device.setParam(HelpUtils.removeObjectKey((BleParam) device.getParam(BleParam.class), "groupId"));
        }
        if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S4) || device.getProductId().equals(ProductId.ID_SWITCH_PANEL_S4M)) {
            extParamString = RelateInfoUtils.initSmartPanelRelateInfoList(device.getProductId()).getExtParamString();
        } else {
            extParamString = (device.getProductId().equals(ProductId.ID_SENSOR_MR03) || device.getProductId().equals(ProductId.ID_SENSOR_MR04)) ? new WaveSensorExtParam().getSensorParamMapString() : "";
        }
        device.setExtParam(extParamString);
        if (device.getDeviceState() != null) {
            device.getDeviceState().setOnlineState(1);
        }
        ((ObservableSubscribeProxy) Injection.net().updateParams(device.getDeviceId(), device.getParam(), device.getExtParam()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$uploadDeviceOutGroupData$24((Disposable) obj);
            }
        }).doFinally(new ActSelectDivide$$ExternalSyntheticLambda35(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.lambda$uploadDeviceOutGroupData$25(Device.this, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceOutGroupData$24(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    static /* synthetic */ void lambda$uploadDeviceOutGroupData$25(Device device, Object obj) throws Exception {
        SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_CHANGED, true);
        Injection.repo().device().saveDevice(device);
        SmartToast.showShort(R.string.save_success);
    }

    private void synGroupSettingToDevice(Device device, boolean b2) {
        if (b2) {
            setting(device, new SmartPanelSettingState());
            return;
        }
        if (this.editGroup.getSetting() != null) {
            SmartPanelSettingState smartPanelSettingState = (SmartPanelSettingState) GsonUtils.fromJson(this.editGroup.getSetting(), new TypeToken<SmartPanelSettingState>(this) { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.6
            }.getType());
            if (smartPanelSettingState == null) {
                smartPanelSettingState = new SmartPanelSettingState();
            }
            setting(device, smartPanelSettingState);
        }
        setpowerOffStatus(device, this.editGroup.getMemorizePowerOff());
    }

    public void setpowerOffStatus(Device device, int memorizePowerOff) {
        if (device != null) {
            CmdAssistant.getLightCmdAssistant(device, new int[0]).setOnState(ActivityUtils.getTopActivity(), memorizePowerOff, 0, 0, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda16
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDivide.this.lambda$setpowerOffStatus$26((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setpowerOffStatus$26(Boolean bool) {
        if (bool.booleanValue()) {
            setResult(-1);
        }
    }

    private void setting(Device device, SmartPanelSettingState state) {
        if (device != null) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setSmartPanelMode(ActivityUtils.getTopActivity(), state.getDoubleLight(), state.getReverseLight(), state.getNightMode(), state.getEngravedTextMode(), state.getStartH(), state.getStartM(), state.getEndH(), state.getEndM(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.7
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    if (aBoolean.booleanValue()) {
                        ActSelectDivide.this.setResult(-1);
                    }
                }
            });
        }
    }

    private void synSensorGroupSettingToDevice(final Device device, boolean b2) {
        final WaveSensorState waveSensorState = new WaveSensorState();
        if (!b2) {
            waveSensorState = this.editGroup.getDeviceState().getWaveSensorState();
        }
        CmdAssistant.getDeviceAssistant(device, new int[0]).setIllumincance(ActivityUtils.getTopActivity(), false, waveSensorState.getIlluminance(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda21
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDivide.lambda$synSensorGroupSettingToDevice$28(Device.this, waveSensorState, (Boolean) obj);
            }
        });
    }

    static /* synthetic */ void lambda$synSensorGroupSettingToDevice$28(Device device, WaveSensorState waveSensorState, Boolean bool) {
        if (bool.booleanValue()) {
            CmdAssistant.getDeviceAssistant(device, new int[0]).setSensitivity(ActivityUtils.getTopActivity(), waveSensorState.getIlluminance(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda28
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDivide.lambda$synSensorGroupSettingToDevice$27((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncLocalScene(final Device device) {
        ((ObservableSubscribeProxy) Injection.net().getGroupLocalSceneAction(this.editGroup.getGroupId()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$syncLocalScene$29((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$syncLocalScene$30(device, (QuerySceneActionResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda22
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$syncLocalScene$31((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalScene$29(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalScene$30(Device device, QuerySceneActionResponse querySceneActionResponse) throws Exception {
        if (querySceneActionResponse.getTotal() > 0) {
            showLoadingDialog(String.format(getString(R.string.app_str_synchronizing_data), String.format(Locale.US, "%.2f%%", Float.valueOf((0.0f / querySceneActionResponse.getTotal()) * 100.0f))));
        }
        syncLocalDataToDevice(device, querySceneActionResponse.getRows());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalScene$31(Throwable th) throws Exception {
        dismissLoadingDialog();
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    private void syncLocalDataToDevice(Device device, List<QuerySceneActionResponse.RowsBean> rows) {
        this.mSceneNums = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        this.saveSceneStep = new HashMap();
        for (QuerySceneActionResponse.RowsBean rowsBean : rows) {
            BaseLocalParam executecommandObject = rowsBean.getExecutecommandObject();
            int intValue = (this.saveSceneStep.containsKey(Long.valueOf(rowsBean.getSceneid())) ? this.saveSceneStep.get(Long.valueOf(rowsBean.getSceneid())).intValue() : 0) + 1;
            this.saveSceneStep.put(Long.valueOf(rowsBean.getSceneid()), Integer.valueOf(intValue));
            arrayList.add(saveTempData(device, rowsBean.getScenenum(), rowsBean.getScenename(), new LocalSceneParam(executecommandObject.getTotalDelay(), intValue, executecommandObject.instruct, executecommandObject.sceneZone, this.editGroup.getGroupAddress())));
            this.mSceneNums.add(Integer.valueOf(rowsBean.getScenenum()));
        }
        batchControl(arrayList);
    }

    private Observable<ErrorTip> saveTempData(final Device device, final int sceneNum, final String sceneName, final LocalSceneParam content) {
        return Observable.create(new ObservableOnSubscribe<ErrorTip>() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.8
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<ErrorTip> emitter) throws Exception {
                List<String> list;
                if (ActSelectDivide.this.errorTipMap.size() > 0 && ActSelectDivide.this.errorTipMap.containsKey(-1) && (list = ActSelectDivide.this.errorTipMap.get(-1)) != null && list.size() >= 1) {
                    emitter.onComplete();
                } else {
                    ActSelectDivide.this.isReceiveState = true;
                    CmdAssistant.getSceneCmdAssistant(device, new int[0]).syncLocalSceneAction(ActivityUtils.getTopActivity(), sceneNum, content.getInstruct(), content.getStep(), content.getTime(), content.getZoneNum(), content.getAddress(), content.isCurState(), ProductRepository.getInfraredType(device.getProductId()), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.8.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(ResponseMsg responseMsg) {
                            if (ActSelectDivide.this.isReceiveState) {
                                if (responseMsg != null) {
                                    ActSelectDivide.this.mSyncNum++;
                                }
                                emitter.onNext(ActSelectDivide.this.getSameDeviceByResponse(sceneNum, sceneName, responseMsg));
                                emitter.onComplete();
                            }
                        }
                    });
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ErrorTip getSameDeviceByResponse(int sceneNum, String sceneName, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                return new ErrorTip(sceneNum, sceneName, 0);
            }
            if (responseMsg.getStateCode() == 153) {
                return new ErrorTip(sceneNum, sceneName, 153);
            }
            if (responseMsg.getStateCode() == 12) {
                return new ErrorTip(sceneNum, sceneName, 12);
            }
            if (responseMsg.getStateCode() == 13) {
                return new ErrorTip(sceneNum, sceneName, 13);
            }
            if (responseMsg.getStateCode() == 15) {
                return new ErrorTip(sceneNum, sceneName, 15);
            }
            return new ErrorTip(sceneNum, sceneName, -1);
        }
        return new ErrorTip(sceneNum, sceneName, -1);
    }

    private void batchControl(List<Observable<ErrorTip>> request) {
        this.errorTipMap = new HashMap();
        this.mSyncNum = 0;
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new io.reactivex.Observer<ErrorTip>() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.9
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(ErrorTip errorTip) {
                if (errorTip.getErrorType() != 0) {
                    if (ActSelectDivide.this.errorTipMap.containsKey(Integer.valueOf(errorTip.getErrorType()))) {
                        List<String> list = ActSelectDivide.this.errorTipMap.get(Integer.valueOf(errorTip.getErrorType()));
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        list.add(errorTip.getSceneName());
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(errorTip.getSceneName());
                        ActSelectDivide.this.errorTipMap.put(Integer.valueOf(errorTip.getErrorType()), arrayList);
                    }
                }
                String format = String.format(Locale.US, "%.2f%%", Float.valueOf((ActSelectDivide.this.mSyncNum / ActSelectDivide.this.mSceneNums.size()) * 100.0f));
                ActSelectDivide actSelectDivide = ActSelectDivide.this;
                actSelectDivide.showLoadingDialog(String.format(actSelectDivide.getString(R.string.app_str_synchronizing_data), format));
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (ActSelectDivide.this.errorTipMap.size() > 0) {
                    ActSelectDivide.this.showErrorTipDialog();
                    ActSelectDivide.this.dismissLoadingDialog();
                } else {
                    ActSelectDivide actSelectDivide = ActSelectDivide.this;
                    actSelectDivide.showSuccessDialog(actSelectDivide.getString(R.string.sync_success));
                    ActSelectDivide.this.dismissLoadingDialog();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showErrorTipDialog() {
        final Iterator<Map.Entry<Integer, List<String>>> it = this.errorTipMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<String>> next = it.next();
            int intValue = next.getKey().intValue();
            if (intValue == 12) {
                MessageDialog.show(this.activity, getString(R.string.local_scene_sync_fail), getString(R.string.local_scene_sync_error_0C)).setOkButton(getString(R.string.ok)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.11
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        it.remove();
                        ActSelectDivide.this.showErrorTipDialog();
                        return false;
                    }
                }).setCustomView(getCustomView(next.getValue())).setCancelable(false);
            } else if (intValue == 13) {
                MessageDialog.show(this.activity, getString(R.string.local_scene_sync_fail), getString(R.string.local_scene_sync_error_0D)).setOkButton(getString(R.string.ok)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.12
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        it.remove();
                        ActSelectDivide.this.showErrorTipDialog();
                        return false;
                    }
                }).setCustomView(getCustomView(next.getValue())).setCancelable(false);
            } else if (intValue == 15) {
                MessageDialog.show(this.activity, getString(R.string.local_scene_sync_fail), getString(R.string.local_scene_sync_error_0F)).setOkButton(getString(R.string.ok)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.10
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        it.remove();
                        ActSelectDivide.this.showErrorTipDialog();
                        return false;
                    }
                }).setCustomView(getCustomView(next.getValue())).setCancelable(false);
            } else {
                MessageDialog.show(this.activity, getString(R.string.local_scene_sync_fail), getString(next.getKey().intValue() == 153 ? R.string.local_scene_sync_error_99 : R.string.local_scene_sync_error_normal)).setOkButton(getString(R.string.ok)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.13
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        it.remove();
                        ActSelectDivide.this.showErrorTipDialog();
                        return false;
                    }
                }).setCancelable(false);
            }
        }
    }

    private void delGroupFromNet(final Group group) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(group.getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$delGroupFromNet$32((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectDivide.this.lambda$delGroupFromNet$33();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDivide$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDivide.this.lambda$delGroupFromNet$34(group, obj);
            }
        }, new SmartErrorComsumer());
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 1 || lightColorType == 2 || lightColorType == 3 || lightColorType == 4 || lightColorType == 5) {
            unSubscribePublicationAddress(group);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$32(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$33() throws Exception {
        dismissLoadingDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$34(Group group, Object obj) throws Exception {
        Injection.repo().group().removeGroupFromDb(group.getId());
        finishActivity();
    }

    private void unSubscribePublicationAddress(Group group) {
        LHomeLog.i(getClass(), "delete device--->" + group);
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), group.getGroupAddress(), ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()), new int[0]);
    }

    private View getCustomView(List<String> names) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_custom_list, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_scene_error_tip, names) { // from class: com.ltech.smarthome.ui.group.ActSelectDivide.14
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
            }
        });
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSmartPanelDevice(Device device) {
        return device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S4) || device.getProductId().equals(ProductId.ID_SWITCH_PANEL_S4M) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO);
    }

    static class ErrorTip {
        private int errorType;
        private String sceneName;
        private int sceneNum;

        public ErrorTip(int sceneNum, String sceneName, int errorType) {
            this.sceneNum = sceneNum;
            this.sceneName = sceneName;
            this.errorType = errorType;
        }

        public int getSceneNum() {
            return this.sceneNum;
        }

        public void setSceneNum(int sceneNum) {
            this.sceneNum = sceneNum;
        }

        public String getSceneName() {
            return this.sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public int getErrorType() {
            return this.errorType;
        }

        public void setErrorType(int errorType) {
            this.errorType = errorType;
        }
    }
}