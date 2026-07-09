package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.doorsensor.ActDoorSensor;
import com.ltech.smarthome.ui.device.hsd.ActHsdSensor;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorPro;
import com.ltech.smarthome.ui.device.setting.ActSensorSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class SensorItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_sensor;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 11;
    }

    public SensorItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        if (data.getProductId().equals(ProductId.ID_BODY_SENSOR) || data.getProductId().equals(ProductId.ID_DOOR_SENSOR) || data.getProductId().equals(ProductId.ID_SENSOR_HSD)) {
            helper.setGone(R.id.appCompatTextView16, false);
        } else {
            helper.setGone(R.id.appCompatTextView16, !data.getDeviceState().isOnline());
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        NavUtils.Builder withLong;
        String productId = data.getProductId();
        productId.hashCode();
        switch (productId) {
            case "121120911474901":
            case "121061709483801":
                withLong = NavUtils.destination(ActWaveSensor.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId());
                break;
            case "4304746736451584":
                withLong = NavUtils.destination(ActHsdSensor.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId());
                break;
            case "3763962108692992":
                withLong = NavUtils.destination(ActDoorSensor.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId());
                break;
            case "120042616112401":
                withLong = NavUtils.destination(ActSensorSetting.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId());
                break;
            case "3842335314313472":
                withLong = NavUtils.destination(ActWaveSensorPro.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId());
                break;
            default:
                withLong = null;
                break;
        }
        if (withLong != null) {
            this.viewModel.navigation(withLong);
        }
    }
}