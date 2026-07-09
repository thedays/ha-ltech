package com.ltech.nfc.source;

import android.content.Context;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.nfc.R;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SourceModel {
    public static final int KBS = 200;
    public static final int KBS1 = 201;
    public static final int TYPE_BLE = 3;
    public static final int TYPE_BLE5 = 8;
    public static final int TYPE_BLE_PANEL = 17;
    public static final int TYPE_E6 = 12;
    public static final int TYPE_HB4 = 19;
    public int IOutMax;
    public int IOutMin;
    public float P;
    public int UOutMax;
    public int UOutMin;
    public int[] currentSteps;
    public int daliChannelNum;
    public int daliDeviceType;
    public String[] frequencyArray;
    public int[] linearGamas;
    public int[] logGamas;
    public int maxAdd;
    public int[] otherGamas;
    public int sourceId;
    public String sourceName;
    public int sourceType;
    public int[] specialGamas;
    public String uuid;
    public boolean hasPowerFadeTime = true;
    public int versionFlag = 1;

    public int getBlockNumber() {
        return 30;
    }

    public int getCheckBlockNumber() {
        return 0;
    }

    public static SourceModel getSourceModel(int i) {
        for (SourceModel sourceModel : SourceHelper.sourceModelList) {
            if (sourceModel.sourceId == i) {
                String json = GsonUtils.toJson(sourceModel);
                int i2 = sourceModel.sourceType;
                if (i2 == 3) {
                    return (SourceModel) GsonUtils.fromJson(json, BleSource.class);
                }
                if (i2 == 8) {
                    return (SourceModel) GsonUtils.fromJson(json, BleSourceFive.class);
                }
                if (i2 == 12) {
                    return (SourceModel) GsonUtils.fromJson(json, E6Model.class);
                }
                return i2 == 19 ? (SourceModel) GsonUtils.fromJson(json, HB4Model.class) : sourceModel;
            }
        }
        return null;
    }

    public boolean isConstantVoltage() {
        return this.UOutMax == this.UOutMin;
    }

    public boolean isBleDimDevice() {
        return this.sourceType == 3 && this.daliChannelNum == 1;
    }

    public boolean isBleSource() {
        int i = this.sourceType;
        return i == 3 || i == 8 || i == 19;
    }

    public boolean isBleSwitch() {
        int i = this.sourceId;
        return i == 200 || i == 201;
    }

    public boolean supportPwmFrequency() {
        if (this.sourceType == 19) {
            return false;
        }
        return isConstantVoltage();
    }

    public String[] getFrequencyArray(Context context) {
        String[] strArr = this.frequencyArray;
        if (strArr == null || strArr.length == 0) {
            String[] stringArray = context.getResources().getStringArray(R.array.pwm_frequency_ad);
            this.frequencyArray = stringArray;
            return stringArray;
        }
        int i = 0;
        while (true) {
            String[] strArr2 = this.frequencyArray;
            if (i >= strArr2.length) {
                return strArr2;
            }
            if (strArr2[i].contains("默认")) {
                String[] strArr3 = this.frequencyArray;
                strArr3[i] = strArr3[i].replace("默认", context.getResources().getString(R.string.default_value));
            } else if (this.frequencyArray[i].contains("常规")) {
                String[] strArr4 = this.frequencyArray;
                strArr4[i] = strArr4[i].replace("常规", context.getResources().getString(R.string.general));
            }
            i++;
        }
    }

    public static String getSourceName(int i) {
        for (SourceModel sourceModel : SourceHelper.sourceModelList) {
            if (sourceModel.sourceId == i) {
                return sourceModel.sourceName;
            }
        }
        return null;
    }

    public int getOutputCurrent(int i) {
        if (isConstantVoltage()) {
            return 0;
        }
        return Math.max(Math.min(i, this.IOutMax), this.IOutMin);
    }

    public String getOutputParam(int i) {
        if (isConstantVoltage()) {
            return "";
        }
        double d2 = i;
        return ActivityUtils.getTopActivity().getString(R.string.param_scope, new Object[]{Float.valueOf(this.UOutMin), BigDecimal.valueOf(Math.min((this.P * 1000.0d) / d2, this.UOutMax)).setScale(1, RoundingMode.DOWN), new BigDecimal((d2 / 1000.0d) * this.UOutMin).setScale(2, RoundingMode.DOWN), BigDecimal.valueOf(Math.min((this.UOutMax * i) / 1000.0d, this.P)).setScale(2, RoundingMode.DOWN)});
    }

    public String getOutputParamFive(int i) {
        double d2 = i;
        return ActivityUtils.getTopActivity().getString(R.string.param_scope_five, new Object[]{Float.valueOf(this.UOutMin), BigDecimal.valueOf(Math.min((this.P * 1000.0d) / d2, this.UOutMax)).setScale(1, RoundingMode.DOWN), new BigDecimal((d2 / 1000.0d) * this.UOutMin).setScale(2, RoundingMode.DOWN), BigDecimal.valueOf(Math.min((this.UOutMax * i) / 1000.0d, this.P)).setScale(2, RoundingMode.DOWN)});
    }

    public int calculateGama(int i) {
        int[] iArr = this.logGamas;
        if (iArr != null) {
            return calculateGama(i, this.currentSteps, iArr);
        }
        int[] iArr2 = this.otherGamas;
        if (iArr2 != null) {
            return calculateGama(i, this.currentSteps, iArr2);
        }
        return 0;
    }

    public int calculateLinearGama(int i) {
        int[] iArr = this.linearGamas;
        if (iArr != null) {
            return calculateGama(i, this.currentSteps, iArr);
        }
        int[] iArr2 = this.otherGamas;
        if (iArr2 != null) {
            return calculateGama(i, this.currentSteps, iArr2);
        }
        return 0;
    }

    public int calculateGama(int i, int i2) {
        int[] iArr = this.specialGamas;
        if (iArr != null && iArr.length != 0 && i2 <= 1) {
            return calculateGama(i, this.currentSteps, iArr);
        }
        return calculateGama(i);
    }

    private int calculateGama(int i, int[] iArr, int[] iArr2) {
        int i2;
        int i3;
        if (iArr != null && iArr2 != null) {
            for (int i4 = 0; i4 < iArr.length - 1; i4++) {
                if (i >= iArr[i4] && i <= (i3 = iArr[(i2 = i4 + 1)])) {
                    return Math.round(((iArr2[i2] - iArr2[i4]) / (i3 - r2)) * (i - r2)) + iArr2[i4];
                }
            }
        }
        return 0;
    }

    public int getCheckSum(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 <= getCheckBlockNumber() * 4; i2++) {
            if (!getBlankBlocks().contains(Integer.valueOf(i2))) {
                i += iArr[i2];
            }
        }
        return i;
    }

    public int getCheckSum(List<Integer> list, int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            i += iArr[list.get(i2).intValue()];
        }
        return i;
    }

    public static List<Integer> getExtendDataList(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        while (i <= i2) {
            arrayList.add(Integer.valueOf(i));
            i++;
        }
        return arrayList;
    }

    public List<Integer> getBlankBlocks() {
        return new ArrayList();
    }
}