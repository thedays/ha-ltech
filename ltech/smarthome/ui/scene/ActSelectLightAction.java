package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActSelectLightBinding;
import com.ltech.smarthome.databinding.ItemTextHeadBinding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.e6knob.E6Helper;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.device.spicontroller.ActSelectSpiForAction;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.base.BaseCmdParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectLightAction extends VMActivity<ActSelectLightBinding, ActSelectLightActionVM> implements ISelectAction {
    private Role controlObject;
    private boolean isKeySet;
    private int lightType;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mActionAdapter;
    private BaseQuickAdapter<String, BaseViewHolder> mKeyAdapter;
    protected List<String> dataList = new ArrayList();
    protected int selectPosition = -1;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_light;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$initActionView$7(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.KEY_SET, false);
        this.isKeySet = booleanExtra;
        if (booleanExtra) {
            initKeyView();
            if (this.lightType == 7) {
                return;
            }
        }
        initActionView();
    }

    private void initKeyView() {
        setEditString(getString(R.string.save));
        int i = this.lightType;
        if (i == 1) {
            this.dataList = Arrays.asList(NameRepository.getDimSwitchActionName(this));
        } else if (i == 2) {
            this.dataList = Arrays.asList(NameRepository.getCtSwitchActionName(this));
        } else if (i == 7) {
            this.dataList = Arrays.asList(NameRepository.getBleSwitchActionName(this));
        } else {
            this.dataList = Arrays.asList(NameRepository.getKeySwitchActionName(this));
        }
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_select, this.dataList) { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item).setBackgroundRes(R.id.iv_select, helper.getAdapterPosition() == ActSelectLightAction.this.selectPosition ? R.mipmap.ic_tick_sel : R.color.transparent);
                ((AppCompatTextView) helper.getView(R.id.tv_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mKeyAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActSelectLightBinding) this.mViewBinding).rvKey);
        this.mKeyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda9
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActSelectLightAction.this.lambda$initKeyView$0(baseQuickAdapter2, view, i2);
            }
        });
        ((ActSelectLightBinding) this.mViewBinding).rvKey.setLayoutManager(new LinearLayoutManager(this, this) { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction.2
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        });
        ((ActSelectLightBinding) this.mViewBinding).rvKey.setHasFixedSize(true);
        ItemTextHeadBinding itemTextHeadBinding = (ItemTextHeadBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_text_head, (ViewGroup) ((ActSelectLightBinding) this.mViewBinding).rvKey.getParent(), false);
        itemTextHeadBinding.setItem(getString(R.string.select_key_action_tip));
        itemTextHeadBinding.setTextColor(Integer.valueOf(ContextCompat.getColor(this, R.color.color_text_black)));
        ((ActSelectLightBinding) this.mViewBinding).rvKey.addHeaderView(itemTextHeadBinding.getRoot());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initKeyView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.selectPosition != i) {
            this.selectPosition = i;
            baseQuickAdapter.notifyDataSetChanged();
        }
    }

    private void initActionView() {
        this.controlObject = (Role) SceneHelper.instance().controlObject;
        ArrayList arrayList = new ArrayList();
        int i = this.lightType;
        if (i == 7) {
            arrayList.add(new GoItem().setMainText(getString(R.string.function_open)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda11
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightAction.this.lambda$initActionView$2();
                }
            })));
            arrayList.add(new GoItem().setMainText(getString(R.string.close)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda20
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightAction.this.lambda$initActionView$4();
                }
            })));
        } else if (i == 17) {
            arrayList.add(new GoItem().setMainText(getString(R.string.rgb_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda21
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightAction.this.lambda$initActionView$5();
                }
            })));
            arrayList.add(new GoItem().setMainText(getString(R.string.spi_mode_and_list_action)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightAction.this.lambda$initActionView$6();
                }
            })));
            arrayList.add(new GoItem().setMainText(getString(R.string.light_off_1)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectLightAction.this.lambda$initActionView$8();
                }
            })));
        } else {
            if (i == 1) {
                arrayList.add(new GoItem().setMainText(getString(R.string.dim_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda3
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$9();
                    }
                })));
            } else if (i == 2) {
                arrayList.add(new GoItem().setMainText(getString(R.string.ct_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda4
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$10();
                    }
                })));
            } else if (i == 20) {
                arrayList.add(new GoItem().setMainText(getString(R.string.rgb_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda5
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$11();
                    }
                })));
            } else {
                arrayList.add(new GoItem().setMainText(getString(R.string.rgb_static)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda6
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$12();
                    }
                })));
            }
            if (ProductRepository.supportDynamicMode(SceneHelper.instance().controlObject)) {
                arrayList.add(new GoItem().setMainText(getString(R.string.theme)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda7
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$13();
                    }
                })));
                int i2 = this.lightType;
                if (i2 > 2 || (i2 == 2 && ProductRepository.supportCtGeneralMode(SceneHelper.instance().controlObject))) {
                    arrayList.add(new GoItem().setMainText(getString(R.string.general_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda14
                        @Override // com.ltech.smarthome.binding.command.BindingAction
                        public final void call() {
                            ActSelectLightAction.this.lambda$initActionView$14();
                        }
                    })));
                }
                arrayList.add(new GoItem().setMainText(getString(R.string.diy_mode)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda15
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$15();
                    }
                })));
            }
            if (getIntent().getBooleanExtra(Constants.IS_E6, false)) {
                arrayList.add(new GoItem().setMainText(getString(R.string.scene_key)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda16
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$16();
                    }
                })));
            }
            if (!this.isKeySet) {
                arrayList.add(new GoItem().setMainText(getString(R.string.light_off_1)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda17
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$18();
                    }
                })));
            }
            if (SceneHelper.instance().bindingType != 2 && !getIntent().getBooleanExtra(Constants.IS_E6, false) && this.lightType == 2 && supportRhythm()) {
                arrayList.add(new GoItem().setMainText(getString(R.string.app_circadian_lighting)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda18
                    @Override // com.ltech.smarthome.binding.command.BindingAction
                    public final void call() {
                        ActSelectLightAction.this.lambda$initActionView$19();
                    }
                })));
            }
        }
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mActionAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda19
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i3) {
                ActSelectLightAction.this.lambda$initActionView$20(baseQuickAdapter2, view, i3);
            }
        });
        this.mActionAdapter.bindToRecyclerView(((ActSelectLightBinding) this.mViewBinding).rvAction);
        ((ActSelectLightBinding) this.mViewBinding).rvAction.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectLightBinding) this.mViewBinding).rvAction.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$2() {
        if (ProductRepository.isRelaySeparationSub(this.controlObject)) {
            ((ActSelectLightActionVM) this.mViewModel).selectSwitchOn(RelaySeparationHelper.getZoneNum(this.controlObject));
        } else {
            ((ActSelectLightActionVM) this.mViewModel).selectSwitchOn(new int[0]);
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectLightAction.this.lambda$initActionView$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$4() {
        if (ProductRepository.isRelaySeparationSub(this.controlObject)) {
            ((ActSelectLightActionVM) this.mViewModel).selectSwitchClose(RelaySeparationHelper.getZoneNum(this.controlObject));
        } else {
            ((ActSelectLightActionVM) this.mViewModel).selectSwitchClose(new int[0]);
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectLightAction.this.lambda$initActionView$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$5() {
        NavUtils.destination(ActSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$6() {
        NavUtils.destination(ActSelectSpiForAction.class).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(SceneHelper.instance().controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$8() {
        ((ActSelectLightActionVM) this.mViewModel).selectClose(new int[0]);
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda13
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectLightAction.this.lambda$initActionView$7((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$9() {
        NavUtils.destination(ActDimSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$10() {
        NavUtils.destination(ActCtSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(SceneHelper.instance().controlObject)).withBoolean(Constants.GROUP_CONTROL, ProductRepository.isGroup(SceneHelper.instance().controlObject)).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$11() {
        NavUtils.destination(ActSelectColorCC.class).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(SceneHelper.instance().controlObject)).withBoolean(Constants.GROUP_CONTROL, ProductRepository.isGroup(SceneHelper.instance().controlObject)).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$12() {
        NavUtils.destination(ActSelectColor.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$13() {
        NavUtils.destination(ActSelectThemeMode.class).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$14() {
        NavUtils.destination(ActSelectDefaultMode.class).withInt(Constants.LIGHT_TYPE, this.lightType).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$15() {
        NavUtils.destination(ActSelectDiyMode.class).withDefaultRequestCode().withInt(Constants.LIGHT_TYPE, this.lightType).navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$16() {
        E6Helper.instance().showSceneKeyDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$18() {
        ((ActSelectLightActionVM) this.mViewModel).selectClose(new int[0]);
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectLightAction.this.lambda$initActionView$17((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$17(Boolean bool) {
        CmdAssistant.getLightCmdAssistant(SceneHelper.instance().controlObject, new int[0]).sendOnOff(getApplicationContext(), false);
        lambda$initActionView$7(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$19() {
        NavUtils.destination(ActSelectLightRhythmAction.class).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$20(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mActionAdapter.getData().get(i).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectPosition == -1) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        SceneHelper.instance().cmdParam = new BaseCmdParam();
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION, "8");
        SceneHelper.instance().cmdParam.addExtParam(SceneHelper.OPTION_VALUE, this.mKeyAdapter.getData().get(this.selectPosition - 1));
        SuperPanelInfo.PanelKeyLight panelKeyLight = new SuperPanelInfo.PanelKeyLight();
        panelKeyLight.address = getIntent().getIntExtra(Constants.ADDRESS, -1);
        panelKeyLight.devicetype = this.lightType;
        if (this.lightType != 7) {
            panelKeyLight.actioncode = this.selectPosition;
        } else {
            panelKeyLight.actioncode = this.selectPosition + 3;
        }
        SceneHelper.instance().panelKeyLight = panelKeyLight;
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectLightAction$$ExternalSyntheticLambda12
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectLightAction.this.lambda$edit$21((Boolean) obj);
            }
        });
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
            lambda$initActionView$7(this);
        }
    }
}