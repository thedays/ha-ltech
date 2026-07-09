package com.ltech.smarthome.net.request.mode;

/* loaded from: classes4.dex */
public class AddModeRequest {
    private String Content;
    private int devicetype;
    private int modeindex;
    private String modename;
    private int modetype;
    private String param;
    private int picindex;
    private long placeid;
    private int playtimes;
    private long userid;

    public AddModeRequest(String modename, long placeid, long userid, int devicetype, int modetype, int modeindex, int picindex, String Content, String param, int playtimes) {
        this.modename = modename;
        this.userid = userid;
        this.devicetype = devicetype;
        this.modetype = modetype;
        this.modeindex = modeindex;
        this.picindex = picindex;
        this.Content = Content;
        this.param = param;
        this.playtimes = playtimes;
    }
}