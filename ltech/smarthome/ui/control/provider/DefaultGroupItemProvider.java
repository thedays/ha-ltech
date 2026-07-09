package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanel;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class DefaultGroupItemProvider extends BaseDeviceProvider<Group> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_group_wave_sensor;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 28;
    }

    public DefaultGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, Group data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        int lightColorType = ProductRepository.getLightColorType((Object) data);
        if (lightColorType == 27 || lightColorType == 28 || lightColorType == 29 || lightColorType == 30) {
            helper.setVisible(R.id.iv_device_more, false);
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Group data, int position) {
        int lightColorType = ProductRepository.getLightColorType((Object) data);
        NavUtils.Builder withString = (lightColorType == 22 || lightColorType == 23 || lightColorType == 24) ? NavUtils.destination(ActEurPanel.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withInt(Constants.LIGHT_TYPE, EurHelper.convertType(data)).withLong(Constants.CONTROL_ID, data.getId()).withBoolean(Constants.GROUP_CONTROL, true).withString(Constants.PRODUCT_ID, EurHelper.getEurProductId(data)) : (lightColorType == 27 || lightColorType == 28 || lightColorType == 29 || lightColorType == 30) ? NavUtils.destination(ActEurPanel.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withInt(Constants.LIGHT_TYPE, AsHelper.convertType(data)).withLong(Constants.CONTROL_ID, data.getId()).withBoolean(Constants.GROUP_CONTROL, true).withString(Constants.PRODUCT_ID, AsHelper.getAsProductId(data)) : null;
        if (withString != null) {
            this.viewModel.navigation(withString);
        }
    }
}