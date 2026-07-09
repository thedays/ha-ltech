package com.ltech.smarthome.ui.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.juphoon.cloud.BuildConfig;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.repo.ifun.IScene;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.ui.log.ActLocalDeviceLog;
import com.ltech.smarthome.utils.BitUtils;
import com.sun.jna.platform.win32.WinBase;
import com.xiaomi.mipush.sdk.Constants;
import java.util.List;

/* loaded from: classes4.dex */
public class LocalLogHelper {
    private static final String TAG = "LocalLogHelper";
    private static LocalLogHelper instance;
    private static List<TrigRepository.TrigItem> trigItems;

    public interface IParseCmdData {
        void parseData(int funCode, int cmdCode, String instruct);
    }

    public static LocalLogHelper getInstance() {
        if (instance == null) {
            synchronized (LocalLogHelper.class) {
                if (instance == null) {
                    instance = new LocalLogHelper();
                }
            }
        }
        return instance;
    }

    public static String getDeviceControlAction(Context context, String instruct, IParseCmdData iParseCmdData, Device device, long placeId) {
        int parseInt = Integer.parseInt(instruct.substring(0, 2), 16);
        if (parseInt == 18) {
            return context.getString(R.string.device_power_on);
        }
        if (parseInt == 19) {
            return context.getString(R.string.device_network_exit);
        }
        if (parseInt == 20) {
            return context.getString(R.string.device_network_access);
        }
        if (instruct.length() < 8) {
            Log.e(TAG, "命令数字异常，当前可能是错误码数据");
            return "";
        }
        int parseInt2 = Integer.parseInt(instruct.substring(6, 8), 16);
        if (iParseCmdData != null) {
            iParseCmdData.parseData(parseInt, parseInt2, instruct);
        }
        String[] stringArray = context.getResources().getStringArray(R.array.c2_commands);
        String[] stringArray2 = context.getResources().getStringArray(R.array.c3_commands);
        String[] stringArray3 = context.getResources().getStringArray(R.array.c4_commands);
        String[] stringArray4 = context.getResources().getStringArray(R.array.c5_commands);
        String[] stringArray5 = context.getResources().getStringArray(R.array.cc_commands);
        String[] stringArray6 = context.getResources().getStringArray(R.array.ce_commands);
        String[] stringArray7 = context.getResources().getStringArray(R.array.d1_commands);
        String[] stringArray8 = context.getResources().getStringArray(R.array.d2_commands);
        String[] stringArray9 = context.getResources().getStringArray(R.array.d3_commands);
        String[] stringArray10 = context.getResources().getStringArray(R.array.d4_commands);
        context.getResources().getStringArray(R.array.d5_commands);
        String[] stringArray11 = context.getResources().getStringArray(R.array.d7_commands);
        String[] stringArray12 = context.getResources().getStringArray(R.array.d8_commands);
        String[] stringArray13 = context.getResources().getStringArray(R.array.d9_commands);
        if (parseInt == 192) {
            return parseC0(context, instruct);
        }
        if (parseInt == 194) {
            return parseC2(context, instruct, placeId, stringArray);
        }
        if (parseInt == 195) {
            return parseC3(context, instruct, stringArray2);
        }
        if (parseInt == 196) {
            return getCmdName(stringArray3, parseInt, parseInt2 - 1);
        }
        if (parseInt == 197) {
            return getCmdName(stringArray4, parseInt, parseInt2);
        }
        if (parseInt == 198) {
            return parseC6(context, instruct, device);
        }
        if (parseInt == 199) {
            return parseC7(context, instruct, device);
        }
        if (parseInt == 200) {
            return parseC8(context, instruct, device);
        }
        if (parseInt == 201) {
            return parseC9(context, instruct, device);
        }
        if (parseInt == 202) {
            return parseCA(context, instruct, device);
        }
        if (parseInt == 203) {
            return parseCB(context, instruct);
        }
        if (parseInt == 204) {
            return getCmdName(stringArray5, parseInt, parseInt2 - 1);
        }
        if (parseInt == 205) {
            return parseCD(context, instruct);
        }
        if (parseInt == 206) {
            return getCmdName(stringArray6, parseInt, parseInt2);
        }
        if (parseInt == 207) {
            return parseCF(context, instruct, device);
        }
        if (parseInt == 208) {
            return parseD0(context, instruct, device, placeId);
        }
        if (parseInt == 209) {
            return getCmdName(stringArray7, parseInt, parseInt2);
        }
        if (parseInt == 210) {
            return getCmdName(stringArray8, parseInt, parseInt2 - 1);
        }
        if (parseInt == 211) {
            return getCmdName(stringArray9, parseInt, parseInt2 - 1);
        }
        if (parseInt == 212) {
            if (parseInt2 == 2 || parseInt2 == 4 || parseInt2 == 9 || parseInt2 == 3 || parseInt2 == 1) {
                return parseD4(context, instruct);
            }
            return getCmdName(stringArray10, parseInt, parseInt2 - 1);
        }
        if (parseInt == 213) {
            return parseD5(context, instruct);
        }
        if (parseInt == 215) {
            return getCmdName(stringArray11, parseInt, parseInt2 - 1);
        }
        if (parseInt == 216) {
            return getCmdName(stringArray12, parseInt, parseInt2 - 1);
        }
        return parseInt == 217 ? getCmdName(stringArray13, parseInt, parseInt2 - 1) : "";
    }

    private static String getCmdName(String[] cmd, int funCode, int cmdCode) {
        StringBuilder sb = new StringBuilder("cmd.length:");
        sb.append(cmd == null ? 0 : cmd.length);
        sb.append("__funCode:");
        sb.append(funCode);
        sb.append("__cmdCode:");
        sb.append(cmdCode);
        Log.e(TAG, sb.toString());
        if (cmd != null && cmdCode < cmd.length) {
            return cmd[cmdCode];
        }
        return "";
    }

    public static ActLocalDeviceLog.LogDetailMsg parseLogData(Context context, String logData, long placeId, IParseCmdData iParseCmdData, int currentDeviceAddress) {
        String str;
        ActLocalDeviceLog.LogDetailMsg logDetailMsg;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        try {
            int intValue = Integer.valueOf(logData.substring(0, 4), 16).intValue();
            int intValue2 = Integer.valueOf(logData.substring(4, 8), 16).intValue();
            logDetailMsg = null;
            try {
                int intValue3 = Integer.valueOf(logData.substring(8, 10), 16).intValue();
                ActLocalDeviceLog.LogDetailMsg logDetailMsg2 = new ActLocalDeviceLog.LogDetailMsg();
                logDetailMsg2.setTotal(intValue);
                logDetailMsg2.setReadNum(intValue2);
                logDetailMsg2.setNumber(intValue2);
                if (logData.length() > 14) {
                    String substring = logData.substring(10);
                    String str12 = Integer.valueOf(substring.substring(0, 4), 16) + "";
                    String str13 = Integer.valueOf(substring.substring(4, 6), 16) + "";
                    String str14 = Integer.valueOf(substring.substring(6, 8), 16) + "";
                    String str15 = Integer.valueOf(substring.substring(8, 10), 16) + "";
                    String str16 = Integer.valueOf(substring.substring(10, 12), 16) + "";
                    str5 = Integer.valueOf(substring.substring(12, 14), 16) + "";
                    str4 = str15;
                    str3 = str13;
                    str6 = str12;
                    str7 = str16;
                    str8 = "";
                    str9 = substring;
                    str2 = str14;
                } else {
                    str2 = "00";
                    str3 = "00";
                    str4 = str3;
                    str5 = str4;
                    str6 = str5;
                    str7 = str6;
                    str8 = "";
                    str9 = str7;
                }
                String substring2 = str9.substring(14);
                StringBuilder sb = new StringBuilder();
                sb.append(str6);
                if (str3.length() == 1) {
                    try {
                        str3 = "0" + str3;
                    } catch (Exception e) {
                        e = e;
                        str = TAG;
                        Log.e(str, e.getMessage());
                        return logDetailMsg;
                    }
                }
                sb.append(str3);
                if (str2.length() == 1) {
                    str2 = "0" + str2;
                }
                sb.append(str2);
                logDetailMsg2.setDate(sb.toString());
                StringBuilder sb2 = new StringBuilder();
                if (str4.length() == 1) {
                    str4 = "0" + str4;
                }
                sb2.append(str4);
                sb2.append(Constants.COLON_SEPARATOR);
                if (str7.length() == 1) {
                    str7 = "0" + str7;
                }
                sb2.append(str7);
                sb2.append(Constants.COLON_SEPARATOR);
                if (str5.length() == 1) {
                    str5 = "0" + str5;
                }
                sb2.append(str5);
                logDetailMsg2.setTime(sb2.toString());
                List<Device> deviceByUnicastAddress = Injection.repo().device().getDeviceByUnicastAddress(placeId, currentDeviceAddress);
                String substring3 = substring2.length() < 8 ? substring2 : substring2.substring(8);
                Device device = (deviceByUnicastAddress == null || deviceByUnicastAddress.size() <= 0) ? null : deviceByUnicastAddress.get(0);
                String str17 = "placeId:" + placeId + "__receivedAddress:" + currentDeviceAddress + "__device is " + device + "__instruct:" + substring3 + "__LogData:" + logData;
                str = TAG;
                try {
                    Log.e(str, str17);
                    if (intValue3 == 3) {
                        String[] strArr = {context.getString(R.string.app_str_short_press), context.getString(R.string.gqx_long_click), context.getString(R.string.app_str_levorotatory), context.getString(R.string.app_str_dextrorotatory), context.getString(R.string.app_str_touch)};
                        int parseInt = Integer.parseInt(substring3.substring(0, 2));
                        logDetailMsg2.setAction(parseInt > 5 ? str8 : strArr[parseInt - 1]);
                        int parseInt2 = Integer.parseInt(substring3.substring(2, 6), 16);
                        int parseInt3 = substring3.length() >= 8 ? Integer.parseInt(substring3.substring(6, 8), 16) : 0;
                        String[] strArr2 = {context.getString(R.string.app_str_key), context.getString(R.string.str_key_zone), context.getString(R.string.str_key_scene), context.getString(R.string.str_key_mode), context.getString(R.string.str_key_quick), context.getString(R.string.str_key_rgb), context.getString(R.string.str_key_w), context.getString(R.string.str_key_cw), context.getString(R.string.str_key_on), context.getString(R.string.str_key_touch), context.getString(R.string.str_touch_bar), context.getString(R.string.str_key_brt_plus), context.getString(R.string.str_key_brt_minus)};
                        if (parseInt3 != 0) {
                            if (parseInt3 == 1) {
                                str11 = context.getString(R.string.str_key_zone) + (BitUtils.convertIntToBit(parseInt2) + 1);
                            } else if (parseInt3 != 2) {
                                str11 = parseInt3 <= 12 ? strArr2[parseInt3] : str8;
                            } else {
                                str11 = context.getString(R.string.str_key_scene) + (BitUtils.convertIntToBit(parseInt2) + 1);
                            }
                        } else if (parseInt2 == 0) {
                            str11 = context.getString(R.string.gqx_knob);
                        } else {
                            str11 = context.getString(R.string.app_str_key) + (BitUtils.convertIntToBit(parseInt2) + 1);
                        }
                        logDetailMsg2.setControlName(str11);
                        str10 = substring3;
                    } else {
                        str10 = substring3;
                        logDetailMsg2.setAction(getDeviceControlAction(context, str10, null, device, placeId));
                        String controlObject = getControlObject(substring2, placeId);
                        if (!TextUtils.isEmpty(controlObject)) {
                            if (controlObject.contains("0x")) {
                                logDetailMsg2.setControlName(context.getString(R.string.app_str_src_address) + Constants.COLON_SEPARATOR + controlObject);
                            } else {
                                logDetailMsg2.setControlName(controlObject + context.getString(R.string.control));
                            }
                        }
                    }
                    if (intValue3 == 2) {
                        logDetailMsg2.setError("0x" + str10.substring(0, 2));
                    }
                    return logDetailMsg2;
                } catch (Exception e2) {
                    e = e2;
                    Log.e(str, e.getMessage());
                    return logDetailMsg;
                }
            } catch (Exception e3) {
                e = e3;
                str = TAG;
            }
        } catch (Exception e4) {
            e = e4;
            str = TAG;
            logDetailMsg = null;
        }
    }

    private static String getControlObject(String data, long placeId) {
        String substring = data.substring(0, 4);
        String substring2 = data.substring(4, 8);
        int parseInt = Integer.parseInt(substring, 16);
        Integer.parseInt(substring2, 16);
        if (parseInt <= 2000) {
            return BuildConfig.FLAVOR;
        }
        List<Device> deviceByUnicastAddress = Injection.repo().device().getDeviceByUnicastAddress(placeId, parseInt);
        if (deviceByUnicastAddress != null && deviceByUnicastAddress.size() > 0) {
            if (deviceByUnicastAddress.size() == 1) {
                return deviceByUnicastAddress.get(0).getDeviceName();
            }
            for (Device device : deviceByUnicastAddress) {
                if (!device.isSubDevice()) {
                    return device.getDeviceName();
                }
            }
            return deviceByUnicastAddress.get(0).getDeviceName();
        }
        Log.e(TAG, "未找到发送地址的设备：" + substring);
        return "0x" + substring2;
    }

    private static String parseC0(Context context, String cmd) {
        int parseInt = Integer.parseInt(cmd.substring(10, 12), 16);
        if (parseInt == 18) {
            return context.getString(R.string.device_power_on);
        }
        if (parseInt == 20) {
            return context.getString(R.string.device_network_access);
        }
        if (parseInt == 19) {
            return context.getString(R.string.device_network_exit);
        }
        return "";
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String parseC6(android.content.Context r8, java.lang.String r9, com.ltech.smarthome.model.bean.Device r10) {
        /*
            Method dump skipped, instructions count: 446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.log.LocalLogHelper.parseC6(android.content.Context, java.lang.String, com.ltech.smarthome.model.bean.Device):java.lang.String");
    }

    private static String parseC7(Context context, String instruct, Device device) {
        String str;
        List<Device> deviceByUnicastAddress;
        int intValue = Integer.valueOf(instruct.substring(2, 6), 16).intValue();
        int intValue2 = Integer.valueOf(instruct.substring(6, 8), 16).intValue();
        if (device != null && device.getProductId().equalsIgnoreCase(ProductId.ID_BLE_KBS) && (deviceByUnicastAddress = Injection.repo().device().getDeviceByUnicastAddress(device.getPlaceId(), device.getUnicastAddress())) != null) {
            for (int i = 0; i < deviceByUnicastAddress.size(); i++) {
                if (intValue == 1) {
                    if (deviceByUnicastAddress.get(i).getWifiMac().contains("_1")) {
                        str = deviceByUnicastAddress.get(i).getDeviceName();
                        break;
                    }
                } else {
                    if (intValue == 2 && deviceByUnicastAddress.get(i).getWifiMac().contains("_2")) {
                        str = deviceByUnicastAddress.get(i).getDeviceName();
                        break;
                    }
                }
            }
        }
        str = "";
        if (intValue2 == 0) {
            return str + context.getString(R.string.close);
        }
        if (intValue2 == 1) {
            return str + context.getString(R.string.on);
        }
        if (intValue2 == 2) {
            return str + context.getString(R.string.cancel_selection);
        }
        if (intValue2 == 3) {
            return str + context.getString(R.string.reverse_control);
        }
        if (intValue2 != 4) {
            return "";
        }
        return str + context.getString(R.string.app_str_bind_control);
    }

    private static String parseC8(Context context, String instruct, Device device) {
        String[] strArr;
        int i;
        int i2;
        if (device != null) {
            int lightColorType = ProductRepository.getLightColorType((Object) device);
            if (lightColorType == 2) {
                strArr = context.getResources().getStringArray(R.array.ct_light_default_mode_name);
            } else if (lightColorType == 1) {
                strArr = context.getResources().getStringArray(R.array.dim_light_default_mode_name);
            } else {
                strArr = context.getResources().getStringArray(R.array.color_light_default_mode_name);
            }
        } else {
            strArr = null;
        }
        String substring = instruct.substring(12, 16);
        int intValue = Integer.valueOf(substring.substring(substring.length() - 1), 16).intValue();
        int intValue2 = Integer.valueOf(substring.substring(substring.length() - 2, substring.length() - 1), 16).intValue();
        int intValue3 = Integer.valueOf(substring.substring(substring.length() - 3, substring.length() - 2), 16).intValue();
        int intValue4 = Integer.valueOf(substring.substring(substring.length() - 4, substring.length() - 3), 16).intValue();
        if (intValue > 0) {
            i2 = intValue;
            i = 1;
        } else {
            i = 0;
            i2 = 0;
        }
        if (intValue2 > 0) {
            i++;
            i2 = intValue2 + 4;
        }
        if (intValue3 > 0) {
            i++;
            i2 = intValue3 + 8;
        }
        if (intValue4 > 0) {
            i++;
            i2 = intValue4 + 12;
        }
        if (i > 1) {
            return context.getString(R.string.theme) + context.getString(R.string.play_list);
        }
        if (strArr != null) {
            return context.getString(R.string.theme) + "\"" + strArr[i2 - 1] + "\"";
        }
        return context.getString(R.string.theme);
    }

    private static String parseC9(Context context, String instruct, Device device) {
        String[] strArr;
        String str;
        int i;
        int i2;
        int intValue = Integer.valueOf(instruct.substring(6, 8), 16).intValue();
        if (device == null) {
            strArr = null;
        } else if (ProductRepository.getLightColorType((Object) device) == 2) {
            strArr = context.getResources().getStringArray(R.array.general_mode_ct_name);
        } else {
            strArr = context.getResources().getStringArray(R.array.general_mode_default_name);
        }
        str = "";
        if (intValue != 1) {
            if (intValue == 2) {
                int intValue2 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
                StringBuilder sb = new StringBuilder();
                sb.append(context.getString(R.string.general_mode));
                sb.append(" Update Name ");
                sb.append(strArr != null ? strArr[intValue2 - 1] : "");
                return sb.toString();
            }
            if (intValue == 3) {
                int intValue3 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(context.getString(R.string.general_mode));
                sb2.append(" Update Data ");
                sb2.append(strArr != null ? strArr[intValue3 - 1] : "");
                return sb2.toString();
            }
            return context.getString(R.string.general_mode) + intValue;
        }
        int intValue4 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
        if (intValue4 == 0) {
            context.getString(R.string.musicplayer_action_stop);
        } else if (intValue4 == 1) {
            context.getString(R.string.musicplayer_action_play);
        } else if (intValue4 == 2) {
            context.getString(R.string.save);
            context.getString(R.string.play_super_panel_music);
        } else if (intValue4 == 3) {
            context.getString(R.string.preview);
            context.getString(R.string.play_super_panel_music);
        } else if (intValue4 == 4) {
            context.getString(R.string.save);
        }
        String substring = instruct.substring(12, 16);
        int intValue5 = Integer.valueOf(substring.substring(substring.length() - 1), 16).intValue();
        int intValue6 = Integer.valueOf(substring.substring(substring.length() - 2, substring.length() - 1), 16).intValue();
        int intValue7 = Integer.valueOf(substring.substring(substring.length() - 3, substring.length() - 2), 16).intValue();
        int intValue8 = Integer.valueOf(substring.substring(substring.length() - 4, substring.length() - 3), 16).intValue();
        if (intValue5 > 0) {
            i2 = intValue5;
            i = 1;
        } else {
            i = 0;
            i2 = 0;
        }
        if (intValue6 > 0) {
            i++;
            i2 = intValue6 + 4;
        }
        if (intValue7 > 0) {
            i++;
            i2 = intValue7 + 8;
        }
        if (intValue8 > 0) {
            i++;
            i2 = intValue8 + 12;
        }
        if (i > 1) {
            return context.getString(R.string.general_mode) + context.getString(R.string.play_list);
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(context.getString(R.string.general_mode));
        if (strArr != null) {
            str = "\"" + strArr[i2 - 1] + "\"";
        }
        sb3.append(str);
        return sb3.toString();
    }

    private static String parseCA(Context context, String instruct, Device device) {
        String[] strArr;
        int i;
        int i2;
        String str;
        int intValue = Integer.valueOf(instruct.substring(6, 8), 16).intValue();
        Log.e(TAG, "instruct:" + instruct + "__subCmd:" + intValue + "__device:" + device);
        if (device != null) {
            int lightColorType = ProductRepository.getLightColorType((Object) device);
            Log.e(TAG, "parseCA.instruct:" + instruct + "__type:" + lightColorType);
            if (lightColorType == 1) {
                strArr = context.getResources().getStringArray(R.array.advanced_mode_dim_name);
            } else if (lightColorType == 2) {
                strArr = context.getResources().getStringArray(R.array.advanced_mode_ct_name);
            } else {
                strArr = context.getResources().getStringArray(R.array.advanced_mode_default_name);
            }
        } else {
            strArr = null;
        }
        if (intValue != 1) {
            if (intValue == 2) {
                return context.getString(R.string.advanced_mode) + " Update Name";
            }
            if (intValue == 3) {
                return context.getString(R.string.advanced_mode) + " Update Data";
            }
            return context.getString(R.string.advanced_mode) + intValue;
        }
        String substring = instruct.substring(12, 16);
        int intValue2 = Integer.valueOf(substring.substring(substring.length() - 1), 16).intValue();
        int intValue3 = Integer.valueOf(substring.substring(substring.length() - 2, substring.length() - 1), 16).intValue();
        int intValue4 = Integer.valueOf(substring.substring(substring.length() - 3, substring.length() - 2), 16).intValue();
        int intValue5 = Integer.valueOf(substring.substring(substring.length() - 4, substring.length() - 3), 16).intValue();
        if (intValue2 > 0) {
            i2 = intValue2;
            i = 1;
        } else {
            i = 0;
            i2 = 0;
        }
        if (intValue3 > 0) {
            i++;
            i2 = intValue3 + 4;
        }
        if (intValue4 > 0) {
            i++;
            i2 = intValue4 + 8;
        }
        if (intValue5 > 0) {
            i++;
            i2 = intValue5 + 12;
        }
        Log.e(TAG, "parseCA.themeSum:" + i + "__themeIndex:" + i2);
        if (i > 1) {
            return context.getString(R.string.advanced_mode) + context.getString(R.string.play_list);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.advanced_mode));
        if (strArr != null) {
            str = "\"" + strArr[i2 - 1] + "\"";
        } else {
            str = "";
        }
        sb.append(str);
        return sb.toString();
    }

    private static String parseCB(Context context, String instruct) {
        String str;
        Scene localSceneBySceneNum;
        int intValue = Integer.valueOf(instruct.substring(6, 8)).intValue();
        int intValue2 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
        IScene scene = Injection.repo().scene();
        if (scene != null && (localSceneBySceneNum = scene.getLocalSceneBySceneNum(intValue2)) != null) {
            str = localSceneBySceneNum.getSceneName();
        } else {
            str = "";
        }
        if (intValue == 1) {
            return context.getString(R.string.save) + context.getString(R.string.local_scene) + Constants.COLON_SEPARATOR + str;
        }
        if (intValue == 2) {
            return context.getString(R.string.app_execute) + context.getString(R.string.local_scene) + Constants.COLON_SEPARATOR + str;
        }
        return context.getString(R.string.local_scene) + Constants.COLON_SEPARATOR + intValue;
    }

    private static String parseCD(Context context, String instruct) {
        Integer.valueOf(instruct.substring(6, 8)).intValue();
        return context.getString(R.string.category_sensor) + ":value:" + Integer.valueOf(instruct.substring(8, 12), 16).intValue();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004f, code lost:
    
        if (r11.equals(com.ltech.smarthome.model.product.ProductId.ID_SENSOR_MS03) == false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String parseCF(android.content.Context r9, java.lang.String r10, com.ltech.smarthome.model.bean.Device r11) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.log.LocalLogHelper.parseCF(android.content.Context, java.lang.String, com.ltech.smarthome.model.bean.Device):java.lang.String");
    }

    private static String parseD0(Context context, String instruct, Device device, long placeId) {
        String str = "";
        if (device == null) {
            return "";
        }
        int intValue = Integer.valueOf(instruct.substring(6, 8), 16).intValue();
        String string = context.getString(R.string.adjust_to);
        switch (intValue) {
            case 1:
            case 9:
            case 14:
                int intValue2 = Integer.valueOf(instruct.substring(14, 16), 16).intValue();
                StringBuilder sb = new StringBuilder();
                sb.append(intValue2 == 0 ? context.getString(R.string.close) : context.getString(R.string.on));
                sb.append(getAirconSubDeviceName(instruct, placeId, device.getUnicastAddress()));
                break;
            case 2:
            case 10:
            case 15:
                break;
            case 3:
            case 11:
                int intValue3 = Integer.valueOf(instruct.substring(14, 16), 16).intValue();
                if (intValue == 3) {
                    str = new String[]{context.getString(R.string.air_mode_1), context.getString(R.string.air_mode_5), context.getString(R.string.refreshing), context.getString(R.string.air_mode_4), context.getString(R.string.automatic_dehumidification), context.getString(R.string.intimate_sleep), "", context.getString(R.string.air_mode_2), context.getString(R.string.floor_heat), context.getString(R.string.strong_heating)}[intValue3 - 1];
                } else if (intValue == 11) {
                    str = new String[]{context.getString(R.string.fan_speed_1), context.getString(R.string.air_exchange), context.getString(R.string.exhaust), context.getString(R.string.intelligent), context.getString(R.string.powerful), context.getString(R.string.power_saving), context.getString(R.string.air_mode_4), context.getString(R.string.bypass), context.getString(R.string.quick_clean), context.getString(R.string.comfort), context.getString(R.string.cool_breeze), context.getString(R.string.app_manual), context.getString(R.string.ir_mute), context.getString(R.string.new_air_name), context.getString(R.string.air_mode_1), context.getString(R.string.air_mode_2), context.getString(R.string.air_mode_5), context.getString(R.string.heat_exchange), context.getString(R.string.inner_circulation), context.getString(R.string.outer_circulation), context.getString(R.string.mixed_air), context.getString(R.string.close)}[intValue3];
                }
                break;
            case 4:
            case 12:
            case 16:
                int intValue4 = Integer.valueOf(instruct.substring(14, 16), 16).intValue();
                if (intValue == 4) {
                    str = new String[]{context.getString(R.string.air_mode_3), context.getString(R.string.high_speed), context.getString(R.string.medium_speed), context.getString(R.string.medium_high_speed), context.getString(R.string.low_speed), context.getString(R.string.medium_low_speed)}[intValue4];
                } else if (intValue == 12) {
                    str = new String[]{context.getString(R.string.air_mode_3), context.getString(R.string.high_speed), context.getString(R.string.medium_speed), context.getString(R.string.medium_high_speed), context.getString(R.string.low_speed), context.getString(R.string.medium_low_speed), context.getString(R.string.close)}[intValue4];
                }
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
            case 13:
            case 18:
                int intValue5 = Integer.valueOf(instruct.substring(14, 16), 16).intValue();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(context.getString(R.string.unification));
                sb2.append(intValue5 == 0 ? context.getString(R.string.close) : context.getString(R.string.on));
                sb2.append(getAirconSubDeviceName(instruct, placeId, device.getUnicastAddress()));
                break;
            case 17:
                int intValue6 = Integer.valueOf(instruct.substring(14, 16), 16).intValue();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getAirconSubDeviceName(instruct, placeId, device.getUnicastAddress()));
                sb3.append(context.getString(R.string.freeze_protection));
                sb3.append(intValue6 == 0 ? context.getString(R.string.close) : context.getString(R.string.on));
                break;
            case 19:
                break;
            case 20:
                int intValue7 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
                int intValue8 = Integer.valueOf(instruct.substring(10, 12), 16).intValue();
                int intValue9 = Integer.valueOf(instruct.substring(12, 14), 16).intValue();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(context.getString(intValue9 == 80 ? R.string.ac : R.string.new_air));
                sb4.append(context.getString(R.string.timing));
                sb4.append(intValue8 * 0.5d);
                sb4.append("h");
                sb4.append(intValue7 == 0 ? context.getString(R.string.close) : context.getString(R.string.on));
                break;
        }
        return "";
    }

    public static String getAirconSubDeviceName(String instruct, long placeId, int unicastAddress) {
        int intValue = Integer.valueOf(instruct.substring(10, 12)).intValue();
        int intValue2 = Integer.valueOf(instruct.substring(12, 14)).intValue();
        for (Device device : Injection.repo().device().getDeviceByUnicastAddress(placeId, unicastAddress)) {
            if (device.isSubDevice()) {
                CentralAirSubDeviceParam centralAirSubDeviceParam = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
                if (centralAirSubDeviceParam.outAddr == intValue && centralAirSubDeviceParam.inAddr == intValue2) {
                    return device.getDeviceName();
                }
            }
        }
        return "";
    }

    private static String parseD4(Context context, String instruct) {
        String str;
        Scene localSceneBySceneNum;
        int intValue = Integer.valueOf(instruct.substring(6, 8)).intValue();
        int intValue2 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
        IScene scene = Injection.repo().scene();
        if (scene != null && (localSceneBySceneNum = scene.getLocalSceneBySceneNum(intValue2)) != null) {
            str = localSceneBySceneNum.getSceneName();
        } else {
            str = "";
        }
        if (intValue == 2) {
            return context.getString(R.string.local_scene) + context.getString(R.string.app_execute) + Constants.COLON_SEPARATOR + str;
        }
        if (intValue == 4) {
            return context.getString(R.string.local_scene) + context.getString(R.string.delete) + Constants.COLON_SEPARATOR + str;
        }
        if (intValue == 9) {
            return "DaLi" + context.getString(R.string.scenes) + context.getString(R.string.app_execute) + Constants.COLON_SEPARATOR + str;
        }
        if (intValue == 1 || intValue == 3) {
            return context.getString(R.string.local_scene) + context.getString(R.string.save) + Constants.COLON_SEPARATOR + str;
        }
        return context.getString(R.string.local_scene) + Constants.COLON_SEPARATOR + intValue + Constants.COLON_SEPARATOR + str;
    }

    private static String parseD5(Context context, String instruct) {
        int intValue = Integer.valueOf(instruct.substring(6, 8)).intValue();
        if (intValue == 1) {
            return context.getString(R.string.setting) + " " + context.getString(R.string.circadian_lighting);
        }
        String str = "";
        if (intValue == 2) {
            int intValue2 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
            if (intValue2 == 1) {
                return context.getString(R.string.circadian_lighting) + " " + context.getString(Integer.valueOf(instruct.substring(10, 12)).intValue() == 0 ? R.string.off_1 : R.string.on_1);
            }
            if (intValue2 == 2) {
                return context.getString(R.string.circadian_lighting) + Constants.COLON_SEPARATOR + context.getString(Integer.valueOf(instruct.substring(10, 12)).intValue() == 0 ? R.string.preview_stop : R.string.start);
            }
            if (intValue2 == 3) {
                if (instruct.length() == 12) {
                    int intValue3 = Integer.valueOf(instruct.substring(10, 12)).intValue();
                    if (intValue3 == 1) {
                        str = context.getString(R.string.app_light_sunset_sunrise);
                    } else if (intValue3 == 2) {
                        str = context.getString(R.string.app_light_plan_time);
                    }
                    return context.getString(R.string.circadian_lighting) + Constants.COLON_SEPARATOR + str;
                }
                if (instruct.length() == 14) {
                    Integer.valueOf(instruct.substring(10, 12)).intValue();
                    return context.getString(R.string.circadian_lighting) + Constants.COLON_SEPARATOR + context.getString(R.string.app_light_plan) + Integer.valueOf(instruct.substring(12, 14)).intValue();
                }
            } else {
                if (intValue2 == 4) {
                    return context.getString(R.string.circadian_lighting) + " " + Integer.valueOf(instruct.substring(10, 12)).intValue() + Constants.COLON_SEPARATOR + Integer.valueOf(instruct.substring(12, 14)).intValue() + "--" + Integer.valueOf(instruct.substring(14, 16)).intValue() + Constants.COLON_SEPARATOR + Integer.valueOf(instruct.substring(16, 18)).intValue();
                }
                if (intValue2 == 5) {
                    int intValue4 = Integer.valueOf(instruct.substring(10, 12)).intValue();
                    if (intValue4 == 128) {
                        return context.getString(R.string.circadian_lighting) + " " + context.getString(R.string.only_once);
                    }
                    char[] charArray = Integer.toBinaryString(intValue4).toCharArray();
                    String[] strArr = {context.getString(R.string.mon), context.getString(R.string.tue), context.getString(R.string.wed), context.getString(R.string.thur), context.getString(R.string.fri), context.getString(R.string.sat), context.getString(R.string.sun)};
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < charArray.length; i++) {
                        if (charArray[i] == '1') {
                            if (i == charArray.length - 1) {
                                stringBuffer.append(strArr[i]);
                            } else {
                                stringBuffer.append(strArr[i]).append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                            }
                        }
                    }
                    return context.getString(R.string.circadian_lighting) + " " + stringBuffer.toString();
                }
            }
        }
        return "";
    }

    private static String parseC3(Context context, String instruct, String[] c3Cmd) {
        String str;
        Scene localSceneBySceneNum;
        String str2;
        Integer.valueOf(instruct.substring(2, 6), 16).intValue();
        int intValue = Integer.valueOf(instruct.substring(6, 8), 16).intValue();
        str = "";
        if (intValue == 3) {
            int intValue2 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
            int intValue3 = Integer.valueOf(instruct.substring(10, 12), 16).intValue();
            if (intValue3 == 1) {
                str2 = context.getString(R.string.on);
            } else if (intValue3 == 2) {
                str2 = context.getString(R.string.close);
            } else if (intValue3 == 3) {
                str2 = context.getString(R.string.app_str_power_off_memory);
            } else if (intValue3 != 4) {
                str2 = "";
            } else {
                str2 = context.getString(R.string.mode_diy);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(context.getString(R.string.app_str_power_on_status));
            sb.append(" ");
            sb.append(intValue2 == 0 ? context.getString(R.string.reset_factory_default) : "");
            sb.append(" ");
            sb.append(str2);
            return sb.toString();
        }
        if (intValue != 60) {
            if (intValue == 54) {
                int intValue4 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
                String string = context.getString(R.string.app_str_elec);
                String string2 = context.getString(R.string.app_str_power_off_memory);
                if (intValue4 == 3) {
                    int intValue5 = Integer.valueOf(instruct.substring(10, 12), 16).intValue();
                    int intValue6 = Integer.valueOf(instruct.substring(12, 14), 16).intValue();
                    if (intValue6 > 0) {
                        IScene scene = Injection.repo().scene();
                        if (scene != null && (localSceneBySceneNum = scene.getLocalSceneBySceneNum(intValue6)) != null) {
                            str = localSceneBySceneNum.getSceneName();
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(context.getString(R.string.setting));
                        sb2.append(" ");
                        if (intValue5 != 1) {
                            string = string2;
                        }
                        sb2.append(string);
                        sb2.append(" ");
                        sb2.append(context.getString(R.string.relate_scene));
                        sb2.append(" ");
                        sb2.append(str);
                        return sb2.toString();
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(context.getString(R.string.setting));
                    sb3.append(" ");
                    if (intValue5 != 1) {
                        string = string2;
                    }
                    sb3.append(string);
                    sb3.append(" ");
                    sb3.append(context.getString(R.string.reset_relate));
                    return sb3.toString();
                }
                if (intValue4 == 4) {
                    int intValue7 = Integer.valueOf(instruct.substring(10, 12), 16).intValue();
                    if (instruct.length() >= 14) {
                        Integer valueOf = Integer.valueOf(instruct.substring(12, 14), 16);
                        valueOf.intValue();
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(context.getString(R.string.setting));
                        sb4.append(" ");
                        if (intValue7 != 1) {
                            string = string2;
                        }
                        sb4.append(string);
                        sb4.append(" ");
                        sb4.append(context.getString(R.string.delay));
                        sb4.append(valueOf);
                        return sb4.toString();
                    }
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(context.getString(R.string.setting));
                    sb5.append(" ");
                    if (intValue7 != 1) {
                        string = string2;
                    }
                    sb5.append(string);
                    return sb5.toString();
                }
            } else {
                if (intValue == 41) {
                    int intValue8 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
                    int intValue9 = Integer.valueOf(instruct.substring(10, 12), 16).intValue();
                    if (intValue8 == 1) {
                        if (intValue9 == 1) {
                            return context.getString(R.string.modify_now) + context.getString(R.string.hex_data);
                        }
                        if (intValue9 == 2) {
                            return context.getString(R.string.modify_now) + context.getString(R.string.ascii_data);
                        }
                    } else {
                        if (intValue8 == 2) {
                            return context.getString(R.string.modify_now) + context.getString(R.string.baud_rate) + new int[]{1200, WinBase.CBR_4800, 9600, WinBase.CBR_19200, WinBase.CBR_38400, 57600, 115200, 2400, 250000}[intValue9 - 1];
                        }
                        if (intValue8 == 3) {
                            if (intValue9 == 1) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.even_parity);
                            }
                            if (intValue9 == 2) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.odd_parity);
                            }
                            if (intValue9 == 3) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.none_parity);
                            }
                        } else if (intValue8 == 4) {
                            if (intValue9 == 1) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.data_bits) + "7";
                            }
                            if (intValue9 == 2) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.data_bits) + "8";
                            }
                            if (intValue9 == 3) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.data_bits) + "9";
                            }
                        } else if (intValue8 == 5) {
                            if (intValue9 == 1) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.stop_bits) + "1";
                            }
                            if (intValue9 == 2) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.stop_bits) + com.ez.player.BuildConfig.VERSION_NAME;
                            }
                            if (intValue9 == 3) {
                                return context.getString(R.string.modify_now) + context.getString(R.string.stop_bits) + "2";
                            }
                        }
                    }
                    return "";
                }
                if (intValue == 19 && instruct.length() >= 12) {
                    int intValue10 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
                    int intValue11 = Integer.valueOf(instruct.substring(10, 12), 16).intValue();
                    if (intValue10 == 1) {
                        if (intValue11 == 1) {
                            return context.getString(R.string.device_type) + " " + context.getString(R.string.app_str_curtain);
                        }
                        if (intValue11 == 2) {
                            return context.getString(R.string.device_type) + " " + context.getString(R.string.set_composition_15);
                        }
                        if (intValue11 == 3) {
                            return context.getString(R.string.device_type) + " " + context.getString(R.string.set_composition_8);
                        }
                        if (intValue11 == 4) {
                            return context.getString(R.string.device_type) + " " + context.getString(R.string.set_dream_curtain);
                        }
                    } else if (intValue10 == 2) {
                        if (intValue11 == 1) {
                            return context.getString(R.string.device_type) + " " + context.getString(R.string.app_dry_to_ble_type1);
                        }
                        if (intValue11 == 2) {
                            return context.getString(R.string.device_type) + " " + context.getString(R.string.app_dry_to_ble_type2);
                        }
                        if (intValue11 == 3) {
                            return context.getString(R.string.device_type) + " " + context.getString(R.string.app_dry_to_ble_type3);
                        }
                    } else if (intValue10 == 3) {
                        if (intValue11 == 1) {
                            return context.getString(R.string.app_control_mode) + " " + context.getString(R.string.app_str_trig_control_mode_2);
                        }
                        if (intValue11 == 2) {
                            return context.getString(R.string.app_control_mode) + " " + context.getString(R.string.app_str_trig_control_mode_3);
                        }
                    }
                } else {
                    if (intValue == 45) {
                        return context.getString(R.string.reset_factory_default);
                    }
                    if (intValue < c3Cmd.length) {
                        return c3Cmd[intValue - 1];
                    }
                }
            }
            return "";
        }
        int intValue12 = Integer.valueOf(instruct.substring(8, 10), 16).intValue();
        if (intValue12 == 0) {
            str = context.getString(R.string.app_dry_to_ble_type2);
        }
        if (intValue12 == 1) {
            str = context.getString(R.string.app_dry_to_ble_type1);
        }
        return context.getString(R.string.setting) + " " + context.getString(R.string.app_str_switch_control_type) + str;
    }

    private static String parseC2(Context context, String instruct, long placeId, String[] cmds) {
        int intValue = Integer.valueOf(instruct.substring(6, 8), 16).intValue();
        if (intValue == 18) {
            return context.getString(R.string.app_set_cg485_add);
        }
        if (intValue == 19) {
            return context.getString(R.string.app_set_cg485_delete);
        }
        if (intValue == 1) {
            String deviceGroupName = getDeviceGroupName(placeId, Integer.valueOf(instruct.substring(8, 12), 16).intValue());
            if (TextUtils.isEmpty(deviceGroupName)) {
                return cmds[0] + ":0x" + instruct.substring(8, 12);
            }
            return cmds[0] + deviceGroupName;
        }
        if (intValue == 2) {
            String deviceGroupName2 = getDeviceGroupName(placeId, Integer.valueOf(instruct.substring(8, 12), 16).intValue());
            if (TextUtils.isEmpty(deviceGroupName2)) {
                return cmds[1] + ":0x" + instruct.substring(8, 12);
            }
            return cmds[1] + deviceGroupName2;
        }
        return "";
    }

    private static String getDeviceGroupName(long placeId, int address) {
        if (address >= 49152) {
            List<Group> queryGroupByUnicastAddress = Injection.repo().group().queryGroupByUnicastAddress(placeId, address);
            if (queryGroupByUnicastAddress != null && queryGroupByUnicastAddress.size() > 0) {
                return queryGroupByUnicastAddress.get(0).getGroupName();
            }
            return "";
        }
        List<Device> deviceByUnicastAddress = Injection.repo().device().getDeviceByUnicastAddress(placeId, address);
        if (deviceByUnicastAddress != null && deviceByUnicastAddress.size() > 0) {
            return deviceByUnicastAddress.get(0).getDeviceName();
        }
        return "";
    }
}