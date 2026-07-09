package com.ltech.smarthome.singleton;

import android.content.Context;
import com.ltech.smarthome.model.product.ProductId;
import java.io.File;

/* loaded from: classes4.dex */
public class PathManager {
    private static final String CAMERA_PIC_PERFIX = "camera_pic_";
    private static final String HEAD_PIC_PERFIX = "head_pic_";
    private static final String IMAGE_DIR = "/ltech/smarthome/image";
    private static final String SUPER_PANEL_PIC_PERFIX = "photo_";

    private PathManager() {
    }

    public static void createDir(Context context) {
        File file = new File(getCacheDir(context));
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public static String getHeadPicName(String tag) {
        return HEAD_PIC_PERFIX + tag;
    }

    public static String getSuperPanelPicName(String tag) {
        return SUPER_PANEL_PIC_PERFIX + tag + ProductId.SPLIT;
    }

    public static String getCameraPicName(String tag) {
        return CAMERA_PIC_PERFIX + tag + ".jpg";
    }

    public static String getCacheDir(Context context) {
        return context.getExternalCacheDir() + IMAGE_DIR;
    }
}