package com.ltech.smarthome.ui.device.super_panel;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSortActivity;
import com.ltech.smarthome.databinding.ActSortBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.PanelSortInfoRequest;
import com.ltech.smarthome.ui.scene.SceneHelper;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSortScene extends BaseSortActivity<Scene> {
    private long deviceId;
    public List<Scene> sceneData = new ArrayList();
    public List<Long> sceneIdList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected int itemLayout() {
        return R.layout.item_scene_new;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        setEditString(getString(R.string.save));
        setTitle(getString(R.string.sort_scene));
        ((ActSortBinding) this.mViewBinding).layoutLoad.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActSortBinding) this.mViewBinding).layoutLoad.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSortScene.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(10.0f));
            }
        });
        long[] longArrayExtra = getIntent().getLongArrayExtra(Constants.SCENE_ID_ARRAY);
        if (longArrayExtra != null) {
            for (long j : longArrayExtra) {
                this.sceneIdList.add(Long.valueOf(j));
            }
        }
        if (this.sceneIdList.size() > 0) {
            for (int i = 0; i < this.sceneIdList.size(); i++) {
                this.sceneData.add(Injection.repo().scene().getSceneBySceneId(this.sceneIdList.get(i).longValue()));
            }
            setDataList(this.sceneData);
        }
        this.deviceId = getIntent().getLongExtra("device_id", -1L);
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected List<Scene> getItemList() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSortActivity
    public void convertView(BaseViewHolder helper, Scene item) {
        helper.setText(R.id.tv_name, item.getSceneName()).setImageResource(R.id.appCompatImageView14, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setImageResource(R.id.iv_edit, R.mipmap.ic_item_sort).setBackgroundRes(R.id.tv_type, item.getSceneType() == 1 ? R.drawable.shape_scene_cloud_bg : R.drawable.shape_scene_local_bg).setText(R.id.tv_type, item.getSceneType() == 1 ? R.string.type_cloud : R.string.type_local).setTextColor(R.id.tv_type, getResources().getColor(item.getSceneType() == 1 ? R.color.color_text_blue : R.color.color_text_red));
    }

    @Override // com.ltech.smarthome.base.BaseSortActivity
    protected void saveData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.dataList.size(); i++) {
            PanelSortInfoRequest.SceneSortBean sceneSortBean = new PanelSortInfoRequest.SceneSortBean();
            sceneSortBean.setSceneid(((Scene) this.dataList.get(i)).getSceneId());
            sceneSortBean.setSorting(i);
            arrayList.add(sceneSortBean);
        }
        ((ObservableSubscribeProxy) Injection.net().panelSortScene(this.deviceId, arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSortScene$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortScene.this.lambda$saveData$0((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSortScene$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSortScene.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSortScene$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSortScene.this.lambda$saveData$1(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$0(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveData$1(Object obj) throws Exception {
        setResult(-1);
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }
}