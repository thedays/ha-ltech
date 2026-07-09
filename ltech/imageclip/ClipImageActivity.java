package com.ltech.imageclip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import com.jaeger.library.StatusBarUtil;
import com.ltech.imageclip.fragment.ActivityResultHelper;

/* loaded from: classes3.dex */
public class ClipImageActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String FILE_TAG = "file_tag";
    public static final int REQ_CLIP_AVATAR = 50;
    private static final String TAG = "ClipImageActivity";
    public static final String TYPE = "type";
    private AppCompatTextView mBtnCancel;
    private AppCompatTextView mBtnOk;
    private ClipViewLayout mClipViewLayout;
    private String mFileTag;
    private int mType;

    public static void goToClipActivity(FragmentActivity fragmentActivity, Uri uri, String str, ActivityResultHelper.Callback callback) {
        if (uri == null) {
            return;
        }
        ActivityResultHelper.init(fragmentActivity).startActivityForResult(getClipIntent(fragmentActivity, uri, str), callback);
    }

    public static void goToClipActivity(FragmentActivity fragmentActivity, Uri uri, String str) {
        fragmentActivity.startActivityForResult(getClipIntent(fragmentActivity, uri, str), 50);
    }

    public static Intent getClipIntent(FragmentActivity fragmentActivity, Uri uri, String str) {
        Intent intent = new Intent();
        intent.setClass(fragmentActivity, ClipImageActivity.class);
        intent.putExtra("type", 3);
        intent.putExtra(FILE_TAG, str);
        intent.setData(uri);
        return intent;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_clip_image);
        this.mType = getIntent().getIntExtra("type", 1);
        this.mFileTag = getIntent().getStringExtra(FILE_TAG);
        Log.i(getClass().getSimpleName(), "onCreate: mType =" + this.mType);
        StatusBarUtil.setColor(this, -16777216);
        initView();
        this.mClipViewLayout.setClipType(this.mType);
    }

    public void initView() {
        this.mClipViewLayout = (ClipViewLayout) findViewById(R.id.clipViewLayout);
        this.mBtnCancel = (AppCompatTextView) findViewById(R.id.btn_cancel);
        this.mBtnOk = (AppCompatTextView) findViewById(R.id.bt_ok);
        this.mBtnCancel.setOnClickListener(this);
        this.mBtnOk.setOnClickListener(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mClipViewLayout.setVisibility(0);
        this.mClipViewLayout.setClipType(this.mType);
        this.mClipViewLayout.setImageSrc(getIntent().getData());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            finish();
        } else if (id == R.id.bt_ok) {
            generateUriAndReturn();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004e, code lost:
    
        if (r3 != null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0050, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0054, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0055, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0073, code lost:
    
        if (r3 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void generateUriAndReturn() {
        /*
            r6 = this;
            java.lang.String r0 = "Cannot open file: "
            com.ltech.imageclip.ClipViewLayout r1 = r6.mClipViewLayout
            android.graphics.Bitmap r1 = r1.clip()
            if (r1 != 0) goto L18
            java.lang.Class r0 = r6.getClass()
            java.lang.String r0 = r0.getSimpleName()
            java.lang.String r1 = "zoomedCropBitmap == null"
            android.util.Log.i(r0, r1)
            return
        L18:
            java.io.File r2 = new java.io.File
            java.io.File r3 = r6.getCacheDir()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "cropped_"
            r4.<init>(r5)
            java.lang.String r5 = r6.mFileTag
            r4.append(r5)
            java.lang.String r5 = ".jpg"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r2.<init>(r3, r4)
            android.net.Uri r2 = android.net.Uri.fromFile(r2)
            if (r2 == 0) goto L91
            r3 = 0
            android.content.ContentResolver r4 = r6.getContentResolver()     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b
            java.io.OutputStream r3 = r4.openOutputStream(r2)     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b
            if (r3 == 0) goto L4e
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b
            r5 = 90
            r1.compress(r4, r5, r3)     // Catch: java.lang.Throwable -> L59 java.io.IOException -> L5b
        L4e:
            if (r3 == 0) goto L76
        L50:
            r3.close()     // Catch: java.io.IOException -> L54
            goto L76
        L54:
            r0 = move-exception
            r0.printStackTrace()
            goto L76
        L59:
            r0 = move-exception
            goto L86
        L5b:
            r1 = move-exception
            java.lang.Class r4 = r6.getClass()     // Catch: java.lang.Throwable -> L59
            java.lang.String r4 = r4.getSimpleName()     // Catch: java.lang.Throwable -> L59
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L59
            r5.append(r2)     // Catch: java.lang.Throwable -> L59
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L59
            android.util.Log.i(r4, r0, r1)     // Catch: java.lang.Throwable -> L59
            if (r3 == 0) goto L76
            goto L50
        L76:
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            r0.setData(r2)
            r1 = -1
            r6.setResult(r1, r0)
            r6.finish()
            return
        L86:
            if (r3 == 0) goto L90
            r3.close()     // Catch: java.io.IOException -> L8c
            goto L90
        L8c:
            r1 = move-exception
            r1.printStackTrace()
        L90:
            throw r0
        L91:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.imageclip.ClipImageActivity.generateUriAndReturn():void");
    }
}