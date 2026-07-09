package com.ltech.smarthome.utils;

import android.content.Context;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;

/* loaded from: classes4.dex */
public class IconRepository {
    public static int[] getLightIconSelect(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.light_icon_select);
    }

    public static int[] getLightIconValue(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.light_icon_value);
    }

    public static int[] getSceneIcons(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.scene_icon);
    }

    public static int[] getModePic(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.mode_cover);
    }

    public static int[] getDefaultModePic(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.rgb_light_default_mode_icon);
    }

    public static int[] getDimDefaultModePic(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.dim_light_default_theme_mode_icon);
    }

    public static int[] getCtDefaultModePic(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.ct_light_default_theme_mode_icon);
    }

    public static int[] getAutomationPic(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.color_light_default_mode_icon);
    }

    public static int[] getRs485DevicePic(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.rs485_device_icon);
    }

    public static int getIconRes(Device item) {
        if (item.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT)) {
            if (item.getExtParam() != null) {
                TrigExtParam trigExtParam = new TrigExtParam();
                trigExtParam.fillMapWithString(item.getExtParam());
                return trigExtParam.getCurtainType() == 0 ? R.mipmap.ic_device_curtain : trigExtParam.getCurtainType() == 1 ? R.mipmap.trig_icon_cur : trigExtParam.getCurtainType() == 2 ? R.mipmap.device_icon_curtain_single : R.mipmap.ic_device_curtain;
            }
            if (item.getParam(DryContactBleParam.class) != null) {
                return ((DryContactBleParam) item.getParam(DryContactBleParam.class)).getSubType() == 0 ? R.mipmap.ic_device_curtain : ProductRepository.getProductIcon(item);
            }
            return ProductRepository.getProductIcon(item);
        }
        return ProductRepository.getProductIcon(item);
    }

    public static int[] getLightGroupIconSelect(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.light_group_icon_select);
    }

    public static int[] getLightGroupIconValue(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.light_group_icon);
    }

    public static int[] getSpiModePic(Context context) {
        return HelpUtils.getDrawableResourceArray(context, R.array.spi_mode_pic_array);
    }
}