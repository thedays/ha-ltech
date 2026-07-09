package com.ltech.smarthome.ui.device.central.airpro;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.model.device_param.ExtParam;
import com.ltech.smarthome.model.device_param.FloorHeatSubDeviceParam;
import com.ltech.smarthome.model.device_param.FreshAirSubDeviceParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.ConfigDeviceBean;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.device.central.air.FtSubDevice;
import com.ltech.smarthome.ui.device.central.air.Utils;
import com.ltech.smarthome.utils.CodeLibraryUtil;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.base.IReceiveListener;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.productBle.CmdBleFactory;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActCentralAirProGatewayVM extends BaseViewModel {
    private static int SUB_DEVICE_LENGTH = 20;
    private static final int TYPE_CENTRAL_AIR = 1;
    private static final int TYPE_FLOOR_HEAT = 3;
    private static final int TYPE_NEW_AIR = 2;
    public long controlId;
    public String floorName;
    private String framModData;
    public long placeId;
    public String roomName;
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public MediatorLiveData<List<Device>> subDeviceList = new MediatorLiveData<>();
    public SingleLiveEvent<Void> searchDevice = new SingleLiveEvent<>();
    List<Device> saveDevice = new ArrayList();
    public List<FtSubDevice> subDeviceFtList = new ArrayList();
    public List<String> centralAirByteDataList = new ArrayList();
    public List<String> newAirByteDataList = new ArrayList();
    public List<String> floorHeatByteDataList = new ArrayList();
    public List<CentralAirSubDeviceParam> centralAirParamList = new ArrayList();
    public List<FreshAirSubDeviceParam> newAirParamList = new ArrayList();
    public List<FloorHeatSubDeviceParam> floorHeatParamList = new ArrayList();
    public String deviceFeature = "";
    public SingleLiveEvent<Void> startUpdateCentralAir = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> startUpdateNewAir = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> startUpdateFloorHeat = new SingleLiveEvent<>();
    public BindingCommand<View> viewClick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM$$ExternalSyntheticLambda2
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ((View) obj).getId();
        }
    });

    public void saveSearchSubDevice(CentralAirSubDeviceParam subDeviceParam) {
        ((ObservableSubscribeProxy) Injection.net().addDevice(centralAirProductSubData(subDeviceParam)).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCentralAirProGatewayVM.this.lambda$saveSearchSubDevice$1((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM.1
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActCentralAirProGatewayVM.this.dismissLoadingDialog();
                ActCentralAirProGatewayVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSearchSubDevice$1(AddDeviceResponse addDeviceResponse) throws Exception {
        LHomeLog.i(getClass(), "addSubDeviceOk=" + addDeviceResponse.toString());
        this.saveDevice.add(addSubDevice(addDeviceResponse));
        this.subDeviceList.setValue(this.saveDevice);
    }

    public void saveNewAirDevice(FreshAirSubDeviceParam freshAirSubDeviceParam) {
        ((ObservableSubscribeProxy) Injection.net().addDevice(newAirProductSubData(freshAirSubDeviceParam)).delaySubscription(200L, TimeUnit.MILLISECONDS).observeOn(Schedulers.single()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCentralAirProGatewayVM.this.lambda$saveNewAirDevice$2((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM.2
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActCentralAirProGatewayVM.this.dismissLoadingDialog();
                ActCentralAirProGatewayVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveNewAirDevice$2(AddDeviceResponse addDeviceResponse) throws Exception {
        LHomeLog.i(getClass(), "addSubDeviceOk=" + addDeviceResponse.toString());
        this.saveDevice.add(addNewAirSubDevice(addDeviceResponse));
        this.subDeviceList.setValue(this.saveDevice);
    }

    public void saveFloorHeatDevice(FloorHeatSubDeviceParam floorHeatSubDeviceParam) {
        ((ObservableSubscribeProxy) Injection.net().addDevice(floorHeatProductSubData(floorHeatSubDeviceParam)).delaySubscription(200L, TimeUnit.MILLISECONDS).observeOn(Schedulers.single()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActCentralAirProGatewayVM.this.lambda$saveFloorHeatDevice$3((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM.3
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActCentralAirProGatewayVM.this.dismissLoadingDialog();
                ActCentralAirProGatewayVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveFloorHeatDevice$3(AddDeviceResponse addDeviceResponse) throws Exception {
        LHomeLog.i(getClass(), "addSubDeviceOk=" + addDeviceResponse.toString());
        this.saveDevice.add(addFloorHeatSubDevice(addDeviceResponse));
        this.subDeviceList.setValue(this.saveDevice);
    }

    public void getSubDevice(final int framIndex) {
        MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(CmdBleFactory.getCentralAirCmd(CmdBleFactory.queryCentralAirSubDevice(1, framIndex))).control(this.controlObject.getValue()).sendTimes(1).resendTimes(0).timeOutTime(10000).intervalTime(0).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM.4
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                if (msg != null) {
                    String substring = msg.getResData().substring(16);
                    if (substring.length() < 12 || !Utils.checkData(substring)) {
                        return;
                    }
                    int intValue = Integer.valueOf(substring.substring(0, 2), 16).intValue();
                    int intValue2 = Integer.valueOf(substring.substring(2, 4), 16).intValue();
                    if (framIndex == 0) {
                        ActCentralAirProGatewayVM.this.saveSubDeviceByteData(substring.substring(12, substring.length() - 2), 1);
                    } else {
                        String substring2 = substring.substring(4, substring.length() - 2);
                        if (ActCentralAirProGatewayVM.this.framModData.length() > 0) {
                            substring2 = ActCentralAirProGatewayVM.this.framModData + substring2;
                        }
                        ActCentralAirProGatewayVM.this.saveSubDeviceByteData(substring2, 1);
                    }
                    int i = intValue2 + 1;
                    if (i != intValue) {
                        ActCentralAirProGatewayVM.this.getSubDevice(i);
                    } else {
                        ActCentralAirProGatewayVM.this.parseCentralAirData();
                    }
                }
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                ActCentralAirProGatewayVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.tip_search_timeout));
                ActCentralAirProGatewayVM.this.dismissLoadingDialog();
            }
        }).enqueue();
    }

    public void getNewAirDevice() {
        this.newAirByteDataList.clear();
        MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(CmdBleFactory.getNewAirCmd(CmdBleFactory.queryNewAirSubDevice(1, 0))).control(this.controlObject.getValue()).sendTimes(1).resendTimes(0).timeOutTime(10000).intervalTime(0).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM.5
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                if (msg != null) {
                    String substring = msg.getResData().substring(16);
                    if (substring.length() >= 12 && Utils.checkData(substring)) {
                        ActCentralAirProGatewayVM.this.saveSubDeviceByteData(substring.substring(12, substring.length() - 2), 2);
                    }
                    ActCentralAirProGatewayVM.this.parseNewAirData();
                }
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                ActCentralAirProGatewayVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.tip_search_timeout));
                ActCentralAirProGatewayVM.this.dismissLoadingDialog();
            }
        }).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseFloorHeatData() {
        int i;
        int i2 = 0;
        while (i2 < this.floorHeatByteDataList.size()) {
            int unicastAddress = this.controlObject.getValue().getUnicastAddress();
            String str = this.floorHeatByteDataList.get(i2);
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.getString(R.string.floor_heat));
            i2++;
            sb.append(i2);
            FloorHeatSubDeviceParam floorHeatSubDeviceParam = new FloorHeatSubDeviceParam(unicastAddress, str, sb.toString(), this.controlObject.getValue().getFloorName() + this.controlObject.getValue().getRoomName());
            while (true) {
                if (i < this.saveDevice.size()) {
                    FloorHeatSubDeviceParam floorHeatSubDeviceParam2 = (FloorHeatSubDeviceParam) this.saveDevice.get(i).getParam(FloorHeatSubDeviceParam.class);
                    i = (floorHeatSubDeviceParam2.inAddr == floorHeatSubDeviceParam.inAddr && floorHeatSubDeviceParam2.outAddr == floorHeatSubDeviceParam.outAddr) ? 0 : i + 1;
                } else {
                    this.floorHeatParamList.add(floorHeatSubDeviceParam);
                    break;
                }
            }
        }
        if (this.floorHeatParamList.size() != 0) {
            this.startUpdateFloorHeat.call();
        } else {
            showSuccessTipDialog(String.format(StringUtils.getString(R.string.total_search_device), Integer.valueOf(this.centralAirByteDataList.size() + this.newAirByteDataList.size() + this.floorHeatByteDataList.size())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseNewAirData() {
        int i;
        int i2 = 0;
        while (i2 < this.newAirByteDataList.size()) {
            int unicastAddress = this.controlObject.getValue().getUnicastAddress();
            String str = this.newAirByteDataList.get(i2);
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.getString(R.string.new_air_name));
            i2++;
            sb.append(i2);
            FreshAirSubDeviceParam freshAirSubDeviceParam = new FreshAirSubDeviceParam(unicastAddress, str, sb.toString(), this.controlObject.getValue().getFloorName() + this.controlObject.getValue().getRoomName());
            while (true) {
                if (i < this.saveDevice.size()) {
                    FreshAirSubDeviceParam freshAirSubDeviceParam2 = (FreshAirSubDeviceParam) this.saveDevice.get(i).getParam(FreshAirSubDeviceParam.class);
                    i = (freshAirSubDeviceParam2.inAddr == freshAirSubDeviceParam.inAddr && freshAirSubDeviceParam2.outAddr == freshAirSubDeviceParam.outAddr) ? 0 : i + 1;
                } else {
                    this.newAirParamList.add(freshAirSubDeviceParam);
                    break;
                }
            }
        }
        if (this.newAirParamList.size() != 0) {
            this.startUpdateNewAir.call();
        } else {
            getFloorHeatDevice();
        }
    }

    public void getFloorHeatDevice() {
        this.floorHeatByteDataList.clear();
        MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(CmdBleFactory.getFloorHeatCmd(CmdBleFactory.queryFloorHeatSubDevice(1, 0))).control(this.controlObject.getValue()).sendTimes(1).resendTimes(0).timeOutTime(10000).intervalTime(0).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM.6
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                if (msg != null) {
                    String substring = msg.getResData().substring(16);
                    if (substring.length() >= 12 && Utils.checkData(substring)) {
                        ActCentralAirProGatewayVM.this.saveSubDeviceByteData(substring.substring(12, substring.length() - 2), 3);
                    }
                    ActCentralAirProGatewayVM.this.parseFloorHeatData();
                }
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                ActCentralAirProGatewayVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.tip_search_timeout));
                ActCentralAirProGatewayVM.this.dismissLoadingDialog();
            }
        }).enqueue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveSubDeviceByteData(String data, int type) {
        int length = data.length() / SUB_DEVICE_LENGTH;
        LHomeLog.i(getClass(), "size=" + length);
        int length2 = data.length() % SUB_DEVICE_LENGTH;
        LHomeLog.i(getClass(), "modData=" + length2);
        if (length2 > 0) {
            this.framModData = data.substring(data.length() - length2);
        }
        for (int i = 0; i < length; i++) {
            int i2 = SUB_DEVICE_LENGTH;
            String substring = data.substring(i * i2, (i * i2) + i2);
            if (type == 1) {
                this.centralAirByteDataList.add(substring);
            } else if (type == 2) {
                this.newAirByteDataList.add(substring);
            } else {
                this.floorHeatByteDataList.add(substring);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseCentralAirData() {
        int i;
        int i2 = 0;
        while (i2 < this.centralAirByteDataList.size()) {
            int unicastAddress = this.controlObject.getValue().getUnicastAddress();
            String str = this.centralAirByteDataList.get(i2);
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.getString(R.string.central_air));
            i2++;
            sb.append(i2);
            CentralAirSubDeviceParam centralAirSubDeviceParam = new CentralAirSubDeviceParam(unicastAddress, str, sb.toString(), this.controlObject.getValue().getFloorName() + this.controlObject.getValue().getRoomName());
            while (true) {
                if (i < this.saveDevice.size()) {
                    CentralAirSubDeviceParam centralAirSubDeviceParam2 = (CentralAirSubDeviceParam) this.saveDevice.get(i).getParam(CentralAirSubDeviceParam.class);
                    i = (centralAirSubDeviceParam2.inAddr == centralAirSubDeviceParam.inAddr && centralAirSubDeviceParam2.outAddr == centralAirSubDeviceParam.outAddr) ? 0 : i + 1;
                } else {
                    this.centralAirParamList.add(centralAirSubDeviceParam);
                    break;
                }
            }
        }
        if (this.centralAirParamList.size() != 0) {
            this.startUpdateCentralAir.call();
        } else {
            getNewAirDevice();
        }
    }

    private Device getCentralAir(CentralAirSubDeviceParam centralAirSubDeviceParam) {
        Device device = new Device();
        device.setPlaceId(this.placeId);
        device.setFloorId(this.controlObject.getValue().getFloorId());
        device.setRoomId(this.controlObject.getValue().getRoomId());
        device.setRoomName(this.roomName);
        device.setFloorName(this.floorName);
        device.setProductId(ProductId.CENTRAL_AIR_SUB_DEVICE);
        device.setOnlineFlag(1);
        device.setDeviceState(new DeviceState());
        device.setExtParam("{\"ACType\":1}");
        device.setParam(centralAirSubDeviceParam);
        device.setDeviceName(centralAirSubDeviceParam.getName());
        device.setMaccode("");
        device.setWifiMac(this.controlObject.getValue().getWifiMac().replaceAll(Constants.COLON_SEPARATOR, "") + getSubDeviceString(centralAirSubDeviceParam));
        device.setDevicesn(this.controlObject.getValue().getWifiMac().replaceAll(Constants.COLON_SEPARATOR, "") + getSubDeviceString(centralAirSubDeviceParam));
        device.setMacdeviceid(this.controlObject.getValue().getDeviceId());
        device.setMacfalg(2);
        return device;
    }

    public String centralAirProductSubData(CentralAirSubDeviceParam subDeviceParam) {
        ConfigHelper.instance().codeLibrary = CodeLibraryUtil.generateAcCentralCodeLibrary(subDeviceParam.getUnicastAddress(), subDeviceParam.getOutAddr(), subDeviceParam.getInAddr());
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).placeid(this.placeId).floorid(this.controlObject.getValue().getFloorId()).roomid(this.controlObject.getValue().getRoomId()).param(GsonUtils.toJson(subDeviceParam)).mac(this.controlObject.getValue().getWifiMac().replaceAll(Constants.COLON_SEPARATOR, "") + getSubDeviceString(subDeviceParam)).maccode("").devicename(subDeviceParam.getName()).macfalg(2).codeLibrary(ConfigHelper.instance().codeLibrary).aiPuductType(ProductId.IR_SUB_TYPE_AC).subProductTypeName(ProductId.IR_SUB_TYPE_AC).subManufacturerName("LTECH").subProductName(subDeviceParam.getName()).macdeviceid(this.controlObject.getValue().getDeviceId()).paramext(GsonUtils.toJson(new ExtParam(1, this.deviceFeature))).build().getConfigJson();
    }

    public String newAirProductSubData(FreshAirSubDeviceParam freshAirSubDeviceParam) {
        ConfigHelper.instance().codeLibrary = CodeLibraryUtil.generateFreshAirCodeLibrary(freshAirSubDeviceParam.getUnicastAddress(), freshAirSubDeviceParam.getOutAddr(), freshAirSubDeviceParam.getInAddr());
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).placeid(this.placeId).floorid(this.controlObject.getValue().getFloorId()).roomid(this.controlObject.getValue().getRoomId()).param(GsonUtils.toJson(freshAirSubDeviceParam)).mac(this.controlObject.getValue().getWifiMac().replaceAll(Constants.COLON_SEPARATOR, "") + getSubDeviceString(freshAirSubDeviceParam)).maccode("").devicename(freshAirSubDeviceParam.getName()).macfalg(2).codeLibrary(ConfigHelper.instance().codeLibrary).aiPuductType(ProductId.BLE_SUB_TYPE_NEW_AIR).subProductTypeName(ProductId.BLE_SUB_TYPE_NEW_AIR).subManufacturerName("LTECH").subProductName(freshAirSubDeviceParam.getName()).macdeviceid(this.controlObject.getValue().getDeviceId()).paramext("{\"ACType\":2}").build().getConfigJson();
    }

    public String floorHeatProductSubData(FloorHeatSubDeviceParam floorHeatSubDeviceParam) {
        ConfigHelper.instance().codeLibrary = CodeLibraryUtil.generateFloorHeatCodeLibrary(floorHeatSubDeviceParam.getUnicastAddress(), floorHeatSubDeviceParam.getOutAddr(), floorHeatSubDeviceParam.getInAddr());
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).placeid(this.placeId).floorid(this.controlObject.getValue().getFloorId()).roomid(this.controlObject.getValue().getRoomId()).param(GsonUtils.toJson(floorHeatSubDeviceParam)).mac(this.controlObject.getValue().getWifiMac().replaceAll(Constants.COLON_SEPARATOR, "") + getSubDeviceString(floorHeatSubDeviceParam)).maccode("").devicename(floorHeatSubDeviceParam.getName()).macfalg(2).codeLibrary(ConfigHelper.instance().codeLibrary).aiPuductType(ProductId.BLE_SUB_TYPE_FLOOR_HEAT).subProductTypeName(ProductId.BLE_SUB_TYPE_FLOOR_HEAT).subManufacturerName("LTECH").subProductName(floorHeatSubDeviceParam.getName()).macdeviceid(this.controlObject.getValue().getDeviceId()).paramext("{\"ACType\":3}").build().getConfigJson();
    }

    private String getSubDeviceString(CentralAirSubDeviceParam subDeviceParam) {
        return ProductId.SPLIT + com.smart.message.utils.StringUtils.addZeroForNum(Integer.toHexString(subDeviceParam.outAddr), 2) + com.smart.message.utils.StringUtils.addZeroForNum(Integer.toHexString(subDeviceParam.inAddr), 2);
    }

    private String getSubDeviceString(FreshAirSubDeviceParam freshAirSubDeviceParam) {
        return ProductId.SPLIT + com.smart.message.utils.StringUtils.addZeroForNum(Integer.toHexString(freshAirSubDeviceParam.outAddr), 2) + com.smart.message.utils.StringUtils.addZeroForNum(Integer.toHexString(freshAirSubDeviceParam.inAddr), 2);
    }

    private String getSubDeviceString(FloorHeatSubDeviceParam floorHeatSubDeviceParam) {
        return ProductId.SPLIT + com.smart.message.utils.StringUtils.addZeroForNum(Integer.toHexString(floorHeatSubDeviceParam.outAddr), 2) + com.smart.message.utils.StringUtils.addZeroForNum(Integer.toHexString(floorHeatSubDeviceParam.inAddr), 2);
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

    private Device addNewAirSubDevice(AddDeviceResponse response) {
        Device device = new Device();
        device.setPlaceId(this.placeId);
        device.setFloorId(this.controlObject.getValue().getFloorId());
        device.setRoomId(this.controlObject.getValue().getRoomId());
        device.setRoomName(this.roomName);
        device.setFloorName(this.floorName);
        device.setProductId(ProductId.FRESH_AIR_SUB_DEVICE);
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
        device.setExtParam("{\"ACType\":2}");
        LHomeLog.i(getClass(), "response.getMac() =" + response.getMac());
        if (1 == ProductRepository.getMacFlag(response.getProductid())) {
            Injection.repo().device().removeDeviceByMac(this.placeId, response.getMac());
        }
        Injection.repo().device().saveDevice(device);
        return device;
    }

    private Device addFloorHeatSubDevice(AddDeviceResponse response) {
        Device device = new Device();
        device.setPlaceId(this.placeId);
        device.setFloorId(this.controlObject.getValue().getFloorId());
        device.setRoomId(this.controlObject.getValue().getRoomId());
        device.setRoomName(this.roomName);
        device.setFloorName(this.floorName);
        device.setProductId(ProductId.FLOOR_HEAT_SUB_DEVICE);
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
        device.setExtParam("{\"ACType\":3}");
        LHomeLog.i(getClass(), "response.getMac() =" + response.getMac());
        if (1 == ProductRepository.getMacFlag(response.getProductid())) {
            Injection.repo().device().removeDeviceByMac(this.placeId, response.getMac());
        }
        Injection.repo().device().saveDevice(device);
        return device;
    }

    public void initSubDeviceList() {
        this.subDeviceFtList.add(FtSubDevice.newInstance(1));
        this.subDeviceFtList.add(FtSubDevice.newInstance(2));
        this.subDeviceFtList.add(FtSubDevice.newInstance(3));
    }
}