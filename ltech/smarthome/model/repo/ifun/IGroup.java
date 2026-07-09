package com.ltech.smarthome.model.repo.ifun;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Group;
import java.util.List;

/* loaded from: classes4.dex */
public interface IGroup {
    void checkCodeLibrary(LifecycleOwner owner, long groupId);

    List<Group> getGroupByDeviceUnicastAddress(long place, long unicastAddress);

    Group getGroupByGroupId(long id);

    Group getGroupById(long id);

    LiveData<Group> getGroupFromDb(long id);

    Listing<Group> getGroupList(LifecycleOwner owner, long placeId);

    List<Group> getGroupListByDeviceId(long placeId, long deviceId);

    List<Group> getGroupListByPlaceId(long placeId);

    List<Group> getGroupListByRoomIdFromDb(long placeId, long floorId, long roomId);

    List<Group> getGroupsByIds(List<Long> ids);

    List<Group> getSubGroup(long placeId, long groupId);

    List<Group> getSubGroupListFromDb(long placeId, long floorId, long roomId, long deviceId);

    List<Group> queryGroupByUnicastAddress(long place, long unicastAddress);

    void removeGroupFromDb(long id);

    void removeSubGroupFromDb(long placeId, long groupId);

    void saveGroup(Group group);

    /* renamed from: saveGroup2Db */
    long lambda$saveGroup$1(Group group);

    void sortGroup(List<Group> groups);
}