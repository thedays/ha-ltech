package com.ltech.smarthome.model.device_param;

import android.util.ArrayMap;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.adapter.ArrayMapTypeAdapter;
import com.ltech.smarthome.base.BaseExtParam;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class TrigExtParam extends BaseExtParam {
    public static final String BTN_CHANNELS = "btnChannels";
    public static final String CURTAIN_TYPE = "curtainType";
    private ArrayMap<String, Object> infoMap = new ArrayMap<>();

    public String getParamMapString() {
        return GsonUtils.getGson().toJson(this.infoMap);
    }

    public void fillMapWithString(String infoString) {
        ArrayMap<String, Object> arrayMap = (ArrayMap) new GsonBuilder().registerTypeAdapter(new TypeToken<ArrayMap<String, Object>>(this) { // from class: com.ltech.smarthome.model.device_param.TrigExtParam.1
        }.getType(), new ArrayMapTypeAdapter()).create().fromJson(infoString, new TypeToken<ArrayMap<String, Object>>(this) { // from class: com.ltech.smarthome.model.device_param.TrigExtParam.2
        }.getType());
        this.infoMap = arrayMap;
        if (arrayMap == null) {
            this.infoMap = new ArrayMap<>();
        }
    }

    public int getCurtainType() {
        if (this.infoMap.containsKey(CURTAIN_TYPE)) {
            return ((Integer) this.infoMap.get(CURTAIN_TYPE)).intValue();
        }
        return -1;
    }

    public void setCurtainType(int curtainType) {
        this.infoMap.put(CURTAIN_TYPE, Integer.valueOf(curtainType));
    }

    public ArrayList<Integer> getBtnChannels() {
        if (this.infoMap.containsKey(BTN_CHANNELS)) {
            return (ArrayList) this.infoMap.get(BTN_CHANNELS);
        }
        return null;
    }

    public void setBtnChannels(int[] channels) {
        this.infoMap.put(BTN_CHANNELS, channels);
    }

    public ArrayMap<String, Object> getInfoMap() {
        return this.infoMap;
    }
}