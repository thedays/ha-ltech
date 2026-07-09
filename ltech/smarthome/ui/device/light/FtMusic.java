package com.ltech.smarthome.ui.device.light;

import android.animation.ObjectAnimator;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.FtMusicBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.preference_bean.MusicBean;
import com.ltech.smarthome.service.music.PlayingMusic;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import java.util.List;
import javax.jmdns.impl.constants.DNSConstants;

/* loaded from: classes4.dex */
public class FtMusic extends VMFragment<FtMusicBinding, FtMusicVM> {
    private ObjectAnimator coverAnimator;
    private long currentPlayTime;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_music;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected boolean useEventBus() {
        return true;
    }

    public static FtMusic newInstance(long controlId, boolean groupControl) {
        FtMusic ftMusic = new FtMusic();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.CONTROL_ID, controlId);
        bundle.putBoolean(Constants.GROUP_CONTROL, groupControl);
        ftMusic.setArguments(bundle);
        return ftMusic;
    }

    private FtMusic() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            ((FtMusicVM) this.mViewModel).controlId = arguments.getLong(Constants.CONTROL_ID);
            ((FtMusicVM) this.mViewModel).groupControl = arguments.getBoolean(Constants.GROUP_CONTROL);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        TitleDefault titleDefault = new TitleDefault();
        titleDefault.setBackImageRes(R.mipmap.icon_back_white);
        titleDefault.setTitle(getString(R.string.music));
        titleDefault.setEditImageRes(R.mipmap.icon_mic);
        titleDefault.setBackAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMusic.this.lambda$initView$0();
            }
        }));
        titleDefault.setEditAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                FtMusic.this.lambda$initView$1();
            }
        }));
        ((FtMusicBinding) this.mViewBinding).setTitle(titleDefault);
        ((FtMusicBinding) this.mViewBinding).sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(this) { // from class: com.ltech.smarthome.ui.device.light.FtMusic.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                Injection.player().setSeek(seekBar.getProgress());
            }
        });
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(((FtMusicBinding) this.mViewBinding).ivMusic, "rotation", 0.0f, 360.0f);
        this.coverAnimator = ofFloat;
        ofFloat.setDuration(DNSConstants.SERVICE_INFO_TIMEOUT);
        this.coverAnimator.setInterpolator(new LinearInterpolator());
        this.coverAnimator.setRepeatCount(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        ((FtMusicVM) this.mViewModel).finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        EventBusUtils.post(new LiveBusHelper(5, getClass().getName()));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        if (((FtMusicVM) this.mViewModel).groupControl) {
            Injection.repo().group().getGroupFromDb(((FtMusicVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda5
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    FtMusic.this.lambda$startObserve$2((Group) obj);
                }
            });
        } else {
            Injection.repo().device().getDeviceFromDb(((FtMusicVM) this.mViewModel).controlId).observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda6
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    FtMusic.this.lambda$startObserve$3((Device) obj);
                }
            });
        }
        Injection.player().setRecordAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtMusic.this.lambda$startObserve$4((Float) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Group group) {
        ((FtMusicVM) this.mViewModel).controlObject = group;
        setPlayerObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Device device) {
        ((FtMusicVM) this.mViewModel).controlObject = device;
        setPlayerObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Float f) {
        ((FtMusicVM) this.mViewModel).setVolume(getActivity(), f.floatValue());
    }

    private void setPlayerObserver() {
        Injection.player().init(Utils.getApp());
        Injection.player().refreshSaveList(getActivity());
        Injection.player().getPlayList().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMusic.this.lambda$setPlayerObserver$5((List) obj);
            }
        });
        Injection.player().getPauseLiveData().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMusic.this.lambda$setPlayerObserver$6((Boolean) obj);
            }
        });
        Injection.player().getCurrentPlayMusic().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMusic.this.lambda$setPlayerObserver$7((PlayingMusic) obj);
            }
        });
        Injection.player().getChangePlayMusic().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMusic.this.lambda$setPlayerObserver$8((MusicBean) obj);
            }
        });
        Injection.player().getPlayModeLiveData().observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.light.FtMusic$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtMusic.this.lambda$setPlayerObserver$9((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlayerObserver$5(List list) {
        ((FtMusicVM) this.mViewModel).playListEmpty = list.isEmpty();
        if (((FtMusicVM) this.mViewModel).playListEmpty) {
            ((FtMusicBinding) this.mViewBinding).sbMusic.setEnabled(false);
            ((FtMusicVM) this.mViewModel).stopRecord();
            Injection.player().reset();
            return;
        }
        ((FtMusicBinding) this.mViewBinding).sbMusic.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlayerObserver$6(Boolean bool) {
        if (bool.booleanValue()) {
            ((FtMusicBinding) this.mViewBinding).ivPlay.setImageResource(R.mipmap.ic_music_play);
            ((FtMusicVM) this.mViewModel).stopRecord();
            playAnimation(false);
        } else {
            playAnimation(true);
            ((FtMusicVM) this.mViewModel).startRecord();
            ((FtMusicBinding) this.mViewBinding).ivPlay.setImageResource(R.mipmap.ic_music_pause);
            EventBusUtils.post(new LiveBusHelper(3));
        }
        ((FtMusicVM) this.mViewModel).setPlay(getActivity(), !bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlayerObserver$7(PlayingMusic playingMusic) {
        ((FtMusicBinding) this.mViewBinding).tvMusicCurrent.setText(playingMusic.getNowTime());
        ((FtMusicBinding) this.mViewBinding).tvMusicTotal.setText(playingMusic.getAllTime());
        ((FtMusicBinding) this.mViewBinding).sbMusic.setMax((int) playingMusic.getDuration());
        ((FtMusicBinding) this.mViewBinding).sbMusic.setProgress(playingMusic.getCurPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlayerObserver$8(MusicBean musicBean) {
        byte[] embeddedPicture;
        ((FtMusicBinding) this.mViewBinding).tvMusicName.setText(musicBean.getTitle());
        ((FtMusicBinding) this.mViewBinding).tvMusicArtist.setText(musicBean.getArtist());
        if (!TextUtils.isEmpty(musicBean.getPath())) {
            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(getActivity(), Uri.parse(musicBean.getPath()));
                embeddedPicture = mediaMetadataRetriever.getEmbeddedPicture();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            Glide.with(this).load(embeddedPicture).dontAnimate().circleCrop().fallback(R.mipmap.ic_music_cover).error(R.mipmap.ic_music_cover).placeholder(R.mipmap.ic_music_cover).into(((FtMusicBinding) this.mViewBinding).ivMusic);
        }
        embeddedPicture = null;
        Glide.with(this).load(embeddedPicture).dontAnimate().circleCrop().fallback(R.mipmap.ic_music_cover).error(R.mipmap.ic_music_cover).placeholder(R.mipmap.ic_music_cover).into(((FtMusicBinding) this.mViewBinding).ivMusic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlayerObserver$9(Integer num) {
        if (2 == num.intValue()) {
            ((FtMusicBinding) this.mViewBinding).ivPlayMode.setImageResource(R.mipmap.ic_music_random);
        } else if (1 == num.intValue()) {
            ((FtMusicBinding) this.mViewBinding).ivPlayMode.setImageResource(R.mipmap.ic_music_cycle_one);
        } else {
            ((FtMusicBinding) this.mViewBinding).ivPlayMode.setImageResource(R.mipmap.ic_music_cycle);
        }
    }

    private void playAnimation(boolean start) {
        if (start) {
            this.coverAnimator.start();
            this.coverAnimator.setCurrentPlayTime(this.currentPlayTime);
        } else {
            this.currentPlayTime = this.coverAnimator.getCurrentPlayTime();
            this.coverAnimator.cancel();
        }
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        ObjectAnimator objectAnimator = this.coverAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        this.coverAnimator = null;
        ((FtMusicVM) this.mViewModel).stopRecord();
        Injection.player().clear();
        super.onDestroyView();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void handleBusEvent(LiveBusHelper helper) {
        if (4 == helper.getCode() && Injection.player().isPlaying()) {
            Injection.player().pauseAudio();
        }
    }
}