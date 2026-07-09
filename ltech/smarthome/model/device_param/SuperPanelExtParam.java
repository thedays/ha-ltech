package com.ltech.smarthome.model.device_param;

import android.text.TextUtils;
import android.util.ArrayMap;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.device_param.Rs485ExtParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SuperPanelExtParam extends Rs485ExtParam {
    private static final String PREFIX = "zone";
    private int switchNightMode;
    private int switchWordMode;
    private int zoneNumber;
    private int switchNightModeStartH = 22;
    private int switchNightModeStartM = 0;
    private int switchNightModeEndH = 8;
    private int switchNightModeEndM = 0;
    private int continous_mode = 0;
    private String switch1_name = "";
    private String switch2_name = "";
    private String switch3_name = "";
    private String switch4_name = "";
    private int switch_memory = 1;
    private int quick_cmd = 1;
    private int near_wake_up = 0;
    private int apkAutoUpdate = 0;
    private ArrayMap<String, RelatedInfoExtParam.RelateInfo> relateParamMap = new ArrayMap<>();

    public String getParamString() {
        JSONObject parseObject = JSONObject.parseObject(GsonUtils.getGson().toJson(this.relateParamMap));
        putBaseFields(parseObject);
        parseObject.put("zoneNumber", (Object) Integer.valueOf(this.zoneNumber));
        parseObject.put("switchNightMode", (Object) Integer.valueOf(this.switchNightMode));
        parseObject.put("switchWordMode", (Object) Integer.valueOf(this.switchWordMode));
        parseObject.put("switchNightModeStartH", (Object) Integer.valueOf(this.switchNightModeStartH));
        parseObject.put("switchNightModeStartM", (Object) Integer.valueOf(this.switchNightModeStartM));
        parseObject.put("switchNightModeEndH", (Object) Integer.valueOf(this.switchNightModeEndH));
        parseObject.put("switchNightModeEndM", (Object) Integer.valueOf(this.switchNightModeEndM));
        parseObject.put("continous_mode", (Object) Integer.valueOf(this.continous_mode));
        parseObject.put("switch1_name", (Object) this.switch1_name);
        parseObject.put("switch2_name", (Object) this.switch2_name);
        parseObject.put("switch3_name", (Object) this.switch3_name);
        parseObject.put("switch4_name", (Object) this.switch4_name);
        parseObject.put("switch_memory", (Object) Integer.valueOf(this.switch_memory));
        parseObject.put("quick_cmd", (Object) Integer.valueOf(this.quick_cmd));
        parseObject.put("near_wake_up", (Object) Integer.valueOf(this.near_wake_up));
        parseObject.put("apkAutoUpdate", (Object) Integer.valueOf(this.apkAutoUpdate));
        if (this.toBleList == null) {
            this.toBleList = new ArrayList();
        }
        parseObject.put("categories_ToBle", (Object) JSONArray.parseArray(GsonUtils.toJson(this.toBleList)));
        if (this.to485List == null) {
            this.to485List = new ArrayList();
        }
        parseObject.put("categories_To485", (Object) JSONArray.parseArray(GsonUtils.toJson(this.to485List)));
        return parseObject.toJSONString();
    }

    public SuperPanelExtParam() {
        setSwitch1_name(ActivityUtils.getTopActivity().getString(R.string.app_str_smart_panel_switch1));
        setSwitch2_name(ActivityUtils.getTopActivity().getString(R.string.app_str_smart_panel_switch2));
        setSwitch3_name(ActivityUtils.getTopActivity().getString(R.string.app_str_smart_panel_switch3));
        setSwitch4_name(ActivityUtils.getTopActivity().getString(R.string.app_str_smart_panel_switch4));
    }

    public void fillMapWithString(String infoString) {
        if (this.relateParamMap == null) {
            this.relateParamMap = new ArrayMap<>();
        }
        if (infoString != null) {
            JSONObject parseObject = JSONObject.parseObject(infoString);
            for (String str : parseObject.keySet()) {
                if (str.equals("zone1") || str.equals("zone2") || str.equals("zone3") || str.equals("zone4") || str.equals("zone5") || str.equals("zone6") || str.equals("zone7") || str.equals("zone8")) {
                    this.relateParamMap.put(str, (RelatedInfoExtParam.RelateInfo) GsonUtils.getGson().fromJson(parseObject.getString(str), RelatedInfoExtParam.RelateInfo.class));
                } else if (str.equals("zoneNumber")) {
                    this.zoneNumber = parseObject.getIntValue(str);
                } else if (str.equals("switchNightMode")) {
                    this.switchNightMode = parseObject.getIntValue(str);
                } else if (str.equals("switchWordMode")) {
                    this.switchWordMode = parseObject.getIntValue(str);
                } else if (str.equals("switchNightModeStartH")) {
                    this.switchNightModeStartH = parseObject.getIntValue(str);
                } else if (str.equals("switchNightModeStartM")) {
                    this.switchNightModeStartM = parseObject.getIntValue(str);
                } else if (str.equals("switchNightModeEndH")) {
                    this.switchNightModeEndH = parseObject.getIntValue(str);
                } else if (str.equals("switchNightModeEndM")) {
                    this.switchNightModeEndM = parseObject.getIntValue(str);
                } else if (str.equals("continous_mode")) {
                    this.continous_mode = parseObject.getIntValue(str);
                } else if (str.equals("switch1_name")) {
                    this.switch1_name = parseObject.getString(str);
                } else if (str.equals("switch2_name")) {
                    this.switch2_name = parseObject.getString(str);
                } else if (str.equals("switch3_name")) {
                    this.switch3_name = parseObject.getString(str);
                } else if (str.equals("switch4_name")) {
                    this.switch4_name = parseObject.getString(str);
                } else if (str.equals("switch_memory")) {
                    this.switch_memory = parseObject.getIntValue(str);
                } else if (str.equals("quick_cmd")) {
                    this.quick_cmd = parseObject.getIntValue(str);
                } else if (str.equals("apkAutoUpdate")) {
                    this.apkAutoUpdate = parseObject.getIntValue(str);
                } else if (str.equals("near_wake_up")) {
                    this.near_wake_up = parseObject.getIntValue(str);
                } else if (str.equals("categories_ToBle")) {
                    JSONArray jSONArray = parseObject.getJSONArray(str);
                    if (jSONArray == null) {
                        jSONArray = new JSONArray();
                    }
                    this.toBleList = (List) GsonUtils.fromJson(jSONArray.toString(), new TypeToken<List<Rs485ExtParam.Category>>(this) { // from class: com.ltech.smarthome.model.device_param.SuperPanelExtParam.1
                    }.getType());
                } else if (str.equals("categories_To485")) {
                    JSONArray jSONArray2 = parseObject.getJSONArray(str);
                    if (jSONArray2 == null) {
                        jSONArray2 = new JSONArray();
                    }
                    this.to485List = (List) GsonUtils.fromJson(jSONArray2.toString(), new TypeToken<List<Rs485ExtParam.Category>>(this) { // from class: com.ltech.smarthome.model.device_param.SuperPanelExtParam.2
                    }.getType());
                } else {
                    getBaseFields(parseObject, str);
                }
            }
        }
    }

    public RelatedInfoExtParam.RelateInfo getRelateInfoMap(int position) {
        if (this.relateParamMap.get(PREFIX + position) == null) {
            this.relateParamMap.put(PREFIX + position, new RelatedInfoExtParam.RelateInfo());
        }
        return this.relateParamMap.get(PREFIX + position);
    }

    public String[] getKeyNameArray(String[] keyName) {
        if (!TextUtils.isEmpty(this.switch1_name)) {
            keyName[0] = this.switch1_name;
        }
        if (!TextUtils.isEmpty(this.switch2_name)) {
            keyName[1] = this.switch2_name;
        }
        if (keyName.length == 4) {
            if (!TextUtils.isEmpty(this.switch3_name)) {
                keyName[2] = this.switch3_name;
            }
            if (!TextUtils.isEmpty(this.switch4_name)) {
                keyName[3] = this.switch4_name;
            }
        }
        return keyName;
    }

    public int getApkAutoUpdate() {
        return this.apkAutoUpdate;
    }

    public void setApkAutoUpdate(int apkAutoUpdate) {
        this.apkAutoUpdate = apkAutoUpdate;
    }

    public int getZoneNumber() {
        return this.zoneNumber;
    }

    public void setZoneNumber(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public void setRelateInfo(int position, RelatedInfoExtParam.RelateInfo relateInfo) {
        this.relateParamMap.put(PREFIX + position, relateInfo);
    }

    public int getSwitchNightMode() {
        return this.switchNightMode;
    }

    public void setSwitchNightMode(int switchNightMode) {
        this.switchNightMode = switchNightMode;
    }

    public int getSwitchWordMode() {
        return this.switchWordMode;
    }

    public void setSwitchWordMode(int switchWordMode) {
        this.switchWordMode = switchWordMode;
    }

    public int getSwitchNightModeStartH() {
        return this.switchNightModeStartH;
    }

    public void setSwitchNightModeStartH(int switchNightModeStartH) {
        this.switchNightModeStartH = switchNightModeStartH;
    }

    public int getSwitchNightModeStartM() {
        return this.switchNightModeStartM;
    }

    public void setSwitchNightModeStartM(int switchNightModeStartM) {
        this.switchNightModeStartM = switchNightModeStartM;
    }

    public int getSwitchNightModeEndH() {
        return this.switchNightModeEndH;
    }

    public void setSwitchNightModeEndH(int switchNightModeEndH) {
        this.switchNightModeEndH = switchNightModeEndH;
    }

    public int getSwitchNightModeEndM() {
        return this.switchNightModeEndM;
    }

    public void setSwitchNightModeEndM(int switchNightModeEndM) {
        this.switchNightModeEndM = switchNightModeEndM;
    }

    public int getContinous_mode() {
        return this.continous_mode;
    }

    public void setContinous_mode(int continous_mode) {
        this.continous_mode = continous_mode;
    }

    public String getSwitch1_name() {
        return this.switch1_name;
    }

    public void setSwitch1_name(String switch1_name) {
        this.switch1_name = switch1_name;
    }

    public String getSwitch2_name() {
        return this.switch2_name;
    }

    public void setSwitch2_name(String switch2_name) {
        this.switch2_name = switch2_name;
    }

    public String getSwitch3_name() {
        return this.switch3_name;
    }

    public void setSwitch3_name(String switch3_name) {
        this.switch3_name = switch3_name;
    }

    public String getSwitch4_name() {
        return this.switch4_name;
    }

    public void setSwitch4_name(String switch4_name) {
        this.switch4_name = switch4_name;
    }

    public int getSwitch_memory() {
        return this.switch_memory;
    }

    public void setSwitch_memory(int switch_memory) {
        this.switch_memory = switch_memory;
    }

    public int getQuick_cmd() {
        return this.quick_cmd;
    }

    public void setQuick_cmd(int quick_cmd) {
        this.quick_cmd = quick_cmd;
    }

    public int getNear_wake_up() {
        return this.near_wake_up;
    }

    public void setNear_wake_up(int near_wake_up) {
        this.near_wake_up = near_wake_up;
    }
}