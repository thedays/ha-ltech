package com.ltech.smarthome.model.expand;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/* loaded from: classes4.dex */
public class LightGroupSubCountExpandableItem implements MultiItemEntity {
    private int count;

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return 2;
    }

    public LightGroupSubCountExpandableItem(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }
}