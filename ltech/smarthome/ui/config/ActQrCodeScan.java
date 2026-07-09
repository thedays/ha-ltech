package com.ltech.smarthome.ui.config;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActQrCodeScanBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.camera.EZManager;
import com.ltech.smarthome.ui.device.camera.EzDevice;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.HelpUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.QrcodeUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.Utils;
import com.masget.api.security.AesEncryption;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import java.io.IOException;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class ActQrCodeScan extends BaseNormalActivity<ActQrCodeScanBinding> implements QRCodeView.Delegate {
    private static final int PICK_IMAGE_REQUEST = 1;

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
        setEditString(getString(R.string.album));
        setBackImage(R.mipmap.icon_back_white);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.setDelegate(this);
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
        LHomeLog.d(getClass(), ">>>scan result<<<" + result);
        HelpUtils.vibrate(200L);
        if (AppUtils.isAppDebug() && result.startsWith("MT:")) {
            setResult(-1, new Intent().putExtra("MT", result));
            finishActivity();
            return;
        }
        if (checkResult(result)) {
            String decryptResult = getDecryptResult(result, Constants.AES_DEFAULT_KEY);
            LHomeLog.d(getClass(), ">>>decodeResult<<<" + decryptResult);
            String decryptResultPlaceName = getDecryptResultPlaceName(decryptResult);
            int parseInt = Integer.parseInt(getDecryptResultType(decryptResult));
            if (parseInt == 1) {
                NavUtils.destination(ActQrCodeScanResult.class).withString(Constants.QR_CODE_INFO, result).withString(Constants.PLACE_NAME, decryptResultPlaceName).navigation(this);
                return;
            }
            if (parseInt == 2) {
                if (!Injection.repo().home().checkPlaceList()) {
                    showNoHomeMessage();
                    return;
                }
                ConfigHelper.instance().panelinfo = result;
                ConfigHelper.instance().mac = getDecryptResultMac(decryptResult);
                ConfigHelper.instance().productInfo = ProductRepository.getProductInfoByPid(ProductId.ID_ANDROID_SUPER_PANEL);
                NavUtils.destination(ActConfigSuccess.class).navigation(this);
                return;
            }
            if (parseInt == 3) {
                if (!Injection.repo().home().checkPlaceList()) {
                    showNoHomeMessage();
                    return;
                }
                ConfigHelper.instance().panelinfo = result;
                ConfigHelper.instance().mac = getDecryptResultMac(decryptResult);
                ConfigHelper.instance().productInfo = ProductRepository.getProductInfoByPid(getDecryptResultProductId(decryptResult));
                if (ConfigHelper.instance().productInfo != null) {
                    NavUtils.destination(ActConfigSuccess.class).navigation(this);
                    return;
                }
                return;
            }
            return;
        }
        if (QrcodeUtil.decode(result) != null && QrcodeUtil.decode(result).length > 3 && Utils.isYYBFlavor(this)) {
            String[] decode = QrcodeUtil.decode(result);
            EZManager.instance().setEzDevice(new EzDevice(decode[1], decode[2], decode[3]));
            NavUtils.destination("com.ltech.smarthome.ui.camera.config.ActCameraInfo").navigation(this);
        } else {
            SmartToast.showShort(getString(R.string.app_str_canot_identify_qr_code));
            ((ActQrCodeScanBinding) this.mViewBinding).zxingview.startSpot();
        }
    }

    private void showNoHomeMessage() {
        MessageDialog.show(this, R.string.tips, R.string.no_home_add_super_panel).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActQrCodeScan$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showNoHomeMessage$0;
                lambda$showNoHomeMessage$0 = ActQrCodeScan.this.lambda$showNoHomeMessage$0(baseDialog, view);
                return lambda$showNoHomeMessage$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showNoHomeMessage$0(BaseDialog baseDialog, View view) {
        ((ActQrCodeScanBinding) this.mViewBinding).zxingview.startSpot();
        return false;
    }

    @Override // cn.bingoogolapple.qrcode.core.QRCodeView.Delegate
    public void onScanQRCodeOpenCameraError() {
        SmartToast.showShort(R.string.open_camera_error);
        finishActivity();
    }

    private boolean checkResult(String result) {
        LHomeLog.d(getClass(), "check result ---->>");
        return !getDecryptResult(result, Constants.AES_DEFAULT_KEY).isEmpty();
    }

    public String getDecryptResult(String data, String key) {
        String str = "";
        try {
            str = AesEncryption.Desencrypt(data, key, key).replace("\u0000", "");
            LHomeLog.d(getClass(), "decryptResult result -->>" + str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public String getDecryptResultType(String decryptResult) {
        return (String) ((HashMap) GsonUtils.getGson().fromJson(decryptResult, GsonUtils.getType(HashMap.class, String.class, String.class))).get("type");
    }

    public String getDecryptResultPlaceName(String decryptResult) {
        return (String) ((HashMap) GsonUtils.getGson().fromJson(decryptResult, GsonUtils.getType(HashMap.class, String.class, String.class))).get("placename");
    }

    public String getDecryptResultProductId(String decryptResult) {
        return (String) ((HashMap) GsonUtils.getGson().fromJson(decryptResult, GsonUtils.getType(HashMap.class, String.class, String.class))).get("productid");
    }

    public String getDecryptResultMac(String decryptResult) {
        return (String) ((HashMap) GsonUtils.getGson().fromJson(decryptResult, GsonUtils.getType(HashMap.class, String.class, String.class))).get("mac");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1 && data != null) {
            try {
                String decodeQRCode = decodeQRCode(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()));
                if (decodeQRCode != null) {
                    onScanQRCodeSuccess(decodeQRCode);
                } else {
                    SmartToast.showShort(getString(R.string.app_str_canot_identify_qr_code));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String decodeQRCode(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        try {
            return new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(new RGBLuminanceSource(width, height, iArr)))).getText();
        } catch (ChecksumException | FormatException | NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
    }
}