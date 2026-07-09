package com.ltech.smarthome.model.scene_param;

/* loaded from: classes4.dex */
public class BaseLocalParam {
    public int delayIn100Ms;
    public int sceneAddr;
    public int sceneStep;
    public int sceneTotalDelay;
    public int totalDelayIn100Ms;
    public String instruct = "";
    public int sceneZone = 1;

    public int getTotalDelay() {
        int i = this.totalDelayIn100Ms;
        return i == 0 ? this.sceneTotalDelay * 10 : i;
    }

    public void setTotalDelay(int totalDelay) {
        this.sceneTotalDelay = totalDelay / 10;
        this.totalDelayIn100Ms = totalDelay;
    }
}