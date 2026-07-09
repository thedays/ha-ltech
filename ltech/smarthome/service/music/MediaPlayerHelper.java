package com.ltech.smarthome.service.music;

import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Handler;
import com.blankj.utilcode.util.Utils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class MediaPlayerHelper implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener {
    private static volatile MediaPlayerHelper sInstance;
    private Holder mHolder;
    private MediaPlayerHelperCallBack mMediaPlayerHelperCallBack;
    private int seekProgress = -1;
    private Handler refreshTimeHandler = new Handler();
    private Runnable refreshTimeRunnable = new Runnable() { // from class: com.ltech.smarthome.service.music.MediaPlayerHelper.1
        @Override // java.lang.Runnable
        public void run() {
            MediaPlayerHelper.this.refreshTimeHandler.removeCallbacks(MediaPlayerHelper.this.refreshTimeRunnable);
            if (MediaPlayerHelper.this.mHolder.player != null && MediaPlayerHelper.this.mHolder.player.isPlaying()) {
                MediaPlayerHelper mediaPlayerHelper = MediaPlayerHelper.this;
                mediaPlayerHelper.callBack(104, Integer.valueOf(mediaPlayerHelper.mHolder.player.getCurrentPosition()), Integer.valueOf(MediaPlayerHelper.this.mHolder.player.getDuration()));
            }
            MediaPlayerHelper.this.refreshTimeHandler.postDelayed(MediaPlayerHelper.this.refreshTimeRunnable, 1000L);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallBackState {
        public static final int COMPLETE = 101;
        public static final int ERROR = 102;
        public static final int INFO = 103;
        public static final int PREPARE = 100;
        public static final int PROGRESS = 104;
        public static final int SEEK_COMPLETE = 105;
        public static final int VISUALIZER = 106;
    }

    public interface MediaPlayerHelperCallBack {
        void onCallBack(int state, MediaPlayerHelper mediaPlayerHelper, Object... args);
    }

    private void enableVisualizer(boolean enable) {
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
    }

    public static MediaPlayerHelper getInstance() {
        if (sInstance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (sInstance == null) {
                    sInstance = new MediaPlayerHelper();
                }
            }
        }
        return sInstance;
    }

    private MediaPlayerHelper() {
        Holder holder = new Holder();
        this.mHolder = holder;
        holder.player = new MediaPlayer();
        this.mHolder.player.setOnPreparedListener(this);
        this.mHolder.player.setOnErrorListener(this);
        this.mHolder.player.setOnCompletionListener(this);
        this.mHolder.player.setOnSeekCompleteListener(this);
        this.mHolder.player.setOnBufferingUpdateListener(this);
        this.mHolder.player.setOnInfoListener(this);
        this.mHolder.player.setOnVideoSizeChangedListener(this);
        setVisualizer(this.mHolder.player);
    }

    public MediaPlayerHelper setMediaPlayerHelperCallBack(MediaPlayerHelperCallBack callBack) {
        this.mMediaPlayerHelperCallBack = callBack;
        return sInstance;
    }

    public void release() {
        this.refreshTimeHandler.removeCallbacks(this.refreshTimeRunnable);
        Holder holder = this.mHolder;
        if (holder != null) {
            if (holder.player != null) {
                this.mHolder.player.stop();
                this.mHolder.player.reset();
                this.mHolder.player.release();
                this.mHolder.player = null;
            }
            if (this.mHolder.visualizer != null) {
                this.mHolder.visualizer.release();
                this.mHolder.visualizer = null;
            }
            this.mHolder = null;
        }
        sInstance = null;
    }

    public boolean play(String path) {
        try {
            this.mHolder.player.setDisplay(null);
            this.mHolder.player.reset();
            this.mHolder.player.setDataSource(Utils.getApp(), Uri.parse(path));
            this.mHolder.player.prepare();
            return true;
        } catch (Exception unused) {
            callBack(102, this.mHolder.player);
            return false;
        }
    }

    public void start() {
        this.mHolder.player.start();
        if (this.seekProgress != -1) {
            this.mHolder.player.seekTo(this.seekProgress);
        }
        enableVisualizer(true);
        this.refreshTimeHandler.removeCallbacks(this.refreshTimeRunnable);
        this.refreshTimeHandler.post(this.refreshTimeRunnable);
    }

    public void reset() {
        this.seekProgress = -1;
        this.mHolder.player.reset();
        enableVisualizer(false);
        this.refreshTimeHandler.removeCallbacks(this.refreshTimeRunnable);
    }

    public void pause() {
        this.mHolder.player.pause();
        enableVisualizer(false);
        this.refreshTimeHandler.removeCallbacks(this.refreshTimeRunnable);
    }

    public boolean isPlaying() {
        return this.mHolder.player.isPlaying();
    }

    public void seekTo(int progress) {
        if (isPlaying()) {
            this.mHolder.player.seekTo(progress);
        } else {
            this.seekProgress = progress;
        }
    }

    private void setVisualizer(MediaPlayer mediaPlayer) {
        this.mHolder.visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        this.mHolder.visualizer.setCaptureSize(128);
        this.mHolder.visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() { // from class: com.ltech.smarthome.service.music.MediaPlayerHelper.2
            @Override // android.media.audiofx.Visualizer.OnDataCaptureListener
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
            }

            @Override // android.media.audiofx.Visualizer.OnDataCaptureListener
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                MediaPlayerHelper.this.callBack(106, Float.valueOf(MediaPlayerHelper.getVisualizer(fft)));
            }
        }, 10000, false, true);
    }

    public static float getVisualizer(byte[] fft) {
        int length = (fft.length / 2) + 1;
        byte[] bArr = new byte[length];
        bArr[0] = (byte) Math.abs((int) fft[0]);
        int i = 2;
        for (int i2 = 1; i2 < 48; i2++) {
            bArr[i2] = (byte) Math.hypot(fft[i], fft[i + 1]);
            i += 2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            i3 += bArr[i4];
        }
        return i3 / 1000.0f;
    }

    private static final class Holder {
        private MediaPlayer player;
        private Visualizer visualizer;

        private Holder() {
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        callBack(101, mp);
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int what, int extra) {
        callBack(102, mp, Integer.valueOf(what), Integer.valueOf(extra));
        return false;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mp) {
        callBack(100, mp);
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(MediaPlayer mp) {
        callBack(105, mp);
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        callBack(103, mp, Integer.valueOf(what), Integer.valueOf(extra));
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callBack(int state, Object... args) {
        MediaPlayerHelperCallBack mediaPlayerHelperCallBack = this.mMediaPlayerHelperCallBack;
        if (mediaPlayerHelperCallBack != null) {
            mediaPlayerHelperCallBack.onCallBack(state, sInstance, args);
        }
    }
}