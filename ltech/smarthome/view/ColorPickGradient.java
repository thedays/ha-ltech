package com.ltech.smarthome.view;

import android.graphics.Color;

/* loaded from: classes4.dex */
public class ColorPickGradient {
    private int[] mColorArr;
    private float[] mColorPosition;

    public float getAreaRadio(float radio, float startPosition, float endPosition) {
        return (radio - startPosition) / (endPosition - startPosition);
    }

    public ColorPickGradient(int[] colorArr, float[] mColorPosition) {
        this.mColorArr = colorArr;
        this.mColorPosition = mColorPosition;
    }

    public int getColor(float radio) {
        if (radio >= 1.0f) {
            return this.mColorArr[r7.length - 1];
        }
        int i = 0;
        while (true) {
            float[] fArr = this.mColorPosition;
            if (i >= fArr.length) {
                return -1;
            }
            float f = fArr[i];
            if (radio <= f) {
                if (i == 0) {
                    return this.mColorArr[0];
                }
                int[] iArr = this.mColorArr;
                int i2 = i - 1;
                return getColorFrom(iArr[i2], iArr[i], getAreaRadio(radio, fArr[i2], f));
            }
            i++;
        }
    }

    public int getColorFrom(int startColor, int endColor, float radio) {
        int red = Color.red(startColor);
        int blue = Color.blue(startColor);
        return Color.argb(255, (int) (red + ((Color.red(endColor) - red) * radio) + 0.5d), (int) (Color.green(startColor) + ((Color.green(endColor) - r11) * radio) + 0.5d), (int) (blue + ((Color.blue(endColor) - blue) * radio) + 0.5d));
    }
}