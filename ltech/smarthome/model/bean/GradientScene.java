package com.ltech.smarthome.model.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class GradientScene {
    private long groupid;
    private List<GroupSceneInfos> groupsceneinfoList;
    private List<GroupSceneInfos> groupsceneinfos;
    private long gsid;
    private String gsname;
    private String imgurl;
    private boolean sel;

    public boolean isSel() {
        return this.sel;
    }

    public void setSel(boolean sel) {
        this.sel = sel;
    }

    public List<GroupSceneInfos> getGroupsceneinfoList() {
        return this.groupsceneinfoList;
    }

    public void setGroupsceneinfoList(List<GroupSceneInfos> groupsceneinfoList) {
        this.groupsceneinfoList = groupsceneinfoList;
    }

    public long getGsid() {
        return this.gsid;
    }

    public void setGsid(long gsid) {
        this.gsid = gsid;
    }

    public long getGroupid() {
        return this.groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public String getGsname() {
        return this.gsname;
    }

    public void setGsname(String gsname) {
        this.gsname = gsname;
    }

    public String getImgurl() {
        return this.imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public List<GroupSceneInfos> getGroupsceneinfos() {
        List<GroupSceneInfos> list = this.groupsceneinfos;
        return list == null ? this.groupsceneinfoList : list;
    }

    public void setGroupsceneinfos(List<GroupSceneInfos> groupsceneinfos) {
        this.groupsceneinfos = groupsceneinfos;
    }

    public static class GroupSceneInfos {
        private String color;
        private long deviceid;
        private int unicastAddress;
        private int x;
        private int y;

        public GroupSceneInfos(long deviceid, String color, int x, int y, int unicastAddress) {
            this.deviceid = deviceid;
            this.color = color;
            this.x = x;
            this.y = y;
            this.unicastAddress = unicastAddress;
        }

        public long getDeviceid() {
            return this.deviceid;
        }

        public void setDeviceid(long deviceid) {
            this.deviceid = deviceid;
        }

        public String getColor() {
            return this.color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getX() {
            return this.x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return this.y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getUnicastAddress() {
            return this.unicastAddress;
        }

        public void setUnicastAddress(int unicastAddress) {
            this.unicastAddress = unicastAddress;
        }
    }
}