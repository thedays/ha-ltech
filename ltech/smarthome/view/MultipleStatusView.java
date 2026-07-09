package com.ltech.smarthome.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.ltech.smarthome.R;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class MultipleStatusView extends RelativeLayout {
    private static final RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS = new RelativeLayout.LayoutParams(-1, -1);
    private static final int NULL_RESOURCE_ID = -1;
    public static final int STATUS_CONTENT = 0;
    public static final int STATUS_EMPTY = 2;
    public static final int STATUS_ERROR = 3;
    public static final int STATUS_EXT = 4;
    public static final int STATUS_LOADING = 1;
    private static final String TAG = "MultipleStatusView";
    private View mContentView;
    private int mContentViewResId;
    private View mEmptyView;
    private int mEmptyViewResId;
    private View mErrorView;
    private int mErrorViewResId;
    private View mExtView;
    private final LayoutInflater mInflater;
    private View mLoadingView;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private View.OnClickListener mOnRetryClickListener;
    private final ArrayList<Integer> mOtherIds;
    private int mViewStatus;
    private OnViewStatusChangeListener mViewStatusListener;

    public interface OnViewStatusChangeListener {
        void onChange(int oldViewStatus, int newViewStatus);
    }

    public MultipleStatusView(Context context) {
        this(context, null);
    }

    public MultipleStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContentViewResId = -1;
        this.mViewStatus = -1;
        this.mOtherIds = new ArrayList<>();
        this.mInflater = LayoutInflater.from(getContext());
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        showContent();
    }

    public int getViewStatus() {
        return this.mViewStatus;
    }

    public void setOnRetryClickListener(View.OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    public final void showEmpty() {
        showEmpty(this.mEmptyViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    public final void showEmpty(int layoutId, ViewGroup.LayoutParams layoutParams) {
        View view = this.mEmptyView;
        if (view == null) {
            view = inflateView(layoutId);
        }
        showEmpty(view, layoutParams);
    }

    public final void showEmpty(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Empty view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(2);
        if (this.mEmptyView == null) {
            this.mEmptyView = view;
            View findViewById = view.findViewById(R.id.empty_retry_view);
            View.OnClickListener onClickListener = this.mOnRetryClickListener;
            if (onClickListener != null && findViewById != null) {
                findViewById.setOnClickListener(onClickListener);
            }
            this.mOtherIds.add(Integer.valueOf(this.mEmptyView.getId()));
            addView(this.mEmptyView, 0, layoutParams);
        }
        showViewById(this.mEmptyView.getId());
    }

    public final void showError() {
        showError(this.mErrorViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    public final void showError(int layoutId, ViewGroup.LayoutParams layoutParams) {
        View view = this.mErrorView;
        if (view == null) {
            view = inflateView(layoutId);
        }
        showError(view, layoutParams);
    }

    public final void showError(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Error view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(3);
        View view2 = this.mErrorView;
        if (view2 != null) {
            removeView(view2);
        }
        this.mErrorView = view;
        View findViewById = view.findViewById(R.id.error_retry_view);
        View.OnClickListener onClickListener = this.mOnRetryClickListener;
        if (onClickListener != null && findViewById != null) {
            findViewById.setOnClickListener(onClickListener);
        }
        this.mOtherIds.add(Integer.valueOf(this.mErrorView.getId()));
        addView(this.mErrorView, 0, layoutParams);
        showViewById(this.mErrorView.getId());
    }

    public final void showLoading() {
        showLoading(this.mLoadingViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    public final void showLoading(int layoutId, ViewGroup.LayoutParams layoutParams) {
        View view = this.mLoadingView;
        if (view == null) {
            view = inflateView(layoutId);
        }
        showLoading(view, layoutParams);
    }

    public final void showLoading(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Loading view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(1);
        if (this.mLoadingView == null) {
            this.mLoadingView = view;
            this.mOtherIds.add(Integer.valueOf(view.getId()));
            addView(this.mLoadingView, 0, layoutParams);
        }
        showViewById(this.mLoadingView.getId());
    }

    public final void showExtView() {
        showExtView(this.mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    public final void showExtView(int layoutId, ViewGroup.LayoutParams layoutParams) {
        View view = this.mExtView;
        if (view == null) {
            view = inflateView(layoutId);
        }
        showExtView(view, layoutParams);
    }

    public final void showExtView(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "No network view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(4);
        if (this.mExtView == null) {
            this.mExtView = view;
            this.mOtherIds.add(Integer.valueOf(view.getId()));
            addView(this.mExtView, 0, layoutParams);
        }
        showViewById(this.mExtView.getId());
    }

    public final void showContent() {
        int i;
        changeViewStatus(0);
        if (this.mContentView == null && (i = this.mContentViewResId) != -1) {
            View inflate = this.mInflater.inflate(i, (ViewGroup) null);
            this.mContentView = inflate;
            addView(inflate, 0, DEFAULT_LAYOUT_PARAMS);
        }
        showContentView();
    }

    public final void showContent(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showContent(inflateView(layoutId), layoutParams);
    }

    public final void showContent(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Content view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(0);
        clear(this.mContentView);
        this.mContentView = view;
        addView(view, 0, layoutParams);
        showViewById(this.mContentView.getId());
    }

    private View inflateView(int layoutId) {
        return this.mInflater.inflate(layoutId, (ViewGroup) null);
    }

    private void showViewById(int viewId) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.setVisibility(childAt.getId() == viewId ? 0 : 8);
        }
    }

    private void showContentView() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.setVisibility(this.mOtherIds.contains(Integer.valueOf(childAt.getId())) ? 8 : 0);
        }
    }

    private void checkNull(Object object, String hint) {
        if (object == null) {
            throw new NullPointerException(hint);
        }
    }

    private void clear(View... views) {
        if (views == null) {
            return;
        }
        try {
            for (View view : views) {
                if (view != null) {
                    removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnViewStatusChangeListener(OnViewStatusChangeListener onViewStatusChangeListener) {
        this.mViewStatusListener = onViewStatusChangeListener;
    }

    private void changeViewStatus(int newViewStatus) {
        int i = this.mViewStatus;
        if (i == newViewStatus) {
            return;
        }
        OnViewStatusChangeListener onViewStatusChangeListener = this.mViewStatusListener;
        if (onViewStatusChangeListener != null) {
            onViewStatusChangeListener.onChange(i, newViewStatus);
        }
        this.mViewStatus = newViewStatus;
    }
}