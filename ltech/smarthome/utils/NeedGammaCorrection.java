package com.ltech.smarthome.utils;

import kotlin.Metadata;
import org.mozilla.javascript.ES6Iterator;

/* compiled from: XYToRGB.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\u0010\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\u0005\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0013"}, d2 = {"Lcom/ltech/smarthome/utils/NeedGammaCorrection;", "Lcom/ltech/smarthome/utils/GammaCorrection;", "gamma", "", "transition", "slope", "offset", "<init>", "(FFFF)V", "getGamma", "()F", "getTransition", "getSlope", "getOffset", "transform", ES6Iterator.VALUE_PROPERTY, "invTransform", "toString", "", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class NeedGammaCorrection implements GammaCorrection {
    private final float gamma;
    private final float offset;
    private final float slope;
    private final float transition;

    public NeedGammaCorrection(float f, float f2, float f3, float f4) {
        this.gamma = f;
        this.transition = f2;
        this.slope = f3;
        this.offset = f4;
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
    public float transform(float value) {
        return value <= getTransition() ? getSlope() * value : ((1 + getOffset()) * ((float) Math.pow(value, getGamma()))) - getOffset();
    }

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public float invTransform(float value) {
        return value <= transform(getTransition()) ? value * (1 / getSlope()) : (value + getOffset()) / ((float) Math.pow(r0 + getOffset(), r0 / getGamma()));
    }

    @Override // com.ltech.smarthome.utils.GammaCorrection
    public String toString() {
        return "gamma " + getGamma();
    }
}