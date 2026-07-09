package com.ltech.smarthome.singleton;

/* loaded from: classes4.dex */
public class KeyCreator {
    private KeyCreator() {
    }

    public String userInfoKey(long userId) {
        return "UserInfo" + userId;
    }

    public String placeListKey() {
        return "PlaceList";
    }

    public String placeInfoKey(long placeId) {
        return "PlaceInfo" + placeId;
    }

    public String placeKey(long placeId) {
        return "Place" + placeId;
    }

    public String floorListKey(long placeId) {
        return "FloorList" + placeId;
    }

    public String roomListKey(long floorId) {
        return "RoomList" + floorId;
    }

    public String placeUserRoomListKey(long floorId) {
        return "placeUserRoomList" + floorId;
    }

    public String userRoomListKey(long placeId) {
        return "UserRoomList" + placeId;
    }

    public String deviceListKey(long placeId) {
        return "DeviceList" + placeId;
    }

    public String placeUserKey(long placeId) {
        return "PlaceUserList" + placeId;
    }

    public String sceneKey(long placeId) {
        return "SceneList" + placeId;
    }

    public String sceneContentKey(long sceneId) {
        return "SceneContentList" + sceneId;
    }

    public String automationListKey(long placeId) {
        return "AutomationList" + placeId;
    }

    public String automationKey(long automationId) {
        return "Automation" + automationId;
    }

    public String groupKey(long placeId) {
        return "GroupList" + placeId;
    }

    public String superPanelInfoKey(long deviceId) {
        return "SuperPanelInfo" + deviceId;
    }

    public String superPanelKeywordInfoKey(long deviceId) {
        return "SuperPanelKeywordInfo" + deviceId;
    }

    public String modeKey(long placeId) {
        return "ModeList" + placeId;
    }
}