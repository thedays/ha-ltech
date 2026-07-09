package com.ltech.smarthome.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.ui.permission.ActGetMeshPermission;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.NormalDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseNormalFragment<V extends ViewDataBinding> extends Fragment {
    private Observer<LiveBusHelper> mBusHelperObserver = new Observer() { // from class: com.ltech.smarthome.base.BaseNormalFragment$$ExternalSyntheticLambda2
        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            BaseNormalFragment.this.handleBusEvent((LiveBusHelper) obj);
        }
    };
    private NormalDialog mChoiceDialog;
    protected Gloading.Holder mHolder;
    protected V mViewBinding;

    protected void handleBusEvent(LiveBusHelper helper) {
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected boolean isRootViewClickEnable() {
        return true;
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

    protected abstract int provideLayoutId();

    public void refreshData() {
    }

    protected void startObserve() {
    }

    protected boolean useEventBus() {
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.mViewBinding == null) {
            this.mViewBinding = (V) DataBindingUtil.inflate(layoutInflater, provideLayoutId(), viewGroup, false);
        }
        this.mViewBinding.setLifecycleOwner(this);
        View findViewById = this.mViewBinding.getRoot().findViewById(layoutLoadId());
        if (findViewById != null) {
            Gloading.Holder withEmpty = createGLoading().wrap(findViewById).withRetry(new Runnable() { // from class: com.ltech.smarthome.base.BaseNormalFragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BaseNormalFragment.this.onRetry();
                }
            }).withEmpty(new Runnable() { // from class: com.ltech.smarthome.base.BaseNormalFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BaseNormalFragment.this.onEmptyTry();
                }
            });
            this.mHolder = withEmpty;
            withEmpty.enableRootViewClick(isRootViewClickEnable());
            return this.mViewBinding.getRoot();
        }
        Gloading.Holder withEmpty2 = createGLoading().wrap(this.mViewBinding.getRoot()).withRetry(new Runnable() { // from class: com.ltech.smarthome.base.BaseNormalFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseNormalFragment.this.onRetry();
            }
        }).withEmpty(new Runnable() { // from class: com.ltech.smarthome.base.BaseNormalFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BaseNormalFragment.this.onEmptyTry();
            }
        });
        this.mHolder = withEmpty2;
        withEmpty2.enableRootViewClick(isRootViewClickEnable());
        return this.mHolder.getWrapper();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewCreated();
        initData();
        initView();
        registerEventBus();
        startObserve();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        unregisterEventBus();
        V v = this.mViewBinding;
        if (v != null) {
            v.unbind();
            this.mViewBinding = null;
        }
    }

    protected void registerEventBus() {
        if (useEventBus()) {
            EventBusUtils.register(this, this.mBusHelperObserver);
        }
    }

    protected void unregisterEventBus() {
        if (useEventBus()) {
            EventBusUtils.unregister(this.mBusHelperObserver);
        }
    }

    protected void back() {
        ((BaseNormalActivity) getActivity()).back();
    }

    protected void dismissLoadingDialog() {
        ((BaseNormalActivity) getActivity()).dismissLoadingDialog();
    }

    protected void showLoadingDialog(String content) {
        ((BaseNormalActivity) getActivity()).showLoadingDialog(content);
    }

    protected void showSuccessDialog(String content) {
        ((BaseNormalActivity) getActivity()).showSuccessDialog(content);
    }

    protected void showErrorDialog(String content) {
        ((BaseNormalActivity) getActivity()).showErrorDialog(content);
    }

    protected <T> void handleData(LiveData<Resource<List<T>>> liveData, final IAction<List<T>> action) {
        liveData.observe(this, new Observer() { // from class: com.ltech.smarthome.base.BaseNormalFragment$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseNormalFragment.this.lambda$handleData$0(action, (Resource) obj);
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
        if (resource.isLoading()) {
            showLoading();
            return;
        }
        if (resource.isError() && resource.getData().isEmpty()) {
            if (resource.getData().isEmpty()) {
                showEmpty();
                return;
            } else {
                showError();
                return;
            }
        }
        if (resource.isSuccess()) {
            if (action != null) {
                action.act(resource.getData());
            }
        } else {
            if (resource.getData().isEmpty()) {
                showEmpty();
                return;
            }
            showContent();
            if (action != null) {
                action.act(resource.getData());
            }
        }
    }

    protected Gloading createGLoading() {
        return Gloading.getDefault();
    }

    protected void showLoading() {
        this.mHolder.showLoading();
    }

    protected void showContent() {
        this.mHolder.showLoadSuccess();
    }

    protected void showEmpty() {
        this.mHolder.showEmpty();
    }

    protected void showError() {
        this.mHolder.showLoadFailed();
    }

    public void showLoadingDialog(String content, int timeOut) {
        ((BaseNormalActivity) getActivity()).showLoadingDialog(content, timeOut);
    }

    protected boolean isBlePermissionOk() {
        if (ActivityUtils.getTopActivity() == null) {
            return false;
        }
        return (Build.VERSION.SDK_INT < 23 || Build.VERSION.SDK_INT >= 31) ? Build.VERSION.SDK_INT < 31 || AndPermission.hasPermissions(ActivityUtils.getTopActivity(), "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE") : AndPermission.hasPermissions(ActivityUtils.getTopActivity(), Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION);
    }

    protected boolean bleIsOk() {
        if (ActivityUtils.getTopActivity() == null) {
            return false;
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
        } else if (Build.VERSION.SDK_INT >= 31 && !AndPermission.hasPermissions(ActivityUtils.getTopActivity(), "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE")) {
            showPermissionActivity();
            return false;
        }
        if (Injection.state().isBluetoothEnabled()) {
            return true;
        }
        ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 0);
        return false;
    }

    /* renamed from: com.ltech.smarthome.base.BaseNormalFragment$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SharedPreferenceUtil.edit().keepShared(Constants.PHONE_LOCATION_PERMISSION_HOME_PAGE_NEED_SHOW, false).commit();
            ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(ActGetMeshPermission.class, new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.base.BaseNormalFragment$1$$ExternalSyntheticLambda0
                @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
                public final void onActivityResult(int i, Intent intent) {
                    BaseNormalFragment.AnonymousClass1.this.lambda$run$0(i, intent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(int i, Intent intent) {
            if (i == 1002 || i != 1003 || ActivityUtils.getTopActivity().getClass().getName().equals("com.ltech.smarthome.ui.control")) {
                return;
            }
            BaseNormalFragment.this.getPermissionFail();
        }
    }

    private void showPermissionActivity() {
        ThreadUtils.runOnUiThread(new AnonymousClass1());
    }

    private void showResetDialog() {
        if (this.mChoiceDialog == null) {
            NormalDialog normalDialog = new NormalDialog(ActivityUtils.getTopActivity(), R.style.MyDialog);
            this.mChoiceDialog = normalDialog;
            normalDialog.setYesOnclickListener(ActivityUtils.getTopActivity().getResources().getString(R.string.go_to_setting), new NormalDialog.onYesOnclickListener() { // from class: com.ltech.smarthome.base.BaseNormalFragment.2
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onYesOnclickListener
                public void onYesOnclick() {
                    ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 666);
                    BaseNormalFragment.this.mChoiceDialog.dismiss();
                }
            });
            this.mChoiceDialog.setNoOnclickListener(ActivityUtils.getTopActivity().getResources().getString(R.string.no_open), new NormalDialog.onNoOnclickListener() { // from class: com.ltech.smarthome.base.BaseNormalFragment.3
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onNoOnclickListener
                public void onNoClick() {
                    if (!ActivityUtils.getTopActivity().getClass().getName().equals("com.ltech.smarthome.ui.control")) {
                        BaseNormalFragment.this.getPermissionFail();
                    }
                    BaseNormalFragment.this.mChoiceDialog.dismiss();
                }
            });
        }
        if (this.mChoiceDialog.isShowing()) {
            return;
        }
        this.mChoiceDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPermissionFail() {
        SmartToast.showShort(R.string.permission_deny);
        if (getActivity() != null) {
            getActivity().setResult(1003);
        }
        ActivityCompat.finishAfterTransition(getActivity());
    }
}