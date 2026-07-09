package com.ltech.smarthome.net.request.group;

import com.blankj.utilcode.util.GsonUtils;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class UpdateGroupDataRequest {
    private HashMap<String, Object> paramMap = new HashMap<>();

    public String getRequestString() {
        return GsonUtils.toJson(this.paramMap);
    }

    public void setGroupId(long deviceId) {
        this.paramMap.put("groupid", Long.valueOf(deviceId));
    }

    public void setSubHide(boolean z) {
        this.paramMap.put("subhide", Integer.valueOf(!z ? 1 : 0));
    }
}