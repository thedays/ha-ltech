package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanel;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb8;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class EurPanelProItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_mesh_gateway;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 27;
    }

    public EurPanelProItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, !data.getDeviceState().isOnline()).setText(R.id.appCompatTextView16, ActivityUtils.getTopActivity().getString(R.string.offline)).setBackgroundRes(R.id.appCompatTextView16, R.drawable.shape_light_gray_bt);
        if (isNormalMode()) {
            helper.setVisible(R.id.iv_device_more, false);
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        NavUtils.Builder destination;
        if (data.getProductId().equals(ProductId.ID_EUR_PANEL_EB6)) {
            destination = NavUtils.destination(ActEurPanelEb6.class);
        } else if (data.getProductId().equals(ProductId.ID_EUR_PANEL_EB8) || data.getProductId().equals(ProductId.ID_AS_PANEL_UB8)) {
            destination = NavUtils.destination(ActEurPanelEb8.class);
        } else {
            destination = NavUtils.destination(ActEurPanel.class);
        }
        destination.withLong(Constants.PLACE_ID, data.getPlaceId()).withInt(Constants.LIGHT_TYPE, EurHelper.convertType(data)).withLong(Constants.CONTROL_ID, data.getId()).withString(Constants.PRODUCT_ID, data.getProductId());
        this.viewModel.navigation(destination);
    }
}