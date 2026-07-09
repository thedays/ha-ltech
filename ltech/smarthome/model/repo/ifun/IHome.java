package com.ltech.smarthome.model.repo.ifun;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.net.response.place.AppTokenResponse;
import java.util.List;

/* loaded from: classes4.dex */
public interface IHome {
    boolean checkPlaceList();

    AppTokenResponse getAppToken(long placeId);

    Floor getFloor(long floorId);

    Listing<Floor> getFloorList(LifecycleOwner owner, long placeId);

    List<Floor> getFloorListByPlaceId(long placeId);

    Listing<Place> getPlace(LifecycleOwner owner, long placeId);

    Listing<Place> getPlaceDetailInfo(LifecycleOwner owner, long placeId);

    Listing<Place> getPlaceList(LifecycleOwner owner);

    Listing<PlaceUser> getPlaceUserList(LifecycleOwner owner, long placeId, long placeUserId);

    Room getRoom(long roomId);

    Listing<Room> getRoomList(LifecycleOwner owner, long placeId, long floorId);

    List<Room> getRoomList(long placeId);

    List<Room> getRoomListByFloorId(long placeId, long floorId);

    List<Room> getRoomListByPlaceId(long placeId, long floorId);

    String getRoomName(long floorId, long roomId);

    Place getSelPlace();

    LiveData<Place> getSelectPlace();

    void removeFloor(long floorId);

    void removePlace(long placeId);

    void removeRoom(long roomId);

    void saveFloor(Floor floor);

    void savePlace(Place place);

    void saveRoom(Room room);

    void setSelectPlace(Place place);

    void sortFloor(List<Floor> floors);

    void sortRoom(List<Room> rooms);

    void updateAppToken(Object appTokenResponse);

    void updateFloorName(long floorId, String name);

    void updatePlaceIvIndex(long placeId, int ivIndex);

    void updatePlaceLocation(long placeId, String location, double latitude, double longitude);

    void updatePlaceName(long placeId, String placeName);

    void updateRoomName(long roomId, String roomName);
}