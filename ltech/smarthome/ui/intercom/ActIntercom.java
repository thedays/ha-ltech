package com.ltech.smarthome.ui.intercom;

import android.animation.ObjectAnimator;
import android.bluetooth.BluetoothAdapter;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.akuvox.ble_nfc_library.BleOpenDoorCallBack;
import com.akuvox.ble_nfc_library.NBSdkUtils;
import com.akuvox.mobile.libcommon.model.media.MediaManager;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActIntercomBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.intercom.KeyListResponse;
import com.ltech.smarthome.net.response.intercom.UserInfoResponse;
import com.ltech.smarthome.ui.intercom.ActIntercom;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.CenterSelectListIntercomDialog;
import com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.runtime.Permission;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActIntercom extends VMActivity<ActIntercomBinding, ActIntercomVM> {
    private static final String[] PERMISSIONS = {Permission.RECORD_AUDIO, Permission.WRITE_EXTERNAL_STORAGE, "android.permission.MODIFY_AUDIO_SETTINGS"};
    private BaseQuickAdapter<UserInfoResponse.DevInfo, BaseViewHolder> devInfoAdapter;
    private ObjectAnimator rotationAnimator;
    private final int MONITOR_PERMISSION_CODE = 6666;
    private final int BLE_SEARCHING_FAIL = 4567;
    private final int BLE_TRY_OPEN = 123;
    private boolean isSearching = false;
    private Handler bleHandler = new Handler(Looper.getMainLooper()) { // from class: com.ltech.smarthome.ui.intercom.ActIntercom.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 4567) {
                ActIntercom.this.bleHandler.removeMessages(123);
                SmartToast.showCenterShort(ActIntercom.this.getString(R.string.not_open));
                ActIntercom.this.setBluetoothSearchView(false);
            } else if (msg.what == 123) {
                ActIntercom.this.bleHandler.sendEmptyMessageDelayed(123, 10000L);
                ActIntercom actIntercom = ActIntercom.this;
                NBSdkUtils.openDoorWithBleCode(actIntercom, ((ActIntercomVM) actIntercom.mViewModel).bleCode, new BleOpenDoorCallBack() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom.1.1
                    @Override // com.akuvox.ble_nfc_library.BleOpenDoorCallBack
                    public void openDoorResult(String ret) {
                        if (Integer.valueOf(ret).intValue() == 0) {
                            ActIntercom.this.setBluetoothSearchView(false);
                            SmartToast.showCenterShort(ActIntercom.this.getString(R.string.open_success));
                            ActIntercom.this.bleHandler.removeMessages(4567);
                            ActIntercom.this.bleHandler.removeMessages(123);
                        }
                    }
                });
            }
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_intercom;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.intercom));
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
        ((ActIntercomVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActIntercomVM) this.mViewModel).getQrcodeData();
        ((ActIntercomVM) this.mViewModel).initAccessControlFtList();
        initRv();
        initRefresh();
        if (MediaManager.getInstance(this).getSipBackendOnline()) {
            return;
        }
        MediaManager.getInstance(this).setSipBackendOnline(true);
    }

    private void initRefresh() {
        ((ActIntercomBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((ActIntercomBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((ActIntercomBinding) this.mViewBinding).refreshLayout.setHeaderHeight(100.0f);
        ((ActIntercomBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda8
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ActIntercom.this.lambda$initRefresh$0(refreshLayout);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRefresh$0(RefreshLayout refreshLayout) {
        refreshData();
        refreshLayout.finishRefresh();
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        super.refreshData();
        ((ActIntercomVM) this.mViewModel).getQrcodeData();
        ((ActIntercomVM) this.mViewModel).initAccessControlFtList();
    }

    private void initRv() {
        RecyclerView recyclerView = ((ActIntercomBinding) this.mViewBinding).rvDoor;
        BaseQuickAdapter<UserInfoResponse.DevInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<UserInfoResponse.DevInfo, BaseViewHolder>(this, R.layout.item_intercom_door) { // from class: com.ltech.smarthome.ui.intercom.ActIntercom.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, UserInfoResponse.DevInfo item) {
                holder.setText(R.id.intercom_name, item.getLocation());
                holder.addOnClickListener(R.id.layout_monitor);
                holder.addOnClickListener(R.id.layout_open);
                if (item.getStatus() == 1) {
                    holder.setAlpha(R.id.intercom_name, 1.0f);
                    holder.setAlpha(R.id.layout_monitor, 1.0f);
                    holder.setAlpha(R.id.layout_open, 1.0f);
                } else {
                    holder.setAlpha(R.id.intercom_name, 0.5f);
                    holder.setAlpha(R.id.layout_monitor, 0.5f);
                    holder.setAlpha(R.id.layout_open, 0.5f);
                }
            }
        };
        this.devInfoAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.devInfoAdapter.setOnItemChildClickListener(new AnonymousClass3());
    }

    /* renamed from: com.ltech.smarthome.ui.intercom.ActIntercom$3, reason: invalid class name */
    class AnonymousClass3 implements BaseQuickAdapter.OnItemChildClickListener {
        AnonymousClass3() {
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            final UserInfoResponse.DevInfo devInfo = (UserInfoResponse.DevInfo) ActIntercom.this.devInfoAdapter.getItem(position);
            if (devInfo != null) {
                int id = view.getId();
                if (id == R.id.layout_monitor) {
                    if (devInfo.getStatus() == 1 && ActIntercom.this.checkPermission()) {
                        IntercomManager.getInstance().onMonitorPending(ActIntercom.this, position);
                        return;
                    }
                    return;
                }
                if (id == R.id.layout_open && devInfo.getStatus() == 1) {
                    if (devInfo.getRelay() != null) {
                        if (devInfo.getRelay().size() > 1) {
                            ArrayList arrayList = new ArrayList();
                            for (int i = 0; i < devInfo.getRelay().size(); i++) {
                                arrayList.add(devInfo.getRelay().get(i).getDoor_name());
                            }
                            CenterSelectListIntercomDialog.asDefault(false).setTitle(ActIntercom.this.getString(R.string.intercom_choose_gate)).setCancelString(ActIntercom.this.getString(R.string.cancel)).setSelectList(arrayList).setSelectPosition(0).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$3$$ExternalSyntheticLambda0
                                @Override // com.ltech.smarthome.base.IAction
                                public final void act(Object obj) {
                                    ActIntercom.AnonymousClass3.this.lambda$onItemChildClick$0(devInfo, (Integer) obj);
                                }
                            }).showDialog(ActIntercom.this);
                            return;
                        }
                        ActIntercom.this.openDoor(devInfo.getMac(), devInfo.getRelay().get(0).getRelay_id());
                        return;
                    }
                    SmartToast.showCenterShort(ActIntercom.this.getString(R.string.super_key_no));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemChildClick$0(UserInfoResponse.DevInfo devInfo, Integer num) {
            ActIntercom.this.openDoor(devInfo.getMac(), devInfo.getRelay().get(num.intValue()).getRelay_id());
        }
    }

    private void setLayoutManager(int row) {
        PagerGridLayoutManager pagerGridLayoutManager = new PagerGridLayoutManager(row, 1, 0);
        pagerGridLayoutManager.setMillisecondPreInch(50.0f);
        pagerGridLayoutManager.setPagerChangedListener(new PagerGridLayoutManager.PagerChangedListener() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom.4
            @Override // com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.PagerChangedListener
            public void onPagerCountChanged(int pagerCount) {
            }

            @Override // com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.PagerChangedListener
            public void onPagerIndexSelected(int prePagerIndex, int currentPagerIndex) {
                ActIntercom.this.addDots((int) Math.ceil(r5.devInfoAdapter.getItemCount() / 3.0d), currentPagerIndex);
            }
        });
        ((ActIntercomBinding) this.mViewBinding).rvDoor.setLayoutManager(pagerGridLayoutManager);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDots(int numberOfDots, int currentPage) {
        LinearLayout.LayoutParams layoutParams;
        ((ActIntercomBinding) this.mViewBinding).llDoor.removeAllViews();
        for (int i = 0; i < numberOfDots; i++) {
            View view = new View(this);
            if (i == currentPage) {
                view.setBackground(getDotDrawable(R.color.color_text_dark_gray, 20));
                layoutParams = new LinearLayout.LayoutParams(20, 20);
            } else {
                view.setBackground(getDotDrawable(R.color.color_border_gray, 15));
                layoutParams = new LinearLayout.LayoutParams(15, 15);
            }
            layoutParams.setMargins(10, 0, 10, 0);
            view.setLayoutParams(layoutParams);
            ((ActIntercomBinding) this.mViewBinding).llDoor.addView(view);
        }
    }

    private ShapeDrawable getDotDrawable(int color, int size) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setIntrinsicHeight(size);
        shapeDrawable.setIntrinsicWidth(size);
        shapeDrawable.getPaint().setColor(getResources().getColor(color));
        return shapeDrawable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openDoor(String mac, long relay) {
        ((ObservableSubscribeProxy) Injection.net().openDoor(mac, relay).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercom.this.lambda$openDoor$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActIntercom.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActIntercom.this.lambda$openDoor$2(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDoor$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.opening));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDoor$2(Object obj) throws Exception {
        showSuccessDialog(getString(R.string.open_success));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavUtils.destination(ActIntercomSetting.class).withLong(Constants.PLACE_ID, ((ActIntercomVM) this.mViewModel).placeId).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActIntercomVM) this.mViewModel).loadQrcodeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercom.this.lambda$startObserve$3((KeyListResponse.OpenDoorTempKey) obj);
            }
        });
        ((ActIntercomVM) this.mViewModel).foldQrcodeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercom.this.lambda$startObserve$4((Boolean) obj);
            }
        });
        ((ActIntercomVM) this.mViewModel).ftDevInfoListEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercom.this.lambda$startObserve$6((List) obj);
            }
        });
        ((ActIntercomVM) this.mViewModel).bleOpenDoorEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActIntercom.this.lambda$startObserve$7((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(KeyListResponse.OpenDoorTempKey openDoorTempKey) {
        Glide.with((FragmentActivity) this).load(openDoorTempKey.getQrcode_url()).into(((ActIntercomBinding) this.mViewBinding).intercomQrcode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Boolean bool) {
        ((ActIntercomBinding) this.mViewBinding).ivQrcodeGo.setImageResource(bool.booleanValue() ? R.mipmap.ic_fold : R.mipmap.ic_unfold);
        ((ActIntercomBinding) this.mViewBinding).groupQrcode.setVisibility(bool.booleanValue() ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(List list) {
        if (list.size() > 0) {
            ViewGroup.LayoutParams layoutParams = ((ActIntercomBinding) this.mViewBinding).layoutDoor.getLayoutParams();
            int size = list.size();
            if (size == 1) {
                layoutParams.height = SizeUtils.dp2px(60.0f);
                ((ActIntercomBinding) this.mViewBinding).layoutDoor.setLayoutParams(layoutParams);
                setLayoutManager(1);
            } else if (size == 2) {
                layoutParams.height = SizeUtils.dp2px(120.0f);
                ((ActIntercomBinding) this.mViewBinding).layoutDoor.setLayoutParams(layoutParams);
                setLayoutManager(2);
            } else {
                setLayoutManager(3);
            }
            this.devInfoAdapter.setNewData(list);
            if (this.devInfoAdapter.getItemCount() < 4) {
                ((ActIntercomBinding) this.mViewBinding).llDoor.setVisibility(8);
                return;
            } else {
                getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.ActIntercom$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActIntercom.this.lambda$startObserve$5();
                    }
                }, 500L);
                return;
            }
        }
        ViewGroup.LayoutParams layoutParams2 = ((ActIntercomBinding) this.mViewBinding).layoutDoor.getLayoutParams();
        layoutParams2.height = SizeUtils.px2dp(1.0f);
        layoutParams2.width = -1;
        ((ActIntercomBinding) this.mViewBinding).layoutDoor.setLayoutParams(layoutParams2);
        ((ActIntercomBinding) this.mViewBinding).doorLine.setVisibility(8);
        ((ActIntercomBinding) this.mViewBinding).llDoor.setVisibility(8);
        ((ActIntercomBinding) this.mViewBinding).rvDoor.setLayoutManager(new LinearLayoutManager(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5() {
        addDots((int) Math.ceil(this.devInfoAdapter.getItemCount() / 3.0d), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(String str) {
        if (this.isSearching) {
            return;
        }
        setBluetoothSearchView(true);
        this.bleHandler.sendEmptyMessage(123);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBluetoothSearchView(boolean searching) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            if (!defaultAdapter.isEnabled()) {
                SmartToast.showCenterShort(getString(R.string.please_open_bluetooth));
                return;
            }
            this.isSearching = searching;
            ((ActIntercomBinding) this.mViewBinding).layoutBluetooth.setBackground(getResources().getDrawable(searching ? R.mipmap.bg_bluetooth_disable : R.mipmap.bg_bluetooth));
            ((ActIntercomBinding) this.mViewBinding).ivBluetooth.setVisibility(searching ? 4 : 0);
            ((ActIntercomBinding) this.mViewBinding).tvBluetooth.setText(getString(searching ? R.string.opening : R.string.bluetooth_open_door));
            ((ActIntercomBinding) this.mViewBinding).ivSearch.setVisibility(searching ? 0 : 4);
            if (searching) {
                this.bleHandler.sendEmptyMessageDelayed(4567, 31000L);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(((ActIntercomBinding) this.mViewBinding).ivSearch, "rotation", 0.0f, 360.0f);
                this.rotationAnimator = ofFloat;
                ofFloat.setDuration(800L);
                this.rotationAnimator.setRepeatCount(-1);
                this.rotationAnimator.start();
                return;
            }
            ObjectAnimator objectAnimator = this.rotationAnimator;
            if (objectAnimator != null) {
                objectAnimator.end();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPermission() {
        int checkSelfPermission;
        int checkSelfPermission2;
        int checkSelfPermission3;
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        checkSelfPermission = checkSelfPermission(Permission.RECORD_AUDIO);
        if (checkSelfPermission == 0) {
            checkSelfPermission2 = checkSelfPermission(Permission.WRITE_EXTERNAL_STORAGE);
            if (checkSelfPermission2 == 0) {
                checkSelfPermission3 = checkSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS");
                if (checkSelfPermission3 == 0) {
                    return true;
                }
            }
        }
        ActivityCompat.requestPermissions(this, PERMISSIONS, 6666);
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 6666) {
            Injection.intercom().login();
        }
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.bleHandler.removeCallbacksAndMessages(null);
    }
}