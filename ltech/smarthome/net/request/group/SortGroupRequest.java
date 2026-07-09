package com.ltech.smarthome.net.request.group;

import java.util.List;

/* loaded from: classes4.dex */
public class SortGroupRequest {
    private List<GroupId> groups;

    public SortGroupRequest(List<GroupId> groupIds) {
        this.groups = groupIds;
    }

    public static class GroupId {
        public long groupid;

        public GroupId(long groupid) {
            this.groupid = groupid;
        }
    }
}