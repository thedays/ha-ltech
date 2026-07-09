package com.ltech.smarthome.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class MultiTypeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, MultiItemEntity item) {
    }

    protected abstract int[] itemLayout();

    public MultiTypeAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, itemLayout()[0]);
        addItemType(1, itemLayout()[1]);
    }
}