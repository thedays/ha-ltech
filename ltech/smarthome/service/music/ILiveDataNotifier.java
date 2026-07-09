package com.ltech.smarthome.service.music;

import androidx.lifecycle.MutableLiveData;
import java.util.List;

/* loaded from: classes4.dex */
public interface ILiveDataNotifier<M> {
    MutableLiveData<M> getChangePlayMusic();

    MutableLiveData<PlayingMusic> getCurrentPlayMusic();

    MutableLiveData<Boolean> getPauseLiveData();

    MutableLiveData<List<M>> getPlayList();

    MutableLiveData<Integer> getPlayModeLiveData();

    MutableLiveData<Float> getVisualizerValue();
}