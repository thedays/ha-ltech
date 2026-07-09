package com.ltech.smarthome.ui.device.super_panel;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseDeviceManageVM;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelDeviceVM extends BaseDeviceManageVM {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getItemBinding$1(ItemBinding itemBinding, final int i, final Device device) {
        itemBinding.clearExtras().set(23, R.layout.item_device_select).bindExtra(59, getPlaceName(device.getFloorId(), device.getRoomId())).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(device))).bindExtra(67, Boolean.valueOf(this.selectDeviceIdList.contains(Long.valueOf(device.getDeviceId())))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelDeviceVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectSuperPanelDeviceVM.this.lambda$getItemBinding$0(i, device);
            }
        }));
    }

    @Override // com.ltech.smarthome.base.BaseDeviceManageVM
    public ItemBinding<Device> getItemBinding() {
        return ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelDeviceVM$$ExternalSyntheticLambda0
            @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
            public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
                ActSelectSuperPanelDeviceVM.this.lambda$getItemBinding$1(itemBinding, i, (Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseDeviceManageVM
    /* renamed from: clickItem, reason: merged with bridge method [inline-methods] */
    public void lambda$getItemBinding$0(int position, Device device) {
        if (!this.selectDeviceIdList.contains(Long.valueOf(device.getDeviceId()))) {
            this.selectDeviceIdList.add(Long.valueOf(device.getDeviceId()));
        } else {
            this.selectDeviceIdList.remove(Long.valueOf(device.getDeviceId()));
        }
        this.mDeviceList.set(position, device);
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectDeviceIdList.size()));
    }
}