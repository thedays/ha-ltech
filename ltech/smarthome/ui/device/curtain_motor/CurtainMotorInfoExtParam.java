package com.ltech.smarthome.ui.device.curtain_motor;

import android.util.ArrayMap;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.base.BaseExtParam;

/* loaded from: classes4.dex */
public class CurtainMotorInfoExtParam extends BaseExtParam {
    private static final String PREFIX = "modeName-";
    private ArrayMap<String, String> modeInfoMap = new ArrayMap<>();
    private int openType;

    public String getParamMapString() {
        JSONObject parseObject = JSONObject.parseObject(GsonUtils.getGson().toJson(this.modeInfoMap));
        putBaseFields(parseObject);
        parseObject.put("openType", (Object) Integer.valueOf(this.openType));
        return parseObject.toJSONString();
    }

    public void fillMapWithString(String infoString) {
        if (infoString == null) {
            this.openType = 0;
            return;
        }
        JSONObject parseObject = JSONObject.parseObject(infoString);
        for (String str : parseObject.keySet()) {
            if (str.contains(PREFIX)) {
                this.modeInfoMap.put(str, parseObject.getString(str));
            } else if (str.equals("openType")) {
                this.openType = parseObject.getIntValue(str);
            } else {
                getBaseFields(parseObject, str);
            }
        }
    }

    public String getModeInfo(int position) {
        return this.modeInfoMap.get(PREFIX + position);
    }

    public void setModeInfo(int position, String modeInfo) {
        this.modeInfoMap.put(PREFIX + position, modeInfo);
    }

    public ArrayMap<String, String> getModeInfoMap() {
        return this.modeInfoMap;
    }

    public void setModeInfoMap(ArrayMap<String, String> modeInfoMap) {
        this.modeInfoMap = modeInfoMap;
    }

    public int getOpenType() {
        return this.openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }
}