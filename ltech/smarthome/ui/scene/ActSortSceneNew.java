package com.ltech.smarthome.ui.scene;

import androidx.lifecycle.Lifecycle;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelectSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.scene.SortSceneRequest;
import com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity;
import com.ltech.smarthome.ui.newselect.FtRoomSceneVM;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSortSceneNew extends BaseRoomSceneActivity<ActSelectSceneBinding, FtRoomSceneVM> {
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setEditString(getString(R.string.save));
        setTitle(getString(R.string.sort_scene));
        ((ActSelectSceneBinding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelectSceneBinding) this.mViewBinding).layoutSortAndType.setVisibility(8);
        this.listType = 3;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < getSceneList().size(); i++) {
            SortSceneRequest.SceneSortBean sceneSortBean = new SortSceneRequest.SceneSortBean();
            sceneSortBean.sceneid = getSceneList().get(i).getSceneId();
            arrayList.add(sceneSortBean);
        }
        ((ObservableSubscribeProxy) Injection.net().sortScene(arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActSortSceneNew$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortSceneNew.this.lambda$edit$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.scene.ActSortSceneNew$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSortSceneNew.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.scene.ActSortSceneNew$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortSceneNew.this.lambda$edit$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$0(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$edit$1(Object obj) throws Exception {
        Injection.repo().scene().sortScene(getSceneList());
        finishActivity();
    }
}