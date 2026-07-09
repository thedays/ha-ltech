package com.ltech.smarthome.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.DraggableController;
import java.util.List;

/* loaded from: classes3.dex */
public class MultipleDragItemRvAdapter<Role, V extends BaseViewHolder> extends com.chad.library.adapter.base.MultipleItemRvAdapter<Role, V> {
    private DraggableController mDraggableController;

    @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
    protected int getViewType(Role role) {
        return 0;
    }

    @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
    public void registerItemProvider() {
    }

    public MultipleDragItemRvAdapter(List data) {
        super(data);
        this.mDraggableController = new DraggableController(this);
    }

    @Override // com.chad.library.adapter.base.MultipleItemRvAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    protected void convert(V helper, Role item) {
        this.mDraggableController.initView(helper);
        super.convert(helper, item);
    }

    public DraggableController getDraggableController() {
        return this.mDraggableController;
    }
}