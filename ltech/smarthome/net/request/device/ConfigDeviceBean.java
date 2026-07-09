package com.ltech.smarthome.net.request.device;

import android.util.ArrayMap;
import com.aliyun.alink.h2.api.Constraint;
import com.blankj.utilcode.util.GsonUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.utils.Constants;

/* loaded from: classes4.dex */
public class ConfigDeviceBean {
    private String configJson;

    public String getConfigJson() {
        return this.configJson;
    }

    public static final class Builder {
        private ArrayMap<String, Object> paramMap = new ArrayMap<>();

        private Builder() {
        }

        public static Builder aBean() {
            return new Builder();
        }

        public Builder productid(String productid) {
            this.paramMap.put("productid", productid);
            return this;
        }

        public Builder userid(long userid) {
            this.paramMap.put(PushContentParamKey.USER_ID, Long.valueOf(userid));
            return this;
        }

        public Builder roomid(long roomid) {
            this.paramMap.put("roomid", Long.valueOf(roomid));
            return this;
        }

        public Builder floorid(long floorid) {
            this.paramMap.put("floorid", Long.valueOf(floorid));
            return this;
        }

        public Builder placeid(long placeid) {
            this.paramMap.put(PushContentParamKey.PLACE_ID, Long.valueOf(placeid));
            return this;
        }

        public Builder mac(String mac) {
            this.paramMap.put("mac", mac);
            return this;
        }

        public Builder devicename(String devicename) {
            this.paramMap.put(Constraint.AUTH_TYPE_DEVICE_NAME, devicename);
            return this;
        }

        public Builder macfalg(int macfalg) {
            this.paramMap.put("macfalg", Integer.valueOf(macfalg));
            return this;
        }

        public Builder devicesn(String devicesn) {
            this.paramMap.put("devicesn", devicesn);
            return this;
        }

        public Builder platformdeviceid(String platformdeviceid) {
            this.paramMap.put("platformdeviceid", platformdeviceid);
            return this;
        }

        public Builder maccode(String maccode) {
            this.paramMap.put("maccode", maccode);
            return this;
        }

        public Builder location(String location) {
            this.paramMap.put("location", location);
            return this;
        }

        public Builder latitude(double latitude) {
            this.paramMap.put(Constants.LATITUDE, Double.valueOf(latitude));
            return this;
        }

        public Builder longitude(double longitude) {
            this.paramMap.put(Constants.LONGITUDE, Double.valueOf(longitude));
            return this;
        }

        public Builder macdeviceid(long macdeviceid) {
            this.paramMap.put("macdeviceid", Long.valueOf(macdeviceid));
            return this;
        }

        public Builder param(Object param) {
            this.paramMap.put(RemoteMessageConst.MessageBody.PARAM, param);
            return this;
        }

        public Builder voiceControlType(int type) {
            this.paramMap.put("voiceControlType", Integer.valueOf(type));
            return this;
        }

        public Builder aiPuductType(String aiPuductType) {
            this.paramMap.put("aipuducttype", aiPuductType);
            return this;
        }

        public Builder codeLibrary(String codeLibrary) {
            this.paramMap.put("CodeLibrary", codeLibrary);
            return this;
        }

        public Builder subProductTypeName(String subProductTypeName) {
            this.paramMap.put("subproducttypename", subProductTypeName);
            return this;
        }

        public Builder btmodule(String btmodule) {
            ArrayMap<String, Object> arrayMap = this.paramMap;
            if (btmodule == null) {
                btmodule = "";
            }
            arrayMap.put("btmodule", btmodule);
            return this;
        }

        public Builder subManufacturerName(String subManufacturerName) {
            this.paramMap.put("submanufacturername", subManufacturerName);
            return this;
        }

        public Builder subProductName(String subProductName) {
            this.paramMap.put("subproductname", subProductName);
            return this;
        }

        public ConfigDeviceBean build() {
            ConfigDeviceBean configDeviceBean = new ConfigDeviceBean();
            configDeviceBean.configJson = GsonUtils.getGson().toJson(this.paramMap);
            return configDeviceBean;
        }

        public Builder mid(String mid) {
            this.paramMap.put("mid", mid);
            return this;
        }

        public Builder paramext(String paramext) {
            this.paramMap.put("paramext", paramext);
            return this;
        }

        public Builder yingshitype(long yingshitype) {
            this.paramMap.put("yingshitype", Long.valueOf(yingshitype));
            return this;
        }

        public Builder validateCode(String validateCode) {
            this.paramMap.put("validateCode", validateCode);
            return this;
        }

        public Builder imageIndex(int imageIndex) {
            this.paramMap.put("imgindex", Integer.valueOf(imageIndex));
            return this;
        }

        public Builder virtual(int virtual) {
            this.paramMap.put("virtual", Integer.valueOf(virtual));
            return this;
        }

        public Builder writable(int writable) {
            this.paramMap.put("writable", Integer.valueOf(writable));
            return this;
        }
    }
}