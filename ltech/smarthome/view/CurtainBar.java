package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.ltech.smarthome.utils.LightUtils;

/* loaded from: classes4.dex */
public class CurtainBar extends HorizontalSeekBar {
    private final int BRT_PROGRESS_MAX;
    private boolean isIncludeZero;
    private int minProgressValue;

    public CurtainBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isIncludeZero = false;
        this.BRT_PROGRESS_MAX = 110;
        if (getMax() == 110) {
            setMax(100);
        }
    }

    public CurtainBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isIncludeZero = false;
        this.BRT_PROGRESS_MAX = 110;
        if (getMax() == 110) {
            setMax(100);
        }
    }

    public CurtainBar(Context context) {
        super(context);
        this.isIncludeZero = false;
        this.BRT_PROGRESS_MAX = 110;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0007, code lost:
    
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
            int r0 = r2.getProgress()     // Catch: java.lang.Throwable -> L37
            if (r0 == r3) goto L35
        L9:
            boolean r0 = r2.isIncludeZero()     // Catch: java.lang.Throwable -> L37
            if (r0 == 0) goto L13
            super.setProgress(r3)     // Catch: java.lang.Throwable -> L37
            goto L29
        L13:
            int r0 = r2.minProgressValue     // Catch: java.lang.Throwable -> L37
            r1 = 1
            if (r3 > r0) goto L22
            if (r0 != 0) goto L1e
            int r0 = java.lang.Math.max(r3, r1)     // Catch: java.lang.Throwable -> L37
        L1e:
            super.setProgress(r0)     // Catch: java.lang.Throwable -> L37
            goto L29
        L22:
            int r3 = java.lang.Math.max(r3, r1)     // Catch: java.lang.Throwable -> L37
            super.setProgress(r3)     // Catch: java.lang.Throwable -> L37
        L29:
            int r3 = r2.getWidth()     // Catch: java.lang.Throwable -> L37
            int r0 = r2.getHeight()     // Catch: java.lang.Throwable -> L37
            r1 = 0
            r2.onSizeChanged(r3, r0, r1, r1)     // Catch: java.lang.Throwable -> L37
        L35:
            monitor-exit(r2)
            return
        L37:
            r3 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L37
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.CurtainBar.setProgress(int):void");
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

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        canvas.rotate(90.0f);
        canvas.translate(0.0f, -super.getWidth());
        super.onDraw(canvas);
    }

    @Override // com.ltech.smarthome.view.HorizontalSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }
}