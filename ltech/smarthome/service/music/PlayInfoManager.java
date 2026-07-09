package com.ltech.smarthome.service.music;

import com.ltech.smarthome.utils.SharedPreferenceUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* loaded from: classes4.dex */
public class PlayInfoManager<M> {
    private static final String PLAY_INDEX = "play_index";
    private static final String REPEAT_MODE = "repeate_mode";
    private List<M> mPlayList = new ArrayList();
    private int mPlayIndex = 0;
    private int mRepeatMode = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
        public static final int LIST_LOOP = 0;
        public static final int ONE_LOOP = 1;
        public static final int RANDOM = 2;
    }

    public void init() {
        this.mPlayIndex = SharedPreferenceUtil.queryIntValue(PLAY_INDEX);
        this.mRepeatMode = SharedPreferenceUtil.queryIntValue(REPEAT_MODE);
    }

    public void changeMode(int repeatMode) {
        this.mRepeatMode = repeatMode;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    public void setPlayList(List<M> list) {
        this.mPlayList.clear();
        this.mPlayList.addAll(list);
    }

    public List<M> getPlayList() {
        return this.mPlayList;
    }

    public void setPlayIndex(int playIndex) {
        this.mPlayIndex = playIndex;
    }

    public int getPlayIndex() {
        return this.mPlayIndex;
    }

    public M getCurrentPlayingMusic() {
        int i = this.mPlayIndex;
        if (i < 0 || i >= getPlayList().size()) {
            this.mPlayIndex = 0;
        }
        return getPlayList().get(this.mPlayIndex);
    }

    public void countNextIndex() {
        if (this.mPlayIndex == getPlayList().size() - 1) {
            this.mPlayIndex = 0;
        } else {
            this.mPlayIndex++;
        }
    }

    public void countPreviousIndex() {
        int i = this.mPlayIndex;
        if (i == 0) {
            this.mPlayIndex = getPlayList().size() - 1;
        } else {
            this.mPlayIndex = i - 1;
        }
    }

    public void randomIndex() {
        this.mPlayIndex = new Random().nextInt(getPlayList().size());
    }

    public void clear() {
        saveRecord();
    }

    private void saveRecord() {
        SharedPreferenceUtil.edit().keepShared(PLAY_INDEX, this.mPlayIndex).keepShared(REPEAT_MODE, this.mRepeatMode).apply();
    }
}