package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.trig.ActTrigToBle;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;

/* loaded from: classes4.dex */
public class DryToBleItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_scene;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 18;
    }

    public DryToBleItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(BaseViewHolder helper, Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, !data.getDeviceState().isOnline());
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        int i = 3;
        if (data.getParam(DryContactBleParam.class) != null) {
            int subType = ((DryContactBleParam) data.getParam(DryContactBleParam.class)).getSubType();
            if (subType == 0) {
                ((DryContactBleParam) data.getParam(DryContactBleParam.class)).setSubType(3);
            } else {
                i = subType;
            }
        }
        this.viewModel.navigation(NavUtils.destination(ActTrigToBle.class).withLong(Constants.PLACE_ID, data.getPlaceId()).withInt(Constants.SUB_TYPE, i).withLong(Constants.CONTROL_ID, data.getId()));
    }
}