package com.ltech.smarthome.ui.device.super_panel;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActDaliSelectBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.super_panel.SetSuperPanelResponse;
import com.ltech.smarthome.ui.device.dalipro.FtDaliScene;
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
public class ActSuperPanelSelectDaliScene extends VMActivity<ActDaliSelectBinding, FtRoomSceneVM> {
    private FtDaliScene ftScene;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_dali_select;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.select_dali_scene));
        this.ftScene = FtDaliScene.newInstance(getIntent().getLongExtra(Constants.RELATE_ID, 0L), true);
        ((ActDaliSelectBinding) this.mViewBinding).tvBottom.setText(R.string.finish);
        setEditString(getString(R.string.app_str_select_all));
        FragmentUtils.add(getSupportFragmentManager(), this.ftScene, R.id.layout_content);
        ((ActDaliSelectBinding) this.mViewBinding).tvBottom.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectDaliScene$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActSuperPanelSelectDaliScene.this.lambda$initView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        save();
    }

    private void save() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.ftScene.getSelectSceneList().size(); i++) {
            arrayList.add(Long.valueOf(this.ftScene.getSelectSceneList().get(i).getSceneId()));
        }
        ((ObservableSubscribeProxy) Injection.net().setSuperPanelScene(getIntent().getLongExtra("device_id", -1L), arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectDaliScene$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelSelectDaliScene.this.lambda$save$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectDaliScene$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSuperPanelSelectDaliScene.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectDaliScene$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSuperPanelSelectDaliScene.this.lambda$save$2((SetSuperPanelResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$2(SetSuperPanelResponse setSuperPanelResponse) throws Exception {
        ArrayList arrayList = new ArrayList();
        if (setSuperPanelResponse.getScenes() != null) {
            Iterator<SetSuperPanelResponse.ScenesBean> it = setSuperPanelResponse.getScenes().iterator();
            while (it.hasNext()) {
                arrayList.add(Long.valueOf(it.next().getSceneid()));
            }
        }
        Injection.repo().device().setSuperPanelScene(setSuperPanelResponse.getInfo().getPanelid(), arrayList);
        SmartToast.showShort(getString(R.string.save_success));
        setResult(3001);
        finishActivity();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        this.ftScene.editClickEvent.call();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.ftScene.refreshEdit.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectDaliScene$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelSelectDaliScene.this.lambda$startObserve$3((Boolean) obj);
            }
        });
        this.ftScene.selectNumber.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectDaliScene$$ExternalSyntheticLambda4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelSelectDaliScene.this.lambda$startObserve$4((Integer) obj);
            }
        });
        this.ftScene.firstIn.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelSelectDaliScene$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelSelectDaliScene.this.lambda$startObserve$5((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Boolean bool) {
        if (bool.booleanValue()) {
            setEditString(getString(R.string.app_str_cancel_select_all));
        } else {
            setEditString(getString(R.string.app_str_select_all));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$4(Integer num) {
        ((ActDaliSelectBinding) this.mViewBinding).tvBottom.setText(String.format(getString(R.string.finish_with_num), num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$5(Void r6) {
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.ftScene.getSelectSceneList().add(Injection.repo().scene().getSceneBySceneId(j));
            }
        }
    }
}