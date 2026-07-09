package com.ltech.smarthome.ui.automation;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseListActivity;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.scene_param.AppNoticeParam;
import com.ltech.smarthome.ui.item.GoItem;
import com.ltech.smarthome.ui.scene.ActSelectCgdPro;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.select.ActSelectSceneForAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectAutoAction extends BaseListActivity<GoItem> implements ISelectAction {
    private String automationName;
    private boolean isLocalAutomation;
    private long placeId;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_go;
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

    @Override // com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_action));
        setBackImage(R.mipmap.icon_back);
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.automationName = getIntent().getStringExtra(Constants.AUTOMATION_NAME);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoAction$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectAutoAction.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ((GoItem) this.mAdapter.getData().get(i)).getAction().execute();
    }

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected List<GoItem> getList() {
        ArrayList arrayList = new ArrayList(4);
        this.isLocalAutomation = getIntent().getBooleanExtra(Constants.IS_LOCAL_AUTOMATION, false);
        arrayList.add(new GoItem().setMainText(getString(R.string.action_scene_tip)).setImageRes(R.mipmap.ic_auto_scene).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAutoAction.this.lambda$getList$1();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.action_dali_scene_tip)).setImageRes(R.mipmap.ic_auto_dali_scene).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAutoAction.this.lambda$getList$2();
            }
        })));
        arrayList.add(new GoItem().setMainText(getString(R.string.action_automation_tip)).setImageRes(R.mipmap.ic_auto_automation).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoAction$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAutoAction.this.lambda$getList$3();
            }
        })));
        if (!this.isLocalAutomation) {
            arrayList.add(new GoItem().setMainText(getString(R.string.action_notice_tip)).setImageRes(R.mipmap.ic_auto_notice).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoAction$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.binding.command.BindingAction
                public final void call() {
                    ActSelectAutoAction.this.lambda$getList$4();
                }
            })));
        }
        arrayList.add(new GoItem().setMainText(getString(R.string.action_device_tip)).setImageRes(R.mipmap.ic_auto_device).setGoRes(R.mipmap.icon_more).setAction(new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.automation.ActSelectAutoAction$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActSelectAutoAction.this.lambda$getList$5();
            }
        })));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$1() {
        SceneHelper.instance().controlObject = SceneHelper.instance().cacheObject.get(SceneHelper.CACHE_SELECT_SCENE);
        NavUtils.destination(ActSelectSceneForAction.class).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.IS_LOCAL_AUTOMATION, this.isLocalAutomation).withBoolean(Constants.AUTOMATION_SELECT_SCENE, true).withDefaultRequestCode().navigation(this.activity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$2() {
        NavUtils.destination(ActSelectCgdPro.class).withLong(Constants.PLACE_ID, this.placeId).withDefaultRequestCode().navigation(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$3() {
        SceneHelper.instance().controlObject = SceneHelper.instance().cacheObject.get(SceneHelper.CACHE_SELECT_AUTOMATION);
        if (!this.isLocalAutomation) {
            SceneHelper.goSelectAction(this, 3, this.placeId);
        } else {
            NavUtils.destination(ActSelectAutomationForAction.class).withLong(Constants.PLACE_ID, this.placeId).withBoolean(Constants.SELECT_ACTION, true).withBoolean(Constants.IS_LOCAL_AUTOMATION, true).withDefaultRequestCode().navigation(this.activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$4() {
        SceneHelper.instance().maskType = MaskType.APP_NOTICE;
        SceneHelper.instance().controlObject = createAppNoticeParam();
        setResult(3001);
        finishActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getList$5() {
        SceneHelper.goSelectAction(this, 6, this.placeId);
    }

    private AppNoticeParam createAppNoticeParam() {
        AppNoticeParam appNoticeParam = new AppNoticeParam();
        appNoticeParam.title = getString(R.string.app_str_notice);
        appNoticeParam.body = "\"" + this.automationName + "\"" + getString(R.string.app_str_has_been_executed);
        appNoticeParam.users = new ArrayList();
        return appNoticeParam;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, GoItem item) {
        helper.setBackgroundRes(R.id.iv_icon, item.getImageRes()).setText(R.id.tv_main, item.getMainText()).setImageResource(R.id.iv_go, item.getGoRes());
        ((AppCompatTextView) helper.getView(R.id.tv_main)).getPaint().setFakeBoldText(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            lambda$save$0(this);
        }
    }
}