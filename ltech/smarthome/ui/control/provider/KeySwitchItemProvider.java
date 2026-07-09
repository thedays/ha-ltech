package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.r8.ActRc4s;
import com.ltech.smarthome.ui.device.remote_controller.ActKeySwitch;
import com.ltech.smarthome.ui.device.remote_controller.ActRemoteBattery;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class KeySwitchItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_key_switch;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 8;
    }

    public KeySwitchItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, false);
        helper.setVisible(R.id.iv_device_more, !ProductRepository.isRcB(data.getProductId()));
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        if (ProductId.ID_RC4S.equals(data.getProductId())) {
            this.viewModel.navigation(NavUtils.destination(ActRc4s.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withString(Constants.PRODUCT_ID, data.getProductId()));
        } else {
            this.viewModel.navigation(NavUtils.destination(ProductRepository.isRcB(data.getProductId()) ? ActRemoteBattery.class : ActKeySwitch.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withString(Constants.PRODUCT_ID, data.getProductId()));
        }
    }
}