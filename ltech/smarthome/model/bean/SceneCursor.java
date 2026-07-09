package com.ltech.smarthome.model.bean;

import com.github.mikephil.charting.utils.Utils;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.Scene_;
import io.objectbox.BoxStore;
import io.objectbox.Cursor;
import io.objectbox.Transaction;
import io.objectbox.internal.CursorFactory;
import java.util.List;

/* loaded from: classes4.dex */
public final class SceneCursor extends Cursor<Scene> {
    private final Scene.SceneContent.SceneContentConverter sceneContentsConverter;
    private static final Scene_.SceneIdGetter ID_GETTER = Scene_.__ID_GETTER;
    private static final int __ID_sceneId = Scene_.sceneId.id;
    private static final int __ID_userId = Scene_.userId.id;
    private static final int __ID_placeId = Scene_.placeId.id;
    private static final int __ID_sceneName = Scene_.sceneName.id;
    private static final int __ID_sceneIndex = Scene_.sceneIndex.id;
    private static final int __ID_iconPos = Scene_.iconPos.id;
    private static final int __ID_createTime = Scene_.createTime.id;
    private static final int __ID_floorId = Scene_.floorId.id;
    private static final int __ID_roomId = Scene_.roomId.id;
    private static final int __ID_common = Scene_.common.id;
    private static final int __ID_dynamic = Scene_.dynamic.id;
    private static final int __ID_sceneContents = Scene_.sceneContents.id;
    private static final int __ID_sceneNum = Scene_.sceneNum.id;
    private static final int __ID_sceneType = Scene_.sceneType.id;
    private static final int __ID_param = Scene_.param.id;
    private static final int __ID_macdeviceid = Scene_.macdeviceid.id;
    private static final int __ID_extParam = Scene_.extParam.id;
    private static final int __ID_floorName = Scene_.floorName.id;
    private static final int __ID_roomName = Scene_.roomName.id;

    static final class Factory implements CursorFactory<Scene> {
        Factory() {
        }

        @Override // io.objectbox.internal.CursorFactory
        public Cursor<Scene> createCursor(Transaction tx, long cursorHandle, BoxStore boxStoreForEntities) {
            return new SceneCursor(tx, cursorHandle, boxStoreForEntities);
        }
    }

    public SceneCursor(Transaction tx, long cursor, BoxStore boxStore) {
        super(tx, cursor, Scene_.__INSTANCE, boxStore);
        this.sceneContentsConverter = new Scene.SceneContent.SceneContentConverter();
    }

    @Override // io.objectbox.Cursor
    public long getId(Scene entity) {
        return ID_GETTER.getId(entity);
    }

    @Override // io.objectbox.Cursor
    public long put(Scene scene) {
        String sceneName = scene.getSceneName();
        int i = sceneName != null ? __ID_sceneName : 0;
        String createTime = scene.getCreateTime();
        int i2 = createTime != null ? __ID_createTime : 0;
        List<Scene.SceneContent> sceneContents = scene.getSceneContents();
        int i3 = sceneContents != null ? __ID_sceneContents : 0;
        String param = scene.getParam();
        collect400000(this.cursor, 0L, 1, i, sceneName, i2, createTime, i3, i3 != 0 ? this.sceneContentsConverter.convertToDatabaseValue(sceneContents) : null, param != null ? __ID_param : 0, param);
        String extParam = scene.getExtParam();
        int i4 = extParam != null ? __ID_extParam : 0;
        String floorName = scene.getFloorName();
        int i5 = floorName != null ? __ID_floorName : 0;
        String roomName = scene.getRoomName();
        collect313311(this.cursor, 0L, 0, i4, extParam, i5, floorName, roomName != null ? __ID_roomName : 0, roomName, 0, null, __ID_sceneId, scene.getSceneId(), __ID_userId, scene.getUserId(), __ID_placeId, scene.getPlaceId(), __ID_sceneIndex, scene.getSceneIndex(), __ID_iconPos, scene.getIconPos(), __ID_sceneNum, scene.getSceneNum(), 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        long collect313311 = collect313311(this.cursor, scene.getId(), 2, 0, null, 0, null, 0, null, 0, null, __ID_floorId, scene.getFloorId(), __ID_roomId, scene.getRoomId(), __ID_macdeviceid, scene.getMacdeviceid(), __ID_sceneType, scene.getSceneType(), __ID_common, scene.isCommon() ? 1 : 0, __ID_dynamic, scene.isDynamic() ? 1 : 0, 0, 0.0f, 0, Utils.DOUBLE_EPSILON);
        scene.setId(collect313311);
        return collect313311;
    }
}