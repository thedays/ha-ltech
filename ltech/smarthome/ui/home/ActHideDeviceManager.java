package com.ltech.smarthome.ui.home;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSelectDeviceAndGroupDivideActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.databinding.ActSelectDivideBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
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
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActHideDeviceManager extends BaseSelectDeviceAndGroupDivideActivity {
    public List<Long> addDeviceIdList = new ArrayList();
    public List<Long> notAddDeviceIdList = new ArrayList();
    public MutableLiveData<List<Long>> addDeviceIdsLiveData = new MutableLiveData<>();
    public MutableLiveData<List<Long>> notAddDeviceIdsLiveData = new MutableLiveData<>();
    List<Role> notAddDeviceList = new ArrayList();
    List<Role> addDeviceList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity
    protected int itemLayout() {
        return R.layout.item_select_device_in_group;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity
    public void convertNotSelectDataView(BaseViewHolder helper, Role item) {
        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId())).setBackgroundRes(R.id.iv_select, R.mipmap.spgroup_edit_add).addOnClickListener(R.id.iv_select);
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity
    public void convertSelectedDataView(BaseViewHolder helper, Role item) {
        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId())).setBackgroundRes(R.id.iv_select, R.mipmap.spgroup_edit_delete).addOnClickListener(R.id.iv_select);
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceAndGroupDivideActivity, com.ltech.smarthome.base.BaseSelectDivideListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_hide_devices));
        ((ActSelectDivideBinding) this.mViewBinding).tvTip.setText(R.string.app_str_not_hidden_devices);
        ((ActSelectDivideBinding) this.mViewBinding).tvTip.setVisibility(0);
        this.mNotAddAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_select) {
                    if (Injection.state().isConnectOuterNet()) {
                        ActHideDeviceManager.this.hideOrShow((Role) ActHideDeviceManager.this.mNotAddAdapter.getData().get(position), false);
                    } else {
                        SmartToast.showShort(R.string.no_network);
                    }
                }
            }
        });
        this.mAddedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_select) {
                    if (Injection.state().isConnectOuterNet()) {
                        ActHideDeviceManager.this.hideOrShow((Role) ActHideDeviceManager.this.mAddedAdapter.getData().get(position), true);
                    } else {
                        SmartToast.showShort(R.string.no_network);
                    }
                }
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceAndGroupDivideActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.deviceResult, new IAction<List<Role>>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(List<Role> roles) {
                ActHideDeviceManager.this.addDeviceList.clear();
                ActHideDeviceManager.this.notAddDeviceList.clear();
                ActHideDeviceManager.this.addDeviceIdList.clear();
                ActHideDeviceManager.this.notAddDeviceIdList.clear();
                if (roles != null && !roles.isEmpty()) {
                    for (Role role : roles) {
                        if (role.getHideDevice() != 0) {
                            ActHideDeviceManager.this.addDeviceIdList.add(Long.valueOf(role.getObjectId()));
                            ActHideDeviceManager.this.addDeviceList.add(role);
                        } else {
                            ActHideDeviceManager.this.notAddDeviceIdList.add(Long.valueOf(role.getObjectId()));
                            ActHideDeviceManager.this.notAddDeviceList.add(role);
                        }
                    }
                }
                ActHideDeviceManager actHideDeviceManager = ActHideDeviceManager.this;
                actHideDeviceManager.setNotSelectDataList(actHideDeviceManager.notAddDeviceList);
                ActHideDeviceManager actHideDeviceManager2 = ActHideDeviceManager.this;
                actHideDeviceManager2.setSelectDataList(actHideDeviceManager2.addDeviceList);
            }
        });
        this.addDeviceIdsLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHideDeviceManager.this.lambda$startObserve$0((List) obj);
            }
        });
        this.notAddDeviceIdsLiveData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActHideDeviceManager.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(List list) {
        if (this.mAddedAdapter != null) {
            this.mAddedAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        if (this.mNotAddAdapter != null) {
            this.mNotAddAdapter.notifyDataSetChanged();
        }
    }

    public void hideOrShow(final Role role, final boolean z) {
        if (role instanceof Device) {
            final Device device = (Device) role;
            try {
                JSONObject jSONObject = device.getExtParam() != null ? new JSONObject(device.getExtParam()) : new JSONObject();
                jSONObject.put("hideDevice", !z ? 1 : 0);
                device.setExtParam(jSONObject.toString());
                ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), jSONObject.toString()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActHideDeviceManager.this.lambda$hideOrShow$2((Disposable) obj);
                    }
                }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Action
                    public final void run() {
                        ActHideDeviceManager.this.dismissLoadingDialog();
                    }
                }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager.4
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Object o) throws Exception {
                        if (z) {
                            if (ActHideDeviceManager.this.addDeviceIdList.contains(Long.valueOf(role.getObjectId()))) {
                                ActHideDeviceManager.this.addDeviceIdList.remove(Long.valueOf(role.getObjectId()));
                                ActHideDeviceManager.this.addDeviceList.remove(role);
                            }
                            if (!ActHideDeviceManager.this.notAddDeviceIdList.contains(Long.valueOf(role.getObjectId()))) {
                                ActHideDeviceManager.this.notAddDeviceIdList.add(Long.valueOf(role.getObjectId()));
                                ActHideDeviceManager.this.notAddDeviceList.add(role);
                            }
                        } else {
                            if (ActHideDeviceManager.this.notAddDeviceIdList.contains(Long.valueOf(role.getObjectId()))) {
                                ActHideDeviceManager.this.notAddDeviceIdList.remove(Long.valueOf(role.getObjectId()));
                                ActHideDeviceManager.this.notAddDeviceList.remove(role);
                            }
                            if (!ActHideDeviceManager.this.addDeviceIdList.contains(Long.valueOf(role.getObjectId()))) {
                                ActHideDeviceManager.this.addDeviceIdList.add(Long.valueOf(role.getObjectId()));
                                ActHideDeviceManager.this.addDeviceList.add(role);
                            }
                        }
                        Injection.repo().device().saveDevice(device);
                        ActHideDeviceManager.this.refreshData();
                        ActHideDeviceManager actHideDeviceManager = ActHideDeviceManager.this;
                        actHideDeviceManager.showSuccessDialog(actHideDeviceManager.getString(R.string.save_success));
                    }
                }, new SmartErrorComsumer());
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        if (role instanceof Group) {
            final Group group = (Group) role;
            try {
                JSONObject jSONObject2 = group.getExtParam() != null ? new JSONObject(group.getExtParam()) : new JSONObject();
                jSONObject2.put("hideDevice", !z ? 1 : 0);
                group.setExtParam(jSONObject2.toString());
                ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), jSONObject2.toString()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager$$ExternalSyntheticLambda4
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        ActHideDeviceManager.this.lambda$hideOrShow$3((Disposable) obj);
                    }
                }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager$$ExternalSyntheticLambda3
                    @Override // io.reactivex.functions.Action
                    public final void run() {
                        ActHideDeviceManager.this.dismissLoadingDialog();
                    }
                }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycle(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<Object>() { // from class: com.ltech.smarthome.ui.home.ActHideDeviceManager.5
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Object o) throws Exception {
                        if (z) {
                            if (ActHideDeviceManager.this.addDeviceIdList.contains(Long.valueOf(role.getObjectId()))) {
                                ActHideDeviceManager.this.addDeviceIdList.remove(Long.valueOf(role.getObjectId()));
                                ActHideDeviceManager.this.addDeviceList.remove(role);
                            }
                            if (!ActHideDeviceManager.this.notAddDeviceIdList.contains(Long.valueOf(role.getObjectId()))) {
                                ActHideDeviceManager.this.notAddDeviceIdList.add(Long.valueOf(role.getObjectId()));
                                ActHideDeviceManager.this.notAddDeviceList.add(role);
                            }
                        } else {
                            if (ActHideDeviceManager.this.notAddDeviceIdList.contains(Long.valueOf(role.getObjectId()))) {
                                ActHideDeviceManager.this.notAddDeviceIdList.remove(Long.valueOf(role.getObjectId()));
                                ActHideDeviceManager.this.notAddDeviceList.remove(role);
                            }
                            if (!ActHideDeviceManager.this.addDeviceIdList.contains(Long.valueOf(role.getObjectId()))) {
                                ActHideDeviceManager.this.addDeviceIdList.add(Long.valueOf(role.getObjectId()));
                                ActHideDeviceManager.this.addDeviceList.add(role);
                            }
                        }
                        Injection.repo().group().saveGroup(group);
                        ActHideDeviceManager.this.refreshData();
                        ActHideDeviceManager actHideDeviceManager = ActHideDeviceManager.this;
                        actHideDeviceManager.showSuccessDialog(actHideDeviceManager.getString(R.string.save_success));
                    }
                }, new SmartErrorComsumer());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideOrShow$2(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideOrShow$3(Disposable disposable) throws Exception {
        showLoadingDialog(getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshData() {
        this.addDeviceIdsLiveData.setValue(this.addDeviceIdList);
        this.notAddDeviceIdsLiveData.setValue(this.notAddDeviceIdList);
    }
}