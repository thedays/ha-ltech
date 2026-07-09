package com.ltech.smarthome.ui.share;

import java.util.List;

/* loaded from: classes4.dex */
public class PlaceShareHelper {
    public static final int ADD_MEMBER_SETTING_PERMISSION = 1001;
    public static final int SELECT_DEVICE_PERMISSION = 2;
    public static final int SELECT_GROUP_PERMISSION = 3;
    public static final int SELECT_ROOM_PERMISSION = 1;
    public static final int SELECT_SCENE_PERMISSION = 4;
    public static final int UPDATE_PERMISSION = 1002;
    public Object data;
    public List<Long> deviceIds;
    public int enterType;
    public List<Long> groupIds;
    public List<Long> roomIds;
    public List<Long> sceneIds;
    public long userId;

    private PlaceShareHelper() {
    }

    public static PlaceShareHelper getInstance() {
        return PlaceShareHelperHolder.INSTANCE;
    }

    private static class PlaceShareHelperHolder {
        private static PlaceShareHelper INSTANCE = new PlaceShareHelper();

        private PlaceShareHelperHolder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void reset() {
            INSTANCE = new PlaceShareHelper();
        }
    }

    public void reset() {
        PlaceShareHelperHolder.reset();
    }
}