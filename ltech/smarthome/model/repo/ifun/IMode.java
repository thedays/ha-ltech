package com.ltech.smarthome.model.repo.ifun;

import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.ModeContent;
import java.util.List;

/* loaded from: classes4.dex */
public interface IMode {
    void getAllModeFromNet(LifecycleOwner owner);

    ModeContent getModeById(long modeId);

    Listing<ModeContent> getModeList(LifecycleOwner owner, long placeId, int deviceType, int modeType);

    List<ModeContent> getModeListFromDb(int deviceType, int modeType);
}