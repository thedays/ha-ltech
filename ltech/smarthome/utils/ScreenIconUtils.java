package com.ltech.smarthome.utils;

import android.util.Base64;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ScreenIconUtils {
    public static final String LATEST_VERSION = "V1.0";
    public static final int TOTAL_ICON = 152;
    public static int[] deviceDefault = {R.mipmap.screen_device_1_def, R.mipmap.screen_device_2_def, R.mipmap.screen_device_3_def, R.mipmap.screen_device_4_def, R.mipmap.screen_device_5_def, R.mipmap.screen_device_6_def, R.mipmap.screen_device_7_def, R.mipmap.screen_device_8_def, R.mipmap.screen_device_9_def, R.mipmap.screen_device_10_def, R.mipmap.screen_device_11_def, R.mipmap.screen_device_12_def, R.mipmap.screen_device_13_def, R.mipmap.screen_device_14_def, R.mipmap.screen_device_15_def, R.mipmap.screen_device_16_def, R.mipmap.screen_device_17_def, R.mipmap.screen_device_18_def, R.mipmap.screen_device_19_def, R.mipmap.screen_device_20_def, R.mipmap.screen_device_21_def, R.mipmap.screen_device_22_def, R.mipmap.screen_device_23_def, R.mipmap.screen_device_24_def, R.mipmap.screen_device_25_def, R.mipmap.screen_device_26_def, R.mipmap.screen_device_27_def, R.mipmap.screen_device_28_def, R.mipmap.screen_device_29_def, R.mipmap.screen_device_30_def, R.mipmap.screen_device_31_def, R.mipmap.screen_device_32_def, R.mipmap.screen_device_33_def, R.mipmap.screen_device_34_def, R.mipmap.screen_device_35_def, R.mipmap.screen_device_36_def, R.mipmap.screen_device_37_def, R.mipmap.screen_device_38_def, R.mipmap.screen_device_39_def, R.mipmap.screen_device_40_def, R.mipmap.screen_device_41_def, R.mipmap.screen_device_42_def, R.mipmap.screen_device_43_def, R.mipmap.screen_device_44_def, R.mipmap.screen_device_45_def, R.mipmap.screen_device_46_def, R.mipmap.screen_device_47_def, R.mipmap.screen_device_48_def, R.mipmap.screen_device_49_def, R.mipmap.screen_device_50_def, R.mipmap.screen_device_51_def, R.mipmap.screen_device_52_def, R.mipmap.screen_device_53_def, R.mipmap.screen_device_54_def, R.mipmap.screen_device_55_def, R.mipmap.screen_device_56_def, R.mipmap.screen_device_57_def, R.mipmap.screen_device_58_def, R.mipmap.screen_device_59_def, R.mipmap.screen_device_60_def, R.mipmap.screen_device_61_def, R.mipmap.screen_device_62_def, R.mipmap.screen_device_63_def, R.mipmap.screen_device_64_def, R.mipmap.screen_device_65_def, R.mipmap.screen_device_66_def, R.mipmap.screen_device_67_def, R.mipmap.screen_device_68_def, R.mipmap.screen_device_69_def, R.mipmap.screen_device_70_def, R.mipmap.screen_device_71_def, R.mipmap.screen_device_72_def};
    public static int[] locDefault = {R.mipmap.screen_loc_1_def, R.mipmap.screen_loc_2_def, R.mipmap.screen_loc_3_def, R.mipmap.screen_loc_4_def, R.mipmap.screen_loc_5_def, R.mipmap.screen_loc_6_def, R.mipmap.screen_loc_7_def, R.mipmap.screen_loc_8_def, R.mipmap.screen_loc_9_def, R.mipmap.screen_loc_10_def, R.mipmap.screen_loc_11_def, R.mipmap.screen_loc_12_def, R.mipmap.screen_loc_13_def, R.mipmap.screen_loc_14_def, R.mipmap.screen_loc_15_def, R.mipmap.screen_loc_16_def, R.mipmap.screen_loc_17_def, R.mipmap.screen_loc_18_def, R.mipmap.screen_loc_19_def, R.mipmap.screen_loc_20_def, R.mipmap.screen_loc_21_def, R.mipmap.screen_loc_22_def, R.mipmap.screen_loc_23_def, R.mipmap.screen_loc_24_def, R.mipmap.screen_loc_25_def, R.mipmap.screen_loc_26_def, R.mipmap.screen_loc_27_def, R.mipmap.screen_loc_28_def, R.mipmap.screen_loc_29_def, R.mipmap.screen_loc_30_def, R.mipmap.screen_loc_31_def, R.mipmap.screen_loc_32_def, R.mipmap.screen_loc_33_def, R.mipmap.screen_loc_34_def, R.mipmap.screen_loc_35_def, R.mipmap.screen_loc_36_def, R.mipmap.screen_loc_37_def, R.mipmap.screen_loc_38_def, R.mipmap.screen_loc_39_def, R.mipmap.screen_loc_40_def};
    public static int[] sceneDefault = {R.mipmap.screen_scene_1_def, R.mipmap.screen_scene_2_def, R.mipmap.screen_scene_3_def, R.mipmap.screen_scene_4_def, R.mipmap.screen_scene_5_def, R.mipmap.screen_scene_6_def, R.mipmap.screen_scene_7_def, R.mipmap.screen_scene_8_def, R.mipmap.screen_scene_9_def, R.mipmap.screen_scene_10_def, R.mipmap.screen_scene_11_def, R.mipmap.screen_scene_12_def, R.mipmap.screen_scene_13_def, R.mipmap.screen_scene_14_def, R.mipmap.screen_scene_15_def, R.mipmap.screen_scene_16_def, R.mipmap.screen_scene_17_def, R.mipmap.screen_scene_18_def, R.mipmap.screen_scene_19_def, R.mipmap.screen_scene_20_def, R.mipmap.screen_scene_21_def, R.mipmap.screen_scene_22_def, R.mipmap.screen_scene_23_def, R.mipmap.screen_scene_24_def, R.mipmap.screen_scene_25_def, R.mipmap.screen_scene_26_def, R.mipmap.screen_scene_27_def, R.mipmap.screen_scene_28_def, R.mipmap.screen_scene_29_def, R.mipmap.screen_scene_30_def, R.mipmap.screen_scene_31_def, R.mipmap.screen_scene_32_def, R.mipmap.screen_scene_33_def, R.mipmap.screen_scene_34_def, R.mipmap.screen_scene_35_def, R.mipmap.screen_scene_36_def, R.mipmap.screen_scene_37_def, R.mipmap.screen_scene_38_def, R.mipmap.screen_scene_39_def, R.mipmap.screen_scene_40_def};
    public static int[] deviceSelect = {R.mipmap.screen_device_1_sel, R.mipmap.screen_device_2_sel, R.mipmap.screen_device_3_sel, R.mipmap.screen_device_4_sel, R.mipmap.screen_device_5_sel, R.mipmap.screen_device_6_sel, R.mipmap.screen_device_7_sel, R.mipmap.screen_device_8_sel, R.mipmap.screen_device_9_sel, R.mipmap.screen_device_10_sel, R.mipmap.screen_device_11_sel, R.mipmap.screen_device_12_sel, R.mipmap.screen_device_13_sel, R.mipmap.screen_device_14_sel, R.mipmap.screen_device_15_sel, R.mipmap.screen_device_16_sel, R.mipmap.screen_device_17_sel, R.mipmap.screen_device_18_sel, R.mipmap.screen_device_19_sel, R.mipmap.screen_device_20_sel, R.mipmap.screen_device_21_sel, R.mipmap.screen_device_22_sel, R.mipmap.screen_device_23_sel, R.mipmap.screen_device_24_sel, R.mipmap.screen_device_25_sel, R.mipmap.screen_device_26_sel, R.mipmap.screen_device_27_sel, R.mipmap.screen_device_28_sel, R.mipmap.screen_device_29_sel, R.mipmap.screen_device_30_sel, R.mipmap.screen_device_31_sel, R.mipmap.screen_device_32_sel, R.mipmap.screen_device_33_sel, R.mipmap.screen_device_34_sel, R.mipmap.screen_device_35_sel, R.mipmap.screen_device_36_sel, R.mipmap.screen_device_37_sel, R.mipmap.screen_device_38_sel, R.mipmap.screen_device_39_sel, R.mipmap.screen_device_40_sel, R.mipmap.screen_device_41_sel, R.mipmap.screen_device_42_sel, R.mipmap.screen_device_43_sel, R.mipmap.screen_device_44_sel, R.mipmap.screen_device_45_sel, R.mipmap.screen_device_46_sel, R.mipmap.screen_device_47_sel, R.mipmap.screen_device_48_sel, R.mipmap.screen_device_49_sel, R.mipmap.screen_device_50_sel, R.mipmap.screen_device_51_sel, R.mipmap.screen_device_52_sel, R.mipmap.screen_device_53_sel, R.mipmap.screen_device_54_sel, R.mipmap.screen_device_55_sel, R.mipmap.screen_device_56_sel, R.mipmap.screen_device_57_sel, R.mipmap.screen_device_58_sel, R.mipmap.screen_device_59_sel, R.mipmap.screen_device_60_sel, R.mipmap.screen_device_61_sel, R.mipmap.screen_device_62_sel, R.mipmap.screen_device_63_sel, R.mipmap.screen_device_64_sel, R.mipmap.screen_device_65_sel, R.mipmap.screen_device_66_sel, R.mipmap.screen_device_67_sel, R.mipmap.screen_device_68_sel, R.mipmap.screen_device_69_sel, R.mipmap.screen_device_70_sel, R.mipmap.screen_device_71_sel, R.mipmap.screen_device_72_sel};
    public static int[] locSelect = {R.mipmap.screen_loc_1_sel, R.mipmap.screen_loc_2_sel, R.mipmap.screen_loc_3_sel, R.mipmap.screen_loc_4_sel, R.mipmap.screen_loc_5_sel, R.mipmap.screen_loc_6_sel, R.mipmap.screen_loc_7_sel, R.mipmap.screen_loc_8_sel, R.mipmap.screen_loc_9_sel, R.mipmap.screen_loc_10_sel, R.mipmap.screen_loc_11_sel, R.mipmap.screen_loc_12_sel, R.mipmap.screen_loc_13_sel, R.mipmap.screen_loc_14_sel, R.mipmap.screen_loc_15_sel, R.mipmap.screen_loc_16_sel, R.mipmap.screen_loc_17_sel, R.mipmap.screen_loc_18_sel, R.mipmap.screen_loc_19_sel, R.mipmap.screen_loc_20_sel, R.mipmap.screen_loc_21_sel, R.mipmap.screen_loc_22_sel, R.mipmap.screen_loc_23_sel, R.mipmap.screen_loc_24_sel, R.mipmap.screen_loc_25_sel, R.mipmap.screen_loc_26_sel, R.mipmap.screen_loc_27_sel, R.mipmap.screen_loc_28_sel, R.mipmap.screen_loc_29_sel, R.mipmap.screen_loc_30_sel, R.mipmap.screen_loc_31_sel, R.mipmap.screen_loc_32_sel, R.mipmap.screen_loc_33_sel, R.mipmap.screen_loc_34_sel, R.mipmap.screen_loc_35_sel, R.mipmap.screen_loc_36_sel, R.mipmap.screen_loc_37_sel, R.mipmap.screen_loc_38_sel, R.mipmap.screen_loc_39_sel, R.mipmap.screen_loc_40_sel};
    public static int[] sceneSelect = {R.mipmap.screen_scene_1_sel, R.mipmap.screen_scene_2_sel, R.mipmap.screen_scene_3_sel, R.mipmap.screen_scene_4_sel, R.mipmap.screen_scene_5_sel, R.mipmap.screen_scene_6_sel, R.mipmap.screen_scene_7_sel, R.mipmap.screen_scene_8_sel, R.mipmap.screen_scene_9_sel, R.mipmap.screen_scene_10_sel, R.mipmap.screen_scene_11_sel, R.mipmap.screen_scene_12_sel, R.mipmap.screen_scene_13_sel, R.mipmap.screen_scene_14_sel, R.mipmap.screen_scene_15_sel, R.mipmap.screen_scene_16_sel, R.mipmap.screen_scene_17_sel, R.mipmap.screen_scene_18_sel, R.mipmap.screen_scene_19_sel, R.mipmap.screen_scene_20_sel, R.mipmap.screen_scene_21_sel, R.mipmap.screen_scene_22_sel, R.mipmap.screen_scene_23_sel, R.mipmap.screen_scene_24_sel, R.mipmap.screen_scene_25_sel, R.mipmap.screen_scene_26_sel, R.mipmap.screen_scene_27_sel, R.mipmap.screen_scene_28_sel, R.mipmap.screen_scene_29_sel, R.mipmap.screen_scene_30_sel, R.mipmap.screen_scene_31_sel, R.mipmap.screen_scene_32_sel, R.mipmap.screen_scene_33_sel, R.mipmap.screen_scene_34_sel, R.mipmap.screen_scene_35_sel, R.mipmap.screen_scene_36_sel, R.mipmap.screen_scene_37_sel, R.mipmap.screen_scene_38_sel, R.mipmap.screen_scene_39_sel, R.mipmap.screen_scene_40_sel};
    public static int[] deviceDisplay = {R.mipmap.screen_device_1, R.mipmap.screen_device_2, R.mipmap.screen_device_3, R.mipmap.screen_device_4, R.mipmap.screen_device_5, R.mipmap.screen_device_6, R.mipmap.screen_device_7, R.mipmap.screen_device_8, R.mipmap.screen_device_9, R.mipmap.screen_device_10, R.mipmap.screen_device_11, R.mipmap.screen_device_12, R.mipmap.screen_device_13, R.mipmap.screen_device_14, R.mipmap.screen_device_15, R.mipmap.screen_device_16, R.mipmap.screen_device_17, R.mipmap.screen_device_18, R.mipmap.screen_device_19, R.mipmap.screen_device_20, R.mipmap.screen_device_21, R.mipmap.screen_device_22, R.mipmap.screen_device_23, R.mipmap.screen_device_24, R.mipmap.screen_device_25, R.mipmap.screen_device_26, R.mipmap.screen_device_27, R.mipmap.screen_device_28, R.mipmap.screen_device_29, R.mipmap.screen_device_30, R.mipmap.screen_device_31, R.mipmap.screen_device_32, R.mipmap.screen_device_33, R.mipmap.screen_device_34, R.mipmap.screen_device_35, R.mipmap.screen_device_36, R.mipmap.screen_device_37, R.mipmap.screen_device_38, R.mipmap.screen_device_39, R.mipmap.screen_device_40, R.mipmap.screen_device_41, R.mipmap.screen_device_42, R.mipmap.screen_device_43, R.mipmap.screen_device_44, R.mipmap.screen_device_45, R.mipmap.screen_device_46, R.mipmap.screen_device_47, R.mipmap.screen_device_48, R.mipmap.screen_device_49, R.mipmap.screen_device_50, R.mipmap.screen_device_51, R.mipmap.screen_device_52, R.mipmap.screen_device_53, R.mipmap.screen_device_54, R.mipmap.screen_device_55, R.mipmap.screen_device_56, R.mipmap.screen_device_57, R.mipmap.screen_device_58, R.mipmap.screen_device_59, R.mipmap.screen_device_60, R.mipmap.screen_device_61, R.mipmap.screen_device_62, R.mipmap.screen_device_63, R.mipmap.screen_device_64, R.mipmap.screen_device_65, R.mipmap.screen_device_66, R.mipmap.screen_device_67, R.mipmap.screen_device_68, R.mipmap.screen_device_69, R.mipmap.screen_device_70, R.mipmap.screen_device_71, R.mipmap.screen_device_72};
    public static int[] locDisplay = {R.mipmap.screen_loc_1, R.mipmap.screen_loc_2, R.mipmap.screen_loc_3, R.mipmap.screen_loc_4, R.mipmap.screen_loc_5, R.mipmap.screen_loc_6, R.mipmap.screen_loc_7, R.mipmap.screen_loc_8, R.mipmap.screen_loc_9, R.mipmap.screen_loc_10, R.mipmap.screen_loc_11, R.mipmap.screen_loc_12, R.mipmap.screen_loc_13, R.mipmap.screen_loc_14, R.mipmap.screen_loc_15, R.mipmap.screen_loc_16, R.mipmap.screen_loc_17, R.mipmap.screen_loc_18, R.mipmap.screen_loc_19, R.mipmap.screen_loc_20, R.mipmap.screen_loc_21, R.mipmap.screen_loc_22, R.mipmap.screen_loc_23, R.mipmap.screen_loc_24, R.mipmap.screen_loc_25, R.mipmap.screen_loc_26, R.mipmap.screen_loc_27, R.mipmap.screen_loc_28, R.mipmap.screen_loc_29, R.mipmap.screen_loc_30, R.mipmap.screen_loc_31, R.mipmap.screen_loc_32, R.mipmap.screen_loc_33, R.mipmap.screen_loc_34, R.mipmap.screen_loc_35, R.mipmap.screen_loc_36, R.mipmap.screen_loc_37, R.mipmap.screen_loc_38, R.mipmap.screen_loc_39, R.mipmap.screen_loc_40};
    public static int[] sceneDisplay = {R.mipmap.screen_scene_1, R.mipmap.screen_scene_2, R.mipmap.screen_scene_3, R.mipmap.screen_scene_4, R.mipmap.screen_scene_5, R.mipmap.screen_scene_6, R.mipmap.screen_scene_7, R.mipmap.screen_scene_8, R.mipmap.screen_scene_9, R.mipmap.screen_scene_10, R.mipmap.screen_scene_11, R.mipmap.screen_scene_12, R.mipmap.screen_scene_13, R.mipmap.screen_scene_14, R.mipmap.screen_scene_15, R.mipmap.screen_scene_16, R.mipmap.screen_scene_17, R.mipmap.screen_scene_18, R.mipmap.screen_scene_19, R.mipmap.screen_scene_20, R.mipmap.screen_scene_21, R.mipmap.screen_scene_22, R.mipmap.screen_scene_23, R.mipmap.screen_scene_24, R.mipmap.screen_scene_25, R.mipmap.screen_scene_26, R.mipmap.screen_scene_27, R.mipmap.screen_scene_28, R.mipmap.screen_scene_29, R.mipmap.screen_scene_30, R.mipmap.screen_scene_31, R.mipmap.screen_scene_32, R.mipmap.screen_scene_33, R.mipmap.screen_scene_34, R.mipmap.screen_scene_35, R.mipmap.screen_scene_36, R.mipmap.screen_scene_37, R.mipmap.screen_scene_38, R.mipmap.screen_scene_39, R.mipmap.screen_scene_40};
    public static final int[][] VERSION_NUM_ARRAY = {new int[]{0, 0, 0, 0}, new int[]{40, 40, 72, 152}};

    public static int getVersionNum(int start) {
        int i = 1;
        while (true) {
            int[][] iArr = VERSION_NUM_ARRAY;
            if (i < iArr.length) {
                if (start < iArr[i][3]) {
                    return i - 1;
                }
                i++;
            } else {
                return iArr.length - 1;
            }
        }
    }

    public static int getScreenIconRes(int iconIndex) {
        int i = iconIndex / 1000;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i == 1) {
            return sceneDisplay[iconIndex % 1000];
        }
        if (i == 2) {
            return locDisplay[iconIndex % 1000];
        }
        if (i == 3) {
            return deviceDisplay[iconIndex % 1000];
        }
        return 0;
    }

    public static int getScreenDefaultIconRes(int iconIndex) {
        int i = iconIndex / 1000;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i == 1) {
            return sceneDefault[iconIndex % 1000];
        }
        if (i == 2) {
            return locDefault[iconIndex % 1000];
        }
        if (i == 3) {
            return deviceDefault[iconIndex % 1000];
        }
        return 0;
    }

    public static int getSendIconIndex(int iconIndex) {
        int i;
        int i2;
        int i3 = iconIndex / 1000;
        int i4 = iconIndex % 1000;
        int i5 = 1;
        while (true) {
            int[][] iArr = VERSION_NUM_ARRAY;
            if (i5 >= iArr.length) {
                return 0;
            }
            int[] iArr2 = iArr[i5];
            if (i4 < iArr2[i3 - 1]) {
                int[] iArr3 = iArr[i5 - 1];
                int i6 = iArr3[3];
                if (i3 == 1) {
                    i = i6 + i4;
                    i2 = iArr3[0];
                } else if (i3 == 2) {
                    i = i6 + (iArr2[0] - iArr3[0]) + i4;
                    i2 = iArr3[1];
                } else {
                    i = i6 + (iArr2[0] - iArr3[0]) + (iArr2[1] - iArr3[1]) + i4;
                    i2 = iArr3[2];
                }
                return i - i2;
            }
            i5++;
        }
    }

    public static byte[] getSmallIconData(int iconIndex) {
        if (iconIndex < ScreenIconArray.SMALL_ICON_DATA.length) {
            return Base64.decode(ScreenIconArray.SMALL_ICON_DATA[iconIndex].getBytes(), 0);
        }
        return null;
    }

    public static byte[] getBigIconData(int iconIndex) {
        if (iconIndex < ScreenIconArray.BIG_ICON_DATA.length) {
            return Base64.decode(ScreenIconArray.BIG_ICON_DATA[iconIndex].getBytes(), 0);
        }
        return null;
    }

    public static byte[] getG4SmallIconData(int iconIndex) {
        if (iconIndex < ScreenIconArray.SMALL_G4_ICON_DATA.length) {
            return Base64.decode(ScreenIconArray.SMALL_G4_ICON_DATA[iconIndex].getBytes(), 0);
        }
        return null;
    }

    public static byte[] getG4BigIconData(int iconIndex) {
        if (iconIndex < ScreenIconArray.BIG_G4_ICON_DATA.length) {
            return Base64.decode(ScreenIconArray.BIG_G4_ICON_DATA[iconIndex].getBytes(), 0);
        }
        return null;
    }

    public static int getG4IconLength() {
        return ScreenIconArray.BIG_G4_ICON_DATA.length;
    }
}