package com.ltech.smarthome.utils;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: XYToRGB.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u001b\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 12\u00020\u0001:\u00011B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\r\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0010\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\u0012\u001a\u00020\u0003J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0016\u001a\u00020\tJ\u0006\u0010\u0017\u001a\u00020\u0000J\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u0000J\u000e\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0003J\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000fJ\u000e\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0003J\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000fJ\u0006\u0010 \u001a\u00020\u0000J\b\u0010!\u001a\u0004\u0018\u00010\u0000J\u0006\u0010\"\u001a\u00020\tJ\u000e\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\tJ\u000e\u0010%\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u000fJ\u000e\u0010&\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u000fJ\u0006\u0010'\u001a\u00020\u0003J\u0018\u0010(\u001a\u00020\t2\u0006\u0010)\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u000bH\u0002J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\u0013\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010-\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010.\u001a\u00020\u000bHÖ\u0001J\t\u0010/\u001a\u000200HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u00062"}, d2 = {"Lcom/ltech/smarthome/utils/Matrix3;", "", "elements", "", "<init>", "([F)V", "getElements", "()[F", "e", "", "x", "", Constants.Y, "row", "rowVector", "Lcom/ltech/smarthome/utils/Vector3;", "column", "columnVector", "diagonal", "equals", "", "that", "precision", "clone", "times", "matrix", "timesArray", TmpConstant.TYPE_VALUE_ARRAY, "timesVector", "vector", "arrayTimes", "vectorTimes", "transposed", "inversed", "determinant", "rotated", "angle", "translated", "scaled", "toJson", "det2", "x0", "x1", "component1", "copy", DispatchConstants.OTHER, "hashCode", "toString", "", "Companion", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class Matrix3 {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final float[] elements;

    public static /* synthetic */ Matrix3 copy$default(Matrix3 matrix3, float[] fArr, int i, Object obj) {
        if ((i & 1) != 0) {
            fArr = matrix3.elements;
        }
        return matrix3.copy(fArr);
    }

    /* renamed from: component1, reason: from getter */
    public final float[] getElements() {
        return this.elements;
    }

    public final Matrix3 copy(float[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return new Matrix3(elements);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Matrix3) && Intrinsics.areEqual(this.elements, ((Matrix3) other).elements);
    }

    public int hashCode() {
        return Arrays.hashCode(this.elements);
    }

    public String toString() {
        return "Matrix3(elements=" + Arrays.toString(this.elements) + ")";
    }

    public Matrix3(float[] elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        this.elements = elements;
        if (elements.length != 9) {
            throw new IllegalArgumentException("Matrix3 elements must be of size 9".toString());
        }
    }

    public final float[] getElements() {
        return this.elements;
    }

    /* compiled from: XYToRGB.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u0006\u0010\t\u001a\u00020\u0005J\u001e\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\fJ\u000e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\fJ\u000e\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015¨\u0006\u0016"}, d2 = {"Lcom/ltech/smarthome/utils/Matrix3$Companion;", "", "<init>", "()V", "fromJson", "Lcom/ltech/smarthome/utils/Matrix3;", "json", "", "defaultValue", "createIdentity", "createWithColumnVectors", "c1", "Lcom/ltech/smarthome/utils/Vector3;", "c2", "c3", "createWithColumnVectors2", "createTranslation", "vector", "createScaling", "createRotation", "angle", "", "app_yingyongbaoRelease"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Matrix3 fromJson(float[] json, Matrix3 defaultValue) {
            Intrinsics.checkNotNullParameter(json, "json");
            Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
            if (json.length != 9) {
                return defaultValue;
            }
            for (float f : json) {
                if (Math.abs(f) > Float.MAX_VALUE) {
                    throw new IllegalArgumentException("All elements must be finite".toString());
                }
            }
            return new Matrix3(json);
        }

        public final Matrix3 createIdentity() {
            return new Matrix3(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f});
        }

        public final Matrix3 createWithColumnVectors(Vector3 c1, Vector3 c2, Vector3 c3) {
            Intrinsics.checkNotNullParameter(c1, "c1");
            Intrinsics.checkNotNullParameter(c2, "c2");
            Intrinsics.checkNotNullParameter(c3, "c3");
            return new Matrix3(new float[]{c1.getX(), c2.getX(), c3.getX(), c1.getY(), c2.getY(), c3.getY(), c1.getZ(), c2.getZ(), c3.getZ()});
        }

        public final Matrix3 createWithColumnVectors2(Vector3 c1, Vector3 c2) {
            Intrinsics.checkNotNullParameter(c1, "c1");
            Intrinsics.checkNotNullParameter(c2, "c2");
            return new Matrix3(new float[]{c1.getX(), c2.getX(), 0.0f, c1.getY(), c2.getY(), 0.0f, 0.0f, 0.0f, 1.0f});
        }

        public final Matrix3 createTranslation(Vector3 vector) {
            Intrinsics.checkNotNullParameter(vector, "vector");
            return new Matrix3(new float[]{1.0f, 0.0f, vector.getX(), 0.0f, 1.0f, vector.getY(), 0.0f, 0.0f, 1.0f});
        }

        public final Matrix3 createScaling(Vector3 vector) {
            Intrinsics.checkNotNullParameter(vector, "vector");
            return new Matrix3(new float[]{vector.getX(), 0.0f, 0.0f, 0.0f, vector.getY(), 0.0f, 0.0f, 0.0f, 1.0f});
        }

        public final Matrix3 createRotation(float angle) {
            double d2 = angle;
            float cos = (float) Math.cos(d2);
            float sin = (float) Math.sin(d2);
            return new Matrix3(new float[]{cos, -sin, 0.0f, sin, cos, 0.0f, 0.0f, 0.0f, 1.0f});
        }
    }

    public final float e(int x, int y) {
        return this.elements[(y * 3) + x];
    }

    public final float[] row(int y) {
        return ArraysKt.copyOfRange(this.elements, y * 3, (y + 1) * 3);
    }

    public final Vector3 rowVector(int y) {
        float[] fArr = this.elements;
        int i = y * 3;
        return new Vector3(fArr[i], fArr[i + 1], fArr[i + 2]);
    }

    public final float[] column(int x) {
        float[] fArr = this.elements;
        return new float[]{fArr[x], fArr[x + 3], fArr[x + 6]};
    }

    public final Vector3 columnVector(int x) {
        float[] fArr = this.elements;
        return new Vector3(fArr[x], fArr[x + 3], fArr[x + 6]);
    }

    public final float[] diagonal() {
        float[] fArr = this.elements;
        return new float[]{fArr[0], fArr[4], fArr[8]};
    }

    public static /* synthetic */ boolean equals$default(Matrix3 matrix3, Matrix3 matrix32, float f, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 0.0f;
        }
        return matrix3.equals(matrix32, f);
    }

    public final boolean equals(Matrix3 that, float precision) {
        Intrinsics.checkNotNullParameter(that, "that");
        int length = this.elements.length;
        for (int i = 0; i < length; i++) {
            if (Math.abs(this.elements[i] - that.elements[i]) > precision) {
                return false;
            }
        }
        return true;
    }

    public final Matrix3 clone() {
        float[] fArr = this.elements;
        float[] copyOf = Arrays.copyOf(fArr, fArr.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
        return new Matrix3(copyOf);
    }

    public final Matrix3 times(Matrix3 matrix) {
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        float[] fArr = new float[9];
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = i * 3;
                int i4 = i3 + i2;
                fArr[i4] = 0.0f;
                for (int i5 = 0; i5 < 3; i5++) {
                    fArr[i4] = fArr[i4] + (this.elements[i3 + i5] * matrix.elements[(i5 * 3) + i2]);
                }
            }
        }
        return new Matrix3(fArr);
    }

    public final float[] timesArray(float[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        float[] fArr = new float[3];
        for (int i = 0; i < 3; i++) {
            float f = 0.0f;
            for (int i2 = 0; i2 < 3; i2++) {
                f += this.elements[(i * 3) + i2] * array[i2];
            }
            fArr[i] = f;
        }
        return fArr;
    }

    public final Vector3 timesVector(Vector3 vector) {
        Intrinsics.checkNotNullParameter(vector, "vector");
        return new Vector3((this.elements[0] * vector.getX()) + (this.elements[1] * vector.getY()) + (this.elements[2] * vector.getZ()), (this.elements[3] * vector.getX()) + (this.elements[4] * vector.getY()) + (this.elements[5] * vector.getZ()), (this.elements[6] * vector.getX()) + (this.elements[7] * vector.getY()) + (this.elements[8] * vector.getZ()));
    }

    public final float[] arrayTimes(float[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        float[] fArr = new float[3];
        for (int i = 0; i < 3; i++) {
            float f = 0.0f;
            for (int i2 = 0; i2 < 3; i2++) {
                f += array[i2] * this.elements[(i2 * 3) + i];
            }
            fArr[i] = f;
        }
        return fArr;
    }

    public final Vector3 vectorTimes(Vector3 vector) {
        Intrinsics.checkNotNullParameter(vector, "vector");
        return new Vector3((vector.getX() * this.elements[0]) + (vector.getY() * this.elements[3]) + (vector.getZ() * this.elements[6]), (vector.getX() * this.elements[1]) + (vector.getY() * this.elements[4]) + (vector.getZ() * this.elements[7]), (vector.getX() * this.elements[2]) + (vector.getY() * this.elements[5]) + (vector.getZ() * this.elements[8]));
    }

    public final Matrix3 transposed() {
        float[] fArr = new float[9];
        for (int i = 0; i < 3; i++) {
            for (int i2 = 0; i2 < 3; i2++) {
                fArr[(i * 3) + i2] = this.elements[(i2 * 3) + i];
            }
        }
        return new Matrix3(fArr);
    }

    public final Matrix3 inversed() {
        Matrix3 clone = clone();
        Matrix3 createIdentity = INSTANCE.createIdentity();
        int i = 0;
        while (i < 3) {
            int i2 = i * 3;
            int i3 = i2 + i;
            float f = clone.elements[i3];
            if (f == 0.0f) {
                int i4 = i + 1;
                while (i4 < 3 && Math.abs(clone.elements[(i4 * 3) + i]) < 1.0E-10d) {
                    i4++;
                }
                if (i4 == 3) {
                    return null;
                }
                for (int i5 = 0; i5 < 3; i5++) {
                    float[] fArr = clone.elements;
                    int i6 = i2 + i5;
                    int i7 = (i4 * 3) + i5;
                    fArr[i6] = fArr[i6] + fArr[i7];
                    float[] fArr2 = createIdentity.elements;
                    fArr2[i6] = fArr2[i6] + fArr2[i7];
                }
                f = clone.elements[i3];
            }
            float f2 = 1 / f;
            for (int i8 = 0; i8 < 3; i8++) {
                float[] fArr3 = clone.elements;
                int i9 = i2 + i8;
                fArr3[i9] = fArr3[i9] * f2;
                float[] fArr4 = createIdentity.elements;
                fArr4[i9] = fArr4[i9] * f2;
            }
            int i10 = i + 1;
            for (int i11 = i10; i11 < 3; i11++) {
                int i12 = i11 * 3;
                float f3 = clone.elements[i12 + i];
                if (f3 != 0.0f) {
                    for (int i13 = 0; i13 < 3; i13++) {
                        float[] fArr5 = clone.elements;
                        int i14 = i12 + i13;
                        int i15 = i2 + i13;
                        fArr5[i14] = fArr5[i14] - (fArr5[i15] * f3);
                        float[] fArr6 = createIdentity.elements;
                        fArr6[i14] = fArr6[i14] - (fArr6[i15] * f3);
                    }
                }
            }
            i = i10;
        }
        for (int i16 = 2; -1 < i16; i16--) {
            for (int i17 = 0; i17 < i16; i17++) {
                int i18 = i17 * 3;
                float f4 = clone.elements[i18 + i16];
                for (int i19 = 0; i19 < 3; i19++) {
                    float[] fArr7 = clone.elements;
                    int i20 = i18 + i19;
                    int i21 = (i16 * 3) + i19;
                    fArr7[i20] = fArr7[i20] - (fArr7[i21] * f4);
                    float[] fArr8 = createIdentity.elements;
                    fArr8[i20] = fArr8[i20] - (fArr8[i21] * f4);
                }
            }
        }
        return createIdentity;
    }

    public final float determinant() {
        return ((this.elements[0] * det2(1, 2)) - (this.elements[1] * det2(0, 2))) + (this.elements[2] * det2(0, 1));
    }

    public final Matrix3 rotated(float angle) {
        return INSTANCE.createRotation(angle).times(this);
    }

    public final Matrix3 translated(Vector3 vector) {
        Intrinsics.checkNotNullParameter(vector, "vector");
        return INSTANCE.createTranslation(vector).times(this);
    }

    public final Matrix3 scaled(Vector3 vector) {
        Intrinsics.checkNotNullParameter(vector, "vector");
        return INSTANCE.createScaling(vector).times(this);
    }

    public final float[] toJson() {
        return this.elements;
    }

    private final float det2(int x0, int x1) {
        float[] fArr = this.elements;
        return (fArr[x0 + 3] * fArr[x1 + 6]) - (fArr[x1 + 3] * fArr[x0 + 6]);
    }
}