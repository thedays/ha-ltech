package com.ltech.smarthome.net.response.user;

/* loaded from: classes4.dex */
public class BindUserResponse {
    private String accesstoken;
    private Object begincreatetime;
    private String createtime;
    private int devicetype;
    private Object endcreatetime;
    private int expiretime;
    private String name;
    private String openid;
    private int pagenum;
    private int pagesize;
    private String param;
    private String source;
    private Object sourcename;
    private long userid;
    private long useroauthid;

    public long getUseroauthid() {
        return this.useroauthid;
    }

    public void setUseroauthid(long useroauthid) {
        this.useroauthid = useroauthid;
    }

    public long getUserid() {
        return this.userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccesstoken() {
        return this.accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDevicetype() {
        return this.devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Object getBegincreatetime() {
        return this.begincreatetime;
    }

    public void setBegincreatetime(Object begincreatetime) {
        this.begincreatetime = begincreatetime;
    }

    public Object getEndcreatetime() {
        return this.endcreatetime;
    }

    public void setEndcreatetime(Object endcreatetime) {
        this.endcreatetime = endcreatetime;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Object getSourcename() {
        return this.sourcename;
    }

    public void setSourcename(Object sourcename) {
        this.sourcename = sourcename;
    }

    public int getExpiretime() {
        return this.expiretime;
    }

    public void setExpiretime(int expiretime) {
        this.expiretime = expiretime;
    }

    public int getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPagenum() {
        return this.pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public class ParamBean {
        private String deviceName;
        private String deviceSecret;
        private String iotId;
        private String productKey;

        public ParamBean(final BindUserResponse this$0) {
        }

        public String getIotId() {
            return this.iotId;
        }

        public void setIotId(String iotId) {
            this.iotId = iotId;
        }

        public String getDeviceSecret() {
            return this.deviceSecret;
        }

        public void setDeviceSecret(String deviceSecret) {
            this.deviceSecret = deviceSecret;
        }

        public String getProductKey() {
            return this.productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }
    }
}