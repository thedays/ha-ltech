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
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ActSelectLightActionVM;
import com.ltech.smarthome.ui.scene.ActSelectSpSceneKeyAction;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ActSelectSpLocalSceneAction extends VMActivity<ActSelectBinding, ActSelectLightActionVM> implements ISelectAction {
    private BaseQuickAdapter<GoItem, BaseViewHolder> mAdapter;
    private String productId;

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
    public /* synthetic */ void lambda$save$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        ArrayList arrayList = new ArrayList();
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        arrayList.add(new GoItem().setMainText(getString(R.string.function_open)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSpLocalSceneAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectSpLocalSceneAction.this.lambda$initView$0();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.close)).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSpLocalSceneAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectSpLocalSceneAction.this.lambda$initView$1();
            }
        })));
        BaseQuickAdapter<GoItem, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<GoItem, BaseViewHolder>(this, R.layout.item_go_1, arrayList) { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSpLocalSceneAction.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, GoItem item) {
                helper.setText(R.id.tv_main, item.getMainText());
                ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.scene.local.ActSelectSpLocalSceneAction$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i) {
                ActSelectSpLocalSceneAction.this.lambda$initView$2(baseQuickAdapter2, view, i);
            }
        });
        this.mAdapter.bindToRecyclerView(((ActSelectBinding) this.mViewBinding).rvContent);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new LinearLayoutManager(this));
        ((ActSelectBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0() {
        NavUtils.destination(ActSelectSpSceneKeyAction.class).withString(Constants.PRODUCT_ID, this.productId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withBoolean(Constants.KEY_ACTION_TYPE, true).withBoolean(Constants.IS_LOCAL_SCENE, true).withBoolean(Constants.IS_SEPT, RelaySeparationHelper.isRelaySeparationDevice(SceneHelper.instance().controlObject)).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1() {
        NavUtils.destination(ActSelectSpSceneKeyAction.class).withString(Constants.PRODUCT_ID, this.productId).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType(SceneHelper.instance().controlObject)).withBoolean(Constants.KEY_ACTION_TYPE, false).withBoolean(Constants.IS_LOCAL_SCENE, true).withBoolean(Constants.IS_SEPT, RelaySeparationHelper.isRelaySeparationDevice(SceneHelper.instance().controlObject)).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.mAdapter.getData().get(i).getAction().execute();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            lambda$save$0(this);
        }
    }
}