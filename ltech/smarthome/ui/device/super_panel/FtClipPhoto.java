package com.ltech.smarthome.ui.device.super_panel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.akuvox.mobile.libcommon.defined.MediaDefined;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.databinding.FtClipPhotoBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.singleton.PathManager;
import com.ltech.smarthome.utils.Constants;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class FtClipPhoto extends BaseNormalFragment<FtClipPhotoBinding> {
    private String image;
    private boolean isRoundCut;
    private String mac;
    private String productId;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_clip_photo;
    }

    public static FtClipPhoto newInstance(String image, String mac, boolean isRoundCut, String productId) {
        FtClipPhoto ftClipPhoto = new FtClipPhoto();
        Bundle bundle = new Bundle();
        bundle.putString("image", image);
        bundle.putString(Constants.MAC_ADDRESS, mac);
        bundle.putBoolean(Constants.ROUND_CUT, isRoundCut);
        bundle.putString(Constants.PRODUCT_ID, productId);
        ftClipPhoto.setArguments(bundle);
        return ftClipPhoto;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.image = arguments.getString("image");
            this.mac = arguments.getString(Constants.MAC_ADDRESS);
            this.isRoundCut = arguments.getBoolean(Constants.ROUND_CUT);
            String string = arguments.getString(Constants.PRODUCT_ID);
            this.productId = string;
            if (string != null) {
                string.hashCode();
                switch (string) {
                    case "123050811340901":
                    case "122080911090801":
                    case "121052512023201":
                    case "120010615085201":
                        ((FtClipPhotoBinding) this.mViewBinding).clipViewLayout.setClipRatio(1.0f);
                        break;
                    case "3731481586450752":
                        ((FtClipPhotoBinding) this.mViewBinding).clipViewLayout.setClipRatio(1.0f);
                        break;
                    case "122042815485901":
                        ((FtClipPhotoBinding) this.mViewBinding).clipViewLayout.setClipRatio(0.5625f);
                        break;
                }
                return;
            }
            ((FtClipPhotoBinding) this.mViewBinding).clipViewLayout.setClipRatio(1.0f);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtClipPhotoBinding) this.mViewBinding).clipViewLayout.setImageSrc(Uri.fromFile(new File(this.image)));
        ((FtClipPhotoBinding) this.mViewBinding).clipViewLayout.setClipType(this.isRoundCut ? 1 : 2);
    }

    public Uri generateUri() {
        Bitmap clip = ((FtClipPhotoBinding) this.mViewBinding).clipViewLayout.clip();
        OutputStream outputStream = null;
        if (clip == null) {
            Log.i(getClass().getSimpleName(), "zoomedCropBitmap == null");
            return null;
        }
        if (ProductId.ID_SMART_PANEL_GQ_PRO.equals(this.productId)) {
            clip = Bitmap.createScaledBitmap(clip, MediaDefined.DEGRESS_360, MediaDefined.DEGRESS_360, true);
        }
        Uri fromFile = Uri.fromFile(new File(requireActivity().getCacheDir(), PathManager.getSuperPanelPicName(this.mac) + System.currentTimeMillis() + ".png"));
        try {
            if (fromFile != null) {
                try {
                    try {
                        outputStream = requireActivity().getContentResolver().openOutputStream(fromFile);
                        if (outputStream != null) {
                            clip.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        }
                        if (outputStream != null) {
                            outputStream.close();
                            return fromFile;
                        }
                    } catch (Throwable th) {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    Log.i(getClass().getSimpleName(), "Cannot open file: " + fromFile, e2);
                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return fromFile;
    }
}