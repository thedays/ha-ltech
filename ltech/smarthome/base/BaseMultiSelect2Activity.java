package com.ltech.smarthome.base;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseMultiSelect2Activity<T> extends BaseList2Activity<T> {
    protected boolean[] selectArray;

    protected void onItemClick(int position) {
    }

    protected abstract void save();

    @Override // com.ltech.smarthome.base.BaseList2Activity
    protected void setUpData() {
        super.setUpData();
        this.selectArray = new boolean[this.dataList.size()];
    }

    @Override // com.ltech.smarthome.base.BaseList2Activity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.base.BaseMultiSelect2Activity$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BaseMultiSelect2Activity.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
        onItemClick(i);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        save();
    }

    @Override // com.ltech.smarthome.base.BaseList2Activity
    protected void setDataList(List<T> list) {
        this.selectArray = new boolean[list.size()];
        super.setDataList(list);
    }
}