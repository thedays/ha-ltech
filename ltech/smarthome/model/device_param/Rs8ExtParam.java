package com.ltech.smarthome.model.device_param;

import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import java.util.List;

/* loaded from: classes4.dex */
public class Rs8ExtParam {
    private List<Rs8CodeLibResponse.CodeLib.Action> actionlist;
    private long brandid;
    private String brandname;
    private long categoryid;
    private String categoryname;
    private long codelibid;
    private long codelibnumber;
    private String icon;
    private long status;

    public long getCodelibid() {
        return this.codelibid;
    }

    public void setCodelibid(long codelibid) {
        this.codelibid = codelibid;
    }

    public long getCategoryid() {
        return this.categoryid;
    }

    public void setCategoryid(long categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return this.categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public long getBrandid() {
        return this.brandid;
    }

    public void setBrandid(long brandid) {
        this.brandid = brandid;
    }

    public String getBrandname() {
        return this.brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public long getCodelibnumber() {
        return this.codelibnumber;
    }

    public void setCodelibnumber(long codelibnumber) {
        this.codelibnumber = codelibnumber;
    }

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public List<Rs8CodeLibResponse.CodeLib.Action> getActionlist() {
        return this.actionlist;
    }

    public void setActionlist(List<Rs8CodeLibResponse.CodeLib.Action> actionlist) {
        this.actionlist = actionlist;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }

    public String toString() {
        return "CodeLib{codelibid=" + this.codelibid + ", categoryid=" + this.categoryid + ", categoryname='" + this.categoryname + "', brandid=" + this.brandid + ", brandname='" + this.brandname + "', codelibnumber=" + this.codelibnumber + ", status=" + this.status + ", actionlist=" + this.actionlist + ", icon='" + this.icon + "'}";
    }

    public static class Action {
        private long actionid;
        private String cname;
        private long codelibid;
        private String ename;
        private String icon;
        private String instruct;

        public long getActionid() {
            return this.actionid;
        }

        public void setActionid(long actionid) {
            this.actionid = actionid;
        }

        public long getCodelibid() {
            return this.codelibid;
        }

        public void setCodelibid(long codelibid) {
            this.codelibid = codelibid;
        }

        public String getCname() {
            return this.cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getEname() {
            return this.ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getInstruct() {
            return this.instruct;
        }

        public void setInstruct(String instruct) {
            this.instruct = instruct;
        }

        public String toString() {
            return "Actions{actionid=" + this.actionid + ", codelibid=" + this.codelibid + ", cname='" + this.cname + "', ename='" + this.ename + "', icon='" + this.icon + "', instruct='" + this.instruct + "'}";
        }
    }
}