package com.ltech.smarthome.ui.control.binding;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.ActAsPanel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class AsPanelItemBinding extends BaseDeviceItemBinding {
    @Override // com.ltech.smarthome.ui.control.binding.IBindItem
    public void bindItem(final BaseViewModel viewModel, ItemBinding itemBinding, int position, final Device item) {
        itemBinding.set(23, R.layout.item_device_as_panel).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(item))).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.binding.AsPanelItemBinding$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                AsPanelItemBinding.this.lambda$bindItem$0(item, viewModel, (View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindItem$0(Device device, BaseViewModel baseViewModel, View view) {
        int id = view.getId();
        if (id == R.id.layout_item_bg) {
            baseViewModel.navigation(NavUtils.destination(ActAsPanel.class).withLong(Constants.PLACE_ID, device.getPlaceId()).withLong(Constants.CONTROL_ID, device.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) device)));
        } else {
            if (id != R.id.v_favorite) {
                return;
            }
            setDeviceFavourite(device);
        }
    }
}