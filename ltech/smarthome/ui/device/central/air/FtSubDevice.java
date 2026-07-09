package com.ltech.smarthome.ui.device.central.air;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtSubDeviceBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGateway;
import com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM;
import com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat;
import com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.smart.message.utils.LHomeLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtSubDevice extends BaseNormalFragment<FtSubDeviceBinding> {
    public static final int TYPE_CENTRAL_AIR = 1;
    public static final int TYPE_FLOOR_HEAT = 3;
    public static final int TYPE_NEW_AIR = 2;
    private MultipleItemRvAdapter<Device, BaseViewHolder> deviceAdapter;
    public int deviceType;
    private ActCentralAirProGatewayVM mViewModel;
    private long placeId;
    private List<Device> subDeviceList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_sub_device;
    }

    public static FtSubDevice newInstance(int deviceType) {
        FtSubDevice ftSubDevice = new FtSubDevice();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DEVICE_TYPE, deviceType);
        ftSubDevice.setArguments(bundle);
        return ftSubDevice;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.deviceType = arguments.getInt(Constants.DEVICE_TYPE, 0);
        }
        ActCentralAirProGatewayVM actCentralAirProGatewayVM = (ActCentralAirProGatewayVM) new ViewModelProvider(getActivity()).get(ActCentralAirProGatewayVM.class);
        this.mViewModel = actCentralAirProGatewayVM;
        this.placeId = actCentralAirProGatewayVM.placeId;
        getData();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        initRvData();
    }

    private void initRvData() {
        MultipleItemRvAdapter<Device, BaseViewHolder> multipleItemRvAdapter = new MultipleItemRvAdapter<Device, BaseViewHolder>(this.subDeviceList) { // from class: com.ltech.smarthome.ui.device.central.air.FtSubDevice.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public int getViewType(Device centralAirSubDeviceItem) {
                return FtSubDevice.this.deviceType;
            }

            @Override // com.chad.library.adapter.base.MultipleItemRvAdapter
            public void registerItemProvider() {
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Device, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.central.air.FtSubDevice.1.1
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.act_central_air_sub_item;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 1;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Device content, int position) {
                        helper.setText(R.id.tv_name, content.getDeviceName()).setText(R.id.tv_position, content.getFloorName() + content.getRoomName());
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Device data, int position) {
                        NavUtils.destination(ActCentralAc.class).withLong(Constants.CONTROL_ID, data.getId()).withBoolean(Constants.CENTRAL_GATEWAY, true).withDefaultRequestCode().navigation(FtSubDevice.this.getActivity());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Device, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.central.air.FtSubDevice.1.2
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.act_central_air_sub_item;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 2;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Device content, int position) {
                        helper.setText(R.id.tv_name, content.getDeviceName()).setBackgroundRes(R.id.iv_ic, R.mipmap.ic_device_newair).setText(R.id.tv_position, content.getFloorName() + content.getRoomName());
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Device data, int position) {
                        NavUtils.destination(ActCentralFreshAir.class).withLong(Constants.CONTROL_ID, data.getId()).withBoolean(Constants.CENTRAL_GATEWAY, true).withDefaultRequestCode().navigation(FtSubDevice.this.getActivity());
                    }
                });
                this.mProviderDelegate.registerProvider(new BaseItemProvider<Device, BaseViewHolder>() { // from class: com.ltech.smarthome.ui.device.central.air.FtSubDevice.1.3
                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int layout() {
                        return R.layout.act_central_air_sub_item;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public int viewType() {
                        return 3;
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void convert(BaseViewHolder helper, Device content, int position) {
                        helper.setText(R.id.tv_name, content.getDeviceName()).setBackgroundRes(R.id.iv_ic, R.mipmap.ic_device_floor_heat).setText(R.id.tv_position, content.getFloorName() + content.getRoomName());
                    }

                    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
                    public void onClick(BaseViewHolder helper, Device data, int position) {
                        NavUtils.destination(ActCentralFloorHeat.class).withLong(Constants.CONTROL_ID, data.getId()).withBoolean(Constants.CENTRAL_GATEWAY, true).withDefaultRequestCode().navigation(FtSubDevice.this.getActivity());
                    }
                });
            }
        };
        this.deviceAdapter = multipleItemRvAdapter;
        multipleItemRvAdapter.finishInitialize();
        this.deviceAdapter.bindToRecyclerView(((FtSubDeviceBinding) this.mViewBinding).rvDevice);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(1);
        ((FtSubDeviceBinding) this.mViewBinding).rvDevice.setLayoutManager(linearLayoutManager);
        ((FtSubDeviceBinding) this.mViewBinding).rvDevice.setHasFixedSize(true);
    }

    public void getData() {
        this.subDeviceList.clear();
        Injection.repo().device().getDeviceList(this, this.placeId, -1L, -1L).data();
        for (Device device : Injection.repo().device().getDeviceListCache().getValue()) {
            if (device.isSubDevice()) {
                String extParam = device.getExtParam();
                int i = this.deviceType;
                if (i == 1) {
                    if (extParam != null && extParam.contains("\"ACType\":1")) {
                        this.subDeviceList.add(device);
                    }
                } else if (i == 2) {
                    if (extParam != null && extParam.contains("\"ACType\":2")) {
                        this.subDeviceList.add(device);
                    }
                } else if (i == 3 && extParam != null && extParam.contains("\"ACType\":3")) {
                    this.subDeviceList.add(device);
                }
            }
        }
        ActCentralAirProGatewayVM actCentralAirProGatewayVM = this.mViewModel;
        if (actCentralAirProGatewayVM != null && actCentralAirProGatewayVM.controlObject.getValue() != null) {
            int i2 = 0;
            while (i2 < this.subDeviceList.size()) {
                if (this.subDeviceList.get(i2).getMacdeviceid() != this.mViewModel.controlObject.getValue().getDeviceId()) {
                    this.subDeviceList.remove(i2);
                    i2--;
                }
                i2++;
            }
            ((FtSubDeviceBinding) this.mViewBinding).tvCount.setText(((Object) getText(R.string.sub_device)) + String.valueOf(this.subDeviceList.size()));
        }
        LHomeLog.i(ActCentralAirProGateway.class, "size=" + this.subDeviceList.size());
        MultipleItemRvAdapter<Device, BaseViewHolder> multipleItemRvAdapter = this.deviceAdapter;
        if (multipleItemRvAdapter != null) {
            multipleItemRvAdapter.notifyDataSetChanged();
        }
    }
}