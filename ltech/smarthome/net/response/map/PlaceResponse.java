package com.ltech.smarthome.net.response.map;

import java.util.List;

/* loaded from: classes4.dex */
public class PlaceResponse {
    private String count;
    private String info;
    private String infocode;
    private List<PoiItem> pois;
    private String status;

    public String getCount() {
        return this.count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfocode() {
        return this.infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<PoiItem> getPois() {
        return this.pois;
    }

    public void setPois(List<PoiItem> pois) {
        this.pois = pois;
    }

    public static class PoiItem {
        private String adcode;
        private String address;
        private String adname;
        private String citycode;
        private String cityname;
        private String distance;
        private String id;
        private String location;
        private String name;
        private String parent;
        private String pcode;
        private String pname;
        private String type;
        private String typecode;

        public String getParent() {
            return this.parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistance() {
            return this.distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPcode() {
            return this.pcode;
        }

        public void setPcode(String pcode) {
            this.pcode = pcode;
        }

        public String getAdcode() {
            return this.adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getPname() {
            return this.pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getCityname() {
            return this.cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypecode() {
            return this.typecode;
        }

        public void setTypecode(String typecode) {
            this.typecode = typecode;
        }

        public String getAdname() {
            return this.adname;
        }

        public void setAdname(String adname) {
            this.adname = adname;
        }

        public String getCitycode() {
            return this.citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return this.location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}