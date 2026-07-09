package com.ltech.smarthome.ui.control;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMFragment;
import com.ltech.smarthome.databinding.FtLocalSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.ui.scene.ActSortCloudScene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.local.ActAddLocalScene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class FtLocalScene extends VMFragment<FtLocalSceneBinding, FtSceneVM> implements IIntelligence {
    private long deviceId;
    private boolean groupControl;
    private BaseQuickAdapter<Scene, BaseViewHolder> mAdapter;

    static /* synthetic */ void lambda$showEditNameDialog$4(String str) {
    }

    static /* synthetic */ boolean lambda$showNoPermissionDialog$5(BaseDialog baseDialog, View view) {
        return false;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_local_scene;
    }

    public static FtLocalScene newInstance(long deviceId, boolean groupControl) {
        FtLocalScene ftLocalScene = new FtLocalScene();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        bundle.putBoolean(Constants.GROUP_CONTROL, groupControl);
        ftLocalScene.setArguments(bundle);
        return ftLocalScene;
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
        ((FtLocalSceneBinding) this.mViewBinding).refreshLayout.setEnableFooterTranslationContent(false);
        ((FtLocalSceneBinding) this.mViewBinding).refreshLayout.setEnableAutoLoadMore(false);
        ((FtLocalSceneBinding) this.mViewBinding).refreshLayout.setFooterHeight(0.0f);
        ((FtLocalSceneBinding) this.mViewBinding).refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ltech.smarthome.ui.control.FtLocalScene$$ExternalSyntheticLambda0
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                FtLocalScene.this.lambda$initView$0(refreshLayout);
            }
        });
        initListView();
        ((FtLocalSceneBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(R.string.app_str_search_scene_name);
        ((FtLocalSceneBinding) this.mViewBinding).searchBar.searchLayout.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.FtLocalScene.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavUtils.destination(ActSearchScene.class).withLong(Constants.PLACE_ID, ((FtSceneVM) FtLocalScene.this.mViewModel).getCurrentPlace().getPlaceId()).withBoolean(Constants.IS_LOCAL_SCENE, true).navigation(FtLocalScene.this.getActivity());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(RefreshLayout refreshLayout) {
        if (((FtSceneVM) this.mViewModel).request != null) {
            ((FtSceneVM) this.mViewModel).request.refresh();
        } else {
            queryScene();
        }
        ((FtLocalSceneBinding) this.mViewBinding).refreshLayout.finishRefresh();
    }

    private void initListView() {
        ((FtLocalSceneBinding) this.mViewBinding).rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        RecyclerView recyclerView = ((FtLocalSceneBinding) this.mViewBinding).rv;
        BaseQuickAdapter<Scene, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Scene, BaseViewHolder>(this, R.layout.item_cloud_scene) { // from class: com.ltech.smarthome.ui.control.FtLocalScene.2
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
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.control.FtLocalScene.3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Scene scene = (Scene) FtLocalScene.this.mAdapter.getItem(position);
                if (scene != null) {
                    ((FtSceneVM) FtLocalScene.this.mViewModel).executeLocalScene(FtLocalScene.this.getActivity(), scene.getSceneNum());
                }
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.control.FtLocalScene.4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (((FtSceneVM) FtLocalScene.this.mViewModel).getCurrentPlace().isOwner() || ((FtSceneVM) FtLocalScene.this.mViewModel).getCurrentPlace().isManager()) {
                    Scene scene = (Scene) FtLocalScene.this.mAdapter.getItem(position);
                    if (scene != null) {
                        NavUtils.destination(ActAddLocalScene.class).withLong(Constants.PLACE_ID, ((FtSceneVM) FtLocalScene.this.mViewModel).getCurrentPlace().getPlaceId()).withInt(Constants.ICON_POSITION, scene.getIconPos()).withString(Constants.SCENE_NAME, scene.getSceneName()).withInt(Constants.SCENE_NUM, scene.getSceneNum()).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(FtLocalScene.this);
                        return;
                    }
                    return;
                }
                FtLocalScene.this.showNoPermissionDialog();
            }
        });
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
            handleData(((FtSceneVM) this.mViewModel).sceneLiveData, new IAction() { // from class: com.ltech.smarthome.ui.control.FtLocalScene$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    FtLocalScene.this.lambda$startObserve$1((List) obj);
                }
            });
        }
        ((FtSceneVM) this.mViewModel).refreshData.observe(this, new Observer<List<Scene>>() { // from class: com.ltech.smarthome.ui.control.FtLocalScene.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Scene> scenes) {
                FtLocalScene.this.mAdapter.replaceData(scenes);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ((FtSceneVM) this.mViewModel).allData.clear();
        ((FtSceneVM) this.mViewModel).allData.addAll(list);
        this.mAdapter.replaceData(list);
    }

    private void queryScene() {
        if (this.groupControl) {
            ((ObservableSubscribeProxy) Injection.net().queryGroupScene(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtLocalScene$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtLocalScene.this.lambda$queryScene$2((ListSceneResponse) obj);
                }
            });
        } else {
            ((ObservableSubscribeProxy) Injection.net().queryDeviceScene(this.deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtLocalScene$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtLocalScene.this.lambda$queryScene$3((ListSceneResponse) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryScene$2(ListSceneResponse listSceneResponse) throws Exception {
        showRelateScene(Injection.repo().scene().getSceneListFromNet(listSceneResponse, 2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryScene$3(ListSceneResponse listSceneResponse) throws Exception {
        showRelateScene(Injection.repo().scene().getSceneListFromNet(listSceneResponse, 2));
    }

    private void showRelateScene(List<Scene> list) {
        if (list.size() > 0) {
            showContent();
            ((FtSceneVM) this.mViewModel).allData.clear();
            ((FtSceneVM) this.mViewModel).allData.addAll(list);
            this.mAdapter.replaceData(list);
            return;
        }
        showEmpty();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void onRetry() {
        super.onRetry();
        if (((FtSceneVM) this.mViewModel).request != null) {
            ((FtSceneVM) this.mViewModel).request.retry();
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goSort() {
        Place place = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
        if (place.isOwner() || place.isManager()) {
            NavUtils.destination(ActSortCloudScene.class).withLong(Constants.PLACE_ID, place.getPlaceId()).withBoolean(Constants.IS_LOCAL_SCENE, true).navigation(this);
        } else {
            showNoPermissionDialog();
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goAdd() {
        Place place = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
        if (place.isOwner() || place.isManager()) {
            showEditNameDialog();
        } else {
            showNoPermissionDialog();
        }
    }

    @Override // com.ltech.smarthome.ui.control.IIntelligence
    public void goSearch() {
        NavUtils.destination(ActSearchScene.class).withLong(Constants.PLACE_ID, ((FtSceneVM) this.mViewModel).getCurrentPlace().getPlaceId()).withBoolean(Constants.IS_LOCAL_SCENE, true).navigation(this);
    }

    private void showEditNameDialog() {
        EditDialog asDefault = EditDialog.asDefault();
        asDefault.setContent(getSceneName());
        asDefault.setTitle(getString(R.string.add_new_local_scene));
        asDefault.setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.control.FtLocalScene$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtLocalScene.lambda$showEditNameDialog$4((String) obj);
            }
        });
        asDefault.showDialog(getActivity());
        getActivity();
        asDefault.setActionType("ftLocalScene");
        ((InputMethodManager) getActivity().getSystemService("input_method")).toggleSoftInput(2, 0);
    }

    public String getSceneName() {
        String str = "";
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            str = getString(R.string.local_scene) + i;
            z = Injection.repo().scene().isSceneNameExist(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), str);
        }
        return str;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(ActivityUtils.getTopActivity() instanceof ActIntelligence ? R.string.no_relate_scene : R.string.no_scence).emptyTryStringRes(R.string.add_group1));
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
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.app_member_no_permission), getString(R.string.app_member_no_permission_tip)).setOkButton(getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.control.FtLocalScene$$ExternalSyntheticLambda5
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                return FtLocalScene.lambda$showNoPermissionDialog$5(baseDialog, view);
            }
        });
    }

    @Override // com.ltech.smarthome.base.VMFragment, com.ltech.smarthome.base.BaseNormalFragment
    public void refreshData() {
        if (((FtSceneVM) this.mViewModel).request != null) {
            ((FtSceneVM) this.mViewModel).request.refresh();
        } else {
            queryScene();
        }
    }
}