package com.ltech.smarthome.websocketlog;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.ltech.smarthome.websocketlog.MonitorLogcat;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/* loaded from: classes4.dex */
public class WSLog {
    private static final String TAG = "WSLog";
    private volatile boolean closing;
    private OkHttpClient mOkHttpClient;
    private WebSocket mWebSocket;
    private Handler workHandler;
    private HandlerThread workerThread;
    private String wsUrl;

    private interface Msg {
        public static final int CLOSE = 2;
        public static final int OPEN = 1;
    }

    public static WSLog getInstance() {
        return SingletonHolder.sInstance;
    }

    public void init(String wsUrl) {
        this.wsUrl = wsUrl;
        HandlerThread handlerThread = new HandlerThread("WSLog-thread");
        this.workerThread = handlerThread;
        handlerThread.start();
        this.workHandler = new Handler(this.workerThread.getLooper()) { // from class: com.ltech.smarthome.websocketlog.WSLog.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    WSLog.this.connectWS();
                    MonitorLogcat.getInstance().start(new MonitorLogcat.LogcatOutputCallback() { // from class: com.ltech.smarthome.websocketlog.WSLog.1.1
                        @Override // com.ltech.smarthome.websocketlog.MonitorLogcat.LogcatOutputCallback
                        public void onReaderLine(String line) {
                            if (WSLog.this.mWebSocket == null || WSLog.this.closing) {
                                return;
                            }
                            WSLog.this.mWebSocket.send(line);
                        }
                    });
                } else if (msg.what == 2) {
                    MonitorLogcat.getInstance().stop();
                    WSLog.this.disconnectWS();
                }
            }
        };
    }

    public void setEnable(boolean b2) {
        setEnable("", b2);
    }

    public void setEnable(String name, boolean b2) {
        MonitorLogcat.getInstance().LOG_NAME = name + "==";
        if (b2) {
            this.workHandler.sendEmptyMessage(1);
        } else {
            this.workHandler.sendEmptyMessage(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectWS() {
        if (this.mOkHttpClient == null) {
            this.mOkHttpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();
        }
        this.mOkHttpClient.dispatcher().cancelAll();
        this.mOkHttpClient.newWebSocket(new Request.Builder().url(this.wsUrl).build(), new WebSocketListener() { // from class: com.ltech.smarthome.websocketlog.WSLog.2
            @Override // okhttp3.WebSocketListener
            public void onOpen(WebSocket webSocket, final Response response) {
                WSLog.this.mWebSocket = webSocket;
                WSLog.this.closing = false;
                Log.i(WSLog.TAG, "onOpen---------------------------");
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, String text) {
                Log.i(WSLog.TAG, "onMessage---------------------------" + text);
            }

            @Override // okhttp3.WebSocketListener
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.i(WSLog.TAG, "onClosing---------------------------");
            }

            @Override // okhttp3.WebSocketListener
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.i(WSLog.TAG, "onClosed---------------------------");
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Log.i(WSLog.TAG, "onFailure---------------------------" + response, t);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectWS() {
        this.closing = true;
        OkHttpClient okHttpClient = this.mOkHttpClient;
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
        WebSocket webSocket = this.mWebSocket;
        if (webSocket != null) {
            boolean close = webSocket.close(1000, "NORMAL");
            Log.i(TAG, "mWebSocket.close = " + close);
            this.mWebSocket = null;
        }
    }

    private static class SingletonHolder {
        private static final WSLog sInstance = new WSLog();

        private SingletonHolder() {
        }
    }

    private WSLog() {
    }
}