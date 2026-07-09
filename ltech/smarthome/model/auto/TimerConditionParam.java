package com.ltech.smarthome.model.auto;

import android.content.Context;
import com.ltech.smarthome.utils.HelpUtils;

/* loaded from: classes4.dex */
public class TimerConditionParam {
    public int hour;
    public int min;
    public String weeks;

    public String getStateString(Context context) {
        return String.format("%s %s", HelpUtils.getTimeString(this.hour, this.min), HelpUtils.getWeeksString(context, this.weeks));
    }
}