package com.ltech.smarthome.net.request.group;

import com.blankj.utilcode.util.GsonUtils;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class DeleteGroupRequest {
    private long groupid;
    private long publicationid;

    public DeleteGroupRequest(long groupid) {
        this.groupid = groupid;
    }

    public DeleteGroupRequest(long groupid, long publicationid) {
        this.groupid = groupid;
        this.publicationid = publicationid;
    }

    public String getRequestString() {
        HashMap hashMap = new HashMap();
        hashMap.put("groupid", Long.valueOf(this.groupid));
        long j = this.publicationid;
        if (j != 0) {
            hashMap.put("publicationid", Long.valueOf(j));
        }
        return GsonUtils.toJson(hashMap);
    }
}