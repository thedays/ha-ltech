package com.ltech.smarthome.net.request.scene;

import java.util.List;

/* loaded from: classes4.dex */
public class SortSceneRequest {
    private List<SceneSortBean> scenes;

    public static final class SceneSortBean {
        public long sceneid;
    }

    public SortSceneRequest(List<SceneSortBean> scenes) {
        this.scenes = scenes;
    }
}