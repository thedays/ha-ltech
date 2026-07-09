package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.cg485.ActCg485;
import com.ltech.smarthome.ui.device.cg485.ActCg485Device;
import com.ltech.smarthome.ui.device.gqpro.ActGqPro;
import com.ltech.smarthome.ui.device.gqx.ActGqx;
import com.ltech.smarthome.ui.device.homekit.ActHomeKit;
import com.ltech.smarthome.ui.device.homekit.ActHomeKitOld;
import com.ltech.smarthome.ui.device.rs8.ActRs8;
import com.ltech.smarthome.ui.device.sonos.ActSonosPlayControl;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class DefaultItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_mesh_gateway;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 26;
    }

    public DefaultItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
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
        if (ProductId.ID_RS485_BLE.equals(data.getProductId())) {
            this.viewModel.navigation(NavUtils.destination(ActCg485.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()));
            return;
        }
        if (ProductId.CG485_SUB_DEVICE.equals(data.getProductId())) {
            this.viewModel.navigation(NavUtils.destination(ActCg485Device.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()));
            return;
        }
        if (ProductId.ID_HOME_KIT.equals(data.getProductId())) {
            this.viewModel.navigation(NavUtils.destination(((BleParam) data.getParam(BleParam.class)).getBleType() == 270 ? ActHomeKit.class : ActHomeKitOld.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()));
            return;
        }
        if (ProductId.ID_SMART_PANEL_GQ.equals(data.getProductId()) || ProductId.ID_SMART_PANEL_GQX.equals(data.getProductId())) {
            this.viewModel.navigation(NavUtils.destination(ActGqx.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) data)).withString(Constants.PRODUCT_ID, data.getProductId()));
            return;
        }
        if (ProductId.ID_SMART_PANEL_GQ_PRO.equals(data.getProductId())) {
            this.viewModel.navigation(NavUtils.destination(ActGqPro.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) data)).withString(Constants.PRODUCT_ID, data.getProductId()));
        } else if (ProductId.ID_WIFI_SONOS.equals(data.getProductId())) {
            NavUtils.destination(ActSonosPlayControl.class).withLong(Constants.CONTROL_ID, data.getId()).withLong("device_id", data.getDeviceId()).withString("device_name", data.getDeviceName()).navigation(ActivityUtils.getTopActivity());
        } else if (ProductId.ID_BLE_RS8.equals(data.getProductId())) {
            this.viewModel.navigation(NavUtils.destination(ActRs8.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()));
        }
    }
}