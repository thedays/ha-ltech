package com.ltech.smarthome.ui.qrcode;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.imageclip.util.FileUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSaveQrCodeBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ThreadPoolManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActSaveQrCode extends BaseNormalActivity<ActSaveQrCodeBinding> {
    private static final int REQUEST_WRITE_STORAGE = 1;
    private Bitmap bitmap;
    private boolean isSave;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_save_qr_code;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.share_duv));
        setBackImage(R.mipmap.icon_back);
        showQrCode();
        ((ActSaveQrCodeBinding) this.mViewBinding).tvSave.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.qrcode.ActSaveQrCode$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSaveQrCode.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (!this.isSave && checkWriteStoragePermission(1)) {
            saveQrCode();
        }
    }

    private void saveQrCode() {
        showLoadingDialog(getString(R.string.saving));
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.qrcode.ActSaveQrCode$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ActSaveQrCode.this.lambda$saveQrCode$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveQrCode$2() {
        this.isSave = true;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        final boolean saveBitmap = FileUtil.saveBitmap(this, getScreenshotView(), "qrcode_" + simpleDateFormat.format(new Date()) + ".jpg");
        ThreadUtils.getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.qrcode.ActSaveQrCode$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ActSaveQrCode.this.lambda$saveQrCode$1(saveBitmap);
            }
        });
        this.isSave = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveQrCode$1(boolean z) {
        if (z) {
            showSuccessDialog(getString(R.string.save_success));
        } else {
            showErrorDialog(getString(R.string.save_fail));
        }
    }

    private void showQrCode() {
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.qrcode.ActSaveQrCode$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ActSaveQrCode.this.lambda$showQrCode$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showQrCode$3() {
        this.bitmap = QRCodeEncoder.syncEncodeQRCode(ShareHelper.INSTANCE.getQrCode(), SizeUtils.dp2px(200.0f));
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.qrcode.ActSaveQrCode$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ActSaveQrCode.this.showView();
            }
        }, 20L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showView() {
        ((ActSaveQrCodeBinding) this.mViewBinding).layoutContent.setVisibility(0);
        ((ActSaveQrCodeBinding) this.mViewBinding).ivQrCode.setImageBitmap(this.bitmap);
    }

    private Bitmap getScreenshotView() {
        RelativeLayout relativeLayout = ((ActSaveQrCodeBinding) this.mViewBinding).layoutContent;
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        return Bitmap.createBitmap(relativeLayout.getDrawingCache());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == 0) {
                saveQrCode();
            } else {
                SmartToast.showShort(getString(R.string.permission_deny));
            }
        }
    }
}