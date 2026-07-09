package com.ltech.smarthome.utils;

import com.smart.message.utils.LHomeLog;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes4.dex */
public class DownLoadFileRunnable implements Runnable {
    private DownloadCallback downloadCallback;
    private String urlPath;

    public interface DownloadCallback {
        void onSuccess(byte[] byteArray);
    }

    public DownLoadFileRunnable(String url, DownloadCallback downloadCallback) {
        this.urlPath = url;
        this.downloadCallback = downloadCallback;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.urlPath).openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestMethod("GET");
            if (httpURLConnection.getResponseCode() == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (inputStream != null) {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        } else {
                            byteArrayOutputStream.write(bArr, 0, read);
                        }
                    }
                }
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                LHomeLog.i(DownLoadFileRunnable.class, "download success:" + this.urlPath + " size:" + byteArrayOutputStream.toByteArray().length);
                DownloadCallback downloadCallback = this.downloadCallback;
                if (downloadCallback != null) {
                    downloadCallback.onSuccess(byteArrayOutputStream.toByteArray());
                    return;
                }
                return;
            }
            LHomeLog.i(DownLoadFileRunnable.class, "download fail:" + this.urlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}