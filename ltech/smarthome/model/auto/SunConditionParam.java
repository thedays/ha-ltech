package com.ltech.smarthome.model.auto;

import android.content.Context;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class SunConditionParam {
    public String city;
    public int type;
    public double x;
    public double y;

    public String getStateString(Context context) {
        int i = this.type;
        if (i == 1) {
            return context.getString(R.string.sunrise);
        }
        if (i == 2) {
            return context.getString(R.string.sunset);
        }
        return "";
    }

    public static final class SunState {
        public static final int SUNRISE = 1;
        public static final int SUNSET = 2;
        public String name;
        public int value;

        public static SunState sunrise(Context context) {
            return new SunState(context.getString(R.string.sunrise), 1);
        }

        public static SunState sunset(Context context) {
            return new SunState(context.getString(R.string.sunset), 2);
        }

        private SunState(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
}