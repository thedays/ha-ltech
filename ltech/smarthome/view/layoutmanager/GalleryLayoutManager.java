package com.ltech.smarthome.view.layoutmanager;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class GalleryLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {
    public static final int HORIZONTAL = 0;
    static final int LAYOUT_END = 1;
    static final int LAYOUT_START = -1;
    private static final String TAG = "GalleryLayoutManager";
    public static final int VERTICAL = 1;
    View mCurSelectedView;
    private OrientationHelper mHorizontalHelper;
    private ItemTransformer mItemTransformer;
    private OnItemSelectedListener mOnItemSelectedListener;
    private int mOrientation;
    RecyclerView mRecyclerView;
    private State mState;
    private OrientationHelper mVerticalHelper;
    private int mFirstVisiblePosition = 0;
    private int mLastVisiblePos = 0;
    private int mInitialSelectedPosition = 0;
    private boolean canScroll = true;
    int mCurSelectedPosition = -1;
    private LinearSnapHelper mSnapHelper = new LinearSnapHelper();
    private InnerScrollListener mInnerScrollListener = new InnerScrollListener();
    private boolean mCallbackInFling = false;

    public interface ItemTransformer {
        void transformItem(GalleryLayoutManager layoutManager, View item, float fraction);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(RecyclerView recyclerView, View item, int position, boolean scrolling);
    }

    public GalleryLayoutManager(int orientation) {
        this.mOrientation = orientation;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getCurSelectedPosition() {
        return this.mCurSelectedPosition;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return new LayoutParams(-2, -1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context c2, AttributeSet attrs) {
        return new LayoutParams(c2, attrs);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
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
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            reset();
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        if (state.getItemCount() == 0 || state.didStructureChange()) {
            if (getChildCount() == 0 || state.didStructureChange()) {
                reset();
            }
            this.mInitialSelectedPosition = Math.min(Math.max(0, this.mInitialSelectedPosition), getItemCount() - 1);
            detachAndScrapAttachedViews(recycler);
            firstFillCover(recycler, state, 0);
        }
    }

    private void reset() {
        State state = this.mState;
        if (state != null) {
            state.mItemsFrames.clear();
        }
        int i = this.mCurSelectedPosition;
        if (i != -1) {
            this.mInitialSelectedPosition = i;
        }
        int min = Math.min(Math.max(0, this.mInitialSelectedPosition), getItemCount() - 1);
        this.mInitialSelectedPosition = min;
        this.mFirstVisiblePosition = min;
        this.mLastVisiblePos = min;
        this.mCurSelectedPosition = -1;
        View view = this.mCurSelectedView;
        if (view != null) {
            view.setSelected(false);
            this.mCurSelectedView = null;
        }
    }

    private void firstFillCover(RecyclerView.Recycler recycler, RecyclerView.State state, int scrollDelta) {
        if (this.mOrientation == 0) {
            firstFillWithHorizontal(recycler, state);
        } else {
            firstFillWithVertical(recycler, state);
        }
        if (this.mItemTransformer != null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                this.mItemTransformer.transformItem(this, childAt, calculateToCenterFraction(childAt, scrollDelta));
            }
        }
        this.mInnerScrollListener.onScrolled(this.mRecyclerView, 0, 0);
    }

    private void firstFillWithHorizontal(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int startAfterPadding = getOrientationHelper().getStartAfterPadding();
        int endAfterPadding = getOrientationHelper().getEndAfterPadding();
        int i = this.mInitialSelectedPosition;
        Rect rect = new Rect();
        int verticalSpace = getVerticalSpace();
        View viewForPosition = recycler.getViewForPosition(this.mInitialSelectedPosition);
        addView(viewForPosition, 0);
        measureChildWithMargins(viewForPosition, 0, 0);
        int paddingTop = (int) (getPaddingTop() + ((verticalSpace - r5) / 2.0f));
        int paddingLeft = (int) (getPaddingLeft() + ((getHorizontalSpace() - r4) / 2.0f));
        rect.set(paddingLeft, paddingTop, getDecoratedMeasuredWidth(viewForPosition) + paddingLeft, getDecoratedMeasuredHeight(viewForPosition) + paddingTop);
        layoutDecorated(viewForPosition, rect.left, rect.top, rect.right, rect.bottom);
        if (getState().mItemsFrames.get(i) == null) {
            getState().mItemsFrames.put(i, rect);
        } else {
            getState().mItemsFrames.get(i).set(rect);
        }
        this.mLastVisiblePos = i;
        this.mFirstVisiblePosition = i;
        int decoratedLeft = getDecoratedLeft(viewForPosition);
        int decoratedRight = getDecoratedRight(viewForPosition);
        fillLeft(recycler, this.mInitialSelectedPosition - 1, decoratedLeft, startAfterPadding);
        fillRight(recycler, this.mInitialSelectedPosition + 1, decoratedRight, endAfterPadding);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsRemoved(recyclerView, positionStart, itemCount);
    }

    private void firstFillWithVertical(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int startAfterPadding = getOrientationHelper().getStartAfterPadding();
        int endAfterPadding = getOrientationHelper().getEndAfterPadding();
        int i = this.mInitialSelectedPosition;
        Rect rect = new Rect();
        int horizontalSpace = getHorizontalSpace();
        View viewForPosition = recycler.getViewForPosition(this.mInitialSelectedPosition);
        addView(viewForPosition, 0);
        measureChildWithMargins(viewForPosition, 0, 0);
        int paddingLeft = (int) (getPaddingLeft() + ((horizontalSpace - r4) / 2.0f));
        int paddingTop = (int) (getPaddingTop() + ((getVerticalSpace() - r5) / 2.0f));
        rect.set(paddingLeft, paddingTop, getDecoratedMeasuredWidth(viewForPosition) + paddingLeft, getDecoratedMeasuredHeight(viewForPosition) + paddingTop);
        layoutDecorated(viewForPosition, rect.left, rect.top, rect.right, rect.bottom);
        if (getState().mItemsFrames.get(i) == null) {
            getState().mItemsFrames.put(i, rect);
        } else {
            getState().mItemsFrames.get(i).set(rect);
        }
        this.mLastVisiblePos = i;
        this.mFirstVisiblePosition = i;
        int decoratedTop = getDecoratedTop(viewForPosition);
        int decoratedBottom = getDecoratedBottom(viewForPosition);
        fillTop(recycler, this.mInitialSelectedPosition - 1, decoratedTop, startAfterPadding);
        fillBottom(recycler, this.mInitialSelectedPosition + 1, decoratedBottom, endAfterPadding);
    }

    private void fillLeft(RecyclerView.Recycler recycler, int startPosition, int startOffset, int leftEdge) {
        Rect rect = new Rect();
        int verticalSpace = getVerticalSpace();
        while (startPosition >= 0 && startOffset > leftEdge) {
            View viewForPosition = recycler.getViewForPosition(startPosition);
            addView(viewForPosition, 0);
            measureChildWithMargins(viewForPosition, 0, 0);
            int paddingTop = (int) (getPaddingTop() + ((verticalSpace - r4) / 2.0f));
            rect.set(startOffset - getDecoratedMeasuredWidth(viewForPosition), paddingTop, startOffset, getDecoratedMeasuredHeight(viewForPosition) + paddingTop);
            layoutDecorated(viewForPosition, rect.left, rect.top, rect.right, rect.bottom);
            startOffset = rect.left;
            this.mFirstVisiblePosition = startPosition;
            if (getState().mItemsFrames.get(startPosition) == null) {
                getState().mItemsFrames.put(startPosition, rect);
            } else {
                getState().mItemsFrames.get(startPosition).set(rect);
            }
            startPosition--;
        }
    }

    private void fillRight(RecyclerView.Recycler recycler, int startPosition, int startOffset, int rightEdge) {
        Rect rect = new Rect();
        int verticalSpace = getVerticalSpace();
        while (startPosition < getItemCount() && startOffset < rightEdge) {
            View viewForPosition = recycler.getViewForPosition(startPosition);
            addView(viewForPosition);
            measureChildWithMargins(viewForPosition, 0, 0);
            int paddingTop = (int) (getPaddingTop() + ((verticalSpace - r3) / 2.0f));
            rect.set(startOffset, paddingTop, getDecoratedMeasuredWidth(viewForPosition) + startOffset, getDecoratedMeasuredHeight(viewForPosition) + paddingTop);
            layoutDecorated(viewForPosition, rect.left, rect.top, rect.right, rect.bottom);
            startOffset = rect.right;
            this.mLastVisiblePos = startPosition;
            if (getState().mItemsFrames.get(startPosition) == null) {
                getState().mItemsFrames.put(startPosition, rect);
            } else {
                getState().mItemsFrames.get(startPosition).set(rect);
            }
            startPosition++;
        }
    }

    private void fillTop(RecyclerView.Recycler recycler, int startPosition, int startOffset, int topEdge) {
        Rect rect = new Rect();
        int horizontalSpace = getHorizontalSpace();
        while (startPosition >= 0 && startOffset > topEdge) {
            View viewForPosition = recycler.getViewForPosition(startPosition);
            addView(viewForPosition, 0);
            measureChildWithMargins(viewForPosition, 0, 0);
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
            int paddingLeft = (int) (getPaddingLeft() + ((horizontalSpace - decoratedMeasuredWidth) / 2.0f));
            rect.set(paddingLeft, startOffset - getDecoratedMeasuredHeight(viewForPosition), decoratedMeasuredWidth + paddingLeft, startOffset);
            layoutDecorated(viewForPosition, rect.left, rect.top, rect.right, rect.bottom);
            startOffset = rect.top;
            this.mFirstVisiblePosition = startPosition;
            if (getState().mItemsFrames.get(startPosition) == null) {
                getState().mItemsFrames.put(startPosition, rect);
            } else {
                getState().mItemsFrames.get(startPosition).set(rect);
            }
            startPosition--;
        }
    }

    private void fillBottom(RecyclerView.Recycler recycler, int startPosition, int startOffset, int bottomEdge) {
        Rect rect = new Rect();
        int horizontalSpace = getHorizontalSpace();
        while (startPosition < getItemCount() && startOffset < bottomEdge) {
            View viewForPosition = recycler.getViewForPosition(startPosition);
            addView(viewForPosition);
            measureChildWithMargins(viewForPosition, 0, 0);
            int paddingLeft = (int) (getPaddingLeft() + ((horizontalSpace - r2) / 2.0f));
            rect.set(paddingLeft, startOffset, getDecoratedMeasuredWidth(viewForPosition) + paddingLeft, getDecoratedMeasuredHeight(viewForPosition) + startOffset);
            layoutDecorated(viewForPosition, rect.left, rect.top, rect.right, rect.bottom);
            startOffset = rect.bottom;
            this.mLastVisiblePos = startPosition;
            if (getState().mItemsFrames.get(startPosition) == null) {
                getState().mItemsFrames.put(startPosition, rect);
            } else {
                getState().mItemsFrames.get(startPosition).set(rect);
            }
            startPosition++;
        }
    }

    private void fillCover(RecyclerView.Recycler recycler, RecyclerView.State state, int scrollDelta) {
        if (getItemCount() == 0) {
            return;
        }
        if (this.mOrientation == 0) {
            fillWithHorizontal(recycler, state, scrollDelta);
        } else {
            fillWithVertical(recycler, state, scrollDelta);
        }
        if (this.mItemTransformer != null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                this.mItemTransformer.transformItem(this, childAt, calculateToCenterFraction(childAt, scrollDelta));
            }
        }
    }

    private float calculateToCenterFraction(View child, float pendingOffset) {
        return Math.max(-1.0f, Math.min(1.0f, (calculateDistanceCenter(child, pendingOffset) * 1.0f) / (this.mOrientation == 0 ? child.getWidth() : child.getHeight())));
    }

    private int calculateDistanceCenter(View child, float pendingOffset) {
        float height;
        int top;
        OrientationHelper orientationHelper = getOrientationHelper();
        int endAfterPadding = ((orientationHelper.getEndAfterPadding() - orientationHelper.getStartAfterPadding()) / 2) + orientationHelper.getStartAfterPadding();
        if (this.mOrientation == 0) {
            height = (child.getWidth() / 2) - pendingOffset;
            top = child.getLeft();
        } else {
            height = (child.getHeight() / 2) - pendingOffset;
            top = child.getTop();
        }
        return (int) ((height + top) - endAfterPadding);
    }

    private void fillWithVertical(RecyclerView.Recycler recycler, RecyclerView.State state, int dy) {
        int i;
        int i2;
        int startAfterPadding = getOrientationHelper().getStartAfterPadding();
        int endAfterPadding = getOrientationHelper().getEndAfterPadding();
        if (getChildCount() > 0) {
            if (dy >= 0) {
                int i3 = 0;
                for (int i4 = 0; i4 < getChildCount(); i4++) {
                    View childAt = getChildAt(i4 + i3);
                    if (getDecoratedBottom(childAt) - dy >= startAfterPadding) {
                        break;
                    }
                    removeAndRecycleView(childAt, recycler);
                    this.mFirstVisiblePosition++;
                    i3--;
                }
            } else {
                for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                    View childAt2 = getChildAt(childCount);
                    if (getDecoratedTop(childAt2) - dy <= endAfterPadding) {
                        break;
                    }
                    removeAndRecycleView(childAt2, recycler);
                    this.mLastVisiblePos--;
                }
            }
        }
        int i5 = this.mFirstVisiblePosition;
        int horizontalSpace = getHorizontalSpace();
        int i6 = -1;
        if (dy >= 0) {
            if (getChildCount() != 0) {
                View childAt3 = getChildAt(getChildCount() - 1);
                int position = getPosition(childAt3) + 1;
                i2 = getDecoratedBottom(childAt3);
                i = position;
            } else {
                i = i5;
                i2 = -1;
            }
            for (int i7 = i; i7 < getItemCount() && i2 < endAfterPadding + dy; i7++) {
                Rect rect = getState().mItemsFrames.get(i7);
                View viewForPosition = recycler.getViewForPosition(i7);
                addView(viewForPosition);
                if (rect == null) {
                    rect = new Rect();
                    getState().mItemsFrames.put(i7, rect);
                }
                Rect rect2 = rect;
                measureChildWithMargins(viewForPosition, 0, 0);
                int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
                int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
                int paddingLeft = (int) (getPaddingLeft() + ((horizontalSpace - decoratedMeasuredWidth) / 2.0f));
                if (i2 == -1 && i == 0) {
                    int paddingTop = (int) (getPaddingTop() + ((getVerticalSpace() - decoratedMeasuredHeight) / 2.0f));
                    rect2.set(paddingLeft, paddingTop, decoratedMeasuredWidth + paddingLeft, decoratedMeasuredHeight + paddingTop);
                } else {
                    rect2.set(paddingLeft, i2, decoratedMeasuredWidth + paddingLeft, decoratedMeasuredHeight + i2);
                }
                layoutDecorated(viewForPosition, rect2.left, rect2.top, rect2.right, rect2.bottom);
                i2 = rect2.bottom;
                this.mLastVisiblePos = i7;
            }
            return;
        }
        if (getChildCount() > 0) {
            View childAt4 = getChildAt(0);
            int position2 = getPosition(childAt4) - 1;
            i6 = getDecoratedTop(childAt4);
            i5 = position2;
        }
        for (int i8 = i5; i8 >= 0 && i6 > startAfterPadding + dy; i8--) {
            Rect rect3 = getState().mItemsFrames.get(i8);
            View viewForPosition2 = recycler.getViewForPosition(i8);
            addView(viewForPosition2, 0);
            if (rect3 == null) {
                rect3 = new Rect();
                getState().mItemsFrames.put(i8, rect3);
            }
            Rect rect4 = rect3;
            measureChildWithMargins(viewForPosition2, 0, 0);
            int decoratedMeasuredWidth2 = getDecoratedMeasuredWidth(viewForPosition2);
            int paddingLeft2 = (int) (getPaddingLeft() + ((horizontalSpace - decoratedMeasuredWidth2) / 2.0f));
            rect4.set(paddingLeft2, i6 - getDecoratedMeasuredHeight(viewForPosition2), decoratedMeasuredWidth2 + paddingLeft2, i6);
            layoutDecorated(viewForPosition2, rect4.left, rect4.top, rect4.right, rect4.bottom);
            i6 = rect4.top;
            this.mFirstVisiblePosition = i8;
        }
    }

    private void fillWithHorizontal(RecyclerView.Recycler recycler, RecyclerView.State state, int dx) {
        int i;
        int i2;
        int startAfterPadding = getOrientationHelper().getStartAfterPadding();
        int endAfterPadding = getOrientationHelper().getEndAfterPadding();
        if (getChildCount() > 0) {
            if (dx >= 0) {
                int i3 = 0;
                for (int i4 = 0; i4 < getChildCount(); i4++) {
                    View childAt = getChildAt(i4 + i3);
                    if (getDecoratedRight(childAt) - dx >= startAfterPadding) {
                        break;
                    }
                    removeAndRecycleView(childAt, recycler);
                    this.mFirstVisiblePosition++;
                    i3--;
                }
            } else {
                for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                    View childAt2 = getChildAt(childCount);
                    if (getDecoratedLeft(childAt2) - dx > endAfterPadding) {
                        removeAndRecycleView(childAt2, recycler);
                        this.mLastVisiblePos--;
                    }
                }
            }
        }
        int i5 = this.mFirstVisiblePosition;
        int verticalSpace = getVerticalSpace();
        int i6 = -1;
        if (dx >= 0) {
            if (getChildCount() != 0) {
                View childAt3 = getChildAt(getChildCount() - 1);
                int position = getPosition(childAt3) + 1;
                i2 = getDecoratedRight(childAt3);
                i = position;
            } else {
                i = i5;
                i2 = -1;
            }
            for (int i7 = i; i7 < getItemCount() && i2 < endAfterPadding + dx; i7++) {
                Rect rect = getState().mItemsFrames.get(i7);
                View viewForPosition = recycler.getViewForPosition(i7);
                addView(viewForPosition);
                if (rect == null) {
                    rect = new Rect();
                    getState().mItemsFrames.put(i7, rect);
                }
                Rect rect2 = rect;
                measureChildWithMargins(viewForPosition, 0, 0);
                int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
                int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
                int paddingTop = (int) (getPaddingTop() + ((verticalSpace - decoratedMeasuredHeight) / 2.0f));
                if (i2 == -1 && i == 0) {
                    int paddingLeft = (int) (getPaddingLeft() + ((getHorizontalSpace() - decoratedMeasuredWidth) / 2.0f));
                    rect2.set(paddingLeft, paddingTop, decoratedMeasuredWidth + paddingLeft, decoratedMeasuredHeight + paddingTop);
                } else {
                    rect2.set(i2, paddingTop, decoratedMeasuredWidth + i2, decoratedMeasuredHeight + paddingTop);
                }
                layoutDecorated(viewForPosition, rect2.left, rect2.top, rect2.right, rect2.bottom);
                i2 = rect2.right;
                this.mLastVisiblePos = i7;
            }
            return;
        }
        if (getChildCount() > 0) {
            View childAt4 = getChildAt(0);
            int position2 = getPosition(childAt4) - 1;
            i6 = getDecoratedLeft(childAt4);
            i5 = position2;
        }
        for (int i8 = i5; i8 >= 0 && i6 > startAfterPadding + dx; i8--) {
            Rect rect3 = getState().mItemsFrames.get(i8);
            View viewForPosition2 = recycler.getViewForPosition(i8);
            addView(viewForPosition2, 0);
            if (rect3 == null) {
                rect3 = new Rect();
                getState().mItemsFrames.put(i8, rect3);
            }
            Rect rect4 = rect3;
            measureChildWithMargins(viewForPosition2, 0, 0);
            int paddingTop2 = (int) (getPaddingTop() + ((verticalSpace - r3) / 2.0f));
            rect4.set(i6 - getDecoratedMeasuredWidth(viewForPosition2), paddingTop2, i6, getDecoratedMeasuredHeight(viewForPosition2) + paddingTop2);
            layoutDecorated(viewForPosition2, rect4.left, rect4.top, rect4.right, rect4.bottom);
            i6 = rect4.left;
            this.mFirstVisiblePosition = i8;
        }
    }

    private int getHorizontalSpace() {
        return (getWidth() - getPaddingRight()) - getPaddingLeft();
    }

    private int getVerticalSpace() {
        return (getHeight() - getPaddingBottom()) - getPaddingTop();
    }

    public State getState() {
        if (this.mState == null) {
            this.mState = new State(this);
        }
        return this.mState;
    }

    private int calculateScrollDirectionForPosition(int position) {
        return (getChildCount() != 0 && position >= this.mFirstVisiblePosition) ? 1 : -1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public PointF computeScrollVectorForPosition(int targetPosition) {
        int calculateScrollDirectionForPosition = calculateScrollDirectionForPosition(targetPosition);
        PointF pointF = new PointF();
        if (calculateScrollDirectionForPosition == 0) {
            return null;
        }
        if (this.mOrientation == 0) {
            pointF.x = calculateScrollDirectionForPosition;
            pointF.y = 0.0f;
            return pointF;
        }
        pointF.x = 0.0f;
        pointF.y = calculateScrollDirectionForPosition;
        return pointF;
    }

    class State {
        SparseArray<Rect> mItemsFrames = new SparseArray<>();
        int mScrollDelta = 0;

        public State(final GalleryLayoutManager this$0) {
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.canScroll && this.mOrientation == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.canScroll && this.mOrientation == 1;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int min;
        if (getChildCount() == 0 || dx == 0) {
            return 0;
        }
        int i = -dx;
        int endAfterPadding = ((getOrientationHelper().getEndAfterPadding() - getOrientationHelper().getStartAfterPadding()) / 2) + getOrientationHelper().getStartAfterPadding();
        if (dx > 0) {
            if (getPosition(getChildAt(getChildCount() - 1)) == getItemCount() - 1) {
                View childAt = getChildAt(getChildCount() - 1);
                min = Math.max(0, Math.min(dx, (((childAt.getRight() - childAt.getLeft()) / 2) + childAt.getLeft()) - endAfterPadding));
                i = -min;
            }
            int i2 = -i;
            getState().mScrollDelta = i2;
            fillCover(recycler, state, i2);
            offsetChildrenHorizontal(i);
            return i2;
        }
        if (this.mFirstVisiblePosition == 0) {
            View childAt2 = getChildAt(0);
            min = Math.min(0, Math.max(dx, (((childAt2.getRight() - childAt2.getLeft()) / 2) + childAt2.getLeft()) - endAfterPadding));
            i = -min;
        }
        int i22 = -i;
        getState().mScrollDelta = i22;
        fillCover(recycler, state, i22);
        offsetChildrenHorizontal(i);
        return i22;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int min;
        if (getChildCount() == 0 || dy == 0) {
            return 0;
        }
        int i = -dy;
        int endAfterPadding = ((getOrientationHelper().getEndAfterPadding() - getOrientationHelper().getStartAfterPadding()) / 2) + getOrientationHelper().getStartAfterPadding();
        if (dy > 0) {
            if (getPosition(getChildAt(getChildCount() - 1)) == getItemCount() - 1) {
                View childAt = getChildAt(getChildCount() - 1);
                min = Math.max(0, Math.min(dy, (((getDecoratedBottom(childAt) - getDecoratedTop(childAt)) / 2) + getDecoratedTop(childAt)) - endAfterPadding));
                i = -min;
            }
            int i2 = -i;
            getState().mScrollDelta = i2;
            fillCover(recycler, state, i2);
            offsetChildrenVertical(i);
            return i2;
        }
        if (this.mFirstVisiblePosition == 0) {
            View childAt2 = getChildAt(0);
            min = Math.min(0, Math.max(dy, (((getDecoratedBottom(childAt2) - getDecoratedTop(childAt2)) / 2) + getDecoratedTop(childAt2)) - endAfterPadding));
            i = -min;
        }
        int i22 = -i;
        getState().mScrollDelta = i22;
        fillCover(recycler, state, i22);
        offsetChildrenVertical(i);
        return i22;
    }

    public OrientationHelper getOrientationHelper() {
        if (this.mOrientation == 0) {
            if (this.mHorizontalHelper == null) {
                this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(this);
            }
            return this.mHorizontalHelper;
        }
        if (this.mVerticalHelper == null) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(this);
        }
        return this.mVerticalHelper;
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

    public void setItemTransformer(ItemTransformer itemTransformer) {
        this.mItemTransformer = itemTransformer;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public void attach(RecyclerView recyclerView) {
        attach(recyclerView, -1);
    }

    public void attach(RecyclerView recyclerView, int selectedPosition) {
        if (recyclerView == null) {
            throw new IllegalArgumentException("The attach RecycleView must not null!!");
        }
        this.mRecyclerView = recyclerView;
        this.mInitialSelectedPosition = Math.max(0, selectedPosition);
        recyclerView.setLayoutManager(this);
        this.mSnapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(this.mInnerScrollListener);
    }

    public void setCallbackInFling(boolean callbackInFling) {
        this.mCallbackInFling = callbackInFling;
    }

    private class InnerScrollListener extends RecyclerView.OnScrollListener {
        boolean mCallbackOnIdle;
        int mState;

        private InnerScrollListener() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int position;
            super.onScrolled(recyclerView, dx, dy);
            View findSnapView = GalleryLayoutManager.this.mSnapHelper.findSnapView(recyclerView.getLayoutManager());
            if (findSnapView == null || (position = recyclerView.getLayoutManager().getPosition(findSnapView)) == GalleryLayoutManager.this.mCurSelectedPosition) {
                return;
            }
            if (GalleryLayoutManager.this.mCurSelectedView != null) {
                GalleryLayoutManager.this.mCurSelectedView.setSelected(false);
            }
            GalleryLayoutManager.this.mCurSelectedView = findSnapView;
            GalleryLayoutManager.this.mCurSelectedView.setSelected(true);
            GalleryLayoutManager.this.mCurSelectedPosition = position;
            if (!GalleryLayoutManager.this.mCallbackInFling && this.mState != 0) {
                this.mCallbackOnIdle = true;
            } else if (GalleryLayoutManager.this.mOnItemSelectedListener != null) {
                GalleryLayoutManager.this.mOnItemSelectedListener.onItemSelected(recyclerView, findSnapView, GalleryLayoutManager.this.mCurSelectedPosition, true);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            this.mState = newState;
            if (newState == 0) {
                View findSnapView = GalleryLayoutManager.this.mSnapHelper.findSnapView(recyclerView.getLayoutManager());
                if (findSnapView != null) {
                    int position = recyclerView.getLayoutManager().getPosition(findSnapView);
                    if (position != GalleryLayoutManager.this.mCurSelectedPosition) {
                        if (GalleryLayoutManager.this.mCurSelectedView != null) {
                            GalleryLayoutManager.this.mCurSelectedView.setSelected(false);
                        }
                        GalleryLayoutManager.this.mCurSelectedView = findSnapView;
                        GalleryLayoutManager.this.mCurSelectedView.setSelected(true);
                        GalleryLayoutManager.this.mCurSelectedPosition = position;
                        if (GalleryLayoutManager.this.mOnItemSelectedListener != null) {
                            GalleryLayoutManager.this.mOnItemSelectedListener.onItemSelected(recyclerView, findSnapView, GalleryLayoutManager.this.mCurSelectedPosition, false);
                            return;
                        }
                        return;
                    }
                    if (GalleryLayoutManager.this.mCallbackInFling || GalleryLayoutManager.this.mOnItemSelectedListener == null || !this.mCallbackOnIdle) {
                        return;
                    }
                    this.mCallbackOnIdle = false;
                    GalleryLayoutManager.this.mOnItemSelectedListener.onItemSelected(recyclerView, findSnapView, GalleryLayoutManager.this.mCurSelectedPosition, false);
                    return;
                }
                LHomeLog.i(getClass(), "onScrollStateChanged: snap null");
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        GallerySmoothScroller gallerySmoothScroller = new GallerySmoothScroller(this, recyclerView.getContext());
        gallerySmoothScroller.setTargetPosition(position);
        startSmoothScroll(gallerySmoothScroller);
    }

    private class GallerySmoothScroller extends LinearSmoothScroller {
        public GallerySmoothScroller(final GalleryLayoutManager this$0, Context context) {
            super(context);
        }

        public int calculateDxToMakeCentral(View view) {
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (layoutManager == null || !layoutManager.canScrollHorizontally()) {
                return 0;
            }
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            int decoratedLeft = layoutManager.getDecoratedLeft(view) - layoutParams.leftMargin;
            int decoratedRight = layoutManager.getDecoratedRight(view) + layoutParams.rightMargin;
            return ((int) (((layoutManager.getWidth() - layoutManager.getPaddingRight()) - layoutManager.getPaddingLeft()) / 2.0f)) - (decoratedLeft + ((int) ((decoratedRight - decoratedLeft) / 2.0f)));
        }

        public int calculateDyToMakeCentral(View view) {
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            if (layoutManager == null || !layoutManager.canScrollVertically()) {
                return 0;
            }
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            int decoratedTop = layoutManager.getDecoratedTop(view) - layoutParams.topMargin;
            int decoratedBottom = layoutManager.getDecoratedBottom(view) + layoutParams.bottomMargin;
            return ((int) (((layoutManager.getHeight() - layoutManager.getPaddingBottom()) - layoutManager.getPaddingTop()) / 2.0f)) - (decoratedTop + ((int) ((decoratedBottom - decoratedTop) / 2.0f)));
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
        protected void onTargetFound(View targetView, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
            int calculateDxToMakeCentral = calculateDxToMakeCentral(targetView);
            int calculateDyToMakeCentral = calculateDyToMakeCentral(targetView);
            int calculateTimeForDeceleration = calculateTimeForDeceleration((int) Math.sqrt((calculateDxToMakeCentral * calculateDxToMakeCentral) + (calculateDyToMakeCentral * calculateDyToMakeCentral)));
            if (calculateTimeForDeceleration > 0) {
                action.update(-calculateDxToMakeCentral, -calculateDyToMakeCentral, calculateTimeForDeceleration, this.mDecelerateInterpolator);
            }
        }
    }
}