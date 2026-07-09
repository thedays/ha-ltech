package com.ltech.smarthome.ui.mode;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.smart.product_agreement.bean.AdvancedModeInfo;
import com.smart.product_agreement.bean.AdvancedModeList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IEditAdvancedMode {
    void changeModeName(Context context, int modeNum, String modeName, IAction<Boolean> result);

    void pausePreview(Context context, int modeNum, List<AdvancedModeInfo.ContentItem> contentItemList);

    void playAllList(Context context);

    void playMode(Context context, List<Integer> playList, int playTimes, boolean listPlay);

    void previewModeInfo(Context context, int modeNum, int playTime, List<AdvancedModeInfo.ContentItem> contentItemList);

    void queryModeInfo(Context context, int modeNum, IAction<String> result);

    void queryModeList(Context context, IAction<AdvancedModeList> result);

    void resetMode(Context context, int modeNum);

    void saveModeInfo(Context context, int modeNum, int playTime, List<AdvancedModeInfo.ContentItem> contentItemList, IAction<Boolean> result);
}