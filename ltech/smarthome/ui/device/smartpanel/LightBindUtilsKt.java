package com.ltech.smarthome.ui.device.smartpanel;

import com.ltech.smarthome.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* compiled from: LightBindUtils.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0013\u001a\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b\u001a\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\b\u001a\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00020\b\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0004\"\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0004\"\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0004\"\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0004\"\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0004\"\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0004\"\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0004\"\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0004\"\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0004¨\u0006\u001b"}, d2 = {"dimList", "Ljava/util/ArrayList;", "Lcom/ltech/smarthome/ui/device/smartpanel/BindValue;", "getDimList", "()Ljava/util/ArrayList;", "ctList", "getCtList", "getB5CtList", "", "rgbList", "getRgbList", "rgbwList", "getRgbwList", "rgbcwList", "getRgbcwList", "rgbcwCcList", "getRgbcwCcList", "switchList", "getSwitchList", "getSpiList", "dimCtList", "getDimCtList", "dimRgbList", "getDimRgbList", "ctRgbList", "getCtRgbList", "getDimCtRgbList", "app_yingyongbaoRelease"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class LightBindUtilsKt {
    private static final ArrayList<BindValue> dimList = CollectionsKt.arrayListOf(new BindValue(16, R.string.theme, 1), new BindValue(17, R.string.advanced_mode, 1), new BindValue(19, R.string.dim_new_action_1, 1), new BindValue(20, R.string.dim_new_action_2, 1), new BindValue(4, R.string.dim_new_action_3, 1), new BindValue(5, R.string.dim_new_action_4, 1), new BindValue(6, R.string.dim_new_action_5, 1), new BindValue(1, R.string.dim_new_action_6, 1), new BindValue(7, R.string.dim_new_action_7, 1), new BindValue(21, R.string.dim_new_action_8, 1), new BindValue(2, R.string.dim_new_action_9, 1), new BindValue(3, R.string.dim_new_action_10, 1), new BindValue(8, R.string.dim_new_action_11, 1), new BindValue(9, R.string.dim_new_action_12, 1));
    private static final ArrayList<BindValue> ctList = CollectionsKt.arrayListOf(new BindValue(16, R.string.theme, 2), new BindValue(17, R.string.advanced_mode, 2), new BindValue(19, R.string.ct_new_action_1, 2), new BindValue(20, R.string.ct_new_action_2, 2), new BindValue(4, R.string.ct_new_action_3, 2), new BindValue(5, R.string.ct_new_action_4, 2), new BindValue(6, R.string.ct_new_action_5, 2), new BindValue(1, R.string.ct_new_action_6, 2), new BindValue(7, R.string.ct_new_action_7, 2), new BindValue(10, R.string.ct_new_action_8, 2), new BindValue(13, R.string.ct_new_action_9, 2), new BindValue(21, R.string.ct_new_action_10, 2), new BindValue(2, R.string.ct_new_action_11, 2), new BindValue(3, R.string.ct_new_action_12, 2), new BindValue(11, R.string.ct_new_action_13, 2), new BindValue(12, R.string.ct_new_action_14, 2), new BindValue(8, R.string.ct_new_action_15, 2), new BindValue(9, R.string.ct_new_action_16, 2), new BindValue(14, R.string.ct_new_action_17, 2), new BindValue(15, R.string.ct_new_action_18, 2));
    private static final ArrayList<BindValue> rgbList = CollectionsKt.arrayListOf(new BindValue(16, R.string.theme, 3), new BindValue(18, R.string.general_mode, 3), new BindValue(17, R.string.advanced_mode, 3), new BindValue(19, R.string.rgb_new_action_1, 3), new BindValue(20, R.string.rgb_new_action_2, 3), new BindValue(4, R.string.rgb_new_action_3, 3), new BindValue(5, R.string.rgb_new_action_4, 3), new BindValue(6, R.string.rgb_new_action_5, 3), new BindValue(1, R.string.rgb_new_action_6, 3), new BindValue(7, R.string.rgb_new_action_7, 3), new BindValue(10, R.string.rgb_new_action_8, 3), new BindValue(13, R.string.rgb_new_action_9, 3), new BindValue(21, R.string.rgb_new_action_10, 3), new BindValue(26, R.string.rgb_new_action_11, 3), new BindValue(2, R.string.rgb_new_action_12, 3), new BindValue(3, R.string.rgb_new_action_13, 3), new BindValue(11, R.string.rgb_new_action_14, 3), new BindValue(12, R.string.rgb_new_action_15, 3), new BindValue(8, R.string.rgb_new_action_16, 3), new BindValue(9, R.string.rgb_new_action_17, 3), new BindValue(14, R.string.rgb_new_action_18, 3), new BindValue(15, R.string.rgb_new_action_19, 3));
    private static final ArrayList<BindValue> rgbwList = CollectionsKt.arrayListOf(new BindValue(16, R.string.theme), new BindValue(18, R.string.general_mode), new BindValue(17, R.string.advanced_mode), new BindValue(19, R.string.rgb_new_action_1), new BindValue(20, R.string.rgb_new_action_2), new BindValue(4, R.string.rgb_new_action_3), new BindValue(5, R.string.rgb_new_action_4), new BindValue(6, R.string.rgb_new_action_5), new BindValue(1, R.string.rgbw_new_action_8), new BindValue(7, R.string.rgbw_new_action_9), new BindValue(23, R.string.rgbw_new_action_10), new BindValue(25, R.string.rgbw_new_action_11), new BindValue(26, R.string.rgbw_new_action_13), new BindValue(2, R.string.rgbw_new_action_18), new BindValue(3, R.string.rgbw_new_action_19), new BindValue(11, R.string.rgbw_new_action_20), new BindValue(12, R.string.rgbw_new_action_21), new BindValue(10, R.string.rgbw_new_action_22), new BindValue(13, R.string.rgbw_new_action_23));
    private static final ArrayList<BindValue> rgbcwList = CollectionsKt.arrayListOf(new BindValue(16, R.string.theme), new BindValue(18, R.string.general_mode), new BindValue(17, R.string.advanced_mode), new BindValue(19, R.string.rgb_new_action_1), new BindValue(20, R.string.rgb_new_action_2), new BindValue(4, R.string.rgb_new_action_3), new BindValue(5, R.string.rgb_new_action_4), new BindValue(6, R.string.rgb_new_action_5), new BindValue(1, R.string.rgbw_new_action_8), new BindValue(7, R.string.rgbw_new_action_9), new BindValue(23, R.string.rgbcw_new_action_10), new BindValue(25, R.string.rgbcw_new_action_11), new BindValue(26, R.string.rgbcw_new_action_13), new BindValue(27, R.string.rgbcw_new_action_14), new BindValue(10, R.string.rgbcw_new_action_19), new BindValue(30, R.string.rgbcw_new_action_20), new BindValue(13, R.string.rgbcw_new_action_21), new BindValue(31, R.string.rgbcw_new_action_22), new BindValue(2, R.string.rgbcw_new_action_23), new BindValue(3, R.string.rgbcw_new_action_24), new BindValue(11, R.string.rgbcw_new_action_25), new BindValue(12, R.string.rgbcw_new_action_26));
    private static final ArrayList<BindValue> rgbcwCcList = CollectionsKt.arrayListOf(new BindValue(16, R.string.theme), new BindValue(18, R.string.general_mode), new BindValue(17, R.string.advanced_mode), new BindValue(4, R.string.rgb_new_action_3), new BindValue(5, R.string.rgb_new_action_4), new BindValue(6, R.string.rgb_new_action_5), new BindValue(1, R.string.rgbw_new_action_8), new BindValue(7, R.string.rgbw_new_action_9), new BindValue(23, R.string.rgbcw_new_action_10), new BindValue(25, R.string.rgbcw_new_action_11), new BindValue(26, R.string.rgbcw_new_action_13), new BindValue(27, R.string.rgbcw_new_action_14), new BindValue(10, R.string.rgbcw_new_action_19), new BindValue(30, R.string.rgbcw_new_action_20), new BindValue(13, R.string.rgbcw_new_action_21), new BindValue(31, R.string.rgbcw_new_action_22), new BindValue(2, R.string.rgbcw_new_action_23), new BindValue(3, R.string.rgbcw_new_action_24), new BindValue(11, R.string.rgbcw_new_action_25), new BindValue(12, R.string.rgbcw_new_action_26));
    private static final ArrayList<BindValue> switchList = CollectionsKt.arrayListOf(new BindValue(4, R.string.key_switch_action_4), new BindValue(5, R.string.key_switch_action_5), new BindValue(6, R.string.key_switch_action_6));
    private static final ArrayList<BindValue> dimCtList = CollectionsKt.arrayListOf(new BindValue(34, R.string.dim_new_action_1, 1), new BindValue(34, R.string.ct_new_action_1, 2), new BindValue(35, R.string.dim_new_action_2, 1), new BindValue(35, R.string.ct_new_action_2, 2), new BindValue(4, R.string.ct_new_action_3, 1), new BindValue(5, R.string.ct_new_action_4, 1), new BindValue(6, R.string.ct_new_action_5, 1), new BindValue(1, R.string.ct_new_action_6, 1), new BindValue(7, R.string.ct_new_action_7, 1), new BindValue(10, R.string.ct_new_action_8, 2), new BindValue(13, R.string.ct_new_action_9, 2), new BindValue(21, R.string.ct_new_action_10, 1), new BindValue(2, R.string.ct_new_action_11, 1), new BindValue(3, R.string.ct_new_action_12, 1), new BindValue(11, R.string.ct_new_action_13, 2), new BindValue(12, R.string.ct_new_action_14, 2), new BindValue(8, R.string.ct_new_action_15, 1), new BindValue(9, R.string.ct_new_action_16, 1), new BindValue(14, R.string.ct_new_action_17, 2), new BindValue(15, R.string.ct_new_action_18, 2));
    private static final ArrayList<BindValue> dimRgbList = CollectionsKt.arrayListOf(new BindValue(34, R.string.dim_new_action_1, 1), new BindValue(34, R.string.rgb_new_action_1, 3), new BindValue(35, R.string.dim_new_action_2, 1), new BindValue(35, R.string.rgb_new_action_2, 3), new BindValue(4, R.string.rgb_new_action_3, 1), new BindValue(5, R.string.rgb_new_action_4, 1), new BindValue(6, R.string.rgb_new_action_5, 1), new BindValue(1, R.string.rgb_new_action_6, 1), new BindValue(7, R.string.rgb_new_action_7, 1), new BindValue(10, R.string.rgb_new_action_8, 3), new BindValue(13, R.string.rgb_new_action_9, 3), new BindValue(21, R.string.rgb_new_action_10, 3), new BindValue(26, R.string.rgb_new_action_11, 3), new BindValue(2, R.string.rgb_new_action_12, 1), new BindValue(3, R.string.rgb_new_action_13, 1), new BindValue(11, R.string.rgb_new_action_14, 3), new BindValue(12, R.string.rgb_new_action_15, 3), new BindValue(8, R.string.rgb_new_action_16, 1), new BindValue(9, R.string.rgb_new_action_17, 1), new BindValue(14, R.string.rgb_new_action_18, 3), new BindValue(15, R.string.rgb_new_action_19, 3));
    private static final ArrayList<BindValue> ctRgbList = CollectionsKt.arrayListOf(new BindValue(34, R.string.ct_new_action_1, 2), new BindValue(34, R.string.rgb_new_action_1, 3), new BindValue(35, R.string.ct_new_action_2, 2), new BindValue(35, R.string.rgb_new_action_2, 3), new BindValue(4, R.string.rgb_new_action_3, 1), new BindValue(5, R.string.rgb_new_action_4, 1), new BindValue(6, R.string.rgb_new_action_5, 1), new BindValue(1, R.string.rgb_new_action_6, 1), new BindValue(7, R.string.rgb_new_action_7, 1), new BindValue(10, R.string.ct_new_action_8, 2), new BindValue(10, R.string.rgb_new_action_8, 3), new BindValue(13, R.string.ct_new_action_9, 2), new BindValue(13, R.string.rgb_new_action_9, 3), new BindValue(21, R.string.rgb_new_action_10, 1), new BindValue(26, R.string.rgb_new_action_11, 3), new BindValue(2, R.string.rgb_new_action_12, 1), new BindValue(3, R.string.rgb_new_action_13, 1), new BindValue(11, R.string.ct_new_action_13, 2), new BindValue(8, R.string.ct_new_action_14, 2), new BindValue(11, R.string.rgb_new_action_14, 3), new BindValue(12, R.string.rgb_new_action_15, 3), new BindValue(8, R.string.rgb_new_action_16, 1), new BindValue(9, R.string.rgb_new_action_17, 1), new BindValue(14, R.string.ct_new_action_17, 2), new BindValue(15, R.string.ct_new_action_18, 2), new BindValue(14, R.string.rgb_new_action_18, 3), new BindValue(15, R.string.rgb_new_action_19, 3));

    public static final ArrayList<BindValue> getDimList() {
        return dimList;
    }

    public static final ArrayList<BindValue> getCtList() {
        return ctList;
    }

    public static final List<BindValue> getB5CtList() {
        List<BindValue> mutableList = CollectionsKt.toMutableList((Collection) ctList);
        mutableList.add(1, new BindValue(18, R.string.general_mode, 2));
        return mutableList;
    }

    public static final ArrayList<BindValue> getRgbList() {
        return rgbList;
    }

    public static final ArrayList<BindValue> getRgbwList() {
        return rgbwList;
    }

    public static final ArrayList<BindValue> getRgbcwList() {
        return rgbcwList;
    }

    public static final ArrayList<BindValue> getRgbcwCcList() {
        return rgbcwCcList;
    }

    public static final ArrayList<BindValue> getSwitchList() {
        return switchList;
    }

    public static final List<BindValue> getSpiList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BindValue(256, R.string.spi_mode_and_list_action));
        ArrayList<BindValue> arrayList2 = rgbList;
        arrayList.addAll(arrayList2.subList(3, arrayList2.size()));
        return arrayList;
    }

    public static final ArrayList<BindValue> getDimCtList() {
        return dimCtList;
    }

    public static final ArrayList<BindValue> getDimRgbList() {
        return dimRgbList;
    }

    public static final ArrayList<BindValue> getCtRgbList() {
        return ctRgbList;
    }

    public static final List<BindValue> getDimCtRgbList() {
        List<BindValue> mutableList = CollectionsKt.toMutableList((Collection) ctRgbList);
        mutableList.add(0, new BindValue(34, R.string.dim_new_action_1, 1));
        mutableList.add(3, new BindValue(35, R.string.dim_new_action_2, 1));
        return mutableList;
    }
}