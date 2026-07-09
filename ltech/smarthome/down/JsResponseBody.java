package com.ltech.smarthome.down;

import com.smart.message.utils.LHomeLog;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/* loaded from: classes3.dex */
public class JsResponseBody extends ResponseBody {
    private BufferedSource bufferedSource;
    private JsDownloadListener downloadListener;
    private ResponseBody responseBody;

    public JsResponseBody(ResponseBody responseBody, JsDownloadListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
        downloadListener.onStartDownload(responseBody.contentLength());
    }

    @Override // okhttp3.ResponseBody
    public MediaType contentType() {
        return this.responseBody.contentType();
    }

    @Override // okhttp3.ResponseBody
    public long contentLength() {
        return this.responseBody.contentLength();
    }

    @Override // okhttp3.ResponseBody
    public BufferedSource source() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(source(this.responseBody.source()));
        }
        return this.bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) { // from class: com.ltech.smarthome.down.JsResponseBody.1
            long totalBytesRead = 0;

            @Override // okio.ForwardingSource, okio.Source
            public long read(Buffer sink, long byteCount) throws IOException {
                long read = super.read(sink, byteCount);
                this.totalBytesRead += read != -1 ? read : 0L;
                LHomeLog.i(getClass(), "download：+read: " + ((int) ((this.totalBytesRead * 100) / JsResponseBody.this.responseBody.contentLength())));
                if (JsResponseBody.this.downloadListener != null && read != -1) {
                    JsResponseBody.this.downloadListener.onProgress((int) ((this.totalBytesRead * 100) / JsResponseBody.this.responseBody.contentLength()));
                }
                return read;
            }
        };
    }
}