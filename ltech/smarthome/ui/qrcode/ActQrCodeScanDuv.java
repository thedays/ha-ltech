package com.ltech.smarthome.ui.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import com.ltech.imageclip.util.FileUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActQrCodeScanBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.qrcode.ShareHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActQrCodeScanDuv extends BaseNormalActivity<ActQrCodeScanBinding> implements QRCodeView.Delegate {
    private static final int REQUEST_READ_STORAGE = 1;
    private int count;
    private int sampleSize;
    private List<ShareHelper.ShareData> importList = new ArrayList();
    private MutableLiveData<Integer> importIndex = new MutableLiveData<>();

    @Override // cn.bingoogolapple.qrcode.core.QRCodeView.Delegate
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_qr_code_scan;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.scan_qr_code));
        setBackImage(R.mipmap.icon_back_white);
        setEditString(getString(R.string.album));
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.setDelegate(this);
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.getScanBoxView().setTipText(getString(R.string.scan_duv_tip));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.importIndex.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.qrcode.ActQrCodeScanDuv$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActQrCodeScanDuv.this.lambda$startObserve$0((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Integer num) {
        if (num.intValue() < this.importList.size()) {
            importDuv(this.importList.get(num.intValue()));
            return;
        }
        showSuccessDialog(getString(R.string.import_duv_success, new Object[]{Integer.valueOf(this.count)}));
        setResult(-1);
        finishActivity();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.startCamera();
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.startSpotAndShowRect();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.stopCamera();
        super.onStop();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.onDestroy();
        super.onDestroy();
    }

    @Override // cn.bingoogolapple.qrcode.core.QRCodeView.Delegate
    public void onScanQRCodeSuccess(String result) {
        LHomeLog.i(getClass(), ">>>scan result<<<" + result);
        if (result.startsWith(ShareHelper.PREFIX_DUV)) {
            List<ShareHelper.ShareData> decodeQrCode = ShareHelper.INSTANCE.decodeQrCode(result);
            this.importList = decodeQrCode;
            if (decodeQrCode.isEmpty()) {
                ((ActQrCodeScanBinding) this.mViewBinding).zxingview.startSpot();
                return;
            }
            ShareHelper.INSTANCE.setQrCode(result);
            showLoadingDialog(getString(R.string.saving));
            this.importIndex.setValue(0);
            return;
        }
        SmartToast.showShort(getString(R.string.app_str_canot_identify_qr_code));
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.startSpot();
    }

    @Override // cn.bingoogolapple.qrcode.core.QRCodeView.Delegate
    public void onScanQRCodeOpenCameraError() {
        SmartToast.showShort(R.string.open_camera_error);
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        goPic();
    }

    private void goPic() {
        if (checkWriteStoragePermission(1)) {
            pickPic();
        }
    }

    private void pickPic() {
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 3024);
    }

    private void readQrCode(final String path) {
        showLoadingDialog(getString(R.string.loading));
        this.sampleSize = 4;
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.ltech.smarthome.ui.qrcode.ActQrCodeScanDuv$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActQrCodeScanDuv.this.lambda$readQrCode$3(path);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readQrCode$3(String str) {
        while (this.sampleSize > 0) {
            String syncDecodeQRCode = QRCodeDecoder.syncDecodeQRCode(getDecodeBitmap(str));
            if (syncDecodeQRCode != null) {
                if (syncDecodeQRCode.startsWith(ShareHelper.PREFIX_DUV)) {
                    this.sampleSize = 0;
                    List<ShareHelper.ShareData> decodeQrCode = ShareHelper.INSTANCE.decodeQrCode(syncDecodeQRCode);
                    this.importList = decodeQrCode;
                    if (decodeQrCode.isEmpty()) {
                        return;
                    }
                    ShareHelper.INSTANCE.setQrCode(syncDecodeQRCode);
                    this.count = 0;
                    this.importIndex.postValue(0);
                    return;
                }
                getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.qrcode.ActQrCodeScanDuv$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActQrCodeScanDuv.this.lambda$readQrCode$1();
                    }
                });
            } else {
                int i = this.sampleSize;
                if (i > 1) {
                    this.sampleSize = i - 1;
                } else {
                    this.sampleSize = 0;
                    getMainHandler().post(new Runnable() { // from class: com.ltech.smarthome.ui.qrcode.ActQrCodeScanDuv$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            ActQrCodeScanDuv.this.lambda$readQrCode$2();
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readQrCode$1() {
        dismissLoadingDialog();
        SmartToast.showShort(getString(R.string.app_str_canot_identify_qr_code));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readQrCode$2() {
        dismissLoadingDialog();
        SmartToast.showShort(R.string.read_fail);
    }

    public Bitmap getDecodeBitmap(String picturePath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = this.sampleSize;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(picturePath, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void importDuv(ShareHelper.ShareData shareData) {
        final ModeContent convertShareDataToDuv = ShareHelper.INSTANCE.convertShareDataToDuv(shareData);
        ((ObservableSubscribeProxy) Injection.net().addMode(convertShareDataToDuv.getModeName(), Injection.repo().home().getSelectPlace().getValue().getPlaceId(), 20, convertShareDataToDuv.getModeType(), convertShareDataToDuv.getModeIndex(), 0, convertShareDataToDuv.getContent(), "", -1).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.qrcode.ActQrCodeScanDuv$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActQrCodeScanDuv.this.lambda$importDuv$4(convertShareDataToDuv, obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.qrcode.ActQrCodeScanDuv.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                ActQrCodeScanDuv.this.importIndex.setValue(Integer.valueOf(((Integer) ActQrCodeScanDuv.this.importIndex.getValue()).intValue() + 1));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$importDuv$4(ModeContent modeContent, Object obj) throws Exception {
        this.count++;
        ShareHelper.INSTANCE.getDuvList().add(modeContent);
        MutableLiveData<Integer> mutableLiveData = this.importIndex;
        mutableLiveData.setValue(Integer.valueOf(mutableLiveData.getValue().intValue() + 1));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == 0) {
                goPic();
            } else {
                SmartToast.showShort(getString(R.string.permission_deny));
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3024 && resultCode == -1) {
            readQrCode(FileUtil.getRealFilePathFromUri(this, data.getData()));
        }
    }
}