package com.ltech.smarthome.net.request.device;

import com.aliyun.alink.h2.api.Constraint;
import com.blankj.utilcode.util.GsonUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ltech.smarthome.model.bean.KValue;
import com.ltech.smarthome.push.PushContentParamKey;
import com.ltech.smarthome.utils.Constants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class UpdateDeviceRequest {
    private HashMap<String, Object> paramMap = new HashMap<>();

    public HashMap<String, Object> getParamMap() {
        return this.paramMap;
    }

    public String getRequestString() {
        return GsonUtils.toJson(this.paramMap);
    }

    public void setSubOutGroup(int b2) {
        this.paramMap.put("isSubOutGroup", Integer.valueOf(b2));
    }

    public void setDeviceId(long deviceId) {
        this.paramMap.put(PushContentParamKey.DEVICE_ID, Long.valueOf(deviceId));
    }

    public void setItem(String s) {
        this.paramMap.put("item", s);
    }

    public void setDeviceName(String deviceName) {
        this.paramMap.put(Constraint.AUTH_TYPE_DEVICE_NAME, deviceName);
    }

    public void setImageIndex(int imageIndex) {
        this.paramMap.put("imgindex", Integer.valueOf(imageIndex));
    }

    public void setParam(String param) {
        this.paramMap.put(RemoteMessageConst.MessageBody.PARAM, param);
    }

    public void setParamExt(String paramExt) {
        if (paramExt == null || paramExt.isEmpty()) {
            paramExt = "{}";
        }
        this.paramMap.put("paramext", paramExt);
    }

    public void setPlatformDeviceId(String platformDeviceId) {
        this.paramMap.put("platformdeviceid", platformDeviceId);
    }

    public void setRoomId(long roomId) {
        this.paramMap.put("roomid", Long.valueOf(roomId));
    }

    public void setCodeLibrary(String codeLibrary) {
        this.paramMap.put("CodeLibrary", codeLibrary);
    }

    public void firmwareVersion(String firmwareversion) {
        this.paramMap.put("firmwareversion", firmwareversion);
    }

    public void setMcuVersion(String mcuversion) {
        this.paramMap.put("mcuversion", mcuversion);
    }

    public void setFirmwareVersion(String firewareversion) {
        this.paramMap.put("firmwareversion", firewareversion);
    }

    public void setBleVersion(String bleversion) {
        this.paramMap.put("bleversion", bleversion);
    }

    public void setUpdateSub(boolean b2) {
        this.paramMap.put("updatesub", Boolean.valueOf(b2));
    }

    public void setMac(String mac) {
        this.paramMap.put("mac", mac);
    }

    public void setPanelColor(String panelColor) {
        this.paramMap.put("panelColor", panelColor);
    }

    public void setCtRange(int max, int min) {
        if (min < 1000 || min > 10000) {
            min = 1000;
        }
        if (max < min || max > 10000) {
            max = 10000;
        }
        this.paramMap.put("minkelvin", Integer.valueOf(min));
        this.paramMap.put("maxkelvin", Integer.valueOf(max));
    }

    public void setLatitude(double latitude) {
        this.paramMap.put(Constants.LATITUDE, Double.valueOf(latitude));
    }

    public void setLongitude(double longitude) {
        this.paramMap.put(Constants.LONGITUDE, Double.valueOf(longitude));
    }

    public void setReplace(boolean z) {
        this.paramMap.put("isreplace", Integer.valueOf(z ? 1 : 0));
    }

    public void setSubHide(boolean z) {
        this.paramMap.put("subhide", Integer.valueOf(!z ? 1 : 0));
    }

    public void setVirtual(int virtual) {
        this.paramMap.put("virtual", Integer.valueOf(virtual));
    }

    public void setWritable(int writable) {
        this.paramMap.put("writable", Integer.valueOf(writable));
    }

    public void setPresetKValues(Map<String, KValue> kValues) {
        this.paramMap.put("presetKValues", kValues);
    }

    public void setReportInstruct(String reportInstruct) {
        this.paramMap.put(PushContentParamKey.REPORT_INSTRUCT, reportInstruct);
    }

    public void setPanelUpdate(boolean panelUpdate) {
        this.paramMap.put("isPanelUpdate", false);
    }
}