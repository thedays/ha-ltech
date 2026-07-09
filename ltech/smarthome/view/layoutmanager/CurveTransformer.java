package com.ltech.smarthome.view.layoutmanager;

import android.view.View;
import com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager;

/* loaded from: classes4.dex */
public class CurveTransformer implements GalleryLayoutManager.ItemTransformer {
    public static final int ROTATE_ANGEL = 7;
    private static final String TAG = "CurveTransformer";

    @Override // com.ltech.smarthome.view.layoutmanager.GalleryLayoutManager.ItemTransformer
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        if (layoutManager.getOrientation() == 1) {
            return;
        }
        int width = item.getWidth();
        int height = item.getHeight();
        item.setPivotX(width / 2.0f);
        item.setPivotY(height);
        float abs = 1.0f - (Math.abs(fraction) * 0.1f);
        item.setScaleX(abs);
        item.setScaleY(abs);
        item.setRotation(7.0f * fraction);
        item.setTranslationY((float) ((Math.sin((Math.abs(fraction) * 43.982297150257104d) / 360.0d) * width) / 2.0d));
        item.setTranslationX((float) ((((1.0f - abs) * r1) / 2.0f) / Math.cos((fraction * 43.982297150257104d) / 360.0d)));
        if (fraction > 0.0f) {
            item.setTranslationX(-item.getTranslationX());
        }
        item.setAlpha(1.0f - (Math.abs(fraction) * 0.2f));
    }
}