package com.ltech.smarthome.service.music;

import android.media.AudioRecord;
import com.github.mikephil.charting.utils.Utils;
import com.mediaplayer.audio.AudioCodecParam;

/* loaded from: classes4.dex */
public class RecordThread extends Thread {
    private boolean isRecording = false;
    private final Object mLock = new Object();
    private RecordCallback mRecordCallback;

    public interface RecordCallback {
        void onVolumes(float percent);
    }

    public RecordThread(RecordCallback recordCallback) {
        this.mRecordCallback = recordCallback;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        AudioRecord audioRecord = new AudioRecord(1, AudioCodecParam.AudioSampleRate.AUDIO_SAMPLERATE_44K, 12, 2, AudioRecord.getMinBufferSize(AudioCodecParam.AudioSampleRate.AUDIO_SAMPLERATE_44K, 12, 2));
        audioRecord.startRecording();
        short[] sArr = new short[100];
        byte[] bArr = new byte[100];
        while (this.isRecording) {
            if (this.mRecordCallback != null) {
                audioRecord.read(bArr, 0, 100);
                audioRecord.read(sArr, 0, 100);
                long j = 0;
                for (int i = 0; i < 100; i++) {
                    j += Math.abs((int) sArr[i]);
                }
                if ((j / 100) / 400 > 0) {
                    double visualizer = MediaPlayerHelper.getVisualizer(bArr) * 100.0f;
                    this.mRecordCallback.onVolumes(visualizer > Utils.DOUBLE_EPSILON ? (float) ((visualizer * 7.0d) / 5.0d) : 0.0f);
                } else {
                    this.mRecordCallback.onVolumes(0.0f);
                }
            }
            synchronized (this.mLock) {
                try {
                    this.mLock.wait(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        audioRecord.stop();
        audioRecord.release();
        super.run();
    }

    public void startRecord() {
        this.isRecording = true;
        start();
    }

    public void stopRecord() {
        this.isRecording = false;
    }

    public boolean isRecording() {
        return this.isRecording;
    }
}