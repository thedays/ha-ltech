package com.ltech.smarthome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/* loaded from: classes4.dex */
public class NestedScrollParentView extends LinearLayout {
    private View mHeader;
    private int mHeaderHeight;

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    public NestedScrollParentView(Context context) {
        super(context);
    }

    public NestedScrollParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        boolean z = dy > 0 && getScrollY() < this.mHeaderHeight;
        boolean z2 = dy < 0 && getScrollY() > 0 && !target.canScrollVertically(-1);
        if (z || z2) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            this.mHeader = getChildAt(0);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mHeaderHeight = this.mHeader.getMeasuredHeight();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mHeader.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, 0));
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(heightMeasureSpec) + this.mHeader.getMeasuredHeight(), View.MeasureSpec.getMode(heightMeasureSpec)));
    }

    @Override // android.view.View
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        } else {
            int i = this.mHeaderHeight;
            if (y > i) {
                y = i;
            }
        }
        super.scrollTo(x, y);
    }
}