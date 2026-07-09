package com.ltech.smarthome.base;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect2Binding;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseList2Activity<T> extends BaseNormalActivity<ActSelect2Binding> {
    protected List<T> dataList;
    protected BaseItemDraggableAdapter<T, BaseViewHolder> mAdapter;

    protected abstract void convertView(BaseViewHolder helper, T item);

    protected abstract List<T> getList();

    protected abstract int itemLayout();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select2;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setUpData();
        BaseItemDraggableAdapter<T, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<T, BaseViewHolder>(itemLayout(), this.dataList) { // from class: com.ltech.smarthome.base.BaseList2Activity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            protected void convert(BaseViewHolder helper, T item) {
                BaseList2Activity.this.convertView(helper, item);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.bindToRecyclerView(((ActSelect2Binding) this.mViewBinding).rvContent);
        ((ActSelect2Binding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelect2Binding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelect2Binding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    protected void setUpData() {
        this.dataList = getList();
    }

    protected void setDataList(List<T> list) {
        this.dataList = list;
        this.mAdapter.setNewData(list);
    }
}