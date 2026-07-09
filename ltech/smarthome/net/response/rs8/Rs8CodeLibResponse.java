package com.ltech.smarthome.net.response.rs8;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class Rs8CodeLibResponse {
    public List<CodeLib> rows;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CodeLib> getRows() {
        return this.rows;
    }

    public void setRows(List<CodeLib> rows) {
        this.rows = rows;
    }

    public static class CodeLib {
        private List<Action> actionlist;
        private String address;
        private int addressType;
        private long brandid;
        private String brandname;
        private long categoryid;
        private String categoryname;
        private long codelibid;
        private long codelibnumber;
        private int fixedaction;
        private String icon;
        private long status;

        public int getFixedaction() {
            return this.fixedaction;
        }

        public void setFixedaction(int fixedaction) {
            this.fixedaction = fixedaction;
        }

        public int getAddressType() {
            return this.addressType;
        }

        public void setAddressType(int addressType) {
            this.addressType = addressType;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

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

        public List<Action> getActionlist() {
            List<Action> list = this.actionlist;
            return list == null ? new ArrayList() : list;
        }

        public void setActionlist(List<Action> actionlist) {
            this.actionlist = actionlist;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon() {
            return this.icon;
        }

        public String toString() {
            return "CodeLib{codelibid=" + this.codelibid + ", categoryid=" + this.categoryid + ", categoryname='" + this.categoryname + "', brandid=" + this.brandid + ", brandname='" + this.brandname + "', codelibnumber=" + this.codelibnumber + ", status=" + this.status + ", actionlist=" + this.actionlist + ", icon='" + this.icon + "', addressType=" + this.addressType + ", address='" + this.address + "'}";
        }

        public static class Action {
            private long actionid;
            private String cname;
            private long codelibid;
            private String ename;
            private String icon;
            private String instruct;
            private int position;
            private String replyinstruct;

            @Expose
            private boolean sel;

            public int getPosition() {
                return this.position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public boolean isSel() {
                return this.sel;
            }

            public void setSel(boolean sel) {
                this.sel = sel;
            }

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

            public String getReplyinstruct() {
                return this.replyinstruct;
            }

            public void setReplyinstruct(String replyinstruct) {
                this.replyinstruct = replyinstruct;
            }

            public String toString() {
                return "Action{actionid=" + this.actionid + ", codelibid=" + this.codelibid + ", cname='" + this.cname + "', ename='" + this.ename + "', icon='" + this.icon + "', instruct='" + this.instruct + "', position=" + this.position + ", sel=" + this.sel + '}';
            }
        }
    }

    public String toString() {
        return "Rs8CodeLibResponse{total=" + this.total + ", rows=" + this.rows + '}';
    }
}