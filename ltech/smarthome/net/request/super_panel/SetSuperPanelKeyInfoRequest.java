package com.ltech.smarthome.net.request.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class SetSuperPanelKeyInfoRequest {
    private List<ContentInfo> content;
    private int keywords;
    private String keywordsname;
    private long panelid;

    public static final class ContentInfo {
        public String action;
        public int actiontype;
        public String actiontypename;
        public String executecommand;
        public int timespace;
    }

    public SetSuperPanelKeyInfoRequest(long panelid, String keywordsname, int keywords, List<ContentInfo> content) {
        this.panelid = panelid;
        this.keywordsname = keywordsname;
        this.keywords = keywords;
        this.content = content;
    }
}