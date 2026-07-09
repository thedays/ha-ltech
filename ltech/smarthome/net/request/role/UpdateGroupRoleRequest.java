package com.ltech.smarthome.net.request.role;

import java.util.List;

/* loaded from: classes4.dex */
public class UpdateGroupRoleRequest {
    private List<GroupId> groups;
    private long placeid;
    private long userid;

    public UpdateGroupRoleRequest(long placeid, long userid, List groups) {
        this.placeid = placeid;
        this.userid = userid;
        this.groups = groups;
    }

    public static class GroupId {
        private long groupid;

        public GroupId(long groupid) {
            this.groupid = groupid;
        }

        public long getGroupid() {
            return this.groupid;
        }
    }
}