package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* loaded from: classes4.dex */
public class ReversedSeekBar extends HorizontalSeekBar {
    public ReversedSeekBar(Context context) {
        super(context);
    }

    public ReversedSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReversedSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.scale(-1.0f, 1.0f, getWidth() / 2.0f, getHeight() / 2.0f);
        super.onDraw(canvas);
    }

    @Override // com.ltech.smarthome.view.HorizontalSeekBar, android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        event.setLocation(getWidth() - event.getX(), event.getY());
        return super.onTouchEvent(event);
    }
}