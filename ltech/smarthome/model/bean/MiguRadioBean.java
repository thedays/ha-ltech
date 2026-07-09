package com.ltech.smarthome.model.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class MiguRadioBean {
    private String columnId;
    private String columnName;
    private List<RadiosDTO> radios;

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public List<RadiosDTO> getRadios() {
        return this.radios;
    }

    public void setRadios(List<RadiosDTO> radios) {
        this.radios = radios;
    }

    public static class RadiosDTO {
        private String radioId;
        private String radioName;
        private String radioPic;
        private List<String> songIds;

        public String getRadioId() {
            return this.radioId;
        }

        public void setRadioId(String radioId) {
            this.radioId = radioId;
        }

        public String getRadioName() {
            return this.radioName;
        }

        public void setRadioName(String radioName) {
            this.radioName = radioName;
        }

        public String getRadioPic() {
            return this.radioPic;
        }

        public void setRadioPic(String radioPic) {
            this.radioPic = radioPic;
        }

        public List<String> getSongIds() {
            return this.songIds;
        }

        public void setSongIds(List<String> songIds) {
            this.songIds = songIds;
        }

        public String toString() {
            return "RadiosDTO{radioId='" + this.radioId + "', radioName='" + this.radioName + "', radioPic='" + this.radioPic + "', songIds=" + this.songIds + '}';
        }
    }

    public String toString() {
        return "MiguRadioBean{columnId='" + this.columnId + "', columnName='" + this.columnName + "', radios=" + this.radios + '}';
    }
}