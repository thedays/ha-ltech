package com.ltech.smarthome.view.pagergridlayoutmanager;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class PagerGridSmoothScroller extends LinearSmoothScroller {
    static final int MAX_SCROLL_ON_FLING_DURATION = 500;
    static final float MILLISECONDS_PER_INCH = 100.0f;
    private static final String TAG = "PagerGridSmoothScroller";
    private final PagerGridLayoutManager mLayoutManager;
    private final RecyclerView mRecyclerView;

    PagerGridSmoothScroller(RecyclerView recyclerView, PagerGridLayoutManager layoutManager) {
        super(recyclerView.getContext());
        this.mRecyclerView = recyclerView;
        this.mLayoutManager = layoutManager;
    }

    @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
    protected void onTargetFound(View targetView, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
        PagerGridLayoutManager pagerGridLayoutManager;
        int position;
        PointF computeScrollVectorForPosition;
        Rect endSnapRect;
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (!(layoutManager instanceof PagerGridLayoutManager) || (computeScrollVectorForPosition = computeScrollVectorForPosition((position = (pagerGridLayoutManager = (PagerGridLayoutManager) layoutManager).getPosition(targetView)))) == null) {
            return;
        }
        boolean z = computeScrollVectorForPosition.x > 0.0f || computeScrollVectorForPosition.y > 0.0f;
        if (pagerGridLayoutManager.shouldHorizontallyReverseLayout()) {
            z = !z;
        }
        if (z) {
            endSnapRect = pagerGridLayoutManager.getStartSnapRect();
        } else {
            endSnapRect = pagerGridLayoutManager.getEndSnapRect();
        }
        Rect rect = new Rect();
        layoutManager.getDecoratedBoundsWithMargins(targetView, rect);
        int calculateDx = calculateDx(pagerGridLayoutManager, endSnapRect, rect, z);
        int calculateDy = calculateDy(pagerGridLayoutManager, endSnapRect, rect, z);
        int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(calculateDx), Math.abs(calculateDy)));
        if (PagerGridLayoutManager.DEBUG) {
            Log.i(TAG, "onTargetFound-targetPosition:" + position + ", dx:" + calculateDx + ",dy:" + calculateDy + ",time:" + calculateTimeForDeceleration + ",isLayoutToEnd:" + z + ",snapRect:" + endSnapRect + ",targetRect:" + rect);
        }
        if (calculateTimeForDeceleration > 0) {
            action.update(calculateDx, calculateDy, calculateTimeForDeceleration, this.mDecelerateInterpolator);
        } else {
            pagerGridLayoutManager.calculateCurrentPagerIndexByPosition(position);
        }
    }

    @Override // androidx.recyclerview.widget.LinearSmoothScroller
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        float millisecondPreInch = this.mLayoutManager.getMillisecondPreInch() / displayMetrics.densityDpi;
        if (PagerGridLayoutManager.DEBUG) {
            Log.i(TAG, "calculateSpeedPerPixel-speed: " + millisecondPreInch);
        }
        return millisecondPreInch;
    }

    @Override // androidx.recyclerview.widget.LinearSmoothScroller
    protected final int calculateTimeForScrolling(int dx) {
        int min = Math.min(this.mLayoutManager.getMaxScrollOnFlingDuration(), super.calculateTimeForScrolling(dx));
        Log.i(TAG, "calculateTimeForScrolling-time: " + min);
        return min;
    }

    static int calculateDx(PagerGridLayoutManager manager, Rect snapRect, Rect targetRect) {
        if (manager.canScrollHorizontally()) {
            return targetRect.left - snapRect.left;
        }
        return 0;
    }

    static int calculateDy(PagerGridLayoutManager manager, Rect snapRect, Rect targetRect) {
        if (manager.canScrollVertically()) {
            return targetRect.top - snapRect.top;
        }
        return 0;
    }

    static int calculateDx(PagerGridLayoutManager manager, Rect snapRect, Rect targetRect, boolean isLayoutToEnd) {
        int i;
        int i2;
        if (!manager.canScrollHorizontally()) {
            return 0;
        }
        if (isLayoutToEnd) {
            i = targetRect.left;
            i2 = snapRect.left;
        } else {
            i = targetRect.right;
            i2 = snapRect.right;
        }
        return i - i2;
    }

    static int calculateDy(PagerGridLayoutManager manager, Rect snapRect, Rect targetRect, boolean isLayoutToEnd) {
        int i;
        int i2;
        if (!manager.canScrollVertically()) {
            return 0;
        }
        if (isLayoutToEnd) {
            i = targetRect.top;
            i2 = snapRect.top;
        } else {
            i = targetRect.bottom;
            i2 = snapRect.bottom;
        }
        return i - i2;
    }
}