package com.ltech.smarthome.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class AverageGridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "AverageGridSpacingItemDecoration";
    int itemSpace;
    int p;
    int spanCount;

    public AverageGridSpacingItemDecoration(int spanCount, int itemSpace) {
        this.spanCount = spanCount;
        this.itemSpace = itemSpace;
        this.p = (itemSpace * (spanCount + 1)) / spanCount;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int i = this.spanCount;
        int i2 = childAdapterPosition % i;
        if (childAdapterPosition / i == 0) {
            outRect.top = this.itemSpace;
        }
        int i3 = this.itemSpace;
        int i4 = this.p;
        int i5 = i3 + (i2 * (i3 - i4));
        int i6 = i4 - i5;
        outRect.left = Math.round(i5);
        outRect.right = Math.round(i6);
        outRect.bottom = this.itemSpace;
        Log.i(TAG, "getItemOffsets: position=" + childAdapterPosition + " left=" + i5 + " right=" + i6);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas c2, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c2, parent, state);
    }
}