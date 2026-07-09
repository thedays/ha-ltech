package com.ltech.smarthome.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.utils.LifecycleHandler;
import com.ltech.smarthome.utils.NavUtils;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public class BaseViewModel extends ViewModel implements IBaseViewModel {
    static final int CONTENT = 1;
    static final int EMPTY = 2;
    static final int ERROR = 3;
    static final int LOADING = 0;
    private LifecycleOwner mLifecycleOwner;
    private LifecycleHandler mainHandler;
    private Bundle resultBundle;
    private int resultCode = 0;
    private UIChangeLiveData uc;

    public interface ParameterField {
        public static final String INTENT_BUILDER = "intent_builder";
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "owner");
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "owner");
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* synthetic */ void onStart(LifecycleOwner lifecycleOwner) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "owner");
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "owner");
    }

    public LifecycleOwner getLifecycleOwner() {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        return lifecycleOwner == null ? (AppCompatActivity) ActivityUtils.getTopActivity() : lifecycleOwner;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onCreate(LifecycleOwner owner) {
        this.mLifecycleOwner = owner;
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        this.mainHandler = null;
        this.mLifecycleOwner = null;
    }

    public Handler getMainHandler() {
        if (this.mainHandler == null) {
            this.mainHandler = new LifecycleHandler(Looper.getMainLooper(), getLifecycleOwner());
        }
        return this.mainHandler;
    }

    public UIChangeLiveData getUC() {
        if (this.uc == null) {
            this.uc = new UIChangeLiveData(this);
        }
        return this.uc;
    }

    public void showLoadingDialog(String content) {
        this.uc.showLoadingDialogEvent.postValue(content);
    }

    public void showLoadingDialog() {
        this.uc.showLoadingDialogEvent.postValue("");
    }

    public void dismissLoadingDialog() {
        this.uc.dismissLoadingDialogEvent.call();
    }

    public void showSuccessTipDialog(String content) {
        this.uc.showSuccessDialogEvent.postValue(content);
    }

    public void showErrorTipDialog(String content) {
        this.uc.showErrorDialogEvent.postValue(content);
    }

    public void navigation(NavUtils.Builder builder) {
        HashMap hashMap = new HashMap(2);
        hashMap.put(ParameterField.INTENT_BUILDER, builder);
        this.uc.startActivityEvent.postValue(hashMap);
    }

    public void finishActivity() {
        finishActivity(this.resultCode, this.resultBundle);
    }

    public void finishActivity(int resultCode, Bundle bundle) {
        this.resultCode = resultCode;
        this.resultBundle = bundle;
        callFinishEvent();
    }

    private void callFinishEvent() {
        HashMap hashMap = new HashMap(2);
        hashMap.put(ParameterField.INTENT_BUILDER, NavUtils.result().withResultCode(this.resultCode).withBundle(this.resultBundle));
        this.uc.finishEvent.postValue(hashMap);
    }

    protected void setResult(int code) {
        synchronized (this) {
            this.resultCode = code;
        }
    }

    protected void setResultBundle(Bundle bundle) {
        synchronized (this) {
            this.resultBundle = bundle;
        }
    }

    protected Context getContext() {
        if (ActivityUtils.getTopActivity() != null) {
            return ActivityUtils.getTopActivity();
        }
        return Utils.getApp();
    }

    public void onBackPressed() {
        this.uc.onBackPressedEvent.call();
    }

    protected void showLoading() {
        this.uc.stateEvent.setValue(0);
    }

    protected void showContent() {
        this.uc.stateEvent.setValue(1);
    }

    protected void showEmpty() {
        this.uc.stateEvent.setValue(2);
    }

    protected void showError() {
        this.uc.stateEvent.setValue(3);
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<Integer> dismissLoadingDialogEvent;
        private SingleLiveEvent<Map<String, Object>> finishEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;
        private SingleLiveEvent<String> showErrorDialogEvent;
        private SingleLiveEvent<String> showLoadingDialogEvent;
        private SingleLiveEvent<String> showSuccessDialogEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
        private SingleLiveEvent<Integer> stateEvent;

        public UIChangeLiveData(final BaseViewModel this$0) {
        }

        public SingleLiveEvent<String> getShowLoadingDialogEvent() {
            SingleLiveEvent<String> createLiveData = createLiveData(this.showLoadingDialogEvent);
            this.showLoadingDialogEvent = createLiveData;
            return createLiveData;
        }

        public SingleLiveEvent<String> getShowSuccessDialogEvent() {
            SingleLiveEvent<String> createLiveData = createLiveData(this.showSuccessDialogEvent);
            this.showSuccessDialogEvent = createLiveData;
            return createLiveData;
        }

        public SingleLiveEvent<String> getShowErrorialogEvent() {
            SingleLiveEvent<String> createLiveData = createLiveData(this.showErrorDialogEvent);
            this.showErrorDialogEvent = createLiveData;
            return createLiveData;
        }

        public SingleLiveEvent<Integer> getDismissLoadingDialogEvent() {
            SingleLiveEvent<Integer> createLiveData = createLiveData(this.dismissLoadingDialogEvent);
            this.dismissLoadingDialogEvent = createLiveData;
            return createLiveData;
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            SingleLiveEvent<Map<String, Object>> createLiveData = createLiveData(this.startActivityEvent);
            this.startActivityEvent = createLiveData;
            return createLiveData;
        }

        public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
            SingleLiveEvent<Map<String, Object>> createLiveData = createLiveData(this.startContainerActivityEvent);
            this.startContainerActivityEvent = createLiveData;
            return createLiveData;
        }

        public SingleLiveEvent<Map<String, Object>> getFinishEvent() {
            SingleLiveEvent<Map<String, Object>> createLiveData = createLiveData(this.finishEvent);
            this.finishEvent = createLiveData;
            return createLiveData;
        }

        public SingleLiveEvent<Void> getOnBackPressedEvent() {
            SingleLiveEvent<Void> createLiveData = createLiveData(this.onBackPressedEvent);
            this.onBackPressedEvent = createLiveData;
            return createLiveData;
        }

        public SingleLiveEvent<Integer> getStateEvent() {
            SingleLiveEvent<Integer> createLiveData = createLiveData(this.stateEvent);
            this.stateEvent = createLiveData;
            return createLiveData;
        }

        private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
            return liveData == null ? new SingleLiveEvent<>() : liveData;
        }

        @Override // com.ltech.smarthome.base.SingleLiveEvent, androidx.lifecycle.LiveData
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }
}