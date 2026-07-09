package com.ltech.smarthome.ui.device.ir;

import com.blankj.utilcode.util.ActivityUtils;
import com.hzy.tvmao.ir.ac.ACStateV2;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class AcRepository {
    public static IrKeyItem getAcModeInfo(int mode) {
        if (mode == 0) {
            return new IrKeyItem(R.mipmap.icon_ir_cool, getModeTypeString(mode), -1);
        }
        if (mode == 1) {
            return new IrKeyItem(R.mipmap.icon_ir_heat, getModeTypeString(mode), -1);
        }
        if (mode == 2) {
            return new IrKeyItem(R.mipmap.icon_ir_auto, getModeTypeString(mode), -1);
        }
        if (mode == 3) {
            return new IrKeyItem(R.mipmap.icon_ir_autowind, getModeTypeString(mode), -1);
        }
        if (mode == 4) {
            return new IrKeyItem(R.mipmap.icon_ir_dry, getModeTypeString(mode), -1);
        }
        throw new RuntimeException("no key type");
    }

    public static String getModeTypeString(int mode) {
        if (mode == 0) {
            return ActivityUtils.getTopActivity().getString(R.string.air_mode_1);
        }
        if (mode == 1) {
            return ActivityUtils.getTopActivity().getString(R.string.air_mode_2);
        }
        if (mode == 2) {
            return ActivityUtils.getTopActivity().getString(R.string.air_mode_3);
        }
        if (mode == 3) {
            return ActivityUtils.getTopActivity().getString(R.string.air_mode_4);
        }
        if (mode == 4) {
            return ActivityUtils.getTopActivity().getString(R.string.air_mode_5);
        }
        return "";
    }

    public static IrKeyItem getWindSpeedInfo(int windSpeed) {
        if (windSpeed == 0) {
            return new IrKeyItem(R.mipmap.icon_ir_autowind, R.string.fan_speed_1, -1);
        }
        if (windSpeed == 1) {
            return new IrKeyItem(R.mipmap.icon_ir_lowwind, R.string.fan_speed_2, -1);
        }
        if (windSpeed == 2) {
            return new IrKeyItem(R.mipmap.icon_ir_midwind, R.string.fan_speed_3, -1);
        }
        return new IrKeyItem(R.mipmap.icon_ir_highwind, R.string.fan_speed_4, -1);
    }

    public static String getWindSpeedString(int windSpeed) {
        if (windSpeed == 0) {
            return ActivityUtils.getTopActivity().getString(R.string.fan_speed_1);
        }
        if (windSpeed == 1) {
            return ActivityUtils.getTopActivity().getString(R.string.fan_speed_2);
        }
        if (windSpeed == 2) {
            return ActivityUtils.getTopActivity().getString(R.string.fan_speed_3);
        }
        return ActivityUtils.getTopActivity().getString(R.string.fan_speed_4);
    }

    public static String getDirectTypeString(int type) {
        if (type == 0) {
            return ActivityUtils.getTopActivity().getString(R.string.air_swing);
        }
        return ActivityUtils.getTopActivity().getString(R.string.air_fixed);
    }

    public static IrKeyItem getDirectTypeInfo(boolean swing) {
        if (swing) {
            return new IrKeyItem(R.mipmap.icon_ir_swing, R.string.air_swing, -1);
        }
        return new IrKeyItem(R.mipmap.icon_ir_freeze, R.string.air_direct, -1);
    }

    public static Object[][] getModeKey() {
        return new Object[][]{new Object[]{"制冷", (byte) 0}, new Object[]{"制热", (byte) 1}, new Object[]{"自动", (byte) 2}, new Object[]{"送风", (byte) 3}, new Object[]{"抽湿", (byte) 4}};
    }

    public static Object[][] getWindSpeedKey() {
        return new Object[][]{new Object[]{"自动", (byte) 0}, new Object[]{"小风", (byte) 1}, new Object[]{"中风", (byte) 2}, new Object[]{"大风", (byte) 3}};
    }

    public static Object[][] getSwingModeKey() {
        return new Object[][]{new Object[]{"定风", ACStateV2.UDWindDirectKey.UDDIRECT_KEY_FIX}, new Object[]{"扫风", ACStateV2.UDWindDirectKey.UDDIRECT_KEY_SWING}};
    }
}