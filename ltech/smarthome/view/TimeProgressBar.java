package com.ltech.smarthome.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import androidx.core.content.ContextCompat;
import com.ltech.smarthome.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class TimeProgressBar extends View {
    private int currentProgress;
    private Runnable drawRunnable;
    private int failColor;
    private Paint failPaint;
    private Path failPath1;
    private Path failPath2;
    private PathMeasure failPathMeasure1;
    private PathMeasure failPathMeasure2;
    private float failPercent;
    private int firstProgressColor;
    private Paint firstProgressPaint;
    private ValueAnimator mFailAnimation;
    private Handler mHandler;
    private onListener mOnListener;
    private ValueAnimator mSuccessAnimation;
    private Path path;
    private float radius;
    private RectF rectF;
    private int secondProgressColor;
    private Paint secondProgressPaint;
    private int state;
    private float strokeWidth;
    private Path successPath;
    private PathMeasure successPathMeasure;
    private float successPercent;
    private int textColor;
    private float textHeight;
    private Paint textPaint;
    private float textSize;
    private float textWidth;
    private int totalProgress;
    private String unit;

    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        public static final int FAIL = 1;
        public static final int LOADING = 2;
        public static final int SUCCESS = 0;
    }

    public interface onListener {
        void onFail();

        void onLoading();

        void onSuccess();
    }

    public TimeProgressBar(Context context) {
        this(context, null);
    }

    public TimeProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.currentProgress = 50;
        this.totalProgress = 100;
        this.unit = "";
        this.successPercent = 0.0f;
        this.failPercent = 0.0f;
        this.mHandler = new Handler();
        this.drawRunnable = new Runnable() { // from class: com.ltech.smarthome.view.TimeProgressBar.3
            @Override // java.lang.Runnable
            public void run() {
                if (TimeProgressBar.this.currentProgress == 0 && TimeProgressBar.this.mOnListener != null) {
                    TimeProgressBar.this.mOnListener.onLoading();
                }
                TimeProgressBar.this.postInvalidate();
                TimeProgressBar.this.currentProgress++;
                if (TimeProgressBar.this.currentProgress <= TimeProgressBar.this.totalProgress) {
                    TimeProgressBar.this.mHandler.postDelayed(TimeProgressBar.this.drawRunnable, 1000L);
                } else {
                    TimeProgressBar.this.setState(1);
                }
            }
        };
        initAttr(context, attrs);
        init();
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.radius = (getWidth() / 2) - this.strokeWidth;
        this.rectF = new RectF((getWidth() / 2) - this.radius, (getHeight() / 2) - this.radius, (getWidth() / 2) + this.radius, (getHeight() / 2) + this.radius);
        Path path = new Path();
        this.successPath = path;
        path.moveTo((getWidth() * 0.7f) / 2.0f, (getWidth() * 1.1f) / 2.0f);
        this.successPath.lineTo((getWidth() * 0.9f) / 2.0f, (getWidth() * 1.3f) / 2.0f);
        this.successPath.lineTo((getWidth() * 1.3f) / 2.0f, (getWidth() * 0.8f) / 2.0f);
        this.successPathMeasure = new PathMeasure(this.successPath, false);
        Path path2 = new Path();
        this.failPath1 = path2;
        path2.moveTo((getWidth() * 0.8f) / 2.0f, (getWidth() * 0.8f) / 2.0f);
        this.failPath1.lineTo((getWidth() * 1.2f) / 2.0f, (getWidth() * 1.2f) / 2.0f);
        this.failPathMeasure1 = new PathMeasure(this.failPath1, false);
        Path path3 = new Path();
        this.failPath2 = path3;
        path3.moveTo((getWidth() * 1.2f) / 2.0f, (getWidth() * 0.8f) / 2.0f);
        this.failPath2.lineTo((getWidth() * 0.8f) / 2.0f, (getWidth() * 1.2f) / 2.0f);
        this.failPathMeasure2 = new PathMeasure(this.failPath2, false);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TimeProgressBar);
        this.textColor = obtainStyledAttributes.getColor(4, -1);
        this.firstProgressColor = obtainStyledAttributes.getColor(1, ContextCompat.getColor(context, R.color.color_text_red));
        this.secondProgressColor = obtainStyledAttributes.getColor(2, ContextCompat.getColor(context, R.color.color_line_gray));
        this.failColor = obtainStyledAttributes.getColor(0, ContextCompat.getColor(context, R.color.color_line_gray));
        this.textSize = (int) obtainStyledAttributes.getDimension(5, sp2px(20));
        this.strokeWidth = (int) obtainStyledAttributes.getDimension(3, dp2px(6));
        obtainStyledAttributes.recycle();
    }

    private void init() {
        Paint paint = new Paint();
        this.firstProgressPaint = paint;
        paint.setAntiAlias(true);
        this.firstProgressPaint.setDither(true);
        this.firstProgressPaint.setStyle(Paint.Style.STROKE);
        this.firstProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        this.firstProgressPaint.setStrokeJoin(Paint.Join.ROUND);
        this.firstProgressPaint.setStrokeWidth(this.strokeWidth);
        this.firstProgressPaint.setColor(this.firstProgressColor);
        Paint paint2 = new Paint();
        this.secondProgressPaint = paint2;
        paint2.setAntiAlias(true);
        this.secondProgressPaint.setDither(true);
        this.secondProgressPaint.setStyle(Paint.Style.STROKE);
        this.secondProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        this.secondProgressPaint.setStrokeJoin(Paint.Join.ROUND);
        this.secondProgressPaint.setStrokeWidth(this.strokeWidth);
        this.secondProgressPaint.setColor(this.secondProgressColor);
        Paint paint3 = new Paint();
        this.failPaint = paint3;
        paint3.setAntiAlias(true);
        this.failPaint.setDither(true);
        this.failPaint.setStyle(Paint.Style.STROKE);
        this.failPaint.setStrokeCap(Paint.Cap.ROUND);
        this.failPaint.setStrokeJoin(Paint.Join.ROUND);
        this.failPaint.setStrokeWidth(this.strokeWidth);
        this.failPaint.setColor(this.failColor);
        Paint paint4 = new Paint();
        this.textPaint = paint4;
        paint4.setAntiAlias(true);
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setColor(this.textColor);
        Paint.FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        this.textHeight = fontMetrics.descent + Math.abs(fontMetrics.ascent);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mSuccessAnimation = ofFloat;
        ofFloat.setDuration(500L);
        this.mSuccessAnimation.setInterpolator(new AccelerateInterpolator());
        this.mSuccessAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.TimeProgressBar$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                TimeProgressBar.this.lambda$init$0(valueAnimator);
            }
        });
        this.mSuccessAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.ltech.smarthome.view.TimeProgressBar.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                if (TimeProgressBar.this.mOnListener != null) {
                    TimeProgressBar.this.mOnListener.onSuccess();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (TimeProgressBar.this.mOnListener != null) {
                    TimeProgressBar.this.mOnListener.onSuccess();
                }
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mFailAnimation = ofFloat2;
        ofFloat2.setDuration(500L);
        this.mFailAnimation.setInterpolator(new AccelerateInterpolator());
        this.mFailAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ltech.smarthome.view.TimeProgressBar$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                TimeProgressBar.this.lambda$init$1(valueAnimator);
            }
        });
        this.mFailAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.ltech.smarthome.view.TimeProgressBar.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
                if (TimeProgressBar.this.mOnListener != null) {
                    TimeProgressBar.this.mOnListener.onFail();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                if (TimeProgressBar.this.mOnListener != null) {
                    TimeProgressBar.this.mOnListener.onFail();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(ValueAnimator valueAnimator) {
        this.successPercent = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(ValueAnimator valueAnimator) {
        this.failPercent = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int i = this.state;
        if (i == 0) {
            drawSuccess(canvas);
        } else if (i == 1) {
            drawFail(canvas);
        } else {
            if (i != 2) {
                return;
            }
            drawLoading(canvas);
        }
    }

    private void drawLoading(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, this.radius, this.secondProgressPaint);
        canvas.drawArc(this.rectF, -90.0f, (this.currentProgress / this.totalProgress) * 360.0f, false, this.firstProgressPaint);
        String str = (this.totalProgress - this.currentProgress) + this.unit;
        this.textWidth = this.textPaint.measureText(str, 0, str.length());
        canvas.drawText(str, (getWidth() / 2) - (this.textWidth / 2.0f), (getHeight() / 2) + (this.textHeight / 3.0f), this.textPaint);
    }

    private void drawSuccess(Canvas canvas) {
        this.path = new Path();
        PathMeasure pathMeasure = this.successPathMeasure;
        pathMeasure.getSegment(0.0f, this.successPercent * pathMeasure.getLength(), this.path, true);
        this.path.rLineTo(0.0f, 0.0f);
        canvas.drawPath(this.path, this.firstProgressPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (float) (((this.successPercent * 0.2d) + 0.8d) * this.radius), this.firstProgressPaint);
    }

    private void drawFail(Canvas canvas) {
        this.path = new Path();
        PathMeasure pathMeasure = this.failPathMeasure1;
        pathMeasure.getSegment(0.0f, this.failPercent * pathMeasure.getLength(), this.path, true);
        this.path.rLineTo(0.0f, 0.0f);
        canvas.drawPath(this.path, this.failPaint);
        this.path = new Path();
        PathMeasure pathMeasure2 = this.failPathMeasure2;
        pathMeasure2.getSegment(0.0f, this.failPercent * pathMeasure2.getLength(), this.path, true);
        this.path.rLineTo(0.0f, 0.0f);
        canvas.drawPath(this.path, this.failPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (float) (((this.failPercent * 0.2d) + 0.8d) * this.radius), this.failPaint);
    }

    public void setProgress(int progress) {
        this.currentProgress = progress;
        postInvalidate();
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setTotalProgress(int total) {
        this.totalProgress = total;
    }

    public void stopLoading() {
        this.mHandler.removeCallbacks(this.drawRunnable);
    }

    public void setState(int state) {
        this.state = state;
        if (state == 0) {
            stopLoading();
            this.mSuccessAnimation.start();
        } else if (state == 1) {
            stopLoading();
            this.mFailAnimation.start();
        } else {
            if (state != 2) {
                return;
            }
            this.currentProgress = 0;
            stopLoading();
            this.mHandler.post(this.drawRunnable);
        }
    }

    public int getState() {
        return this.state;
    }

    public void destroy() {
        stopLoading();
        this.mSuccessAnimation.cancel();
        this.mFailAnimation.cancel();
        this.mHandler = null;
    }

    public void setOnListener(onListener listener) {
        this.mOnListener = listener;
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(2, sp, getResources().getDisplayMetrics());
    }
}