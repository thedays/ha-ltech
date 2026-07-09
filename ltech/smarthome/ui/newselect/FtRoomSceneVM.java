package com.ltech.smarthome.ui.newselect;

import android.text.TextUtils;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.scene.AddSceneResponse;
import com.ltech.smarthome.ui.newselect.BaseRoomPageVM;
import com.ltech.smarthome.ui.scene.ActAddCloudScene;
import com.ltech.smarthome.ui.scene.local.ActAddLocalScene;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.AddSceneDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtRoomSceneVM extends BaseRoomPageVM {
    public Listing<Scene> request;
    public Scene scene;
    public boolean selectAll;
    public List<SceneItem> mRoomList = new ArrayList();
    public List<Scene> allSceneData = new ArrayList();
    public List<Scene> selectSceneList = new ArrayList();
    public MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>();
    public boolean allowMultiSelect = false;
    public SingleLiveEvent<Void> addSceneEvent = new SingleLiveEvent<>();

    public boolean checkRoomChange(List<Room> roomList, List<Floor> floorList) {
        if (this.selectFloor.getValue().getFloorId() == -1) {
            if (floorList.size() != this.mRoomList.size()) {
                return true;
            }
            for (int i = 0; i < floorList.size(); i++) {
                if (floorList.get(i).getFloorId() != this.mRoomList.get(i).getFloorId() || !floorList.get(i).getFloorName().equals(this.mRoomList.get(i).getRoomName()) || floorList.get(i).getIndex() != this.mRoomList.get(i).getRoomIndex()) {
                    return true;
                }
            }
        } else {
            if (roomList.size() != this.mRoomList.size()) {
                return true;
            }
            for (int i2 = 0; i2 < roomList.size(); i2++) {
                if (roomList.get(i2).getRoomId() != this.mRoomList.get(i2).getRoomId() || !roomList.get(i2).getRoomName().equals(this.mRoomList.get(i2).getRoomName()) || roomList.get(i2).getIndex() != this.mRoomList.get(i2).getRoomIndex()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void initRoomList(List<Room> roomList, List<Floor> floorList) {
        ArrayList arrayList = new ArrayList();
        if (this.selectFloor.getValue().getFloorId() == -1) {
            for (Floor floor : floorList) {
                arrayList.add(new SceneItem(this, this.placeId.getValue().longValue(), floor.getFloorId(), -1L, floor.getIndex(), floor.getFloorName()));
            }
        } else {
            for (Room room : roomList) {
                arrayList.add(new SceneItem(this, this.placeId.getValue().longValue(), room.getFloorId(), room.getRoomId(), room.getIndex(), room.getRoomName()));
            }
        }
        this.mRoomList = arrayList;
    }

    public void initRoomList(List<Room> roomList, List<Floor> floorList, long deviceId) {
        ArrayList arrayList = new ArrayList();
        if (this.selectFloor.getValue().getFloorId() == -1) {
            for (Floor floor : floorList) {
                arrayList.add(new SceneItem(this, this.placeId.getValue().longValue(), floor.getFloorId(), -1L, floor.getIndex(), floor.getFloorName(), deviceId));
            }
        } else {
            for (Room room : roomList) {
                arrayList.add(new SceneItem(this, this.placeId.getValue().longValue(), room.getFloorId(), room.getRoomId(), room.getIndex(), room.getRoomName(), deviceId));
            }
        }
        this.mRoomList = arrayList;
    }

    public void initRoomList(List<Room> roomList, List<Floor> floorList, long[] relateSceneIds) {
        ArrayList arrayList = new ArrayList();
        if (this.selectFloor.getValue().getFloorId() == -1) {
            for (Floor floor : floorList) {
                arrayList.add(new SceneItem(this, this.placeId.getValue().longValue(), floor.getFloorId(), -1L, floor.getIndex(), floor.getFloorName(), relateSceneIds));
            }
        } else {
            for (Room room : roomList) {
                arrayList.add(new SceneItem(this, this.placeId.getValue().longValue(), room.getFloorId(), room.getRoomId(), room.getIndex(), room.getRoomName(), relateSceneIds));
            }
        }
        this.mRoomList = arrayList;
    }

    public boolean hasUnConfigScene(long floorId, long roomId) {
        return Injection.repo().scene().getSceneListByRoomId(this.placeId.getValue().longValue(), floorId, roomId, new boolean[0]).size() > 0;
    }

    public boolean hasUnConfigRelateScene(List<Scene> list) {
        for (Scene scene : list) {
            if (scene.getFloorId() == 0 || scene.getRoomId() == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasUnConfigScene(long floorId, long roomId, long deviceId) {
        return Injection.repo().scene().getSceneListByRoomId(this.placeId.getValue().longValue(), floorId, roomId, deviceId).size() > 0;
    }

    public void selectAll() {
        if (this.allSceneData.isEmpty()) {
            return;
        }
        if (!this.selectAll) {
            for (Scene scene : this.allSceneData) {
                if (!this.selectSceneList.contains(scene)) {
                    this.selectSceneList.add(scene);
                }
            }
        } else {
            Iterator<Scene> it = this.allSceneData.iterator();
            while (it.hasNext()) {
                this.selectSceneList.remove(it.next());
            }
        }
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectSceneList.size()));
    }

    public String getSceneName() {
        String str = "";
        boolean z = true;
        int i = 0;
        while (z) {
            i++;
            str = getContext().getString(R.string.app_scene) + i;
            z = Injection.repo().scene().isSceneNameExist(Injection.repo().home().getSelectPlace().getValue().getPlaceId(), str);
        }
        return str;
    }

    public void showEditNameDialog(final RoomPickHelper roomPickHelper) {
        final AddSceneDialog onSaveListener = AddSceneDialog.asDefault().setDefaultName(getSceneName()).setOnSaveListener(new AddSceneDialog.OnSaveListener() { // from class: com.ltech.smarthome.ui.newselect.FtRoomSceneVM.1
            @Override // com.ltech.smarthome.view.dialog.AddSceneDialog.OnSaveListener
            public void cancel() {
            }

            @Override // com.ltech.smarthome.view.dialog.AddSceneDialog.OnSaveListener
            public void onSave(String name, boolean isLocal, int floorPos, int roomPos) {
                if (TextUtils.isEmpty(name.trim())) {
                    SmartToast.showShort(FtRoomSceneVM.this.getContext().getString(R.string.input_name));
                    return;
                }
                RoomPickHelper roomPickHelper2 = roomPickHelper;
                if (roomPickHelper2 != null) {
                    roomPickHelper2.setSelectPosition(floorPos, roomPos);
                    FtRoomSceneVM.this.createScene(name, roomPickHelper.getSelectFloorId(), roomPickHelper.getSelectRoomId(), isLocal);
                }
            }
        });
        if (this.mRoomList != null && this.roomPosition < this.mRoomList.size()) {
            SceneItem sceneItem = this.mRoomList.get(this.roomPosition);
            roomPickHelper.setCurrentFloorIdPos(sceneItem.getFloorId());
            roomPickHelper.setCurrentRoomIdPos(sceneItem.getFloorId(), sceneItem.getRoomId());
        }
        onSaveListener.setSelectRoom(roomPickHelper.canSetRoom()).setFloorList(roomPickHelper.getCurrentFloorNames()).setRoomList(roomPickHelper.getCurrentRoomNames()).setSelectFloorPosition(roomPickHelper.getSelectFloorPosition()).setSelectRoomPosition(roomPickHelper.getSelectRoomPosition()).setOnSelectFloorListener(new AddSceneDialog.OnSelectFloorListener() { // from class: com.ltech.smarthome.ui.newselect.FtRoomSceneVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.dialog.AddSceneDialog.OnSelectFloorListener
            public final void selectFloor(AddSceneDialog addSceneDialog, int i, String str) {
                FtRoomSceneVM.lambda$showEditNameDialog$0(AddSceneDialog.this, roomPickHelper, addSceneDialog, i, str);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    static /* synthetic */ void lambda$showEditNameDialog$0(AddSceneDialog addSceneDialog, RoomPickHelper roomPickHelper, AddSceneDialog addSceneDialog2, int i, String str) {
        addSceneDialog.setRoomList(roomPickHelper.getRoomNames(i));
        addSceneDialog.notifyDialog();
    }

    public void createScene(String name, final long floorId, final long roomId, final boolean isLocal) {
        ((ObservableSubscribeProxy) Injection.net().addScene(1000, getCurrentPlace().getPlaceId(), name, new ArrayList(), 0, floorId, roomId, isLocal).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomSceneVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomSceneVM.this.lambda$createScene$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.newselect.FtRoomSceneVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() {
                FtRoomSceneVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.newselect.FtRoomSceneVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomSceneVM.this.lambda$createScene$2(isLocal, floorId, roomId, (AddSceneResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createScene$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.creating));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createScene$2(boolean z, long j, long j2, AddSceneResponse addSceneResponse) throws Exception {
        Scene scene = new Scene();
        scene.setSceneId(addSceneResponse.getSceneid());
        scene.setPlaceId(addSceneResponse.getPlaceid());
        scene.setSceneIndex(addSceneResponse.getSceneindex());
        scene.setSceneName(addSceneResponse.getScenename());
        scene.setSceneIndex(addSceneResponse.getSceneindex());
        scene.setSceneNum(addSceneResponse.getScenenum());
        scene.setSceneType(addSceneResponse.getScenetype());
        scene.setIconPos(addSceneResponse.getImgindex());
        scene.setFloorId(addSceneResponse.getFloorid());
        scene.setRoomId(addSceneResponse.getRoomid());
        Injection.repo().scene().saveScene(scene);
        if (z) {
            delOldLocalScene(scene.getSceneNum());
            navigation(NavUtils.destination(ActAddLocalScene.class).withLong(Constants.PLACE_ID, addSceneResponse.getPlaceid()).withInt(Constants.ICON_POSITION, scene.getIconPos()).withString(Constants.SCENE_NAME, scene.getSceneName()).withLong(Constants.SCENE_ID, scene.getSceneId()).withInt(Constants.SCENE_NUM, scene.getSceneNum()));
        } else {
            navigation(NavUtils.destination(ActAddCloudScene.class).withLong(Constants.PLACE_ID, addSceneResponse.getPlaceid()).withLong(Constants.FLOOR_ID, j).withLong(Constants.ROOM_ID, j2).withString(Constants.SCENE_NAME, scene.getSceneName()).withLong(Constants.SCENE_ID, scene.getSceneId()));
        }
    }

    private void delOldLocalScene(final int sceneNum) {
        CmdAssistant.getSceneCmdAssistant(null, new int[0]).delDeviceLocalScene(ActivityUtils.getTopActivity(), sceneNum);
        ThreadUtils.getMainHandler().postDelayed(new Runnable(this) { // from class: com.ltech.smarthome.ui.newselect.FtRoomSceneVM.2
            @Override // java.lang.Runnable
            public void run() {
                CmdAssistant.getSceneCmdAssistant(null, new int[0]).delLocalScene(ActivityUtils.getTopActivity(), sceneNum);
            }
        }, 1000L);
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public class SceneItem extends BaseRoomPageVM.RoomPageItem {
        private FtRoomScene ftScene;

        public SceneItem(final FtRoomSceneVM this$0, long placeId, long floorId, long roomId, int roomIndex, String roomName) {
            this.placeId = placeId;
            this.floorId = floorId;
            this.roomId = roomId;
            this.roomIndex = roomIndex;
            this.roomName = roomName;
            this.ftScene = FtRoomScene.newInstance(placeId, roomId, floorId);
        }

        public SceneItem(final FtRoomSceneVM this$0, long placeId, long floorId, long roomId, int roomIndex, String roomName, long[] relateSceneIds) {
            this.placeId = placeId;
            this.floorId = floorId;
            this.roomId = roomId;
            this.roomIndex = roomIndex;
            this.roomName = roomName;
            this.ftScene = FtRoomScene.newInstance(placeId, roomId, floorId, relateSceneIds);
        }

        public SceneItem(final FtRoomSceneVM this$0, long placeId, long floorId, long roomId, int roomIndex, String roomName, long deviceId) {
            this.placeId = placeId;
            this.floorId = floorId;
            this.roomId = roomId;
            this.roomIndex = roomIndex;
            this.roomName = roomName;
            this.ftScene = FtRoomScene.newInstance(placeId, roomId, floorId, deviceId);
        }

        public FtRoomScene getFtScene() {
            return this.ftScene;
        }
    }
}