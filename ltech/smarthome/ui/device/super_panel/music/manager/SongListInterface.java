package com.ltech.smarthome.ui.device.super_panel.music.manager;

import com.ltech.smarthome.model.bean.SongListItemInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface SongListInterface {
    void onFail();

    void onSuccess(List<SongListItemInfo> listItemInfos);
}