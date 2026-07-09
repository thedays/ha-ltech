package com.ltech.smarthome.ui.control;

import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSearchSceneBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.ui.scene.local.ActAddLocalScene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSearchScene extends VMActivity<ActSearchSceneBinding, FtSceneVM> implements TextWatcher {
    private BaseQuickAdapter<Scene, BaseViewHolder> mAdapter;

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_search_scene;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        initListView();
        ((ActSearchSceneBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(R.string.app_str_search_scene_name);
        ((ActSearchSceneBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActSearchSceneBinding) this.mViewBinding).searchBar.cancelBtn.setVisibility(0);
        ((ActSearchSceneBinding) this.mViewBinding).searchBar.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchScene.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ActSearchScene.this.finishActivity();
            }
        });
        ((ActSearchSceneBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchScene.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSearchSceneBinding) ActSearchScene.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((FtSceneVM) this.mViewModel).placeId.setValue(Long.valueOf(getIntent().getLongExtra(Constants.PLACE_ID, 0L)));
        ((FtSceneVM) this.mViewModel).refreshData.observe(this, new Observer<List<Scene>>() { // from class: com.ltech.smarthome.ui.control.ActSearchScene.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<Scene> scenes) {
                ActSearchScene.this.mAdapter.replaceData(scenes);
            }
        });
    }

    private void initListView() {
        ((ActSearchSceneBinding) this.mViewBinding).rvDevice.setLayoutManager(new GridLayoutManager(this, 2));
        RecyclerView recyclerView = ((ActSearchSceneBinding) this.mViewBinding).rvDevice;
        BaseQuickAdapter<Scene, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<Scene, BaseViewHolder>(this, R.layout.item_cloud_scene) { // from class: com.ltech.smarthome.ui.control.ActSearchScene.4
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
        ((ActSearchSceneBinding) this.mViewBinding).rvDevice.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.control.ActSearchScene.5
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchScene.6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Scene scene = (Scene) ActSearchScene.this.mAdapter.getItem(position);
                if (scene != null) {
                    if (scene.getSceneType() == 2) {
                        ((FtSceneVM) ActSearchScene.this.mViewModel).executeLocalScene(ActSearchScene.this.activity, scene.getSceneNum());
                    } else {
                        ((FtSceneVM) ActSearchScene.this.mViewModel).executeCloudScene(scene);
                    }
                }
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.control.ActSearchScene.7
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!((FtSceneVM) ActSearchScene.this.mViewModel).getCurrentPlace().isOwner() && !((FtSceneVM) ActSearchScene.this.mViewModel).getCurrentPlace().isManager()) {
                    ActSearchScene.this.showNoPermissionDialog();
                    return;
                }
                Scene scene = (Scene) ActSearchScene.this.mAdapter.getItem(position);
                if (scene != null) {
                    Injection.limiter().reset(Injection.keyCreator().sceneContentKey(scene.getSceneId()));
                    NavUtils.destination(ActAddLocalScene.class).withLong(Constants.PLACE_ID, ((FtSceneVM) ActSearchScene.this.mViewModel).getCurrentPlace().getPlaceId()).withInt(Constants.ICON_POSITION, scene.getIconPos()).withString(Constants.SCENE_NAME, scene.getSceneName()).withInt(Constants.SCENE_NUM, scene.getSceneNum()).withLong(Constants.SCENE_ID, scene.getSceneId()).navigation(ActSearchScene.this.activity);
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (((FtSceneVM) this.mViewModel).request != null) {
            ((FtSceneVM) this.mViewModel).request.retry();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((FtSceneVM) this.mViewModel).search(((ActSearchSceneBinding) this.mViewBinding).searchBar.searchEdtTxt.getText().toString());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_scence).emptyTryStringRes(R.string.add_group1));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        this.mAdapter.replaceData(new ArrayList());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showError() {
        super.showError();
        this.mAdapter.replaceData(new ArrayList());
    }

    @Override // com.ltech.smarthome.base.VMActivity
    public void refreshData() {
        ((FtSceneVM) this.mViewModel).request.refresh();
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        ((FtSceneVM) this.mViewModel).search(editable.toString());
    }
}