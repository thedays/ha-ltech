package com.ltech.smarthome.net.request.group;

import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class UpdateDaliGroupRequest {
    private long floorid;
    private List<DaliGroup> groups = new ArrayList();
    private long macdeviceid;
    private long placeid;
    private long roomid;

    public UpdateDaliGroupRequest(Device controlDevice, List<Group> groupList) {
        this.roomid = controlDevice.getRoomId();
        this.floorid = controlDevice.getFloorId();
        this.placeid = controlDevice.getPlaceId();
        this.macdeviceid = controlDevice.getDeviceId();
        for (Group group : groupList) {
            DaliGroup daliGroup = new DaliGroup();
            daliGroup.setGroupid(Long.valueOf(group.getGroupId()));
            daliGroup.setGroupname(group.getGroupName());
            daliGroup.setType(3);
            daliGroup.setColortype(group.getControlType());
            daliGroup.setIndex(Integer.toHexString(controlDevice.getUnicastAddress()));
            daliGroup.setParamext(group.getExtParam());
            daliGroup.setFloorid(group.getFloorId());
            daliGroup.setRoomid(group.getRoomId());
            ArrayList arrayList = new ArrayList();
            Iterator<Long> it = group.getDeviceIds().iterator();
            while (it.hasNext()) {
                arrayList.add(new AddGroupContent(it.next().longValue()));
            }
            daliGroup.setDevices(arrayList);
            this.groups.add(daliGroup);
        }
    }

    public static final class DaliGroup {
        private String colortype;
        private List<AddGroupContent> devices;
        private long floorid;
        private long groupid;
        private String groupindex;
        private String groupname;
        private String index;
        private String instruct;
        private String paramext;
        private long roomid;
        private int type;

        public Long getGroupid() {
            return Long.valueOf(this.groupid);
        }

        public void setGroupid(Long groupid) {
            this.groupid = groupid.longValue();
        }

        public String getGroupname() {
            return this.groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getIndex() {
            return this.index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getColortype() {
            return this.colortype;
        }

        public void setColortype(String colortype) {
            this.colortype = colortype;
        }

        public String getInstruct() {
            return this.instruct;
        }

        public void setInstruct(String instruct) {
            this.instruct = instruct;
        }

        public String getGroupindex() {
            return this.groupindex;
        }

        public void setGroupindex(String groupindex) {
            this.groupindex = groupindex;
        }

        public String getParamext() {
            return this.paramext;
        }

        public void setParamext(String paramext) {
            this.paramext = paramext;
        }

        public List<AddGroupContent> getDevices() {
            return this.devices;
        }

        public void setDevices(List<AddGroupContent> devices) {
            this.devices = devices;
        }

        public long getFloorid() {
            return this.floorid;
        }

        public void setFloorid(long floorid) {
            this.floorid = floorid;
        }

        public long getRoomid() {
            return this.roomid;
        }

        public void setRoomid(long roomid) {
            this.roomid = roomid;
        }
    }

    public static final class AddGroupContent {
        public long deviceid;

        public AddGroupContent(long deviceid) {
            this.deviceid = deviceid;
        }
    }
}