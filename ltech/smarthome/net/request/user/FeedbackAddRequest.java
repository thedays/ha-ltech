package com.ltech.smarthome.net.request.user;

import com.ltech.smarthome.model.Injection;

/* loaded from: classes4.dex */
public class FeedbackAddRequest {
    private String remark;
    private String feedbacktypecode = "app";
    private String feedbacktypename = "app";
    private String sysnodecode = "china";
    private String sysnodename = "china";
    private long userid = Injection.repo().user().getUserId();

    public FeedbackAddRequest(String remark) {
        this.remark = remark;
    }
}