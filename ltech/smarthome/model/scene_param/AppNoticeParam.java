package com.ltech.smarthome.model.scene_param;

import java.util.List;

/* loaded from: classes4.dex */
public class AppNoticeParam {
    public String body;
    public String title;
    public List<UserParam> users;

    public static class UserParam {
        private long userid;
    }
}