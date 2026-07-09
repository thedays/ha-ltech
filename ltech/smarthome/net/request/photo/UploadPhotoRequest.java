package com.ltech.smarthome.net.request.photo;

import java.util.List;

/* loaded from: classes4.dex */
public class UploadPhotoRequest {
    private String mac;
    private long panelid;
    private List<Photo> pictures;

    public UploadPhotoRequest(long panelid, String mac, List<Photo> pictures) {
        this.panelid = panelid;
        this.mac = mac;
        this.pictures = pictures;
    }

    public static class Photo {
        private String path;
        private long size;
        private String url;

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getSize() {
            return this.size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}