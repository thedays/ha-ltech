package com.ltech.smarthome;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Process;
import android.os.Vibrator;
import androidx.multidex.MultiDex;
import anet.channel.util.StringUtils;
import com.akuvox.mobile.libcommon.bean.CallDataBean;
import com.akuvox.mobile.libcommon.exp.ISipMessageListener;
import com.akuvox.mobile.libcommon.model.media.MediaManager;
import com.akuvox.mobile.libcommon.params.SurfaceViewsParams;
import com.akuvox.mobile.libcommon.utils.Log;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.service.LocationService;
import com.ltech.smarthome.singleton.InitManager;
import com.ltech.smarthome.ui.camera.EZManager;
import com.ltech.smarthome.ui.device.super_panel.music.manager.MiguManager;
import com.ltech.smarthome.ui.intercom.IntercomManager;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.LocationHelper;
import com.smart.message.utils.LHomeLog;
import com.tencent.bugly.crashreport.CrashReport;
import java.util.List;
import java.util.Locale;
import me.jessyan.autosize.AutoSizeConfig;

/* loaded from: classes3.dex */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static Application application;
    private static Context context;
    public static LocationService locationService;
    public static Vibrator mVibrator;

    private void setLanguage() {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = base;
        MultiDex.install(this);
        AutoSizeConfig.getInstance().setExcludeFontScale(true);
    }

    public static Context getContext() {
        return context;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        InitManager initManager = InitManager.getInstance();
        Context context2 = context;
        initManager.init(context2, context2.getApplicationContext());
        registerActivityLifecycleCallbacks(this);
        setLanguage();
        application = this;
        ThreadUtils.getMainHandler().postDelayed(new Runnable(this) { // from class: com.ltech.smarthome.MyApplication.1
            @Override // java.lang.Runnable
            public void run() {
                Injection.push().register(MyApplication.application);
            }
        }, 2000L);
    }

    private static void initIntercom(Application application2) {
        MediaManager.initAKTalkSDK(application2, new ISipMessageListener() { // from class: com.ltech.smarthome.MyApplication.2
            @Override // com.akuvox.mobile.libcommon.exp.ISipMessageListener
            public int rtspMessageEstablishedMonitor(int monitorId, SurfaceViewsParams surfaceViewsParams) {
                IntercomManager.getInstance().onMonitorEvent(monitorId, surfaceViewsParams);
                return 0;
            }

            @Override // com.akuvox.mobile.libcommon.exp.ISipMessageListener
            public int rtspMessageFinishedMonitor() {
                IntercomManager.getInstance().stopMonitor();
                return 0;
            }

            @Override // com.akuvox.mobile.libcommon.exp.ISipMessageListener
            public int sipMessageFinishedCall(int callId, String reason) {
                IntercomManager.getInstance().finishedCall();
                return 0;
            }

            @Override // com.akuvox.mobile.libcommon.exp.ISipMessageListener
            public int sipMessageIncomingCall(CallDataBean callData) {
                if (Injection.intercom().getIntercomDevList().size() <= 0) {
                    return 0;
                }
                IntercomManager.getInstance().sipIncomingCall(callData);
                return 0;
            }

            @Override // com.akuvox.mobile.libcommon.exp.ISipMessageListener
            public int sipMessageEstablishedCall(CallDataBean callData) {
                IntercomManager.getInstance().sipMessageEstablishedCall(callData);
                return 0;
            }

            @Override // com.akuvox.mobile.libcommon.exp.ISipMessageListener
            public int sipMessageRegStatus(int status) {
                LHomeLog.i(MyApplication.class, "sip 账号注册状态  : " + status);
                if (!Injection.intercom().isLogin()) {
                    return 0;
                }
                if (status == 0) {
                    IntercomManager.getInstance().sipReconnect();
                    return 0;
                }
                if (status != 2) {
                    return 0;
                }
                IntercomManager.getInstance().sipReconnectFinish();
                return 0;
            }
        });
    }

    public static void delayInit(Context applicationContext) {
        bugly();
        InitManager.getInstance().delayInit(context, applicationContext);
        EZManager.instance().init(application);
        MiguManager.getInstance().init(application);
        initLocationService(applicationContext);
        String processName = getProcessName(application, Process.myPid());
        if (processName == null || !processName.equalsIgnoreCase(getPackageName(application))) {
            return;
        }
        initIntercom(application);
    }

    private static void bugly() {
        CrashReport.initCrashReport(context, "e42dda2676", false);
    }

    private static void initLocationService(Context applicationContext) {
        locationService = new LocationService(applicationContext);
        mVibrator = (Vibrator) applicationContext.getSystemService("vibrator");
    }

    @Override // android.app.Application
    public void onTerminate() {
        LHomeLog.d(getClass(), "onTerminate");
        stopLocationService();
        super.onTerminate();
    }

    public static void stopLocationService() {
        locationService.unregisterListener(LocationHelper.getLocationListener());
        locationService.stop();
    }

    private static String getProcessName(Context context2, int pid) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context2.getApplicationContext().getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == pid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    private static String getPackageName(Context context2) {
        if (context2 == null) {
            return null;
        }
        try {
            return context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0).packageName;
        } catch (Exception e) {
            Log.e("Catch an exception = " + e);
            return null;
        }
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLanguage();
    }

    public static boolean isAppInForeground(Context context2) {
        ActivityManager activityManager;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (context2 != null && (activityManager = (ActivityManager) context2.getSystemService("activity")) != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName.equals(context2.getPackageName()) && runningAppProcessInfo.importance == 100) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        String currentLocaleLanguage = LanguageUtils.getCurrentLocaleLanguage(activity);
        LHomeLog.i(getClass(), "user_locale-->" + currentLocaleLanguage);
        if (StringUtils.isNotBlank(currentLocaleLanguage)) {
            Locale locale = Locale.SIMPLIFIED_CHINESE;
            if (currentLocaleLanguage.equals("en")) {
                locale = Locale.ENGLISH;
            } else if (currentLocaleLanguage.contains("ko") || currentLocaleLanguage.contains("KR")) {
                locale = Locale.KOREA;
            } else if (currentLocaleLanguage.contains("ru") || currentLocaleLanguage.contains("RU")) {
                locale = new Locale("ru", "RU");
            } else if (currentLocaleLanguage.contains("vi") || currentLocaleLanguage.contains("VI")) {
                locale = new Locale("vi", "VI");
            } else if (currentLocaleLanguage.contains("TW") || currentLocaleLanguage.contains("HK")) {
                locale = Locale.TRADITIONAL_CHINESE;
            }
            LanguageUtils.applyLanguage(activity, locale);
            LanguageUtils.applyLanguage(getContext(), locale);
            return;
        }
        LanguageUtils.applySystemLanguage(activity);
        LanguageUtils.applySystemLanguage(getContext());
    }

    public static LocationService getLocationService() {
        return locationService;
    }
}