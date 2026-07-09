package com.ltech.smarthome.net.request.photo;

import java.util.List;

/* loaded from: classes4.dex */
public class SortPhotoRequest {
    private List<Bean> pictures;

    public SortPhotoRequest(List<Bean> pictures) {
        this.pictures = pictures;
    }

    public static class Bean {
        private long pictureid;
        private int sorting;

        public long getPictureid() {
            return this.pictureid;
        }

        public void setPictureid(long pictureid) {
            this.pictureid = pictureid;
        }

        public int getSorting() {
            return this.sorting;
        }

        public void setSorting(int sorting) {
            this.sorting = sorting;
        }
    }
}