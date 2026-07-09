package com.ltech.smarthome.base;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSortBinding;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseSortActivity<T> extends BaseNormalActivity<ActSortBinding> {
    protected List<T> dataList;
    private BaseItemDraggableAdapter<T, BaseViewHolder> mAdapter;

    protected abstract void convertView(BaseViewHolder helper, T item);

    protected abstract List<T> getItemList();

    protected abstract int itemLayout();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_sort;
    }

    protected abstract void saveData();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.sort));
        setBackString(getString(R.string.cancel));
        setEditString(getString(R.string.finish));
        setUpData();
        BaseItemDraggableAdapter<T, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<T, BaseViewHolder>(itemLayout(), this.dataList) { // from class: com.ltech.smarthome.base.BaseSortActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            protected void convert(BaseViewHolder holder, T item) {
                BaseSortActivity.this.convertView(holder, item);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.bindToRecyclerView(((ActSortBinding) this.mViewBinding).layoutLoad);
        ((ActSortBinding) this.mViewBinding).layoutLoad.setHasFixedSize(true);
        ((ActSortBinding) this.mViewBinding).layoutLoad.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(this.mAdapter));
        itemTouchHelper.attachToRecyclerView(((ActSortBinding) this.mViewBinding).layoutLoad);
        this.mAdapter.enableDragItem(itemTouchHelper);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        saveData();
    }

    protected void setUpData() {
        this.dataList = getItemList();
    }

    protected void setDataList(List<T> list) {
        this.dataList.clear();
        this.dataList.addAll(list);
        this.mAdapter.replaceData(list);
    }
}