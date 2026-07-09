package com.ltech.imageclip.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/* loaded from: classes3.dex */
public class FileUtil {
    private static final String TAG = "FileUtil";

    public static String getRealFilePathFromUri(Context context, Uri uri) {
        Cursor query;
        int columnIndex;
        String str = null;
        if (uri == null) {
            return null;
        }
        String scheme = uri.getScheme();
        if (scheme == null) {
            return uri.getPath();
        }
        if ("file".equalsIgnoreCase(scheme)) {
            return uri.getPath();
        }
        if ("content".equalsIgnoreCase(scheme) && (query = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null)) != null) {
            if (query.moveToFirst() && (columnIndex = query.getColumnIndex("_data")) > -1) {
                str = query.getString(columnIndex);
            }
            query.close();
        }
        return str;
    }

    public static String checkDirPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str;
    }

    public static String saveBitmapToSDCard(Bitmap bitmap, String str) {
        String str2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "myPic" + File.separator;
        Log.i("BitmapUtil", "saveBitmapToSDCard: newFile = " + new File(str2).mkdirs());
        String str3 = str2 + ("img-" + str + ".jpg");
        Log.i("BitmapUtil", "saveBitmapToSDCard: path =" + str3);
        try {
            new File(str3);
            FileOutputStream fileOutputStream = new FileOutputStream(str3);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            return str3;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveBitmap(Context context, Bitmap bitmap, String str) {
        String str2;
        if (Build.BRAND.equalsIgnoreCase("Xiaomi")) {
            str2 = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + str;
        } else if (Build.BRAND.equalsIgnoreCase("Huawei")) {
            str2 = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + str;
        } else {
            str2 = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + str;
        }
        File file = new File(str2);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                return false;
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void saveCameraBitmap(Context context, Bitmap bitmap, String str) {
        File file = new File(str);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                    context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            e = e4;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0031 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap getCameraBitmap(java.lang.String r2) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r2)
            r2 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L1a java.io.FileNotFoundException -> L1e
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L1a java.io.FileNotFoundException -> L1e
            android.graphics.Bitmap r2 = android.graphics.BitmapFactory.decodeStream(r1)     // Catch: java.io.FileNotFoundException -> L18 java.lang.Throwable -> L2e
            r1.close()     // Catch: java.io.IOException -> L13
            return r2
        L13:
            r0 = move-exception
            r0.printStackTrace()
            return r2
        L18:
            r0 = move-exception
            goto L20
        L1a:
            r0 = move-exception
            r1 = r2
            r2 = r0
            goto L2f
        L1e:
            r0 = move-exception
            r1 = r2
        L20:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L2e
            if (r1 == 0) goto L2d
            r1.close()     // Catch: java.io.IOException -> L29
            goto L2d
        L29:
            r0 = move-exception
            r0.printStackTrace()
        L2d:
            return r2
        L2e:
            r2 = move-exception
        L2f:
            if (r1 == 0) goto L39
            r1.close()     // Catch: java.io.IOException -> L35
            goto L39
        L35:
            r0 = move-exception
            r0.printStackTrace()
        L39:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.imageclip.util.FileUtil.getCameraBitmap(java.lang.String):android.graphics.Bitmap");
    }

    public static String getCameraPath() {
        if (Build.BRAND.equalsIgnoreCase("Xiaomi")) {
            return Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
        }
        if (Build.BRAND.equalsIgnoreCase("Huawei")) {
            return Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
        }
        return Environment.getExternalStorageDirectory().getPath() + "/DCIM/";
    }

    public static String getEzShotFilePath(Context context) {
        String str = getCameraPath() + "/ezImage";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str + "/" + System.currentTimeMillis() + ".jpg";
    }

    public static String getEzRecordFilePath(Context context) {
        String str = context.getExternalFilesDir("").getPath() + "/video";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return str + "/" + System.currentTimeMillis() + ".mp4";
    }

    public static long getFileSize(String str) {
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            return 0L;
        }
        return new File(str).length();
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0051 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readAssets(android.content.Context r4, java.lang.String r5) {
        /*
            r0 = 0
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3d
            java.io.InputStream r4 = r4.open(r5)     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3d
            r5 = 1024(0x400, float:1.435E-42)
            byte[] r5 = new byte[r5]     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            r1.<init>()     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
        L12:
            int r2 = r4.read(r5)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            r3 = -1
            if (r2 != r3) goto L34
            r4.close()     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            r1.close()     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            java.lang.String r5 = new java.lang.String     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            byte[] r1 = r1.toByteArray()     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            r5.<init>(r1)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            if (r4 == 0) goto L32
            r4.close()     // Catch: java.io.IOException -> L2e
            goto L32
        L2e:
            r4 = move-exception
            r4.printStackTrace()
        L32:
            r0 = r5
            goto L4c
        L34:
            r3 = 0
            r1.write(r5, r3, r2)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L4d
            goto L12
        L39:
            r5 = move-exception
            goto L3f
        L3b:
            r5 = move-exception
            goto L4f
        L3d:
            r5 = move-exception
            r4 = r0
        L3f:
            r5.printStackTrace()     // Catch: java.lang.Throwable -> L4d
            if (r4 == 0) goto L4c
            r4.close()     // Catch: java.io.IOException -> L48
            goto L4c
        L48:
            r4 = move-exception
            r4.printStackTrace()
        L4c:
            return r0
        L4d:
            r5 = move-exception
            r0 = r4
        L4f:
            if (r0 == 0) goto L59
            r0.close()     // Catch: java.io.IOException -> L55
            goto L59
        L55:
            r4 = move-exception
            r4.printStackTrace()
        L59:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.imageclip.util.FileUtil.readAssets(android.content.Context, java.lang.String):java.lang.String");
    }

    public static void saveVideo2Album(final Context context, final File file) {
        new Thread(new Runnable() { // from class: com.ltech.imageclip.util.FileUtil$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                FileUtil.lambda$saveVideo2Album$0(context, file);
            }
        }).start();
    }

    static /* synthetic */ void lambda$saveVideo2Album$0(Context context, File file) {
        Uri insert;
        Path path;
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues videoContentValues = getVideoContentValues(context, file, System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= 31) {
            insert = contentResolver.insert(MediaStore.Video.Media.getContentUri("external_primary"), videoContentValues);
        } else {
            insert = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoContentValues);
        }
        try {
            if (Build.VERSION.SDK_INT >= 30) {
                OutputStream openOutputStream = contentResolver.openOutputStream(insert);
                path = file.toPath();
                Files.copy(path, openOutputStream);
            }
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", insert));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ContentValues getVideoContentValues(Context context, File file, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", file.getName());
        contentValues.put("_display_name", file.getName());
        contentValues.put("mime_type", "video/mp4");
        contentValues.put("datetaken", Long.valueOf(j));
        contentValues.put("date_modified", Long.valueOf(j));
        contentValues.put("date_added", Long.valueOf(j));
        if (Build.VERSION.SDK_INT >= 29) {
            contentValues.put("relative_path", Environment.DIRECTORY_DCIM + File.separator + context.getPackageName());
        } else {
            contentValues.put("_data", file.getAbsolutePath());
        }
        contentValues.put("_size", Long.valueOf(file.length()));
        return contentValues;
    }
}