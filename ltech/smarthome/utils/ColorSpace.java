package com.ltech.smarthome.utils;

/* loaded from: classes4.dex */
public class ColorSpace {
    private final GammaCorrection gammaCorrection;
    private final Matrix3 matrix;
    public static final ColorSpace sRGB = new ColorSpace(new Matrix3(new float[]{0.412453f, 0.35758f, 0.180423f, 0.212671f, 0.71516f, 0.072169f, 0.019334f, 0.119193f, 0.950227f}), new NeedGammaCorrection(0.42f, 0.0031308f, 12.92f, 0.055f));
    public static final ColorSpace wide = new ColorSpace(new Matrix3(new float[]{0.7164f, 0.101f, 0.1468f, 0.2587f, 0.7247f, 0.0166f, 0.0f, 0.0512f, 0.774f}), new NoGammaCorrection());
    public static final ColorSpace adobeRGB = new ColorSpace(new Matrix3(new float[]{0.5767f, 0.1856f, 0.1882f, 0.2974f, 0.6273f, 0.0753f, 0.027f, 0.0707f, 0.9911f}), new NoGammaCorrection());

    public ColorSpace(Matrix3 matrix, GammaCorrection gammaCorrection) {
        this.matrix = matrix;
        this.gammaCorrection = gammaCorrection;
    }

    public Tuple3<Float> xyzFromColor(Color color) {
        float[] timesArray = this.matrix.timesArray(new float[]{this.gammaCorrection.invTransform(color.getR()), this.gammaCorrection.invTransform(color.getG()), this.gammaCorrection.invTransform(color.getB())});
        return new Tuple3<>(Float.valueOf(timesArray[0]), Float.valueOf(timesArray[1]), Float.valueOf(timesArray[2]));
    }

    public Tuple3<Float> xyYFromColor(Color color) {
        Tuple3<Float> xyzFromColor = xyzFromColor(color);
        float floatValue = xyzFromColor.getX().floatValue() + xyzFromColor.getY().floatValue() + xyzFromColor.getZ().floatValue();
        if (color.getR() < 1.0E-12d && color.getG() < 1.0E-12d && color.getB() < 1.0E-12d) {
            Tuple3<Float> xyzFromColor2 = xyzFromColor(Color.white());
            float floatValue2 = xyzFromColor2.getX().floatValue() + xyzFromColor2.getY().floatValue() + xyzFromColor2.getZ().floatValue();
            return new Tuple3<>(Float.valueOf(xyzFromColor2.getX().floatValue() / floatValue2), Float.valueOf(xyzFromColor2.getY().floatValue() / floatValue2), Float.valueOf(0.0f));
        }
        return new Tuple3<>(Float.valueOf(xyzFromColor.getX().floatValue() / floatValue), Float.valueOf(xyzFromColor.getY().floatValue() / floatValue), xyzFromColor.getY());
    }

    public Color colorFromXYZ(float x, float y, float z) {
        Matrix3 inversed = this.matrix.inversed();
        if (inversed == null) {
            return new Color(0.0f, 0.0f, 0.0f, 1.0f);
        }
        float[] timesArray = inversed.timesArray(new float[]{x, y, z});
        return new Color(this.gammaCorrection.transform(timesArray[0]), this.gammaCorrection.transform(timesArray[1]), this.gammaCorrection.transform(timesArray[2]), 1.0f);
    }

    public Color colorFromXYY(float x, float y, float Y) {
        float f = (1.0f - x) - y;
        float f2 = Y / y;
        return colorFromXYZ(x * f2, Y, f2 * f);
    }

    public float findMaximumY(float x, float y) {
        return findMaximumY(x, y, 10);
    }

    public float findMaximumY(float x, float y, int iterations) {
        float f = 1.0f;
        for (int i = 0; i < iterations; i++) {
            Color colorFromXYY = colorFromXYY(x, y, f);
            f /= Math.max(colorFromXYY.getR(), Math.max(colorFromXYY.getG(), colorFromXYY.getB()));
        }
        return f;
    }

    public static class Tuple3<T> {
        private final T x;
        private final T y;
        private final T z;

        public Tuple3(T x, T y, T z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public T getX() {
            return this.x;
        }

        public T getY() {
            return this.y;
        }

        public T getZ() {
            return this.z;
        }
    }

    public static class Color {

        /* renamed from: a, reason: collision with root package name */
        private final float f6276a;

        /* renamed from: b, reason: collision with root package name */
        private final float f6277b;
        private final float g;
        private final float r;

        public Color(float r, float g, float b2, float a2) {
            this.r = r;
            this.g = g;
            this.f6277b = b2;
            this.f6276a = a2;
        }

        public float getR() {
            return this.r;
        }

        public float getG() {
            return this.g;
        }

        public float getB() {
            return this.f6277b;
        }

        public static Color white() {
            return new Color(1.0f, 1.0f, 1.0f, 1.0f);
        }

        public int[] clamped() {
            return new int[]{(int) (Math.min(Math.max(this.r, 0.0f), 1.0f) * 255.0f), (int) (Math.min(Math.max(this.g, 0.0f), 1.0f) * 255.0f), (int) (Math.min(Math.max(this.f6277b, 0.0f), 1.0f) * 255.0f)};
        }
    }
}