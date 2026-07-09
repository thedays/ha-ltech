package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.aspanel.ActAsPanel;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class AsPanelItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_as_panel;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 0;
    }

    public AsPanelItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, !data.getDeviceState().isOnline());
        helper.setVisible(R.id.iv_device_more, false);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        if (AsHelper.isNewUb(data)) {
            this.viewModel.navigation(NavUtils.destination(ActEurPanel.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withString(Constants.PRODUCT_ID, data.getProductId()).withInt(Constants.LIGHT_TYPE, AsHelper.convertType(data)));
        } else {
            this.viewModel.navigation(NavUtils.destination(ActAsPanel.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) data)));
        }
    }
}