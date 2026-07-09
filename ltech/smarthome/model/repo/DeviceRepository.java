package com.ltech.smarthome.model.repo;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.WrapperResource;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.DeviceRepository;
import com.ltech.smarthome.model.repo.ifun.IDevice;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.response.place.QueryPlaceInfoResponse;
import com.ltech.smarthome.net.response.super_panel.DetailSuperPanelResponse;
import com.ltech.smarthome.net.response.super_panel.QuerySuperPanelKeywordInfoResponse;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.ltech.smarthome.utils.RxUtils;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class DeviceRepository extends BaseRepository implements IDevice {
    private MediatorLiveData<List<Device>> deviceListCache;

    static void checkBleDevice(List<Device> deviceList) {
    }

    static /* synthetic */ void lambda$getDeviceListCache$8(List list) {
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void resetDeviceOnlineState(long placeId) {
    }

    public DeviceRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
        getDeviceListCache();
    }

    /* renamed from: com.ltech.smarthome.model.repo.DeviceRepository$1, reason: invalid class name */
    class AnonymousClass1 extends WrapperResource<Device, QueryPlaceInfoResponse> {
        final /* synthetic */ long val$floorId;
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$placeId;
        final /* synthetic */ long val$roomId;

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return true;
        }

        AnonymousClass1(final long val$placeId, final long val$floorId, final long val$roomId, final LifecycleOwner val$owner) {
            this.val$placeId = val$placeId;
            this.val$floorId = val$floorId;
            this.val$roomId = val$roomId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            DeviceRepository.this.mLimiter.reset(DeviceRepository.this.mKeyCreator.deviceListKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Device> getDbQueryBuilder() {
            return DeviceRepository.this.mBoxBuilderFactory.queryDeviceList(DeviceRepository.this.mUser.getUserId(), this.val$placeId, this.val$floorId, this.val$roomId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<QueryPlaceInfoResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().queryPlaceDetailInfo(this.val$placeId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final QueryPlaceInfoResponse response) {
            BoxStore boxStore = DeviceRepository.this.mBoxStore;
            final long j = this.val$placeId;
            boxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Injection.repo().role().setDeviceData(j, response.getDevices());
                }
            });
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Listing<Device> getDeviceList(LifecycleOwner owner, long placeId, long floorId, long roomId) {
        return new AnonymousClass1(placeId, floorId, roomId, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void saveDevice(final Device... devices) {
        if (this.mUser == null || this.mUser.getUserId() <= 0 || devices == null) {
            return;
        }
        MediatorLiveData<List<Device>> mediatorLiveData = this.deviceListCache;
        if (mediatorLiveData != null && mediatorLiveData.getValue() != null) {
            for (int i = 0; i < this.deviceListCache.getValue().size(); i++) {
                Device device = this.deviceListCache.getValue().get(i);
                for (Device device2 : devices) {
                    if (device2.equals(device)) {
                        this.deviceListCache.getValue().set(i, device2);
                        device = device2;
                    }
                }
            }
        }
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$saveDevice$0(devices);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDevice$0(Device[] deviceArr) {
        Device findFirst;
        int length = deviceArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Device device = deviceArr[i];
            if (device != null && this.mUser != null) {
                device.setUserId(this.mUser.getUserId());
                if (device.getId() <= 0 && (findFirst = this.mBoxBuilderFactory.queryDeviceByDeviceId(this.mUser.getUserId(), device.getDeviceId()).build().findFirst()) != null) {
                    device.setId(findFirst.getId());
                    break;
                }
            }
            i++;
        }
        this.mBoxStore.boxFor(Device.class).put((Object[]) deviceArr);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void saveDevice(final List<Device> devices) {
        if (this.mUser == null || this.mUser.getUserId() <= 0 || devices == null) {
            return;
        }
        MediatorLiveData<List<Device>> mediatorLiveData = this.deviceListCache;
        if (mediatorLiveData != null && mediatorLiveData.getValue() != null) {
            Iterator<Device> it = this.deviceListCache.getValue().iterator();
            while (it.hasNext()) {
                Device next = it.next();
                for (Device device : devices) {
                    if (device.equals(next)) {
                        next = device;
                    }
                }
            }
        }
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$saveDevice$1(devices);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDevice$1(List list) {
        Device findFirst;
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Device device = (Device) it.next();
            device.setUserId(this.mUser.getUserId());
            if (device.getId() <= 0 && (findFirst = this.mBoxBuilderFactory.queryDeviceByDeviceId(this.mUser.getUserId(), device.getDeviceId()).build().findFirst()) != null) {
                device.setId(findFirst.getId());
                break;
            }
        }
        this.mBoxStore.boxFor(Device.class).put((Collection) list);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void changeDeviceIcon(final long deviceId, final int imageIndex) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$changeDeviceIcon$2(deviceId, imageIndex);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeDeviceIcon$2(long j, int i) {
        List<Device> find = this.mBoxBuilderFactory.queryDeviceByDeviceId(this.mUser.getUserId(), j).build().find();
        Iterator<Device> it = find.iterator();
        while (it.hasNext()) {
            it.next().setImageIndex(i);
        }
        this.mBoxStore.boxFor(Device.class).put((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void removeDeviceFromDb(final long id) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$removeDeviceFromDb$3(id);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeviceFromDb$3(long j) {
        List<Device> find = this.mBoxBuilderFactory.queryDeviceById(this.mUser.getUserId(), j).build().find();
        if (find.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Device.class).remove((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void removeDeviceByMac(final long placeId, final String mac) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$removeDeviceByMac$4(placeId, mac);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeviceByMac$4(long j, String str) {
        List<Device> find = this.mBoxBuilderFactory.queryDeviceByMac(this.mUser.getUserId(), j, str).build().find();
        if (find.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Device.class).remove((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public LiveData<Device> getDeviceFromDb(final long id) {
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(getDeviceListCache(), new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DeviceRepository.lambda$getDeviceFromDb$5(id, mediatorLiveData, (List) obj);
            }
        });
        return mediatorLiveData;
    }

    static /* synthetic */ void lambda$getDeviceFromDb$5(long j, MediatorLiveData mediatorLiveData, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Device device = (Device) it.next();
            if (device.getId() == j) {
                mediatorLiveData.setValue(device);
                return;
            }
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public LiveData<List<Device>> getDeviceListCache() {
        if (this.deviceListCache == null) {
            MediatorLiveData<List<Device>> mediatorLiveData = new MediatorLiveData<>();
            this.deviceListCache = mediatorLiveData;
            mediatorLiveData.addSource(Transformations.switchMap(Injection.repo().home().getSelectPlace(), new Function1() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LiveData lambda$getDeviceListCache$6;
                    lambda$getDeviceListCache$6 = DeviceRepository.this.lambda$getDeviceListCache$6((Place) obj);
                    return lambda$getDeviceListCache$6;
                }
            }), new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda12
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    DeviceRepository.this.lambda$getDeviceListCache$7((List) obj);
                }
            });
        }
        if (!this.deviceListCache.hasActiveObservers()) {
            this.deviceListCache.observeForever(new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda13
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    DeviceRepository.lambda$getDeviceListCache$8((List) obj);
                }
            });
        }
        return this.deviceListCache;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$getDeviceListCache$6(Place place) {
        if (place == null) {
            return new MutableLiveData(new ArrayList());
        }
        return new ObjectBoxLiveData(this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), place.getPlaceId(), -1L, -1L).build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeviceListCache$7(List list) {
        this.deviceListCache.setValue(list);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getDeviceFromDb(String iotProductKey, String iotDeviceName) {
        List<Device> value = getDeviceListCache().getValue();
        if (value == null) {
            return null;
        }
        for (Device device : value) {
            if (iotDeviceName.equals(device.getIotDeviceName()) && iotProductKey.equals(device.getIotProductKey())) {
                return device;
            }
        }
        return null;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getExistGateway() {
        List<Device> value = getDeviceListCache().getValue();
        if (value == null) {
            return null;
        }
        for (Device device : value) {
            if (ProductRepository.isSuperPanel(device.getProductId())) {
                return device;
            }
        }
        return null;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> getOnlineGateways() {
        ArrayList arrayList = new ArrayList();
        List<Device> value = getDeviceListCache().getValue();
        if (value != null) {
            for (Device device : value) {
                if (ProductRepository.isSuperPanel(device.getProductId())) {
                    LHomeLog.i(getClass(), "gateway device-->" + device);
                    if (device.isOnline()) {
                        arrayList.add(device);
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getOnlineGateway() {
        List<Device> value = getDeviceListCache().getValue();
        if (value == null) {
            return null;
        }
        for (Device device : value) {
            if (ProductRepository.isSuperPanel(device.getProductId())) {
                LHomeLog.i(getClass(), "gateway device-->" + device);
                if (device.isOnline()) {
                    return device;
                }
            }
        }
        return null;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getOnlineGateway(int address) {
        BleParam bleParam;
        List<Device> value = getDeviceListCache().getValue();
        if (value == null) {
            return null;
        }
        for (Device device : value) {
            if (ProductRepository.isSuperPanel(device.getProductId()) && (bleParam = (BleParam) device.getParam(BleParam.class)) != null && bleParam.getUnicastAddress() == address && device.isOnline()) {
                return device;
            }
        }
        for (Device device2 : value) {
            if (ProductRepository.isSuperPanel(device2.getProductId()) && device2.isOnline()) {
                return device2;
            }
        }
        return null;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getDeviceById(long id) {
        List<Device> value = getDeviceListCache().getValue();
        if (value == null) {
            return null;
        }
        for (Device device : value) {
            if (device.getId() == id) {
                return device;
            }
        }
        return null;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getDevice(long placeId, long deviceId) {
        List<Device> find = this.mBoxBuilderFactory.queryDeviceByPlace(this.mUser.getUserId(), placeId, deviceId).build().find();
        if (find.isEmpty()) {
            return null;
        }
        return find.get(0);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getDeviceByDeviceId(long deviceId) {
        List<Device> value = getDeviceListCache().getValue();
        if (value == null) {
            return null;
        }
        for (Device device : value) {
            if (device.getDeviceId() == deviceId) {
                return device;
            }
        }
        return null;
    }

    /* renamed from: com.ltech.smarthome.model.repo.DeviceRepository$2, reason: invalid class name */
    class AnonymousClass2 extends WrapperResource<SuperPanelInfo, DetailSuperPanelResponse> {
        final /* synthetic */ long val$deviceId;
        final /* synthetic */ LifecycleOwner val$owner;

        AnonymousClass2(final long val$deviceId, final LifecycleOwner val$owner) {
            this.val$deviceId = val$deviceId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return DeviceRepository.this.mLimiter.shouldFetch(DeviceRepository.this.mKeyCreator.superPanelInfoKey(this.val$deviceId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            DeviceRepository.this.mLimiter.reset(DeviceRepository.this.mKeyCreator.superPanelInfoKey(this.val$deviceId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<SuperPanelInfo> getDbQueryBuilder() {
            return DeviceRepository.this.mBoxBuilderFactory.querySuperPanelInfo(DeviceRepository.this.mUser.getUserId(), this.val$deviceId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<DetailSuperPanelResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().detailSuperPanel(this.val$deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final DetailSuperPanelResponse response) {
            DeviceRepository.this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceRepository.AnonymousClass2.this.lambda$saveDataFromNet$0(response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(DetailSuperPanelResponse detailSuperPanelResponse) {
            Box boxFor = DeviceRepository.this.mBoxStore.boxFor(SuperPanelInfo.class);
            SuperPanelInfo findFirst = getDbQueryBuilder().build().findFirst();
            if (findFirst == null) {
                findFirst = new SuperPanelInfo();
            }
            findFirst.setUserId(DeviceRepository.this.mUser.getUserId());
            findFirst.setDeviceId(detailSuperPanelResponse.getInfo().getDeviceid());
            if (detailSuperPanelResponse.getInfo() != null) {
                findFirst.setLastfirmwareversion(detailSuperPanelResponse.getInfo().getLastfirmwareversion());
                findFirst.setLastmcuversion(detailSuperPanelResponse.getInfo().getLastmcuversion());
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            if (detailSuperPanelResponse.getDevices().getTotal() > 0) {
                for (DetailSuperPanelResponse.DevicesBean.RowsBean rowsBean : detailSuperPanelResponse.getDevices().getRows()) {
                    arrayList3.add(Long.valueOf(rowsBean.getDeviceid()));
                    SuperPanelInfo.SortInfo sortInfo = new SuperPanelInfo.SortInfo();
                    sortInfo.setSortId(rowsBean.getDeviceid());
                    sortInfo.setSort(rowsBean.getSorting());
                    arrayList.add(sortInfo);
                }
                findFirst.setDevices(detailSuperPanelResponse.getDevices().getRows());
            }
            findFirst.setDeviceIds(arrayList3);
            ArrayList arrayList4 = new ArrayList();
            if (detailSuperPanelResponse.getScenes().getTotal() > 0) {
                for (DetailSuperPanelResponse.ScenesBean.RowsBean rowsBean2 : detailSuperPanelResponse.getScenes().getRows()) {
                    arrayList4.add(Long.valueOf(rowsBean2.getSceneid()));
                    SuperPanelInfo.SortInfo sortInfo2 = new SuperPanelInfo.SortInfo();
                    sortInfo2.setSortId(rowsBean2.getSceneid());
                    sortInfo2.setSort(rowsBean2.getSorting());
                    arrayList2.add(sortInfo2);
                }
                findFirst.setScenes(detailSuperPanelResponse.getScenes().getRows());
            }
            findFirst.setSceneIds(arrayList4);
            findFirst.setSortSceneList(arrayList2);
            ArrayList arrayList5 = new ArrayList();
            if (detailSuperPanelResponse.getGroups().getTotal() > 0) {
                for (DetailSuperPanelResponse.GroupsBean.RowsBean rowsBean3 : detailSuperPanelResponse.getGroups().getRows()) {
                    arrayList5.add(Long.valueOf(rowsBean3.getGroupid()));
                    SuperPanelInfo.SortInfo sortInfo3 = new SuperPanelInfo.SortInfo();
                    sortInfo3.setSortId(rowsBean3.getGroupid());
                    sortInfo3.setSort(rowsBean3.getSorting());
                    arrayList.add(sortInfo3);
                }
                findFirst.setGroups(detailSuperPanelResponse.getGroups().getRows());
            }
            findFirst.setGroupIds(arrayList5);
            findFirst.setSortRoleList(arrayList);
            if (detailSuperPanelResponse.getContents().getTotal() > 0) {
                ArrayList arrayList6 = new ArrayList();
                for (DetailSuperPanelResponse.ContentsBean.RowsBean rowsBean4 : detailSuperPanelResponse.getContents().getRows()) {
                    SuperPanelInfo.PanelKeyInfo panelKeyInfo = new SuperPanelInfo.PanelKeyInfo();
                    panelKeyInfo.setPanelinfoid(rowsBean4.getPanelinfoid());
                    panelKeyInfo.setKeywords(rowsBean4.getKeywords());
                    panelKeyInfo.setKeywordsname(rowsBean4.getKeywordsname());
                    panelKeyInfo.setActionType(rowsBean4.getActiontype());
                    panelKeyInfo.setExecutecommand(rowsBean4.getExecutecommand());
                    arrayList6.add(panelKeyInfo);
                }
                findFirst.setPanelKeyInfo(arrayList6);
                DeviceRepository.this.mLimiter.refresh(DeviceRepository.this.mKeyCreator.superPanelKeywordInfoKey(detailSuperPanelResponse.getInfo().getDeviceid()));
            }
            boxFor.put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Listing<SuperPanelInfo> getSuperPanelInfo(LifecycleOwner owner, long deviceId) {
        return new AnonymousClass2(deviceId, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public SuperPanelInfo getSuperPanelInfoByDb(long deviceId) {
        return this.mBoxBuilderFactory.querySuperPanelInfo(this.mUser.getUserId(), deviceId).build().findFirst();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Observable<SuperPanelInfo> getGqProInfo(final long deviceId) {
        return Injection.net().detailSuperPanel(deviceId).flatMap(new Function<DetailSuperPanelResponse, ObservableSource<SuperPanelInfo>>() { // from class: com.ltech.smarthome.model.repo.DeviceRepository.3
            @Override // io.reactivex.functions.Function
            public ObservableSource<SuperPanelInfo> apply(DetailSuperPanelResponse response) throws Exception {
                Box boxFor = DeviceRepository.this.mBoxStore.boxFor(SuperPanelInfo.class);
                final SuperPanelInfo findFirst = DeviceRepository.this.mBoxBuilderFactory.querySuperPanelInfo(DeviceRepository.this.mUser.getUserId(), deviceId).build().findFirst();
                if (findFirst == null) {
                    findFirst = new SuperPanelInfo();
                }
                findFirst.setUserId(DeviceRepository.this.mUser.getUserId());
                findFirst.setDeviceId(response.getInfo().getDeviceid());
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                if (response.getDevices().getTotal() > 0) {
                    for (DetailSuperPanelResponse.DevicesBean.RowsBean rowsBean : response.getDevices().getRows()) {
                        arrayList2.add(Long.valueOf(rowsBean.getDeviceid()));
                        SuperPanelInfo.SortInfo sortInfo = new SuperPanelInfo.SortInfo();
                        sortInfo.setSortId(rowsBean.getDeviceid());
                        sortInfo.setSort(rowsBean.getSorting());
                        sortInfo.setObjectType(1);
                        arrayList.add(sortInfo);
                    }
                    findFirst.setDevices(response.getDevices().getRows());
                }
                findFirst.setDeviceIds(arrayList2);
                ArrayList arrayList3 = new ArrayList();
                if (response.getScenes().getTotal() > 0) {
                    for (DetailSuperPanelResponse.ScenesBean.RowsBean rowsBean2 : response.getScenes().getRows()) {
                        arrayList3.add(Long.valueOf(rowsBean2.getSceneid()));
                        SuperPanelInfo.SortInfo sortInfo2 = new SuperPanelInfo.SortInfo();
                        sortInfo2.setSortId(rowsBean2.getSceneid());
                        sortInfo2.setSort(rowsBean2.getSorting());
                        sortInfo2.setObjectType(3);
                        arrayList.add(sortInfo2);
                    }
                    findFirst.setScenes(response.getScenes().getRows());
                }
                findFirst.setSceneIds(arrayList3);
                ArrayList arrayList4 = new ArrayList();
                if (response.getGroups().getTotal() > 0) {
                    for (DetailSuperPanelResponse.GroupsBean.RowsBean rowsBean3 : response.getGroups().getRows()) {
                        rowsBean3.setGroupAddress(Integer.parseInt(rowsBean3.getGroupindex() == null ? "0" : rowsBean3.getGroupindex(), 16));
                        arrayList4.add(Long.valueOf(rowsBean3.getGroupid()));
                        SuperPanelInfo.SortInfo sortInfo3 = new SuperPanelInfo.SortInfo();
                        sortInfo3.setSortId(rowsBean3.getGroupid());
                        sortInfo3.setSort(rowsBean3.getSorting());
                        sortInfo3.setObjectType(2);
                        arrayList.add(sortInfo3);
                    }
                    findFirst.setGroups(response.getGroups().getRows());
                }
                findFirst.setGroupIds(arrayList4);
                findFirst.setSortRoleList(arrayList);
                if (response.getContents().getTotal() > 0) {
                    ArrayList arrayList5 = new ArrayList();
                    for (DetailSuperPanelResponse.ContentsBean.RowsBean rowsBean4 : response.getContents().getRows()) {
                        SuperPanelInfo.PanelKeyInfo panelKeyInfo = new SuperPanelInfo.PanelKeyInfo();
                        panelKeyInfo.setPanelinfoid(rowsBean4.getPanelinfoid());
                        panelKeyInfo.setKeywords(rowsBean4.getKeywords());
                        panelKeyInfo.setKeywordsname(rowsBean4.getKeywordsname());
                        panelKeyInfo.setActionType(rowsBean4.getActiontype());
                        panelKeyInfo.setExecutecommand(rowsBean4.getExecutecommand());
                        arrayList5.add(panelKeyInfo);
                    }
                    findFirst.setDevices(response.getDevices().getRows());
                    findFirst.setPanelKeyInfo(arrayList5);
                    DeviceRepository.this.mLimiter.refresh(DeviceRepository.this.mKeyCreator.superPanelKeywordInfoKey(response.getInfo().getDeviceid()));
                }
                boxFor.put((Box) findFirst);
                return Observable.create(new ObservableOnSubscribe<SuperPanelInfo>(this) { // from class: com.ltech.smarthome.model.repo.DeviceRepository.3.1
                    @Override // io.reactivex.ObservableOnSubscribe
                    public void subscribe(ObservableEmitter<SuperPanelInfo> emitter) throws Exception {
                        emitter.onNext(findFirst);
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    /* renamed from: com.ltech.smarthome.model.repo.DeviceRepository$4, reason: invalid class name */
    class AnonymousClass4 extends WrapperResource<SuperPanelInfo, QuerySuperPanelKeywordInfoResponse> {
        final /* synthetic */ long val$deviceId;
        final /* synthetic */ LifecycleOwner val$owner;

        AnonymousClass4(final long val$deviceId, final LifecycleOwner val$owner) {
            this.val$deviceId = val$deviceId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return DeviceRepository.this.mLimiter.shouldFetch(DeviceRepository.this.mKeyCreator.superPanelKeywordInfoKey(this.val$deviceId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            DeviceRepository.this.mLimiter.reset(DeviceRepository.this.mKeyCreator.superPanelKeywordInfoKey(this.val$deviceId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<SuperPanelInfo> getDbQueryBuilder() {
            return DeviceRepository.this.mBoxBuilderFactory.querySuperPanelInfo(DeviceRepository.this.mUser.getUserId(), this.val$deviceId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<QuerySuperPanelKeywordInfoResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().querySuperPanelKeywordsInfo(this.val$deviceId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final QuerySuperPanelKeywordInfoResponse response) {
            BoxStore boxStore = DeviceRepository.this.mBoxStore;
            final long j = this.val$deviceId;
            boxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DeviceRepository.AnonymousClass4.this.lambda$saveDataFromNet$0(j, response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(long j, QuerySuperPanelKeywordInfoResponse querySuperPanelKeywordInfoResponse) {
            Box boxFor = DeviceRepository.this.mBoxStore.boxFor(SuperPanelInfo.class);
            SuperPanelInfo findFirst = getDbQueryBuilder().build().findFirst();
            if (findFirst == null) {
                findFirst = new SuperPanelInfo();
            }
            findFirst.setUserId(DeviceRepository.this.mUser.getUserId());
            findFirst.setDeviceId(j);
            ArrayList arrayList = new ArrayList();
            for (QuerySuperPanelKeywordInfoResponse.ContentBean.RowsBean rowsBean : querySuperPanelKeywordInfoResponse.getContent().getRows()) {
                SuperPanelInfo.PanelKeyInfo panelKeyInfo = new SuperPanelInfo.PanelKeyInfo();
                panelKeyInfo.setPanelinfoid(rowsBean.getPanelinfoid());
                panelKeyInfo.setKeywords(rowsBean.getKeywords());
                panelKeyInfo.setKeywordsname(rowsBean.getKeywordsname());
                panelKeyInfo.setActionType(rowsBean.getActiontype());
                panelKeyInfo.setExecutecommand(rowsBean.getExecutecommand());
                arrayList.add(panelKeyInfo);
            }
            findFirst.setPanelKeyInfo(arrayList);
            boxFor.put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Listing<SuperPanelInfo> getSuperPanelKeyInfo(LifecycleOwner owner, long deviceId) {
        return new AnonymousClass4(deviceId, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void setSuperPanelDevice(final long deviceId, final List<Long> deviceIds) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$setSuperPanelDevice$9(deviceId, deviceIds);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSuperPanelDevice$9(long j, List list) {
        SuperPanelInfo findFirst = this.mBoxBuilderFactory.querySuperPanelInfo(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setDeviceIds(list);
            this.mBoxStore.boxFor(SuperPanelInfo.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void setSuperPanelDeviceAndGroup(final long deviceId, final List<Long> deviceIds, final List<Long> groupIds) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$setSuperPanelDeviceAndGroup$10(deviceId, deviceIds, groupIds);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSuperPanelDeviceAndGroup$10(long j, List list, List list2) {
        SuperPanelInfo findFirst = this.mBoxBuilderFactory.querySuperPanelInfo(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setDeviceIds(list);
            findFirst.setGroupIds(list2);
            this.mBoxStore.boxFor(SuperPanelInfo.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void setSuperPanelScene(final long deviceId, final List<Long> sceneIds) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$setSuperPanelScene$11(deviceId, sceneIds);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSuperPanelScene$11(long j, List list) {
        SuperPanelInfo findFirst = this.mBoxBuilderFactory.querySuperPanelInfo(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setSceneIds(list);
            this.mBoxStore.boxFor(SuperPanelInfo.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void setSuperPanelGroup(final long deviceId, final List<Long> groupIds) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$setSuperPanelGroup$12(deviceId, groupIds);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSuperPanelGroup$12(long j, List list) {
        SuperPanelInfo findFirst = this.mBoxBuilderFactory.querySuperPanelInfo(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setGroupIds(list);
            this.mBoxStore.boxFor(SuperPanelInfo.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void setSuperPanelKeyInfo(final long deviceId, final SuperPanelInfo.PanelKeyInfo keyInfo) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$setSuperPanelKeyInfo$13(deviceId, keyInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSuperPanelKeyInfo$13(long j, SuperPanelInfo.PanelKeyInfo panelKeyInfo) {
        SuperPanelInfo findFirst = this.mBoxBuilderFactory.querySuperPanelInfo(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            int size = findFirst.getPanelKeyInfo().size();
            for (int i = 0; i < size; i++) {
                if (panelKeyInfo.equals(findFirst.getPanelKeyInfo().get(i))) {
                    findFirst.getPanelKeyInfo().set(i, panelKeyInfo);
                    this.mBoxStore.boxFor(SuperPanelInfo.class).put((Box) findFirst);
                    return;
                }
            }
            findFirst.getPanelKeyInfo().add(panelKeyInfo);
            this.mBoxStore.boxFor(SuperPanelInfo.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getSuperPanel() {
        List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
        if (value == null) {
            return null;
        }
        for (Device device : value) {
            if (ProductRepository.isSuperPanel(device.getProductId())) {
                return device;
            }
        }
        return null;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void sortDevice(final List<Device> devices) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$sortDevice$14(devices);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortDevice$14(List list) {
        int i = 0;
        while (i < list.size()) {
            Device device = (Device) list.get(i);
            i++;
            device.setIndex(i);
        }
        this.mBoxStore.boxFor(Device.class).put((Collection) list);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> getSubDevice(long placeId, long deviceId) {
        ArrayList arrayList = new ArrayList();
        for (Device device : this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), placeId, -1L, -1L).build().find()) {
            if (device.isSubDevice() && device.getMacdeviceid() == deviceId) {
                arrayList.add(device);
            }
        }
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> getDeviceListByPlaceId(long placeId) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), placeId, -1L, -1L).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> getDeviceListByRoomIdFromDb(long floorId, long roomId) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), -1L, floorId, roomId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> getDeviceListByRoomIdFromDb(long place, long floorId, long roomId) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), place, floorId, roomId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> getDeviceByUnicastAddress(long place, int unicastAddress) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryDeviceByUnicastAddress(this.mUser.getUserId(), place, unicastAddress).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> getDevicesByIds(List<Long> ids) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryDevicesByIds(this.mUser.getUserId(), ids).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> queryDevicesByMacDeviceId(Long id) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryDevicesByMacDeviceId(this.mUser.getUserId(), id.longValue()).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public boolean isDeviceNameExist(long place, String name) {
        return !this.mBoxBuilderFactory.queryDeviceByName(this.mUser.getUserId(), place, name).build().find().isEmpty();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public List<Device> getSubDeviceListFromDb(long placeId, long floorId, long roomId, long deviceId) {
        new ArrayList();
        return this.mBoxBuilderFactory.querySubDeviceList(this.mUser.getUserId(), placeId, floorId, roomId, deviceId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public void removeDaliSubContentByDeviceId(final long placeId, final long deviceId) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.DeviceRepository$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                DeviceRepository.this.lambda$removeDaliSubContentByDeviceId$15(placeId, deviceId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDaliSubContentByDeviceId$15(long j, long j2) {
        List<Device> find = this.mBoxBuilderFactory.querySubDeviceList(this.mUser.getUserId(), j, -1L, -1L, j2).build().find();
        if (!find.isEmpty()) {
            this.mBoxStore.boxFor(Device.class).remove((Collection) find);
        }
        List<Group> find2 = this.mBoxBuilderFactory.querySubGroupList(this.mUser.getUserId(), j, -1L, -1L, j2).build().find();
        if (!find2.isEmpty()) {
            this.mBoxStore.boxFor(Group.class).remove((Collection) find2);
        }
        List<Scene> find3 = this.mBoxBuilderFactory.querySceneList(this.mUser.getUserId(), j, -1L, -1L, j2).build().find();
        if (find3.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Scene.class).remove((Collection) find3);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IDevice
    public Device getDeviceByMac(long placeId, String mac) {
        return this.mBoxBuilderFactory.queryDeviceByMac(this.mUser.getUserId(), placeId, mac).build().findFirst();
    }
}