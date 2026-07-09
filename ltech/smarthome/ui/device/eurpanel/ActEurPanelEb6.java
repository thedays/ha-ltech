package com.ltech.smarthome.ui.device.eurpanel;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.badge.BadgeDrawable;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.databinding.ActEurPanelEb6Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.base.BaseControlActivity;
import com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6;
import com.ltech.smarthome.ui.device.knobpanel.ActKnobPanelVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.Utils;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActEurPanelEb6 extends BaseControlActivity<ActEurPanelEb6Binding, ActEurPanelEb6VM> {
    private BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> infoAdapter;
    private GridLayoutManager mGridLayoutManager;
    private BaseQuickAdapter<RelateInfoItem, BaseViewHolder> sceneAdapter;
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_eur_panel_eb6;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setEditImage(R.mipmap.ic_setting);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        ((ActEurPanelEb6VM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActEurPanelEb6VM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActEurPanelEb6VM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        ((ActEurPanelEb6VM) this.mViewModel).groupList.setValue(Injection.repo().group().getGroupListByPlaceId(((ActEurPanelEb6VM) this.mViewModel).placeId));
        ((ActEurPanelEb6VM) this.mViewModel).deviceList.setValue(Injection.repo().device().getDeviceListByPlaceId(((ActEurPanelEb6VM) this.mViewModel).placeId));
        ((ActEurPanelEb6VM) this.mViewModel).controlObject.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelEb6.this.lambda$startObserve$0(obj);
            }
        });
        ((ActEurPanelEb6VM) this.mViewModel).showBindEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelEb6.this.lambda$startObserve$1((Void) obj);
            }
        });
        this.showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActEurPanelEb6.this.lambda$startObserve$2((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Object obj) {
        if (obj instanceof Device) {
            Device device = (Device) obj;
            setTitle(device.getDeviceName());
            ((ActEurPanelEb6VM) this.mViewModel).initRelateInfoList(device);
            initActionInfoView();
            initRelatedSceneView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(Void r4) {
        RelateInfoItem relateInfoItem = RelateInfoUtils.relatedInfoList.get(0);
        if (isOwnerOrManager()) {
            ((ActEurPanelEb6VM) this.mViewModel).showZoneBindDialog(0, relateInfoItem, true);
        } else {
            this.showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Void r1) {
        showNoPermissionDialog();
    }

    private void initActionInfoView() {
        ((ActEurPanelEb6Binding) this.mViewBinding).tvInstruction.setText(((ActEurPanelEb6VM) this.mViewModel).getInstructionName(this, ((ActEurPanelEb6VM) this.mViewModel).relateInfo));
        ((ActEurPanelEb6Binding) this.mViewBinding).tvBindName.setText(((ActEurPanelEb6VM) this.mViewModel).getRelateInfoString(this, ((ActEurPanelEb6VM) this.mViewModel).relateInfo));
        BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> baseQuickAdapter = this.infoAdapter;
        if (baseQuickAdapter == null) {
            BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder> baseQuickAdapter2 = new BaseQuickAdapter<ActKnobPanelVM.ActionItem, BaseViewHolder>(this, R.layout.item_sq_panel_action, ((ActEurPanelEb6VM) this.mViewModel).actionItemList) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.chad.library.adapter.base.BaseQuickAdapter
                public void convert(BaseViewHolder helper, ActKnobPanelVM.ActionItem item) {
                    helper.setImageResource(R.id.iv_action, item.iconRes);
                    if (item.actionInfo != null) {
                        helper.setText(R.id.tv_action, item.actionName).setGone(R.id.tv_action, true);
                        helper.setText(R.id.tv_action_tip, item.actionInfo).setGone(R.id.tv_action_tip, true);
                        helper.setGone(R.id.tv_action_1, false);
                    } else {
                        helper.setGone(R.id.tv_action, false);
                        helper.setGone(R.id.tv_action_tip, false);
                        helper.setText(R.id.tv_action_1, item.actionName).setGone(R.id.tv_action_1, true);
                    }
                }
            };
            this.infoAdapter = baseQuickAdapter2;
            baseQuickAdapter2.bindToRecyclerView(((ActEurPanelEb6Binding) this.mViewBinding).rvKnobAction);
            ((ActEurPanelEb6Binding) this.mViewBinding).rvKnobAction.setHasFixedSize(true);
            ((ActEurPanelEb6Binding) this.mViewBinding).rvKnobAction.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(SizeUtils.dp2px(5.0f), SizeUtils.dp2px(8.0f), SizeUtils.dp2px(5.0f), SizeUtils.dp2px(8.0f));
                }
            });
            ((DefaultItemAnimator) ((ActEurPanelEb6Binding) this.mViewBinding).rvKnobAction.getItemAnimator()).setSupportsChangeAnimations(false);
        } else {
            baseQuickAdapter.setNewData(((ActEurPanelEb6VM) this.mViewModel).actionItemList);
        }
        if (((ActEurPanelEb6VM) this.mViewModel).relateInfo.objectId == 0) {
            ((ActEurPanelEb6Binding) this.mViewBinding).tvTipMessage.setVisibility(0);
            ((ActEurPanelEb6Binding) this.mViewBinding).rvKnobAction.setLayoutManager(new GridLayoutManager(this, 4));
        } else {
            ((ActEurPanelEb6Binding) this.mViewBinding).tvTipMessage.setVisibility(8);
            ((ActEurPanelEb6Binding) this.mViewBinding).rvKnobAction.setLayoutManager(new GridLayoutManager(this, 2));
        }
    }

    private void initRelatedSceneView() {
        if (this.sceneAdapter == null) {
            this.mGridLayoutManager = new GridLayoutManager(this, 2);
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(R.layout.item_smart_panel_key_eb6, new ArrayList());
            this.sceneAdapter = anonymousClass3;
            anonymousClass3.bindToRecyclerView(((ActEurPanelEb6Binding) this.mViewBinding).rvScene);
            ((ActEurPanelEb6Binding) this.mViewBinding).rvScene.setLayoutManager(this.mGridLayoutManager);
            ((ActEurPanelEb6Binding) this.mViewBinding).rvScene.setHasFixedSize(true);
        }
        this.sceneAdapter.replaceData(RelateInfoUtils.relatedSceneInfoList);
    }

    /* renamed from: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6$3, reason: invalid class name */
    class AnonymousClass3 extends BaseQuickAdapter<RelateInfoItem, BaseViewHolder> {
        AnonymousClass3(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder helper, final RelateInfoItem item) {
            final int bindingAdapterPosition = helper.getBindingAdapterPosition();
            LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.layout_content);
            ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.height = ActEurPanelEb6.this.mGridLayoutManager.getHeight() / 2;
            if (bindingAdapterPosition == 0) {
                linearLayout.setGravity(BadgeDrawable.TOP_START);
                float f = 25;
                layoutParams2.setMargins(0, Utils.dip2px(this.mContext, f), Utils.dip2px(this.mContext, f), 0);
            } else if (bindingAdapterPosition == 1) {
                linearLayout.setGravity(BadgeDrawable.TOP_END);
                float f2 = 25;
                layoutParams2.setMargins(Utils.dip2px(this.mContext, f2), Utils.dip2px(this.mContext, f2), 0, 0);
            } else if (bindingAdapterPosition == 2) {
                linearLayout.setGravity(BadgeDrawable.BOTTOM_START);
                float f3 = 25;
                layoutParams2.setMargins(0, 0, Utils.dip2px(this.mContext, f3), Utils.dip2px(this.mContext, f3));
            } else {
                linearLayout.setGravity(BadgeDrawable.BOTTOM_END);
                float f4 = 25;
                layoutParams2.setMargins(Utils.dip2px(this.mContext, f4), 0, 0, Utils.dip2px(this.mContext, f4));
            }
            if (item.infoName == null || item.type == 0) {
                helper.setText(R.id.tv_device_name, ActEurPanelEb6.this.getString(R.string.app_scene) + (bindingAdapterPosition + 1));
                helper.setText(R.id.tv_sub_text, R.string.no_bind);
            } else {
                helper.setText(R.id.tv_device_name, item.infoName);
                helper.setText(R.id.tv_sub_text, item.actionInfo);
            }
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6$3$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ActEurPanelEb6.AnonymousClass3.this.lambda$convert$0(item, bindingAdapterPosition, view);
                }
            });
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.ltech.smarthome.ui.device.eurpanel.ActEurPanelEb6$3$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean lambda$convert$1;
                    lambda$convert$1 = ActEurPanelEb6.AnonymousClass3.this.lambda$convert$1(bindingAdapterPosition, item, view);
                    return lambda$convert$1;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(RelateInfoItem relateInfoItem, int i, View view) {
            if (relateInfoItem.infoName == null || relateInfoItem.type != 3) {
                return;
            }
            ((ActEurPanelEb6VM) ActEurPanelEb6.this.mViewModel).executeCurScene(ActEurPanelEb6.this.activity, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$convert$1(int i, RelateInfoItem relateInfoItem, View view) {
            if (ActEurPanelEb6.this.isOwnerOrManager()) {
                ((ActEurPanelEb6VM) ActEurPanelEb6.this.mViewModel).showRelatedSceneDialog(i, (relateInfoItem.infoName == null || relateInfoItem.type == 0) ? false : true);
            } else {
                ActEurPanelEb6.this.showNoPermissionDialogEvent.call();
            }
            return true;
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        NavHelper.goSetting(((ActEurPanelEb6VM) this.mViewModel).controlObject.getValue());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        ((ActEurPanelEb6VM) this.mViewModel).controlObject.setValue(Injection.repo().device().getDeviceById(((ActEurPanelEb6VM) this.mViewModel).controlId));
    }
}