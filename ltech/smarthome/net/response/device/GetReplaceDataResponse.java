package com.ltech.smarthome.net.response.device;

import android.text.TextUtils;
import com.ltech.smarthome.net.response.scene.QuerySceneActionResponse;
import java.util.List;

/* loaded from: classes4.dex */
public class GetReplaceDataResponse {
    private Object backData;
    private List<RowsBean> groupData;
    private List<QuerySceneActionResponse.RowsBean> sceneData;
    private List<RowsBean> subGroupData;

    public Object getBackData() {
        return this.backData;
    }

    public void setBackData(Object backData) {
        this.backData = backData;
    }

    public List<QuerySceneActionResponse.RowsBean> getSceneData() {
        return this.sceneData;
    }

    public void setSceneData(List<QuerySceneActionResponse.RowsBean> sceneData) {
        this.sceneData = sceneData;
    }

    public List<RowsBean> getGroupData() {
        return this.groupData;
    }

    public void setGroupData(List<RowsBean> groupData) {
        this.groupData = groupData;
    }

    public List<RowsBean> getSubGroupData() {
        return this.subGroupData;
    }

    public void setSubGroupData(List<RowsBean> subGroupData) {
        this.subGroupData = subGroupData;
    }

    public static class RowsBean {
        private String createtime;
        private long groupid;
        private String index;
        private int zone;

        public int getZone() {
            return this.zone;
        }

        public void setZone(int zone) {
            this.zone = zone;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public long getGroupid() {
            return this.groupid;
        }

        public void setGroupid(long groupid) {
            this.groupid = groupid;
        }

        public String getIndex() {
            return this.index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public int getGroupAddress() {
            if (TextUtils.isEmpty(getIndex())) {
                return 0;
            }
            return Integer.parseInt(getIndex(), 16);
        }
    }
}