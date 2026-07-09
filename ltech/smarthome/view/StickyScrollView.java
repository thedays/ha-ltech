package com.ltech.smarthome.view;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.widget.NestedScrollView;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class StickyScrollView extends NestedScrollView {
    private static final int DEFAULT_SHADOW_HEIGHT = 10;
    public static final String FLAG_HASTRANSPARANCY = "-hastransparancy";
    public static final String FLAG_NONCONSTANT = "-nonconstant";
    public static final String STICKY_TAG = "sticky";
    private boolean clipToPaddingHasBeenSet;
    private boolean clippingToPadding;
    private View currentlyStickingView;
    private boolean hasNotDoneActionDown;
    private final Runnable invalidateRunnable;
    private Drawable mShadowDrawable;
    private int mShadowHeight;
    private boolean redirectTouchesToStickyView;
    private boolean showStickyView;
    private int stickyViewLeftOffset;
    private float stickyViewTopOffset;
    private ArrayList<View> stickyViews;

    public StickyScrollView(Context context) {
        this(context, null);
    }

    public StickyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.scrollViewStyle);
    }

    public StickyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.showStickyView = true;
        this.invalidateRunnable = new Runnable() { // from class: com.ltech.smarthome.view.StickyScrollView.1
            @Override // java.lang.Runnable
            public void run() {
                if (StickyScrollView.this.currentlyStickingView != null) {
                    StickyScrollView stickyScrollView = StickyScrollView.this;
                    int leftForViewRelativeOnlyChild = stickyScrollView.getLeftForViewRelativeOnlyChild(stickyScrollView.currentlyStickingView);
                    StickyScrollView stickyScrollView2 = StickyScrollView.this;
                    int bottomForViewRelativeOnlyChild = stickyScrollView2.getBottomForViewRelativeOnlyChild(stickyScrollView2.currentlyStickingView);
                    StickyScrollView stickyScrollView3 = StickyScrollView.this;
                    StickyScrollView.this.invalidate(leftForViewRelativeOnlyChild, bottomForViewRelativeOnlyChild, stickyScrollView3.getRightForViewRelativeOnlyChild(stickyScrollView3.currentlyStickingView), (int) (StickyScrollView.this.getScrollY() + StickyScrollView.this.currentlyStickingView.getHeight() + StickyScrollView.this.stickyViewTopOffset));
                }
                StickyScrollView.this.postDelayed(this, 16L);
            }
        };
        this.hasNotDoneActionDown = true;
        setup();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, com.ltech.smarthome.R.styleable.StickyScrollView, defStyle, 0);
        this.mShadowHeight = obtainStyledAttributes.getDimensionPixelSize(1, (int) ((context.getResources().getDisplayMetrics().density * 10.0f) + 0.5f));
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        if (resourceId != -1) {
            this.mShadowDrawable = context.getResources().getDrawable(resourceId);
        }
        obtainStyledAttributes.recycle();
    }

    public void setShadowHeight(int height) {
        this.mShadowHeight = height;
    }

    public void setup() {
        this.stickyViews = new ArrayList<>();
    }

    public void setShowStickyView(boolean setStickyViewShow) {
        this.showStickyView = setStickyViewShow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLeftForViewRelativeOnlyChild(View v) {
        int left = v.getLeft();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            left += v.getLeft();
        }
        return left;
    }

    private int getTopForViewRelativeOnlyChild(View v) {
        int top = v.getTop();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            top += v.getTop();
        }
        return top;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRightForViewRelativeOnlyChild(View v) {
        int right = v.getRight();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            right += v.getRight();
        }
        return right;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBottomForViewRelativeOnlyChild(View v) {
        int bottom = v.getBottom();
        while (v.getParent() != getChildAt(0)) {
            v = (View) v.getParent();
            bottom += v.getBottom();
        }
        return bottom;
    }

    @Override // androidx.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int l, int t, int r, int b2) {
        super.onLayout(changed, l, t, r, b2);
        if (!this.clipToPaddingHasBeenSet) {
            this.clippingToPadding = true;
        }
        notifyHierarchyChanged();
    }

    @Override // android.view.ViewGroup
    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        this.clippingToPadding = clipToPadding;
        this.clipToPaddingHasBeenSet = true;
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public void addView(View child) {
        super.addView(child);
        findStickyViews(child);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public void addView(View child, int index) {
        super.addView(child, index);
        findStickyViews(child);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        findStickyViews(child);
    }

    @Override // android.view.ViewGroup
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        findStickyViews(child);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup, android.view.ViewManager
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        findStickyViews(child);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.currentlyStickingView != null) {
            canvas.save();
            canvas.translate(getPaddingLeft() + this.stickyViewLeftOffset, getScrollY() + this.stickyViewTopOffset + (this.clippingToPadding ? getPaddingTop() : 0));
            canvas.clipRect(0.0f, this.clippingToPadding ? -this.stickyViewTopOffset : 0.0f, getWidth() - this.stickyViewLeftOffset, this.currentlyStickingView.getHeight() + this.mShadowHeight + 1);
            if (this.mShadowDrawable != null) {
                this.mShadowDrawable.setBounds(0, this.currentlyStickingView.getHeight(), this.currentlyStickingView.getWidth(), this.currentlyStickingView.getHeight() + this.mShadowHeight);
                this.mShadowDrawable.draw(canvas);
            }
            canvas.clipRect(0.0f, this.clippingToPadding ? -this.stickyViewTopOffset : 0.0f, getWidth(), this.currentlyStickingView.getHeight());
            if (getStringTagForView(this.currentlyStickingView).contains(FLAG_HASTRANSPARANCY)) {
                showView(this.currentlyStickingView);
                this.currentlyStickingView.draw(canvas);
                hideView(this.currentlyStickingView);
            } else {
                this.currentlyStickingView.draw(canvas);
            }
            canvas.restore();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            this.redirectTouchesToStickyView = true;
        }
        if (this.redirectTouchesToStickyView) {
            boolean z = this.currentlyStickingView != null;
            this.redirectTouchesToStickyView = z;
            if (z) {
                this.redirectTouchesToStickyView = ev.getY() <= ((float) this.currentlyStickingView.getHeight()) + this.stickyViewTopOffset && ev.getX() >= ((float) getLeftForViewRelativeOnlyChild(this.currentlyStickingView)) && ev.getX() <= ((float) getRightForViewRelativeOnlyChild(this.currentlyStickingView));
            }
        } else if (this.currentlyStickingView == null) {
            this.redirectTouchesToStickyView = false;
        }
        if (this.redirectTouchesToStickyView) {
            ev.offsetLocation(0.0f, ((getScrollY() + this.stickyViewTopOffset) - getTopForViewRelativeOnlyChild(this.currentlyStickingView)) * (-1.0f));
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.redirectTouchesToStickyView) {
            ev.offsetLocation(0.0f, (getScrollY() + this.stickyViewTopOffset) - getTopForViewRelativeOnlyChild(this.currentlyStickingView));
        }
        if (ev.getAction() == 0) {
            this.hasNotDoneActionDown = false;
        }
        if (this.hasNotDoneActionDown) {
            MotionEvent obtain = MotionEvent.obtain(ev);
            obtain.setAction(0);
            super.onTouchEvent(obtain);
            this.hasNotDoneActionDown = false;
        }
        if (ev.getAction() == 1 || ev.getAction() == 3) {
            this.hasNotDoneActionDown = true;
        }
        return super.onTouchEvent(ev);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        doTheStickyThing();
    }

    private void doTheStickyThing() {
        float min;
        Iterator<View> it = this.stickyViews.iterator();
        View view = null;
        View view2 = null;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            View next = it.next();
            int topForViewRelativeOnlyChild = (getTopForViewRelativeOnlyChild(next) - getScrollY()) + (this.clippingToPadding ? 0 : getPaddingTop());
            if (topForViewRelativeOnlyChild <= 0) {
                if (view != null) {
                    if (topForViewRelativeOnlyChild > (getTopForViewRelativeOnlyChild(view) - getScrollY()) + (this.clippingToPadding ? 0 : getPaddingTop())) {
                    }
                }
                view = next;
            } else {
                if (view2 != null) {
                    if (topForViewRelativeOnlyChild < (getTopForViewRelativeOnlyChild(view2) - getScrollY()) + (this.clippingToPadding ? 0 : getPaddingTop())) {
                    }
                }
                view2 = next;
            }
        }
        if (view != null) {
            if (view2 == null) {
                min = 0.0f;
            } else {
                min = Math.min(0, ((getTopForViewRelativeOnlyChild(view2) - getScrollY()) + (this.clippingToPadding ? 0 : getPaddingTop())) - view.getHeight());
            }
            this.stickyViewTopOffset = min;
            View view3 = this.currentlyStickingView;
            if (view != view3) {
                if (view3 != null) {
                    stopStickingCurrentlyStickingView();
                }
                this.stickyViewLeftOffset = getLeftForViewRelativeOnlyChild(view);
                startStickingView(view);
                return;
            }
            return;
        }
        if (this.currentlyStickingView != null) {
            stopStickingCurrentlyStickingView();
        }
    }

    private void startStickingView(View viewThatShouldStick) {
        this.currentlyStickingView = viewThatShouldStick;
        if (getStringTagForView(viewThatShouldStick).contains(FLAG_HASTRANSPARANCY)) {
            hideView(this.currentlyStickingView);
        }
        if (((String) this.currentlyStickingView.getTag()).contains("-nonconstant")) {
            post(this.invalidateRunnable);
        }
    }

    private void stopStickingCurrentlyStickingView() {
        if (getStringTagForView(this.currentlyStickingView).contains(FLAG_HASTRANSPARANCY)) {
            showView(this.currentlyStickingView);
        }
        this.currentlyStickingView = null;
        removeCallbacks(this.invalidateRunnable);
    }

    public void notifyStickyAttributeChanged() {
        notifyHierarchyChanged();
    }

    private void notifyHierarchyChanged() {
        if (this.currentlyStickingView != null) {
            stopStickingCurrentlyStickingView();
        }
        this.stickyViews.clear();
        findStickyViews(getChildAt(0));
        doTheStickyThing();
        invalidate();
    }

    private void findStickyViews(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                String stringTagForView = getStringTagForView(viewGroup.getChildAt(i));
                if (stringTagForView != null && stringTagForView.contains("sticky") && this.showStickyView) {
                    this.stickyViews.add(viewGroup.getChildAt(i));
                } else if ((viewGroup.getChildAt(i) instanceof ViewGroup) && this.showStickyView) {
                    findStickyViews(viewGroup.getChildAt(i));
                }
            }
            return;
        }
        String str = (String) v.getTag();
        if (str != null && str.contains("sticky") && this.showStickyView) {
            this.stickyViews.add(v);
        }
    }

    private String getStringTagForView(View v) {
        return String.valueOf(v.getTag());
    }

    private void hideView(View v) {
        v.setAlpha(0.0f);
    }

    private void showView(View v) {
        v.setAlpha(1.0f);
    }
}