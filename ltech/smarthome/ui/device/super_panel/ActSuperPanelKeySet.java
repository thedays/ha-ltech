package com.ltech.smarthome.ui.device.super_panel;

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
import com.ltech.smarthome.databinding.ActSuperPanelKeySetBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSuperPanelKeySet extends VMActivity<ActSuperPanelKeySetBinding, ActSuperPanelKeySetVM> {
    private BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder> mAdapter;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_key_set;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.key_set));
        setBackImage(R.mipmap.icon_back);
        ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList.clear();
        for (int i = 0; i < 8; i++) {
            ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList.add(new SuperPanelInfo.PanelKeyInfo());
        }
        BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder>(R.layout.item_super_panel_key_set, ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, SuperPanelInfo.PanelKeyInfo item) {
                boolean z = false;
                BaseViewHolder gone = helper.setGone(R.id.v_left, (helper.getAdapterPosition() - ((ActSuperPanelKeySetBinding) ActSuperPanelKeySet.this.mViewBinding).rvContent.getHeaderCount()) % 2 == 0).setGone(R.id.v_right, (helper.getAdapterPosition() - ((ActSuperPanelKeySetBinding) ActSuperPanelKeySet.this.mViewBinding).rvContent.getHeaderCount()) % 2 == 1);
                if (helper.getAdapterPosition() - ((ActSuperPanelKeySetBinding) ActSuperPanelKeySet.this.mViewBinding).rvContent.getHeaderCount() != ActSuperPanelKeySet.this.mAdapter.getData().size() - 1 && helper.getAdapterPosition() - ((ActSuperPanelKeySetBinding) ActSuperPanelKeySet.this.mViewBinding).rvContent.getHeaderCount() != ActSuperPanelKeySet.this.mAdapter.getData().size() - 2) {
                    z = true;
                }
                gone.setGone(R.id.v_bottom, z);
                String keyName = ((ActSuperPanelKeySetVM) ActSuperPanelKeySet.this.mViewModel).getKeyName(item);
                if (TextUtils.isEmpty(keyName)) {
                    keyName = ActSuperPanelKeySet.this.getString(R.string.super_key_no);
                }
                helper.setText(R.id.tv_key_name, keyName);
                helper.setText(R.id.tv_key_fun, ((ActSuperPanelKeySetVM) ActSuperPanelKeySet.this.mViewModel).getFunString(this.mContext, item)).setGone(R.id.tv_key_fun, !TextUtils.isEmpty(r7));
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent);
        ((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent.setLayoutManager(new GridLayoutManager(this, 2));
        ((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActSuperPanelKeySet.this.lambda$initView$0(baseQuickAdapter2, view, i2);
            }
        });
        ((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent.addHeaderView(LayoutInflater.from(this).inflate(R.layout.head_super_panel_key_set, (ViewGroup) ((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent.getParent(), false));
        ((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent.addFooterView(LayoutInflater.from(this).inflate(R.layout.footer_super_panel_key_set, (ViewGroup) ((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent.getParent(), false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (((ActSuperPanelKeySetVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSuperPanelKeySetVM) this.mViewModel).getCurrentPlace().isManager()) {
            ((ActSuperPanelKeySetVM) this.mViewModel).showAddDialog(this, i - ((ActSuperPanelKeySetBinding) this.mViewBinding).rvContent.getHeaderCount());
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
        ((ActSuperPanelKeySetVM) this.mViewModel).productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        Injection.limiter().reset(Injection.keyCreator().superPanelKeywordInfoKey(((ActSuperPanelKeySetVM) this.mViewModel).deviceId));
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(((ActSuperPanelKeySetVM) this.mViewModel).placeId);
        List<Group> groupListByPlaceId = Injection.repo().group().getGroupListByPlaceId(((ActSuperPanelKeySetVM) this.mViewModel).placeId);
        List<Scene> allSceneByPlaceId = Injection.repo().scene().getAllSceneByPlaceId(((ActSuperPanelKeySetVM) this.mViewModel).placeId);
        ((ActSuperPanelKeySetVM) this.mViewModel).deviceList.setValue(deviceListByPlaceId);
        ((ActSuperPanelKeySetVM) this.mViewModel).groupList.setValue(groupListByPlaceId);
        ((ActSuperPanelKeySetVM) this.mViewModel).sceneList.setValue(allSceneByPlaceId);
        handleData(Transformations.switchMap(((ActSuperPanelKeySetVM) this.mViewModel).groupList, new Function1() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$1;
                lambda$startObserve$1 = ActSuperPanelKeySet.this.lambda$startObserve$1((List) obj);
                return lambda$startObserve$1;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelKeySet.this.lambda$startObserve$2((List) obj);
            }
        });
        ((ActSuperPanelKeySetVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelKeySet.this.lambda$startObserve$3((Void) obj);
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