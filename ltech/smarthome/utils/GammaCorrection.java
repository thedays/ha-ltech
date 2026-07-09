package com.ltech.smarthome.utils;

import kotlin.Metadata;
import org.mozilla.javascript.ES6Iterator;

/* compiled from: XYToRGB.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H&J\b\u0010\u000f\u001a\u00020\u0010H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005¨\u0006\u0011"}, d2 = {"Lcom/ltech/smarthome/utils/GammaCorrection;", "", "gamma", "", "getGamma", "()F", "transition", "getTransition", "slope", "getSlope", "offset", "getOffset", "transform", ES6Iterator.VALUE_PROPERTY, "invTransform", "toString", "", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public interface GammaCorrection {
    float getGamma();

    float getOffset();

    float getSlope();

    float getTransition();

    float invTransform(float value);

    String toString();

    float transform(float value);
}