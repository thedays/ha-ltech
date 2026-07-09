package com.ltech.smarthome.ui.device.ir;

import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class IrKeyRepository {
    public static final int ID_0 = 56;
    public static final int ID_1 = 61;
    public static final int ID_2 = 66;
    public static final int ID_3 = 71;
    public static final int ID_4 = 76;
    public static final int ID_5 = 81;
    public static final int ID_6 = 86;
    public static final int ID_7 = 91;
    public static final int ID_8 = 96;
    public static final int ID_9 = 101;
    public static final int ID_ANION_AC = 31;
    public static final int ID_APPOINTMENT_BATH = 1299;
    public static final int ID_BACK = 116;
    public static final int ID_CHANNEL_DOWN = 44;
    public static final int ID_CHANNEL_UP = 43;
    public static final int ID_FAN_SPEED = 9367;
    public static final int ID_HOME_PAGE = 136;
    public static final int ID_INPUT = 111;
    public static final int ID_MEDIUM_TEMPERATURE_INSULATION = 1298;
    public static final int ID_MENU = 45;
    public static final int ID_MODE = 2;
    public static final int ID_MUTE = 106;
    public static final int ID_NAVIGATE_DOWN = 47;
    public static final int ID_NAVIGATE_LEFT = 48;
    public static final int ID_NAVIGATE_RIGHT = 49;
    public static final int ID_NAVIGATE_UP = 46;
    public static final int ID_OK = 42;
    public static final int ID_PLAY = 146;
    public static final int ID_POWER = 1;
    public static final int ID_PTT = 1762;
    public static final int ID_SET = 437;
    public static final int ID_SLEEP = 22;
    public static final int ID_SWING = 9362;
    public static final int ID_SWING_MODE = 9372;
    public static final int ID_TEMPERATURE_DOWN = 4;
    public static final int ID_TEMPERATURE_UP = 3;
    public static final int ID_TIMING = 23;
    public static final int ID_TV_POWER = -2;
    public static final int ID_VOLUME_DOWN = 51;
    public static final int ID_VOLUME_UP = 50;
    public static final int ID_ZOOM_DOWN = 2812;
    public static final int ID_ZOOM_UP = 2807;
    public static final int MORE = -1;

    public static IrKeyItem getIrKeyItem(int type) {
        if (type == -2) {
            return new IrKeyItem(R.mipmap.icon_ir_tv_power, R.string.ir_tv_power, -2);
        }
        if (type == -1) {
            return new IrKeyItem(R.mipmap.icon_ir_more, R.string.more, -1);
        }
        if (type == 1) {
            return new IrKeyItem(R.mipmap.icon_ir_power, R.string.ir_power, 1);
        }
        if (type == 2) {
            return new IrKeyItem(R.mipmap.icon_ir_mode, R.string.ir_mode, 2);
        }
        if (type == 3) {
            return new IrKeyItem(R.mipmap.icon_ir_increase, R.string.ir_temperature_up, 3);
        }
        if (type == 4) {
            return new IrKeyItem(R.mipmap.icon_ir_reduce, R.string.ir_temperature_down, 4);
        }
        if (type == 22) {
            return new IrKeyItem(R.mipmap.icon_ir_sleeping, R.string.ir_sleep, 22);
        }
        if (type == 23) {
            return new IrKeyItem(R.mipmap.icon_ir_timing, R.string.ir_timing, 23);
        }
        if (type == 31) {
            return new IrKeyItem(R.mipmap.icon_ir_anion, R.string.ir_anion, 31);
        }
        if (type == 106) {
            return new IrKeyItem(R.mipmap.icon_ir_mute, R.string.ir_mute, 106);
        }
        if (type == 437) {
            return new IrKeyItem(R.mipmap.icon_ir_setting, R.string.ir_setting, ID_SET);
        }
        if (type == 1762) {
            return new IrKeyItem(R.mipmap.icon_ir_onestep, R.string.ir_ptt, 1762);
        }
        if (type == 9362) {
            return new IrKeyItem(R.mipmap.icon_ir_swing, R.string.ir_wind_swing, ID_SWING);
        }
        if (type == 9367) {
            return new IrKeyItem(R.mipmap.icon_ir_highwind, R.string.ir_wind_speed, ID_FAN_SPEED);
        }
        if (type == 9372) {
            return new IrKeyItem(R.mipmap.icon_ir_wind_type, R.string.ir_wind_type, ID_SWING_MODE);
        }
        if (type == 50) {
            return new IrKeyItem(R.mipmap.icon_ir_increase, R.string.ir_volume_up, 50);
        }
        if (type == 51) {
            return new IrKeyItem(R.mipmap.icon_ir_reduce, R.string.ir_volume_down, 51);
        }
        if (type == 1298) {
            return new IrKeyItem(R.mipmap.icon_ir_keep_temp, R.string.ir_keep_temp, ID_MEDIUM_TEMPERATURE_INSULATION);
        }
        if (type != 1299) {
            switch (type) {
                case 42:
                    return new IrKeyItem(R.mipmap.icon_ir_confirm, R.string.ir_ok, 42);
                case 43:
                    return new IrKeyItem(R.mipmap.icon_ir_increase, R.string.ir_volume_up, 43);
                case 44:
                    return new IrKeyItem(R.mipmap.icon_ir_reduce, R.string.ir_volume_down, 44);
                default:
                    throw new RuntimeException("no key type");
            }
        }
        return new IrKeyItem(R.mipmap.icon_ir_appointment, R.string.ir_appointment, 1299);
    }

    public static Object[][] getFanLibKey() {
        return new Object[][]{new Object[]{"开", 1}, new Object[]{"关", 1}, new Object[]{"摆风", Integer.valueOf(ID_SWING)}, new Object[]{"风速", Integer.valueOf(ID_FAN_SPEED)}};
    }

    public static Object[][] getAirCleanerLibKey() {
        return new Object[][]{new Object[]{"开", 1}, new Object[]{"关", 1}};
    }

    public static Object[][] getWaterHeaterLibKey() {
        return new Object[][]{new Object[]{"开", 1}, new Object[]{"关", 1}};
    }

    public static Object[][] getTvLibKey() {
        return new Object[][]{new Object[]{"开", 1}, new Object[]{"关", 1}, new Object[]{"调大声音", 50}, new Object[]{"静音", 106}, new Object[]{"调小声音", 51}, new Object[]{"0", 56}, new Object[]{"1", 61}, new Object[]{"2", 66}, new Object[]{"3", 71}, new Object[]{"4", 76}, new Object[]{"5", 81}, new Object[]{"6", 86}, new Object[]{"7", 91}, new Object[]{"8", 96}, new Object[]{"9", 101}};
    }
}