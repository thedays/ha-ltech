package com.ltech.smarthome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatSeekBar;

/* loaded from: classes4.dex */
public class HorizontalSeekBar extends AppCompatSeekBar {
    private boolean isInScrollingContainer;
    private boolean mIsDragging;
    private int mScaledTouchSlop;
    private float mTouchDownX;
    float mTouchProgressOffset;
    protected SeekBar.OnSeekBarChangeListener mySeekBarChangeListener;

    public boolean isInScrollingContainer() {
        return this.isInScrollingContainer;
    }

    public void setInScrollingContainer(boolean isInScrollingContainer) {
        this.isInScrollingContainer = isInScrollingContainer;
    }

    public HorizontalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isInScrollingContainer = false;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public HorizontalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isInScrollingContainer = false;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public HorizontalSeekBar(Context context) {
        super(context);
        this.isInScrollingContainer = false;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
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
                    trackTouchEvent(event);
                } else if (Math.abs(event.getX() - this.mTouchDownX) > this.mScaledTouchSlop) {
                    setPressed(true);
                    invalidate();
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                    attemptClaimDrag();
                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
            }
        } else if (isInScrollingContainer()) {
            this.mTouchDownX = event.getX();
        } else {
            setPressed(true);
            invalidate();
            onStartTrackingTouch();
            trackTouchEvent(event);
            attemptClaimDrag();
            onSizeChanged(getWidth(), getHeight(), 0, 0);
        }
        return true;
    }

    private void trackTouchEvent(MotionEvent event) {
        float f;
        int width = getWidth();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int i = (width - paddingLeft) - paddingRight;
        int x = (int) event.getX();
        float f2 = 0.0f;
        if (x < paddingLeft) {
            f = 0.0f;
        } else if (x > width - paddingRight) {
            f = 1.0f;
        } else {
            f2 = this.mTouchProgressOffset;
            f = (x - paddingLeft) / i;
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
        if (getProgress() != progress) {
            super.setProgress(progress);
            onSizeChanged(getWidth(), getHeight(), 0, 0);
            SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = this.mySeekBarChangeListener;
            if (onSeekBarChangeListener != null) {
                onSeekBarChangeListener.onProgressChanged(this, getProgress(), isPressed());
            }
        }
    }
}