package com.ltech.smarthome.service.music;

import android.content.Context;
import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.preference_bean.MusicBean;
import com.ltech.smarthome.preference_bean.SelectMusicBean;
import com.ltech.smarthome.service.music.MediaPlayerHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PlayerController implements IPlayerController<MusicBean> {
    private boolean mIsChangingPlayingMusic;
    private boolean mIsPaused;
    private PlayInfoManager<MusicBean> mPlayInfoManager = new PlayInfoManager<>();
    private MutableLiveData<Boolean> pauseLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> playModeLiveData = new MutableLiveData<>();
    private MutableLiveData<PlayingMusic> playingMusicLiveData = new MutableLiveData<>();
    private MutableLiveData<MusicBean> changePlayMusicLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MusicBean>> playListLiveData = new MutableLiveData<>();
    private MutableLiveData<Float> visualizerLiveData = new MutableLiveData<>();
    private PlayingMusic mCurrentPlay = new PlayingMusic("00:00", "00:00");
    private MusicBean mChangePlayMusic = new MusicBean();

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void init(Context context) {
        this.mPlayInfoManager.init();
        this.playModeLiveData.setValue(Integer.valueOf(this.mPlayInfoManager.getRepeatMode()));
        MediaPlayerHelper.getInstance().setMediaPlayerHelperCallBack(new MediaPlayerHelper.MediaPlayerHelperCallBack() { // from class: com.ltech.smarthome.service.music.PlayerController$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.service.music.MediaPlayerHelper.MediaPlayerHelperCallBack
            public final void onCallBack(int i, MediaPlayerHelper mediaPlayerHelper, Object[] objArr) {
                PlayerController.this.lambda$init$0(i, mediaPlayerHelper, objArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i, MediaPlayerHelper mediaPlayerHelper, Object[] objArr) {
        switch (i) {
            case 100:
                if (!this.mIsPaused) {
                    mediaPlayerHelper.start();
                    break;
                }
                break;
            case 101:
                if (!this.mPlayInfoManager.getPlayList().isEmpty()) {
                    if (this.mPlayInfoManager.getRepeatMode() == 1) {
                        playCurrent();
                        break;
                    } else if (this.mPlayInfoManager.getRepeatMode() == 2) {
                        playRandom();
                        break;
                    } else {
                        playNext();
                        break;
                    }
                }
                break;
            case 102:
                reset();
                SmartToast.showShort("播放失败");
                break;
            case 104:
                if (objArr != null && objArr.length == 2) {
                    refreshPlayMusicInfo(this.mCurrentPlay, ((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue());
                    break;
                }
                break;
            case 106:
                MutableLiveData<Float> mutableLiveData = this.visualizerLiveData;
                Float f = (Float) objArr[0];
                f.floatValue();
                mutableLiveData.setValue(f);
                break;
        }
    }

    private void playCurrent() {
        setChangePlayingMusic(true);
        playAudio();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void setPlayList(List<MusicBean> list) {
        this.mPlayInfoManager.setPlayList(list);
        this.playListLiveData.setValue(list);
        if (list.isEmpty()) {
            return;
        }
        setChangePlayingMusic(true);
        if (isPlaying()) {
            playAudio();
        }
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public List<MusicBean> getSavePlayList() {
        SelectMusicBean selectMusicBean = (SelectMusicBean) SharedPreferenceUtil.getBean(Constants.SELECT_MUSIC_LIST, SelectMusicBean.class);
        if (selectMusicBean != null) {
            return selectMusicBean.musicList;
        }
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void savePlayList(List<MusicBean> list) {
        SelectMusicBean selectMusicBean = (SelectMusicBean) SharedPreferenceUtil.getBean(Constants.SELECT_MUSIC_LIST, SelectMusicBean.class);
        if (selectMusicBean == null) {
            selectMusicBean = new SelectMusicBean();
        }
        selectMusicBean.musicList = list;
        SharedPreferenceUtil.edit().putBean(Constants.SELECT_MUSIC_LIST, selectMusicBean);
        setPlayList(list);
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playAudio() {
        if (this.mIsChangingPlayingMusic) {
            stopAudio();
            play();
        } else if (this.mIsPaused) {
            resumeAudio();
        }
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playAudio(int index) {
        if (isPlaying() && index == this.mPlayInfoManager.getPlayIndex()) {
            return;
        }
        this.mPlayInfoManager.setPlayIndex(index);
        playCurrent();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void togglePlay() {
        if (isPlaying()) {
            pauseAudio();
        } else {
            playAudio();
        }
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playNext() {
        this.mPlayInfoManager.countNextIndex();
        playCurrent();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playPrevious() {
        this.mPlayInfoManager.countPreviousIndex();
        playCurrent();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void playRandom() {
        this.mPlayInfoManager.randomIndex();
        playCurrent();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void pauseAudio() {
        MediaPlayerHelper.getInstance().pause();
        this.mIsPaused = true;
        this.pauseLiveData.setValue(true);
    }

    private void stopAudio() {
        MediaPlayerHelper.getInstance().reset();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void resumeAudio() {
        MediaPlayerHelper.getInstance().start();
        this.mIsPaused = false;
        this.pauseLiveData.setValue(false);
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void clear() {
        MediaPlayerHelper.getInstance().release();
        this.mPlayInfoManager.clear();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void changeMode() {
        if (this.mPlayInfoManager.getRepeatMode() == 0) {
            changeMode(1);
        } else if (1 == this.mPlayInfoManager.getRepeatMode()) {
            changeMode(2);
        } else {
            changeMode(0);
        }
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void changeMode(int mode) {
        this.mPlayInfoManager.changeMode(mode);
        this.playModeLiveData.setValue(Integer.valueOf(mode));
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void setSeek(int progress) {
        MediaPlayerHelper.getInstance().seekTo(progress);
        PlayingMusic playingMusic = this.mCurrentPlay;
        refreshPlayMusicInfo(playingMusic, progress, (int) playingMusic.getDuration());
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public boolean isPlaying() {
        return MediaPlayerHelper.getInstance().isPlaying();
    }

    @Override // com.ltech.smarthome.service.music.IPlayerController
    public void reset() {
        stopAudio();
        this.mIsPaused = true;
        this.pauseLiveData.setValue(true);
        setChangePlayingMusic(true);
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<Boolean> getPauseLiveData() {
        return this.pauseLiveData;
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<Integer> getPlayModeLiveData() {
        return this.playModeLiveData;
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<PlayingMusic> getCurrentPlayMusic() {
        return this.playingMusicLiveData;
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<MusicBean> getChangePlayMusic() {
        return this.changePlayMusicLiveData;
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<Float> getVisualizerValue() {
        return this.visualizerLiveData;
    }

    @Override // com.ltech.smarthome.service.music.ILiveDataNotifier
    public MutableLiveData<List<MusicBean>> getPlayList() {
        return this.playListLiveData;
    }

    private void setChangePlayingMusic(boolean change) {
        this.mIsChangingPlayingMusic = change;
        if (change) {
            if (this.mPlayInfoManager.getPlayList().isEmpty()) {
                this.playingMusicLiveData.setValue(new PlayingMusic("", ""));
                this.changePlayMusicLiveData.setValue(new MusicBean());
            } else {
                this.mChangePlayMusic.setBaseInfo(this.mPlayInfoManager.getCurrentPlayingMusic());
                this.changePlayMusicLiveData.setValue(this.mChangePlayMusic);
                MusicBean musicBean = this.mChangePlayMusic;
                refreshPlayMusicInfo(musicBean, 0, (int) musicBean.getDuration());
            }
        }
    }

    private void play() {
        MusicBean currentPlayingMusic = this.mPlayInfoManager.getCurrentPlayingMusic();
        if (currentPlayingMusic == null || TextUtils.isEmpty(currentPlayingMusic.getTitle())) {
            pauseAudio();
            return;
        }
        this.mIsPaused = false;
        this.pauseLiveData.setValue(false);
        setChangePlayingMusic(false);
        MediaPlayerHelper.getInstance().play(currentPlayingMusic.getPath());
    }

    private void refreshPlayMusicInfo(MusicBean playingMusic, int curPosition, int duration) {
        this.mCurrentPlay.setNowTime(calculateTime(curPosition / 1000));
        this.mCurrentPlay.setAllTime(calculateTime(duration / 1000));
        this.mCurrentPlay.setCurPosition(curPosition);
        this.mCurrentPlay.setBaseInfo(playingMusic);
        this.playingMusicLiveData.setValue(this.mCurrentPlay);
    }

    private String calculateTime(int time) {
        if (time < 60) {
            if (time < 10) {
                return "00:0" + time;
            }
            return "00:" + time;
        }
        int i = time / 60;
        int i2 = time % 60;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = i < 10 ? new StringBuilder("0") : new StringBuilder("");
        sb2.append(i);
        sb.append(sb2.toString());
        StringBuilder sb3 = i2 < 10 ? new StringBuilder(":0") : new StringBuilder(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
        sb3.append(i2);
        sb.append(sb3.toString());
        return sb.toString();
    }
}