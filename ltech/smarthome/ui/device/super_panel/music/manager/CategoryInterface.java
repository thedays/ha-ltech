package com.ltech.smarthome.ui.device.super_panel.music.manager;

import java.util.List;

/* loaded from: classes4.dex */
public interface CategoryInterface {
    void onFail(int error);

    void onSuccess(List<String> songCategoryInfoList);
}