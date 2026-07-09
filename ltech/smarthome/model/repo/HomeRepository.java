package com.ltech.smarthome.model.repo;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.WrapperResource;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.repo.HomeRepository;
import com.ltech.smarthome.model.repo.ifun.IHome;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.response.floor.ListFloorResponse;
import com.ltech.smarthome.net.response.place.AppTokenResponse;
import com.ltech.smarthome.net.response.place.DetailPlaceResponse;
import com.ltech.smarthome.net.response.place.ListPlaceResponse;
import com.ltech.smarthome.net.response.place.QueryPlaceInfoResponse;
import com.ltech.smarthome.net.response.placeuser.ListPlaceUserResponse;
import com.ltech.smarthome.net.response.room.ListRoomResponse;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class HomeRepository extends BaseRepository implements IHome {
    private MediatorLiveData<List<Device>> deviceListCache;
    private MutableLiveData<Place> mSelectPlace;

    static /* synthetic */ void lambda$getDeviceListCache$6(List list) {
    }

    public HomeRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
    }

    /* renamed from: com.ltech.smarthome.model.repo.HomeRepository$1, reason: invalid class name */
    class AnonymousClass1 extends WrapperResource<Place, ListPlaceResponse> {
        final /* synthetic */ LifecycleOwner val$owner;

        AnonymousClass1(final LifecycleOwner val$owner) {
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return HomeRepository.this.mLimiter.shouldFetch(HomeRepository.this.mKeyCreator.placeListKey());
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            HomeRepository.this.mLimiter.reset(HomeRepository.this.mKeyCreator.placeListKey());
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Place> getDbQueryBuilder() {
            return HomeRepository.this.mBoxBuilderFactory.queryPlaceList(HomeRepository.this.mUser.getUserId());
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<ListPlaceResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().listPlace().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final ListPlaceResponse response) {
            HomeRepository.this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HomeRepository.AnonymousClass1.this.lambda$saveDataFromNet$0(response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(ListPlaceResponse listPlaceResponse) {
            Box boxFor = HomeRepository.this.mBoxStore.boxFor(Place.class);
            List<Place> find = getDbQueryBuilder().build().find();
            if (listPlaceResponse.getTotal() > 0) {
                ArrayList arrayList = new ArrayList(listPlaceResponse.getTotal());
                for (int size = listPlaceResponse.getRows().size() - 1; size >= 0; size--) {
                    ListPlaceResponse.RowsBean rowsBean = listPlaceResponse.getRows().get(size);
                    Place place = new Place();
                    place.setCurrentUserId(HomeRepository.this.mUser.getUserId());
                    place.setUserId(rowsBean.getUserid());
                    place.setPlaceId(rowsBean.getPlaceid());
                    place.setPlaceName(rowsBean.getPlacename());
                    place.setRoleType(rowsBean.getRoletype());
                    place.setLatitude(rowsBean.getLatitude());
                    place.setLongitude(rowsBean.getLongitude());
                    place.setQrCode(rowsBean.getQrcode());
                    place.setLocation(rowsBean.getLocation());
                    place.setMeshUUID(rowsBean.getMeshuuid());
                    place.setNetKey(rowsBean.getNetkey());
                    place.setAppKey(rowsBean.getApplicationkey());
                    place.setProvisionerAddress(rowsBean.getProvisioneraddress());
                    place.setIvindex(rowsBean.getIvindex());
                    place.setDevicetotal(rowsBean.getDevicetotal());
                    place.setRoomtotal(rowsBean.getRoomtotal());
                    place.setFloortotal(rowsBean.getFloortotal());
                    Iterator<Place> it = find.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Place next = it.next();
                            if (next.equals(place)) {
                                place.setId(next.getId());
                                break;
                            }
                        }
                    }
                    LHomeLog.i(getClass(), place.toString());
                    arrayList.add(place);
                }
                boxFor.put((Collection) arrayList);
                find.removeAll(arrayList);
            }
            Log.e("placeList", "saveDataFromNet.places.size=" + listPlaceResponse.getTotal() + "__remote.size=" + find.size());
            boxFor.remove((Collection) find);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Listing<Place> getPlaceList(LifecycleOwner owner) {
        return new AnonymousClass1(owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Listing<Place> getPlace(final LifecycleOwner owner, final long placeId) {
        return new WrapperResource<Place, DetailPlaceResponse>() { // from class: com.ltech.smarthome.model.repo.HomeRepository.2
            @Override // com.ltech.smarthome.model.WrapperResource
            protected boolean shouldFetch() {
                return HomeRepository.this.mLimiter.shouldFetch(HomeRepository.this.mKeyCreator.placeKey(placeId));
            }

            @Override // com.ltech.smarthome.model.WrapperResource
            protected void fetchFail() {
                HomeRepository.this.mLimiter.reset(HomeRepository.this.mKeyCreator.placeKey(placeId));
            }

            @Override // com.ltech.smarthome.model.WrapperResource
            protected QueryBuilder<Place> getDbQueryBuilder() {
                return HomeRepository.this.mBoxBuilderFactory.queryPlace(HomeRepository.this.mUser.getUserId(), placeId);
            }

            @Override // com.ltech.smarthome.model.WrapperResource
            protected void netCall(Observer<DetailPlaceResponse> observer) {
                ((ObservableSubscribeProxy) Injection.net().detailPlace(placeId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.model.WrapperResource
            public void saveDataFromNet(DetailPlaceResponse response) {
                Box boxFor = HomeRepository.this.mBoxStore.boxFor(Place.class);
                Place findFirst = getDbQueryBuilder().build().findFirst();
                if (findFirst == null) {
                    findFirst = new Place();
                    findFirst.setCurrentUserId(HomeRepository.this.mUser.getUserId());
                    findFirst.setUserId(response.getUserid());
                    findFirst.setPlaceId(response.getPlaceid());
                }
                findFirst.setPlaceName(response.getPlacename());
                findFirst.setLongitude(response.getLongitude());
                findFirst.setLocation(response.getLocation());
                findFirst.setLatitude(response.getLatitude());
                findFirst.setRoleType(response.getRoletype());
                findFirst.setQrCode(response.getQrcode());
                findFirst.setMeshUUID(response.getMeshuuid());
                findFirst.setNetKey(response.getNetkey());
                findFirst.setAppKey(response.getApplicationkey());
                findFirst.setAppToken(response.getApplicationkey());
                findFirst.setAppKey(response.getApplicationkey());
                findFirst.setAppKey(response.getApplicationkey());
                findFirst.setProvisionerAddress(response.getProvisioneraddress());
                findFirst.setIvindex(response.getIvindex());
                findFirst.setDevicetotal(response.getDevicetotal());
                findFirst.setRoomtotal(response.getRoomtotal());
                findFirst.setFloortotal(response.getFloortotal());
                boxFor.put((Box) findFirst);
            }
        }.toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void removePlace(final long placeId) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$removePlace$0(placeId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removePlace$0(long j) {
        List<Place> find = this.mBoxBuilderFactory.queryPlace(this.mUser.getUserId(), j).build().find();
        if (find.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Place.class).remove((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void updatePlaceName(final long placeId, final String placeName) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$updatePlaceName$1(placeId, placeName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePlaceName$1(long j, String str) {
        Place findFirst = this.mBoxBuilderFactory.queryPlace(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setPlaceName(str);
            this.mBoxStore.boxFor(Place.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void updatePlaceIvIndex(final long placeId, final int ivIndex) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$updatePlaceIvIndex$2(placeId, ivIndex);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePlaceIvIndex$2(long j, int i) {
        Place findFirst = this.mBoxBuilderFactory.queryPlace(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setIvindex(i);
            this.mBoxStore.boxFor(Place.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void updatePlaceLocation(final long placeId, final String location, final double latitude, final double longitude) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$updatePlaceLocation$3(placeId, location, latitude, longitude);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePlaceLocation$3(long j, String str, double d2, double d3) {
        Place findFirst = this.mBoxBuilderFactory.queryPlace(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setLocation(str);
            findFirst.setLatitude(d2);
            findFirst.setLongitude(d3);
            this.mBoxStore.boxFor(Place.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void savePlace(Place place) {
        place.setCurrentUserId(this.mUser.getUserId());
        this.mBoxStore.boxFor(Place.class).put((Box) place);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public LiveData<Place> getSelectPlace() {
        if (this.mSelectPlace == null) {
            this.mSelectPlace = new MutableLiveData<>();
        }
        if (this.mSelectPlace.getValue() == null) {
            this.mSelectPlace.postValue((Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class));
        }
        return this.mSelectPlace;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Place getSelPlace() {
        return (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void setSelectPlace(Place place) {
        if (this.mSelectPlace == null) {
            this.mSelectPlace = new MutableLiveData<>();
        }
        if (place == null) {
            SharedPreferenceUtil.edit().removeBean(Constants.SELECT_PLACE, Place.class);
        } else {
            if (this.mSelectPlace.getValue() != null) {
                if (!this.mSelectPlace.getValue().equals(place)) {
                    SharedPreferenceUtil.edit().keepShared(Constants.PLACE_CHANGED, true);
                } else {
                    SharedPreferenceUtil.edit().keepShared(Constants.PLACE_CHANGED, false);
                }
            }
            SharedPreferenceUtil.edit().putBean(Constants.SELECT_PLACE, place);
        }
        this.mSelectPlace.postValue(place);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void updateAppToken(Object appTokenResponse) {
        SharedPreferenceUtil.edit().putBean("apptoken", appTokenResponse.toString());
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public AppTokenResponse getAppToken(long placeId) {
        return (AppTokenResponse) GsonUtils.getGson().fromJson(((String) SharedPreferenceUtil.getBean("apptoken", null)).trim(), AppTokenResponse.class);
    }

    /* renamed from: com.ltech.smarthome.model.repo.HomeRepository$3, reason: invalid class name */
    class AnonymousClass3 extends WrapperResource<Place, QueryPlaceInfoResponse> {
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$placeId;

        AnonymousClass3(final long val$placeId, final LifecycleOwner val$owner) {
            this.val$placeId = val$placeId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return HomeRepository.this.mLimiter.shouldFetch(HomeRepository.this.mKeyCreator.placeInfoKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            HomeRepository.this.mLimiter.reset(HomeRepository.this.mKeyCreator.placeInfoKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Place> getDbQueryBuilder() {
            return HomeRepository.this.mBoxBuilderFactory.queryPlace(HomeRepository.this.mUser.getUserId(), this.val$placeId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<QueryPlaceInfoResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().queryPlaceDetailInfo(this.val$placeId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final QueryPlaceInfoResponse response) {
            BoxStore boxStore = HomeRepository.this.mBoxStore;
            final long j = this.val$placeId;
            boxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HomeRepository.AnonymousClass3.this.lambda$saveDataFromNet$0(j, response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(long j, QueryPlaceInfoResponse queryPlaceInfoResponse) {
            Box boxFor = HomeRepository.this.mBoxStore.boxFor(Floor.class);
            List<Floor> find = HomeRepository.this.mBoxBuilderFactory.queryFloorList(HomeRepository.this.mUser.getUserId(), j).build().find();
            if (queryPlaceInfoResponse.getFloors().getTotal() > 0) {
                ArrayList arrayList = new ArrayList(queryPlaceInfoResponse.getFloors().getTotal());
                for (QueryPlaceInfoResponse.FloorsBean.RowsBeanX rowsBeanX : queryPlaceInfoResponse.getFloors().getRows()) {
                    Floor floor = new Floor();
                    floor.setUserId(HomeRepository.this.mUser.getUserId());
                    floor.setFloorId(rowsBeanX.getFloorid());
                    floor.setFloorName(rowsBeanX.getFloorname());
                    floor.setPlaceId(rowsBeanX.getPlaceid());
                    floor.setIndex(rowsBeanX.getFloorindex());
                    Iterator<Floor> it = find.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Floor next = it.next();
                            if (next.equals(floor)) {
                                floor.setId(next.getId());
                                break;
                            }
                        }
                    }
                    arrayList.add(floor);
                    HomeRepository.this.mLimiter.refresh(HomeRepository.this.mKeyCreator.roomListKey(rowsBeanX.getFloorid()));
                }
                boxFor.put((Collection) arrayList);
                find.removeAll(arrayList);
            }
            boxFor.remove((Collection) find);
            HomeRepository.this.mLimiter.refresh(HomeRepository.this.mKeyCreator.floorListKey(j));
            Box boxFor2 = HomeRepository.this.mBoxStore.boxFor(Room.class);
            List<Room> find2 = HomeRepository.this.mBoxBuilderFactory.queryRoomList(HomeRepository.this.mUser.getUserId(), j, -1L).build().find();
            if (queryPlaceInfoResponse.getRooms().getTotal() > 0) {
                ArrayList arrayList2 = new ArrayList(queryPlaceInfoResponse.getRooms().getTotal());
                for (QueryPlaceInfoResponse.RoomsBean.RowsBean rowsBean : queryPlaceInfoResponse.getRooms().getRows()) {
                    Room room = new Room();
                    room.setPlaceId(j);
                    room.setUserId(HomeRepository.this.mUser.getUserId());
                    room.setRoomId(rowsBean.getRoomid());
                    room.setRoomName(rowsBean.getRoomname());
                    room.setFloorId(rowsBean.getFloorid());
                    room.setIndex(rowsBean.getRoomindex());
                    Iterator<Room> it2 = find2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            Room next2 = it2.next();
                            if (next2.equals(room)) {
                                room.setId(next2.getId());
                                break;
                            }
                        }
                    }
                    arrayList2.add(room);
                }
                boxFor2.put((Collection) arrayList2);
                find2.removeAll(arrayList2);
            }
            boxFor2.remove((Collection) find2);
            HomeRepository.this.mLimiter.refresh(HomeRepository.this.mKeyCreator.roomListKey(-1L));
            Box boxFor3 = HomeRepository.this.mBoxStore.boxFor(PlaceUser.class);
            boxFor3.remove((Collection) HomeRepository.this.mBoxBuilderFactory.queryPlaceUserList(HomeRepository.this.mUser.getUserId(), j, -1L).build().find());
            if (queryPlaceInfoResponse.getUsers().getTotal() > 0) {
                for (QueryPlaceInfoResponse.UsersBean.RowsBeanXXXX rowsBeanXXXX : queryPlaceInfoResponse.getUsers().getRows()) {
                    PlaceUser placeUser = new PlaceUser();
                    placeUser.setCurrentUserId(HomeRepository.this.mUser.getUserId());
                    placeUser.setUserId(rowsBeanXXXX.getUserid());
                    placeUser.setPlaceUserId(rowsBeanXXXX.getPlaceuserid());
                    placeUser.setPlaceId(rowsBeanXXXX.getPlaceid());
                    placeUser.setRoleType(rowsBeanXXXX.getRoletype());
                    placeUser.setUserName(rowsBeanXXXX.getUsername());
                    boxFor3.put((Box) placeUser);
                }
            }
            HomeRepository.this.mLimiter.refresh(HomeRepository.this.mKeyCreator.placeUserKey(j));
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Listing<Place> getPlaceDetailInfo(LifecycleOwner owner, long placeId) {
        return new AnonymousClass3(placeId, owner).toListing();
    }

    static String getSubDeviceId(String type) {
        return ProductRepository.getIrProductIdByType(type);
    }

    private Device getDeviceByDeviceId(long deviceId) {
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

    public LiveData<List<Device>> getDeviceListCache() {
        if (this.deviceListCache == null) {
            MediatorLiveData<List<Device>> mediatorLiveData = new MediatorLiveData<>();
            this.deviceListCache = mediatorLiveData;
            mediatorLiveData.addSource(Transformations.switchMap(Injection.repo().home().getSelectPlace(), new Function1() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda11
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LiveData lambda$getDeviceListCache$4;
                    lambda$getDeviceListCache$4 = HomeRepository.this.lambda$getDeviceListCache$4((Place) obj);
                    return lambda$getDeviceListCache$4;
                }
            }), new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda12
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    HomeRepository.this.lambda$getDeviceListCache$5((List) obj);
                }
            });
        }
        if (!this.deviceListCache.hasActiveObservers()) {
            this.deviceListCache.observeForever(new androidx.lifecycle.Observer() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    HomeRepository.lambda$getDeviceListCache$6((List) obj);
                }
            });
        }
        return this.deviceListCache;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$getDeviceListCache$4(Place place) {
        if (place == null) {
            return new MutableLiveData(new ArrayList());
        }
        return new ObjectBoxLiveData(this.mBoxBuilderFactory.queryDeviceList(this.mUser.getUserId(), place.getPlaceId(), -1L, -1L).build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDeviceListCache$5(List list) {
        this.deviceListCache.setValue(list);
    }

    /* renamed from: com.ltech.smarthome.model.repo.HomeRepository$4, reason: invalid class name */
    class AnonymousClass4 extends WrapperResource<Floor, ListFloorResponse> {
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$placeId;

        AnonymousClass4(final long val$placeId, final LifecycleOwner val$owner) {
            this.val$placeId = val$placeId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return HomeRepository.this.mLimiter.shouldFetch(HomeRepository.this.mKeyCreator.floorListKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            HomeRepository.this.mLimiter.reset(HomeRepository.this.mKeyCreator.floorListKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Floor> getDbQueryBuilder() {
            return HomeRepository.this.mBoxBuilderFactory.queryFloorList(HomeRepository.this.mUser.getUserId(), this.val$placeId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<ListFloorResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().listFloor(this.val$placeId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final ListFloorResponse response) {
            HomeRepository.this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HomeRepository.AnonymousClass4.this.lambda$saveDataFromNet$0(response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(ListFloorResponse listFloorResponse) {
            Box boxFor = HomeRepository.this.mBoxStore.boxFor(Floor.class);
            List<Floor> find = getDbQueryBuilder().build().find();
            if (listFloorResponse.getTotal() > 0) {
                ArrayList arrayList = new ArrayList(listFloorResponse.getTotal());
                for (ListFloorResponse.RowsBean rowsBean : listFloorResponse.getRows()) {
                    Floor floor = new Floor();
                    floor.setUserId(HomeRepository.this.mUser.getUserId());
                    floor.setFloorId(rowsBean.getFloorid());
                    floor.setFloorName(rowsBean.getFloorname());
                    floor.setPlaceId(rowsBean.getPlaceid());
                    floor.setIndex(rowsBean.getFloorindex());
                    Iterator<Floor> it = find.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Floor next = it.next();
                            if (next.equals(floor)) {
                                floor.setId(next.getId());
                                break;
                            }
                        }
                    }
                    arrayList.add(floor);
                }
                boxFor.put((Collection) arrayList);
                find.removeAll(arrayList);
            }
            boxFor.remove((Collection) find);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Listing<Floor> getFloorList(LifecycleOwner owner, long placeId) {
        return new AnonymousClass4(placeId, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public List<Floor> getFloorListByPlaceId(long placeId) {
        return this.mBoxBuilderFactory.queryFloorList(this.mUser.getUserId(), placeId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public List<Room> getRoomListByPlaceId(long placeId, long floorId) {
        return this.mBoxBuilderFactory.queryRoomList(this.mUser.getUserId(), placeId, floorId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public List<Room> getRoomListByFloorId(long placeId, long floorId) {
        new ArrayList();
        return this.mBoxBuilderFactory.queryRoomList(this.mUser.getUserId(), placeId, floorId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public List<Room> getRoomList(long placeId) {
        return getRoomListByFloorId(placeId, -1L);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void removeFloor(final long floorId) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$removeFloor$7(floorId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeFloor$7(long j) {
        List<Floor> find = this.mBoxBuilderFactory.queryFloor(this.mUser.getUserId(), j).build().find();
        if (find.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Floor.class).remove((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void updateFloorName(final long floorId, final String name) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$updateFloorName$8(floorId, name);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFloorName$8(long j, String str) {
        Floor findFirst = this.mBoxBuilderFactory.queryFloor(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setFloorName(str);
            this.mBoxStore.boxFor(Floor.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void saveFloor(Floor floor) {
        floor.setUserId(this.mUser.getUserId());
        this.mBoxStore.boxFor(Floor.class).put((Box) floor);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Floor getFloor(long floorId) {
        return this.mBoxBuilderFactory.queryFloor(this.mUser.getUserId(), floorId).build().findFirst();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void sortFloor(final List<Floor> floors) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$sortFloor$9(floors);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortFloor$9(List list) {
        int i = 0;
        while (i < list.size()) {
            Floor floor = (Floor) list.get(i);
            i++;
            floor.setIndex(i);
        }
        this.mBoxStore.boxFor(Floor.class).put((Collection) list);
    }

    /* renamed from: com.ltech.smarthome.model.repo.HomeRepository$5, reason: invalid class name */
    class AnonymousClass5 extends WrapperResource<Room, ListRoomResponse> {
        final /* synthetic */ long val$floorId;
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$placeId;

        AnonymousClass5(final long val$floorId, final long val$placeId, final LifecycleOwner val$owner) {
            this.val$floorId = val$floorId;
            this.val$placeId = val$placeId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            if (this.val$floorId == -1) {
                return false;
            }
            return HomeRepository.this.mLimiter.shouldFetch(HomeRepository.this.mKeyCreator.roomListKey(this.val$floorId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            HomeRepository.this.mLimiter.reset(HomeRepository.this.mKeyCreator.roomListKey(this.val$floorId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Room> getDbQueryBuilder() {
            return HomeRepository.this.mBoxBuilderFactory.queryRoomList(HomeRepository.this.mUser.getUserId(), this.val$placeId, this.val$floorId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<ListRoomResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().listRoom(this.val$floorId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final ListRoomResponse response) {
            BoxStore boxStore = HomeRepository.this.mBoxStore;
            final long j = this.val$placeId;
            boxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    HomeRepository.AnonymousClass5.this.lambda$saveDataFromNet$0(response, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(ListRoomResponse listRoomResponse, long j) {
            Box boxFor = HomeRepository.this.mBoxStore.boxFor(Room.class);
            List<Room> find = getDbQueryBuilder().build().find();
            if (listRoomResponse.getTotal() > 0) {
                ArrayList arrayList = new ArrayList(listRoomResponse.getTotal());
                for (ListRoomResponse.RowsBean rowsBean : listRoomResponse.getRows()) {
                    Room room = new Room();
                    room.setPlaceId(j);
                    room.setUserId(HomeRepository.this.mUser.getUserId());
                    room.setRoomId(rowsBean.getRoomid());
                    room.setRoomName(rowsBean.getRoomname());
                    room.setFloorId(rowsBean.getFloorid());
                    room.setIndex(rowsBean.getRoomindex());
                    Iterator<Room> it = find.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Room next = it.next();
                            if (next.equals(room)) {
                                room.setId(next.getId());
                                break;
                            }
                        }
                    }
                    arrayList.add(room);
                }
                boxFor.put((Collection) arrayList);
                find.removeAll(arrayList);
            }
            boxFor.remove((Collection) find);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Listing<Room> getRoomList(LifecycleOwner owner, long placeId, long floorId) {
        return new AnonymousClass5(floorId, placeId, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void removeRoom(final long roomId) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$removeRoom$10(roomId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeRoom$10(long j) {
        List<Room> find = this.mBoxBuilderFactory.queryRoom(this.mUser.getUserId(), j).build().find();
        if (find.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Room.class).remove((Collection) find);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void updateRoomName(final long roomId, final String roomName) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$updateRoomName$11(roomId, roomName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRoomName$11(long j, String str) {
        Room findFirst = this.mBoxBuilderFactory.queryRoom(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setRoomName(str);
            this.mBoxStore.boxFor(Room.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void saveRoom(Room room) {
        room.setUserId(this.mUser.getUserId());
        this.mBoxStore.boxFor(Room.class).put((Box) room);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Room getRoom(long roomId) {
        return this.mBoxBuilderFactory.queryRoom(this.mUser.getUserId(), roomId).build().findFirst();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public void sortRoom(final List<Room> rooms) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                HomeRepository.this.lambda$sortRoom$12(rooms);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortRoom$12(List list) {
        int i = 0;
        while (i < list.size()) {
            Room room = (Room) list.get(i);
            i++;
            room.setIndex(i);
        }
        this.mBoxStore.boxFor(Room.class).put((Collection) list);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public boolean checkPlaceList() {
        return !this.mBoxBuilderFactory.queryPlaceList(this.mUser.getUserId()).build().find().isEmpty();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public String getRoomName(long floorId, long roomId) {
        Floor floor = getFloor(floorId);
        Room room = getRoom(roomId);
        if (floor == null || room == null) {
            if (floor != null) {
                return floor.getFloorName();
            }
            return room != null ? room.getRoomName() : "";
        }
        return floor.getFloorName() + " " + room.getRoomName();
    }

    /* renamed from: com.ltech.smarthome.model.repo.HomeRepository$6, reason: invalid class name */
    class AnonymousClass6 extends WrapperResource<PlaceUser, ListPlaceUserResponse> {
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$placeId;
        final /* synthetic */ long val$placeUserId;

        AnonymousClass6(final long val$placeId, final long val$placeUserId, final LifecycleOwner val$owner) {
            this.val$placeId = val$placeId;
            this.val$placeUserId = val$placeUserId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return HomeRepository.this.mLimiter.shouldFetch(HomeRepository.this.mKeyCreator.placeUserKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            HomeRepository.this.mLimiter.reset(HomeRepository.this.mKeyCreator.placeUserKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<PlaceUser> getDbQueryBuilder() {
            return HomeRepository.this.mBoxBuilderFactory.queryPlaceUserList(HomeRepository.this.mUser.getUserId(), this.val$placeId, this.val$placeUserId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<ListPlaceUserResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().listPlaceUser(this.val$placeId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner, Lifecycle.Event.ON_DESTROY)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final ListPlaceUserResponse response) {
            final Box boxFor = HomeRepository.this.mBoxStore.boxFor(PlaceUser.class);
            final List<PlaceUser> find = getDbQueryBuilder().build().find();
            if (response.getTotal() > 0) {
                HomeRepository.this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.HomeRepository$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        HomeRepository.AnonymousClass6.this.lambda$saveDataFromNet$0(boxFor, find, response);
                    }
                });
            } else {
                boxFor.remove((Collection) find);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(Box box, List list, ListPlaceUserResponse listPlaceUserResponse) {
            box.remove((Collection) list);
            for (ListPlaceUserResponse.RowsBean rowsBean : listPlaceUserResponse.getRows()) {
                PlaceUser placeUser = new PlaceUser();
                placeUser.setCurrentUserId(HomeRepository.this.mUser.getUserId());
                placeUser.setUserId(rowsBean.getUserid());
                placeUser.setPlaceUserId(rowsBean.getPlaceuserid());
                placeUser.setPlaceId(rowsBean.getPlaceid());
                placeUser.setRoleType(rowsBean.getRoletype());
                placeUser.setUserName(rowsBean.getUsername());
                placeUser.setHeadUrl(rowsBean.getHeadurl());
                placeUser.setRemark(rowsBean.getRemark());
                placeUser.setMobilephone(rowsBean.getMobilephone());
                placeUser.setEmail(rowsBean.getEmail());
                box.put((Box) placeUser);
            }
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IHome
    public Listing<PlaceUser> getPlaceUserList(LifecycleOwner owner, long placeId, long placeUserId) {
        return new AnonymousClass6(placeId, placeUserId, owner).toListing();
    }
}