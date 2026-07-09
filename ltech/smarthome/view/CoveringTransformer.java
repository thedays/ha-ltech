package com.ltech.smarthome.view;

import android.view.View;
import com.youth.banner.transformer.BasePageTransformer;

/* loaded from: classes4.dex */
public class CoveringTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_SCALE = 0.85f;
    public static float MIN_ALPHA = 0.8f;
    public static float MIN_SCALE = 0.8f;
    private float mMinScale;

    public CoveringTransformer() {
        this.mMinScale = DEFAULT_MIN_SCALE;
    }

    public CoveringTransformer(float minScale) {
        this.mMinScale = minScale;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.PageTransformer
    public void transformPage(View page, float position) {
        float abs = 1.0f - (Math.abs(position) * 0.15f);
        page.setScaleX(abs);
        page.setScaleY(abs);
        page.setAlpha(abs);
        page.setPivotX(page.getWidth() * ((1.0f - position) - ((position > 0.0f ? 1 : -1) * 2.75f)) * 0.5f);
        page.setPivotY(page.getHeight() / 2.0f);
        double d2 = position;
        page.setElevation((d2 <= -0.25d || d2 >= 0.25d) ? 0.0f : 1.0f);
    }
}