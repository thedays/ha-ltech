package com.ltech.smarthome.model.repo;

import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.blankj.utilcode.util.GsonUtils;
import com.iflytek.home.sdk.IFlyHome;
import com.iflytek.home.sdk.callback.ResponseCallback;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.place.AppTokenResponse;
import com.ltech.smarthome.net.response.super_panel.IFlyDeviceDetail;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Response;

/* loaded from: classes4.dex */
public class IFlyRepository {
    private static final String TAG = "IFlyRepository";
    private String deviceID;
    final IFlyHome iFlyHome = IFlyHome.INSTANCE;
    private LifecycleOwner owner;
    private long placeid;

    public void initIFly(Context context) {
        if (context != null) {
            IFlyHome.INSTANCE.init(context, Constants.APP_ID, IFlyHome.LoginWay.CUSTOM_TOKEN);
        }
    }

    public void getAppToken(Context context, LifecycleOwner owner, final long placeid) {
        this.owner = owner;
        this.placeid = placeid;
        initIFly(context);
        AppTokenResponse appTokenResponse = (AppTokenResponse) SharedPreferenceUtil.getBean(String.valueOf(placeid), AppTokenResponse.class);
        if (appTokenResponse != null && appTokenResponse.getCreated_at() + appTokenResponse.getExpires_in() > System.currentTimeMillis() / 1000) {
            setToken(appTokenResponse);
        } else {
            ((ObservableSubscribeProxy) Injection.net().getAppToken(placeid).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.model.repo.IFlyRepository$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IFlyRepository.this.lambda$getAppToken$0(placeid, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getAppToken$0(long j, Object obj) throws Exception {
        AppTokenResponse appTokenResponse = (AppTokenResponse) GsonUtils.getGson().fromJson(obj.toString().trim(), AppTokenResponse.class);
        SharedPreferenceUtil.edit().putBean(String.valueOf(j), appTokenResponse);
        setToken(appTokenResponse);
    }

    public void setToken(AppTokenResponse token) {
        if (IFlyHome.INSTANCE.isReady()) {
            this.iFlyHome.setCustomToken(token.getAccess_token());
            oauth(this.owner, token.getAccess_token(), this.placeid);
        }
    }

    public void oauth(LifecycleOwner owner, String token, long placeid) {
        LHomeLog.d(getClass(), "oauth: " + placeid + "token: " + token);
        ((ObservableSubscribeProxy) Injection.net().oauth(placeid, token).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.model.repo.IFlyRepository$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                IFlyRepository.this.lambda$oauth$1(obj);
            }
        }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.model.repo.IFlyRepository.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                super.action(throwable);
                LHomeLog.d(getClass(), "action: " + throwable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$oauth$1(Object obj) throws Exception {
        LHomeLog.d(getClass(), "oauth: " + obj.toString());
    }

    public void getDeviceDetail(final String deviceID) {
        this.deviceID = deviceID;
        this.iFlyHome.getDeviceDetail(deviceID, new ResponseCallback(this) { // from class: com.ltech.smarthome.model.repo.IFlyRepository.2
            @Override // com.iflytek.home.sdk.callback.ResponseCallback
            public void onResponse(Response<String> response) {
                if (response.isSuccessful()) {
                    IFlyDeviceDetail iFlyDeviceDetail = (IFlyDeviceDetail) GsonUtils.getGson().fromJson(response.body(), IFlyDeviceDetail.class);
                    iFlyDeviceDetail.setContinous_mode(true);
                    SharedPreferenceUtil.edit().putBean(deviceID, Boolean.valueOf(iFlyDeviceDetail.isContinous_mode()));
                    LHomeLog.d(getClass(), "onResponse: " + response.body());
                    return;
                }
                LHomeLog.d(getClass(), "onResponse fail: " + GsonUtils.getGson().toJson(response.errorBody().toString().trim()));
            }

            @Override // com.iflytek.home.sdk.callback.ResponseCallback
            public void onFailure(Call<String> call, Throwable throwable) {
                LHomeLog.d(getClass(), "onFailure: ");
            }
        });
    }

    private void updateDeviceInfo(boolean isChecked) {
        IFlyHome.INSTANCE.updateDeviceInfo("000d9d32-b027-410d-981d-fead0b8cbddd." + this.deviceID, null, null, Boolean.valueOf(isChecked), null, new ResponseCallback(this) { // from class: com.ltech.smarthome.model.repo.IFlyRepository.3
            @Override // com.iflytek.home.sdk.callback.ResponseCallback
            public void onFailure(Call<String> call, Throwable throwable) {
            }

            @Override // com.iflytek.home.sdk.callback.ResponseCallback
            public void onResponse(Response<String> response) {
                LHomeLog.d(getClass(), "updateDeviceInfo onResponse: " + response.body());
            }
        });
    }
}