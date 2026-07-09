package com.ltech.smarthome.model.type;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes4.dex */
public @interface SensorTrigType {
    public static final int BLE_DEVICE = 3;
    public static final int DALI = 2;
    public static final int LOCAL_SCENE = 4;
    public static final int NO_ACTION = 255;
    public static final int SWITCH_ON_OFF = 0;
    public static final int V010 = 1;
}