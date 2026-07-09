package com.ltech.smarthome.view.pagergridlayoutmanager;

import android.util.Log;
import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class PagerGridItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "ItemTouchListener";
    private final PagerGridLayoutManager layoutManager;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mScrollPointerId;
    private final RecyclerView recyclerView;

    PagerGridItemTouchListener(PagerGridLayoutManager layoutManager, RecyclerView recyclerView) {
        this.layoutManager = layoutManager;
        this.recyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener, androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        int actionMasked = e.getActionMasked();
        int actionIndex = e.getActionIndex();
        if (PagerGridLayoutManager.DEBUG) {
            Log.i(TAG, "onInterceptTouchEvent-actionMasked: " + actionMasked + ", actionIndex: " + actionIndex);
        }
        if (actionMasked != 0) {
            if (actionMasked == 2) {
                int findPointerIndex = e.findPointerIndex(this.mScrollPointerId);
                if (findPointerIndex < 0) {
                    return false;
                }
                int x = (int) (e.getX(findPointerIndex) + 0.5f);
                int y = (int) (e.getY(findPointerIndex) + 0.5f);
                int i = x - this.mInitialTouchX;
                int i2 = y - this.mInitialTouchY;
                if (this.layoutManager.canScrollHorizontally()) {
                    this.recyclerView.getParent().requestDisallowInterceptTouchEvent(this.recyclerView.canScrollHorizontally(-i));
                }
                if (this.layoutManager.canScrollVertically()) {
                    this.recyclerView.getParent().requestDisallowInterceptTouchEvent(this.recyclerView.canScrollVertically(-i2));
                }
            } else if (actionMasked != 5) {
                if (actionMasked == 6) {
                    onPointerUp(e);
                }
            }
            return false;
        }
        this.mScrollPointerId = e.getPointerId(actionIndex);
        this.mInitialTouchX = (int) (e.getX(actionIndex) + 0.5f);
        this.mInitialTouchY = (int) (e.getY(actionIndex) + 0.5f);
        this.recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }

    private void onPointerUp(MotionEvent e) {
        int actionIndex = e.getActionIndex();
        if (e.getPointerId(actionIndex) == this.mScrollPointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = e.getPointerId(i);
            this.mInitialTouchX = (int) (e.getX(i) + 0.5f);
            this.mInitialTouchY = (int) (e.getY(i) + 0.5f);
        }
    }
}