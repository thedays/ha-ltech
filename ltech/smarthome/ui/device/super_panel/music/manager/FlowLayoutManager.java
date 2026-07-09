package com.ltech.smarthome.ui.device.super_panel.music.manager;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class FlowLayoutManager extends RecyclerView.LayoutManager {
    private int mContentHeight;
    private int mContentWidth;

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            this.mContentWidth = 0;
            this.mContentHeight = 0;
            return;
        }
        detachAndScrapAttachedViews(recycler);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i = 0;
        int i2 = 0;
        while (i < getItemCount()) {
            View viewForPosition = recycler.getViewForPosition(i);
            addView(viewForPosition);
            measureChildWithMargins(viewForPosition, 0, 0);
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
            if (paddingLeft + decoratedMeasuredWidth > getWidth() - getPaddingRight()) {
                paddingLeft = getPaddingLeft();
                paddingTop += i2;
                i2 = 0;
            }
            int i3 = paddingLeft;
            int i4 = paddingTop;
            int i5 = i3 + decoratedMeasuredWidth;
            layoutDecorated(viewForPosition, i3, i4, i5, i4 + decoratedMeasuredHeight);
            i2 = Math.max(i2, decoratedMeasuredHeight);
            i++;
            paddingTop = i4;
            paddingLeft = i5;
        }
        this.mContentWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
        this.mContentHeight = paddingTop + i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.mContentHeight > (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getVerticalScrollOffset() + dy < 0) {
            dy = -getVerticalScrollOffset();
        } else {
            int verticalScrollOffset = getVerticalScrollOffset() + dy + getVerticalVisibleHeight();
            int i = this.mContentHeight;
            if (verticalScrollOffset > i) {
                dy = (i - getVerticalVisibleHeight()) - getVerticalScrollOffset();
            }
        }
        offsetChildrenVertical(-dy);
        return dy;
    }

    private int getVerticalVisibleHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private int getVerticalScrollOffset() {
        return (getChildAt(0).getTop() * (-1)) + getPaddingTop();
    }
}