package com.ltech.smarthome.model.auto;

import android.content.Context;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class HumidityConditionParam {
    public String city;
    public int type;
    public double x;
    public double y;

    public String getStateString(Context context) {
        int i = this.type;
        if (3 == i) {
            return context.getString(R.string.damp);
        }
        if (2 == i) {
            return context.getString(R.string.comfortable);
        }
        if (1 == i) {
            return context.getString(R.string.dry);
        }
        return "";
    }

    public static final class HumidityState {
        public static final int COMFORTABLE = 2;
        public static final int DAMP = 3;
        public static final int DRY = 1;
        public String name;
        public int value;

        public static HumidityState dry(Context context) {
            return new HumidityState(context.getString(R.string.dry), 1);
        }

        public static HumidityState comfortable(Context context) {
            return new HumidityState(context.getString(R.string.comfortable), 2);
        }

        public static HumidityState damp(Context context) {
            return new HumidityState(context.getString(R.string.damp), 3);
        }

        private HumidityState(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
}