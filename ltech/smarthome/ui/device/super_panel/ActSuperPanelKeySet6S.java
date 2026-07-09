package com.ltech.smarthome.ui.device.super_panel;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActSuperPanelKeySet6sBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.device_param.SuperPanelExtParam;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActSuperPanelKeySet6S extends VMActivity<ActSuperPanelKeySet6sBinding, ActSuperPanelKeySetVM> {
    private BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder> mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_super_panel_key_set_6s;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.key_set));
        setBackImage(R.mipmap.icon_back);
        ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList.clear();
        for (int i = 0; i < 2; i++) {
            ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList.add(new SuperPanelInfo.PanelKeyInfo());
        }
        BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<SuperPanelInfo.PanelKeyInfo, BaseViewHolder>(R.layout.item_smart_panel_key, ((ActSuperPanelKeySetVM) this.mViewModel).panelInfoList) { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet6S.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, SuperPanelInfo.PanelKeyInfo item) {
                helper.itemView.getLayoutParams().height = ActSuperPanelKeySet6S.this.mLinearLayoutManager.getHeight() / 2;
                ((LinearLayout) helper.getView(R.id.layout_content)).setPadding(Utils.dip2px(this.mContext, 10.0f), 0, Utils.dip2px(this.mContext, 10.0f), 0);
                String keyName = ((ActSuperPanelKeySetVM) ActSuperPanelKeySet6S.this.mViewModel).getKeyName(item);
                if (!TextUtils.isEmpty(keyName)) {
                    helper.setText(R.id.tv_device_name, keyName);
                    helper.setText(R.id.tv_sub_text, ((ActSuperPanelKeySetVM) ActSuperPanelKeySet6S.this.mViewModel).getFunString(this.mContext, item));
                } else {
                    helper.setText(R.id.tv_device_name, ActSuperPanelKeySet6S.this.getKeyNameArray()[helper.getAdapterPosition()]);
                    helper.setText(R.id.tv_sub_text, ActSuperPanelKeySet6S.this.getString(R.string.no_bind_object));
                }
                helper.setTextColor(R.id.tv_device_name, ContextCompat.getColor(ActSuperPanelKeySet6S.this, R.color.colorPrimary));
                ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
            }
        };
        this.mAdapter = baseQuickAdapter;
        baseQuickAdapter.bindToRecyclerView(((ActSuperPanelKeySet6sBinding) this.mViewBinding).rvKeyInfo);
        RecyclerView recyclerView = ((ActSuperPanelKeySet6sBinding) this.mViewBinding).rvKeyInfo;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.mLinearLayoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(linearLayoutManager);
        ((ActSuperPanelKeySet6sBinding) this.mViewBinding).rvKeyInfo.setHasFixedSize(true);
        ((DefaultItemAnimator) ((ActSuperPanelKeySet6sBinding) this.mViewBinding).rvKeyInfo.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet6S$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i2) {
                ActSuperPanelKeySet6S.this.lambda$initView$0(baseQuickAdapter2, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (((ActSuperPanelKeySetVM) this.mViewModel).getCurrentPlace().isOwner() || ((ActSuperPanelKeySetVM) this.mViewModel).getCurrentPlace().isManager()) {
            ((ActSuperPanelKeySetVM) this.mViewModel).showAddDialog(this, i);
        } else {
            ((ActSuperPanelKeySetVM) this.mViewModel).showNoPermissionDialogEvent.call();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] getKeyNameArray() {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(((ActSuperPanelKeySetVM) this.mViewModel).deviceId);
        String[] smartPanelS4KeyName = NameRepository.getSmartPanelS4KeyName(this);
        if (deviceByDeviceId.getExtParam() != null) {
            SuperPanelExtParam superPanelExtParam = (SuperPanelExtParam) deviceByDeviceId.getExtParam(SuperPanelExtParam.class);
            if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch1_name())) {
                smartPanelS4KeyName[0] = superPanelExtParam.getSwitch1_name();
            }
            if (superPanelExtParam != null && !TextUtils.isEmpty(superPanelExtParam.getSwitch2_name())) {
                smartPanelS4KeyName[1] = superPanelExtParam.getSwitch2_name();
            }
        }
        return smartPanelS4KeyName;
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
        handleData(Transformations.switchMap(((ActSuperPanelKeySetVM) this.mViewModel).groupList, new Function1() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet6S$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$1;
                lambda$startObserve$1 = ActSuperPanelKeySet6S.this.lambda$startObserve$1((List) obj);
                return lambda$startObserve$1;
            }
        }), new IAction() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet6S$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSuperPanelKeySet6S.this.lambda$startObserve$2((List) obj);
            }
        });
        ((ActSuperPanelKeySetVM) this.mViewModel).showNoPermissionDialogEvent.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.super_panel.ActSuperPanelKeySet6S$$ExternalSyntheticLambda3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSuperPanelKeySet6S.this.lambda$startObserve$3((Void) obj);
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