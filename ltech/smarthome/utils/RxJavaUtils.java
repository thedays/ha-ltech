package com.ltech.smarthome.utils;

import android.util.Log;
import io.reactivex.Single;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.BlockingFirstObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.io.IOException;
import java.net.SocketException;

/* loaded from: classes4.dex */
public final class RxJavaUtils {
    private static ThreadLocal<Throwable> sLastError = new ThreadLocal<>();

    public static <T> T blockingGet(Single<T> single) {
        sLastError.remove();
        try {
            BlockingFirstObserver blockingFirstObserver = new BlockingFirstObserver();
            single.toObservable().subscribe(blockingFirstObserver);
            return blockingFirstObserver.blockingGet();
        } catch (Throwable th) {
            th = th;
            th.printStackTrace();
            ThreadLocal<Throwable> threadLocal = sLastError;
            if (th.getCause() != null) {
                th = th.getCause();
            }
            threadLocal.set(th);
            return null;
        }
    }

    public static Throwable getLastError() {
        return sLastError.get();
    }

    public static void setupErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer() { // from class: com.ltech.smarthome.utils.RxJavaUtils$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RxJavaUtils.lambda$setupErrorHandler$0((Throwable) obj);
            }
        });
    }

    static /* synthetic */ void lambda$setupErrorHandler$0(Throwable th) throws Exception {
        if (th instanceof UndeliverableException) {
            th = th.getCause();
        }
        Log.w("LDS", "Undeliverable exception received, not sure what to do", th);
        if ((th instanceof IOException) || (th instanceof SocketException) || (th instanceof InterruptedException) || (th instanceof NullPointerException) || (th instanceof IllegalArgumentException)) {
            return;
        }
        boolean z = th instanceof IllegalStateException;
    }
}