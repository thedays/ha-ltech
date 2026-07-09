package com.ltech.smarthome.ui.device.super_panel.music.manager;

import com.rich.czlylibary.bean.SongNewVoiceBox;
import java.util.List;

/* loaded from: classes4.dex */
public interface SearchSongInfoInterface {
    void onFail();

    void onSuccess(List<SongNewVoiceBox> songNewVoiceBoxes);
}