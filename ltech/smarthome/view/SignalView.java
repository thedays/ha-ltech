package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class SignalView extends View {
    private int barNum;
    private int barWidth;
    private int curValue;
    private int mainColor;
    private int maxValue;
    private Paint paint;
    private int secColor;
    private Point[] startPoint;
    private int totalHeight;

    public SignalView(Context context) {
        this(context, null);
    }

    public SignalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SignalView);
        this.mainColor = obtainStyledAttributes.getColor(3, -16777216);
        this.secColor = obtainStyledAttributes.getColor(4, -7829368);
        this.curValue = obtainStyledAttributes.getInt(1, 0);
        this.maxValue = obtainStyledAttributes.getInt(2, 100);
        this.barNum = obtainStyledAttributes.getInt(0, 3);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.barWidth = getMeasuredWidth() / ((this.barNum * 2) - 1);
        this.totalHeight = getMeasuredHeight();
        this.paint.setStrokeWidth(this.barWidth);
        this.startPoint = new Point[this.barNum];
        for (int i = 0; i < this.startPoint.length; i++) {
            Point point = new Point();
            int i2 = this.barWidth;
            point.x = (i2 * 2 * i) + (i2 >> 1);
            point.y = (int) (((((r4 - i) - 1.0f) / this.barNum) * this.totalHeight) + (this.barWidth >> 1));
            this.startPoint[i] = point;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = (this.curValue * 1.0f) / this.maxValue;
        for (int i = 0; i < this.startPoint.length; i++) {
            if ((i + 1.0f) / this.barNum <= f) {
                this.paint.setColor(this.mainColor);
            } else {
                this.paint.setColor(this.secColor);
            }
            canvas.drawLine(this.startPoint[i].x, this.startPoint[i].y, this.startPoint[i].x, this.totalHeight - (this.barWidth >> 1), this.paint);
        }
    }

    public void setCurValue(int curValue) {
        this.curValue = curValue;
        postInvalidate();
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        postInvalidate();
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
        postInvalidate();
    }

    public void setSecColor(int secColor) {
        this.secColor = secColor;
        postInvalidate();
    }
}