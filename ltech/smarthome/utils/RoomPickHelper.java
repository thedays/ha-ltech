package com.ltech.smarthome.utils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Room;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class RoomPickHelper {
    private LinkedHashMap<Floor, List<Room>> mLinkedHashMap;
    private long selectFloorId;
    private String selectFloorName;
    private String selectFullRoomName;
    private long selectPlaceId;
    private long selectRoomId;
    private String selectRoomName;
    private List<Floor> mFloorList = new ArrayList();
    private List<Room> mRoomList = new ArrayList();
    private boolean getFloorSuccess = false;
    private boolean getRoomSuccess = false;
    private int selectFloorPosition = 0;
    private int selectRoomPosition = 0;
    private MutableLiveData<Boolean> canSetRoom = new MutableLiveData<>(false);
    private SingleLiveEvent<Void> selectRoomEvent = new SingleLiveEvent<>();
    private boolean isObserving = false;

    public void startObserve(LifecycleOwner owner, long placeId, long roomId) {
        if (this.isObserving) {
            return;
        }
        this.isObserving = true;
        this.selectPlaceId = placeId;
        this.selectRoomId = roomId;
        List<Room> roomList = Injection.repo().home().getRoomList(placeId);
        List<Floor> floorListByPlaceId = Injection.repo().home().getFloorListByPlaceId(placeId);
        if (roomList != null) {
            this.mRoomList.clear();
            this.mRoomList.addAll(roomList);
            this.getRoomSuccess = true;
            if (roomList.isEmpty()) {
                this.selectRoomId = 0L;
            }
        }
        if (floorListByPlaceId != null) {
            this.mFloorList.clear();
            this.mFloorList.addAll(floorListByPlaceId);
            this.getFloorSuccess = true;
        }
        checkData();
    }

    public void resetObserve() {
        this.isObserving = false;
    }

    public void checkData() {
        if (this.getFloorSuccess && this.getRoomSuccess) {
            LinkedHashMap<Floor, List<Room>> linkedHashMap = this.mLinkedHashMap;
            if (linkedHashMap == null) {
                this.mLinkedHashMap = new LinkedHashMap<>();
            } else {
                linkedHashMap.clear();
            }
            for (Floor floor : this.mFloorList) {
                ArrayList arrayList = new ArrayList();
                for (Room room : this.mRoomList) {
                    if (room.getFloorId() == floor.getFloorId()) {
                        arrayList.add(room);
                        if (room.getRoomId() == this.selectRoomId) {
                            this.selectFloorId = floor.getFloorId();
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.mLinkedHashMap.put(floor, arrayList);
                }
            }
            this.getFloorSuccess = false;
            this.getRoomSuccess = false;
            if (this.mLinkedHashMap.isEmpty()) {
                this.canSetRoom.setValue(false);
                return;
            }
            this.canSetRoom.setValue(true);
            int size = this.mLinkedHashMap.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (this.mFloorList.get(i).getFloorId() == this.selectFloorId) {
                    this.selectFloorPosition = i;
                    List<Room> list = this.mLinkedHashMap.get(this.mFloorList.get(i));
                    if (list != null) {
                        int size2 = list.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            if (list.get(i2).getRoomId() == this.selectRoomId) {
                                this.selectRoomPosition = i2;
                            }
                        }
                    }
                } else {
                    i++;
                }
            }
            setSelectPosition(this.selectFloorPosition, this.selectRoomPosition);
            this.selectRoomEvent.call();
        }
    }

    public List<String> getCurrentFloorNames() {
        ArrayList arrayList = new ArrayList();
        Iterator<Floor> it = this.mFloorList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getFloorName());
        }
        return arrayList;
    }

    public List<String> getRoomNames(int floorPosition) {
        ArrayList arrayList = new ArrayList();
        List<Floor> list = this.mFloorList;
        if (list != null && floorPosition < list.size() && this.mLinkedHashMap != null) {
            List<Room> list2 = this.mLinkedHashMap.get(this.mFloorList.get(floorPosition));
            if (list2 != null) {
                Iterator<Room> it = list2.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getRoomName());
                }
            }
        }
        return arrayList;
    }

    public List<String> getCurrentRoomNames() {
        return getRoomNames(this.selectFloorPosition);
    }

    public int getSelectFloorPosition() {
        return this.selectFloorPosition;
    }

    public int getSelectRoomPosition() {
        return this.selectRoomPosition;
    }

    public String getSelectFullRoomName() {
        return this.selectFullRoomName;
    }

    public long getSelectRoomId() {
        return this.selectRoomId;
    }

    public long getSelectFloorId() {
        return this.selectFloorId;
    }

    public long getSelectPlaceId() {
        return this.selectPlaceId;
    }

    public String getSelectRoomName() {
        return this.selectRoomName;
    }

    public void setSelectRoomName(String selectRoomName) {
        this.selectRoomName = selectRoomName;
    }

    public String getSelectFloorName() {
        return this.selectFloorName;
    }

    public void setSelectFloorName(String selectFloorName) {
        this.selectFloorName = selectFloorName;
    }

    public void setSelectPosition(int floorPosition, int roomPosition) {
        String str;
        LinkedHashMap<Floor, List<Room>> linkedHashMap = this.mLinkedHashMap;
        if (linkedHashMap == null || linkedHashMap.isEmpty()) {
            return;
        }
        this.selectFloorPosition = floorPosition;
        this.selectRoomPosition = roomPosition;
        if (floorPosition > this.mFloorList.size() - 1) {
            floorPosition = 0;
        }
        List<Room> list = this.mLinkedHashMap.get(this.mFloorList.get(floorPosition));
        this.selectFloorId = this.mFloorList.get(floorPosition).getFloorId();
        if (list != null) {
            if (roomPosition > list.size() - 1) {
                roomPosition = 0;
            }
            this.selectRoomId = list.get(roomPosition).getRoomId();
            str = list.get(roomPosition).getRoomName();
        } else {
            this.selectRoomId = 0L;
            str = "";
        }
        this.selectFullRoomName = this.mFloorList.get(floorPosition).getFloorName() + str;
        this.selectFloorName = this.mFloorList.get(floorPosition).getFloorName();
        this.selectRoomName = str;
    }

    public SingleLiveEvent<Void> getSelectRoomEvent() {
        return this.selectRoomEvent;
    }

    public MutableLiveData<Boolean> getCanSetRoom() {
        return this.canSetRoom;
    }

    public boolean canSetRoom() {
        return this.canSetRoom != null && Boolean.TRUE.equals(this.canSetRoom.getValue());
    }

    public void setCurrentFloorIdPos(long selectFloorId) {
        if (selectFloorId > 0 && this.mFloorList != null) {
            for (int i = 0; i < this.mFloorList.size(); i++) {
                if (selectFloorId == this.mFloorList.get(i).getFloorId()) {
                    this.selectFloorPosition = i;
                }
            }
        }
    }

    public void setCurrentRoomIdPos(long selectFloorId, long selectRoomId) {
        if (selectFloorId <= 0 || selectRoomId <= 0 || this.mFloorList == null || this.mLinkedHashMap == null) {
            return;
        }
        List<Room> arrayList = new ArrayList<>();
        Iterator<Floor> it = this.mFloorList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Floor next = it.next();
            if (next.getFloorId() == selectFloorId) {
                arrayList = this.mLinkedHashMap.get(next);
                break;
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (selectRoomId == arrayList.get(i).getRoomId()) {
                this.selectRoomPosition = i;
            }
        }
    }
}