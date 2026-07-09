package com.ltech.smarthome.ui.device.super_panel;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActG4MaxKeySetBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.utils.Constants;
import com.yuyh.library.imgsel.utils.DisplayUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActG4MaxKeySet extends VMActivity<ActG4MaxKeySetBinding, ActSuperPanelKeySetVM> {
    private BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_g4_max_key_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.key_set));
        setBackImage(R.mipmap.icon_back);
        ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList.clear();
        for (int i = 0; i < 4; i++) {
            ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList.add(new SuperPanelInfo.PanelKeyInfo());
        }
        BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder>(R.layout.item_super_panel_key_set, ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList) { // from class: com.ltech.smarthome.ui.device.super_panel.ActG4MaxKeySet.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, SuperPanelInfo.PanelKeyInfo item) {
                helper.setGone(R.id.v_left, (helper.getAdapterPosition() - ((ActG4MaxKeySetBinding) ActG4MaxKeySet.this.mViewBinding).rvContent.getHeaderCount()) % 2 == 0).setGone(R.id.v_right, (helper.getAdapterPosition() - ((ActG4MaxKeySetBinding) ActG4MaxKeySet.this.mViewBinding).rvContent.getHeaderCount()) % 2 == 1);
                helper.setBackgroundColor(R.id.layout_item_bg, Color.parseColor("#FFFFFF"));
                String keyName = ((ActSuperPanelKeySetVM) ActG4MaxKeySet.this.mViewModel).getKeyName(item);
                if (TextUtils.isEmpty(keyName)) {
                    keyName = ActG4MaxKeySet.this.getString(R.string.app_str_key) + helper.getAdapterPosition();
                }
                helper.setText(R.id.tv_key_name, keyName);
                String funString = ((ActSuperPanelKeySetVM) ActG4MaxKeySet.this.mViewModel).getFunString(this.mContext, item);
                if (TextUtils.isEmpty(funString)) {
                    funString = ActG4MaxKeySet.this.getString(R.string.super_key_no);
                }
                helper.setText(R.id.tv_key_fun, funString).setGone(R.id.tv_key_fun, true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActG4MaxKeySetBinding) this.mViewBinding).rvContent);
        ((ActG4MaxKeySetBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActG4MaxKeySetBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActG4MaxKeySetBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActG4MaxKeySet$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActG4MaxKeySet.this.lambda$initView$0(baseQuickAdapter2, view, i2);
            }
        });
        ((ActG4MaxKeySetBinding) this.mViewBinding).rvContent.addHeaderView(LayoutInflater.from(this).inflate(R.layout.head_g4_max_key_set, (ViewGroup) ((ActG4MaxKeySetBinding) this.mViewBinding).rvContent.getParent(), false));
        View view = new View(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, DisplayUtils.dip2px(this, 2.0f)));
        view.setBackgroundColor(getResources().getColor(R.color.color_border_gray));
        ((ActG4MaxKeySetBinding) this.mViewBinding).rvContent.addFooterView(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (((ActSuperPanelKeySetVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSuperPanelKeySetVM) this.mViewModel).getCurrentPlace().isManager()) {
            ((ActSuperPanelKeySetVM) this.mViewModel).showAddDialog(this, i - ((ActG4MaxKeySetBinding) this.mViewBinding).rvContent.getHeaderCount());
        } else {
            ((ActSuperPanelKeySetVM) this.mViewModel).showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        showContent();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        if (((ActSuperPanelKeySetVM) this.mViewModel).request != null) {
            ((ActSuperPanelKeySetVM) this.mViewModel).request.retry();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActSuperPanelKeySetVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        ((ActSuperPanelKeySetVM) this.mViewModel).deviceId = getIntent().getLongExtra("device_id", -1L);
        Injection.limiter().reset(Injection.keyCreator().superPanelKeywordInfoKey(((ActSuperPanelKeySetVM) this.mViewModel).deviceId));
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(((ActSuperPanelKeySetVM) this.mViewModel).placeId);
        List<Group> groupListByPlaceId = Injection.repo().group().getGroupListByPlaceId(((ActSuperPanelKeySetVM) this.mViewModel).placeId);
        List<Scene> allSceneByPlaceId = Injection.repo().scene().getAllSceneByPlaceId(((ActSuperPanelKeySetVM) this.mViewModel).placeId);
        ((ActSuperPanelKeySetVM) this.mViewModel).deviceList.setValue(deviceListByPlaceId);
        ((ActSuperPanelKeySetVM) this.mViewModel).groupList.setValue(groupListByPlaceId);
        ((ActSuperPanelKeySetVM) this.mViewModel).sceneList.setValue(allSceneByPlaceId);
        handleData(Transformations.switchMap(((ActSuperPanelKeySetVM) this.mViewModel).groupList, new Function1() { // from class: com.ltech.smarthome.ui.device.super_panel.ActG4MaxKeySet$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$1;
                lambda$startObserve$1 = ActG4MaxKeySet.this.lambda$startObserve$1((List) obj);
                return lambda$startObserve$1;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActG4MaxKeySet$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActG4MaxKeySet.this.lambda$startObserve$2((List) obj);
            }
        });
        ((ActSuperPanelKeySetVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActG4MaxKeySet$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActG4MaxKeySet.this.lambda$startObserve$3((Void) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$1(List list) {
        if (list == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().device().getSuperPanelKeyInfo(this, ((ActSuperPanelKeySetVM) this.mViewModel).deviceId).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        if (list.isEmpty()) {
            this.mAdapter.setNewData(new ArrayList());
            return;
        }
        if (list.get(0) != null && ((SuperPanelInfo) list.get(0)).getPanelKeyInfo() != null) {
            Iterator<SuperPanelInfo.PanelKeyInfo> it = ((SuperPanelInfo) list.get(0)).getPanelKeyInfo().iterator();
            while (it.hasNext()) {
                ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList.set(r0.getKeywords() - 1, it.next());
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(Void r1) {
        showNoPermissionDialog();
    }
}