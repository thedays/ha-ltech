package com.ltech.smarthome.ui.share;

import android.graphics.Rect;
import android.view.View;
import androidx.lifecycle.Lifecycle;
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
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.placeuser.InvitationPlaceUserRequest;
import com.ltech.smarthome.net.request.role.UpdateSceneRoleRequest;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActSelectSceneForPermission extends BaseSelectSceneActivity implements ISelectAction {
    private boolean[] selectArray;
    private long userId;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_cloud_scene;
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Scene item) {
        helper.setText(R.id.tv_scene_name, item.getSceneName()).setImageResource(R.id.iv_scene_icon, SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), item.getIconPos())).setImageResource(R.id.iv_select, this.selectArray[helper.getAdapterPosition()] ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
    }

    @Override // com.ltech.smarthome.base.BaseSelectSceneActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.select_scene));
        setBackImage(R.mipmap.icon_back);
        setEditString(getString(R.string.save));
        ((ActSelectBinding) this.mViewBinding).layoutSearch.setVisibility(0);
        this.userId = getIntent().getLongExtra(Constants.USER_ID, -1L);
        ((ActSelectBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActSelectBinding) this.mViewBinding).rvContent.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.share.ActSelectSceneForPermission.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(ConvertUtils.dp2px(8.0f), 0, ConvertUtils.dp2px(8.0f), ConvertUtils.dp2px(16.0f));
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.share.ActSelectSceneForPermission$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectSceneForPermission.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        this.selectArray[i] = !r3[i];
        baseQuickAdapter.notifyItemChanged(i);
    }

    @Override // com.ltech.smarthome.base.BaseSelectSceneActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.request, new IAction() { // from class: com.ltech.smarthome.ui.share.ActSelectSceneForPermission$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectSceneForPermission.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        if (list.isEmpty()) {
            showEmpty();
            return;
        }
        this.selectArray = new boolean[list.size()];
        int i = 0;
        if (PlaceShareHelper.getInstance().enterType == 1002) {
            Arrays.fill(this.selectArray, false);
            while (i < this.selectArray.length) {
                Iterator<Long> it = PlaceShareHelper.getInstance().sceneIds.iterator();
                while (it.hasNext()) {
                    if (it.next().longValue() == ((Scene) list.get(i)).getSceneId()) {
                        this.selectArray[i] = true;
                    }
                }
                i++;
            }
        } else if (PlaceShareHelper.getInstance().sceneIds == null) {
            Arrays.fill(this.selectArray, true);
        } else {
            Arrays.fill(this.selectArray, false);
            while (i < this.selectArray.length) {
                Iterator<Long> it2 = PlaceShareHelper.getInstance().sceneIds.iterator();
                while (it2.hasNext()) {
                    if (it2.next().longValue() == ((Scene) list.get(i)).getSceneId()) {
                        this.selectArray[i] = true;
                    }
                }
                i++;
            }
        }
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
        PlaceShareHelper.getInstance().sceneIds = getSelectScene();
        if (PlaceShareHelper.getInstance().enterType == 1002) {
            updateScenePermission();
        } else {
            finishActivity();
        }
    }

    private void updateScenePermission() {
        ((ObservableSubscribeProxy) Injection.net().updateSceneRole(generateRequest()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActSelectSceneForPermission$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSceneForPermission.this.lambda$updateScenePermission$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.share.ActSelectSceneForPermission$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActSelectSceneForPermission.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.share.ActSelectSceneForPermission$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActSelectSceneForPermission.this.lambda$updateScenePermission$3(obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateScenePermission$2(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateScenePermission$3(Object obj) throws Exception {
        LHomeLog.i(getClass(), "updateDeviceRole: response enter");
        finishActivity();
    }

    private UpdateSceneRoleRequest generateRequest() {
        ArrayList arrayList = new ArrayList();
        Iterator<Long> it = PlaceShareHelper.getInstance().sceneIds.iterator();
        while (it.hasNext()) {
            arrayList.add(new InvitationPlaceUserRequest.SceneId(it.next().longValue()));
        }
        return new UpdateSceneRoleRequest(this.placeId, this.userId, arrayList);
    }

    private List<Long> getSelectScene() {
        ArrayList arrayList = new ArrayList();
        if (this.selectArray != null) {
            int i = 0;
            while (true) {
                boolean[] zArr = this.selectArray;
                if (i >= zArr.length) {
                    break;
                }
                if (zArr[i]) {
                    arrayList.add(Long.valueOf(((Scene) this.mAdapter.getData().get(i)).getSceneId()));
                }
                i++;
            }
        }
        return arrayList;
    }
}