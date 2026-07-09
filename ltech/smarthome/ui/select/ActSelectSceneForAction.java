package com.ltech.smarthome.ui.select;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.product_agreement.param.SceneCmdParam;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectSceneForAction extends BaseRoomSceneActivity<ActSelectSceneBinding, FtRoomSceneVM> implements ISelectAction {
    private boolean automationSelectScene;

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$save$0(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.listType = 2;
        if (SceneHelper.instance().controlObject != null) {
            this.selectSceneList.addAll((List) SceneHelper.instance().controlObject);
        }
        boolean booleanExtra = getIntent().getBooleanExtra(Constants.AUTOMATION_SELECT_SCENE, false);
        this.automationSelectScene = booleanExtra;
        if (booleanExtra) {
            this.allowMultiSelect = true;
            ((ActSelectSceneBinding) this.mViewBinding).title.ivSearch.setVisibility(0);
            setEditString(getString(R.string.app_str_select_all));
            ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(getString(R.string.finish_with_num, new Object[]{Integer.valueOf(this.selectSceneList.size())}));
        } else {
            this.allowMultiSelect = false;
            setEditImage(R.mipmap.icon_search);
            ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(getString(R.string.finish));
        }
        ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.select.ActSelectSceneForAction.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActSelectSceneForAction.this.save();
            }
        });
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        if (this.automationSelectScene) {
            super.edit();
            return;
        }
        ((ActSelectSceneBinding) this.mViewBinding).setTitleGone(true);
        ((ActSelectSceneBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActSelectSceneBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save() {
        if (this.selectSceneList.isEmpty()) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        SceneHelper.instance().maskType = MaskType.SCENE;
        SceneHelper.instance().controlObject = this.automationSelectScene ? this.selectSceneList : this.selectSceneList.get(0);
        if (SceneHelper.instance().bindingType == 4 || SceneHelper.instance().bindingType == 5) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            selectExtendScene(this.selectSceneList.get(0));
        }
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.select.ActSelectSceneForAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectSceneForAction.this.lambda$save$0((Boolean) obj);
            }
        });
    }

    public void selectExtendScene(Scene scene) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(7);
        sceneCmdParam.setSceneNum(scene.getSceneNum());
        SceneHelper.instance().cmdParam = sceneCmdParam;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected void changeSelectCount(int selectCount) {
        if (this.automationSelectScene) {
            ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setText(String.format(getResources().getString(R.string.app_str_select_finish), Integer.valueOf(selectCount)));
        }
    }
}