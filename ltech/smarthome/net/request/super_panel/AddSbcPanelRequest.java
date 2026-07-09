package com.ltech.smarthome.net.request.super_panel;

import com.ltech.smarthome.utils.HelpUtils;

/* loaded from: classes4.dex */
public class AddSbcPanelRequest {
    private String aispeechUserId;
    private String authCode;
    private String cliendId;
    private String codeVerifier;
    private long floorid;
    private String panelinfo;
    private String panelname;
    private String placeid;
    private String productid;
    private long roomid;
    private String param = "{\"timeZone\":\"" + HelpUtils.getTimeZoneId() + "\"}";
    private int oauthtype = 2;

    public AddSbcPanelRequest(String panelinfo, String panelname, String productid, String placeid, long roomid, long floorid, String authCode, String codeVerifier, String aispeechUserId, String clientId) {
        this.panelinfo = panelinfo;
        this.panelname = panelname;
        this.productid = productid;
        this.placeid = placeid;
        this.roomid = roomid;
        this.floorid = floorid;
        this.codeVerifier = codeVerifier;
        this.authCode = authCode;
        this.aispeechUserId = aispeechUserId;
        this.cliendId = clientId;
    }
}