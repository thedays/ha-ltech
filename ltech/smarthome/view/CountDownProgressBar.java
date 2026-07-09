package com.ltech.smarthome.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.ltech.smarthome.R;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class CountDownProgressBar extends View {
    private float alphaAngle;
    private ValueAnimator animator;
    private int centerTextColor;
    private int centerTextSize;
    private Paint circlePaint;
    private int circleWidth;
    private int[] colorArray;
    private int currentValue;
    private int duration;
    private OnFinishListener finishListener;
    private int firstColor;
    private boolean isShowGradient;
    private int maxValue;
    private int secondColor;
    private Paint textPaint;
    private OnUpdateListener updateListener;

    public interface OnFinishListener {
        void onFinish();
    }

    public interface OnUpdateListener {
        void onUpdate(int time);
    }

    public CountDownProgressBar(Context context) {
        this(context, null);
    }

    public CountDownProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.maxValue = 200;
        this.centerTextColor = -16776961;
        this.isShowGradient = false;
        this.colorArray = new int[]{Color.parseColor("#2773FF"), Color.parseColor("#27C0D2"), Color.parseColor("#40C66E")};
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownProgressBar, defStyleAttr, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            switch (index) {
                case 1:
                    this.centerTextColor = obtainStyledAttributes.getColor(index, -16776961);
                    break;
                case 2:
                    this.centerTextSize = obtainStyledAttributes.getDimensionPixelSize(index, (int) dip2px(40.0f));
                    break;
                case 3:
                    this.circleWidth = obtainStyledAttributes.getDimensionPixelSize(index, (int) dip2px(6.0f));
                    break;
                case 4:
                    this.firstColor = obtainStyledAttributes.getColor(index, -3355444);
                    break;
                case 5:
                    this.isShowGradient = obtainStyledAttributes.getBoolean(index, false);
                    break;
                case 6:
                    this.secondColor = obtainStyledAttributes.getColor(index, -16776961);
                    break;
            }
        }
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.circlePaint = paint;
        paint.setAntiAlias(true);
        this.circlePaint.setDither(true);
        this.circlePaint.setStrokeWidth(this.circleWidth);
        Paint paint2 = new Paint();
        this.textPaint = paint2;
        paint2.setAntiAlias(true);
        this.textPaint.setDither(true);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = getResources().getDisplayMetrics().widthPixels;
        int i2 = getResources().getDisplayMetrics().heightPixels;
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(i, size);
        int min2 = Math.min(i2, size2);
        setMeasuredDimension(Math.min(min, min2), Math.min(min, min2));
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int width = getWidth() / 2;
        drawCircle(canvas, width, width - (this.circleWidth / 2));
        drawText(canvas, width);
    }

    private void drawCircle(Canvas canvas, int center, int radius) {
        this.circlePaint.setShader(null);
        this.circlePaint.setColor(this.firstColor);
        this.circlePaint.setStyle(Paint.Style.STROKE);
        float f = center;
        canvas.drawCircle(f, f, radius, this.circlePaint);
        float f2 = center - radius;
        float f3 = center + radius;
        RectF rectF = new RectF(f2, f2, f3, f3);
        if (this.isShowGradient) {
            int i = this.circleWidth;
            this.circlePaint.setShader(new LinearGradient(i, i, getMeasuredWidth() - this.circleWidth, getMeasuredHeight() - this.circleWidth, this.colorArray, (float[]) null, Shader.TileMode.MIRROR));
        }
        this.circlePaint.setColor(this.secondColor);
        this.circlePaint.setStrokeCap(Paint.Cap.ROUND);
        float f4 = ((this.currentValue * 360.0f) / this.maxValue) * 1.0f;
        this.alphaAngle = f4;
        canvas.drawArc(rectF, -90.0f, f4, false, this.circlePaint);
    }

    private void drawText(Canvas canvas, int center) {
        Object valueOf;
        Object valueOf2;
        String sb;
        int i = this.maxValue;
        int i2 = this.currentValue;
        int i3 = ((i - i2) * (this.duration / 1000)) / i;
        if (i == i2) {
            this.textPaint.setTextSize(this.centerTextSize);
            this.textPaint.setColor(this.secondColor);
            sb = "00:00";
        } else {
            StringBuilder sb2 = new StringBuilder();
            int i4 = i3 / 60;
            if (i4 < 10) {
                valueOf = "0" + i4;
            } else {
                valueOf = Integer.valueOf(i4);
            }
            sb2.append(valueOf);
            sb2.append(Constants.COLON_SEPARATOR);
            int i5 = i3 % 60;
            if (i5 < 10) {
                valueOf2 = "0" + i5;
            } else {
                valueOf2 = Integer.valueOf(i5);
            }
            sb2.append(valueOf2);
            sb = sb2.toString();
            Paint paint = this.textPaint;
            int i6 = this.centerTextSize;
            paint.setTextSize(i6 + (i6 / 3));
            this.textPaint.setColor(this.centerTextColor);
        }
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        this.textPaint.setStrokeWidth(0.0f);
        this.textPaint.getTextBounds(sb, 0, sb.length(), new Rect());
        Paint.FontMetricsInt fontMetricsInt = this.textPaint.getFontMetricsInt();
        canvas.drawText(sb, center, (((fontMetricsInt.bottom - fontMetricsInt.top) / 2) + center) - fontMetricsInt.bottom, this.textPaint);
    }

    public void setCircleWidth(int width) {
        int applyDimension = (int) TypedValue.applyDimension(1, width, getResources().getDisplayMetrics());
        this.circleWidth = applyDimension;
        this.circlePaint.setStrokeWidth(applyDimension);
        invalidate();
    }

    public void setFirstColor(int color) {
        this.firstColor = color;
        this.circlePaint.setColor(color);
        invalidate();
    }

    public void setSecondColor(int color) {
        this.secondColor = color;
        this.circlePaint.setColor(color);
        invalidate();
    }

    public void setColorArray(int[] colors) {
        this.colorArray = colors;
        invalidate();
    }

    public void setDuration(int duration, OnFinishListener listener) {
        this.finishListener = listener;
        this.duration = duration;
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        } else {
            ValueAnimator ofInt = ValueAnimator.ofInt(0, this.maxValue);
            this.animator = ofInt;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.CountDownProgressBar.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    CountDownProgressBar.this.currentValue = ((Integer) animation.getAnimatedValue()).intValue();
                    CountDownProgressBar.this.invalidate();
                    if (CountDownProgressBar.this.currentValue < CountDownProgressBar.this.maxValue && CountDownProgressBar.this.updateListener != null) {
                        CountDownProgressBar.this.updateListener.onUpdate(CountDownProgressBar.this.currentValue);
                    }
                    if (CountDownProgressBar.this.currentValue != CountDownProgressBar.this.maxValue || CountDownProgressBar.this.finishListener == null) {
                        return;
                    }
                    CountDownProgressBar.this.finishListener.onFinish();
                }
            });
            this.animator.setInterpolator(new LinearInterpolator());
        }
        this.animator.setDuration(duration);
        this.animator.start();
    }

    public void end() {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.end();
        }
    }

    public void setOnFinishListener(OnFinishListener listener) {
        this.finishListener = listener;
    }

    public void setOnUpdateListener(OnUpdateListener listener) {
        this.updateListener = listener;
    }

    public static int px2dip(int pxValue) {
        return (int) ((pxValue / Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static float dip2px(float dipValue) {
        return (dipValue * Resources.getSystem().getDisplayMetrics().density) + 0.5f;
    }
}