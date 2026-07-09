package com.ltech.smarthome.ui.intercom;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import com.akuvox.mobile.libcommon.bean.CallDataBean;
import com.akuvox.mobile.libcommon.params.SurfaceViewsParams;
import com.ltech.smarthome.net.response.intercom.UserInfoResponse;

/* loaded from: classes4.dex */
public interface IIntercom {
    void finishedCall();

    CallDataBean getCallData();

    View getCallView();

    UserInfoResponse.DevInfo getDevInfo();

    int getMonitorId();

    String getSipUrl();

    SurfaceViewsParams getSurfaceViewsParams();

    WindowManager getWindowManager();

    boolean isCalling();

    boolean isMonitor();

    boolean isMonitoring();

    boolean isRejectByUser();

    void onMonitorEvent(int monitorId, SurfaceViewsParams surfaceViewsParams);

    void onMonitorPending(Context context, int position);

    void setCalling(boolean isCalling);

    void setMonitoring(boolean isMonitoring);

    void setOnIntercomListener(OnIntercomListener listener);

    void setRejectByUser(boolean isRejectByUser);

    void setWindow(View view);

    void setWindowManager(WindowManager windowManager);

    void sipIncomingCall(CallDataBean callData);

    void sipMessageEstablishedCall(CallDataBean callData);

    void sipReconnect();

    void sipReconnectFinish();

    void stopIntercom();

    void stopMonitor();
}