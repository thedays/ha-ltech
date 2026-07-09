package com.ltech.smarthome.model.device_param;

import android.util.ArrayMap;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class DiyIrParam extends BaseIrParam {
    private List<ArrayMap<String, DiyIrKey>> irDatas = new ArrayList();

    public DiyIrParam(List<DiyIrKey> keyList) {
        setDiyIrKeyList(keyList);
    }

    public void setDiyIrKeyList(List<DiyIrKey> keyList) {
        ArrayMap<String, DiyIrKey> arrayMap = new ArrayMap<>();
        int size = keyList.size();
        for (int i = 0; i < size; i++) {
            arrayMap.put(String.valueOf(i), keyList.get(i));
        }
        this.irDatas.clear();
        this.irDatas.add(arrayMap);
    }

    public List<DiyIrKey> getDiyIrKeyList() {
        List<ArrayMap<String, DiyIrKey>> list = this.irDatas;
        if (list == null || list.isEmpty() || this.irDatas.get(0) == null) {
            return new ArrayList();
        }
        return new ArrayList(this.irDatas.get(0).values());
    }

    public void changeKeyName(int position, String keyName) {
        List<ArrayMap<String, DiyIrKey>> list = this.irDatas;
        if (list == null || list.isEmpty() || this.irDatas.get(0) == null) {
            return;
        }
        List<DiyIrKey> diyIrKeyList = getDiyIrKeyList();
        diyIrKeyList.get(position).setKeyName(keyName);
        setDiyIrKeyList(diyIrKeyList);
    }

    public void changeKeyData(int position, String keyData) {
        List<ArrayMap<String, DiyIrKey>> list = this.irDatas;
        if (list == null || list.isEmpty() || this.irDatas.get(0) == null) {
            return;
        }
        List<DiyIrKey> diyIrKeyList = getDiyIrKeyList();
        diyIrKeyList.get(position).setKeyData(keyData);
        setDiyIrKeyList(diyIrKeyList);
    }

    public void deleteKey(int position) {
        List<ArrayMap<String, DiyIrKey>> list = this.irDatas;
        if (list == null || list.isEmpty() || this.irDatas.get(0) == null) {
            return;
        }
        List<DiyIrKey> diyIrKeyList = getDiyIrKeyList();
        diyIrKeyList.remove(position);
        setDiyIrKeyList(diyIrKeyList);
    }

    public static final class DiyIrKey {
        private String keyData;
        private String keyName;

        public String getKeyName() {
            return this.keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }

        public String getKeyData() {
            return this.keyData;
        }

        public void setKeyData(String keyData) {
            this.keyData = keyData;
        }
    }
}