package com.ltech.smarthome.model;

import com.ltech.smarthome.model.bean.Automation;
import com.ltech.smarthome.model.bean.Automation_;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Device_;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Floor_;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Group_;
import com.ltech.smarthome.model.bean.McuVersion;
import com.ltech.smarthome.model.bean.McuVersion_;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.model.bean.ModeContent_;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.model.bean.PlaceUser_;
import com.ltech.smarthome.model.bean.Place_;
import com.ltech.smarthome.model.bean.PlayListInfo;
import com.ltech.smarthome.model.bean.PlayListInfo_;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Room_;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.Scene_;
import com.ltech.smarthome.model.bean.SongInfo;
import com.ltech.smarthome.model.bean.SongInfo_;
import com.ltech.smarthome.model.bean.SuperPanelInfo;
import com.ltech.smarthome.model.bean.SuperPanelInfo_;
import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.QueryBuilder;
import java.util.List;

/* loaded from: classes4.dex */
public class BoxBuilderFactory {
    private final BoxStore mBoxStore;

    public BoxBuilderFactory(BoxStore boxStore) {
        this.mBoxStore = boxStore;
    }

    public QueryBuilder<Place> queryPlaceList(long userId) {
        return this.mBoxStore.boxFor(Place.class).query().equal(Place_.currentUserId, userId);
    }

    public QueryBuilder<Place> queryPlace(long userId, long placeId) {
        return this.mBoxStore.boxFor(Place.class).query().equal(Place_.placeId, placeId).equal(Place_.currentUserId, userId);
    }

    public QueryBuilder<Floor> queryFloorList(long userId, long placeId) {
        return this.mBoxStore.boxFor(Floor.class).query().equal(Floor_.placeId, placeId).equal(Floor_.userId, userId).order(Floor_.index);
    }

    public QueryBuilder<Floor> queryFloor(long userId, long floorId) {
        return this.mBoxStore.boxFor(Floor.class).query().equal(Floor_.floorId, floorId).equal(Floor_.userId, userId);
    }

    public QueryBuilder<Room> queryRoomList(long userId, long placeId, long floorId) {
        QueryBuilder<Room> order = this.mBoxStore.boxFor(Room.class).query().equal(Room_.placeId, placeId).equal(Room_.userId, userId).order(Room_.index);
        if (floorId >= 0) {
            order.equal(Room_.floorId, floorId);
        }
        return order;
    }

    public QueryBuilder<Room> queryRoom(long userId, long roomId) {
        return this.mBoxStore.boxFor(Room.class).query().equal(Room_.roomId, roomId).equal(Room_.userId, userId);
    }

    public QueryBuilder<Device> queryDeviceList(long userId, long placeId, long floorId, long roomId) {
        QueryBuilder<Device> equal = this.mBoxStore.boxFor(Device.class).query().order(Device_.index).equal(Device_.userId, userId);
        if (placeId > 0) {
            equal.equal(Device_.placeId, placeId);
        }
        if (floorId > 0) {
            equal.equal(Device_.floorId, floorId);
        } else if (floorId == 0) {
            equal.equal(Device_.floorId, 0L).or().equal(Device_.roomId, 0L);
        }
        if (roomId > 0) {
            equal.equal(Device_.roomId, roomId);
        }
        return equal;
    }

    public QueryBuilder<Device> querySubDeviceList(long userId, long placeId, long floorId, long roomId, long deviceId) {
        QueryBuilder<Device> equal = this.mBoxStore.boxFor(Device.class).query().order(Device_.index).equal(Device_.userId, userId).equal(Device_.macdeviceid, deviceId);
        if (placeId > 0) {
            equal.equal(Device_.placeId, placeId);
        }
        if (floorId > 0) {
            equal.equal(Device_.floorId, floorId);
        } else if (floorId == 0) {
            equal.equal(Device_.floorId, 0L).or().equal(Device_.roomId, 0L);
        }
        if (roomId > 0) {
            equal.equal(Device_.roomId, roomId);
        }
        return equal;
    }

    public QueryBuilder<Device> queryDeviceById(long userId, long id) {
        return this.mBoxStore.boxFor(Device.class).query().equal(Device_.id, id).equal(Device_.userId, userId);
    }

    public QueryBuilder<Device> queryDevicesByIds(long userId, List<Long> ids) {
        long[] jArr = new long[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            jArr[i] = ids.get(i).longValue();
        }
        return this.mBoxStore.boxFor(Device.class).query().in(Device_.deviceId, jArr).equal(Device_.userId, userId);
    }

    public QueryBuilder<Device> queryDevicesByMacDeviceId(long userId, long id) {
        return this.mBoxStore.boxFor(Device.class).query().equal(Device_.macdeviceid, id).equal(Device_.userId, userId);
    }

    public QueryBuilder<Device> queryDeviceByDeviceId(long userId, long deviceId) {
        return this.mBoxStore.boxFor(Device.class).query().equal(Device_.deviceId, deviceId).equal(Device_.userId, userId);
    }

    public QueryBuilder<Device> queryDeviceByUnicastAddress(long userId, long place, long unicastAddress) {
        return this.mBoxStore.boxFor(Device.class).query().equal(Device_.placeId, place).equal(Device_.userId, userId).equal(Device_.unicastAddress, unicastAddress);
    }

    public QueryBuilder<Device> queryDeviceByMac(long userId, long placeId, String mac) {
        return this.mBoxStore.boxFor(Device.class).query().equal(Device_.placeId, placeId).equal(Device_.wifiMac, mac, QueryBuilder.StringOrder.CASE_SENSITIVE).equal(Device_.userId, userId);
    }

    public QueryBuilder<Device> queryDeviceByName(long userId, long placeId, String name) {
        return this.mBoxStore.boxFor(Device.class).query().equal(Device_.placeId, placeId).equal(Device_.deviceName, name, QueryBuilder.StringOrder.CASE_SENSITIVE).equal(Device_.userId, userId);
    }

    public QueryBuilder<Device> queryDeviceByPlace(long userId, long placeId, long deviceId) {
        return this.mBoxStore.boxFor(Device.class).query().equal(Device_.placeId, placeId).equal(Device_.deviceId, deviceId).equal(Device_.userId, userId);
    }

    public QueryBuilder<PlaceUser> queryPlaceUserList(long userId, long placeId, long placeUserId) {
        QueryBuilder<PlaceUser> equal = this.mBoxStore.boxFor(PlaceUser.class).query().equal(PlaceUser_.placeId, placeId).equal(PlaceUser_.currentUserId, userId);
        if (placeUserId > 0) {
            equal.equal(PlaceUser_.placeUserId, placeUserId);
        }
        return equal;
    }

    public QueryBuilder<Scene> querySceneList(long userId, long placeId, int sceneType) {
        return this.mBoxStore.boxFor(Scene.class).query().equal(Scene_.placeId, placeId).equal(Scene_.userId, userId).equal(Scene_.sceneType, sceneType).order(Scene_.sceneIndex);
    }

    public QueryBuilder<Scene> querySceneList(long userId, long placeId) {
        return this.mBoxStore.boxFor(Scene.class).query().equal(Scene_.placeId, placeId).equal(Scene_.userId, userId).order(Scene_.sceneIndex);
    }

    public QueryBuilder<Scene> querySceneList(long userId, long placeId, boolean common) {
        return this.mBoxStore.boxFor(Scene.class).query().equal(Scene_.placeId, placeId).equal(Scene_.userId, userId).equal(Scene_.common, common).order(Scene_.sceneIndex);
    }

    public QueryBuilder<Scene> querySceneList(long userId, long placeId, long floorId, long roomId, boolean... common) {
        QueryBuilder<Scene> order = this.mBoxStore.boxFor(Scene.class).query().equal(Scene_.userId, userId).order(Scene_.sceneIndex);
        if (placeId > 0) {
            order.equal(Scene_.placeId, placeId);
        }
        if (floorId > 0) {
            order.equal(Scene_.floorId, floorId);
        } else if (floorId == 0) {
            order.equal(Scene_.floorId, 0L).or().equal(Scene_.roomId, 0L);
        }
        if (roomId > 0) {
            order.equal(Scene_.roomId, roomId);
        }
        if (common.length > 0 && common[0]) {
            order.equal(Scene_.common, true);
        }
        return order;
    }

    public QueryBuilder<Scene> querySceneList(long userId, long placeId, long floorId, long roomId, long deviceId) {
        QueryBuilder<Scene> order = this.mBoxStore.boxFor(Scene.class).query().equal(Scene_.placeId, placeId).equal(Scene_.userId, userId).equal(Scene_.macdeviceid, deviceId).order(Scene_.sceneIndex);
        if (floorId > 0) {
            order.equal(Scene_.floorId, floorId);
        } else if (floorId == 0) {
            order.equal(Scene_.floorId, 0L).or().equal(Scene_.roomId, 0L);
        }
        if (roomId > 0) {
            order.equal(Scene_.roomId, roomId);
        }
        return order;
    }

    public QueryBuilder<Scene> queryScene(long userId, long sceneId) {
        return this.mBoxStore.boxFor(Scene.class).query().equal(Scene_.sceneId, sceneId).equal(Scene_.userId, userId);
    }

    public QueryBuilder<Scene> queryLocalSceneByNum(long userId, long num) {
        return this.mBoxStore.boxFor(Scene.class).query().equal(Scene_.sceneNum, num).equal(Scene_.sceneType, 2L).equal(Scene_.userId, userId);
    }

    public QueryBuilder<Scene> querySceneByName(long userId, long placeId, String name) {
        return this.mBoxStore.boxFor(Scene.class).query().equal(Scene_.placeId, placeId).equal(Scene_.userId, userId).equal(Scene_.sceneName, name, QueryBuilder.StringOrder.CASE_SENSITIVE);
    }

    public QueryBuilder<Automation> queryAutomationList(long userId, long placeId, int automationType) {
        return this.mBoxStore.boxFor(Automation.class).query().equal(Automation_.placeId, placeId).equal(Automation_.currentUserId, userId).equal(Automation_.automationType, automationType).order(Automation_.index);
    }

    public QueryBuilder<Automation> queryAutomationList(long userId, long placeId) {
        return this.mBoxStore.boxFor(Automation.class).query().equal(Automation_.placeId, placeId).equal(Automation_.currentUserId, userId).order(Automation_.index);
    }

    public QueryBuilder<Automation> queryAutomation(long userId, long automationId) {
        return this.mBoxStore.boxFor(Automation.class).query().equal(Automation_.automationId, automationId).equal(Automation_.currentUserId, userId);
    }

    public QueryBuilder<Automation> queryAutomationByName(long userId, long placeId, String name) {
        return this.mBoxStore.boxFor(Automation.class).query().equal(Automation_.placeId, placeId).equal(Automation_.currentUserId, userId).equal(Automation_.name, name, QueryBuilder.StringOrder.CASE_SENSITIVE);
    }

    public QueryBuilder<Group> queryGroupList(long userId, long placeId) {
        return this.mBoxStore.boxFor(Group.class).query().equal(Group_.placeId, placeId).equal(Group_.userId, userId).order(Group_.groupIndex);
    }

    public QueryBuilder<Group> queryGroupListByDeviceId(long userId, long placeId, long deviceId) {
        return this.mBoxStore.boxFor(Group.class).query().equal(Group_.placeId, placeId).equal(Group_.userId, userId).contains(Group_.deviceIds, deviceId + "", QueryBuilder.StringOrder.CASE_SENSITIVE).order(Group_.groupIndex);
    }

    public QueryBuilder<Group> queryGroupList(long userId, long placeId, long floorId, long roomId) {
        QueryBuilder<Group> equal = this.mBoxStore.boxFor(Group.class).query().order(Group_.groupIndex).equal(Group_.userId, userId);
        if (placeId > 0) {
            equal.equal(Group_.placeId, placeId);
        }
        if (floorId > 0) {
            equal.equal(Group_.floorId, floorId);
        } else if (floorId == 0) {
            equal.equal(Group_.floorId, 0L).or().equal(Group_.roomId, 0L);
        }
        if (roomId > 0) {
            equal.equal(Group_.roomId, roomId);
        }
        return equal;
    }

    public QueryBuilder<Group> querySubGroupList(long userId, long placeId, long floorId, long roomId, long deviceId) {
        QueryBuilder<Group> equal = this.mBoxStore.boxFor(Group.class).query().order(Group_.groupIndex).equal(Group_.userId, userId).equal(Group_.macdeviceid, deviceId);
        if (placeId > 0) {
            equal.equal(Group_.placeId, placeId);
        }
        if (floorId > 0) {
            equal.equal(Group_.floorId, floorId);
        } else if (floorId == 0) {
            equal.equal(Group_.floorId, 0L).or().equal(Group_.roomId, 0L);
        }
        if (roomId > 0) {
            equal.equal(Group_.roomId, roomId);
        }
        return equal;
    }

    public QueryBuilder<Group> queryGroup(long userId, long id) {
        return this.mBoxStore.boxFor(Group.class).query().equal(Group_.id, id).equal(Group_.userId, userId);
    }

    public QueryBuilder<Group> queryGroupByDeviceUnicastAddress(long userId, long place, long unicastAddress) {
        return this.mBoxStore.boxFor(Group.class).query().equal(Group_.placeId, place).equal(Group_.userId, userId).equal(Group_.firstDevUniAddr, unicastAddress);
    }

    public QueryBuilder<Group> queryGroupByUnicastAddress(long userId, long place, long unicastAddress) {
        return this.mBoxStore.boxFor(Group.class).query().equal(Group_.placeId, place).equal(Group_.userId, userId).equal(Group_.groupAddress, unicastAddress);
    }

    public QueryBuilder<Group> queryGroupByGroupId(long userId, long groupId) {
        return this.mBoxStore.boxFor(Group.class).query().equal(Group_.groupId, groupId).equal(Group_.userId, userId);
    }

    public QueryBuilder<SuperPanelInfo> querySuperPanelInfo(long userId, long deviceId) {
        return this.mBoxStore.boxFor(SuperPanelInfo.class).query().equal(SuperPanelInfo_.deviceId, deviceId).equal(SuperPanelInfo_.userId, userId);
    }

    public QueryBuilder<SongInfo> querySongInfo(long songId) {
        return this.mBoxStore.boxFor(SongInfo.class).query().equal(SongInfo_.num, songId);
    }

    public QueryBuilder<SongInfo> querySongsByIds(List<Integer> ids) {
        int[] iArr = new int[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            iArr[i] = ids.get(i).intValue();
        }
        return this.mBoxStore.boxFor(SongInfo.class).query().in((Property) SongInfo_.num, iArr);
    }

    public QueryBuilder<SongInfo> querySongList(long deviceId) {
        return this.mBoxStore.boxFor(SongInfo.class).query().equal(SongInfo_.deviceId, deviceId);
    }

    public QueryBuilder<SongInfo> queryActivSongList(long deviceId) {
        return this.mBoxStore.boxFor(SongInfo.class).query().equal(SongInfo_.deviceId, deviceId).equal(SongInfo_.state, 0L);
    }

    public QueryBuilder<PlayListInfo> queryPlaylist(long deviceId) {
        return this.mBoxStore.boxFor(PlayListInfo.class).query().equal(PlayListInfo_.deviceId, deviceId);
    }

    public QueryBuilder<PlayListInfo> queryPlaylistById(long deviceId, long playlistId) {
        return this.mBoxStore.boxFor(PlayListInfo.class).query().equal(PlayListInfo_.deviceId, deviceId).equal(PlayListInfo_.num, playlistId);
    }

    public QueryBuilder<McuVersion> queryMcuByType(String type) {
        return this.mBoxStore.boxFor(McuVersion.class).query().equal(McuVersion_.firmwaretypecode, type, QueryBuilder.StringOrder.CASE_INSENSITIVE);
    }

    public QueryBuilder<ModeContent> queryModeList(long userId, int deviceType, int modeType) {
        QueryBuilder<ModeContent> equal = this.mBoxStore.boxFor(ModeContent.class).query().equal(ModeContent_.userId, userId);
        if (deviceType > 0) {
            equal.equal(ModeContent_.deviceType, deviceType);
        }
        if (modeType > 0) {
            equal.equal(ModeContent_.modeType, modeType);
        }
        return equal;
    }

    public QueryBuilder<ModeContent> queryModeById(long userId, long modeId) {
        return this.mBoxStore.boxFor(ModeContent.class).query().equal(ModeContent_.userId, userId).equal(ModeContent_.lightModeId, modeId);
    }

    public QueryBuilder<Group> queryGroupsByIds(long userId, List<Long> ids) {
        long[] jArr = new long[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            jArr[i] = ids.get(i).longValue();
        }
        return this.mBoxStore.boxFor(Group.class).query().in(Group_.groupId, jArr).equal(Group_.userId, userId);
    }
}