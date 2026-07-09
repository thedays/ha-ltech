package com.ltech.smarthome.model.repo.ifun;

import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.net.response.device.ListDeviceResponse;
import io.reactivex.Observable;
import java.util.List;

/* loaded from: classes4.dex */
public interface IRole {
    Observable<List<Role>> getGroupFromNet(long placeId);

    Role getRoleById(long controlId, boolean group);

    Observable<List<Role>> getRoleList(long placeId, long floorId, long roomId);

    List<Role> getRoleListByRoomIdFromDb(long placeId, long floorId, long roomId);

    List<Device> setDeviceData(long placeId, ListDeviceResponse response);
}