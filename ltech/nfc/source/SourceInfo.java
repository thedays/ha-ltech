package com.ltech.nfc.source;

import com.ltech.nfc.utils.DataUtil;

/* loaded from: classes3.dex */
public class SourceInfo {
    private int bleModule;
    private int bleVersion;
    private boolean check;
    private int countFlag;
    private String mac;
    private int netFlag;
    private int nfcVersion;
    private int[] paramValue;
    private int sourceId;
    private int sourceType;
    private String uuid;

    public SourceInfo(int[] iArr, int[] iArr2) {
        this.paramValue = iArr2;
        this.check = DataUtil.getCheckSum(iArr, 2, 48) == ((iArr[1] << 8) | iArr[0]);
        this.nfcVersion = iArr[2] | (iArr[3] << 8);
        this.sourceId = (iArr[5] << 8) | iArr[4];
        this.mac = getString(iArr, 14, 6);
        this.bleVersion = (iArr[21] << 8) | iArr[20];
        this.uuid = getString(iArr, 22, 3);
        this.netFlag = iArr[25];
        this.bleModule = iArr[26];
        this.sourceType = iArr[27];
        this.countFlag = iArr[52];
    }

    public boolean isValid() {
        return this.check && this.nfcVersion != 0 && this.bleVersion >= 394;
    }

    private String getString(int[] iArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        for (int i3 = i; i3 < i + i2; i3++) {
            String hexString = Integer.toHexString(iArr[i3]);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }

    public int[] getParamValue() {
        return this.paramValue;
    }

    public int getNfcVersion() {
        return this.nfcVersion;
    }

    public int getSourceId() {
        return this.sourceId;
    }

    public String getMac() {
        return this.mac;
    }

    public int getBleVersion() {
        return this.bleVersion;
    }

    public String getUuid() {
        return this.uuid;
    }

    public int getNetFlag() {
        return this.netFlag;
    }

    public int getBleModule() {
        return this.bleModule;
    }

    public int getSourceType() {
        return this.sourceType;
    }

    public int getCountFlag() {
        return this.countFlag;
    }

    public boolean isCheck() {
        return this.check;
    }
}