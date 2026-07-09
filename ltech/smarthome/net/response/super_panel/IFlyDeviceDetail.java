package com.ltech.smarthome.net.response.super_panel;

import java.util.List;

/* loaded from: classes4.dex */
public class IFlyDeviceDetail {
    private List<?> addtional_items;
    private String alias;
    private boolean continous_mode;
    private Object firmware_version;
    private String image;
    private MusicAccessDTO music_access;
    private String name;
    private String status;
    private String zone;

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isContinous_mode() {
        return this.continous_mode;
    }

    public void setContinous_mode(boolean continous_mode) {
        this.continous_mode = continous_mode;
    }

    public Object getFirmware_version() {
        return this.firmware_version;
    }

    public void setFirmware_version(Object firmware_version) {
        this.firmware_version = firmware_version;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MusicAccessDTO getMusic_access() {
        return this.music_access;
    }

    public void setMusic_access(MusicAccessDTO music_access) {
        this.music_access = music_access;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZone() {
        return this.zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<?> getAddtional_items() {
        return this.addtional_items;
    }

    public void setAddtional_items(List<?> addtional_items) {
        this.addtional_items = addtional_items;
    }

    public static class MusicAccessDTO {
        private boolean music_enable;
        private String name;
        private String redirect_url;
        private String value;

        public boolean isMusic_enable() {
            return this.music_enable;
        }

        public void setMusic_enable(boolean music_enable) {
            this.music_enable = music_enable;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRedirect_url() {
            return this.redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}