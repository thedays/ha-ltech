package com.ltech.smarthome.ui.device.light;

import android.content.Context;
import android.graphics.Color;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.utils.LightUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PowerState {

    /* renamed from: b, reason: collision with root package name */
    public int f6272b;
    public int brt;

    /* renamed from: c, reason: collision with root package name */
    public int f6273c;
    public int cwOn;
    public int g;
    public int r;
    public int rgbOn;
    public int state;
    public boolean supportAll;
    public int w;
    public int wyBrt;

    public PowerState() {
        this.state = 1;
        this.brt = 255;
        this.wyBrt = 255;
        this.r = 255;
        this.g = 255;
        this.f6272b = 255;
        this.f6273c = 128;
        this.w = 127;
        this.rgbOn = 1;
        this.cwOn = 1;
    }

    public PowerState(String resData) {
        this(DataUtil.hexStringToInts(resData));
    }

    public PowerState(int[] array) {
        this(array, false);
    }

    public PowerState(int[] array, boolean supportAll) {
        this.state = 1;
        this.brt = 255;
        this.wyBrt = 255;
        this.r = 255;
        this.g = 255;
        this.f6272b = 255;
        this.f6273c = 128;
        this.w = 127;
        this.rgbOn = 1;
        this.cwOn = 1;
        this.state = array[0];
        this.supportAll = supportAll;
        if (array.length >= 3) {
            this.brt = array[1];
            this.wyBrt = array[2];
        }
        if (array.length >= 10) {
            this.supportAll = true;
            this.r = array[3];
            this.g = array[4];
            this.f6272b = array[5];
            this.f6273c = array[6];
            this.w = array[7];
            this.rgbOn = array[8];
            this.cwOn = array[9];
        }
    }

    public List<Integer> getStateValues() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(this.brt));
        arrayList.add(Integer.valueOf(this.wyBrt));
        if (this.supportAll) {
            arrayList.add(Integer.valueOf(this.r));
            arrayList.add(Integer.valueOf(this.g));
            arrayList.add(Integer.valueOf(this.f6272b));
            arrayList.add(Integer.valueOf(this.f6273c));
            arrayList.add(Integer.valueOf(this.w));
            arrayList.add(Integer.valueOf(this.rgbOn));
            arrayList.add(Integer.valueOf(this.cwOn));
        }
        return arrayList;
    }

    public void setColor(int color) {
        this.r = Color.red(color);
        this.g = Color.green(color);
        this.f6272b = Color.blue(color);
    }

    public int getColor() {
        return Color.rgb(this.r, this.g, this.f6272b);
    }

    public String getStateContent(Context context, Object object) {
        String str;
        String str2;
        if (this.supportAll) {
            if (object != null) {
                int lightColorType = ProductRepository.getLightColorType(object);
                if (lightColorType == 2) {
                    return LightUtils.getKValue(this.w, object) + " " + LightUtils.brt2PercentHasBelowZero(this.brt) + "%" + context.getString(R.string.brt);
                }
                if (lightColorType == 3) {
                    return String.format("#%06X", Integer.valueOf(16777215 & getColor())) + LightUtils.brt2PercentHasBelowZero(this.brt) + "%" + context.getString(R.string.brt);
                }
                String str3 = "";
                if (lightColorType == 4) {
                    StringBuilder sb = new StringBuilder();
                    if (this.rgbOn != 1) {
                        str2 = "";
                    } else {
                        str2 = String.format("#%06X", Integer.valueOf(16777215 & getColor())) + LightUtils.brt2PercentHasBelowZero(this.brt) + "%" + context.getString(R.string.brt);
                    }
                    sb.append(str2);
                    sb.append(" ");
                    if (this.cwOn == 1) {
                        str3 = "W:" + LightUtils.brt2PercentHasBelowZero(this.wyBrt) + "%";
                    }
                    sb.append(str3);
                    return sb.toString();
                }
                if (lightColorType == 5 || lightColorType == 20) {
                    StringBuilder sb2 = new StringBuilder();
                    if (this.rgbOn != 1) {
                        str = "";
                    } else {
                        str = String.format("#%06X", Integer.valueOf(16777215 & getColor())) + LightUtils.brt2PercentHasBelowZero(this.brt) + "%" + context.getString(R.string.brt);
                    }
                    sb2.append(str);
                    sb2.append(" ");
                    if (this.cwOn == 1) {
                        str3 = context.getString(R.string.ct) + Constants.COLON_SEPARATOR + this.w + " " + LightUtils.brt2PercentHasBelowZero(this.wyBrt) + "%";
                    }
                    sb2.append(str3);
                    return sb2.toString();
                }
            } else {
                return String.format("#%06X", Integer.valueOf(16777215 & getColor())) + LightUtils.ctY2K(this.w) + "K " + LightUtils.brt2PercentHasBelowZero(this.brt) + "%" + context.getString(R.string.brt);
            }
        }
        return LightUtils.brt2PercentHasBelowZero(this.brt) + "%" + context.getString(R.string.brt);
    }
}