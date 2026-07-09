package com.ltech.smarthome.base;

import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.MultiTypeAdapter;
import com.ltech.smarthome.databinding.ActSelectMultiTypeBinding;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseMultiTypesSelectActivity<T extends MultiItemEntity> extends BaseNormalActivity<ActSelectMultiTypeBinding> {
    protected List<MultiItemEntity> dataList;
    protected boolean initSelectAll;
    protected MultiTypeAdapter mAdapter;
    protected boolean[] selectArray;

    protected abstract void convertView(BaseViewHolder helper, MultiItemEntity item);

    protected abstract List<T> getList();

    protected abstract void initSelectArray();

    protected abstract int[] itemLayouts();

    protected void onItemClick(int position) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_multi_type;
    }

    protected abstract void save();

    protected void setUpData() {
        initSelectArray(this.dataList, isInitSelectAll());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter(this.dataList) { // from class: com.ltech.smarthome.base.BaseMultiTypesSelectActivity.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.adapter.MultiTypeAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, MultiItemEntity item) {
                BaseMultiTypesSelectActivity.this.convertView(helper, item);
            }

            @Override // com.ltech.smarthome.adapter.MultiTypeAdapter
            protected int[] itemLayout() {
                return BaseMultiTypesSelectActivity.this.itemLayouts();
            }
        };
        this.mAdapter = multiTypeAdapter;
        multiTypeAdapter.bindToRecyclerView(((ActSelectMultiTypeBinding) this.mViewBinding).rvContent);
        ((ActSelectMultiTypeBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectMultiTypeBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSelectMultiTypeBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.base.BaseMultiTypesSelectActivity$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseMultiTypesSelectActivity.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.mAdapter.getItemViewType(i) == 0) {
            return;
        }
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
        onItemClick(i);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        save();
    }

    protected void setDataList(List list) {
        this.dataList = list;
        initSelectArray(list, isInitSelectAll());
        this.mAdapter.setNewData(list);
    }

    protected void initSelectArray(List dataList, boolean selectAll) {
        boolean[] zArr = new boolean[dataList.size()];
        this.selectArray = zArr;
        if (selectAll) {
            Arrays.fill(zArr, true);
        } else {
            initSelectArray();
        }
    }

    public boolean isInitSelectAll() {
        return this.initSelectAll;
    }

    public void setInitSelectAll(boolean initSelectAll) {
        this.initSelectAll = initSelectAll;
    }
}