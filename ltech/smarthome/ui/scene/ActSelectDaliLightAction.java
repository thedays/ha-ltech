package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
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
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectDaliLightAction extends VMActivity<ActSelectLightBinding, ActSelectLightActionVM> implements ISelectAction {
    private Role controlObject;
    private boolean isLocal;
    private int lightType;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mActionAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select_light;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$initActionView$3(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        this.lightType = getIntent().getIntExtra(Constants.LIGHT_TYPE, -1);
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        initActionView();
    }

    private void initActionView() {
        String str;
        ArrayList arrayList = new ArrayList();
        Role role = (Role) SceneHelper.instance().controlObject;
        this.controlObject = role;
        int i = this.lightType;
        if (i == 1) {
            str = getString(R.string.dim_static);
        } else if (i == 2) {
            str = getString(R.string.dali_ct);
            if (DaliProHelper.isSupportDim(this.controlObject)) {
                str = getString(R.string.dali_dim) + "/" + getString(R.string.dali_ct);
            }
        } else if (i == 3) {
            str = getString(R.string.dali_rgb);
            if (DaliProHelper.isSupportDim(this.controlObject)) {
                str = getString(R.string.dali_dim) + "/" + getString(R.string.dali_rgb);
            }
        } else if (DaliProHelper.isSupportDim(role)) {
            str = getString(R.string.dali_dim) + "/" + getString(R.string.dali_ct) + "/" + getString(R.string.dali_rgb);
        } else {
            str = getString(R.string.dali_ct) + "/" + getString(R.string.dali_rgb);
        }
        arrayList.add(new GoItem().setMainText(str).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightAction$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectDaliLightAction.this.lambda$initActionView$0();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.light_on_1)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightAction$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectDaliLightAction.this.lambda$initActionView$2();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.light_off_1)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightAction$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectDaliLightAction.this.lambda$initActionView$4();
            }
        })));
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mActionAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightAction$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActSelectDaliLightAction.this.lambda$initActionView$5(baseQuickAdapter2, view, i2);
            }
        });
        this.mActionAdapter.bindToRecyclerView(((ActSelectLightBinding) this.mViewBinding).rvAction);
        ((ActSelectLightBinding) this.mViewBinding).rvAction.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectLightBinding) this.mViewBinding).rvAction.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$0() {
        NavUtils.destination(ActSelectDaliColor.class).withInt(Constants.LIGHT_TYPE, this.lightType).withLong(Constants.CONTROL_ID, ProductRepository.getControlId(this.controlObject)).withBoolean(Constants.GROUP_CONTROL, ProductRepository.isGroup(this.controlObject)).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$2() {
        ((ActSelectLightActionVM) this.mViewModel).selectOn(DaliProHelper.getZoneNum(this.controlObject));
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).sendOnOff(getApplicationContext(), true);
        if (this.isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            lambda$initActionView$3(this);
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDaliLightAction.this.lambda$initActionView$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$4() {
        ((ActSelectLightActionVM) this.mViewModel).selectClose(DaliProHelper.getZoneNum(this.controlObject));
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).sendOnOff(getApplicationContext(), false);
        if (this.isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            lambda$initActionView$3(this);
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectDaliLightAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectDaliLightAction.this.lambda$initActionView$3((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionView$5(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mActionAdapter.getData().get(i).getAction().execute();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            if (this.isLocal) {
                SceneHelper.instance().maskType = MaskType.LOCAL;
            }
            lambda$initActionView$3(this);
        }
    }
}