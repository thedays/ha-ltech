package com.ltech.smarthome.ui.device.super_panel;

import android.content.Intent;
import android.view.View;
import com.blankj.utilcode.util.FragmentUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSuperPanelSelectPhotoBinding;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.Callback;
import com.yuyh.library.imgsel.common.ImageHelper;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.yuyh.library.imgsel.ui.fragment.FtSuperPanelSelectImg;
import java.io.File;

/* loaded from: classes4.dex */
public class ActSuperPanelSelectPhoto extends BaseNormalActivity<ActSuperPanelSelectPhotoBinding> implements Callback {
    private ISListConfig config;
    private FtSuperPanelSelectImg fragment;

    @Override // com.yuyh.library.imgsel.common.Callback
    public void onCameraShot(File imageFile) {
    }

    @Override // com.yuyh.library.imgsel.common.Callback
    public void onPreviewChanged(int select, int sum, boolean visible) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_select_photo;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        this.config = ISNav.getInstance().getConfig();
        FtSuperPanelSelectImg instance = FtSuperPanelSelectImg.instance();
        this.fragment = instance;
        instance.setCallback(this);
        FragmentUtils.add(getSupportFragmentManager(), this.fragment, R.id.fmImageList);
        ISListConfig iSListConfig = this.config;
        if (iSListConfig != null) {
            if (iSListConfig.multiSelect) {
                if (!this.config.rememberSelected) {
                    ImageHelper.imageList.clear();
                }
            } else {
                ImageHelper.imageList.clear();
            }
            changeNumber(ImageHelper.imageList.size());
        }
        ((ActSuperPanelSelectPhotoBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectPhoto$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSuperPanelSelectPhoto.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        int id = view.getId();
        if (id == R.id.tv_preview) {
            ImageHelper.tempImageList.clear();
            ImageHelper.tempImageList.addAll(ImageHelper.imageList);
            NavUtils.destination(ActSuperPanelPreviewPhoto.class).withBoolean(Constants.SELECT_MODE, true).navigation(this);
        } else {
            if (id != R.id.tv_upload) {
                return;
            }
            ImageHelper.tempImageList.clear();
            ImageHelper.tempImageList.addAll(ImageHelper.imageList);
            goClipPhoto();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        changeNumber(ImageHelper.imageList.size());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.fragment == null) {
            ImageHelper.imageList.clear();
            super.onBackPressed();
        }
    }

    public ISListConfig getConfig() {
        return this.config;
    }

    private void changeNumber(int integer) {
        ((ActSuperPanelSelectPhotoBinding) this.mViewBinding).tvUpload.setText(String.format(getResources().getString(R.string.confirm_with_num), Integer.valueOf(integer)));
        ((ActSuperPanelSelectPhotoBinding) this.mViewBinding).tvUpload.setBackgroundResource(integer == 0 ? R.drawable.shape_red_bt_20_2 : R.drawable.shape_red_bt_20);
        ((ActSuperPanelSelectPhotoBinding) this.mViewBinding).tvUpload.setEnabled(integer != 0);
    }

    private void goClipPhoto() {
        NavUtils.destination(ActSuperPanelClipPhoto.class).withString(Constants.PRODUCT_ID, getIntent().getStringExtra(Constants.PRODUCT_ID)).withLong("device_id", getIntent().getLongExtra("device_id", -1L)).withString(Constants.MAC_ADDRESS, getIntent().getStringExtra(Constants.MAC_ADDRESS)).withBoolean(Constants.ROUND_CUT, getIntent().getBooleanExtra(Constants.ROUND_CUT, false)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withLong(Constants.GROUP_ID, getIntent().getLongExtra(Constants.GROUP_ID, -1L)).withDefaultRequestCode().navigation(this);
    }

    @Override // com.yuyh.library.imgsel.common.Callback
    public void onSingleImageSelected(String path) {
        goClipPhoto();
    }

    @Override // com.yuyh.library.imgsel.common.Callback
    public void onImageSelected(String path) {
        changeNumber(ImageHelper.imageList.size());
    }

    @Override // com.yuyh.library.imgsel.common.Callback
    public void onImageUnselected(String path) {
        changeNumber(ImageHelper.imageList.size());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3021) {
            setResult(3021, data);
            finishActivity();
        }
    }
}