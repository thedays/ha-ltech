package com.ltech.smarthome.base;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseDeviceManageVM;
import com.ltech.smarthome.databinding.ActDeviceManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import com.ltech.smarthome.utils.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public abstract class BaseDeviceManageActivity<V extends ActDeviceManageBinding, VM extends BaseDeviceManageVM> extends VMActivity<V, VM> implements TextWatcher {
    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    protected boolean filterDevice(Device device) {
        return true;
    }

    protected void getDevices() {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int layoutLoadId() {
        return R.id.rv_content;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_device_manage;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        ((BaseDeviceManageVM) this.mViewModel).placeId = getIntent().getLongExtra(Constants.PLACE_ID, 0L);
        setBackImage(R.mipmap.icon_back);
        ((ActDeviceManageBinding) this.mViewBinding).searchBar.searchEdtTxt.addTextChangedListener(this);
        ((ActDeviceManageBinding) this.mViewBinding).searchBar.ivClean.setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.base.BaseDeviceManageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((ActDeviceManageBinding) BaseDeviceManageActivity.this.mViewBinding).searchBar.searchEdtTxt.setText("");
            }
        });
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        handleData(Transformations.switchMap(((BaseDeviceManageVM) this.mViewModel).selectRoom, new Function1() { // from class: com.ltech.smarthome.base.BaseDeviceManageActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LiveData lambda$startObserve$0;
                lambda$startObserve$0 = BaseDeviceManageActivity.this.lambda$startObserve$0((Room) obj);
                return lambda$startObserve$0;
            }
        }), new IAction() { // from class: com.ltech.smarthome.base.BaseDeviceManageActivity$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                BaseDeviceManageActivity.this.lambda$startObserve$1((List) obj);
            }
        });
        ((BaseDeviceManageVM) this.mViewModel).selectFloor.observe(this, new Observer<Floor>() { // from class: com.ltech.smarthome.base.BaseDeviceManageActivity.3
            @Override // androidx.lifecycle.Observer
            public void onChanged(Floor floor) {
                List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).placeId, floor.getFloorId());
                if (((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).mRoomList.size() == roomListByFloorId.size() + 1) {
                    return;
                }
                Room room = new Room();
                room.setRoomName(BaseDeviceManageActivity.this.getString(R.string.all_room));
                room.setRoomId(-1L);
                room.setFloorId(((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).selectFloor.getValue().getFloorId());
                room.setPlaceId(((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).placeId);
                roomListByFloorId.add(0, room);
                ((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).mRoomList = roomListByFloorId;
                ((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).setCurRoom(((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).checkRoom(roomListByFloorId));
                BaseDeviceManageActivity.this.initRoomSpinner();
            }
        });
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(((BaseDeviceManageVM) this.mViewModel).placeId);
        Floor floor = new Floor();
        floor.setFloorName(getString(R.string.all_floor));
        floor.setFloorId(-1L);
        floor.setPlaceId(((BaseDeviceManageVM) this.mViewModel).placeId);
        floorListByPlaceId.add(0, floor);
        ((BaseDeviceManageVM) this.mViewModel).mFloorList = floorListByPlaceId;
        ((BaseDeviceManageVM) this.mViewModel).setCurFloor(((BaseDeviceManageVM) this.mViewModel).checkFloor(floorListByPlaceId));
        initFloorSpinner();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$startObserve$0(Room room) {
        if (room == null) {
            return ResourceEmptyLiveData.create();
        }
        List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(((BaseDeviceManageVM) this.mViewModel).placeId, ((BaseDeviceManageVM) this.mViewModel).getCurRoom().getFloorId(), ((BaseDeviceManageVM) this.mViewModel).getCurRoom().getRoomId());
        Collections.sort(deviceListByRoomIdFromDb, new Comparator<Device>(this) { // from class: com.ltech.smarthome.base.BaseDeviceManageActivity.2
            @Override // java.util.Comparator
            public int compare(Device o1, Device o2) {
                if (o1.getIndex() > o2.getIndex()) {
                    return 1;
                }
                if (o1.getIndex() < o2.getIndex()) {
                    return -1;
                }
                if (o1.getCreatetime() == null || o2.getCreatetime() == null) {
                    return 0;
                }
                return o1.getCreatetime().compareTo(o2.getCreatetime());
            }
        });
        MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.setValue(Resource.success(deviceListByRoomIdFromDb));
        return mediatorLiveData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ((BaseDeviceManageVM) this.mViewModel).mDeviceList.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Device device = (Device) it.next();
            if (filterDevice(device)) {
                ((BaseDeviceManageVM) this.mViewModel).mDeviceList.add(device);
            }
        }
        ((BaseDeviceManageVM) this.mViewModel).allData = new ArrayList();
        ((BaseDeviceManageVM) this.mViewModel).allData.addAll(((BaseDeviceManageVM) this.mViewModel).mDeviceList);
        ((ActDeviceManageBinding) this.mViewBinding).rvContent.getAdapter().notifyDataSetChanged();
        ((BaseDeviceManageVM) this.mViewModel).selectCountLiveData.setValue(Integer.valueOf(((BaseDeviceManageVM) this.mViewModel).selectDeviceIdList.size()));
        controlShowEmpty();
        getDevices();
        if (this.mViewBinding == 0 || ((ActDeviceManageBinding) this.mViewBinding).searchBar.searchEdtTxt.getText() == null) {
            return;
        }
        String obj = ((ActDeviceManageBinding) this.mViewBinding).searchBar.searchEdtTxt.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            return;
        }
        ((BaseDeviceManageVM) this.mViewModel).search(obj);
    }

    private void initFloorSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((BaseDeviceManageVM) this.mViewModel).mFloorList));
        ((ActDeviceManageBinding) this.mViewBinding).spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActDeviceManageBinding) this.mViewBinding).spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        ((ActDeviceManageBinding) this.mViewBinding).spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseDeviceManageActivity.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).mFloorList.get(position).equals(((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).selectFloor.getValue())) {
                    ((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).setCurFloor(((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).mFloorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActDeviceManageBinding) this.mViewBinding).spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActDeviceManageBinding) this.mViewBinding).spinnerFloor.getSelectedItemPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRoomSpinner() {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(this, new ArrayList(((BaseDeviceManageVM) this.mViewModel).mRoomList));
        ((ActDeviceManageBinding) this.mViewBinding).spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.base.BaseDeviceManageActivity.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).mRoomList.get(position).equals(((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).selectRoom.getValue())) {
                    ((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).setCurRoom(((BaseDeviceManageVM) BaseDeviceManageActivity.this.mViewModel).mRoomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        ((ActDeviceManageBinding) this.mViewBinding).spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        ((ActDeviceManageBinding) this.mViewBinding).spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        ((ActDeviceManageBinding) this.mViewBinding).spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        deviceManagerSpinnerAdapter.setSelectPosition(((ActDeviceManageBinding) this.mViewBinding).spinnerRoom.getSelectedItemPosition());
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        ((BaseDeviceManageVM) this.mViewModel).placeInfoResult.retry();
    }

    protected void controlShowEmpty() {
        if (((BaseDeviceManageVM) this.mViewModel).mDeviceList.isEmpty() || Injection.repo().device().getSuperPanel() == null) {
            showEmpty();
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        ((BaseDeviceManageVM) this.mViewModel).search(editable.toString());
    }
}