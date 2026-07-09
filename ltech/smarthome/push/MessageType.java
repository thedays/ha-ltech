package com.ltech.smarthome.push;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes4.dex */
public @interface MessageType {
    public static final int AUTO_AVL_STATUS_CHANGED = 7;
    public static final int AUTO_EX_SEND_TO_MOBILE = 6;
    public static final int DELETE_FAMILY = 15;
    public static final int DELETE_FAMILY_MEMBER = 14;
    public static final int DEVICE_MCU_UPGRADE_PUSH = 30;
    public static final int DEVICE_STATUS_CHANGED = 2;
    public static final int DEVICE_STATUS_CHANGE_PUSH = 29;
    public static final int FAMILY_AUTO_CHANGED = 19;
    public static final int FAMILY_DEVICE_CHANGED = 4;
    public static final int FAMILY_FLOOR_ROOM_CHANGED = 3;
    public static final int FAMILY_GROUP_CHANGED = 17;
    public static final int FAMILY_SCENE_CHANGED = 18;
    public static final int INTERCOM_MESSAGE_PULL = 23;
    public static final int MANAGER_AGREE_JOIN = 10;
    public static final int MANAGER_DISAGREE_JOIN = 11;
    public static final int MANAGER_TO_MEMBER = 12;
    public static final int MEMBER_TO_MANGER = 13;
    public static final int MSG_BROADCAST = 5;
    public static final int RECEIVE_FAMILY_INVITATION = 8;
    public static final int RECEIVE_MEMBER_JOIN = 9;
    public static final int SECURITY_PULL = 24;
    public static final int SONOS_PUSH = 27;
    public static final int SUPER_PANEL_REPLACE_SUCCESS = 28;
    public static final int TRANSFORM_FAMILY = 16;
    public static final int USER_LOGIN_ANOTHER_DEVICE = 1;
    public static final int VOICE_CALL_MESSAGE_PULL = 20;
    public static final int VOICE_CALL_MESSAGE_STATUS_PULL = 21;
}