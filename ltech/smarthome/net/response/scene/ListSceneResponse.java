package com.ltech.smarthome.net.response.scene;

import java.util.List;

/* loaded from: classes4.dex */
public class ListSceneResponse {
    private List<RowsBean> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return this.rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private int common;
        private String createby;
        private String createbyname;
        private String createtime;
        private int dynamics;
        private long floorid;
        private String floorname;
        private String img;
        private int imgindex;
        private long macdeviceid;
        private String paramext;
        private long placeid;
        private long roomid;
        private String roomname;
        private long sceneid;
        private int sceneindex;
        private String scenename;
        private int scenenum;
        private long sceneroleid;
        private int scenetype;
        private long userid;

        public String getFloorname() {
            return this.floorname;
        }

        public void setFloorname(String floorname) {
            this.floorname = floorname;
        }

        public String getRoomname() {
            return this.roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public String getCreateby() {
            return this.createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public long getSceneid() {
            return this.sceneid;
        }

        public void setSceneid(long sceneid) {
            this.sceneid = sceneid;
        }

        public String getScenename() {
            return this.scenename;
        }

        public void setScenename(String scenename) {
            this.scenename = scenename;
        }

        public long getPlaceid() {
            return this.placeid;
        }

        public void setPlaceid(long placeid) {
            this.placeid = placeid;
        }

        public String getCreatebyname() {
            return this.createbyname;
        }

        public void setCreatebyname(String createbyname) {
            this.createbyname = createbyname;
        }

        public int getSceneindex() {
            return this.sceneindex;
        }

        public void setSceneindex(int sceneindex) {
            this.sceneindex = sceneindex;
        }

        public long getUserid() {
            return this.userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public long getSceneroleid() {
            return this.sceneroleid;
        }

        public void setSceneroleid(long sceneroleid) {
            this.sceneroleid = sceneroleid;
        }

        public String getImg() {
            return this.img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getImgindex() {
            return this.imgindex;
        }

        public void setImgindex(int imgindex) {
            this.imgindex = imgindex;
        }

        public int getScenetype() {
            return this.scenetype;
        }

        public void setScenetype(int scenetype) {
            this.scenetype = scenetype;
        }

        public int getScenenum() {
            return this.scenenum;
        }

        public void setScenenum(int scenenum) {
            this.scenenum = scenenum;
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

        public long getMacdeviceid() {
            return this.macdeviceid;
        }

        public void setMacdeviceid(long macdeviceid) {
            this.macdeviceid = macdeviceid;
        }

        public String getParamext() {
            return this.paramext;
        }

        public void setParamext(String paramext) {
            this.paramext = paramext;
        }

        public int getCommon() {
            return this.common;
        }

        public void setCommon(int common) {
            this.common = common;
        }

        public int getDynamics() {
            return this.dynamics;
        }

        public void setDynamics(int dynamics) {
            this.dynamics = dynamics;
        }
    }
}