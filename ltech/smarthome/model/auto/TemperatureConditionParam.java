package com.ltech.smarthome.model.auto;

import android.content.Context;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class TemperatureConditionParam {
    public static final int EQUAL = 2;
    public static final int GREAT_THAN = 3;
    public static final int LESS_THAN = 1;
    public String city;
    public int temperature;
    public int type;
    public double x;
    public double y;

    public String getStateString(Context context) {
        StringBuilder sb = new StringBuilder();
        int i = this.type;
        if (i == 1) {
            sb.append(context.getString(R.string.less_than));
        } else if (i == 3) {
            sb.append(context.getString(R.string.great_than));
        } else if (i == 2) {
            sb.append(context.getString(R.string.equal));
        }
        sb.append(this.temperature);
        sb.append(context.getString(R.string.centigrade));
        return sb.toString();
    }
}