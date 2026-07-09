package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes4.dex */
public class ImaginaryLineView extends View {
    private Context ct;
    private int defaultColor;
    private PathEffect effects;
    private int height;
    private Paint mPaint;
    private Path mPath;
    private int width;

    public ImaginaryLineView(Context context) {
        this(context, null);
    }

    public ImaginaryLineView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ImaginaryLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.defaultColor = -3881788;
        this.ct = context;
        init();
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    private void init() {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(this.defaultColor);
        this.mPaint.setStrokeWidth(dip2px(this.ct, 1.0f));
        this.mPath = new Path();
        this.effects = new DashPathEffect(new float[]{2.0f, 4.0f, 10.0f, 4.0f}, 0.0f);
    }

    public void setLineAttribute(int color, float lineWidth, float[] f) {
        if (color == 0) {
            color = this.defaultColor;
        }
        if (lineWidth == 0.0f) {
            lineWidth = 1.0f;
        }
        if (f == null) {
            f = new float[]{4.0f, 2.0f};
        }
        this.effects = new DashPathEffect(f, 0.0f);
        this.mPaint.setStrokeWidth(dip2px(this.ct, lineWidth));
        this.mPaint.setColor(color);
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPath.moveTo(0.0f, 0.0f);
        int i = this.width;
        int i2 = this.height;
        if (i > i2) {
            this.mPath.lineTo(i, 0.0f);
        } else {
            this.mPath.lineTo(0.0f, i2);
        }
        this.mPaint.setPathEffect(this.effects);
        canvas.drawPath(this.mPath, this.mPaint);
    }

    private static int dip2px(Context context, float dpValue) {
        return (int) ((dpValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}