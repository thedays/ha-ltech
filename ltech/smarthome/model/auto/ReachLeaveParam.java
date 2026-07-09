package com.ltech.smarthome.model.auto;

import android.content.Context;
import com.ltech.smarthome.R;

/* loaded from: classes4.dex */
public class ReachLeaveParam {
    public static final int LEAVE = 1;
    public static final int REACH = 2;
    public String city;
    public int range;
    public int type;
    public long userid;
    public double x;
    public double y;

    public String getStateString(Context context) {
        return context.getString(this.type == 1 ? R.string.leave : R.string.reach);
    }
}