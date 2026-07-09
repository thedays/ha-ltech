package com.ltech.smarthome.upgrade;

import android.app.Activity;
import android.text.TextUtils;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.upgrade.ActBtOtaVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/* loaded from: classes4.dex */
public class ActBtOtaVM extends BaseViewModel {
    public long placeId;
    public Listing<Place> placeInfoResult;
    public List<Floor> mFloorList = new ArrayList();
    public List<Room> mRoomList = new ArrayList();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();
    public ObservableList<ScanItem> mDeviceList = new ObservableArrayList();
    public List<Device> tempData = new ArrayList();
    public List<String> tempIdData = new ArrayList();
    public ItemBinding<ScanItem> itemBinding = getItemBinding();
    public BindingRecyclerViewAdapter.ItemIds<ScanItem> itemIds = new BindingRecyclerViewAdapter.ItemIds() { // from class: com.ltech.smarthome.upgrade.ActBtOtaVM$$ExternalSyntheticLambda2
        @Override // me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter.ItemIds
        public final long getItemId(int i, Object obj) {
            long id;
            id = ((ActBtOtaVM.ScanItem) obj).getDevice().getId();
            return id;
        }
    };
    public List<Long> selectDeviceIdList = new ArrayList();
    public MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>(Integer.valueOf(this.selectDeviceIdList.size()));
    public List<ScanItem> allData = new ArrayList();
    public boolean canClickItem = true;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getItemBinding$2(ItemBinding itemBinding, final int i, final ScanItem scanItem) {
        Activity topActivity;
        int i2;
        ItemBinding itemBinding2 = itemBinding.clearExtras().set(40, R.layout.item_bt_ota);
        if (scanItem.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_1) || scanItem.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_2) || scanItem.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_3) || scanItem.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_4) || scanItem.getDevice().getProductId().equals(ProductId.ID_BODY_SENSOR) || scanItem.getDevice().getProductId().equals(ProductId.ID_RC4) || scanItem.getDevice().getProductId().equals(ProductId.ID_RC4S) || scanItem.getDevice().getProductId().equals(ProductId.ID_SMART_PANEL_S6B) || scanItem.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
            topActivity = ActivityUtils.getTopActivity();
            i2 = R.string.app_manual;
        } else {
            topActivity = ActivityUtils.getTopActivity();
            i2 = R.string.app_upgrade;
        }
        itemBinding2.bindExtra(7, topActivity.getString(i2)).bindExtra(10, new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.upgrade.ActBtOtaVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingAction
            public final void call() {
                ActBtOtaVM.this.lambda$getItemBinding$1(i, scanItem);
            }
        }));
    }

    protected ItemBinding<ScanItem> getItemBinding() {
        return ItemBinding.of(new OnItemBind() { // from class: com.ltech.smarthome.upgrade.ActBtOtaVM$$ExternalSyntheticLambda0
            @Override // me.tatarka.bindingcollectionadapter2.OnItemBind
            public final void onItemBind(ItemBinding itemBinding, int i, Object obj) {
                ActBtOtaVM.this.lambda$getItemBinding$2(itemBinding, i, (ActBtOtaVM.ScanItem) obj);
            }
        });
    }

    public Floor checkFloor(List<Floor> floorList) {
        Floor value = this.selectFloor.getValue();
        if (value != null) {
            Iterator<Floor> it = floorList.iterator();
            while (it.hasNext()) {
                if (it.next().getFloorId() == value.getFloorId()) {
                    return value;
                }
            }
        }
        return floorList.get(0);
    }

    public boolean setCurFloor(Floor floor) {
        this.selectFloor.setValue(floor);
        return true;
    }

    public Floor getCurFloor() {
        return this.selectFloor.getValue();
    }

    public Room checkRoom(List<Room> roomList) {
        Room value = this.selectRoom.getValue();
        if (value != null) {
            Iterator<Room> it = roomList.iterator();
            while (it.hasNext()) {
                if (it.next().getRoomId() == value.getRoomId()) {
                    return value;
                }
            }
        }
        return roomList.get(0);
    }

    public boolean setCurRoom(Room room) {
        this.selectRoom.setValue(room);
        return true;
    }

    public Room getCurRoom() {
        return this.selectRoom.getValue();
    }

    public String getPlaceName(long floorId, long roomId) {
        StringBuilder sb = new StringBuilder();
        Iterator<Floor> it = this.mFloorList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Floor next = it.next();
            if (next.getFloorId() != -1 && next.getFloorId() == floorId) {
                sb.append(next.getFloorName());
                break;
            }
        }
        Iterator<Room> it2 = this.mRoomList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Room next2 = it2.next();
            if (next2.getRoomId() != -1 && next2.getRoomId() == roomId) {
                sb.append(next2.getRoomName());
                break;
            }
        }
        if (TextUtils.isEmpty(sb.toString())) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.no_set_room));
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: clickItem, reason: merged with bridge method [inline-methods] */
    public void lambda$getItemBinding$1(int position, ScanItem item) {
        if (this.canClickItem) {
            if (!item.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_1) && !item.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_2) && !item.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_3) && !item.getDevice().getProductId().equals(ProductId.ID_KEY_SWITCH_4) && !item.getDevice().getProductId().equals(ProductId.ID_BODY_SENSOR) && !item.getDevice().getProductId().equals(ProductId.ID_RC4) && !item.getDevice().getProductId().equals(ProductId.ID_RC4S) && !item.getDevice().getProductId().equals(ProductId.ID_SMART_PANEL_S6B) && !item.getDevice().getProductId().equals(ProductId.ID_SMART_SWITCH_SQB)) {
                navigation(NavUtils.destination(ActBtOtaSingle.class).withLong(Constants.CONTROL_ID, item.getDevice().getId()).withString("DEVICE_ADDRESS", item.getDevice().getWifiMac()));
            } else {
                BtOtaLowPowerDialog.as(ActivityUtils.getTopActivity()).setProductId(item.getDevice().getProductId()).setControlId(item.getDevice().getId()).setAddress(item.getDevice().getWifiMac()).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            }
        }
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<ScanItem>>() { // from class: com.ltech.smarthome.upgrade.ActBtOtaVM.2
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<ScanItem>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (ScanItem scanItem : ActBtOtaVM.this.allData) {
                        if (scanItem.getDevice().getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(scanItem);
                        }
                    }
                } else {
                    arrayList.addAll(ActBtOtaVM.this.allData);
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<ScanItem>>() { // from class: com.ltech.smarthome.upgrade.ActBtOtaVM.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<ScanItem> items) {
                ActBtOtaVM.this.mDeviceList.clear();
                ActBtOtaVM.this.mDeviceList.addAll(items);
            }
        });
    }

    public static class ScanItem {
        private Device device;
        private String progress;
        private int upgradeFlag;
        private String ver;

        public String getProgress() {
            return this.progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public ScanItem(Device device) {
            this.device = device;
        }

        public int getUpgradeFlag() {
            return this.upgradeFlag;
        }

        public void setUpgradeFlag(int upgradeFlag) {
            this.upgradeFlag = upgradeFlag;
        }

        public Device getDevice() {
            return this.device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public String getVer() {
            return this.ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }
    }

    public void updateBleVersion(final Device device, final String version) {
        if (device != null) {
            ((ObservableSubscribeProxy) Injection.net().updateDeviceBleVersion(device.getDeviceId(), version).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActBtOtaVM$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActBtOtaVM.lambda$updateBleVersion$3(Device.this, version, obj);
                }
            }, new SmartErrorComsumer());
        }
    }

    static /* synthetic */ void lambda$updateBleVersion$3(Device device, String str, Object obj) throws Exception {
        device.setBleversion(str);
        Injection.repo().device().saveDevice(device);
    }
}