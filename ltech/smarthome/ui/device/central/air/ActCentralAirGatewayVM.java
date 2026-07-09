package com.ltech.smarthome.ui.device.central.air;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.ISendCallback;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.ConfigDeviceBean;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.base.BaseCmd;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.productBle.CmdBleFactory;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActCentralAirGatewayVM extends BaseViewModel {
    public long controlId;
    public String floorName;
    public long placeId;
    public String roomName;
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public MediatorLiveData<List<Device>> subDeviceList = new MediatorLiveData<>();
    public SingleLiveEvent<Void> searchDevice = new SingleLiveEvent<>();
    List<Device> saveDevice = new ArrayList();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGatewayVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActCentralAirGatewayVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.bt_search || id == R.id.iv_search_add) {
            this.searchDevice.call();
        }
    }

    public void saveSearchSubDevice(CentralAirSubDeviceParam subDeviceParam) {
        ((ObservableSubscribeProxy) Injection.net().addDevice(centralAirProductSubData(subDeviceParam)).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGatewayVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCentralAirGatewayVM.this.lambda$saveSearchSubDevice$1((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer(this) { // from class: com.ltech.smarthome.ui.device.central.air.ActCentralAirGatewayVM.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSearchSubDevice$1(AddDeviceResponse addDeviceResponse) throws Exception {
        LHomeLog.i(getClass(), "addSubDeviceOk=" + addDeviceResponse.toString());
        this.saveDevice.add(addSubDevice(addDeviceResponse));
        this.subDeviceList.setValue(this.saveDevice);
    }

    public void getSubDevice(int framIndex, ISendCallback iSendCallback) {
        BleParam bleParam = (BleParam) this.controlObject.getValue().getParam(BleParam.class);
        BaseCmd queryCentralAirSubDevice = CmdBleFactory.queryCentralAirSubDevice(1, framIndex);
        Injection.mesh().sendVendorModelMessage(bleParam.getUnicastAddress(), queryCentralAirSubDevice.getFunCode(), queryCentralAirSubDevice.value(new Object[0]), false, iSendCallback);
    }

    public String centralAirProductSubData(CentralAirSubDeviceParam subDeviceParam) {
        ConfigHelper.instance().codeLibrary = CodeLibraryUtil.generateAcCentralCodeLibrary(subDeviceParam.getUnicastAddress(), subDeviceParam.getOutAddr(), subDeviceParam.getInAddr());
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).placeid(this.placeId).floorid(this.controlObject.getValue().getFloorId()).roomid(this.controlObject.getValue().getRoomId()).param(GsonUtils.toJson(subDeviceParam)).mac(this.controlObject.getValue().getWifiMac().replaceAll(Constants.COLON_SEPARATOR, "") + getSubDeviceString(subDeviceParam)).maccode("").devicename(subDeviceParam.getName()).macfalg(2).codeLibrary(ConfigHelper.instance().codeLibrary).aiPuductType(ProductId.IR_SUB_TYPE_AC).subProductTypeName(ProductId.IR_SUB_TYPE_AC).subManufacturerName("LTECH").subProductName(subDeviceParam.getName()).macdeviceid(this.controlObject.getValue().getDeviceId()).paramext("{\"ACType\":1}").build().getConfigJson();
    }

    private String getSubDeviceString(CentralAirSubDeviceParam subDeviceParam) {
        return ProductId.SPLIT + StringUtils.addZeroForNum(Integer.toHexString(subDeviceParam.outAddr), 2) + StringUtils.addZeroForNum(Integer.toHexString(subDeviceParam.inAddr), 2);
    }

    private Device addSubDevice(AddDeviceResponse response) {
        Device device = new Device();
        device.setPlaceId(this.placeId);
        device.setFloorId(this.controlObject.getValue().getFloorId());
        device.setRoomId(this.controlObject.getValue().getRoomId());
        device.setRoomName(this.roomName);
        device.setFloorName(this.floorName);
        device.setProductId(ProductId.CENTRAL_AIR_SUB_DEVICE);
        LHomeLog.i(getClass(), "response 2 =" + response.toString());
        device.setDeviceId(response.getDeviceid());
        device.setPlatFormDeviceId(response.getPlatformdeviceid());
        device.setOnlineFlag(1);
        device.setWifiMac(response.getMac());
        device.setDeviceName(response.getDevicename());
        device.setMacfalg(response.getMacfalg());
        device.setMacdeviceid(response.getMacdeviceid());
        device.setParam(response.getParam());
        device.setDeviceState(new DeviceState());
        device.setExtParam("{\"ACType\":1}");
        LHomeLog.i(getClass(), "response.getMac() =" + response.getMac());
        if (1 == ProductRepository.getMacFlag(response.getProductid())) {
            Injection.repo().device().removeDeviceByMac(this.placeId, response.getMac());
        }
        Injection.repo().device().saveDevice(device);
        return device;
    }
}