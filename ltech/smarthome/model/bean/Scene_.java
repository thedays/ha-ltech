package com.ltech.smarthome.model.bean;

import com.huawei.hms.push.constant.RemoteMessageConst;
import com.justalk.cloud.lemon.MtcConf2Constants;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.bean.SceneCursor;
import io.objectbox.EntityInfo;
import io.objectbox.Property;
import io.objectbox.internal.CursorFactory;
import io.objectbox.internal.IdGetter;
import java.util.List;

/* loaded from: classes4.dex */
public final class Scene_ implements EntityInfo<Scene> {
    public static final Property<Scene>[] __ALL_PROPERTIES;
    public static final String __DB_NAME = "Scene";
    public static final int __ENTITY_ID = 8;
    public static final String __ENTITY_NAME = "Scene";
    public static final Property<Scene> __ID_PROPERTY;
    public static final Scene_ __INSTANCE;
    public static final Property<Scene> common;
    public static final Property<Scene> createTime;
    public static final Property<Scene> dynamic;
    public static final Property<Scene> extParam;
    public static final Property<Scene> floorId;
    public static final Property<Scene> floorName;
    public static final Property<Scene> iconPos;
    public static final Property<Scene> id;
    public static final Property<Scene> macdeviceid;
    public static final Property<Scene> param;
    public static final Property<Scene> placeId;
    public static final Property<Scene> roomId;
    public static final Property<Scene> roomName;
    public static final Property<Scene> sceneContents;
    public static final Property<Scene> sceneId;
    public static final Property<Scene> sceneIndex;
    public static final Property<Scene> sceneName;
    public static final Property<Scene> sceneNum;
    public static final Property<Scene> sceneType;
    public static final Property<Scene> userId;
    public static final Class<Scene> __ENTITY_CLASS = Scene.class;
    public static final CursorFactory<Scene> __CURSOR_FACTORY = new SceneCursor.Factory();
    static final SceneIdGetter __ID_GETTER = new SceneIdGetter();

    @Override // io.objectbox.EntityInfo
    public int getEntityId() {
        return 8;
    }

    static {
        Scene_ scene_ = new Scene_();
        __INSTANCE = scene_;
        Property<Scene> property = new Property<>(scene_, 0, 1, Long.TYPE, "id", true, "id");
        id = property;
        Property<Scene> property2 = new Property<>(scene_, 1, 2, Long.TYPE, "sceneId");
        sceneId = property2;
        Property<Scene> property3 = new Property<>(scene_, 2, 3, Long.TYPE, MtcConf2Constants.MtcConfThirdUserIdKey);
        userId = property3;
        Property<Scene> property4 = new Property<>(scene_, 3, 4, Long.TYPE, "placeId");
        placeId = property4;
        Property<Scene> property5 = new Property<>(scene_, 4, 5, String.class, "sceneName");
        sceneName = property5;
        Property<Scene> property6 = new Property<>(scene_, 5, 6, Integer.TYPE, "sceneIndex");
        sceneIndex = property6;
        Property<Scene> property7 = new Property<>(scene_, 6, 7, Integer.TYPE, "iconPos");
        iconPos = property7;
        Property<Scene> property8 = new Property<>(scene_, 7, 11, String.class, MtcConf2Constants.MtcConfCreateTimeKey);
        createTime = property8;
        Property<Scene> property9 = new Property<>(scene_, 8, 12, Long.TYPE, "floorId");
        floorId = property9;
        Property<Scene> property10 = new Property<>(scene_, 9, 13, Long.TYPE, "roomId");
        roomId = property10;
        Property<Scene> property11 = new Property<>(scene_, 10, 17, Boolean.TYPE, "common");
        common = property11;
        Property<Scene> property12 = new Property<>(scene_, 11, 18, Boolean.TYPE, "dynamic");
        dynamic = property12;
        Property<Scene> property13 = new Property<>(scene_, 12, 8, String.class, "sceneContents", false, "sceneContents", Scene.SceneContent.SceneContentConverter.class, List.class);
        sceneContents = property13;
        Property<Scene> property14 = new Property<>(scene_, 13, 9, Integer.TYPE, "sceneNum");
        sceneNum = property14;
        Property<Scene> property15 = new Property<>(scene_, 14, 10, Integer.TYPE, "sceneType");
        sceneType = property15;
        Property<Scene> property16 = new Property<>(scene_, 15, 14, String.class, RemoteMessageConst.MessageBody.PARAM);
        param = property16;
        Property<Scene> property17 = new Property<>(scene_, 16, 15, Long.TYPE, "macdeviceid");
        macdeviceid = property17;
        Property<Scene> property18 = new Property<>(scene_, 17, 16, String.class, "extParam");
        extParam = property18;
        Property<Scene> property19 = new Property<>(scene_, 18, 19, String.class, "floorName");
        floorName = property19;
        Property<Scene> property20 = new Property<>(scene_, 19, 20, String.class, "roomName");
        roomName = property20;
        __ALL_PROPERTIES = new Property[]{property, property2, property3, property4, property5, property6, property7, property8, property9, property10, property11, property12, property13, property14, property15, property16, property17, property18, property19, property20};
        __ID_PROPERTY = property;
    }

    @Override // io.objectbox.EntityInfo
    public String getEntityName() {
        return "Scene";
    }

    @Override // io.objectbox.EntityInfo
    public Class<Scene> getEntityClass() {
        return __ENTITY_CLASS;
    }

    @Override // io.objectbox.EntityInfo
    public String getDbName() {
        return "Scene";
    }

    @Override // io.objectbox.EntityInfo
    public Property<Scene>[] getAllProperties() {
        return __ALL_PROPERTIES;
    }

    @Override // io.objectbox.EntityInfo
    public Property<Scene> getIdProperty() {
        return __ID_PROPERTY;
    }

    @Override // io.objectbox.EntityInfo
    public IdGetter<Scene> getIdGetter() {
        return __ID_GETTER;
    }

    @Override // io.objectbox.EntityInfo
    public CursorFactory<Scene> getCursorFactory() {
        return __CURSOR_FACTORY;
    }

    static final class SceneIdGetter implements IdGetter<Scene> {
        SceneIdGetter() {
        }

        @Override // io.objectbox.internal.IdGetter
        public long getId(Scene object) {
            return object.getId();
        }
    }
}