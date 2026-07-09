package com.ltech.smarthome.adapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chad.library.adapter.base.util.ProviderDelegate;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class MultipleItemRvAdapter<T, V extends BaseViewHolder> extends BaseItemDraggableAdapter<T, V> {
    private SparseArray<BaseItemProvider> mItemProviders;
    private MultiTypeDelegate<T> mMultiTypeDelegate;
    protected ProviderDelegate mProviderDelegate;

    protected abstract int getViewType(T var1);

    public abstract void registerItemProvider();

    public MultipleItemRvAdapter(List<T> data) {
        super(data);
    }

    public void finishInitialize() {
        this.mProviderDelegate = new ProviderDelegate();
        setMultiTypeDelegate(new MultiTypeDelegate<T>() { // from class: com.ltech.smarthome.adapter.MultipleItemRvAdapter.1
            @Override // com.chad.library.adapter.base.util.MultiTypeDelegate
            protected int getItemType(T t) {
                return MultipleItemRvAdapter.this.getViewType(t);
            }
        });
        registerItemProvider();
        this.mItemProviders = this.mProviderDelegate.getItemProviders();
        for (int i = 0; i < this.mItemProviders.size(); i++) {
            int keyAt = this.mItemProviders.keyAt(i);
            BaseItemProvider baseItemProvider = this.mItemProviders.get(keyAt);
            baseItemProvider.mData = this.mData;
            getMultiTypeDelegate().registerItemType(keyAt, baseItemProvider.layout());
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void bindViewClickListener(V baseViewHolder) {
        if (baseViewHolder != null) {
            bindClick(baseViewHolder);
            super.bindViewClickListener(baseViewHolder);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected V onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        if (getMultiTypeDelegate() == null) {
            throw new IllegalStateException("please use setMultiTypeDelegate first!");
        }
        return (V) createBaseViewHolder(viewGroup, getMultiTypeDelegate().getLayoutId(i));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected int getDefItemViewType(int position) {
        if (getMultiTypeDelegate() == null) {
            throw new IllegalStateException("please use setMultiTypeDelegate first!");
        }
        return getMultiTypeDelegate().getDefItemViewType(this.mData, position);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convert(V helper, T item) {
        BaseItemProvider baseItemProvider = this.mItemProviders.get(helper.getItemViewType());
        baseItemProvider.mContext = helper.itemView.getContext();
        baseItemProvider.convert(helper, item, helper.getLayoutPosition() - getHeaderLayoutCount());
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convertPayloads(V helper, T item, List<Object> payloads) {
        this.mItemProviders.get(helper.getItemViewType()).convertPayloads(helper, item, helper.getLayoutPosition() - getHeaderLayoutCount(), payloads);
    }

    private void bindClick(final V helper) {
        BaseQuickAdapter.OnItemClickListener onItemClickListener = getOnItemClickListener();
        BaseQuickAdapter.OnItemLongClickListener onItemLongClickListener = getOnItemLongClickListener();
        if (onItemClickListener == null || onItemLongClickListener == null) {
            if (onItemClickListener == null) {
                helper.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.adapter.MultipleItemRvAdapter.2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        int adapterPosition = helper.getAdapterPosition();
                        if (adapterPosition != -1) {
                            int headerLayoutCount = adapterPosition - MultipleItemRvAdapter.this.getHeaderLayoutCount();
                            ((BaseItemProvider) MultipleItemRvAdapter.this.mItemProviders.get(helper.getItemViewType())).onClick(helper, MultipleItemRvAdapter.this.mData.get(headerLayoutCount), headerLayoutCount);
                        }
                    }
                });
            }
            if (onItemLongClickListener == null) {
                helper.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.ltech.smarthome.adapter.MultipleItemRvAdapter.3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View v) {
                        int adapterPosition = helper.getAdapterPosition();
                        if (adapterPosition == -1) {
                            return false;
                        }
                        int headerLayoutCount = adapterPosition - MultipleItemRvAdapter.this.getHeaderLayoutCount();
                        return ((BaseItemProvider) MultipleItemRvAdapter.this.mItemProviders.get(helper.getItemViewType())).onLongClick(helper, MultipleItemRvAdapter.this.mData.get(headerLayoutCount), headerLayoutCount);
                    }
                });
            }
        }
    }

    public void setMultiTypeDelegate(MultiTypeDelegate<T> multiTypeDelegate) {
        this.mMultiTypeDelegate = multiTypeDelegate;
    }

    public MultiTypeDelegate<T> getMultiTypeDelegate() {
        return this.mMultiTypeDelegate;
    }
}