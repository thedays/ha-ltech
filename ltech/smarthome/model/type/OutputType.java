package com.ltech.smarthome.model.type;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes4.dex */
public @interface OutputType {
    public static final int DALI = 2;
    public static final int DALI_CHANGE = 7;
    public static final int DMX = 3;
    public static final int DMX_CHANGE = 31;
    public static final int V010 = 1;
    public static final int V010_CHANGE = 3;
}