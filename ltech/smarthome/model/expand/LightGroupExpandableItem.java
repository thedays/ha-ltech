package com.ltech.smarthome.model.expand;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.expand.LightGroupSubExpandableItem;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class LightGroupExpandableItem extends AbstractExpandableItem<LightGroupSubExpandableItem> implements MultiItemEntity {
    private Role item;

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return 0;
    }

    @Override // com.chad.library.adapter.base.entity.IExpandable
    public int getLevel() {
        return 0;
    }

    public LightGroupExpandableItem(Role item) {
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

    public void setItem(Role item) {
        this.item = item;
    }

    public void setOn(boolean isOn) {
        Role role = this.item;
        if (role instanceof Group) {
            ((Group) role).getGroupState().setOn(isOn);
        } else if (role instanceof Device) {
            role.getDeviceState().setOn(isOn);
        }
    }

    public void subItemChange(final LightGroupSubExpandableItem.DataChangeListener listener) {
        Iterator<LightGroupSubExpandableItem> it = getSubItems().iterator();
        while (it.hasNext()) {
            it.next().setOnDataChangeListener(new LightGroupSubExpandableItem.DataChangeListener(this) { // from class: com.ltech.smarthome.model.expand.LightGroupExpandableItem.1
                @Override // com.ltech.smarthome.model.expand.LightGroupSubExpandableItem.DataChangeListener
                public void change(Group group) {
                    LightGroupSubExpandableItem.DataChangeListener dataChangeListener = listener;
                    if (dataChangeListener != null) {
                        dataChangeListener.change(group);
                    }
                }
            });
        }
    }
}