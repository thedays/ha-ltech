package com.ltech.smarthome.utils.selectedCountryLib.demo;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import androidx.core.widget.NestedScrollView;

/* loaded from: classes4.dex */
public class ReboundScrollView extends NestedScrollView {
    private int lastY;
    private View mContentView;
    private boolean mEnableBottomRebound;
    private boolean mEnableTopRebound;
    private OnReboundEndListener mOnReboundEndListener;
    private Rect mRect;
    private boolean rebound;
    private int reboundDirection;

    public interface OnReboundEndListener {
        void onReboundBottomComplete();

        void onReboundTopComplete();
    }

    public ReboundScrollView(Context context) {
        super(context);
        this.mEnableTopRebound = true;
        this.mEnableBottomRebound = true;
        this.mRect = new Rect();
        this.rebound = false;
        this.reboundDirection = 0;
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mEnableTopRebound = true;
        this.mEnableBottomRebound = true;
        this.mRect = new Rect();
        this.rebound = false;
        this.reboundDirection = 0;
    }

    public ReboundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mEnableTopRebound = true;
        this.mEnableBottomRebound = true;
        this.mRect = new Rect();
        this.rebound = false;
        this.reboundDirection = 0;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mContentView = getChildAt(0);
    }

    @Override // androidx.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b2) {
        super.onLayout(changed, l, t, r, b2);
        View view = this.mContentView;
        if (view == null) {
            return;
        }
        this.mRect.set(view.getLeft(), this.mContentView.getTop(), this.mContentView.getRight(), this.mContentView.getBottom());
    }

    public ReboundScrollView setOnReboundEndListener(OnReboundEndListener onReboundEndListener) {
        this.mOnReboundEndListener = onReboundEndListener;
        return this;
    }

    public ReboundScrollView setEnableTopRebound(boolean enableTopRebound) {
        this.mEnableTopRebound = enableTopRebound;
        return this;
    }

    public ReboundScrollView setEnableBottomRebound(boolean mEnableBottomRebound) {
        this.mEnableBottomRebound = mEnableBottomRebound;
        return this;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.mContentView == null) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getAction();
        if (action == 0) {
            this.lastY = (int) ev.getY();
        } else if (action != 1) {
            if (action == 2) {
                if (!isScrollToTop()) {
                    this.lastY = (int) ev.getY();
                } else {
                    int y = (int) (ev.getY() - this.lastY);
                    if ((this.mEnableTopRebound || y <= 0) && (this.mEnableBottomRebound || y >= 0)) {
                        int i = (int) (y * 0.48d);
                        this.mContentView.layout(this.mRect.left, this.mRect.top + i, this.mRect.right, this.mRect.bottom + i);
                        this.rebound = true;
                    }
                }
            }
        } else if (this.rebound) {
            this.reboundDirection = this.mContentView.getTop() - this.mRect.top;
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, this.mContentView.getTop(), this.mRect.top);
            translateAnimation.setDuration(300L);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.ltech.smarthome.utils.selectedCountryLib.demo.ReboundScrollView.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    if (ReboundScrollView.this.mOnReboundEndListener != null) {
                        if (ReboundScrollView.this.reboundDirection > 0) {
                            ReboundScrollView.this.mOnReboundEndListener.onReboundTopComplete();
                        }
                        if (ReboundScrollView.this.reboundDirection < 0) {
                            ReboundScrollView.this.mOnReboundEndListener.onReboundBottomComplete();
                        }
                        ReboundScrollView.this.reboundDirection = 0;
                    }
                }
            });
            this.mContentView.startAnimation(translateAnimation);
            this.mContentView.layout(this.mRect.left, this.mRect.top, this.mRect.right, this.mRect.bottom);
            this.rebound = false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // androidx.core.widget.NestedScrollView
    public void setFillViewport(boolean fillViewport) {
        super.setFillViewport(true);
    }

    private boolean isScrollToTop() {
        return getScrollY() == 0;
    }

    private boolean isScrollToBottom() {
        return this.mContentView.getHeight() <= getHeight() + getScrollY();
    }
}