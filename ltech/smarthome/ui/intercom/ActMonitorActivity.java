package com.ltech.smarthome.ui.intercom;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.akuvox.mobile.libcommon.bean.CallDataBean;
import com.akuvox.mobile.libcommon.model.media.MediaManager;
import com.akuvox.mobile.libcommon.params.SurfaceViewsParams;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActMonitorBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.intercom.UserInfoResponse;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.dialog.CenterSelectListIntercomDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActMonitorActivity extends VMActivity<ActMonitorBinding, ActMonitorVM> {
    public static int LISTENING_MODE = 2;
    public static int MONITOR_MODE = 1;
    private AudioManager audioManager;
    private int callId;
    private int currVolume;
    private UserInfoResponse.DevInfo devInfo;
    private int doorPos;
    private int mMonitorId;
    private MediaPlayer mediaPlayer;
    private int mode;
    private Runnable runnable;
    private String sip;
    private String sipUrl;
    private long time;
    private String userName;
    private Vibrator vibrator;
    private int videoMode;
    private Handler handler = new Handler();
    private boolean isMonitoring = true;
    private boolean isMuteSpeak = false;
    private boolean isMuteMusic = false;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_monitor;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected boolean useEventBus() {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        int intExtra = getIntent().getIntExtra(Constants.INTERCOM_MODE, 1);
        this.mode = intExtra;
        if (intExtra == MONITOR_MODE) {
            initMonitor();
        } else {
            initMonitorAndSip();
        }
        MediaManager.getInstance(this).initMedia(this);
    }

    private void initMonitorAndSip() {
        this.callId = getIntent().getIntExtra("callId", -1);
        this.videoMode = getIntent().getIntExtra(Constants.VIDEO_MODE, 0);
        this.userName = getIntent().getStringExtra(Constants.REMOTE_NAME);
        String stringExtra = getIntent().getStringExtra(Constants.REMOTE_SIP);
        this.sip = stringExtra;
        UserInfoResponse.DevInfo devInfo = getDevInfo(stringExtra);
        this.devInfo = devInfo;
        this.sipUrl = String.format(Constants.LIVEVIEW_URL, devInfo.getRtsp_pwd(), Injection.intercom().getIntercomUser().getRtsp_server(), this.devInfo.getMac());
        if (this.devInfo != null) {
            MediaManager.getInstance(this).startMonitor(this.sipUrl, this.sip);
        }
        ((ActMonitorBinding) this.mViewBinding).layoutVoice.setVisibility(8);
        ((ActMonitorBinding) this.mViewBinding).itvSpeak.setVisibility(8);
        ((ActMonitorBinding) this.mViewBinding).tvTopName.setText(this.userName);
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

    private void initMonitor() {
        ((ActMonitorBinding) this.mViewBinding).layoutVoice.setVisibility(8);
        ((ActMonitorBinding) this.mViewBinding).itvAnswer.setVisibility(8);
        ((ActMonitorBinding) this.mViewBinding).itvSpeak.setVisibility(8);
        this.doorPos = getIntent().getIntExtra(Constants.INTERCOM_DOOR_POSITION, 0);
        UserInfoResponse.DevInfo devInfo = Injection.intercom().getIntercomDevList().get(this.doorPos);
        this.devInfo = devInfo;
        this.sipUrl = String.format(Constants.LIVEVIEW_URL, devInfo.getRtsp_pwd(), Injection.intercom().getIntercomUser().getRtsp_server(), this.devInfo.getMac());
        MediaManager.getInstance(this).startMonitor(this.sipUrl, this.devInfo.getSip());
        ((ActMonitorBinding) this.mViewBinding).tvTopName.setText(this.devInfo.getLocation());
        initRunnable();
    }

    private void initRunnable() {
        Runnable runnable = new Runnable() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity.1
            @Override // java.lang.Runnable
            public void run() {
                ActMonitorActivity.this.handler.postDelayed(this, 1000L);
                ((ActMonitorBinding) ActMonitorActivity.this.mViewBinding).tvTime.setText(String.format("%02d:%02d", Long.valueOf(ActMonitorActivity.this.time / 60), Long.valueOf(ActMonitorActivity.this.time % 60)));
                ActMonitorActivity.this.time++;
            }
        };
        this.runnable = runnable;
        this.handler.post(runnable);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActMonitorVM) this.mViewModel).hangupEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMonitorActivity.this.lambda$startObserve$0((Void) obj);
            }
        });
        ((ActMonitorVM) this.mViewModel).answerEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMonitorActivity.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((ActMonitorVM) this.mViewModel).voiceSpeakEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMonitorActivity.this.lambda$startObserve$3((Void) obj);
            }
        });
        ((ActMonitorVM) this.mViewModel).muteAudioEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMonitorActivity.this.lambda$startObserve$4((Void) obj);
            }
        });
        ((ActMonitorVM) this.mViewModel).unlockEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActMonitorActivity.this.lambda$startObserve$6((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Void r1) {
        if (this.isMonitoring) {
            monitorHandleReject();
        } else {
            listeningHandleReject();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r4) {
        playSoundAndVibration(false);
        MediaManager.getInstance(this).answerCall(this.callId, this.videoMode);
        ((ActMonitorBinding) this.mViewBinding).layoutVoice.setVisibility(0);
        ((ActMonitorBinding) this.mViewBinding).itvAnswer.setVisibility(8);
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ActMonitorActivity.this.lambda$startObserve$1();
            }
        }, 800L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r2) {
        boolean z = this.isMuteSpeak;
        this.isMuteSpeak = !z;
        if (!z) {
            ((ActMonitorBinding) this.mViewBinding).itvVoiceSpeak.setText(getString(R.string.microphone_off));
            ((ActMonitorBinding) this.mViewBinding).itvVoiceSpeak.setSrc(R.mipmap.ic_microphone_off);
        } else {
            ((ActMonitorBinding) this.mViewBinding).itvVoiceSpeak.setText(getString(R.string.microphone_on));
            ((ActMonitorBinding) this.mViewBinding).itvVoiceSpeak.setSrc(R.mipmap.ic_microphone);
        }
        MediaManager.getInstance(this).setMute(this.isMuteSpeak);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Void r2) {
        boolean z = this.isMuteMusic;
        this.isMuteMusic = !z;
        if (!z) {
            ((ActMonitorBinding) this.mViewBinding).itvVoiceMicrophone.setText(getString(R.string.speaker_off));
            ((ActMonitorBinding) this.mViewBinding).itvVoiceMicrophone.setSrc(R.mipmap.ic_speaker_off);
            closeSpeaker();
        } else {
            ((ActMonitorBinding) this.mViewBinding).itvVoiceMicrophone.setText(getString(R.string.speaker_on));
            ((ActMonitorBinding) this.mViewBinding).itvVoiceMicrophone.setSrc(R.mipmap.ic_speaker_on);
            lambda$startObserve$1();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(Void r4) {
        if (this.devInfo.getRelay().size() > 1) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.devInfo.getRelay().size(); i++) {
                arrayList.add(this.devInfo.getRelay().get(i).getDoor_name());
            }
            CenterSelectListIntercomDialog.asDefault(false).setTitle(getString(R.string.intercom_choose_gate)).setCancelString(getString(R.string.cancel)).setSelectList(arrayList).setSelectPosition(0).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActMonitorActivity.this.lambda$startObserve$5((Integer) obj);
                }
            }).showDialog(this);
            return;
        }
        openDoor(this.devInfo.getMac(), this.devInfo.getRelay().get(0).getRelay_id());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Integer num) {
        openDoor(this.devInfo.getMac(), this.devInfo.getRelay().get(num.intValue()).getRelay_id());
    }

    private void openDoor(String mac, long relay) {
        ((ObservableSubscribeProxy) Injection.net().openDoor(mac, relay).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMonitorActivity.this.lambda$openDoor$7((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActMonitorActivity.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.intercom.ActMonitorActivity$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMonitorActivity.this.lambda$openDoor$8(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDoor$7(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.opening));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDoor$8(Object obj) throws Exception {
        showSuccessDialog(getString(R.string.open_success));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void handleBusEvent(LiveBusHelper helper) {
        if (helper.getCode() == 16) {
            Message message = (Message) helper.getData();
            this.mMonitorId = message.arg1;
            SurfaceViewsParams surfaceViewsParams = (SurfaceViewsParams) message.obj;
            this.isMonitoring = true;
            handleEstablishMessage(surfaceViewsParams);
            if (this.mode == LISTENING_MODE) {
                playSoundAndVibration(true);
                return;
            }
            return;
        }
        if (helper.getCode() == 13) {
            if (this.mode == MONITOR_MODE) {
                monitorHandleReject();
                return;
            } else {
                MediaManager.getInstance(this).finishMonitor(this.mMonitorId, this.sipUrl);
                return;
            }
        }
        if (helper.getCode() == 14) {
            if (this.isMonitoring) {
                MediaManager.getInstance(this).finishMonitor(this.mMonitorId, this.sipUrl);
                this.isMonitoring = false;
            }
            handleEstablishMessage(((CallDataBean) ((Message) helper.getData()).obj).surfaceViewsParams);
            return;
        }
        if (helper.getCode() == 15) {
            playSoundAndVibration(false);
            if (this.isMonitoring) {
                monitorHandleReject();
            } else {
                listeningHandleReject();
            }
        }
    }

    private void handleEstablishMessage(SurfaceViewsParams surfaceViewsParams) {
        if (surfaceViewsParams != null) {
            surfaceViewsParams.remoteVideo.setZOrderOnTop(true);
            surfaceViewsParams.remoteVideo.setZOrderMediaOverlay(true);
            ((ActMonitorBinding) this.mViewBinding).rlRemoteVideoView.removeAllViews();
            ((ActMonitorBinding) this.mViewBinding).rlRemoteVideoView.addView(surfaceViewsParams.remoteVideo);
        }
    }

    private void monitorHandleReject() {
        MediaManager.getInstance(this).finishMonitor(this.mMonitorId, this.sipUrl);
        finish();
    }

    private void listeningHandleReject() {
        MediaManager.getInstance(this).hungupCall(this.callId);
        finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (4 == keyCode) {
            monitorHandleReject();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        monitorHandleReject();
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        playSoundAndVibration(false);
        this.handler.removeCallbacks(this.runnable);
        closeSpeaker();
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

    /* renamed from: openSpeaker, reason: merged with bridge method [inline-methods] */
    public void lambda$startObserve$1() {
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
}