package com.ltech.imageclip.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import java.io.FileDescriptor;

/* loaded from: classes3.dex */
public class BitmapUtil {
    private static final String TAG = "BitmapUtil";

    public static int[] getImageWidthHeight(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static int[] getImageWidthHeight(FileDescriptor fileDescriptor) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static Bitmap zoomImg(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(i / width, i2 / height);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap decodeSampledBitmap(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(str, options);
        int calculateInSampleSize = calculateInSampleSize(options, i, i2);
        Log.i(TAG, "decodeSampledBitmap: inSampleSize = " + calculateInSampleSize);
        options.inSampleSize = calculateInSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public static Bitmap decodeSampledBitmap(FileDescriptor fileDescriptor, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        int calculateInSampleSize = calculateInSampleSize(options, i, i2);
        Log.i(TAG, "decodeSampledBitmap: inSampleSize = " + calculateInSampleSize);
        options.inSampleSize = calculateInSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(i3 / i2);
        int round2 = Math.round(i4 / i);
        if (round >= round2) {
            round = round2;
        }
        if (round >= 3) {
            if (round < 6.5d) {
                return 4;
            }
            if (round < 8) {
                return 8;
            }
        }
        return round;
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float f = i / width;
        float f2 = i2 / height;
        Log.i(TAG, "zoomBitmap: scaleWidth =" + f + " scaleHeight = " + f2);
        matrix.postScale(f, f2);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }
}