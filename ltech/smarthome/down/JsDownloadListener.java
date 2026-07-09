package com.ltech.smarthome.down;

/* loaded from: classes3.dex */
public interface JsDownloadListener {
    void onFail(String errorInfo);

    void onFinishDownload();

    void onProgress(int progress);

    void onStartDownload(long length);
}