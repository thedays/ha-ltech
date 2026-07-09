package com.ltech.smarthome.push;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.os.Build;
import androidx.core.internal.view.SupportMenu;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.huawei.HuaWeiRegister;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.GcmRegister;
import com.alibaba.sdk.android.push.register.MeizuRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.alibaba.sdk.android.push.register.OppoRegister;
import com.alibaba.sdk.android.push.register.VivoRegister;
import com.aliyun.ams.emas.push.AgooMessageReceiver;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.push.PushTestRequest;
import com.ltech.smarthome.utils.RxUtils;
import com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/* loaded from: classes4.dex */
public class PushManager {
    boolean gcmPushRegisterResult;
    boolean huaWeiPushRegisterResult;
    boolean meizuPushRegisterResult;
    boolean miPushRegisterResult;
    boolean oppoPushRegisterResult;
    boolean vivoPushRegisterResult;

    private boolean checkPackageCorrect() {
        return true;
    }

    private PushManager() {
    }

    public void init(Context applicationContext) {
        if (checkPackageCorrect()) {
            try {
                PushServiceFactory.init(applicationContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void register(Context applicationContext) {
        try {
            createNotificationChannel(applicationContext);
            createVoiceCallNotificationChannel(applicationContext);
            createIntercomNotificationChannel(applicationContext);
            CloudPushService cloudPushService = PushServiceFactory.getCloudPushService();
            cloudPushService.setLogLevel(2);
            cloudPushService.register(applicationContext, new CommonCallback(this) { // from class: com.ltech.smarthome.push.PushManager.1
                @Override // com.alibaba.sdk.android.push.CommonCallback
                public void onSuccess(String response) {
                    LHomeLog.d(getClass(), "onSuccess: init cloudchannel success=============" + response);
                    Timber.tag(AgooMessageReceiver.TAG).d("init cloudchannel success=============%s", response);
                    LHomeLog.d(getClass(), "阿里初始化后设备id onSuccess:" + PushServiceFactory.getCloudPushService().getDeviceId());
                }

                @Override // com.alibaba.sdk.android.push.CommonCallback
                public void onFailed(String errorCode, String errorMessage) {
                    Timber.tag(AgooMessageReceiver.TAG).d("init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage, new Object[0]);
                }
            });
            this.miPushRegisterResult = MiPushRegister.register(applicationContext, PushConstants.XIAOMI_ID, PushConstants.XIAOMI_KEY);
            this.huaWeiPushRegisterResult = HuaWeiRegister.register((Application) applicationContext);
            this.oppoPushRegisterResult = OppoRegister.register(applicationContext, PushConstants.OPPO_APP_KEY, PushConstants.OPPO_APP_SECRET);
            this.meizuPushRegisterResult = MeizuRegister.register(applicationContext, PushConstants.MEIZU_APP_ID, PushConstants.MEIZU_APP_KEY);
            boolean register = VivoRegister.register(applicationContext);
            this.vivoPushRegisterResult = register;
            if (this.miPushRegisterResult || this.oppoPushRegisterResult || this.meizuPushRegisterResult || register) {
                return;
            }
            GcmRegister.register(applicationContext, PushConstants.GCM_SEND_ID, PushConstants.GCM_APPLICATION_ID, PushConstants.GCM_PROJECT_ID, PushConstants.GCM_API_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPushId() {
        if (!checkPackageCorrect()) {
            return "";
        }
        return PushServiceFactory.getCloudPushService().getDeviceId();
    }

    public void pushBind(LifecycleOwner owner, String deviceSn) {
        if (checkPackageCorrect()) {
            ((ObservableSubscribeProxy) Injection.net().pushBind(deviceSn).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.push.PushManager$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Timber.tag(AgooMessageReceiver.TAG).d("push bind success", new Object[0]);
                }
            }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.push.PushManager.2
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    Timber.tag(AgooMessageReceiver.TAG).d(this.errorMessage, new Object[0]);
                }
            });
        }
    }

    public void pushUnbind(LifecycleOwner owner) {
        if (checkPackageCorrect()) {
            ((ObservableSubscribeProxy) Injection.net().pushUnBind().compose(RxUtils.io_io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.push.PushManager$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Timber.tag(AgooMessageReceiver.TAG).d("push unbind success", new Object[0]);
                }
            }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.push.PushManager.3
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    Timber.tag(AgooMessageReceiver.TAG).d(this.errorMessage, new Object[0]);
                }
            });
        }
    }

    public void pushAndroidMessage(LifecycleOwner owner, String title, String content, long... userIds) {
        if (checkPackageCorrect()) {
            PushTestRequest.PushTestRequestBuilder aPushTestRequest = PushTestRequest.PushTestRequestBuilder.aPushTestRequest();
            aPushTestRequest.pushNotification().toAndroid().toIos().shareMessage().title(title).content(content).target(userIds);
            ((ObservableSubscribeProxy) Injection.net().pushTest(aPushTestRequest).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.push.PushManager$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Timber.tag(AgooMessageReceiver.TAG).d("push message success", new Object[0]);
                }
            }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.push.PushManager.4
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    Timber.tag(AgooMessageReceiver.TAG).d(this.errorMessage, new Object[0]);
                }
            });
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
            NotificationChannel m2 = GuideView$$ExternalSyntheticApiModelOutline0.m("1", "服务通知", 4);
            m2.setDescription("服务通知");
            m2.enableLights(true);
            m2.setLightColor(SupportMenu.CATEGORY_MASK);
            m2.setSound(RingtoneManager.getDefaultUri(2), new AudioAttributes.Builder().setContentType(4).setUsage(6).build());
            m2.setLockscreenVisibility(0);
            m2.enableVibration(true);
            m2.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(m2);
        }
    }

    private void createVoiceCallNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
            NotificationChannel m2 = GuideView$$ExternalSyntheticApiModelOutline0.m("2", "语音对讲", 4);
            m2.setDescription("语音对讲");
            m2.enableLights(true);
            m2.setLightColor(SupportMenu.CATEGORY_MASK);
            m2.setLockscreenVisibility(0);
            m2.setSound(RingtoneManager.getDefaultUri(2), new AudioAttributes.Builder().setContentType(4).setUsage(6).build());
            m2.enableVibration(true);
            m2.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(m2);
        }
    }

    private void createIntercomNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
            NotificationChannel m2 = GuideView$$ExternalSyntheticApiModelOutline0.m("3", "可视对讲", 4);
            m2.setDescription("可视对讲");
            m2.enableLights(true);
            m2.setLightColor(SupportMenu.CATEGORY_MASK);
            m2.setLockscreenVisibility(0);
            m2.setSound(RingtoneManager.getDefaultUri(1), new AudioAttributes.Builder().setContentType(4).setUsage(6).build());
            m2.enableVibration(true);
            m2.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(m2);
        }
    }
}