package com.ltech.smarthome.model.extra;

/* loaded from: classes4.dex */
public enum MaskType {
    SCENE("场景", 1),
    AUTOMATION("自动化", 2),
    APP_NOTICE("app通知", 3),
    DEVICE("设备", 4),
    GROUP("灯组", 5),
    DEVICE_GROUP("设备和组", 6),
    LOCAL("本地", 7),
    VOICE_CALL("语音对讲", 8),
    SONOS("Sonos音箱", 9);

    private final String typeName;
    private final int value;

    MaskType(String typeName, int value) {
        this.typeName = typeName;
        this.value = value;
    }

    public String typeName() {
        return this.typeName;
    }

    public int value() {
        return this.value;
    }

    public static boolean isDeviceAction(int type) {
        return type == DEVICE.value();
    }

    public static boolean isGroupAction(int type) {
        return type == GROUP.value();
    }

    public static boolean isSceneAction(int type) {
        return type == SCENE.value();
    }

    public static boolean isSonosAction(int type) {
        return type == SONOS.value();
    }

    public static boolean isVoiceCallAction(int type) {
        return type == VOICE_CALL.value();
    }

    public static boolean isAutomationAction(int type) {
        return type == AUTOMATION.value();
    }

    public static boolean isLocalAction(int type) {
        return type == LOCAL.value();
    }

    public static boolean isAppNoticeAction(int type) {
        return type == APP_NOTICE.value();
    }
}