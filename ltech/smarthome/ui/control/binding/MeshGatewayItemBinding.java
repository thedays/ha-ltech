package com.ltech.smarthome.ui.control.binding;

import android.view.View;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGateway;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class MeshGatewayItemBinding extends BaseDeviceItemBinding {
    @Override // com.ltech.smarthome.ui.control.binding.IBindItem
    public void bindItem(final BaseViewModel viewModel, ItemBinding itemBinding, int position, final Device item) {
        String string;
        ItemBinding bindExtra = itemBinding.set(23, R.layout.item_device_mesh_gateway).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(item)));
        if (item.hasIotFun()) {
            string = item.isOnline() ? "" : ActivityUtils.getTopActivity().getString(R.string.offline);
        } else {
            string = ActivityUtils.getTopActivity().getString(R.string.device_no_network);
        }
        bindExtra.bindExtra(52, string).bindExtra(53, Integer.valueOf(!item.hasIotFun() ? R.drawable.shape_white_red_bt : R.drawable.shape_light_gray_bt)).bindExtra(54, Integer.valueOf(ContextCompat.getColor(ActivityUtils.getTopActivity(), !item.hasIotFun() ? R.color.color_text_red : R.color.color_text_gray))).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.binding.MeshGatewayItemBinding$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                MeshGatewayItemBinding.this.lambda$bindItem$0(item, viewModel, (View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindItem$0(Device device, BaseViewModel baseViewModel, View view) {
        int id = view.getId();
        if (id == R.id.layout_item_bg) {
            baseViewModel.navigation(NavUtils.destination(ActMeshGateway.class).withLong(Constants.PLACE_ID, device.getPlaceId()).withLong(Constants.CONTROL_ID, device.getId()));
        } else {
            if (id != R.id.v_favorite) {
                return;
            }
            setDeviceFavourite(device);
        }
    }
}