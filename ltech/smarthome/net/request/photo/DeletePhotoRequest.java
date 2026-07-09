package com.ltech.smarthome.net.request.photo;

import java.util.List;

/* loaded from: classes4.dex */
public class DeletePhotoRequest {
    private List<Long> pictureids;

    public DeletePhotoRequest(List<Long> pictureids) {
        this.pictureids = pictureids;
    }
}