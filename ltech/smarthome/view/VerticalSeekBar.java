package com.ltech.smarthome.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;

/* loaded from: classes4.dex */
public class VerticalSeekBar extends AppCompatSeekBar {
    private static final int PICKER_RADIUS = 100;
    private int bgHeight;
    private int bgWidth;
    private boolean isInScrollingContainer;
    private Point mCenterPoint;
    private boolean mIsDragging;
    protected Point mPickPoint;
    private int mPickPointX;
    private int mPickPointY;
    protected int mPickerRadius;
    private int mScaledTouchSlop;
    private float mTouchDownY;
    float mTouchProgressOffset;
    private SeekBar.OnSeekBarChangeListener mySeekBarChangeListener;

    public boolean isInScrollingContainer() {
        return this.isInScrollingContainer;
    }

    public void setInScrollingContainer(boolean isInScrollingContainer) {
        this.isInScrollingContainer = isInScrollingContainer;
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isInScrollingContainer = false;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mPickPoint = new Point(-100, -100);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isInScrollingContainer = false;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mPickPoint = new Point(-100, -100);
    }

    public VerticalSeekBar(Context context) {
        super(context);
        this.isInScrollingContainer = false;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mPickPoint = new Point(-100, -100);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
        this.mCenterPoint = new Point(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        this.bgHeight = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        this.bgWidth = measuredHeight;
        int i = measuredHeight / 30;
        this.mPickerRadius = i;
        if (i > 100) {
            this.mPickerRadius = 100;
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override // androidx.appcompat.widget.AppCompatSeekBar, android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        canvas.rotate(-90.0f);
        canvas.translate(-getHeight(), 0.0f);
        super.onDraw(canvas);
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        int action = event.getAction();
        if (action != 0) {
            if (action == 1) {
                if (this.mIsDragging) {
                    trackTouchEvent(event);
                    onStopTrackingTouch();
                    setPressed(false);
                } else {
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                    onStopTrackingTouch();
                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                invalidate();
            } else if (action == 2) {
                if (this.mIsDragging) {
                    this.mPickPointX = (int) event.getX();
                    int y = (int) event.getY();
                    this.mPickPointY = y;
                    if (y > 0) {
                        this.mPickPoint.set(this.mPickPointX, y);
                        trackTouchEvent(event);
                    }
                } else if (Math.abs(event.getY() - this.mTouchDownY) > this.mScaledTouchSlop) {
                    setPressed(true);
                    invalidate();
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                    attemptClaimDrag();
                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
            }
        } else if (isInScrollingContainer()) {
            this.mTouchDownY = event.getY();
        } else {
            this.mPickPointX = (int) event.getX();
            int y2 = (int) event.getY();
            this.mPickPointY = y2;
            if (y2 > 0) {
                this.mPickPoint.set(this.mPickPointX, y2);
                setPressed(true);
                invalidate();
                onStartTrackingTouch();
                trackTouchEvent(event);
                attemptClaimDrag();
                onSizeChanged(getWidth(), getHeight(), 0, 0);
            }
        }
        return true;
    }

    private void trackTouchEvent(MotionEvent event) {
        float f;
        int height = getHeight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i = (height - paddingTop) - paddingBottom;
        int y = (int) event.getY();
        int i2 = height - paddingBottom;
        float f2 = 0.0f;
        if (y > i2) {
            f = 0.0f;
        } else if (y < paddingTop) {
            f = 1.0f;
        } else {
            f2 = this.mTouchProgressOffset;
            f = ((i - y) + paddingTop) / i;
        }
        setProgress((int) (f2 + (f * getMax())));
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mySeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mySeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    @Override // android.widget.SeekBar
    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        super.setOnSeekBarChangeListener(l);
        this.mySeekBarChangeListener = l;
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mySeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(this, getProgress(), isPressed());
        }
    }
}