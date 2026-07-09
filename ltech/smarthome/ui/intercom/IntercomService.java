package com.ltech.smarthome.ui.intercom;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleService;
import com.akuvox.mobile.libcommon.model.media.MediaManager;
import com.akuvox.mobile.libcommon.params.SurfaceViewsParams;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.intercom.IntercomService;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.ImageTextView;
import com.ltech.smarthome.view.dialog.CallInviteDialog;
import com.ltech.smarthome.view.dialog.CenterSelectListIntercomDialog;
import com.ltech.smarthome.view.dialog.LoadingDialog;
import com.ltech.smarthome.view.dialog.LoadingSuccessDialog;
import com.smart.message.utils.LHomeLog;
import com.sun.jna.platform.win32.WinError;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class IntercomService extends LifecycleService implements OnIntercomListener {
    private static final int APP_TO_FRONT = 1;
    private static final int CLOSE_SUCCESS_DIALOG = 2;
    private static final int SIP_RECONNECT = 4;
    private static final int TIME_INCREASE = 3;
    private AudioManager audioManager;
    private Context context;
    private int currVolume;
    private IIntercom iIntercom;
    private ImageTextView itvVoiceMicrophone;
    private ImageTextView itvVoiceSpeak;
    private ImageTextView ivtAnswer;
    private ConstraintLayout layoutVoice;
    private LoadingDialog loadingDialog;
    private MediaPlayer mediaPlayer;
    private LoadingSuccessDialog successDialog;
    private TextView tvTime;
    private TextView tvTopName;
    private Vibrator vibrator;
    private int time = 0;
    private boolean isMuteSpeak = false;
    private boolean isMuteMusic = false;
    private boolean isAutoAnswer = false;
    private final Handler handler = new Handler() { // from class: com.ltech.smarthome.ui.intercom.IntercomService.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if (i == 1) {
                IntercomService intercomService = IntercomService.this;
                intercomService.setTopApp(intercomService.getApplicationContext());
                return;
            }
            if (i == 2) {
                if (IntercomService.this.successDialog != null) {
                    IntercomService.this.successDialog.dismiss();
                }
            } else {
                if (i == 3) {
                    IntercomService.this.handler.sendEmptyMessageDelayed(3, 1000L);
                    IntercomService.this.tvTime.setText(String.format("%02d:%02d", Integer.valueOf(IntercomService.this.time / 60), Integer.valueOf(IntercomService.this.time % 60)));
                    IntercomService.this.time++;
                    return;
                }
                if (i != 4) {
                    return;
                }
                LHomeLog.e(IntercomService.class, "sip重连中");
                if (Injection.intercom().getIntercomUser() != null) {
                    Injection.intercom().requestReg(Injection.intercom().getIntercomUser());
                }
                IntercomService.this.handler.sendEmptyMessageDelayed(4, 3000L);
            }
        }
    };

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.context = this;
        IIntercom intercomManager = IntercomManager.getInstance();
        this.iIntercom = intercomManager;
        intercomManager.setOnIntercomListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void showDialog() {
        if (VoiceCallManager.getInstance().hasCalling()) {
            CallInviteDialog.asDefault().setCallName(this.iIntercom.getCallData().remoteDisplayName).setLogoResId(R.mipmap.ic_invite_tercom).setInviteTip(getString(R.string.invite_call)).setOnConfirmListener(new AnonymousClass2()).showDialog((AppCompatActivity) ActivityUtils.getTopActivity());
        } else {
            setTopApp(MyApplication.getContext());
            showFloatingWindow();
        }
    }

    /* renamed from: com.ltech.smarthome.ui.intercom.IntercomService$2, reason: invalid class name */
    class AnonymousClass2 implements CallInviteDialog.OnConfirmListener {
        AnonymousClass2() {
        }

        @Override // com.ltech.smarthome.view.dialog.CallInviteDialog.OnConfirmListener
        public void onConfirm() {
            VoiceCallManager.getInstance().dismissDialog();
            if (VoiceCallManager.getInstance().isSingle()) {
                if (VoiceCallManager.getInstance().isCalling()) {
                    VoiceCallManager.getInstance().term(IntercomService.this.getString(R.string.app_str_voice_call_other_has_cut), true);
                } else {
                    VoiceCallManager.getInstance().term(IntercomService.this.getString(R.string.app_str_voice_call_other_has_cut), true);
                }
            } else if (VoiceCallManager.getInstance().isCalling()) {
                VoiceCallManager.getInstance().stop();
            } else {
                VoiceCallManager.getInstance().leave();
            }
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.IntercomService$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IntercomService.AnonymousClass2.this.lambda$onConfirm$0();
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfirm$0() {
            IntercomService.this.showDialogWithAnswer();
        }

        @Override // com.ltech.smarthome.view.dialog.CallInviteDialog.OnConfirmListener
        public void onCancel() {
            MediaManager.getInstance(IntercomService.this).hungupCall(IntercomService.this.iIntercom.getCallData().callId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogWithAnswer() {
        this.isAutoAnswer = true;
        setTopApp(MyApplication.getContext());
        showFloatingWindow();
        playSoundAndVibration(false);
        MediaManager.getInstance(this).answerCall(this.iIntercom.getCallData().callId, this.iIntercom.getCallData().callVideoMode);
        this.layoutVoice.setVisibility(0);
        this.ivtAnswer.setVisibility(8);
        this.handler.sendEmptyMessage(3);
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.IntercomService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                IntercomService.this.lambda$showDialogWithAnswer$0();
            }
        }, 800L);
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void showSipMonitor() {
        handleEstablishMessage(this.iIntercom.getSurfaceViewsParams());
    }

    private void showFloatingWindow() {
        if (this.iIntercom.getWindowManager() == null) {
            this.iIntercom.setWindowManager((WindowManager) getSystemService("window"));
        }
        View initView = initView();
        this.iIntercom.setWindow(initView);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= 26) {
            layoutParams.type = 2038;
        } else {
            layoutParams.type = 2003;
        }
        layoutParams.format = 1;
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.flags = WinError.ERROR_INVALID_DLL;
        this.iIntercom.getWindowManager().addView(initView, layoutParams);
        showView();
    }

    private void showView() {
        if (this.iIntercom.isMonitor()) {
            showMonitorView();
        } else {
            lambda$onCallWhenMonitor$3();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showMonitorAndSip, reason: merged with bridge method [inline-methods] */
    public void lambda$onCallWhenMonitor$3() {
        this.iIntercom.getCallView().findViewById(R.id.layout_voice).setVisibility(8);
        this.iIntercom.getCallView().findViewById(R.id.itv_speak).setVisibility(8);
        this.iIntercom.getCallView().findViewById(R.id.itv_answer).setVisibility(0);
        this.tvTopName.setText(this.iIntercom.getCallData().remoteDisplayName);
        if (this.isAutoAnswer) {
            return;
        }
        this.isAutoAnswer = false;
        this.tvTime.setText(getString(R.string.voice_call_str_multi_invite_waiting));
        MediaManager.getInstance(this).startMonitor(this.iIntercom.getSipUrl(), this.iIntercom.getDevInfo().getSip());
    }

    private void showMonitorView() {
        this.iIntercom.getCallView().findViewById(R.id.layout_voice).setVisibility(8);
        this.iIntercom.getCallView().findViewById(R.id.itv_answer).setVisibility(8);
        this.iIntercom.getCallView().findViewById(R.id.itv_speak).setVisibility(8);
        this.tvTopName.setText(this.iIntercom.getDevInfo().getLocation());
        handleEstablishMessage(this.iIntercom.getSurfaceViewsParams());
        this.handler.sendEmptyMessage(3);
    }

    private void handleEstablishMessage(SurfaceViewsParams surfaceViewsParams) {
        if (surfaceViewsParams != null) {
            surfaceViewsParams.remoteVideo.setZOrderOnTop(true);
            surfaceViewsParams.remoteVideo.setZOrderMediaOverlay(true);
            RelativeLayout relativeLayout = (RelativeLayout) this.iIntercom.getCallView().findViewById(R.id.rl_remote_video_view);
            relativeLayout.removeAllViews();
            relativeLayout.addView(surfaceViewsParams.remoteVideo);
        }
    }

    private View initView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_intercom_monitor, (ViewGroup) null);
        inflate.findViewById(R.id.itv_hangup).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.intercom.IntercomService.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                IntercomService.this.iIntercom.setRejectByUser(true);
                if (IntercomService.this.iIntercom.isMonitoring()) {
                    IntercomService.this.monitorHandleReject();
                } else {
                    IntercomService.this.listeningHandleReject();
                }
            }
        });
        inflate.findViewById(R.id.itv_unlock).setOnClickListener(new AnonymousClass4());
        inflate.findViewById(R.id.itv_answer).setOnClickListener(new AnonymousClass5());
        inflate.findViewById(R.id.itv_voice_speak).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.intercom.IntercomService.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                IntercomService.this.isMuteSpeak = !r3.isMuteSpeak;
                if (IntercomService.this.isMuteSpeak) {
                    IntercomService.this.itvVoiceSpeak.setText(IntercomService.this.getString(R.string.microphone_off));
                    IntercomService.this.itvVoiceSpeak.setSrc(R.mipmap.ic_microphone_off);
                } else {
                    IntercomService.this.itvVoiceSpeak.setText(IntercomService.this.getString(R.string.microphone_on));
                    IntercomService.this.itvVoiceSpeak.setSrc(R.mipmap.ic_microphone);
                }
                MediaManager.getInstance(IntercomService.this).setMute(IntercomService.this.isMuteSpeak);
            }
        });
        inflate.findViewById(R.id.itv_voice_microphone).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.intercom.IntercomService.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                IntercomService.this.isMuteMusic = !r3.isMuteMusic;
                if (IntercomService.this.isMuteMusic) {
                    IntercomService.this.itvVoiceMicrophone.setText(IntercomService.this.getString(R.string.speaker_off));
                    IntercomService.this.itvVoiceMicrophone.setSrc(R.mipmap.ic_speaker_off);
                    IntercomService.this.closeSpeaker();
                } else {
                    IntercomService.this.itvVoiceMicrophone.setText(IntercomService.this.getString(R.string.speaker_on));
                    IntercomService.this.itvVoiceMicrophone.setSrc(R.mipmap.ic_speaker_on);
                    IntercomService.this.lambda$showDialogWithAnswer$0();
                }
            }
        });
        this.layoutVoice = (ConstraintLayout) inflate.findViewById(R.id.layout_voice);
        this.itvVoiceSpeak = (ImageTextView) inflate.findViewById(R.id.itv_voice_speak);
        this.itvVoiceMicrophone = (ImageTextView) inflate.findViewById(R.id.itv_voice_microphone);
        this.ivtAnswer = (ImageTextView) inflate.findViewById(R.id.itv_answer);
        this.tvTopName = (TextView) inflate.findViewById(R.id.tv_top_name);
        this.tvTime = (TextView) inflate.findViewById(R.id.tv_time);
        return inflate;
    }

    /* renamed from: com.ltech.smarthome.ui.intercom.IntercomService$4, reason: invalid class name */
    class AnonymousClass4 implements View.OnClickListener {
        AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (IntercomService.this.iIntercom.getDevInfo().getRelay().size() > 1) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < IntercomService.this.iIntercom.getDevInfo().getRelay().size(); i++) {
                    arrayList.add(IntercomService.this.iIntercom.getDevInfo().getRelay().get(i).getDoor_name());
                }
                CenterSelectListIntercomDialog.asDefault(false).setTitle(IntercomService.this.getString(R.string.intercom_choose_gate)).setCancelString(IntercomService.this.getString(R.string.cancel)).setSelectList(arrayList).setSelectPosition(0).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.intercom.IntercomService$4$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        IntercomService.AnonymousClass4.this.lambda$onClick$0((Integer) obj);
                    }
                }).showDialog((AppCompatActivity) ActivityUtils.getTopActivity());
                return;
            }
            IntercomService intercomService = IntercomService.this;
            intercomService.openDoor(intercomService.iIntercom.getDevInfo().getMac(), IntercomService.this.iIntercom.getDevInfo().getRelay().get(0).getRelay_id());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0(Integer num) {
            IntercomService intercomService = IntercomService.this;
            intercomService.openDoor(intercomService.iIntercom.getDevInfo().getMac(), IntercomService.this.iIntercom.getDevInfo().getRelay().get(num.intValue()).getRelay_id());
        }
    }

    /* renamed from: com.ltech.smarthome.ui.intercom.IntercomService$5, reason: invalid class name */
    class AnonymousClass5 implements View.OnClickListener {
        AnonymousClass5() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            IntercomService.this.iIntercom.setRejectByUser(true);
            IntercomService.this.playSoundAndVibration(false);
            MediaManager.getInstance(IntercomService.this).answerCall(IntercomService.this.iIntercom.getCallData().callId, IntercomService.this.iIntercom.getCallData().callVideoMode);
            IntercomService.this.layoutVoice.setVisibility(0);
            IntercomService.this.ivtAnswer.setVisibility(8);
            IntercomService.this.handler.sendEmptyMessage(3);
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.IntercomService$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IntercomService.AnonymousClass5.this.lambda$onClick$0();
                }
            }, 800L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$0() {
            IntercomService.this.lambda$showDialogWithAnswer$0();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void listeningHandleReject() {
        MediaManager.getInstance(this).hungupCall(this.iIntercom.getCallData().callId);
        dismissDialog();
    }

    /* renamed from: openSpeaker, reason: merged with bridge method [inline-methods] */
    public void lambda$showDialogWithAnswer$0() {
        try {
            if (this.audioManager == null) {
                this.audioManager = (AudioManager) getSystemService("audio");
            }
            this.currVolume = this.audioManager.getStreamVolume(0);
            int streamMaxVolume = this.audioManager.getStreamMaxVolume(0);
            Log.e("openSpeaker", "currVolume=" + this.currVolume + "__max=" + streamMaxVolume);
            if (this.audioManager.isSpeakerphoneOn()) {
                return;
            }
            this.audioManager.setSpeakerphoneOn(true);
            this.audioManager.setStreamVolume(0, streamMaxVolume, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSpeaker() {
        try {
            if (this.audioManager == null) {
                this.audioManager = (AudioManager) getSystemService("audio");
            }
            if (this.audioManager.isSpeakerphoneOn()) {
                this.audioManager.setSpeakerphoneOn(false);
                this.audioManager.setStreamVolume(0, this.currVolume, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundAndVibration(boolean enabled) {
        VibrationEffect createOneShot;
        if (enabled) {
            MediaPlayer create = MediaPlayer.create(this, R.raw.voice_call);
            this.mediaPlayer = create;
            create.start();
        } else {
            MediaPlayer mediaPlayer = this.mediaPlayer;
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
                this.mediaPlayer.release();
                this.mediaPlayer = null;
            }
        }
        Vibrator vibrator = (Vibrator) getSystemService("vibrator");
        this.vibrator = vibrator;
        if (enabled) {
            if (vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= 26) {
                    Vibrator vibrator2 = this.vibrator;
                    createOneShot = VibrationEffect.createOneShot(500L, 200);
                    vibrator2.vibrate(createOneShot);
                    return;
                }
                this.vibrator.vibrate(500L);
                return;
            }
            return;
        }
        if (vibrator.hasVibrator()) {
            this.vibrator.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openDoor(String mac, long relay) {
        ((ObservableSubscribeProxy) Injection.net().openDoor(mac, relay).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.IntercomService$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                IntercomService.this.lambda$openDoor$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.intercom.IntercomService$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Action
            public final void run() {
                IntercomService.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.IntercomService$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                IntercomService.this.lambda$openDoor$2(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDoor$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.opening));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDoor$2(Object obj) throws Exception {
        showSuccessDialog(getString(R.string.open_success));
    }

    private void showLoadingDialog(String content) {
        LoadingDialog asDefault = LoadingDialog.asDefault(content);
        this.loadingDialog = asDefault;
        asDefault.showDialog((AppCompatActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissLoadingDialog() {
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    private void showSuccessDialog(String content) {
        LoadingSuccessDialog asDefault = LoadingSuccessDialog.asDefault(content);
        this.successDialog = asDefault;
        asDefault.showDialog((AppCompatActivity) ActivityUtils.getTopActivity());
        this.handler.sendEmptyMessageDelayed(2, 1500L);
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void monitorHandleReject() {
        MediaManager.getInstance(this).finishMonitor(this.iIntercom.getMonitorId(), this.iIntercom.getSipUrl());
        dismissDialog();
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void sipStopMonitor() {
        this.iIntercom.setMonitoring(false);
        MediaManager.getInstance(this).finishMonitor(this.iIntercom.getMonitorId(), this.iIntercom.getSipUrl());
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void sipMessageEstablishedCall() {
        if (this.iIntercom.isMonitoring()) {
            MediaManager.getInstance(this).finishMonitor(this.iIntercom.getMonitorId(), this.iIntercom.getSipUrl());
        }
        this.iIntercom.setMonitoring(false);
        handleEstablishMessage(this.iIntercom.getCallData().surfaceViewsParams);
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void finishedCall() {
        playSoundAndVibration(false);
        if (this.iIntercom.isMonitoring()) {
            this.iIntercom.setRejectByUser(true);
            monitorHandleReject();
        } else {
            listeningHandleReject();
        }
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void onCallWhenMonitor() {
        if (!this.iIntercom.getCallData().remoteDisplayName.equalsIgnoreCase(this.tvTopName.getText().toString())) {
            this.iIntercom.setRejectByUser(true);
            MediaManager.getInstance(this).finishMonitor(this.iIntercom.getMonitorId(), this.iIntercom.getSipUrl());
        }
        if (this.handler.hasMessages(3)) {
            this.time = 0;
            this.handler.removeMessages(3);
        }
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.IntercomService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                IntercomService.this.lambda$onCallWhenMonitor$3();
            }
        }, 200L);
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void sipReconnect() {
        LHomeLog.e(IntercomService.class, "开始sip重连");
        this.handler.sendEmptyMessageDelayed(4, 3000L);
    }

    @Override // com.ltech.smarthome.ui.intercom.OnIntercomListener
    public void sipReconnectFinish() {
        if (this.handler.hasMessages(4)) {
            LHomeLog.e(IntercomService.class, "结束sip重连");
            this.handler.removeMessages(4);
        }
    }

    private void dismissDialog() {
        this.time = 0;
        closeSpeaker();
        this.isMuteSpeak = false;
        this.isMuteMusic = false;
        this.iIntercom.setMonitoring(false);
        this.iIntercom.setCalling(false);
        playSoundAndVibration(false);
        this.handler.removeMessages(1);
        TextView textView = this.tvTopName;
        if (textView != null) {
            textView.setText("");
        }
        TextView textView2 = this.tvTime;
        if (textView2 != null) {
            textView2.setText("");
        }
        if (this.handler.hasMessages(3)) {
            this.handler.removeMessages(3);
        }
        IIntercom iIntercom = this.iIntercom;
        if (iIntercom == null || iIntercom.getWindowManager() == null || this.iIntercom.getCallView() == null || !this.iIntercom.getCallView().isAttachedToWindow()) {
            return;
        }
        this.iIntercom.getWindowManager().removeView(this.iIntercom.getCallView());
        this.iIntercom.setWindow(null);
    }

    public void setTopApp(Context context) {
        ComponentName componentName;
        ComponentName componentName2;
        if (AppUtils.isAppForeground()) {
            return;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        for (ActivityManager.RunningTaskInfo runningTaskInfo : activityManager.getRunningTasks(100)) {
            StringBuilder sb = new StringBuilder("进程Task：");
            componentName = runningTaskInfo.topActivity;
            sb.append(componentName.getPackageName());
            Log.e("MainActivity", sb.toString());
            componentName2 = runningTaskInfo.topActivity;
            if (componentName2.getPackageName().equals(context.getPackageName())) {
                Log.e("MainActivity", "进程Task：start");
                activityManager.moveTaskToFront(runningTaskInfo.id, 1);
                this.handler.sendEmptyMessageDelayed(1, 1000L);
                return;
            }
        }
    }
}