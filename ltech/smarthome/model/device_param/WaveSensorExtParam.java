package com.ltech.smarthome.model.device_param;

import android.util.ArrayMap;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.base.BaseExtParam;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class WaveSensorExtParam extends BaseExtParam {
    private int automationDelay;
    private int microWaveLux;
    private int microWaveSensitive;
    private List<SensorParam> delayParam = Arrays.asList(null, null, null);
    private List<SensorParam> stayParam = Arrays.asList(null, null, null);
    private List<SensorParam> exitParam = Arrays.asList(null, null, null);
    private int[] effectTimeStartH = {0, 0, 0};
    private int[] effectTimeStartM = {0, 0, 0};
    private int[] effectTimeEndH = {23, 23, 23};
    private int[] effectTimeEndM = {59, 59, 59};
    private int[] effectTimeRepeat = {127, 127, 127};
    private int[] relayAlwaysOn = {0, 0, 0};
    private int[] effectTimeEnable = {1, 0, 0};

    public void fillMapWithString(String paramString) {
        if (paramString != null) {
            JSONObject parseObject = JSONObject.parseObject(paramString);
            for (String str : parseObject.keySet()) {
                if ("microWaveAction1".equals(str)) {
                    this.delayParam.set(0, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("microWaveAction2".equals(str)) {
                    this.stayParam.set(0, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("microWaveAction3".equals(str)) {
                    this.exitParam.set(0, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("microWaveAction1_1".equals(str)) {
                    this.delayParam.set(1, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("microWaveAction2_1".equals(str)) {
                    this.stayParam.set(1, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("microWaveAction3_1".equals(str)) {
                    this.exitParam.set(1, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("microWaveAction1_2".equals(str)) {
                    this.delayParam.set(2, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("microWaveAction2_2".equals(str)) {
                    this.stayParam.set(2, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("microWaveAction3_2".equals(str)) {
                    this.exitParam.set(2, (SensorParam) GsonUtils.getGson().fromJson(parseObject.getString(str), SensorParam.class));
                } else if ("effectTimeStartH".equals(str)) {
                    this.effectTimeStartH[0] = parseObject.getIntValue(str);
                } else if ("effectTimeStartM".equals(str)) {
                    this.effectTimeStartM[0] = parseObject.getIntValue(str);
                } else if ("effectTimeEndH".equals(str)) {
                    this.effectTimeEndH[0] = parseObject.getIntValue(str);
                } else if ("effectTimeEndM".equals(str)) {
                    this.effectTimeEndM[0] = parseObject.getIntValue(str);
                } else if ("effectTimeRepeat".equals(str)) {
                    this.effectTimeRepeat[0] = parseObject.getIntValue(str);
                } else if ("relayAlwaysOn".equals(str)) {
                    this.relayAlwaysOn[0] = parseObject.getIntValue(str);
                } else if ("effectTimeEnable".equals(str)) {
                    this.effectTimeEnable[0] = parseObject.getIntValue(str);
                } else if ("effectTimeStartH_1".equals(str)) {
                    this.effectTimeStartH[1] = parseObject.getIntValue(str);
                } else if ("effectTimeStartM_1".equals(str)) {
                    this.effectTimeStartM[1] = parseObject.getIntValue(str);
                } else if ("effectTimeEndH_1".equals(str)) {
                    this.effectTimeEndH[1] = parseObject.getIntValue(str);
                } else if ("effectTimeEndM_1".equals(str)) {
                    this.effectTimeEndM[1] = parseObject.getIntValue(str);
                } else if ("effectTimeRepeat_1".equals(str)) {
                    this.effectTimeRepeat[1] = parseObject.getIntValue(str);
                } else if ("relayAlwaysOn_1".equals(str)) {
                    this.relayAlwaysOn[1] = parseObject.getIntValue(str);
                } else if ("effectTimeEnable_1".equals(str)) {
                    this.effectTimeEnable[1] = parseObject.getIntValue(str);
                } else if ("effectTimeStartH_2".equals(str)) {
                    this.effectTimeStartH[2] = parseObject.getIntValue(str);
                } else if ("effectTimeStartM_2".equals(str)) {
                    this.effectTimeStartM[2] = parseObject.getIntValue(str);
                } else if ("effectTimeEndH_2".equals(str)) {
                    this.effectTimeEndH[2] = parseObject.getIntValue(str);
                } else if ("effectTimeEndM_2".equals(str)) {
                    this.effectTimeEndM[2] = parseObject.getIntValue(str);
                } else if ("effectTimeRepeat_2".equals(str)) {
                    this.effectTimeRepeat[2] = parseObject.getIntValue(str);
                } else if ("relayAlwaysOn_2".equals(str)) {
                    this.relayAlwaysOn[2] = parseObject.getIntValue(str);
                } else if ("effectTimeEnable_2".equals(str)) {
                    this.effectTimeEnable[2] = parseObject.getIntValue(str);
                } else if ("microWaveSensitive".equals(str)) {
                    this.microWaveSensitive = parseObject.getIntValue(str);
                } else if ("microWaveLux".equals(str)) {
                    this.microWaveLux = parseObject.getIntValue(str);
                } else if ("automationDelay".equals(str)) {
                    this.automationDelay = parseObject.getIntValue(str);
                } else {
                    getBaseFields(parseObject, str);
                }
            }
        }
    }

    public String getSensorParamMapString() {
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("microWaveAction1", this.delayParam.get(0));
        arrayMap.put("microWaveAction2", this.stayParam.get(0));
        arrayMap.put("microWaveAction3", this.exitParam.get(0));
        arrayMap.put("microWaveAction1_1", this.delayParam.get(1));
        arrayMap.put("microWaveAction2_1", this.stayParam.get(1));
        arrayMap.put("microWaveAction3_1", this.exitParam.get(1));
        arrayMap.put("microWaveAction1_2", this.delayParam.get(2));
        arrayMap.put("microWaveAction2_2", this.stayParam.get(2));
        arrayMap.put("microWaveAction3_2", this.exitParam.get(2));
        JSONObject parseObject = JSONObject.parseObject(GsonUtils.getGson().toJson(arrayMap));
        putBaseFields(parseObject);
        parseObject.put("microWaveSensitive", (Object) Integer.valueOf(this.microWaveSensitive));
        parseObject.put("microWaveLux", (Object) Integer.valueOf(this.microWaveLux));
        parseObject.put("automationDelay", (Object) Integer.valueOf(this.automationDelay));
        parseObject.put("effectTimeStartH", (Object) Integer.valueOf(this.effectTimeStartH[0]));
        parseObject.put("effectTimeStartM", (Object) Integer.valueOf(this.effectTimeStartM[0]));
        parseObject.put("effectTimeEndH", (Object) Integer.valueOf(this.effectTimeEndH[0]));
        parseObject.put("effectTimeEndM", (Object) Integer.valueOf(this.effectTimeEndM[0]));
        parseObject.put("effectTimeRepeat", (Object) Integer.valueOf(this.effectTimeRepeat[0]));
        parseObject.put("relayAlwaysOn", (Object) Integer.valueOf(this.relayAlwaysOn[0]));
        parseObject.put("effectTimeEnable", (Object) Integer.valueOf(this.effectTimeEnable[0]));
        parseObject.put("effectTimeStartH_1", (Object) Integer.valueOf(this.effectTimeStartH[1]));
        parseObject.put("effectTimeStartM_1", (Object) Integer.valueOf(this.effectTimeStartM[1]));
        parseObject.put("effectTimeEndH_1", (Object) Integer.valueOf(this.effectTimeEndH[1]));
        parseObject.put("effectTimeEndM_1", (Object) Integer.valueOf(this.effectTimeEndM[1]));
        parseObject.put("effectTimeRepeat_1", (Object) Integer.valueOf(this.effectTimeRepeat[1]));
        parseObject.put("relayAlwaysOn_1", (Object) Integer.valueOf(this.relayAlwaysOn[1]));
        parseObject.put("effectTimeEnable_1", (Object) Integer.valueOf(this.effectTimeEnable[1]));
        parseObject.put("effectTimeStartH_2", (Object) Integer.valueOf(this.effectTimeStartH[2]));
        parseObject.put("effectTimeStartM_2", (Object) Integer.valueOf(this.effectTimeStartM[2]));
        parseObject.put("effectTimeEndH_2", (Object) Integer.valueOf(this.effectTimeEndH[2]));
        parseObject.put("effectTimeEndM_2", (Object) Integer.valueOf(this.effectTimeEndM[2]));
        parseObject.put("effectTimeRepeat_2", (Object) Integer.valueOf(this.effectTimeRepeat[2]));
        parseObject.put("relayAlwaysOn_2", (Object) Integer.valueOf(this.relayAlwaysOn[2]));
        parseObject.put("effectTimeEnable_2", (Object) Integer.valueOf(this.effectTimeEnable[2]));
        return parseObject.toJSONString();
    }

    public SensorParam getDelayParam(int index) {
        SensorParam sensorParam = this.delayParam.get(index);
        if (sensorParam != null) {
            return sensorParam;
        }
        SensorParam sensorParam2 = new SensorParam();
        sensorParam2.destAddress = 1;
        sensorParam2.actionPart = 1;
        sensorParam2.optionValue = 1;
        sensorParam2.delayTime = 30;
        sensorParam2.instruct = "000100010101";
        setDelayParam(index, sensorParam2);
        return sensorParam2;
    }

    public SensorParam getStayParam(int index) {
        SensorParam sensorParam = this.stayParam.get(index);
        if (sensorParam != null) {
            return sensorParam;
        }
        SensorParam sensorParam2 = new SensorParam();
        sensorParam2.actionPart = 2;
        sensorParam2.delayTime = 30;
        setStayParam(index, sensorParam2);
        return sensorParam2;
    }

    public SensorParam getExitParam(int index) {
        SensorParam sensorParam = this.exitParam.get(index);
        if (sensorParam != null) {
            return sensorParam;
        }
        SensorParam sensorParam2 = new SensorParam();
        sensorParam2.destAddress = 1;
        sensorParam2.actionPart = 3;
        sensorParam2.optionValue = 0;
        sensorParam2.instruct = "000100030100";
        setExitParam(index, sensorParam2);
        return sensorParam2;
    }

    public void setDelayParam(int index, SensorParam sensorParam) {
        this.delayParam.set(index, sensorParam);
    }

    public void setStayParam(int index, SensorParam sensorParam) {
        this.stayParam.set(index, sensorParam);
    }

    public void setExitParam(int index, SensorParam sensorParam) {
        this.exitParam.set(index, sensorParam);
    }

    public int getEffectTimeStartH(int index) {
        return this.effectTimeStartH[index];
    }

    public void setEffectTimeStartH(int index, int effectTimeStartH) {
        this.effectTimeStartH[index] = effectTimeStartH;
    }

    public int getEffectTimeStartM(int index) {
        return this.effectTimeStartM[index];
    }

    public void setEffectTimeStartM(int index, int effectTimeStartM) {
        this.effectTimeStartM[index] = effectTimeStartM;
    }

    public int getEffectTimeEndH(int index) {
        return this.effectTimeEndH[index];
    }

    public void setEffectTimeEndH(int index, int effectTimeEndH) {
        this.effectTimeEndH[index] = effectTimeEndH;
    }

    public int getEffectTimeEndM(int index) {
        return this.effectTimeEndM[index];
    }

    public void setEffectTimeEndM(int index, int effectTimeEndM) {
        this.effectTimeEndM[index] = effectTimeEndM;
    }

    public int getEffectTimeRepeat(int index) {
        return this.effectTimeRepeat[index];
    }

    public void setEffectTimeRepeat(int index, int effectTimeRepeat) {
        this.effectTimeRepeat[index] = effectTimeRepeat;
    }

    public int getRelayAlwaysOn(int index) {
        return this.relayAlwaysOn[index];
    }

    public void setRelayAlwaysOn(int index, int relayAlwaysOn) {
        this.relayAlwaysOn[index] = relayAlwaysOn;
    }

    public int getEffectTimeEnable(int index) {
        return this.effectTimeEnable[index];
    }

    public void setEffectTimeEnable(int index, int effectTimeEnable) {
        this.effectTimeEnable[index] = effectTimeEnable;
    }

    public int getMicroWaveSensitive() {
        return this.microWaveSensitive;
    }

    public void setMicroWaveSensitive(int microWaveSensitive) {
        this.microWaveSensitive = microWaveSensitive;
    }

    public int getMicroWaveLux() {
        return this.microWaveLux;
    }

    public void setMicroWaveLux(int microWaveLux) {
        this.microWaveLux = microWaveLux;
    }

    public int getAutomationDelay() {
        return this.automationDelay;
    }

    public void setAutomationDelay(int automationDelay) {
        this.automationDelay = automationDelay;
    }

    public static class SensorParam {
        public int actionPart;
        public int actionType;
        public int delayTime;
        public int destAddress;
        public long objectId;
        public int optionValue;
        public int smartType;
        public String option = "";
        public String instruct = "";

        public boolean isV010orDali() {
            int i = this.actionType;
            return i == 1 || i == 2;
        }
    }
}