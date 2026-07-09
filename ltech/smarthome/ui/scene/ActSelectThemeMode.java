package com.ltech.smarthome.ui.scene;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectThemeModeBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.singleton.ComboCmdHelper;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;
import com.ltech.smarthome.ui.mode.FtThemeMode;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.ModeAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.smart.product_agreement.param.ModeCmdParam;
import java.util.Collections;

/* loaded from: classes4.dex */
public class ActSelectThemeMode extends BaseNormalActivity<ActSelectThemeModeBinding> implements ISelectAction {
    private FtThemeMode ftThemeMode;
    private String productId;
    private int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_theme_mode;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$save$2(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.confirm));
        setTitle(getString(R.string.theme));
        int intExtra = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        if (getIntent().getBooleanExtra(Constants.IS_E6, false) && !ProductId.ID_KNOB_PANEL_E6M.equals(this.productId)) {
            ((ActSelectThemeModeBinding) this.mViewBinding).tvTip.setText(getString(R.string.e6_mode_tip));
            ((ActSelectThemeModeBinding) this.mViewBinding).tvTip.setVisibility(0);
        }
        FtThemeMode newInstance = FtThemeMode.newInstance(intExtra);
        this.ftThemeMode = newInstance;
        newInstance.setCallback(new FtThemeMode.OnItemClickCallback() { // from class: com.ltech.smarthome.ui.scene.ActSelectThemeMode$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.ui.mode.FtThemeMode.OnItemClickCallback
            public final void click(int i) {
                ActSelectThemeMode.this.lambda$initView$0(i);
            }
        });
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.layout_content, this.ftThemeMode).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(int i) {
        this.selectPosition = i;
        getModeCmdHelper().previewDefaultThemeMode(this, i);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
        } else if (ProductId.ID_SMART_PANEL_S6B.equals(this.productId) && RelateInfoUtils.needShowTipDialog()) {
            RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.scene.ActSelectThemeMode$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                    ActSelectThemeMode.this.lambda$edit$1(imageTipDialog);
                }
            });
        } else {
            showLoadingDialog(getString(R.string.subscribing));
            save();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(ImageTipDialog imageTipDialog) {
        save();
        imageTipDialog.dismissDialog();
    }

    private void save() {
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            lambda$edit$0(this);
            ComboCmdHelper.getInstance().selectTheme(this.selectPosition);
            return;
        }
        if (getIntent().getBooleanExtra(Constants.IS_E6, false)) {
            E6Helper.instance().selectTheme(this.selectPosition);
            return;
        }
        ModeCmdParam modeCmdParam = new ModeCmdParam();
        modeCmdParam.setCmdType(1);
        modeCmdParam.setPlayModeList(Collections.singletonList(Integer.valueOf(this.selectPosition)));
        modeCmdParam.addExtParam(SceneHelper.OPTION, "1");
        modeCmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.ftThemeMode.getSelectThemeName());
        SceneHelper.instance().cmdParam = modeCmdParam;
        if (getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false)) {
            lambda$edit$0(this);
        } else {
            SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectThemeMode$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActSelectThemeMode.this.lambda$save$2((Boolean) obj);
                }
            });
        }
    }

    public ModeAssistant getModeCmdHelper() {
        if (getIntent().getBooleanExtra(Constants.IS_E6, false)) {
            return E6Helper.instance().getModeCmdHelper();
        }
        return CmdAssistant.getModeCmdAssistant(SceneHelper.instance().controlObject, new int[0]);
    }
}