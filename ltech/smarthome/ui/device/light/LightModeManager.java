package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.text.TextUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.preference_bean.DiyModeList;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.product_agreement.bean.ItemMode;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class LightModeManager {
    private static final String COLOR_LIGHT_DIY_MODE = "color_light_diy_mode";
    private static final String CT_LIGHT_DIY_MODE = "ct_light_diy_mode";
    private static final String DIM_LIGHT_DIY_MODE = "dim_light_diy_mode";

    public static List<ItemMode> getColorDefaultModeList(Context context) {
        List<ItemMode> modeList = getModeList(context, R.array.color_light_default_mode_name, R.array.color_light_default_mode_icon);
        String[] stringArray = context.getResources().getStringArray(R.array.color_light_default_mode_content);
        int size = modeList.size();
        for (int i = 0; i < size; i++) {
            modeList.get(i).content = stringArray[i];
        }
        return modeList;
    }

    private static List<ItemMode> getModeList(Context context, int nameArrayRes, int iconArrayRes) {
        ArrayList arrayList = new ArrayList();
        int[] drawableResourceArray = HelpUtils.getDrawableResourceArray(context, iconArrayRes);
        String[] stringArray = context.getResources().getStringArray(nameArrayRes);
        for (int i = 0; i < stringArray.length; i++) {
            arrayList.add(new ItemMode(stringArray[i], drawableResourceArray[i], i));
        }
        return arrayList;
    }

    public static String[] getGeneralModeDefaultName(Context context) {
        return context.getResources().getStringArray(R.array.color_light_diy_mode_name);
    }

    public static int[] getGeneralModeDefaultIcon(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.color_light_diy_mode_icon);
    }

    public static List<ItemMode> getColorDiyModeList(Context context, long deviceId) {
        return getDiyModeList(context, deviceId, COLOR_LIGHT_DIY_MODE, R.array.color_light_diy_mode_name, R.array.color_light_diy_mode_icon);
    }

    private static List<ItemMode> getDiyModeList(Context context, long deviceId, String key, int nameArrayRes, int iconArrayRes) {
        int[] drawableResourceArray = HelpUtils.getDrawableResourceArray(context, iconArrayRes);
        String[] stringArray = context.getResources().getStringArray(nameArrayRes);
        ArrayList arrayList = new ArrayList();
        DiyModeList diyModeList = (DiyModeList) SharedPreferenceUtil.getBean(key + deviceId, DiyModeList.class);
        if (diyModeList != null) {
            for (int i = 0; i < diyModeList.list.size(); i++) {
                arrayList.add(new ItemMode(TextUtils.isEmpty(diyModeList.list.get(i).name) ? stringArray[i] : diyModeList.list.get(i).name, drawableResourceArray[diyModeList.list.get(i).resPos], diyModeList.list.get(i).resPos));
            }
        }
        if (arrayList.isEmpty()) {
            for (int i2 = 0; i2 < stringArray.length; i2++) {
                arrayList.add(new ItemMode(stringArray[i2], drawableResourceArray[i2], i2));
            }
        }
        return arrayList;
    }

    private static void saveDiyModeList(List<ItemMode> list, long deviceId, String key) {
        if (list != null) {
            DiyModeList diyModeList = (DiyModeList) SharedPreferenceUtil.getBean(key + deviceId, DiyModeList.class);
            if (diyModeList == null) {
                diyModeList = new DiyModeList();
            }
            diyModeList.list = list;
            SharedPreferenceUtil.edit().putBean(key + deviceId, diyModeList).commit();
        }
    }
}