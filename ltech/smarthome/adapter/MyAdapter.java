package com.ltech.smarthome.adapter;

import android.content.Context;
import android.view.View;
import com.ltech.smarthome.adapter.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class MyAdapter<T> extends BaseAdapter<BaseAdapter.ViewHolder> {
    private List<T> mDataSet;
    private boolean mLastPage;
    private int mPageNumber;
    private Object mTag;

    public MyAdapter(Context context) {
        super(context);
        this.mPageNumber = 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<T> list = this.mDataSet;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setData(List<T> data) {
        this.mDataSet = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return this.mDataSet;
    }

    public void addData(List<T> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        List<T> list = this.mDataSet;
        if (list == null || list.size() == 0) {
            setData(data);
        } else {
            this.mDataSet.addAll(data);
            notifyItemRangeInserted(this.mDataSet.size() - data.size(), data.size());
        }
    }

    public void clearData() {
        List<T> list = this.mDataSet;
        if (list == null || list.size() == 0) {
            return;
        }
        this.mDataSet.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return this.mDataSet.get(position);
    }

    public void setItem(int position, T item) {
        if (this.mDataSet == null) {
            this.mDataSet = new ArrayList();
        }
        this.mDataSet.set(position, item);
        notifyItemChanged(position);
    }

    public void addItem(T item) {
        if (this.mDataSet == null) {
            this.mDataSet = new ArrayList();
        }
        addItem(this.mDataSet.size(), item);
    }

    public void addItem(int position, T item) {
        if (this.mDataSet == null) {
            this.mDataSet = new ArrayList();
        }
        if (position < this.mDataSet.size()) {
            this.mDataSet.add(position, item);
        } else {
            this.mDataSet.add(item);
            position = this.mDataSet.size() - 1;
        }
        notifyItemInserted(position);
    }

    public void removeItem(T item) {
        int indexOf = this.mDataSet.indexOf(item);
        if (indexOf != -1) {
            removeItem(indexOf);
        }
    }

    public void removeItem(int position) {
        this.mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public int getPageNumber() {
        return this.mPageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.mPageNumber = pageNumber;
    }

    public boolean isLastPage() {
        return this.mLastPage;
    }

    public void setLastPage(boolean flag) {
        this.mLastPage = flag;
    }

    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public final class SimpleHolder extends BaseAdapter<BaseAdapter.ViewHolder>.ViewHolder {
        @Override // com.ltech.smarthome.adapter.BaseAdapter.ViewHolder
        public void onBindView(int position) {
        }

        public SimpleHolder(final MyAdapter this$0, int id) {
            super(this$0, id);
        }

        public SimpleHolder(final MyAdapter this$0, View itemView) {
            super(itemView);
        }
    }
}