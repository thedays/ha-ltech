package com.ltech.smarthome.net.request.role;

import java.util.List;

/* loaded from: classes4.dex */
public class UpdateSceneRoleRequest {
    private long placeid;
    private List<SceneId> scenes;
    private long userid;

    public UpdateSceneRoleRequest(long placeid, long userid, List scenes) {
        this.placeid = placeid;
        this.userid = userid;
        this.scenes = scenes;
    }

    public static class SceneId {
        private long sceneid;

        public SceneId(long sceneid) {
            this.sceneid = sceneid;
        }

        public long getSceneid() {
            return this.sceneid;
        }
    }
}