package com.ltech.smarthome.ui.device.super_panel.music;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.SeekBar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActDcaMusicDetailBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.FastBlurUtils;
import com.ltech.smarthome.view.dialog.MusicListDialog;
import com.ltech.smarthome.view.dialog.MusicScheduledTimeDialog;
import com.ltech.smarthome.view.dialog.MusicVolumeDialog;
import com.rich.czlylibary.bean.MusicInfo;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class ActDcaMusicDetail extends VMActivity<ActDcaMusicDetailBinding, ActDcaMusicDetailVM> {
    private CountDownTimerManager countDownTimerManager;
    private CountDownTimerManager scheduledCountDownTimerManager;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dca_music_detail;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.ic_music_back);
        ((ActDcaMusicDetailBinding) this.mViewBinding).songCirclePic.setImageResource(R.mipmap.ic_music_local);
        setBlurBackground(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_music_detail));
        ((ActDcaMusicDetailVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActDcaMusicDetailVM) this.mViewModel).controlDevice = Injection.repo().device().getDeviceById(((ActDcaMusicDetailVM) this.mViewModel).controlId);
        ((ActDcaMusicDetailVM) this.mViewModel).deviceMac = getIntent().getStringExtra(Constants.MAC_ADDRESS);
        ((ActDcaMusicDetailVM) this.mViewModel).songId = getIntent().getStringExtra(Constants.MUSIC_SONG_ID);
        if (getIntent().getBooleanExtra(Constants.MUSIC_NEED_ORDER_MUSIC, false)) {
            ((ActDcaMusicDetailVM) this.mViewModel).initSongData();
        } else {
            ((ActDcaMusicDetailVM) this.mViewModel).getSongFromBle();
        }
        getTitleBar().setEditAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActDcaMusicDetail.this.lambda$initView$0();
            }
        }));
        ((ActDcaMusicDetailBinding) this.mViewBinding).sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean b2) {
                if (b2 && ActDcaMusicDetail.this.countDownTimerManager != null) {
                    ActDcaMusicDetail.this.countDownTimerManager.cancel();
                }
                ((ActDcaMusicDetailBinding) ActDcaMusicDetail.this.mViewBinding).tvTimeStart.setText(ActDcaMusicDetail.this.formatTime(seekBar.getProgress()));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((ActDcaMusicDetailVM) ActDcaMusicDetail.this.mViewModel).setSongTime(seekBar.getProgress());
                ActDcaMusicDetail.this.changeCountdownTime(((ActDcaMusicDetailVM) r0.mViewModel).songLength - seekBar.getProgress(), seekBar.getProgress());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        ((ActDcaMusicDetailVM) this.mViewModel).setCollect();
    }

    private void playOrPauseMusic() {
        if (((ActDcaMusicDetailVM) this.mViewModel).isPlaying.getValue().booleanValue()) {
            CountDownTimerManager countDownTimerManager = this.countDownTimerManager;
            if (countDownTimerManager != null) {
                countDownTimerManager.resume();
            }
            ((ActDcaMusicDetailBinding) this.mViewBinding).songCirclePic.playMusic();
            return;
        }
        CountDownTimerManager countDownTimerManager2 = this.countDownTimerManager;
        if (countDownTimerManager2 != null) {
            countDownTimerManager2.pause();
        }
        ((ActDcaMusicDetailBinding) this.mViewBinding).songCirclePic.pauseMusic();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActDcaMusicDetailVM) this.mViewModel).musicData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$1((MusicInfo) obj);
            }
        });
        ((ActDcaMusicDetailVM) this.mViewModel).showMusicData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$2((MusicInfo) obj);
            }
        });
        ((ActDcaMusicDetailVM) this.mViewModel).updateCollectData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$3((Boolean) obj);
            }
        });
        ((ActDcaMusicDetailVM) this.mViewModel).showVolumeClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$5((Void) obj);
            }
        });
        ((ActDcaMusicDetailVM) this.mViewModel).showTimeClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda9
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$7((Void) obj);
            }
        });
        ((ActDcaMusicDetailVM) this.mViewModel).showMusicListClickEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda10
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$10((Void) obj);
            }
        });
        ((ActDcaMusicDetailVM) this.mViewModel).isPlaying.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda11
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$11((Boolean) obj);
            }
        });
        ((ActDcaMusicDetailVM) this.mViewModel).playModeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda12
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$12((Integer) obj);
            }
        });
        ((ActDcaMusicDetailVM) this.mViewModel).refreshScheduledTimeEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda13
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActDcaMusicDetail.this.lambda$startObserve$13((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(MusicInfo musicInfo) {
        setTitle(musicInfo.getMusicName());
        ((ActDcaMusicDetailBinding) this.mViewBinding).tvSinger.setText(musicInfo.getSingerName());
        ((ActDcaMusicDetailVM) this.mViewModel).songLength = StringUtils.stringTimeToIntSecond(musicInfo.getLength());
        ((ActDcaMusicDetailBinding) this.mViewBinding).sbMusic.setMax(((ActDcaMusicDetailVM) this.mViewModel).songLength);
        ((ActDcaMusicDetailBinding) this.mViewBinding).tvTimeEnd.setText(formatTime(((ActDcaMusicDetailVM) this.mViewModel).songLength));
        ((ActDcaMusicDetailBinding) this.mViewBinding).songCirclePic.init();
        changeCountdownTime(((ActDcaMusicDetailVM) this.mViewModel).songLength - ((ActDcaMusicDetailVM) this.mViewModel).CurTime, ((ActDcaMusicDetailVM) this.mViewModel).CurTime);
        playOrPauseMusic();
        if (!((ActDcaMusicDetailVM) this.mViewModel).isLocal(musicInfo.getMusicId())) {
            setEditImage(musicInfo.getIsCollection().equalsIgnoreCase("1") ? R.mipmap.ic_favorite_music : R.mipmap.ic_not_favorite);
            Glide.with((FragmentActivity) this).asBitmap().load(musicInfo.getPicUrl()).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail.2
                @Override // com.bumptech.glide.request.target.Target
                public void onLoadCleared(Drawable placeholder) {
                }

                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                    onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    ((ActDcaMusicDetailBinding) ActDcaMusicDetail.this.mViewBinding).songCirclePic.setImageDrawable(new BitmapDrawable(ActDcaMusicDetail.this.getResources(), resource));
                    ActDcaMusicDetail.this.setBlurBackground(resource);
                }
            });
        } else {
            setEditImage(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(MusicInfo musicInfo) {
        setTitle(musicInfo.getMusicName());
        ((ActDcaMusicDetailBinding) this.mViewBinding).tvSinger.setText(musicInfo.getSingerName());
        ((ActDcaMusicDetailVM) this.mViewModel).songLength = StringUtils.stringTimeToIntSecond(musicInfo.getLength());
        ((ActDcaMusicDetailBinding) this.mViewBinding).sbMusic.setMax(((ActDcaMusicDetailVM) this.mViewModel).songLength);
        ((ActDcaMusicDetailBinding) this.mViewBinding).tvTimeEnd.setText(formatTime(((ActDcaMusicDetailVM) this.mViewModel).songLength));
        if (!((ActDcaMusicDetailVM) this.mViewModel).isLocal) {
            setEditImage(musicInfo.getIsCollection().equalsIgnoreCase("1") ? R.mipmap.ic_favorite_music : R.mipmap.ic_not_favorite);
            Glide.with((FragmentActivity) this).asBitmap().load(musicInfo.getPicUrl()).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail.3
                @Override // com.bumptech.glide.request.target.Target
                public void onLoadCleared(Drawable placeholder) {
                }

                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                    onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    ((ActDcaMusicDetailBinding) ActDcaMusicDetail.this.mViewBinding).songCirclePic.setImageDrawable(new BitmapDrawable(ActDcaMusicDetail.this.getResources(), resource));
                    ActDcaMusicDetail.this.setBlurBackground(resource);
                }
            });
        } else {
            setEditImage(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        if (!((ActDcaMusicDetailVM) this.mViewModel).isLocal) {
            setEditImage(bool.booleanValue() ? R.mipmap.ic_favorite_music : R.mipmap.ic_not_favorite);
            Intent intent = new Intent();
            intent.putExtra(Constants.MUSIC_FAVORITE_STATE, bool);
            setResult(Constants.SELECT_MUSIC_FAVORITE_STATE, intent);
            return;
        }
        setEditImage(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r2) {
        MusicVolumeDialog.asDefault().setVolume(((ActDcaMusicDetailVM) this.mViewModel).curVolume).setOnVolumeChangeCallback(new MusicVolumeDialog.OnVolumeChangeCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.view.dialog.MusicVolumeDialog.OnVolumeChangeCallback
            public final void onVolumeChange(int i) {
                ActDcaMusicDetail.this.lambda$startObserve$4(i);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(int i) {
        ((ActDcaMusicDetailVM) this.mViewModel).setVolume(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$7(Void r2) {
        MusicScheduledTimeDialog.asDefault().setMinList(Arrays.asList(getResources().getStringArray(R.array.music_time_list))).setScheduledSetTime(((ActDcaMusicDetailVM) this.mViewModel).curScheduledSetTime).setScheduledTimeLeft(((ActDcaMusicDetailVM) this.mViewModel).curScheduledTimeLeft).setOnTimeClickCallback(new MusicScheduledTimeDialog.OnTimeClickCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.view.dialog.MusicScheduledTimeDialog.OnTimeClickCallback
            public final void onTimeClick(int i) {
                ActDcaMusicDetail.this.lambda$startObserve$6(i);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$6(int i) {
        ((ActDcaMusicDetailVM) this.mViewModel).setCloseTime(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$10(Void r2) {
        MusicListDialog.asDefault().setDeviceMac(((ActDcaMusicDetailVM) this.mViewModel).deviceMac).setMusicId(((ActDcaMusicDetailVM) this.mViewModel).songId).setCurMode(((ActDcaMusicDetailVM) this.mViewModel).curMode).setControlDevice(((ActDcaMusicDetailVM) this.mViewModel).controlDevice).setOnDismissCallback(new MusicListDialog.OnDismissCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.dialog.MusicListDialog.OnDismissCallback
            public final void onDismiss(String str) {
                ActDcaMusicDetail.this.lambda$startObserve$8(str);
            }
        }).setOnMusicChangeCallback(new MusicListDialog.OnMusicChangeCallback() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.dialog.MusicListDialog.OnMusicChangeCallback
            public final void onChange(ResponseMsg responseMsg, String str) {
                ActDcaMusicDetail.this.lambda$startObserve$9(responseMsg, str);
            }
        }).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$8(String str) {
        if (!str.equals(((ActDcaMusicDetailVM) this.mViewModel).songId)) {
            ((ActDcaMusicDetailVM) this.mViewModel).getSongFromBle();
        }
        onResume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(ResponseMsg responseMsg, String str) {
        ((ActDcaMusicDetailVM) this.mViewModel).refreshPanelData(responseMsg, false);
        ((ActDcaMusicDetailVM) this.mViewModel).songId = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$11(Boolean bool) {
        playOrPauseMusic();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$12(Integer num) {
        int intValue = num.intValue();
        if (intValue == 1) {
            ((ActDcaMusicDetailBinding) this.mViewBinding).ivMode.setImageResource(R.mipmap.ic_music_detail_cycle);
            return;
        }
        if (intValue == 2) {
            ((ActDcaMusicDetailBinding) this.mViewBinding).ivMode.setImageResource(R.mipmap.ic_music_detail_random);
        } else if (intValue == 3) {
            ((ActDcaMusicDetailBinding) this.mViewBinding).ivMode.setImageResource(R.mipmap.ic_music_detail_play_once);
        } else {
            ((ActDcaMusicDetailBinding) this.mViewBinding).ivMode.setImageResource(R.mipmap.ic_music_detail_playback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$13(Integer num) {
        changeScheduledCountdownTime(((ActDcaMusicDetailVM) this.mViewModel).curScheduledTimeLeft);
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        MessageManager.getInstance().setSmartPanelMusicCallBack(new MessageManager.SmartPanelMusicCallBack() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail$$ExternalSyntheticLambda4
            @Override // com.smart.message.MessageManager.SmartPanelMusicCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                ActDcaMusicDetail.this.lambda$onResume$14(responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$14(ResponseMsg responseMsg) {
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(6, 10), 16);
        if ((ActivityUtils.getTopActivity() instanceof ActDcaMusicDetail) && ((ActDcaMusicDetailVM) this.mViewModel).controlDevice != null && parseInt == ((BleParam) ((ActDcaMusicDetailVM) this.mViewModel).controlDevice.getParam(BleParam.class)).getUnicastAddress()) {
            ((ActDcaMusicDetailVM) this.mViewModel).refreshPanelData(responseMsg, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setBlurBackground(Bitmap bitmap) {
        Glide.with((FragmentActivity) this).asBitmap().load(bitmap).into((RequestBuilder<Bitmap>) new CustomTarget<Bitmap>() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail.4
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadCleared(Drawable placeholder) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object resource, Transition transition) {
                onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                ((ActDcaMusicDetailBinding) ActDcaMusicDetail.this.mViewBinding).layoutMusicDetail.setBackground(new BitmapDrawable(ActDcaMusicDetail.this.getResources(), FastBlurUtils.startBlurBackground(resource)));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String formatTime(int seconds) {
        return String.format("%02d:%02d", Integer.valueOf(seconds / 60), Integer.valueOf(seconds % 60));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeCountdownTime(final long newTime, final int alreadyRunTime) {
        CountDownTimerManager countDownTimerManager = this.countDownTimerManager;
        if (countDownTimerManager != null) {
            countDownTimerManager.cancel();
        }
        long j = 1000 * newTime;
        CountDownTimerManager countDownTimerManager2 = new CountDownTimerManager(j, 1000L, new CountDownTimerManager.OnCountDownTimerListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail.5
            @Override // com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager.OnCountDownTimerListener
            public void onFinish() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager.OnCountDownTimerListener
            public void onTick(long millisUntilFinished) {
                ActDcaMusicDetail.this.updateProgressBar(newTime, millisUntilFinished, alreadyRunTime);
            }
        });
        this.countDownTimerManager = countDownTimerManager2;
        countDownTimerManager2.start();
        if (((ActDcaMusicDetailVM) this.mViewModel).isPlaying.getValue().booleanValue()) {
            return;
        }
        this.countDownTimerManager.pause();
        updateProgressBar(newTime, j, alreadyRunTime);
    }

    private void changeScheduledCountdownTime(long leftTime) {
        CountDownTimerManager countDownTimerManager = this.scheduledCountDownTimerManager;
        if (countDownTimerManager != null) {
            countDownTimerManager.cancel();
        }
        CountDownTimerManager countDownTimerManager2 = new CountDownTimerManager(1000 * leftTime, 1000L, new CountDownTimerManager.OnCountDownTimerListener() { // from class: com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicDetail.6
            @Override // com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager.OnCountDownTimerListener
            public void onFinish() {
            }

            @Override // com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager.OnCountDownTimerListener
            public void onTick(long millisUntilFinished) {
                if (((ActDcaMusicDetailVM) ActDcaMusicDetail.this.mViewModel).curScheduledTimeLeft > 0) {
                    ActDcaMusicDetailVM actDcaMusicDetailVM = (ActDcaMusicDetailVM) ActDcaMusicDetail.this.mViewModel;
                    actDcaMusicDetailVM.curScheduledTimeLeft--;
                }
            }
        });
        this.scheduledCountDownTimerManager = countDownTimerManager2;
        countDownTimerManager2.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgressBar(long newTime, long millisUntilFinished, int alreadyRunTime) {
        ((ActDcaMusicDetailBinding) this.mViewBinding).sbMusic.setProgress((alreadyRunTime + ((int) newTime)) - Math.round(millisUntilFinished / 1000.0f));
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        CountDownTimerManager countDownTimerManager = this.countDownTimerManager;
        if (countDownTimerManager != null) {
            countDownTimerManager.cancel();
        }
    }
}