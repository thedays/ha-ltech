package com.ltech.nfc.source;

import com.ltech.nfc.utils.DataUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class NetInfo {
    private String appKey = "63964771734FBD76E3B40519D1D94A48";
    private int countFlag;
    private String deviceKey;
    private int ivIndex;
    private String netKey;
    private int nodeId;

    public NetInfo(int i) {
        if (i == 255) {
            this.countFlag = 0;
        } else {
            this.countFlag = i + 1;
        }
    }

    public void setNodeId(int i) {
        this.nodeId = i;
    }

    public void setNetKey(String str) {
        this.netKey = str;
    }

    public void setDeviceKey(String str) {
        this.deviceKey = str;
    }

    public void setIvIndex(int i) {
        this.ivIndex = i;
    }

    public int getCountFlag() {
        return this.countFlag;
    }

    public List<Integer> getNetArray(boolean z) {
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.add(1);
            arrayList.add(Integer.valueOf(this.nodeId & 255));
            arrayList.add(Integer.valueOf(this.nodeId >> 8));
            arrayList.addAll(DataUtil.hexStringToIntList(this.netKey));
            arrayList.addAll(DataUtil.hexStringToIntList(this.deviceKey));
            arrayList.add(Integer.valueOf(this.ivIndex & 255));
            arrayList.add(Integer.valueOf((this.ivIndex >> 8) & 255));
            arrayList.add(Integer.valueOf((this.ivIndex >> 16) & 255));
            arrayList.add(Integer.valueOf((this.ivIndex >> 24) & 255));
            int crc16 = getCRC16();
            arrayList.add(3, Integer.valueOf(crc16 & 255));
            arrayList.add(4, Integer.valueOf(crc16 >> 8));
        } else {
            arrayList.addAll(Collections.nCopies(41, 0));
        }
        int checkSum = DataUtil.getCheckSum(arrayList, 0, arrayList.size());
        arrayList.add(0, Integer.valueOf(this.countFlag));
        arrayList.add(1, Integer.valueOf(checkSum & 255));
        arrayList.add(2, Integer.valueOf(checkSum >> 8));
        return arrayList;
    }

    private int getCRC16() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(DataUtil.hexStringToIntList(this.netKey));
        arrayList.addAll(DataUtil.hexStringToIntList(this.appKey));
        arrayList.addAll(DataUtil.hexStringToIntList(this.deviceKey));
        arrayList.addAll(DataUtil.hexStringToIntList("00000000"));
        arrayList.add(Integer.valueOf(this.ivIndex & 255));
        arrayList.add(Integer.valueOf((this.ivIndex >> 8) & 255));
        arrayList.add(Integer.valueOf((this.ivIndex >> 16) & 255));
        arrayList.add(Integer.valueOf((this.ivIndex >> 24) & 255));
        arrayList.add(0);
        arrayList.add(Integer.valueOf(this.nodeId & 255));
        arrayList.add(Integer.valueOf(this.nodeId >> 8));
        return DataUtil.crc16(arrayList);
    }
}