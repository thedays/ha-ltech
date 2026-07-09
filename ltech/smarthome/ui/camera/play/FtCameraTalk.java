package com.ltech.smarthome.ui.camera.play;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtCameraTalkBinding;
import com.smart.message.utils.LHomeLog;
import com.videogo.openapi.EZPlayer;

/* loaded from: classes4.dex */
public class FtCameraTalk extends BaseNormalFragment<FtCameraTalkBinding> {
    private ActCameraPlay activity;
    private EZPlayer mEZPlayer;
    private View.OnTouchListener mOnHalfTouchListener = new View.OnTouchListener() { // from class: com.ltech.smarthome.ui.camera.play.FtCameraTalk.1
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v, MotionEvent event) {
            LHomeLog.i(FtCameraTalk.class, "mOnTouchListener:" + event.getAction());
            int action = event.getAction();
            if (action == 0) {
                FtCameraTalk.this.mEZPlayer.setVoiceTalkStatus(true);
                FtCameraTalk.this.changeTalkView(true);
            } else if (action == 1 || action == 3) {
                FtCameraTalk.this.mEZPlayer.setVoiceTalkStatus(false);
                FtCameraTalk.this.changeTalkView(false);
            }
            return true;
        }
    };
    private View.OnClickListener mOnFullClickListener = new View.OnClickListener() { // from class: com.ltech.smarthome.ui.camera.play.FtCameraTalk.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (FtCameraTalk.this.activity.isTalk) {
                FtCameraTalk.this.activity.controlTalk(false);
            } else {
                FtCameraTalk.this.activity.controlTalk(true);
            }
        }
    };

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_camera_talk;
    }

    public static FtCameraTalk newInstance() {
        return new FtCameraTalk();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        this.mEZPlayer = this.activity.getPlayer();
        if (this.activity.getSupportTalk() == 1) {
            ((FtCameraTalkBinding) this.mViewBinding).talkView.setOnClickListener(this.mOnFullClickListener);
        } else {
            ((FtCameraTalkBinding) this.mViewBinding).talkView.setOnTouchListener(this.mOnHalfTouchListener);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (ActCameraPlay) getActivity();
    }

    public void changeTalkView(boolean talk) {
        if (this.mViewBinding != 0) {
            if (talk) {
                ((FtCameraTalkBinding) this.mViewBinding).spreadviewTalk.setVisibility(0);
                ((FtCameraTalkBinding) this.mViewBinding).spreadviewTalkStop.setVisibility(8);
                ((FtCameraTalkBinding) this.mViewBinding).spreadviewTalk.setAnimate(false);
                ((FtCameraTalkBinding) this.mViewBinding).spreadviewTalk.requestLayout();
                ((FtCameraTalkBinding) this.mViewBinding).spreadviewTalk.setAnimate(true);
                return;
            }
            ((FtCameraTalkBinding) this.mViewBinding).spreadviewTalk.setVisibility(8);
            ((FtCameraTalkBinding) this.mViewBinding).spreadviewTalkStop.setVisibility(0);
        }
    }
}