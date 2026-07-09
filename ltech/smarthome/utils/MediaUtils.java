package com.ltech.smarthome.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

/* loaded from: classes4.dex */
public class MediaUtils {
    private static MediaUtils mMediaUtils;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public MediaUtils(Context context) {
        this.mContext = context.getApplicationContext();
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mMediaPlayer = mediaPlayer;
        mediaPlayer.setAudioStreamType(3);
    }

    public static MediaUtils get(Context context) {
        if (mMediaUtils == null) {
            synchronized (MediaUtils.class) {
                if (mMediaUtils == null) {
                    mMediaUtils = new MediaUtils(context.getApplicationContext());
                }
            }
        }
        return mMediaUtils;
    }

    public void playMedia(int resourcesId) {
        if (this.mMediaPlayer.isPlaying()) {
            return;
        }
        playMedia(resourcesId, false, null);
    }

    public synchronized void playMedia(int resourcesId, boolean isLooping, Integer streamType) {
        if (streamType != null) {
            try {
                this.mMediaPlayer.reset();
                this.mMediaPlayer.setAudioStreamType(streamType.intValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        AssetFileDescriptor openRawResourceFd = this.mContext.getResources().openRawResourceFd(resourcesId);
        this.mMediaPlayer.reset();
        this.mMediaPlayer.setOnCompletionListener(null);
        this.mMediaPlayer.setLooping(isLooping);
        this.mMediaPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getLength());
        openRawResourceFd.close();
        this.mMediaPlayer.prepare();
        this.mMediaPlayer.start();
    }

    public synchronized void stopPlay() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mMediaPlayer.stop();
        }
    }
}