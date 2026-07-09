package com.ltech.smarthome.model.repo;

import android.text.TextUtils;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.device_param.Rs8ExtParam;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.type.LightType;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainMotorInfoExtParam;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.device.super_panel.SuperPanelVersionHelper;
import com.ltech.smarthome.utils.IconRepository;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes4.dex */
public final class ProductRepository {
    public static final Set<Integer> AGREEMENT_SET;
    public static final String HOT_PWD = "ltech888888";
    public static final String HOT_SSID = "LTECH-HAM-WF";
    private static List<ProductInfo> productList;

    public static int getRelayCount(int colorType) {
        if (colorType != 18) {
            if (colorType == 19) {
                return 3;
            }
            if (colorType == 21) {
                return 4;
            }
            switch (colorType) {
                case 9:
                    return 2;
                case 10:
                    return 3;
                case 11:
                    break;
                default:
                    return 1;
            }
        }
        return 4;
    }

    public static int getZoneCount(int colorType) {
        if (colorType == 19) {
            return 6;
        }
        if (colorType == 21) {
            return 12;
        }
        switch (colorType) {
            case 8:
                return 1;
            case 9:
                return 2;
            case 10:
                return 3;
            default:
                return 4;
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        productList = arrayList;
        arrayList.add(Injection.productFactory().createWifiLightDim());
        productList.add(Injection.productFactory().createWifiLightCt());
        productList.add(Injection.productFactory().createWifiLightRgb());
        productList.add(Injection.productFactory().createWifiLightRgbw());
        productList.add(Injection.productFactory().createWifiLightRgbwy());
        productList.add(Injection.productFactory().createBleLightDim());
        productList.add(Injection.productFactory().createBleLightCt());
        productList.add(Injection.productFactory().createBleLightRgb());
        productList.add(Injection.productFactory().createBleLightRgbw());
        productList.add(Injection.productFactory().createBleLightRgbwy());
        productList.add(Injection.productFactory().createBleLightRgbwyCC());
        productList.add(Injection.productFactory().createBleSwitch());
        productList.add(Injection.productFactory().createBleMusicPlayer());
        productList.add(Injection.productFactory().createCgdPro());
        productList.add(Injection.productFactory().createRs485Module());
        productList.add(Injection.productFactory().createRs8Module());
        productList.add(Injection.productFactory().createBleLightDam());
        productList.add(Injection.productFactory().createBleLightCgd());
        productList.add(Injection.productFactory().createBleLightCgt());
        productList.add(Injection.productFactory().createBleHAM());
        productList.add(Injection.productFactory().createKbs());
        productList.add(Injection.productFactory().createKbs1());
        productList.add(Injection.productFactory().createBleLightHB4());
        productList.add(Injection.productFactory().createWifiGroupDim());
        productList.add(Injection.productFactory().createWifiGroupCt());
        productList.add(Injection.productFactory().createWifiGroupRgb());
        productList.add(Injection.productFactory().createWifiGroupRgbw());
        productList.add(Injection.productFactory().createWifiGroupRgbwy());
        productList.add(Injection.productFactory().createBleGroupDim());
        productList.add(Injection.productFactory().createBleGroupCt());
        productList.add(Injection.productFactory().createBleGroupRgb());
        productList.add(Injection.productFactory().createBleGroupRgbw());
        productList.add(Injection.productFactory().createBleGroupRgbwy());
        productList.add(Injection.productFactory().createBleGroupRgbwyCC());
        productList.add(Injection.productFactory().createBleGroupSwitch());
        productList.add(Injection.productFactory().createBleGroupKbs());
        productList.add(Injection.productFactory().createBleGroupSwitchPanelS1());
        productList.add(Injection.productFactory().createBleGroupSwitchPanelS2());
        productList.add(Injection.productFactory().createBleGroupSwitchPanelS3());
        productList.add(Injection.productFactory().createBleGroupSwitchPanelS4());
        productList.add(Injection.productFactory().createBleGroupSwitchPanelS4M());
        productList.add(Injection.productFactory().createBleGroupSwitchPanelS6Pro());
        productList.add(Injection.productFactory().createBleGroupSwitchPanelG4());
        productList.add(Injection.productFactory().createBleGroupCurtain());
        productList.add(Injection.productFactory().createBleGroupDryCurtain());
        productList.add(Injection.productFactory().createBleGroupWaveSensorMR03());
        productList.add(Injection.productFactory().createBleGroupWaveSensorMR04());
        productList.add(Injection.productFactory().createBleGroupWaveSensorMS03());
        productList.add(Injection.productFactory().createBleGroupDryDreamCurtain());
        productList.add(Injection.productFactory().createBleGroupEb1());
        productList.add(Injection.productFactory().createBleGroupEb2());
        productList.add(Injection.productFactory().createBleGroupEb5());
        productList.add(Injection.productFactory().createBleGroupUb1());
        productList.add(Injection.productFactory().createBleGroupUb2());
        productList.add(Injection.productFactory().createBleGroupUb4());
        productList.add(Injection.productFactory().createBleGroupUb5());
        productList.add(Injection.productFactory().createAsPanelU1S());
        productList.add(Injection.productFactory().createAsPanelU2S());
        productList.add(Injection.productFactory().createAsPanelU4S());
        productList.add(Injection.productFactory().createAsPanelU5S());
        productList.add(Injection.productFactory().createAsPanelUB8());
        productList.add(Injection.productFactory().createSwitchPanelS1C());
        productList.add(Injection.productFactory().createSwitchPanelS2C());
        productList.add(Injection.productFactory().createSwitchPanelS3C());
        productList.add(Injection.productFactory().createSwitchPanelS1());
        productList.add(Injection.productFactory().createSwitchPanelS2());
        productList.add(Injection.productFactory().createSwitchPanelS3());
        productList.add(Injection.productFactory().createSwitchPanelS1Pro());
        productList.add(Injection.productFactory().createSwitchPanelS2Pro());
        productList.add(Injection.productFactory().createSwitchPanelS3Pro());
        productList.add(Injection.productFactory().createSwitchPanelS6Pro());
        productList.add(Injection.productFactory().createSwitchPanelS6());
        productList.add(Injection.productFactory().createSwitchPanelS4());
        productList.add(Injection.productFactory().createSwitchPanelS4M());
        productList.add(Injection.productFactory().createScenePanelS8M());
        productList.add(Injection.productFactory().createScenePanelS6B());
        productList.add(Injection.productFactory().createSwitchPanelSQ());
        productList.add(Injection.productFactory().createSwitchPanelSQB());
        productList.add(Injection.productFactory().createSwitchPanelSQPro());
        productList.add(Injection.productFactory().createKnobRemoteGQ());
        productList.add(Injection.productFactory().createKnobRemoteGQX());
        productList.add(Injection.productFactory().createSwitchPanelG4());
        productList.add(Injection.productFactory().createSwitchPanelG4Pro());
        productList.add(Injection.productFactory().createSmartPanelG4TE());
        productList.add(Injection.productFactory().createKnobPanelGQPro());
        productList.add(Injection.productFactory().createKnobPanelGQMax());
        productList.add(Injection.productFactory().createSwitchPanelSeptS1());
        productList.add(Injection.productFactory().createSwitchPanelSeptS2());
        productList.add(Injection.productFactory().createSwitchPanelSeptS3());
        productList.add(Injection.productFactory().createSwitchPanelSeptS4());
        productList.add(Injection.productFactory().createSwitchPanelSeptS1Pro());
        productList.add(Injection.productFactory().createSwitchPanelSeptS2Pro());
        productList.add(Injection.productFactory().createSwitchPanelSeptS3Pro());
        productList.add(Injection.productFactory().createSwitchPanelSeptS4M());
        productList.add(Injection.productFactory().createKnobPanelE6D());
        productList.add(Injection.productFactory().createKnobPanelE6M());
        productList.add(Injection.productFactory().createKnobPanelE6A());
        productList.add(Injection.productFactory().createKnobPanelE6T());
        productList.add(Injection.productFactory().createKeySwitch1());
        productList.add(Injection.productFactory().createKeySwitch2());
        productList.add(Injection.productFactory().createKeySwitch3());
        productList.add(Injection.productFactory().createKeySwitch4());
        productList.add(Injection.productFactory().createBleLightSpi());
        productList.add(Injection.productFactory().createEurPanelEB1());
        productList.add(Injection.productFactory().createEurPanelEB2());
        productList.add(Injection.productFactory().createEurPanelEB5());
        productList.add(Injection.productFactory().createEurPanelEB6());
        productList.add(Injection.productFactory().createEurPanelEB8());
        productList.add(Injection.productFactory().createRc4());
        productList.add(Injection.productFactory().createRc4S());
        productList.add(Injection.productFactory().createRcB1());
        productList.add(Injection.productFactory().createRcB2());
        productList.add(Injection.productFactory().createRcB5());
        productList.add(Injection.productFactory().createRcB8());
        productList.add(Injection.productFactory().createBleDryContact());
        productList.add(Injection.productFactory().createDryContactToBle());
        productList.add(Injection.productFactory().createAndroidSuperPanel());
        productList.add(Injection.productFactory().createAndroidSuperPanelMini());
        productList.add(Injection.productFactory().createAndroidSuperPanelPro());
        productList.add(Injection.productFactory().createAndroidSuperPanel4S());
        productList.add(Injection.productFactory().createAndroidSuperPanel6S());
        productList.add(Injection.productFactory().createAndroidSuperPanel12S());
        productList.add(Injection.productFactory().createMeshGateway());
        productList.add(Injection.productFactory().createCentreAirGateway());
        productList.add(Injection.productFactory().createCentreAirProGateway());
        productList.add(Injection.productFactory().createCentreAirSubDevice());
        productList.add(Injection.productFactory().createNewAirSubDevice());
        productList.add(Injection.productFactory().createFloorHeatSubDevice());
        productList.add(Injection.productFactory().createCamera());
        productList.add(Injection.productFactory().createCg485SubDevice());
        productList.add(Injection.productFactory().createAndroidSuperPanelG4Max());
        productList.add(Injection.productFactory().createHomeKit());
        productList.add(Injection.productFactory().createCentreAirMaxGateway());
        productList.add(Injection.productFactory().createCgRs8SubDevice());
        productList.add(Injection.productFactory().createHomeKitMatter());
        productList.add(Injection.productFactory().createTV());
        productList.add(Injection.productFactory().createTvBox());
        productList.add(Injection.productFactory().createStb());
        productList.add(Injection.productFactory().createAc());
        productList.add(Injection.productFactory().createFan());
        productList.add(Injection.productFactory().createProjector());
        productList.add(Injection.productFactory().createAirCleaner());
        productList.add(Injection.productFactory().createWaterHeater());
        productList.add(Injection.productFactory().createHanger());
        productList.add(Injection.productFactory().createCurtain());
        productList.add(Injection.productFactory().createIrDiy());
        productList.add(Injection.productFactory().createBleCurtainCGCUR());
        productList.add(Injection.productFactory().createBleCurtainCGCUR15());
        productList.add(Injection.productFactory().createBleCurtainCGCURH3());
        productList.add(Injection.productFactory().createBodySensor());
        productList.add(Injection.productFactory().createWaveSensorMR3());
        productList.add(Injection.productFactory().createWaveSensorMR4());
        productList.add(Injection.productFactory().createWaveSensorMS3());
        productList.add(Injection.productFactory().createBleHSD());
        productList.add(Injection.productFactory().createDoorSensor());
        productList.add(Injection.productFactory().createSonos());
        AGREEMENT_SET = new HashSet();
        Iterator<ProductInfo> it = productList.iterator();
        while (it.hasNext()) {
            AGREEMENT_SET.add(Integer.valueOf(it.next().getAgreementId()));
        }
    }

    public static int getProductIcon(Object object) {
        if (object instanceof Group) {
            Group group = (Group) object;
            if (getLightColorType(group) == 12) {
                TrigExtParam trigExtParam = new TrigExtParam();
                trigExtParam.fillMapWithString(group.getExtParam());
                return trigExtParam.getCurtainType() == 0 ? R.mipmap.ic_device_ic_curgroup : trigExtParam.getCurtainType() == 1 ? R.mipmap.trig_icon_curgroup : trigExtParam.getCurtainType() == 2 ? R.mipmap.device_icon_curgroup_single : R.mipmap.ic_device_ic_curgroup;
            }
            if (getLightColorType(group) == 16) {
                TrigExtParam trigExtParam2 = new TrigExtParam();
                trigExtParam2.fillMapWithString(group.getExtParam());
                return (trigExtParam2.getCurtainType() != 0 && trigExtParam2.getCurtainType() == 2) ? R.mipmap.trig_curgroup_ic_dream_single : R.mipmap.trig_curgroup_ic_dream;
            }
            if (getLightColorType(group) == 14) {
                if (group.getExtParam() != null) {
                    CurtainMotorInfoExtParam curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
                    curtainMotorInfoExtParam.fillMapWithString(group.getExtParam());
                    if (curtainMotorInfoExtParam.getOpenType() == 0) {
                        return R.mipmap.ic_device_ic_curgroup;
                    }
                    if (curtainMotorInfoExtParam.getOpenType() == 1) {
                        return R.mipmap.device_icon_curgroup_single;
                    }
                }
            } else if (group.getImgindex() == -1 && group.getMaingroupid() > 0 && group.getMainModuleType() != null && group.getMainControlType() != null) {
                return getProductGroupIcon(group.getMainModuleType(), group.getMainControlType(), group.getImgindex());
            }
            return getProductGroupIcon(group.getModuleType(), group.getControlType(), group.getImgindex());
        }
        if (!(object instanceof Device)) {
            return R.mipmap.ic_device_none;
        }
        Device device = (Device) object;
        if (device.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT)) {
            if (device.getParam(DryContactBleParam.class) != null) {
                if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 0) {
                    TrigExtParam trigExtParam3 = new TrigExtParam();
                    trigExtParam3.fillMapWithString(device.getExtParam());
                    return trigExtParam3.getCurtainType() == 0 ? R.mipmap.ic_device_curtain : trigExtParam3.getCurtainType() == 1 ? R.mipmap.trig_icon_cur : trigExtParam3.getCurtainType() == 2 ? R.mipmap.device_icon_curtain_single : R.mipmap.ic_device_curtain;
                }
                if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 1 || ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 2) {
                    return R.mipmap.trig_icon_normal;
                }
                if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 3) {
                    TrigExtParam trigExtParam4 = new TrigExtParam();
                    trigExtParam4.fillMapWithString(device.getExtParam());
                    return (trigExtParam4.getCurtainType() != 0 && trigExtParam4.getCurtainType() == 2) ? R.mipmap.trig_cur_ic_dream_single : R.mipmap.trig_cur_ic_dream;
                }
                return getProductIcon(device.getProductId(), device.getHardwareId(), device.getImageIndex());
            }
            return getProductIcon(device);
        }
        if (device.getProductId().equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
            if (device.getParam(DryContactBleParam.class) != null) {
                return ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() - 1 == 0 ? R.mipmap.trig_icon_8switch : ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() - 1 == 1 ? R.mipmap.trig_icon_4switch : (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 0 || ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() - 1 == 2) ? R.mipmap.trig_icon_normal : getProductIcon(device.getProductId(), device.getHardwareId(), device.getImageIndex());
            }
            return getProductIcon(device);
        }
        if (device.getProductId().equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
            CurtainMotorInfoExtParam curtainMotorInfoExtParam2 = new CurtainMotorInfoExtParam();
            curtainMotorInfoExtParam2.fillMapWithString(device.getExtParam());
            return curtainMotorInfoExtParam2.getOpenType() == 0 ? R.mipmap.ic_device_curtain : curtainMotorInfoExtParam2.getOpenType() == 1 ? R.mipmap.device_icon_curtain_single : R.mipmap.ic_device_none;
        }
        if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3C)) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            return (bleParam.getBleType() == 1043 || bleParam.getBleType() == 1066) ? R.mipmap.device_icon_s3 : R.mipmap.device_icon_s3c;
        }
        if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C)) {
            BleParam bleParam2 = (BleParam) device.getParam(BleParam.class);
            return (bleParam2.getBleType() == 1041 || bleParam2.getBleType() == 1064) ? R.mipmap.device_icon_s1 : R.mipmap.device_icon_s1c;
        }
        if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2C)) {
            BleParam bleParam3 = (BleParam) device.getParam(BleParam.class);
            return (bleParam3.getBleType() == 1042 || bleParam3.getBleType() == 1065) ? R.mipmap.device_icon_s2 : R.mipmap.device_icon_s2c;
        }
        if (device.getProductId().equals(ProductId.CG485_SUB_DEVICE)) {
            return Cg485Helper.getDeviceImage(Utils.getApp(), device);
        }
        if (device.getProductId().equals(ProductId.CGRS8_SUB_DEVICE)) {
            Rs8ExtParam rs8ExtParam = (Rs8ExtParam) device.getExtParam(Rs8ExtParam.class);
            if (rs8ExtParam != null && rs8ExtParam.getIcon() != null) {
                if (rs8ExtParam.getIcon().equals("2")) {
                    return R.mipmap.trig_cur_ic_dream;
                }
                if (rs8ExtParam.getIcon().equals("3")) {
                    return R.mipmap.trig_icon_cur;
                }
            }
            return R.mipmap.ic_device_curtain;
        }
        if (device.getImageIndex() == -1 && !StringUtils.isEmpty(device.getMainProductId())) {
            return getProductIcon(device.getMainProductId(), device.getHardwareId(), device.getImageIndex());
        }
        return getProductIcon(device.getProductId(), device.getHardwareId(), device.getImageIndex());
    }

    public static int getProductIcon(String productId, String hardwareId, int... iconPosition) {
        if (iconPosition.length > 0 && iconPosition[0] > 0) {
            int[] lightIconValue = IconRepository.getLightIconValue(Utils.getApp());
            int i = iconPosition[0];
            if (i < lightIconValue.length) {
                return lightIconValue[i];
            }
        }
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductId().equals(productId) && productInfo.getHardwareId().equals(hardwareId)) {
                return productInfo.getDefaultIconRes();
            }
        }
        return R.mipmap.ic_device_none;
    }

    public static int getProductGroupIcon(String productId, String hardwareId, int... iconPosition) {
        if (iconPosition.length > 0 && iconPosition[0] > 0) {
            int[] lightGroupIconValue = IconRepository.getLightGroupIconValue(Utils.getApp());
            int i = iconPosition[0];
            if (i < lightGroupIconValue.length) {
                return lightGroupIconValue[i];
            }
        } else if (hardwareId.equals("101") && iconPosition[0] == 0) {
            return R.mipmap.ic_device_light_group;
        }
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductId().equals(productId) && productInfo.getHardwareId().equals(hardwareId)) {
                return productInfo.getDefaultIconRes();
            }
        }
        return R.mipmap.ic_device_none;
    }

    public static int getMacFlag(String productId) {
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductId().equals(productId)) {
                return productInfo.getMacFlag();
            }
        }
        return 0;
    }

    public static String getGroupKey(Device device) {
        BleParam bleParam;
        ProductInfo productInfo = null;
        if (device.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT)) {
            DryContactBleParam dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class);
            if (dryContactBleParam != null) {
                if (dryContactBleParam.getSubType() == 0) {
                    productInfo = Injection.productFactory().createBleTrigCurtain();
                } else if (dryContactBleParam.getSubType() == 3) {
                    productInfo = Injection.productFactory().createBleTrigDreamCurtain();
                }
                if (productInfo != null && !TextUtils.isEmpty(productInfo.getGroupKey())) {
                    return productInfo.getGroupKey();
                }
            }
        } else if (!device.getProductId().equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
            productInfo = getProductInfoByPid(device.getProductId());
        }
        if (productInfo != null) {
            if (device.getProductId().equals(ProductId.ID_BLE_CURTAIN) && !TextUtils.isEmpty(productInfo.getGroupKey())) {
                return productInfo.getGroupKey();
            }
            if (productInfo.isBleProduct() && (bleParam = (BleParam) device.getParam(BleParam.class)) != null) {
                return ProductId.CC.createGroupKey("3", String.valueOf(bleParam.getDeviceType()));
            }
            return productInfo.getGroupKey();
        }
        return "";
    }

    public static boolean checkDevice(String produceId) {
        return getProductInfoByPid(produceId) != null;
    }

    private static List<ProductInfo> getProductList() {
        return Collections.unmodifiableList(productList);
    }

    public static int getAgreementIdByPid(String pid) {
        ProductInfo productInfoByPid = getProductInfoByPid(pid);
        if (productInfoByPid != null) {
            return productInfoByPid.getAgreementId();
        }
        return 2;
    }

    public static int getGroupAgreementId(String moduleType, String ControlType) {
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductId().equals(moduleType) && productInfo.getHardwareId().equals(ControlType)) {
                return productInfo.getAgreementId();
            }
        }
        return 2;
    }

    public static ProductInfo getProductInfoByPid(String pid) {
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductId().equals(pid)) {
                return productInfo;
            }
        }
        return null;
    }

    public static boolean isWifiBleDevice(String productId) {
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductId().equals(productId)) {
                return productInfo.isWifiBleProduct();
            }
        }
        return false;
    }

    public static boolean isBLeDevice(String productId) {
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductId().equals(productId)) {
                return productInfo.isBleProduct();
            }
        }
        return false;
    }

    public static boolean isNewBleModule(Device device) {
        return (device.getBtmodule() != null && device.getBtmodule().equalsIgnoreCase("bt200")) || device.getProductId().equals(ProductId.ID_SMART_PANEL_GQ) || device.getProductId().equals(ProductId.ID_SMART_PANEL_GQX) || device.getProductId().equals(ProductId.ID_BLE_KBS) || device.getProductId().equals(ProductId.ID_DOOR_SENSOR) || device.getProductId().equals(ProductId.ID_EUR_PANEL_EB1) || device.getProductId().equals(ProductId.ID_EUR_PANEL_EB2) || device.getProductId().equals(ProductId.ID_EUR_PANEL_EB5) || device.getProductId().equals(ProductId.ID_EUR_PANEL_EB8) || device.getProductId().equals(ProductId.ID_RC_B1) || device.getProductId().equals(ProductId.ID_RC_B2) || device.getProductId().equals(ProductId.ID_RC_B5) || device.getProductId().equals(ProductId.ID_RC_B8);
    }

    public static int getInfraredType(String productId) {
        productId.hashCode();
        switch (productId) {
            case "IR_100":
                return 1;
            case "IR_1":
            case "IR_2":
            case "IR_3":
            case "IR_5":
            case "IR_6":
            case "IR_8":
            case "IR_11":
            case "IR_12":
                return 6;
            case "IR_98":
            case "IR_99":
                return 7;
            default:
                return 0;
        }
    }

    public static boolean isBleGroup(String moduleType) {
        ProductInfo productInfoByPid = getProductInfoByPid(moduleType);
        if (productInfoByPid != null) {
            return productInfoByPid.isBleProduct();
        }
        return false;
    }

    public static ProductInfo getBleProductInfoByType(String type, String subType) {
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductKey().equalsIgnoreCase(type) && productInfo.getSubProductKey().equalsIgnoreCase(subType)) {
                return productInfo;
            }
        }
        return null;
    }

    public static ProductInfo getBleProductInfoByType(Device device) {
        BleParam bleParam = (BleParam) device.getParam(BleParam.class);
        if (bleParam != null && bleParam.getBleType() != 0) {
            return getBleProductInfoByType(com.smart.message.utils.StringUtils.toHexString((bleParam.getBleType() >> 8) & 255), com.smart.message.utils.StringUtils.toHexString(bleParam.getBleType() & 255));
        }
        for (ProductInfo productInfo : getProductList()) {
            if (productInfo.getProductId().equalsIgnoreCase(device.getProductId())) {
                return productInfo;
            }
        }
        return null;
    }

    public static boolean needSubscribeGroup(String pid) {
        pid.hashCode();
        switch (pid) {
            case "120042616091901":
            case "120042616094101":
            case "120042616101901":
            case "120042616103901":
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static int getZoneCount(String pid) {
        pid.hashCode();
        char c2 = 65535;
        switch (pid.hashCode()) {
            case -1822884084:
                if (pid.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1819630261:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1817691924:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1796419228:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1309274422:
                if (pid.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1308265372:
                if (pid.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1281119909:
                if (pid.equals(ProductId.ID_RC_B2)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1265646206:
                if (pid.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1084555505:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1082613022:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -969622016:
                if (pid.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -961541705:
                if (pid.equals(ProductId.ID_SMART_PANEL_S6B)) {
                    c2 = 11;
                    break;
                }
                break;
            case -835060954:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -833770237:
                if (pid.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -732569219:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 14;
                    break;
                }
                break;
            case -324427448:
                if (pid.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 15;
                    break;
                }
                break;
            case -249671171:
                if (pid.equals(ProductId.ID_RC_B5)) {
                    c2 = 16;
                    break;
                }
                break;
            case -208296259:
                if (pid.equals(ProductId.ID_RC4)) {
                    c2 = 17;
                    break;
                }
                break;
            case -207348713:
                if (pid.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = 18;
                    break;
                }
                break;
            case -206567420:
                if (pid.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = 19;
                    break;
                }
                break;
            case -206510721:
                if (pid.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = 20;
                    break;
                }
                break;
            case -206454022:
                if (pid.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = 21;
                    break;
                }
                break;
            case 225641606:
                if (pid.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 22;
                    break;
                }
                break;
            case 294483828:
                if (pid.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 23;
                    break;
                }
                break;
            case 356111630:
                if (pid.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = 24;
                    break;
                }
                break;
            case 356193315:
                if (pid.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = 25;
                    break;
                }
                break;
            case 359647590:
                if (pid.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 376429092:
                if (pid.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = 27;
                    break;
                }
                break;
            case 376488674:
                if (pid.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = 28;
                    break;
                }
                break;
            case 427686243:
                if (pid.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = 29;
                    break;
                }
                break;
            case 613226983:
                if (pid.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                    c2 = 30;
                    break;
                }
                break;
            case 811752507:
                if (pid.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 31;
                    break;
                }
                break;
            case 1097035898:
                if (pid.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 1181428532:
                if (pid.equals(ProductId.ID_SMART_PANEL_GQ)) {
                    c2 = '!';
                    break;
                }
                break;
            case 1309050445:
                if (pid.equals(ProductId.ID_SMART_PANEL_GQX)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 1473345811:
                if (pid.equals(ProductId.ID_RC_B1)) {
                    c2 = '#';
                    break;
                }
                break;
            case 1951402182:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '$';
                    break;
                }
                break;
            case 1951547293:
                if (pid.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '%';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case '\f':
            case '\r':
            case 18:
            case 20:
            case 26:
            case 30:
                return 1;
            case 2:
            case 5:
            case 15:
            case 19:
            case 23:
            case 31:
            case '%':
                return 2;
            case 3:
            case '$':
                return 3;
            case 4:
            case 6:
            case 7:
            case 14:
            case 16:
            case 17:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case '#':
                return 4;
            case '\b':
            case '\t':
            case 11:
                return 6;
            case '\n':
            case 29:
            case '!':
            case '\"':
                return 12;
            case ' ':
                return 8;
            default:
                return 0;
        }
    }

    public static int getSceneCount(String pid) {
        pid.hashCode();
        switch (pid) {
            case "4122243156296320":
            case "3508084028410496":
            case "3537619681035968":
                return 8;
            case "3557654002353408":
            case "3486586935738368":
            case "3503908278750336":
            case "3503908725640320":
            case "120042616091901":
            case "120042616094101":
            case "120042616101901":
            case "120042616103901":
            case "3486587348451328":
            case "3503907950824576":
            case "3486587769094144":
                return 4;
            default:
                return 0;
        }
    }

    public static int getLightColorType(Object object) {
        if (object instanceof Device) {
            return getLightColorType((Device) object);
        }
        if (object instanceof Group) {
            return getLightColorType((Group) object);
        }
        return 0;
    }

    public static int getMainGroupLightColorType(Object object) {
        if (object instanceof Group) {
            return getMainLightColorType((Group) object);
        }
        return 0;
    }

    public static long getControlId(Object object) {
        if (object instanceof Device) {
            return ((Device) object).getId();
        }
        if (object instanceof Group) {
            return ((Group) object).getId();
        }
        return 0L;
    }

    public static boolean isGroup(Object object) {
        return object instanceof Group;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static int getLightColorType(Device device) {
        if (device == null) {
            return 0;
        }
        String productId = device.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1777527685:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_DIM)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1777494050:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_CT)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1776694498:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGB)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1776638760:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBW)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1776570529:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBWY)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = 14;
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = 15;
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 16;
                    break;
                }
                break;
            case -1281119909:
                if (productId.equals(ProductId.ID_RC_B2)) {
                    c2 = 17;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 18;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 19;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 20;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 21;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 22;
                    break;
                }
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 23;
                    break;
                }
                break;
            case -249671171:
                if (productId.equals(ProductId.ID_RC_B5)) {
                    c2 = 24;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 25;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = 27;
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = 28;
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = 29;
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = 30;
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = 31;
                    break;
                }
                break;
            case 414687077:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = '!';
                    break;
                }
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = '#';
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = '$';
                    break;
                }
                break;
            case 1473345811:
                if (productId.equals(ProductId.ID_RC_B1)) {
                    c2 = '%';
                    break;
                }
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = '(';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = ')';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '*';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '+';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
            case 25:
            case 26:
            case 27:
            case '!':
            case '\"':
            case '#':
            case '&':
            case '(':
            case '*':
            case '+':
                break;
            case '\t':
            case 23:
            case 28:
            case '%':
                return 1;
            case '\n':
            case 17:
            case 29:
                return 2;
            case 11:
                return 3;
            case '\f':
            case 30:
                return 4;
            case '\r':
            case 31:
                return 5;
            case 16:
            case ',':
                return 14;
            case ' ':
                return 10009;
            case '$':
                return 17;
            case '\'':
                return LightType.MUSIC_PLAYER;
            case ')':
                DryContactBleParam dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class);
                if (dryContactBleParam == null) {
                    return 0;
                }
                if (dryContactBleParam.getSubType() == 0) {
                    return 12;
                }
                if (dryContactBleParam.getSubType() == 3) {
                    return 16;
                }
                break;
            default:
                return 0;
        }
        if (device.getParam(BleParam.class) == null || ((BleParam) device.getParam(BleParam.class)).getDeviceType() == 0) {
            return 5;
        }
        return ((BleParam) device.getParam(BleParam.class)).getDeviceType();
    }

    private static int getLightColorType(Group group) {
        if (group == null) {
            return 0;
        }
        String groupKey = group.getGroupKey();
        if (ProductId.WIFI_GROUP_RGB_LIGHT.equalsIgnoreCase(groupKey) || ProductId.BLE_GROUP_RGB_LIGHT.equalsIgnoreCase(groupKey)) {
            return 3;
        }
        if (ProductId.WIFI_GROUP_DIM_LIGHT.equalsIgnoreCase(groupKey) || ProductId.BLE_GROUP_DIM_LIGHT.equalsIgnoreCase(groupKey)) {
            return 1;
        }
        if (ProductId.WIFI_GROUP_CT_LIGHT.equalsIgnoreCase(groupKey) || ProductId.BLE_GROUP_CT_LIGHT.equalsIgnoreCase(groupKey)) {
            return 2;
        }
        if (ProductId.WIFI_GROUP_RGBW_LIGHT.equalsIgnoreCase(groupKey) || ProductId.BLE_GROUP_RGBW_LIGHT.equalsIgnoreCase(groupKey)) {
            return 4;
        }
        if (ProductId.WIFI_GROUP_RGBWY_LIGHT.equalsIgnoreCase(groupKey) || ProductId.BLE_GROUP_RGBWY_LIGHT.equalsIgnoreCase(groupKey)) {
            return 5;
        }
        if (ProductId.BLE_GROUP_RGBWY_CC_LIGHT.equalsIgnoreCase(groupKey)) {
            return 20;
        }
        if (ProductId.BLE_GROUP_SWITCH.equalsIgnoreCase(groupKey)) {
            return 7;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S1C.equalsIgnoreCase(groupKey)) {
            return 8;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S2C.equalsIgnoreCase(groupKey)) {
            return 9;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S3C.equalsIgnoreCase(groupKey)) {
            return 10;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S4M.equalsIgnoreCase(groupKey)) {
            return 11;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S4.equalsIgnoreCase(groupKey)) {
            return 18;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S6PRO.equalsIgnoreCase(groupKey)) {
            return 19;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_G4.equalsIgnoreCase(groupKey)) {
            return 21;
        }
        if (ProductId.BLE_GROUP_CURTAIN.equalsIgnoreCase(groupKey)) {
            return 14;
        }
        if (ProductId.BLE_GROUP_DRY_CURTAIN.equalsIgnoreCase(groupKey)) {
            return 12;
        }
        if (ProductId.BLE_GROUP_WAVE_SENSOR_MR03.equalsIgnoreCase(groupKey)) {
            return 13;
        }
        if (ProductId.BLE_GROUP_WAVE_SENSOR_MR04.equalsIgnoreCase(groupKey)) {
            return 15;
        }
        if (ProductId.BLE_GROUP_DRY_DREAM_CURTAIN.equalsIgnoreCase(groupKey)) {
            return 16;
        }
        if (group.getControlType() != null) {
            return Integer.parseInt(group.getControlType());
        }
        return 0;
    }

    private static int getMainLightColorType(Group group) {
        if (group == null) {
            return 0;
        }
        String mainGroupKey = group.getMainGroupKey();
        if (ProductId.WIFI_GROUP_RGB_LIGHT.equalsIgnoreCase(mainGroupKey) || ProductId.BLE_GROUP_RGB_LIGHT.equalsIgnoreCase(mainGroupKey)) {
            return 3;
        }
        if (ProductId.WIFI_GROUP_DIM_LIGHT.equalsIgnoreCase(mainGroupKey) || ProductId.BLE_GROUP_DIM_LIGHT.equalsIgnoreCase(mainGroupKey)) {
            return 1;
        }
        if (ProductId.WIFI_GROUP_CT_LIGHT.equalsIgnoreCase(mainGroupKey) || ProductId.BLE_GROUP_CT_LIGHT.equalsIgnoreCase(mainGroupKey)) {
            return 2;
        }
        if (ProductId.WIFI_GROUP_RGBW_LIGHT.equalsIgnoreCase(mainGroupKey) || ProductId.BLE_GROUP_RGBW_LIGHT.equalsIgnoreCase(mainGroupKey)) {
            return 4;
        }
        if (ProductId.WIFI_GROUP_RGBWY_LIGHT.equalsIgnoreCase(mainGroupKey) || ProductId.BLE_GROUP_RGBWY_LIGHT.equalsIgnoreCase(mainGroupKey)) {
            return 5;
        }
        if (ProductId.BLE_GROUP_RGBWY_CC_LIGHT.equalsIgnoreCase(mainGroupKey)) {
            return 20;
        }
        if (ProductId.BLE_GROUP_SWITCH.equalsIgnoreCase(mainGroupKey)) {
            return 7;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S1C.equalsIgnoreCase(mainGroupKey)) {
            return 8;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S2C.equalsIgnoreCase(mainGroupKey)) {
            return 9;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S3C.equalsIgnoreCase(mainGroupKey)) {
            return 10;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S4M.equalsIgnoreCase(mainGroupKey)) {
            return 11;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S4.equalsIgnoreCase(mainGroupKey)) {
            return 18;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_S6PRO.equalsIgnoreCase(mainGroupKey)) {
            return 19;
        }
        if (ProductId.BLE_GROUP_SWITCH_PANEL_G4.equalsIgnoreCase(mainGroupKey)) {
            return 21;
        }
        if (ProductId.BLE_GROUP_CURTAIN.equalsIgnoreCase(mainGroupKey)) {
            return 14;
        }
        if (ProductId.BLE_GROUP_DRY_CURTAIN.equalsIgnoreCase(mainGroupKey)) {
            return 12;
        }
        if (ProductId.BLE_GROUP_WAVE_SENSOR_MR03.equalsIgnoreCase(mainGroupKey)) {
            return 13;
        }
        if (ProductId.BLE_GROUP_WAVE_SENSOR_MR04.equalsIgnoreCase(mainGroupKey)) {
            return 15;
        }
        if (ProductId.BLE_GROUP_DRY_DREAM_CURTAIN.equalsIgnoreCase(mainGroupKey)) {
            return 16;
        }
        if (group.getMainControlType() != null) {
            return Integer.parseInt(group.getMainControlType());
        }
        return 0;
    }

    public static String getIrProductIdByType(String type) {
        if (TextUtils.isEmpty(type)) {
            return "";
        }
        type.hashCode();
        switch (type) {
        }
        return "";
    }

    public static boolean isBleLight(String productId) {
        ProductInfo productInfoByPid = getProductInfoByPid(productId);
        if (productInfoByPid != null && "02".equalsIgnoreCase(productInfoByPid.getProductKey())) {
            return !ProductId.ID_RS485_BLE.equals(productId);
        }
        return isE6Panel(productId);
    }

    public static boolean isBleSwitch(String productId) {
        productId.hashCode();
        return productId.equals(ProductId.ID_BLE_SWITCH);
    }

    public static boolean isSuperPanel(String productId) {
        productId.hashCode();
        switch (productId) {
            case "123050811340901":
            case "123050811353501":
            case "3683388245101248":
            case "122042815485901":
            case "122080911090801":
            case "121052512023201":
            case "120010615085201":
                return true;
            default:
                return false;
        }
    }

    public static boolean isDcaSuperPanel(String productId) {
        productId.hashCode();
        switch (productId) {
            case "123050811340901":
            case "123050811353501":
            case "3683388245101248":
            case "122042815485901":
            case "122080911090801":
            case "121052512023201":
                return true;
            default:
                return false;
        }
    }

    public static boolean isSmartPanelDevice(String productId) {
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "3683369128495808":
            case "4249823578721536":
            case "3701704216101056":
            case "123031312002001":
            case "221042516351701":
            case "123072510445601":
            case "221030816330401":
            case "3701703750123712":
            case "121031814513301":
            case "121042516340801":
            case "121042516345401":
                return true;
            default:
                return false;
        }
    }

    public static boolean isScreenPanelDevice(String productId) {
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
                return true;
            default:
                return false;
        }
    }

    public static boolean isScreenPanel(String productId) {
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "3683369128495808":
            case "3701704216101056":
            case "3701703750123712":
                return true;
            default:
                return false;
        }
    }

    public static boolean isRcB(String productId) {
        productId.hashCode();
        switch (productId) {
            case "3503908278750336":
            case "3508084028410496":
            case "3503908725640320":
            case "3503907950824576":
                return true;
            default:
                return false;
        }
    }

    public static boolean isEurPanel(String productId) {
        productId.hashCode();
        switch (productId) {
            case "3557654002353408":
            case "3486586935738368":
            case "3537619681035968":
            case "3486587348451328":
            case "3486587769094144":
                return true;
            default:
                return false;
        }
    }

    public static boolean isAsPanel(String productId) {
        productId.hashCode();
        switch (productId) {
            case "4122243156296320":
            case "120042616091901":
            case "120042616094101":
            case "120042616101901":
            case "120042616103901":
                return true;
            default:
                return false;
        }
    }

    public static boolean isE6Panel(String productId) {
        productId.hashCode();
        switch (productId) {
            case "4002207473371776":
            case "4002205681371776":
            case "4002206372514432":
            case "4002206816422528":
                return true;
            default:
                return false;
        }
    }

    public static boolean isCentralAcSubDevice(String productId) {
        productId.hashCode();
        switch (productId) {
            case "AC_1":
            case "AC_2":
            case "AC_3":
                return true;
            default:
                return false;
        }
    }

    public static boolean isCgdPro(Object object) {
        return getLightColorType(object) == 10009;
    }

    public static boolean isDaliLightGroup(Object object) {
        switch (getLightColorType(object)) {
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
                return true;
            default:
                return false;
        }
    }

    public static boolean isRelaySeparationSub(Object object) {
        return RelaySeparationHelper.isRelaySeparationSub(object);
    }

    public static boolean isWaveSensor(Object object) {
        if (object instanceof Device) {
            Device device = (Device) object;
            return device.getProductId().equals(ProductId.ID_SENSOR_MR03) || device.getProductId().equals(ProductId.ID_SENSOR_MR04) || device.getProductId().equals(ProductId.ID_SENSOR_MS03);
        }
        if (!(object instanceof Group)) {
            return false;
        }
        Group group = (Group) object;
        return ProductId.BLE_GROUP_WAVE_SENSOR_MR03.equals(group.getGroupKey()) || ProductId.BLE_GROUP_WAVE_SENSOR_MR04.equals(group.getGroupKey()) || ProductId.BLE_GROUP_WAVE_SENSOR_MS03.equals(group.getGroupKey());
    }

    public static boolean isEurPanel(Object object) {
        if (object instanceof Device) {
            return isEurPanel(((Device) object).getProductId());
        }
        if (!(object instanceof Group)) {
            return false;
        }
        int lightColorType = getLightColorType(object);
        return lightColorType == 22 || lightColorType == 23 || lightColorType == 24;
    }

    public static boolean isAsPanel(Object object) {
        if (object instanceof Device) {
            return isAsPanel(((Device) object).getProductId());
        }
        if (!(object instanceof Group)) {
            return false;
        }
        int lightColorType = getLightColorType(object);
        return lightColorType == 27 || lightColorType == 28 || lightColorType == 30 || lightColorType == 29;
    }

    public static boolean isEbSupportLight(Object object) {
        if (object instanceof Device) {
            Device device = (Device) object;
            if (isE6Panel(device.getProductId())) {
                return true;
            }
            if (device.getExtParam() != null) {
                BaseExtParam baseExtParam = (BaseExtParam) device.getExtParam(BaseExtParam.class);
                return "B5-3A".equals(baseExtParam.getBinName()) || "B2-8A".equals(baseExtParam.getBinName()) || ("B5-DMX-4A-S".equals(baseExtParam.getBinName()) && aboveVersion(device, "000.005.000")) || ("CG-DAM-PRO".equals(baseExtParam.getBinName()) && aboveVersion(device, "000.009.000"));
            }
        } else if (object instanceof Group) {
            Group group = (Group) object;
            if (!group.getDeviceIds().isEmpty()) {
                Iterator<Long> it = group.getDeviceIds().iterator();
                while (it.hasNext()) {
                    if (!isEbSupportLight(Injection.repo().device().getDeviceByDeviceId(it.next().longValue()))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean supportCtGeneralMode(Object object) {
        if (isEbSupportLight(object) || getLightColorType(object) == 28) {
            return true;
        }
        if (getLightColorType(object) == 23) {
            return !EurHelper.hasBindNotEbLight(object);
        }
        return (object instanceof Device) && ((Device) object).getProductId().equals(ProductId.ID_AS_PANEL_U2S);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0028, code lost:
    
        if (r7.equals("0E") == false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00d4, code lost:
    
        if (r7.equals(com.ltech.smarthome.model.product.ProductId.ID_KNOB_PANEL_E6M) == false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean canAutoAdd(com.ltech.smarthome.model.product.ProductInfo r7) {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.model.repo.ProductRepository.canAutoAdd(com.ltech.smarthome.model.product.ProductInfo):boolean");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static boolean needLocation(String productId) {
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -2060969856:
                if (productId.equals(ProductId.ID_AS_PANEL_UB8)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1698123058:
                if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1343252468:
                if (productId.equals(ProductId.ID_RS485_BLE)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 14;
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = 15;
                    break;
                }
                break;
            case -1201890867:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ_PRO)) {
                    c2 = 16;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 17;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 18;
                    break;
                }
                break;
            case -1073881216:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                    c2 = 19;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 20;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 21;
                    break;
                }
                break;
            case -833770237:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = 22;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 23;
                    break;
                }
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 24;
                    break;
                }
                break;
            case 13862565:
                if (productId.equals(ProductId.ID_BLE_RS8)) {
                    c2 = 25;
                    break;
                }
                break;
            case 70457728:
                if (productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 27;
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = 28;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 29;
                    break;
                }
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = 30;
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = 31;
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 359647590:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = '!';
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = '#';
                    break;
                }
                break;
            case 414687077:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                    c2 = '$';
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = '%';
                    break;
                }
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 612512450:
                if (productId.equals(ProductId.ID_HOME_KIT)) {
                    c2 = '(';
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = ')';
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '*';
                    break;
                }
                break;
            case 1181428532:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ)) {
                    c2 = '+';
                    break;
                }
                break;
            case 1309050445:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQX)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
            case 1479279198:
                if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = Soundex.SILENT_MARKER;
                    break;
                }
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = '/';
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = '0';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = '1';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '2';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '3';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = '4';
                    break;
                }
                break;
            case 2088187733:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = '5';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
                return true;
            default:
                return false;
        }
    }

    public static boolean needCheckOnlineState(Group group) {
        int lightColorType = getLightColorType(group);
        return lightColorType == 1 || lightColorType == 2 || lightColorType == 3 || lightColorType == 4 || lightColorType == 5 || lightColorType == 7 || lightColorType == 20;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static boolean needCheckOnlineState(String productId, String subProductKey) {
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -2060969856:
                if (productId.equals(ProductId.ID_AS_PANEL_UB8)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1777527685:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_DIM)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1777494050:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_CT)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1776694498:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGB)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1776638760:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBW)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1776570529:
                if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBWY)) {
                    c2 = 14;
                    break;
                }
                break;
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = 15;
                    break;
                }
                break;
            case -1642166464:
                if (productId.equals(ProductId.ID_BLE_HAM)) {
                    c2 = 16;
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = 17;
                    break;
                }
                break;
            case -1343252468:
                if (productId.equals(ProductId.ID_RS485_BLE)) {
                    c2 = 18;
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 19;
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = 20;
                    break;
                }
                break;
            case -1201890867:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ_PRO)) {
                    c2 = 21;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 22;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 23;
                    break;
                }
                break;
            case -1073881216:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                    c2 = 24;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 25;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case -833770237:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = 27;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 28;
                    break;
                }
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 29;
                    break;
                }
                break;
            case 2003796:
                if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                    c2 = 30;
                    break;
                }
                break;
            case 2003797:
                if (productId.equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
                    c2 = 31;
                    break;
                }
                break;
            case 2003798:
                if (productId.equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 13862565:
                if (productId.equals(ProductId.ID_BLE_RS8)) {
                    c2 = '!';
                    break;
                }
                break;
            case 70457728:
                if (productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = '#';
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = '$';
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = '%';
                    break;
                }
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = '(';
                    break;
                }
                break;
            case 359647590:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = ')';
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = '*';
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = '+';
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = Soundex.SILENT_MARKER;
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                    break;
                }
                break;
            case 612512450:
                if (productId.equals(ProductId.ID_HOME_KIT)) {
                    c2 = '/';
                    break;
                }
                break;
            case 613226983:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                    c2 = '0';
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = '1';
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '2';
                    break;
                }
                break;
            case 1378424449:
                if (productId.equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY)) {
                    c2 = '3';
                    break;
                }
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = '4';
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = '5';
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = '6';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = '7';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '8';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '9';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = ':';
                    break;
                }
                break;
            case 2002295507:
                if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                    c2 = ';';
                    break;
                }
                break;
            case 2088187733:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = Typography.less;
                    break;
                }
                break;
        }
        switch (c2) {
            case 21:
                if (subProductKey.equals(ProductId.BLE_SMART_PANEL_SUB_TYPE_GQ_MAX)) {
                    return false;
                }
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
            case ';':
            case '<':
                return true;
            default:
                return false;
        }
    }

    public static boolean supportLightMode(String productId) {
        if (isBleLight(productId)) {
            return true;
        }
        productId.hashCode();
        switch (productId) {
            case "3486586935738368":
            case "120042616091901":
            case "120042616094101":
            case "120042616101901":
            case "120042616103901":
            case "3486587348451328":
            case "3486587769094144":
                return true;
            default:
                return false;
        }
    }

    public static boolean supportGradientGroup(Group group) {
        if (group == null) {
            return false;
        }
        int lightColorType = getLightColorType(group);
        return lightColorType == 3 || lightColorType == 4 || lightColorType == 5 || lightColorType == 20;
    }

    public static boolean supportDynamicMode(Object object) {
        if (object instanceof Device) {
            return !ProductId.ID_KNOB_PANEL_E6D.equals(((Device) object).getProductId());
        }
        return true;
    }

    public static boolean supportDynamicScene(Object object) {
        if (getLightColorType(object) == 20) {
            return true;
        }
        if (object instanceof Device) {
            Device device = (Device) object;
            if (device.getExtParam() != null) {
                return "HB4".equals(((BaseExtParam) device.getExtParam(BaseExtParam.class)).getBinName());
            }
        } else if (object instanceof Group) {
            Group group = (Group) object;
            if (!group.getDeviceIds().isEmpty()) {
                Iterator<Long> it = group.getDeviceIds().iterator();
                while (it.hasNext()) {
                    if (!supportDynamicScene(Injection.repo().device().getDeviceByDeviceId(it.next().longValue()))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean aboveVersion(Device device, String version) {
        return !TextUtils.isEmpty(device.getMcuversion()) && device.getMcuversion().substring(4).compareTo(version) >= 0;
    }

    public static boolean needPublishAddress(Object object) {
        return EurHelper.needPublishAddress(object) || AsHelper.needPublishAddress(object);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean filterDeviceForSuperPanel(Device device, Device superPanelDevice) {
        char c2;
        char c3;
        if (device.isVirtual() || 31 == getLightColorType(device)) {
            return false;
        }
        if (device.isSubDevice()) {
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId.hashCode()) {
                case -2133025271:
                    if (productId.equals(ProductId.CGRS8_SUB_DEVICE)) {
                        c3 = 0;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1805322688:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                        c3 = 1;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1805199680:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                        c3 = 2;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1804340546:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                        c3 = 3;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1804278081:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                        c3 = 4;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1803448738:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                        c3 = 5;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 2003796:
                    if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                        c3 = 6;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 2003797:
                    if (productId.equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
                        c3 = 7;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 2003798:
                    if (productId.equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
                        c3 = '\b';
                        break;
                    }
                    c3 = 65535;
                    break;
                case 2256543:
                    if (productId.equals(ProductId.ID_IR_AC)) {
                        c3 = '\t';
                        break;
                    }
                    c3 = 65535;
                    break;
                case 2256546:
                    if (productId.equals(ProductId.ID_IR_FAN)) {
                        c3 = '\n';
                        break;
                    }
                    c3 = 65535;
                    break;
                case 69953013:
                    if (productId.equals(ProductId.ID_IR_CURTAIN)) {
                        c3 = 11;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 166485422:
                    if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                        c3 = '\f';
                        break;
                    }
                    c3 = 65535;
                    break;
                default:
                    c3 = 65535;
                    break;
            }
            switch (c3) {
                case 0:
                    if (ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId())) {
                        return false;
                    }
                    return SuperPanelVersionHelper.canAddRs8(superPanelDevice);
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case '\t':
                case '\n':
                case 11:
                    return !ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId());
                case 6:
                case 7:
                case '\b':
                    return true;
                case '\f':
                    if (!isRelaySeparationSub(device) || device.getSubhide() == 1) {
                        return false;
                    }
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
                    if (deviceByDeviceId == null || deviceByDeviceId.getParam() == null || deviceByDeviceId.getParam(BleParam.class) == null) {
                        return SuperPanelVersionHelper.canAddG4(superPanelDevice);
                    }
                    return ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getGroupId() == 0;
                default:
                    return false;
            }
        }
        String productId2 = device.getProductId();
        productId2.hashCode();
        switch (productId2.hashCode()) {
            case -1819630261:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1817691924:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1805322688:
                if (productId2.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -1805199680:
                if (productId2.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -1804340546:
                if (productId2.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1804278081:
                if (productId2.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -1803448738:
                if (productId2.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -1796419228:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -1710907378:
                if (productId2.equals(ProductId.ID_BLE_KBS)) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case -1287620343:
                if (productId2.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -1084555505:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case -1082613022:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -969622016:
                if (productId2.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case -835060954:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case -732569219:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case -728269602:
                if (productId2.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case 155753896:
                if (productId2.equals(ProductId.ID_DOOR_SENSOR)) {
                    c2 = 16;
                    break;
                }
                c2 = 65535;
                break;
            case 166485422:
                if (productId2.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 17;
                    break;
                }
                c2 = 65535;
                break;
            case 225641606:
                if (productId2.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 18;
                    break;
                }
                c2 = 65535;
                break;
            case 312618751:
                if (productId2.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = 19;
                    break;
                }
                c2 = 65535;
                break;
            case 353722044:
                if (productId2.equals(ProductId.ID_WIFI_CAMERA)) {
                    c2 = 20;
                    break;
                }
                c2 = 65535;
                break;
            case 427686243:
                if (productId2.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = 21;
                    break;
                }
                c2 = 65535;
                break;
            case 439998223:
                if (productId2.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = 22;
                    break;
                }
                c2 = 65535;
                break;
            case 662799966:
                if (productId2.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = 23;
                    break;
                }
                c2 = 65535;
                break;
            case 1647983530:
                if (productId2.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = 24;
                    break;
                }
                c2 = 65535;
                break;
            case 1921260107:
                if (productId2.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 25;
                    break;
                }
                c2 = 65535;
                break;
            case 1951402182:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                c2 = 65535;
                break;
            case 1951547293:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 27;
                    break;
                }
                c2 = 65535;
                break;
            case 1976427583:
                if (productId2.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 28;
                    break;
                }
                c2 = 65535;
                break;
            case 2061235487:
                if (productId2.equals(ProductId.ID_WIFI_SONOS)) {
                    c2 = 29;
                    break;
                }
                c2 = 65535;
                break;
            case 2088187733:
                if (productId2.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                    c2 = 30;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 7:
            case '\n':
            case 11:
            case '\r':
            case 14:
            case 18:
            case 26:
            case 27:
                return ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            case 6:
                if (getLightColorType(device) != 20 || ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId())) {
                    return true;
                }
                return SuperPanelVersionHelper.canAddW5b(superPanelDevice);
            case 2:
            case 3:
            case 4:
            case 5:
            case '\t':
            case 17:
            case 28:
                return true;
            case '\b':
            case 15:
            case 19:
            case 22:
            case 24:
                return !ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId());
            case '\f':
            case 21:
                return SuperPanelVersionHelper.canAddG4(superPanelDevice) && ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            case 16:
            case 23:
                return !ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId());
            case 20:
                return ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(superPanelDevice.getProductId());
            case 25:
                return (ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId()) || ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 1 || ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 2) ? false : true;
            case 29:
                if (ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId())) {
                    return false;
                }
                return SuperPanelVersionHelper.canAddSonos(superPanelDevice);
            case 30:
                return !ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId()) && SuperPanelVersionHelper.canAddG4(superPanelDevice) && ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            default:
                return false;
        }
    }

    public static boolean filterGroupForSuperPanel(Group group, Device superPanelDevice) {
        int lightColorType;
        if (group.isVirtual()) {
            return false;
        }
        if ((!isDaliLightGroup(group) || !ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId())) && (lightColorType = getLightColorType(group)) != 13) {
            if (lightColorType == 20) {
                if (ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId())) {
                    return true;
                }
                return SuperPanelVersionHelper.canAddW5b(superPanelDevice);
            }
            if (lightColorType != 15) {
                if (lightColorType != 16) {
                    switch (lightColorType) {
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                            break;
                        case 26:
                            break;
                        default:
                            if (!RelaySeparationHelper.isRelaySeparationSub(group) || group.getSubhide() != 1) {
                            }
                            break;
                    }
                    return false;
                }
                return !ProductId.ID_SMART_PANEL_GQ_PRO.equals(superPanelDevice.getProductId());
            }
        }
        return false;
    }

    public static boolean filterGroupForMatter(Group group, Device superPanelDevice) {
        if (group.isVirtual()) {
            return false;
        }
        switch (getLightColorType(group)) {
        }
        return false;
    }

    public static boolean filterDeviceForMatter(Device device, Device superPanelDevice) {
        if (device.isVirtual()) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "3683369128495808":
            case "4249823578721536":
            case "221042516351701":
            case "123072510445601":
            case "221030816330401":
            case "121042516340801":
            case "121042516345401":
                if (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                }
                break;
            case "3701704216101056":
            case "3701703750123712":
                if (!SuperPanelVersionHelper.canAddG4(superPanelDevice) || ((BleParam) device.getParam(BleParam.class)).getGroupId() != 0) {
                }
                break;
        }
        return false;
    }
}