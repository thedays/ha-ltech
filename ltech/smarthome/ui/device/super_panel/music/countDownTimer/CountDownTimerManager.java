package com.ltech.smarthome.ui.device.super_panel.music.countDownTimer;

import android.os.CountDownTimer;

/* loaded from: classes4.dex */
public class CountDownTimerManager {
    private CountDownTimer countDownTimer;
    private long interval;
    private boolean isRunning = false;
    private OnCountDownTimerListener onCountDownTimerListener;
    private long timeLeft;
    private long totalTime;

    public interface OnCountDownTimerListener {
        void onFinish();

        void onTick(long millisUntilFinished);
    }

    public CountDownTimerManager(long totalTime, long interval, OnCountDownTimerListener onCountDownTimerListener) {
        this.totalTime = totalTime;
        this.interval = interval;
        this.timeLeft = totalTime;
        this.onCountDownTimerListener = onCountDownTimerListener;
        initCountDownTimer(onCountDownTimerListener);
    }

    private void initCountDownTimer(final OnCountDownTimerListener onCountDownTimerListener) {
        this.countDownTimer = new CountDownTimer(this.timeLeft, this.interval) { // from class: com.ltech.smarthome.ui.device.super_panel.music.countDownTimer.CountDownTimerManager.1
            @Override // android.os.CountDownTimer
            public void onTick(long millisUntilFinished) {
                CountDownTimerManager.this.timeLeft = millisUntilFinished;
                onCountDownTimerListener.onTick(millisUntilFinished);
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                CountDownTimerManager.this.timeLeft = 0L;
                onCountDownTimerListener.onFinish();
            }
        };
    }

    public void start() {
        if (this.isRunning) {
            return;
        }
        this.countDownTimer.start();
        this.isRunning = true;
    }

    public void pause() {
        if (this.isRunning) {
            this.countDownTimer.cancel();
            this.isRunning = false;
        }
    }

    public void resume() {
        if (this.isRunning) {
            return;
        }
        initCountDownTimer(this.onCountDownTimerListener);
        this.countDownTimer.start();
        this.isRunning = true;
    }

    public void cancel() {
        this.countDownTimer.cancel();
        this.timeLeft = 0L;
        this.isRunning = false;
    }
}