package com.ltech.smarthome.view.pagergridlayoutmanager;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PagerGridSnapHelper extends SnapHelper {
    private static final String TAG = "PagerGridSnapHelper";
    private RecyclerView mRecyclerView;
    private final List<View> snapList = new ArrayList(2);

    PagerGridSnapHelper() {
    }

    @Override // androidx.recyclerview.widget.SnapHelper
    public void attachToRecyclerView(RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.SnapHelper
    protected RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager layoutManager) {
        if ((layoutManager instanceof PagerGridLayoutManager) && this.mRecyclerView != null) {
            return new PagerGridSmoothScroller(this.mRecyclerView, (PagerGridLayoutManager) layoutManager);
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.SnapHelper
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int i = -1;
        if (layoutManager.getItemCount() == 0 || layoutManager.getChildCount() == 0 || !(layoutManager instanceof PagerGridLayoutManager)) {
            return -1;
        }
        PagerGridLayoutManager pagerGridLayoutManager = (PagerGridLayoutManager) layoutManager;
        if (pagerGridLayoutManager.getLayoutState().mLastScrollDelta == 0) {
            return -1;
        }
        int[] calculateScrollDistance = calculateScrollDistance(velocityX, velocityY);
        int i2 = pagerGridLayoutManager.canScrollHorizontally() ? calculateScrollDistance[0] : calculateScrollDistance[1];
        if (pagerGridLayoutManager.shouldHorizontallyReverseLayout()) {
            i2 = -i2;
        }
        boolean isForwardFling = isForwardFling(pagerGridLayoutManager, velocityX, velocityY);
        int layoutCenter = getLayoutCenter(pagerGridLayoutManager);
        reacquireSnapList(pagerGridLayoutManager);
        int size = this.snapList.size();
        if (size == 1) {
            View view = this.snapList.get(0);
            int position = pagerGridLayoutManager.getPosition(view);
            if (!isForwardFling ? Math.abs(i2) < layoutCenter ? !pagerGridLayoutManager.shouldHorizontallyReverseLayout() ? getViewDecoratedEnd(pagerGridLayoutManager, view) + Math.abs(i2) <= layoutCenter || position - 1 >= 0 : getViewDecoratedStart(pagerGridLayoutManager, view) - Math.abs(i2) >= layoutCenter || position - 1 >= 0 : position - 1 >= 0 : i2 >= layoutCenter || (!pagerGridLayoutManager.shouldHorizontallyReverseLayout() ? !(getViewDecoratedStart(pagerGridLayoutManager, view) - i2 > layoutCenter && position - 1 < 0) : !(getViewDecoratedEnd(pagerGridLayoutManager, view) + i2 < layoutCenter && position - 1 < 0))) {
                i = position;
            }
        } else if (size == 2) {
            View view2 = this.snapList.get(0);
            pagerGridLayoutManager.getPosition(view2);
            View view3 = this.snapList.get(1);
            int position2 = pagerGridLayoutManager.getPosition(view3);
            if (!pagerGridLayoutManager.shouldHorizontallyReverseLayout() ? !isForwardFling ? Math.abs(i2) < layoutCenter ? getViewDecoratedEnd(pagerGridLayoutManager, view2) + Math.abs(i2) < layoutCenter || position2 - 1 >= 0 : position2 - 1 >= 0 : i2 >= layoutCenter || getViewDecoratedStart(pagerGridLayoutManager, view3) - i2 <= layoutCenter || position2 - 1 >= 0 : !isForwardFling ? Math.abs(i2) < layoutCenter ? getViewDecoratedStart(pagerGridLayoutManager, view2) - Math.abs(i2) > layoutCenter || position2 - 1 >= 0 : position2 - 1 >= 0 : i2 >= layoutCenter || getViewDecoratedEnd(pagerGridLayoutManager, view3) + i2 >= layoutCenter || position2 - 1 >= 0) {
                i = position2;
            }
        } else if (size == 3) {
            i = pagerGridLayoutManager.getPosition(this.snapList.get(1));
        } else if (PagerGridLayoutManager.DEBUG) {
            Log.w(TAG, "findTargetSnapPosition-snapList.size: " + this.snapList.size());
        }
        if (PagerGridLayoutManager.DEBUG) {
            Log.d(TAG, "findTargetSnapPosition->forwardDirection:" + isForwardFling + ",targetPosition:" + i + ",velocityX: " + velocityX + ",velocityY: " + velocityY + ",scrollDistance:" + i2 + ",snapList:" + this.snapList.size());
        }
        this.snapList.clear();
        return i;
    }

    @Override // androidx.recyclerview.widget.SnapHelper
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        View view = null;
        if (layoutManager instanceof PagerGridLayoutManager) {
            PagerGridLayoutManager pagerGridLayoutManager = (PagerGridLayoutManager) layoutManager;
            reacquireSnapList(pagerGridLayoutManager);
            int size = this.snapList.size();
            if (size == 1) {
                view = this.snapList.get(0);
            } else if (size == 2) {
                int layoutCenter = getLayoutCenter(pagerGridLayoutManager);
                View view2 = this.snapList.get(0);
                View view3 = this.snapList.get(1);
                pagerGridLayoutManager.getDecoratedBoundsWithMargins(view3, new Rect());
                if (pagerGridLayoutManager.shouldHorizontallyReverseLayout()) {
                    if (getViewDecoratedEnd(pagerGridLayoutManager, view3) > layoutCenter) {
                        view2 = view3;
                    }
                } else if (getViewDecoratedStart(pagerGridLayoutManager, view3) <= layoutCenter) {
                    view = view3;
                }
                view = view2;
            } else if (size == 3) {
                view = this.snapList.get(1);
            } else if (PagerGridLayoutManager.DEBUG) {
                Log.w(TAG, "findSnapView wrong -> snapList.size: " + this.snapList.size());
            }
            if (PagerGridLayoutManager.DEBUG) {
                StringBuilder sb = new StringBuilder("findSnapView: position:");
                sb.append(view != null ? layoutManager.getPosition(view) : -1);
                sb.append(", snapList.size:");
                sb.append(this.snapList.size());
                Log.i(TAG, sb.toString());
            }
            this.snapList.clear();
        }
        return view;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0062 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006b  */
    @Override // androidx.recyclerview.widget.SnapHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int[] calculateDistanceToFinalSnap(androidx.recyclerview.widget.RecyclerView.LayoutManager r6, android.view.View r7) {
        /*
            r5 = this;
            r0 = 2
            int[] r0 = new int[r0]
            int r1 = r6.getPosition(r7)
            boolean r2 = r6 instanceof com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager
            if (r2 == 0) goto L8a
            r2 = r6
            com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager r2 = (com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager) r2
            int r3 = getLayoutCenter(r2)
            android.graphics.Rect r4 = new android.graphics.Rect
            r4.<init>()
            r6.getDecoratedBoundsWithMargins(r7, r4)
            boolean r6 = r2.shouldHorizontallyReverseLayout()
            if (r6 == 0) goto L3d
            int r6 = getViewDecoratedEnd(r2, r7)
            if (r6 < r3) goto L33
            android.graphics.Rect r6 = r2.getStartSnapRect()
            int r7 = com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridSmoothScroller.calculateDx(r2, r6, r4)
            int r6 = com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridSmoothScroller.calculateDy(r2, r6, r4)
            goto L5a
        L33:
            int r6 = calculateDxToNextPager(r2, r4)
            int r7 = -r6
            int r6 = calculateDyToNextPager(r2, r4)
            goto L59
        L3d:
            int r6 = getViewDecoratedStart(r2, r7)
            if (r6 > r3) goto L50
            android.graphics.Rect r6 = r2.getStartSnapRect()
            int r7 = com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridSmoothScroller.calculateDx(r2, r6, r4)
            int r6 = com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridSmoothScroller.calculateDy(r2, r6, r4)
            goto L5a
        L50:
            int r6 = calculateDxToNextPager(r2, r4)
            int r7 = -r6
            int r6 = calculateDyToNextPager(r2, r4)
        L59:
            int r6 = -r6
        L5a:
            r3 = 0
            r0[r3] = r7
            r3 = 1
            r0[r3] = r6
            if (r7 != 0) goto L67
            if (r6 != 0) goto L67
            r2.calculateCurrentPagerIndexByPosition(r1)
        L67:
            boolean r6 = com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.DEBUG
            if (r6 == 0) goto L8a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "calculateDistanceToFinalSnap-targetView: "
            r6.<init>(r7)
            r6.append(r1)
            java.lang.String r7 = ",snapDistance: "
            r6.append(r7)
            java.lang.String r7 = java.util.Arrays.toString(r0)
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = "PagerGridSnapHelper"
            android.util.Log.i(r7, r6)
        L8a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridSnapHelper.calculateDistanceToFinalSnap(androidx.recyclerview.widget.RecyclerView$LayoutManager, android.view.View):int[]");
    }

    private boolean isForwardFling(PagerGridLayoutManager layoutManager, int velocityX, int velocityY) {
        return layoutManager.canScrollHorizontally() ? layoutManager.getShouldReverseLayout() ? velocityX < 0 : velocityX > 0 : velocityY > 0;
    }

    private void reacquireSnapList(PagerGridLayoutManager manager) {
        if (!this.snapList.isEmpty()) {
            this.snapList.clear();
        }
        int childCount = manager.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = manager.getChildAt(i);
            if (childAt != null && manager.getPosition(childAt) % manager.getOnePageSize() == 0) {
                this.snapList.add(childAt);
            }
        }
    }

    static int calculateDxToNextPager(PagerGridLayoutManager manager, Rect targetRect) {
        if (manager.canScrollHorizontally()) {
            return getLayoutEndAfterPadding(manager) - targetRect.left;
        }
        return 0;
    }

    static int calculateDyToNextPager(PagerGridLayoutManager manager, Rect targetRect) {
        if (manager.canScrollVertically()) {
            return getLayoutEndAfterPadding(manager) - targetRect.top;
        }
        return 0;
    }

    static int distanceToCenter(RecyclerView.LayoutManager layoutManager, View targetView) {
        return getChildViewCenter(layoutManager, targetView) - getLayoutCenter(layoutManager);
    }

    static int getLayoutCenter(RecyclerView.LayoutManager layoutManager) {
        return getLayoutStartAfterPadding(layoutManager) + (getLayoutTotalSpace(layoutManager) / 2);
    }

    static int getLayoutStartAfterPadding(RecyclerView.LayoutManager layoutManager) {
        return layoutManager.canScrollHorizontally() ? layoutManager.getPaddingStart() : layoutManager.getPaddingTop();
    }

    static int getLayoutEndAfterPadding(RecyclerView.LayoutManager layoutManager) {
        int height;
        int paddingBottom;
        if (layoutManager.canScrollHorizontally()) {
            height = layoutManager.getWidth();
            paddingBottom = layoutManager.getPaddingEnd();
        } else {
            height = layoutManager.getHeight();
            paddingBottom = layoutManager.getPaddingBottom();
        }
        return height - paddingBottom;
    }

    static int getLayoutTotalSpace(RecyclerView.LayoutManager layoutManager) {
        int height;
        int paddingBottom;
        if (layoutManager.canScrollHorizontally()) {
            height = layoutManager.getWidth() - layoutManager.getPaddingStart();
            paddingBottom = layoutManager.getPaddingEnd();
        } else {
            height = layoutManager.getHeight() - layoutManager.getPaddingTop();
            paddingBottom = layoutManager.getPaddingBottom();
        }
        return height - paddingBottom;
    }

    static int getChildViewCenter(RecyclerView.LayoutManager layoutManager, View targetView) {
        return getViewDecoratedStart(layoutManager, targetView) + (getViewDecoratedMeasurement(layoutManager, targetView) / 2);
    }

    static int getViewDecoratedStart(RecyclerView.LayoutManager layoutManager, View view) {
        int decoratedTop;
        int i;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (layoutManager.canScrollHorizontally()) {
            decoratedTop = layoutManager.getDecoratedLeft(view);
            i = layoutParams.leftMargin;
        } else {
            decoratedTop = layoutManager.getDecoratedTop(view);
            i = layoutParams.topMargin;
        }
        return decoratedTop - i;
    }

    static int getViewDecoratedEnd(RecyclerView.LayoutManager layoutManager, View view) {
        int decoratedBottom;
        int i;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (layoutManager.canScrollHorizontally()) {
            decoratedBottom = layoutManager.getDecoratedRight(view);
            i = layoutParams.rightMargin;
        } else {
            decoratedBottom = layoutManager.getDecoratedBottom(view);
            i = layoutParams.bottomMargin;
        }
        return decoratedBottom - i;
    }

    static int getViewDecoratedMeasurement(RecyclerView.LayoutManager layoutManager, View view) {
        int decoratedMeasuredHeight;
        int i;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (layoutManager.canScrollHorizontally()) {
            decoratedMeasuredHeight = layoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin;
            i = layoutParams.rightMargin;
        } else {
            decoratedMeasuredHeight = layoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin;
            i = layoutParams.bottomMargin;
        }
        return decoratedMeasuredHeight + i;
    }
}