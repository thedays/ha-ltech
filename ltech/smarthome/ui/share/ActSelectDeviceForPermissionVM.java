package com.ltech.smarthome.ui.share;

import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseDeviceManageVM;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActSelectDeviceForPermissionVM extends BaseDeviceManageVM {
    public long userId;
    public List<Long> selectDeviceIdList = new ArrayList();
    public MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>(Integer.valueOf(this.selectDeviceIdList.size()));

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getItemBinding$1(ItemBinding itemBinding, final int i, final Device device) {
        itemBinding.clearExtras().set(23, R.layout.item_device_select).bindExtra(59, getPlaceName(device.getFloorId(), device.getRoomId())).bindExtra(67, Boolean.valueOf(this.selectDeviceIdList.contains(Long.valueOf(device.getDeviceId())))).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(device))).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.share.ActSelectDeviceForPermissionVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectDeviceForPermissionVM.this.lambda$getItemBinding$0(i, device);
            }
        }));
    }

    @Override // com.ltech.smarthome.base.BaseDeviceManageVM
    protected ItemBinding<Device> getItemBinding() {
        return ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.ui.share.ActSelectDeviceForPermissionVM$$ExternalSyntheticLambda1
            @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
            public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
                ActSelectDeviceForPermissionVM.this.lambda$getItemBinding$1(itemBinding, i, (Device) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseDeviceManageVM
    /* renamed from: clickItem */
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