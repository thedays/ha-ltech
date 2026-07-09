package com.ltech.smarthome.base;

import android.text.Editable;
import android.text.TextWatcher;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelectBinding;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseListActivity<T> extends BaseNormalActivity<ActSelectBinding> implements TextWatcher {
    protected BaseItemDraggableAdapter<T, BaseViewHolder> mAdapter;
    protected List<T> dataList = new ArrayList();
    protected MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>(0);
    protected boolean selectAll = false;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    protected abstract void convertView(BaseViewHolder helper, T item);

    protected abstract List<T> getList();

    protected abstract int itemLayout();

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    protected void search(String key) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setUpData();
        BaseItemDraggableAdapter<T, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<T, BaseViewHolder>(itemLayout(), this.dataList) { // from class: com.ltech.smarthome.base.BaseListActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            protected void convert(BaseViewHolder helper, T item) {
                BaseListActivity.this.convertView(helper, item);
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        baseItemDraggableAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        ((ActSelectBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
    }

    protected void setUpData() {
        if (this.dataList == null) {
            this.dataList = new ArrayList();
        }
        this.dataList.addAll(getList());
    }

    protected void setDataList(List<T> list) {
        if (this.dataList == null) {
            this.dataList = new ArrayList();
        }
        this.dataList.clear();
        this.dataList.addAll(list);
        this.mAdapter.replaceData(list);
    }

    protected void changeSelectCount(int selectCount) {
        if (this.selectCountLiveData.getValue().intValue() == this.mAdapter.getData().size()) {
            setEditString(getString(R.string.app_str_cancel_select_all));
            this.selectAll = true;
        } else {
            setEditString(getString(R.string.app_str_select_all));
            this.selectAll = false;
        }
    }

    protected void setChooseAll(boolean select) {
        this.selectCountLiveData.setValue(Integer.valueOf(select ? this.mAdapter.getData().size() : 0));
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        search(editable.toString());
    }
}