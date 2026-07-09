package com.ltech.smarthome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;
import com.ltech.smarthome.utils.LightUtils;

/* loaded from: classes4.dex */
public class LightBrtBar extends HorizontalSeekBar {
    private final int BRT_PROGRESS_MAX;
    private boolean isIncludeZero;
    private int minProgressValue;

    public LightBrtBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isIncludeZero = false;
        this.BRT_PROGRESS_MAX = 110;
        if (getMax() == 110) {
            setMax(100);
        }
    }

    public LightBrtBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isIncludeZero = false;
        this.BRT_PROGRESS_MAX = 110;
        if (getMax() == 110) {
            setMax(100);
        }
    }

    public LightBrtBar(Context context) {
        super(context);
        this.isIncludeZero = false;
        this.BRT_PROGRESS_MAX = 110;
    }

    @Override // com.ltech.smarthome.view.HorizontalSeekBar, android.widget.SeekBar
    public void setOnSeekBarChangeListener(final SeekBar.OnSeekBarChangeListener l) {
        super.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ltech.smarthome.view.LightBrtBar.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (l != null) {
                    if (!LightBrtBar.this.isIncludeZero()) {
                        seekBar.setProgress(Math.max(seekBar.getProgress(), 1));
                    }
                    l.onProgressChanged(seekBar, seekBar.getProgress(), fromUser);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = l;
                if (onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!LightBrtBar.this.isIncludeZero()) {
                    seekBar.setProgress(Math.max(seekBar.getProgress(), 1));
                }
                SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = l;
                if (onSeekBarChangeListener != null) {
                    onSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0007, code lost:
    
        if (getProgress() != r3) goto L6;
     */
    @Override // com.ltech.smarthome.view.HorizontalSeekBar, android.widget.ProgressBar
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void setProgress(int r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L9
            int r0 = r2.getProgress()     // Catch: java.lang.Throwable -> L48
            if (r0 == r3) goto L46
        L9:
            boolean r0 = r2.isIncludeZero()     // Catch: java.lang.Throwable -> L48
            if (r0 == 0) goto L13
            super.setProgress(r3)     // Catch: java.lang.Throwable -> L48
            goto L29
        L13:
            int r0 = r2.minProgressValue     // Catch: java.lang.Throwable -> L48
            r1 = 1
            if (r3 > r0) goto L22
            if (r0 != 0) goto L1e
            int r0 = java.lang.Math.max(r3, r1)     // Catch: java.lang.Throwable -> L48
        L1e:
            super.setProgress(r0)     // Catch: java.lang.Throwable -> L48
            goto L29
        L22:
            int r3 = java.lang.Math.max(r3, r1)     // Catch: java.lang.Throwable -> L48
            super.setProgress(r3)     // Catch: java.lang.Throwable -> L48
        L29:
            int r3 = r2.getWidth()     // Catch: java.lang.Throwable -> L48
            int r0 = r2.getHeight()     // Catch: java.lang.Throwable -> L48
            r1 = 0
            r2.onSizeChanged(r3, r0, r1, r1)     // Catch: java.lang.Throwable -> L48
            android.widget.SeekBar$OnSeekBarChangeListener r3 = r2.mySeekBarChangeListener     // Catch: java.lang.Throwable -> L48
            if (r3 == 0) goto L46
            android.widget.SeekBar$OnSeekBarChangeListener r3 = r2.mySeekBarChangeListener     // Catch: java.lang.Throwable -> L48
            int r0 = r2.getProgress()     // Catch: java.lang.Throwable -> L48
            boolean r1 = r2.isPressed()     // Catch: java.lang.Throwable -> L48
            r3.onProgressChanged(r2, r0, r1)     // Catch: java.lang.Throwable -> L48
        L46:
            monitor-exit(r2)
            return
        L48:
            r3 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L48
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.LightBrtBar.setProgress(int):void");
    }

    public synchronized String getProgressHasBelowOne() {
        return LightUtils.getProgressHasBelowOne(getProgress());
    }

    public boolean isIncludeZero() {
        return this.isIncludeZero;
    }

    public void setIncludeZero(boolean includeZero) {
        this.isIncludeZero = includeZero;
    }

    public int getMinProgressValue() {
        return this.minProgressValue;
    }

    public void setMinProgressValue(int minProgressValue) {
        this.minProgressValue = minProgressValue;
    }
}