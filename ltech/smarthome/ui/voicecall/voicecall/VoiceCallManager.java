package com.ltech.smarthome.ui.voicecall.voicecall;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.VibrateUtils;
import com.google.gson.reflect.TypeToken;
import com.juphoon.cloud.JCCall;
import com.juphoon.cloud.JCCallCallback;
import com.juphoon.cloud.JCCallItem;
import com.juphoon.cloud.JCClient;
import com.juphoon.cloud.JCClientCallback;
import com.juphoon.cloud.JCMediaChannel;
import com.juphoon.cloud.JCMediaChannelCallback;
import com.juphoon.cloud.JCMediaChannelParticipant;
import com.juphoon.cloud.JCMediaChannelQueryInfo;
import com.juphoon.cloud.JCMediaDevice;
import com.juphoon.cloud.JCMediaDeviceCallback;
import com.juphoon.cloud.JCMediaDeviceVideoCanvas;
import com.juphoon.cloud.LoginParam;
import com.ltech.imageclip.fragment.ActivityResultHelper;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.ui.voicecall.bind.ActBindVoiceCall;
import com.ltech.smarthome.ui.voicecall.group.ActVoiceGroupList;
import com.ltech.smarthome.ui.voicecall.setting.ActVoiceCallSetting;
import com.ltech.smarthome.ui.voicecall.whitelist.ActWhiteList;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.xiaomi.mipush.sdk.Constants;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhuhai.ltech.lt_voice_call_api.IVoiceCall;
import com.zhuhai.ltech.lt_voice_call_api.bean.QuerySettingResponse;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallGroup;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallSetting;
import com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class VoiceCallManager implements IVoiceCall {
    private static IVoiceCall sInstance;
    private View callView;
    private long groupId;
    private boolean hasCall;
    private boolean isCalling;
    private boolean isFirst;
    private boolean isSingle;
    private OnVoiceCallListener listener;
    private JCCall mCall;
    private JCClient mClient;
    private boolean mInit;
    private JCCallItem mJcCallItem;
    private JCMediaChannel mMediaChannel;
    private JCMediaDevice mMediaDevice;
    private boolean needUserTimeOut;
    private QuerySettingResponse querysettingresponse;
    private MediaPlayer r;
    private long user;
    private long voiceCallGroupUserId;
    private WindowManager windowManager;
    private final List<VoiceCallMember> users = new ArrayList();
    private final Handler handler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.10
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            VoiceCallManager.this.needUserTimeOut = true;
            VoiceCallManager.this.queryChannelCall(VoiceCallManager.this.groupId + "");
            return false;
        }
    });
    private final IUser iUser = Injection.repo().user();

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDoNotDisturbTime() {
        return false;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public Fragment getChooseView() {
        return null;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void showTempView(FragmentActivity activity, long placeId, long panelId) {
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public Observable<QuerySettingResponse> syncVoiceCallSetting(String mac) {
        return null;
    }

    public static IVoiceCall getInstance() {
        if (sInstance == null) {
            synchronized (VoiceCallManager.class) {
                if (sInstance == null) {
                    sInstance = new VoiceCallManager();
                }
            }
        }
        return sInstance;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void init() {
        if (this.mInit) {
            return;
        }
        JCClient create = JCClient.create(MyApplication.getContext(), "1de320185cd79b26f7285097", new JCClientCallback(this) { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.1
            @Override // com.juphoon.cloud.JCClientCallback
            public void onOnlineMessageReceive(String s, String s1) {
            }

            @Override // com.juphoon.cloud.JCClientCallback
            public void onOnlineMessageSendResult(int i, boolean b2) {
            }

            @Override // com.juphoon.cloud.JCClientCallback
            public void onLogin(boolean result, int reason) {
                LogUtils.eTag(getClass().getSimpleName(), "onLogin:result=" + result + ";reason" + reason);
            }

            @Override // com.juphoon.cloud.JCClientCallback
            public void onLogout(int reason) {
                LogUtils.eTag(getClass().getSimpleName(), "onLogout:reason" + reason);
            }

            @Override // com.juphoon.cloud.JCClientCallback
            public void onClientStateChange(int state, int oldState) {
                LogUtils.eTag(getClass().getSimpleName(), "state" + state);
            }
        }, null);
        this.mClient = create;
        this.mInit = create.getState() == 1;
        LogUtils.eTag(getClass().getSimpleName(), "初始化对讲" + this.mInit);
        JCMediaDevice create2 = JCMediaDevice.create(this.mClient, new JCMediaDeviceCallback(this) { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.2
            @Override // com.juphoon.cloud.JCMediaDeviceCallback
            public void onAudioError(boolean background) {
            }

            @Override // com.juphoon.cloud.JCMediaDeviceCallback
            public void onAudioOutputTypeChange(int i) {
            }

            @Override // com.juphoon.cloud.JCMediaDeviceCallback
            public void onAudioResume() {
            }

            @Override // com.juphoon.cloud.JCMediaDeviceCallback
            public void onCameraUpdate() {
            }

            @Override // com.juphoon.cloud.JCMediaDeviceCallback
            public void onNeedKeyFrame() {
            }

            @Override // com.juphoon.cloud.JCMediaDeviceCallback
            public void onRenderReceived(JCMediaDeviceVideoCanvas jcMediaDeviceVideoCanvas) {
            }

            @Override // com.juphoon.cloud.JCMediaDeviceCallback
            public void onRenderStart(JCMediaDeviceVideoCanvas jcMediaDeviceVideoCanvas) {
            }

            @Override // com.juphoon.cloud.JCMediaDeviceCallback
            public void onVideoError(JCMediaDeviceVideoCanvas jcMediaDeviceVideoCanvas) {
            }
        });
        this.mMediaDevice = create2;
        this.mCall = JCCall.create(this.mClient, create2, new JCCallCallback() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.3
            @Override // com.juphoon.cloud.JCCallCallback
            public void onDtmfReceived(JCCallItem jcCallItem, int i) {
            }

            @Override // com.juphoon.cloud.JCCallCallback
            public void onEarlyMediaReceived(JCCallItem item) {
            }

            @Override // com.juphoon.cloud.JCCallCallback
            public void onMessageReceive(String s, String s1, JCCallItem jcCallItem) {
            }

            @Override // com.juphoon.cloud.JCCallCallback
            public void onMissedCallItem(JCCallItem jcCallItem) {
            }

            @Override // com.juphoon.cloud.JCCallCallback
            public void onSipRingInfoReceived(JCCallItem item, String callSipType) {
            }

            @Override // com.juphoon.cloud.JCCallCallback
            public void onCallItemAdd(final JCCallItem jcCallItem) {
                VoiceCallManager.this.mJcCallItem = jcCallItem;
                if (jcCallItem.getDirection() == 0) {
                    if (!VoiceCallManager.this.isSingle && VoiceCallManager.this.hasCall) {
                        VoiceCallManager.this.mCall.term(jcCallItem, 0, ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_other_has_call));
                        return;
                    }
                    if (VoiceCallManager.this.isDoNotDisturbTime()) {
                        VoiceCallManager.this.term(ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_other_do_not_disturb), true);
                        return;
                    }
                    if (VoiceCallManager.this.listener != null) {
                        VoiceCallManager.this.listener.onPending();
                    }
                    VoiceCallManager.this.isSingle = true;
                    VoiceCallManager.this.isCalling = false;
                    VoiceCallManager.this.showDialog();
                    ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                VoiceCallMember voiceCallMember = (VoiceCallMember) GsonUtils.fromJson(jcCallItem.getExtraParam(), new TypeToken<VoiceCallMember>(this) { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.3.1.1
                                }.getType());
                                if (voiceCallMember != null) {
                                    VoiceCallManager.this.users.clear();
                                    voiceCallMember.setCaller(true);
                                    VoiceCallManager.this.users.add(voiceCallMember);
                                    if (VoiceCallManager.this.listener != null) {
                                        VoiceCallManager.this.listener.onUserChange(VoiceCallManager.this.users);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (VoiceCallManager.this.listener != null) {
                                VoiceCallManager.this.listener.onInvite("", true);
                            }
                        }
                    }, 1000L);
                    return;
                }
                if (jcCallItem.getDirection() == 1) {
                    VoiceCallManager.this.showDialog();
                    ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (VoiceCallManager.this.listener != null) {
                                VoiceCallManager.this.listener.onCallPending(jcCallItem.getUserId());
                            }
                        }
                    }, 1000L);
                }
            }

            @Override // com.juphoon.cloud.JCCallCallback
            public void onCallItemRemove(JCCallItem jcCallItem, int i, String s) {
                if (VoiceCallManager.this.listener != null) {
                    OnVoiceCallListener onVoiceCallListener = VoiceCallManager.this.listener;
                    if (jcCallItem.getReason() != 0) {
                        s = jcCallItem.getReason() == 8 ? ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_other_has_call) : "";
                    }
                    onVoiceCallListener.onHandUp(s);
                }
            }

            @Override // com.juphoon.cloud.JCCallCallback
            public void onCallItemUpdate(JCCallItem jcCallItem, JCCallItem.ChangeParam changeParam) {
                Log.e("VoiceCall", "状态更新" + jcCallItem.getState());
                if (jcCallItem.getState() == 3) {
                    if (VoiceCallManager.this.listener != null) {
                        VoiceCallManager.this.listener.onAnswer();
                    }
                } else {
                    if (jcCallItem.getState() == 1 || jcCallItem.getState() != -3 || VoiceCallManager.this.listener == null) {
                        return;
                    }
                    VoiceCallManager.this.listener.onHandUp(ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_net_disconnect));
                }
            }
        });
        JCMediaChannel create3 = JCMediaChannel.create(this.mClient, this.mMediaDevice, new JCMediaChannelCallback() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.4
            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onInviteSipUserResult(int i, boolean b2, int i1) {
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onMediaChannelPropertyChange(JCMediaChannel.PropChangeParam propChangeParam) {
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onMediaChannelStateChange(int i, int i1) {
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onMessageReceive(String s, String s1, String s2) {
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onParticipantUpdate(JCMediaChannelParticipant jcMediaChannelParticipant, JCMediaChannelParticipant.ChangeParam changeParam) {
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onStop(boolean b2, int i) {
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onJoin(boolean result, int i, String s) {
                if (result) {
                    if (!VoiceCallManager.this.isCalling) {
                        if (VoiceCallManager.this.listener != null) {
                            VoiceCallManager.this.listener.onAnswer();
                        }
                        if (VoiceCallManager.this.listener != null) {
                            VoiceCallManager.this.listener.onUserChange(VoiceCallManager.this.users);
                        }
                    } else {
                        VoiceCallManager.this.isFirst = true;
                        if (VoiceCallManager.this.listener != null) {
                            VoiceCallManager.this.listener.onCallPending(s);
                        }
                    }
                    VoiceCallManager.this.queryChannelCall(VoiceCallManager.this.groupId + "");
                    return;
                }
                if (VoiceCallManager.this.listener != null) {
                    VoiceCallManager.this.listener.onHandUp(ActivityUtils.getTopActivity().getString(R.string.voice_call_str_call_end));
                }
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onLeave(int i, String s) {
                if (VoiceCallManager.this.users == null || VoiceCallManager.this.users.size() == 0) {
                    VoiceCallManager.this.stop();
                } else if (VoiceCallManager.this.listener != null) {
                    VoiceCallManager.this.listener.onHandUp(ActivityUtils.getTopActivity().getString(R.string.voice_call_str_call_end));
                }
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onQuery(int operationId, boolean result, int reason, JCMediaChannelQueryInfo queryInfo) {
                if (result) {
                    queryInfo.getChannelId();
                    queryInfo.getNumber();
                    queryInfo.getClientCount();
                    List<String> members = queryInfo.getMembers();
                    if (members == null || members.size() <= 0 || VoiceCallManager.this.users == null || VoiceCallManager.this.users.size() <= 0) {
                        return;
                    }
                    boolean z = true;
                    if (VoiceCallManager.this.needUserTimeOut) {
                        VoiceCallManager.this.removeUserTimeOutEvent();
                        ArrayList arrayList = new ArrayList();
                        for (VoiceCallMember voiceCallMember : VoiceCallManager.this.users) {
                            Iterator<String> it = queryInfo.getMembers().iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (it.next().equals(voiceCallMember.getUserid() + "")) {
                                        arrayList.add(voiceCallMember);
                                        break;
                                    }
                                }
                            }
                        }
                        VoiceCallManager.this.users.clear();
                        VoiceCallManager.this.users.addAll(arrayList);
                    } else {
                        boolean z2 = false;
                        for (String str : queryInfo.getMembers()) {
                            Iterator it2 = VoiceCallManager.this.users.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    VoiceCallMember voiceCallMember2 = (VoiceCallMember) it2.next();
                                    if (str.equals(voiceCallMember2.getUserid() + "")) {
                                        voiceCallMember2.setWait(false);
                                        z2 = true;
                                        break;
                                    }
                                }
                            }
                        }
                        z = z2;
                    }
                    if (!z || VoiceCallManager.this.listener == null) {
                        return;
                    }
                    VoiceCallManager.this.listener.onUserChange(VoiceCallManager.this.users);
                    return;
                }
                if (VoiceCallManager.this.listener != null) {
                    VoiceCallManager.this.listener.onHandUp(ActivityUtils.getTopActivity().getString(R.string.voice_call_str_call_end));
                }
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onParticipantJoin(JCMediaChannelParticipant jcMediaChannelParticipant) {
                if (VoiceCallManager.this.isCalling && VoiceCallManager.this.isFirst && VoiceCallManager.this.listener != null) {
                    VoiceCallManager.this.listener.onAnswer();
                }
                if (VoiceCallManager.this.users != null && VoiceCallManager.this.users.size() > 0) {
                    Iterator it = VoiceCallManager.this.users.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        VoiceCallMember voiceCallMember = (VoiceCallMember) it.next();
                        if (jcMediaChannelParticipant.getUserId().equals(voiceCallMember.getUserid() + "")) {
                            voiceCallMember.setWait(false);
                            if (VoiceCallManager.this.listener != null) {
                                VoiceCallManager.this.listener.onUserChange(VoiceCallManager.this.users);
                            }
                        }
                    }
                }
                VoiceCallManager.this.queryChannelCall(VoiceCallManager.this.groupId + "");
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onParticipantLeft(JCMediaChannelParticipant jcMediaChannelParticipant) {
                if (VoiceCallManager.this.users != null && VoiceCallManager.this.users.size() > 0) {
                    Iterator it = VoiceCallManager.this.users.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        VoiceCallMember voiceCallMember = (VoiceCallMember) it.next();
                        if (jcMediaChannelParticipant.getUserId().equals(voiceCallMember.getUserid() + "")) {
                            VoiceCallManager.this.users.remove(voiceCallMember);
                            if (VoiceCallManager.this.listener != null) {
                                VoiceCallManager.this.listener.onUserChange(VoiceCallManager.this.users);
                            }
                        }
                    }
                }
                if (VoiceCallManager.this.users.size() == 1 && VoiceCallManager.this.iUser.getUserId() == ((VoiceCallMember) VoiceCallManager.this.users.get(0)).getUserid()) {
                    if (VoiceCallManager.this.listener != null) {
                        VoiceCallManager.this.listener.onHandUp("");
                    }
                } else {
                    VoiceCallManager.this.queryChannelCall(VoiceCallManager.this.groupId + "");
                }
            }

            @Override // com.juphoon.cloud.JCMediaChannelCallback
            public void onParticipantVolumeChange(JCMediaChannelParticipant jcMediaChannelParticipant) {
                if (jcMediaChannelParticipant.getVolumeStatus() != 3 || VoiceCallManager.this.users == null) {
                    return;
                }
                for (VoiceCallMember voiceCallMember : VoiceCallManager.this.users) {
                    voiceCallMember.setSpeak(jcMediaChannelParticipant.getUserId().equals(voiceCallMember.getUserid() + ""));
                }
                if (VoiceCallManager.this.listener != null) {
                    VoiceCallManager.this.listener.onUserChange(VoiceCallManager.this.users);
                }
            }
        });
        this.mMediaChannel = create3;
        create3.volumeChangeNotify = true;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void login(long user) {
        if (user > 0) {
            this.user = user;
            JCClient jCClient = this.mClient;
            if (jCClient != null && this.mInit) {
                if (jCClient.getUserId() != null) {
                    if (!this.mClient.getUserId().equals(user + "")) {
                        this.mClient.logout();
                    }
                }
                LogUtils.eTag(getClass().getSimpleName(), "登录id" + user);
                LoginParam loginParam = new LoginParam();
                this.mClient.login(user + "", "password", loginParam);
                LogUtils.eTag(getClass().getSimpleName(), "登陆化对讲");
                return;
            }
            LogUtils.eTag(getClass().getSimpleName(), "没有初始化对讲，登陆失败");
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void logout() {
        JCClient jCClient = this.mClient;
        if (jCClient == null || !this.mInit) {
            return;
        }
        jCClient.logout();
    }

    private void call(final VoiceCallMember voiceCallMember, final boolean isVideo) {
        JCClient jCClient;
        boolean canDrawOverlays;
        if (Build.VERSION.SDK_INT >= 23) {
            canDrawOverlays = Settings.canDrawOverlays(ActivityUtils.getTopActivity());
            if (!canDrawOverlays) {
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_float_permission), ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_float_permission_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.app_str_refuse), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.5
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        return false;
                    }
                }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.setting), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager$$ExternalSyntheticLambda2
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$call$1;
                        lambda$call$1 = VoiceCallManager.this.lambda$call$1(voiceCallMember, isVideo, baseDialog, view);
                        return lambda$call$1;
                    }
                }).setCancelable(false);
                return;
            }
        }
        if (this.mCall == null || (jCClient = this.mClient) == null || !this.mInit) {
            return;
        }
        if (jCClient.getUserId() == null) {
            SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_call_failed));
            long j = this.user;
            if (j > 0) {
                login(j);
                return;
            }
            return;
        }
        setSingle(true);
        this.users.clear();
        this.users.add(voiceCallMember);
        try {
            VoiceCallMember voiceCallMember2 = new VoiceCallMember();
            voiceCallMember2.setUserid(this.iUser.getUserId());
            voiceCallMember2.setHeadurl(this.iUser.getUserByDb().getHeadUrl());
            voiceCallMember2.setUsername(this.iUser.getUserByDb().getUserName());
            voiceCallMember2.setType(1);
            this.mCall.call(voiceCallMember.getUserid() + "", isVideo, new JCCall.CallParam(GsonUtils.toJson(voiceCallMember2), ""));
            this.isCalling = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$call$1(final VoiceCallMember voiceCallMember, final boolean z, BaseDialog baseDialog, View view) {
        ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"), new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager$$ExternalSyntheticLambda3
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                VoiceCallManager.this.lambda$call$0(voiceCallMember, z, i, intent);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$call$0(VoiceCallMember voiceCallMember, boolean z, int i, Intent intent) {
        boolean canDrawOverlays;
        canDrawOverlays = Settings.canDrawOverlays(ActivityUtils.getTopActivity());
        if (canDrawOverlays) {
            call(voiceCallMember, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialog() {
        boolean canDrawOverlays;
        if (this.hasCall) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            canDrawOverlays = Settings.canDrawOverlays(ActivityUtils.getTopActivity());
            if (!canDrawOverlays) {
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_float_permission), ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_float_permission_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.app_str_refuse), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.6
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        VoiceCallManager.this.term();
                        return false;
                    }
                }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.setting), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager$$ExternalSyntheticLambda0
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        boolean lambda$showDialog$3;
                        lambda$showDialog$3 = VoiceCallManager.this.lambda$showDialog$3(baseDialog, view);
                        return lambda$showDialog$3;
                    }
                }).setCancelable(false);
                return;
            }
        }
        this.hasCall = true;
        OnVoiceCallListener onVoiceCallListener = this.listener;
        if (onVoiceCallListener != null) {
            onVoiceCallListener.showDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showDialog$3(BaseDialog baseDialog, View view) {
        ActivityResultHelper.init((FragmentActivity) ActivityUtils.getTopActivity()).startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"), new ActivityResultHelper.Callback() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager$$ExternalSyntheticLambda1
            @Override // com.ltech.imageclip.fragment.ActivityResultHelper.Callback
            public final void onActivityResult(int i, Intent intent) {
                VoiceCallManager.this.lambda$showDialog$2(i, intent);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDialog$2(int i, Intent intent) {
        boolean canDrawOverlays;
        canDrawOverlays = Settings.canDrawOverlays(ActivityUtils.getTopActivity());
        if (canDrawOverlays) {
            OnVoiceCallListener onVoiceCallListener = this.listener;
            if (onVoiceCallListener != null) {
                onVoiceCallListener.showDialog();
                return;
            }
            return;
        }
        term();
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void dismissDialog() {
        JCMediaChannel jCMediaChannel;
        JCCall jCCall;
        removeUserTimeOutEvent();
        hideRingtone();
        this.hasCall = false;
        if (this.isSingle) {
            if (this.mJcCallItem != null && (jCCall = this.mCall) != null && this.mClient != null && this.mInit) {
                this.mCall.term(jCCall.getActiveCallItem(), 0, null);
            }
        } else if (this.mMediaDevice != null && (jCMediaChannel = this.mMediaChannel) != null && this.mClient != null && this.mInit) {
            jCMediaChannel.enableUploadAudioStream(false);
            if (this.isCalling) {
                this.mMediaChannel.stop();
            } else {
                this.mMediaChannel.leave();
            }
        }
        OnVoiceCallListener onVoiceCallListener = this.listener;
        if (onVoiceCallListener != null) {
            onVoiceCallListener.dismissDialog();
        }
        if (getWindowManager() == null || getCallWindow() == null || !getCallWindow().isAttachedToWindow()) {
            return;
        }
        getWindowManager().removeView(getCallWindow());
        setWindow(null);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void callByVoice(VoiceCallMember member) {
        call(member, false);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void callByVideo(VoiceCallMember member) {
        call(member, true);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void answer() {
        JCCall jCCall;
        JCCallItem jCCallItem = this.mJcCallItem;
        if (jCCallItem == null || (jCCall = this.mCall) == null || this.mClient == null || !this.mInit) {
            return;
        }
        jCCall.answer(jCCallItem, false);
        this.mJcCallItem = null;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void term() {
        term("", false);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void term(String msg, boolean needSendOther) {
        JCCall jCCall = this.mCall;
        if (jCCall == null || this.mClient == null || !this.mInit) {
            return;
        }
        JCCallItem activeCallItem = jCCall.getActiveCallItem();
        if (activeCallItem != null) {
            this.mCall.term(activeCallItem, 0, needSendOther ? msg : null);
        }
        this.mJcCallItem = null;
        OnVoiceCallListener onVoiceCallListener = this.listener;
        if (onVoiceCallListener == null) {
            dismissDialog();
            return;
        }
        if (needSendOther) {
            msg = "";
        }
        onVoiceCallListener.onHandUp(msg);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void createVoiceGroupCall(long groupId, List<VoiceCallMember> memberList) {
        if (this.mMediaDevice == null || this.mMediaChannel == null || this.mClient == null || !this.mInit) {
            return;
        }
        this.isCalling = true;
        this.isSingle = false;
        this.groupId = groupId;
        this.users.clear();
        for (VoiceCallMember voiceCallMember : memberList) {
            if (getVoiceCallGroupUserId() == voiceCallMember.getUserid()) {
                voiceCallMember.setCaller(true);
            }
            if (this.iUser.getUserId() == voiceCallMember.getUserid()) {
                voiceCallMember.setWait(false);
                voiceCallMember.setMe(true);
                this.users.add(0, voiceCallMember);
            } else {
                voiceCallMember.setWait(true);
                voiceCallMember.setMe(false);
                this.users.add(voiceCallMember);
            }
        }
        showDialog();
        join(groupId + "");
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void join(String channel) {
        JCClient jCClient;
        if (this.mMediaDevice == null || this.mMediaChannel == null || (jCClient = this.mClient) == null || !this.mInit) {
            return;
        }
        if (jCClient.getUserId() == null) {
            SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.app_str_voice_call_call_failed));
            long j = this.user;
            if (j > 0) {
                login(j);
                return;
            }
            return;
        }
        JCMediaChannel.JoinParam joinParam = new JCMediaChannel.JoinParam();
        joinParam.capacity = 64;
        joinParam.maxSender = 50;
        this.isSingle = false;
        this.mMediaChannel.enableUploadAudioStream(true);
        this.mMediaChannel.join(channel, joinParam);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void leave(long id) {
        if (this.isSingle || this.groupId != id) {
            return;
        }
        leave();
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void userLeave(long id, long userId) {
        List<VoiceCallMember> list;
        OnVoiceCallListener onVoiceCallListener;
        if (this.isSingle || this.groupId != id || (list = this.users) == null || list.size() <= 0) {
            return;
        }
        Iterator<VoiceCallMember> it = this.users.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            VoiceCallMember next = it.next();
            if (next.getUserid() == userId) {
                this.users.remove(next);
                break;
            }
        }
        OnVoiceCallListener onVoiceCallListener2 = this.listener;
        if (onVoiceCallListener2 != null) {
            onVoiceCallListener2.onUserChange(this.users);
        }
        if (this.users.size() == 1 && this.iUser.getUserId() == this.users.get(0).getUserid() && (onVoiceCallListener = this.listener) != null) {
            onVoiceCallListener.onHandUp("");
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void leave() {
        JCMediaChannel jCMediaChannel;
        removeUserTimeOutEvent();
        if (this.mMediaDevice != null && (jCMediaChannel = this.mMediaChannel) != null && this.mClient != null && this.mInit) {
            jCMediaChannel.enableUploadAudioStream(false);
            List<VoiceCallMember> list = this.users;
            if (list != null && list.size() > 0) {
                Iterator<VoiceCallMember> it = this.users.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    VoiceCallMember next = it.next();
                    if (next.getUserid() == this.iUser.getUserId()) {
                        this.users.remove(next);
                        break;
                    }
                }
            }
            this.mMediaChannel.getState();
            try {
                if (this.mMediaChannel.getSelfParticipant() != null) {
                    this.mMediaChannel.leave();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        OnVoiceCallListener onVoiceCallListener = this.listener;
        if (onVoiceCallListener != null) {
            onVoiceCallListener.onHandUp("");
        }
        messageStatusPull(this.groupId, 2);
        this.groupId = 0L;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void stop() {
        stop("");
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void stop(String msg) {
        JCMediaChannel jCMediaChannel;
        removeUserTimeOutEvent();
        if (this.mMediaDevice != null && (jCMediaChannel = this.mMediaChannel) != null && this.mClient != null && this.mInit) {
            jCMediaChannel.enableUploadAudioStream(false);
            this.mMediaChannel.stop();
        }
        OnVoiceCallListener onVoiceCallListener = this.listener;
        if (onVoiceCallListener != null) {
            onVoiceCallListener.onHandUp(msg);
        } else {
            dismissDialog();
        }
        messageStatusPull(this.groupId, 1);
        this.groupId = 0L;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void queryChannelCall(String channel) {
        JCMediaChannel jCMediaChannel;
        if (this.mMediaDevice == null || (jCMediaChannel = this.mMediaChannel) == null || this.mClient == null || !this.mInit) {
            return;
        }
        jCMediaChannel.query(channel);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public Observable<QuerySettingResponse> syncVoiceCallSetting(long panelId) {
        return Injection.net().queryVoiceCallSetting(panelId, "");
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void enableSpeaker(boolean b2) {
        JCMediaDevice jCMediaDevice = this.mMediaDevice;
        if (jCMediaDevice == null || this.mClient == null || !this.mInit) {
            return;
        }
        jCMediaDevice.enableSpeaker(b2);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public boolean isSpeakerEnable() {
        JCMediaDevice jCMediaDevice = this.mMediaDevice;
        if (jCMediaDevice == null || this.mClient == null || !this.mInit) {
            return false;
        }
        return jCMediaDevice.isSpeakerOn();
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void micMute(boolean b2) {
        JCCallItem activeCallItem;
        if (this.isSingle) {
            JCCall jCCall = this.mCall;
            if (jCCall == null || this.mClient == null || !this.mInit || (activeCallItem = jCCall.getActiveCallItem()) == null) {
                return;
            }
            Log.e("VoiceCall", "设置静音麦克风" + b2);
            this.mCall.muteMicrophone(activeCallItem, b2);
            return;
        }
        JCMediaDevice jCMediaDevice = this.mMediaDevice;
        if (jCMediaDevice == null || this.mClient == null || !this.mInit) {
            return;
        }
        if (b2) {
            jCMediaDevice.stopAudio();
        } else {
            jCMediaDevice.startAudio();
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public boolean isMicMute() {
        JCCallItem activeCallItem;
        if (this.isSingle) {
            JCCall jCCall = this.mCall;
            if (jCCall == null || this.mClient == null || !this.mInit || (activeCallItem = jCCall.getActiveCallItem()) == null) {
                return false;
            }
            Log.e("VoiceCall", "获取麦克风状态" + activeCallItem.getMicrophoneMute());
            return activeCallItem.getMicrophoneMute();
        }
        if (this.mMediaDevice == null || this.mClient == null || !this.mInit) {
            return false;
        }
        return !r0.isAudioStart();
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void enableAutoAnswer(boolean z) {
        getSetting().setIsautoanswer(z ? 1 : 0);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public boolean isAutoAnswer() {
        return getSetting().getIsautoanswer() == 1;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public boolean isDoNotDisturb() {
        return getSetting().getIsbusymode() == 1;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void doNotDisturb(boolean z) {
        getSetting().setIsbusymode(z ? 1 : 0);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public String getRepeatWeek() {
        return getSetting().getBusymodeweeks();
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void setRepeatWeek(String s) {
        String[] split = getRepeatTime().split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        getSetting().setBusymodeweeks(s);
        getSetting().setBusymodestart(split[0]);
        getSetting().setBusymodeend(split[1]);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public String getRepeatTime() {
        VoiceCallSetting setting = getSetting();
        String busymodestart = setting.getBusymodestart();
        String busymodeend = setting.getBusymodeend();
        if (busymodestart == null || TextUtils.isEmpty(busymodestart)) {
            busymodestart = "2200";
        }
        if (busymodeend == null || TextUtils.isEmpty(busymodeend)) {
            busymodeend = "800";
        }
        return busymodestart + Constants.ACCEPT_TIME_SEPARATOR_SP + busymodeend;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void setRepeatTime(String s) {
        String[] split = s.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        getSetting().setBusymodeweeks(getRepeatWeek());
        getSetting().setBusymodestart(split[0]);
        getSetting().setBusymodeend(split[1]);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public Observable<Object> messageGroupPull(long groupId) {
        return Injection.net().messageGroupPull(groupId);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public Observable<Object> messageUserPull(long userId) {
        return Injection.net().messageUserPull(userId);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public Observable<VoiceCallSetting> updateSetting(String mac) {
        VoiceCallSetting setting = getSetting();
        if (setting.getPanelvoicesettingid() > 0) {
            return Injection.net().updateSetting(setting.getPanelvoicesettingid(), setting.getIsautoanswer(), setting.getIsbusymode(), setting.getBusymodeweeks(), setting.getBusymodestart(), setting.getBusymodeend());
        }
        return Injection.net().addSetting(0L, mac, setting.getIsautoanswer(), setting.getIsbusymode(), setting.getBusymodeweeks(), setting.getBusymodestart(), setting.getBusymodeend());
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public Observable<VoiceCallSetting> updateSetting(long panelId) {
        VoiceCallSetting setting = getSetting();
        if (setting.getPanelvoicesettingid() > 0) {
            return Injection.net().updateSetting(setting.getPanelvoicesettingid(), setting.getIsautoanswer(), setting.getIsbusymode(), setting.getBusymodeweeks(), setting.getBusymodestart(), setting.getBusymodeend());
        }
        return Injection.net().addSetting(panelId, "", setting.getIsautoanswer(), setting.getIsbusymode(), setting.getBusymodeweeks(), setting.getBusymodestart(), setting.getBusymodeend());
    }

    private void requestPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Permission.WRITE_EXTERNAL_STORAGE) == 0 && ContextCompat.checkSelfPermission(context, Permission.RECORD_AUDIO) == 0 && ContextCompat.checkSelfPermission(context, Permission.CAMERA) == 0) {
                return;
            }
            context.requestPermissions(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.RECORD_AUDIO, Permission.CAMERA}, 1000);
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void setOnVoiceCallListener(OnVoiceCallListener listener) {
        this.listener = listener;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void removeVoiceCallListener() {
        this.listener = null;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public boolean isCalling() {
        return this.isCalling;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public boolean hasCalling() {
        return this.hasCall;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void setCalling(boolean calling) {
        this.isCalling = calling;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public boolean isSingle() {
        return this.isSingle;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void setSingle(boolean single) {
        this.isSingle = single;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public List<VoiceCallMember> getUsers() {
        return this.users;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void showAddWhiteListView(FragmentActivity activity, long placeId, long panelId, List<Long> ids, int requestCode) {
        activity.startActivityForResult(new Intent(activity, (Class<?>) ActWhiteList.class).putExtra(com.ltech.smarthome.utils.Constants.PLACE_ID, placeId).putExtra("panel_id", panelId).putExtra("ids", GsonUtils.toJson(ids)), requestCode);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void showAddGroupListView(FragmentActivity activity, long placeId, long panelId, List<VoiceCallGroup> groupList, int requestCode) {
        activity.startActivityForResult(new Intent(activity, (Class<?>) ActVoiceGroupList.class).putExtra(com.ltech.smarthome.utils.Constants.PLACE_ID, placeId).putExtra("panel_id", panelId).putExtra("voice_call_group", GsonUtils.toJson(groupList)), requestCode);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void showBindListView(FragmentActivity activity, long deviceId, int requestCode) {
        activity.startActivityForResult(new Intent(activity, (Class<?>) ActBindVoiceCall.class).putExtra("device_id", deviceId), requestCode);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public Observable<QuerySettingResponse.VoiceWhiteList> addWhiteList(long panelId, List<Long> ids) {
        return Injection.net().addWhiteList(panelId, ids);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public List<VoiceCallMember> getWhiteList() {
        QuerySettingResponse querySettingResponse = this.querysettingresponse;
        if (querySettingResponse == null || querySettingResponse.getVoicewhitelist() == null) {
            return new ArrayList();
        }
        return this.querysettingresponse.getVoicewhitelist().getUserinfoList();
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public List<VoiceCallGroup> getGroupList() {
        QuerySettingResponse querySettingResponse = this.querysettingresponse;
        if (querySettingResponse == null || querySettingResponse.getVoicegroup() == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (VoiceCallGroup voiceCallGroup : this.querysettingresponse.getVoicegroup().getRows()) {
            if (voiceCallGroup.getType() == 1) {
                arrayList.add(voiceCallGroup);
            }
        }
        return arrayList;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public VoiceCallSetting getSetting() {
        QuerySettingResponse querySettingResponse = this.querysettingresponse;
        if (querySettingResponse == null) {
            return new VoiceCallSetting();
        }
        return querySettingResponse.getVoicesetting();
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void updateDbSetting(VoiceCallSetting setting) {
        QuerySettingResponse querySettingResponse = this.querysettingresponse;
        if (querySettingResponse != null) {
            querySettingResponse.setVoicesetting(setting);
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void setSettingData(QuerySettingResponse response) {
        this.querysettingresponse = response;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public QuerySettingResponse getSettingData() {
        if (this.querysettingresponse == null) {
            this.querysettingresponse = new QuerySettingResponse();
            this.querysettingresponse.setVoicesetting(new VoiceCallSetting());
        }
        return this.querysettingresponse;
    }

    private void messageStatusPull(long groupId, int i) {
        if (this.users == null) {
            return;
        }
        Injection.net().messageStatusPull(groupId, new ArrayList(), i).compose(RxUtils.io_main()).subscribe((Consumer<? super R>) new Consumer<Object>(this) { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Object o) throws Exception {
            }
        });
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void inviteGroupChat(long id) {
        if (this.mMediaDevice == null || this.mMediaChannel == null || this.mClient == null || !this.mInit) {
            return;
        }
        if (id != this.groupId && this.hasCall) {
            messageStatusPull(id, 2);
            return;
        }
        if (isDoNotDisturbTime()) {
            leave();
            return;
        }
        OnVoiceCallListener onVoiceCallListener = this.listener;
        if (onVoiceCallListener != null) {
            onVoiceCallListener.onPending();
        }
        this.groupId = id;
        Injection.net().queryGroup(id).compose(RxUtils.io_main()).subscribe(new Consumer<VoiceCallGroup>() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.8
            @Override // io.reactivex.functions.Consumer
            public void accept(final VoiceCallGroup voiceCallGroup) throws Exception {
                VoiceCallManager.this.removeUserTimeOutEvent();
                VoiceCallManager.this.voiceCallGroupUserId = voiceCallGroup.getUserid();
                VoiceCallManager.this.users.clear();
                VoiceCallManager.this.isCalling = false;
                VoiceCallManager.this.isSingle = false;
                VoiceCallManager.this.showDialog();
                ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (VoiceCallManager.this.listener != null) {
                            VoiceCallManager.this.listener.onInvite(voiceCallGroup.getPanelvoicegroupid() + "", false);
                        }
                        VoiceCallManager.this.users.clear();
                        for (VoiceCallMember voiceCallMember : voiceCallGroup.getUserinfoList()) {
                            voiceCallMember.setWait(true);
                            if (VoiceCallManager.this.getVoiceCallGroupUserId() == voiceCallMember.getUserid()) {
                                voiceCallMember.setCaller(true);
                            }
                            if (voiceCallMember.getUserid() == VoiceCallManager.this.iUser.getUserId()) {
                                voiceCallMember.setMe(true);
                                VoiceCallManager.this.users.add(0, voiceCallMember);
                            } else {
                                VoiceCallManager.this.users.add(voiceCallMember);
                            }
                        }
                        if (VoiceCallManager.this.listener != null) {
                            VoiceCallManager.this.listener.onUserChange(VoiceCallManager.this.users);
                        }
                    }
                }, 1000L);
                VoiceCallManager.this.getUserTimeOutEvent();
            }
        }, new Consumer<Throwable>(this) { // from class: com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager.9
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                SmartToast.showShort(ActivityUtils.getTopActivity().getString(R.string.call_fail));
            }
        });
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void showVoiceSetting(FragmentActivity activity, long deviceId) {
        activity.startActivity(new Intent(activity, (Class<?>) ActVoiceCallSetting.class).putExtra("device_id", deviceId));
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public long getVoiceCallGroupUserId() {
        return this.voiceCallGroupUserId;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public long getGroupId() {
        return this.groupId;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void setWindow(View view) {
        this.callView = view;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public View getCallWindow() {
        return this.callView;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void showRingtone() {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.r = mediaPlayer;
            if (this.isCalling) {
                mediaPlayer.setAudioStreamType(0);
            } else {
                mediaPlayer.setAudioStreamType(3);
                VibrateUtils.vibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}, 1);
            }
            AssetFileDescriptor openRawResourceFd = MyApplication.getContext().getResources().openRawResourceFd(R.raw.voice_call);
            this.r.reset();
            this.r.setLooping(true);
            this.r.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
            openRawResourceFd.close();
            this.r.prepare();
            this.r.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void hideRingtone() {
        try {
            VibrateUtils.cancel();
            MediaPlayer mediaPlayer = this.r;
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public void setWindowManager(WindowManager w) {
        this.windowManager = w;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.IVoiceCall
    public WindowManager getWindowManager() {
        return this.windowManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUserTimeOutEvent() {
        this.needUserTimeOut = false;
        this.handler.removeMessages(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserTimeOutEvent() {
        this.handler.removeMessages(1);
        this.handler.sendEmptyMessageDelayed(1, 65000L);
    }
}