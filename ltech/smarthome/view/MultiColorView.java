package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class MultiColorView extends View {
    private final int STROKE_WIDTH;
    private int[] colorArray;
    private Paint mPaint;
    private int mRadius;
    private RectF mRectF;
    private Paint mStrokePaint;

    public MultiColorView(Context context) {
        this(context, null);
    }

    public MultiColorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.STROKE_WIDTH = 1;
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setDither(true);
        Paint paint2 = new Paint();
        this.mStrokePaint = paint2;
        paint2.setStrokeWidth(1.0f);
        this.mStrokePaint.setAntiAlias(true);
        this.mStrokePaint.setDither(true);
        this.mStrokePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_text_gray));
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        this.mRadius = (Math.min(measuredWidth, measuredHeight) / 2) - 1;
        int i = measuredWidth >> 1;
        int i2 = this.mRadius;
        int i3 = measuredHeight >> 1;
        this.mRectF = new RectF(i - i2, i3 - i2, i + i2, i3 + i2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        super.onDraw(canvas);
        int[] iArr = this.colorArray;
        if (iArr == null) {
            canvas2 = canvas;
        } else if (iArr.length == 1) {
            this.mPaint.setColor(iArr[0]);
            canvas2 = canvas;
            canvas2.drawArc(this.mRectF, 0.0f, 360.0f, true, this.mPaint);
        } else {
            canvas2 = canvas;
            this.mPaint.setColor(iArr[0]);
            canvas2.drawArc(this.mRectF, 0.0f, 360.0f, true, this.mPaint);
        }
        canvas2.drawCircle(this.mRectF.centerX(), this.mRectF.centerY(), this.mRadius, this.mStrokePaint);
    }

    public void setColorArray(int[] colors) {
        if (colors.length > 2) {
            throw new RuntimeException("color size is error!");
        }
        this.colorArray = colors;
        postInvalidate();
    }
}