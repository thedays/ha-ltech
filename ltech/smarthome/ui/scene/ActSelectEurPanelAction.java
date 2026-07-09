package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.local.ActSelectLightLocalActionVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectEurPanelAction extends VMActivity<ActSelectBinding, ActSelectLightLocalActionVM> implements ISelectAction {
    private boolean isLocal;
    private int lightType;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$initActionView$3(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        final String eurProductId;
        String string;
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        int intExtra = getIntent().getIntExtra(Constants.ZONE_NUM, 0);
        SceneHelper.instance().isMultiZone = intExtra > 1;
        if (AsHelper.isNewUb(SceneHelper.instance().controlObject)) {
            eurProductId = AsHelper.getAsProductId(SceneHelper.instance().controlObject);
        } else {
            eurProductId = EurHelper.getEurProductId(SceneHelper.instance().controlObject);
        }
        ArrayList arrayList = new ArrayList();
        if (ProductId.ID_EUR_PANEL_EB6.equals(eurProductId)) {
            arrayList.add(new GoItem().setMainText(getString(R.string.switch_on)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectEurPanelAction.this.lambda$initView$0();
                }
            })));
            arrayList.add(new GoItem().setMainText(getString(R.string.close)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectEurPanelAction.this.lambda$initView$1();
                }
            })));
        } else {
            int i = this.lightType;
            if (i == 1) {
                string = getString(R.string.dim_static);
            } else if (i == 2) {
                string = getString(R.string.ct_static);
            } else {
                string = getString(R.string.rgb_static);
            }
            arrayList.add(new GoItem().setMainText(string).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectEurPanelAction.this.lambda$initView$2();
                }
            })));
            int i2 = this.lightType;
            if (i2 > 2 || (i2 == 2 && ProductRepository.supportCtGeneralMode(SceneHelper.instance().controlObject))) {
                arrayList.add(new GoItem().setMainText(getString(R.string.general_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda3
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectEurPanelAction.this.lambda$initView$3();
                    }
                })));
            }
            if (this.lightType > 1 || AsHelper.isNewUb(SceneHelper.instance().controlObject)) {
                arrayList.add(new GoItem().setMainText(getString(R.string.diy_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectEurPanelAction.this.lambda$initView$4();
                    }
                })));
            }
            if (SceneHelper.instance().isMultiZone) {
                arrayList.add(new GoItem().setMainText(getString(R.string.open_zone)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectEurPanelAction.this.lambda$initView$5(eurProductId);
                    }
                })));
                arrayList.add(new GoItem().setMainText(getString(R.string.close_zone)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda6
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectEurPanelAction.this.lambda$initView$6(eurProductId);
                    }
                })));
            }
            arrayList.add(new GoItem().setMainText(getString(R.string.light_off_1)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectEurPanelAction.this.lambda$initView$7();
                }
            })));
        }
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectEurPanelAction$$ExternalSyntheticLambda8
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i3) {
                ActSelectEurPanelAction.this.lambda$initView$8(baseQuickAdapter2, view, i3);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        ((ActSelectLightLocalActionVM) this.mViewModel).selectOn(new int[0]);
        SceneHelper.instance().cmdParam.setZoneNum(0);
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "0");
        saveAction(this, this.isLocal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        ((ActSelectLightLocalActionVM) this.mViewModel).selectClose(new int[0]);
        SceneHelper.instance().cmdParam.setZoneNum(0);
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "4");
        saveAction(this, this.isLocal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2() {
        goSelectColor(this.lightType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3() {
        NavUtils.destination(ActSelectDefaultMode.class).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withInt(Constants.LIGHT_TYPE, this.lightType).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4() {
        NavUtils.destination(ActSelectDiyMode.class).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withDefaultRequestCode().withInt(Constants.LIGHT_TYPE, this.lightType).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(String str) {
        NavUtils.destination(ActSelectSpSceneKeyAction.class).withString(Constants.PRODUCT_ID, str).withInt(Constants.LIGHT_TYPE, this.lightType).withBoolean(Constants.KEY_ACTION_TYPE, true).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(String str) {
        NavUtils.destination(ActSelectSpSceneKeyAction.class).withString(Constants.PRODUCT_ID, str).withInt(Constants.LIGHT_TYPE, this.lightType).withBoolean(Constants.KEY_ACTION_TYPE, false).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$7() {
        ((ActSelectLightLocalActionVM) this.mViewModel).selectClose(new int[0]);
        SceneHelper.instance().cmdParam.setZoneNum(0);
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "4");
        CmdAssistant.getLightCmdAssistant(SceneHelper.instance().controlObject, (AsHelper.isNewUb(SceneHelper.instance().controlObject) || EurHelper.isEb125(SceneHelper.instance().controlObject)) ? 0 : 1).sendOnOffEurPanel(getApplicationContext(), false);
        saveAction(this, this.isLocal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mAdapter.getData().get(i).getAction().execute();
    }

    private void goSelectColor(int lightType) {
        NavUtils.Builder destination;
        if (lightType == 1) {
            destination = NavUtils.destination(ActDimSelectColor.class);
        } else if (lightType == 2) {
            destination = NavUtils.destination(ActCtSelectColor.class);
        } else {
            destination = NavUtils.destination(ActSelectColor.class);
        }
        destination.withInt(Constants.LIGHT_TYPE, lightType).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withDefaultRequestCode().navigation(this);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            saveAction(this, this.isLocal);
        }
    }
}