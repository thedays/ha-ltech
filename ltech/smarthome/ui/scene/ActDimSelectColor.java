package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDimSelectColorBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.BrightVerticalSeekBar;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class ActDimSelectColor extends VMActivity<ActDimSelectColorBinding, ActSelectColorVM> {
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dim_select_color;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_brightness));
        setEditString(getString(R.string.confirm));
        ((ActSelectColorVM) this.mViewModel).isGq = getIntent().getBooleanExtra(Constants.IS_GQ, false);
        ((ActSelectColorVM) this.mViewModel).isE6 = getIntent().getBooleanExtra(Constants.IS_E6, false);
        ((ActSelectColorVM) this.mViewModel).isLocalScene = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        ((ActSelectColorVM) this.mViewModel).lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        ((ActSelectColorVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActSelectColorVM) this.mViewModel).isWaveSensorAction = getIntent().getBooleanExtra(Constants.WAVE_SENSOR_ACTION, false);
        ((ActSelectColorVM) this.mViewModel).initData();
        initDimView();
        if (((ActSelectColorVM) this.mViewModel).getWyBrt() == 0) {
            ((ActDimSelectColorBinding) this.mViewBinding).sbBrt.setCurrentProgress(100);
            ((ActSelectColorVM) this.mViewModel).setWyBrt(255);
        } else {
            ((ActDimSelectColorBinding) this.mViewBinding).sbBrt.setCurrentProgress(LightUtils.brt2ProgressHasBelowZero(((ActSelectColorVM) this.mViewModel).getWyBrt()));
        }
        ((ActDimSelectColorBinding) this.mViewBinding).tvBrt.setText(((ActDimSelectColorBinding) this.mViewBinding).sbBrt.getProgressPercent());
    }

    private void initDimView() {
        ((ActDimSelectColorBinding) this.mViewBinding).sbBrt.setOnProgressChangeListener(new BrightVerticalSeekBar.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.scene.ActDimSelectColor.1
            @Override // com.ltech.smarthome.view.BrightVerticalSeekBar.OnProgressChangeListener
            public void onStartProgressChanged(BrightVerticalSeekBar bar) {
            }

            @Override // com.ltech.smarthome.view.BrightVerticalSeekBar.OnProgressChangeListener
            public void onProgressChanged(BrightVerticalSeekBar bar) {
                ((ActSelectColorVM) ActDimSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(bar.getProgress()));
                ((ActDimSelectColorBinding) ActDimSelectColor.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
                ((ActSelectColorVM) ActDimSelectColor.this.mViewModel).getLightCmdHelper().sendDimBrtHas1to9(ActDimSelectColor.this, bar.getProgress(), false);
            }

            @Override // com.ltech.smarthome.view.BrightVerticalSeekBar.OnProgressChangeListener
            public void onStopProgressChanged(BrightVerticalSeekBar bar) {
                ((ActSelectColorVM) ActDimSelectColor.this.mViewModel).setWyBrt(LightUtils.progress2BrtHasBelowOne(bar.getProgress()));
                ((ActSelectColorVM) ActDimSelectColor.this.mViewModel).getLightCmdHelper().sendDimBrtHas1to9(ActDimSelectColor.this, bar.getProgress(), true);
                ((ActDimSelectColorBinding) ActDimSelectColor.this.mViewBinding).tvBrt.setText(bar.getProgressPercent());
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        LHomeLog.i(getClass(), "选择亮度save ");
        if (ProductId.ID_SMART_PANEL_S6B.equals(((ActSelectColorVM) this.mViewModel).productId) && RelateInfoUtils.needShowTipDialog()) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.scene.ActDimSelectColor$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActDimSelectColor.this.lambda$edit$0(imageTipDialog);
                }
            });
        } else {
            ((ActSelectColorVM) this.mViewModel).saveData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(ImageTipDialog imageTipDialog) {
        ((ActSelectColorVM) this.mViewModel).saveData();
        imageTipDialog.dismissDialog();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            setResult(3001);
            finishActivity();
        }
    }
}