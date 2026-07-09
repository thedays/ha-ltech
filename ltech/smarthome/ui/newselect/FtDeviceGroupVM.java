package com.ltech.smarthome.ui.newselect;

import android.text.TextUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.EditDeviceDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class FtDeviceGroupVM extends BaseViewModel {
    public int floorPosition;
    public long orgFloorId;
    public long orgRoomId;
    public int roomPosition;
    public List<RoomItem> mRoomList = new ArrayList();
    public MediatorLiveData<Long> placeId = new MediatorLiveData<>();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();
    public List<Room> roomList = new ArrayList();
    public List<Floor> floorList = new ArrayList();
    public SingleLiveEvent<Void> refreshRoleItem = new SingleLiveEvent<>();
    public MutableLiveData<StateParam> updateStateLiveData = new MutableLiveData<>();
    public boolean isFirst = true;
    public boolean needFloorRoomMemory = true;
    public boolean showUnconfigRoom = false;
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    private List<Observable<Void>> requestList = new ArrayList();

    static /* synthetic */ void lambda$loadNewGroupList$5(Throwable th) throws Exception {
    }

    public boolean initRoomList(List<Room> roomList, List<Floor> floorList) {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        if (this.selectFloor.getValue().getFloorId() == -1) {
            for (Floor floor : floorList) {
                arrayList.add(new RoomItem(getCurPlaceId(), -1L, -1, floor.getFloorName(), floor.getFloorId()));
            }
        } else {
            for (Room room : roomList) {
                arrayList.add(new RoomItem(getCurPlaceId(), room.getRoomId(), room.getIndex(), room.getRoomName(), room.getFloorId()));
            }
        }
        this.mRoomList = arrayList;
        return false;
    }

    public long getCurPlaceId() {
        return this.placeId.getValue().longValue();
    }

    public Floor checkFloor(List<Floor> floorList) {
        if (this.needFloorRoomMemory && this.isFirst) {
            long j = this.orgFloorId;
            if (j != -2) {
                for (int i = 0; i < floorList.size(); i++) {
                    Floor floor = floorList.get(i);
                    if (floor.getFloorId() == j) {
                        this.floorPosition = i;
                        return floor;
                    }
                }
            }
        }
        return floorList.get(0);
    }

    public void setFloorPosition(Floor curFloor, List<Floor> floorList) {
        for (int i = 0; i < floorList.size(); i++) {
            if (floorList.get(i).getFloorId() == curFloor.getFloorId()) {
                this.floorPosition = i;
            }
        }
    }

    public boolean setCurFloor(Floor floor) {
        this.selectFloor.setValue(floor);
        return true;
    }

    public Floor getCurFloor() {
        return this.selectFloor.getValue();
    }

    public boolean setCurRoom(Room room) {
        this.selectRoom.setValue(room);
        return true;
    }

    public String getPlaceInfo(long floorId, long roomId) {
        StringBuilder sb = new StringBuilder();
        List<Floor> list = this.floorList;
        if (list != null) {
            Iterator<Floor> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Floor next = it.next();
                if (floorId == next.getFloorId()) {
                    sb.append(next.getFloorName());
                    break;
                }
            }
        }
        List<Room> list2 = this.roomList;
        if (list2 != null) {
            Iterator<Room> it2 = list2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Room next2 = it2.next();
                if (roomId == next2.getRoomId()) {
                    sb.append(next2.getRoomName());
                    break;
                }
            }
        }
        return sb.toString();
    }

    public Role getRoleByRoleId(long roleId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(roleId);
        return deviceByDeviceId != null ? deviceByDeviceId : Injection.repo().group().getGroupByGroupId(roleId);
    }

    public Role getRoleByRoleIdIncludeScene(long roleId) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(roleId);
        if (deviceByDeviceId != null) {
            return deviceByDeviceId;
        }
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(roleId);
        return groupByGroupId != null ? groupByGroupId : Injection.repo().scene().getSceneBySceneId(roleId);
    }

    public boolean needLocation(Role role) {
        if (role.isVirtual()) {
            return false;
        }
        if (!(role instanceof Device)) {
            return true;
        }
        Device device = (Device) role;
        if (RelaySeparationHelper.isRelaySeparationSub(device)) {
            return false;
        }
        return ProductRepository.needLocation(device.getProductId());
    }

    public void showEditDialog(final Role role) {
        final EditDeviceDialog onLocationListener = EditDeviceDialog.asDefault().setTitle(StringUtils.getString(R.string.edit_device)).setLabel(StringUtils.getString(R.string.device_name)).setRole(role).setLocation(needLocation(role)).setOnSaveListener(new EditDeviceDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.1
            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSaveListener
            public boolean onSave(String name, int imgIndex, int floorPos, int roomPos) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(R.string.input_name);
                    return false;
                }
                FtDeviceGroupVM.this.roomPickHelper.setSelectPosition(floorPos, roomPos);
                FtDeviceGroupVM.this.updateRole(role, name, imgIndex, FtDeviceGroupVM.this.roomPickHelper.getSelectFloorId(), FtDeviceGroupVM.this.roomPickHelper.getSelectRoomId());
                return true;
            }
        }).setOnLocationListener(new IAction() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                CmdAssistant.getSettingCmdAssistant(Role.this, new int[0]).testDeviceLocation(ActivityUtils.getTopActivity());
            }
        });
        onLocationListener.setSelectRoom(this.roomPickHelper.canSetRoom()).setFloorList(this.roomPickHelper.getCurrentFloorNames()).setRoomList(this.roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(this.roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(this.roomPickHelper.getSelectRoomPosition()).setOnSelectFloorListener(new EditDeviceDialog.OnSelectFloorListener() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.dialog.EditDeviceDialog.OnSelectFloorListener
            public final void selectFloor(EditDeviceDialog editDeviceDialog, int i, String str) {
                FtDeviceGroupVM.this.lambda$showEditDialog$1(onLocationListener, editDeviceDialog, i, str);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$1(EditDeviceDialog editDeviceDialog, EditDeviceDialog editDeviceDialog2, int i, String str) {
        editDeviceDialog.setRoomList(this.roomPickHelper.getRoomNames(i));
        editDeviceDialog.notifyDialog();
    }

    public void updateRole(Role role, String name, int imgIndex, long floorId, long roomId) {
        this.requestList.clear();
        this.requestList.add(changeRoleName(role, name));
        if (imgIndex != -1) {
            this.requestList.add(changeRoleIcon(role, imgIndex));
        }
        this.requestList.add(changeRolePlace(role, floorId, roomId));
        batchControl(this.requestList);
    }

    public Observable<Void> changeRoleName(final Role role, final String name) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                FtDeviceGroupVM.this.lambda$changeRoleName$2(role, name, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleName$2(Role role, final String str, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDeviceName(device.getDeviceId(), str).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.2
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    device.setDeviceName(str);
                    Injection.repo().device().saveDevice(device);
                    observableEmitter.onComplete();
                }
            });
        } else if (role instanceof Group) {
            final Group group = (Group) role;
            Injection.net().updateGroupName(group.getGroupId(), str).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.3
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    group.setGroupName(str);
                    Injection.repo().group().saveGroup(group);
                    observableEmitter.onComplete();
                }
            });
        }
    }

    public Observable<Void> changeRoleIcon(final Role role, final int imgIndex) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                FtDeviceGroupVM.this.lambda$changeRoleIcon$3(role, imgIndex, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRoleIcon$3(Role role, final int i, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDeviceImgIndex(device.getDeviceId(), i).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.4
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    device.setImageIndex(i);
                    Injection.repo().device().saveDevice(device);
                    observableEmitter.onComplete();
                }
            });
        } else {
            final Group group = (Group) role;
            Injection.net().updateGroupDeviceIcon(group.getGroupId(), i).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.5
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    group.setImgindex(i);
                    Injection.repo().group().saveGroup(group);
                    observableEmitter.onComplete();
                }
            });
        }
    }

    public Observable<Void> changeRolePlace(final Role role, final long floorId, final long roomId) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                FtDeviceGroupVM.this.lambda$changeRolePlace$4(role, roomId, floorId, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeRolePlace$4(Role role, final long j, final long j2, final ObservableEmitter observableEmitter) throws Exception {
        if (role instanceof Device) {
            final Device device = (Device) role;
            Injection.net().updateDevicePlace(device.getDeviceId(), j, ProductId.ID_BLE_HAM.equals(device.getProductId()) || ProductId.ID_MESH_GATEWAY.equals(device.getProductId())).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.6
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    if (ProductId.ID_BLE_HAM.equals(device.getProductId()) || ProductId.ID_MESH_GATEWAY.equals(device.getProductId())) {
                        ArrayList arrayList = new ArrayList();
                        for (Device device2 : Injection.repo().device().getDeviceListByPlaceId(device.getPlaceId())) {
                            if (device2.getMacdeviceid() == device.getDeviceId()) {
                                device2.setFloorId(j2);
                                device2.setRoomId(j);
                                arrayList.add(device2);
                            }
                        }
                        Injection.repo().device().saveDevice(arrayList);
                    }
                    device.setFloorId(j2);
                    device.setRoomId(j);
                    Injection.repo().device().saveDevice(device);
                    observableEmitter.onComplete();
                }
            });
        } else {
            final Group group = (Group) role;
            Injection.net().updateGroupLocation(group.getGroupId(), j2, j).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.7
                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                    observableEmitter.onComplete();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    group.setFloorId(j2);
                    group.setRoomId(j);
                    Injection.repo().group().saveGroup(group);
                    observableEmitter.onComplete();
                }
            });
        }
    }

    public void batchControl(List<Observable<Void>> request) {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Void>() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.8
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Void aVoid) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                SmartToast.showShort(R.string.save_success);
                FtDeviceGroupVM.this.dismissLoadingDialog();
                FtDeviceGroupVM.this.refreshRoleItem.call();
            }
        });
    }

    public Room getCurRoom() {
        return this.selectRoom.getValue();
    }

    public void updateState(long id, int state, float progress) {
        this.updateStateLiveData.postValue(new StateParam(id, state, progress));
    }

    public void loadNewGroupList() {
        ((ObservableSubscribeProxy) Injection.repo().role().getGroupFromNet(this.placeId.getValue().longValue()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<List<Role>>(this) { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM.9
            @Override // io.reactivex.functions.Consumer
            public void accept(List<Role> roles) throws Exception {
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.newselect.FtDeviceGroupVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtDeviceGroupVM.lambda$loadNewGroupList$5((Throwable) obj);
            }
        });
    }

    public static class RoomItem {
        private long floorId;
        private FtDeviceGroup ftDevice;
        private int index;
        private long placeId;
        private long roomId;
        private String roomName;

        public RoomItem(long placeId, long roomId, int index, String roomName, long floorId) {
            this.placeId = placeId;
            this.roomId = roomId;
            this.roomName = roomName;
            this.index = index;
            this.floorId = floorId;
            this.ftDevice = FtDeviceGroup.newInstance(placeId, roomId, floorId);
        }

        public long getRoomId() {
            return this.roomId;
        }

        public long getPlaceId() {
            return this.placeId;
        }

        public String getRoomName() {
            return this.roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public FtDeviceGroup getFtDevice() {
            return this.ftDevice;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getFloorId() {
            return this.floorId;
        }

        public void setFloorId(long floorId) {
            this.floorId = floorId;
        }
    }

    public static class StateParam {
        public static int STATE_ALL_COMPLETED = 4;
        public static int STATE_COMPLETED = 3;
        public static int STATE_PENDING = 1;
        public static int STATE_WORKING = 2;
        public long id;
        public float progress;
        public int state;

        public StateParam(long id, int state, float progress) {
            this.id = id;
            this.state = state;
            this.progress = progress;
        }
    }

    public static final class InOrOutGroupItem {
        private Device device;
        ObservableEmitter<Integer> emitter;
        private int id;
        public float progress;
        public int state = StateParam.STATE_PENDING;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Device getDevice() {
            return this.device;
        }

        public void setDevice(Device device) {
            this.device = device;
        }

        public ObservableEmitter<Integer> getEmitter() {
            return this.emitter;
        }

        public void setEmitter(ObservableEmitter<Integer> emitter) {
            this.emitter = emitter;
        }
    }
}