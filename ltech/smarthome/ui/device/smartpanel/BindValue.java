package com.ltech.smarthome.ui.device.smartpanel;

import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import kotlin.Metadata;

/* compiled from: LightBindUtils.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\r\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003B\u0019\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005¢\u0006\u0004\b\u0002\u0010\tR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\rR\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\r¨\u0006\u0012"}, d2 = {"Lcom/ltech/smarthome/ui/device/smartpanel/BindValue;", "", "<init>", "()V", "key", "", "nameRes", "(II)V", UpdateBackDataRequest.LIGHT_TYPE, "(III)V", "getKey", "()I", "setKey", "(I)V", "getNameRes", "setNameRes", "getLightType", "setLightType", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class BindValue {
    private int key;
    private int lightType;
    private int nameRes;

    public BindValue() {
    }

    public final int getKey() {
        return this.key;
    }

    public final void setKey(int i) {
        this.key = i;
    }

    public final int getNameRes() {
        return this.nameRes;
    }

    public final void setNameRes(int i) {
        this.nameRes = i;
    }

    public final int getLightType() {
        return this.lightType;
    }

    public final void setLightType(int i) {
        this.lightType = i;
    }

    public BindValue(int i, int i2) {
        this();
        this.key = i;
        this.nameRes = i2;
    }

    public BindValue(int i, int i2, int i3) {
        this(i, i2);
        this.lightType = i3;
    }
}