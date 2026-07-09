package com.ltech.smarthome.ui.control.provider;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.nfc.BaseNfcActivity;
import com.ltech.smarthome.nfc.WriteVirtualHelper;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.group.ActAddWiFiGroup;
import com.ltech.smarthome.ui.group.ActSelectDeviceNew;
import com.ltech.smarthome.ui.permission.ActGetMeshPermission;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.NormalDialog;
import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.IGuide;
import com.qw.curtain.lib.shape.RoundShape;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.SmartUtils;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.ISendResutCallback;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.extra.Emitter;
import com.smart.product_agreement.productBle.CmdBleFactory;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zyyoona7.popup.EasyPopup;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class BaseDeviceProvider<T extends Role> extends BaseItemProvider<T, BaseViewHolder> {
    private final FragmentActivity activity;
    private NormalDialog mChoiceDialog;
    protected OnDataChangeListener onDataChangeListener;
    private final RecyclerView recyclerView;
    protected final BaseViewModel viewModel;

    public interface OnDataChangeListener {
        void onDataChange(Group sub);

        void onDataDelete(Role role);

        void onDataOfflineChange(int position);

        void onDeviceHide(Role role);

        void onGroupOfflineChange(int position);
    }

    static /* synthetic */ boolean lambda$showDelFailDialog$6(BaseDialog baseDialog, View view) {
        return false;
    }

    public abstract void onItemClick(BaseViewHolder helper, T data, int position);

    public BaseDeviceProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, OnDataChangeListener onDataChangeListener) {
        this.viewModel = viewModel;
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.onDataChangeListener = onDataChangeListener;
    }

    public BaseDeviceProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
        this.viewModel = viewModel;
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    void setDeviceFavourite(T t) {
        t.getDeviceState().setFavorite(!t.getDeviceState().isFavorite());
        if (t instanceof Device) {
            Injection.repo().device().saveDevice((Device) t);
        }
    }

    void setDeviceOn(T t, boolean on) {
        t.getDeviceState().setOn(on);
        if (t instanceof Device) {
            Injection.repo().device().saveDevice((Device) t);
        }
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final T data, final int position) {
        boolean z = false;
        if ((data instanceof Group) && ((Group) data).getDeviceIds().isEmpty() && !RelaySeparationHelper.isRelaySeparationSub(data)) {
            helper.setText(R.id.appCompatTextView17, this.mContext.getResources().getString(R.string.app_str_empty_group)).setGone(R.id.appCompatTextView17, true);
            helper.setGone(R.id.view10, true);
            helper.getView(R.id.view10).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    if (Boolean.TRUE.equals(((FtRoomVM) BaseDeviceProvider.this.viewModel).editRoleMode.getValue())) {
                        if (((FtRoomVM) BaseDeviceProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                            ((FtRoomVM) BaseDeviceProvider.this.viewModel).selectRoleList.getValue().remove(data);
                            helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                        } else {
                            ((FtRoomVM) BaseDeviceProvider.this.viewModel).selectRoleList.getValue().add(data);
                            helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                        }
                        ((FtRoomVM) BaseDeviceProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) BaseDeviceProvider.this.viewModel).selectRoleList.getValue());
                        return;
                    }
                    if (BaseDeviceProvider.this.isNormalMode()) {
                        BaseDeviceProvider.this.showEmptyGroupDialog((Group) data, position);
                    }
                }
            });
        } else {
            if (helper.getView(R.id.appCompatTextView17) != null) {
                helper.setGone(R.id.appCompatTextView17, false);
            }
            if (helper.getView(R.id.view10) != null) {
                helper.setGone(R.id.view10, false);
            }
        }
        helper.setText(R.id.appCompatTextView15, data.getName());
        helper.setImageResource(R.id.appCompatImageView9, ProductRepository.getProductIcon(data));
        helper.setBackgroundRes(R.id.iv_favorite, data.getDeviceState().isFavorite() ? R.mipmap.ic_favorite : R.mipmap.ic_favorite_default);
        helper.getView(R.id.v_favorite).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BaseDeviceProvider.this.setDeviceFavourite(data);
            }
        });
        if (Boolean.TRUE.equals(((FtRoomVM) this.viewModel).editRoleMode.getValue())) {
            helper.setVisible(R.id.iv_device_more, true);
            if (((FtRoomVM) this.viewModel).selectRoleList.getValue().contains(data)) {
                helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
            } else {
                helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
            }
        } else if (Boolean.TRUE.equals(((FtRoomVM) this.viewModel).editSceneMode.getValue())) {
            helper.setVisible(R.id.iv_device_more, false);
        } else {
            helper.setVisible(R.id.iv_device_more, true);
            helper.setImageResource(R.id.iv_device_more, R.mipmap.icon_as_panel_bind_gray);
        }
        if (helper.getView(R.id.tv_virtual) != null) {
            helper.setGone(R.id.tv_virtual, data.isVirtual());
        }
        if (data instanceof Device) {
            final Device device = (Device) data;
            if (helper.getView(R.id.tv_write) != null) {
                if (isNormalMode() && device.isVirtual() && !device.isSubDevice() && device.getWritable() == 1) {
                    z = true;
                }
                helper.setGone(R.id.tv_write, z);
                helper.getView(R.id.tv_write).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BaseDeviceProvider.lambda$convert$1(Device.this, helper, view);
                    }
                });
            }
        }
    }

    static /* synthetic */ void lambda$convert$1(Device device, final BaseViewHolder baseViewHolder, View view) {
        WriteVirtualHelper.instance().init((BaseNfcActivity) ActivityUtils.getTopActivity(), device, true);
        ((BaseNfcActivity) ActivityUtils.getTopActivity()).showNfcDialog(new BaseNfcActivity.OnNfcWriteCallback() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.nfc.BaseNfcActivity.OnNfcWriteCallback
            public final void onFinish(boolean z) {
                BaseDeviceProvider.lambda$convert$0(BaseViewHolder.this, z);
            }
        });
    }

    static /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, boolean z) {
        if (z) {
            baseViewHolder.setGone(R.id.tv_write, false);
        }
    }

    protected boolean bleIsOk() {
        if (Injection.repo().device().getOnlineGateway() != null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT < 31) {
            boolean isLocationEnabled = Injection.state().isLocationEnabled(ActivityUtils.getTopActivity());
            if (!AndPermission.hasPermissions(ActivityUtils.getTopActivity(), Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)) {
                showPermissionActivity();
                return false;
            }
            if (!isLocationEnabled) {
                showResetDialog();
                return false;
            }
        } else if (Build.VERSION.SDK_INT >= 31 && !AndPermission.hasPermissions(ActivityUtils.getTopActivity(), "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT")) {
            showPermissionActivity();
            return false;
        }
        if (Injection.state().isBluetoothEnabled()) {
            return true;
        }
        ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 0);
        return false;
    }

    /* renamed from: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$3, reason: invalid class name */
    class AnonymousClass3 implements Runnable {
        AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SharedPreferenceUtil.edit().keepShared(Constants.PHONE_LOCATION_PERMISSION_HOME_PAGE_NEED_SHOW, false).commit();
            ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(ActGetMeshPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$3$$ExternalSyntheticLambda0
                @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
                public final void onActivityResult(int i, Intent intent) {
                    BaseDeviceProvider.AnonymousClass3.this.lambda$run$0(i, intent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(int i, Intent intent) {
            if (i == 1002) {
                LHomeLog.i(getClass(), "onCreate: GET_MESH_PERMISSION_SUCCESS");
                if (BaseDeviceProvider.this.viewModel instanceof FtRoomVM) {
                    ((FtRoomVM) BaseDeviceProvider.this.viewModel).scanEvent.call();
                }
            }
        }
    }

    private void showPermissionActivity() {
        ThreadUtils.runOnUiThread(new AnonymousClass3());
    }

    private void showResetDialog() {
        if (this.mChoiceDialog == null) {
            NormalDialog normalDialog = new NormalDialog(ActivityUtils.getTopActivity(), R.style.MyDialog);
            this.mChoiceDialog = normalDialog;
            normalDialog.setYesOnclickListener(ActivityUtils.getTopActivity().getResources().getString(R.string.go_to_setting), new NormalDialog.onYesOnclickListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.4
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onYesOnclickListener
                public void onYesOnclick() {
                    ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 666);
                    BaseDeviceProvider.this.mChoiceDialog.dismiss();
                }
            });
            this.mChoiceDialog.setNoOnclickListener(ActivityUtils.getTopActivity().getResources().getString(R.string.no_open), new NormalDialog.onNoOnclickListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.5
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onNoOnclickListener
                public void onNoClick() {
                    BaseDeviceProvider.this.mChoiceDialog.dismiss();
                }
            });
        }
        if (this.mChoiceDialog.isShowing()) {
            return;
        }
        this.mChoiceDialog.show();
    }

    protected boolean isNormalMode() {
        return Boolean.FALSE.equals(((FtRoomVM) this.viewModel).editRoleMode.getValue()) && Boolean.FALSE.equals(((FtRoomVM) this.viewModel).editSceneMode.getValue());
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void onClick(BaseViewHolder helper, T data, int position) {
        super.onClick((BaseDeviceProvider<T>) helper, (BaseViewHolder) data, position);
        if (this.viewModel instanceof FtRoomVM) {
            if (Boolean.TRUE.equals(((FtRoomVM) this.viewModel).editRoleMode.getValue())) {
                List<Role> value = ((FtRoomVM) this.viewModel).selectRoleList.getValue();
                if (value != null) {
                    if (value.contains(data)) {
                        value.remove(data);
                    } else {
                        value.add(data);
                    }
                    ((FtRoomVM) this.viewModel).selectRoleList.setValue(value);
                    ((FtRoomVM) this.viewModel).selectRolePos.setValue(Integer.valueOf(position));
                    return;
                }
                return;
            }
            if (Boolean.FALSE.equals(((FtRoomVM) this.viewModel).editSceneMode.getValue())) {
                onItemClick(helper, data, position);
            }
        }
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public boolean onLongClick(BaseViewHolder helper, T data, int position) {
        if ((getCurPlace().isManager() || getCurPlace().isOwner()) && isNormalMode()) {
            ((FtRoomVM) this.viewModel).selectRoleList.setValue(new ArrayList());
            ((FtRoomVM) this.viewModel).editRoleMode.setValue(true);
        }
        return true;
    }

    private void showHideDialog(final T data, BaseViewHolder helper, int position) {
        final EasyPopup apply = EasyPopup.create().setContentView(this.activity, R.layout.dialog_device_hide).setFocusAndOutsideEnable(true).setWidth(helper.itemView.getWidth()).setHeight(ConvertUtils.dp2px(50.0f)).apply();
        final Curtain callBack = new Curtain(this.activity).withShape(apply.getContentView(), new RoundShape(ConvertUtils.dp2px(10.0f))).withShape(helper.itemView, new RoundShape(ConvertUtils.dp2px(10.0f)), true).setInterceptTargetView(false).setCallBack(new Curtain.CallBack() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.6
            @Override // com.qw.curtain.lib.Curtain.CallBack
            public void onShow(IGuide curtain) {
                ((FtRoomVM) BaseDeviceProvider.this.viewModel).canScroll.setValue(false);
            }

            @Override // com.qw.curtain.lib.Curtain.CallBack
            public void onDismiss(IGuide iGuide) {
                if (apply.isShowing()) {
                    apply.dismiss();
                }
                ((FtRoomVM) BaseDeviceProvider.this.viewModel).canScroll.setValue(true);
            }
        });
        callBack.show();
        apply.showAtAnchorView(helper.itemView, 2, 0, 0, ConvertUtils.dp2px(20.0f));
        apply.getContentView().setOnTouchListener(new View.OnTouchListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.7
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                BaseDeviceProvider.this.updateExt(data);
                callBack.dismiss();
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateExt(final Role role) {
        if (role instanceof Device) {
            final Device device = (Device) role;
            try {
                JSONObject jSONObject = device.getExtParam() != null ? new JSONObject(device.getExtParam()) : new JSONObject();
                jSONObject.put("hideDevice", 1);
                device.setExtParam(jSONObject.toString());
                ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda6
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BaseDeviceProvider.this.lambda$updateExt$2((Disposable) obj);
                    }
                }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.8
                    @Override // io.reactivex.functions.Action
                    public void run() throws Exception {
                        BaseDeviceProvider.this.viewModel.dismissLoadingDialog();
                    }
                }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda7
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BaseDeviceProvider.this.lambda$updateExt$3(role, device, obj);
                    }
                }, new SmartErrorComsumer());
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        if (role instanceof Group) {
            final Group group = (Group) role;
            try {
                JSONObject jSONObject2 = group.getExtParam() != null ? new JSONObject(group.getExtParam()) : new JSONObject();
                jSONObject2.put("hideDevice", 1);
                group.setExtParam(jSONObject2.toString());
                ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), jSONObject2.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda8
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BaseDeviceProvider.this.lambda$updateExt$4((Disposable) obj);
                    }
                }).doFinally(new Action() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.9
                    @Override // io.reactivex.functions.Action
                    public void run() throws Exception {
                        BaseDeviceProvider.this.viewModel.dismissLoadingDialog();
                    }
                }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda9
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        BaseDeviceProvider.this.lambda$updateExt$5(role, group, obj);
                    }
                }, new SmartErrorComsumer());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExt$2(Disposable disposable) throws Exception {
        this.viewModel.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExt$3(Role role, Device device, Object obj) throws Exception {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(role.getObjectId());
        deviceByDeviceId.setExtParam(device.getExtParam());
        Injection.repo().device().saveDevice(deviceByDeviceId);
        OnDataChangeListener onDataChangeListener = this.onDataChangeListener;
        if (onDataChangeListener != null) {
            onDataChangeListener.onDeviceHide(deviceByDeviceId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExt$4(Disposable disposable) throws Exception {
        this.viewModel.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExt$5(Role role, Group group, Object obj) throws Exception {
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(role.getObjectId());
        groupByGroupId.setExtParam(group.getExtParam());
        Injection.repo().group().saveGroup(groupByGroupId);
        OnDataChangeListener onDataChangeListener = this.onDataChangeListener;
        if (onDataChangeListener != null) {
            onDataChangeListener.onDeviceHide(groupByGroupId);
        }
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public Observable<Integer> checkSingleDeviceStatus(final Device device) {
        return Observable.create(new ObservableOnSubscribe<Integer>(this) { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.10
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code restructure failed: missing block: B:55:0x00c8, code lost:
            
                if (r4.equals(com.ltech.smarthome.model.product.ProductId.ID_BLE_LIGHT_DIM) == false) goto L12;
             */
            @Override // io.reactivex.ObservableOnSubscribe
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void subscribe(final io.reactivex.ObservableEmitter<java.lang.Integer> r8) throws java.lang.Exception {
                /*
                    Method dump skipped, instructions count: 382
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.AnonymousClass10.subscribe(io.reactivex.ObservableEmitter):void");
            }
        });
    }

    public void checkGroupStatus(final Group group) {
        if (group.getDeviceIds() == null || group.getDeviceIds().isEmpty()) {
            return;
        }
        if (ProductRepository.needCheckOnlineState(group)) {
            ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<Integer>(this) { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.12
                @Override // io.reactivex.ObservableOnSubscribe
                public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                    if (System.currentTimeMillis() - Injection.repo().group().getGroupById(group.getId()).getCheckTime() <= 8000) {
                        emitter.onComplete();
                        return;
                    }
                    if (group.getGroupAddress() == 0) {
                        emitter.onComplete();
                        return;
                    }
                    BaseCtrlPackage convert = SmartUtils.getICtrlConverter().convert(group);
                    BaseCmd queryAmbientLightStatus = CmdBleFactory.queryAmbientLightStatus(0);
                    MessageManager.getInstance().addGroupQueryResult(group.getGroupAddress(), new MessageManager.UpdateQuery() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.12.1
                        @Override // com.smart.message.MessageManager.UpdateQuery
                        public void update(int uniAddress) {
                            if (group.getGroupAddress() == uniAddress) {
                                emitter.onComplete();
                            }
                        }
                    });
                    LHomeLog.e("checkGroup", getClass(), "send=" + group.getGroupAddress());
                    SmartUtils.send(Emitter.MIX_BLE_IOT, convert, queryAmbientLightStatus, new ISendResutCallback(this) { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.12.2
                        @Override // com.smart.message.base.ISendResutCallback
                        public void onResultError() {
                        }

                        @Override // com.smart.message.base.ISendResutCallback
                        public void onResultSuccess(boolean isIot) {
                        }
                    });
                    Thread.sleep(5000L);
                    emitter.onNext(Integer.valueOf(group.getGroupAddress()));
                    emitter.onComplete();
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.11
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable d2) {
                }

                @Override // io.reactivex.Observer
                public void onNext(Integer integer) {
                    Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
                    if (group.getLeaderSup() == 0) {
                        BaseDeviceProvider.this.checkSingleDeviceStatus(group);
                        return;
                    }
                    groupByGroupId.getGroupState().setOnlineState(2);
                    Injection.repo().group().saveGroup(groupByGroupId);
                    if (BaseDeviceProvider.this.onDataChangeListener != null) {
                        BaseDeviceProvider.this.onDataChangeListener.onGroupOfflineChange(integer.intValue());
                    }
                }
            });
        } else {
            checkSingleDeviceStatus(group);
        }
    }

    protected void checkSingleDeviceStatus(Group group) {
        final Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue());
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<Integer>(this) { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.14
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                BaseCmd querDeviceState;
                if (System.currentTimeMillis() - Injection.repo().device().getDeviceById(deviceByDeviceId.getId()).getCheckTime() <= 8000) {
                    emitter.onComplete();
                    return;
                }
                final BleParam bleParam = (BleParam) deviceByDeviceId.getParam(BleParam.class);
                if (bleParam == null) {
                    emitter.onComplete();
                    return;
                }
                BaseCtrlPackage convert = SmartUtils.getICtrlConverter().convert(deviceByDeviceId);
                querDeviceState = CmdBleFactory.querDeviceState(0, bleParam.getUnicastAddress());
                String productId = deviceByDeviceId.getProductId();
                productId.hashCode();
                switch (productId) {
                    case "120033108251501":
                    case "120033108255901":
                    case "120033108263401":
                    case "120033108265701":
                    case "120033108272201":
                    case "120033108344501":
                    case "120033108345901":
                    case "120033108351401":
                    case "120033108353001":
                    case "120033108355901":
                        querDeviceState = CmdBleFactory.queryAmbientLightStatus(0);
                        break;
                }
                MessageManager.getInstance().addDeviceQueryResult(bleParam.getUnicastAddress(), new MessageManager.UpdateQuery(this) { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.14.1
                    @Override // com.smart.message.MessageManager.UpdateQuery
                    public void update(int uniAddress) {
                        if (bleParam.getUnicastAddress() == uniAddress) {
                            emitter.onComplete();
                        }
                    }
                });
                LHomeLog.e("checkDEV", getClass(), "send=" + bleParam.getUnicastAddress());
                SmartUtils.send(Emitter.MIX_BLE_IOT, convert, querDeviceState, new ISendResutCallback(this) { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.14.2
                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultError() {
                    }

                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultSuccess(boolean isIot) {
                    }
                });
                Thread.sleep(5000L);
                emitter.onNext(Integer.valueOf(bleParam.getUnicastAddress()));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.13
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Integer integer) {
                Device deviceByDeviceId2 = Injection.repo().device().getDeviceByDeviceId(deviceByDeviceId.getDeviceId());
                deviceByDeviceId2.getDeviceState().setOnlineState(2);
                Injection.repo().device().saveDevice(deviceByDeviceId2);
                if (BaseDeviceProvider.this.onDataChangeListener != null) {
                    BaseDeviceProvider.this.onDataChangeListener.onDataOfflineChange(integer.intValue());
                }
            }
        });
    }

    protected void showEmptyGroupDialog(final Group data, final int position) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_empty_group_tip, (ViewGroup) null);
        final AlertDialog create = new AlertDialog.Builder(this.mContext).create();
        create.show();
        Window window = create.getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        window.setContentView(inflate);
        Display defaultDisplay = this.activity.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = defaultDisplay.getWidth() - 100;
        attributes.height = -2;
        window.setAttributes(attributes);
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.15
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                create.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_dissolve).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.16
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ProductRepository.isBleGroup(data.getModuleType()) && !data.getDeviceIds().isEmpty()) {
                    BaseDeviceProvider.this.showDelFailDialog();
                    create.dismiss();
                } else {
                    BaseDeviceProvider.this.delGroupFromNet(data, position, create);
                }
            }
        });
        inflate.findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider.17
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                NavUtils.destination(ProductRepository.isBleGroup(data.getModuleType()) ? ActSelectDeviceNew.class : ActAddWiFiGroup.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.FLOOR_ID, data.getFloorId()).withLong(Constants.ROOM_ID, data.getRoomId()).withLong(Constants.GROUP_ID, data.getId()).navigation(BaseDeviceProvider.this.activity);
                create.dismiss();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDelFailDialog() {
        MessageDialog.show((AppCompatActivity) this.activity, this.mContext.getString(R.string.del_fail), this.mContext.getString(R.string.del_fail_tip)).setOkButton(this.mContext.getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return BaseDeviceProvider.lambda$showDelFailDialog$6(baseDialog, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delGroupFromNet(final Group group, int position, final AlertDialog alertDialog) {
        ((ObservableSubscribeProxy) Injection.net().deleteGroup(group.getGroupId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceProvider.this.lambda$delGroupFromNet$7((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Action
            public final void run() {
                BaseDeviceProvider.this.lambda$delGroupFromNet$8(alertDialog);
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.provider.BaseDeviceProvider$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseDeviceProvider.this.lambda$delGroupFromNet$9(group, obj);
            }
        }, new SmartErrorComsumer());
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 1 || lightColorType == 2 || lightColorType == 3 || lightColorType == 4 || lightColorType == 5 || lightColorType == 20) {
            unSubscribePublicationAddress(group);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$7(Disposable disposable) throws Exception {
        this.viewModel.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$8(AlertDialog alertDialog) throws Exception {
        this.viewModel.dismissLoadingDialog();
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delGroupFromNet$9(Group group, Object obj) throws Exception {
        for (Group group2 : Injection.repo().group().getSubGroup(group.getPlaceId(), group.getGroupId())) {
            Injection.repo().group().removeGroupFromDb(group2.getId());
            OnDataChangeListener onDataChangeListener = this.onDataChangeListener;
            if (onDataChangeListener != null) {
                onDataChangeListener.onDataDelete(group2);
            }
        }
        Injection.repo().group().removeGroupFromDb(group.getId());
        OnDataChangeListener onDataChangeListener2 = this.onDataChangeListener;
        if (onDataChangeListener2 != null) {
            onDataChangeListener2.onDataDelete(group);
        }
    }

    private void unSubscribePublicationAddress(Group group) {
        LHomeLog.i(getClass(), "delete device--->" + group);
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), group.getGroupAddress(), ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()), new int[0]);
    }
}