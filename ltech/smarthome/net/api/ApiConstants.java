package com.ltech.smarthome.net.api;

import android.text.TextUtils;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;

/* loaded from: classes4.dex */
public class ApiConstants {
    public static String FUN_URL_BASE = "ysnetwork.base.com";
    public static final int MANAGER = 2;
    public static final int MEMBER = 3;
    public static final int OWNER = 1;
    public static final String REST_URL = "openapi/rest";
    public static String SERVER_URL = "https://apic.ltsys.com.cn:2443/";
    public static String TEST_SERVER_URL = "https://ltsit.ltechcloud.cn/";
    public static String TEST_WIFI_GATEWAY_URL = "http://trytest.granwin.com:8088/ysnetworkweb";
    public static String WIFI_GATEWAY_URL = "http://apic.ltsys.com.cn/ysnetworkweb";

    public static String getAppDomain() {
        String queryValue = SharedPreferenceUtil.queryValue(Constants.APP_DOMAIN_KEY);
        SERVER_URL = queryValue;
        if (TextUtils.isEmpty(queryValue)) {
            SERVER_URL = "https://apic.ltsys.com.cn:2443/";
        }
        return SERVER_URL;
    }

    public static boolean isChinaNode() {
        return SERVER_URL.contains("https://apic.ltsys.com.cn:2443/") || SERVER_URL.contains("https://ltsit.ltechcloud.cn/");
    }

    public static boolean isTestNode() {
        return SERVER_URL.contains("https://ltsit.ltechcloud.cn/");
    }

    public static String getWifiGatewayUrl() {
        String queryValue = SharedPreferenceUtil.queryValue(Constants.WEB_DOMAIN_KEY);
        if (!TextUtils.isEmpty(queryValue)) {
            WIFI_GATEWAY_URL = queryValue;
        }
        return WIFI_GATEWAY_URL + "/device/get.do";
    }

    public static String getDcaManufactureSecret() {
        if (isChinaNode()) {
            return isRelease() ? Constants.DCA_MANUFACTURE_SECRET : Constants.DCA_MANUFACTURE_SECRET_TEST;
        }
        return Constants.DCA_MANUFACTURE_SECRET_E;
    }

    public static String getDcaClientId() {
        if (isChinaNode()) {
            return isRelease() ? Constants.DCA_CLIENT_ID : Constants.DCA_CLIENT_ID_TEST;
        }
        return Constants.DCA_CLIENT_ID_E;
    }

    public static String getDcaSkillId() {
        if (isChinaNode()) {
            return isRelease() ? Constants.DCA_SMART_HOME_SKILL_ID : Constants.DCA_SMART_HOME_SKILL_ID_TEST;
        }
        return Constants.DCA_SMART_HOME_SKILL_ID_E;
    }

    public static String getDcaApiKey() {
        return isChinaNode() ? isRelease() ? Constants.DCA_API_KEY : Constants.DCA_API_KEY_TEST : Constants.DCA_API_KEY_E;
    }

    public static String getDcaApiSecret() {
        if (isChinaNode()) {
            return isRelease() ? Constants.DCA_API_SECRET : Constants.DCA_API_SECRET_TEST;
        }
        return Constants.DCA_API_SECRET_E;
    }

    public static boolean isRelease() {
        return SERVER_URL.equals("https://apic.ltsys.com.cn:2443/");
    }
}