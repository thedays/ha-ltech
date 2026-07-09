package com.ltech.smarthome.ui.group;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSelectDeviceActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.group.AddGroupResponse;
import com.ltech.smarthome.net.response.group.UpdateGroupResponse;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class ActAddWiFiGroup extends BaseSelectDeviceActivity {
    public List<Long> addDeviceIdList = new ArrayList();
    public List<Device> deviceList;
    public Group editGroup;
    public boolean isEdit;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_select_device_with_place;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Device item) {
        helper.setText(R.id.tv_device_name, item.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId())).setBackgroundRes(R.id.iv_select, this.addDeviceIdList.contains(Long.valueOf(item.getDeviceId())) ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default);
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.group.ActAddWiFiGroup$$ExternalSyntheticLambda8
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActAddWiFiGroup.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        long deviceId = ((Device) this.mAdapter.getData().get(i)).getDeviceId();
        if (this.addDeviceIdList.contains(Long.valueOf(deviceId))) {
            this.addDeviceIdList.remove(Long.valueOf(deviceId));
        } else {
            this.addDeviceIdList.add(Long.valueOf(deviceId));
        }
        this.mAdapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showContent() {
        super.showContent();
        setEditString(getString(R.string.save));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseNormalActivity
    public void showEmpty() {
        super.showEmpty();
        setEditString("");
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void edit() {
        super.edit();
        if (this.isEdit) {
            editGroup();
        } else {
            addGroup();
        }
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        boolean z = getIntent().getLongExtra(Constants.GROUP_ID, 0L) > 0;
        this.isEdit = z;
        if (!z) {
            this.placeId = ConfigHelper.instance().placeId;
        }
        super.startObserve();
        if (this.isEdit) {
            handleData(Transformations.switchMap(Injection.repo().group().getGroupFromDb(getIntent().getLongExtra(Constants.GROUP_ID, 0L)), new Function1() { // from class: com.ltech.smarthome.ui.group.ActAddWiFiGroup$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LiveData lambda$startObserve$1;
                    lambda$startObserve$1 = ActAddWiFiGroup.this.lambda$startObserve$1((Group) obj);
                    return lambda$startObserve$1;
                }
            }), new IAction() { // from class: com.ltech.smarthome.ui.group.ActAddWiFiGroup$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActAddWiFiGroup.this.lambda$startObserve$2((List) obj);
                }
            });
        } else {
            handleData(this.deviceResult, new IAction() { // from class: com.ltech.smarthome.ui.group.ActAddWiFiGroup$$ExternalSyntheticLambda7
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActAddWiFiGroup.this.lambda$startObserve$3((List) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$1(Group group) {
        this.editGroup = group;
        return this.deviceResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(List list) {
        this.addDeviceIdList.clear();
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            String groupKey = this.editGroup.getGroupKey();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Device device = (Device) it.next();
                if (groupKey.equals(ProductRepository.getGroupKey(device))) {
                    if (this.editGroup.getDeviceIds().contains(Long.valueOf(device.getDeviceId()))) {
                        this.addDeviceIdList.add(Long.valueOf(device.getDeviceId()));
                    }
                    arrayList.add(device);
                }
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        } else {
            setDataList(arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$3(List list) {
        this.addDeviceIdList.clear();
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            String createGroupKey = ProductId.CC.createGroupKey(ConfigHelper.instance().productInfo.getProductId(), ConfigHelper.instance().productInfo.getHardwareId());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Device device = (Device) it.next();
                if (createGroupKey.equals(ProductRepository.getGroupKey(device))) {
                    arrayList.add(device);
                }
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        } else {
            setDataList(arrayList);
        }
    }

    public void editGroup() {
        ((ObservableSubscribeProxy) Injection.net().updateGroupDevices(this.editGroup.getGroupId(), this.addDeviceIdList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActAddWiFiGroup$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddWiFiGroup.this.lambda$editGroup$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddWiFiGroup$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActAddWiFiGroup$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddWiFiGroup.this.lambda$editGroup$5((UpdateGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editGroup$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editGroup$5(UpdateGroupResponse updateGroupResponse) throws Exception {
        this.editGroup.setDeviceIds(this.addDeviceIdList);
        Injection.repo().group().saveGroup(this.editGroup);
        finishActivity();
        SmartToast.showShort(R.string.save_success);
    }

    public void addGroup() {
        ((ObservableSubscribeProxy) Injection.net().addGroup(this.placeId, this.floorId, this.roomId, ConfigHelper.instance().deviceName, ConfigHelper.instance().productInfo.getProductId(), ConfigHelper.instance().productInfo.getHardwareId(), this.addDeviceIdList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActAddWiFiGroup$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddWiFiGroup.this.lambda$addGroup$6((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActAddWiFiGroup$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.group.ActAddWiFiGroup$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActAddWiFiGroup.this.lambda$addGroup$7((AddGroupResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addGroup$6(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addGroup$7(AddGroupResponse addGroupResponse) throws Exception {
        Group group = new Group();
        group.setGroupId(addGroupResponse.getGroupid());
        group.setPlaceId(addGroupResponse.getPlaceid());
        group.setGroupName(addGroupResponse.getGroupname());
        group.setModuleType(addGroupResponse.getType());
        group.setControlType(addGroupResponse.getColortype());
        group.setGroupIndex(Integer.parseInt(addGroupResponse.getIndex(), 16));
        group.setCreatetime(addGroupResponse.getCreatetime());
        this.addDeviceIdList.clear();
        Iterator<AddGroupResponse.DevicesBean> it = addGroupResponse.getDevices().iterator();
        while (it.hasNext()) {
            this.addDeviceIdList.add(Long.valueOf(it.next().getDeviceid()));
        }
        group.setDeviceIds(this.addDeviceIdList);
        group.setGroupState(new DeviceState());
        Injection.repo().group().saveGroup(group);
        SmartToast.showShort(R.string.create_success);
        finishActivity();
    }
}