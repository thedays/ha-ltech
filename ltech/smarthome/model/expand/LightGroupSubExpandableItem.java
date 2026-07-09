package com.ltech.smarthome.model.expand;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;

/* loaded from: classes4.dex */
public class LightGroupSubExpandableItem implements MultiItemEntity {
    private Role item;
    private DataChangeListener lsn;

    public interface DataChangeListener {
        void change(Group group);
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return 1;
    }

    public LightGroupSubExpandableItem(Role item) {
        this.item = item;
    }

    public String getName() {
        Role role = this.item;
        if (role != null) {
            return role.getName();
        }
        return "";
    }

    public Role getItem() {
        return this.item;
    }

    public void change(Group group) {
        DataChangeListener dataChangeListener = this.lsn;
        if (dataChangeListener != null) {
            dataChangeListener.change(group);
        }
    }

    public void setOnDataChangeListener(DataChangeListener listener) {
        this.lsn = listener;
    }
}