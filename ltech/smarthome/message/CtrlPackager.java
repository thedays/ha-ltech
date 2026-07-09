package com.ltech.smarthome.message;

import com.feasycom.feasymesh.library.utils.MeshAddress;
import com.google.common.primitives.Longs;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.smart.message.utils.LHomeLog;
import java.util.List;

/* loaded from: classes3.dex */
public class CtrlPackager {
    private static final boolean USE_PLATFORM_ID = false;

    public static CtrlPackage getWifiDeviceCtrlPackage(Device device) {
        CtrlPackage ctrlPackage = new CtrlPackage(ProductRepository.getAgreementIdByPid(device.getProductId()));
        ctrlPackage.setDeviceIds(new long[]{device.getDeviceId()});
        ctrlPackage.setDeviceFlag(device.getIotDeviceName());
        ctrlPackage.setControlType(0);
        return ctrlPackage;
    }

    public static CtrlPackage getWifiGroupCtrlPackage(Group group) {
        List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
        StringBuilder sb = new StringBuilder(ProductId.SPLIT);
        String str = "";
        if (value != null) {
            for (Device device : value) {
                if (group.getDeviceIds().contains(Long.valueOf(device.getDeviceId()))) {
                    sb.append(device.getIotDeviceName());
                    sb.append(ProductId.SPLIT);
                    str = device.getProductId();
                }
            }
        }
        CtrlPackage ctrlPackage = new CtrlPackage(ProductRepository.getAgreementIdByPid(str));
        ctrlPackage.setDeviceIds(Longs.toArray(group.getDeviceIds()));
        ctrlPackage.setDeviceFlag(sb.toString());
        ctrlPackage.setControlType(1);
        return ctrlPackage;
    }

    public static CtrlPackage getWifiBleDeviceCtrlPackage(Device device) {
        CtrlPackage ctrlPackage = new CtrlPackage(ProductRepository.getAgreementIdByPid(device.getProductId()));
        ctrlPackage.setDeviceIds(new long[]{device.getDeviceId()});
        ctrlPackage.setDeviceFlag(device.getIotDeviceName());
        if (device.getParam(BleParam.class) != null) {
            ctrlPackage.setAddress(((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
        }
        ctrlPackage.setControlType(4);
        return ctrlPackage;
    }

    public static CtrlPackage getSubDeviceCtrlPackage(Device device) {
        CtrlPackage ctrlPackage = new CtrlPackage(ProductRepository.getAgreementIdByPid(device.getProductId()));
        ctrlPackage.setDeviceIds(new long[]{device.getMacdeviceid()});
        ctrlPackage.setControlType(4);
        return ctrlPackage;
    }

    public static CtrlPackage getBleDeviceCtrlPackage(Device device) {
        CtrlPackage ctrlPackage = new CtrlPackage(ProductRepository.getAgreementIdByPid(device.getProductId()));
        if (!AsHelper.isNewUb(device) && ProductRepository.needSubscribeGroup(device.getProductId())) {
            LHomeLog.i(CtrlPackage.class, "getBleDeviceCtrlPackage = " + ((BleParam) device.getParam(BleParam.class)).getPublicationAddress());
            ctrlPackage.setAddress(((BleParam) device.getParam(BleParam.class)).getPublicationAddress());
        } else {
            LHomeLog.i(CtrlPackage.class, "getBleDeviceCtrlPackage 1= " + ((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
            ctrlPackage.setAddress(((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
        }
        ctrlPackage.setUnicastAddress(((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
        ctrlPackage.setControlType(2);
        return ctrlPackage;
    }

    public static CtrlPackage getDeviceCtrlPackageByUnicastAdd(Device device) {
        CtrlPackage ctrlPackage = new CtrlPackage(ProductRepository.getAgreementIdByPid(device.getProductId()));
        ctrlPackage.setAddress(((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
        ctrlPackage.setUnicastAddress(((BleParam) device.getParam(BleParam.class)).getUnicastAddress());
        ctrlPackage.setControlType(2);
        return ctrlPackage;
    }

    public static CtrlPackage getBleGroupCtrlPackage(Group group) {
        CtrlPackage ctrlPackage = new CtrlPackage(ProductRepository.getGroupAgreementId(group.getModuleType(), group.getControlType()));
        ctrlPackage.setAddress(group.getGroupAddress());
        ctrlPackage.setControlType(3);
        return ctrlPackage;
    }

    public static CtrlPackage getBleForCentralAirDeviceCtrlPackage(int uniCastAddress) {
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(uniCastAddress);
        ctrlPackage.setUnicastAddress(uniCastAddress);
        ctrlPackage.setControlType(2);
        return ctrlPackage;
    }

    public static CtrlPackage getBleForReportCtrlPackage() {
        CtrlPackage ctrlPackage = new CtrlPackage(2);
        ctrlPackage.setAddress(MeshAddress.START_GROUP_ADDRESS);
        ctrlPackage.setControlType(2);
        return ctrlPackage;
    }
}