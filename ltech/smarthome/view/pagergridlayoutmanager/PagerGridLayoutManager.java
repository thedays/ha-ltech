package com.ltech.smarthome.view.pagergridlayoutmanager;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PagerGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    static boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int NO_ITEM = -1;
    public static final int NO_PAGER_COUNT = 0;
    private static final String TAG = "PagerGridLayoutManager";
    public static final int VERTICAL = 1;
    private int diffHeight;
    private int diffWidth;
    private boolean enable;
    private boolean isHandlingSlidingConflictsEnabled;
    private int mColumns;
    private int mCurrentPagerIndex;
    private final Rect mEndSnapRect;
    private int mItemHeight;
    private int mItemHeightUsed;
    private int mItemWidth;
    private int mItemWidthUsed;
    protected final LayoutChunkResult mLayoutChunkResult;
    protected final LayoutState mLayoutState;
    private int mMaxScrollOnFlingDuration;
    private float mMillisecondPreInch;
    private int mOnePageSize;
    private int mOrientation;
    private PagerChangedListener mPagerChangedListener;
    private int mPagerCount;
    private PagerGridSnapHelper mPagerGridSnapHelper;
    private RecyclerView mRecyclerView;
    private boolean mReverseLayout;
    private int mRows;
    protected boolean mShouldReverseLayout;
    private final Rect mStartSnapRect;
    private final RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener;
    private RecyclerView.OnItemTouchListener onItemTouchListener;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public interface PagerChangedListener {
        void onPagerCountChanged(int pagerCount);

        void onPagerIndexSelected(int prePagerIndex, int currentPagerIndex);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int state) {
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public PagerGridLayoutManager(int rows, int columns) {
        this(rows, columns, 0);
    }

    public PagerGridLayoutManager(int rows, int columns, boolean reverseLayout) {
        this(rows, columns, 0, reverseLayout);
    }

    public PagerGridLayoutManager(int rows, int columns, int orientation) {
        this(rows, columns, orientation, false);
    }

    public PagerGridLayoutManager(int rows, int columns, int orientation, boolean reverseLayout) {
        this.enable = true;
        this.mOrientation = 0;
        this.mPagerCount = 0;
        this.mCurrentPagerIndex = -1;
        this.mStartSnapRect = new Rect();
        this.mEndSnapRect = new Rect();
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.diffWidth = 0;
        this.diffHeight = 0;
        this.isHandlingSlidingConflictsEnabled = true;
        this.mMillisecondPreInch = 100.0f;
        this.mMaxScrollOnFlingDuration = 500;
        this.onChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener(this) { // from class: com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
            public void onChildViewDetachedFromWindow(View view) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
            public void onChildViewAttachedToWindow(View view) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (layoutParams.width != -1 || layoutParams.height != -1) {
                    throw new IllegalStateException("Item layout  must fill the whole PagerGridLayoutManager (use match_parent)");
                }
            }
        };
        this.mLayoutState = createLayoutState();
        this.mLayoutChunkResult = createLayoutChunkResult();
        setRows(rows);
        setColumns(columns);
        setOrientation(orientation);
        setReverseLayout(reverseLayout);
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context c2, AttributeSet attrs) {
        return new LayoutParams(c2, attrs);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof RecyclerView.LayoutParams) {
            return new LayoutParams((RecyclerView.LayoutParams) lp);
        }
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) lp);
        }
        return new LayoutParams(lp);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return lp instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        if (DEBUG) {
            Log.d(TAG, "onAttachedToWindow: ");
        }
        view.setHasFixedSize(true);
        if (isInScrollingContainer(view)) {
            if (this.isHandlingSlidingConflictsEnabled) {
                PagerGridItemTouchListener pagerGridItemTouchListener = new PagerGridItemTouchListener(this, view);
                this.onItemTouchListener = pagerGridItemTouchListener;
                view.addOnItemTouchListener(pagerGridItemTouchListener);
            } else if (DEBUG) {
                Log.w(TAG, "isHandlingSlidingConflictsEnabled: false.");
            }
        }
        view.addOnChildAttachStateChangeListener(this.onChildAttachStateChangeListener);
        PagerGridSnapHelper pagerGridSnapHelper = new PagerGridSnapHelper();
        this.mPagerGridSnapHelper = pagerGridSnapHelper;
        pagerGridSnapHelper.attachToRecyclerView(view);
        this.mRecyclerView = view;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        View.MeasureSpec.getSize(widthSpec);
        View.MeasureSpec.getMode(heightSpec);
        int size = View.MeasureSpec.getSize(widthSpec);
        int size2 = View.MeasureSpec.getSize(heightSpec);
        int paddingStart = (size - getPaddingStart()) - getPaddingEnd();
        int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
        int i = this.mColumns;
        int i2 = i > 0 ? paddingStart / i : 0;
        this.mItemWidth = i2;
        int i3 = this.mRows;
        int i4 = i3 > 0 ? paddingTop / i3 : 0;
        this.mItemHeight = i4;
        int i5 = paddingStart - (i * i2);
        this.diffWidth = i5;
        int i6 = paddingTop - (i3 * i4);
        this.diffHeight = i6;
        this.mItemWidthUsed = (paddingStart - i5) - i2;
        this.mItemHeightUsed = (paddingTop - i6) - i4;
        if (DEBUG) {
            Log.d(TAG, "onMeasure-originalWidthSize: " + size + ",originalHeightSize: " + size2 + ",diffWidth: " + this.diffWidth + ",diffHeight: " + this.diffHeight + ",mItemWidth: " + this.mItemWidth + ",mItemHeight: " + this.mItemHeight + ",mStartSnapRect:" + this.mStartSnapRect + ",mEndSnapRect:" + this.mEndSnapRect);
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01cb  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onLayoutChildren(androidx.recyclerview.widget.RecyclerView.Recycler r11, androidx.recyclerview.widget.RecyclerView.State r12) {
        /*
            Method dump skipped, instructions count: 735
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.onLayoutChildren(androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public View findViewByPosition(int position) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position2 = position - getPosition(getChildAt(0));
        if (position2 >= 0 && position2 < childCount) {
            View childAt = getChildAt(position2);
            if (getPosition(childAt) == position) {
                return childAt;
            }
        }
        return super.findViewByPosition(position);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return computeScrollExtent(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public Parcelable onSaveInstanceState() {
        if (DEBUG) {
            Log.d(TAG, "onSaveInstanceState: ");
        }
        SavedState savedState = new SavedState();
        savedState.mOrientation = this.mOrientation;
        savedState.mRows = this.mRows;
        savedState.mColumns = this.mColumns;
        savedState.mCurrentPagerIndex = this.mCurrentPagerIndex;
        savedState.mReverseLayout = this.mReverseLayout;
        return savedState;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            this.mOrientation = savedState.mOrientation;
            this.mRows = savedState.mRows;
            this.mColumns = savedState.mColumns;
            calculateOnePageSize();
            setCurrentPagerIndex(savedState.mCurrentPagerIndex);
            this.mReverseLayout = savedState.mReverseLayout;
            requestLayout();
            if (DEBUG) {
                Log.d(TAG, "onRestoreInstanceState: loaded saved state");
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int position) {
        assertNotInLayoutOrScroll(null);
        scrollToPagerIndex(getPagerIndexByPosition(position));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        assertNotInLayoutOrScroll(null);
        smoothScrollToPagerIndex(getPagerIndexByPosition(position));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (isEnable() && this.mOrientation != 1) {
            return scrollBy(dx, recycler, state);
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (isEnable() && this.mOrientation != 0) {
            return scrollBy(dy, recycler, state);
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getWidth() {
        return super.getWidth() - getDiffWidth();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int getHeight() {
        return super.getHeight() - getDiffHeight();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        if (DEBUG) {
            Log.w(TAG, "onDetachedFromWindow: ");
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            RecyclerView.OnItemTouchListener onItemTouchListener = this.onItemTouchListener;
            if (onItemTouchListener != null) {
                recyclerView.removeOnItemTouchListener(onItemTouchListener);
            }
            this.mRecyclerView.removeOnChildAttachStateChangeListener(this.onChildAttachStateChangeListener);
            this.mRecyclerView = null;
        }
        this.mPagerGridSnapHelper.attachToRecyclerView(null);
        this.mPagerGridSnapHelper = null;
    }

    public void setPagerChangedListener(PagerChangedListener listener) {
        this.mPagerChangedListener = listener;
    }

    public final void setHandlingSlidingConflictsEnabled(boolean enabled) {
        this.isHandlingSlidingConflictsEnabled = enabled;
    }

    public final boolean isHandlingSlidingConflictsEnabled() {
        return this.isHandlingSlidingConflictsEnabled;
    }

    public final void setMillisecondPreInch(float millisecondPreInch) {
        this.mMillisecondPreInch = Math.max(1.0f, millisecondPreInch);
    }

    public final float getMillisecondPreInch() {
        return this.mMillisecondPreInch;
    }

    public final void setMaxScrollOnFlingDuration(int maxScrollOnFlingDuration) {
        this.mMaxScrollOnFlingDuration = Math.max(1, maxScrollOnFlingDuration);
    }

    public final int getMaxScrollOnFlingDuration() {
        return this.mMaxScrollOnFlingDuration;
    }

    public final int getItemWidth() {
        return this.mItemWidth;
    }

    public final int getItemHeight() {
        return this.mItemHeight;
    }

    private void calculateOnePageSize() {
        this.mOnePageSize = this.mRows * this.mColumns;
    }

    public final int getOnePageSize() {
        return this.mOnePageSize;
    }

    public void setColumns(int columns) {
        assertNotInLayoutOrScroll(null);
        if (this.mColumns == columns) {
            return;
        }
        this.mColumns = Math.max(columns, 1);
        this.mPagerCount = 0;
        this.mCurrentPagerIndex = -1;
        calculateOnePageSize();
        requestLayout();
    }

    public final int getColumns() {
        return this.mColumns;
    }

    public void setRows(int rows) {
        assertNotInLayoutOrScroll(null);
        if (this.mRows == rows) {
            return;
        }
        this.mRows = Math.max(rows, 1);
        this.mPagerCount = 0;
        this.mCurrentPagerIndex = -1;
        calculateOnePageSize();
        requestLayout();
    }

    public final int getRows() {
        return this.mRows;
    }

    public void setOrientation(int orientation) {
        assertNotInLayoutOrScroll(null);
        if (orientation != 0 && orientation != 1) {
            throw new IllegalArgumentException("invalid orientation:" + orientation);
        }
        if (orientation != this.mOrientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setReverseLayout(boolean reverseLayout) {
        assertNotInLayoutOrScroll(null);
        if (reverseLayout == this.mReverseLayout) {
            return;
        }
        this.mReverseLayout = reverseLayout;
        requestLayout();
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public final int getPagerIndexByPosition(int position) {
        return position / this.mOnePageSize;
    }

    public final int getMaxPagerIndex() {
        return getPagerIndexByPosition(getItemCount() - 1);
    }

    public void scrollToPagerIndex(int pagerIndex) {
        assertNotInLayoutOrScroll(null);
        int min = Math.min(Math.max(pagerIndex, 0), getMaxPagerIndex());
        if (min == this.mCurrentPagerIndex) {
            return;
        }
        setCurrentPagerIndex(min);
        requestLayout();
    }

    public void scrollToPrePager() {
        assertNotInLayoutOrScroll(null);
        scrollToPagerIndex(this.mCurrentPagerIndex - 1);
    }

    public void scrollToNextPager() {
        assertNotInLayoutOrScroll(null);
        scrollToPagerIndex(this.mCurrentPagerIndex + 1);
    }

    public void smoothScrollToPagerIndex(int pagerIndex) {
        assertNotInLayoutOrScroll(null);
        int min = Math.min(Math.max(pagerIndex, 0), getMaxPagerIndex());
        int i = this.mCurrentPagerIndex;
        if (min == i) {
            return;
        }
        boolean z = min > i;
        if (Math.abs(min - i) > 3) {
            scrollToPagerIndex(min > i ? min - 3 : min + 3);
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView != null) {
                recyclerView.post(new SmoothScrollToPosition(getPositionByPagerIndex(min, z), this, this.mRecyclerView));
                return;
            }
            return;
        }
        PagerGridSmoothScroller pagerGridSmoothScroller = new PagerGridSmoothScroller(this.mRecyclerView, this);
        pagerGridSmoothScroller.setTargetPosition(getPositionByPagerIndex(min, z));
        startSmoothScroll(pagerGridSmoothScroller);
    }

    public void smoothScrollToPrePager() {
        assertNotInLayoutOrScroll(null);
        smoothScrollToPagerIndex(this.mCurrentPagerIndex - 1);
    }

    public void smoothScrollToNextPager() {
        assertNotInLayoutOrScroll(null);
        smoothScrollToPagerIndex(this.mCurrentPagerIndex + 1);
    }

    protected LayoutState createLayoutState() {
        return new LayoutState();
    }

    protected LayoutChunkResult createLayoutChunkResult() {
        return new LayoutChunkResult();
    }

    protected boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    private void setPagerCount(int pagerCount) {
        if (this.mPagerCount == pagerCount) {
            return;
        }
        this.mPagerCount = pagerCount;
        PagerChangedListener pagerChangedListener = this.mPagerChangedListener;
        if (pagerChangedListener != null) {
            pagerChangedListener.onPagerCountChanged(pagerCount);
        }
    }

    public final int getPagerCount() {
        return Math.max(this.mPagerCount, 0);
    }

    private void setCurrentPagerIndex(int pagerIndex) {
        int i = this.mCurrentPagerIndex;
        if (i == pagerIndex) {
            return;
        }
        this.mCurrentPagerIndex = pagerIndex;
        PagerChangedListener pagerChangedListener = this.mPagerChangedListener;
        if (pagerChangedListener != null) {
            pagerChangedListener.onPagerIndexSelected(i, pagerIndex);
        }
    }

    public final int getCurrentPagerIndex() {
        return this.mCurrentPagerIndex;
    }

    private boolean isInScrollingContainer(View view) {
        for (ViewParent parent = view.getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
            if (((ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
        }
        return false;
    }

    private int getPositionByPagerIndex(int pagerIndex, boolean isLayoutToEnd) {
        if (isLayoutToEnd) {
            return pagerIndex * this.mOnePageSize;
        }
        int i = this.mOnePageSize;
        return ((pagerIndex * i) + i) - 1;
    }

    public final int getDiffWidth() {
        return Math.max(this.diffWidth, 0);
    }

    public final int getDiffHeight() {
        return Math.max(this.diffHeight, 0);
    }

    private int getRealWidth() {
        return (getWidth() - getPaddingStart()) - getPaddingEnd();
    }

    private int getRealHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        boolean isNeedMoveToPreSpan;
        LayoutState layoutState = this.mLayoutState;
        int i = layoutState.mAvailable;
        LayoutChunkResult layoutChunkResult = this.mLayoutChunkResult;
        for (int i2 = layoutState.mAvailable; i2 > 0 && layoutState.hasMore(state); i2 -= layoutChunkResult.mConsumed) {
            if (this.mShouldReverseLayout) {
                reverseLayoutChunk(recycler, state, layoutState, layoutChunkResult);
            } else {
                layoutChunk(recycler, state, layoutState, layoutChunkResult);
            }
            layoutState.mAvailable -= layoutChunkResult.mConsumed;
        }
        boolean z = layoutState.mLayoutDirection == 1;
        while (layoutState.hasMore(state)) {
            if (z) {
                isNeedMoveToPreSpan = isNeedMoveToNextSpan(layoutState.mCurrentPosition);
            } else {
                isNeedMoveToPreSpan = isNeedMoveToPreSpan(layoutState.mCurrentPosition);
            }
            if (isNeedMoveToPreSpan) {
                break;
            }
            if (this.mShouldReverseLayout) {
                reverseLayoutChunk(recycler, state, layoutState, layoutChunkResult);
            } else {
                layoutChunk(recycler, state, layoutState, layoutChunkResult);
            }
        }
        recycleViews(recycler);
        return i - layoutState.mAvailable;
    }

    private void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        LayoutState layoutState2;
        int prePosition;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int calculateClipOffset;
        int i6;
        boolean z = layoutState.mLayoutDirection == 1;
        int i7 = layoutState.mCurrentPosition;
        View next = layoutState.next(recycler);
        if (z) {
            addView(next);
        } else {
            addView(next, 0);
        }
        if (z) {
            layoutState2 = layoutState;
            prePosition = layoutState2.getNextPosition(i7, this.mOrientation, this.mRows, this.mColumns, state);
        } else {
            layoutState2 = layoutState;
            prePosition = layoutState2.getPrePosition(i7, this.mOrientation, this.mRows, this.mColumns, state);
        }
        layoutState2.mCurrentPosition = prePosition;
        measureChildWithMargins(next, this.mItemWidthUsed, this.mItemHeightUsed);
        boolean isNeedMoveToNextSpan = z ? isNeedMoveToNextSpan(i7) : isNeedMoveToPreSpan(i7);
        if (isNeedMoveToNextSpan) {
            i = this.mOrientation == 0 ? this.mItemWidth : this.mItemHeight;
        } else {
            i = 0;
        }
        layoutChunkResult.mConsumed = i;
        Rect rect = layoutState2.mOffsetRect;
        if (this.mOrientation != 0) {
            if (z) {
                if (isNeedMoveToNextSpan) {
                    i2 = getPaddingStart();
                    i3 = rect.bottom + calculateClipOffset(true, i7);
                } else {
                    i2 = rect.left + this.mItemWidth;
                    i3 = rect.top;
                }
                i4 = this.mItemWidth + i2;
                i5 = this.mItemHeight;
            } else if (isNeedMoveToNextSpan) {
                i4 = getWidth() - getPaddingEnd();
                i2 = i4 - this.mItemWidth;
                calculateClipOffset = rect.top - calculateClipOffset(false, i7);
                i3 = calculateClipOffset - this.mItemHeight;
            } else {
                i2 = rect.left - this.mItemWidth;
                i3 = rect.top;
                i4 = this.mItemWidth + i2;
                i5 = this.mItemHeight;
            }
            calculateClipOffset = i5 + i3;
        } else if (z) {
            if (isNeedMoveToNextSpan) {
                i2 = rect.left + this.mItemWidth + calculateClipOffset(true, i7);
                i3 = getPaddingTop();
            } else {
                i2 = rect.left;
                i3 = rect.bottom;
            }
            i4 = this.mItemWidth + i2;
            i5 = this.mItemHeight;
            calculateClipOffset = i5 + i3;
        } else {
            if (isNeedMoveToNextSpan) {
                i2 = (rect.left - this.mItemWidth) - calculateClipOffset(false, i7);
                i6 = getHeight() - getPaddingBottom();
            } else {
                i2 = rect.left;
                i6 = rect.top;
            }
            calculateClipOffset = i6;
            i3 = calculateClipOffset - this.mItemHeight;
            i4 = this.mItemWidth + i2;
        }
        int i8 = i2;
        int i9 = i3;
        int i10 = i4;
        int i11 = calculateClipOffset;
        layoutState2.setOffsetRect(i8, i9, i10, i11);
        layoutDecoratedWithMargins(next, i8, i9, i10, i11);
    }

    private void reverseLayoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        LayoutState layoutState2;
        int prePosition;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int calculateClipOffset;
        int i6;
        int i7;
        boolean z = layoutState.mLayoutDirection == 1;
        int i8 = layoutState.mCurrentPosition;
        View next = layoutState.next(recycler);
        if (z) {
            addView(next);
        } else {
            addView(next, 0);
        }
        if (z) {
            layoutState2 = layoutState;
            prePosition = layoutState2.getNextPosition(i8, this.mOrientation, this.mRows, this.mColumns, state);
        } else {
            layoutState2 = layoutState;
            prePosition = layoutState2.getPrePosition(i8, this.mOrientation, this.mRows, this.mColumns, state);
        }
        layoutState2.mCurrentPosition = prePosition;
        measureChildWithMargins(next, this.mItemWidthUsed, this.mItemHeightUsed);
        boolean isNeedMoveToNextSpan = z ? isNeedMoveToNextSpan(i8) : isNeedMoveToPreSpan(i8);
        if (isNeedMoveToNextSpan) {
            i = this.mOrientation == 0 ? this.mItemWidth : this.mItemHeight;
        } else {
            i = 0;
        }
        layoutChunkResult.mConsumed = i;
        Rect rect = layoutState2.mOffsetRect;
        if (this.mOrientation != 0) {
            if (z) {
                if (isNeedMoveToNextSpan) {
                    i6 = getWidth() - getPaddingEnd();
                    i4 = rect.bottom + calculateClipOffset(true, i8);
                } else {
                    i6 = rect.left;
                    i4 = rect.top;
                }
                i3 = i6;
                i2 = i3 - this.mItemWidth;
                i5 = this.mItemHeight;
            } else if (isNeedMoveToNextSpan) {
                i2 = getPaddingStart();
                i3 = this.mItemWidth + i2;
                calculateClipOffset = rect.top - calculateClipOffset(false, i8);
                i4 = calculateClipOffset - this.mItemHeight;
            } else {
                i2 = rect.right;
                i3 = this.mItemWidth + i2;
                i4 = rect.top;
                i5 = this.mItemHeight;
            }
            calculateClipOffset = i5 + i4;
        } else if (z) {
            if (isNeedMoveToNextSpan) {
                i2 = (rect.left - this.mItemWidth) - calculateClipOffset(true, i8);
                i4 = getPaddingTop();
            } else {
                i2 = rect.left;
                i4 = rect.bottom;
            }
            i3 = this.mItemWidth + i2;
            i5 = this.mItemHeight;
            calculateClipOffset = i5 + i4;
        } else {
            if (isNeedMoveToNextSpan) {
                i2 = rect.left + this.mItemWidth + calculateClipOffset(false, i8);
                i7 = getHeight() - getPaddingBottom();
            } else {
                i2 = rect.left;
                i7 = rect.top;
            }
            calculateClipOffset = i7;
            i4 = calculateClipOffset - this.mItemHeight;
            i3 = this.mItemWidth + i2;
        }
        int i9 = i2;
        int i10 = i4;
        int i11 = i3;
        int i12 = calculateClipOffset;
        layoutState2.setOffsetRect(i9, i10, i11, i12);
        layoutDecoratedWithMargins(next, i9, i10, i11, i12);
    }

    private int scrollBy(int delta, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || delta == 0 || this.mPagerCount == 1) {
            return 0;
        }
        this.mLayoutState.mRecycle = true;
        int i = -1;
        if (!shouldHorizontallyReverseLayout() ? delta > 0 : delta <= 0) {
            i = 1;
        }
        this.mLayoutState.mLayoutDirection = i;
        boolean z = i == 1;
        int abs = Math.abs(delta);
        if (DEBUG) {
            Log.i(TAG, "scrollBy -> before : childCount:" + getChildCount() + ",recycler.scrapList.size:" + recycler.getScrapList().size() + ",delta:" + delta);
        }
        updateLayoutState(z, abs, true, state);
        int fill = this.mLayoutState.mScrollingOffset + fill(recycler, state);
        if (z) {
            fill += this.mLayoutState.replenishDelta;
        }
        if (fill < 0) {
            return 0;
        }
        int i2 = abs > fill ? i * fill : delta;
        offsetChildren(-i2);
        this.mLayoutState.mLastScrollDelta = i2;
        recycleViews(recycler);
        if (DEBUG) {
            Log.i(TAG, "scrollBy -> end : childCount:" + getChildCount() + ",recycler.scrapList.size:" + recycler.getScrapList().size() + ",delta:" + delta + ",scrolled:" + i2);
        }
        return i2;
    }

    private void updateLayoutState(boolean layoutToEnd, int requiredSpace, boolean canUseExistingSpace, RecyclerView.State state) {
        View childClosestToStart;
        int i;
        int startAfterPadding;
        int decoratedEnd;
        int endAfterPadding;
        int i2;
        if (layoutToEnd) {
            childClosestToStart = getChildClosestToEnd();
            if (shouldHorizontallyReverseLayout()) {
                i = -getDecoratedStart(childClosestToStart);
                startAfterPadding = getStartAfterPadding();
                i2 = i + startAfterPadding;
            } else {
                decoratedEnd = getDecoratedEnd(childClosestToStart);
                endAfterPadding = getEndAfterPadding();
                i2 = decoratedEnd - endAfterPadding;
            }
        } else {
            childClosestToStart = getChildClosestToStart();
            if (shouldHorizontallyReverseLayout()) {
                decoratedEnd = getDecoratedEnd(childClosestToStart);
                endAfterPadding = getEndAfterPadding();
                i2 = decoratedEnd - endAfterPadding;
            } else {
                i = -getDecoratedStart(childClosestToStart);
                startAfterPadding = getStartAfterPadding();
                i2 = i + startAfterPadding;
            }
        }
        getDecoratedBoundsWithMargins(childClosestToStart, this.mLayoutState.mOffsetRect);
        LayoutState layoutState = this.mLayoutState;
        layoutState.mCurrentPosition = layoutToEnd ? layoutState.getNextPosition(getPosition(childClosestToStart), this.mOrientation, this.mRows, this.mColumns, state) : layoutState.getPrePosition(getPosition(childClosestToStart), this.mOrientation, this.mRows, this.mColumns, state);
        this.mLayoutState.mAvailable = requiredSpace;
        if (canUseExistingSpace) {
            this.mLayoutState.mAvailable -= i2;
        }
        this.mLayoutState.mScrollingOffset = i2;
    }

    private View getChildClosestToEnd() {
        return getChildAt(getChildCount() - 1);
    }

    private View getChildClosestToStart() {
        return getChildAt(0);
    }

    private void recycleViews(RecyclerView.Recycler recycler) {
        if (this.mLayoutState.mRecycle) {
            if (shouldHorizontallyReverseLayout()) {
                if (this.mLayoutState.mLayoutDirection == -1) {
                    recycleViewsFromStart(recycler);
                    return;
                } else {
                    recycleViewsFromEnd(recycler);
                    return;
                }
            }
            if (this.mLayoutState.mLayoutDirection == -1) {
                recycleViewsFromEnd(recycler);
            } else {
                recycleViewsFromStart(recycler);
            }
        }
    }

    private void recycleViewsFromStart(RecyclerView.Recycler recycler) {
        int startAfterPadding = getClipToPadding() ? getStartAfterPadding() : 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt != null && getDecoratedEnd(childAt) < startAfterPadding) {
                if (DEBUG) {
                    Log.w(TAG, "recycleViewsFromStart-removeAndRecycleViewAt: " + childCount + ", position: " + getPosition(childAt));
                }
                removeAndRecycleViewAt(childCount, recycler);
            }
        }
    }

    private void recycleViewsFromEnd(RecyclerView.Recycler recycler) {
        int width;
        if (getClipToPadding()) {
            width = getEndAfterPadding();
        } else {
            width = this.mOrientation == 0 ? getWidth() : getHeight();
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt != null && getDecoratedStart(childAt) > width) {
                if (DEBUG) {
                    Log.w(TAG, "recycleViewsFromEnd-removeAndRecycleViewAt: " + childCount + ", position: " + getPosition(childAt));
                }
                removeAndRecycleViewAt(childCount, recycler);
            }
        }
    }

    private int getDecoratedEnd(View child) {
        int decoratedBottom;
        int i;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        if (this.mOrientation == 0) {
            decoratedBottom = getDecoratedRight(child);
            i = layoutParams.rightMargin;
        } else {
            decoratedBottom = getDecoratedBottom(child);
            i = layoutParams.bottomMargin;
        }
        return decoratedBottom + i;
    }

    private int getDecoratedStart(View child) {
        int decoratedTop;
        int i;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        if (this.mOrientation == 0) {
            decoratedTop = getDecoratedLeft(child);
            i = layoutParams.leftMargin;
        } else {
            decoratedTop = getDecoratedTop(child);
            i = layoutParams.topMargin;
        }
        return decoratedTop - i;
    }

    private int getEndAfterPadding() {
        int height;
        int paddingBottom;
        if (this.mOrientation == 0) {
            height = getWidth();
            paddingBottom = getPaddingEnd();
        } else {
            height = getHeight();
            paddingBottom = getPaddingBottom();
        }
        return height - paddingBottom;
    }

    private int getStartAfterPadding() {
        return this.mOrientation == 0 ? getPaddingStart() : getPaddingTop();
    }

    private int getClipToPaddingSize() {
        int paddingTop;
        int paddingBottom;
        if (this.mOrientation == 0) {
            paddingTop = getPaddingStart();
            paddingBottom = getPaddingEnd();
        } else {
            paddingTop = getPaddingTop();
            paddingBottom = getPaddingBottom();
        }
        return paddingTop + paddingBottom;
    }

    private int calculateClipOffset(boolean layoutToEnd, int position) {
        if (!getClipToPadding()) {
            int i = this.mOnePageSize;
            if (position % i == (layoutToEnd ? 0 : i - 1)) {
                return getClipToPaddingSize();
            }
        }
        return 0;
    }

    private int getEnd() {
        return this.mOrientation == 0 ? getRealWidth() : getRealHeight();
    }

    private void offsetChildren(int delta) {
        if (this.mOrientation == 0) {
            offsetChildrenHorizontal(delta);
        } else {
            offsetChildrenVertical(delta);
        }
    }

    private boolean isIdle() {
        RecyclerView recyclerView = this.mRecyclerView;
        return recyclerView == null || recyclerView.getScrollState() == 0;
    }

    private boolean isNeedMoveToNextSpan(int position) {
        return this.mOrientation == 0 ? (position % this.mOnePageSize) / this.mColumns == 0 : position % this.mColumns == 0;
    }

    private boolean isNeedMoveToPreSpan(int position) {
        if (this.mOrientation == 0) {
            return (position % this.mOnePageSize) / this.mColumns == this.mRows - 1;
        }
        int i = this.mColumns;
        return position % i == i - 1;
    }

    private int computeScrollOffset(RecyclerView.State state) {
        int i;
        int i2 = 0;
        if (getChildCount() != 0 && state.getItemCount() != 0) {
            View childAt = getChildAt(0);
            if (childAt == null) {
                return 0;
            }
            int position = getPosition(childAt);
            float end = getEnd();
            int i3 = this.mOrientation;
            float f = end / (i3 == 0 ? this.mColumns : this.mRows);
            if (i3 == 0) {
                int pagerIndexByPosition = getPagerIndexByPosition(position);
                int i4 = this.mColumns;
                i = (pagerIndexByPosition * i4) + (position % i4);
            } else {
                i = position / this.mColumns;
            }
            if (shouldHorizontallyReverseLayout()) {
                i2 = (computeScrollRange(state) - computeScrollExtent(state)) - Math.round((i * f) + (getDecoratedEnd(childAt) - getEndAfterPadding()));
            } else {
                i2 = Math.round((i * f) + (getStartAfterPadding() - getDecoratedStart(childAt)));
            }
            if (DEBUG) {
                Log.i(TAG, "computeScrollOffset: " + i2);
            }
        }
        return i2;
    }

    private int computeScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0 || state.getItemCount() == 0) {
            return 0;
        }
        int end = getEnd();
        if (DEBUG) {
            Log.i(TAG, "computeScrollExtent: " + end);
        }
        return end;
    }

    private int computeScrollRange(RecyclerView.State state) {
        if (getChildCount() == 0 || state.getItemCount() == 0) {
            return 0;
        }
        int max = Math.max(this.mPagerCount, 0) * getEnd();
        if (DEBUG) {
            Log.i(TAG, "computeScrollRange: " + max);
        }
        return max;
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation == 1 || !isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout;
        } else {
            this.mShouldReverseLayout = !this.mReverseLayout;
        }
    }

    boolean getShouldReverseLayout() {
        return this.mShouldReverseLayout;
    }

    final Rect getStartSnapRect() {
        return this.mStartSnapRect;
    }

    final Rect getEndSnapRect() {
        return this.mEndSnapRect;
    }

    final void calculateCurrentPagerIndexByPosition(int position) {
        setCurrentPagerIndex(getPagerIndexByPosition(position));
    }

    final LayoutState getLayoutState() {
        return this.mLayoutState;
    }

    boolean shouldHorizontallyReverseLayout() {
        return this.mShouldReverseLayout && this.mOrientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public PointF computeScrollVectorForPosition(int targetPosition) {
        int i;
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        while (true) {
            childCount--;
            if (childCount < 0) {
                i = -1;
                break;
            }
            View childAt = getChildAt(childCount);
            if (childAt != null) {
                i = getPosition(childAt);
                if (i % this.mOnePageSize == 0) {
                    break;
                }
            }
        }
        if (i == -1) {
            return null;
        }
        float f = targetPosition < i ? -1.0f : 1.0f;
        if (shouldHorizontallyReverseLayout()) {
            f = -f;
        }
        if (DEBUG) {
            Log.w(TAG, "computeScrollVectorForPosition-firstSnapPosition: " + i + ", targetPosition:" + targetPosition + ",mOrientation :" + this.mOrientation + ", direction:" + f);
        }
        if (this.mOrientation == 0) {
            return new PointF(f, 0.0f);
        }
        return new PointF(0.0f, f);
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
        public LayoutParams(Context c2, AttributeSet attrs) {
            super(c2, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(RecyclerView.LayoutParams source) {
            super(source);
        }
    }

    private static class SmoothScrollToPosition implements Runnable {
        private final PagerGridLayoutManager mLayoutManager;
        private final int mPosition;
        private final RecyclerView mRecyclerView;

        SmoothScrollToPosition(int position, PagerGridLayoutManager layoutManager, RecyclerView recyclerView) {
            this.mPosition = position;
            this.mLayoutManager = layoutManager;
            this.mRecyclerView = recyclerView;
        }

        @Override // java.lang.Runnable
        public void run() {
            PagerGridSmoothScroller pagerGridSmoothScroller = new PagerGridSmoothScroller(this.mRecyclerView, this.mLayoutManager);
            pagerGridSmoothScroller.setTargetPosition(this.mPosition);
            this.mLayoutManager.startSmoothScroll(pagerGridSmoothScroller);
        }
    }

    protected static class LayoutState {
        protected static final int LAYOUT_END = 1;
        protected static final int LAYOUT_START = -1;
        protected static final int SCROLLING_OFFSET_NaN = Integer.MIN_VALUE;
        protected int mAvailable;
        protected int mCurrentPosition;
        protected int mLastScrollDelta;
        protected int mLayoutDirection;
        protected final Rect mOffsetRect = new Rect();
        protected boolean mRecycle;
        protected int mScrollingOffset;
        protected int replenishDelta;

        protected LayoutState() {
        }

        protected void setOffsetRect(int left, int top, int right, int bottom) {
            this.mOffsetRect.set(left, top, right, bottom);
        }

        protected View next(RecyclerView.Recycler recycler) {
            return recycler.getViewForPosition(this.mCurrentPosition);
        }

        protected boolean hasMore(RecyclerView.State state) {
            int i = this.mCurrentPosition;
            return i >= 0 && i < state.getItemCount();
        }

        protected int getNextPosition(int currentPosition, int orientation, int rows, int columns, RecyclerView.State state) {
            int i;
            int i2;
            int i3 = rows * columns;
            if (orientation == 0 && (i = currentPosition % i3) != i3 - 1) {
                int i4 = i / columns;
                return (i4 != rows + (-1) && ((i2 = currentPosition + columns) < state.getItemCount() || currentPosition % columns == columns + (-1))) ? i2 : (currentPosition - (i4 * columns)) + 1;
            }
            return currentPosition + 1;
        }

        protected int getPrePosition(int currentPosition, int orientation, int rows, int columns, RecyclerView.State state) {
            int i;
            int i2 = rows * columns;
            if (orientation == 0 && (i = currentPosition % i2) != 0) {
                return i / columns == 0 ? (currentPosition - 1) + ((rows - 1) * columns) : currentPosition - columns;
            }
            return currentPosition - 1;
        }
    }

    protected static class LayoutChunkResult {
        protected int mConsumed;
        protected boolean mFinished;
        protected boolean mFocusable;
        protected boolean mIgnoreConsumed;

        protected LayoutChunkResult() {
        }

        protected void resetInternal() {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    protected static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.ltech.smarthome.view.pagergridlayoutmanager.PagerGridLayoutManager.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        protected int mColumns;
        protected int mCurrentPagerIndex;
        protected int mOrientation;
        protected boolean mReverseLayout;
        protected int mRows;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mOrientation);
            dest.writeInt(this.mRows);
            dest.writeInt(this.mColumns);
            dest.writeInt(this.mCurrentPagerIndex);
        }

        public void readFromParcel(Parcel source) {
            this.mOrientation = source.readInt();
            this.mRows = source.readInt();
            this.mColumns = source.readInt();
            this.mCurrentPagerIndex = source.readInt();
        }

        public SavedState() {
            this.mCurrentPagerIndex = -1;
            this.mReverseLayout = false;
        }

        protected SavedState(Parcel in) {
            this.mCurrentPagerIndex = -1;
            this.mReverseLayout = false;
            this.mOrientation = in.readInt();
            this.mRows = in.readInt();
            this.mColumns = in.readInt();
            this.mCurrentPagerIndex = in.readInt();
        }

        public String toString() {
            return "SavedState{mOrientation=" + this.mOrientation + ", mRows=" + this.mRows + ", mColumns=" + this.mColumns + ", mCurrentPagerIndex=" + this.mCurrentPagerIndex + '}';
        }
    }
}