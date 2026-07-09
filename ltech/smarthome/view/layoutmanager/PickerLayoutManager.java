package com.ltech.smarthome.view.layoutmanager;

import android.content.Context;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class PickerLayoutManager extends LinearLayoutManager {
    private final boolean mAlpha;
    private final float mAlphaData;
    private final LinearSnapHelper mLinearSnapHelper;
    private OnPickerListener mListener;
    private final int mMaxItem;
    private final int mOrientation;
    private RecyclerView mRecyclerView;
    private final float mScale;
    private boolean realWidthHeight;

    public interface OnPickerListener {
        void onPicked(RecyclerView recyclerView, int position);
    }

    private PickerLayoutManager(Context context, int orientation, boolean reverseLayout, int maxItem, float scale, boolean alpha, float alphaData) {
        super(context, orientation, reverseLayout);
        this.mLinearSnapHelper = new LinearSnapHelper();
        this.mMaxItem = maxItem;
        this.mOrientation = orientation;
        this.mAlpha = alpha;
        this.mScale = scale;
        this.mAlphaData = alphaData;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        this.mRecyclerView = recyclerView;
        recyclerView.setClipToPadding(false);
        if (this.mRecyclerView.getOnFlingListener() != null) {
            this.mRecyclerView.setOnFlingListener(null);
        }
        this.mLinearSnapHelper.attachToRecyclerView(this.mRecyclerView);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        this.mRecyclerView = null;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return this.mMaxItem == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        int chooseSize = RecyclerView.LayoutManager.chooseSize(widthSpec, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth(this.mRecyclerView));
        int chooseSize2 = RecyclerView.LayoutManager.chooseSize(heightSpec, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight(this.mRecyclerView));
        if (state.getItemCount() != 0 && this.mMaxItem != 0) {
            View viewForPosition = recycler.getViewForPosition(0);
            measureChildWithMargins(viewForPosition, widthSpec, heightSpec);
            int i = this.mOrientation;
            if (i == 0) {
                int measuredWidth = viewForPosition.getMeasuredWidth();
                int i2 = ((this.mMaxItem - 1) / 2) * measuredWidth;
                this.mRecyclerView.setPadding(i2, 0, i2, 0);
                chooseSize = measuredWidth * this.mMaxItem;
                if (this.realWidthHeight) {
                    chooseSize2 = viewForPosition.getHeight();
                }
            } else if (i == 1) {
                int measuredHeight = viewForPosition.getMeasuredHeight();
                int i3 = ((this.mMaxItem - 1) / 2) * measuredHeight;
                this.mRecyclerView.setPadding(0, i3, 0, i3);
                chooseSize2 = measuredHeight * this.mMaxItem;
                if (this.realWidthHeight) {
                    chooseSize = viewForPosition.getMeasuredWidth();
                }
            }
        }
        setMeasuredDimension(chooseSize, chooseSize2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int state) {
        OnPickerListener onPickerListener;
        super.onScrollStateChanged(state);
        if (state != 0 || (onPickerListener = this.mListener) == null) {
            return;
        }
        onPickerListener.onPicked(this.mRecyclerView, getPickedPosition());
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (getItemCount() < 0 || state.isPreLayout()) {
            return;
        }
        int i = this.mOrientation;
        if (i == 0) {
            scaleHorizontalChildView();
        } else if (i == 1) {
            scaleVerticalChildView();
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        scaleHorizontalChildView();
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        scaleVerticalChildView();
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    private void scaleHorizontalChildView() {
        float width = getWidth() / 2.0f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                float min = ((((1.0f - this.mScale) * (-1.0f)) * Math.min(width, Math.abs(width - ((getDecoratedLeft(childAt) + getDecoratedRight(childAt)) / 2.0f)))) / width) + 1.0f;
                childAt.setScaleX(min);
                childAt.setScaleY(min);
                if (this.mAlpha && min <= 0.9d) {
                    childAt.setAlpha(min);
                }
            }
        }
    }

    private void scaleVerticalChildView() {
        float height = getHeight() / 2.0f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                float min = ((((1.0f - this.mScale) * (-1.0f)) * Math.min(height, Math.abs(height - ((getDecoratedTop(childAt) + getDecoratedBottom(childAt)) / 2.0f)))) / height) + 1.0f;
                childAt.setScaleX(min);
                childAt.setScaleY(min);
                if (this.mAlpha && min <= 0.9d) {
                    childAt.setAlpha(this.mAlphaData);
                } else {
                    childAt.setAlpha(1.0f);
                }
            }
        }
    }

    public int getPickedPosition() {
        View findSnapView = this.mLinearSnapHelper.findSnapView(this);
        if (findSnapView != null) {
            return getPosition(findSnapView);
        }
        return 0;
    }

    public void setOnPickerListener(OnPickerListener listener) {
        this.mListener = listener;
    }

    public static final class Builder {
        private final Context mContext;
        private OnPickerListener mListener;
        private boolean mReverseLayout;
        private int mOrientation = 1;
        private int mMaxItem = 3;
        private float mScale = 0.6f;
        private boolean mAlpha = true;
        private float mAlphaData = 0.4f;

        public Builder setAlphaData(float mAlphaData) {
            return this;
        }

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setOrientation(int orientation) {
            this.mOrientation = orientation;
            return this;
        }

        public Builder setReverseLayout(boolean reverseLayout) {
            this.mReverseLayout = reverseLayout;
            return this;
        }

        public Builder setMaxItem(int maxItem) {
            this.mMaxItem = maxItem;
            return this;
        }

        public Builder setScale(float scale) {
            this.mScale = scale;
            return this;
        }

        public Builder setAlpha(boolean alpha) {
            this.mAlpha = alpha;
            return this;
        }

        public Builder setOnPickerListener(OnPickerListener listener) {
            this.mListener = listener;
            return this;
        }

        public PickerLayoutManager build() {
            PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this.mContext, this.mOrientation, this.mReverseLayout, this.mMaxItem, this.mScale, this.mAlpha, this.mAlphaData);
            OnPickerListener onPickerListener = this.mListener;
            if (onPickerListener != null) {
                pickerLayoutManager.setOnPickerListener(onPickerListener);
            }
            return pickerLayoutManager;
        }

        public void into(RecyclerView recyclerView) {
            recyclerView.setLayoutManager(build());
        }
    }

    public void setRealWidthHeight(boolean b2) {
        this.realWidthHeight = b2;
    }
}