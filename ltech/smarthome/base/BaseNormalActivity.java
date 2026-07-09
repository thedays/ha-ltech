package com.ltech.smarthome.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.jaeger.library.StatusBarUtil;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.LayoutTitleDefaultBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.permission.ActGetMeshPermission;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.LifecycleHandler;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.view.dialog.NormalDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.v3.MessageDialog;
import com.smart.dialog.v3.TipDialog;
import com.smart.dialog.v3.WaitDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.List;
import me.jessyan.autosize.AutoSizeCompat;

/* loaded from: classes3.dex */
public abstract class BaseNormalActivity<V extends ViewDataBinding> extends AppCompatActivity {
    protected BaseNormalActivity activity;
    private long exitTime;
    private Observer<LiveBusHelper> mBusHelperObserver = new Observer() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda14
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            BaseNormalActivity.this.handleBusEvent((LiveBusHelper) obj);
        }
    };
    private NormalDialog mChoiceDialog;
    private Gloading.Holder mHolder;
    private TitleDefault mTitleBar;
    protected V mViewBinding;
    private LifecycleHandler mainHandler;

    static /* synthetic */ boolean lambda$showNoPermissionDialog$9(com.smart.dialog.util.BaseDialog baseDialog, View view) {
        return false;
    }

    protected void check() {
    }

    protected void edit() {
    }

    protected void handleBusEvent(LiveBusHelper helper) {
    }

    protected boolean hideGloading() {
        return false;
    }

    protected void initView() {
    }

    protected boolean isNeedAddMarginTopEqualStatusBar() {
        return true;
    }

    protected boolean isRootViewClickEnable() {
        return true;
    }

    protected boolean isSupportExit() {
        return false;
    }

    protected int layoutLoadId() {
        return R.id.layout_load;
    }

    protected void onEmptyTry() {
    }

    protected void onRetry() {
    }

    protected void onViewCreated() {
    }

    protected void preInit() {
    }

    protected abstract int provideLayoutId();

    protected void startObserve() {
    }

    protected void titleClick() {
    }

    protected boolean useEventBus() {
        return false;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        View findViewById;
        super.onCreate(bundle);
        getWindow().setSoftInputMode(2);
        this.activity = this;
        preInit();
        if (this.mViewBinding == null) {
            this.mViewBinding = (V) DataBindingUtil.setContentView(this, provideLayoutId());
        }
        this.mViewBinding.setLifecycleOwner(this);
        setStatusBar();
        onViewCreated();
        initView();
        registerEventBus();
        startObserve();
        if (isNeedAddMarginTopEqualStatusBar()) {
            BarUtils.addMarginTopEqualStatusBarHeight(this.mViewBinding.getRoot());
            BarUtils.setNavBarVisibility(getWindow(), false);
        }
        if (LanguageUtils.isChinese(this) || (findViewById = this.mViewBinding.getRoot().findViewById(R.id.title)) == null) {
            return;
        }
        try {
            LayoutTitleDefaultBinding layoutTitleDefaultBinding = (LayoutTitleDefaultBinding) DataBindingUtil.bind(findViewById);
            if (layoutTitleDefaultBinding != null) {
                if (!TextUtils.isEmpty(getTitleBar().getBackString())) {
                    ((ConstraintLayout.LayoutParams) layoutTitleDefaultBinding.tvTitle.getLayoutParams()).startToEnd = layoutTitleDefaultBinding.tvBack.getId();
                }
                if (TextUtils.isEmpty(getTitleBar().getEditString())) {
                    return;
                }
                ((ConstraintLayout.LayoutParams) layoutTitleDefaultBinding.tvTitle.getLayoutParams()).endToStart = layoutTitleDefaultBinding.tvEdit.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
        V v = this.mViewBinding;
        if (v != null) {
            v.unbind();
            this.mViewBinding = null;
        }
        this.mainHandler = null;
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, -1);
    }

    private void registerEventBus() {
        if (useEventBus()) {
            EventBusUtils.register(this, this.mBusHelperObserver);
        }
    }

    private void unregisterEventBus() {
        if (useEventBus()) {
            EventBusUtils.unregister(this.mBusHelperObserver);
        }
    }

    public Handler getMainHandler() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (this.mainHandler == null) {
                this.mainHandler = new LifecycleHandler(Looper.getMainLooper(), this);
            }
            return this.mainHandler;
        }
        return ThreadUtils.getMainHandler();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (!isSupportExit()) {
            back();
            return;
        }
        if (System.currentTimeMillis() - this.exitTime > 2000) {
            SmartToast.showShort(R.string.press_to_exit);
            this.exitTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.setFlags(268435456);
            startActivity(intent);
        }
    }

    protected TitleDefault getTitleBar() {
        if (this.mTitleBar == null) {
            TitleDefault titleDefault = new TitleDefault();
            this.mTitleBar = titleDefault;
            titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda8
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    BaseNormalActivity.this.back();
                }
            }));
            this.mTitleBar.setEditAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda9
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    BaseNormalActivity.this.edit();
                }
            }));
            this.mTitleBar.setTitleAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda10
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    BaseNormalActivity.this.titleClick();
                }
            }));
        }
        return this.mTitleBar;
    }

    protected void setTitleView() {
        V v = this.mViewBinding;
        if (v != null) {
            v.setVariable(83, this.mTitleBar);
        }
    }

    protected void setTitle(String title) {
        getTitleBar().setTitle(title);
        setTitleView();
    }

    protected void setSubTitle(String title) {
        getTitleBar().setSubTitle(title);
        setTitleView();
    }

    protected void setBackImage(int res) {
        getTitleBar().setBackImageRes(res);
        setTitleView();
    }

    protected void setEditImage(int res) {
        getTitleBar().setEditImageRes(res);
        setTitleView();
    }

    protected void setBackString(String editString) {
        getTitleBar().setBackString(editString);
        setTitleView();
    }

    protected void setEditString(String editString) {
        getTitleBar().setEditString(editString);
        setTitleView();
    }

    protected void back() {
        supportFinishAfterTransition();
    }

    public void finishActivity() {
        finishActivity((NavUtils.Builder) null);
    }

    protected void finishActivity(NavUtils.Builder builder) {
        dismissLoadingDialog();
        if (builder != null) {
            builder.setResult(this);
        }
        back();
    }

    public void showLoadingDialog(String content) {
        WaitDialog.show(this, content);
    }

    public void dismissLoadingDialog() {
        WaitDialog.dismiss(500);
    }

    public void showSuccessDialog(String content) {
        TipDialog.show(this, content, TipDialog.TYPE.SUCCESS);
    }

    public void showErrorDialog(String content) {
        TipDialog.show(this, content, TipDialog.TYPE.ERROR);
    }

    protected <T> void handleData(LiveData<Resource<List<T>>> liveData, final IAction<List<T>> action) {
        liveData.observe(this, new Observer() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseNormalActivity.this.lambda$handleData$0(action, (Resource) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleData$0(IAction iAction, Resource resource) {
        if (resource.isLoading()) {
            showLoading();
            return;
        }
        if (resource.isError() && ((List) resource.getData()).isEmpty()) {
            if (((List) resource.getData()).isEmpty()) {
                showEmpty();
                return;
            } else {
                showError();
                return;
            }
        }
        if (resource.isSuccess()) {
            if (((List) resource.getData()).isEmpty()) {
                if (iAction != null) {
                    iAction.act((List) resource.getData());
                }
                showEmpty();
                return;
            } else {
                showContent();
                if (iAction != null) {
                    iAction.act((List) resource.getData());
                    return;
                }
                return;
            }
        }
        if (((List) resource.getData()).isEmpty()) {
            showEmpty();
            return;
        }
        showContent();
        if (iAction != null) {
            iAction.act((List) resource.getData());
        }
    }

    protected <T> void handleData(Listing<T> liveData, IAction<List<T>> action) {
        handleData(liveData.data(), action);
    }

    protected <T> void handleResource(Resource<List<T>> resource, IAction<List<T>> action) {
        if (resource.isLoading() && !hideGloading()) {
            showLoading();
            return;
        }
        if (resource.isError()) {
            showError();
        } else {
            if (!resource.isSuccess() || action == null) {
                return;
            }
            action.act(resource.getData());
        }
    }

    protected void initLoadingStatusViewIfNeed() {
        if (this.mHolder == null) {
            View findViewById = this.mViewBinding.getRoot().findViewById(layoutLoadId());
            if (findViewById != null) {
                this.mHolder = createGLoading().wrap(findViewById);
            } else {
                this.mHolder = createGLoading().wrap(this);
            }
            this.mHolder.withRetry(new Runnable() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    BaseNormalActivity.this.onRetry();
                }
            }).withEmpty(new Runnable() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    BaseNormalActivity.this.onEmptyTry();
                }
            });
            this.mHolder.enableRootViewClick(isRootViewClickEnable());
        }
    }

    protected Gloading createGLoading() {
        return Gloading.getDefault();
    }

    protected void showLoading() {
        initLoadingStatusViewIfNeed();
        this.mHolder.showLoading();
    }

    protected void showContent() {
        initLoadingStatusViewIfNeed();
        this.mHolder.showLoadSuccess();
    }

    protected void showEmpty() {
        initLoadingStatusViewIfNeed();
        this.mHolder.showEmpty();
    }

    protected void showError() {
        initLoadingStatusViewIfNeed();
        this.mHolder.showLoadFailed();
    }

    protected void showLoadingState(int status) {
        initLoadingStatusViewIfNeed();
        this.mHolder.showLoadingStatus(status);
    }

    public void showLoadingDialog(String content, int timeOut) {
        WaitDialog.show(this, content, timeOut);
    }

    public boolean isNetworkConnected() {
        return ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources());
            AutoSizeCompat.autoConvertDensity(super.getResources(), 812.0f, false);
        }
        return super.getResources();
    }

    public void showAsDialog(float heightRate) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            window.setGravity(80);
            attributes.width = ScreenUtils.getScreenWidth() - (SizeUtils.dp2px(16.0f) * 2);
            if (heightRate > 0.0f) {
                attributes.height = (int) (ScreenUtils.getScreenHeight() * heightRate);
            } else {
                attributes.height = -2;
            }
            attributes.y = SizeUtils.dp2px(10.0f) + getNavigationBarHeight();
            window.setAttributes(attributes);
        }
        setFinishOnTouchOutside(false);
    }

    public int getNavigationBarHeight() {
        int identifier = getResources().getIdentifier("navigation_bar_height", "dimen", DispatchConstants.ANDROID);
        if (identifier > 0) {
            return getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    protected boolean isBlePermissionOk() {
        return (Build.VERSION.SDK_INT < 23 || Build.VERSION.SDK_INT >= 31) ? Build.VERSION.SDK_INT < 31 || AndPermission.hasPermissions(ActivityUtils.getTopActivity(), "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT") : AndPermission.hasPermissions(ActivityUtils.getTopActivity(), Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION);
    }

    public boolean bleIsOk() {
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

    /* renamed from: com.ltech.smarthome.base.BaseNormalActivity$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SharedPreferenceUtil.edit().keepShared(Constants.PHONE_LOCATION_PERMISSION_HOME_PAGE_NEED_SHOW, false).commit();
            ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(ActGetMeshPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.base.BaseNormalActivity$1$$ExternalSyntheticLambda0
                @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
                public final void onActivityResult(int i, Intent intent) {
                    BaseNormalActivity.AnonymousClass1.this.lambda$run$0(i, intent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(int i, Intent intent) {
            if (i == 1002 || i != 1003 || ActivityUtils.getTopActivity().getClass().getName().equals("com.ltech.smarthome.ui.control")) {
                return;
            }
            BaseNormalActivity.this.getPermissionFail();
        }
    }

    private void showPermissionActivity() {
        ThreadUtils.runOnUiThread(new AnonymousClass1());
    }

    private void showResetDialog() {
        if (this.mChoiceDialog == null) {
            NormalDialog normalDialog = new NormalDialog(ActivityUtils.getTopActivity(), R.style.MyDialog);
            this.mChoiceDialog = normalDialog;
            normalDialog.setYesOnclickListener(ActivityUtils.getTopActivity().getString(R.string.go_to_setting), new NormalDialog.onYesOnclickListener() { // from class: com.ltech.smarthome.base.BaseNormalActivity.2
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onYesOnclickListener
                public void onYesOnclick() {
                    ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 666);
                    BaseNormalActivity.this.mChoiceDialog.dismiss();
                }
            });
            this.mChoiceDialog.setNoOnclickListener(ActivityUtils.getTopActivity().getString(R.string.no_open), new NormalDialog.onNoOnclickListener() { // from class: com.ltech.smarthome.base.BaseNormalActivity.3
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onNoOnclickListener
                public void onNoClick() {
                    if (!ActivityUtils.getTopActivity().getClass().getName().equals("com.ltech.smarthome.ui.control")) {
                        BaseNormalActivity.this.getPermissionFail();
                    }
                    BaseNormalActivity.this.mChoiceDialog.dismiss();
                }
            });
        }
        if (this.mChoiceDialog.isShowing()) {
            return;
        }
        this.mChoiceDialog.show();
    }

    public void requestPermission(final String[] permissions) {
        if (Utils.isYYBFlavor(this)) {
            MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.call_location_permission), getString(R.string.call_location_permission_tip)).setOkButton(getString(R.string.i_know), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda3
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(com.smart.dialog.util.BaseDialog baseDialog, View view) {
                    boolean lambda$requestPermission$4;
                    lambda$requestPermission$4 = BaseNormalActivity.this.lambda$requestPermission$4(permissions, baseDialog, view);
                    return lambda$requestPermission$4;
                }
            }).setCancelable(false);
        } else {
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    BaseNormalActivity.this.lambda$requestPermission$7(permissions);
                }
            }, 300L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPermission$3(String[] strArr) {
        AndPermission.with((Activity) this.activity).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda11
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                BaseNormalActivity.this.lambda$requestPermission$1((List) obj);
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda12
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                BaseNormalActivity.this.lambda$requestPermission$2((List) obj);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$requestPermission$4(final String[] strArr, com.smart.dialog.util.BaseDialog baseDialog, View view) {
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                BaseNormalActivity.this.lambda$requestPermission$3(strArr);
            }
        }, 300L);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPermission$1(List list) {
        check();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPermission$2(List list) {
        getPermissionFail();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPermission$7(String[] strArr) {
        AndPermission.with((Activity) this.activity).runtime().permission(strArr).onGranted(new Action() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda15
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                BaseNormalActivity.this.lambda$requestPermission$5((List) obj);
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda16
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                BaseNormalActivity.this.lambda$requestPermission$6((List) obj);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPermission$5(List list) {
        check();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestPermission$6(List list) {
        getPermissionFail();
    }

    protected void getPermissionFail() {
        SmartToast.showShort(R.string.permission_deny);
        setResult(1003);
        finishAfterTransition();
    }

    protected void getGpsPermissionFail() {
        SmartToast.showShort(R.string.gps_close);
        setResult(1003);
        finishAfterTransition();
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001e, code lost:
    
        if (r1 == 0) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean checkWriteStoragePermission(int r7) {
        /*
            r6 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 33
            r2 = 1
            r3 = 0
            if (r0 < r1) goto L2e
            java.lang.String r0 = "android.permission.READ_MEDIA_AUDIO"
            int r1 = com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0.m(r6, r0)
            java.lang.String r4 = "android.permission.READ_MEDIA_VIDEO"
            java.lang.String r5 = "android.permission.READ_MEDIA_IMAGES"
            if (r1 != 0) goto L21
            int r1 = com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0.m(r6, r5)
            if (r1 != 0) goto L21
            int r1 = com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0.m(r6, r4)
            if (r1 != 0) goto L21
            goto L22
        L21:
            r2 = 0
        L22:
            if (r2 != 0) goto L2d
            com.ltech.smarthome.base.BaseNormalActivity r1 = r6.activity
            java.lang.String[] r0 = new java.lang.String[]{r0, r5, r4}
            androidx.core.app.ActivityCompat.requestPermissions(r1, r0, r7)
        L2d:
            return r2
        L2e:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 23
            if (r0 < r1) goto L4a
            java.lang.String r0 = "android.permission.WRITE_EXTERNAL_STORAGE"
            int r1 = com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0.m(r6, r0)
            if (r1 != 0) goto L3d
            goto L3e
        L3d:
            r2 = 0
        L3e:
            if (r2 != 0) goto L49
            com.ltech.smarthome.base.BaseNormalActivity r1 = r6.activity
            java.lang.String[] r0 = new java.lang.String[]{r0}
            androidx.core.app.ActivityCompat.requestPermissions(r1, r0, r7)
        L49:
            return r2
        L4a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.base.BaseNormalActivity.checkWriteStoragePermission(int):boolean");
    }

    public boolean checkPhotoPermission(int requestCode) {
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            checkSelfPermission = checkSelfPermission(Permission.CAMERA);
            if (checkSelfPermission != 0) {
                ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, requestCode);
                return false;
            }
        }
        return true;
    }

    public class BlueToothValueReceiver extends BroadcastReceiver {
        public static final int DEFAULT_VALUE_BULUETOOTH = 1000;

        public BlueToothValueReceiver(final BaseNormalActivity this$0) {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 1000)) {
                    case 10:
                        LogUtils.e("蓝牙已关闭");
                        break;
                    case 11:
                        LogUtils.e("正在打开蓝牙");
                        break;
                    case 12:
                        LogUtils.e("蓝牙已打开");
                        break;
                    case 13:
                        LogUtils.e("正在关闭蓝牙");
                        Injection.mesh().stopScan();
                        break;
                    default:
                        LogUtils.e("未知状态");
                        break;
                }
            }
        }
    }

    protected void showNeedSaveDialog() {
        MessageDialog.show(this, "", getString(R.string.need_save_action)).setOkButton(getString(R.string.ir_exit), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(com.smart.dialog.util.BaseDialog baseDialog, View view) {
                boolean lambda$showNeedSaveDialog$8;
                lambda$showNeedSaveDialog$8 = BaseNormalActivity.this.lambda$showNeedSaveDialog$8(baseDialog, view);
                return lambda$showNeedSaveDialog$8;
            }
        }).setCancelButton(getString(R.string.cancel)).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showNeedSaveDialog$8(com.smart.dialog.util.BaseDialog baseDialog, View view) {
        supportFinishAfterTransition();
        return false;
    }

    protected void showNoSuperPanelDialog() {
        MessageDialog.show(this, getString(R.string.app_str_operation_failure), getString(R.string.app_str_smart_panel_set_night_mode_no_super_panel_tip)).setOkButton(getString(R.string.ok));
    }

    protected boolean isOwnerOrManager() {
        Place selPlace = Injection.repo().home().getSelPlace();
        if (selPlace != null) {
            return selPlace.isOwner() || selPlace.isManager();
        }
        return false;
    }

    protected void showNoPermissionDialog() {
        MessageDialog.show(this, getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.base.BaseNormalActivity$$ExternalSyntheticLambda5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(com.smart.dialog.util.BaseDialog baseDialog, View view) {
                return BaseNormalActivity.lambda$showNoPermissionDialog$9(baseDialog, view);
            }
        });
    }

    private void addMarginTopEqualStatusBarHeight(View view) {
        view.setTag("TAG_OFFSET_BOTTOM");
        Object tag = view.getTag(-123);
        if (tag == null || !((Boolean) tag).booleanValue()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin + BarUtils.getNavBarHeight());
            view.setTag(-123, true);
        }
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v == null) {
            return false;
        }
        int[] iArr = {0, 0};
        v.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        return event.getX() <= ((float) i) || event.getX() >= ((float) (v.getWidth() + i)) || event.getY() <= ((float) i2) || event.getY() >= ((float) (v.getHeight() + i2));
    }
}