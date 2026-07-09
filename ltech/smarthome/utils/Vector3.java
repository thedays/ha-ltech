package com.ltech.smarthome.utils;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.mozilla.javascript.ES6Iterator;

/* compiled from: XYToRGB.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0014\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 (2\u00020\u0001:\u0001(B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\f\u001a\u00020\u0003J\u0006\u0010\r\u001a\u00020\u0003J\u0006\u0010\u000e\u001a\u00020\u0000J\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0000J\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0000J\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0003J\u0006\u0010\u0014\u001a\u00020\u0000J\u000e\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0000J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0000J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u0000J\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00030\u001aJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\u001dJ\u0006\u0010\u001f\u001a\u00020\u001dJ\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J'\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020&HÖ\u0001J\t\u0010'\u001a\u00020\u001bHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006)"}, d2 = {"Lcom/ltech/smarthome/utils/Vector3;", "", "x", "", Constants.Y, "z", "<init>", "(FFF)V", "getX", "()F", "getY", "getZ", "length", "lengthSquared", "unit", "plus", "v", "minus", "timesScalar", ES6Iterator.VALUE_PROPERTY, "reversed", "dot", "cross", "equals", "", "toJson", "", "", "toArray", "", "toGlArray", "toArray4", "component1", "component2", "component3", "copy", DispatchConstants.OTHER, "hashCode", "", "toString", "Companion", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class Vector3 {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final float x;
    private final float y;
    private final float z;

    public static /* synthetic */ Vector3 copy$default(Vector3 vector3, float f, float f2, float f3, int i, Object obj) {
        if ((i & 1) != 0) {
            f = vector3.x;
        }
        if ((i & 2) != 0) {
            f2 = vector3.y;
        }
        if ((i & 4) != 0) {
            f3 = vector3.z;
        }
        return vector3.copy(f, f2, f3);
    }

    /* renamed from: component1, reason: from getter */
    public final float getX() {
        return this.x;
    }

    /* renamed from: component2, reason: from getter */
    public final float getY() {
        return this.y;
    }

    /* renamed from: component3, reason: from getter */
    public final float getZ() {
        return this.z;
    }

    public final Vector3 copy(float x, float y, float z) {
        return new Vector3(x, y, z);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector3)) {
            return false;
        }
        Vector3 vector3 = (Vector3) other;
        return Float.compare(this.x, vector3.x) == 0 && Float.compare(this.y, vector3.y) == 0 && Float.compare(this.z, vector3.z) == 0;
    }

    public int hashCode() {
        return (((Float.floatToIntBits(this.x) * 31) + Float.floatToIntBits(this.y)) * 31) + Float.floatToIntBits(this.z);
    }

    public String toString() {
        return "Vector3(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
    }

    public Vector3(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final float getZ() {
        return this.z;
    }

    /* compiled from: XYToRGB.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007J\u0010\u0010\t\u001a\u0004\u0018\u00010\u00052\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"Lcom/ltech/smarthome/utils/Vector3$Companion;", "", "<init>", "()V", "fromJson", "Lcom/ltech/smarthome/utils/Vector3;", "json", "", "", "fromArray", TmpConstant.TYPE_VALUE_ARRAY, "", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Vector3 fromJson(Map<String, ? extends Object> json) {
            Intrinsics.checkNotNullParameter(json, "json");
            Object obj = json.get("x");
            Float f = obj instanceof Float ? (Float) obj : null;
            Object obj2 = json.get(Constants.Y);
            Float f2 = obj2 instanceof Float ? (Float) obj2 : null;
            Object obj3 = json.get("z");
            Float f3 = obj3 instanceof Float ? (Float) obj3 : null;
            if (f == null || f2 == null || f3 == null) {
                return null;
            }
            return new Vector3(f.floatValue(), f2.floatValue(), f3.floatValue());
        }

        public final Vector3 fromArray(float[] array) {
            Intrinsics.checkNotNullParameter(array, "array");
            if (array.length != 3) {
                return null;
            }
            return new Vector3(array[0], array[1], array[2]);
        }
    }

    public final float length() {
        float f = this.x;
        float f2 = this.y;
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.z;
        return (float) Math.sqrt(f3 + (f4 * f4));
    }

    public final float lengthSquared() {
        float f = this.x;
        float f2 = this.y;
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.z;
        return f3 + (f4 * f4);
    }

    public final Vector3 unit() {
        float length = length();
        return new Vector3(this.x / length, this.y / length, this.z / length);
    }

    public final Vector3 plus(Vector3 v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public final Vector3 minus(Vector3 v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public final Vector3 timesScalar(float value) {
        return new Vector3(this.x * value, this.y * value, this.z * value);
    }

    public final Vector3 reversed() {
        return new Vector3(-this.x, -this.y, -this.z);
    }

    public final float dot(Vector3 v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return (this.x * v.x) + (this.y * v.y) + (this.z * v.z);
    }

    public final Vector3 cross(Vector3 v) {
        Intrinsics.checkNotNullParameter(v, "v");
        float f = this.y;
        float f2 = v.z;
        float f3 = this.z;
        float f4 = v.y;
        float f5 = (f * f2) - (f3 * f4);
        float f6 = v.x;
        float f7 = this.x;
        return new Vector3(f5, (f3 * f6) - (f2 * f7), (f7 * f4) - (f * f6));
    }

    public final boolean equals(Vector3 v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return this.x == v.x && this.y == v.y && this.z == v.z;
    }

    public final Map<String, Float> toJson() {
        return MapsKt.mapOf(TuplesKt.to("x", Float.valueOf(this.x)), TuplesKt.to(Constants.Y, Float.valueOf(this.y)), TuplesKt.to("z", Float.valueOf(this.z)));
    }

    public final float[] toArray() {
        return new float[]{this.x, this.y, this.z};
    }

    public final float[] toGlArray() {
        return toArray();
    }

    public final float[] toArray4() {
        return new float[]{this.x, this.y, this.z, 0.0f};
    }
}