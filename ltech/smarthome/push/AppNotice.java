package com.ltech.smarthome.push;

/* loaded from: classes4.dex */
public class AppNotice {
    private String content;
    private String createTime;
    private int messagetype;

    public AppNotice(String createTime, String content) {
        this.createTime = createTime;
        this.content = content;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessagetype() {
        return this.messagetype;
    }

    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }
}