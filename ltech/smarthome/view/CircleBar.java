package com.ltech.smarthome.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import com.blankj.utilcode.util.SizeUtils;
import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.utils.ExtUtils;

/* loaded from: classes4.dex */
public class CircleBar extends View {
    public static final int START_ANGLE = 90;
    private int angleDelta;
    private int barRadius;
    private int barStrokeWith;
    private int bgColor;
    private int bgRadius;
    private int bgStrokeWidth;
    private Point centerPoint;
    private boolean enable;
    private int endColor;
    private Paint mBarPaint;
    private RectF mBarRecF;
    private Paint mBgPaint;
    private RectF mBgRecF;
    private Point mBorderLeftPoint;
    private Point mBorderRightPoint;
    private Paint mThumbPaint;
    private Paint mThumbStrokePaint;
    private int max;
    private int min;
    private OnProgressChangeListener progressChangeListener;
    private double radianLeft;
    private double radianRight;
    private int startColor;
    private SweepGradient sweepGradient;
    private Point thumbPoint;
    private int unableColor;

    public interface OnProgressChangeListener {
        void onProgressChanged(float percent, int progress, boolean finish);
    }

    public CircleBar(Context context) {
        this(context, null);
    }

    public CircleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CircleBar);
        this.enable = obtainStyledAttributes.getBoolean(6, true);
        this.bgColor = obtainStyledAttributes.getColor(4, -3355444);
        this.unableColor = obtainStyledAttributes.getColor(9, -7829368);
        this.startColor = obtainStyledAttributes.getColor(2, Color.parseColor("#50E8B5"));
        this.endColor = obtainStyledAttributes.getColor(1, Color.parseColor("#59B6FF"));
        this.min = obtainStyledAttributes.getInt(8, 16);
        this.max = obtainStyledAttributes.getInt(7, 31);
        this.angleDelta = obtainStyledAttributes.getInt(0, 30);
        this.bgStrokeWidth = obtainStyledAttributes.getDimensionPixelOffset(5, SizeUtils.dp2px(10.0f));
        this.barStrokeWith = obtainStyledAttributes.getDimensionPixelOffset(3, SizeUtils.dp2px(30.0f));
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mBgPaint = paint;
        paint.setAntiAlias(true);
        this.mBgPaint.setStyle(Paint.Style.STROKE);
        this.mBgPaint.setStrokeWidth(this.bgStrokeWidth);
        this.mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint2 = new Paint();
        this.mBarPaint = paint2;
        paint2.setAntiAlias(true);
        this.mBarPaint.setStyle(Paint.Style.STROKE);
        this.mBarPaint.setStrokeWidth(this.barStrokeWith);
        Paint paint3 = new Paint();
        this.mThumbPaint = paint3;
        paint3.setAntiAlias(true);
        this.mThumbPaint.setColor(-1);
        Paint paint4 = new Paint();
        this.mThumbStrokePaint = paint4;
        paint4.setAntiAlias(true);
        this.mThumbStrokePaint.setColor(-7829368);
        this.mThumbStrokePaint.setStrokeWidth(1.0f);
        this.mThumbStrokePaint.setStyle(Paint.Style.STROKE);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float min = Math.min(getMeasuredHeight(), getMeasuredWidth()) / 2;
        int i = this.bgStrokeWidth;
        int i2 = (int) (min - (i / 2.0f));
        this.bgRadius = i2;
        this.barRadius = (int) ((i2 - (i / 2.0f)) - (this.barStrokeWith / 2.0f));
        Point point = new Point();
        this.centerPoint = point;
        point.x = getMeasuredWidth() / 2;
        this.centerPoint.y = getMeasuredHeight() / 2;
        this.mBgRecF = new RectF(this.centerPoint.x - this.bgRadius, this.centerPoint.y - this.bgRadius, this.centerPoint.x + this.bgRadius, this.centerPoint.y + this.bgRadius);
        this.mBarRecF = new RectF(this.centerPoint.x - this.barRadius, this.centerPoint.y - this.barRadius, this.centerPoint.x + this.barRadius, this.centerPoint.y + this.barRadius);
        this.sweepGradient = new SweepGradient(this.mBgRecF.centerX(), this.mBgRecF.centerY(), this.startColor, this.endColor);
        Matrix matrix = new Matrix();
        matrix.setRotate((float) ((this.angleDelta + 90) - (Math.asin((this.bgStrokeWidth / 2.0f) / this.bgRadius) * 57.29577951308232d)), this.mBgRecF.centerX(), this.mBgRecF.centerY());
        this.sweepGradient.setLocalMatrix(matrix);
        int i3 = this.angleDelta;
        this.radianLeft = ((i3 + 90) * 3.141592653589793d) / 180.0d;
        this.radianRight = ((90 - i3) * 3.141592653589793d) / 180.0d;
        this.mBorderLeftPoint = new Point((int) (this.centerPoint.x - (this.barRadius * Math.sin(this.radianLeft - 1.5707963267948966d))), (int) (this.centerPoint.y + (this.barRadius * Math.cos(this.radianLeft - 1.5707963267948966d))));
        this.mBorderRightPoint = new Point((int) (this.centerPoint.x + (this.barRadius * Math.sin(this.radianLeft - 1.5707963267948966d))), (int) (this.centerPoint.y + (this.barRadius * Math.cos(this.radianLeft - 1.5707963267948966d))));
        if (this.thumbPoint == null) {
            this.thumbPoint = new Point(0, 0);
            setThumbPoint(0.0f, false);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.enable) {
            drawUnableView(canvas);
        } else {
            drawEnableView(canvas);
        }
    }

    private void drawUnableView(Canvas canvas) {
        this.mBgPaint.setColor(this.bgColor);
        this.mBgPaint.setShader(null);
        canvas.drawArc(this.mBgRecF, 0.0f, 360.0f, false, this.mBgPaint);
        this.mBgPaint.setColor(this.unableColor);
        canvas.drawArc(this.mBgRecF, (float) (this.angleDelta + 90 + (Math.asin((this.bgStrokeWidth / 2.0f) / this.bgRadius) * 57.29577951308232d)), 360 - (this.angleDelta * 2), false, this.mBgPaint);
        this.mBarPaint.setShader(null);
        this.mBarPaint.setAlpha(255);
        this.mBarPaint.setColor(this.bgColor);
        canvas.drawArc(this.mBarRecF, this.angleDelta + 90, getSweepAngle(101.0f), false, this.mBarPaint);
    }

    private void drawEnableView(Canvas canvas) {
        this.mBgPaint.setColor(this.bgColor);
        this.mBgPaint.setShader(null);
        canvas.drawArc(this.mBgRecF, 0.0f, 360.0f, false, this.mBgPaint);
        this.mBgPaint.setShader(this.sweepGradient);
        canvas.drawArc(this.mBgRecF, (float) (this.angleDelta + 90 + (Math.asin((this.bgStrokeWidth / 2.0f) / this.bgRadius) * 57.29577951308232d)), 360 - (this.angleDelta * 2), false, this.mBgPaint);
        this.mBarPaint.setShader(this.sweepGradient);
        this.mBarPaint.setAlpha(50);
        canvas.drawArc(this.mBarRecF, this.angleDelta + 90, getSweepAngle(getCurPercent()), false, this.mBarPaint);
        canvas.drawCircle(this.thumbPoint.x, this.thumbPoint.y, this.barStrokeWith / 2.0f, this.mThumbPaint);
        canvas.drawCircle(this.thumbPoint.x, this.thumbPoint.y, this.barStrokeWith / 2.0f, this.mThumbStrokePaint);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0016, code lost:
    
        if (r0 != 3) goto L43;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            Method dump skipped, instructions count: 376
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.CircleBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void setSweepGradient() {
        this.sweepGradient = new SweepGradient(this.mBgRecF.centerX(), this.mBgRecF.centerY(), this.startColor, this.endColor);
        Matrix matrix = new Matrix();
        matrix.setRotate(90.0f, this.mBgRecF.centerX(), this.mBgRecF.centerY());
        this.sweepGradient.setLocalMatrix(matrix);
    }

    private float getSweepAngle(float percent) {
        return (percent / 100.0f) * (360 - (this.angleDelta * 2));
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        post(new Runnable() { // from class: com.ltech.smarthome.view.CircleBar$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CircleBar.this.postInvalidate();
            }
        });
    }

    public void setMin(int min) {
        if (this.min == min) {
            return;
        }
        this.min = min;
        postInvalidate();
    }

    public void setMax(int max) {
        if (this.max == max) {
            return;
        }
        this.max = max;
        postInvalidate();
    }

    public void setBarColor(final int start, final int end) {
        post(new Runnable() { // from class: com.ltech.smarthome.view.CircleBar$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CircleBar.this.lambda$setBarColor$0(start, end);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBarColor$0(int i, int i2) {
        this.startColor = i;
        this.endColor = i2;
        setSweepGradient();
        postInvalidate();
    }

    public int getCurProgress() {
        float curPercent = getCurPercent();
        int i = this.max;
        return (int) (((curPercent * (i - r2)) / 99.0f) + this.min);
    }

    public void setPercent(final float percent) {
        if (percent < 0.0f || percent > 100.0f) {
            return;
        }
        post(new Runnable() { // from class: com.ltech.smarthome.view.CircleBar$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                CircleBar.this.lambda$setPercent$1(percent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPercent$1(float f) {
        setThumbPoint(f, true);
        postInvalidate();
    }

    public void setProgress(final int progress) {
        if (progress < this.min || progress > this.max) {
            return;
        }
        post(new Runnable() { // from class: com.ltech.smarthome.view.CircleBar$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CircleBar.this.lambda$setProgress$2(progress);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProgress$2(int i) {
        int i2 = this.min;
        setThumbPoint(((i - i2) * 100.0f) / (this.max - i2), true);
        postInvalidate();
    }

    public float getCurPercent() {
        double radian = ExtUtils.getRadian(this.centerPoint, this.thumbPoint);
        double d2 = (radian < Utils.DOUBLE_EPSILON || this.centerPoint.x < this.thumbPoint.x) ? radian + 4.71238898038469d : radian - 1.5707963267948966d;
        int i = this.angleDelta;
        return (float) (((d2 - ((i * 3.141592653589793d) / 180.0d)) * 100.0d) / ((3.141592653589793d - ((i * 3.141592653589793d) / 180.0d)) * 2.0d));
    }

    private void setThumbPoint(float percent, boolean animate) {
        double d2 = percent / 100.0f;
        int i = this.angleDelta;
        float f = (float) ((((float) ((d2 * ((3.141592653589793d - ((i * 3.141592653589793d) / 180.0d)) * 2.0d)) + ((i * 3.141592653589793d) / 180.0d))) + 1.5707963267948966d) - 6.283185307179586d);
        if (animate) {
            double d3 = f;
            setThumbPoint((int) (this.centerPoint.x + (Math.cos(d3) * this.barRadius)), (int) (this.centerPoint.y + (Math.sin(d3) * this.barRadius)));
        } else {
            double d4 = f;
            this.thumbPoint.set((int) (this.centerPoint.x + (Math.cos(d4) * this.barRadius)), (int) (this.centerPoint.y + (Math.sin(d4) * this.barRadius)));
        }
    }

    private boolean isInvalidPoint(float x, float y) {
        double radian = ExtUtils.getRadian(this.centerPoint, new Point((int) x, (int) y));
        return radian > this.radianRight && radian < this.radianLeft;
    }

    public void setThumbPoint(final int newX, final int newY) {
        final int i = this.thumbPoint.x;
        final int i2 = this.thumbPoint.y;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.CircleBar$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                CircleBar.this.lambda$setThumbPoint$3(i, newX, i2, newY, valueAnimator);
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.ltech.smarthome.view.CircleBar.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                CircleBar.this.thumbPoint.x = newX;
                CircleBar.this.thumbPoint.y = newY;
                CircleBar.this.invalidate();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                CircleBar.this.thumbPoint.x = newX;
                CircleBar.this.thumbPoint.y = newY;
                CircleBar.this.invalidate();
            }
        });
        ofFloat.setDuration(200L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setThumbPoint$3(int i, int i2, int i3, int i4, ValueAnimator valueAnimator) {
        this.thumbPoint.x = (int) (i + ((i2 - i) * ((Float) valueAnimator.getAnimatedValue()).floatValue()));
        this.thumbPoint.y = (int) (i3 + ((i4 - i3) * ((Float) valueAnimator.getAnimatedValue()).floatValue()));
        invalidate();
    }

    public void setProgressChangeListener(OnProgressChangeListener progressChangeListener) {
        this.progressChangeListener = progressChangeListener;
    }
}