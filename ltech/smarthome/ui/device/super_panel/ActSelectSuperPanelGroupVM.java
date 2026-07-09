package com.ltech.smarthome.ui.device.super_panel;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseGroupManageVM;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelGroupVM extends BaseGroupManageVM {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getItemBinding$1(ItemBinding itemBinding, final int i, final Group group) {
        itemBinding.clearExtras().set(32, R.layout.item_group_select).bindExtra(59, getPlaceName(group.getFloorId(), group.getRoomId())).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(group))).bindExtra(67, Boolean.valueOf(this.selectGroupIdList.contains(Long.valueOf(group.getGroupId())))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelGroupVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectSuperPanelGroupVM.this.lambda$getItemBinding$0(i, group);
            }
        }));
    }

    @Override // com.ltech.smarthome.base.BaseGroupManageVM
    public ItemBinding<Group> getItemBinding() {
        return ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelGroupVM$$ExternalSyntheticLambda0
            @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
            public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
                ActSelectSuperPanelGroupVM.this.lambda$getItemBinding$1(itemBinding, i, (Group) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseGroupManageVM
    /* renamed from: clickItem, reason: merged with bridge method [inline-methods] */
    public void lambda$getItemBinding$0(int position, Group device) {
        if (!this.selectGroupIdList.contains(Long.valueOf(device.getGroupId()))) {
            this.selectGroupIdList.add(Long.valueOf(device.getGroupId()));
        } else {
            this.selectGroupIdList.remove(Long.valueOf(device.getGroupId()));
        }
        this.mGroupList.set(position, device);
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectGroupIdList.size()));
    }
}