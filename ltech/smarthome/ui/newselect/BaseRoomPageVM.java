package com.ltech.smarthome.ui.newselect;

import android.app.Activity;
import android.text.TextUtils;
import androidx.lifecycle.MediatorLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.ui.control.ActHome;
import java.util.List;

/* loaded from: classes4.dex */
public class BaseRoomPageVM extends BaseViewModel {
    public int floorPosition;
    public List<Floor> floors;
    public long orgFloorId;
    public long orgRoomId;
    public int roomPosition;
    public MediatorLiveData<Long> placeId = new MediatorLiveData<>();
    public MediatorLiveData<Floor> selectFloor = new MediatorLiveData<>();
    public boolean isFirst = true;
    public boolean needFloorRoomMemory = true;

    public void initFloorList(long placeId) {
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(placeId);
        this.floors = floorListByPlaceId;
        if (floorListByPlaceId.size() == 0) {
            Floor floor = new Floor();
            floor.setFloorName(StringUtils.getString(R.string.all_floor));
            floor.setFloorId(-1L);
            floor.setPlaceId(placeId);
            this.floors.add(0, floor);
        }
    }

    public Floor checkFloor(List<Floor> floorList, String constant) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity instanceof ActHome) {
            ActHome actHome = (ActHome) topActivity;
            for (Floor floor : floorList) {
                if (floor.getFloorId() == actHome.getSelectFloorId()) {
                    return floor;
                }
            }
        }
        if (this.needFloorRoomMemory && this.isFirst && !TextUtils.isEmpty(constant)) {
            long j = this.orgFloorId;
            if (j != -2) {
                for (int i = 0; i < floorList.size(); i++) {
                    Floor floor2 = floorList.get(i);
                    if (floor2.getFloorId() == j) {
                        this.floorPosition = i;
                        return floor2;
                    }
                }
            }
        }
        if (this.floorPosition < floorList.size()) {
            return floorList.get(this.isFirst ? 0 : this.floorPosition);
        }
        return floorList.get(0);
    }

    public Floor checkFloor(List<Floor> floorList, String constant, long floorId) {
        if (this.needFloorRoomMemory && this.isFirst && !TextUtils.isEmpty(constant)) {
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
        int floorIndex = getFloorIndex(floorList, floorId);
        if (this.floorPosition < floorList.size()) {
            if (this.isFirst) {
                this.floorPosition = floorIndex;
                return floorList.get(floorIndex);
            }
            return floorList.get(this.floorPosition);
        }
        return floorList.get(floorIndex);
    }

    public int getFloorIndex(List<Floor> floorList, long floorId) {
        for (int i = 0; i < floorList.size(); i++) {
            if (floorList.get(i).getFloorId() == floorId) {
                return i;
            }
        }
        return 0;
    }

    public int getRoomIndex(List<Room> roomList, long roomId) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getRoomId() == roomId) {
                return i;
            }
        }
        return 0;
    }

    public long getCurPlaceId() {
        return this.placeId.getValue().longValue();
    }

    public boolean setCurFloor(Floor floor) {
        this.selectFloor.setValue(floor);
        Activity topActivity = ActivityUtils.getTopActivity();
        if (!(topActivity instanceof ActHome)) {
            return true;
        }
        ((ActHome) topActivity).setSelectFloorId(floor.getFloorId());
        return true;
    }

    public Floor getCurFloor() {
        return this.selectFloor.getValue();
    }

    public static class RoomPageItem {
        protected long floorId;
        protected long placeId;
        protected long roomId;
        protected int roomIndex;
        protected String roomName;

        public long getRoomId() {
            return this.roomId;
        }

        public int getRoomIndex() {
            return this.roomIndex;
        }

        public long getPlaceId() {
            return this.placeId;
        }

        public String getRoomName() {
            return this.roomName;
        }

        public long getFloorId() {
            return this.floorId;
        }
    }
}