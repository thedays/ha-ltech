package com.ltech.smarthome.ui.replace;

import android.view.View;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import androidx.appcompat.widget.MySpinner;
import androidx.lifecycle.MediatorLiveData;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.device.base.BaseDeviceSetViewModel;
import com.ltech.smarthome.ui.home.DeviceManagerSpinnerAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActDeviceReplaceVM extends BaseDeviceSetViewModel {
    public long floorId;
    public long placeId;
    public long roomId;
    public List<Floor> mFloorList = new ArrayList();
    public List<Room> mRoomList = new ArrayList();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public MediatorLiveData<Room> selectRoom = new MediatorLiveData<>();
    private int selectFloorPosition = 0;
    private int selectRoomPosition = 0;

    public Floor checkFloor(List<Floor> floorList, long floorId) {
        if (floorId != -1) {
            for (int i = 0; i < floorList.size(); i++) {
                if (floorList.get(i).getFloorId() == floorId) {
                    this.selectFloorPosition = i;
                    return floorList.get(i);
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

    public Room checkRoom(List<Room> roomList, long roomId) {
        if (roomId != -1) {
            for (int i = 0; i < roomList.size(); i++) {
                if (roomList.get(i).getRoomId() == roomId) {
                    this.selectRoomPosition = i;
                    return roomList.get(i);
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

    public void initFloorList(long floorId) {
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(this.placeId);
        Floor floor = new Floor();
        floor.setFloorName(getContext().getString(R.string.all_floor));
        floor.setFloorId(-1L);
        floor.setPlaceId(this.placeId);
        floorListByPlaceId.add(0, floor);
        this.mFloorList = floorListByPlaceId;
        setCurFloor(checkFloor(floorListByPlaceId, floorId));
    }

    public void initRoomList(Floor floor, long roomId) {
        List<Room> roomListByFloorId = Injection.repo().home().getRoomListByFloorId(this.placeId, floor.getFloorId());
        if (this.mRoomList.size() == roomListByFloorId.size() + 1) {
            return;
        }
        Room room = new Room();
        room.setRoomName(getContext().getString(R.string.all_room));
        room.setRoomId(-1L);
        room.setFloorId(floor.getFloorId());
        room.setPlaceId(this.placeId);
        roomListByFloorId.add(0, room);
        this.mRoomList = roomListByFloorId;
        setCurRoom(checkRoom(roomListByFloorId, roomId));
    }

    public void initFloorSpinner(MySpinner spinnerFloor) {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(getContext(), new ArrayList(this.mFloorList));
        spinnerFloor.setDropDownWidth(ScreenUtils.getScreenWidth());
        spinnerFloor.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplaceVM.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!ActDeviceReplaceVM.this.mFloorList.get(position).equals(ActDeviceReplaceVM.this.selectFloor.getValue())) {
                    ActDeviceReplaceVM actDeviceReplaceVM = ActDeviceReplaceVM.this;
                    actDeviceReplaceVM.setCurFloor(actDeviceReplaceVM.mFloorList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        spinnerFloor.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        spinnerFloor.setSelection(this.selectFloorPosition);
    }

    public void initRoomSpinner(MySpinner spinnerRoom) {
        final DeviceManagerSpinnerAdapter deviceManagerSpinnerAdapter = new DeviceManagerSpinnerAdapter(getContext(), new ArrayList(this.mRoomList));
        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.ltech.smarthome.ui.replace.ActDeviceReplaceVM.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!ActDeviceReplaceVM.this.mRoomList.get(position).equals(ActDeviceReplaceVM.this.selectRoom.getValue())) {
                    ActDeviceReplaceVM actDeviceReplaceVM = ActDeviceReplaceVM.this;
                    actDeviceReplaceVM.setCurRoom(actDeviceReplaceVM.mRoomList.get(position));
                }
                deviceManagerSpinnerAdapter.setSelectPosition(position);
            }
        });
        spinnerRoom.setMaxHeight(deviceManagerSpinnerAdapter.getCount() > 6 ? SizeUtils.dp2px(300.0f) : 0);
        spinnerRoom.setDropDownWidth(ScreenUtils.getScreenWidth());
        spinnerRoom.setAdapter((SpinnerAdapter) deviceManagerSpinnerAdapter);
        spinnerRoom.setSelection(this.selectRoomPosition);
    }
}