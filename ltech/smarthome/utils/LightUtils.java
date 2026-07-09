package com.ltech.smarthome.utils;

import android.graphics.Color;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.repo.ProductRepository;

/* loaded from: classes4.dex */
public class LightUtils {
    public static final int BRIGHTNESS_MAX = 255;
    public static final int BRT_PROGRESS_MAX = 100;
    public static final int COLOR_MAX = 255;
    public static final int DEFAULT_BRT_PROGRESS = 25;
    public static final int DEFAULT_BRT_PROGRESS_MAX110 = 35;
    public static final int PROGRESS_MAX = 100;
    public static final int PROGRESS_MAX_110 = 110;

    public static float brt2Percent(int brt) {
        return ((brt * 100) * 1.0f) / 255.0f;
    }

    public static float c2percent(int c2) {
        return c2 / 255.0f;
    }

    public static int progress2Brt(float progress) {
        return Math.round(((progress * 255.0f) * 1.0f) / 100.0f);
    }

    public static int brt2Progress(int brt) {
        return Math.round(brt2Percent(brt));
    }

    public static int percent2C(float percent) {
        return Math.round(percent * 255.0f);
    }

    public static int getHsvColor(int red, int green, int blue, int w1) {
        if (255 == red && 255 == green && 255 == blue) {
            return -1;
        }
        float[] fArr = {0.0f, (((255 - w1) * 0.95f) / 255.0f) + 0.05f, 0.0f};
        Color.RGBToHSV(red, green, blue, fArr);
        return Color.HSVToColor(fArr);
    }

    public static int getInvertedColor(int color) {
        return Color.rgb(255 - Color.red(color), 255 - Color.green(color), 255 - Color.blue(color));
    }

    public static int progress2BrtHasBelowOne(int progress) {
        if (progress < 10) {
            if (progress < 1) {
                return 1;
            }
            return progress;
        }
        int round = Math.round(((progress - 10) / 90.0f) * 245.0f) + 10;
        if (round > 255) {
            return 255;
        }
        return round;
    }

    public static int progress2BrtIncludeZero(int progress) {
        if (progress < 10) {
            return progress;
        }
        int round = Math.round(((progress - 10) / 90.0f) * 245.0f) + 10;
        if (round > 255) {
            return 255;
        }
        return round;
    }

    public static int brt2ProgressHasBelowZero(int brt) {
        return brt < 10 ? brt : Math.round(((brt - 10) / 245.0f) * 90.0f) + 10;
    }

    public static int brt2PercentHasBelowZero(int brt) {
        return brt < 10 ? brt : Math.round(((brt - 10) / 245.0f) * 90.0f) + 10;
    }

    public static int progressNormal2progressMax(int progress) {
        return Math.round(((progress * 100) * 1.0f) / 100.0f);
    }

    public static int progressMax2progressNormal(int progress) {
        return Math.round(((progress * 100) * 1.0f) / 100.0f);
    }

    public static String getProgressHasBelowOne(int progress) {
        return Math.min(progress, 100) + "%";
    }

    public static String getProgressHasBelowOneWithoutPercent(int progress) {
        return Math.min(progress, 100) + "";
    }

    public static int getStepK(int k, int step) {
        return k - (k % step);
    }

    public static String getKValue(int progress, Object object) {
        int minkelvin;
        int maxkelvin;
        int i = 6500;
        int i2 = 2700;
        if (object instanceof Group) {
            Group group = (Group) object;
            if (group.getMaxkelvin() != 0 || ProductRepository.isDaliLightGroup(group)) {
                i = 10000;
                i2 = 1000;
            }
            minkelvin = group.getMinkelvin();
            maxkelvin = group.getMaxkelvin();
        } else {
            Device device = (Device) object;
            if (device.getMaxkelvin() != 0 || ProductRepository.isDaliLightGroup(device) || ProductRepository.isCgdPro(device)) {
                i = 10000;
                i2 = 1000;
            }
            minkelvin = device.getMinkelvin();
            maxkelvin = device.getMaxkelvin();
        }
        int ctY2K = ctY2K(progress, i, i2);
        if (minkelvin != 0 && maxkelvin != 0) {
            ctY2K = Math.min(Math.max(ctY2K, minkelvin), maxkelvin);
        }
        return ctY2K + "K";
    }

    public static int ctK2Y(int k, int kMax, int kMin) {
        return Math.round(255.0f - (((k - kMin) / (kMax - kMin)) * 255.0f));
    }

    public static int ctY2K(int progress, int kMax, int kMin) {
        int ceil = (int) Math.ceil(kMax - ((progress / 255.0d) * (kMax - kMin)));
        return ((ceil / 50) + (ceil % 50 > 24 ? 1 : 0)) * 50;
    }

    public static int ctY2K(int progress) {
        return ctY2K(progress, 10000, 1000);
    }
}