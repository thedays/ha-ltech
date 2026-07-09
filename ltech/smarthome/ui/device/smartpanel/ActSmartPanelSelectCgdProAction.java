package com.ltech.smarthome.ui.device.smartpanel;

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
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ActSelectDaliColor;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.LightCmdParam;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectCgdProAction extends VMActivity<ActSelectBinding, ActSmartPanelSelectActionVM> implements ISelectAction {
    private long deviceId;
    private boolean isGq;
    private BaseQuickAdapter<GoItem, BaseViewHolder> mAdapter;
    private long placeId;
    private RelatedInfoExtParam.RelateInfo relateInfo;

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
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        ArrayList arrayList = new ArrayList();
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
        this.placeId = Injection.repo().home().getSelectPlace().getValue().getPlaceId();
        SceneHelper.instance().reset();
        SceneHelper.instance().bindingType = 1;
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.IS_GQ, false);
        this.isGq = booleanExtra;
        if (!booleanExtra) {
            arrayList.add(new GoItem().setMainText(getString(R.string.dali_scene)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSmartPanelSelectCgdProAction.this.lambda$initView$0();
                }
            })));
        }
        arrayList.add(new GoItem().setMainText(getString(R.string.dali_group)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSmartPanelSelectCgdProAction.this.lambda$initView$1();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.dali_light)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSmartPanelSelectCgdProAction.this.lambda$initView$2();
            }
        })));
        if (!this.isGq) {
            arrayList.add(new GoItem().setMainText(getString(R.string.dali_broadcast)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSmartPanelSelectCgdProAction.this.showBroadcastDialog();
                }
            })));
        }
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSmartPanelSelectCgdProAction.this.lambda$initView$3(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        NavUtils.destination(ActSmartPanelSelectDaliScene.class).withLong("device_id", this.deviceId).withLong(Constants.RELATE_ID, getIntent().getLongExtra(Constants.RELATE_ID, -1L)).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        this.relateInfo.action = 0;
        NavUtils.destination(ActSmartPanelSelectDaliLightGroup.class).withLong("device_id", this.deviceId).withBoolean(Constants.IS_GQ, this.isGq).withLong(Constants.RELATE_ID, getIntent().getLongExtra(Constants.RELATE_ID, -1L)).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.SHOW_TYPE, 1).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2() {
        this.relateInfo.action = 0;
        NavUtils.destination(ActSmartPanelSelectDaliLightGroup.class).withLong("device_id", this.deviceId).withBoolean(Constants.IS_GQ, this.isGq).withLong(Constants.RELATE_ID, getIntent().getLongExtra(Constants.RELATE_ID, -1L)).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withLong(Constants.PLACE_ID, this.placeId).withInt(Constants.SHOW_TYPE, 2).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mAdapter.getData().get(i).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition = getIntent().getIntExtra(Constants.RELATED_POSITION, -1);
        ((ActSmartPanelSelectActionVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        ((ActSmartPanelSelectActionVM) this.mViewModel).initRelateAssistant(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateObject = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.RELATE_ID, -1L));
        RelatedInfoExtParam.RelateInfo RelatedDeviceInfo = RelatedInfoExtParam.RelateInfo.RelatedDeviceInfo(this.deviceId);
        this.relateInfo = RelatedDeviceInfo;
        RelatedDeviceInfo.type = 8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBroadcastDialog() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.dali_dim) + "/" + getString(R.string.dali_ct) + "/" + getString(R.string.dali_rgb));
        arrayList.add(getString(R.string.light_on_1));
        arrayList.add(getString(R.string.light_off_1));
        this.relateInfo.action = 36;
        this.relateInfo.bindingZone = DaliProHelper.BROADCAST_ADD;
        SelectListDialog.asDefault(false).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setSelectPosition(-1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSmartPanelSelectCgdProAction.this.lambda$showBroadcastDialog$4((Integer) obj);
            }
        }).setSelectList(arrayList).showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBroadcastDialog$4(Integer num) {
        int intValue = num.intValue();
        if (intValue == 0) {
            NavUtils.destination(ActSelectDaliColor.class).withInt(Constants.LIGHT_TYPE, 10009).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.RELATE_ID, -1L)).withBoolean(Constants.IS_LOCAL_SCENE, true).withDefaultRequestCode().navigation(this);
            return;
        }
        if (intValue == 1) {
            this.relateInfo.wholeDataExtra = "0001";
            save();
        } else {
            if (intValue != 2) {
                return;
            }
            this.relateInfo.wholeDataExtra = "0000";
            save();
        }
    }

    private void save() {
        ((ActSmartPanelSelectActionVM) this.mViewModel).relateInfoAssistant.setRelateInfo(((ActSmartPanelSelectActionVM) this.mViewModel).relatePosition, this.relateInfo);
        if (Injection.state().isConnectOuterNet()) {
            if (((ActSmartPanelSelectActionVM) this.mViewModel).groupControl) {
                showLoadingDialog(getString(R.string.subscribing));
                ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe();
                return;
            } else if (((ActSmartPanelSelectActionVM) this.mViewModel).controlDevice.getProductId().equals(ProductId.ID_SMART_PANEL_S6B) && RelateInfoUtils.needShowTipDialog()) {
                RelateInfoUtils.showImageTipDialog(getString(R.string.s6b_click_tip), R.mipmap.pic_click_tip_s6b, this, new ImageTipDialog.OnConfirmCallback() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectCgdProAction$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.view.dialog.ImageTipDialog.OnConfirmCallback
                    public final void onConfirmClick(ImageTipDialog imageTipDialog) {
                        ActSmartPanelSelectCgdProAction.this.lambda$save$5(imageTipDialog);
                    }
                });
                return;
            } else {
                showLoadingDialog(getString(R.string.subscribing));
                ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe();
                return;
            }
        }
        SmartToast.showShort(R.string.no_network);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$5(ImageTipDialog imageTipDialog) {
        showLoadingDialog(getString(R.string.subscribing));
        ((ActSmartPanelSelectActionVM) this.mViewModel).subscribe();
        imageTipDialog.dismissDialog();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            if (this.isGq) {
                setResult(3001, data);
                finishActivity();
                return;
            }
            if ((SceneHelper.instance().cmdParam instanceof LightCmdParam) && this.relateInfo.action == 36) {
                LightCmdParam lightCmdParam = (LightCmdParam) SceneHelper.instance().cmdParam;
                this.relateInfo.wholeDataExtra = StringUtils.toHexString(lightCmdParam.getLightType()) + SceneHelper.instance().getConvertCmd(lightCmdParam).substring(2);
                save();
                return;
            }
            lambda$edit$1(this);
        }
    }
}