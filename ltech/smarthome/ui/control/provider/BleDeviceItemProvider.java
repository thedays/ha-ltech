package com.ltech.smarthome.ui.control.provider;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;

/* loaded from: classes4.dex */
public class BleDeviceItemProvider extends IrDeviceItemProvider {
    @Override // com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_ble;
    }

    @Override // com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 4;
    }

    public BleDeviceItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    public BleDeviceItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
        super(activity, recyclerView, viewModel);
    }
}