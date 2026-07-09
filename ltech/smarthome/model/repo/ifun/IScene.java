package com.ltech.smarthome.model.repo.ifun;

import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import io.reactivex.Observable;
import java.util.List;

/* loaded from: classes4.dex */
public interface IScene {
    Observable<List<Scene>> getAllScene(long placeId);

    List<Scene> getAllSceneByPlaceId(long placeId);

    Scene getLocalSceneBySceneNum(long num);

    Scene getSceneBySceneId(long sceneId);

    Listing<Scene> getSceneContent(LifecycleOwner owner, long sceneId);

    Listing<Scene> getSceneList(LifecycleOwner owner, long placeId, int sceneType);

    Listing<Scene> getSceneList(LifecycleOwner owner, long placeId, int sceneType, boolean common);

    Listing<Scene> getSceneList(LifecycleOwner owner, long placeId, boolean... common);

    List<Scene> getSceneListByPlaceId(long placeId, boolean isLocal);

    List<Scene> getSceneListByRoomId(long placeId, long floorId, long roomId, long deviceId);

    List<Scene> getSceneListByRoomId(long placeId, long floorId, long roomId, boolean... common);

    List<Scene> getSceneListFromNet(ListSceneResponse response, int sceneType);

    boolean isSceneNameExist(long place, String name);

    void removeScene(long sceneId);

    void saveScene(Scene scene);

    void sortScene(List<Scene> scenes);

    void updateSceneContent(long sceneId, List<Scene.SceneContent> contentList);

    void updateSceneIconPosition(long sceneId, int iconPos);

    void updateSceneName(long sceneId, String name);

    void updateSceneRoom(long sceneId, long floorId, long roomId);
}