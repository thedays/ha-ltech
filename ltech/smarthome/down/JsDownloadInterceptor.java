package com.ltech.smarthome.down;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class JsDownloadInterceptor implements Interceptor {
    private JsDownloadListener downloadListener;

    public JsDownloadInterceptor(JsDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response proceed = chain.proceed(chain.request());
        return proceed.newBuilder().body(new JsResponseBody(proceed.body(), this.downloadListener)).build();
    }
}