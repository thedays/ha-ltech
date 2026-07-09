package com.ltech.smarthome.ui.config;

import android.text.TextUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.hzy.tvmao.KKACZipManagerV2;
import com.hzy.tvmao.KKNonACManager;
import com.hzy.tvmao.ir.ac.ACStateV2;
import com.kookong.app.data.IrData;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.request.device.ConfigDeviceBean;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.ui.device.ir.AcRepository;
import com.ltech.smarthome.ui.device.ir.Device433Repository;
import com.ltech.smarthome.ui.device.ir.IrKeyRepository;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.message.base.IStateConverter;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.MeshGatewayParam;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class ConfigHelper {
    public String btmodule;
    public String codeLibrary;
    public String deviceName;
    public long floorId;
    public String mac;
    public String macCode;
    public long macdeviceid;
    public String mid;
    public String panelinfo;
    public Object param;
    public String paramext;
    public String password;
    public long placeId;
    public String platDeviceId;
    public ProductInfo productInfo;
    public long roomId;
    public List<ActMeshScanVM.ScanItem> scanListCache;
    public String ssid;
    public String subManufacturerName;
    public String subProductName;
    public int unicastAddress;

    private ConfigHelper() {
        this.codeLibrary = "{}";
        this.macCode = "{\"CharSwitch\":\"66BB27C000002A002200090ED8010000000018001400180040010004015A00AD0204030605040018031C00EB\"}";
        this.scanListCache = new ArrayList();
    }

    private static class Holder {
        private static ConfigHelper INSTANCE = new ConfigHelper();

        private Holder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void reset() {
            INSTANCE = new ConfigHelper();
        }
    }

    public static ConfigHelper instance() {
        return Holder.INSTANCE;
    }

    public void reset() {
        Holder.reset();
    }

    public String wifiSingleProductData() {
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).productid(this.productInfo.getProductId()).mid(this.mid).placeid(this.placeId).floorid(this.floorId).roomid(this.roomId).mac(this.mac.replaceAll(Constants.COLON_SEPARATOR, "")).maccode(this.macCode).devicename(this.deviceName).macfalg(ProductRepository.getMacFlag(this.productInfo.getProductId())).build().getConfigJson();
    }

    public String wifiCameraData(String serialNo, String validateCode) {
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).productid(this.productInfo.getProductId()).placeid(this.placeId).floorid(this.floorId).roomid(this.roomId).param(this.param).mac(serialNo).maccode(this.macCode).platformdeviceid(Device.DEFAULT_PLAT_ID).devicename(this.deviceName).devicesn(serialNo).macfalg(ProductRepository.getMacFlag(this.productInfo.getProductId())).yingshitype(1L).validateCode(validateCode).build().getConfigJson();
    }

    public String bleProductData() {
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).productid(this.productInfo.getProductId()).placeid(this.placeId).floorid(this.floorId).roomid(this.roomId).param(this.param).paramext(this.paramext).mac(this.mac.replaceAll(Constants.COLON_SEPARATOR, "")).platformdeviceid(Device.DEFAULT_PLAT_ID).maccode(this.macCode).devicename(this.deviceName).btmodule(this.btmodule).codeLibrary(this.codeLibrary).macfalg(ProductRepository.getMacFlag(this.productInfo.getProductId())).build().getConfigJson();
    }

    public String bleProductDataNoPlace() {
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).productid(this.productInfo.getProductId()).placeid(this.placeId).param(this.param).paramext(this.paramext).mac(this.mac.replaceAll(Constants.COLON_SEPARATOR, "")).platformdeviceid(Device.DEFAULT_PLAT_ID).maccode(this.macCode).devicename(this.deviceName).codeLibrary(this.codeLibrary).macfalg(ProductRepository.getMacFlag(this.productInfo.getProductId())).build().getConfigJson();
    }

    public String irProductData() {
        ConfigDeviceBean.Builder aBean = ConfigDeviceBean.Builder.aBean();
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.macdeviceid);
        if (deviceByDeviceId != null && (deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX) || deviceByDeviceId.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S))) {
            aBean.voiceControlType(1);
        }
        aBean.userid(Injection.repo().user().getUserId()).placeid(this.placeId).floorid(this.floorId).roomid(this.roomId).param(GsonUtils.toJson(this.param)).mac(this.mac.replaceAll(Constants.COLON_SEPARATOR, "")).maccode(this.macCode).devicename(this.deviceName).macfalg(ProductRepository.getMacFlag(this.productInfo.getProductId())).codeLibrary(this.codeLibrary).aiPuductType(this.productInfo.getProductKey()).subProductTypeName(this.productInfo.getProductKey()).subManufacturerName(this.subManufacturerName).subProductName(this.subProductName).macdeviceid(this.macdeviceid);
        return aBean.build().getConfigJson();
    }

    public String bleVirtualData() {
        return ConfigDeviceBean.Builder.aBean().userid(Injection.repo().user().getUserId()).productid(this.productInfo.getProductId()).placeid(this.placeId).floorid(this.floorId).roomid(this.roomId).param(this.param).paramext(this.paramext).platformdeviceid(Device.DEFAULT_PLAT_ID).maccode(this.macCode).devicename(this.deviceName).codeLibrary(this.codeLibrary).virtual(1).writable(1).macfalg(ProductRepository.getMacFlag(this.productInfo.getProductId())).build().getConfigJson();
    }

    public Device addDevice(AddDeviceResponse response, String... productId) {
        BleParam bleParam;
        Device device = new Device();
        device.setPlaceId(instance().placeId);
        device.setFloorId(instance().floorId);
        device.setRoomId(instance().roomId);
        if (instance().floorId > 0) {
            device.setFloorName(Injection.repo().home().getFloor(instance().floorId).getFloorName());
        }
        if (instance().roomId > 0) {
            device.setRoomName(Injection.repo().home().getRoom(instance().roomId).getRoomName());
        }
        device.setProductId(productId.length > 0 ? productId[0] : response.getProductid());
        LHomeLog.i(getClass(), "response 2 =" + response.toString());
        device.setDeviceId(response.getDeviceid());
        device.setPlatFormDeviceId(response.getPlatformdeviceid());
        device.setOnlineFlag(1);
        device.setWifiMac(response.getMac());
        device.setDeviceName(response.getDevicename());
        device.setMacfalg(response.getMacfalg());
        device.setMacdeviceid(response.getMacdeviceid());
        device.setParam(response.getParam());
        device.setExtParam(response.getParamext());
        device.setDeviceState(new DeviceState());
        device.setIndex(1000);
        device.setCreatetime(response.getCreatetime());
        if (!TextUtils.isEmpty(device.getParam()) && (bleParam = (BleParam) device.getParam(BleParam.class)) != null) {
            device.setUnicastAddress(bleParam.getUnicastAddress());
        }
        LHomeLog.i(getClass(), "response.getMac() =" + response.getMac());
        if (1 == ProductRepository.getMacFlag(response.getProductid()) && !TextUtils.isEmpty(response.getMac())) {
            Injection.repo().device().removeDeviceByMac(instance().placeId, response.getMac());
        }
        SharedPreferenceUtil.edit().keepShared(com.ltech.smarthome.utils.Constants.DEVICE_CHANGED, true);
        Injection.repo().device().saveDevice(device);
        return device;
    }

    public String getMotorCodeLib(String productId, int agreementId, Device433Repository.MotorCodeLib motorCodeLib) {
        return getMotorCodeLib(productId, agreementId, motorCodeLib, -1);
    }

    public String getMotorCodeLib(String productId, int agreementId, Device433Repository.MotorCodeLib motorCodeLib, int address) {
        IStateConverter cmdConvertStrategy = Injection.strategy().getCmdConvertStrategy(agreementId);
        productId.hashCode();
        if (productId.equals(ProductId.ID_IR_CURTAIN)) {
            return getMotorCodeLib(cmdConvertStrategy, motorCodeLib, Device433Repository.getCurtainLibKey(), address);
        }
        return GsonUtils.toJson(new HashMap());
    }

    private String getMotorCodeLib(IStateConverter cmdConverter, Device433Repository.MotorCodeLib motorCodeLib, Object[][] libKey, int address) {
        HashMap hashMap = new HashMap();
        if (cmdConverter != null) {
            for (Object[] objArr : libKey) {
                MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
                meshGatewayParam.setCmdType(3);
                meshGatewayParam.setCmdData(motorCodeLib.getCodeByKey((String) objArr[1]));
                if (address > 0) {
                    hashMap.put((String) objArr[0], SceneHelper.createCmdData(StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, Injection.strategy().getCmdConvertStrategy(3).convert2cmd(Injection.strategy().getCmdConvertStrategy(2).convert2cmd(meshGatewayParam), Integer.valueOf(address)).value(new Object[0])))));
                } else {
                    hashMap.put((String) objArr[0], SceneHelper.createCmdData(cmdConverter.convert2cmd(meshGatewayParam).valueString(new Object[0])));
                }
            }
        }
        return GsonUtils.toJson(hashMap);
    }

    public String getIrCodeLibrary(String productId, int agreementId, IrData irData, int address) {
        IStateConverter cmdConvertStrategy;
        cmdConvertStrategy = Injection.strategy().getCmdConvertStrategy(agreementId);
        productId.hashCode();
        switch (productId) {
            case "IR_1":
            case "IR_2":
                return getIrCodeLibrary(cmdConvertStrategy, irData, IrKeyRepository.getTvLibKey(), address);
            case "IR_5":
                return getAcCodeLibrary(cmdConvertStrategy, irData, address);
            case "IR_8":
                return getIrCodeLibrary(cmdConvertStrategy, irData, IrKeyRepository.getFanLibKey(), address);
            case "IR_11":
                return getIrCodeLibrary(cmdConvertStrategy, irData, IrKeyRepository.getAirCleanerLibKey(), address);
            case "IR_12":
                return getIrCodeLibrary(cmdConvertStrategy, irData, IrKeyRepository.getWaterHeaterLibKey(), address);
            default:
                return GsonUtils.toJson(new HashMap());
        }
    }

    public String getIrCodeLibrary(String productId, int agreementId, IrData irData) {
        return getIrCodeLibrary(productId, agreementId, irData, -1);
    }

    private String getIrCodeLibrary(IStateConverter cmdConverter, IrData irData, Object[][] libKey) {
        return getIrCodeLibrary(cmdConverter, irData, libKey, -1);
    }

    private String getIrCodeLibrary(IStateConverter cmdConverter, IrData irData, Object[][] libKey, int address) {
        KKNonACManager kKNonACManager = new KKNonACManager(irData);
        HashMap hashMap = new HashMap();
        if (cmdConverter != null) {
            for (Object[] objArr : libKey) {
                MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
                meshGatewayParam.setCmdType(1);
                byte[] keyIr = kKNonACManager.getKeyIr(((Integer) objArr[1]).intValue());
                if (keyIr == null) {
                    keyIr = new byte[0];
                }
                meshGatewayParam.setCmdData(keyIr);
                if (address != -1) {
                    hashMap.put((String) objArr[0], SceneHelper.createCmdData(StringUtils.byte2Str(Injection.iot().connectAndSendData4(address, Injection.strategy().getCmdConvertStrategy(3).convert2cmd(Injection.strategy().getCmdConvertStrategy(2).convert2cmd(meshGatewayParam), Integer.valueOf(address)).value(new Object[0])))));
                } else {
                    hashMap.put((String) objArr[0], SceneHelper.createCmdData(cmdConverter.convert2cmd(meshGatewayParam).valueString(new Object[0])));
                }
            }
        }
        return GsonUtils.toJson(hashMap);
    }

    private String getAcCodeLibrary(IStateConverter cmdConverter, IrData irData) {
        return getAcCodeLibrary(cmdConverter, irData, -1);
    }

    private String getAcCodeLibrary(IStateConverter cmdConverter, IrData irData, int address) {
        int i;
        int i2;
        HashMap hashMap = new HashMap();
        if (cmdConverter != null) {
            KKACZipManagerV2 kKACZipManagerV2 = new KKACZipManagerV2();
            kKACZipManagerV2.initIRData(irData);
            kKACZipManagerV2.setACStateV2FromString("");
            char c2 = 1;
            if (kKACZipManagerV2.getPowerState() == 1) {
                kKACZipManagerV2.changePowerState();
            }
            int i3 = 0;
            if (kKACZipManagerV2.getPowerState() == 0) {
                MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
                meshGatewayParam.setCmdType(1);
                byte[] aCKeyIr = kKACZipManagerV2.getACKeyIr();
                if (aCKeyIr == null) {
                    aCKeyIr = new byte[0];
                }
                meshGatewayParam.setCmdData(aCKeyIr);
                if (address != -1) {
                    hashMap.put("开", getCmdData(address, meshGatewayParam));
                } else {
                    hashMap.put("开", SceneHelper.createCmdData(cmdConverter.convert2cmd(meshGatewayParam).valueString(new Object[0])));
                }
            }
            Object[][] swingModeKey = AcRepository.getSwingModeKey();
            int length = swingModeKey.length;
            int i4 = 0;
            while (i4 < length) {
                Object[] objArr = swingModeKey[i4];
                kKACZipManagerV2.changeUDWindDirect((ACStateV2.UDWindDirectKey) objArr[c2]);
                Object[][] modeKey = AcRepository.getModeKey();
                int length2 = modeKey.length;
                int i5 = 0;
                while (i5 < length2) {
                    Object[] objArr2 = modeKey[i5];
                    kKACZipManagerV2.changeACTargetModel(((Byte) objArr2[c2]).byteValue());
                    Object[][] windSpeedKey = AcRepository.getWindSpeedKey();
                    int length3 = windSpeedKey.length;
                    while (i3 < length3) {
                        Object[] objArr3 = windSpeedKey[i3];
                        kKACZipManagerV2.setTargetWindSpeed(((Byte) objArr3[c2]).byteValue());
                        int i6 = 16;
                        Object[][] objArr4 = swingModeKey;
                        while (i6 <= 30) {
                            kKACZipManagerV2.setTargetTemp(i6);
                            MeshGatewayParam meshGatewayParam2 = new MeshGatewayParam();
                            KKACZipManagerV2 kKACZipManagerV22 = kKACZipManagerV2;
                            meshGatewayParam2.setCmdType(1);
                            byte[] aCKeyIr2 = kKACZipManagerV22.getACKeyIr();
                            if (aCKeyIr2 == null) {
                                i = length3;
                                aCKeyIr2 = new byte[0];
                            } else {
                                i = length3;
                            }
                            meshGatewayParam2.setCmdData(aCKeyIr2);
                            if (address != -1) {
                                i2 = i3;
                                hashMap.put(((String) objArr2[0]) + i6 + ((String) objArr3[0]) + ((String) objArr[0]), SceneHelper.createCmdData(StringUtils.byte2Str(Injection.iot().connectAndSendData4(address, Injection.strategy().getCmdConvertStrategy(3).convert2cmd(Injection.strategy().getCmdConvertStrategy(2).convert2cmd(meshGatewayParam2), Integer.valueOf(address)).value(new Object[0])))));
                            } else {
                                i2 = i3;
                                hashMap.put(((String) objArr2[0]) + i6 + ((String) objArr3[0]) + ((String) objArr[0]), SceneHelper.createCmdData(cmdConverter.convert2cmd(meshGatewayParam2).valueString(new Object[0])));
                            }
                            i6++;
                            kKACZipManagerV2 = kKACZipManagerV22;
                            length3 = i;
                            i3 = i2;
                        }
                        i3++;
                        swingModeKey = objArr4;
                        c2 = 1;
                    }
                    i5++;
                    c2 = 1;
                    i3 = 0;
                }
                i4++;
                c2 = 1;
                i3 = 0;
            }
            KKACZipManagerV2 kKACZipManagerV23 = kKACZipManagerV2;
            if (kKACZipManagerV23.getPowerState() == 0) {
                kKACZipManagerV23.changePowerState();
                MeshGatewayParam meshGatewayParam3 = new MeshGatewayParam();
                meshGatewayParam3.setCmdType(1);
                byte[] aCKeyIr3 = kKACZipManagerV23.getACKeyIr();
                if (aCKeyIr3 == null) {
                    aCKeyIr3 = new byte[0];
                }
                meshGatewayParam3.setCmdData(aCKeyIr3);
                if (address != -1) {
                    hashMap.put("关", getCmdData(address, meshGatewayParam3));
                } else {
                    hashMap.put("关", SceneHelper.createCmdData(cmdConverter.convert2cmd(meshGatewayParam3).valueString(new Object[0])));
                }
            }
        }
        return GsonUtils.toJson(hashMap);
    }

    private static String getCmdData(int address, MeshGatewayParam cmdParam) {
        return SceneHelper.createCmdData(StringUtils.byte2Str(Injection.iot().connectAndSendData3(address, Injection.strategy().getCmdConvertStrategy(3).convert2cmd(Injection.strategy().getCmdConvertStrategy(2).convert2cmd(cmdParam), Integer.valueOf(address)).value(new Object[0]))));
    }

    public void setScanListCache(List<ActMeshScanVM.ScanItem> list) {
        this.scanListCache.clear();
        new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).config) {
                this.scanListCache.add(list.get(i));
            }
        }
    }
}