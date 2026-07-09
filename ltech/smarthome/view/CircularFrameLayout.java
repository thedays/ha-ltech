package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* loaded from: classes4.dex */
public class CircularFrameLayout extends FrameLayout {
    private Paint maskPaint;
    private Path maskPath;

    public CircularFrameLayout(Context context) {
        super(context);
        init();
    }

    public CircularFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.maskPath = new Path();
        Paint paint = new Paint(1);
        this.maskPaint = paint;
        paint.setColor(-16777216);
        this.maskPaint.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        this.maskPath.reset();
        this.maskPath.addCircle(width / 2.0f, height / 2.0f, Math.min(width, height) / 2.0f, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(this.maskPath);
        super.dispatchDraw(canvas);
        canvas.restore();
    }
}