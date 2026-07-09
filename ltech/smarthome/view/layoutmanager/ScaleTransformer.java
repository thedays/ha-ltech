package com.ltech.smarthome.view.layoutmanager;

import android.view.View;
import com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager;

/* loaded from: classes4.dex */
public class ScaleTransformer implements GalleryLayoutManager.ItemTransformer {
    private static final String TAG = "CurveTransformer";

    @Override // com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager.ItemTransformer
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        item.setPivotX(item.getWidth() / 2.0f);
        item.setPivotY(item.getHeight() / 2.0f);
        if (!Float.isNaN(fraction)) {
            item.setScaleX(2.0f - (Math.abs(fraction) * 0.5f));
            item.setScaleY(2.0f - (Math.abs(fraction) * 0.5f));
        } else {
            item.setScaleX(2.0f);
            item.setScaleY(2.0f);
        }
    }
}