package com.ltech.smarthome.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.databinding.ActSelectDivideBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public abstract class BaseSelectDeviceDivideActivity extends BaseSelectDivideListActivity<Device> {
    public LiveData<Resource<List<Device>>> deviceResult;
    protected Listing<Floor> mRequest;
    protected long placeId;
    public Listing<Place> placeInfoResult;
    protected String productId;
    public MutableLiveData<Place> placeMutableLiveData = new MutableLiveData<>();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();
    public MediatorLiveData<Device> deviceMediatorLiveData = new MediatorLiveData<>();
    public List<Floor> mFloorList = new ArrayList();
    public List<Room> mRoomList = new ArrayList();
    public List<Floor> mTotalFloorList = new ArrayList();
    public List<Room> mTotalRoomList = new ArrayList();

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_not_select_content;
    }

    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        setBackImage(R.mipmap.icon_back);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onViewCreated() {
        super.onViewCreated();
        this.placeId = getIntent().getLongExtra(Constants.PLACE_ID, -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        this.selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.base.BaseSelectDeviceDivideActivity.1
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(BaseSelectDeviceDivideActivity.this.placeId, floor.getFloorId());
                if (BaseSelectDeviceDivideActivity.this.mRoomList.size() == roomListByFloorId.size() + 1) {
                    return;
                }
                Room room = new Room();
                room.setRoomName(BaseSelectDeviceDivideActivity.this.getString(R.string.all_room));
                room.setRoomId(-1L);
                room.setFloorId(BaseSelectDeviceDivideActivity.this.selectFloor.getValue().getFloorId());
                room.setPlaceId(BaseSelectDeviceDivideActivity.this.placeId);
                roomListByFloorId.add(0, room);
                BaseSelectDeviceDivideActivity.this.mRoomList = roomListByFloorId;
                BaseSelectDeviceDivideActivity baseSelectDeviceDivideActivity = BaseSelectDeviceDivideActivity.this;
                baseSelectDeviceDivideActivity.setCurRoom(baseSelectDeviceDivideActivity.checkRoom(roomListByFloorId));
                BaseSelectDeviceDivideActivity.this.initRoomSpinner();
            }
        });
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(this.placeId);
        Floor floor = new Floor();
        floor.setFloorName(getString(R.string.all_floor));
        floor.setFloorId(-1L);
        floor.setPlaceId(this.placeId);
        floorListByPlaceId.add(0, floor);
        this.mFloorList = floorListByPlaceId;
        setCurFloor(checkFloor(floorListByPlaceId));
        initFloorSpinner();
        this.deviceResult = Transformations.switchMap(this.selectRoom, new Function1() { // from class: com.ltech.smarthome.base.BaseSelectDeviceDivideActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = BaseSelectDeviceDivideActivity.this.lambda$startObserve$0((Room) obj);
                return lambda$startObserve$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$0(Room room) {
        if (room == null) {
            return ResourceEmptyLiveData.create();
        }
        List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(this.placeId, getCurRoom().getFloorId(), getCurRoom().getRoomId());
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.setValue(Resource.success(deviceListByRoomIdFromDb));
        return mediatorLiveData;
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
        List<Floor> list = this.mFloorList;
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
        List<Room> list2 = this.mRoomList;
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

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.no_device));
    }

    private void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(this.mFloorList));
        ((ActSelectDivideBinding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActSelectDivideBinding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActSelectDivideBinding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseSelectDeviceDivideActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!BaseSelectDeviceDivideActivity.this.mFloorList.get(position).equals(BaseSelectDeviceDivideActivity.this.selectFloor.getValue())) {
                    BaseSelectDeviceDivideActivity baseSelectDeviceDivideActivity = BaseSelectDeviceDivideActivity.this;
                    baseSelectDeviceDivideActivity.setCurFloor(baseSelectDeviceDivideActivity.mFloorList.get(position));
                }
                if (position == 0) {
                    BaseSelectDeviceDivideActivity.this.mTotalFloorList.addAll(BaseSelectDeviceDivideActivity.this.mFloorList.subList(1, BaseSelectDeviceDivideActivity.this.mFloorList.size()));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActSelectDivideBinding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActSelectDivideBinding) this.mViewBinding).spinnerFloor.getSelectedItemPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(this.mRoomList));
        ((ActSelectDivideBinding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseSelectDeviceDivideActivity.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!BaseSelectDeviceDivideActivity.this.mRoomList.get(position).equals(BaseSelectDeviceDivideActivity.this.selectRoom.getValue())) {
                    BaseSelectDeviceDivideActivity baseSelectDeviceDivideActivity = BaseSelectDeviceDivideActivity.this;
                    baseSelectDeviceDivideActivity.setCurRoom(baseSelectDeviceDivideActivity.mRoomList.get(position));
                }
                if (position == 0) {
                    BaseSelectDeviceDivideActivity.this.mTotalRoomList.addAll(BaseSelectDeviceDivideActivity.this.mRoomList.subList(1, BaseSelectDeviceDivideActivity.this.mRoomList.size()));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActSelectDivideBinding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActSelectDivideBinding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActSelectDivideBinding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActSelectDivideBinding) this.mViewBinding).spinnerRoom.getSelectedItemPosition());
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

    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return super.provideLayoutId();
    }

    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity
    protected List<Device> getSelectList() {
        return new ArrayList();
    }

    @Override // com.ltech.smarthome.base.BaseSelectDivideListActivity
    protected List<Device> getNotSelectList() {
        return new ArrayList();
    }
}