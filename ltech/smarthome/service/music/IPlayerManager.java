package com.ltech.smarthome.service.music;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import java.util.List;

/* loaded from: classes4.dex */
public interface IPlayerManager<T> extends IPlayerController<T> {
    List<T> queryMusic(Context context);

    List<T> refreshSaveList(Context context);

    void setRecordAction(IAction<Float> action);

    void startRecord();

    void stopRecord();
}