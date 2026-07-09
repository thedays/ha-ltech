package com.ltech.smarthome.model.device_param;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.annotations.SerializedName;
import com.ltech.smarthome.base.BaseExtParam;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class Rs485ExtParam extends BaseExtParam {
    public static final int TYPE_485_TO_BT = 2;
    public static final int TYPE_BT_TO_485 = 1;
    public static final int TYPE_LEARN = 3;
    public static final int TYPE_LIBRARY = 2;
    public static final int TYPE_MANUAL = 1;

    @SerializedName("categories_ToBle")
    public List<Category> toBleList = new ArrayList();

    @SerializedName("categories_To485")
    protected List<Category> to485List = new ArrayList();

    public List<Category> getToBleList() {
        return this.toBleList;
    }

    public void setToBleList(List<Category> toBleList) {
        this.toBleList = toBleList;
    }

    public List<Category> getTo485List() {
        return this.to485List;
    }

    public void setTo485List(List<Category> to485List) {
        this.to485List = to485List;
    }

    public static class Category {
        private List<Instruct> action = new ArrayList();
        private String categoryName;
        private int colorIdx;
        private int type;

        public String getCategoryName() {
            return this.categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getColorIdx() {
            return this.colorIdx;
        }

        public void setColorIdx(int colorIdx) {
            this.colorIdx = colorIdx;
        }

        public List<Instruct> getAction() {
            return this.action;
        }

        public void setAction(List<Instruct> action) {
            this.action = action;
        }
    }

    public static class Instruct {
        private String actionName;
        private RelateInfo bindAction;
        private String cmd;
        private int cmdIdx;
        private int dataFormat;
        private int recordType;

        public Instruct() {
            this.actionName = "";
            this.cmd = "";
            this.recordType = 1;
            this.dataFormat = 1;
            this.bindAction = new RelateInfo();
        }

        public Instruct(int recordType) {
            this.actionName = "";
            this.cmd = "";
            this.recordType = 1;
            this.dataFormat = 1;
            this.bindAction = new RelateInfo();
            this.recordType = recordType;
        }

        public String getActionName() {
            return this.actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public String getCmd() {
            return this.cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public int getRecordType() {
            return this.recordType;
        }

        public void setRecordType(int recordType) {
            this.recordType = recordType;
        }

        public int getDataFormat() {
            return this.dataFormat;
        }

        public void setDataFormat(int dataFormat) {
            this.dataFormat = dataFormat;
        }

        public int getCmdIdx() {
            return this.cmdIdx;
        }

        public void setCmdIdx(int cmdIdx) {
            this.cmdIdx = cmdIdx;
        }

        public RelateInfo getBindAction() {
            return this.bindAction;
        }

        public void setBindAction(RelateInfo bindAction) {
            this.bindAction = bindAction;
        }

        public Instruct getClone() {
            return (Instruct) GsonUtils.fromJson(GsonUtils.toJson(this), Instruct.class);
        }
    }

    public static class RelateInfo {
        private int actionType;
        private int address;
        private String instruct;
        private String name;
        private long objectId;
        private int option;
        private String optionValue;
        private int zone;
        private String roomname = "";
        private String floorname = "";

        public long getObjectId() {
            return this.objectId;
        }

        public void setObjectId(long objectId) {
            this.objectId = objectId;
        }

        public String getInstruct() {
            return this.instruct;
        }

        public void setInstruct(String instruct) {
            this.instruct = instruct;
        }

        public int getZone() {
            return this.zone;
        }

        public void setZone(int zone) {
            this.zone = zone;
        }

        public int getOption() {
            return this.option;
        }

        public void setOption(int option) {
            this.option = option;
        }

        public String getOptionValue() {
            return this.optionValue;
        }

        public void setOptionValue(String optionValue) {
            this.optionValue = optionValue;
        }

        public int getActionType() {
            return this.actionType;
        }

        public void setActionType(int actionType) {
            this.actionType = actionType;
        }

        public int getAddress() {
            return this.address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoomname() {
            return this.roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public String getFloorname() {
            return this.floorname;
        }

        public void setFloorname(String floorname) {
            this.floorname = floorname;
        }
    }
}