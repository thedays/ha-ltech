package com.ltech.smarthome.ui.device.light;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActPubListBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.GradientScene;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.ui.device.light.ActGradientScene;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActGradientScene extends VMActivity<ActPubListBinding, ActGradientSceneVM> implements ISelectAction {
    private BaseQuickAdapter<GradientScene, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_pub_list;
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

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_gradient_scene));
        setBackImage(R.mipmap.icon_back);
        ((ActGradientSceneVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActGradientSceneVM) this.mViewModel).isLocal = getIntent().getBooleanExtra(Constants.IS_LOCAL_SCENE, false);
        Group groupById = Injection.repo().group().getGroupById(((ActGradientSceneVM) this.mViewModel).controlId);
        if (groupById != null) {
            ((ActGradientSceneVM) this.mViewModel).controlObject.setValue(groupById);
        }
        initRv();
        if (((ActGradientSceneVM) this.mViewModel).isLocal) {
            SceneHelper.instance().maskType = MaskType.LOCAL;
            setEditString(getString(R.string.save));
        }
    }

    private void initRv() {
        ((ActPubListBinding) this.mViewBinding).rv.setSwipeMenuCreator(new SwipeMenuCreator() { // from class: com.ltech.smarthome.ui.device.light.ActGradientScene$$ExternalSyntheticLambda0
            @Override // com.yanzhenjie.recyclerview.SwipeMenuCreator
            public final void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
                ActGradientScene.this.lambda$initRv$0(swipeMenu, swipeMenu2, i);
            }
        });
        ((ActPubListBinding) this.mViewBinding).rv.setOnItemMenuClickListener(new OnItemMenuClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActGradientScene$$ExternalSyntheticLambda1
            @Override // com.yanzhenjie.recyclerview.OnItemMenuClickListener
            public final void onItemClick(SwipeMenuBridge swipeMenuBridge, int i) {
                ActGradientScene.this.lambda$initRv$1(swipeMenuBridge, i);
            }
        });
        ((ActPubListBinding) this.mViewBinding).rv.setSwipeItemMenuEnabled(Injection.repo().home().getSelPlace().isManager() || Injection.repo().home().getSelPlace().isOwner());
        ((ActPubListBinding) this.mViewBinding).rv.setLayoutManager(new LinearLayoutManager(this));
        SwipeRecyclerView swipeRecyclerView = ((ActPubListBinding) this.mViewBinding).rv;
        BaseItemDraggableAdapter<GradientScene, BaseViewHolder> baseItemDraggableAdapter = new BaseItemDraggableAdapter<GradientScene, BaseViewHolder>(R.layout.item_device_select, new ArrayList()) { // from class: com.ltech.smarthome.ui.device.light.ActGradientScene.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, GradientScene gradientScene) {
                holder.setGone(R.id.tv_place_info, false);
                if (!((ActGradientSceneVM) ActGradientScene.this.mViewModel).isLocal) {
                    holder.setGone(R.id.layout_edit, true);
                    holder.addOnClickListener(R.id.layout_edit);
                } else {
                    holder.setGone(R.id.iv_go, true);
                    holder.setImageResource(R.id.iv_go, gradientScene.isSel() ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
                }
                if (!TextUtils.isEmpty(gradientScene.getImgurl())) {
                    if (gradientScene.getImgurl().startsWith("https://")) {
                        new RequestOptions();
                        Glide.with(this.mContext).load(gradientScene.getImgurl()).apply((BaseRequestOptions<?>) RequestOptions.circleCropTransform()).into((ImageView) holder.getView(R.id.iv_icon));
                    } else if (Integer.parseInt(gradientScene.getImgurl()) == 2) {
                        ((ImageView) holder.getView(R.id.iv_icon)).setImageResource(R.mipmap.bg_palette_2);
                    } else {
                        ((ImageView) holder.getView(R.id.iv_icon)).setImageResource(R.mipmap.bg_palette_1);
                    }
                } else {
                    ((ImageView) holder.getView(R.id.iv_icon)).setImageResource(R.mipmap.bg_palette_1);
                }
                holder.setText(R.id.tv_device_name, gradientScene.getGsname());
            }
        };
        this.mAdapter = baseItemDraggableAdapter;
        swipeRecyclerView.setAdapter(baseItemDraggableAdapter);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.light.ActGradientScene.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                GradientScene gradientScene = (GradientScene) ActGradientScene.this.mAdapter.getData().get(i);
                if (!((ActGradientSceneVM) ActGradientScene.this.mViewModel).isLocal) {
                    ((ActGradientSceneVM) ActGradientScene.this.mViewModel).selPos = i;
                    ((ActGradientSceneVM) ActGradientScene.this.mViewModel).apply(gradientScene);
                    return;
                }
                gradientScene.setSel(true);
                ActGradientScene.this.mAdapter.setData(i, gradientScene);
                GradientScene gradientScene2 = (GradientScene) ActGradientScene.this.mAdapter.getData().get(((ActGradientSceneVM) ActGradientScene.this.mViewModel).selPos);
                gradientScene2.setSel(false);
                ActGradientScene.this.mAdapter.setData(((ActGradientSceneVM) ActGradientScene.this.mViewModel).selPos, gradientScene2);
                ((ActGradientSceneVM) ActGradientScene.this.mViewModel).selectGradientScene(gradientScene);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$0(SwipeMenu swipeMenu, SwipeMenu swipeMenu2, int i) {
        swipeMenu2.addMenuItem(new SwipeMenuItem(this).setTextSize(14).setWidth(SizeUtils.dp2px(60.0f)).setHeight(-1).setText(R.string.delete).setTextColor(-1).setBackgroundColorResource(R.color.color_text_red));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initRv$1(SwipeMenuBridge swipeMenuBridge, int i) {
        swipeMenuBridge.closeMenu();
        if (-1 == swipeMenuBridge.getDirection() && swipeMenuBridge.getPosition() == 0) {
            ((ActGradientSceneVM) this.mViewModel).delete(this.mAdapter.getData().get(i).getGsid());
            this.mAdapter.remove(i);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.device.light.ActGradientScene$3, reason: invalid class name */
    class AnonymousClass3 implements BaseQuickAdapter.OnItemChildClickListener {
        AnonymousClass3() {
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
        public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
            final GradientScene gradientScene = (GradientScene) ActGradientScene.this.mAdapter.getData().get(i);
            EditDialog.asDefault().setContent(gradientScene.getGsname()).setTitle(ActGradientScene.this.getString(R.string.app_str_gradient_scene_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.light.ActGradientScene$3$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActGradientScene.AnonymousClass3.this.lambda$onItemChildClick$0(gradientScene, i, (String) obj);
                }
            }).showDialog(ActGradientScene.this.activity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemChildClick$0(GradientScene gradientScene, int i, String str) {
            gradientScene.setGsname(str);
            ActGradientScene.this.mAdapter.setData(i, gradientScene);
            ((ActGradientSceneVM) ActGradientScene.this.mViewModel).editName(str, gradientScene);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActGradientSceneVM) this.mViewModel).controlObject.observe(this, new Observer<Group>() { // from class: com.ltech.smarthome.ui.device.light.ActGradientScene.4
            @Override // androidx.lifecycle.Observer
            public void onChanged(Group group) {
                ((ActGradientSceneVM) ActGradientScene.this.mViewModel).loadScene();
            }
        });
        ((ActGradientSceneVM) this.mViewModel).data.observe(this, new Observer<List<GradientScene>>() { // from class: com.ltech.smarthome.ui.device.light.ActGradientScene.5
            @Override // androidx.lifecycle.Observer
            public void onChanged(List<GradientScene> gradientScenes) {
                ActGradientScene.this.mAdapter.replaceData(gradientScenes);
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        lambda$edit$2(this);
    }
}