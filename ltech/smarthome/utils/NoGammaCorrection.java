package com.ltech.smarthome.utils;

import kotlin.Metadata;
import org.mozilla.javascript.ES6Iterator;

/* compiled from: XYToRGB.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0014\u0010\f\u001a\u00020\u0005X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007¨\u0006\u0013"}, d2 = {"Lcom/ltech/smarthome/utils/NoGammaCorrection;", "Lcom/ltech/smarthome/utils/GammaCorrection;", "<init>", "()V", "gamma", "", "getGamma", "()F", "transition", "getTransition", "slope", "getSlope", "offset", "getOffset", "transform", ES6Iterator.VALUE_PROPERTY, "invTransform", "toString", "", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class NoGammaCorrection implements GammaCorrection {
    private final float gamma;
    private final float offset;
    private final float slope;
    private final float transition;

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public float invTransform(float value) {
        return value;
    }

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public float transform(float value) {
        return value;
    }

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public float getGamma() {
        return this.gamma;
    }

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public float getTransition() {
        return this.transition;
    }

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public float getSlope() {
        return this.slope;
    }

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public float getOffset() {
        return this.offset;
    }

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public String toString() {
        return "no gamma correction";
    }
}