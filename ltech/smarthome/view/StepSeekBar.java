package com.ltech.smarthome.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import androidx.core.internal.view.SupportMenu;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class StepSeekBar extends View {
    private int barHeight;
    private int curStep;
    private float curX;
    private int defaultColor;
    private boolean enable;
    private int height;
    private boolean isFromUser;
    private boolean mIsDragging;
    private OnStepChangedListener mOnStepChangedListener;
    private Paint mPaint;
    private float mTouchDownX;
    private float mTouchSlop;
    private int progressColor;
    private int step;
    private int stepLength;
    private int stepRadius;
    private int width;

    public interface OnStepChangedListener {
        void onColorChanged(int step, boolean isFromUser);
    }

    private boolean isInScrollingContainer() {
        return false;
    }

    public StepSeekBar(Context context) {
        this(context, null);
    }

    public StepSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.enable = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.StepSeekBar);
        this.barHeight = (int) (obtainStyledAttributes.getDimension(1, (int) TypedValue.applyDimension(1, 3.0f, context.getResources().getDisplayMetrics())) / 2.0f);
        this.stepRadius = (int) obtainStyledAttributes.getDimension(5, (int) TypedValue.applyDimension(1, 10.0f, context.getResources().getDisplayMetrics()));
        this.step = obtainStyledAttributes.getInt(4, 3);
        this.curStep = obtainStyledAttributes.getInt(2, 0);
        this.defaultColor = obtainStyledAttributes.getColor(0, -7829368);
        this.progressColor = obtainStyledAttributes.getColor(3, SupportMenu.CATEGORY_MASK);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(100, widthMeasureSpec), getDefaultSize(this.stepRadius, heightMeasureSpec));
    }

    public static int getDefaultSize(int size, int measureSpec) {
        return View.MeasureSpec.getMode(measureSpec) != 1073741824 ? size : View.MeasureSpec.getSize(measureSpec);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height = getMeasuredHeight();
        int measuredWidth = ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - (this.stepRadius * 2);
        this.width = measuredWidth;
        this.stepLength = measuredWidth / this.step;
        this.curX = r1 * this.curStep;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgress(canvas);
        drawSteps(canvas);
    }

    private void drawSteps(Canvas canvas) {
        for (int i = 0; i <= this.step; i++) {
            if (i <= this.curStep) {
                this.mPaint.setColor(this.progressColor);
            } else {
                this.mPaint.setColor(this.defaultColor);
            }
            canvas.drawCircle((this.stepLength * i) + r1, this.height / 2, this.stepRadius, this.mPaint);
        }
    }

    private void drawProgress(Canvas canvas) {
        this.mPaint.setColor(this.progressColor);
        int i = this.height;
        int i2 = this.barHeight;
        canvas.drawRect(0.0f, (i / 2) - i2, this.curX, (i / 2) + i2, this.mPaint);
        this.mPaint.setColor(this.defaultColor);
        float f = this.curX;
        int i3 = this.height;
        int i4 = this.barHeight;
        canvas.drawRect(f, (i3 / 2) - i4, this.width, (i3 / 2) + i4, this.mPaint);
    }

    public int getCurStep() {
        return this.curStep;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = event.getActionMasked();
        this.isFromUser = true;
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                if (!this.mIsDragging) {
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                }
                onStopTrackingTouch(event);
                postInvalidate();
                this.isFromUser = false;
            } else if (actionMasked != 2) {
                if (actionMasked == 3) {
                    onStopTrackingTouch(event);
                    this.isFromUser = false;
                }
            } else if (this.mIsDragging) {
                trackTouchEvent(event);
                postInvalidate();
            } else if (Math.abs(event.getY() - this.mTouchDownX) > this.mTouchSlop) {
                onStartTrackingTouch();
                trackTouchEvent(event);
                attemptClaimDrag();
                postInvalidate();
            }
        } else if (isInScrollingContainer()) {
            this.mTouchDownX = (int) event.getX();
        } else {
            onStartTrackingTouch();
            trackTouchEvent(event);
            attemptClaimDrag();
            postInvalidate();
        }
        return true;
    }

    private void onStartTrackingTouch() {
        this.mIsDragging = true;
        setPressed(true);
    }

    private void trackTouchEvent(MotionEvent event) {
        checkProgress((int) event.getX(), false);
        OnStepChangedListener onStepChangedListener = this.mOnStepChangedListener;
        if (onStepChangedListener != null) {
            onStepChangedListener.onColorChanged(this.curStep, this.isFromUser);
        }
    }

    private void onStopTrackingTouch(MotionEvent event) {
        this.mIsDragging = false;
        checkProgress((int) event.getX(), true);
        OnStepChangedListener onStepChangedListener = this.mOnStepChangedListener;
        if (onStepChangedListener != null) {
            onStepChangedListener.onColorChanged(this.curStep, this.isFromUser);
        }
        setPressed(false);
    }

    private void checkProgress(int x, boolean finish) {
        if (finish) {
            int i = this.stepLength;
            if (x % i < i / 2) {
                this.curStep = x / i;
            } else {
                this.curStep = (x / i) + 1;
            }
            this.curX = this.curStep * i;
            return;
        }
        this.curX = x;
        this.curStep = x / this.stepLength;
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setOnStepChangedListener(OnStepChangedListener onStepChangedListener) {
        this.mOnStepChangedListener = onStepChangedListener;
    }
}