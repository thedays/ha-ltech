package com.ltech.smarthome.base;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelectDivideBinding;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseSelectDivideListActivity<T> extends BaseNormalActivity<ActSelectDivideBinding> {
    protected BaseQuickAdapter<T, BaseViewHolder> mAddedAdapter;
    protected BaseQuickAdapter<T, BaseViewHolder> mNotAddAdapter;
    protected List<T> notSelectDataList;
    protected List<T> selectDataList;

    protected abstract void convertNotSelectDataView(BaseViewHolder helper, T item);

    protected abstract void convertSelectedDataView(BaseViewHolder helper, T item);

    protected abstract List<T> getNotSelectList();

    protected abstract List<T> getSelectList();

    protected abstract int itemLayout();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_divide;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setUpData();
        this.mNotAddAdapter = new BaseQuickAdapter<T, BaseViewHolder>(itemLayout(), this.selectDataList) { // from class: com.ltech.smarthome.base.BaseSelectDivideListActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            protected void convert(BaseViewHolder helper, T item) {
                BaseSelectDivideListActivity.this.convertNotSelectDataView(helper, item);
            }
        };
        this.mAddedAdapter = new BaseQuickAdapter<T, BaseViewHolder>(itemLayout(), this.notSelectDataList) { // from class: com.ltech.smarthome.base.BaseSelectDivideListActivity.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            protected void convert(BaseViewHolder helper, T item) {
                BaseSelectDivideListActivity.this.convertSelectedDataView(helper, item);
            }
        };
        this.mNotAddAdapter.bindToRecyclerView(((ActSelectDivideBinding) this.mViewBinding).rvNotSelectContent);
        ((ActSelectDivideBinding) this.mViewBinding).rvNotSelectContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectDivideBinding) this.mViewBinding).rvNotSelectContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectDivideBinding) this.mViewBinding).rvNotSelectContent.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mAddedAdapter.bindToRecyclerView(((ActSelectDivideBinding) this.mViewBinding).rvSelectContent);
        ((ActSelectDivideBinding) this.mViewBinding).rvSelectContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectDivideBinding) this.mViewBinding).rvSelectContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectDivideBinding) this.mViewBinding).rvSelectContent.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    protected void setUpData() {
        this.notSelectDataList = getNotSelectList();
        this.selectDataList = getSelectList();
    }

    protected void setNotSelectDataList(List<T> list) {
        this.notSelectDataList = list;
        this.mNotAddAdapter.setNewData(list);
    }

    protected void setSelectDataList(List<T> list) {
        this.selectDataList = list;
        this.mAddedAdapter.setNewData(list);
    }
}