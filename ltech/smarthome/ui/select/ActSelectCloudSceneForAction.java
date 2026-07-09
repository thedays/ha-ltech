package com.ltech.smarthome.ui.select;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSelectSceneActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectBinding;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.SmartToast;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectCloudSceneForAction extends BaseSelectSceneActivity implements ISelectAction {
    private boolean automationSelectScene;
    private List<Scene> selectSceneList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_cloud_scene;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult, reason: merged with bridge method [inline-methods] */
    public /* synthetic */ void lambda$edit$2(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Scene item) {
        helper.setText(R.id.tv_scene_name, item.getSceneName()).setImageResource(R.id.iv_scene_icon, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setImageResource(R.id.iv_select, this.selectSceneList.contains(item) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
    }

    @Override // com.ltech.smarthome.base.BaseSelectSceneActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.select_scene));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        this.automationSelectScene = getIntent().getBooleanExtra(Constants.AUTOMATION_SELECT_SCENE, false);
        ((ActSelectBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        ((ActSelectBinding) this.mViewBinding).searchBar.searchEdtTxt.setHint(getString(R.string.app_str_search_scene_name));
        if (SceneHelper.instance().controlObject != null && this.automationSelectScene) {
            this.selectSceneList.addAll((List) SceneHelper.instance().controlObject);
        }
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActSelectBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.select.ActSelectCloudSceneForAction.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.select.ActSelectCloudSceneForAction$$ExternalSyntheticLambda2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectCloudSceneForAction.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (this.automationSelectScene) {
            Scene scene = (Scene) this.mAdapter.getData().get(i);
            if (this.selectSceneList.contains(scene)) {
                this.selectSceneList.remove(scene);
            } else {
                this.selectSceneList.add(scene);
            }
        } else {
            this.selectSceneList.clear();
            this.selectSceneList.add((Scene) this.mAdapter.getData().get(i));
        }
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // com.ltech.smarthome.base.BaseSelectSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.request, new IAction() { // from class: com.ltech.smarthome.ui.select.ActSelectCloudSceneForAction$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectCloudSceneForAction.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        this.allData = new ArrayList();
        this.allData.addAll(list);
        this.mAdapter.replaceData(list);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(R.string.no_scence));
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.selectSceneList.isEmpty()) {
            SmartToast.showShort(R.string.please_choose);
            return;
        }
        SceneHelper.instance().maskType = MaskType.SCENE;
        SceneHelper.instance().controlObject = this.automationSelectScene ? this.selectSceneList : this.selectSceneList.get(0);
        SceneHelper.instance().saveSelectResult(this, new IAction() { // from class: com.ltech.smarthome.ui.select.ActSelectCloudSceneForAction$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectCloudSceneForAction.this.lambda$edit$2((Boolean) obj);
            }
        });
    }
}