package com.ltech.smarthome.ui.scene.local;

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
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;
import com.ltech.smarthome.ui.device.light.ActGradientScene;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.device.spicontroller.ActSelectSpiForAction;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ActCtSelectColor;
import com.ltech.smarthome.ui.scene.ActDimSelectColor;
import com.ltech.smarthome.ui.scene.ActSelectColor;
import com.ltech.smarthome.ui.scene.ActSelectColorCC;
import com.ltech.smarthome.ui.scene.ActSelectDefaultMode;
import com.ltech.smarthome.ui.scene.ActSelectDiyMode;
import com.ltech.smarthome.ui.scene.ActSelectLightRhythmAction;
import com.ltech.smarthome.ui.scene.ActSelectThemeMode;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectLightLocalAction extends VMActivity<ActSelectBinding, ActSelectLightLocalActionVM> implements ISelectAction {
    private Role controlObject;
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
    public /* synthetic */ void lambda$save$2(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.controlObject = (Role) SceneHelper.instance().controlObject;
        ArrayList arrayList = new ArrayList();
        int i = this.lightType;
        if (i == 7) {
            arrayList.add(new GoItem().setMainText(getString(R.string.function_open)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightLocalAction.this.lambda$initView$0();
                }
            })));
            arrayList.add(new GoItem().setMainText(getString(R.string.close)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda17
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightLocalAction.this.lambda$initView$1();
                }
            })));
        } else if (i == 17) {
            arrayList.add(new GoItem().setMainText(getString(R.string.rgb_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightLocalAction.this.lambda$initView$2();
                }
            })));
            arrayList.add(new GoItem().setMainText(getString(R.string.spi_mode_and_list_action)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightLocalAction.this.lambda$initView$3();
                }
            })));
            arrayList.add(new GoItem().setMainText(getString(R.string.light_off_1)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightLocalAction.this.lambda$initView$4();
                }
            })));
        } else {
            if (SceneHelper.instance().bindingType != 4 && SceneHelper.instance().bindingType != 5 && !getIntent().getBooleanExtra(Constants.IS_E6, false) && !isVirtualObject()) {
                arrayList.add(new GoItem().setMainText(getString(R.string.app_str_local_scene_current_state)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$5();
                    }
                })));
            }
            int i2 = this.lightType;
            if (i2 == 1) {
                arrayList.add(new GoItem().setMainText(getString(R.string.dim_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$6();
                    }
                })));
            } else if (i2 == 2) {
                arrayList.add(new GoItem().setMainText(getString(R.string.ct_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda6
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$7();
                    }
                })));
            } else if (i2 == 20) {
                arrayList.add(new GoItem().setMainText(getString(R.string.rgb_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda7
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$8();
                    }
                })));
            } else {
                arrayList.add(new GoItem().setMainText(getString(R.string.rgb_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda8
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$9();
                    }
                })));
            }
            if (ProductRepository.supportDynamicMode(SceneHelper.instance().controlObject)) {
                arrayList.add(new GoItem().setMainText(getString(R.string.theme)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda9
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$10();
                    }
                })));
                int i3 = this.lightType;
                if (i3 > 2 || (i3 == 2 && ProductRepository.supportCtGeneralMode(SceneHelper.instance().controlObject))) {
                    arrayList.add(new GoItem().setMainText(getString(R.string.general_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda10
                        @Override // com.ltech.smarthome.binding.command.BindingAction
                        public final void call() {
                            ActSelectLightLocalAction.this.lambda$initView$11();
                        }
                    })));
                }
                arrayList.add(new GoItem().setMainText(getString(R.string.diy_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda11
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$12();
                    }
                })));
            }
            if (getIntent().getBooleanExtra(Constants.IS_E6, false)) {
                arrayList.add(new GoItem().setMainText(getString(R.string.scene_key)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda12
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$13();
                    }
                })));
            }
            Role role = this.controlObject;
            if ((role instanceof Group) && ProductRepository.supportGradientGroup((Group) role)) {
                arrayList.add(new GoItem().setMainText(getString(R.string.app_str_gradient_scene)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda13
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$14();
                    }
                })));
            }
            arrayList.add(new GoItem().setMainText(getString(R.string.light_off_1)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda14
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightLocalAction.this.lambda$initView$15();
                }
            })));
            if (!getIntent().getBooleanExtra(Constants.IS_E6, false) && this.lightType == 2 && supportRhythm()) {
                arrayList.add(new GoItem().setMainText(getString(R.string.app_circadian_lighting)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda15
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightLocalAction.this.lambda$initView$16();
                    }
                })));
            }
        }
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectLightLocalAction$$ExternalSyntheticLambda16
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i4) {
                ActSelectLightLocalAction.this.lambda$initView$17(baseQuickAdapter2, view, i4);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        if (ProductRepository.isRelaySeparationSub(this.controlObject)) {
            ((ActSelectLightLocalActionVM) this.mViewModel).selectOn(RelaySeparationHelper.getZoneNum(this.controlObject));
        } else {
            ((ActSelectLightLocalActionVM) this.mViewModel).selectOn(new int[0]);
        }
        SceneHelper.instance().maskType = MaskType.LOCAL;
        lambda$save$2(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        if (ProductRepository.isRelaySeparationSub(this.controlObject)) {
            ((ActSelectLightLocalActionVM) this.mViewModel).selectClose(RelaySeparationHelper.getZoneNum(this.controlObject));
        } else {
            ((ActSelectLightLocalActionVM) this.mViewModel).selectClose(new int[0]);
        }
        SceneHelper.instance().maskType = MaskType.LOCAL;
        lambda$save$2(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2() {
        NavUtils.destination(ActSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3() {
        NavUtils.destination(ActSelectSpiForAction.class).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4() {
        ((ActSelectLightLocalActionVM) this.mViewModel).selectClose(new int[0]);
        SceneHelper.instance().maskType = MaskType.LOCAL;
        lambda$save$2(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5() {
        ((ActSelectLightLocalActionVM) this.mViewModel).selectCurrentState();
        SceneHelper.instance().maskType = MaskType.LOCAL;
        lambda$save$2(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6() {
        NavUtils.destination(ActDimSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$7() {
        NavUtils.destination(ActCtSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(SceneHelper.instance().controlObject)).withBoolean(Constants.GROUP_CONTROL, ProductRepository.isGroup(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8() {
        NavUtils.destination(ActSelectColorCC.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$9() {
        NavUtils.destination(ActSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$10() {
        NavUtils.destination(ActSelectThemeMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$11() {
        NavUtils.destination(ActSelectDefaultMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$12() {
        NavUtils.destination(ActSelectDiyMode.class).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().withInt(Constants.LIGHT_TYPE, this.lightType).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$13() {
        E6Helper.instance().showSceneKeyDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$14() {
        NavUtils.destination(ActGradientScene.class).withLong(Constants.CONTROL_ID, this.controlObject.getId()).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$15() {
        ((ActSelectLightLocalActionVM) this.mViewModel).selectClose(new int[0]);
        SceneHelper.instance().maskType = MaskType.LOCAL;
        CmdAssistant.getLightCmdAssistant(SceneHelper.instance().controlObject, new int[0]).sendOnOff(getApplicationContext(), false);
        lambda$save$2(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$16() {
        NavUtils.destination(ActSelectLightRhythmAction.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$17(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mAdapter.getData().get(i).getAction().execute();
    }

    private boolean isVirtualObject() {
        if (getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)) {
            return ((Group) SceneHelper.instance().controlObject).isVirtual();
        }
        return ((Device) SceneHelper.instance().controlObject).isVirtual();
    }

    private boolean supportRhythm() {
        if (getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)) {
            Group group = (Group) SceneHelper.instance().controlObject;
            return group.getMinkelvin() + group.getMaxkelvin() > 0;
        }
        Device device = (Device) SceneHelper.instance().controlObject;
        return device.getMinkelvin() + device.getMaxkelvin() > 0;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            lambda$save$2(this);
        }
    }
}