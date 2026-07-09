package com.ltech.smarthome.model.device_param;

import android.util.ArrayMap;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.base.BaseExtParam;

/* loaded from: classes4.dex */
public class TrigToBleExtParam extends BaseExtParam {
    private static final String PREFIX = "zone";
    private ArrayMap<String, TrigRelateInfo> relateParamMap = new ArrayMap<>();

    public void fillMapWithString(String infoString) {
        if (this.relateParamMap == null) {
            this.relateParamMap = new ArrayMap<>();
        }
        if (infoString != null) {
            JSONObject parseObject = JSONObject.parseObject(infoString);
            for (String str : parseObject.keySet()) {
                if (str.equals("zone1") || str.equals("zone2") || str.equals("zone3") || str.equals("zone4") || str.equals("zone5") || str.equals("zone6") || str.equals("zone7") || str.equals("zone8") || str.equals("zone9") || str.equals("zone10") || str.equals("zone11") || str.equals("zone12") || str.equals("zone13") || str.equals("zone14") || str.equals("zone15")) {
                    this.relateParamMap.put(str, (TrigRelateInfo) GsonUtils.getGson().fromJson(parseObject.getString(str), TrigRelateInfo.class));
                } else {
                    getBaseFields(parseObject, str);
                }
            }
        }
    }

    public void fillMapWithString(String infoString, int num) {
        fillMapWithString(infoString);
        if (this.relateParamMap.isEmpty()) {
            int i = 0;
            while (i < num) {
                ArrayMap<String, TrigRelateInfo> arrayMap = this.relateParamMap;
                StringBuilder sb = new StringBuilder(PREFIX);
                i++;
                sb.append(i);
                arrayMap.put(sb.toString(), new TrigRelateInfo());
            }
        }
    }

    public String getRelateParamMapString() {
        JSONObject parseObject = JSONObject.parseObject(GsonUtils.getGson().toJson(this.relateParamMap));
        putBaseFields(parseObject);
        return parseObject.toJSONString();
    }

    public TrigRelateInfo getRelateInfo(int position) {
        return this.relateParamMap.get(PREFIX + position);
    }

    public void setRelateInfo(int position, TrigRelateInfo relateInfo) {
        this.relateParamMap.put(PREFIX + position, relateInfo);
    }

    public static class TrigRelateInfo {
        private int actiontype;
        private String customerName;
        private String executecommand;

        public int getActiontype() {
            return this.actiontype;
        }

        public void setActiontype(int actiontype) {
            this.actiontype = actiontype;
        }

        public String getExecutecommand() {
            return this.executecommand;
        }

        public void setExecutecommand(String executecommand) {
            this.executecommand = executecommand;
        }

        public <T> T getExecuteCommand(Class<T> cls) {
            return (T) GsonUtils.getGson().fromJson(this.executecommand, (Class) cls);
        }

        public String getCustomerName() {
            return this.customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }
    }
}