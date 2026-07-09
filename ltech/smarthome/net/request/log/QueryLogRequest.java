package com.ltech.smarthome.net.request.log;

import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.push.PushContentParamKey;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class QueryLogRequest {
    private HashMap<String, Object> paramMap = new HashMap<>();

    public String getRequestString() {
        return GsonUtils.toJson(this.paramMap);
    }

    public void setDeviceId(long deviceId) {
        this.paramMap.put(PushContentParamKey.DEVICE_ID, Long.valueOf(deviceId));
    }

    public void setBeginTime(String beginTime) {
        this.paramMap.put("begincreatetime", beginTime);
    }

    public void setEndTime(String endTime) {
        this.paramMap.put("endcreatetime", endTime);
    }

    public void setPageSize(int pageSize) {
        this.paramMap.put("pagesize", Integer.valueOf(pageSize));
    }

    public void setPageNum(int pageNum) {
        this.paramMap.put("pagenum", Integer.valueOf(pageNum));
    }

    public void setPropertyCode(String propertycode) {
        this.paramMap.put("propertycode", propertycode);
    }

    public void setCodeList(String[] codeList) {
        this.paramMap.put("codelist", codeList);
    }

    public void setDateMonth(String dateMonth) {
        this.paramMap.put("datemonth", dateMonth);
    }
}