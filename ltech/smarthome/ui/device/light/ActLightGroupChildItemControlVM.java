package com.ltech.smarthome.ui.device.light;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.expand.LightGroupExpandableItem;
import com.ltech.smarthome.model.expand.LightGroupSubCountExpandableItem;
import com.ltech.smarthome.model.expand.LightGroupSubExpandableItem;
import com.smart.message.MessageManager;
import com.smart.message.SmartUtils;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.ISendResutCallback;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.extra.Emitter;
import com.smart.product_agreement.productBle.CmdBleFactory;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActLightGroupChildItemControlVM extends BaseViewModel {
    public long controlId;
    private List<Device> needCheckDevices;
    public MutableLiveData<List<MultiItemEntity>> refreshData = new MutableLiveData<>();
    public MutableLiveData<Group> groupSwitchChange = new MutableLiveData<>();
    public MutableLiveData<Group> groupChangeOnly = new MutableLiveData<>();

    public void loadData() {
        Group groupById;
        if (this.controlId == -1 || (groupById = Injection.repo().group().getGroupById(this.controlId)) == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        LightGroupExpandableItem lightGroupExpandableItem = new LightGroupExpandableItem(groupById);
        lightGroupExpandableItem.addSubItem(new LightGroupSubExpandableItem(groupById));
        arrayList.add(lightGroupExpandableItem);
        arrayList.add(new LightGroupSubCountExpandableItem(groupById.getDeviceIds() != null ? groupById.getDeviceIds().size() : 0));
        if (groupById.getDeviceIds() != null && !groupById.getDeviceIds().isEmpty()) {
            this.needCheckDevices = new ArrayList();
            Iterator<Long> it = groupById.getDeviceIds().iterator();
            while (it.hasNext()) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                if (deviceByDeviceId != null) {
                    this.needCheckDevices.add(deviceByDeviceId);
                    LightGroupExpandableItem lightGroupExpandableItem2 = new LightGroupExpandableItem(deviceByDeviceId);
                    lightGroupExpandableItem2.addSubItem(new LightGroupSubExpandableItem(deviceByDeviceId));
                    arrayList.add(lightGroupExpandableItem2);
                }
            }
        }
        this.refreshData.postValue(arrayList);
    }

    public void updateStateLight(Group group) {
        if (group.getDeviceIds() == null || group.getDeviceIds().size() <= 0) {
            return;
        }
        List<Device> devicesByIds = Injection.repo().device().getDevicesByIds(group.getDeviceIds());
        for (int i = 0; i < devicesByIds.size(); i++) {
            devicesByIds.get(i).setDeviceState(group.getDeviceState());
            Injection.repo().device().saveDevice(devicesByIds.get(i));
        }
    }

    public void updateStateGroup(Device device) {
        Group groupById = Injection.repo().group().getGroupById(this.controlId);
        groupById.setGroupState(device.getDeviceState());
        Injection.repo().group().saveGroup(groupById);
        this.groupChangeOnly.setValue(groupById);
    }

    public Place getCurPlace() {
        return Injection.repo().home().getSelPlace();
    }

    public void checkSingleDeviceStatus() {
        if (this.needCheckDevices != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<Device> it = this.needCheckDevices.iterator();
            while (it.hasNext()) {
                arrayList.add(check(it.next()));
            }
            ((ObservableSubscribeProxy) Observable.concat(arrayList).subscribeOn(Schedulers.io()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>(this) { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupChildItemControlVM.1
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                }

                @Override // io.reactivex.Observer
                public void onNext(Integer integer) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable d2) {
                }
            });
        }
    }

    private Observable<Integer> check(final Device device) {
        return Observable.create(new ObservableOnSubscribe<Integer>(this) { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupChildItemControlVM.2
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                final BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                if (bleParam == null) {
                    emitter.onComplete();
                    return;
                }
                BaseCtrlPackage convert = SmartUtils.getICtrlConverter().convert(device);
                BaseCmd querDeviceState = CmdBleFactory.querDeviceState(0, bleParam.getUnicastAddress());
                MessageManager.getInstance().addDeviceQueryResult(bleParam.getUnicastAddress(), new MessageManager.UpdateQuery(this) { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupChildItemControlVM.2.1
                    @Override // com.smart.message.MessageManager.UpdateQuery
                    public void update(int uniAddress) {
                        if (bleParam.getUnicastAddress() == uniAddress) {
                            emitter.onComplete();
                        }
                    }
                });
                LHomeLog.e("checkDEV", getClass(), "send=" + bleParam.getUnicastAddress());
                SmartUtils.send(Emitter.MIX_BLE_IOT, convert, querDeviceState, new ISendResutCallback(this) { // from class: com.ltech.smarthome.ui.device.light.ActLightGroupChildItemControlVM.2.2
                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultError() {
                    }

                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultSuccess(boolean isIot) {
                    }
                });
                Thread.sleep(100L);
                emitter.onNext(Integer.valueOf(bleParam.getUnicastAddress()));
                emitter.onComplete();
            }
        });
    }
}