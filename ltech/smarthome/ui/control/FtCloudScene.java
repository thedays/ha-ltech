package com.ltech.smarthome.ui.control;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import anetwork.channel.util.RequestConstant;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtCloudSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.ui.control.FtCloudScene;
import com.ltech.smarthome.ui.scene.ActAddCloudScene;
import com.ltech.smarthome.ui.scene.ActSortCloudScene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtCloudScene extends VMFragment<FtCloudSceneBinding, FtCloudSceneVM> implements IIntelligence {
    private long deviceId;
    private boolean groupControl;
    private BaseQuickAdapter<Scene, BaseViewHolder> mAdapter;

    static /* synthetic */ boolean lambda$showNoPermissionDialog$5(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goSearch() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_cloud_scene;
    }

    public static FtCloudScene newInstance(long deviceId, boolean groupControl) {
        FtCloudScene ftCloudScene = new FtCloudScene();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putBoolean(Constants.GROUP_CONTROL, groupControl);
        ftCloudScene.setArguments(bundle);
        return ftCloudScene;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.groupControl = arguments.getBoolean(Constants.GROUP_CONTROL);
            this.deviceId = arguments.getLong("device_id");
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        super.initView();
        ((FtCloudSceneBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((FtCloudSceneBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtCloudSceneBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((FtCloudSceneBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$$ExternalSyntheticLambda2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtCloudScene.this.lambda$initView$0(refreshLayout);
            }
        });
        initListView();
        ((FtCloudSceneBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(R.string.app_str_search_scene_name);
        ((FtCloudSceneBinding) this.mViewBinding).searchBar.searchLayout.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.FtCloudScene.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavUtils.destination(ActSearchScene.class).withLong(Constants.PLACE_ID, ((FtCloudSceneVM) FtCloudScene.this.mViewModel).getCurrentPlace().getPlaceId()).withBoolean(Constants.IS_LOCAL_SCENE, false).navigation(FtCloudScene.this.getActivity());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((FtCloudSceneVM) this.mViewModel).request != null) {
            Injection.limiter().reset(Injection.keyCreator().sceneKey(((FtCloudSceneVM) this.mViewModel).getCurrentPlace().getPlaceId()));
            ((FtCloudSceneVM) this.mViewModel).request.refresh();
        } else {
            queryScene();
        }
        ((FtCloudSceneBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.deviceId > 0) {
            queryScene();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        if (this.deviceId <= 0) {
            handleData(((FtCloudSceneVM) this.mViewModel).sceneLiveData, new IAction() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtCloudScene.this.lambda$startObserve$1((List) obj);
                }
            });
        }
        ((FtCloudSceneVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$$ExternalSyntheticLambda5
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtCloudScene.this.lambda$startObserve$2((Void) obj);
            }
        });
        ((FtCloudSceneVM) this.mViewModel).refreshData.observe(this, new Observer<List<Scene>>() { // from class: com.ltech.smarthome.ui.control.FtCloudScene.2
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Scene> scenes) {
                FtCloudScene.this.mAdapter.replaceData(scenes);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ((FtCloudSceneVM) this.mViewModel).allData.clear();
        ((FtCloudSceneVM) this.mViewModel).allData.addAll(list);
        this.mAdapter.replaceData(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }

    private void queryScene() {
        if (this.groupControl) {
            ((ObservableSubscribeProxy) Injection.net().queryGroupScene(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtCloudScene.this.lambda$queryScene$3((ListSceneResponse) obj);
                }
            });
        } else {
            ((ObservableSubscribeProxy) Injection.net().queryDeviceScene(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtCloudScene.this.lambda$queryScene$4((ListSceneResponse) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryScene$3(ListSceneResponse listSceneResponse) throws Exception {
        showRelateScene(Injection.repo().scene().getSceneListFromNet(listSceneResponse, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryScene$4(ListSceneResponse listSceneResponse) throws Exception {
        showRelateScene(Injection.repo().scene().getSceneListFromNet(listSceneResponse, 1));
    }

    private void showRelateScene(List<Scene> list) {
        if (list.size() > 0) {
            showContent();
            ((FtCloudSceneVM) this.mViewModel).allData.clear();
            ((FtCloudSceneVM) this.mViewModel).allData.addAll(list);
            this.mAdapter.replaceData(list);
            return;
        }
        showEmpty();
    }

    private void initListView() {
        ((FtCloudSceneBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        RecyclerView recyclerView = ((FtCloudSceneBinding) this.mViewBinding).rvContent;
        BaseQuickAdapter<Scene, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Scene, BaseViewHolder>(this, R.layout.item_cloud_scene) { // from class: com.ltech.smarthome.ui.control.FtCloudScene.3
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, Scene item) {
                helper.setText(R.id.tv_name, item.getSceneName());
                helper.setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos()));
                helper.addOnClickListener(R.id.iv_edit);
            }
        };
        this.mAdapter = baseQuickAdapter;
        recyclerView.setAdapter(baseQuickAdapter);
        this.mAdapter.setOnItemClickListener(new AnonymousClass4());
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.control.FtCloudScene.5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (((FtCloudSceneVM) FtCloudScene.this.mViewModel).getCurrentPlace().isOwner() || ((FtCloudSceneVM) FtCloudScene.this.mViewModel).getCurrentPlace().isManager()) {
                    Scene scene = (Scene) FtCloudScene.this.mAdapter.getItem(position);
                    if (scene != null) {
                        Injection.limiter().reset(Injection.keyCreator().sceneContentKey(scene.getSceneId()));
                        NavUtils.destination(ActAddCloudScene.class).withLong(Constants.PLACE_ID, Injection.repo().home().getSelectPlace().getValue().getPlaceId()).withInt(Constants.ICON_POSITION, scene.getIconPos()).withString(Constants.SCENE_NAME, scene.getSceneName()).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtCloudScene.this.getActivity());
                        return;
                    }
                    return;
                }
                FtCloudScene.this.showNoPermissionDialog();
            }
        });
    }

    /* renamed from: com.ltech.smarthome.ui.control.FtCloudScene$4, reason: invalid class name */
    class AnonymousClass4 implements BaseQuickAdapter.OnItemClickListener {
        AnonymousClass4() {
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Scene scene = (Scene) FtCloudScene.this.mAdapter.getItem(position);
            if (scene != null) {
                LHomeLog.i(getClass(), "场景执行 ID " + scene.getSceneId());
                ((ObservableSubscribeProxy) Injection.net().executeScene(scene.getSceneId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$4$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        FtCloudScene.AnonymousClass4.this.lambda$onItemClick$0((Disposable) obj);
                    }
                }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(FtCloudScene.this.getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$4$$ExternalSyntheticLambda1
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        FtCloudScene.AnonymousClass4.this.lambda$onItemClick$1(obj);
                    }
                }, new Consumer() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$4$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        FtCloudScene.AnonymousClass4.this.lambda$onItemClick$2((Throwable) obj);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$0(Disposable disposable) throws Exception {
            FtCloudScene.this.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.executing));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$1(Object obj) throws Exception {
            FtCloudScene ftCloudScene = FtCloudScene.this;
            ftCloudScene.showSuccessDialog(ftCloudScene.getString(R.string.execute_success));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$2(Throwable th) throws Exception {
            FtCloudScene ftCloudScene = FtCloudScene.this;
            ftCloudScene.showErrorDialog(ftCloudScene.getString(R.string.execute_fail));
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onRetry() {
        super.onRetry();
        if (((FtCloudSceneVM) this.mViewModel).request != null) {
            ((FtCloudSceneVM) this.mViewModel).request.retry();
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goSort() {
        Place place = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
        if (place.isOwner() || place.isManager()) {
            NavUtils.destination(ActSortCloudScene.class).withLong(Constants.PLACE_ID, place.getPlaceId()).navigation(this);
        } else {
            ((FtCloudSceneVM) this.mViewModel).showNoPermissionDialogEvent.call();
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goAdd() {
        LHomeLog.i(RequestConstant.ENV_TEST, getClass(), "FtCloudScene goAdd mviewModel--->" + this.mViewModel + "  mviewbinding--->" + this.mViewBinding);
        Place place = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
        if (place.isOwner() || place.isManager()) {
            NavUtils.destination(ActAddCloudScene.class).withLong(Constants.PLACE_ID, place.getPlaceId()).withString(Constants.SCENE_NAME, ((FtCloudSceneVM) this.mViewModel).getSceneName(getActivity())).navigation(this);
        } else {
            ((FtCloudSceneVM) this.mViewModel).showNoPermissionDialogEvent.call();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(ActivityUtils.getTopActivity() instanceof ActIntelligence ? R.string.no_relate_cloud_scene : R.string.no_scence).emptyTryStringRes(R.string.add_group1));
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onEmptyTry() {
        super.onEmptyTry();
        goAdd();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void showEmpty() {
        super.showEmpty();
        this.mAdapter.replaceData(new ArrayList());
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void showError() {
        super.showError();
        this.mAdapter.replaceData(new ArrayList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNoPermissionDialog() {
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.FtCloudScene$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtCloudScene.lambda$showNoPermissionDialog$5(baseDialog, view);
            }
        });
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        if (((FtCloudSceneVM) this.mViewModel).request != null) {
            ((FtCloudSceneVM) this.mViewModel).request.refresh();
        } else {
            queryScene();
        }
    }
}