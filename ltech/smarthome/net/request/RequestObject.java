package com.ltech.smarthome.net.request;

import android.os.Build;
import android.text.TextUtils;
import com.blankj.utilcode.util.AppUtils;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.masget.api.security.AesEncryption;
import com.masget.api.security.MD5Util;
import com.smart.message.utils.LHomeLog;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class RequestObject {
    private long appid;
    private String data;
    private String method;
    private String platform_version;
    private String session;
    private String sign;
    private String system_model;
    private String target_appid;
    private String timestamp;
    private String format = "json";
    private String v = "2.0";

    public RequestObject(String method, long appId, String secretKey, String session, String temp) {
        this.data = "";
        LHomeLog.e(Constants.HTTP_LOG, getClass(), method + "json = " + temp);
        this.appid = appId;
        this.method = method;
        this.session = session;
        try {
            this.timestamp = String.valueOf(System.currentTimeMillis());
            if (!TextUtils.isEmpty(temp)) {
                this.data = AesEncryption.Encrypt(temp, secretKey, secretKey);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(secretKey);
            sb.append(this.appid);
            sb.append(this.data);
            sb.append(this.format);
            sb.append(method);
            sb.append(session);
            if (!TextUtils.isEmpty(this.target_appid)) {
                sb.append(this.target_appid);
            }
            sb.append(this.timestamp);
            sb.append(this.v);
            sb.append(secretKey);
            this.sign = MD5Util.string2MD5(sb.toString());
            this.platform_version = "Android_" + AppUtils.getAppVersionName();
            this.system_model = Build.VERSION.SDK_INT + ProductId.SPLIT + Build.MODEL;
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}