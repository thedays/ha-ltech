package com.ltech.smarthome.down;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class RequestInterceptor implements Interceptor {
    private long startPoints;

    public RequestInterceptor(long startPoints) {
        this.startPoints = startPoints;
    }

    public long getStartPoints() {
        return this.startPoints;
    }

    public long setStartPoints(long startPoints) {
        this.startPoints = startPoints;
        return startPoints;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder newBuilder = chain.request().newBuilder();
        newBuilder.addHeader("RANGE", "bytes=" + this.startPoints + Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        return chain.proceed(newBuilder.build());
    }
}