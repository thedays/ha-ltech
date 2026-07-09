package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

/* loaded from: classes4.dex */
public class CtVerticalSeekBar extends VerticalSeekBar {
    private Paint mPickerPaint;
    private Paint mPickerPaintOuter;

    public CtVerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CtVerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CtVerticalSeekBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Paint paint = new Paint();
        this.mPickerPaint = paint;
        paint.setAntiAlias(true);
        this.mPickerPaint.setDither(true);
        this.mPickerPaint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.mPickerPaintOuter = paint2;
        paint2.setAntiAlias(true);
        this.mPickerPaintOuter.setDither(true);
        this.mPickerPaintOuter.setColor(-1);
        this.mPickerPaintOuter.setShadowLayer(15.0f, 0.0f, 0.0f, -7829368);
    }

    @Override // com.ltech.smarthome.view.VerticalSeekBar, androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getBackground();
        int width = bitmapDrawable.getBitmap().getWidth();
        int height = bitmapDrawable.getBitmap().getHeight();
        if (this.mPickPoint.y >= 0) {
            this.mPickerPaint.setColor(getBitmapPixel(this.mPickPoint.x, this.mPickPoint.y));
            this.mPickerPaint.setStrokeWidth(this.mPickerRadius >> 2);
            if (this.mPickPoint.y <= this.mPickerRadius) {
                float f = width / 2.0f;
                canvas.drawCircle(height - this.mPickerRadius, f, this.mPickerRadius, this.mPickerPaintOuter);
                canvas.drawCircle(height - this.mPickerRadius, f, this.mPickerRadius - this.mPickerPaint.getStrokeWidth(), this.mPickerPaint);
            } else if (height - this.mPickPoint.y <= this.mPickerRadius) {
                float f2 = width / 2.0f;
                canvas.drawCircle(this.mPickerRadius, f2, this.mPickerRadius, this.mPickerPaintOuter);
                canvas.drawCircle(this.mPickerRadius, f2, this.mPickerRadius - this.mPickerPaint.getStrokeWidth(), this.mPickerPaint);
            } else {
                float f3 = width / 2.0f;
                canvas.drawCircle(height - this.mPickPoint.y, f3, this.mPickerRadius, this.mPickerPaintOuter);
                canvas.drawCircle(height - this.mPickPoint.y, f3, this.mPickerRadius - this.mPickerPaint.getStrokeWidth(), this.mPickerPaint);
            }
        }
    }

    private int getBitmapPixel(float x, float y) {
        Bitmap bitmap = ((BitmapDrawable) getBackground()).getBitmap();
        if (y >= bitmap.getHeight()) {
            y = bitmap.getHeight() - 1;
        }
        return bitmap.getPixel(bitmap.getWidth() / 2, (int) y);
    }
}