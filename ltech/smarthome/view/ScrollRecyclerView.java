package com.ltech.smarthome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class ScrollRecyclerView extends RecyclerView {
    private boolean isScrollEnabled;

    public ScrollRecyclerView(Context context) {
        super(context);
        this.isScrollEnabled = true;
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isScrollEnabled = true;
    }

    public ScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.isScrollEnabled = true;
    }

    public void setScrollEnabled(boolean enabled) {
        this.isScrollEnabled = enabled;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        return this.isScrollEnabled && super.onTouchEvent(e);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return this.isScrollEnabled && super.onInterceptTouchEvent(e);
    }

    @Override // android.view.View
    public boolean canScrollVertically(int direction) {
        return this.isScrollEnabled && super.canScrollVertically(direction);
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int direction) {
        return this.isScrollEnabled && super.canScrollHorizontally(direction);
    }
}