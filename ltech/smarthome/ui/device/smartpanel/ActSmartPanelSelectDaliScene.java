package com.ltech.smarthome.ui.device.smartpanel;

import android.content.Intent;
import android.view.View;
import com.blankj.utilcode.util.FragmentUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDaliSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.FtDaliScene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectDaliScene extends VMActivity<ActDaliSelectBinding, ActSmartPanelSelectActionVM> {
    private long deviceId;
    private FtDaliScene ftScene;
    private boolean isEurPanelBinding;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dali_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_dali_scene));
        ((ActSmartPanelSelectActionVM) this.mViewModel).clickType = getIntent().getIntExtra(Constants.CLICK_TYPE, 1);
        long longExtra = getIntent().getLongExtra("device_id", 0L);
        this.deviceId = longExtra;
        this.ftScene = FtDaliScene.newInstance(longExtra, false);
        FragmentUtils.add(getSupportFragmentManager(), this.ftScene, R.id.layout_content);
        ((ActDaliSelectBinding) this.mViewBinding).tvBottom.setText(getString(R.string.finish));
        ((ActDaliSelectBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectDaliScene$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSmartPanelSelectDaliScene.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.ftScene.getSelectSceneList().isEmpty()) {
            return;
        }
        Scene scene = this.ftScene.getSelectSceneList().get(0);
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            Intent intent = new Intent();
            intent.putExtra(Constants.RELATE_ID, scene.getSceneId());
            intent.putExtra(Constants.GROUP_RELATE, 3);
            setResult(3001, intent);
            finishActivity();
            return;
        }
        if (getIntent().getBooleanExtra(Constants.BATCH_SET_SCENE, false)) {
            Intent intent2 = new Intent();
            intent2.putExtra(Constants.SCENE_ID, scene.getSceneId());
            setResult(6004, intent2);
            finishActivity();
            return;
        }
        RelatedInfoExtParam.RelateInfo RelatedSceneInfo = RelatedInfoExtParam.RelateInfo.RelatedSceneInfo(scene.getSceneId());
        if (this.isEurPanelBinding) {
            RelatedSceneInfo.type = 3;
            RelatedSceneInfo.action = 2;
            RelatedSceneInfo.keyActionExtra = scene.getSceneNum();
            ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setSceneRelateInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, RelatedSceneInfo);
        } else {
            RelatedSceneInfo.type = 8;
            RelatedSceneInfo.keyActionExtra = scene.getSceneNum();
            if (((ActSmartPanelSelectActionVM) this.mViewModel).clickType == 2) {
                RelatedSceneInfo.action = 38;
                ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setRelateLongClickInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, RelatedSceneInfo);
            } else {
                RelatedSceneInfo.action = 37;
                ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, RelatedSceneInfo);
            }
        }
        subscribe();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        ((ActSmartPanelSelectActionVM) this.mViewModel).isNightUp = getIntent().getBooleanExtra(Constants.IS_NIGHT_UP, false);
        if (((ActSmartPanelSelectActionVM) this.mViewModel).isNightUp) {
            ((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition = -1;
        }
        ((ActSmartPanelSelectActionVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelSelectActionVM) this.mViewModel).initRelateAssistant(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
        if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
            this.isEurPanelBinding = ProductRepository.isEurPanel(((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup) || ProductRepository.isAsPanel(((ActSmartPanelSelectActionVM) this.mViewModel).controlGroup);
        } else {
            this.isEurPanelBinding = ProductRepository.isRcB(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId()) || ProductRepository.isEurPanel(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId()) || ProductRepository.isAsPanel(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId());
        }
    }

    private void subscribe() {
        if (Injection.state().isConnectOuterNet()) {
            if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
                if (this.isEurPanelBinding) {
                    ((ActSmartPanelSelectActionVM) this.mViewModel).eurSubscribeScene(false);
                    return;
                } else {
                    showLoadingDialog(getString(R.string.subscribing));
                    ((ActSmartPanelSelectActionVM) this.mViewModel).subscribeScene(false);
                    return;
                }
            }
            if (ProductRepository.isRcB(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId())) {
                RelateInfoUtils.showImageTipDialog(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectDaliScene$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        ActSmartPanelSelectDaliScene.this.lambda$subscribe$1(imageTipDialog);
                    }
                });
                return;
            }
            if (ProductId.ID_SMART_SWITCH_SQB.equals(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId()) || (ProductId.ID_SMART_PANEL_S6B.equals(((ActSmartPanelSelectActionVM) this.mViewModel).productId) && RelateInfoUtils.needShowTipDialog())) {
                RelateInfoUtils.showImageTipDialog(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectDaliScene$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        ActSmartPanelSelectDaliScene.this.lambda$subscribe$2(imageTipDialog);
                    }
                });
                return;
            }
            if (ProductRepository.isEurPanel(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId()) || ProductRepository.isAsPanel(((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId())) {
                ((ActSmartPanelSelectActionVM) this.mViewModel).eurSubscribeScene(false);
                return;
            }
            showLoadingDialog(getString(R.string.subscribing));
            ((ActSmartPanelSelectActionVM) this.mViewModel).tipDialog = null;
            ((ActSmartPanelSelectActionVM) this.mViewModel).subscribeScene(false);
            return;
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribe$1(ImageTipDialog imageTipDialog) {
        showLoadingDialog(getString(R.string.subscribing));
        ((ActSmartPanelSelectActionVM) this.mViewModel).tipDialog = imageTipDialog;
        ((ActSmartPanelSelectActionVM) this.mViewModel).eurSubscribeScene(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$subscribe$2(ImageTipDialog imageTipDialog) {
        showLoadingDialog(getString(R.string.subscribing));
        ((ActSmartPanelSelectActionVM) this.mViewModel).tipDialog = imageTipDialog;
        ((ActSmartPanelSelectActionVM) this.mViewModel).subscribeScene(false);
    }
}