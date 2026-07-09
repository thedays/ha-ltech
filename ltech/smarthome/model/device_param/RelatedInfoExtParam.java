package com.ltech.smarthome.model.device_param;

import android.util.ArrayMap;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.base.BaseExtParam;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.push.PushContentParamKey;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class RelatedInfoExtParam extends BaseExtParam {
    private static final String PREFIX = "zone";
    private static final String SCENE_PREFIX = "scene";
    private int colorType;
    private int isSeparated;
    private long panelId;
    private ArrayMap<String, CurtainRelateInfo> relateInfoArrayMap;
    private int reportTime;
    private String sceneDimmerBrt;
    private int sensitive;
    private int switchPowerMemory;
    private int switchScreenBigIcon;
    private int switchScreenLanguage;
    private int switchShowType;
    private int switchSort0;
    private int switchSort1;
    private int switchSort2;
    private int zoneNumber;
    private int battery = -1;
    private int buzzerState = 1;
    private int sceneLongPress = -1;
    private ArrayMap<String, RelateInfo> relateParamMap = new ArrayMap<>();

    public String getRelateParamMapString() {
        JSONObject parseObject = JSONObject.parseObject(GsonUtils.getGson().toJson(this.relateParamMap));
        putBaseFields(parseObject);
        parseObject.put("zoneNumber", (Object) Integer.valueOf(this.zoneNumber));
        parseObject.put("buzzerState", (Object) Integer.valueOf(this.buzzerState));
        parseObject.put("sceneDimmerBrt", (Object) this.sceneDimmerBrt);
        parseObject.put("isSeparated", (Object) Integer.valueOf(this.isSeparated));
        int i = this.switchShowType;
        if (i != 0) {
            parseObject.put("switchShowType", (Object) Integer.valueOf(i));
        }
        int i2 = this.switchScreenBigIcon;
        if (i2 != 0) {
            parseObject.put("switchScreenBigIcon", (Object) Integer.valueOf(i2));
        }
        int i3 = this.switchScreenLanguage;
        if (i3 != 0) {
            parseObject.put("switchScreenLanguage", (Object) Integer.valueOf(i3));
        }
        int i4 = this.battery;
        if (i4 != -1) {
            parseObject.put("battery", (Object) Integer.valueOf(i4));
        }
        int i5 = this.switchSort0;
        if (i5 != 0) {
            parseObject.put("switchSort0", (Object) Integer.valueOf(i5));
        }
        int i6 = this.switchSort1;
        if (i6 != 0) {
            parseObject.put("switchSort1", (Object) Integer.valueOf(i6));
        }
        int i7 = this.switchSort2;
        if (i7 != 0) {
            parseObject.put("switchSort2", (Object) Integer.valueOf(i7));
        }
        int i8 = this.switchPowerMemory;
        if (i8 != 0) {
            parseObject.put("switchPowerMemory", (Object) Integer.valueOf(i8));
        }
        int i9 = this.colorType;
        if (i9 != 0) {
            parseObject.put("colortype", (Object) Integer.valueOf(i9));
        }
        int i10 = this.sensitive;
        if (i10 != 0) {
            parseObject.put("sensitive", (Object) Integer.valueOf(i10));
        }
        int i11 = this.reportTime;
        if (i11 != 0) {
            parseObject.put("reportTime", (Object) Integer.valueOf(i11));
        }
        long j = this.panelId;
        if (j != 0) {
            parseObject.put(PushContentParamKey.PANEL_ID, (Object) Long.valueOf(j));
        }
        int i12 = this.sceneLongPress;
        if (i12 != -1) {
            parseObject.put(UpdateBackDataRequest.KEY_SAVE, (Object) Integer.valueOf(i12));
        }
        return parseObject.toJSONString();
    }

    public void fillMapWithString(String infoString) {
        if (this.relateParamMap == null) {
            this.relateParamMap = new ArrayMap<>();
        }
        if (infoString != null) {
            LHomeLog.i(getClass(), "device.getExtParam()=" + infoString);
            JSONObject parseObject = JSONObject.parseObject(infoString);
            for (String str : parseObject.keySet()) {
                if (str.equals("zone1") || str.equals("zone2") || str.equals("zone3") || str.equals("zone4") || str.equals("zone5") || str.equals("zone6") || str.equals("zone7") || str.equals("zone8") || str.equals("zone9") || str.equals("zone10") || str.equals("zone11") || str.equals("zone12") || str.equals("zone0")) {
                    this.relateParamMap.put(str, (RelateInfo) GsonUtils.getGson().fromJson(parseObject.getString(str), RelateInfo.class));
                }
                if (str.equals("zone1_1") || str.equals("zone2_1") || str.equals("zone3_1") || str.equals("zone4_1") || str.equals("zone5_1") || str.equals("zone6_1") || str.equals("zone7_1") || str.equals("zone8_1") || str.equals("zone9_1") || str.equals("zone10_1") || str.equals("zone11_1") || str.equals("zone12_1") || str.equals("zone0_1")) {
                    this.relateParamMap.put(str, (RelateInfo) GsonUtils.getGson().fromJson(parseObject.getString(str), RelateInfo.class));
                }
                if (str.equals("scene1") || str.equals("scene2") || str.equals("scene3") || str.equals("scene4") || str.equals("scene5") || str.equals("scene6") || str.equals("scene7") || str.equals("scene8")) {
                    this.relateParamMap.put(str, (RelateInfo) GsonUtils.getGson().fromJson(parseObject.getString(str), RelateInfo.class));
                } else if (str.equals("zoneNumber")) {
                    this.zoneNumber = parseObject.getIntValue(str);
                } else if (str.equals("switchShowType")) {
                    this.switchShowType = parseObject.getIntValue(str);
                } else if (str.equals("switchScreenBigIcon")) {
                    this.switchScreenBigIcon = parseObject.getIntValue(str);
                } else if (str.equals("switchScreenLanguage")) {
                    this.switchScreenLanguage = parseObject.getIntValue(str);
                } else if (str.equals("battery")) {
                    this.battery = parseObject.getIntValue(str);
                } else if (str.equals("switchSort0")) {
                    this.switchSort0 = parseObject.getIntValue(str);
                } else if (str.equals("switchSort1")) {
                    this.switchSort1 = parseObject.getIntValue(str);
                } else if (str.equals("switchSort2")) {
                    this.switchSort2 = parseObject.getIntValue(str);
                } else if (str.equals("switchPowerMemory")) {
                    this.switchPowerMemory = parseObject.getIntValue(str);
                } else if (str.equals("buzzerState")) {
                    this.buzzerState = parseObject.getIntValue(str);
                } else if (str.equals("sceneDimmerBrt")) {
                    this.sceneDimmerBrt = parseObject.getString(str);
                } else if (str.equals("sensitive")) {
                    this.sensitive = parseObject.getIntValue(str);
                } else if (str.equals("reportTime")) {
                    this.reportTime = parseObject.getIntValue(str);
                } else if (str.equals("colortype") || str.equals("colorType")) {
                    this.colorType = parseObject.getIntValue(str);
                } else if (str.equals(PushContentParamKey.PANEL_ID) || str.equals("panelId")) {
                    this.panelId = parseObject.getLongValue(str);
                } else if (str.equals(UpdateBackDataRequest.KEY_SAVE)) {
                    this.sceneLongPress = parseObject.getIntValue(str);
                } else {
                    getBaseFields(parseObject, str);
                }
            }
        }
    }

    public RelateInfo getRelateInfo(int position) {
        return this.relateParamMap.get(PREFIX + position);
    }

    public RelateInfo getRelateLongClickInfo(int position) {
        return this.relateParamMap.get(PREFIX + position + "_1");
    }

    public void setRelateInfo(int position, RelateInfo relateInfo) {
        this.relateParamMap.put(PREFIX + position, relateInfo);
    }

    public void setRelateLongClickInfo(int position, RelateInfo relateInfo) {
        this.relateParamMap.put(PREFIX + position + "_1", relateInfo);
    }

    public ArrayMap<String, RelateInfo> getRelateParamMap() {
        return this.relateParamMap;
    }

    public CurtainRelateInfo getCurtainRelateInfo(int position) {
        return this.relateInfoArrayMap.get(PREFIX + position);
    }

    public void setCurtainRelateInfo(int position, CurtainRelateInfo relateInfo) {
        this.relateInfoArrayMap.put(PREFIX + position, relateInfo);
    }

    public ArrayMap<String, CurtainRelateInfo> getRelateInfoArrayMap() {
        return this.relateInfoArrayMap;
    }

    public void setZoneNumber(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public int getZoneNumber() {
        return this.zoneNumber;
    }

    public int getSwitchShowType() {
        return this.switchShowType;
    }

    public void setSwitchShowType(int switchShowType) {
        this.switchShowType = switchShowType;
    }

    public int getSwitchScreenBigIcon() {
        return this.switchScreenBigIcon;
    }

    public void setSwitchScreenBigIcon(int switchScreenBigIcon) {
        this.switchScreenBigIcon = switchScreenBigIcon;
    }

    public int getSwitchScreenLanguage() {
        return this.switchScreenLanguage;
    }

    public void setSwitchScreenLanguage(int switchScreenLanguage) {
        this.switchScreenLanguage = switchScreenLanguage;
    }

    public int getBattery() {
        return this.battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getBuzzerState() {
        return this.buzzerState;
    }

    public void setBuzzerState(int buzzerState) {
        this.buzzerState = buzzerState;
    }

    public String getSceneDimmerBrt() {
        return this.sceneDimmerBrt;
    }

    public void setSceneDimmerBrt(String sceneDimmerBrt) {
        this.sceneDimmerBrt = sceneDimmerBrt;
    }

    public int getSensitive() {
        return this.sensitive;
    }

    public void setSensitive(int sensitive) {
        this.sensitive = sensitive;
    }

    public int getReportTime() {
        return this.reportTime;
    }

    public void setReportTime(int reportTime) {
        this.reportTime = reportTime;
    }

    public int getSwitchSort0() {
        return this.switchSort0;
    }

    public void setSwitchSort0(int switchSort0) {
        this.switchSort0 = switchSort0;
    }

    public int getSwitchSort1() {
        return this.switchSort1;
    }

    public void setSwitchSort1(int switchSort1) {
        this.switchSort1 = switchSort1;
    }

    public int getSwitchSort2() {
        return this.switchSort2;
    }

    public void setSwitchSort2(int switchSort2) {
        this.switchSort2 = switchSort2;
    }

    public int getSwitchPowerMemory() {
        return this.switchPowerMemory;
    }

    public int getColorType() {
        return this.colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }

    public long getPanelId() {
        return this.panelId;
    }

    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public RelateInfo getRelateSceneInfo(int position) {
        return this.relateParamMap.get(SCENE_PREFIX + position);
    }

    public void setRelateSceneInfo(int position, RelateInfo relateInfo) {
        this.relateParamMap.put(SCENE_PREFIX + position, relateInfo);
    }

    public void setIsSeparated(int isSeparated) {
        this.isSeparated = isSeparated;
    }

    public int getSceneLongPress() {
        return this.sceneLongPress;
    }

    public void setSceneLongPress(int sceneLongPress) {
        this.sceneLongPress = sceneLongPress;
    }

    public static class RelateInfo {
        public static final int TYPE_CGD_PRO = 8;
        public static final int TYPE_CURTAIN = 4;
        public static final int TYPE_CURTAIN_GROUP = 5;
        public static final int TYPE_DEVICE = 1;
        public static final int TYPE_GROUP = 2;
        public static final int TYPE_MUSIC_PLAYER = 6;
        public static final int TYPE_SCENE = 3;
        public static final int TYPE_TRIG = 7;
        public int action;
        public int bindingZone = 1;
        public int delay;
        public int iconIndex;
        public int keyActionExtra;
        public String name;
        public long objectId;
        public String screenStr;
        public int screenType;
        public int switchIndex;
        public String switchName;
        public int type;
        public String wholeDataExtra;

        public static RelateInfo RelatedInfo(Object o) {
            RelateInfo relateInfo = new RelateInfo();
            if (o instanceof Device) {
                relateInfo.type = 1;
                relateInfo.objectId = ((Device) o).getDeviceId();
                return relateInfo;
            }
            if (o instanceof Group) {
                relateInfo.type = 2;
                relateInfo.objectId = ((Group) o).getGroupId();
                return relateInfo;
            }
            if (o instanceof Scene) {
                relateInfo.type = 3;
                relateInfo.objectId = ((Scene) o).getSceneId();
            }
            return relateInfo;
        }

        public static RelateInfo RelatedDeviceInfo(long deviceId) {
            RelateInfo relateInfo = new RelateInfo();
            relateInfo.type = 1;
            relateInfo.objectId = deviceId;
            return relateInfo;
        }

        public static RelateInfo RelatedGroupInfo(long groupId) {
            RelateInfo relateInfo = new RelateInfo();
            relateInfo.type = 2;
            relateInfo.objectId = groupId;
            return relateInfo;
        }

        public static RelateInfo RelatedSceneInfo(long sceneId) {
            RelateInfo relateInfo = new RelateInfo();
            relateInfo.type = 3;
            relateInfo.objectId = sceneId;
            return relateInfo;
        }

        public static RelateInfo RelatePanelDeviceInfo(long deviceId, int bindingZone) {
            RelateInfo relateInfo = new RelateInfo();
            relateInfo.type = 1;
            relateInfo.objectId = deviceId;
            relateInfo.bindingZone = bindingZone;
            return relateInfo;
        }

        public static RelateInfo RelatedPanelGroupInfo(long groupId, int bindingZone) {
            RelateInfo relateInfo = new RelateInfo();
            relateInfo.type = 2;
            relateInfo.objectId = groupId;
            relateInfo.bindingZone = bindingZone;
            return relateInfo;
        }

        public static RelateInfo RelateCurtainDeviceInfo(long deviceId) {
            return RelateCurtainDeviceInfo(deviceId, 0);
        }

        public static RelateInfo RelateCurtainDeviceInfo(long deviceId, int keyActionExtra) {
            RelateInfo relateInfo = new RelateInfo();
            relateInfo.type = 1;
            relateInfo.objectId = deviceId;
            relateInfo.keyActionExtra = keyActionExtra;
            return relateInfo;
        }

        public boolean isRelateDeviceInfo() {
            int i = this.type;
            return ((i != 8 || this.action == 37) && i != 7) ? i == 1 || i == 4 || i == 6 : Injection.repo().device().getDeviceByDeviceId(this.objectId) != null;
        }

        public boolean isRelateGroupInfo() {
            int i = this.type;
            return ((i != 8 || this.action == 37) && i != 7) ? i == 2 || i == 5 : Injection.repo().group().getGroupByGroupId(this.objectId) != null;
        }

        public boolean isRelateSceneInfo() {
            return this.type == 3;
        }

        public boolean isRelateDaliSceneInfo() {
            return this.type == 8 && this.action == 37;
        }

        public boolean isRelateLongClickDaliSceneInfo() {
            return this.type == 8 && this.action == 38;
        }

        public boolean isRelateCgdAdd() {
            int i;
            return (this.type != 8 || (i = this.action) == 36 || i == 37 || Injection.repo().device().getDeviceByDeviceId(this.objectId) == null) ? false : true;
        }
    }

    public static class CurtainRelateInfo extends RelateInfo {
        public int keyActionExtra;

        public static CurtainRelateInfo RelateCurtainDeviceInfo(long deviceId) {
            return RelateCurtainDeviceInfo(deviceId, 0);
        }

        public static CurtainRelateInfo RelateCurtainDeviceInfo(long deviceId, int keyActionExtra) {
            CurtainRelateInfo curtainRelateInfo = new CurtainRelateInfo();
            curtainRelateInfo.type = 1;
            curtainRelateInfo.objectId = deviceId;
            curtainRelateInfo.keyActionExtra = keyActionExtra;
            return curtainRelateInfo;
        }
    }

    public enum ScreenType {
        ScreenTypeNone(0),
        ScreenTypeWord(1),
        ScreenTypeIcon(2),
        ScreenTypeIconWord(3);

        private int value;

        ScreenType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}