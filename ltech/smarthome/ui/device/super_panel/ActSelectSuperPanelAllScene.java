package com.ltech.smarthome.ui.device.super_panel;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.super_panel.SetSuperPanelResponse;
import com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectSuperPanelAllScene extends BaseRoomSceneActivity<ActSelectSceneBinding, FtRoomSceneVM> {
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.listType = 2;
        this.allowMultiSelect = true;
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.selectSceneList.add(Injection.repo().scene().getSceneBySceneId(j));
            }
        }
        ((ActSelectSceneBinding) this.mViewBinding).title.ivSearch.setVisibility(0);
        setEditString(getString(R.string.app_str_select_all));
        ((ActSelectSceneBinding) this.mViewBinding).setBottomTip(getString(R.string.finish_with_num, new Object[]{Integer.valueOf(this.selectSceneList.size())}));
        ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelAllScene.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ActSelectSuperPanelAllScene.this.save();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.selectSceneList.size(); i++) {
            arrayList.add(Long.valueOf(this.selectSceneList.get(i).getSceneId()));
        }
        ((ObservableSubscribeProxy) Injection.net().setSuperPanelScene(getIntent().getLongExtra("device_id", -1L), arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelAllScene$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSuperPanelAllScene.this.lambda$save$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelAllScene$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectSuperPanelAllScene.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSelectSuperPanelAllScene$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSuperPanelAllScene.this.lambda$save$1((SetSuperPanelResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$0(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$1(SetSuperPanelResponse setSuperPanelResponse) throws Exception {
        ArrayList arrayList = new ArrayList();
        if (setSuperPanelResponse.getScenes() != null) {
            Iterator<SetSuperPanelResponse.ScenesBean> it = setSuperPanelResponse.getScenes().iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(it.next().getSceneid()));
            }
        }
        Injection.repo().device().setSuperPanelScene(setSuperPanelResponse.getInfo().getPanelid(), arrayList);
        SmartToast.showShort(getString(R.string.save_success));
        setResult(-1);
        finishActivity();
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected void changeSelectCount(int selectCount) {
        ((ActSelectSceneBinding) this.mViewBinding).tvBottom.setText(String.format(getResources().getString(R.string.app_str_select_finish), Integer.valueOf(selectCount)));
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity
    protected boolean filterScene(Scene scene) {
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false) && scene.getSceneType() == 1) {
            return false;
        }
        return super.filterScene(scene);
    }
}