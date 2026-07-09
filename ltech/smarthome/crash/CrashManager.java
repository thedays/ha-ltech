package com.ltech.smarthome.crash;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import java.lang.Thread;

/* loaded from: classes3.dex */
public class CrashManager implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private static CrashManager instance;
    private Application application;
    private Thread.UncaughtExceptionHandler mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    private CrashManager(Context context) {
        this.application = (Application) context.getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashManager getInstance(Context context) {
        CrashManager crashManager;
        CrashManager crashManager2 = instance;
        if (crashManager2 != null) {
            return crashManager2;
        }
        synchronized (CrashManager.class) {
            crashManager = instance;
            if (crashManager == null) {
                crashManager = new CrashManager(context.getApplicationContext());
                instance = crashManager;
            }
        }
        return crashManager;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable ex) {
        this.mDefaultHandler.uncaughtException(thread, ex);
        Process.killProcess(Process.myPid());
    }
}