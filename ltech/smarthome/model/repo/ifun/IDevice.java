package com.ltech.smarthome.model.repo.ifun;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import io.reactivex.Observable;
import java.util.List;

/* loaded from: classes4.dex */
public interface IDevice {
    void changeDeviceIcon(long deviceId, int imageIndex);

    Device getDevice(long placeId, long deviceId);

    Device getDeviceByDeviceId(long deviceId);

    Device getDeviceById(long id);

    Device getDeviceByMac(long placeId, String mac);

    List<Device> getDeviceByUnicastAddress(long place, int unicastAddress);

    LiveData<Device> getDeviceFromDb(long dbId);

    Device getDeviceFromDb(String iotProductKey, String iotDeviceName);

    Listing<Device> getDeviceList(LifecycleOwner owner, long placeId, long floorId, long roomId);

    List<Device> getDeviceListByPlaceId(long placeId);

    List<Device> getDeviceListByRoomIdFromDb(long floorId, long roomId);

    List<Device> getDeviceListByRoomIdFromDb(long place, long floorId, long roomId);

    LiveData<List<Device>> getDeviceListCache();

    List<Device> getDevicesByIds(List<Long> ids);

    Device getExistGateway();

    Observable<SuperPanelInfo> getGqProInfo(long deviceId);

    Device getOnlineGateway();

    Device getOnlineGateway(int address);

    List<Device> getOnlineGateways();

    List<Device> getSubDevice(long placeId, long deviceId);

    List<Device> getSubDeviceListFromDb(long place, long floorId, long roomId, long deviceId);

    Device getSuperPanel();

    Listing<SuperPanelInfo> getSuperPanelInfo(LifecycleOwner owner, long deviceId);

    SuperPanelInfo getSuperPanelInfoByDb(long deviceId);

    Listing<SuperPanelInfo> getSuperPanelKeyInfo(LifecycleOwner owner, long deviceId);

    boolean isDeviceNameExist(long place, String name);

    List<Device> queryDevicesByMacDeviceId(Long id);

    void removeDaliSubContentByDeviceId(long placeId, long deviceId);

    void removeDeviceByMac(long placeId, String mac);

    void removeDeviceFromDb(long dbId);

    void resetDeviceOnlineState(long placeId);

    void saveDevice(List<Device> devices);

    void saveDevice(Device... devices);

    void setSuperPanelDevice(long deviceId, List<Long> deviceIds);

    void setSuperPanelDeviceAndGroup(long deviceId, List<Long> deviceIds, List<Long> groupIds);

    void setSuperPanelGroup(long deviceId, List<Long> groupIds);

    void setSuperPanelKeyInfo(long deviceId, SuperPanelInfo.PanelKeyInfo keyInfo);

    void setSuperPanelScene(long deviceId, List<Long> sceneIds);

    void sortDevice(List<Device> devices);
}