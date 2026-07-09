package com.ltech.smarthome.net.request.group;

import com.aliyun.alink.h2.api.Constraint;
import com.blankj.utilcode.util.GsonUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.ltech.smarthome.model.bean.KValue;
import com.ltech.smarthome.net.request.group.UpdateGroupRequest;
import com.ltech.smarthome.ui.scene.SceneHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class UpdateGroupParamRequest {
    private HashMap<String, Object> paramMap = new HashMap<>();

    public String getRequestString() {
        return GsonUtils.toJson(this.paramMap);
    }

    public void setGroupId(long groupId) {
        this.paramMap.put("groupid", Long.valueOf(groupId));
    }

    public void setGroupDeviceIndexs(long[] ids) {
        this.paramMap.put("indexs", ids);
    }

    public void setGroupName(String groupName) {
        this.paramMap.put(Constraint.AUTH_TYPE_DEVICE_NAME, groupName);
    }

    public void setParam(String param) {
        this.paramMap.put(RemoteMessageConst.MessageBody.PARAM, param);
    }

    public void setParamExt(String paramExt) {
        this.paramMap.put("paramext", paramExt);
    }

    public void setDevices(List<UpdateGroupRequest.UpdateGroupContent> devices) {
        this.paramMap.put("devices", devices);
    }

    public void setPresetKValues(Map<String, KValue> kValues) {
        this.paramMap.put("presetKValues", kValues);
    }

    public void setColorPaletteType(int type) {
        this.paramMap.put("colorPaletteType", Integer.valueOf(type));
    }

    public void setColorPaletteUrl(String url) {
        this.paramMap.put("colorPaletteUrl", url);
    }

    public void setGsId(long id) {
        this.paramMap.put(SceneHelper.GS_Id, Long.valueOf(id));
    }

    public void setGsName(String s) {
        this.paramMap.put(SceneHelper.GS_NAME, s);
    }

    public void setLeaderSup(int v) {
        this.paramMap.put("leadersup", Integer.valueOf(v));
    }
}