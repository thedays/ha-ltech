package com.ltech.smarthome.view.layoutmanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WrapContentLinearLayoutManager.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ,\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u00060\rR\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0016¨\u0006\u0013"}, d2 = {"Lcom/ltech/smarthome/view/layoutmanager/WrapContentLinearLayoutManager;", "Landroidx/recyclerview/widget/LinearLayoutManager;", "context", "Landroid/content/Context;", "orientation", "", "reverseLayout", "", "<init>", "(Landroid/content/Context;IZ)V", "onMeasure", "", "recycler", "Landroidx/recyclerview/widget/RecyclerView$Recycler;", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "widthSpec", "heightSpec", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrapContentLinearLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        Intrinsics.checkNotNullParameter(recycler, "recycler");
        Intrinsics.checkNotNullParameter(state, "state");
        int itemCount = getItemCount();
        int i = 0;
        for (int i2 = 0; i2 < itemCount; i2++) {
            View viewForPosition = recycler.getViewForPosition(i2);
            Intrinsics.checkNotNullExpressionValue(viewForPosition, "getViewForPosition(...)");
            measureChildWithMargins(viewForPosition, widthSpec, heightSpec);
            ViewGroup.LayoutParams layoutParams = viewForPosition.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.RecyclerView.LayoutParams");
            RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
            i += viewForPosition.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin;
        }
        int mode = View.MeasureSpec.getMode(widthSpec);
        int size = View.MeasureSpec.getSize(widthSpec);
        if (mode == Integer.MIN_VALUE) {
            i = Math.min(i, size);
        } else if (mode == 1073741824) {
            i = size;
        }
        setMeasuredDimension(i, View.MeasureSpec.getSize(heightSpec));
    }
}