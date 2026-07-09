package com.ltech.smarthome.ui.voicecall;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleService;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.badge.BadgeDrawable;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.ui.intercom.IntercomManager;
import com.ltech.smarthome.ui.voicecall.VoiceCallService;
import com.ltech.smarthome.ui.voicecall.voicecall.VoiceCallManager;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.view.dialog.CallInviteDialog;
import com.qw.curtain.lib.GuideView$$ExternalSyntheticApiModelOutline0;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.sun.jna.platform.win32.WinError;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wang.avi.AVLoadingIndicatorView;
import com.xiaomi.mipush.sdk.Constants;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhuhai.ltech.lt_voice_call_api.IVoiceCall;
import com.zhuhai.ltech.lt_voice_call_api.bean.VoiceCallMember;
import com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class VoiceCallService extends LifecycleService implements OnVoiceCallListener {
    private static final int APP_TO_FRONT = 4;
    private static final int AUTO_ANSWER = 3;
    private static final int CALL_TIME_COUNT = 2;
    private static final int WAIT_TIME_OUT = 1;
    private CallInviteDialog callInviteDialog;
    private IVoiceCall iVoiceCall;
    private boolean isTalk;
    private ImageView ivFloatingCall;
    private ImageView ivMultiHeader;
    private ImageView ivSingleHeader;
    private BaseQuickAdapter<VoiceCallMember, BaseViewHolder> mMemberAdapter;
    private List<VoiceCallMember> memberList;
    private TextView tvFloatingCall;
    private TextView tvMultiName;
    private TextView tvMultiSub;
    private TextView tvMultiSub2;
    private TextView tvSingleName;
    private TextView tvSingleSub;
    private TextView tvTime;
    private boolean tempMic = false;
    boolean isFull = true;
    private int time = 0;
    private final Handler handler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.11
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i != 1) {
                if (i == 2) {
                    VoiceCallService.this.showFloatingMenu();
                    VoiceCallService.this.time++;
                    VoiceCallService.this.tvSingleSub.setText("");
                    VoiceCallService.this.tvMultiSub.setText("");
                    VoiceCallService.this.tvTime.setText(DateUtils.getStrTime2(Integer.valueOf(VoiceCallService.this.time)));
                    if (VoiceCallService.this.tvFloatingCall != null) {
                        VoiceCallService.this.tvFloatingCall.setText(DateUtils.getStrTime2(Integer.valueOf(VoiceCallService.this.time)));
                    }
                    VoiceCallService.this.handler.sendEmptyMessageDelayed(2, 1000L);
                } else if (i != 3) {
                    if (i == 4) {
                        VoiceCallService voiceCallService = VoiceCallService.this;
                        voiceCallService.setTopApp(voiceCallService.getApplicationContext());
                    }
                } else if (VoiceCallService.this.iVoiceCall.isSingle()) {
                    VoiceCallService.this.iVoiceCall.answer();
                } else if (VoiceCallService.this.iVoiceCall.getGroupId() > 0) {
                    VoiceCallService.this.iVoiceCall.join(VoiceCallService.this.iVoiceCall.getGroupId() + "");
                }
            } else if (!VoiceCallService.this.iVoiceCall.isSingle()) {
                if (VoiceCallService.this.iVoiceCall.isCalling()) {
                    VoiceCallService.this.iVoiceCall.stop(VoiceCallService.this.getString(R.string.voice_call_str_no_answer));
                } else {
                    VoiceCallService.this.iVoiceCall.leave();
                }
            } else {
                VoiceCallService.this.iVoiceCall.term(VoiceCallService.this.getString(R.string.voice_call_str_no_answer), false);
            }
            return false;
        }
    });

    @Override // com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener
    public void onPending() {
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        IVoiceCall voiceCallManager = VoiceCallManager.getInstance();
        this.iVoiceCall = voiceCallManager;
        voiceCallManager.setOnVoiceCallListener(this);
        this.iVoiceCall.init();
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.1
            @Override // java.lang.Runnable
            public void run() {
                VoiceCallService.this.iVoiceCall.login(Injection.repo().user().getUserId());
            }
        }, 1000L);
        return super.onStartCommand(intent, flags, startId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFloatingWindow() {
        if (this.iVoiceCall.getCallWindow() == null) {
            if (this.iVoiceCall.getWindowManager() == null) {
                this.iVoiceCall.setWindowManager((WindowManager) getSystemService("window"));
            }
            View initView = initView();
            this.iVoiceCall.setWindow(initView);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= 26) {
                layoutParams.type = 2038;
            } else {
                layoutParams.type = 2003;
            }
            layoutParams.flags = 40;
            layoutParams.format = 1;
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.flags = WinError.ERROR_INVALID_DLL;
            layoutParams.format = -2;
            this.iVoiceCall.getWindowManager().addView(initView, layoutParams);
            showView();
            onUserChange(this.iVoiceCall.getUsers());
        }
    }

    private View initView() {
        final View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_voice_call_calling_party, (ViewGroup) null);
        inflate.findViewById(R.id.layout_volume);
        inflate.findViewById(R.id.iv_volume_down).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String string;
                boolean z = !VoiceCallManager.getInstance().isMicMute();
                if (!VoiceCallService.this.isTalk) {
                    z = !VoiceCallService.this.tempMic;
                }
                VoiceCallService.this.tempMic = z;
                VoiceCallManager.getInstance().micMute(z);
                TextView textView = (TextView) inflate.findViewById(R.id.tv_mic);
                if (z) {
                    string = VoiceCallService.this.getString(R.string.app_str_voice_call_mic_off);
                } else {
                    string = VoiceCallService.this.getString(R.string.app_str_voice_call_mic_on);
                }
                textView.setText(string);
                ((ImageView) inflate.findViewById(R.id.iv_volume_down)).setBackgroundResource(z ? R.mipmap.intercom_btn_microphoneoff : R.mipmap.intercom_btn_microphoneon);
            }
        });
        inflate.findViewById(R.id.iv_volume_up).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String string;
                boolean isSpeakerEnable = VoiceCallManager.getInstance().isSpeakerEnable();
                VoiceCallManager.getInstance().enableSpeaker(!isSpeakerEnable);
                TextView textView = (TextView) inflate.findViewById(R.id.tv_speaker);
                if (isSpeakerEnable) {
                    string = VoiceCallService.this.getString(R.string.app_str_voice_call_speaker_off);
                } else {
                    string = VoiceCallService.this.getString(R.string.app_str_voice_call_speaker_on);
                }
                textView.setText(string);
                ((ImageView) inflate.findViewById(R.id.iv_volume_up)).setBackgroundResource(isSpeakerEnable ? R.mipmap.intercom_btn_speakeroff : R.mipmap.intercom_btn_speakeron);
            }
        });
        inflate.findViewById(R.id.iv_cut).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                VoiceCallService.this.cut();
            }
        });
        inflate.findViewById(R.id.iv_answer).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                VoiceCallService.this.handler.removeMessages(3);
                if (VoiceCallService.this.iVoiceCall.isSingle()) {
                    VoiceCallService.this.iVoiceCall.answer();
                    return;
                }
                if (VoiceCallService.this.iVoiceCall.getGroupId() > 0) {
                    VoiceCallService.this.iVoiceCall.join(VoiceCallService.this.iVoiceCall.getGroupId() + "");
                }
            }
        });
        this.tvSingleSub = (TextView) inflate.findViewById(R.id.tv_single_sub);
        this.ivSingleHeader = (ImageView) inflate.findViewById(R.id.iv_single_header);
        this.tvSingleName = (TextView) inflate.findViewById(R.id.tv_single_name);
        this.tvMultiSub = (TextView) inflate.findViewById(R.id.tv_multi_sub);
        this.tvMultiSub2 = (TextView) inflate.findViewById(R.id.tv_multi_sub2);
        this.ivMultiHeader = (ImageView) inflate.findViewById(R.id.iv_multi_header);
        this.tvMultiName = (TextView) inflate.findViewById(R.id.tv_multi_name);
        this.tvTime = (TextView) inflate.findViewById(R.id.tv_time);
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cut() {
        this.handler.removeMessages(3);
        if (this.iVoiceCall.isSingle()) {
            if (this.iVoiceCall.isCalling()) {
                this.iVoiceCall.term(getString(R.string.app_str_voice_call_other_has_cut), true);
                return;
            } else if (this.isTalk) {
                this.iVoiceCall.term(getString(R.string.app_str_voice_call_other_has_cut), true);
                return;
            } else {
                this.iVoiceCall.term(getString(R.string.app_str_voice_call_other_has_refuse), true);
                return;
            }
        }
        if (this.iVoiceCall.isCalling()) {
            this.iVoiceCall.stop();
        } else {
            this.iVoiceCall.leave();
        }
    }

    private void showView() {
        if (this.iVoiceCall.isSingle() && this.iVoiceCall.isCalling()) {
            showCallingView();
            return;
        }
        if (this.iVoiceCall.isSingle() && !this.iVoiceCall.isCalling()) {
            showCalledView();
            return;
        }
        if (!this.iVoiceCall.isSingle() && this.iVoiceCall.isCalling()) {
            showMultiCallingView();
        } else {
            if (this.iVoiceCall.isSingle() || this.iVoiceCall.isCalling()) {
                return;
            }
            showMultiCalledView();
        }
    }

    private void showMultiCalledView() {
        if (this.iVoiceCall.getCallWindow() == null) {
            return;
        }
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_called_party).setVisibility(0);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_single_calling_party).setVisibility(8);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_calling_party).setVisibility(8);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_multi_calling_party).setVisibility(0);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_multi_called_party).setVisibility(0);
        ((TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_cut)).setText(getString(R.string.app_str_voice_call_reject));
        this.tvMultiSub.setText(R.string.voice_call_str_single_invite);
        initCalledList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showMultiCallingView() {
        String string;
        String string2;
        if (this.iVoiceCall.getCallWindow() == null) {
            return;
        }
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_calling_party).setVisibility(0);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_multi_calling_party).setVisibility(0);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_called_party).setVisibility(8);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_multi_called_party).setVisibility(8);
        ((TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_cut)).setText(getString(R.string.app_str_voice_call_cut_off));
        this.tvMultiSub.setText(R.string.voice_call_str_multi_invite_waiting);
        TextView textView = (TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_mic);
        if (VoiceCallManager.getInstance().isMicMute()) {
            string = getString(R.string.app_str_voice_call_mic_off);
        } else {
            string = getString(R.string.app_str_voice_call_mic_on);
        }
        textView.setText(string);
        TextView textView2 = (TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_speaker);
        if (!VoiceCallManager.getInstance().isSpeakerEnable()) {
            string2 = getString(R.string.app_str_voice_call_speaker_off);
        } else {
            string2 = getString(R.string.app_str_voice_call_speaker_on);
        }
        textView2.setText(string2);
        ((ImageView) this.iVoiceCall.getCallWindow().findViewById(R.id.iv_volume_down)).setBackgroundResource(VoiceCallManager.getInstance().isMicMute() ? R.mipmap.intercom_btn_microphoneoff : R.mipmap.intercom_btn_microphoneon);
        ((ImageView) this.iVoiceCall.getCallWindow().findViewById(R.id.iv_volume_up)).setBackgroundResource(!VoiceCallManager.getInstance().isSpeakerEnable() ? R.mipmap.intercom_btn_speakeroff : R.mipmap.intercom_btn_speakeron);
        initCallingList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFloatingMenu() {
        this.isFull = true;
        final View findViewById = this.iVoiceCall.getCallWindow().findViewById(R.id.layout_all);
        final View findViewById2 = this.iVoiceCall.getCallWindow().findViewById(R.id.layout_floating);
        final View findViewById3 = this.iVoiceCall.getCallWindow().findViewById(R.id.iv_bg);
        final ImageView imageView = (ImageView) this.iVoiceCall.getCallWindow().findViewById(R.id.iv_floating_menu);
        this.ivFloatingCall = (ImageView) this.iVoiceCall.getCallWindow().findViewById(R.id.iv_floating_call);
        this.tvFloatingCall = (TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_floating_call);
        findViewById2.setVisibility(0);
        findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (VoiceCallService.this.iVoiceCall.getWindowManager() == null || VoiceCallService.this.iVoiceCall.getCallWindow() == null) {
                    return;
                }
                final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                if (Build.VERSION.SDK_INT >= 26) {
                    layoutParams.type = 2038;
                } else {
                    layoutParams.type = 2003;
                }
                layoutParams.flags = 40;
                layoutParams.format = -2;
                if (VoiceCallService.this.isFull) {
                    findViewById2.setBackgroundResource(R.drawable.shape_white_bg);
                    findViewById3.setBackgroundColor(VoiceCallService.this.getResources().getColor(R.color.transparent));
                    imageView.setVisibility(8);
                    VoiceCallService.this.tvFloatingCall.setVisibility(0);
                    VoiceCallService.this.ivFloatingCall.setVisibility(0);
                    findViewById.setVisibility(8);
                    VoiceCallService.this.isFull = false;
                    layoutParams.width = ConvertUtils.dp2px(73.0f);
                    layoutParams.height = ConvertUtils.dp2px(73.0f);
                    layoutParams.gravity = BadgeDrawable.TOP_START;
                    findViewById2.setOnTouchListener(new View.OnTouchListener() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.6.1
                        private int lastX;
                        private int lastY;
                        private float mTouchStartX;
                        private float mTouchStartY;

                        @Override // android.view.View.OnTouchListener
                        public boolean onTouch(View v2, MotionEvent event) {
                            int action = event.getAction();
                            if (action == 0) {
                                this.lastX = layoutParams.x;
                                this.lastY = layoutParams.y;
                                this.mTouchStartX = event.getRawX();
                                this.mTouchStartY = event.getRawY();
                                return true;
                            }
                            if (action != 1) {
                                if (action != 2) {
                                    return false;
                                }
                                layoutParams.x = this.lastX + ((int) (event.getRawX() - this.mTouchStartX));
                                layoutParams.y = this.lastY + ((int) (event.getRawY() - this.mTouchStartY));
                                VoiceCallService.this.iVoiceCall.getWindowManager().updateViewLayout(VoiceCallService.this.iVoiceCall.getCallWindow(), layoutParams);
                                return true;
                            }
                            if (event.getEventTime() - event.getDownTime() <= ViewConfiguration.getTapTimeout()) {
                                findViewById3.setBackgroundColor(VoiceCallService.this.getResources().getColor(R.color.black));
                                findViewById2.setOnTouchListener(null);
                                VoiceCallService.this.isFull = true;
                                layoutParams.format = 1;
                                layoutParams.flags = WinError.ERROR_INVALID_DLL;
                                layoutParams.width = -1;
                                layoutParams.height = -1;
                                findViewById2.setBackgroundResource(R.color.transparent);
                                imageView.setVisibility(0);
                                VoiceCallService.this.tvFloatingCall.setVisibility(8);
                                VoiceCallService.this.ivFloatingCall.setVisibility(8);
                                findViewById.setVisibility(0);
                                VoiceCallService.this.iVoiceCall.getWindowManager().updateViewLayout(VoiceCallService.this.iVoiceCall.getCallWindow(), layoutParams);
                            }
                            return true;
                        }
                    });
                    VoiceCallService.this.iVoiceCall.getWindowManager().updateViewLayout(VoiceCallService.this.iVoiceCall.getCallWindow(), layoutParams);
                }
            }
        });
    }

    private void initCallingList() {
        if (this.iVoiceCall.getCallWindow() == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) this.iVoiceCall.getCallWindow().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.7
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int screenWidth = ((ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(34.0f)) - (ConvertUtils.dp2px(95.0f) * 3)) / 2;
                outRect.bottom = ConvertUtils.dp2px(16.0f);
                int childLayoutPosition = parent.getChildLayoutPosition(view) % 3;
                if (childLayoutPosition == 1) {
                    return;
                }
                if (childLayoutPosition == 0) {
                    outRect.left = ConvertUtils.dp2px(14.0f);
                } else {
                    outRect.right = ConvertUtils.dp2px(14.0f);
                }
            }
        });
        BaseQuickAdapter<VoiceCallMember, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VoiceCallMember, BaseViewHolder>(this, R.layout.item_multi_voice_calling_header) { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.8
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, VoiceCallMember voiceCallMember) {
                if (voiceCallMember.getType() == 2) {
                    switch (voiceCallMember.getPaneltype()) {
                        case 2:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_mini);
                            break;
                        case 3:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_4s);
                            break;
                        case 4:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_6s);
                            break;
                        case 5:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_12s);
                            break;
                        case 6:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_pro);
                            break;
                        case 7:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_g4max);
                            break;
                        default:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.ic_my_photo_default);
                            break;
                    }
                    baseViewHolder.setText(R.id.tv_name, voiceCallMember.getFloorname() + " " + voiceCallMember.getRoomname());
                } else {
                    baseViewHolder.setText(R.id.tv_name, voiceCallMember.getUsername());
                    Glide.with(MyApplication.getContext()).load(voiceCallMember.getHeadurl()).placeholder(R.mipmap.ic_my_photo_default).into((ImageView) baseViewHolder.getView(R.id.iv_cover));
                }
                baseViewHolder.setGone(R.id.iv_mute, voiceCallMember.isSpeak());
                baseViewHolder.setGone(R.id.iv_me, voiceCallMember.isCaller());
                baseViewHolder.setVisible(R.id.iv_cur, voiceCallMember.isMe());
                baseViewHolder.setVisible(R.id.cover_view, !voiceCallMember.isMe() && voiceCallMember.isWait());
                if (voiceCallMember.isMe() || !voiceCallMember.isWait()) {
                    ((AVLoadingIndicatorView) baseViewHolder.getView(R.id.cover_view)).hide();
                } else {
                    ((AVLoadingIndicatorView) baseViewHolder.getView(R.id.cover_view)).show();
                }
            }
        };
        this.mMemberAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
    }

    private void initCalledList() {
        if (this.iVoiceCall.getCallWindow() == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) this.iVoiceCall.getCallWindow().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 0, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.9
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = ConvertUtils.dp2px(5.0f);
                outRect.right = ConvertUtils.dp2px(5.0f);
            }
        });
        BaseQuickAdapter<VoiceCallMember, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<VoiceCallMember, BaseViewHolder>(this, R.layout.item_multi_voice_called_header) { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.10
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, VoiceCallMember voiceCallMember) {
                if (voiceCallMember.getType() == 2) {
                    switch (voiceCallMember.getPaneltype()) {
                        case 2:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_mini);
                            break;
                        case 3:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_4s);
                            break;
                        case 4:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_6s);
                            break;
                        case 5:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_12s);
                            break;
                        case 6:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_pro);
                            break;
                        case 7:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.contacts_ic_g4max);
                            break;
                        default:
                            baseViewHolder.setImageResource(R.id.iv_cover, R.mipmap.ic_my_photo_default);
                            break;
                    }
                    return;
                }
                Glide.with(this.mContext).load(voiceCallMember.getHeadurl()).placeholder(R.mipmap.ic_my_photo_default).into((ImageView) baseViewHolder.getView(R.id.iv_cover));
            }
        };
        this.mMemberAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
    }

    private void showCalledView() {
        if (this.tvSingleSub == null || this.iVoiceCall.getCallWindow().findViewById(R.id.group_called_party).getVisibility() == 0) {
            return;
        }
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_called_party).setVisibility(0);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_single_calling_party).setVisibility(0);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_calling_party).setVisibility(8);
        ((TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_cut)).setText(getString(R.string.app_str_voice_call_reject));
        this.tvSingleSub.setText(R.string.voice_call_str_single_invite);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGridLayoutManager(int num) {
        if (num > 3) {
            num = 3;
        }
        ((RecyclerView) this.iVoiceCall.getCallWindow().findViewById(R.id.rv)).setLayoutManager(new GridLayoutManager(getApplicationContext(), num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCallingView() {
        String string;
        String string2;
        if (this.tvSingleSub == null || this.iVoiceCall.getCallWindow().findViewById(R.id.group_calling_party).getVisibility() == 0) {
            return;
        }
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_calling_party).setVisibility(0);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_single_calling_party).setVisibility(0);
        this.iVoiceCall.getCallWindow().findViewById(R.id.group_called_party).setVisibility(8);
        ((TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_cut)).setText(getString(R.string.app_str_voice_call_cut_off));
        this.tvSingleSub.setText(R.string.voice_call_str_single_invite_waiting);
        this.tempMic = VoiceCallManager.getInstance().isMicMute();
        TextView textView = (TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_mic);
        if (VoiceCallManager.getInstance().isMicMute()) {
            string = getString(R.string.app_str_voice_call_mic_off);
        } else {
            string = getString(R.string.app_str_voice_call_mic_on);
        }
        textView.setText(string);
        TextView textView2 = (TextView) this.iVoiceCall.getCallWindow().findViewById(R.id.tv_speaker);
        if (!VoiceCallManager.getInstance().isSpeakerEnable()) {
            string2 = getString(R.string.app_str_voice_call_speaker_off);
        } else {
            string2 = getString(R.string.app_str_voice_call_speaker_on);
        }
        textView2.setText(string2);
        ((ImageView) this.iVoiceCall.getCallWindow().findViewById(R.id.iv_volume_down)).setBackgroundResource(VoiceCallManager.getInstance().isMicMute() ? R.mipmap.intercom_btn_microphoneoff : R.mipmap.intercom_btn_microphoneon);
        ((ImageView) this.iVoiceCall.getCallWindow().findViewById(R.id.iv_volume_up)).setBackgroundResource(!VoiceCallManager.getInstance().isSpeakerEnable() ? R.mipmap.intercom_btn_speakeroff : R.mipmap.intercom_btn_speakeron);
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean isDoNotDisturb() {
        String[] split;
        if (this.iVoiceCall.isDoNotDisturb()) {
            try {
                Calendar calendar = Calendar.getInstance();
                int parseInt = Integer.parseInt(String.format("%s%s", Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12))));
                String repeatWeek = this.iVoiceCall.getRepeatWeek();
                String weekOfDateNum = DateUtils.getWeekOfDateNum(calendar.getTime());
                if (TextUtils.isEmpty(repeatWeek)) {
                    return false;
                }
                if ((repeatWeek.equals("8") || repeatWeek.contains(weekOfDateNum)) && (split = this.iVoiceCall.getRepeatTime().split(Constants.ACCEPT_TIME_SEPARATOR_SP)) != null) {
                    int parseInt2 = Integer.parseInt(split[0]);
                    int parseInt3 = Integer.parseInt(split[1]);
                    if (parseInt2 < parseInt3 && parseInt >= parseInt2 && parseInt <= parseInt3) {
                        return true;
                    }
                    if (parseInt2 > parseInt3 && (parseInt >= parseInt2 || parseInt <= parseInt3)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener
    public void onHandUp(final String s) {
        this.iVoiceCall.hideRingtone();
        if (TextUtils.isEmpty(s)) {
            s = getString(R.string.voice_call_str_call_end);
        }
        this.handler.removeMessages(3);
        this.handler.removeMessages(1);
        this.handler.removeMessages(2);
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.12
            @Override // java.lang.Runnable
            public void run() {
                if (VoiceCallService.this.tvSingleSub == null) {
                    return;
                }
                VoiceCallService.this.tvSingleSub.setText(s);
                VoiceCallService.this.tvMultiSub.setText(s);
                if (VoiceCallService.this.tvFloatingCall != null) {
                    VoiceCallService.this.tvFloatingCall.setText(s);
                    VoiceCallService.this.tvFloatingCall.setTextColor(VoiceCallService.this.getResources().getColor(R.color.call_red));
                }
                if (VoiceCallService.this.ivFloatingCall != null) {
                    VoiceCallService.this.ivFloatingCall.setImageResource(R.mipmap.intercom_btn_hangup);
                }
            }
        });
        ThreadUtils.getMainHandler().postDelayed(new Runnable(this) { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.13
            @Override // java.lang.Runnable
            public void run() {
                VoiceCallManager.getInstance().dismissDialog();
            }
        }, 2000L);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener
    public void onAnswer() {
        this.iVoiceCall.hideRingtone();
        if (!this.isTalk) {
            this.isTalk = true;
            this.iVoiceCall.enableSpeaker(false);
            this.iVoiceCall.micMute(this.tempMic);
        }
        this.handler.removeMessages(3);
        this.handler.removeMessages(1);
        this.handler.removeMessages(2);
        this.handler.sendEmptyMessage(2);
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.14
            @Override // java.lang.Runnable
            public void run() {
                if (VoiceCallService.this.iVoiceCall.isCalling()) {
                    return;
                }
                if (VoiceCallService.this.iVoiceCall.isSingle()) {
                    VoiceCallService.this.showCallingView();
                } else {
                    VoiceCallService.this.showMultiCallingView();
                }
            }
        });
        if (this.iVoiceCall.isSingle() || this.iVoiceCall.getGroupId() <= 0) {
            return;
        }
        this.iVoiceCall.queryChannelCall(this.iVoiceCall.getGroupId() + "");
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener
    public void onUserChange(final List<VoiceCallMember> member) {
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.15
            @Override // java.lang.Runnable
            public void run() {
                List list = member;
                if (list == null || list.size() == 0) {
                    return;
                }
                VoiceCallService.this.memberList = member;
                VoiceCallMember voiceCallMember = (VoiceCallMember) member.get(0);
                if (!VoiceCallService.this.iVoiceCall.isSingle()) {
                    Iterator it = member.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        VoiceCallMember voiceCallMember2 = (VoiceCallMember) it.next();
                        if (voiceCallMember2.isCaller()) {
                            voiceCallMember = voiceCallMember2;
                            break;
                        }
                    }
                }
                if (IntercomManager.getInstance().isMonitoring() || IntercomManager.getInstance().isCalling()) {
                    if (VoiceCallService.this.callInviteDialog != null) {
                        if (voiceCallMember.getType() == 2) {
                            VoiceCallService.this.callInviteDialog.setCallNameUi(voiceCallMember.getFloorname() + " " + voiceCallMember.getRoomname());
                        } else {
                            VoiceCallService.this.callInviteDialog.setCallNameUi(voiceCallMember.getUsername());
                        }
                        if (VoiceCallService.this.iVoiceCall.isSingle()) {
                            VoiceCallService.this.callInviteDialog.setInviteTipUi(VoiceCallService.this.getString(R.string.invite_single_call));
                        } else {
                            VoiceCallService.this.callInviteDialog.setInviteTipUi(VoiceCallService.this.getString(R.string.invite_group_call));
                        }
                        switch (voiceCallMember.getPaneltype()) {
                            case 2:
                                VoiceCallService.this.callInviteDialog.setLogoResIdUi(R.mipmap.contacts_ic_mini);
                                break;
                            case 3:
                                VoiceCallService.this.callInviteDialog.setLogoResIdUi(R.mipmap.contacts_ic_4s);
                                break;
                            case 4:
                                VoiceCallService.this.callInviteDialog.setLogoResIdUi(R.mipmap.contacts_ic_6s);
                                break;
                            case 5:
                                VoiceCallService.this.callInviteDialog.setLogoResIdUi(R.mipmap.contacts_ic_12s);
                                break;
                            case 6:
                                VoiceCallService.this.callInviteDialog.setLogoResIdUi(R.mipmap.contacts_ic_pro);
                                break;
                            case 7:
                                VoiceCallService.this.callInviteDialog.setLogoResIdUi(R.mipmap.contacts_ic_g4max);
                                break;
                            default:
                                VoiceCallService.this.callInviteDialog.setLogoResIdUi(R.mipmap.ic_my_photo_default);
                                break;
                        }
                        return;
                    }
                    return;
                }
                if (VoiceCallService.this.tvSingleName != null) {
                    if (voiceCallMember.getType() == 2) {
                        VoiceCallService.this.tvSingleName.setText(voiceCallMember.getFloorname() + " " + voiceCallMember.getRoomname());
                        VoiceCallService.this.tvMultiName.setText(voiceCallMember.getFloorname() + " " + voiceCallMember.getRoomname());
                    } else {
                        VoiceCallService.this.tvSingleName.setText(voiceCallMember.getUsername());
                        VoiceCallService.this.tvMultiName.setText(voiceCallMember.getUsername());
                    }
                    switch (voiceCallMember.getPaneltype()) {
                        case 2:
                            VoiceCallService.this.ivSingleHeader.setImageResource(R.mipmap.contacts_ic_mini);
                            VoiceCallService.this.ivMultiHeader.setImageResource(R.mipmap.contacts_ic_mini);
                            break;
                        case 3:
                            VoiceCallService.this.ivSingleHeader.setImageResource(R.mipmap.contacts_ic_4s);
                            VoiceCallService.this.ivMultiHeader.setImageResource(R.mipmap.contacts_ic_4s);
                            break;
                        case 4:
                            VoiceCallService.this.ivSingleHeader.setImageResource(R.mipmap.contacts_ic_6s);
                            VoiceCallService.this.ivMultiHeader.setImageResource(R.mipmap.contacts_ic_6s);
                            break;
                        case 5:
                            VoiceCallService.this.ivSingleHeader.setImageResource(R.mipmap.contacts_ic_12s);
                            VoiceCallService.this.ivMultiHeader.setImageResource(R.mipmap.contacts_ic_12s);
                            break;
                        case 6:
                            VoiceCallService.this.ivSingleHeader.setImageResource(R.mipmap.contacts_ic_pro);
                            VoiceCallService.this.ivMultiHeader.setImageResource(R.mipmap.contacts_ic_pro);
                            break;
                        case 7:
                            VoiceCallService.this.ivSingleHeader.setImageResource(R.mipmap.contacts_ic_g4max);
                            VoiceCallService.this.ivMultiHeader.setImageResource(R.mipmap.contacts_ic_g4max);
                            break;
                        default:
                            Glide.with(MyApplication.getContext()).load(voiceCallMember.getHeadurl()).placeholder(R.mipmap.ic_my_photo_default).into(VoiceCallService.this.ivSingleHeader);
                            Glide.with(MyApplication.getContext()).load(voiceCallMember.getHeadurl()).placeholder(R.mipmap.ic_my_photo_default).into(VoiceCallService.this.ivMultiHeader);
                            break;
                    }
                    if (VoiceCallService.this.iVoiceCall.isSingle()) {
                        return;
                    }
                    if (!VoiceCallService.this.iVoiceCall.isCalling()) {
                        if (VoiceCallService.this.isTalk) {
                            if (VoiceCallService.this.mMemberAdapter.getData().size() != member.size()) {
                                VoiceCallService.this.setGridLayoutManager(member.size());
                            }
                            VoiceCallService.this.mMemberAdapter.replaceData(member);
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        for (VoiceCallMember voiceCallMember3 : member) {
                            if (arrayList.size() < 5 && voiceCallMember3.getUserid() != Injection.repo().user().getUserId() && voiceCallMember3.getUserid() != VoiceCallService.this.iVoiceCall.getVoiceCallGroupUserId()) {
                                arrayList.add(voiceCallMember3);
                            }
                        }
                        VoiceCallService.this.mMemberAdapter.replaceData(arrayList);
                        VoiceCallService.this.tvMultiSub2.setText(String.format(VoiceCallService.this.getString(R.string.voice_call_str_multi_invite), Integer.valueOf(member.size() - 2)));
                        return;
                    }
                    if (VoiceCallService.this.mMemberAdapter.getData().size() != member.size()) {
                        VoiceCallService.this.setGridLayoutManager(member.size());
                    }
                    VoiceCallService.this.mMemberAdapter.replaceData(member);
                }
            }
        });
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener
    public void onInvite(String channel, boolean isSingle) {
        if (isSingle) {
            return;
        }
        this.handler.removeMessages(1);
        this.handler.sendEmptyMessageDelayed(1, 60000L);
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener
    public void onCallPending(String channel) {
        this.handler.removeMessages(1);
        this.handler.sendEmptyMessageDelayed(1, 60000L);
        if (!this.iVoiceCall.isSingle()) {
            IVoiceCall iVoiceCall = this.iVoiceCall;
            ((ObservableSubscribeProxy) iVoiceCall.messageGroupPull(iVoiceCall.getGroupId()).compose(RxUtils.io_io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>(this) { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.16
                @Override // io.reactivex.functions.Consumer
                public void accept(Object o) throws Exception {
                }
            });
        } else {
            ((ObservableSubscribeProxy) this.iVoiceCall.messageUserPull(Long.parseLong(channel)).compose(RxUtils.io_io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>(this) { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.17
                @Override // io.reactivex.functions.Consumer
                public void accept(Object o) throws Exception {
                }
            });
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener
    public void showDialog() {
        if (IntercomManager.getInstance().isMonitoring() || IntercomManager.getInstance().isCalling()) {
            CallInviteDialog onConfirmListener = CallInviteDialog.asDefault().setOnConfirmListener(new AnonymousClass18());
            this.callInviteDialog = onConfirmListener;
            onConfirmListener.showDialog((AppCompatActivity) ActivityUtils.getTopActivity());
        } else {
            showPermissionDialog();
            this.iVoiceCall.showRingtone();
        }
    }

    /* renamed from: com.ltech.smarthome.ui.voicecall.VoiceCallService$18, reason: invalid class name */
    class AnonymousClass18 implements CallInviteDialog.OnConfirmListener {
        AnonymousClass18() {
        }

        @Override // com.ltech.smarthome.view.dialog.CallInviteDialog.OnConfirmListener
        public void onConfirm() {
            IntercomManager.getInstance().stopIntercom();
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService$18$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VoiceCallService.AnonymousClass18.this.lambda$onConfirm$0();
                }
            }, 500L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfirm$0() {
            VoiceCallService.this.showDialogAndAnswer();
        }

        @Override // com.ltech.smarthome.view.dialog.CallInviteDialog.OnConfirmListener
        public void onCancel() {
            VoiceCallService.this.handler.removeMessages(3);
            if (VoiceCallService.this.iVoiceCall.isSingle()) {
                if (VoiceCallService.this.iVoiceCall.isCalling()) {
                    VoiceCallService.this.iVoiceCall.term(VoiceCallService.this.getString(R.string.app_str_voice_call_other_has_cut), true);
                    return;
                } else if (VoiceCallService.this.isTalk) {
                    VoiceCallService.this.iVoiceCall.term(VoiceCallService.this.getString(R.string.app_str_voice_call_other_has_cut), true);
                    return;
                } else {
                    VoiceCallService.this.iVoiceCall.term(VoiceCallService.this.getString(R.string.app_str_voice_call_other_has_refuse), true);
                    return;
                }
            }
            if (VoiceCallService.this.iVoiceCall.isCalling()) {
                VoiceCallService.this.iVoiceCall.stop();
            } else {
                VoiceCallService.this.iVoiceCall.leave();
            }
        }
    }

    private void showPermissionDialog() {
        if (!checkPermission()) {
            MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), getString(R.string.app_str_voice_call_record), getString(R.string.app_str_voice_call_record_tip)).setCancelable(false).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService$$ExternalSyntheticLambda1
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public final boolean onClick(BaseDialog baseDialog, View view) {
                    boolean lambda$showPermissionDialog$1;
                    lambda$showPermissionDialog$1 = VoiceCallService.this.lambda$showPermissionDialog$1(baseDialog, view);
                    return lambda$showPermissionDialog$1;
                }
            }).setCancelable(false);
        } else {
            setTopApp(MyApplication.getContext());
            showFloatingWindow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showPermissionDialog$1(BaseDialog baseDialog, View view) {
        AndPermission.with(ActivityUtils.getTopActivity()).runtime().permission(Permission.RECORD_AUDIO).onGranted(new Action<List<String>>() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService.19
            @Override // com.yanzhenjie.permission.Action
            public void onAction(List<String> data) {
                VoiceCallService.this.setTopApp(MyApplication.getContext());
                VoiceCallService.this.showFloatingWindow();
            }
        }).onDenied(new Action() { // from class: com.ltech.smarthome.ui.voicecall.VoiceCallService$$ExternalSyntheticLambda2
            @Override // com.yanzhenjie.permission.Action
            public final void onAction(Object obj) {
                VoiceCallService.this.lambda$showPermissionDialog$0((List) obj);
            }
        }).start();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPermissionDialog$0(List list) {
        cut();
    }

    private boolean checkPermission() {
        int checkSelfPermission;
        int checkSelfPermission2;
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        checkSelfPermission = checkSelfPermission(Permission.RECORD_AUDIO);
        if (checkSelfPermission != 0) {
            return false;
        }
        checkSelfPermission2 = checkSelfPermission(Permission.CAMERA);
        return checkSelfPermission2 == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDialogAndAnswer() {
        setTopApp(MyApplication.getContext());
        showFloatingWindow();
        this.handler.removeMessages(3);
        if (this.iVoiceCall.isSingle()) {
            this.iVoiceCall.answer();
            return;
        }
        if (this.iVoiceCall.getGroupId() > 0) {
            this.iVoiceCall.join(this.iVoiceCall.getGroupId() + "");
        }
    }

    @Override // com.zhuhai.ltech.lt_voice_call_api.ifun.OnVoiceCallListener
    public void dismissDialog() {
        CallInviteDialog callInviteDialog = this.callInviteDialog;
        if (callInviteDialog != null) {
            callInviteDialog.dismissDialog();
        }
        this.time = 0;
        this.isTalk = false;
        this.tempMic = false;
        this.handler.removeMessages(4);
        TextView textView = this.tvSingleSub;
        if (textView != null) {
            textView.setText("");
        }
        TextView textView2 = this.tvMultiName;
        if (textView2 != null) {
            textView2.setText("");
        }
        TextView textView3 = this.tvSingleName;
        if (textView3 != null) {
            textView3.setText("");
        }
        TextView textView4 = this.tvMultiSub;
        if (textView4 != null) {
            textView4.setText("");
        }
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeMessages(1);
            this.handler.removeMessages(2);
            this.handler.removeMessages(3);
        }
        IVoiceCall iVoiceCall = this.iVoiceCall;
        if (iVoiceCall == null || iVoiceCall.getWindowManager() == null || this.iVoiceCall.getCallWindow() == null || !this.iVoiceCall.getCallWindow().isAttachedToWindow()) {
            return;
        }
        this.iVoiceCall.getWindowManager().removeView(this.iVoiceCall.getCallWindow());
        this.iVoiceCall.setWindow(null);
    }

    private void startForeground() {
        String str;
        if (Build.VERSION.SDK_INT >= 26) {
            str = createNotificationChannel("notification channel", "notification description");
        } else {
            str = "";
        }
        startForeground(10, new NotificationCompat.Builder(this, str).setContentText(getString(R.string.voice_call_str)).setPriority(-2).setSmallIcon(R.mipmap.ic_lhome).setCategory("service").build());
    }

    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel m2 = GuideView$$ExternalSyntheticApiModelOutline0.m(channelId, channelName, 0);
        m2.setLightColor(-16776961);
        m2.setLockscreenVisibility(0);
        ((NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION)).createNotificationChannel(m2);
        return channelId;
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
                this.handler.sendEmptyMessageDelayed(4, 1000L);
                return;
            }
        }
    }
}