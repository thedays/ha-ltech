package com.ltech.smarthome.net.request.super_panel;

import com.ltech.smarthome.utils.HelpUtils;

/* loaded from: classes4.dex */
public class AddSuperPanelRequest {
    private long floorid;
    private String panelinfo;
    private String panelname;
    private String param = "{\"timeZone\":\"" + HelpUtils.getTimeZoneId() + "\"}";
    private String placeid;
    private String productid;
    private long roomid;

    public AddSuperPanelRequest(String panelinfo, String panelname, String productid, String placeid, long roomid, long floorid) {
        this.panelinfo = panelinfo;
        this.panelname = panelname;
        this.productid = productid;
        this.placeid = placeid;
        this.roomid = roomid;
        this.floorid = floorid;
    }
}