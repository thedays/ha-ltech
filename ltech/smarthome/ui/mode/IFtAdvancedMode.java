package com.ltech.smarthome.ui.mode;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.smart.product_agreement.bean.AdvancedModeList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IFtAdvancedMode {
    void changeModeName(Context context, int modeNum, String modeName, IAction<Boolean> result);

    void playAllList(Context context);

    void playMode(Context context, List<Integer> playList, int playTimes, boolean listPlay);

    void queryModeList(Context context, IAction<AdvancedModeList> result);

    void resetMode(Context context, int modeNum);
}