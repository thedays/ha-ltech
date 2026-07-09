package com.ltech.smarthome.ui.device.curtain_motor;

import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.device.ir.MotorKeyItem;
import com.smart.product_agreement.bean.CurtainMotorState;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CurtainRepository {
    public static final String BLE_MODE_GLOW = "BLE_MODE_GLOW";
    public static final String BLE_MODE_RECEIVE_VISITOR = "BLE_MODE_RECEIVE_VISITOR";
    public static final String BLE_MODE_SLEEP = "BLE_MODE_SLEEP";
    public static final String BLE_MODE_WAKE_UP = "BLE_MODE_WAKE_UP";
    public static final String BLE_MOTOR_KEY_NAME_DOWN = "BLE_MOTOR_KEY_NAME_DOWN";
    public static final String BLE_MOTOR_KEY_NAME_MODE = "BLE_MOTOR_KEY_NAME_MODE";
    public static final String BLE_MOTOR_KEY_NAME_OFF_STOP = "BLE_MOTOR_KEY_NAME_OFF_CLOSE";
    public static final String BLE_MOTOR_KEY_NAME_OPEN_STOP = "BLE_MOTOR_KEY_NAME_OPEN_CLOSE";
    public static final String BLE_MOTOR_KEY_NAME_STOP = "BLE_MOTOR_KEY_NAME_STOP";
    public static final String BLE_MOTOR_KEY_NAME_UP = "BLE_MOTOR_KEY_NAME_UP";
    public static final String BLE_MOTOR_KEY_NAME_UP_DOWN = "BLE_MOTOR_KEY_NAME_UP_DOWN";
    public static final String BLE_MOTOR_KEY_NAME_UP_STOP_DOWN_STOP = "BLE_MOTOR_KEY_NAME_UP_STOP_DOWN_STOP";
    public static final int DEFAULT_MOTOR_DIRECTION = 0;
    public static final int DEFAULT_RUN_STATE = 0;
    public static final int DEFAULT_TRAVEL_PERCENT = 0;
    public static final int KEY_DOWN = 4;
    public static final int KEY_POINT_DOWN = 5;
    public static final int KEY_POINT_RUN_MODE = 7;
    public static final int KEY_POINT_RUN_PERCENT = 6;
    public static final int KEY_POINT_UP = 2;
    public static final int KEY_STOP = 3;
    public static final int KEY_UP = 1;
    public static final int MODE_GLOW = 3;
    public static final int MODE_GLOW_INDEX = 8;
    public static final int MODE_GLOW_POSITION = 90;
    public static final int MODE_RECEIVE_VISITOR = 4;
    public static final int MODE_RECEIVE_VISITOR_INDEX = 16;
    public static final int MODE_RECEIVE_VISITOR_POSITION = 0;
    public static final int MODE_SLEEP = 2;
    public static final int MODE_SLEEP_INDEX = 3;
    public static final int MODE_SLEEP_POSITION = 100;
    public static final int MODE_WAKE_UP = 1;
    public static final int MODE_WAKE_UP_INDEX = 2;
    public static final int MODE_WAKE_UP_POSITION = 50;
    public static final int[] defaultModeIconNum = {2, 3, 8, 16};
    public static final int[] defaultModePercent = {50, 100, 90, 0};
    public static List<MotorKeyItem> modeList = new ArrayList();
    public static List<MotorKeyItem> DEFAULT_MODE_LIST = new ArrayList<MotorKeyItem>() { // from class: com.ltech.smarthome.ui.device.curtain_motor.CurtainRepository.1
        {
            add(CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_WAKE_UP));
            add(CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_SLEEP));
            add(CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_GLOW));
            add(CurtainRepository.getMotorKeyModeItem(CurtainRepository.BLE_MODE_RECEIVE_VISITOR));
        }
    };

    public static MotorKeyItem getMotorKeyItem(String key) {
        key.hashCode();
        switch (key) {
            case "BLE_MOTOR_KEY_NAME_UP_STOP_DOWN_STOP":
                return new MotorKeyItem(R.mipmap.cgcur_icon_off, R.string.open_stop_close_stop_curtain, String.valueOf(4));
            case "BLE_MOTOR_KEY_NAME_OFF_CLOSE":
                return new MotorKeyItem(R.mipmap.cgcur_icon_off, R.string.close_stop, String.valueOf(4));
            case "BLE_MOTOR_KEY_NAME_UP":
                return new MotorKeyItem(R.mipmap.cgcur_icon_on, R.string.open_curtain, String.valueOf(1));
            case "BLE_MOTOR_KEY_NAME_OPEN_CLOSE":
                return new MotorKeyItem(R.mipmap.cgcur_icon_off, R.string.open_stop, String.valueOf(4));
            case "BLE_MOTOR_KEY_NAME_DOWN":
                return new MotorKeyItem(R.mipmap.cgcur_icon_off, R.string.close_curtain, String.valueOf(4));
            case "BLE_MOTOR_KEY_NAME_STOP":
                return new MotorKeyItem(R.mipmap.cgcur_icon_stop, R.string.preview_stop, String.valueOf(3));
            case "BLE_MOTOR_KEY_NAME_UP_DOWN":
                return new MotorKeyItem(R.mipmap.cgcur_icon_off, R.string.open_close_curtain, String.valueOf(4));
            default:
                return null;
        }
    }

    public static MotorKeyItem getMotorKeyModeItem(String modeKey) {
        modeKey.hashCode();
        switch (modeKey) {
            case "BLE_MODE_GLOW":
                return new MotorKeyItem(R.string.app_str_curtain_mode_shimmer, String.valueOf(7), 8, String.valueOf(3));
            case "BLE_MODE_SLEEP":
                return new MotorKeyItem(R.string.app_str_curtain_mode_sleep, String.valueOf(7), 3, String.valueOf(2));
            case "BLE_MODE_RECEIVE_VISITOR":
                return new MotorKeyItem(R.string.app_str_curtain_mode_greeting, String.valueOf(7), 16, String.valueOf(4));
            case "BLE_MODE_WAKE_UP":
                return new MotorKeyItem(R.string.app_str_curtain_mode_getup, String.valueOf(7), 2, String.valueOf(1));
            default:
                return null;
        }
    }

    public static List<MotorKeyItem> getModeList() {
        return modeList;
    }

    public static void setModeList(List<MotorKeyItem> modeList2) {
        modeList = modeList2;
    }

    public static CurtainMotorState generateCurtainMotorState(int address) {
        CurtainMotorState curtainMotorState = new CurtainMotorState();
        curtainMotorState.setZoneNum(0);
        curtainMotorState.setAddress(address);
        curtainMotorState.setRunState(0);
        curtainMotorState.setTravelPercent(0);
        curtainMotorState.setMotorDirection(0);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            CurtainMotorState.ModeInfo modeInfo = new CurtainMotorState.ModeInfo();
            modeInfo.setModeIconNum(defaultModeIconNum[i]);
            modeInfo.setModePositionPercent(defaultModePercent[i]);
            arrayList.add(modeInfo);
        }
        curtainMotorState.setModeInfoList(arrayList);
        return curtainMotorState;
    }
}