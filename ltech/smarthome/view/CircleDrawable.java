package com.ltech.smarthome.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/* loaded from: classes4.dex */
public class CircleDrawable extends Drawable {
    private int color;
    private Paint paint;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public CircleDrawable(int color) {
        this.color = color;
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(this.color);
        setBounds(0, 0, 40, 40);
    }

    public CircleDrawable(String color) {
        this.color = Color.parseColor(color);
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(this.color);
        setBounds(0, 0, 40, 40);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.drawCircle(getBounds().width() / 2.0f, getBounds().height() / 2.0f, Math.min(r0, r1) / 2, this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.paint.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter cf) {
        this.paint.setColorFilter(cf);
    }
}