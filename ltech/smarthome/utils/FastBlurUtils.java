package com.ltech.smarthome.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.sun.jna.platform.win32.Winspool;
import java.lang.reflect.Array;

/* loaded from: classes4.dex */
public class FastBlurUtils {
    public static Bitmap startBlurBackground(Bitmap bkg) {
        return big(fastblur(small(bkg), (int) 20.0f));
    }

    private static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(4.0f, 4.0f);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#80000000"));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawRect(0.0f, 0.0f, createBitmap.getWidth(), createBitmap.getHeight(), paint);
        return createBitmap;
    }

    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.25f, 0.25f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static Bitmap addBlackLayer(Bitmap originalBitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(originalBitmap, 0.0f, 0.0f, (Paint) null);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#80000000"));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawRect(0.0f, 0.0f, originalBitmap.getWidth(), originalBitmap.getHeight(), paint);
        return createBitmap;
    }

    private static Bitmap fastblur(Bitmap sentBitmap, int radius) {
        int i;
        int i2 = radius;
        Bitmap copy = sentBitmap.copy(sentBitmap.getConfig(), true);
        if (i2 < 1) {
            return null;
        }
        int width = copy.getWidth();
        int height = copy.getHeight();
        int i3 = width * height;
        int[] iArr = new int[i3];
        copy.getPixels(iArr, 0, width, 0, 0, width, height);
        int i4 = width - 1;
        int i5 = height - 1;
        int i6 = i2 + i2;
        int i7 = i6 + 1;
        int[] iArr2 = new int[i3];
        int[] iArr3 = new int[i3];
        int[] iArr4 = new int[i3];
        int[] iArr5 = new int[Math.max(width, height)];
        int i8 = (i6 + 2) >> 1;
        int i9 = i8 * i8;
        int i10 = i9 * 256;
        int[] iArr6 = new int[i10];
        for (int i11 = 0; i11 < i10; i11++) {
            iArr6[i11] = i11 / i9;
        }
        int[][] iArr7 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i7, 3);
        int i12 = i2 + 1;
        int i13 = 0;
        int i14 = 0;
        int i15 = 0;
        while (i13 < height) {
            int[] iArr8 = iArr4;
            int i16 = -i2;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            int i25 = 0;
            while (i16 <= i2) {
                int[] iArr9 = iArr6;
                Bitmap bitmap = copy;
                int i26 = iArr[i14 + Math.min(i4, Math.max(i16, 0))];
                int[] iArr10 = iArr7[i16 + i2];
                iArr10[0] = (i26 & Winspool.PRINTER_ENUM_ICONMASK) >> 16;
                iArr10[1] = (i26 & 65280) >> 8;
                iArr10[2] = i26 & 255;
                int abs = i12 - Math.abs(i16);
                int i27 = iArr10[0];
                i17 += i27 * abs;
                int i28 = iArr10[1];
                i18 += i28 * abs;
                int i29 = iArr10[2];
                i19 += abs * i29;
                if (i16 > 0) {
                    i23 += i27;
                    i24 += i28;
                    i25 += i29;
                } else {
                    i20 += i27;
                    i21 += i28;
                    i22 += i29;
                }
                i16++;
                iArr6 = iArr9;
                copy = bitmap;
            }
            int[] iArr11 = iArr6;
            Bitmap bitmap2 = copy;
            int i30 = i2;
            int i31 = 0;
            while (i31 < width) {
                iArr2[i14] = iArr11[i17];
                iArr3[i14] = iArr11[i18];
                iArr8[i14] = iArr11[i19];
                int i32 = i17 - i20;
                int i33 = i18 - i21;
                int i34 = i19 - i22;
                int[] iArr12 = iArr7[((i30 - i2) + i7) % i7];
                int i35 = i20 - iArr12[0];
                int i36 = i21 - iArr12[1];
                int i37 = i22 - iArr12[2];
                if (i13 == 0) {
                    i = i31;
                    iArr5[i] = Math.min(i31 + i2 + 1, i4);
                } else {
                    i = i31;
                }
                int i38 = iArr[i15 + iArr5[i]];
                int i39 = (i38 & Winspool.PRINTER_ENUM_ICONMASK) >> 16;
                iArr12[0] = i39;
                int i40 = (i38 & 65280) >> 8;
                iArr12[1] = i40;
                int i41 = i38 & 255;
                iArr12[2] = i41;
                int i42 = i23 + i39;
                int i43 = i24 + i40;
                int i44 = i25 + i41;
                i17 = i32 + i42;
                i18 = i33 + i43;
                i19 = i34 + i44;
                i30 = (i30 + 1) % i7;
                int[] iArr13 = iArr7[i30 % i7];
                int i45 = iArr13[0];
                i20 = i35 + i45;
                int i46 = iArr13[1];
                i21 = i36 + i46;
                int i47 = iArr13[2];
                i22 = i37 + i47;
                i23 = i42 - i45;
                i24 = i43 - i46;
                i25 = i44 - i47;
                i14++;
                i31 = i + 1;
            }
            i15 += width;
            i13++;
            iArr4 = iArr8;
            iArr6 = iArr11;
            copy = bitmap2;
        }
        int[] iArr14 = iArr4;
        int[] iArr15 = iArr6;
        Bitmap bitmap3 = copy;
        int i48 = 0;
        while (i48 < width) {
            int i49 = -i2;
            int i50 = i49 * width;
            int i51 = 0;
            int i52 = 0;
            int i53 = 0;
            int i54 = 0;
            int i55 = 0;
            int i56 = 0;
            int i57 = 0;
            int i58 = 0;
            int i59 = 0;
            while (i49 <= i2) {
                int max = Math.max(0, i50) + i48;
                int[] iArr16 = iArr7[i49 + radius];
                iArr16[0] = iArr2[max];
                iArr16[1] = iArr3[max];
                iArr16[2] = iArr14[max];
                int abs2 = i12 - Math.abs(i49);
                i51 += iArr2[max] * abs2;
                i52 += iArr3[max] * abs2;
                i53 += iArr14[max] * abs2;
                if (i49 > 0) {
                    i57 += iArr16[0];
                    i58 += iArr16[1];
                    i59 += iArr16[2];
                } else {
                    i54 += iArr16[0];
                    i55 += iArr16[1];
                    i56 += iArr16[2];
                }
                if (i49 < i5) {
                    i50 += width;
                }
                i49++;
                i2 = radius;
            }
            int i60 = radius;
            int i61 = i48;
            int i62 = 0;
            while (i62 < height) {
                iArr[i61] = (iArr[i61] & (-16777216)) | (iArr15[i51] << 16) | (iArr15[i52] << 8) | iArr15[i53];
                int i63 = i51 - i54;
                int i64 = i52 - i55;
                int i65 = i53 - i56;
                int[] iArr17 = iArr7[((i60 - radius) + i7) % i7];
                int i66 = i54 - iArr17[0];
                int i67 = i55 - iArr17[1];
                int i68 = i56 - iArr17[2];
                int i69 = i62;
                if (i48 == 0) {
                    iArr5[i69] = Math.min(i69 + i12, i5) * width;
                }
                int i70 = iArr5[i69] + i48;
                int i71 = iArr2[i70];
                iArr17[0] = i71;
                int i72 = iArr3[i70];
                iArr17[1] = i72;
                int i73 = iArr14[i70];
                iArr17[2] = i73;
                int i74 = i57 + i71;
                int i75 = i58 + i72;
                int i76 = i59 + i73;
                i51 = i63 + i74;
                i52 = i64 + i75;
                i53 = i65 + i76;
                i60 = (i60 + 1) % i7;
                int[] iArr18 = iArr7[i60];
                int i77 = iArr18[0];
                i54 = i66 + i77;
                int i78 = iArr18[1];
                i55 = i67 + i78;
                int i79 = iArr18[2];
                i56 = i68 + i79;
                i57 = i74 - i77;
                i58 = i75 - i78;
                i59 = i76 - i79;
                i61 += width;
                i62 = i69 + 1;
            }
            i48++;
            i2 = radius;
        }
        bitmap3.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmap3;
    }
}