package com.ltech.smarthome.model.device_param;

import java.util.List;

/* loaded from: classes4.dex */
public class Channel512Param {
    private int channelCount;
    private int channelHead;
    private List<String> channelNames;

    public List<String> getChannelNames() {
        return this.channelNames;
    }

    public void setChannelNames(List<String> channelNames) {
        this.channelNames = channelNames;
    }

    public int getChannelHead() {
        int i = this.channelHead;
        if (i == 0) {
            return 1;
        }
        return i;
    }

    public void setChannelHead(int channelHead) {
        this.channelHead = channelHead;
    }

    public int getChannelCount() {
        int i = this.channelCount;
        if (i == 0) {
            return 6;
        }
        return i;
    }

    public void setChannelCount(int channelCount) {
        this.channelCount = channelCount;
    }
}