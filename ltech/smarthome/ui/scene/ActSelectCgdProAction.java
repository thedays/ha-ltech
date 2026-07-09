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
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.product_agreement.param.LightCmdParam;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectCgdProAction extends BaseNormalActivity<ActSelectBinding> implements ISelectAction {
    private long deviceId;
    private boolean isLocal;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mAdapter;
    private long placeId;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_select;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$save$5(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        ArrayList arrayList = new ArrayList();
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        this.placeId = Injection.repo().home().getSelectPlace().getValue().getPlaceId();
        arrayList.add(new GoItem().setMainText(getString(R.string.dali_scene)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectCgdProAction.this.lambda$initView$0();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.dali_group)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectCgdProAction.this.lambda$initView$1();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.dali_light)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProAction$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectCgdProAction.this.lambda$initView$2();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.dali_broadcast)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProAction$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectCgdProAction.this.showBroadcastDialog();
            }
        })));
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProAction$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectCgdProAction.this.lambda$initView$3(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        SceneHelper.instance().controlObject = null;
        NavUtils.destination(ActSelectDaliScene.class).withLong("device_id", this.deviceId).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        if (SceneHelper.instance().bindingType == 0) {
            NavUtils.destination(ActSelectCgdProDaliAdd.class).withLong("device_id", this.deviceId).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withInt(Constants.SHOW_TYPE, 1).withDefaultRequestCode().navigation(this);
        } else {
            NavUtils.destination(ActSelectDaliLightGroupForAction.class).withLong("device_id", this.deviceId).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withInt(Constants.SHOW_TYPE, 1).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2() {
        if (SceneHelper.instance().bindingType == 0) {
            NavUtils.destination(ActSelectCgdProDaliAdd.class).withLong("device_id", this.deviceId).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withInt(Constants.SHOW_TYPE, 2).withDefaultRequestCode().navigation(this);
        } else {
            NavUtils.destination(ActSelectDaliLightGroupForAction.class).withLong("device_id", this.deviceId).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withInt(Constants.SHOW_TYPE, 2).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mAdapter.getData().get(i).getAction().execute();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBroadcastDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.dali_dim) + "/" + getString(R.string.dali_ct) + "/" + getString(R.string.dali_rgb));
        arrayList.add(getString(R.string.light_on_1));
        arrayList.add(getString(R.string.light_off_1));
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProAction$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectCgdProAction.this.lambda$showBroadcastDialog$4((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBroadcastDialog$4(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSelectDaliColor.class).withInt(Constants.LIGHT_TYPE, 10009).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withBoolean(Constants.IS_LOCAL_SCENE, this.isLocal).withDefaultRequestCode().navigation(this);
            return;
        }
        if (intValue == 1) {
            LightCmdParam lightCmdParam = new LightCmdParam();
            lightCmdParam.setCmdType(1);
            lightCmdParam.setZoneNum(DaliProHelper.BROADCAST_ADD);
            lightCmdParam.setOn(true);
            lightCmdParam.addExtParam(SceneHelper.OPTION, "7");
            lightCmdParam.addExtParam(SceneHelper.OPTION_VALUE, getString(R.string.light_on_1));
            SceneHelper.instance().cmdParam = lightCmdParam;
            save();
            return;
        }
        if (intValue != 2) {
            return;
        }
        LightCmdParam lightCmdParam2 = new LightCmdParam();
        lightCmdParam2.setCmdType(1);
        lightCmdParam2.setZoneNum(DaliProHelper.BROADCAST_ADD);
        lightCmdParam2.setOn(false);
        lightCmdParam2.addExtParam(SceneHelper.OPTION, "4");
        lightCmdParam2.addExtParam(SceneHelper.OPTION_VALUE, getString(R.string.light_off_1));
        SceneHelper.instance().cmdParam = lightCmdParam2;
        save();
    }

    private void save() {
        if (this.isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            lambda$save$5(this);
            return;
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.scene.ActSelectCgdProAction$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectCgdProAction.this.lambda$save$5((Boolean) obj);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            lambda$save$5(this);
        }
    }
}