package com.ltech.smarthome.model.auto;

import android.content.Context;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class Pm25ConditionParam {
    public String city;
    public int type;
    public double x;
    public double y;

    public String getStateString(Context context) {
        int i = this.type;
        if (1 == i) {
            return context.getString(R.string.excellent);
        }
        if (2 == i) {
            return context.getString(R.string.good);
        }
        if (3 == i) {
            return context.getString(R.string.contaminated);
        }
        return "";
    }

    public static final class PmState {
        public static final int CONTAMINATED = 3;
        public static final int EXCELLENT = 1;
        public static final int GOOD = 2;
        public String name;
        public int value;

        public static PmState excellent(Context context) {
            return new PmState(context.getString(R.string.excellent), 1);
        }

        public static PmState good(Context context) {
            return new PmState(context.getString(R.string.good), 2);
        }

        public static PmState contaminated(Context context) {
            return new PmState(context.getString(R.string.contaminated), 3);
        }

        private PmState(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
}