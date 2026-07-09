package com.ltech.smarthome.view;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsSeekBar;
import java.io.PrintStream;

/* loaded from: classes4.dex */
public class NodeSeekBar extends AbsSeekBar {
    private int height;
    private OnSeekBarChangeListener mOnSeekBarChangeListener;
    private Drawable mThumb;
    private int width;

    public interface OnSeekBarChangeListener {
        void onProgressChanged(NodeSeekBar VerticalSeekBar, int progress, boolean fromUser);

        void onStartTrackingTouch(NodeSeekBar VerticalSeekBar);

        void onStopTrackingTouch(NodeSeekBar VerticalSeekBar);
    }

    public NodeSeekBar(Context context) {
        this(context, null);
    }

    public NodeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarStyle);
    }

    public NodeSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        this.mOnSeekBarChangeListener = l;
    }

    void onStartTrackingTouch() {
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    void onStopTrackingTouch() {
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    void onProgressRefresh(float scale, boolean fromUser) {
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getHeight(), drawable, scale, Integer.MIN_VALUE);
            invalidate();
        }
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(this, getProgress(), fromUser);
        }
    }

    private void setThumbPos(int w, Drawable thumb, float scale, int gap) {
        int i;
        int paddingLeft = (w - getPaddingLeft()) - getPaddingRight();
        System.out.println("--setThumbPos--available=" + paddingLeft + "  scale=" + scale + "  getPaddingLeft=" + getPaddingLeft());
        int intrinsicWidth = thumb.getIntrinsicWidth();
        int intrinsicHeight = thumb.getIntrinsicHeight();
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder("--available=");
        sb.append(paddingLeft);
        printStream.println(sb.toString());
        int i2 = (int) (scale * paddingLeft);
        if (gap == Integer.MIN_VALUE) {
            Rect bounds = thumb.getBounds();
            gap = bounds.top;
            i = bounds.bottom;
        } else {
            i = gap + intrinsicHeight;
        }
        thumb.setBounds(i2, gap, intrinsicWidth + i2, i);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onDraw(Canvas c2) {
        System.out.println("--onDraw--height=" + this.height);
        c2.rotate(-90.0f);
        c2.translate((float) (-this.height), 0.0f);
        super.onDraw(c2);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable thumb = getThumb();
        System.out.println("--getThumb--Height=" + thumb.getIntrinsicHeight());
        System.out.println("--getThumb--Width=" + thumb.getIntrinsicWidth());
        this.width = thumb.getIntrinsicWidth();
        this.height = View.MeasureSpec.getSize(heightMeasureSpec);
        this.height = (getPaddingLeft() * 2) + 200;
        System.out.println("height=" + this.height + "width=" + this.width);
        setMeasuredDimension(this.width, this.height);
    }

    @Override // android.widget.AbsSeekBar
    public void setThumb(Drawable thumb) {
        this.mThumb = thumb;
        super.setThumb(thumb);
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldw, oldh);
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        int action = event.getAction();
        if (action == 0) {
            setPressed(true);
            onStartTrackingTouch();
            trackTouchEvent(event);
        } else if (action == 1) {
            trackTouchEvent(event);
            onStopTrackingTouch();
            setPressed(false);
        } else if (action == 2) {
            trackTouchEvent(event);
            attemptClaimDrag();
        } else if (action == 3) {
            onStopTrackingTouch();
            setPressed(false);
        }
        return true;
    }

    private void trackTouchEvent(MotionEvent event) {
        float paddingLeft;
        int height = getHeight();
        System.out.println("--trackTouchEvent-getHeight=" + getHeight() + "  getPaddingBottom=" + getPaddingBottom() + "  getPaddingLeft=" + getPaddingLeft());
        int paddingLeft2 = (height - getPaddingLeft()) - getPaddingRight();
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder("--available=");
        sb.append(paddingLeft2);
        printStream.println(sb.toString());
        int y = (int) event.getY();
        if (y > height - getPaddingRight()) {
            paddingLeft = 0.0f;
        } else {
            paddingLeft = y < getPaddingTop() ? 1.0f : ((height - y) - getPaddingLeft()) / paddingLeft2;
        }
        PrintStream printStream2 = System.out;
        StringBuilder sb2 = new StringBuilder("--scale=");
        sb2.append(paddingLeft);
        sb2.append("  progress=");
        int max = (int) (getMax() * paddingLeft);
        sb2.append(max);
        printStream2.println(sb2.toString());
        setProgress(max);
    }

    private void attemptClaimDrag() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        KeyEvent keyEvent;
        if (event.getAction() != 0) {
            return false;
        }
        switch (event.getKeyCode()) {
            case 19:
                keyEvent = new KeyEvent(0, 22);
                break;
            case 20:
                keyEvent = new KeyEvent(0, 21);
                break;
            case 21:
                keyEvent = new KeyEvent(0, 20);
                break;
            case 22:
                keyEvent = new KeyEvent(0, 19);
                break;
            default:
                keyEvent = new KeyEvent(0, event.getKeyCode());
                break;
        }
        return keyEvent.dispatch(this);
    }
}