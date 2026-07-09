package com.ltech.smarthome.ui.device.central.air;

import com.ltech.smarthome.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class AcFeatureHelper {
    public String feature;

    public AcFeatureHelper(String feature) {
        this.feature = feature.substring(6, 20);
    }

    public int getBrandNameRes() {
        int parseInt = Integer.parseInt(this.feature.substring(0, 2), 16);
        if (parseInt == 19) {
            return R.string.gree_4;
        }
        if (parseInt == 21) {
            return R.string.mcquay;
        }
        if (parseInt == 255) {
            return R.string.simulator;
        }
        if (parseInt == 101) {
            return R.string.emerson_sj;
        }
        if (parseInt == 102) {
            return R.string.mcquay_sj;
        }
        switch (parseInt) {
            case 1:
                return R.string.hitachi;
            case 2:
                return R.string.daikin;
            case 3:
                return R.string.toshiba;
            case 4:
                return R.string.slzg;
            case 5:
                return R.string.sldj;
            case 6:
                return R.string.gree;
            case 7:
                return R.string.hisense;
            case 8:
                return R.string.midea;
            case 9:
                return R.string.haier;
            case 10:
                return R.string.lg;
            default:
                switch (parseInt) {
                    case 13:
                        return R.string.samsung;
                    case 14:
                        return R.string.aux;
                    case 15:
                        return R.string.panasonic;
                    case 16:
                        return R.string.york;
                    default:
                        switch (parseInt) {
                            case 24:
                                return R.string.tcl;
                            case 25:
                                return R.string.chigo;
                            case 26:
                                return R.string.tica;
                            default:
                                switch (parseInt) {
                                    case 35:
                                        return R.string.york_sj;
                                    case 36:
                                        return R.string.kf;
                                    case 37:
                                        return R.string.qdyork;
                                    case 38:
                                        return R.string.fujitsu;
                                    default:
                                        return R.string.queshen;
                                }
                        }
                }
        }
    }

    public int[] getModeArray() {
        int i;
        int parseInt = Integer.parseInt(this.feature.substring(2, 6), 16);
        int[] iArr = {1, 8, 4, 0, 2, 0, 0, 3, 6};
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 9; i2++) {
            if (((1 << i2) & parseInt) != 0 && (i = iArr[i2]) != 0) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return listToArray(arrayList);
    }

    public int[] getWindSpeedArray() {
        int parseInt = Integer.parseInt(this.feature.substring(6, 8), 16);
        ArrayList arrayList = new ArrayList();
        int[] iArr = {1, 2, 4, 3, 5, 0};
        for (int i = 0; i < 6; i++) {
            if (((1 << i) & parseInt) != 0) {
                arrayList.add(Integer.valueOf(iArr[i]));
            }
        }
        return listToArray(arrayList);
    }

    private int[] listToArray(List<Integer> list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    public int getMaxTemprature() {
        return Integer.parseInt(this.feature.substring(8, 10), 16);
    }

    public int getMinTemprature() {
        return Integer.parseInt(this.feature.substring(10, 12), 16);
    }
}