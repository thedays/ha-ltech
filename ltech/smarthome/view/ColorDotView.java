package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes4.dex */
public class ColorDotView extends View {
    private int dotColor;
    private Paint paint;

    public ColorDotView(Context context) {
        super(context);
        this.dotColor = 0;
        init();
    }

    public ColorDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.dotColor = 0;
        init();
    }

    public ColorDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dotColor = 0;
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(this.dotColor);
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() / 2.0f;
        canvas.drawCircle(width, getHeight() / 2.0f, width, this.paint);
    }

    public void setDotColor(int color) {
        this.dotColor = color;
        this.paint.setColor(color);
        invalidate();
    }
}