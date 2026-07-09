package com.ltech.smarthome.model.auto;

import android.content.Context;
import com.ltech.smarthome.R;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class EvnConditionParam extends DeviceConditionParam {
    @Override // com.ltech.smarthome.model.auto.DeviceConditionParam
    public String getStatusConditionName(Context context) {
        String str;
        int i = this.subIndex;
        if (i == 1) {
            str = "°C";
        } else if (i == 2) {
            str = "%";
        } else {
            str = i != 3 ? "" : "μg/m³";
        }
        if (this.operator == 4) {
            return getValue() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + getValue2() + str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.operator == 2 ? context.getString(R.string.app_str_above) : context.getString(R.string.app_str_blow));
        sb.append(getValue());
        sb.append(str);
        return sb.toString();
    }

    public String getConditionName(Context context) {
        int i = this.subIndex;
        if (i == 1) {
            return context.getString(R.string.temperature);
        }
        if (i == 2) {
            return context.getString(R.string.humidity);
        }
        if (i == 3) {
            return context.getString(R.string.pm_25);
        }
        return "";
    }
}