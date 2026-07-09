package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensor;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorPro;
import com.ltech.smarthome.ui.device.setting.ActSensorSetting;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class SensorGroupItemProvider extends BaseDeviceProvider<Group> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_group_wave_sensor;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 20;
    }

    public SensorGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Group data, int position) {
        NavUtils.Builder withBoolean;
        this.viewModel.navigation(NavUtils.destination(ActSensorSetting.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()));
        int lightColorType = ProductRepository.getLightColorType((Object) data);
        if (lightColorType == 13 || lightColorType == 15) {
            withBoolean = NavUtils.destination(ActWaveSensor.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withBoolean(Constants.GROUP_CONTROL, true);
        } else {
            withBoolean = lightColorType == 25 ? NavUtils.destination(ActWaveSensorPro.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withLong(Constants.CONTROL_ID, data.getId()).withBoolean(Constants.GROUP_CONTROL, true) : null;
        }
        if (withBoolean != null) {
            this.viewModel.navigation(withBoolean);
        }
    }
}