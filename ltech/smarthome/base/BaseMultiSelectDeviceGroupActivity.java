package com.ltech.smarthome.base;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity;
import com.ltech.smarthome.databinding.ActSelect2Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public abstract class BaseMultiSelectDeviceGroupActivity extends BaseMultiSelect2Activity<Role> implements TextWatcher {
    protected long floorId;
    protected Listing<Floor> mRequest;
    protected long placeId;
    protected String productId;
    protected long roomId;
    protected List<Room> roomList = new ArrayList();
    protected List<Floor> floorList = new ArrayList();
    protected List<Room> allRoom = new ArrayList();
    protected LiveData<Resource<List<Role>>> roleList = new MediatorLiveData();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();
    protected List<Role> allData = new ArrayList();
    protected MutableLiveData<List<Role>> refreshData = new MutableLiveData<>();

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    protected boolean filterDevice(Device device) {
        return true;
    }

    protected boolean filterGroup(Group group) {
        return true;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelect2Activity
    protected void save() {
    }

    @Override // com.ltech.smarthome.base.BaseList2Activity
    protected List<Role> getList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelect2Activity, com.ltech.smarthome.base.BaseList2Activity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        setBackImage(R.mipmap.icon_back);
        ((ActSelect2Binding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActSelect2Binding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelect2Binding) BaseMultiSelectDeviceGroupActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.floorId = getIntent().getLongExtra(Constants.FLOOR_ID, -1L);
        this.roomId = getIntent().getLongExtra(Constants.ROOM_ID, -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
    }

    /* renamed from: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity$2, reason: invalid class name */
    class AnonymousClass2 implements Observer<Resource<List<Floor>>> {
        AnonymousClass2() {
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(Resource<List<Floor>> listResource) {
            BaseMultiSelectDeviceGroupActivity.this.handleResource(listResource, new IAction() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity$2$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    BaseMultiSelectDeviceGroupActivity.AnonymousClass2.this.lambda$onChanged$0((List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChanged$0(List list) {
            Floor floor = new Floor();
            floor.setFloorName(BaseMultiSelectDeviceGroupActivity.this.getString(R.string.all_floor));
            floor.setFloorId(-1L);
            floor.setPlaceId(BaseMultiSelectDeviceGroupActivity.this.placeId);
            list.add(0, floor);
            BaseMultiSelectDeviceGroupActivity.this.floorList = list;
            BaseMultiSelectDeviceGroupActivity.this.initFloorSpinner();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Listing<Floor> floorList = Injection.repo().home().getFloorList(this, this.placeId);
        this.mRequest = floorList;
        floorList.data().observe(this, new AnonymousClass2());
        this.selectRoom.addSource(Transformations.switchMap(this.selectFloor, new Function1() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = BaseMultiSelectDeviceGroupActivity.this.lambda$startObserve$0((Floor) obj);
                return lambda$startObserve$0;
            }
        }), new Observer() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseMultiSelectDeviceGroupActivity.this.lambda$startObserve$2((Resource) obj);
            }
        });
        this.roleList = Transformations.switchMap(this.selectRoom, new Function1<Room, LiveData<Resource<List<Role>>>>(this) { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity.3
            @Override // kotlin.jvm.functions.Function1
            public LiveData<Resource<List<Role>>> invoke(Room room) {
                return null;
            }
        });
        this.roleList = Transformations.switchMap(this.selectRoom, new Function1<Room, LiveData<Resource<List<Role>>>>() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity.4
            @Override // kotlin.jvm.functions.Function1
            public LiveData<Resource<List<Role>>> invoke(Room room) {
                if (room == null) {
                    return ResourceEmptyLiveData.create();
                }
                ArrayList arrayList = new ArrayList();
                List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(BaseMultiSelectDeviceGroupActivity.this.placeId, BaseMultiSelectDeviceGroupActivity.this.getCurRoom().getFloorId(), BaseMultiSelectDeviceGroupActivity.this.getCurRoom().getRoomId());
                List<Group> groupListByRoomIdFromDb = Injection.repo().group().getGroupListByRoomIdFromDb(BaseMultiSelectDeviceGroupActivity.this.placeId, BaseMultiSelectDeviceGroupActivity.this.getCurRoom().getFloorId(), BaseMultiSelectDeviceGroupActivity.this.getCurRoom().getRoomId());
                for (Device device : deviceListByRoomIdFromDb) {
                    if (BaseMultiSelectDeviceGroupActivity.this.filterDevice(device)) {
                        arrayList.add(device);
                    }
                }
                for (Group group : groupListByRoomIdFromDb) {
                    if (BaseMultiSelectDeviceGroupActivity.this.filterGroup(group)) {
                        arrayList.add(group);
                    }
                }
                Collections.sort(arrayList, new Comparator<Role>(this) { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity.4.1
                    @Override // java.util.Comparator
                    public int compare(Role o1, Role o2) {
                        if (o1.getIndex() > o2.getIndex()) {
                            return 1;
                        }
                        if (o1.getIndex() < o2.getIndex()) {
                            return -1;
                        }
                        return o1.getCreatetime().compareTo(o2.getCreatetime());
                    }
                });
                MediatorLiveData mediatorLiveData = new MediatorLiveData();
                mediatorLiveData.setValue(Resource.success(arrayList));
                return mediatorLiveData;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$0(Floor floor) {
        if (floor == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().home().getRoomList(this, this.placeId, floor.getFloorId()).data();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$2(Resource resource) {
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseMultiSelectDeviceGroupActivity.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        Floor value = this.selectFloor.getValue();
        if (value != null) {
            Room room = new Room();
            room.setRoomName(getString(R.string.all_room));
            room.setRoomId(-1L);
            room.setFloorId(value.getFloorId());
            room.setPlaceId(this.placeId);
            this.allRoom.clear();
            this.allRoom.addAll(list);
            if (value.getFloorId() == -1) {
                list.clear();
            }
            list.add(0, room);
            this.roomList = list;
            setCurRoom(checkRoom(list));
            initRoomSpinner();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        Listing<Floor> listing = this.mRequest;
        if (listing != null) {
            listing.retry();
        }
    }

    protected String getPlaceInfo(long floorId, long roomId) {
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
        List<Room> list2 = this.allRoom;
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

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(this.floorList));
        ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!BaseMultiSelectDeviceGroupActivity.this.floorList.get(position).equals(BaseMultiSelectDeviceGroupActivity.this.selectFloor.getValue())) {
                    BaseMultiSelectDeviceGroupActivity baseMultiSelectDeviceGroupActivity = BaseMultiSelectDeviceGroupActivity.this;
                    baseMultiSelectDeviceGroupActivity.setCurFloor(baseMultiSelectDeviceGroupActivity.floorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
                SPUtils.getInstance().put(Constants.USER_CUR_FLOOR, BaseMultiSelectDeviceGroupActivity.this.selectFloor.getValue().getFloorId());
            }
        });
        ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        long j = SPUtils.getInstance().getLong(Constants.USER_CUR_FLOOR, -2L);
        if (j != -2) {
            for (int i = 0; i < this.floorList.size(); i++) {
                if (this.floorList.get(i).getFloorId() == j) {
                    deviceManagerSpinnerAdapter.setSelectPosition(i);
                    ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setSelection(i);
                    return;
                }
            }
        }
        if (this.floorList.size() == 2) {
            deviceManagerSpinnerAdapter.setSelectPosition(1);
            ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setSelection(1);
        } else {
            deviceManagerSpinnerAdapter.setSelectPosition(((ActSelect2Binding) this.mViewBinding).spinnerFloor.getSelectedItemPosition());
        }
    }

    private void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(this.roomList));
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity.6
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!BaseMultiSelectDeviceGroupActivity.this.roomList.get(position).equals(BaseMultiSelectDeviceGroupActivity.this.selectRoom.getValue())) {
                    BaseMultiSelectDeviceGroupActivity baseMultiSelectDeviceGroupActivity = BaseMultiSelectDeviceGroupActivity.this;
                    baseMultiSelectDeviceGroupActivity.setCurRoom(baseMultiSelectDeviceGroupActivity.roomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
                SPUtils.getInstance().put(Constants.USER_CUR_ROOM, BaseMultiSelectDeviceGroupActivity.this.selectRoom.getValue().getRoomId());
            }
        });
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        long j = SPUtils.getInstance().getLong(Constants.USER_CUR_ROOM, -2L);
        if (j != -2) {
            for (int i = 0; i < this.roomList.size(); i++) {
                if (this.roomList.get(i).getRoomId() == j) {
                    deviceManagerSpinnerAdapter.setSelectPosition(i);
                    ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setSelection(i);
                    return;
                }
            }
        }
        deviceManagerSpinnerAdapter.setSelectPosition(((ActSelect2Binding) this.mViewBinding).spinnerRoom.getSelectedItemPosition());
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        search(editable.toString());
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Role>>() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity.8
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Role>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Role role : BaseMultiSelectDeviceGroupActivity.this.allData) {
                        if (role.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(role);
                        }
                    }
                } else {
                    arrayList.addAll(BaseMultiSelectDeviceGroupActivity.this.allData);
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Role>>() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceGroupActivity.7
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Role> items) {
                BaseMultiSelectDeviceGroupActivity.this.refreshData.setValue(items);
            }
        });
    }
}