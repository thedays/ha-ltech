package com.ltech.smarthome.net;

import com.smart.message.utils.LHomeLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class RetryFunction implements Function<Observable<? extends Throwable>, Observable<?>> {
    private int currentRetryCount;
    private int maxConnectCount;
    private int waitRetryTime;

    public RetryFunction(int maxConnectCount, int currentRetryCount, int waitRetryTime) {
        this.maxConnectCount = maxConnectCount;
        this.currentRetryCount = currentRetryCount;
        this.waitRetryTime = waitRetryTime;
    }

    @Override // io.reactivex.functions.Function
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        LHomeLog.i(getClass(), "throwableObservable -->" + observable);
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() { // from class: com.ltech.smarthome.net.RetryFunction.1
            @Override // io.reactivex.functions.Function
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                LHomeLog.i(getClass(), "发生异常 = " + throwable.toString());
                if (throwable instanceof IOException) {
                    LHomeLog.i(getClass(), "属于IO异常，需重试");
                    if (RetryFunction.this.currentRetryCount < RetryFunction.this.maxConnectCount) {
                        RetryFunction.this.currentRetryCount++;
                        LHomeLog.i(getClass(), "重试次数 = " + RetryFunction.this.currentRetryCount);
                        RetryFunction retryFunction = RetryFunction.this;
                        retryFunction.waitRetryTime = (retryFunction.currentRetryCount * 1000) + 1000;
                        LHomeLog.i(getClass(), "等待时间 =" + RetryFunction.this.waitRetryTime);
                        return Observable.just(1).delay(RetryFunction.this.waitRetryTime, TimeUnit.MILLISECONDS);
                    }
                    return Observable.error(new Throwable("重试次数已超过设置次数 = " + RetryFunction.this.currentRetryCount + "，即 不再重试"));
                }
                return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
            }
        });
    }
}