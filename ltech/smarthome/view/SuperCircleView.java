package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class SuperCircleView extends View {
    private final String TAG;
    private int[] color;
    private boolean isShowSelect;
    private int mMaxCircleColor;
    private int mMinCircleColor;
    private int mMinRadio;
    private Paint mPaint;
    private RectF mRectF;
    private float mRingAngleWidth;
    private int mRingNormalColor;
    private float mRingWidth;
    private int mSelect;
    private int mSelectAngle;
    private int mSelectRing;
    private int mViewCenterX;
    private int mViewCenterY;
    private int mViewHeight;
    private int mViewWidth;

    public SuperCircleView(Context context) {
        this(context, null);
    }

    public SuperCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = "SuperCircleView";
        this.color = new int[3];
        this.mSelectRing = 0;
        this.isShowSelect = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SuperCircleView);
        int integer = obtainStyledAttributes.getInteger(3, 400);
        this.mMinRadio = integer;
        this.mMinRadio = ConvertUtils.dp2px(integer);
        this.mRingWidth = obtainStyledAttributes.getFloat(6, 40.0f);
        this.mRingWidth = ConvertUtils.dp2px(r5);
        this.mSelect = obtainStyledAttributes.getInteger(8, 7);
        this.mSelectAngle = obtainStyledAttributes.getInteger(7, 3);
        this.mMinCircleColor = obtainStyledAttributes.getColor(0, -1);
        this.mMaxCircleColor = obtainStyledAttributes.getColor(2, -592138);
        this.mRingNormalColor = obtainStyledAttributes.getColor(5, 14869218);
        this.isShowSelect = obtainStyledAttributes.getBoolean(1, false);
        this.mSelectRing = obtainStyledAttributes.getInt(4, 0);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setAntiAlias(true);
        setWillNotDraw(false);
        this.color[0] = Color.parseColor("#F95676");
        this.color[1] = Color.parseColor("#F4103D");
        this.color[2] = Color.parseColor("#F95676");
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mViewWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        this.mViewHeight = measuredHeight;
        this.mViewCenterX = this.mViewWidth / 2;
        this.mViewCenterY = measuredHeight / 2;
        int i = this.mViewCenterX;
        int i2 = this.mMinRadio;
        float f = this.mRingWidth;
        int i3 = this.mViewCenterY;
        this.mRectF = new RectF((i - i2) - (f / 2.0f), (i3 - i2) - (f / 2.0f), i + i2 + (f / 2.0f), i3 + i2 + (f / 2.0f));
        int i4 = this.mSelect;
        this.mRingAngleWidth = (360 - (this.mSelectAngle * i4)) / i4;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.isShowSelect || this.mSelectRing <= this.mSelect) {
            this.mPaint.setColor(this.mMaxCircleColor);
            canvas.drawCircle(this.mViewCenterX, this.mViewCenterY, this.mMinRadio + this.mRingWidth, this.mPaint);
            this.mPaint.setColor(this.mMinCircleColor);
            canvas.drawCircle(this.mViewCenterX, this.mViewCenterY, this.mMinRadio, this.mPaint);
            drawNormalRing(canvas);
            drawColorRing(canvas);
        }
    }

    private void drawColorRing(Canvas canvas) {
        Paint paint = new Paint(this.mPaint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.mRingWidth);
        int i = this.mViewCenterX;
        paint.setShader(new SweepGradient(i, i, this.color, (float[]) null));
        if (!this.isShowSelect) {
            canvas.drawArc(this.mRectF, 270.0f, this.mSelectRing, false, paint);
            return;
        }
        int i2 = this.mSelect;
        int i3 = this.mSelectRing;
        if (i2 == i3 && i3 != 0 && i2 != 0) {
            canvas.drawArc(this.mRectF, 270.0f, 360.0f, false, paint);
        } else {
            StringBuilder sb = new StringBuilder();
            float f = this.mRingAngleWidth;
            int i4 = this.mSelectRing;
            sb.append((f * i4) + this.mSelectAngle + i4);
            sb.append("");
            Log.d("SuperCircleView", sb.toString());
            canvas.drawArc(this.mRectF, 270.0f, (this.mRingAngleWidth * this.mSelectRing) + (this.mSelectAngle * r2), false, paint);
        }
        paint.setShader(null);
        paint.setColor(this.mMaxCircleColor);
        for (int i5 = 0; i5 < this.mSelectRing; i5++) {
            RectF rectF = this.mRectF;
            float f2 = i5 * this.mRingAngleWidth;
            canvas.drawArc(rectF, f2 + (i5 * r3) + 270.0f, this.mSelectAngle, false, paint);
        }
    }

    private void drawNormalRing(Canvas canvas) {
        Paint paint = new Paint(this.mPaint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.mRingWidth);
        paint.setColor(this.mRingNormalColor);
        canvas.drawArc(this.mRectF, 270.0f, 360.0f, false, paint);
        if (this.isShowSelect) {
            paint.setColor(this.mMaxCircleColor);
            for (int i = 0; i < this.mSelect; i++) {
                RectF rectF = this.mRectF;
                float f = i * this.mRingAngleWidth;
                canvas.drawArc(rectF, f + (i * r3) + 270.0f, this.mSelectAngle, false, paint);
            }
        }
    }

    public void setSelect(int i) {
        this.mSelectRing = i;
        invalidate();
    }

    public void setSelectCount(int i) {
        this.mSelect = i;
    }

    public void setShowSelect(boolean b2) {
        this.isShowSelect = b2;
    }

    public void setColor(int[] color) {
        this.color = color;
    }
}