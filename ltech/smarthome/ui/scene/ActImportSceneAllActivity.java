package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActImportSceneAllActivity extends BaseRoomSceneActivity<ActSelectSceneBinding, FtRoomSceneVM> {
    protected long sceneId;
    public MediatorLiveData<Scene> selectScene = new MediatorLiveData<>();

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.import_scene));
        setEditImage(R.mipmap.icon_search);
        this.listType = 2;
        ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(getString(R.string.finish));
        ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.scene.ActImportSceneAllActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActImportSceneAllActivity.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.selectSceneList.size() == 0) {
            SmartToast.showShort(R.string.please_choose);
        } else {
            this.selectScene.setValue(this.selectSceneList.get(0));
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.sceneId = getIntent().getLongExtra(Constants.SCENE_ID, -1L);
        handleData(Transformations.switchMap(this.selectScene, new Function1() { // from class: com.ltech.smarthome.ui.scene.ActImportSceneAllActivity$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$1;
                lambda$startObserve$1 = ActImportSceneAllActivity.this.lambda$startObserve$1((Scene) obj);
                return lambda$startObserve$1;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.scene.ActImportSceneAllActivity$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActImportSceneAllActivity.this.lambda$startObserve$2((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$1(Scene scene) {
        return Injection.repo().scene().getSceneContent(this, this.selectScene.getValue().getSceneId()).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        final List<Scene.SceneContent> sceneContents = ((Scene) list.get(0)).getSceneContents();
        getIntent().getBooleanExtra(Constants.IS_ACTION_EMPTY, false);
        if (sceneContents == null) {
            sceneContents = new ArrayList<>();
        }
        final Intent intent = new Intent();
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.app_str_import_scene_normal));
        arrayList.add(getString(R.string.app_str_import_scene_all_action));
        SelectListDialog.asDefault(true).setSelectList(arrayList).setTitle(getString(R.string.please_choose)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setConfirmAction(new IAction<Integer>() { // from class: com.ltech.smarthome.ui.scene.ActImportSceneAllActivity.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(Integer integer) {
                intent.putExtra("type", integer.intValue());
                SceneHelper.instance().actionList = sceneContents;
                ActImportSceneAllActivity.this.setResult(3016, intent);
                ActImportSceneAllActivity.this.finishActivity();
            }
        }).setSelectPosition(0).showDialog(this);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ((ActSelectSceneBinding) this.mViewBinding).setTitleGone(true);
        ((ActSelectSceneBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActSelectSceneBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected boolean filterScene(Scene scene) {
        this.sceneId = getIntent().getLongExtra(Constants.SCENE_ID, -1L);
        if (scene.getSceneType() == 4) {
            return DaliProHelper.isSceneVisible(scene);
        }
        return getIntent().getBooleanExtra(Constants.IS_DYNAMIC_SCENE, false) ? scene.isDynamic() && this.sceneId != scene.getSceneId() : (scene.isDynamic() || this.sceneId == scene.getSceneId()) ? false : true;
    }
}