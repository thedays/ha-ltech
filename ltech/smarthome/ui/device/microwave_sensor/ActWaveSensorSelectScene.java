package com.ltech.smarthome.ui.device.microwave_sensor;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.product_agreement.param.SceneCmdParam;

/* loaded from: classes4.dex */
public class ActWaveSensorSelectScene extends BaseRoomSceneActivity<ActSelectSceneBinding, FtRoomSceneVM> implements ISelectAction {
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected int getSceneType() {
        return 2;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$2(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.select_local_scene));
        setEditImage(R.mipmap.icon_search);
        this.listType = 2;
        ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(getString(R.string.finish));
        ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorSelectScene$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActWaveSensorSelectScene.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (this.selectSceneList.size() == 0) {
            SmartToast.showShort(R.string.please_choose);
        } else {
            selectRelateScene(this.selectSceneList.get(0));
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        ((ActSelectSceneBinding) this.mViewBinding).setTitleGone(true);
        ((ActSelectSceneBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActSelectSceneBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
    }

    public void selectRelateScene(Scene scene) {
        SceneCmdParam sceneCmdParam = new SceneCmdParam();
        sceneCmdParam.setCmdType(7);
        sceneCmdParam.setSceneNum(scene.getSceneNum());
        WaveSensorHelper.instance().cmdParam = sceneCmdParam;
        WaveSensorHelper.instance().relateObject = scene;
        WaveSensorHelper.instance().setSensorRelateSceneParam();
    }
}