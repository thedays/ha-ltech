package com.ltech.smarthome.ui.device.base;

import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.EurPanelGroupParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.group.AddGroupResponse;
import com.ltech.smarthome.ui.config.ActAddDeviceVM;
import com.ltech.smarthome.ui.config.ActMeshScan;
import com.ltech.smarthome.ui.config.ActMeshScanProxy;
import com.ltech.smarthome.ui.config.ActStepsDelete;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.light.ActAutoNetTimeSetting;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.ui.group.ActSelectDeviceNew;
import com.ltech.smarthome.ui.log.ActLocalDeviceLog;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.DelFailDialog;
import com.ltech.smarthome.view.dialog.DeviceFrequencyAndIntervalDialog;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.RoomSelectDialog;
import com.ltech.smarthome.view.dialog.SelectDeviceIconDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.ltech.smarthome.view.dialog.SelectListSubLineDialog;
import com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog;
import com.ltech.smarthome.view.dialog.SetBleTypeDialog;
import com.ltech.smarthome.view.dialog.TimeSelectDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class BaseDeviceSetActivity<V extends ViewDataBinding, VM extends BaseDeviceSetViewModel> extends VMActivity<V, VM> {
    protected ImageTipDialog dialog;
    protected MultipleItemRvAdapter<LightSettingState, BaseViewHolder> mAdapter;
    protected boolean[] selectArray;
    private SetBleTypeDialog addGroupDialog = null;
    protected boolean isfirst = true;
    protected int powerOnSecPos = 0;
    protected int powerOnMsPos = 0;
    protected int onSecPos = 0;
    protected int onMsPos = 0;
    protected int offSecPos = 0;
    protected int offMsPos = 0;
    protected int sceneSecPos = 0;
    protected int sceneMsPos = 0;
    protected final Runnable getBatteryRunnable = new Runnable() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.5
        @Override // java.lang.Runnable
        public void run() {
            SmartToast.showCenterShort(BaseDeviceSetActivity.this.getString(R.string.search_fail));
            if (BaseDeviceSetActivity.this.dialog != null) {
                BaseDeviceSetActivity.this.dialog.dismissDialog();
            }
        }
    };
    final int COUNTS = 10;
    final long DURATION = 5000;
    long[] mHits = new long[10];

    static /* synthetic */ void lambda$delGroup$29(Object obj) throws Exception {
    }

    static /* synthetic */ boolean lambda$showNoPermissionDialog$33(BaseDialog baseDialog, View view) {
        return false;
    }

    protected void refreshTimeView(BaseViewHolder helper) {
    }

    protected void updatePowerStateDialogData(SelectPowerOnStateDialog.OnOffState state) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        showDeleteDeviceDialog();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((BaseDeviceSetViewModel) this.mViewModel).showDeleteDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).showForceDeleteDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$1((Void) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).showEditNameDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).showEditRoomDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).showSelectDeviceIconDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).showCreateGroupDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).showPowerStateDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$6((Void) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).upgradeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda14
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$7((Void) obj);
            }
        });
        ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda15
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseDeviceSetActivity.this.lambda$startObserve$8((Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r1) {
        showForceDeleteTipDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showEditNameDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showEditRoomDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r1) {
        showSelectDeviceIconDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r1) {
        showCreateGroupDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r1) {
        showPowerStateDialog(((BaseDeviceSetViewModel) this.mViewModel).onState.getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r1) {
        goUpgrade();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(Device device) {
        if (this.isfirst) {
            this.isfirst = false;
            ((BaseDeviceSetViewModel) this.mViewModel).checkVersion();
        }
        if (needEngineerSet()) {
            setEditString("             ");
        }
    }

    protected void showDeleteDeviceDialog() {
        Device value = ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue();
        if (value != null) {
            String productId = value.getProductId();
            productId.hashCode();
            switch (productId) {
                case "3503908278750336":
                case "3508084028410496":
                case "4057094887997440":
                case "3503908725640320":
                case "120042314364601":
                case "120042314375001":
                case "120042314380701":
                case "120042314382401":
                case "120042314384101":
                case "120042616112401":
                case "3721596935046208":
                case "3959367613661440":
                case "3503907950824576":
                    NavUtils.destination(ActStepsDelete.class).withString(Constants.PRODUCT_ID, value.getProductId()).withLong(Constants.CONTROL_ID, value.getId()).withDefaultRequestCode().navigation(this);
                    break;
            }
            return;
        }
        if (value != null) {
            if ((value.getProductId().equals(ProductId.ID_SMART_PANEL_S6B) && RelateInfoUtils.needShowTipDialog()) || value.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB) || value.getProductId().equals(ProductId.ID_DOOR_SENSOR)) {
                RelateInfoUtils.showImageTipDialog(value, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        BaseDeviceSetActivity.this.lambda$showDeleteDeviceDialog$9(imageTipDialog);
                    }
                });
                return;
            } else {
                showDeleteDialog();
                return;
            }
        }
        showDeleteDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeleteDeviceDialog$9(ImageTipDialog imageTipDialog) {
        imageTipDialog.dismissDialog();
        showDeleteDialog();
    }

    public void showPowerStateDialog(PowerState state) {
        ((BaseDeviceSetViewModel) this.mViewModel).powerOnStateDialog = SelectPowerOnStateDialog.asDefault().controlObject(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()).setPowerState(state).setOnSaveListener(new AnonymousClass1());
        if (((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getProductId().equals(ProductId.ID_BLE_SWITCH)) {
            ((BaseDeviceSetViewModel) this.mViewModel).powerOnStateDialog.setTitle(getString(R.string.light_on_state));
        }
        ((BaseDeviceSetViewModel) this.mViewModel).powerOnStateDialog.showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$1, reason: invalid class name */
    class AnonymousClass1 implements SelectPowerOnStateDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass1() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnSaveListener
        public boolean onSave(final SelectPowerOnStateDialog.OnOffState onOffState) {
            if (onOffState.getState().supportAll) {
                BaseDeviceSetActivity.this.getCmdHelper().setOnState(BaseDeviceSetActivity.this, onOffState.getState(), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$1$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        BaseDeviceSetActivity.AnonymousClass1.this.lambda$onSave$0(onOffState, (Boolean) obj);
                    }
                });
                return true;
            }
            BaseDeviceSetActivity.this.getCmdHelper().setOnState(BaseDeviceSetActivity.this, onOffState.getMainValue(), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    BaseDeviceSetActivity.AnonymousClass1.this.lambda$onSave$1(onOffState, (Boolean) obj);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(SelectPowerOnStateDialog.OnOffState onOffState, Boolean bool) {
            if (bool.booleanValue()) {
                BaseDeviceSetActivity.this.setOnStateAll(onOffState);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$1(SelectPowerOnStateDialog.OnOffState onOffState, Boolean bool) {
            if (bool.booleanValue()) {
                BaseDeviceSetActivity.this.setOnState(onOffState);
            }
        }
    }

    public void showPowerStateDialog(int mainValue, int subValue) {
        SelectPowerOnStateDialog.asDefault().controlObject(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()).setSelectPosition(mainValue - 1).setSubValue(subValue).setOnSaveListener(new AnonymousClass2()).showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$2, reason: invalid class name */
    class AnonymousClass2 implements SelectPowerOnStateDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass2() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectPowerOnStateDialog.OnSaveListener
        public boolean onSave(final SelectPowerOnStateDialog.OnOffState onOffState) {
            if (!BaseDeviceSetActivity.this.isVirtual()) {
                BaseDeviceSetActivity.this.getCmdHelper().setOnState(BaseDeviceSetActivity.this, onOffState.getMainValue(), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$2$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        BaseDeviceSetActivity.AnonymousClass2.this.lambda$onSave$0(onOffState, (Boolean) obj);
                    }
                });
                return true;
            }
            BaseDeviceSetActivity.this.setOnState(onOffState);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(SelectPowerOnStateDialog.OnOffState onOffState, Boolean bool) {
            if (bool.booleanValue()) {
                BaseDeviceSetActivity.this.setOnState(onOffState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOnState(SelectPowerOnStateDialog.OnOffState onOffState) {
        updatePowerStateDialogData(onOffState);
        ReplaceHelper.instance().backupData(this, ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.POWER_STATUS, getCmdHelper().setOnState(onOffState.getMainValue(), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue()), LightUtils.progress2BrtHasBelowOne(onOffState.getSubValue())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOnStateAll(SelectPowerOnStateDialog.OnOffState onOffState) {
        updatePowerStateDialogData(onOffState);
        ReplaceHelper.instance().backupData(this, ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.POWER_STATUS, getCmdHelper().setOnState(onOffState.getState()));
    }

    protected void initTimeAdapter(RecyclerView recyclerView, String title, final boolean includeSceneTime) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new LightSettingState(LightSettingState.TYPE_DIY, title));
        this.selectArray = new boolean[arrayList.size()];
        MultipleItemRvAdapter<LightSettingState, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<LightSettingState, BaseViewHolder>(arrayList) { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(LightSettingState lightSettingState) {
                return lightSettingState.getType();
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<LightSettingState, BaseViewHolder>(this) { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.3.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, LightSettingState data, int position) {
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_on_off_time_normal;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return LightSettingState.TYPE_DEFAULT;
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<LightSettingState, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.3.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.item_select_on_off_time_diy;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return LightSettingState.TYPE_DIY;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, LightSettingState data, int position) {
                        helper.setText(R.id.tv_name, data.getTitleName());
                        BaseDeviceSetActivity.this.refreshTimeView(helper);
                        ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
                        if (BaseDeviceSetActivity.this.selectArray[position]) {
                            helper.setGone(R.id.layout_elec, true);
                            helper.setGone(R.id.layout_on, true);
                            helper.setGone(R.id.layout_off, true);
                            if (includeSceneTime) {
                                helper.setGone(R.id.layout_scene, true);
                            }
                            helper.setImageResource(R.id.iv_select, R.mipmap.ic_up_gray);
                        } else {
                            helper.setGone(R.id.layout_elec, false);
                            helper.setGone(R.id.layout_on, false);
                            helper.setGone(R.id.layout_off, false);
                            if (includeSceneTime) {
                                helper.setGone(R.id.layout_scene, false);
                            }
                            helper.setImageResource(R.id.iv_select, R.mipmap.ic_down_gray);
                        }
                        helper.setGone(R.id.iv_elec_select, BaseDeviceSetActivity.this.isOwnerOrManager());
                        helper.setGone(R.id.iv_on_select, BaseDeviceSetActivity.this.isOwnerOrManager());
                        helper.setGone(R.id.iv_off_select, BaseDeviceSetActivity.this.isOwnerOrManager());
                        if (includeSceneTime) {
                            helper.setGone(R.id.iv_scene_select, BaseDeviceSetActivity.this.isOwnerOrManager());
                        }
                        if (BaseDeviceSetActivity.this.isOwnerOrManager()) {
                            helper.addOnClickListener(R.id.layout_elec, R.id.layout_on, R.id.layout_off);
                            if (includeSceneTime) {
                                helper.addOnClickListener(R.id.layout_scene);
                            }
                        }
                    }
                });
            }
        };
        this.mAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseDeviceSetActivity.this.lambda$initTimeAdapter$10(baseQuickAdapter, view, i);
            }
        });
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.4
            @Override // com.chad.library.adapter.base.listener.OnItemChildClickListener
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.layout_elec /* 2131297460 */:
                        BaseDeviceSetActivity.this.showPowerOnTimeDialog();
                        break;
                    case R.id.layout_off /* 2131297557 */:
                        BaseDeviceSetActivity.this.showOnOffTimeDialog(false);
                        break;
                    case R.id.layout_on /* 2131297558 */:
                        BaseDeviceSetActivity.this.showOnOffTimeDialog(true);
                        break;
                    case R.id.layout_scene /* 2131297611 */:
                        BaseDeviceSetActivity.this.showSceneTimeDialog();
                        break;
                }
            }
        });
        this.mAdapter.finishInitialize();
        this.mAdapter.bindToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTimeAdapter$10(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyDataSetChanged();
    }

    protected void showPowerOnTimeDialog() {
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
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.powerOnSecPos).setSelectSecPosition(this.powerOnMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda28
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                BaseDeviceSetActivity.this.lambda$showPowerOnTimeDialog$12(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPowerOnTimeDialog$12(int i, int i2) {
        this.powerOnSecPos = i;
        this.powerOnMsPos = i2;
        getCmdHelper().setPowerOnTime(this, (this.powerOnSecPos * 1000) + (this.powerOnMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda25
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showPowerOnTimeDialog$11((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPowerOnTimeDialog$11(Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this, ((BaseDeviceSetViewModel) this.mViewModel).deviceId, UpdateBackDataRequest.POWER_FADE_TIME, getCmdHelper().setPowerOnTime((this.powerOnSecPos * 1000) + (this.powerOnMsPos * 100)));
            this.mAdapter.notifyDataSetChanged();
        }
    }

    protected void showOnOffTimeDialog(final boolean on) {
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
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(on ? this.onSecPos : this.offSecPos).setSelectSecPosition(on ? this.onMsPos : this.offMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda32
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                BaseDeviceSetActivity.this.lambda$showOnOffTimeDialog$14(on, i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOnOffTimeDialog$14(boolean z, int i, int i2) {
        if (z) {
            this.onSecPos = i;
            this.onMsPos = i2;
        } else {
            this.offSecPos = i;
            this.offMsPos = i2;
        }
        getCmdHelper().setOnOffTime(this, (this.onSecPos * 1000) + (this.onMsPos * 100), (this.offSecPos * 1000) + (this.offMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda21
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showOnOffTimeDialog$13((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOnOffTimeDialog$13(Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.FADE_TIME, getCmdHelper().setOnOffTime((this.onSecPos * 1000) + (this.onMsPos * 100), (this.offSecPos * 1000) + (this.offMsPos * 100)));
            ReplaceHelper.instance().backupData(this, ((BaseDeviceSetViewModel) this.mViewModel).deviceId);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    protected void showSceneTimeDialog() {
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
        TimeSelectDialog.asDefault().setTitle(getString(R.string.please_select_time)).setMinList(arrayList).setSecList(arrayList2).withUnit(true).setMinUnit(getString(R.string.sec)).setSecUnit(getString(R.string.ms)).setSelectMinPosition(this.sceneSecPos).setSelectSecPosition(this.sceneMsPos).setSelectListener(new TimeSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda31
            @Override // com.ltech.smarthome.view.dialog.TimeSelectDialog.SelectListener
            public final void confirm(int i3, int i4) {
                BaseDeviceSetActivity.this.lambda$showSceneTimeDialog$16(i3, i4);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSceneTimeDialog$16(int i, int i2) {
        this.sceneSecPos = i;
        this.sceneMsPos = i2;
        getCmdHelper().setSceneOnTime(this, (this.sceneSecPos * 1000) + (this.sceneMsPos * 100), new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda24
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showSceneTimeDialog$15((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSceneTimeDialog$15(Boolean bool) {
        if (bool.booleanValue()) {
            this.mAdapter.notifyDataSetChanged();
            ReplaceHelper.instance().backupData(this, ((BaseDeviceSetViewModel) this.mViewModel).deviceId, UpdateBackDataRequest.SCENE_FADE_TIME, getCmdHelper().setSceneOnTime((this.sceneSecPos * 1000) + (this.sceneMsPos * 100)));
        }
    }

    protected void showSensitivityDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.low_sensitivity));
        arrayList.add(getString(R.string.medium_sensitivity));
        arrayList.add(getString(R.string.high_sensitivity));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(getString(R.string.low_sensitivity_tip));
        arrayList2.add(getString(R.string.medium_sensitivity_tip));
        arrayList2.add(getString(R.string.high_sensitivity_tip));
        SelectListSubLineDialog.asDefault(true).setTitle(getString(R.string.knob_sensitivity)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(((BaseDeviceSetViewModel) this.mViewModel).sensitivity.getValue().intValue() - 1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda22
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showSensitivityDialog$18((Integer) obj);
            }
        }).setSelectList(arrayList, arrayList2).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSensitivityDialog$18(final Integer num) {
        CmdAssistant.getDeviceAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).setKnobSensitivity(this, num.intValue() + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda34
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showSensitivityDialog$17(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSensitivityDialog$17(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            ((BaseDeviceSetViewModel) this.mViewModel).sensitivity.setValue(Integer.valueOf(num.intValue() + 1));
            ReplaceHelper.instance().backupData(this, ((BaseDeviceSetViewModel) this.mViewModel).deviceId, UpdateBackDataRequest.KNOB_SENSITIVITY, CmdAssistant.getDeviceAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).setKnobSensitivity(num.intValue() + 1));
        }
    }

    private void showCreateGroupDialog() {
        ActAddDeviceVM.ProductItem productItem;
        if (ProductId.BLE_GROUP_DIM_LIGHT.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupDim(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_CT_LIGHT.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupCt(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_RGB_LIGHT.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupRgb(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_RGBW_LIGHT.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupRgbw(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_RGBWY_LIGHT.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupRgbwy(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_RGBWY_CC_LIGHT.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupRgbwyCC(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_SWITCH.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupSwitch(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_SWITCH_PANEL_S1C.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupSwitchPanelS1(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_SWITCH_PANEL_S2C.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupSwitchPanelS2(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_SWITCH_PANEL_S3C.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupSwitchPanelS3(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_SWITCH_PANEL_S4.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupSwitchPanelS4(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_SWITCH_PANEL_S4M.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupSwitchPanelS4M(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_CURTAIN.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupCurtain(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_DRY_CURTAIN.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupDryCurtain(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_WAVE_SENSOR_MR03.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupWaveSensorMR03(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_WAVE_SENSOR_MR04.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupWaveSensorMR04(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_WAVE_SENSOR_MS03.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupWaveSensorMS03(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_DRY_DREAM_CURTAIN.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupDryDreamCurtain(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_SWITCH_PANEL_S6PRO.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupSwitchPanelS6Pro(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_SWITCH_PANEL_G4.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupSwitchPanelG4(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_EUR_PANEL_EB1.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupEb1(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_EUR_PANEL_EB2.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupEb2(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_EUR_PANEL_EB5.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupEb5(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_AS_PANEL_UB1.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupUb1(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_AS_PANEL_UB2.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupUb2(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_AS_PANEL_UB4.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupUb4(), ActSelectDeviceNew.class);
        } else if (ProductId.BLE_GROUP_AS_PANEL_UB5.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue()))) {
            productItem = new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupUb5(), ActSelectDeviceNew.class);
        } else {
            productItem = ProductId.BLE_GROUP_KBS.equals(ProductRepository.getGroupKey(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue())) ? new ActAddDeviceVM.ProductItem(Injection.productFactory().createBleGroupKbs(), ActSelectDeviceNew.class) : null;
        }
        if (productItem != null) {
            if ("2".equals(productItem.productInfo.getProductId())) {
                showAddGroupDialog(productItem, false);
            } else if ("3".equals(productItem.productInfo.getProductId())) {
                showAddGroupDialog(productItem, true);
            } else {
                nav(productItem.productInfo, productItem.navClz, new String[0]);
            }
        }
    }

    protected void showSelectDeviceIconDialog() {
        SelectDeviceIconDialog.asDefault().imageIndex(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getImageIndex()).setOnSaveListener(new AnonymousClass6()).showDialog(this);
    }

    /* renamed from: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$6, reason: invalid class name */
    class AnonymousClass6 implements SelectDeviceIconDialog.OnSaveListener {
        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public void cancel() {
        }

        AnonymousClass6() {
        }

        @Override // com.ltech.smarthome.view.dialog.SelectDeviceIconDialog.OnSaveListener
        public boolean onSave(final int selectPos) {
            ((ObservableSubscribeProxy) Injection.net().updateDeviceImgIndex(((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).controlDevice.getValue().getDeviceId(), selectPos).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$6$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BaseDeviceSetActivity.AnonymousClass6.this.lambda$onSave$0((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new BaseDeviceSetActivity$$ExternalSyntheticLambda2(BaseDeviceSetActivity.this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(BaseDeviceSetActivity.this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$6$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BaseDeviceSetActivity.AnonymousClass6.this.lambda$onSave$1(selectPos, obj);
                }
            }, new SmartErrorComsumer());
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$0(Disposable disposable) throws Exception {
            BaseDeviceSetActivity baseDeviceSetActivity = BaseDeviceSetActivity.this;
            baseDeviceSetActivity.showLoadingDialog(baseDeviceSetActivity.getString(R.string.saving));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSave$1(int i, Object obj) throws Exception {
            Device value = ((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).controlDevice.getValue();
            value.setImageIndex(i);
            Injection.repo().device().saveDevice(value);
            ((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).controlDevice.setValue(value);
            SmartToast.showShort(R.string.save_success);
        }
    }

    protected void showEditNameDialog() {
        EditDialog.asDefault().setContent(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getDeviceName()).setTitle(getString(R.string.edit_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showEditNameDialog$19((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditNameDialog$19(String str) {
        ((BaseDeviceSetViewModel) this.mViewModel).changeDeviceName(str);
    }

    protected void showDeleteDialog() {
        if (((BaseDeviceSetViewModel) this.mViewModel).isDeleteByMeshCmd()) {
            DelFailDialog.asDefault().setTitle(getString(R.string.del_ble_device_before_1)).setContent(getString(R.string.del_ble_device_before_2)).setContent2(getString(R.string.del_ble_device_before_3)).setConfirmString(getString(R.string.delete_device)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda26
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    BaseDeviceSetActivity.this.lambda$showDeleteDialog$20((Void) obj);
                }
            }).setCancelString(getString(R.string.i_know)).showDialog(this);
        } else {
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_del_device)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda27
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$showDeleteDialog$21;
                    lambda$showDeleteDialog$21 = BaseDeviceSetActivity.this.lambda$showDeleteDialog$21(baseDialog, view);
                    return lambda$showDeleteDialog$21;
                }
            }).setCancelButton(getString(R.string.cancel));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeleteDialog$20(Void r1) {
        ((BaseDeviceSetViewModel) this.mViewModel).deleteDevice();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDeleteDialog$21(BaseDialog baseDialog, View view) {
        ((BaseDeviceSetViewModel) this.mViewModel).deleteDevice();
        return false;
    }

    protected void showForceDeleteTipDialog() {
        DelFailDialog.asDefault().setTitle(getString(R.string.del_ble_device_fail)).setContent(getString(R.string.del_ble_device_reason)).setContent2(getString(R.string.force_del_tip)).setConfirmString(getString(R.string.force_del)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda29
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showForceDeleteTipDialog$22((Void) obj);
            }
        }).setCancelString(getString(R.string.cancel)).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showForceDeleteTipDialog$22(Void r1) {
        showForceDeleteDialog();
    }

    protected void showForceDeleteDialog() {
        MessageDialog.show(this, getString(R.string.tips), getString(R.string.tip_force_del_device)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showForceDeleteDialog$23;
                lambda$showForceDeleteDialog$23 = BaseDeviceSetActivity.this.lambda$showForceDeleteDialog$23(baseDialog, view);
                return lambda$showForceDeleteDialog$23;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showForceDeleteDialog$23(BaseDialog baseDialog, View view) {
        ((BaseDeviceSetViewModel) this.mViewModel).forceDelDevice();
        return false;
    }

    protected void showEditRoomDialog() {
        if (((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getCanSetRoom().getValue().booleanValue()) {
            RoomSelectDialog.asDefault().setFloorList(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setSelectListener(new RoomSelectDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.7
                @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
                public void confirm(int floorPosition, int roomPosition) {
                    ((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).roomPickHelper.setSelectPosition(floorPosition, roomPosition);
                    ((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).changeDevicePlace(((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).roomPickHelper.getSelectFloorId(), ((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).roomPickHelper.getSelectRoomId());
                }

                @Override // com.ltech.smarthome.view.dialog.RoomSelectDialog.SelectListener
                public void onFloorSelect(RoomSelectDialog dialog, int floorPosition) {
                    dialog.setRoomList(((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).roomPickHelper.getRoomNames(floorPosition));
                    dialog.notifyDialog();
                }
            }).showDialog(this);
        }
    }

    private void showAddGroupDialog(final ActAddDeviceVM.ProductItem item, final boolean bleGroup) {
        this.addGroupDialog = SetBleTypeDialog.asDefault().setTitle(getString(R.string.add_group)).setLabel(getString(R.string.group_name)).setContent(getString(item.productInfo.getAddNameRes())).setOnSaveListener(new SetBleTypeDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.8
            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSaveListener
            public boolean onSave(String name, boolean changeType, int type, int outputType, int floorPos, int roomPos, int zoneControl, int controlType) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(R.string.input_name);
                    return false;
                }
                if (item.productInfo.getHardwareId().equals(Integer.toString(22)) || item.productInfo.getHardwareId().equals(Integer.toString(23)) || item.productInfo.getHardwareId().equals(Integer.toString(24)) || item.productInfo.getHardwareId().equals(Integer.toString(28)) || item.productInfo.getHardwareId().equals(Integer.toString(27)) || item.productInfo.getHardwareId().equals(Integer.toString(29)) || item.productInfo.getHardwareId().equals(Integer.toString(30))) {
                    EurHelper.paramExt = RelateInfoUtils.initEurPanel(zoneControl, controlType).getExtParamString();
                }
                ((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).roomPickHelper.setSelectPosition(floorPos, roomPos);
                long selectFloorId = ((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).roomPickHelper.getSelectFloorId();
                long selectRoomId = ((BaseDeviceSetViewModel) BaseDeviceSetActivity.this.mViewModel).roomPickHelper.getSelectRoomId();
                if (bleGroup) {
                    BaseDeviceSetActivity.this.createBleGroup(item, selectFloorId, selectRoomId, name);
                    return true;
                }
                BaseDeviceSetActivity.this.nav(item.productInfo, item.navClz, name);
                BaseDeviceSetActivity.this.dismissGroupDialog();
                return true;
            }
        });
        String hardwareId = item.productInfo.getHardwareId();
        if (hardwareId.equals(Integer.toString(22))) {
            this.addGroupDialog.eurPanelType(1);
        } else if (hardwareId.equals(Integer.toString(23))) {
            this.addGroupDialog.eurPanelType(2);
        } else if (hardwareId.equals(Integer.toString(24))) {
            this.addGroupDialog.eurPanelType(5);
        } else if (hardwareId.equals(Integer.toString(29))) {
            this.addGroupDialog.setAsPanelType(4);
        } else if (hardwareId.equals(Integer.toString(30))) {
            this.addGroupDialog.setAsPanelType(5);
        } else if (hardwareId.equals(Integer.toString(27))) {
            this.addGroupDialog.setAsPanelType(1);
        } else if (hardwareId.equals(Integer.toString(28))) {
            this.addGroupDialog.setAsPanelType(2);
        }
        this.addGroupDialog.setSelectRoom(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.canSetRoom()).setFloorList(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getCurrentFloorNames()).setRoomList(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getSelectRoomPosition()).setOnSelectFloorListener(new SetBleTypeDialog.OnSelectFloorListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.view.dialog.SetBleTypeDialog.OnSelectFloorListener
            public final void selectFloor(SetBleTypeDialog setBleTypeDialog, int i, String str) {
                BaseDeviceSetActivity.this.lambda$showAddGroupDialog$24(setBleTypeDialog, i, str);
            }
        }).setAddGroup(true).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAddGroupDialog$24(SetBleTypeDialog setBleTypeDialog, int i, String str) {
        this.addGroupDialog.setRoomList(((BaseDeviceSetViewModel) this.mViewModel).roomPickHelper.getRoomNames(i));
        this.addGroupDialog.initRoomData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createBleGroup(ActAddDeviceVM.ProductItem item, long floorId, long roomId, String groupName) {
        if (((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue() == null) {
            return;
        }
        if (item.productInfo.getHardwareId().equals(Integer.toString(22)) || item.productInfo.getHardwareId().equals(Integer.toString(23)) || item.productInfo.getHardwareId().equals(Integer.toString(24)) || item.productInfo.getHardwareId().equals(Integer.toString(28)) || item.productInfo.getHardwareId().equals(Integer.toString(27)) || item.productInfo.getHardwareId().equals(Integer.toString(29)) || item.productInfo.getHardwareId().equals(Integer.toString(30))) {
            setGroupPublishAddress(item, floorId, roomId, groupName);
        } else {
            addGroup(item, floorId, roomId, groupName);
        }
    }

    private void addGroup(final ActAddDeviceVM.ProductItem item, final long floorId, final long roomId, final String groupName) {
        getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                BaseDeviceSetActivity.this.lambda$addGroup$25();
            }
        });
        ((ObservableSubscribeProxy) Injection.net().addGroup(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getPlaceId(), floorId, roomId, groupName, item.productInfo.getProductId(), item.productInfo.getHardwareId(), new ArrayList()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new BaseDeviceSetActivity$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetActivity.this.lambda$addGroup$26(groupName, floorId, roomId, item, (AddGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addGroup$25() {
        showLoadingDialog(getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addGroup$26(String str, long j, long j2, ActAddDeviceVM.ProductItem productItem, AddGroupResponse addGroupResponse) throws Exception {
        Group group = new Group();
        group.setGroupName(str);
        group.setGroupAddress(Integer.parseInt(addGroupResponse.getIndex(), 16));
        group.setPlaceId(addGroupResponse.getPlaceid());
        group.setGroupId(addGroupResponse.getGroupid());
        group.setModuleType(addGroupResponse.getType());
        group.setControlType(addGroupResponse.getColortype());
        group.setDeviceIds(new ArrayList());
        group.setGroupState(new DeviceState());
        group.setFloorId(j);
        group.setRoomId(j2);
        group.setGroupIndex(Integer.parseInt(addGroupResponse.getIndex(), 16));
        group.setCreatetime(addGroupResponse.getCreatetime());
        group.setParam(addGroupResponse.getParam());
        group.setExtParam(addGroupResponse.getParamext());
        group.setEditTime(System.currentTimeMillis());
        NavUtils.destination(productItem.navClz).withBoolean(Constants.CREATE, true).withLong(Constants.PLACE_ID, ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getPlaceId()).withLong(Constants.GROUP_ID, Injection.repo().group().lambda$saveGroup$1(group)).navigation(this);
        SPUtils.getInstance().put(Constants.USER_CUR_ROOM_FOR_GROUP, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroupPublishAddress$27() {
        showLoadingDialog(getString(R.string.creating));
    }

    private void setGroupPublishAddress(final ActAddDeviceVM.ProductItem item, final long floorId, final long roomId, final String groupName) {
        getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BaseDeviceSetActivity.this.lambda$setGroupPublishAddress$27();
            }
        });
        ((ObservableSubscribeProxy) Injection.net().addGroup(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getPlaceId(), floorId, roomId, "", ProductId.CC.getModuleType(ProductId.BLE_GROUP_REMOTE_CONTROLLER), ProductId.CC.getControlType(ProductId.BLE_GROUP_REMOTE_CONTROLLER), new ArrayList()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).doFinally(new BaseDeviceSetActivity$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetActivity.this.lambda$setGroupPublishAddress$28(item, floorId, roomId, groupName, (AddGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroupPublishAddress$28(ActAddDeviceVM.ProductItem productItem, long j, long j2, String str, AddGroupResponse addGroupResponse) throws Exception {
        EurHelper.eurGroupParam = new EurPanelGroupParam();
        EurHelper.eurGroupParam.setPublicationAddress(Integer.parseInt(addGroupResponse.getIndex(), 16));
        EurHelper.eurGroupParam.setPublicationId(addGroupResponse.getGroupid());
        addGroup(productItem, j, j2, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissGroupDialog() {
        SetBleTypeDialog setBleTypeDialog = this.addGroupDialog;
        if (setBleTypeDialog != null) {
            setBleTypeDialog.dismissDialog();
            this.addGroupDialog = null;
        }
    }

    private void delGroup(long groupId) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(groupId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceSetActivity.lambda$delGroup$29(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(ProductInfo info, Class clazz, String... deviceName) {
        if (clazz == null) {
            return;
        }
        ConfigHelper.instance().reset();
        ConfigHelper.instance().placeId = ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getPlaceId();
        ConfigHelper.instance().roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
        ConfigHelper.instance().productInfo = info;
        if (deviceName.length > 0) {
            ConfigHelper.instance().deviceName = deviceName[0];
        }
        NavUtils.destination(clazz).navigation(this);
    }

    protected void goUpgrade() {
        NavUtils.destination(ActMeshScanProxy.class).withLong(Constants.CONTROL_ID, ((BaseDeviceSetViewModel) this.mViewModel).controlId).withLong(Constants.PLACE_ID, ((BaseDeviceSetViewModel) this.mViewModel).getCurPlace().getPlaceId()).navigation(ActivityUtils.getTopActivity());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (((BaseDeviceSetViewModel) this.mViewModel).getCurPlace() != null) {
            if (((BaseDeviceSetViewModel) this.mViewModel).getCurPlace().isManager() || ((BaseDeviceSetViewModel) this.mViewModel).getCurPlace().isOwner()) {
                long[] jArr = this.mHits;
                System.arraycopy(jArr, 1, jArr, 0, jArr.length - 1);
                long[] jArr2 = this.mHits;
                jArr2[jArr2.length - 1] = SystemClock.uptimeMillis();
                if (this.mHits[0] < SystemClock.uptimeMillis() - 5000 || !needEngineerSet()) {
                    return;
                }
                showEnginnerDialog();
            }
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private boolean needEngineerSet() {
        Device value = ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue();
        if (value == null || value.getProductId() == null) {
            return false;
        }
        String productId = value.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1343252468:
                if (productId.equals(ProductId.ID_RS485_BLE)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -961541705:
                if (productId.equals(ProductId.ID_SMART_PANEL_S6B)) {
                    c2 = 14;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 15;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 16;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 17;
                    break;
                }
                break;
            case 70457728:
                if (productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                    c2 = 18;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 19;
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = 20;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 21;
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 22;
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 23;
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = 24;
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = 25;
                    break;
                }
                break;
            case 1479279198:
                if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = 27;
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 28;
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = 29;
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 30;
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 31;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                return true;
            default:
                return false;
        }
    }

    protected void showEnginnerDialog() {
        ArrayList arrayList = new ArrayList();
        SelectListDialog selectPosition = SelectListDialog.asDefault(false).setTitle(getString(R.string.app_str_engineering_mode)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1);
        arrayList.add("TTL");
        arrayList.add(getString(R.string.auto_net_time));
        arrayList.add(getString(R.string.device_log));
        selectPosition.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda30
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showEnginnerDialog$30((Integer) obj);
            }
        }).setSelectList(arrayList);
        selectPosition.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEnginnerDialog$30(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            showEditTTLDialog();
            return;
        }
        if (intValue == 1) {
            NavUtils.destination(ActAutoNetTimeSetting.class).withLong(Constants.CONTROL_ID, ((BaseDeviceSetViewModel) this.mViewModel).controlId).withBoolean(Constants.GROUP_CONTROL, false).navigation(this);
            return;
        }
        if (intValue == 2) {
            NavUtils.destination(ActLocalDeviceLog.class).withLong(Constants.CONTROL_ID, ((BaseDeviceSetViewModel) this.mViewModel).controlId).navigation(this);
        } else if (intValue == 3) {
            cleanDeviceCache();
        } else {
            if (intValue != 4) {
                return;
            }
            NavUtils.destination(ActMeshScan.class).withLong(Constants.CONTROL_ID, ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().getId()).navigation(this);
        }
    }

    protected void showEditTimesDialog() {
        DeviceFrequencyAndIntervalDialog.asDefault().setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda16
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showEditTimesDialog$31((DeviceFrequencyAndIntervalDialog.FrequencyAndIntervalBean) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditTimesDialog$31(DeviceFrequencyAndIntervalDialog.FrequencyAndIntervalBean frequencyAndIntervalBean) {
        if (frequencyAndIntervalBean.getFrequency() > 10 || frequencyAndIntervalBean.getFrequency() < 1 || frequencyAndIntervalBean.getInterval() > 300 || frequencyAndIntervalBean.getInterval() < 100) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
        } else {
            showLoadingDialog(getString(R.string.saving));
            CmdAssistant.getSettingCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).setDeviceFrequency(this, frequencyAndIntervalBean.getFrequency(), frequencyAndIntervalBean.getInterval(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.9
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    BaseDeviceSetActivity baseDeviceSetActivity;
                    int i;
                    BaseDeviceSetActivity.this.dismissLoadingDialog();
                    if (aBoolean.booleanValue()) {
                        baseDeviceSetActivity = BaseDeviceSetActivity.this;
                        i = R.string.save_success;
                    } else {
                        baseDeviceSetActivity = BaseDeviceSetActivity.this;
                        i = R.string.save_fail;
                    }
                    SmartToast.showShort(baseDeviceSetActivity.getString(i));
                }
            });
        }
    }

    protected void showEditTTLDialog() {
        EditDialog.asDefault().setHint(getString(R.string.app_str_ttl_scope)).setTitle(getString(R.string.app_str_change_device_ttl)).setInputType(2).setInputEmptyTip(getString(R.string.input_number)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda33
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceSetActivity.this.lambda$showEditTTLDialog$32((String) obj);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditTTLDialog$32(String str) {
        if (Integer.parseInt(str) > 32 || Integer.parseInt(str) < 4) {
            SmartToast.showShort(getString(R.string.app_str_out_of_range));
        } else {
            showLoadingDialog(getString(R.string.saving));
            CmdAssistant.getSettingCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).setDeviceTTl(this.activity, Integer.parseInt(str), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.10
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    BaseDeviceSetActivity baseDeviceSetActivity;
                    int i;
                    BaseDeviceSetActivity.this.dismissLoadingDialog();
                    if (aBoolean.booleanValue()) {
                        baseDeviceSetActivity = BaseDeviceSetActivity.this;
                        i = R.string.save_success;
                    } else {
                        baseDeviceSetActivity = BaseDeviceSetActivity.this;
                        i = R.string.save_fail;
                    }
                    SmartToast.showShort(baseDeviceSetActivity.getString(i));
                }
            });
        }
    }

    protected void cleanDeviceCache() {
        showLoadingDialog(getString(R.string.cleaning));
        CmdAssistant.getSettingCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]).cleanDeviceCache(this.activity, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity.11
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                BaseDeviceSetActivity baseDeviceSetActivity;
                int i;
                BaseDeviceSetActivity.this.dismissLoadingDialog();
                if (aBoolean.booleanValue()) {
                    baseDeviceSetActivity = BaseDeviceSetActivity.this;
                    i = R.string.clean_success;
                } else {
                    baseDeviceSetActivity = BaseDeviceSetActivity.this;
                    i = R.string.clean_fail;
                }
                SmartToast.showShort(baseDeviceSetActivity.getString(i));
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void showNoPermissionDialog() {
        MessageDialog.show(this, getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.base.BaseDeviceSetActivity$$ExternalSyntheticLambda23
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return BaseDeviceSetActivity.lambda$showNoPermissionDialog$33(baseDialog, view);
            }
        });
    }

    protected LightAssistant getCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue(), new int[0]);
    }

    protected String getPlaceInfo(Device device) {
        if (device != null) {
            Floor floor = Injection.repo().home().getFloor(device.getFloorId());
            Room room = Injection.repo().home().getRoom(device.getRoomId());
            if (room != null) {
                return floor.getFloorName() + " " + room.getRoomName();
            }
            if (floor != null) {
                return floor.getFloorName();
            }
        }
        return "";
    }

    protected boolean isVirtual() {
        if (((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue() != null) {
            return ((BaseDeviceSetViewModel) this.mViewModel).controlDevice.getValue().isVirtual();
        }
        return false;
    }

    protected void showGeneralData(JSONObject jsonObject, String key) {
        int[] parseBackupData;
        parseBackupData = ReplaceHelper.instance().parseBackupData(jsonObject, key);
        key.hashCode();
        switch (key) {
            case "knobSort":
                ((BaseDeviceSetViewModel) this.mViewModel).orderArray = parseBackupData;
                break;
            case "powerStatus":
                ((BaseDeviceSetViewModel) this.mViewModel).onState.setValue(new PowerState(parseBackupData));
                break;
            case "fadeTime":
                int i = parseBackupData[0];
                this.onSecPos = i / 1000;
                this.onMsPos = (i % 1000) / 100;
                int i2 = parseBackupData[1];
                this.offSecPos = i2 / 1000;
                this.offMsPos = (i2 % 1000) / 100;
                this.mAdapter.notifyDataSetChanged();
                break;
            case "knobDoubleMemory":
                ((BaseDeviceSetViewModel) this.mViewModel).doubleMemorize.setValue(Boolean.valueOf(parseBackupData[0] == 1));
                break;
            case "powerFadeTime":
                int i3 = parseBackupData[0];
                this.powerOnSecPos = i3 / 1000;
                this.powerOnMsPos = (i3 % 1000) / 100;
                this.mAdapter.notifyDataSetChanged();
                break;
            case "sceneFadeTime":
                int i4 = parseBackupData[0];
                this.sceneSecPos = i4 / 1000;
                this.sceneMsPos = (i4 % 1000) / 100;
                this.mAdapter.notifyDataSetChanged();
                break;
            case "knobSensitivity":
                ((BaseDeviceSetViewModel) this.mViewModel).sensitivity.setValue(Integer.valueOf(parseBackupData[0]));
                break;
        }
    }

    public static final class LightSettingState {
        public static int TYPE_DEFAULT = 1;
        public static int TYPE_DIY = 2;
        private String name;
        private String subName;
        private String titleName;
        private int type;

        public LightSettingState(int type, String titleName) {
            this.type = type;
            this.titleName = titleName;
        }

        public LightSettingState(int type, String titleName, String name, String subName) {
            this.type = type;
            this.titleName = titleName;
            this.name = name;
            this.subName = subName;
        }

        public int getType() {
            return this.type;
        }

        public String getName() {
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
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5001) {
            setResult(5001);
            finishActivity();
        }
    }
}