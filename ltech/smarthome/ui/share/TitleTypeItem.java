package com.ltech.smarthome.ui.share;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/* loaded from: classes4.dex */
public class TitleTypeItem implements MultiItemEntity {
    private long titleId;
    private String titleName;

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return 0;
    }

    public TitleTypeItem(String titleName) {
        this.titleName = titleName;
    }

    public TitleTypeItem(String titleName, long titleId) {
        this.titleName = titleName;
        this.titleId = titleId;
    }

    public String getTitleName() {
        return this.titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public long getTitleId() {
        return this.titleId;
    }

    public void setTitleId(long titleId) {
        this.titleId = titleId;
    }
}