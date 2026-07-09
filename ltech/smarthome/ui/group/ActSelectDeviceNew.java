package com.ltech.smarthome.ui.group;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.LocalSceneParam;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.device_param.EurPanelGroupParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.device_param.WaveSensorExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.BaseLocalParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.net.response.scene.QuerySceneActionResponse;
import com.ltech.smarthome.nfc.WriteVirtualHelper;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.group.ActSelectDeviceNew;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.InOrOutManager;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.util.DialogSettings;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.bean.SmartPanelScreenState;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.smart.product_agreement.bean.WaveSensorState;
import com.smart.product_agreement.param.MicrowaveParam;
import com.smart.product_agreement.param.SettingCmdParam;
import com.smart.product_agreement.param.SmartPanelParam;
import com.smart.product_agreement.parser.IUpgradeParser;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectDeviceNew extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private boolean alreadyJoin;
    private Group editGroup;
    private Map<Integer, List<String>> errorTipMap;
    private Floor lastFloor;
    private long lastItemId;
    private ArrayList<Integer> mSceneNums;
    private TextView okTv;
    private int periodIndex;
    private Map<Long, Integer> saveSceneStep;
    private List<Long> addDeviceIdList = new ArrayList();
    private int progress = 0;
    private List<Long> lastSelectRoleIds = new ArrayList();
    private int lastRoomPosition = -1;
    private int mSyncNum = 0;

    static /* synthetic */ void lambda$addToMesh$21(Boolean bool) {
    }

    static /* synthetic */ void lambda$addToMesh$23(Boolean bool) {
    }

    static /* synthetic */ void lambda$addToMesh$24(Boolean bool) {
    }

    static /* synthetic */ void lambda$saveData$26(Boolean bool) {
    }

    static /* synthetic */ void lambda$saveData$28(Boolean bool) {
    }

    static /* synthetic */ void lambda$synSensorGroupSettingToDevice$39(Boolean bool) {
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showSuccessDialog(String content) {
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((ActSelect3Binding) this.mViewBinding).setTitleGone(true);
        ((ActSelect3Binding) this.mViewBinding).layoutTab.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).ivBack.setImageResource(R.mipmap.icon_back);
        ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        ((FtDeviceGroupVM) this.mViewModel).needFloorRoomMemory = true;
        setSortType(1);
        ((FtDeviceGroupVM) this.mViewModel).floorList = Injection.repo().home().getFloorListByPlaceId(this.placeId);
        this.editGroup = Injection.repo().group().getGroupById(getIntent().getLongExtra(Constants.GROUP_ID, 0L));
        Injection.repo().group().checkCodeLibrary(this, this.editGroup.getGroupId());
        this.addDeviceIdList.addAll(this.editGroup.getDeviceIds());
        ((FtDeviceGroupVM) this.mViewModel).orgFloorId = this.editGroup.getFloorId();
        ((FtDeviceGroupVM) this.mViewModel).orgRoomId = this.editGroup.getRoomId();
        boolean isEmpty = this.editGroup.getDeviceIds().isEmpty();
        this.alreadyJoin = !isEmpty;
        if (!isEmpty) {
            this.listType = 3;
        } else {
            this.listType = 1;
        }
        TabLayout.Tab text = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.app_str_not_join);
        ViewHelpUtil.createTabCustomView(this, text, getString(R.string.app_str_not_join), this.editGroup.getDeviceIds().isEmpty());
        TabLayout.Tab text2 = ((ActSelect3Binding) this.mViewBinding).tabTitle.newTab().setText(R.string.app_str_already_join);
        ViewHelpUtil.createTabCustomView(this, text2, getString(R.string.app_str_already_join), !this.editGroup.getDeviceIds().isEmpty());
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addTab(text2);
        TabLayout tabLayout = ((ActSelect3Binding) this.mViewBinding).tabTitle;
        if (!this.editGroup.getDeviceIds().isEmpty()) {
            text = text2;
        }
        tabLayout.selectTab(text);
        ((ActSelect3Binding) this.mViewBinding).tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.1
            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActSelectDeviceNew.this, tab, tab.getText().toString(), true);
                ActSelectDeviceNew.this.alreadyJoin = tab.getPosition() != 0;
                if (ActSelectDeviceNew.this.alreadyJoin) {
                    ActSelectDeviceNew.this.listType = 3;
                    ActSelectDeviceNew.this.okTv.setText(String.format(ActSelectDeviceNew.this.getResources().getString(R.string.app_str_select_out_device), Integer.valueOf(ActSelectDeviceNew.this.selectRoleIds.size())));
                } else {
                    ActSelectDeviceNew.this.listType = 1;
                    ActSelectDeviceNew.this.okTv.setText(String.format(ActSelectDeviceNew.this.getResources().getString(R.string.app_str_select_join_device), Integer.valueOf(ActSelectDeviceNew.this.selectRoleIds.size())));
                }
                if (ActSelectDeviceNew.this.selectFt != null) {
                    ActSelectDeviceNew.this.selectFt.getData();
                }
                List<Long> list = ActSelectDeviceNew.this.lastSelectRoleIds;
                ActSelectDeviceNew actSelectDeviceNew = ActSelectDeviceNew.this;
                actSelectDeviceNew.lastSelectRoleIds = actSelectDeviceNew.selectRoleIds;
                ActSelectDeviceNew.this.selectRoleIds = list;
                ActSelectDeviceNew.this.selectCountLiveData.setValue(Integer.valueOf(ActSelectDeviceNew.this.selectRoleIds.size()));
                int i = ((FtDeviceGroupVM) ActSelectDeviceNew.this.mViewModel).roomPosition;
                if (ActSelectDeviceNew.this.lastFloor != null) {
                    Floor floor = ActSelectDeviceNew.this.lastFloor;
                    ((FtDeviceGroupVM) ActSelectDeviceNew.this.mViewModel).setFloorPosition(floor, ActSelectDeviceNew.this.mFloorList);
                    ActSelectDeviceNew actSelectDeviceNew2 = ActSelectDeviceNew.this;
                    actSelectDeviceNew2.lastFloor = ((FtDeviceGroupVM) actSelectDeviceNew2.mViewModel).getCurFloor();
                    ((FtDeviceGroupVM) ActSelectDeviceNew.this.mViewModel).selectFloor.setValue(floor);
                } else {
                    ActSelectDeviceNew actSelectDeviceNew3 = ActSelectDeviceNew.this;
                    actSelectDeviceNew3.lastFloor = ((FtDeviceGroupVM) actSelectDeviceNew3.mViewModel).getCurFloor();
                    ((FtDeviceGroupVM) ActSelectDeviceNew.this.mViewModel).selectFloor.setValue(ActSelectDeviceNew.this.mFloorList.get(0));
                }
                ActSelectDeviceNew.this.lastRoomPosition = i;
            }

            @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                ViewHelpUtil.createTabCustomView(ActSelectDeviceNew.this, tab, tab.getText().toString(), false);
            }
        });
        TextView textView = new TextView(this);
        this.okTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.okTv.setTextSize(14.0f);
        this.okTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.okTv.setGravity(17);
        this.okTv.setText(this.alreadyJoin ? String.format(getString(R.string.app_str_select_out_device), 0) : String.format(getResources().getString(R.string.app_str_select_join_device), 0));
        ((ActSelect3Binding) this.mViewBinding).footerView.addView(this.okTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        this.okTv.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ActSelectDeviceNew.this.selectRoleIds.size() == 0) {
                    SmartToast.showShort(R.string.app_str_select_no_device_hint);
                    return;
                }
                ArrayList arrayList = new ArrayList();
                InOrOutManager.getInstance().clearAllList();
                for (int i = 0; i < ActSelectDeviceNew.this.selectRoleIds.size(); i++) {
                    arrayList.add(ActSelectDeviceNew.this.inOrOutGroup(InOrOutManager.getInstance().addTask(Injection.repo().device().getDeviceByDeviceId(ActSelectDeviceNew.this.selectRoleIds.get(i).longValue()), i, ActSelectDeviceNew.this.alreadyJoin)));
                }
                if (arrayList.size() > 0) {
                    if (Injection.state().isConnectOuterNet()) {
                        if (ActSelectDeviceNew.this.alreadyJoin) {
                            ActSelectDeviceNew.this.batchControl(arrayList);
                            return;
                        }
                        if (InOrOutManager.getInstance().isSmartPanelDevice()) {
                            ActSelectDeviceNew actSelectDeviceNew = ActSelectDeviceNew.this;
                            actSelectDeviceNew.showConfirmDialog(arrayList, actSelectDeviceNew.getString(R.string.app_str_add_smart_panel_group_tip));
                            return;
                        } else if (InOrOutManager.getInstance().isWaveSensor()) {
                            ActSelectDeviceNew actSelectDeviceNew2 = ActSelectDeviceNew.this;
                            actSelectDeviceNew2.showConfirmDialog(arrayList, actSelectDeviceNew2.getString(R.string.app_str_add_wave_sensor_group_tip));
                            return;
                        } else if (InOrOutManager.getInstance().isEurPanel() || InOrOutManager.getInstance().isAsPanel()) {
                            ActSelectDeviceNew actSelectDeviceNew3 = ActSelectDeviceNew.this;
                            actSelectDeviceNew3.showConfirmDialog(arrayList, actSelectDeviceNew3.getString(R.string.app_str_add_eur_panel_group_tip));
                            return;
                        } else {
                            ActSelectDeviceNew.this.batchControl(arrayList);
                            return;
                        }
                    }
                    SmartToast.showShort(R.string.no_network);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean canSelected(Role role) {
        if (this.editGroup.getDeviceIds().isEmpty()) {
            Device deviceByDeviceId = !this.selectRoleIds.isEmpty() ? Injection.repo().device().getDeviceByDeviceId(this.selectRoleIds.get(0).longValue()) : null;
            if (deviceByDeviceId == null || RelaySeparationHelper.isRelaySeparationDevice(deviceByDeviceId) == RelaySeparationHelper.isRelaySeparationDevice(role)) {
                return true;
            }
        } else if ((RelaySeparationHelper.isRelaySeparationDevice(this.editGroup) && RelaySeparationHelper.isRelaySeparationDevice(role)) || (!RelaySeparationHelper.isRelaySeparationDevice(this.editGroup) && !RelaySeparationHelper.isRelaySeparationDevice(role))) {
            return true;
        }
        MessageDialog.show(this.activity, getString(R.string.tips), getString(R.string.app_str_app_new_and_old_can_in_the_same_group)).setOkButton(getString(R.string.confirm));
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void selectAll() {
        if (this.allRoleData.isEmpty()) {
            return;
        }
        if (!this.selectAll) {
            boolean z = true;
            for (Role role : this.allRoleData) {
                if (!this.selectRoleIds.contains(Long.valueOf(role.getObjectId()))) {
                    if (this.selectRoleIds.isEmpty() || RelaySeparationHelper.isRelaySeparationDevice(Injection.repo().device().getDeviceByDeviceId(this.selectRoleIds.get(0).longValue())) == RelaySeparationHelper.isRelaySeparationDevice(role)) {
                        this.selectRoleIds.add(Long.valueOf(role.getObjectId()));
                    } else {
                        z = false;
                    }
                }
            }
            if (!z) {
                MessageDialog.show(this.activity, getString(R.string.tips), getString(R.string.app_str_app_new_and_old_can_in_the_same_group)).setOkButton(getString(R.string.confirm));
            }
        } else {
            Iterator<Role> it = this.allRoleData.iterator();
            while (it.hasNext()) {
                this.selectRoleIds.remove(Long.valueOf(it.next().getObjectId()));
            }
        }
        this.selectFt.refreshList.call();
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectRoleIds.size()));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void clickBack() {
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.CREATE, false);
        if (this.editGroup.getDeviceIds().size() == 0 && booleanExtra) {
            showEmptyGroupTip();
        } else {
            finishActivity();
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
        return i == -1 ? super.getRoomIndex(roomList) : i;
    }

    private void showEmptyGroupTip() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.tips), ActivityUtils.getTopActivity().getString(R.string.app_str_empty_group_dissolve_tip)).setCancelable(false).setOkButton(ActivityUtils.getTopActivity().getString(R.string.app_str_dissolve), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda8
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showEmptyGroupTip$0;
                lambda$showEmptyGroupTip$0 = ActSelectDeviceNew.this.lambda$showEmptyGroupTip$0(baseDialog, view);
                return lambda$showEmptyGroupTip$0;
            }
        }).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda9
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showEmptyGroupTip$1;
                lambda$showEmptyGroupTip$1 = ActSelectDeviceNew.this.lambda$showEmptyGroupTip$1(baseDialog, view);
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

    private void delGroupFromNet(final Group group) {
        Observable<Object> deleteGroup = Injection.net().deleteGroup(group.getGroupId());
        if ((AsHelper.isNewUb(group) || EurHelper.isEb125(group)) && ((EurPanelGroupParam) group.getParam(EurPanelGroupParam.class)).getPublicationId() > 0) {
            long publicationId = ((EurPanelGroupParam) group.getParam(EurPanelGroupParam.class)).getPublicationId();
            if (publicationId > 0) {
                deleteGroup = Injection.net().deleteDevice(group.getGroupId(), publicationId);
            }
        }
        ((ObservableSubscribeProxy) deleteGroup.delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda38
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$delGroupFromNet$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda39
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectDeviceNew.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda40
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$delGroupFromNet$3(group, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$2(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$3(Group group, Object obj) throws Exception {
        Injection.repo().group().removeGroupFromDb(group.getId());
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Observable<Integer> inOrOutGroup(final FtDeviceGroupVM.InOrOutGroupItem item) {
        return Observable.create(new ObservableOnSubscribe<Integer>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.3
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                item.setEmitter(emitter);
                emitter.onNext(Integer.valueOf(item.getId()));
                if (ActSelectDeviceNew.this.alreadyJoin) {
                    ActSelectDeviceNew.this.leftGroup(item);
                } else {
                    ActSelectDeviceNew.this.addToGroup(item);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void batchControl(List<Observable<Integer>> request) {
        setItemMap();
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.4
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Integer i) {
                FtDeviceGroupVM.InOrOutGroupItem itemById = InOrOutManager.getInstance().getItemById(i.intValue());
                if (itemById != null) {
                    ActSelectDeviceNew.this.progress++;
                    int size = InOrOutManager.getInstance().getTaskList().size();
                    if (ActSelectDeviceNew.this.progress == size) {
                        ActSelectDeviceNew.this.lastItemId = itemById.getDevice().getId();
                    }
                    ActSelectDeviceNew actSelectDeviceNew = ActSelectDeviceNew.this;
                    actSelectDeviceNew.showLoadingDialog(String.format(actSelectDeviceNew.getString(R.string.app_str_process_data), String.valueOf(ActSelectDeviceNew.this.progress), String.valueOf(size)));
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ((FtDeviceGroupVM) ActSelectDeviceNew.this.mViewModel).updateState(ActSelectDeviceNew.this.lastItemId, FtDeviceGroupVM.StateParam.STATE_ALL_COMPLETED, 1.0f);
                ActSelectDeviceNew.this.refreshData();
                ActSelectDeviceNew.this.dismissLoadingDialog();
                if (InOrOutManager.getInstance().getErrorList().size() > 0) {
                    ActSelectDeviceNew actSelectDeviceNew = ActSelectDeviceNew.this;
                    actSelectDeviceNew.showErrorMessageDialog(actSelectDeviceNew.alreadyJoin);
                } else if (ActSelectDeviceNew.this.alreadyJoin) {
                    SmartToast.showCenterShort(ActSelectDeviceNew.this.getString(R.string.left_group_success));
                } else {
                    SmartToast.showCenterShort(ActSelectDeviceNew.this.getString(R.string.group_success));
                }
                ActSelectDeviceNew.this.progress = 0;
                InOrOutManager.getInstance().clearAllList();
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ObservableSubscribeProxy) Injection.net().getGroupLocalSceneAction(this.editGroup.getGroupId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$startObserve$4((QuerySceneActionResponse) obj);
            }
        });
        ((FtDeviceGroupVM) this.mViewModel).updateStateLiveData.observe(this, new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda16
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSelectDeviceNew.this.setStateParam((FtDeviceGroupVM.StateParam) obj);
            }
        });
        if (getIntent().getBooleanExtra(Constants.CREATE, false)) {
            ((FtDeviceGroupVM) this.mViewModel).loadNewGroupList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(QuerySceneActionResponse querySceneActionResponse) throws Exception {
        this.syncTotalNum = querySceneActionResponse.getTotal();
        if (this.selectFt != null) {
            this.selectFt.getData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showErrorMessageDialog(boolean alreadyJoin) {
        String format;
        ArrayList<FtDeviceGroupVM.InOrOutGroupItem> errorList = InOrOutManager.getInstance().getErrorList();
        if (alreadyJoin) {
            format = String.format(getString(R.string.left_group_fail), String.valueOf(errorList.size()));
        } else {
            format = String.format(getString(R.string.add_to_group_fail), String.valueOf(errorList.size()));
        }
        String string = getString(R.string.add_to_group_fail_tip);
        if (InOrOutManager.getInstance().isEurPanelError()) {
            string = getString(R.string.add_to_eur_group_fail_tip);
        } else if (InOrOutManager.getInstance().isAsPanelError()) {
            string = getString(R.string.app_str_device_not_support);
        }
        MessageDialog.build((AppCompatActivity) ActivityUtils.getTopActivity()).setTitle(format).setMessage(string).setStyle(DialogSettings.STYLE.STYLE_IOS).setOkButton(getString(R.string.ok)).setCustomView(getCustomView(errorList)).setCancelable(false).show();
    }

    private View getCustomView(ArrayList<FtDeviceGroupVM.InOrOutGroupItem> errorList) {
        ArrayList arrayList = new ArrayList();
        Iterator<FtDeviceGroupVM.InOrOutGroupItem> it = errorList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getDevice().getDeviceName());
        }
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_custom_list, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_scene_error_tip, arrayList) { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.5
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
            }
        });
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showConfirmDialog(final List<Observable<Integer>> needDel, String tipContent) {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.tips), tipContent).setCancelable(false).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda52
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showConfirmDialog$5;
                lambda$showConfirmDialog$5 = ActSelectDeviceNew.this.lambda$showConfirmDialog$5(needDel, baseDialog, view);
                return lambda$showConfirmDialog$5;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showConfirmDialog$5(List list, BaseDialog baseDialog, View view) {
        batchControl(list);
        return false;
    }

    public void addToGroup(final FtDeviceGroupVM.InOrOutGroupItem item) {
        RelateInfoUtils.initRelateInfoList(this.editGroup);
        final Device device = item.getDevice();
        if (isSmartPanelDevice(device)) {
            if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
                addToGroupNeedVersion(device, item);
                return;
            } else {
                Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda2
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSelectDeviceNew.this.lambda$addToGroup$7(device, item, (Boolean) obj);
                    }
                });
                return;
            }
        }
        if (isWaveSensorDevice(device)) {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$addToGroup$8(item, device, (Boolean) obj);
                }
            });
            return;
        }
        if (ProductRepository.isEurPanel(device.getProductId())) {
            if (EurHelper.isBindByRcb(device)) {
                setTaskError(item);
                return;
            } else {
                EurHelper.inGroup(this.editGroup, device, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSelectDeviceNew.this.lambda$addToGroup$9(item, device, (Boolean) obj);
                    }
                });
                return;
            }
        }
        if (ProductRepository.isAsPanel(device.getProductId())) {
            AsHelper.inGroup(this.editGroup, device, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$addToGroup$10(item, device, (Boolean) obj);
                }
            });
        } else if (device.isVirtual()) {
            getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ActSelectDeviceNew.this.lambda$addToGroup$11(item, device);
                }
            });
        } else {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$addToGroup$12(item, device, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToGroup$7(final Device device, final FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Boolean bool) {
        if (bool.booleanValue()) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setGroupRelateInfo(this, ProductRepository.getZoneCount(device.getProductId()), generateRelateInfoList(this.editGroup), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda53
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$addToGroup$6(inOrOutGroupItem, device, (Boolean) obj);
                }
            });
        } else {
            outGroup(inOrOutGroupItem);
            setTaskError(inOrOutGroupItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToGroup$6(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        if (bool.booleanValue()) {
            ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
            this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
            uploadDeviceInGroupData(inOrOutGroupItem);
            return;
        }
        outGroup(inOrOutGroupItem);
        setTaskError(inOrOutGroupItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToGroup$8(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        this.periodIndex = 2;
        setSensorGroupRelateParam(inOrOutGroupItem, device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToGroup$9(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        if (bool.booleanValue()) {
            ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
            this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
            uploadDeviceInGroupData(inOrOutGroupItem);
            return;
        }
        outGroup(inOrOutGroupItem);
        setTaskError(inOrOutGroupItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToGroup$10(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        if (bool.booleanValue()) {
            ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
            this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
            uploadDeviceInGroupData(inOrOutGroupItem);
            return;
        }
        outGroup(inOrOutGroupItem);
        setTaskError(inOrOutGroupItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToGroup$11(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device) {
        ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
        this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
        this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
        saveData(inOrOutGroupItem, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToGroup$12(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        if (bool.booleanValue()) {
            ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
            this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
            saveData(inOrOutGroupItem, 1);
            return;
        }
        setTaskError(inOrOutGroupItem);
    }

    private void addToGroupNeedVersion(Device device, FtDeviceGroupVM.InOrOutGroupItem item) {
        if (!StringUtils.isEmpty(device.getMcuversion()) && !device.getMcuversion().equals("--")) {
            fixVersionAndAdd(device, item, device.getMcuversion().toUpperCase());
        } else {
            queryVersion(device, item);
        }
    }

    private void fixVersionAndAdd(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item, String version) {
        if ("SVER000.001.000".compareTo(version) <= 0) {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$fixVersionAndAdd$14(device, item, (Boolean) obj);
                }
            });
        } else {
            outGroup(item);
            setTaskError(item);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fixVersionAndAdd$14(final Device device, final FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Boolean bool) {
        if (bool.booleanValue()) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setGroupRelateInfo(this, ProductRepository.getZoneCount(device.getProductId()), generateRelateInfoList(this.editGroup), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda25
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$fixVersionAndAdd$13(inOrOutGroupItem, device, (Boolean) obj);
                }
            });
        } else {
            outGroup(inOrOutGroupItem);
            setTaskError(inOrOutGroupItem);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fixVersionAndAdd$13(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        if (bool.booleanValue()) {
            ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
            this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
            this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
            uploadDeviceInGroupData(inOrOutGroupItem);
            return;
        }
        outGroup(inOrOutGroupItem);
        setTaskError(inOrOutGroupItem);
    }

    private void queryVersion(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        LHomeLog.i(getClass(), "send getQueryCmdAssistant:" + device.getDeviceName());
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.this.lambda$queryVersion$15(device, item, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryVersion$15(Device device, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, ResponseMsg responseMsg) {
        ProductVersionInfo parserUpgradeInfo;
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg);
        if (responseMsg == null) {
            return;
        }
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg.getAgreementId());
        if (responseMsg.getResData().length() < 16) {
            LHomeLog.i(getClass(), "msg.getResData().length()<16 resData-->" + responseMsg.getResData());
            parserUpgradeInfo = null;
        } else {
            LHomeLog.i(getClass(), "msg.getResData() current, resData-->" + responseMsg.getResData());
            parserUpgradeInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(com.smart.message.utils.StringUtils.hexStringToByte(responseMsg.getResData().substring(16))));
        }
        if (parserUpgradeInfo == null) {
            return;
        }
        fixVersionAndAdd(device, inOrOutGroupItem, parserUpgradeInfo.getsVer().toUpperCase());
        changeDeviceVersion(device, parserUpgradeInfo.getsVer(), parserUpgradeInfo.gethVer());
    }

    private void changeDeviceVersion(final Device device, final String sVersion, final String hVersion) {
        if (device != null) {
            if (TextUtils.isEmpty(device.getMcuversion()) || !device.getMcuversion().equals(sVersion) || TextUtils.isEmpty(device.getFirmwareversion()) || !device.getFirmwareversion().equals(hVersion)) {
                ((ObservableSubscribeProxy) Injection.net().updateDeviceVersion(device.getDeviceId(), sVersion, hVersion).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda34
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActSelectDeviceNew.lambda$changeDeviceVersion$16(Device.this, sVersion, hVersion, obj);
                    }
                }, new SmartErrorComsumer());
            }
        }
    }

    static /* synthetic */ void lambda$changeDeviceVersion$16(Device device, String str, String str2, Object obj) throws Exception {
        device.setMcuversion(str);
        device.setFirmwareversion(str2);
        Injection.repo().device().saveDevice(device);
    }

    private void setSensorGroupRelateParam(final FtDeviceGroupVM.InOrOutGroupItem item, final Device device) {
        CmdAssistant.getDeviceAssistant(device, 1 << this.periodIndex).setGroupRelateParamWithoutDialog(this, ProductId.ID_SENSOR_MS03.equals(device.getProductId()), generateSensorRelateInfo(this.periodIndex, this.editGroup), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.this.lambda$setSensorGroupRelateParam$17(item, device, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensorGroupRelateParam$17(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        if (bool.booleanValue()) {
            int i = this.periodIndex;
            if (i == 0) {
                ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
                this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
                this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
                uploadDeviceInGroupData(inOrOutGroupItem);
                return;
            }
            if (i == 1) {
                this.periodIndex = 0;
                setSensorGroupRelateParam(inOrOutGroupItem, device);
                return;
            } else {
                if (i == 2) {
                    this.periodIndex = 1;
                    setSensorGroupRelateParam(inOrOutGroupItem, device);
                    return;
                }
                return;
            }
        }
        setTaskError(inOrOutGroupItem);
    }

    public void leftGroup(final FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem) {
        final Device device = inOrOutGroupItem.getDevice();
        if (isSmartPanelDevice(device) || isWaveSensorDevice(device)) {
            removeSmartPanel(inOrOutGroupItem);
        } else if (device.isVirtual()) {
            getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    ActSelectDeviceNew.this.lambda$leftGroup$18(inOrOutGroupItem, device);
                }
            });
        } else {
            Injection.mesh().outGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda31
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$leftGroup$19(inOrOutGroupItem, device, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$leftGroup$18(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device) {
        ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
        this.addDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
        this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
        saveData(inOrOutGroupItem, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$leftGroup$19(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        if (bool.booleanValue()) {
            ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
            this.addDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
            this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
            saveData(inOrOutGroupItem, 2);
            return;
        }
        addToMesh(device);
        setTaskError(inOrOutGroupItem);
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        this.editGroup.setDeviceIds(this.addDeviceIdList);
        Injection.repo().group().saveGroup(this.editGroup);
        if (this.alreadyJoin) {
            this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_out_device), Integer.valueOf(this.selectRoleIds.size())));
        } else {
            this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_join_device), Integer.valueOf(this.selectRoleIds.size())));
        }
        if (this.selectFt != null) {
            this.selectFt.getData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTaskError(FtDeviceGroupVM.InOrOutGroupItem item) {
        ((FtDeviceGroupVM) this.mViewModel).updateState(item.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_COMPLETED, 1.0f);
        item.getEmitter().onComplete();
        InOrOutManager.getInstance().addErrorList(item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTaskSuccess(FtDeviceGroupVM.InOrOutGroupItem item) {
        ((FtDeviceGroupVM) this.mViewModel).updateState(item.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_COMPLETED, 1.0f);
        item.getEmitter().onComplete();
    }

    private void removeSmartPanel(final FtDeviceGroupVM.InOrOutGroupItem item) {
        final Device device = item.getDevice();
        Injection.mesh().outGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda32
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.this.lambda$removeSmartPanel$20(item, device, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeSmartPanel$20(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Boolean bool) {
        ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.4f);
        if (bool.booleanValue()) {
            this.addDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
            this.selectRoleIds.remove(Long.valueOf(device.getObjectId()));
            saveData(inOrOutGroupItem, 2);
        } else {
            addToMesh(device);
            setTaskError(inOrOutGroupItem);
        }
    }

    private void addToMesh(final Device device) {
        if (isSmartPanelDevice(device)) {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$addToMesh$22(device, (Boolean) obj);
                }
            });
        } else if (ProductRepository.isAsPanel(device.getProductId()) || ProductRepository.isEurPanel(device.getProductId())) {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), EurHelper.getPublishAddress(this.editGroup), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda12
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.lambda$addToMesh$23((Boolean) obj);
                }
            });
        } else {
            Injection.mesh().inGroupByCmd(device, this.editGroup.getGroupAddress(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda13
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.lambda$addToMesh$24((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addToMesh$22(Device device, Boolean bool) {
        if (bool.booleanValue()) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setGroupRelateInfo(this, ProductRepository.getZoneCount(device.getProductId()), generateRelateInfoList(this.editGroup), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda51
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.lambda$addToMesh$21((Boolean) obj);
                }
            });
        }
    }

    private List<SettingCmdParam.RelateInfo> generateRelateInfoList(Group group) {
        ArrayList arrayList = new ArrayList();
        RelateInfoAssistant relateInfoAssistant = new RelateInfoAssistant(group);
        List<Boolean> onOffStates = group.getGroupState().getOnOffStates();
        for (int i = 0; i < ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group)); i++) {
            SettingCmdParam.RelateInfo relateInfo = new SettingCmdParam.RelateInfo();
            RelatedInfoExtParam.RelateInfo relateInfo2 = relateInfoAssistant.getRelateInfo(i);
            int relateAddress = RelateInfoUtils.getRelateAddress(relateInfo2);
            int relateZone = RelateInfoUtils.getRelateZone(relateInfo2);
            int i2 = relateInfo2 != null ? relateInfo2.type : 0;
            int i3 = relateInfo2 != null ? relateInfo2.action : 0;
            int relateDeviceType = RelateInfoUtils.getRelateDeviceType(relateInfo2);
            int delayTime = RelateInfoUtils.getDelayTime(relateInfo2);
            int i4 = (onOffStates == null || onOffStates.isEmpty() || onOffStates.size() < ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group)) || group.getDeviceIds().isEmpty() || !onOffStates.get(i).booleanValue()) ? 0 : 1;
            relateInfo.setRelateAddress(relateAddress);
            relateInfo.setRelateZone(relateZone);
            relateInfo.setRelateType(i2);
            relateInfo.setRelateExtData(i3);
            relateInfo.setRelateLightType(relateDeviceType);
            relateInfo.setZoneOnOffState(i4);
            if (RelaySeparationHelper.isRelaySeparationDevice(group)) {
                relateInfo.setDelayTime(delayTime);
            }
            arrayList.add(relateInfo);
        }
        return arrayList;
    }

    private void uploadDeviceInGroupData(final FtDeviceGroupVM.InOrOutGroupItem item) {
        final Device device = item.getDevice();
        if (device.getParam(BleParam.class) != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            bleParam.setGroupId(this.editGroup.getGroupId());
            device.setParam(bleParam);
        }
        if (ProductRepository.isEurPanel(device.getProductId()) || ProductRepository.isAsPanel(device.getProductId())) {
            RelatedInfoExtParam relatedInfoExtParam = new RelatedInfoExtParam();
            relatedInfoExtParam.fillMapWithString(device.getExtParam());
            relatedInfoExtParam.getRelateParamMap().clear();
            device.setExtParam(relatedInfoExtParam.getRelateParamMapString());
        } else {
            device.setExtParam((String) null);
        }
        ((ObservableSubscribeProxy) Injection.net().updateParams(device.getDeviceId(), device.getParam(), device.getExtParam()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda23
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$uploadDeviceInGroupData$25(item, device, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActSelectDeviceNew.this.outGroup(item);
                ActSelectDeviceNew.this.setTaskError(item);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceInGroupData$25(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, Object obj) throws Exception {
        ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.5f);
        SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_CHANGED, true);
        Injection.repo().device().saveDevice(device);
        RelaySeparationHelper.resetSubName(this.activity, device);
        saveData(inOrOutGroupItem, 1);
    }

    private void saveData(final FtDeviceGroupVM.InOrOutGroupItem item, final int type) {
        final Device device = item.getDevice();
        ((ObservableSubscribeProxy) Injection.net().updateGroupDevices(this.editGroup.getGroupId(), this.addDeviceIdList).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$saveData$31(item, device, type, (UpdateGroupResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$saveData$32(device, type, item, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$31(final FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Device device, int i, UpdateGroupResponse updateGroupResponse) throws Exception {
        ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.6f);
        boolean z = false;
        int i2 = 0;
        r0 = false;
        boolean z2 = false;
        z = false;
        if (device != null && i == 2) {
            if (this.addDeviceIdList.isEmpty()) {
                for (int i3 = 0; i3 < this.editGroup.getGroupState().getOnOffStates().size(); i3++) {
                    this.editGroup.getGroupState().getOnOffStates().set(i3, false);
                }
                Injection.repo().group().saveGroup(this.editGroup);
                RelaySeparationHelper.setSubGroupOnOff(this.editGroup, false);
            }
            if (this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_DIM_LIGHT) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_CT_LIGHT) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_RGB_LIGHT) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_RGBW_LIGHT) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_RGBWY_LIGHT)) {
                clearBindPublishAddress(inOrOutGroupItem);
            } else {
                uploadDeviceOutGroupData(inOrOutGroupItem);
            }
        }
        if (device != null && device.isVirtual()) {
            WriteVirtualHelper.instance().updateDeviceWritable(Collections.singletonList(Long.valueOf(device.getDeviceId())));
        }
        if (this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S1C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S2C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S3C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S4) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S4M) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S6PRO) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_G4)) {
            if (device != null && i == 2) {
                z = true;
            }
            synGroupSettingToDevice(device, z, inOrOutGroupItem);
            return;
        }
        if (this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR03) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR04)) {
            if (device != null && i == 2) {
                z2 = true;
            }
            synSensorGroupSettingToDevice(device, z2);
            i2 = 1000;
        } else if (this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB1) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB2) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB5) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB1) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB2) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB4) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB5)) {
            if (i == 1) {
                if (AsHelper.isNewUb(device)) {
                    AsHelper.clearPublishAddress(device, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda44
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            AsHelper.syncGroupSettings(new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda48
                                @Override // com.ltech.smarthome.base.IAction
                                public final void act(Object obj2) {
                                    ActSelectDeviceNew.lambda$saveData$26((Boolean) obj2);
                                }
                            });
                        }
                    });
                } else {
                    EurHelper.clearPublishAddress(device, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda49
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            EurHelper.syncGroupSettings(new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda37
                                @Override // com.ltech.smarthome.base.IAction
                                public final void act(Object obj2) {
                                    ActSelectDeviceNew.lambda$saveData$28((Boolean) obj2);
                                }
                            });
                        }
                    });
                }
            } else {
                if (AsHelper.isNewUb(device)) {
                    AsHelper.relateInfoAssistant = new RelateInfoAssistant(device);
                    AsHelper.setType(device, null);
                } else {
                    EurHelper.relateInfoAssistant = new RelateInfoAssistant(device);
                    EurHelper.setType(device, null);
                }
                ReplaceHelper.instance().backupEurPanelDefault(this, device);
            }
            i2 = 1500;
        }
        if (i != 2) {
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda50
                @Override // java.lang.Runnable
                public final void run() {
                    ActSelectDeviceNew.this.lambda$saveData$30(inOrOutGroupItem);
                }
            }, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$32(Device device, int i, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Throwable th) throws Exception {
        if (device != null && i == 1) {
            outGroup(inOrOutGroupItem);
            setTaskError(inOrOutGroupItem);
        } else {
            if (device == null || i != 2) {
                return;
            }
            addToGroup(inOrOutGroupItem);
        }
    }

    public void clearBindPublishAddress(FtDeviceGroupVM.InOrOutGroupItem item) {
        ArrayList arrayList = new ArrayList();
        Iterator<Device> it = Injection.repo().device().getDeviceListByPlaceId(this.editGroup.getPlaceId()).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Device next = it.next();
            if (ProductRepository.needPublishAddress(next)) {
                RelateInfoAssistant relateInfoAssistant = new RelateInfoAssistant(next);
                for (int i = 0; i < relateInfoAssistant.getZoneNumber(); i++) {
                    RelatedInfoExtParam.RelateInfo relateInfo = relateInfoAssistant.getRelateInfo(i);
                    if (relateInfo != null && relateInfo.objectId == this.editGroup.getGroupId()) {
                        arrayList.add(clearBindPublishAddress(item.getDevice(), EurHelper.getPublishAddress(next), i));
                    }
                }
            }
        }
        for (Group group : Injection.repo().group().getGroupListByPlaceId(this.editGroup.getPlaceId())) {
            if (ProductRepository.needPublishAddress(group)) {
                RelateInfoAssistant relateInfoAssistant2 = new RelateInfoAssistant(group);
                for (int i2 = 0; i2 < relateInfoAssistant2.getZoneNumber(); i2++) {
                    RelatedInfoExtParam.RelateInfo relateInfo2 = relateInfoAssistant2.getRelateInfo(i2);
                    if (relateInfo2 != null && relateInfo2.objectId == this.editGroup.getGroupId()) {
                        arrayList.add(clearBindPublishAddress(item.getDevice(), EurHelper.getPublishAddress(group), i2));
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            uploadDeviceOutGroupData(item);
        } else {
            batchControl(arrayList, item, true);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.group.ActSelectDeviceNew$7, reason: invalid class name */
    class AnonymousClass7 implements ObservableOnSubscribe<ErrorTip> {
        final /* synthetic */ Device val$device;
        final /* synthetic */ int val$position;
        final /* synthetic */ int val$subscribeAddress;

        AnonymousClass7(final ActSelectDeviceNew this$0, final Device val$device, final int val$subscribeAddress, final int val$position) {
            this.val$device = val$device;
            this.val$subscribeAddress = val$subscribeAddress;
            this.val$position = val$position;
        }

        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(final ObservableEmitter<ErrorTip> emitter) throws Exception {
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribe(ActivityUtils.getTopActivity(), ((BleParam) this.val$device.getParam(BleParam.class)).getUnicastAddress(), this.val$subscribeAddress, 2, 1 << this.val$position, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$7$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.AnonymousClass7.lambda$subscribe$0(ObservableEmitter.this, (Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$subscribe$0(ObservableEmitter observableEmitter, Boolean bool) {
            if (bool.booleanValue()) {
                observableEmitter.onNext(new ErrorTip(0));
            } else {
                observableEmitter.onNext(new ErrorTip(-1));
            }
            observableEmitter.onComplete();
        }
    }

    private Observable<ErrorTip> clearBindPublishAddress(Device device, int subscribeAddress, int position) {
        return Observable.create(new AnonymousClass7(this, device, subscribeAddress, position));
    }

    /* renamed from: syncLocalScene, reason: merged with bridge method [inline-methods] */
    public void lambda$saveData$30(final FtDeviceGroupVM.InOrOutGroupItem item) {
        ((ObservableSubscribeProxy) Injection.net().getGroupLocalSceneAction(this.editGroup.getGroupId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda41
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLocalScene$33(item, (QuerySceneActionResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda42
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLocalScene$34(item, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalScene$33(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, QuerySceneActionResponse querySceneActionResponse) throws Exception {
        ((FtDeviceGroupVM) this.mViewModel).updateState(inOrOutGroupItem.getDevice().getId(), FtDeviceGroupVM.StateParam.STATE_WORKING, 0.8f);
        syncLocalDataToDevice(inOrOutGroupItem, querySceneActionResponse.getRows());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalScene$34(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Throwable th) throws Exception {
        setTaskSuccess(inOrOutGroupItem);
    }

    public List<QuerySceneActionResponse.RowsBean> getSyncSceneAction(List<QuerySceneActionResponse.RowsBean> groupRows, List<QuerySceneActionResponse.RowsBean> deviceRows) {
        ArrayList arrayList = new ArrayList();
        for (QuerySceneActionResponse.RowsBean rowsBean : groupRows) {
            arrayList.add(rowsBean);
            long sceneid = rowsBean.getSceneid();
            for (QuerySceneActionResponse.RowsBean rowsBean2 : deviceRows) {
                if (!arrayList.contains(rowsBean2) && rowsBean2.getSceneid() == sceneid) {
                    arrayList.add(rowsBean2);
                }
            }
        }
        Collections.sort(arrayList, new Comparator() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda22
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ActSelectDeviceNew.lambda$getSyncSceneAction$35((QuerySceneActionResponse.RowsBean) obj, (QuerySceneActionResponse.RowsBean) obj2);
            }
        });
        return arrayList;
    }

    static /* synthetic */ int lambda$getSyncSceneAction$35(QuerySceneActionResponse.RowsBean rowsBean, QuerySceneActionResponse.RowsBean rowsBean2) {
        return rowsBean.getExecutecommandObject().getTotalDelay() - rowsBean2.getExecutecommandObject().getTotalDelay();
    }

    private void syncLocalDataToDevice(final FtDeviceGroupVM.InOrOutGroupItem item, final List<QuerySceneActionResponse.RowsBean> rows) {
        ((ObservableSubscribeProxy) Injection.net().getDeviceLocalSceneAction(item.getDevice().getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda28
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLocalDataToDevice$36(rows, item, (QuerySceneActionResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda29
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLocalDataToDevice$37(item, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalDataToDevice$36(List list, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, QuerySceneActionResponse querySceneActionResponse) throws Exception {
        List<QuerySceneActionResponse.RowsBean> syncSceneAction = getSyncSceneAction(list, querySceneActionResponse.getRows());
        this.mSceneNums = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        this.saveSceneStep = new HashMap();
        if (!inOrOutGroupItem.getDevice().isVirtual()) {
            for (QuerySceneActionResponse.RowsBean rowsBean : syncSceneAction) {
                BaseLocalParam executecommandObject = rowsBean.getExecutecommandObject();
                int intValue = (this.saveSceneStep.containsKey(Long.valueOf(rowsBean.getSceneid())) ? this.saveSceneStep.get(Long.valueOf(rowsBean.getSceneid())).intValue() : 0) + 1;
                this.saveSceneStep.put(Long.valueOf(rowsBean.getSceneid()), Integer.valueOf(intValue));
                arrayList.add(saveTempData(inOrOutGroupItem.getDevice(), rowsBean.getScenenum(), rowsBean.getScenename(), new LocalSceneParam(executecommandObject.getTotalDelay(), intValue, executecommandObject.instruct, executecommandObject.sceneZone, executecommandObject.sceneAddr)));
                this.mSceneNums.add(Integer.valueOf(rowsBean.getScenenum()));
            }
        }
        batchControl(arrayList, inOrOutGroupItem, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalDataToDevice$37(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Throwable th) throws Exception {
        setTaskSuccess(inOrOutGroupItem);
    }

    private void syncLocalDataToDevice(final Device device, final List<QuerySceneActionResponse.RowsBean> rows) {
        ((ObservableSubscribeProxy) Injection.net().getDeviceLocalSceneAction(device.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda27
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLocalDataToDevice$38(rows, device, (QuerySceneActionResponse) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalDataToDevice$38(List list, Device device, QuerySceneActionResponse querySceneActionResponse) throws Exception {
        List<QuerySceneActionResponse.RowsBean> syncSceneAction = getSyncSceneAction(list, querySceneActionResponse.getRows());
        this.mSceneNums = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        this.saveSceneStep = new HashMap();
        for (QuerySceneActionResponse.RowsBean rowsBean : syncSceneAction) {
            BaseLocalParam executecommandObject = rowsBean.getExecutecommandObject();
            int intValue = (this.saveSceneStep.containsKey(Long.valueOf(rowsBean.getSceneid())) ? this.saveSceneStep.get(Long.valueOf(rowsBean.getSceneid())).intValue() : 0) + 1;
            this.saveSceneStep.put(Long.valueOf(rowsBean.getSceneid()), Integer.valueOf(intValue));
            arrayList.add(saveTempDataWithTip(device, rowsBean.getScenenum(), rowsBean.getScenename(), new LocalSceneParam(executecommandObject.getTotalDelay(), intValue, executecommandObject.instruct, executecommandObject.sceneZone, executecommandObject.sceneAddr)));
            this.mSceneNums.add(Integer.valueOf(rowsBean.getScenenum()));
        }
        batchControlWithDialog(arrayList);
    }

    private void batchControl(List<Observable<ErrorTip>> request, final FtDeviceGroupVM.InOrOutGroupItem item, final boolean clearPublishAddress) {
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<ErrorTip>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.8
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(ErrorTip errorTip) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (clearPublishAddress) {
                    ActSelectDeviceNew.this.uploadDeviceOutGroupData(item);
                } else {
                    ActSelectDeviceNew.this.setTaskSuccess(item);
                }
            }
        });
    }

    private Observable<ErrorTip> saveTempData(final Device device, final int sceneNum, final String sceneName, final LocalSceneParam content) {
        return Observable.create(new ObservableOnSubscribe<ErrorTip>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.9
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<ErrorTip> emitter) throws Exception {
                CmdAssistant.getSceneCmdAssistant(device, new int[0]).syncLocalSceneAction(ActivityUtils.getTopActivity(), sceneNum, content.getInstruct(), content.getStep(), content.getTime(), content.getZoneNum(), content.getAddress(), content.isCurState(), ProductRepository.getInfraredType(device.getProductId()), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.9.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg responseMsg) {
                        emitter.onNext(ActSelectDeviceNew.this.getSameDeviceByResponse(sceneNum, sceneName, responseMsg));
                        emitter.onComplete();
                    }
                });
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

    private void synSensorGroupSettingToDevice(final Device device, boolean b2) {
        final WaveSensorState waveSensorState = new WaveSensorState();
        if (!b2) {
            waveSensorState = this.editGroup.getDeviceState().getWaveSensorState();
        }
        CmdAssistant.getDeviceAssistant(device, new int[0]).setIllumincance(ActivityUtils.getTopActivity(), ProductId.ID_SENSOR_MS03.equals(device.getProductId()), waveSensorState.getIlluminance(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda24
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.lambda$synSensorGroupSettingToDevice$40(Device.this, waveSensorState, (Boolean) obj);
            }
        });
    }

    static /* synthetic */ void lambda$synSensorGroupSettingToDevice$40(Device device, WaveSensorState waveSensorState, Boolean bool) {
        if (bool.booleanValue()) {
            CmdAssistant.getDeviceAssistant(device, new int[0]).setSensitivity(ActivityUtils.getTopActivity(), waveSensorState.getSensitivity(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda17
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.lambda$synSensorGroupSettingToDevice$39((Boolean) obj);
                }
            });
        }
    }

    private void synGroupSettingToDevice(Device device, boolean isOut, FtDeviceGroupVM.InOrOutGroupItem item) {
        if (isOut) {
            setting(device, new SmartPanelSettingState(), isOut, item);
            ReplaceHelper.instance().backupSmartPanelDefault(this, device);
        } else {
            if (this.editGroup.getSetting() != null) {
                SmartPanelSettingState smartPanelSettingState = (SmartPanelSettingState) GsonUtils.fromJson(this.editGroup.getSetting(), new TypeToken<SmartPanelSettingState>(this) { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.10
                }.getType());
                if (smartPanelSettingState == null) {
                    smartPanelSettingState = new SmartPanelSettingState();
                }
                setting(device, smartPanelSettingState, isOut, item);
                return;
            }
            this.editGroup.setMemorizePowerOff(3);
            setting(device, new SmartPanelSettingState(), isOut, item);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void settingScreenToDevice(final Device device, final FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem) {
        ArrayList arrayList = new ArrayList();
        if (this.editGroup.getScreenSetting() != null) {
            SmartPanelScreenState smartPanelScreenState = (SmartPanelScreenState) GsonUtils.fromJson(this.editGroup.getScreenSetting(), new TypeToken<SmartPanelScreenState>(this) { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.11
            }.getType());
            if (smartPanelScreenState == null) {
                smartPanelScreenState = new SmartPanelScreenState();
            }
            if (smartPanelScreenState.getScreens() != null && smartPanelScreenState.getTheme() != null && smartPanelScreenState.getScreens().size() == smartPanelScreenState.getTheme().size()) {
                arrayList.add(Integer.valueOf(smartPanelScreenState.getScreensaverMode()));
                arrayList.add(Integer.valueOf((smartPanelScreenState.getScreensaverTime() >> 8) & 255));
                arrayList.add(Integer.valueOf(smartPanelScreenState.getScreensaverTime() & 255));
                for (int i = 0; i < smartPanelScreenState.getScreens().size(); i++) {
                    SmartPanelScreenState.ScreenInfo screenInfo = smartPanelScreenState.getScreens().get(i);
                    arrayList.add(Integer.valueOf(screenInfo.isShow() ? 1 : 0));
                    arrayList.add(smartPanelScreenState.getTheme().get(i));
                    arrayList.add(Integer.valueOf(!screenInfo.isScreenTimeClose() ? 1 : 0));
                    arrayList.add(Integer.valueOf(screenInfo.getStartM()));
                    arrayList.add(Integer.valueOf(screenInfo.getStartS()));
                    arrayList.add(Integer.valueOf(screenInfo.getEndM()));
                    arrayList.add(Integer.valueOf(screenInfo.getEndS()));
                }
            }
        } else {
            arrayList.add(1);
            arrayList.add(0);
            arrayList.add(30);
            arrayList.add(1);
            arrayList.add(1);
            arrayList.add(1);
            arrayList.add(8);
            arrayList.add(0);
            arrayList.add(17);
            arrayList.add(59);
            arrayList.add(1);
            arrayList.add(2);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(1);
            arrayList.add(3);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
        }
        CmdAssistant.getDeviceAssistant(device, new int[0]).syncSmartPanelGroupScreen(this, arrayList, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.12
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                ActSelectDeviceNew actSelectDeviceNew = ActSelectDeviceNew.this;
                actSelectDeviceNew.setpowerOffStatus(device, actSelectDeviceNew.editGroup.getMemorizePowerOff(), inOrOutGroupItem);
            }
        });
    }

    public void setpowerOffStatus(final Device device, int memorizePowerOff, final FtDeviceGroupVM.InOrOutGroupItem item) {
        if (device != null) {
            CmdAssistant.getLightCmdAssistant(device, new int[0]).setOnStateWithoutDialog(ActivityUtils.getTopActivity(), memorizePowerOff, 0, 0, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda21
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.this.lambda$setpowerOffStatus$41(device, item, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setpowerOffStatus$41(Device device, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Boolean bool) {
        if (bool.booleanValue()) {
            setResult(-1);
        }
        if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO) || device.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO) || device.getProductId().equals(ProductId.ID_SMART_PANEL_G4)) {
            syneElderlyMode(device, inOrOutGroupItem);
        } else {
            setRelayPosition(device, inOrOutGroupItem);
        }
    }

    private void syneElderlyMode(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenElderlyMode(this, RelateInfoUtils.relateInfoAssistant.getSwitchScreenBigIcon() == 0 ? 1 : RelateInfoUtils.relateInfoAssistant.getSwitchScreenBigIcon(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda20
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.this.lambda$syneElderlyMode$42(device, item, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syneElderlyMode$42(Device device, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, ResponseMsg responseMsg) {
        syneLanguage(device, inOrOutGroupItem);
    }

    private void syneLanguage(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenLanguage(this, RelateInfoUtils.relateInfoAssistant.getSwitchScreenLanguage() == 0 ? 1 : RelateInfoUtils.relateInfoAssistant.getSwitchScreenLanguage(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda36
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.this.lambda$syneLanguage$43(device, item, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syneLanguage$43(Device device, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, ResponseMsg responseMsg) {
        setSensingDistanceOn(device, inOrOutGroupItem);
    }

    private void setRelayPosition(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        List<RelateInfoItem> list = RelateInfoUtils.relatedInfoList;
        int[] ints = getInts(ProductRepository.getLightColorType((Object) device));
        if (ints.length > 0) {
            for (int i = 0; i < ints.length; i++) {
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    if (list.get(i2).relateInfo.switchIndex == i + 1) {
                        ints[i] = i2 + 1;
                        break;
                    }
                    i2++;
                }
            }
            CmdAssistant.getDeviceAssistant(device, new int[0]).relayMapping(this, ints, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.13
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    ActSelectDeviceNew.this.setGroupLongRelateInfo(device, item);
                }
            });
            return;
        }
        setGroupLongRelateInfo(device, item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGroupLongRelateInfo(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (RelateInfoItem relateInfoItem : RelateInfoUtils.longClickRelatedInfoList) {
            if (relateInfoItem.relateInfo != null) {
                arrayList.add(setLongClick(relateInfoItem, device, i));
            }
            i++;
        }
        if (arrayList.isEmpty()) {
            setSceneBrt(device, item);
        } else {
            ((ObservableSubscribeProxy) Observable.concat(arrayList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Object>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.14
                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                }

                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable d2) {
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    ActSelectDeviceNew.this.setSceneBrt(device, item);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSceneBrt(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        if (RelateInfoUtils.relateInfoAssistant.getSceneDimmerBrt() != null && RelateInfoUtils.relateInfoAssistant.getSceneDimmerBrt().length() >= 6) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setBrtButton(this, Math.round((Integer.parseInt(RelateInfoUtils.relateInfoAssistant.getSceneDimmerBrt().substring(4, 6), 16) * 255.0f) / 100.0f), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.15
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    ActSelectDeviceNew.this.syncDisplayData(device, item);
                }
            });
        } else {
            syncDisplayData(device, item);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.group.ActSelectDeviceNew$16, reason: invalid class name */
    class AnonymousClass16 implements ObservableOnSubscribe<Object> {
        final /* synthetic */ Device val$device;
        final /* synthetic */ RelateInfoItem val$item;
        final /* synthetic */ int val$selectPosition;

        AnonymousClass16(final Device val$device, final RelateInfoItem val$item, final int val$selectPosition) {
            this.val$device = val$device;
            this.val$item = val$item;
            this.val$selectPosition = val$selectPosition;
        }

        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(final ObservableEmitter<Object> emitter) throws Exception {
            int i;
            int i2;
            int unicastAddress = ((BleParam) this.val$device.getParam(BleParam.class)).getUnicastAddress();
            if (this.val$item.relateInfo.type == 8) {
                i = ((BleParam) Injection.repo().device().getDeviceByDeviceId(Injection.repo().scene().getSceneBySceneId(this.val$item.relateInfo.objectId).getMacdeviceid()).getParam(BleParam.class)).getUnicastAddress();
                i2 = DaliProHelper.BROADCAST_ADD;
            } else {
                i = 65025;
                i2 = 1;
            }
            CmdAssistant.getSettingCmdAssistant(this.val$device, new int[0]).subscribeInSwitchPanelScene(ActSelectDeviceNew.this.activity, 1 << this.val$selectPosition, unicastAddress, i, 2, i2, this.val$item.relateInfo.action, this.val$item.relateInfo.type, this.val$item.relateInfo.keyActionExtra, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$16$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectDeviceNew.AnonymousClass16.lambda$subscribe$0(ObservableEmitter.this, (ResponseMsg) obj);
                }
            });
        }

        static /* synthetic */ void lambda$subscribe$0(ObservableEmitter observableEmitter, ResponseMsg responseMsg) {
            observableEmitter.onNext(true);
            observableEmitter.onComplete();
        }
    }

    private Observable<Object> setLongClick(RelateInfoItem item, Device device, int selectPosition) {
        return Observable.create(new AnonymousClass16(device, item, selectPosition));
    }

    private int[] getInts(int type) {
        int[] iArr = new int[0];
        if (type == 19) {
            return new int[3];
        }
        if (type == 21 || type == 18 || type == 11) {
            return new int[4];
        }
        if (type == 8) {
            return new int[1];
        }
        if (type == 9) {
            return new int[2];
        }
        return type == 10 ? new int[3] : iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncDisplayData(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        SmartPanelParam.DisplayParam displayParam;
        showLoadingDialog("");
        List<RelateInfoItem> list = RelateInfoUtils.relatedInfoList;
        ArrayList arrayList = new ArrayList();
        for (RelateInfoItem relateInfoItem : list) {
            RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
            SmartPanelParam.DisplayParam displayParam2 = null;
            if (relateInfo != null) {
                if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue()) {
                    if (relateInfo.objectId <= 0 && RelaySeparationHelper.isRelaySeparationDevice(this.editGroup)) {
                        try {
                            Group relaySubGroup = RelaySeparationHelper.getRelaySubGroup(this.editGroup, relateInfo.switchIndex);
                            if (relaySubGroup != null) {
                                SmartPanelParam.DisplayParam displayParam3 = new SmartPanelParam.DisplayParam();
                                String[] split = relaySubGroup.getName().split("\n");
                                displayParam3.setFirstType(1);
                                displayParam3.setFirstData(split[0].getBytes("gb2312"));
                                if (split.length > 1) {
                                    displayParam3.setSecondType(1);
                                    displayParam3.setSecondData(split[1].getBytes("gb2312"));
                                }
                                arrayList.add(displayParam3);
                            } else {
                                arrayList.add(null);
                            }
                        } catch (Exception unused) {
                            arrayList.add(null);
                        }
                    } else {
                        arrayList.add(null);
                    }
                } else if (relateInfo.objectId != 0 && relateInfoItem.infoName == null) {
                    arrayList.add(null);
                } else {
                    try {
                        displayParam = new SmartPanelParam.DisplayParam();
                    } catch (UnsupportedEncodingException e) {
                        e = e;
                    }
                    try {
                        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
                            String[] split2 = relateInfo.screenStr.split("\n");
                            displayParam.setFirstType(1);
                            displayParam.setFirstData(split2[0].getBytes("gb2312"));
                            if (split2.length > 1) {
                                displayParam.setSecondType(1);
                                displayParam.setSecondData(split2[1].getBytes("gb2312"));
                            }
                        } else if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                            displayParam.setFirstType(2);
                            displayParam.setSecondType(0);
                            int sendIconIndex = ScreenIconUtils.getSendIconIndex(relateInfo.iconIndex);
                            displayParam.setFirstData(new byte[]{(byte) ((sendIconIndex >> 8) & 255), (byte) (sendIconIndex & 255)});
                        } else if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                            displayParam.setFirstType(2);
                            int sendIconIndex2 = ScreenIconUtils.getSendIconIndex(relateInfo.iconIndex);
                            displayParam.setFirstData(new byte[]{(byte) ((sendIconIndex2 >> 8) & 255), (byte) (sendIconIndex2 & 255)});
                            displayParam.setSecondType(1);
                            displayParam.setSecondData(relateInfo.screenStr.getBytes("gb2312"));
                        }
                        displayParam2 = displayParam;
                    } catch (UnsupportedEncodingException e2) {
                        e = e2;
                        displayParam2 = displayParam;
                        e.printStackTrace();
                        arrayList.add(displayParam2);
                    }
                }
            }
            arrayList.add(displayParam2);
        }
        CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenData(this, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda35
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.this.lambda$syncDisplayData$44(device, item, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncDisplayData$44(Device device, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, ResponseMsg responseMsg) {
        syncLongClickDisplayData(device, inOrOutGroupItem);
    }

    private void syncLongClickDisplayData(Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        List<RelateInfoItem> list = RelateInfoUtils.longClickRelatedInfoList;
        ArrayList arrayList = new ArrayList();
        Iterator<RelateInfoItem> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            RelatedInfoExtParam.RelateInfo relateInfo = it.next().relateInfo;
            if (relateInfo != null) {
                try {
                    if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
                        SmartPanelParam.DisplayParam displayParam = new SmartPanelParam.DisplayParam();
                        displayParam.setZone(i);
                        String[] split = relateInfo.screenStr.split("\n");
                        displayParam.setFirstType(3);
                        displayParam.setFirstData(split[0].getBytes("gb2312"));
                        if (split.length > 1) {
                            displayParam.setSecondType(3);
                            displayParam.setSecondData(split[1].getBytes("gb2312"));
                        }
                        arrayList.add(displayParam);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenLongClickData(this, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLongClickDisplayData$45(item, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLongClickDisplayData$45(FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, ResponseMsg responseMsg) {
        lambda$saveData$30(inOrOutGroupItem);
    }

    private void sendSensitivity(final Device device, SmartPanelSettingState state, final FtDeviceGroupVM.InOrOutGroupItem item) {
        CmdAssistant.getDeviceAssistant(device, new int[0]).setSensitivity(ActivityUtils.getTopActivity(), state.getSensingDistance(), new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda26
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDeviceNew.this.lambda$sendSensitivity$46(device, item, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendSensitivity$46(Device device, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Boolean bool) {
        setRelayPosition(device, inOrOutGroupItem);
    }

    public void setSensingDistanceOn(final Device device, final FtDeviceGroupVM.InOrOutGroupItem item) {
        if (this.editGroup.getScreenSetting() != null) {
            final SmartPanelSettingState smartPanelSettingState = (SmartPanelSettingState) GsonUtils.fromJson(this.editGroup.getPanelSettingState(), new TypeToken<SmartPanelSettingState>(this) { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.17
            }.getType());
            if (smartPanelSettingState != null) {
                CmdAssistant.getDeviceAssistant(device, new int[0]).setWaveEnable(ActivityUtils.getTopActivity(), smartPanelSettingState.getSensingDistanceSwitch() == 1, new IAction() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda33
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActSelectDeviceNew.this.lambda$setSensingDistanceOn$47(device, smartPanelSettingState, item, (ResponseMsg) obj);
                    }
                });
                return;
            } else {
                setRelayPosition(device, item);
                return;
            }
        }
        setRelayPosition(device, item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensingDistanceOn$47(Device device, SmartPanelSettingState smartPanelSettingState, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, ResponseMsg responseMsg) {
        sendSensitivity(device, smartPanelSettingState, inOrOutGroupItem);
    }

    private void setting(final Device device, SmartPanelSettingState state, final boolean isOut, final FtDeviceGroupVM.InOrOutGroupItem item) {
        if (device != null) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setSmartPanelMode(ActivityUtils.getTopActivity(), state.getDoubleLight(), state.getReverseLight(), state.getNightMode(), state.getEngravedTextMode(), state.getStartH(), state.getStartM(), state.getEndH(), state.getEndM(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.18
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    if (aBoolean.booleanValue()) {
                        ActSelectDeviceNew.this.setResult(-1);
                    }
                    if (isOut) {
                        return;
                    }
                    if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
                        ActSelectDeviceNew.this.settingScreenToDevice(device, item);
                    } else {
                        ActSelectDeviceNew actSelectDeviceNew = ActSelectDeviceNew.this;
                        actSelectDeviceNew.setpowerOffStatus(device, actSelectDeviceNew.editGroup.getMemorizePowerOff(), item);
                    }
                }
            });
        } else if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
            settingScreenToDevice(device, item);
        } else {
            setpowerOffStatus(device, this.editGroup.getMemorizePowerOff(), item);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadDeviceOutGroupData(final FtDeviceGroupVM.InOrOutGroupItem item) {
        String extParam;
        final Device device = item.getDevice();
        if (device.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT)) {
            if (device.getParam(DryContactBleParam.class) != null) {
                device.setParam(HelpUtils.removeObjectKey((DryContactBleParam) device.getParam(DryContactBleParam.class), "groupId"));
            }
        } else if (device.getParam(BleParam.class) != null) {
            device.setParam(HelpUtils.removeObjectKey((BleParam) device.getParam(BleParam.class), "groupId"));
        }
        if (isSmartPanelDevice(device)) {
            extParam = RelateInfoUtils.initSmartPanelRelateInfoList(device.getProductId()).getExtParamString();
        } else if (isWaveSensorDevice(device)) {
            extParam = new WaveSensorExtParam().getSensorParamMapString();
        } else {
            extParam = device.getExtParam();
        }
        device.setExtParam(extParam);
        if (device.getDeviceState() != null) {
            device.getDeviceState().setOnlineState(1);
        }
        ((ObservableSubscribeProxy) Injection.net().updateParams(device.getDeviceId(), device.getParam(), device.getExtParam(), RelaySeparationHelper.isRelaySeparationPanelDevice(device)).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda47
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$uploadDeviceOutGroupData$48(device, item, obj);
            }
        }, new Consumer<Throwable>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.19
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                ActSelectDeviceNew.this.setTaskSuccess(item);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadDeviceOutGroupData$48(Device device, FtDeviceGroupVM.InOrOutGroupItem inOrOutGroupItem, Object obj) throws Exception {
        SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_CHANGED, true);
        Injection.repo().device().saveDevice(device);
        setTaskSuccess(inOrOutGroupItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void outGroup(FtDeviceGroupVM.InOrOutGroupItem item) {
        if (this.addDeviceIdList.contains(Long.valueOf(item.getDevice().getDeviceId()))) {
            this.addDeviceIdList.remove(Long.valueOf(item.getDevice().getDeviceId()));
            this.selectRoleIds.remove(Long.valueOf(item.getDevice().getObjectId()));
        }
        Injection.mesh().outGroupByCmd(item.getDevice(), this.editGroup.getGroupAddress(), new IAction<Boolean>(this) { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.20
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
            }
        });
    }

    private MicrowaveParam generateSensorRelateInfo(int index, Group group) {
        MicrowaveParam microwaveParam = new MicrowaveParam();
        WaveSensorState waveSensorState = group.getDeviceState().getWaveSensorState();
        microwaveParam.setIndicatorLight(waveSensorState.getIndicatorLight());
        microwaveParam.setEnable(waveSensorState.isEnable());
        microwaveParam.setIlluminance(waveSensorState.getIlluminance());
        microwaveParam.setSensitivity(waveSensorState.getSensitivity());
        WaveSensorExtParam waveSensorExtParam = new WaveSensorExtParam();
        if (group.getExtParam() != null) {
            waveSensorExtParam.fillMapWithString(group.getExtParam());
        }
        microwaveParam.setDestAdd(waveSensorExtParam.getDelayParam(index).destAddress);
        microwaveParam.setTrigType(waveSensorExtParam.getDelayParam(index).actionType);
        microwaveParam.setDelayTime(waveSensorExtParam.getDelayParam(index).delayTime);
        microwaveParam.setDelayInstruct(com.smart.message.utils.StringUtils.hexStringToByte(waveSensorExtParam.getDelayParam(index).instruct.substring(10)));
        if (waveSensorExtParam.getStayParam(index).destAddress == 0 || waveSensorExtParam.getStayParam(index).instruct.equals("")) {
            microwaveParam.setStayInstruct(null);
        } else {
            microwaveParam.setStayTime(waveSensorExtParam.getStayParam(index).delayTime);
            microwaveParam.setStayInstruct(com.smart.message.utils.StringUtils.hexStringToByte(waveSensorExtParam.getStayParam(index).instruct.substring(10)));
        }
        microwaveParam.setExitInstruct(com.smart.message.utils.StringUtils.hexStringToByte(waveSensorExtParam.getExitParam(index).instruct.substring(10)));
        microwaveParam.setStartTimeH(waveSensorExtParam.getEffectTimeStartH(index));
        microwaveParam.setStartTimeM(waveSensorExtParam.getEffectTimeStartM(index));
        microwaveParam.setEndTimeH(waveSensorExtParam.getEffectTimeEndH(index));
        microwaveParam.setEndTimeM(waveSensorExtParam.getEffectTimeEndM(index));
        microwaveParam.setRepeat(waveSensorExtParam.getEffectTimeRepeat(index));
        microwaveParam.setPeriodEnable(waveSensorExtParam.getEffectTimeEnable(index) == 1);
        microwaveParam.setRelayPowerOn(waveSensorExtParam.getRelayAlwaysOn(index) == 1);
        microwaveParam.setAutomationDelay(waveSensorExtParam.getAutomationDelay());
        return microwaveParam;
    }

    private boolean isSmartPanelDevice(Device device) {
        return ProductRepository.isSmartPanelDevice(device.getProductId());
    }

    private boolean isWaveSensorDevice(Device device) {
        return ProductRepository.isWaveSensor(device);
    }

    private boolean filterPanel(Device device) {
        if (device.isSubDevice()) {
            return true;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "4122243156296320":
            case "3557654002353408":
            case "3472747175414464":
            case "3503908278750336":
            case "3537619681035968":
            case "122051609304501":
            case "3503908725640320":
            case "3866287311356992":
            case "122111615282701":
            case "122110709484501":
            case "3503907950824576":
                return true;
            default:
                return false;
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        if (this.alreadyJoin) {
            return this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId()));
        }
        String groupKey = this.editGroup.getGroupKey();
        if (this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S1C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S2C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S3C) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S4) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S4M) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_S6PRO) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH_PANEL_G4) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR03) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR04) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MS03) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB1) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB2) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB5) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB1) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB2) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB4) || this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB5)) {
            if (groupKey.equals(ProductRepository.getGroupKey(device)) && !filterPanel(device) && (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0 || ((BleParam) device.getParam(BleParam.class)).getGroupId() == this.editGroup.getGroupId())) {
                return !this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId()));
            }
        } else {
            if (RelaySeparationHelper.isRelaySeparationSub(device)) {
                return !this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId())) && this.editGroup.getGroupKey().equals(ProductId.BLE_GROUP_SWITCH) && RelaySeparationHelper.isSwitchRelay(device);
            }
            if (groupKey.equals(ProductRepository.getGroupKey(device)) && !filterPanel(device)) {
                return !this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId()));
            }
        }
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void changeSelectCount(int integer) {
        if (this.selectAll) {
            ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_cancel_select_all));
        } else {
            ((ActSelect3Binding) this.mViewBinding).tvEdit.setText(getString(R.string.app_str_select_all));
        }
        if (this.alreadyJoin) {
            this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_out_device), Integer.valueOf(this.selectRoleIds.size())));
        } else {
            this.okTv.setText(String.format(getResources().getString(R.string.app_str_select_join_device), Integer.valueOf(this.selectRoleIds.size())));
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    public void syncLocalScene(final Device device) {
        ((ObservableSubscribeProxy) Injection.net().getGroupLocalSceneAction(this.editGroup.getGroupId()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda43
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLocalScene$49((Disposable) obj);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda45
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLocalScene$50(device, (QuerySceneActionResponse) obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew$$ExternalSyntheticLambda46
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectDeviceNew.this.lambda$syncLocalScene$51((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalScene$49(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalScene$50(Device device, QuerySceneActionResponse querySceneActionResponse) throws Exception {
        syncLocalDataToDevice(device, querySceneActionResponse.getRows());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLocalScene$51(Throwable th) throws Exception {
        dismissLoadingDialog();
        showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    private Observable<ErrorTip> saveTempDataWithTip(final Device device, final int sceneNum, final String sceneName, final LocalSceneParam content) {
        return Observable.create(new ObservableOnSubscribe<ErrorTip>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.21
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<ErrorTip> emitter) throws Exception {
                List list;
                if (ActSelectDeviceNew.this.errorTipMap.size() > 0 && ActSelectDeviceNew.this.errorTipMap.containsKey(-1) && (list = (List) ActSelectDeviceNew.this.errorTipMap.get(-1)) != null && list.size() >= 1) {
                    emitter.onComplete();
                } else {
                    CmdAssistant.getSceneCmdAssistant(device, new int[0]).syncLocalSceneAction(ActivityUtils.getTopActivity(), sceneNum, content.getInstruct(), content.getStep(), content.getTime(), content.getZoneNum(), content.getAddress(), content.isCurState(), ProductRepository.getInfraredType(device.getProductId()), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.21.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(ResponseMsg responseMsg) {
                            if (responseMsg != null) {
                                ActSelectDeviceNew.this.mSyncNum++;
                            }
                            emitter.onNext(ActSelectDeviceNew.this.getSameDeviceByResponse(sceneNum, sceneName, responseMsg));
                            emitter.onComplete();
                        }
                    });
                }
            }
        });
    }

    private void batchControlWithDialog(List<Observable<ErrorTip>> request) {
        this.errorTipMap = new HashMap();
        this.mSyncNum = 0;
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<ErrorTip>() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.22
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
                if (ActSelectDeviceNew.this.mSceneNums.size() > 0) {
                    ActSelectDeviceNew actSelectDeviceNew = ActSelectDeviceNew.this;
                    actSelectDeviceNew.showLoadingDialog(String.format(actSelectDeviceNew.getString(R.string.app_str_synchronizing_data), "1", String.valueOf(ActSelectDeviceNew.this.mSceneNums.size())));
                }
            }

            @Override // io.reactivex.Observer
            public void onNext(ErrorTip errorTip) {
                if (errorTip.getErrorType() != 0) {
                    if (ActSelectDeviceNew.this.errorTipMap.containsKey(Integer.valueOf(errorTip.getErrorType()))) {
                        List list = (List) ActSelectDeviceNew.this.errorTipMap.get(Integer.valueOf(errorTip.getErrorType()));
                        if (list == null) {
                            list = new ArrayList();
                        }
                        list.add(errorTip.getSceneName());
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(errorTip.getSceneName());
                        ActSelectDeviceNew.this.errorTipMap.put(Integer.valueOf(errorTip.getErrorType()), arrayList);
                    }
                }
                ActSelectDeviceNew actSelectDeviceNew = ActSelectDeviceNew.this;
                actSelectDeviceNew.showLoadingDialog(String.format(actSelectDeviceNew.getString(R.string.app_str_synchronizing_data), String.valueOf(ActSelectDeviceNew.this.mSyncNum), String.valueOf(ActSelectDeviceNew.this.mSceneNums.size())));
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (ActSelectDeviceNew.this.errorTipMap.size() > 0) {
                    ActSelectDeviceNew.this.showErrorTipDialog();
                    ActSelectDeviceNew.this.dismissLoadingDialog();
                } else {
                    ActSelectDeviceNew.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActSelectDeviceNew.this.getString(R.string.sync_success));
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
                MessageDialog.show(this.activity, getString(R.string.local_scene_sync_fail), getString(R.string.local_scene_sync_error_0C)).setOkButton(getString(R.string.ok)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.24
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        it.remove();
                        ActSelectDeviceNew.this.showErrorTipDialog();
                        return false;
                    }
                }).setCustomView(getCustomView(next.getValue())).setCancelable(false);
            } else if (intValue == 13) {
                MessageDialog.show(this.activity, getString(R.string.local_scene_sync_fail), getString(R.string.local_scene_sync_error_0D)).setOkButton(getString(R.string.ok)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.25
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        it.remove();
                        ActSelectDeviceNew.this.showErrorTipDialog();
                        return false;
                    }
                }).setCustomView(getCustomView(next.getValue())).setCancelable(false);
            } else if (intValue == 15) {
                MessageDialog.show(this.activity, getString(R.string.local_scene_sync_fail), getString(R.string.local_scene_sync_error_0F)).setOkButton(getString(R.string.ok)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.23
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        it.remove();
                        ActSelectDeviceNew.this.showErrorTipDialog();
                        return false;
                    }
                }).setCustomView(getCustomView(next.getValue())).setCancelable(false);
            } else {
                MessageDialog.show(this.activity, getString(R.string.local_scene_sync_fail), getString(next.getKey().intValue() == 153 ? R.string.local_scene_sync_error_99 : R.string.local_scene_sync_error_normal)).setOkButton(getString(R.string.ok)).setOnOkButtonClickListener(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.26
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        it.remove();
                        ActSelectDeviceNew.this.showErrorTipDialog();
                        return false;
                    }
                }).setCancelable(false);
            }
        }
    }

    private View getCustomView(List<String> names) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_custom_list, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(this, R.layout.item_scene_error_tip, names) { // from class: com.ltech.smarthome.ui.group.ActSelectDeviceNew.27
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_content, item);
            }
        });
        return inflate;
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

        public ErrorTip(int errorType) {
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