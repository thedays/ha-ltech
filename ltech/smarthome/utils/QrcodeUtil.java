package com.ltech.smarthome.utils;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Hashtable;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class QrcodeUtil {
    public static String[] decode(String result) {
        String[] strArr = {" ", StringUtils.CR, "\n", "\r\n", "\n\r"};
        for (int i = 0; i < 5; i++) {
            String[] split = result.split(strArr[i]);
            if (split != null && split.length >= 3) {
                return split;
            }
        }
        return null;
    }

    public static Bitmap createQRCodeBitmap(String content, int width, int height) {
        return createQRCodeBitmap(content, width, height, "UTF-8", "H", "2", -16777216, -1);
    }

    public static Bitmap createQRCodeBitmap(String content, int width, int height, String character_set, String error_correction, String margin, int color_black, int color_white) {
        if (!TextUtils.isEmpty(content) && width >= 0 && height >= 0) {
            try {
                Hashtable hashtable = new Hashtable();
                if (!TextUtils.isEmpty(character_set)) {
                    hashtable.put(EncodeHintType.CHARACTER_SET, character_set);
                }
                if (!TextUtils.isEmpty(error_correction)) {
                    hashtable.put(EncodeHintType.ERROR_CORRECTION, error_correction);
                }
                if (!TextUtils.isEmpty(margin)) {
                    hashtable.put(EncodeHintType.MARGIN, margin);
                }
                BitMatrix encode = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hashtable);
                int[] iArr = new int[width * height];
                for (int i = 0; i < height; i++) {
                    for (int i2 = 0; i2 < width; i2++) {
                        if (encode.get(i2, i)) {
                            iArr[(i * width) + i2] = color_black;
                        } else {
                            iArr[(i * width) + i2] = color_white;
                        }
                    }
                }
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
                return createBitmap;
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}