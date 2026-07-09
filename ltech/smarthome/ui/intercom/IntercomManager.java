package com.ltech.smarthome.ui.intercom;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.akuvox.mobile.libcommon.bean.CallDataBean;
import com.akuvox.mobile.libcommon.model.media.MediaManager;
import com.akuvox.mobile.libcommon.params.SurfaceViewsParams;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.response.intercom.UserInfoResponse;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.Constants;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import java.util.List;

/* loaded from: classes4.dex */
public class IntercomManager implements IIntercom {
    private static IIntercom sInstance;
    private CallDataBean callData;
    private View callView;
    private UserInfoResponse.DevInfo devInfo;
    private OnIntercomListener listener;
    private int monitorId;
    private String sipUrl;
    private SurfaceViewsParams surfaceViewsParams;
    private WindowManager windowManager;
    private boolean isMonitorMode = false;
    private boolean isMonitoring = false;
    private boolean isCalling = false;
    private boolean isRejectByUser = false;

    public static IIntercom getInstance() {
        if (sInstance == null) {
            synchronized (VoiceCallManager.class) {
                if (sInstance == null) {
                    sInstance = new IntercomManager();
                }
            }
        }
        return sInstance;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void setOnIntercomListener(OnIntercomListener listener) {
        this.listener = listener;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void sipIncomingCall(CallDataBean callData) {
        this.callData = callData;
        UserInfoResponse.DevInfo devInfo = getDevInfo(callData.remoteUserName);
        this.devInfo = devInfo;
        this.sipUrl = String.format(Constants.LIVEVIEW_URL, devInfo.getRtsp_pwd(), Injection.intercom().getIntercomUser().getRtsp_server(), this.devInfo.getMac());
        this.isMonitorMode = false;
        OnIntercomListener onIntercomListener = this.listener;
        if (onIntercomListener != null) {
            if (!this.isMonitoring) {
                checkPermissionAndShowDialog();
            } else {
                onIntercomListener.onCallWhenMonitor();
            }
        }
    }

    private void checkPermissionAndShowDialog() {
        boolean canDrawOverlays;
        if (Build.VERSION.SDK_INT >= 23) {
            canDrawOverlays = Settings.canDrawOverlays(ActivityUtils.getTopActivity());
            if (!canDrawOverlays) {
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_float_permission), ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_float_permission_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.app_str_refuse), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.intercom.IntercomManager.1
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        if (!IntercomManager.this.isMonitorMode) {
                            return false;
                        }
                        IntercomManager.this.isRejectByUser = true;
                        IntercomManager.this.listener.sipStopMonitor();
                        return false;
                    }
                }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.setting), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.intercom.IntercomManager$$ExternalSyntheticLambda2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$checkPermissionAndShowDialog$1;
                        lambda$checkPermissionAndShowDialog$1 = IntercomManager.this.lambda$checkPermissionAndShowDialog$1(baseDialog, view);
                        return lambda$checkPermissionAndShowDialog$1;
                    }
                }).setCancelable(false);
                return;
            }
        }
        ThreadUtils.getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.IntercomManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                IntercomManager.this.lambda$checkPermissionAndShowDialog$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$checkPermissionAndShowDialog$1(BaseDialog baseDialog, View view) {
        ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"), new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.intercom.IntercomManager$$ExternalSyntheticLambda1
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                IntercomManager.this.lambda$checkPermissionAndShowDialog$0(i, intent);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkPermissionAndShowDialog$0(int i, Intent intent) {
        if (this.isMonitorMode) {
            this.isRejectByUser = true;
            this.listener.sipStopMonitor();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkPermissionAndShowDialog$2() {
        this.listener.showDialog();
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void sipMessageEstablishedCall(CallDataBean callData) {
        this.callData = callData;
        this.isCalling = true;
        OnIntercomListener onIntercomListener = this.listener;
        if (onIntercomListener != null) {
            onIntercomListener.sipMessageEstablishedCall();
        }
    }

    private UserInfoResponse.DevInfo getDevInfo(String sip) {
        List<UserInfoResponse.DevInfo> intercomDevList = Injection.intercom().getIntercomDevList();
        for (int i = 0; i < intercomDevList.size(); i++) {
            if (intercomDevList.get(i).getSip().equals(sip)) {
                return intercomDevList.get(i);
            }
        }
        return null;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public WindowManager getWindowManager() {
        return this.windowManager;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void setWindow(View view) {
        this.callView = view;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public View getCallView() {
        return this.callView;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void onMonitorPending(Context context, int position) {
        UserInfoResponse.DevInfo devInfo = Injection.intercom().getIntercomDevList().get(position);
        this.devInfo = devInfo;
        this.sipUrl = String.format(Constants.LIVEVIEW_URL, devInfo.getRtsp_pwd(), Injection.intercom().getIntercomUser().getRtsp_server(), this.devInfo.getMac());
        String sip = this.devInfo.getSip();
        this.isMonitorMode = true;
        MediaManager.getInstance(context).startMonitor(this.sipUrl, sip);
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void onMonitorEvent(int monitorId, SurfaceViewsParams surfaceViewsParams) {
        OnIntercomListener onIntercomListener = this.listener;
        if (onIntercomListener != null) {
            this.monitorId = monitorId;
            this.surfaceViewsParams = surfaceViewsParams;
            this.isMonitoring = true;
            if (this.isMonitorMode) {
                checkPermissionAndShowDialog();
            } else {
                onIntercomListener.showSipMonitor();
            }
        }
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void stopMonitor() {
        OnIntercomListener onIntercomListener = this.listener;
        if (onIntercomListener != null) {
            if (!this.isRejectByUser) {
                if (this.isMonitorMode) {
                    onIntercomListener.monitorHandleReject();
                    return;
                } else {
                    onIntercomListener.sipStopMonitor();
                    return;
                }
            }
            this.isRejectByUser = false;
        }
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void finishedCall() {
        this.isCalling = false;
        OnIntercomListener onIntercomListener = this.listener;
        if (onIntercomListener != null) {
            onIntercomListener.finishedCall();
        }
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void sipReconnect() {
        OnIntercomListener onIntercomListener = this.listener;
        if (onIntercomListener != null) {
            onIntercomListener.sipReconnect();
        }
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void stopIntercom() {
        OnIntercomListener onIntercomListener = this.listener;
        if (onIntercomListener != null) {
            onIntercomListener.finishedCall();
        }
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void sipReconnectFinish() {
        OnIntercomListener onIntercomListener = this.listener;
        if (onIntercomListener != null) {
            onIntercomListener.sipReconnectFinish();
        }
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public boolean isMonitor() {
        return this.isMonitorMode;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public boolean isMonitoring() {
        return this.isMonitoring;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void setMonitoring(boolean monitoring) {
        this.isMonitoring = monitoring;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void setCalling(boolean isCalling) {
        this.isCalling = isCalling;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public boolean isCalling() {
        return this.isCalling;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public boolean isRejectByUser() {
        return this.isRejectByUser;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public void setRejectByUser(boolean rejectByUser) {
        this.isRejectByUser = rejectByUser;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public int getMonitorId() {
        return this.monitorId;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public String getSipUrl() {
        return this.sipUrl;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public UserInfoResponse.DevInfo getDevInfo() {
        return this.devInfo;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public SurfaceViewsParams getSurfaceViewsParams() {
        return this.surfaceViewsParams;
    }

    @Override // com.ltech.smarthome.ui.intercom.IIntercom
    public CallDataBean getCallData() {
        return this.callData;
    }
}