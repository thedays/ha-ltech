package com.ltech.smarthome.service.music;

import android.content.Context;
import java.util.List;

/* loaded from: classes4.dex */
public interface IPlayerController<M> extends ILiveDataNotifier<M> {
    void changeMode();

    void changeMode(int mode);

    void clear();

    List<M> getSavePlayList();

    void init(Context context);

    boolean isPlaying();

    void pauseAudio();

    void playAudio();

    void playAudio(int index);

    void playNext();

    void playPrevious();

    void playRandom();

    void reset();

    void resumeAudio();

    void savePlayList(List<M> list);

    void setPlayList(List<M> list);

    void setSeek(int progress);

    void togglePlay();
}