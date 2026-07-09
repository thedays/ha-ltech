package com.ltech.smarthome.base;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseMultiSelectDeviceActivity;
import com.ltech.smarthome.databinding.ActSelect2Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
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
public abstract class BaseMultiSelectDeviceActivity extends BaseMultiSelect2Activity<Device> implements TextWatcher {
    protected TextView cancelTv;
    protected long floorId;
    protected Listing<Floor> mRequest;
    protected long placeId;
    protected String productId;
    protected long roomId;
    protected List<Room> roomList = new ArrayList();
    protected List<Floor> floorList = new ArrayList();
    protected List<Room> allRoom = new ArrayList();
    protected LiveData<Resource<List<Device>>> deviceList = new MediatorLiveData();
    public MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();
    protected List<Device> allData = new ArrayList();
    protected MutableLiveData<List<Device>> refreshData = new MutableLiveData<>();

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseList2Activity
    protected List<Device> getList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.base.BaseMultiSelect2Activity, com.ltech.smarthome.base.BaseList2Activity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        setBackImage(R.mipmap.icon_back);
        TextView textView = new TextView(this);
        this.cancelTv = textView;
        textView.setTextColor(getResources().getColor(R.color.color_text_red));
        this.cancelTv.setTextSize(14.0f);
        this.cancelTv.setBackgroundColor(getResources().getColor(R.color.white));
        this.cancelTv.setGravity(17);
        this.cancelTv.setText(String.format(getResources().getString(R.string.app_str_unhide_devices), 0));
        ((ActSelect2Binding) this.mViewBinding).footerView.addView(this.cancelTv, new RelativeLayout.LayoutParams(-1, ConvertUtils.dp2px(60.0f)));
        ((ActSelect2Binding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActSelect2Binding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActSelect2Binding) BaseMultiSelectDeviceActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
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

    /* renamed from: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity$2, reason: invalid class name */
    class AnonymousClass2 implements Observer<Resource<List<Floor>>> {
        AnonymousClass2() {
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(Resource<List<Floor>> listResource) {
            BaseMultiSelectDeviceActivity.this.handleResource(listResource, new IAction() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity$2$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    BaseMultiSelectDeviceActivity.AnonymousClass2.this.lambda$onChanged$0((List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChanged$0(List list) {
            Floor floor = new Floor();
            floor.setFloorName(BaseMultiSelectDeviceActivity.this.getString(R.string.all_floor));
            floor.setFloorId(-1L);
            floor.setPlaceId(BaseMultiSelectDeviceActivity.this.placeId);
            list.add(0, floor);
            BaseMultiSelectDeviceActivity.this.floorList = list;
            BaseMultiSelectDeviceActivity.this.initFloorSpinner();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        Listing<Floor> floorList = Injection.repo().home().getFloorList(this, this.placeId);
        this.mRequest = floorList;
        floorList.data().observe(this, new AnonymousClass2());
        this.selectRoom.addSource(Transformations.switchMap(this.selectFloor, new Function1() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = BaseMultiSelectDeviceActivity.this.lambda$startObserve$0((Floor) obj);
                return lambda$startObserve$0;
            }
        }), new Observer() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseMultiSelectDeviceActivity.this.lambda$startObserve$2((Resource) obj);
            }
        });
        this.deviceList = Transformations.switchMap(this.selectRoom, new Function1<Room, LiveData<Resource<List<Device>>>>() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity.3
            @Override // kotlin.jvm.functions.Function1
            public LiveData<Resource<List<Device>>> invoke(Room room) {
                if (room == null) {
                    return ResourceEmptyLiveData.create();
                }
                List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(BaseMultiSelectDeviceActivity.this.placeId, BaseMultiSelectDeviceActivity.this.getCurRoom().getFloorId(), BaseMultiSelectDeviceActivity.this.getCurRoom().getRoomId());
                Collections.sort(deviceListByRoomIdFromDb, new Comparator<Device>(this) { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity.3.1
                    @Override // java.util.Comparator
                    public int compare(Device o1, Device o2) {
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
                mediatorLiveData.setValue(Resource.success(deviceListByRoomIdFromDb));
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
        handleResource(resource, new IAction() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseMultiSelectDeviceActivity.this.lambda$startObserve$1((List) obj);
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
        ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!BaseMultiSelectDeviceActivity.this.floorList.get(position).equals(BaseMultiSelectDeviceActivity.this.floorList)) {
                    BaseMultiSelectDeviceActivity baseMultiSelectDeviceActivity = BaseMultiSelectDeviceActivity.this;
                    baseMultiSelectDeviceActivity.setCurFloor(baseMultiSelectDeviceActivity.floorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        if (this.floorList.size() == 2) {
            deviceManagerSpinnerAdapter.setSelectPosition(1);
            ((ActSelect2Binding) this.mViewBinding).spinnerFloor.setSelection(1);
        } else {
            deviceManagerSpinnerAdapter.setSelectPosition(((ActSelect2Binding) this.mViewBinding).spinnerFloor.getSelectedItemPosition());
        }
    }

    private void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(this.roomList));
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!BaseMultiSelectDeviceActivity.this.roomList.get(position).equals(BaseMultiSelectDeviceActivity.this.selectRoom.getValue())) {
                    BaseMultiSelectDeviceActivity baseMultiSelectDeviceActivity = BaseMultiSelectDeviceActivity.this;
                    baseMultiSelectDeviceActivity.setCurRoom(baseMultiSelectDeviceActivity.roomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActSelect2Binding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActSelect2Binding) this.mViewBinding).spinnerRoom.getSelectedItemPosition());
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Device>>() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity.7
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Device>> emitter) throws Exception {
                ArrayList arrayList = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Device device : BaseMultiSelectDeviceActivity.this.allData) {
                        if (device.getName().toLowerCase().contains(keyword.toLowerCase())) {
                            arrayList.add(device);
                        }
                    }
                } else {
                    arrayList.addAll(BaseMultiSelectDeviceActivity.this.allData);
                }
                emitter.onNext(arrayList);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Device>>() { // from class: com.ltech.smarthome.base.BaseMultiSelectDeviceActivity.6
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Device> items) {
                BaseMultiSelectDeviceActivity.this.refreshData.setValue(items);
            }
        });
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        search(editable.toString());
    }
}