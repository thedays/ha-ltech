package com.ltech.smarthome.utils;

import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.device_param.ExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.ActAsPanelSetting;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.central.air.AcFeatureHelper;
import com.ltech.smarthome.ui.device.central.air.ActCentralAc;
import com.ltech.smarthome.ui.device.central.air.ActCentralAirMeshGatewaySetting;
import com.ltech.smarthome.ui.device.central.airpro.ActCentralFloorHeat;
import com.ltech.smarthome.ui.device.central.airpro.ActCentralFreshAir;
import com.ltech.smarthome.ui.device.central.tepanel.ActTePanel;
import com.ltech.smarthome.ui.device.cg485.ActCg485Setting;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleMotor;
import com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSetting;
import com.ltech.smarthome.ui.device.e6knob.ActE6PanelSetting;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelGroupSetting;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelSetting;
import com.ltech.smarthome.ui.device.gqx.ActGqxSetting;
import com.ltech.smarthome.ui.device.homekit.ActHomeKitSetting;
import com.ltech.smarthome.ui.device.hsd.ActHsdSensorSetting;
import com.ltech.smarthome.ui.device.ir.ActAc;
import com.ltech.smarthome.ui.device.ir.ActAirCleaner;
import com.ltech.smarthome.ui.device.ir.ActDiyIrSetting;
import com.ltech.smarthome.ui.device.ir.ActFan;
import com.ltech.smarthome.ui.device.ir.ActHanger;
import com.ltech.smarthome.ui.device.ir.ActIrDiy;
import com.ltech.smarthome.ui.device.ir.ActIrSetting;
import com.ltech.smarthome.ui.device.ir.ActMotor;
import com.ltech.smarthome.ui.device.ir.ActProjector;
import com.ltech.smarthome.ui.device.ir.ActStb;
import com.ltech.smarthome.ui.device.ir.ActTv;
import com.ltech.smarthome.ui.device.ir.ActTvBox;
import com.ltech.smarthome.ui.device.ir.ActWaterHeater;
import com.ltech.smarthome.ui.device.kbs.ActKbsGroupSetting;
import com.ltech.smarthome.ui.device.kbs.ActKbsSetting;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelSetting;
import com.ltech.smarthome.ui.device.light.ActLightSetting;
import com.ltech.smarthome.ui.device.mesh_gateway.ActMeshGatewaySetting;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSetting;
import com.ltech.smarthome.ui.device.r8.ActR8Setting;
import com.ltech.smarthome.ui.device.remote_controller.ActRemoteBatterySetting;
import com.ltech.smarthome.ui.device.setting.ActBleHamSettingDefault;
import com.ltech.smarthome.ui.device.setting.ActBleMotorSetting;
import com.ltech.smarthome.ui.device.setting.ActBleMusicPlayerSetting;
import com.ltech.smarthome.ui.device.setting.ActBleTrigCurtainSetting;
import com.ltech.smarthome.ui.device.setting.ActBleTrigSceneSetting;
import com.ltech.smarthome.ui.device.setting.ActDeviceSettingDefault;
import com.ltech.smarthome.ui.device.setting.ActDmx512Setting;
import com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSetting;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelSetting;
import com.ltech.smarthome.ui.device.setting.ActSubDeviceSettingDefault;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelChildSetting;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelGroupChildSetting;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.device.spicontroller.ActSpiControllerSetting;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSetting;
import com.ltech.smarthome.ui.device.trig.ActTrigCurtain;
import com.ltech.smarthome.ui.group.ActBleCurtainGroupSetting;
import com.ltech.smarthome.ui.group.ActBleTrigCurtainGroupSetting;
import com.ltech.smarthome.ui.group.ActGroupSetting;
import com.ltech.smarthome.ui.group.ActPanelGroupSetting;
import com.ltech.smarthome.ui.group.ActWaveSensorGroupSetting;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.smart.message.utils.LHomeLog;
import io.netty.util.internal.StringUtil;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;
import org.spongycastle.pqc.math.linearalgebra.Matrix;

/* loaded from: classes4.dex */
public class NavHelper {
    public static RelateInfoAssistant relateInfoAssistant;

    public static void goSetting(Object object) {
        NavUtils.Builder builder;
        NavUtils.Builder withInt;
        if (object instanceof Device) {
            Device device = (Device) object;
            try {
                char c2 = 2;
                if (device.getExtParam(ExtParam.class) != null && ((ExtParam) device.getExtParam(ExtParam.class)).getACType() == 1 && device.isSubDevice()) {
                    device.setProductId(ProductId.CENTRAL_AIR_SUB_DEVICE);
                } else if (device.getExtParam(ExtParam.class) != null && ((ExtParam) device.getExtParam(ExtParam.class)).getACType() == 2 && device.isSubDevice()) {
                    device.setProductId(ProductId.FRESH_AIR_SUB_DEVICE);
                } else if (device.getExtParam(ExtParam.class) != null && ((ExtParam) device.getExtParam(ExtParam.class)).getACType() == 3 && device.isSubDevice()) {
                    device.setProductId(ProductId.FLOOR_HEAT_SUB_DEVICE);
                }
                String productId = device.getProductId();
                switch (productId.hashCode()) {
                    case -2133025271:
                        if (productId.equals(ProductId.CGRS8_SUB_DEVICE)) {
                            c2 = 'W';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -2126431781:
                        if (productId.equals(ProductId.ID_IR_DIY)) {
                            c2 = '\"';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -2060969856:
                        if (productId.equals(ProductId.ID_AS_PANEL_UB8)) {
                            c2 = 18;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1822884084:
                        if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                            c2 = 16;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1819630261:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                            c2 = Typography.less;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1817691924:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                            c2 = '=';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1805322688:
                        if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                            c2 = 5;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1805199680:
                        if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1804340546:
                        if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                            c2 = 7;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1804278081:
                        if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                            c2 = '\b';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1803448738:
                        if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                            c2 = '\t';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1796419228:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                            c2 = Typography.greater;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1777527685:
                        if (productId.equals(ProductId.ID_WIFI_LIGHT_DIM)) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1777494050:
                        if (productId.equals(ProductId.ID_WIFI_LIGHT_CT)) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1776694498:
                        if (productId.equals(ProductId.ID_WIFI_LIGHT_RGB)) {
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1776638760:
                        if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBW)) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1776570529:
                        if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBWY)) {
                            c2 = 4;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1710907378:
                        if (productId.equals(ProductId.ID_BLE_KBS)) {
                            c2 = 'X';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1698123058:
                        if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                            c2 = '\f';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1642166464:
                        if (productId.equals(ProductId.ID_BLE_HAM)) {
                            c2 = 'J';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1550133760:
                        if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                            c2 = '\r';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1343252468:
                        if (productId.equals(ProductId.ID_RS485_BLE)) {
                            c2 = Matrix.MATRIX_TYPE_RANDOM_REGULAR;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1309274422:
                        if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                            c2 = ' ';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1308265372:
                        if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                            c2 = 31;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1287620343:
                        if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                            c2 = 'I';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1281119909:
                        if (productId.equals(ProductId.ID_RC_B2)) {
                            c2 = 20;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1273434493:
                        if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                            c2 = 'M';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1265646206:
                        if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                            c2 = '!';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1213322926:
                        if (productId.equals(ProductId.ID_RC_B8)) {
                            c2 = 22;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1084555505:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                            c2 = '?';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1082613022:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                            c2 = '@';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -1073881216:
                        if (productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                            c2 = 17;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -969622016:
                        if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                            c2 = 'C';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -961541705:
                        if (productId.equals(ProductId.ID_SMART_PANEL_S6B)) {
                            c2 = 'A';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -852623517:
                        if (productId.equals(ProductId.ID_RC4S)) {
                            c2 = 'Y';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -835060954:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                            c2 = '6';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -833770237:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                            c2 = 'E';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -732569219:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                            c2 = '9';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -728269602:
                        if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                            c2 = JSONLexer.EOI;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -324427448:
                        if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                            c2 = 30;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case -249671171:
                        if (productId.equals(ProductId.ID_RC_B5)) {
                            c2 = 21;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2003796:
                        if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                            c2 = '/';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2003797:
                        if (productId.equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
                            c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2003798:
                        if (productId.equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
                            c2 = Soundex.SILENT_MARKER;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2256539:
                        if (productId.equals(ProductId.ID_IR_STB)) {
                            c2 = '*';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2256540:
                        if (productId.equals(ProductId.ID_IR_TV)) {
                            c2 = '#';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2256541:
                        if (productId.equals(ProductId.ID_IR_TV_BOX)) {
                            c2 = Typography.amp;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2256543:
                        if (productId.equals(ProductId.ID_IR_AC)) {
                            c2 = '$';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2256544:
                        if (productId.equals(ProductId.ID_IR_PRO)) {
                            c2 = '\'';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2256546:
                        if (productId.equals(ProductId.ID_IR_FAN)) {
                            c2 = '%';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 13862565:
                        if (productId.equals(ProductId.ID_BLE_RS8)) {
                            c2 = 'S';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 42289893:
                        if (productId.equals(ProductId.ID_SENSOR_HSD)) {
                            c2 = Matrix.MATRIX_TYPE_ZERO;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 69952758:
                        if (productId.equals(ProductId.ID_IR_AIR_CLEANER)) {
                            c2 = '(';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 69952759:
                        if (productId.equals(ProductId.ID_IR_WATER_HEATER)) {
                            c2 = ')';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 69953013:
                        if (productId.equals(ProductId.ID_IR_CURTAIN)) {
                            c2 = StringUtil.COMMA;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 69953014:
                        if (productId.equals(ProductId.ID_IR_HANGER)) {
                            c2 = '+';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 166485422:
                        if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                            c2 = '\n';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 186184655:
                        if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                            c2 = Matrix.MATRIX_TYPE_RANDOM_LT;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 225641606:
                        if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                            c2 = ':';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 294483828:
                        if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                            c2 = 29;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 312618751:
                        if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                            c2 = 25;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 353722044:
                        if (productId.equals(ProductId.ID_WIFI_CAMERA)) {
                            c2 = 'P';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 356111630:
                        if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                            c2 = '0';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 356193315:
                        if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                            c2 = '1';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 359647590:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                            c2 = 'G';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 376429092:
                        if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                            c2 = '2';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 376488674:
                        if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                            c2 = '3';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 414687077:
                        if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                            c2 = 'Q';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 427686243:
                        if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                            c2 = 'B';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 439998223:
                        if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                            c2 = 24;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 534249931:
                        if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                            c2 = 14;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 612512450:
                        if (productId.equals(ProductId.ID_HOME_KIT)) {
                            c2 = 'V';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 613226983:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                            c2 = 'F';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 662799966:
                        if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                            c2 = 11;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 811752507:
                        if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                            c2 = 28;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 956710656:
                        if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                            c2 = 27;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1097035898:
                        if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                            c2 = ';';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1181428532:
                        if (productId.equals(ProductId.ID_SMART_PANEL_GQ)) {
                            c2 = Matrix.MATRIX_TYPE_RANDOM_UT;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1309050445:
                        if (productId.equals(ProductId.ID_SMART_PANEL_GQX)) {
                            c2 = 'T';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1378424449:
                        if (productId.equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY)) {
                            c2 = '5';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1473345811:
                        if (productId.equals(ProductId.ID_RC_B1)) {
                            c2 = 19;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1479279198:
                        if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                            c2 = 'N';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1647983530:
                        if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                            c2 = 23;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1786777444:
                        if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                            c2 = 'O';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1861788715:
                        if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                            c2 = 15;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1921260107:
                        if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                            c2 = 'K';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1951402182:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                            c2 = '8';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1951547293:
                        if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                            c2 = '7';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1976427583:
                        if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                            c2 = 'H';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2002295507:
                        if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                            c2 = '4';
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2088187733:
                        if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                            c2 = 'D';
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
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case '\b':
                    case '\t':
                    case '\n':
                        if (RelaySeparationHelper.isRelaySeparationSub(device)) {
                            builder = NavUtils.destination(ActSmartPanelChildSetting.class);
                            break;
                        } else {
                            builder = NavUtils.destination(ActLightSetting.class);
                            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                            if (bleParam != null && bleParam.getDeviceType() != 0) {
                                builder.withInt(Constants.LIGHT_TYPE, bleParam.getDeviceType());
                                if (bleParam.getDeviceType() == 31) {
                                    builder = NavUtils.destination(ActDmx512Setting.class);
                                    break;
                                }
                            }
                        }
                        break;
                    case 11:
                        builder = NavUtils.destination(ActSpiControllerSetting.class);
                        break;
                    case '\f':
                        builder = NavUtils.destination(ActMeshGatewaySetting.class);
                        break;
                    case '\r':
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        builder = NavUtils.destination(ActEurPanelSetting.class);
                        break;
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                        builder = NavUtils.destination(ActRemoteBatterySetting.class);
                        break;
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                        builder = NavUtils.destination(ActE6PanelSetting.class);
                        break;
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case ' ':
                    case '!':
                        builder = NavUtils.destination(ActSuperPanelSetting.class).withString(Constants.MAC_ADDRESS, device.getWifiMac()).withString(Constants.SERIAL_NUMBER, device.getDevicesn());
                        break;
                    case '\"':
                        builder = NavUtils.destination(ActDiyIrSetting.class);
                        break;
                    case '#':
                    case '$':
                    case '%':
                    case '&':
                    case '\'':
                    case '(':
                    case ')':
                        builder = NavUtils.destination(ActIrSetting.class);
                        break;
                    case '*':
                    case '+':
                    case ',':
                    case '-':
                    case '.':
                        builder = NavUtils.destination(ActSubDeviceSettingDefault.class);
                        break;
                    case '/':
                        builder = NavUtils.destination(ActSubDeviceSettingDefault.class);
                        String acGatewayFeature = ((ExtParam) device.getExtParam(ExtParam.class)).getAcGatewayFeature();
                        if (acGatewayFeature != null && acGatewayFeature.length() > 20) {
                            builder.withString(Constants.BRAND_NAME, ActivityUtils.getTopActivity().getString(new AcFeatureHelper(acGatewayFeature).getBrandNameRes()));
                            break;
                        }
                        break;
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                        if (AsHelper.isNewUb(device)) {
                            builder = NavUtils.destination(ActEurPanelSetting.class);
                            break;
                        } else {
                            NavUtils.Builder destination = NavUtils.destination(ActAsPanelSetting.class);
                            destination.withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(object));
                            LHomeLog.i(NavHelper.class, "mViewModel.ligth_type=" + device.toString());
                            builder = destination;
                            break;
                        }
                    case '4':
                    case '5':
                        builder = NavUtils.destination(ActCentralAirMeshGatewaySetting.class).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case ':':
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                    case '?':
                    case '@':
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                        NavUtils.Builder withLong = NavUtils.destination(ActSmartPanelSetting.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong(Constants.PLACE_ID, device.getPlaceId());
                        int deviceType = ((BleParam) device.getParam(BleParam.class)).getDeviceType();
                        Integer.valueOf(deviceType).getClass();
                        builder = withLong.withInt(Constants.LIGHT_TYPE, deviceType);
                        break;
                    case 'E':
                    case 'F':
                    case 'G':
                        builder = NavUtils.destination(ActKnobPanelSetting.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'H':
                    case 'I':
                        builder = NavUtils.destination(ActBleMotorSetting.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'J':
                        builder = NavUtils.destination(ActBleHamSettingDefault.class);
                        break;
                    case 'K':
                        if (device.getParam(DryContactBleParam.class) != null) {
                            if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() != 0 && ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() != 3) {
                                builder = NavUtils.destination(ActBleTrigSceneSetting.class);
                                break;
                            }
                            builder = NavUtils.destination(ActBleTrigCurtainSetting.class).withString(Constants.PRODUCT_ID, device.getProductId()).withInt(Constants.SUB_TYPE, ((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType()).withLong(Constants.PLACE_ID, device.getPlaceId());
                        } else {
                            builder = NavUtils.destination(ActBleTrigSceneSetting.class);
                            break;
                        }
                        break;
                    case 'L':
                    case 'M':
                    case 'N':
                        builder = NavUtils.destination(ActWaveSensorSetting.class);
                        break;
                    case 'O':
                        builder = NavUtils.destination(ActBleMusicPlayerSetting.class).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'P':
                        builder = NavUtils.destination("com.ltech.smarthome.ui.camera.config.ActEzCameraSetting").withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'Q':
                        builder = NavUtils.destination(ActCgdProLightSetting.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'R':
                    case 'S':
                        builder = NavUtils.destination(ActCg485Setting.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'T':
                    case 'U':
                        builder = NavUtils.destination(ActGqxSetting.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'V':
                        builder = NavUtils.destination(ActHomeKitSetting.class).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'W':
                        builder = NavUtils.destination(ActRs8SubDeviceSetting.class);
                        break;
                    case 'X':
                        builder = NavUtils.destination(ActKbsSetting.class).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'Y':
                        builder = NavUtils.destination(ActR8Setting.class).withString(Constants.PRODUCT_ID, device.getProductId()).withLong(Constants.PLACE_ID, device.getPlaceId());
                        break;
                    case 'Z':
                        builder = NavUtils.destination(ActHsdSensorSetting.class);
                        break;
                    default:
                        builder = NavUtils.destination(ActDeviceSettingDefault.class);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                builder = NavUtils.destination(ActDeviceSettingDefault.class);
            }
            builder.withLong(Constants.CONTROL_ID, device.getId());
        } else if (object instanceof Group) {
            Group group = (Group) object;
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            switch (lightColorType) {
                case 7:
                    if (RelaySeparationHelper.isRelaySeparationSub(group)) {
                        withInt = NavUtils.destination(ActSmartPanelGroupChildSetting.class);
                        break;
                    } else {
                        withInt = NavUtils.destination(ActGroupSetting.class).withLong(Constants.PLACE_ID, group.getPlaceId()).withInt(Constants.LIGHT_TYPE, Integer.parseInt(group.getControlType()));
                        break;
                    }
                case 8:
                case 9:
                case 10:
                case 11:
                case 18:
                case 19:
                case 21:
                    withInt = NavUtils.destination(ActPanelGroupSetting.class).withLong(Constants.PLACE_ID, group.getPlaceId()).withInt(Constants.LIGHT_TYPE, Integer.parseInt(group.getControlType()));
                    break;
                case 12:
                case 16:
                    withInt = NavUtils.destination(ActBleTrigCurtainGroupSetting.class).withInt(Constants.LIGHT_TYPE, lightColorType).withLong(Constants.PLACE_ID, group.getPlaceId());
                    break;
                case 13:
                case 15:
                case 25:
                    withInt = NavUtils.destination(ActWaveSensorGroupSetting.class).withLong(Constants.PLACE_ID, group.getPlaceId()).withInt(Constants.LIGHT_TYPE, Integer.parseInt(group.getControlType()));
                    break;
                case 14:
                    withInt = NavUtils.destination(ActBleCurtainGroupSetting.class);
                    break;
                case 17:
                case 20:
                default:
                    withInt = NavUtils.destination(ActGroupSetting.class).withLong(Constants.PLACE_ID, group.getPlaceId()).withInt(Constants.LIGHT_TYPE, Integer.parseInt(group.getControlType()));
                    break;
                case 22:
                case 23:
                case 24:
                case 27:
                case 28:
                case 29:
                case 30:
                    withInt = NavUtils.destination(ActEurPanelGroupSetting.class).withLong(Constants.PLACE_ID, group.getPlaceId()).withInt(Constants.LIGHT_TYPE, Integer.parseInt(group.getControlType()));
                    break;
                case 26:
                    withInt = NavUtils.destination(ActKbsGroupSetting.class).withLong(Constants.PLACE_ID, group.getPlaceId());
                    break;
            }
            withInt.withLong(Constants.CONTROL_ID, group.getId());
            builder = withInt;
        } else {
            builder = null;
        }
        if (builder != null) {
            builder.withDefaultRequestCode().navigation(ActivityUtils.getTopActivity());
        }
    }

    public static NavUtils.Builder getIrNavBuilder(String productId) {
        productId.hashCode();
        switch (productId) {
            case "IR_100":
                return NavUtils.destination(ActIrDiy.class);
            case "122110809100701":
            case "121042516403901":
                return NavUtils.destination(ActBleMotor.class);
            case "AC_1":
                return NavUtils.destination(ActCentralAc.class);
            case "AC_2":
                return NavUtils.destination(ActCentralFreshAir.class);
            case "AC_3":
                return NavUtils.destination(ActCentralFloorHeat.class);
            case "IR_1":
                return NavUtils.destination(ActStb.class);
            case "IR_2":
                return NavUtils.destination(ActTv.class);
            case "IR_3":
                return NavUtils.destination(ActTvBox.class);
            case "IR_5":
                return NavUtils.destination(ActAc.class);
            case "IR_6":
                return NavUtils.destination(ActProjector.class);
            case "IR_8":
                return NavUtils.destination(ActFan.class);
            case "IR_11":
                return NavUtils.destination(ActAirCleaner.class);
            case "IR_12":
                return NavUtils.destination(ActWaterHeater.class);
            case "IR_98":
                return NavUtils.destination(ActMotor.class);
            case "IR_99":
                return NavUtils.destination(ActHanger.class);
            case "121051709233801":
                return NavUtils.destination(ActTrigCurtain.class);
            case "3712996094412352":
                return NavUtils.destination(ActTePanel.class);
            default:
                return null;
        }
    }
}