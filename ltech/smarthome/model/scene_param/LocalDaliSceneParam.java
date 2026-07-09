package com.ltech.smarthome.model.scene_param;

/* loaded from: classes4.dex */
public class LocalDaliSceneParam extends BaseLocalParam {
    public long sceneid;
    public int scenenum;
    public int scenetype;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass() && this.sceneid == ((LocalDaliSceneParam) o).sceneid;
    }
}