package com.ltech.smarthome.model.repo;

import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.WrapperResource;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.repo.SceneRepository;
import com.ltech.smarthome.model.repo.ifun.IScene;
import com.ltech.smarthome.model.repo.ifun.IUser;
import com.ltech.smarthome.net.response.scene.DetailSceneResponse;
import com.ltech.smarthome.net.response.scene.ListSceneResponse;
import com.ltech.smarthome.singleton.KeyCreator;
import com.ltech.smarthome.singleton.RateLimiter;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public final class SceneRepository extends BaseRepository implements IScene {
    public SceneRepository(BoxStore boxStore, RateLimiter limiter, KeyCreator keyCreator, IUser user) {
        super(boxStore, limiter, keyCreator, user);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public Listing<Scene> getSceneList(LifecycleOwner owner, long placeId, boolean... common) {
        if (common.length == 0) {
            return getSceneList(owner, placeId, 3, false);
        }
        return getSceneList(owner, placeId, 3, common[0]);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public Listing<Scene> getSceneList(LifecycleOwner owner, long placeId, int sceneType) {
        return getSceneList(owner, placeId, sceneType, false);
    }

    /* renamed from: com.ltech.smarthome.model.repo.SceneRepository$1, reason: invalid class name */
    class AnonymousClass1 extends WrapperResource<Scene, ListSceneResponse> {
        final /* synthetic */ boolean val$common;
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$placeId;
        final /* synthetic */ int val$sceneType;

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return true;
        }

        AnonymousClass1(final long val$placeId, final boolean val$common, final int val$sceneType, final LifecycleOwner val$owner) {
            this.val$placeId = val$placeId;
            this.val$common = val$common;
            this.val$sceneType = val$sceneType;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            SceneRepository.this.mLimiter.reset(SceneRepository.this.mKeyCreator.sceneKey(this.val$placeId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Scene> getDbQueryBuilder() {
            if (this.val$common) {
                return SceneRepository.this.mBoxBuilderFactory.querySceneList(SceneRepository.this.mUser.getUserId(), this.val$placeId, true);
            }
            if (this.val$sceneType == 3) {
                return SceneRepository.this.mBoxBuilderFactory.querySceneList(SceneRepository.this.mUser.getUserId(), this.val$placeId);
            }
            return SceneRepository.this.mBoxBuilderFactory.querySceneList(SceneRepository.this.mUser.getUserId(), this.val$placeId, this.val$sceneType);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<ListSceneResponse> observer) {
            if (this.val$common) {
                ((ObservableSubscribeProxy) Injection.net().listSceneCommon(this.val$placeId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner)))).subscribe(observer);
            } else if (this.val$sceneType == 4) {
                ((ObservableSubscribeProxy) Injection.net().listScene(this.val$placeId, this.val$sceneType).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner)))).subscribe(observer);
            } else {
                ((ObservableSubscribeProxy) Injection.net().listSceneAll(this.val$placeId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner)))).subscribe(observer);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final ListSceneResponse response) {
            BoxStore boxStore = SceneRepository.this.mBoxStore;
            final int i = this.val$sceneType;
            final boolean z = this.val$common;
            boxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SceneRepository.AnonymousClass1.this.lambda$saveDataFromNet$0(response, i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(ListSceneResponse listSceneResponse, int i, boolean z) {
            Box boxFor = SceneRepository.this.mBoxStore.boxFor(Scene.class);
            List<Scene> find = getDbQueryBuilder().build().find();
            ArrayList arrayList = new ArrayList(listSceneResponse.getTotal());
            if (listSceneResponse.getTotal() > 0) {
                for (ListSceneResponse.RowsBean rowsBean : listSceneResponse.getRows()) {
                    Scene scene = new Scene();
                    scene.setSceneId(rowsBean.getSceneid());
                    scene.setPlaceId(rowsBean.getPlaceid());
                    scene.setSceneIndex(rowsBean.getSceneindex());
                    scene.setSceneName(rowsBean.getScenename());
                    scene.setIconPos(rowsBean.getImgindex());
                    scene.setUserId(SceneRepository.this.mUser.getUserId());
                    scene.setSceneType(rowsBean.getScenetype());
                    scene.setCreateTime(rowsBean.getCreatetime());
                    scene.setFloorId(rowsBean.getFloorid());
                    scene.setRoomId(rowsBean.getRoomid());
                    scene.setMacdeviceid(rowsBean.getMacdeviceid());
                    scene.setExtParam(rowsBean.getParamext());
                    scene.setCommon(rowsBean.getCommon() == 1);
                    scene.setDynamic(rowsBean.getDynamics() == 1);
                    scene.setFloorName(rowsBean.getFloorname());
                    scene.setRoomName(rowsBean.getRoomname());
                    if (scene.getSceneType() == 4) {
                        scene.setSceneNum(DaliProHelper.getSceneNum(scene));
                    } else {
                        scene.setSceneNum(rowsBean.getScenenum());
                    }
                    Iterator<Scene> it = find.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Scene next = it.next();
                            if (next.getSceneId() == scene.getSceneId()) {
                                scene.setId(next.getId());
                                break;
                            }
                        }
                    }
                    arrayList.add(scene);
                }
            }
            if (i == 3 && !z) {
                boxFor.removeAll();
            }
            boxFor.put((Collection) arrayList);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public Listing<Scene> getSceneList(LifecycleOwner owner, long placeId, int sceneType, boolean common) {
        return new AnonymousClass1(placeId, common, sceneType, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public Observable<List<Scene>> getAllScene(final long placeId) {
        return Injection.net().listSceneAll(placeId).flatMap(new Function<ListSceneResponse, ObservableSource<? extends List<Scene>>>() { // from class: com.ltech.smarthome.model.repo.SceneRepository.2

            /* renamed from: com.ltech.smarthome.model.repo.SceneRepository$2$1, reason: invalid class name */
            class AnonymousClass1 implements ObservableOnSubscribe<List<Scene>> {
                final /* synthetic */ ListSceneResponse val$response;

                AnonymousClass1(final ListSceneResponse val$response) {
                    this.val$response = val$response;
                }

                @Override // io.reactivex.ObservableOnSubscribe
                public void subscribe(ObservableEmitter<List<Scene>> emitter) throws Exception {
                    BoxStore boxStore = SceneRepository.this.mBoxStore;
                    final long j = placeId;
                    final ListSceneResponse listSceneResponse = this.val$response;
                    boxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$2$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SceneRepository.AnonymousClass2.AnonymousClass1.this.lambda$subscribe$0(j, listSceneResponse);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$subscribe$0(long j, ListSceneResponse listSceneResponse) {
                    Box boxFor = SceneRepository.this.mBoxStore.boxFor(Scene.class);
                    List<Scene> find = SceneRepository.this.mBoxBuilderFactory.querySceneList(SceneRepository.this.mUser.getUserId(), j).build().find();
                    ArrayList arrayList = new ArrayList(listSceneResponse.getTotal());
                    if (listSceneResponse.getTotal() > 0) {
                        for (ListSceneResponse.RowsBean rowsBean : listSceneResponse.getRows()) {
                            Scene scene = new Scene();
                            scene.setSceneId(rowsBean.getSceneid());
                            scene.setPlaceId(rowsBean.getPlaceid());
                            scene.setSceneIndex(rowsBean.getSceneindex());
                            scene.setSceneName(rowsBean.getScenename());
                            scene.setIconPos(rowsBean.getImgindex());
                            scene.setUserId(SceneRepository.this.mUser.getUserId());
                            scene.setSceneType(rowsBean.getScenetype());
                            scene.setCreateTime(rowsBean.getCreatetime());
                            scene.setFloorId(rowsBean.getFloorid());
                            scene.setRoomId(rowsBean.getRoomid());
                            scene.setMacdeviceid(rowsBean.getMacdeviceid());
                            scene.setExtParam(rowsBean.getParamext());
                            scene.setCommon(rowsBean.getCommon() == 1);
                            scene.setDynamic(rowsBean.getDynamics() == 1);
                            scene.setRoomName(rowsBean.getRoomname());
                            scene.setFloorName(rowsBean.getFloorname());
                            if (scene.getSceneType() == 4) {
                                scene.setSceneNum(DaliProHelper.getSceneNum(scene));
                            } else {
                                scene.setSceneNum(rowsBean.getScenenum());
                            }
                            Iterator<Scene> it = find.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    Scene next = it.next();
                                    if (next.getSceneId() == scene.getSceneId()) {
                                        scene.setId(next.getId());
                                        break;
                                    }
                                }
                            }
                            arrayList.add(scene);
                        }
                    }
                    boxFor.removeAll();
                    boxFor.put((Collection) arrayList);
                }
            }

            @Override // io.reactivex.functions.Function
            public ObservableSource<? extends List<Scene>> apply(ListSceneResponse response) throws Exception {
                return Observable.create(new AnonymousClass1(response));
            }
        });
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public List<Scene> getSceneListFromNet(ListSceneResponse response, int sceneType) {
        ArrayList arrayList = new ArrayList(response.getTotal());
        if (response.getTotal() > 0) {
            for (ListSceneResponse.RowsBean rowsBean : response.getRows()) {
                if (rowsBean != null) {
                    Scene scene = new Scene();
                    scene.setSceneId(rowsBean.getSceneid());
                    scene.setPlaceId(rowsBean.getPlaceid());
                    scene.setSceneIndex(rowsBean.getSceneindex());
                    scene.setSceneName(rowsBean.getScenename());
                    scene.setIconPos(rowsBean.getImgindex());
                    scene.setUserId(this.mUser.getUserId());
                    scene.setSceneType(rowsBean.getScenetype());
                    scene.setCreateTime(rowsBean.getCreatetime());
                    scene.setFloorId(rowsBean.getFloorid());
                    scene.setRoomId(rowsBean.getRoomid());
                    scene.setMacdeviceid(rowsBean.getMacdeviceid());
                    scene.setExtParam(rowsBean.getParamext());
                    scene.setCommon(rowsBean.getCommon() == 1);
                    scene.setDynamic(rowsBean.getDynamics() == 1);
                    scene.setRoomName(rowsBean.getRoomname());
                    scene.setFloorName(rowsBean.getFloorname());
                    if (scene.getSceneType() == 4) {
                        scene.setSceneNum(DaliProHelper.getSceneNum(scene));
                    } else {
                        scene.setSceneNum(rowsBean.getScenenum());
                    }
                    if (sceneType != 1 && sceneType != 2) {
                        arrayList.add(scene);
                    } else if (scene.getSceneType() == sceneType) {
                        arrayList.add(scene);
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public void removeScene(final long sceneId) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SceneRepository.this.lambda$removeScene$0(sceneId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeScene$0(long j) {
        List<Scene> find = this.mBoxBuilderFactory.queryScene(this.mUser.getUserId(), j).build().find();
        if (find.isEmpty()) {
            return;
        }
        this.mBoxStore.boxFor(Scene.class).remove((Collection) find);
    }

    /* renamed from: com.ltech.smarthome.model.repo.SceneRepository$3, reason: invalid class name */
    class AnonymousClass3 extends WrapperResource<Scene, DetailSceneResponse> {
        final /* synthetic */ LifecycleOwner val$owner;
        final /* synthetic */ long val$sceneId;

        @Override // com.ltech.smarthome.model.WrapperResource
        protected boolean shouldFetch() {
            return true;
        }

        AnonymousClass3(final long val$sceneId, final LifecycleOwner val$owner) {
            this.val$sceneId = val$sceneId;
            this.val$owner = val$owner;
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void fetchFail() {
            SceneRepository.this.mLimiter.reset(SceneRepository.this.mKeyCreator.sceneContentKey(this.val$sceneId));
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected QueryBuilder<Scene> getDbQueryBuilder() {
            return SceneRepository.this.mBoxBuilderFactory.queryScene(SceneRepository.this.mUser.getUserId(), this.val$sceneId);
        }

        @Override // com.ltech.smarthome.model.WrapperResource
        protected void netCall(Observer<DetailSceneResponse> observer) {
            ((ObservableSubscribeProxy) Injection.net().detailScene(this.val$sceneId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.val$owner)))).subscribe(observer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.model.WrapperResource
        public void saveDataFromNet(final DetailSceneResponse response) {
            BoxStore boxStore = SceneRepository.this.mBoxStore;
            final long j = this.val$sceneId;
            boxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SceneRepository.AnonymousClass3.this.lambda$saveDataFromNet$0(j, response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$saveDataFromNet$0(long j, DetailSceneResponse detailSceneResponse) {
            Box boxFor = SceneRepository.this.mBoxStore.boxFor(Scene.class);
            Scene findFirst = getDbQueryBuilder().build().findFirst();
            if (findFirst == null) {
                findFirst = new Scene();
                findFirst.setSceneId(j);
            }
            ArrayList arrayList = new ArrayList();
            if (detailSceneResponse.getTotal() > 0) {
                for (DetailSceneResponse.RowsBean rowsBean : detailSceneResponse.getRows()) {
                    Scene.SceneContent sceneContent = new Scene.SceneContent();
                    sceneContent.setAction(rowsBean.getAction());
                    sceneContent.setActionType(rowsBean.getActiontype());
                    sceneContent.setActionTypeName(rowsBean.getActiontypename());
                    sceneContent.setExecuteCommand(rowsBean.getExecutecommand());
                    sceneContent.setTimeSpace(rowsBean.getTimespace());
                    arrayList.add(sceneContent);
                }
            }
            findFirst.setSceneContents(arrayList);
            boxFor.put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public Listing<Scene> getSceneContent(LifecycleOwner owner, long sceneId) {
        return new AnonymousClass3(sceneId, owner).toListing();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public void saveScene(final Scene scene) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SceneRepository.this.lambda$saveScene$1(scene);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveScene$1(Scene scene) {
        scene.setUserId(this.mUser.getUserId());
        this.mBoxStore.boxFor(Scene.class).put((Box) scene);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public void sortScene(final List<Scene> scenes) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SceneRepository.this.lambda$sortScene$2(scenes);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortScene$2(List list) {
        int i = 0;
        while (i < list.size()) {
            Scene scene = (Scene) list.get(i);
            i++;
            scene.setSceneIndex(i);
        }
        this.mBoxStore.boxFor(Scene.class).put((Collection) list);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public void updateSceneName(final long sceneId, final String name) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SceneRepository.this.lambda$updateSceneName$3(sceneId, name);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSceneName$3(long j, String str) {
        Scene findFirst = this.mBoxBuilderFactory.queryScene(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setSceneName(str);
            this.mBoxStore.boxFor(Scene.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public void updateSceneIconPosition(final long sceneId, final int iconPos) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SceneRepository.this.lambda$updateSceneIconPosition$4(sceneId, iconPos);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSceneIconPosition$4(long j, int i) {
        Scene findFirst = this.mBoxBuilderFactory.queryScene(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setIconPos(i);
            this.mBoxStore.boxFor(Scene.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public void updateSceneRoom(final long sceneId, final long floorId, final long roomId) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SceneRepository.this.lambda$updateSceneRoom$5(sceneId, floorId, roomId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSceneRoom$5(long j, long j2, long j3) {
        Scene findFirst = this.mBoxBuilderFactory.queryScene(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setFloorId(j2);
            findFirst.setRoomId(j3);
            this.mBoxStore.boxFor(Scene.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public void updateSceneContent(final long sceneId, final List<Scene.SceneContent> contentList) {
        this.mBoxStore.runInTx(new Runnable() { // from class: com.ltech.smarthome.model.repo.SceneRepository$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SceneRepository.this.lambda$updateSceneContent$6(sceneId, contentList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSceneContent$6(long j, List list) {
        Scene findFirst = this.mBoxBuilderFactory.queryScene(this.mUser.getUserId(), j).build().findFirst();
        if (findFirst != null) {
            findFirst.setSceneContents(list);
            this.mBoxStore.boxFor(Scene.class).put((Box) findFirst);
        }
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public List<Scene> getAllSceneByPlaceId(long placeId) {
        return this.mBoxBuilderFactory.querySceneList(this.mUser.getUserId(), placeId).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public List<Scene> getSceneListByPlaceId(long placeId, boolean isLocal) {
        new ArrayList();
        return this.mBoxBuilderFactory.querySceneList(this.mUser.getUserId(), placeId, isLocal ? 2 : 1).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public Scene getSceneBySceneId(long sceneId) {
        List<Scene> find = this.mBoxBuilderFactory.queryScene(this.mUser.getUserId(), sceneId).build().find();
        if (find.isEmpty()) {
            return null;
        }
        return find.get(0);
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public Scene getLocalSceneBySceneNum(long num) {
        return this.mBoxBuilderFactory.queryLocalSceneByNum(this.mUser.getUserId(), num).build().findFirst();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public boolean isSceneNameExist(long place, String name) {
        return !this.mBoxBuilderFactory.querySceneByName(this.mUser.getUserId(), place, name).build().find().isEmpty();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public List<Scene> getSceneListByRoomId(long placeId, long floorId, long roomId, boolean... common) {
        return this.mBoxBuilderFactory.querySceneList(this.mUser.getUserId(), placeId, floorId, roomId, common).build().find();
    }

    @Override // com.ltech.smarthome.model.repo.ifun.IScene
    public List<Scene> getSceneListByRoomId(long placeId, long floorId, long roomId, long deviceId) {
        return this.mBoxBuilderFactory.querySceneList(this.mUser.getUserId(), placeId, floorId, roomId, deviceId).build().find();
    }
}