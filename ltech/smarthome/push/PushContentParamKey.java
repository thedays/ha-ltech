package com.ltech.smarthome.push;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes4.dex */
public @interface PushContentParamKey {
    public static final String ACTION = "action";
    public static final String BODY = "body";
    public static final String DEVICES = "devices";
    public static final String DEVICE_ID = "deviceid";
    public static final String MESSAGE_ID = "messageid";
    public static final String MESSAGE_TYPE = "messagetype";
    public static final String PANEL_ID = "panelid";
    public static final String PLACE_ID = "placeid";
    public static final String REPORT_INSTRUCT = "reportinstruct";
    public static final String ROLE_TYPE = "roletype";
    public static final String ROLE_TYPE_NAME = "roletypename";
    public static final String SECURITY_TYPE = "securityType";
    public static final String STATE = "state";
    public static final String SUCCESSFUL_DEVICES = "successfulDevices";
    public static final String SUPER_PANEL_REPLACE_TYPE = "replaceStatus";
    public static final String TOTAL_NUM = "totalNum";
    public static final String USER_ID = "userid";
    public static final String VOICE_CALL_GROUP = "panelvoicegroup";
    public static final String VOICE_CALL_GROUP_ID = "panelvoicegroupid";
    public static final String VOICE_CALL_STATUS_TYPE = "statusType";
}