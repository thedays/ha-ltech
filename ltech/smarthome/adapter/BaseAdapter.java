package com.ltech.smarthome.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.adapter.BaseAdapter.ViewHolder;

/* loaded from: classes3.dex */
public abstract class BaseAdapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> {
    private SparseArray<OnChildClickListener> mChildClickListeners;
    private SparseArray<OnChildLongClickListener> mChildLongClickListeners;
    private final Context mContext;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;
    private int mPositionOffset = 0;
    private RecyclerView mRecyclerView;
    private BaseAdapter<VH>.ScrollListener mScrollListener;
    private OnScrollingListener mScrollingListener;

    public interface OnChildClickListener {
        void onChildClick(RecyclerView recyclerView, View childView, int position);
    }

    public interface OnChildLongClickListener {
        boolean onChildLongClick(RecyclerView recyclerView, View childView, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView recyclerView, View itemView, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView recyclerView, View itemView, int position);
    }

    public interface OnScrollingListener {
        void onScrollDown(RecyclerView recyclerView);

        void onScrollTop(RecyclerView recyclerView);

        void onScrolling(RecyclerView recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return position;
    }

    public BaseAdapter(Context context) {
        this.mContext = context;
        if (context == null) {
            throw new IllegalArgumentException("are you ok?");
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(VH holder, int position) {
        this.mPositionOffset = position - holder.getAdapterPosition();
        holder.onBindView(position);
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public Context getContext() {
        return this.mContext;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public abstract void onBindView(int position);

        public ViewHolder(final BaseAdapter this$0, int id) {
            this(LayoutInflater.from(this$0.getContext()).inflate(id, (ViewGroup) this$0.getRecyclerView(), false));
        }

        public ViewHolder(View itemView) {
            super(itemView);
            if (BaseAdapter.this.mItemClickListener != null) {
                itemView.setOnClickListener(this);
            }
            if (BaseAdapter.this.mItemLongClickListener != null) {
                itemView.setOnLongClickListener(this);
            }
            if (BaseAdapter.this.mChildClickListeners != null) {
                for (int i = 0; i < BaseAdapter.this.mChildClickListeners.size(); i++) {
                    View findViewById = findViewById(BaseAdapter.this.mChildClickListeners.keyAt(i));
                    if (findViewById != null) {
                        findViewById.setOnClickListener(this);
                    }
                }
            }
            if (BaseAdapter.this.mChildLongClickListeners != null) {
                for (int i2 = 0; i2 < BaseAdapter.this.mChildLongClickListeners.size(); i2++) {
                    View findViewById2 = findViewById(BaseAdapter.this.mChildLongClickListeners.keyAt(i2));
                    if (findViewById2 != null) {
                        findViewById2.setOnLongClickListener(this);
                    }
                }
            }
        }

        protected final int getViewHolderPosition() {
            return getLayoutPosition() + BaseAdapter.this.mPositionOffset;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            OnChildClickListener onChildClickListener;
            int viewHolderPosition = getViewHolderPosition();
            if (viewHolderPosition < 0 || viewHolderPosition >= BaseAdapter.this.getItemCount()) {
                return;
            }
            if (v == getItemView()) {
                if (BaseAdapter.this.mItemClickListener != null) {
                    BaseAdapter.this.mItemClickListener.onItemClick(BaseAdapter.this.mRecyclerView, v, viewHolderPosition);
                }
            } else {
                if (BaseAdapter.this.mChildClickListeners == null || (onChildClickListener = (OnChildClickListener) BaseAdapter.this.mChildClickListeners.get(v.getId())) == null) {
                    return;
                }
                onChildClickListener.onChildClick(BaseAdapter.this.mRecyclerView, v, viewHolderPosition);
            }
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View v) {
            OnChildLongClickListener onChildLongClickListener;
            int viewHolderPosition = getViewHolderPosition();
            if (viewHolderPosition < 0 || viewHolderPosition >= BaseAdapter.this.getItemCount()) {
                return false;
            }
            if (v == getItemView()) {
                if (BaseAdapter.this.mItemLongClickListener != null) {
                    return BaseAdapter.this.mItemLongClickListener.onItemLongClick(BaseAdapter.this.mRecyclerView, v, viewHolderPosition);
                }
                return false;
            }
            if (BaseAdapter.this.mChildLongClickListeners == null || (onChildLongClickListener = (OnChildLongClickListener) BaseAdapter.this.mChildLongClickListeners.get(v.getId())) == null) {
                return false;
            }
            return onChildLongClickListener.onChildLongClick(BaseAdapter.this.mRecyclerView, v, viewHolderPosition);
        }

        public final View getItemView() {
            return this.itemView;
        }

        public final <V extends View> V findViewById(int i) {
            return (V) getItemView().findViewById(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager generateDefaultLayoutManager;
        this.mRecyclerView = recyclerView;
        BaseAdapter<VH>.ScrollListener scrollListener = this.mScrollListener;
        if (scrollListener != null) {
            recyclerView.addOnScrollListener(scrollListener);
        }
        if (this.mRecyclerView.getLayoutManager() != null || (generateDefaultLayoutManager = generateDefaultLayoutManager(this.mContext)) == null) {
            return;
        }
        this.mRecyclerView.setLayoutManager(generateDefaultLayoutManager);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        BaseAdapter<VH>.ScrollListener scrollListener = this.mScrollListener;
        if (scrollListener != null) {
            this.mRecyclerView.removeOnScrollListener(scrollListener);
        }
        this.mRecyclerView = null;
    }

    protected RecyclerView.LayoutManager generateDefaultLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        checkRecyclerViewState();
        this.mItemClickListener = listener;
    }

    public void setOnChildClickListener(int id, OnChildClickListener listener) {
        checkRecyclerViewState();
        if (this.mChildClickListeners == null) {
            this.mChildClickListeners = new SparseArray<>();
        }
        this.mChildClickListeners.put(id, listener);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        checkRecyclerViewState();
        this.mItemLongClickListener = listener;
    }

    public void setOnChildLongClickListener(int id, OnChildLongClickListener listener) {
        checkRecyclerViewState();
        if (this.mChildLongClickListeners == null) {
            this.mChildLongClickListeners = new SparseArray<>();
        }
        this.mChildLongClickListeners.put(id, listener);
    }

    private void checkRecyclerViewState() {
        if (this.mRecyclerView != null) {
            throw new IllegalStateException("are you ok?");
        }
    }

    public void setOnScrollingListener(OnScrollingListener listener) {
        this.mScrollingListener = listener;
        BaseAdapter<VH>.ScrollListener scrollListener = this.mScrollListener;
        if (scrollListener == null) {
            this.mScrollListener = new ScrollListener();
        } else {
            this.mRecyclerView.removeOnScrollListener(scrollListener);
        }
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null) {
            recyclerView.addOnScrollListener(this.mScrollListener);
        }
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {
        private ScrollListener() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (BaseAdapter.this.mScrollingListener == null) {
                return;
            }
            if (newState != 0) {
                if (newState == 1) {
                    BaseAdapter.this.mScrollingListener.onScrolling(recyclerView);
                }
            } else if (!recyclerView.canScrollVertically(1)) {
                BaseAdapter.this.mScrollingListener.onScrollDown(recyclerView);
            } else {
                if (recyclerView.canScrollVertically(-1)) {
                    return;
                }
                BaseAdapter.this.mScrollingListener.onScrollTop(recyclerView);
            }
        }
    }
}