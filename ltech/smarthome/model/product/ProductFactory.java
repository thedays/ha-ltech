package com.ltech.smarthome.model.product;

import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;

/* loaded from: classes4.dex */
public final class ProductFactory {
    public static final int SINGLE_PRODUCT = 1;
    public static final int SUB_DEVICE = 2;

    private ProductFactory() {
    }

    public ProductInfo createWifiLightDim() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.ID_WIFI_LIGHT_DIM).agreementId(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.wifi_light_dim).macFlag(1).groupKey(ProductId.WIFI_GROUP_DIM_LIGHT).build();
    }

    public ProductInfo createWifiLightCt() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.ID_WIFI_LIGHT_CT).agreementId(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.wifi_light_ct).macFlag(1).groupKey(ProductId.WIFI_GROUP_CT_LIGHT).build();
    }

    public ProductInfo createWifiLightRgb() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.ID_WIFI_LIGHT_RGB).agreementId(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.wifi_light_rgb).macFlag(1).groupKey(ProductId.WIFI_GROUP_RGB_LIGHT).build();
    }

    public ProductInfo createWifiLightRgbw() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.ID_WIFI_LIGHT_RGBW).agreementId(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.wifi_light_rgbw).macFlag(1).groupKey(ProductId.WIFI_GROUP_RGBW_LIGHT).build();
    }

    public ProductInfo createWifiLightRgbwy() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.ID_WIFI_LIGHT_RGBWY).agreementId(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.wifi_light_rgbwy).macFlag(1).groupKey(ProductId.WIFI_GROUP_RGBWY_LIGHT).build();
    }

    public ProductInfo createBleLightDim() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_DIM).agreementId(2).productKey("02").subProductKey("01").controlType(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_dim).macFlag(1).groupKey(ProductId.BLE_GROUP_DIM_LIGHT).build();
    }

    public ProductInfo createBleLightGam() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_DIM).agreementId(2).productKey("02").subProductKey("01").controlType(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_gam).addNameRes(R.string.add_gam_name).macFlag(1).groupKey(ProductId.BLE_GROUP_DIM_LIGHT).build();
    }

    public ProductInfo createBleLightCt() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_CT).agreementId(2).productKey("02").subProductKey("02").controlType(2).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_ct).macFlag(1).groupKey(ProductId.BLE_GROUP_CT_LIGHT).build();
    }

    public ProductInfo createBleLightRgb() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_RGB).agreementId(2).productKey("02").subProductKey("03").controlType(3).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_rgb).macFlag(1).groupKey(ProductId.BLE_GROUP_RGB_LIGHT).build();
    }

    public ProductInfo createBleLightRgbw() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_RGBW).agreementId(2).productKey("02").subProductKey("04").controlType(4).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_rgbw).macFlag(1).groupKey(ProductId.BLE_GROUP_RGBW_LIGHT).build();
    }

    public ProductInfo createBleLightRgbwy() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_RGBWY).agreementId(2).productKey("02").subProductKey("05").controlType(5).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_rgbwy).macFlag(1).groupKey(ProductId.BLE_GROUP_RGBWY_LIGHT).build();
    }

    public ProductInfo createBleLightRgbwyCC() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_RGBWY).agreementId(2).productKey("02").subProductKey("0B").controlType(20).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_rgbwy).macFlag(1).groupKey(ProductId.BLE_GROUP_RGBWY_CC_LIGHT).build();
    }

    public ProductInfo createBleLightDam() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_DIM).agreementId(2).productKey("02").subProductKey("06").controlType(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_dam).addNameRes(R.string.add_cgdam_name).macFlag(1).groupKey(ProductId.BLE_GROUP_DIM_LIGHT).build();
    }

    public ProductInfo createBleLightCgd() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_DIM).agreementId(2).productKey("02").subProductKey("07").controlType(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_cgd).addNameRes(R.string.add_cgd_name).groupKey(ProductId.BLE_GROUP_DIM_LIGHT).macFlag(1).build();
    }

    public ProductInfo createBleLightCgt() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_DIM).agreementId(2).productKey("02").subProductKey("08").controlType(1).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_cgt).addNameRes(R.string.add_cgt_name).macFlag(1).groupKey(ProductId.BLE_GROUP_DIM_LIGHT).build();
    }

    public ProductInfo createBleLightSpi() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_SPI).agreementId(2).productKey("02").subProductKey("09").controlType(17).defaultIconRes(R.mipmap.ic_device_spi).defaultNameRes(R.string.ble_light_spi).addNameRes(R.string.add_cg_spi_name).macFlag(1).build();
    }

    public ProductInfo createBleLightHB4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_RGBW).agreementId(2).productKey("02").subProductKey("0E").controlType(4).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_rgbw).macFlag(1).groupKey(ProductId.BLE_GROUP_RGBW_LIGHT).build();
    }

    public ProductInfo createEurPanelEB1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_EUR_PANEL_EB1).agreementId(2).productKey("04").controlType(22).subProductKey(ProductId.BLE_EUR_PANEL_EB1).defaultIconRes(R.mipmap.ic_device_eur_device).defaultNameRes(R.string.eur_panel_eb1).addNameRes(R.string.eur_panel_eb1_short).macFlag(1).groupKey(ProductId.BLE_GROUP_EUR_PANEL_EB1).build();
    }

    public ProductInfo createEurPanelEB2() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_EUR_PANEL_EB2).agreementId(2).productKey("04").controlType(23).subProductKey(ProductId.BLE_EUR_PANEL_EB2).defaultIconRes(R.mipmap.ic_device_eur_device).defaultNameRes(R.string.eur_panel_eb2).addNameRes(R.string.eur_panel_eb2_short).macFlag(1).groupKey(ProductId.BLE_GROUP_EUR_PANEL_EB2).build();
    }

    public ProductInfo createEurPanelEB5() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_EUR_PANEL_EB5).agreementId(2).productKey("04").controlType(24).subProductKey(ProductId.BLE_EUR_PANEL_EB5).defaultIconRes(R.mipmap.ic_device_eur_device).defaultNameRes(R.string.eur_panel_eb5).addNameRes(R.string.eur_panel_eb5_short).macFlag(1).groupKey(ProductId.BLE_GROUP_EUR_PANEL_EB5).build();
    }

    public ProductInfo createEurPanelEB6() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_EUR_PANEL_EB6).agreementId(2).productKey("04").subProductKey(ProductId.BLE_EUR_PANEL_EB6).defaultIconRes(R.mipmap.ic_device_eur_eb6).defaultNameRes(R.string.eur_panel_eb6).addNameRes(R.string.eur_panel_eb6_short).macFlag(1).build();
    }

    public ProductInfo createEurPanelEB8() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_EUR_PANEL_EB8).agreementId(2).productKey("04").subProductKey(ProductId.BLE_EUR_PANEL_EB8).defaultIconRes(R.mipmap.ic_device_eur_eb8).defaultNameRes(R.string.eur_panel_eb8).addNameRes(R.string.eur_panel_eb8_short).macFlag(1).build();
    }

    public ProductInfo createKnobPanelE6A() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_KNOB_PANEL_E6A).agreementId(2).productKey("04").controlType(1).subProductKey("24").defaultIconRes(R.mipmap.ic_device_e6d).defaultNameRes(R.string.knob_panel_e6a).addNameRes(R.string.e6_ct_short).macFlag(1).build();
    }

    public ProductInfo createKnobPanelE6D() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_KNOB_PANEL_E6D).agreementId(2).productKey("04").controlType(1).subProductKey(ProductId.BLE_KNOB_PANEL_E6D).defaultIconRes(R.mipmap.ic_device_e6d).defaultNameRes(R.string.knob_panel_e6d).addNameRes(R.string.e6_rgbcw_short).macFlag(1).build();
    }

    public ProductInfo createKnobPanelE6M() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_KNOB_PANEL_E6M).agreementId(2).productKey("04").controlType(1).subProductKey(ProductId.BLE_KNOB_PANEL_E6M).defaultIconRes(R.mipmap.ic_device_e6d).defaultNameRes(R.string.knob_panel_e6m).addNameRes(R.string.e6_rgbcw_short).macFlag(1).build();
    }

    public ProductInfo createKnobPanelE6T() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_KNOB_PANEL_E6T).agreementId(2).productKey("04").controlType(1).subProductKey(ProductId.BLE_KNOB_PANEL_E6T).defaultIconRes(R.mipmap.ic_device_e6d).defaultNameRes(R.string.knob_panel_e6t).addNameRes(R.string.e6_dim_short).macFlag(1).build();
    }

    public ProductInfo createBleSwitch() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_SWITCH).agreementId(2).productKey("04").subProductKey("06").controlType(7).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_switch).addNameRes(R.string.ble_switch_short).macFlag(1).groupKey(ProductId.BLE_GROUP_SWITCH).build();
    }

    public ProductInfo createDryContactToBle() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_DRY_CONTACT_TO_BLE).agreementId(2).productKey("04").subProductKey("0D").defaultIconRes(R.mipmap.trig_icon_normal).defaultNameRes(R.string.app_str_product_cg_tb).addNameRes(R.string.add_name_cg_tb).macFlag(1).build();
    }

    public ProductInfo createBleDryContact() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_DRY_CONTACT).agreementId(2).productKey("04").subProductKey("0C").controlType(0).defaultIconRes(R.mipmap.trig_icon_normal).defaultNameRes(R.string.app_str_product_dry_contact).addNameRes(R.string.add_name_dry_contact).macFlag(1).build();
    }

    public ProductInfo createBleTrigCurtain() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_DRY_CONTACT).agreementId(2).productKey("04").subProductKey("0C").controlType(0).defaultIconRes(R.mipmap.ic_device_curtain).defaultNameRes(R.string.app_str_product_dry_contact).groupKey(ProductId.BLE_GROUP_DRY_CURTAIN).macFlag(1).build();
    }

    public ProductInfo createBleTrigDreamCurtain() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_DRY_CONTACT).agreementId(2).productKey("04").subProductKey("0C").controlType(0).defaultIconRes(R.mipmap.trig_cur_ic_dream).defaultNameRes(R.string.app_str_product_dry_contact).groupKey(ProductId.BLE_GROUP_DRY_DREAM_CURTAIN).macFlag(1).build();
    }

    public ProductInfo createBleCurtainCGCUR() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_CURTAIN).agreementId(2).productKey("07").subProductKey("01").controlType(14).defaultIconRes(R.mipmap.ic_device_curtain).defaultNameRes(R.string.app_str_ble_curtain_cg_cur).addNameRes(R.string.add_curtain_name).curtainType(1).groupKey(ProductId.BLE_GROUP_CURTAIN).macFlag(1).build();
    }

    public ProductInfo createBleCurtainCGCUR15() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_CURTAIN).agreementId(2).productKey("07").subProductKey("01").controlType(14).defaultIconRes(R.mipmap.ic_device_curtain).defaultNameRes(R.string.app_str_ble_curtain_cg_cur15).addNameRes(R.string.add_curtain_name).curtainType(2).groupKey(ProductId.BLE_GROUP_CURTAIN).macFlag(1).build();
    }

    public ProductInfo createBleCurtainCGCURH3() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_CURTAIN_CG_CURH3).agreementId(2).productKey("07").subProductKey("02").controlType(14).defaultIconRes(R.mipmap.ic_device_curtain).defaultNameRes(R.string.app_str_ble_curtain_cg_curh3).addNameRes(R.string.add_curtain_name).groupKey(ProductId.BLE_GROUP_CURTAIN).macFlag(1).build();
    }

    public ProductInfo createWifiGroupDim() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.CC.getModuleType(ProductId.WIFI_GROUP_DIM_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.WIFI_GROUP_DIM_LIGHT)).agreementId(1).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_wifi_group_dim_light).build();
    }

    public ProductInfo createWifiGroupCt() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.CC.getModuleType(ProductId.WIFI_GROUP_CT_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.WIFI_GROUP_CT_LIGHT)).agreementId(1).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_wifi_group_ct_light).build();
    }

    public ProductInfo createWifiGroupRgb() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.CC.getModuleType(ProductId.WIFI_GROUP_RGB_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.WIFI_GROUP_RGB_LIGHT)).agreementId(1).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_wifi_group_rgb_light).build();
    }

    public ProductInfo createWifiGroupRgbw() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.CC.getModuleType(ProductId.WIFI_GROUP_RGBW_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.WIFI_GROUP_RGBW_LIGHT)).agreementId(1).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_wifi_group_rgbw_light).build();
    }

    public ProductInfo createWifiGroupRgbwy() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.CC.getModuleType(ProductId.WIFI_GROUP_RGBWY_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.WIFI_GROUP_RGBWY_LIGHT)).agreementId(1).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_wifi_group_rgbwy_light).build();
    }

    public ProductInfo createBleGroupDim() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_DIM_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_DIM_LIGHT)).agreementId(2).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_bluetooth_group_dim_light).build();
    }

    public ProductInfo createBleGroupCt() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_CT_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_CT_LIGHT)).agreementId(2).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_bluetooth_group_ct_light).build();
    }

    public ProductInfo createBleGroupRgb() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_RGB_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_RGB_LIGHT)).agreementId(2).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_bluetooth_group_rgb_light).build();
    }

    public ProductInfo createBleGroupRgbw() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_RGBW_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_RGBW_LIGHT)).agreementId(2).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_bluetooth_group_rgbw_light).build();
    }

    public ProductInfo createBleGroupRgbwy() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_RGBWY_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_RGBWY_LIGHT)).agreementId(2).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_bluetooth_group_rgbwy_light).build();
    }

    public ProductInfo createBleGroupRgbwyCC() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_RGBWY_CC_LIGHT)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_RGBWY_CC_LIGHT)).agreementId(2).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_bluetooth_group_rgbwy_cc_light).build();
    }

    public ProductInfo createBleGroupSwitch() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_SWITCH)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_SWITCH)).agreementId(2).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.group_bluetooth_group_switch).build();
    }

    public ProductInfo createBleGroupKbs() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_KBS)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_KBS)).agreementId(2).defaultIconRes(R.mipmap.ic_device_light_group).defaultNameRes(R.string.app_str_ble_group_kbs).addNameRes(R.string.app_str_ble_group_kbs_name).build();
    }

    public ProductInfo createBleGroupSwitchPanelS1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_SWITCH_PANEL_S1C)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_SWITCH_PANEL_S1C)).agreementId(2).defaultIconRes(R.mipmap.group_icon_s1).defaultNameRes(R.string.group_s1c).addNameRes(R.string.add_switch_group_name).build();
    }

    public ProductInfo createBleGroupSwitchPanelS2() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_SWITCH_PANEL_S2C)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_SWITCH_PANEL_S2C)).agreementId(2).defaultIconRes(R.mipmap.group_icon_s2).defaultNameRes(R.string.group_s2c).addNameRes(R.string.add_switch_group_name).build();
    }

    public ProductInfo createBleGroupSwitchPanelS3() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_SWITCH_PANEL_S3C)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_SWITCH_PANEL_S3C)).agreementId(2).defaultIconRes(R.mipmap.group_icon_s3).defaultNameRes(R.string.group_s3c).addNameRes(R.string.add_switch_group_name).build();
    }

    public ProductInfo createBleGroupSwitchPanelS4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_SWITCH_PANEL_S4)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_SWITCH_PANEL_S4)).agreementId(2).defaultIconRes(R.mipmap.group_icon_s4).defaultNameRes(R.string.group_s4).addNameRes(R.string.add_switch_group_name).build();
    }

    public ProductInfo createBleGroupSwitchPanelS4M() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_SWITCH_PANEL_S4M)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_SWITCH_PANEL_S4M)).agreementId(2).defaultIconRes(R.mipmap.device_icon_spgroup).defaultNameRes(R.string.group_s4).addNameRes(R.string.add_switch_group_name).build();
    }

    public ProductInfo createBleGroupSwitchPanelS6Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_SWITCH_PANEL_S6PRO)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_SWITCH_PANEL_S6PRO)).agreementId(2).defaultIconRes(R.mipmap.devicegroup_icon_s6).defaultNameRes(R.string.group_s6_pro).addNameRes(R.string.add_switch_group_name).build();
    }

    public ProductInfo createBleGroupSwitchPanelG4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_SWITCH_PANEL_G4)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_SWITCH_PANEL_G4)).agreementId(2).defaultIconRes(R.mipmap.icon_g4_group).defaultNameRes(R.string.group_s6_pro).addNameRes(R.string.add_switch_group_name).build();
    }

    public ProductInfo createBleGroupCurtain() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_CURTAIN)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_CURTAIN)).agreementId(2).defaultIconRes(R.mipmap.ic_device_ic_curgroup).defaultNameRes(R.string.app_str_ble_group_curtain).build();
    }

    public ProductInfo createBleGroupDryCurtain() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_DRY_CURTAIN)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_DRY_CURTAIN)).agreementId(2).defaultIconRes(R.mipmap.ic_device_ic_curgroup).defaultNameRes(R.string.app_str_ble_group_dry_curtain).build();
    }

    public ProductInfo createBleGroupDryDreamCurtain() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_DRY_DREAM_CURTAIN)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_DRY_DREAM_CURTAIN)).agreementId(2).defaultIconRes(R.mipmap.trig_curgroup_ic_dream).defaultNameRes(R.string.app_str_group_dry_dream_curtain).build();
    }

    public ProductInfo createBleGroupWaveSensorMR03() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_WAVE_SENSOR_MR03)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_WAVE_SENSOR_MR03)).agreementId(2).defaultIconRes(R.mipmap.group_ic_mr03).defaultNameRes(R.string.app_str_group_wave_sensor_mr03).addNameRes(R.string.add_sensor_group_name).build();
    }

    public ProductInfo createBleGroupWaveSensorMR04() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_WAVE_SENSOR_MR04)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_WAVE_SENSOR_MR04)).agreementId(2).defaultIconRes(R.mipmap.group_ic_mr04).defaultNameRes(R.string.app_str_group_wave_sensor_mr04).addNameRes(R.string.add_sensor_group_name).build();
    }

    public ProductInfo createBleGroupWaveSensorMS03() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_WAVE_SENSOR_MR04)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_WAVE_SENSOR_MS03)).agreementId(2).defaultIconRes(R.mipmap.group_ic_mr03).defaultNameRes(R.string.app_str_group_wave_sensor_ms03).addNameRes(R.string.add_sensor_group_name).build();
    }

    public ProductInfo createBleGroupEb1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_EUR_PANEL_EB1)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_EUR_PANEL_EB1)).agreementId(2).defaultIconRes(R.mipmap.ic_eur_group).defaultNameStr(StringUtils.getString(R.string.eur_group_dim) + "(EB1)").addNameRes(R.string.eur_group_dim).build();
    }

    public ProductInfo createBleGroupEb2() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_EUR_PANEL_EB2)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_EUR_PANEL_EB2)).agreementId(2).defaultIconRes(R.mipmap.ic_eur_group).defaultNameStr(StringUtils.getString(R.string.eur_group_ct) + "(EB2)").addNameRes(R.string.eur_group_ct).build();
    }

    public ProductInfo createBleGroupUb1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_AS_PANEL_UB1)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_AS_PANEL_UB1)).agreementId(2).defaultIconRes(R.mipmap.devicegroup_ic_ub).defaultNameStr(StringUtils.getString(R.string.eur_group_dim) + "(UB1)").addNameRes(R.string.eur_group_dim).build();
    }

    public ProductInfo createBleGroupUb2() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_AS_PANEL_UB2)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_AS_PANEL_UB2)).agreementId(2).defaultIconRes(R.mipmap.devicegroup_ic_ub).defaultNameStr(StringUtils.getString(R.string.eur_group_ct) + "(UB2)").addNameRes(R.string.eur_group_ct).build();
    }

    public ProductInfo createBleGroupUb4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_AS_PANEL_UB4)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_AS_PANEL_UB4)).agreementId(2).defaultIconRes(R.mipmap.devicegroup_ic_ub).defaultNameStr(StringUtils.getString(R.string.eur_group_rgbw) + "(UB4)").addNameRes(R.string.eur_group_rgbw).build();
    }

    public ProductInfo createBleGroupUb5() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_AS_PANEL_UB5)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_AS_PANEL_UB5)).agreementId(2).defaultIconRes(R.mipmap.devicegroup_ic_ub).defaultNameStr(StringUtils.getString(R.string.eur_group_rgbcw) + "(UB5)").addNameRes(R.string.eur_group_rgbcw).build();
    }

    public ProductInfo createBleGroupEb5() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CC.getModuleType(ProductId.BLE_GROUP_EUR_PANEL_EB5)).hardwareId(ProductId.CC.getControlType(ProductId.BLE_GROUP_EUR_PANEL_EB5)).agreementId(2).defaultIconRes(R.mipmap.ic_eur_group).defaultNameStr(StringUtils.getString(R.string.eur_group_rgbcw) + "(EB5)").addNameRes(R.string.eur_group_rgbcw).build();
    }

    public ProductInfo createMeshGateway() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.ID_MESH_GATEWAY).agreementId(1).productKey("01").subProductKey("01").defaultIconRes(R.mipmap.ic_device_gateway).defaultNameRes(R.string.mesh_gateway_wifi).addNameRes(R.string.add_home_appliance_name).macFlag(1).build();
    }

    public ProductInfo createAndroidSuperPanel() {
        return ProductInfo.Builder.wifiBleProduct().productId(ProductId.ID_ANDROID_SUPER_PANEL).agreementId(3).productKey("01").subProductKey("02").defaultIconRes(R.mipmap.ic_device_super_panel).defaultNameRes(R.string.android_pad).addNameRes(R.string.add_super_panel_name).macFlag(1).build();
    }

    public ProductInfo createAndroidSuperPanelMini() {
        return ProductInfo.Builder.wifiBleProduct().productId(ProductId.ID_ANDROID_SUPER_PANEL_MINI).agreementId(3).productKey("01").subProductKey("04").defaultIconRes(R.mipmap.device_icon_mini).defaultNameRes(R.string.android_pad_mini).addNameRes(R.string.add_super_panel_mini_name).macFlag(1).build();
    }

    public ProductInfo createAndroidSuperPanel4S() {
        return ProductInfo.Builder.wifiBleProduct().productId(ProductId.ID_ANDROID_SUPER_PANEL_4S).agreementId(3).productKey("01").subProductKey("07").defaultIconRes(R.mipmap.device_icon_4s).defaultNameRes(R.string.android_pad_4s).addNameRes(R.string.add_super_panel_4S_name).macFlag(1).build();
    }

    public ProductInfo createAndroidSuperPanel6S() {
        return ProductInfo.Builder.wifiBleProduct().productId(ProductId.ID_ANDROID_SUPER_PANEL_6S).agreementId(3).productKey("01").subProductKey("06").defaultIconRes(R.mipmap.device_icon_6s).defaultNameRes(R.string.android_pad_6S).addNameRes(R.string.add_super_panel_6S_name).macFlag(1).build();
    }

    public ProductInfo createAndroidSuperPanelPro() {
        return ProductInfo.Builder.wifiBleProduct().productId(ProductId.ID_ANDROID_SUPER_PANEL_PRO).agreementId(3).productKey("01").subProductKey("08").defaultIconRes(R.mipmap.ic_device_super_panel).defaultNameRes(R.string.android_pro_pad).addNameRes(R.string.add_super_panel_pro_name).macFlag(1).build();
    }

    public ProductInfo createAndroidSuperPanel12S() {
        return ProductInfo.Builder.wifiBleProduct().productId(ProductId.ID_ANDROID_SUPER_PANEL_12S).agreementId(3).productKey("01").subProductKey("09").defaultIconRes(R.mipmap.device_icon_12s).defaultNameRes(R.string.android_pad_12S).addNameRes(R.string.add_super_panel_12S_name).macFlag(1).build();
    }

    public ProductInfo createCentreAirGateway() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_CENTRE_AIR_GATEWAY).agreementId(3).productKey("01").subProductKey("03").defaultIconRes(R.mipmap.device_icon_centralac).defaultNameRes(R.string.centre_air_module).addNameRes(R.string.add_cg_air_name).macFlag(1).build();
    }

    public ProductInfo createCentreAirProGateway() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_CENTRE_AIR_PRO_GATEWAY).agreementId(3).productKey("01").subProductKey("0A").defaultIconRes(R.mipmap.device_icon_centralac).defaultNameRes(R.string.centre_air_pro_module).addNameRes(R.string.add_cg_air_pro_name).macFlag(1).build();
    }

    public ProductInfo createCentreAirMaxGateway() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_CENTRE_AIR_PRO_GATEWAY).agreementId(3).productKey("01").subProductKey("0D").defaultIconRes(R.mipmap.device_icon_centralac_max).defaultNameRes(R.string.centre_air_max_module).addNameRes(R.string.add_cg_air_pro_name).macFlag(1).build();
    }

    public ProductInfo createCentreAirSubDevice() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CENTRAL_AIR_SUB_DEVICE).agreementId(2).productKey(ProductId.BLE_SUB_TYPE_CENTRAL_AIR).defaultIconRes(R.mipmap.ic_device_ac).defaultNameRes(R.string.ac).macFlag(2).build();
    }

    public ProductInfo createNewAirSubDevice() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.FRESH_AIR_SUB_DEVICE).agreementId(2).productKey(ProductId.BLE_SUB_TYPE_NEW_AIR).defaultIconRes(R.mipmap.ic_device_newair).defaultNameRes(R.string.new_air).macFlag(2).build();
    }

    public ProductInfo createFloorHeatSubDevice() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.FLOOR_HEAT_SUB_DEVICE).agreementId(2).productKey(ProductId.BLE_SUB_TYPE_FLOOR_HEAT).defaultIconRes(R.mipmap.ic_device_floor_heat).defaultNameRes(R.string.floor_heat).macFlag(2).build();
    }

    public ProductInfo createAsPanelU1S() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_AS_PANEL_U1S).agreementId(2).productKey("04").subProductKey("01").defaultIconRes(R.mipmap.ic_deivce_as_panel).defaultNameRes(R.string.as_panel_dim).addNameRes(R.string.add_ub1_name).macFlag(1).controlType(27).groupKey(ProductId.BLE_GROUP_AS_PANEL_UB1).build();
    }

    public ProductInfo createAsPanelU2S() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_AS_PANEL_U2S).agreementId(2).productKey("04").subProductKey("02").defaultIconRes(R.mipmap.ic_deivce_as_panel).defaultNameRes(R.string.as_panel_ct).addNameRes(R.string.add_ub2_name).macFlag(1).controlType(28).groupKey(ProductId.BLE_GROUP_AS_PANEL_UB2).build();
    }

    public ProductInfo createAsPanelU4S() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_AS_PANEL_U4S).agreementId(2).productKey("04").subProductKey("03").defaultIconRes(R.mipmap.ic_deivce_as_panel).defaultNameRes(R.string.as_panel_rgbw).addNameRes(R.string.add_ub4_name).macFlag(1).controlType(29).groupKey(ProductId.BLE_GROUP_AS_PANEL_UB4).build();
    }

    public ProductInfo createAsPanelU5S() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_AS_PANEL_U5S).agreementId(2).productKey("04").subProductKey("04").defaultIconRes(R.mipmap.ic_deivce_as_panel).defaultNameRes(R.string.as_panel_rgbwy).addNameRes(R.string.add_ub5_name).macFlag(1).controlType(30).groupKey(ProductId.BLE_GROUP_AS_PANEL_UB5).build();
    }

    public ProductInfo createAsPanelUB8() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_AS_PANEL_UB8).agreementId(2).productKey("04").subProductKey(ProductId.BLE_AS_PANEL_UB8).defaultIconRes(R.mipmap.ic_deivce_as_ub8).defaultNameRes(R.string.as_panel_ub8).addNameRes(R.string.eur_panel_eb8_short).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS1C() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S1C).agreementId(2).productKey("04").subProductKey("09").controlType(8).defaultIconRes(R.mipmap.device_icon_s1c).defaultNameRes(R.string.switch_panel_s1c).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS2C() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S2C).agreementId(2).productKey("04").subProductKey("0A").controlType(9).defaultIconRes(R.mipmap.device_icon_s2c).defaultNameRes(R.string.switch_panel_s2c).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS3C() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S3C).agreementId(2).productKey("04").subProductKey("0B").controlType(10).defaultIconRes(R.mipmap.device_icon_s3c).defaultNameRes(R.string.switch_panel_s3c).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS4M() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SWITCH_PANEL_S4M).agreementId(2).productKey("04").subProductKey("05").controlType(11).defaultIconRes(R.mipmap.ic_device_switch_panel_s4m).defaultNameRes(R.string.switch_panel_s4m).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createScenePanelS8M() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SCENE_PANEL_S8).agreementId(2).productKey("04").subProductKey("08").controlType(6).defaultIconRes(R.mipmap.ic_device_scene_panel_s8).defaultNameRes(R.string.scene_panel_s8).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createScenePanelS6B() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_PANEL_S6B).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_S6B).defaultIconRes(R.mipmap.device_icon_s6b).defaultNameRes(R.string.switch_panel_s6).addNameRes(R.string.add_wireless_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S1C).agreementId(2).productKey("04").subProductKey("11").controlType(8).defaultIconRes(R.mipmap.device_icon_s1).defaultNameRes(R.string.switch_panel_s1).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS2() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S2C).agreementId(2).productKey("04").subProductKey("12").controlType(9).defaultIconRes(R.mipmap.device_icon_s2).defaultNameRes(R.string.switch_panel_s2).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS3() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S3C).agreementId(2).productKey("04").subProductKey("13").controlType(10).defaultIconRes(R.mipmap.device_icon_s3).defaultNameRes(R.string.switch_panel_s3).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S4).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_S4).controlType(18).defaultIconRes(R.mipmap.device_icon_s4).defaultNameRes(R.string.switch_panel_s4).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS1Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S1_PRO).agreementId(2).productKey("04").subProductKey("0E").controlType(8).defaultIconRes(R.mipmap.device_icon_s1_pro).defaultNameRes(R.string.switch_panel_s1pro).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS2Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S2_PRO).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_S2PRO).controlType(9).defaultIconRes(R.mipmap.device_icon_s2_pro).defaultNameRes(R.string.switch_panel_s2pro).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS3Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S3_PRO).agreementId(2).productKey("04").subProductKey("10").controlType(10).defaultIconRes(R.mipmap.device_icon_s3_pro).defaultNameRes(R.string.switch_panel_s3pro).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSeptS4M() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SWITCH_PANEL_S4M).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S4M).controlType(11).defaultIconRes(R.mipmap.ic_device_switch_panel_s4m).defaultNameRes(R.string.switch_panel_s4m).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSeptS1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S1C).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S1).controlType(8).defaultIconRes(R.mipmap.device_icon_s1).defaultNameRes(R.string.switch_panel_s1).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSeptS2() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S2C).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S2).controlType(9).defaultIconRes(R.mipmap.device_icon_s2).defaultNameRes(R.string.switch_panel_s2).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSeptS3() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S3C).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S3).controlType(10).defaultIconRes(R.mipmap.device_icon_s3).defaultNameRes(R.string.switch_panel_s3).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSeptS4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S4).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S4).controlType(18).defaultIconRes(R.mipmap.device_icon_s4).defaultNameRes(R.string.switch_panel_s4).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSeptS1Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S1_PRO).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S1_PRO).controlType(8).defaultIconRes(R.mipmap.device_icon_s1_pro).defaultNameRes(R.string.switch_panel_s1pro).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSeptS2Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S2_PRO).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S2_PRO).controlType(9).defaultIconRes(R.mipmap.device_icon_s2_pro).defaultNameRes(R.string.switch_panel_s2pro).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSeptS3Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S3_PRO).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SEPT_S3_PRO).controlType(10).defaultIconRes(R.mipmap.device_icon_s3_pro).defaultNameRes(R.string.switch_panel_s3pro).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS6Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S6_PRO).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_S6PRO).controlType(19).defaultIconRes(R.mipmap.device_icon_s6_pro).defaultNameRes(R.string.switch_panel_s6pro).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelS6() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_S6).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_S6).controlType(19).defaultIconRes(R.mipmap.device_icon_s6).defaultNameRes(R.string.switch_panel_ss6).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelG4Pro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_PANEL_G4_PRO).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_G4PRO).controlType(21).defaultIconRes(R.mipmap.icon_g4pro).defaultNameRes(R.string.switch_panel_g4pro).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelG4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_PANEL_G4).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_G4).controlType(21).defaultIconRes(R.mipmap.icon_g4).defaultNameRes(R.string.switch_panel_g4).addNameRes(R.string.add_switch_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSQ() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_SQ).agreementId(2).productKey("04").subProductKey("14").defaultIconRes(R.mipmap.device_icon_sq).defaultNameRes(R.string.switch_panel_sq).addNameRes(R.string.add_sq_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSQB() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_SQB).agreementId(2).productKey("04").subProductKey("15").defaultIconRes(R.mipmap.device_icon_sq).defaultNameRes(R.string.switch_panel_sq_battery).addNameRes(R.string.add_sq_panel_name).macFlag(1).build();
    }

    public ProductInfo createSwitchPanelSQPro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_SWITCH_SQ_PRO).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_SQ_PRO).defaultIconRes(R.mipmap.device_icon_sqpro).defaultNameRes(R.string.switch_panel_sq_pro).addNameRes(R.string.add_sq_panel_name).macFlag(1).build();
    }

    public ProductInfo createKnobRemoteGQ() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_PANEL_GQ).agreementId(2).productKey("03").subProductKey("0A").defaultIconRes(R.mipmap.device_icon_gq).defaultNameRes(R.string.knob_remote_gqx).addNameRes(R.string.add_knob_remote_gq_name).macFlag(1).build();
    }

    public ProductInfo createKnobRemoteGQX() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_PANEL_GQX).agreementId(2).productKey("03").subProductKey("0B").defaultIconRes(R.mipmap.device_icon_gqx).defaultNameRes(R.string.knob_remote_gqx).addNameRes(R.string.add_knob_remote_gq_name).macFlag(1).build();
    }

    public ProductInfo createKnobPanelGQPro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_PANEL_GQ_PRO).agreementId(2).productKey("04").subProductKey("21").defaultIconRes(R.mipmap.icon_cqpro).defaultNameRes(R.string.knob_remote_gq_pro).addNameRes(R.string.add_knob_remote_gq_pro_name).macFlag(1).build();
    }

    public ProductInfo createKnobPanelGQMax() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_PANEL_GQ_PRO).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SMART_PANEL_SUB_TYPE_GQ_MAX).defaultIconRes(R.mipmap.icon_cqpro).defaultNameRes(R.string.knob_remote_gq_max).addNameRes(R.string.add_knob_remote_gq_pro_name).macFlag(1).build();
    }

    public ProductInfo createSmartPanelG4TE() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SMART_PANEL_G4TE).agreementId(2).productKey("04").subProductKey("22").defaultIconRes(R.mipmap.device_icon_g4te).defaultNameRes(R.string.smart_panel_g4te).addNameRes(R.string.add_g4te_name).macFlag(1).build();
    }

    public ProductInfo createKeySwitch1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_KEY_SWITCH_1).agreementId(2).productKey("03").subProductKey("02").defaultIconRes(R.mipmap.ic_device_key_switch_01).defaultNameRes(R.string.key_switch_1).addNameRes(R.string.add_wireless_panel_name).macFlag(1).build();
    }

    public ProductInfo createKeySwitch2() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_KEY_SWITCH_2).agreementId(2).productKey("03").subProductKey("03").defaultIconRes(R.mipmap.ic_device_key_switch_02).defaultNameRes(R.string.key_switch_2).addNameRes(R.string.add_wireless_panel_name).macFlag(1).build();
    }

    public ProductInfo createKeySwitch3() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_KEY_SWITCH_3).agreementId(2).productKey("03").subProductKey("04").defaultIconRes(R.mipmap.ic_device_key_switch_03).defaultNameRes(R.string.key_switch_3).addNameRes(R.string.add_wireless_panel_name).macFlag(1).build();
    }

    public ProductInfo createKeySwitch4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_KEY_SWITCH_4).agreementId(2).productKey("03").subProductKey("05").defaultIconRes(R.mipmap.ic_device_key_switch_04).defaultNameRes(R.string.key_switch_4).addNameRes(R.string.add_wireless_panel_name).macFlag(1).build();
    }

    public ProductInfo createRc4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_RC4).agreementId(2).productKey("03").subProductKey("01").defaultIconRes(R.mipmap.ic_device_rc).defaultNameRes(R.string.rc4).addNameRes(R.string.add_remote_name).macFlag(1).build();
    }

    public ProductInfo createRc4S() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_RC4S).agreementId(2).productKey("03").subProductKey("0C").defaultIconRes(R.mipmap.ic_device_rc).defaultNameRes(R.string.rc4s).addNameRes(R.string.add_ble_remote_name).macFlag(1).build();
    }

    public ProductInfo createRcB1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_RC_B1).agreementId(2).productKey("03").subProductKey("06").defaultIconRes(R.mipmap.ic_device_b125).defaultNameRes(R.string.rc_b1).addNameRes(R.string.add_rc_b1).macFlag(1).build();
    }

    public ProductInfo createRcB2() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_RC_B2).agreementId(2).productKey("03").subProductKey("07").defaultIconRes(R.mipmap.ic_device_b125).defaultNameRes(R.string.rc_b2).addNameRes(R.string.add_rc_b2).macFlag(1).build();
    }

    public ProductInfo createRcB5() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_RC_B5).agreementId(2).productKey("03").subProductKey("08").defaultIconRes(R.mipmap.ic_device_b125).defaultNameRes(R.string.rc_b5).addNameRes(R.string.add_rc_b5).macFlag(1).build();
    }

    public ProductInfo createRcB8() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_RC_B8).agreementId(2).productKey("03").subProductKey("09").defaultIconRes(R.mipmap.ic_device_b8).defaultNameRes(R.string.rc_b8).addNameRes(R.string.add_rc_b8).macFlag(1).build();
    }

    public ProductInfo createTV() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_TV).agreementId(3).productKey(ProductId.IR_SUB_TYPE_TV).defaultIconRes(R.mipmap.ic_device_tv).defaultNameRes(R.string.tv).macFlag(2).build();
    }

    public ProductInfo createStb() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_STB).agreementId(3).productKey(ProductId.IR_SUB_TYPE_STB).defaultIconRes(R.mipmap.ic_device_stb).defaultNameRes(R.string.stb).macFlag(2).build();
    }

    public ProductInfo createTvBox() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_TV_BOX).agreementId(3).productKey(ProductId.IR_SUB_TYPE_TV_BOX).defaultIconRes(R.mipmap.ic_device_tvbox).defaultNameRes(R.string.tv_box).macFlag(2).build();
    }

    public ProductInfo createAc() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_AC).agreementId(3).productKey(ProductId.IR_SUB_TYPE_AC).defaultIconRes(R.mipmap.ic_device_ac).defaultNameRes(R.string.ac).macFlag(2).build();
    }

    public ProductInfo createFan() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_FAN).agreementId(3).productKey(ProductId.IR_SUB_TYPE_FAN).defaultIconRes(R.mipmap.ic_device_fan).defaultNameRes(R.string.fan).macFlag(2).build();
    }

    public ProductInfo createProjector() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_PRO).agreementId(3).productKey(ProductId.IR_SUB_TYPE_PRO).defaultIconRes(R.mipmap.ic_device_projector).defaultNameRes(R.string.projector).macFlag(2).build();
    }

    public ProductInfo createAirCleaner() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_AIR_CLEANER).agreementId(3).productKey(ProductId.IR_SUB_TYPE_AIR_CLEANER).defaultIconRes(R.mipmap.ic_device_air_cleaner).defaultNameRes(R.string.air_cleaner).macFlag(2).build();
    }

    public ProductInfo createWaterHeater() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_WATER_HEATER).agreementId(3).productKey(ProductId.IR_SUB_TYPE_WATER_HEATER).defaultIconRes(R.mipmap.ic_device_heater).defaultNameRes(R.string.water_heater).macFlag(2).build();
    }

    public ProductInfo createCurtain() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_CURTAIN).agreementId(3).productKey(ProductId.IR_SUB_TYPE_CURTAIN).defaultIconRes(R.mipmap.ic_device_curtain).defaultNameRes(R.string.curtain).macFlag(2).build();
    }

    public ProductInfo createHanger() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_HANGER).agreementId(3).productKey(ProductId.IR_SUB_TYPE_HANGER).defaultIconRes(R.mipmap.ic_device_hanger).defaultNameRes(R.string.clothes_hanger).macFlag(2).build();
    }

    public ProductInfo createIrDiy() {
        return ProductInfo.Builder.irProduct().productId(ProductId.ID_IR_DIY).agreementId(3).productKey(ProductId.IR_SUB_TYPE_DIY).defaultIconRes(R.mipmap.ic_device_diy_controller).defaultNameRes(R.string.ir_diy).macFlag(2).build();
    }

    public ProductInfo createBodySensor() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BODY_SENSOR).agreementId(2).productKey("05").subProductKey("01").defaultIconRes(R.mipmap.icon_device_sensor).defaultNameRes(R.string.app_str_body_sensor).addNameRes(R.string.add_body_sensor_name).macFlag(1).build();
    }

    public ProductInfo createBleHSD() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SENSOR_HSD).agreementId(2).productKey("05").subProductKey("07").defaultIconRes(R.mipmap.icon_device_sensor).defaultNameRes(R.string.app_str_hsd).addNameRes(R.string.add_body_sensor_name).macFlag(1).build();
    }

    public ProductInfo createDoorSensor() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_DOOR_SENSOR).agreementId(2).productKey("05").subProductKey("05").defaultIconRes(R.mipmap.icon_device_cgdr).defaultNameRes(R.string.app_str_door_sensor).addNameRes(R.string.add_cgdr_name).macFlag(1).build();
    }

    public ProductInfo createWaveSensorMR3() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SENSOR_MR03).agreementId(2).productKey("05").subProductKey("03").controlType(13).defaultIconRes(R.mipmap.icon_device_mr03).defaultNameRes(R.string.app_str_wave_sensor_mr03).addNameRes(R.string.add_wave_sensor_name).macFlag(1).build();
    }

    public ProductInfo createWaveSensorMR4() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SENSOR_MR04).agreementId(2).productKey("05").subProductKey("04").controlType(15).defaultIconRes(R.mipmap.icon_device_mr04).defaultNameRes(R.string.app_str_wave_sensor_mr04).addNameRes(R.string.add_wave_sensor_name).macFlag(1).build();
    }

    public ProductInfo createWaveSensorMS3() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_SENSOR_MS03).agreementId(2).productKey("05").subProductKey("06").controlType(25).defaultIconRes(R.mipmap.icon_device_mr03).defaultNameRes(R.string.app_str_wave_sensor_ms03).addNameRes(R.string.add_wave_sensor_name).macFlag(1).build();
    }

    public ProductInfo createBleMusicPlayer() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_MUSIC_PLAYER).agreementId(2).productKey("08").subProductKey("01").controlType(0).defaultIconRes(R.mipmap.ic_device_cgmusic_icon).defaultNameRes(R.string.ble_device_music_player).addNameRes(R.string.add_cg_audio_name).macFlag(1).build();
    }

    public ProductInfo createCgdPro() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_LIGHT_CGD_PRO).agreementId(2).productKey("02").subProductKey("0A").defaultIconRes(R.mipmap.ic_device_dali_control).defaultNameRes(R.string.ble_device_dali_control).addNameRes(R.string.add_cg_dali_conrtol).macFlag(1).build();
    }

    public ProductInfo createRs485Module() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_RS485_BLE).agreementId(2).productKey("02").subProductKey("0C").defaultIconRes(R.mipmap.icon_device_cg485).defaultNameRes(R.string.ble_device_cg_link_module).addNameRes(R.string.add_cg_link_name).macFlag(1).build();
    }

    public ProductInfo createCg485SubDevice() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CG485_SUB_DEVICE).agreementId(2).productKey(ProductId.BLE_SUB_TYPE_CG_485).macFlag(2).build();
    }

    public ProductInfo createRs8Module() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_RS8).agreementId(2).productKey("02").subProductKey(ProductId.ID_BLE_RS8).subProductKey("0D").defaultIconRes(R.mipmap.icon_device_cg485).defaultNameRes(R.string.ble_device_cg_rs8_module).addNameRes(R.string.add_cg_rs8_name).macFlag(1).build();
    }

    public ProductInfo createCgRs8SubDevice() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.CGRS8_SUB_DEVICE).agreementId(2).productKey(ProductId.BLE_SUB_TYPE_CG_RS8).macFlag(2).build();
    }

    public ProductInfo createBleHAM() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_HAM).agreementId(2).productKey("01").subProductKey("05").controlType(0).defaultIconRes(R.mipmap.ic_device_gateway).defaultNameRes(R.string.ble_home_appliance_module).addNameRes(R.string.add_home_appliance_name).macFlag(1).build();
    }

    public ProductInfo createCamera() {
        return ProductInfo.Builder.wifiProduct().productId(ProductId.ID_WIFI_CAMERA).agreementId(3).productKey("01").subProductKey("01").defaultIconRes(R.mipmap.ic_device_camera).defaultNameRes(R.string.device_ezviz_camera).macFlag(1).build();
    }

    public ProductInfo createSonos() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_WIFI_SONOS).defaultIconRes(R.mipmap.device_ic_sonos).macFlag(1).build();
    }

    public ProductInfo createAndroidSuperPanelG4Max() {
        return ProductInfo.Builder.wifiBleProduct().productId(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX).agreementId(3).productKey("01").subProductKey("0C").defaultIconRes(R.mipmap.ic_device_g4max).defaultNameRes(R.string.android_pad_g4_max).addNameRes(R.string.add_super_panel_g4_max_name).macFlag(1).build();
    }

    public ProductInfo createHomeKit() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_HOME_KIT).agreementId(2).productKey("01").subProductKey("0B").defaultIconRes(R.mipmap.ic_device_cgkit).defaultNameRes(R.string.device_home_kit).addNameRes(R.string.add_home_kit).macFlag(1).build();
    }

    public ProductInfo createHomeKitMatter() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_HOME_KIT).agreementId(2).productKey("01").subProductKey("0E").defaultIconRes(R.mipmap.ic_device_cgkit).defaultNameRes(R.string.device_home_kit).addNameRes(R.string.add_home_kit).macFlag(1).build();
    }

    public ProductInfo createKbs() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_KBS).agreementId(2).productKey("04").subProductKey("23").controlType(26).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_switch_kbs).macFlag(1).groupKey(ProductId.BLE_GROUP_KBS).build();
    }

    public ProductInfo createKbs1() {
        return ProductInfo.Builder.bleProduct().productId(ProductId.ID_BLE_SWITCH).agreementId(2).productKey("04").subProductKey(ProductId.BLE_SWITCH_SUB_TYPE_KBS1).controlType(7).defaultIconRes(R.mipmap.ic_device_light).defaultNameRes(R.string.ble_device_light_switch_kbs1).macFlag(1).groupKey(ProductId.BLE_GROUP_SWITCH).build();
    }
}