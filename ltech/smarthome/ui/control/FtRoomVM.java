package com.ltech.smarthome.ui.control;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.blemesh.ITestSequenceCallback;
import com.ltech.smarthome.blemesh.bean.ExtendedBluetoothDevice;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.Listing;
import com.ltech.smarthome.model.Resource;
import com.ltech.smarthome.model.ResourceEmptyLiveData;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.filter.DeviceFilter;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.IFlyRepository;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.SortDeviceAndGroupRequest;
import com.ltech.smarthome.net.request.scene.SortSceneRequest;
import com.ltech.smarthome.ui.config.ActAddDevice;
import com.ltech.smarthome.ui.control.ActHomeVM;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.QueryDeviceStateRunnable;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ThreadPoolManager;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.SmartUtils;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.ISendResutCallback;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.extra.Emitter;
import com.smart.product_agreement.productBle.CmdBleFactory;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.util.internal.StringUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.functions.Function1;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.pqc.math.linearalgebra.Matrix;

/* loaded from: classes4.dex */
public class FtRoomVM extends BaseViewModel implements DeviceFilter {
    private static int maxRetryTimes = 3;
    public Place lastPlace;
    private Disposable mDisposable;
    private Observer<Boolean> mObserver;
    private HashMap<Long, Device> needCheckMap;
    private HashMap<Long, Group> needGroupCheckMap;
    public long placeId;
    public Listing<Place> placeList;
    public Listing<Place> request;
    public MutableLiveData<Resource<List<Role>>> roleResult;
    public long roomId;
    ArrayList<QueryDeviceStateRunnable> runnables;
    private SafeHandler safeHandler;
    public long selectedRoomId;
    private List<ActHomeVM.TabContent> tabContentList;
    private int tempnum;
    public boolean isRefresh = false;
    private boolean isChecking = false;
    public boolean isFirstLoading = true;
    public SingleLiveEvent<Void> scanEvent = new SingleLiveEvent<>();
    public MutableLiveData<List<Role>> selectRoleList = new MutableLiveData<>(new ArrayList());
    public MutableLiveData<Integer> selectRolePos = new MutableLiveData<>();
    public MutableLiveData<Integer> selectScenePos = new MutableLiveData<>();
    public MutableLiveData<Boolean> canScroll = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> editRoleMode = new MutableLiveData<>(false);
    public MutableLiveData<List<Scene>> selectSceneList = new MutableLiveData<>(new ArrayList());
    public MutableLiveData<Boolean> editSceneMode = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> refreshAllDeviceEvent = new MutableLiveData<>();
    public MutableLiveData<Boolean> refreshAllSceneEvent = new MutableLiveData<>();
    public List<RoomItem> mRoomList = new ArrayList();
    public List<Place> mPlaceList = new ArrayList();
    public List<Floor> mFloorList = new ArrayList();
    public SingleLiveEvent<Void> changePlaceEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> changeFloorEvent = new SingleLiveEvent<>();
    public MediatorLiveData<Place> placeResult = new MediatorLiveData<>();
    public MediatorLiveData<Place> placeInfoResult = new MediatorLiveData<>();
    public MediatorLiveData<Floor> floorResult = new MediatorLiveData<>();
    public SingleLiveEvent<String> selectNumber = new SingleLiveEvent<>();
    public SingleLiveEvent<String> selectAllText = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isShowSelectAll = new SingleLiveEvent<>(false);
    public SingleLiveEvent<Void> showNoPermissionDialogEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> initMeshEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> checkDevStatus = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> checkUserEvent = new SingleLiveEvent<>();
    public List<Integer> tempAddress = new ArrayList();
    public MutableLiveData<Boolean> selectAllEvent = new MutableLiveData<>();
    public MutableLiveData<Boolean> editFinishEvent = new MutableLiveData<>();
    public LiveData<Resource<List<Room>>> roomResult = Transformations.switchMap(this.floorResult, new Function1() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            LiveData lambda$new$0;
            lambda$new$0 = FtRoomVM.this.lambda$new$0((Floor) obj);
            return lambda$new$0;
        }
    });
    public boolean needCheckDeviceStatus = false;
    public boolean isFirst = true;
    public MutableLiveData<Role> freshDeviceData = new MutableLiveData<>();
    public MutableLiveData<Resource<List<Scene>>> commonSceneResult = new MutableLiveData<>();
    public SingleLiveEvent<Void> addUnConfigRoomEvent = new SingleLiveEvent<>();
    public RecyclerView.ItemDecoration deviceDecoration = new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
        }
    };
    public BindingCommand changePlace = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            FtRoomVM.this.lambda$new$2();
        }
    });
    public BindingCommand changeFloor = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            FtRoomVM.this.lambda$new$3();
        }
    });
    public BindingCommand selectAll = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda5
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            FtRoomVM.this.lambda$new$4();
        }
    });
    public BindingCommand finishEdit = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda6
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            FtRoomVM.this.lambda$new$5();
        }
    });
    public BindingCommand bottomClick = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda7
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            FtRoomVM.this.lambda$new$6();
        }
    });
    public BindingCommand addDevice = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda8
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            FtRoomVM.this.lambda$new$7();
        }
    });
    public BindingCommand searchDevice = new BindingCommand(new BindingAction() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda9
        @Override // com.ltech.smarthome.binding.command.BindingAction
        public final void call() {
            FtRoomVM.this.lambda$new$8();
        }
    });

    static /* synthetic */ void lambda$checkMeshGatwayStatus$17(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$loadData$14(Throwable th) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ LiveData lambda$new$0(Floor floor) {
        if (floor == null || getCurPlace() == null) {
            return ResourceEmptyLiveData.create();
        }
        return Injection.repo().home().getRoomList(getLifecycleOwner(), getCurPlace().getPlaceId(), floor.getFloorId()).data();
    }

    public void initTabList() {
        List<ActHomeVM.TabContent> list = this.tabContentList;
        if (list == null) {
            this.tabContentList = new ArrayList();
        } else {
            list.clear();
        }
        this.tabContentList.add(new ActHomeVM.TabContent(R.string.room, R.mipmap.ic_tab_room_sel, R.mipmap.ic_tab_room_default, new FtRoom()));
        this.tabContentList.add(new ActHomeVM.TabContent(R.string.intelligent, R.mipmap.ic_tab_smart_sel, R.mipmap.ic_tab_smart_default, new FtIntelligence()));
        this.tabContentList.add(new ActHomeVM.TabContent(R.string.my, R.mipmap.ic_tab_my_sel, R.mipmap.ic_tab_my_default, new FtMy()));
    }

    public List<ActHomeVM.TabContent> getTabContentList() {
        return this.tabContentList;
    }

    public void scan() {
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.2
            @Override // java.lang.Runnable
            public void run() {
                if (FtRoomVM.this.getCurPlace() == null || Injection.mesh().getConnectedDevice() != null) {
                    return;
                }
                Injection.mesh().checkNearbyDevice(FtRoomVM.this.getCurPlace().getMeshUUID(), FtRoomVM.this.getLifecycleOwner());
            }
        }, 1500L);
    }

    public void updateIvIndex(final int integer) {
        ((ObservableSubscribeProxy) Injection.net().updatePlaceIvIndex(this.placeId, integer).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.this.lambda$updateIvIndex$1(integer, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateIvIndex$1(int i, Object obj) throws Exception {
        Injection.repo().home().updatePlaceIvIndex(this.placeId, i);
    }

    public void setDeviceKey() {
        BleParam bleParam;
        List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(this.placeId);
        ArrayList arrayList = new ArrayList();
        for (Device device : deviceListByPlaceId) {
            if (device != null && device.getBtmodule().equalsIgnoreCase("bt200") && (bleParam = (BleParam) device.getParam(BleParam.class)) != null && !bleParam.isDeviceKeyStatus()) {
                LHomeLog.e(getClass(), device.getName() + "DeviceKeyStatus:" + bleParam.isDeviceKeyStatus());
                arrayList.add(setSingleDeviceKey(device));
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mObserver = Observable.concat(arrayList).subscribeWith(new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.3
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean aBoolean) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
                FtRoomVM.this.mDisposable = d2;
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                FtRoomVM.this.mDisposable.dispose();
            }
        });
    }

    private Observable<Boolean> setSingleDeviceKey(final Device device) {
        final Observable create = Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.4
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                BleParam bleParam = (BleParam) device.getParam(BleParam.class);
                if (bleParam != null) {
                    CmdAssistant.getSettingCmdAssistant(device, new int[0]).setDeviceKey(FtRoomVM.this.getContext(), bleParam.getDeviceKey().getBytes(), new AnonymousClass1(bleParam, emitter));
                } else {
                    emitter.onNext(false);
                    emitter.onComplete();
                }
            }

            /* renamed from: com.ltech.smarthome.ui.control.FtRoomVM$4$1, reason: invalid class name */
            class AnonymousClass1 implements IAction<Boolean> {
                final /* synthetic */ BleParam val$deviceParam;
                final /* synthetic */ ObservableEmitter val$emitter;

                static /* synthetic */ void lambda$act$0(Object obj) throws Exception {
                }

                AnonymousClass1(final BleParam val$deviceParam, final ObservableEmitter val$emitter) {
                    this.val$deviceParam = val$deviceParam;
                    this.val$emitter = val$emitter;
                }

                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    LHomeLog.e(getClass(), device.getName() + "设置DeviceKeyStatus:" + aBoolean);
                    if (aBoolean.booleanValue()) {
                        this.val$deviceParam.setDeviceKeyStatus(aBoolean.booleanValue());
                        ((ObservableSubscribeProxy) Injection.net().updateParam(device.getDeviceId(), GsonUtils.toJson(this.val$deviceParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(FtRoomVM.this.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$4$1$$ExternalSyntheticLambda0
                            @Override // io.reactivex.functions.Consumer
                            public final void accept(Object obj) {
                                FtRoomVM.AnonymousClass4.AnonymousClass1.lambda$act$0(obj);
                            }
                        }, new SmartErrorComsumer());
                    }
                    this.val$emitter.onNext(true);
                    this.val$emitter.onComplete();
                }
            }
        });
        return Observable.timer(150L, TimeUnit.MILLISECONDS).flatMap(new Function<Long, ObservableSource<Boolean>>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.5
            @Override // io.reactivex.functions.Function
            public ObservableSource<Boolean> apply(Long aLong) throws Exception {
                return create;
            }
        });
    }

    public static final class TabContent {
        private BaseNormalFragment fragment;
        private int selectIconRes;
        private int titleRes;
        private int unSelectIconRes;

        public TabContent(int titleRes, int selectIconRes, int unSelectIconRes, BaseNormalFragment fragment) {
            this.titleRes = titleRes;
            this.selectIconRes = selectIconRes;
            this.unSelectIconRes = unSelectIconRes;
            this.fragment = fragment;
        }

        public int getTitleRes() {
            return this.titleRes;
        }

        public int getSelectIconRes() {
            return this.selectIconRes;
        }

        public int getUnSelectIconRes() {
            return this.unSelectIconRes;
        }

        public BaseNormalFragment getFragment() {
            return this.fragment;
        }
    }

    public boolean setCurPlace(Context context, Place place) {
        if (place == null) {
            return false;
        }
        this.placeResult.setValue(place);
        Injection.repo().home().setSelectPlace(place);
        if (context == null) {
            new IFlyRepository().getAppToken(ActivityUtils.getTopActivity(), getLifecycleOwner(), place.getPlaceId());
            return true;
        }
        new IFlyRepository().getAppToken(context, getLifecycleOwner(), place.getPlaceId());
        return true;
    }

    public Place checkPlace(List<Place> placeList) {
        Place value = this.placeResult.getValue();
        if (value == null) {
            value = (Place) SharedPreferenceUtil.getBean(Constants.SELECT_PLACE, Place.class);
        }
        if (value != null) {
            for (Place place : placeList) {
                if (place.getPlaceId() == value.getPlaceId()) {
                    if (!place.getProvisionerAddress().equals(value.getProvisionerAddress()) || place.getRoleType() != value.getRoleType()) {
                        return place;
                    }
                    value.setPlaceName(place.getPlaceName());
                    value.setIvindex(place.getIvindex());
                    return value;
                }
            }
        }
        return placeList.get(0);
    }

    public Place getCurPlace() {
        return this.placeResult.getValue();
    }

    public Floor getCurFloor() {
        return this.floorResult.getValue();
    }

    public boolean setCurFloor(Floor floor) {
        this.floorResult.setValue(floor);
        if (floor == null) {
            return true;
        }
        Activity topActivity = ActivityUtils.getTopActivity();
        if (!(topActivity instanceof ActHome)) {
            return true;
        }
        ((ActHome) topActivity).setSelectFloorId(floor.getFloorId());
        return true;
    }

    public Floor checkFloor(List<Floor> floorList) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity instanceof ActHome) {
            ActHome actHome = (ActHome) topActivity;
            for (Floor floor : floorList) {
                if (floor.getFloorId() == actHome.getSelectFloorId()) {
                    return floor;
                }
            }
        }
        Floor value = this.floorResult.getValue();
        if (value != null) {
            for (Floor floor2 : floorList) {
                if (floor2.getFloorId() == value.getFloorId()) {
                    value.setFloorName(floor2.getFloorName());
                    return value;
                }
            }
        }
        return floorList.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        this.changePlaceEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        this.changeFloorEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4() {
        this.selectAllEvent.setValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5() {
        this.editFinishEvent.setValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6() {
        if (Boolean.TRUE.equals(this.editRoleMode.getValue())) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.selectRoleList.getValue().size(); i++) {
                arrayList.add(updateExt(this.selectRoleList.getValue().get(i), i, false));
            }
            if (arrayList.isEmpty()) {
                return;
            }
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
            batchControl(arrayList);
            return;
        }
        if (Boolean.TRUE.equals(this.editSceneMode.getValue())) {
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < this.selectSceneList.getValue().size(); i2++) {
                arrayList2.add(updateScene(this.selectSceneList.getValue().get(i2), i2));
            }
            if (arrayList2.isEmpty()) {
                return;
            }
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
            batchControl(arrayList2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7() {
        if (this.placeResult.getValue().isOwner() || this.placeResult.getValue().isManager()) {
            if (((BaseNormalActivity) ActivityUtils.getTopActivity()).bleIsOk()) {
                navigation(NavUtils.destination(ActAddDevice.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()).withLong(Constants.FLOOR_ID, getCurFloor() == null ? 0L : getCurFloor().getFloorId()).withLong(Constants.ROOM_ID, this.selectedRoomId));
                return;
            }
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8() {
        navigation(NavUtils.destination(ActSearchDevice.class).withLong(Constants.PLACE_ID, getCurPlace().getPlaceId()));
    }

    private Observable<Integer> updateExt(final Role role, final int position, final boolean isShow) {
        return Observable.create(new ObservableOnSubscribe<Integer>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.6
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Integer> observableEmitter) throws Exception {
                Role role2 = role;
                if (role2 instanceof Device) {
                    final Device device = (Device) role2;
                    try {
                        JSONObject jSONObject = device.getExtParam() != null ? new JSONObject(device.getExtParam()) : new JSONObject();
                        jSONObject.put("hideDevice", !isShow ? 1 : 0);
                        device.setExtParam(jSONObject.toString());
                        Injection.net().updateParamExt(device.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.6.1
                            @Override // io.reactivex.Observer
                            public void onNext(Object o) {
                            }

                            @Override // io.reactivex.Observer
                            public void onError(Throwable e) {
                                observableEmitter.onNext(-1);
                                observableEmitter.onComplete();
                            }

                            @Override // io.reactivex.Observer
                            public void onComplete() {
                                Injection.repo().device().saveDevice(device);
                                observableEmitter.onNext(Integer.valueOf(position));
                                observableEmitter.onComplete();
                            }
                        });
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (role2 instanceof Group) {
                    final Group group = (Group) role2;
                    try {
                        JSONObject jSONObject2 = group.getExtParam() != null ? new JSONObject(group.getExtParam()) : new JSONObject();
                        jSONObject2.put("hideDevice", !isShow ? 1 : 0);
                        group.setExtParam(jSONObject2.toString());
                        Injection.net().updateGroupParamExt(group.getGroupId(), jSONObject2.toString()).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.6.2
                            @Override // io.reactivex.Observer
                            public void onNext(Object o) {
                            }

                            @Override // io.reactivex.Observer
                            public void onError(Throwable e2) {
                                observableEmitter.onNext(-1);
                                observableEmitter.onComplete();
                            }

                            @Override // io.reactivex.Observer
                            public void onComplete() {
                                Injection.repo().group().saveGroup(group);
                                observableEmitter.onNext(Integer.valueOf(position));
                                observableEmitter.onComplete();
                            }
                        });
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
    }

    public void editFinish(List<Role> curAllRoleList, List<Scene> curAllSceneList) {
        if (Boolean.TRUE.equals(this.editRoleMode.getValue())) {
            editDeviceFinish(curAllRoleList);
        } else if (Boolean.TRUE.equals(this.editSceneMode.getValue())) {
            editSceneFinish(curAllSceneList);
        }
    }

    private void editDeviceFinish(List<Role> curAllRoleList) {
        if (curAllRoleList.isEmpty()) {
            this.editRoleMode.setValue(false);
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        final ArrayList arrayList4 = new ArrayList();
        for (int i = 0; i < curAllRoleList.size(); i++) {
            Role role = curAllRoleList.get(i);
            long objectId = role.getObjectId();
            if (role instanceof Device) {
                Device device = (Device) role;
                int i2 = i + 1;
                device.setIndex(i2);
                arrayList3.add(device);
                arrayList.add(new SortDeviceAndGroupRequest.DeviceSortBean(objectId, i2));
            } else if (role instanceof Group) {
                Group group = (Group) role;
                int i3 = i + 1;
                group.setGroupIndex(i3);
                arrayList4.add(group);
                arrayList2.add(new SortDeviceAndGroupRequest.GroupSortBean(objectId, i3));
            }
        }
        ((ObservableSubscribeProxy) Injection.net().sortDeviceAndGroup(new SortDeviceAndGroupRequest(arrayList, arrayList2)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.this.lambda$editDeviceFinish$9((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new FtRoomVM$$ExternalSyntheticLambda13(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.this.lambda$editDeviceFinish$10(arrayList3, arrayList4, obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.7
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                super.action(throwable);
                FtRoomVM.this.selectRoleList.setValue(new ArrayList());
                FtRoomVM.this.editRoleMode.setValue(false);
                FtRoomVM.this.checkDevStatus.call();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editDeviceFinish$9(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editDeviceFinish$10(List list, List list2, Object obj) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            Injection.repo().device().saveDevice((Device) list.get(i));
        }
        for (int i2 = 0; i2 < list2.size(); i2++) {
            Injection.repo().group().saveGroup((Group) list2.get(i2));
        }
        this.selectRoleList.setValue(new ArrayList());
        this.editRoleMode.setValue(false);
        SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.save_success));
        this.checkDevStatus.call();
    }

    private void editSceneFinish(final List<Scene> curAllSceneList) {
        if (curAllSceneList.isEmpty()) {
            this.editSceneMode.setValue(false);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < curAllSceneList.size(); i++) {
            SortSceneRequest.SceneSortBean sceneSortBean = new SortSceneRequest.SceneSortBean();
            sceneSortBean.sceneid = curAllSceneList.get(i).getSceneId();
            arrayList.add(sceneSortBean);
        }
        ((ObservableSubscribeProxy) Injection.net().sortScene(arrayList).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.this.lambda$editSceneFinish$11((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new FtRoomVM$$ExternalSyntheticLambda13(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.this.lambda$editSceneFinish$12(curAllSceneList, obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.8
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                super.action(throwable);
                FtRoomVM.this.selectSceneList.setValue(new ArrayList());
                FtRoomVM.this.editSceneMode.setValue(false);
                FtRoomVM.this.checkDevStatus.call();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editSceneFinish$11(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editSceneFinish$12(List list, Object obj) throws Exception {
        Injection.repo().scene().sortScene(list);
        this.selectSceneList.setValue(new ArrayList());
        this.editSceneMode.setValue(false);
        SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.save_success));
        this.checkDevStatus.call();
    }

    public void selectAll(List<Role> curAllRoleList, List<Scene> curAllSceneList) {
        if (Boolean.TRUE.equals(this.editRoleMode.getValue())) {
            if (this.selectRoleList.getValue() != null) {
                if (this.selectRoleList.getValue().size() == curAllRoleList.size()) {
                    this.selectRoleList.setValue(new ArrayList());
                } else {
                    this.selectRoleList.setValue(new ArrayList(curAllRoleList));
                }
                this.refreshAllDeviceEvent.setValue(true);
                return;
            }
            return;
        }
        if (!Boolean.TRUE.equals(this.editSceneMode.getValue()) || this.selectSceneList.getValue() == null) {
            return;
        }
        if (this.selectSceneList.getValue().size() == curAllSceneList.size()) {
            this.selectSceneList.setValue(new ArrayList());
        } else {
            this.selectSceneList.setValue(new ArrayList(curAllSceneList));
        }
        this.refreshAllSceneEvent.setValue(true);
    }

    private Observable<Integer> updateScene(final Scene scene, final int position) {
        return Observable.create(new ObservableOnSubscribe<Integer>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.9
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                Injection.net().setCommonScene(scene.getSceneId(), false).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.9.1
                    @Override // io.reactivex.Observer
                    public void onNext(Object o) {
                    }

                    @Override // io.reactivex.Observer
                    public void onError(Throwable e) {
                        emitter.onNext(-1);
                        emitter.onComplete();
                    }

                    @Override // io.reactivex.Observer
                    public void onComplete() {
                        scene.setCommon(false);
                        Injection.repo().scene().saveScene(scene);
                        emitter.onNext(Integer.valueOf(position));
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    private void batchControl(List<Observable<Integer>> request) {
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.10
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Integer i) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (FtRoomVM.this.editRoleMode.getValue().booleanValue()) {
                    FtRoomVM.this.selectRoleList.setValue(new ArrayList());
                    FtRoomVM.this.loadData();
                    FtRoomVM.this.dismissLoadingDialog();
                    SmartToast.showCenterShort(ActivityUtils.getTopActivity().getString(R.string.hide_tips));
                    return;
                }
                if (FtRoomVM.this.editSceneMode.getValue().booleanValue()) {
                    FtRoomVM.this.selectSceneList.setValue(new ArrayList());
                    FtRoomVM.this.dismissLoadingDialog();
                    FtRoomVM.this.showSuccessTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_success));
                }
            }
        });
    }

    public boolean initRoomList(Context context, List<Room> list, boolean showAllDev) {
        boolean z = false;
        if (getCurPlace() == null) {
            return false;
        }
        if (list.size() == 0) {
            ArrayList arrayList = new ArrayList();
            this.mRoomList = arrayList;
            arrayList.add(new RoomItem(getCurPlace().getPlaceId(), -1L, -1, context.getString(R.string.app_str_not_distribution)));
            return true;
        }
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap = new HashMap();
        for (RoomItem roomItem : this.mRoomList) {
            hashMap.put(Long.valueOf(roomItem.getRoomId()), roomItem);
        }
        List<Device> deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(getCurPlace().getPlaceId(), 0L, -1L);
        List<Group> groupListByRoomIdFromDb = Injection.repo().group().getGroupListByRoomIdFromDb(getCurPlace().getPlaceId(), 0L, -1L);
        if (Injection.repo().scene().getSceneListByRoomId(getCurPlace().getPlaceId(), 0L, -1L, true).size() > 0) {
            arrayList2.add(new RoomItem(getCurPlace().getPlaceId(), 0L, -1, context.getString(R.string.app_str_not_distribution)));
        } else if (groupListByRoomIdFromDb.size() > 0) {
            arrayList2.add(new RoomItem(getCurPlace().getPlaceId(), 0L, -1, context.getString(R.string.app_str_not_distribution)));
        } else if (deviceListByRoomIdFromDb.size() > 0) {
            Iterator<Device> it = deviceListByRoomIdFromDb.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (filterDevice(it.next())) {
                    arrayList2.add(new RoomItem(getCurPlace().getPlaceId(), 0L, -1, context.getString(R.string.app_str_not_distribution)));
                    break;
                }
            }
        }
        for (Room room : list) {
            if (hashMap.containsKey(Long.valueOf(room.getRoomId()))) {
                RoomItem roomItem2 = (RoomItem) hashMap.get(Long.valueOf(room.getRoomId()));
                if (roomItem2 != null) {
                    if (!room.getRoomName().equals(roomItem2.getRoomName())) {
                        roomItem2.setRoomName(room.getRoomName());
                        z = true;
                    }
                    if (room.getIndex() != roomItem2.getIndex()) {
                        roomItem2.setIndex(room.getIndex());
                        z = true;
                    }
                    arrayList2.add(roomItem2);
                }
            } else {
                arrayList2.add(new RoomItem(getCurPlace().getPlaceId(), room.getRoomId(), room.getIndex(), room.getRoomName()));
                z = true;
            }
        }
        boolean z2 = arrayList2.size() == this.mRoomList.size() ? z : true;
        this.mRoomList = arrayList2;
        return z2;
    }

    public void threadPoolClean() {
        ArrayList<QueryDeviceStateRunnable> arrayList = this.runnables;
        if (arrayList != null) {
            Iterator<QueryDeviceStateRunnable> it = arrayList.iterator();
            while (it.hasNext()) {
                QueryDeviceStateRunnable next = it.next();
                next.setStop();
                ThreadPoolManager.getInstance().remove(next);
            }
            this.runnables = null;
        }
    }

    public static class RoomItem {
        private FtDevice2 ftDevice;
        private int index;
        private long placeId;
        private long roomId;
        private String roomName;

        public RoomItem(long placeId, long roomId, int index, String roomName) {
            this.placeId = placeId;
            this.roomId = roomId;
            this.roomName = roomName;
            this.index = index;
            this.ftDevice = FtDevice2.newInstance(placeId, roomId);
        }

        public long getRoomId() {
            return this.roomId;
        }

        public long getPlaceId() {
            return this.placeId;
        }

        public String getRoomName() {
            return this.roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public FtDevice2 getFtDevice() {
            return this.ftDevice;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public void addDevice(long roomId) {
        if (getCurrentPlace().isOwner() || getCurrentPlace().isManager()) {
            if (((BaseNormalActivity) ActivityUtils.getTopActivity()).bleIsOk()) {
                navigation(NavUtils.destination(ActAddDevice.class).withLong(Constants.PLACE_ID, this.placeId).withLong(Constants.ROOM_ID, roomId));
                return;
            }
            return;
        }
        this.showNoPermissionDialogEvent.call();
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void init() {
        if (this.roleResult == null) {
            this.isChecking = false;
            this.isFirst = true;
            ThreadPoolManager.getInstance().createThreadPool();
            initDeviceStateListener();
            initListener();
            this.roleResult = new MutableLiveData<>();
            loadData();
        }
    }

    public void loadData() {
        checkMeshGatwayStatus(null);
        SharedPreferenceUtil.edit().keepShared(Constants.DEVICE_CHANGED, false);
        ((ObservableSubscribeProxy) Injection.repo().role().getRoleList(this.placeId, -1L, -1L).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<List<Role>>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.11
            @Override // io.reactivex.functions.Consumer
            public void accept(List<Role> roles) throws Exception {
                FtRoomVM.this.isFirst = true;
                FtRoomVM.this.roleResult.setValue(Resource.success(roles));
                FtRoomVM.this.addUnConfigRoomEvent.call();
                FtRoomVM.this.initMeshEvent.call();
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.this.lambda$loadData$13((Throwable) obj);
            }
        });
        ((ObservableSubscribeProxy) Injection.repo().scene().getAllScene(this.placeId).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<List<Scene>>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.12
            @Override // io.reactivex.functions.Consumer
            public void accept(List<Scene> roles) throws Exception {
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.lambda$loadData$14((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadData$13(Throwable th) throws Exception {
        this.initMeshEvent.call();
    }

    private void initListener() {
        Injection.mesh().observeTestSequence(new ITestSequenceCallback() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.13
            @Override // com.ltech.smarthome.blemesh.ITestSequenceCallback
            public void onTestSuccess() {
                FtRoomVM.this.starPool();
                FtRoomVM.this.setDeviceKey();
            }

            @Override // com.ltech.smarthome.blemesh.ITestSequenceCallback
            public void onTestFail() {
                Injection.mesh().disconnect();
                Injection.mesh().reCheckNearbyDevice(FtRoomVM.this.getCurPlace().getMeshUUID(), FtRoomVM.this.getLifecycleOwner());
            }

            @Override // com.ltech.smarthome.blemesh.ITestSequenceCallback
            public void onBleError(int code) {
                ExtendedBluetoothDevice connectableDevice = Injection.mesh().getConnectableDevice();
                if (connectableDevice != null) {
                    Injection.mesh().clearConnectableDevice(connectableDevice);
                }
                FtRoomVM.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.13.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Injection.mesh().checkNearbyDevice(FtRoomVM.this.getCurPlace().getMeshUUID(), FtRoomVM.this.getLifecycleOwner());
                    }
                }, 1500L);
            }

            @Override // com.ltech.smarthome.blemesh.ITestSequenceCallback
            public void stopTestSequence() {
                if (FtRoomVM.this.mObserver != null) {
                    FtRoomVM.this.mObserver.onError(new Throwable());
                }
                FtRoomVM.this.needCheckDeviceStatus = true;
            }
        });
    }

    public void starPool() {
        this.needCheckDeviceStatus = false;
        this.checkDevStatus.call();
    }

    private void initDeviceStateListener() {
        MessageManager.getInstance().setUpdateDeviceStatus(new MessageManager.UpdateDeviceStatus() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda15
            @Override // com.smart.message.MessageManager.UpdateDeviceStatus
            public final void update(int i, int i2, int i3, int i4, boolean z) {
                FtRoomVM.this.lambda$initDeviceStateListener$15(i, i2, i3, i4, z);
            }
        });
        MessageManager.getInstance().setLightCtStatusCallBack(new MessageManager.LightStatusCallBack() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda16
            @Override // com.smart.message.MessageManager.LightStatusCallBack
            public final void onDataReceive(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
                FtRoomVM.this.lambda$initDeviceStateListener$16(i, z, i2, i3, i4, z2, z3, z4, i5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceStateListener$15(final int i, final int i2, final int i3, final int i4, final boolean z) {
        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.14
            @Override // com.blankj.utilcode.util.ThreadUtils.Task
            public void onSuccess(Boolean result) {
            }

            @Override // com.blankj.utilcode.util.ThreadUtils.Task
            public Boolean doInBackground() throws Throwable {
                int i5;
                List<Device> arrayList = new ArrayList<>();
                List<Group> arrayList2 = new ArrayList<>();
                if (i2 > 49152) {
                    arrayList2 = Injection.repo().group().queryGroupByUnicastAddress(FtRoomVM.this.placeId, i2);
                    for (Group group : arrayList2) {
                        if (FtRoomVM.this.needGroupCheckMap.containsKey(Long.valueOf(group.getGroupId()))) {
                            FtRoomVM.this.needGroupCheckMap.remove(Long.valueOf(group.getGroupId()));
                        }
                    }
                } else {
                    arrayList = Injection.repo().device().getDeviceByUnicastAddress(FtRoomVM.this.placeId, i2);
                    if (!arrayList.isEmpty()) {
                        arrayList2 = Injection.repo().group().getGroupByDeviceUnicastAddress(FtRoomVM.this.placeId, i2);
                        if (!arrayList2.isEmpty()) {
                            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                                Group group2 = arrayList2.get(size);
                                if (group2.getLeaderSup() == 1) {
                                    arrayList2.remove(group2);
                                }
                            }
                        }
                    }
                }
                ArrayList<Role> arrayList3 = new ArrayList();
                arrayList3.addAll(arrayList);
                arrayList3.addAll(arrayList2);
                for (int i6 = 0; i6 < arrayList3.size(); i6++) {
                    try {
                        Role role = (Role) arrayList3.get(i6);
                        if (role instanceof Device) {
                            Device device = (Device) role;
                            int lightColorType = ProductRepository.getLightColorType((Object) device);
                            if ((lightColorType != 101 && lightColorType != 102 && lightColorType != 103 && lightColorType != 104 && lightColorType != 105) || i == DaliProHelper.getDeviceAddress(device)) {
                                ProductInfo bleProductInfoByType = ProductRepository.getBleProductInfoByType(device);
                                if (RelaySeparationHelper.isRelaySeparationSub(device)) {
                                    int relayNum = RelaySeparationHelper.getRelayNum(device);
                                    if (relayNum > 0) {
                                        if (!RelaySeparationHelper.isPanelRelay(device) && (bleProductInfoByType == null || !bleProductInfoByType.getSubProductKey().equals(ProductId.BLE_SWITCH_SUB_TYPE_KBS1))) {
                                            if (RelaySeparationHelper.isSwitchRelay(device)) {
                                                device.getDeviceState().setOn(((i4 >> (relayNum + (-1))) & 1) == 1);
                                            }
                                        }
                                        device.getDeviceState().setOn(((i4 >> (relayNum + (-1))) & 1) == 1);
                                    }
                                } else if (device.getProductId() != null && (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO))) {
                                    int i7 = i3;
                                    if (i7 == 0) {
                                        device.getDeviceState().setOn(false);
                                        ArrayList arrayList4 = new ArrayList();
                                        arrayList4.add(false);
                                        device.getDeviceState().setOnOffStates(arrayList4);
                                    } else if (i7 == 1) {
                                        device.getDeviceState().setOn(true);
                                        ArrayList arrayList5 = new ArrayList();
                                        arrayList5.add(true);
                                        device.getDeviceState().setOnOffStates(arrayList5);
                                    }
                                } else {
                                    if (!ProductId.CENTRAL_AIR_SUB_DEVICE.equals(device.getProductId()) && !ProductId.FRESH_AIR_SUB_DEVICE.equals(device.getProductId()) && !ProductId.FLOOR_HEAT_SUB_DEVICE.equals(device.getProductId())) {
                                        int i8 = i3;
                                        if (i8 == 0) {
                                            device.getDeviceState().setOn(false);
                                        } else if (i8 == 1) {
                                            device.getDeviceState().setOn(true);
                                        }
                                    }
                                    if (ProductId.FRESH_AIR_SUB_DEVICE.equals(device.getProductId())) {
                                        i5 = 81;
                                    } else {
                                        i5 = ProductId.FLOOR_HEAT_SUB_DEVICE.equals(device.getProductId()) ? 82 : 80;
                                    }
                                    CentralAirSubDeviceParam centralAirSubDeviceParam = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
                                    if ((centralAirSubDeviceParam.outAddr << 8) + centralAirSubDeviceParam.inAddr + (i5 << 4) == i4) {
                                        centralAirSubDeviceParam.on = i3;
                                    }
                                    device.setParam(centralAirSubDeviceParam);
                                }
                                if (!z) {
                                    device.setOnlineFlag(1);
                                    device.getDeviceState().setOnlineState(1);
                                    device.setCheckTime(System.currentTimeMillis());
                                }
                                Injection.repo().device().saveDevice(device);
                                FtRoomVM.this.freshDeviceData.postValue(device);
                            }
                        } else if (role instanceof Group) {
                            Group group3 = (Group) role;
                            if (i2 > 49152 && group3.getLeaderSup() == 0) {
                                group3.setLeaderSup(1);
                                FtRoomVM.this.updateGroupLeaderSup(group3, 1);
                            }
                            if (ProductRepository.getLightColorType((Object) group3) == 8) {
                                int i9 = i3;
                                if (i9 == 0) {
                                    group3.getGroupState().setOn(false);
                                    ArrayList arrayList6 = new ArrayList();
                                    arrayList6.add(false);
                                    group3.getGroupState().setOnOffStates(arrayList6);
                                } else if (i9 == 1) {
                                    group3.getGroupState().setOn(true);
                                    ArrayList arrayList7 = new ArrayList();
                                    arrayList7.add(true);
                                    group3.getGroupState().setOnOffStates(arrayList7);
                                }
                            } else if (RelaySeparationHelper.isRelaySeparationSub(group3)) {
                                int relayNum2 = RelaySeparationHelper.getRelayNum(group3);
                                if (relayNum2 > 0 && RelaySeparationHelper.isPanelRelay(group3)) {
                                    group3.getGroupState().setOn(((i4 >> (relayNum2 + (-1))) & 1) == 1);
                                }
                            } else {
                                int i10 = i3;
                                if (i10 == 0) {
                                    group3.getGroupState().setOn(false);
                                } else if (i10 == 1) {
                                    group3.getGroupState().setOn(true);
                                }
                            }
                            if (!z) {
                                group3.getGroupState().setOnlineState(1);
                                group3.setCheckTime(System.currentTimeMillis());
                            }
                            Injection.repo().group().saveGroup(group3);
                            FtRoomVM.this.freshDeviceData.postValue(group3);
                        }
                    } catch (Exception e) {
                        LHomeLog.e(getClass(), "参数错误=" + e.toString());
                    }
                }
                if (!z) {
                    for (Role role2 : arrayList3) {
                        if (role2 instanceof Device) {
                            Device device2 = (Device) role2;
                            if (!device2.isSubDevice()) {
                                FtRoomVM.this.changeSubDeviceOnlineStatus(device2);
                            }
                        }
                    }
                }
                FtRoomVM.this.safeHandler.removeMessages(i2);
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceStateListener$16(int i, boolean z, int i2, int i3, int i4, boolean z2, boolean z3, boolean z4, int i5) {
        if (z3) {
            List<Device> deviceByUnicastAddress = Injection.repo().device().getDeviceByUnicastAddress(getCurPlace().getPlaceId(), i);
            for (Device device : deviceByUnicastAddress) {
                if (!ProductId.ID_BLE_KBS.equals(device.getProductId())) {
                    if (ProductRepository.getLightColorType((Object) device) != 20) {
                        checkLightCtRange(device);
                    }
                    device.getDeviceState().setRhythmPlay(z4);
                }
            }
            Injection.repo().device().saveDevice(deviceByUnicastAddress);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateGroupLeaderSup(final Group group, int i) {
        Injection.net().updateGroupLeaderSup(group.getGroupId(), i).compose(RxUtils.io_io()).subscribe(new DisposableObserver<Object>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.15
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Object o) {
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                Injection.repo().group().saveGroup(group);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeSubDeviceOnlineStatus(Device dev) {
        for (Device device : Injection.repo().device().queryDevicesByMacDeviceId(Long.valueOf(dev.getDeviceId()))) {
            if (device.isSubDevice()) {
                device.getDeviceState().setOnlineState(dev.getDeviceState().getOnlineState());
                Injection.repo().device().saveDevice(device);
                this.freshDeviceData.postValue(device);
            }
        }
    }

    @Override // com.ltech.smarthome.model.filter.DeviceFilter
    public boolean filterDevice(Device device) {
        Device deviceByDeviceId;
        if (device.getProductId() == null) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "3486586935738368":
            case "121120911474901":
            case "3683369128495808":
            case "4249823578721536":
            case "3701704216101056":
            case "221042516351701":
            case "123072510445601":
            case "121061709483801":
            case "221030816330401":
            case "120042616091901":
            case "120042616094101":
            case "120042616101901":
            case "120042616103901":
            case "3701703750123712":
            case "3486587348451328":
            case "3842335314313472":
            case "3486587769094144":
            case "121042516340801":
            case "121042516345401":
                return device.getParam() == null || device.getParam(BleParam.class) == null || ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            case "120122111301201":
                return !RelaySeparationHelper.isPanelRelay(device) || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid())) == null || deviceByDeviceId.getParam() == null || deviceByDeviceId.getParam(BleParam.class) == null || ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getGroupId() == 0;
            default:
                return true;
        }
    }

    public void filterDeviceByRoom(long roomId, IAction<RoleData> iAction) {
        List<Device> deviceListByRoomIdFromDb;
        int i;
        long j;
        List<Group> groupListByRoomIdFromDb;
        ArrayList arrayList = new ArrayList();
        if (roomId == 0) {
            deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(this.placeId, 0L, roomId);
        } else {
            deviceListByRoomIdFromDb = Injection.repo().device().getDeviceListByRoomIdFromDb(this.placeId, -1L, roomId);
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator<Device> it = deviceListByRoomIdFromDb.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Device next = it.next();
            if (next.getProductId().equals(ProductId.ID_WIFI_CAMERA)) {
                arrayList3.add(next);
            } else if (filterDevice(next)) {
                arrayList2.add(next);
            }
            ProductInfo bleProductInfoByType = ProductRepository.getBleProductInfoByType(next);
            if (!RelaySeparationHelper.isRelaySeparationSub(next)) {
                if (ProductRepository.needCheckOnlineState(next.getProductId(), bleProductInfoByType == null ? "" : bleProductInfoByType.getSubProductKey()) && !next.getReportinstruct().isEmpty()) {
                    MessageManager.getInstance().handleCloudBlueData(next.getReportinstruct());
                    next.setReportinstruct("");
                    Injection.repo().device().saveDevice(next);
                }
            }
        }
        if (roomId == 0) {
            j = roomId;
            groupListByRoomIdFromDb = Injection.repo().group().getGroupListByRoomIdFromDb(this.placeId, 0L, j);
        } else {
            j = roomId;
            groupListByRoomIdFromDb = Injection.repo().group().getGroupListByRoomIdFromDb(this.placeId, -1L, j);
        }
        for (Group group : groupListByRoomIdFromDb) {
            if (ProductRepository.needCheckOnlineState(group) && group.getReportinstruct() != null && !group.getReportinstruct().isEmpty()) {
                MessageManager.getInstance().handleCloudBlueData(group.getReportinstruct());
                group.setReportinstruct("");
                Injection.repo().group().saveGroup(group);
            }
        }
        arrayList.addAll(arrayList2);
        arrayList.addAll(groupListByRoomIdFromDb);
        Collections.sort(arrayList3, new Comparator<Role>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.16
            @Override // java.util.Comparator
            public int compare(Role o1, Role o2) {
                if (o1.getIndex() > o2.getIndex()) {
                    return 1;
                }
                if (o1.getIndex() < o2.getIndex()) {
                    return -1;
                }
                if (o1.getCreatetime() == null || o2.getCreatetime() == null) {
                    return 0;
                }
                return o1.getCreatetime().compareTo(o2.getCreatetime());
            }
        });
        Collections.sort(arrayList, new Comparator<Role>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.17
            @Override // java.util.Comparator
            public int compare(Role o1, Role o2) {
                if (o1.getIndex() > o2.getIndex()) {
                    return 1;
                }
                if (o1.getIndex() < o2.getIndex()) {
                    return -1;
                }
                if (o1.getCreatetime() == null || o2.getCreatetime() == null) {
                    return 0;
                }
                int compareTo = o1.getCreatetime().compareTo(o2.getCreatetime());
                if (compareTo != 0) {
                    return compareTo;
                }
                if (o1 instanceof Device) {
                    Device device = (Device) o1;
                    if (o2 instanceof Device) {
                        Device device2 = (Device) o2;
                        if (device.getWifiMac() == null || device2.getWifiMac() == null) {
                            return 0;
                        }
                        return device.getWifiMac().compareTo(device2.getWifiMac());
                    }
                }
                if (!(o1 instanceof Group)) {
                    return 0;
                }
                Group group2 = (Group) o1;
                if (o2 instanceof Group) {
                    return group2.getSubkey() - ((Group) o2).getSubkey();
                }
                return 0;
            }
        });
        arrayList.addAll(0, arrayList3);
        ArrayList arrayList4 = new ArrayList();
        for (i = 0; i < arrayList.size(); i++) {
            Role role = (Role) arrayList.get(i);
            if (role.getHideDevice() == 0 && role.getDaliHidden() == 0) {
                arrayList4.add(role);
            }
        }
        RoleData roleData = new RoleData(j, arrayList4);
        if (iAction != null) {
            iAction.act(roleData);
        }
    }

    public void filterSceneByRoom(long roomId, IAction<SceneData> iAction) {
        long j;
        List<Scene> sceneListByRoomId;
        if (roomId == 0) {
            sceneListByRoomId = Injection.repo().scene().getSceneListByRoomId(this.placeId, 0L, roomId, true);
            j = roomId;
        } else {
            j = roomId;
            sceneListByRoomId = Injection.repo().scene().getSceneListByRoomId(this.placeId, -1L, j, true);
        }
        SceneData sceneData = new SceneData(j, sceneListByRoomId);
        if (iAction != null) {
            iAction.act(sceneData);
        }
    }

    public void checkDeviceStatus(List<Role> roles) {
        if (roles == null) {
            return;
        }
        Disposable disposable = this.mDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.isChecking = false;
        LHomeLog.e("checkState", getClass(), "开始查询");
        this.needCheckMap = new HashMap<>();
        this.needGroupCheckMap = new HashMap<>();
        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            if (role instanceof Device) {
                Device device = (Device) role;
                ProductInfo bleProductInfoByType = ProductRepository.getBleProductInfoByType(device);
                if (ProductRepository.needCheckOnlineState(device.getProductId(), bleProductInfoByType == null ? "" : bleProductInfoByType.getSubProductKey()) && (this.isFirst || System.currentTimeMillis() - device.getCheckTime() > 8000)) {
                    LHomeLog.e("checkState", getClass(), "查询设备=" + device.getName());
                    if (RelaySeparationHelper.isRelaySeparationSub(device)) {
                        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid());
                        if (deviceByDeviceId != null) {
                            this.needCheckMap.put(Long.valueOf(deviceByDeviceId.getDeviceId()), deviceByDeviceId);
                        }
                    } else {
                        this.needCheckMap.put(Long.valueOf(device.getDeviceId()), device);
                    }
                }
            } else if (role instanceof Group) {
                Group group = (Group) role;
                if (ProductRepository.needCheckOnlineState(group) && (this.isFirst || System.currentTimeMillis() - group.getCheckTime() > 8000)) {
                    LHomeLog.e("checkState", getClass(), "查询群组=" + group.getName());
                    this.needGroupCheckMap.put(Long.valueOf(group.getGroupId()), group);
                }
            }
        }
        this.isFirst = false;
        LHomeLog.e("checkState", getClass(), "共需要查询设备：" + this.needCheckMap.size() + "，群组：" + this.needGroupCheckMap.size());
        if (this.needCheckMap.isEmpty() && this.needGroupCheckMap.isEmpty()) {
            return;
        }
        if (this.safeHandler == null) {
            this.safeHandler = new SafeHandler(this);
        }
        maxRetryTimes = 3;
        if (this.isChecking) {
            return;
        }
        mergeCheckStatusRequest();
    }

    public void cleanCheck() {
        Disposable disposable = this.mDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeCheckStatusRequest() {
        this.tempnum = 0;
        this.isChecking = true;
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Long, Device>> it = this.needCheckMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(checkSingleDeviceStatus(it.next().getValue()));
        }
        Iterator<Map.Entry<Long, Group>> it2 = this.needGroupCheckMap.entrySet().iterator();
        while (it2.hasNext()) {
            arrayList.add(checkSingleGroupStatus(it2.next().getValue()));
        }
        LHomeLog.e("checkDEV", getClass(), "need=" + this.needCheckMap.size());
        LHomeLog.e("checkGroup", getClass(), "need=" + this.needGroupCheckMap.size());
        this.mObserver = Observable.concat(arrayList).subscribeWith(new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.18
            @Override // io.reactivex.Observer
            public void onNext(Boolean aBoolean) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
                FtRoomVM.this.mDisposable = d2;
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                FtRoomVM.this.isChecking = false;
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.18.1
                    @Override // java.lang.Runnable
                    public void run() {
                        FtRoomVM.this.isChecking = false;
                        if (!FtRoomVM.this.needCheckMap.isEmpty() && !FtRoomVM.this.needGroupCheckMap.isEmpty() && FtRoomVM.maxRetryTimes > 0) {
                            FtRoomVM.maxRetryTimes--;
                            FtRoomVM.this.mergeCheckStatusRequest();
                            return;
                        }
                        LHomeLog.e("checkDEV", getClass(), "end=" + FtRoomVM.this.needCheckMap.size());
                        LHomeLog.e("checkGroup", getClass(), "end=" + FtRoomVM.this.needGroupCheckMap.size());
                        if (FtRoomVM.this.needGroupCheckMap.isEmpty()) {
                            return;
                        }
                        FtRoomVM.maxRetryTimes = 2;
                        FtRoomVM.this.needCheckMap = new HashMap();
                        Iterator it3 = FtRoomVM.this.needGroupCheckMap.entrySet().iterator();
                        while (it3.hasNext()) {
                            Group group = (Group) ((Map.Entry) it3.next()).getValue();
                            List<Device> deviceByUnicastAddress = Injection.repo().device().getDeviceByUnicastAddress(group.getPlaceId(), group.getFirstDevUniAddr());
                            if (!deviceByUnicastAddress.isEmpty()) {
                                FtRoomVM.this.needCheckMap.put(Long.valueOf(deviceByUnicastAddress.get(0).getDeviceId()), deviceByUnicastAddress.get(0));
                            }
                        }
                        FtRoomVM.this.needGroupCheckMap = new HashMap();
                        FtRoomVM.this.mergeCheckStatusRequest();
                    }
                }, 1500L);
            }
        });
    }

    private Observable<Boolean> checkSingleDeviceStatus(final Device device) {
        final Observable create = Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.19
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                Device device2 = device;
                BleParam bleParam = (BleParam) device2.getParam(BleParam.class);
                if (bleParam == null) {
                    emitter.onNext(false);
                    emitter.onComplete();
                    return;
                }
                BaseCtrlPackage convert = SmartUtils.getICtrlConverter().convert(device);
                BaseCmd querDeviceState = CmdBleFactory.querDeviceState(0, bleParam.getUnicastAddress());
                if (ProductId.CENTRAL_AIR_SUB_DEVICE.equals(device.getProductId())) {
                    CentralAirSubDeviceParam centralAirSubDeviceParam = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
                    querDeviceState = CmdBleFactory.queryCentralAirState(centralAirSubDeviceParam.getOutAddr(), centralAirSubDeviceParam.getInAddr());
                } else if (ProductId.FRESH_AIR_SUB_DEVICE.equals(device.getProductId())) {
                    CentralAirSubDeviceParam centralAirSubDeviceParam2 = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
                    querDeviceState = CmdBleFactory.queryFreshAirState(centralAirSubDeviceParam2.getOutAddr(), centralAirSubDeviceParam2.getInAddr());
                } else if (ProductId.FLOOR_HEAT_SUB_DEVICE.equals(device.getProductId())) {
                    CentralAirSubDeviceParam centralAirSubDeviceParam3 = (CentralAirSubDeviceParam) device.getParam(CentralAirSubDeviceParam.class);
                    querDeviceState = CmdBleFactory.queryFloorHeatState(centralAirSubDeviceParam3.getOutAddr(), centralAirSubDeviceParam3.getInAddr());
                }
                MessageManager.getInstance().addDeviceQueryResult(bleParam.getUnicastAddress(), new MessageManager.UpdateQuery() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.19.1
                    @Override // com.smart.message.MessageManager.UpdateQuery
                    public void update(int uniAddress) {
                        Class<?> cls = getClass();
                        StringBuilder sb = new StringBuilder("return=");
                        sb.append(uniAddress);
                        sb.append("  num=");
                        FtRoomVM ftRoomVM = FtRoomVM.this;
                        int i = ftRoomVM.tempnum;
                        ftRoomVM.tempnum = i + 1;
                        sb.append(i);
                        LHomeLog.e("checkDEV", cls, sb.toString());
                        MessageManager.getInstance().removeDeviceQueryResult(uniAddress);
                        FtRoomVM.this.safeHandler.removeMessages(uniAddress);
                        Message message = new Message();
                        message.arg1 = 1;
                        message.what = uniAddress;
                        FtRoomVM.this.safeHandler.sendMessage(message);
                    }
                });
                LHomeLog.e("checkDEV", getClass(), "send=" + bleParam.getUnicastAddress());
                SmartUtils.send(Emitter.MIX_BLE_IOT, convert, querDeviceState, new ISendResutCallback(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.19.2
                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultError() {
                    }

                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultSuccess(boolean isIot) {
                    }
                });
                FtRoomVM.this.safeHandler.removeMessages(bleParam.getUnicastAddress());
                Message message = new Message();
                if (System.currentTimeMillis() - device2.getCheckTime() > 180000) {
                    message.arg1 = 2;
                    message.what = bleParam.getUnicastAddress();
                    FtRoomVM.this.safeHandler.sendMessageDelayed(message, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
                }
                emitter.onNext(true);
                emitter.onComplete();
            }
        });
        return Observable.timer(150L, TimeUnit.MILLISECONDS).flatMap(new Function<Long, ObservableSource<Boolean>>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.20
            @Override // io.reactivex.functions.Function
            public ObservableSource<Boolean> apply(Long aLong) throws Exception {
                return create;
            }
        });
    }

    private Observable<Boolean> checkSingleGroupStatus(final Group g) {
        final Observable create = Observable.create(new ObservableOnSubscribe<Boolean>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.21
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                if (g.getGroupAddress() == 0) {
                    emitter.onNext(false);
                    emitter.onComplete();
                    return;
                }
                BaseCtrlPackage convert = SmartUtils.getICtrlConverter().convert(g);
                BaseCmd querDeviceState = CmdBleFactory.querDeviceState(0, g.getGroupAddress());
                LHomeLog.e("checkGroup", getClass(), "send=" + g.getGroupAddress());
                SmartUtils.send(Emitter.MIX_BLE_IOT, convert, querDeviceState, new ISendResutCallback(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.21.1
                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultError() {
                    }

                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultSuccess(boolean isIot) {
                    }
                });
                emitter.onNext(true);
                emitter.onComplete();
            }
        });
        return Observable.timer(150L, TimeUnit.MILLISECONDS).flatMap(new Function<Long, ObservableSource<Boolean>>(this) { // from class: com.ltech.smarthome.ui.control.FtRoomVM.22
            @Override // io.reactivex.functions.Function
            public ObservableSource<Boolean> apply(Long aLong) throws Exception {
                return create;
            }
        });
    }

    /* renamed from: com.ltech.smarthome.ui.control.FtRoomVM$23, reason: invalid class name */
    class AnonymousClass23 implements IAction<ResponseMsg> {
        final /* synthetic */ Device val$dv;

        AnonymousClass23(final Device val$dv) {
            this.val$dv = val$dv;
        }

        @Override // com.ltech.smarthome.base.IAction
        public void act(ResponseMsg responseMsg) {
            final int i;
            final int i2;
            if (responseMsg == null || responseMsg.getStateCode() != 0) {
                return;
            }
            if (responseMsg.getResData().length() >= 28) {
                i = Integer.parseInt(responseMsg.getResData().substring(20, 24), 16);
                i2 = Integer.parseInt(responseMsg.getResData().substring(24, 28), 16);
                if (i < 1000 || i > 10000) {
                    i = 1000;
                }
                if (i2 < i || i2 > 10000) {
                    i2 = 10000;
                }
            } else {
                i = 2700;
                i2 = 6500;
            }
            ObservableSubscribeProxy observableSubscribeProxy = (ObservableSubscribeProxy) Injection.net().updateCtRange(this.val$dv.getDeviceId(), i2, i).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(FtRoomVM.this.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)));
            final Device device = this.val$dv;
            observableSubscribeProxy.subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$23$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtRoomVM.AnonymousClass23.lambda$act$0(Device.this, i2, i, obj);
                }
            }, new SmartErrorComsumer());
        }

        static /* synthetic */ void lambda$act$0(Device device, int i, int i2, Object obj) throws Exception {
            Device deviceById = Injection.repo().device().getDeviceById(device.getId());
            deviceById.setMaxkelvin(i);
            deviceById.setMinkelvin(i2);
            Injection.repo().device().saveDevice(deviceById);
        }
    }

    private void checkLightCtRange(Device dv) {
        if (dv.getMinkelvin() + dv.getMaxkelvin() == 0) {
            CmdAssistant.getQueryCmdAssistant(dv, new int[0]).queryCtRange(ActivityUtils.getTopActivity(), new AnonymousClass23(dv));
        }
    }

    private static class SafeHandler extends Handler {
        private final FtRoomVM vm;

        public SafeHandler(FtRoomVM vm) {
            this.vm = vm;
        }

        @Override // android.os.Handler
        public void handleMessage(final Message msg) {
            for (Device device : Injection.repo().device().getDeviceByUnicastAddress(Injection.repo().home().getSelPlace().getPlaceId(), msg.what)) {
                if (msg.arg1 == 1) {
                    if (this.vm.needCheckMap.containsKey(Long.valueOf(device.getDeviceId()))) {
                        this.vm.needCheckMap.remove(Long.valueOf(device.getDeviceId()));
                        device.getDeviceState().setOnlineState(1);
                        device.setCheckTime(System.currentTimeMillis());
                        if (device.isVirtual()) {
                            device.setVirtual(0);
                            ((ObservableSubscribeProxy) Injection.net().updateDeviceVirtual(device.getDeviceId(), 0, -1).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.vm.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe();
                        }
                    }
                } else if (msg.arg1 == 2) {
                    device.getDeviceState().setOnlineState(2);
                }
                Injection.repo().device().saveDevice(device);
                this.vm.freshDeviceData.setValue(device);
                this.vm.changeSubDeviceOnlineStatus(device);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseViewModel, androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        SafeHandler safeHandler = this.safeHandler;
        if (safeHandler != null) {
            safeHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy(owner);
    }

    private void checkMeshGatwayStatus(Device device) {
        ((ObservableSubscribeProxy) Injection.net().getDeviceSyncStatus(this.placeId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.lambda$checkMeshGatwayStatus$17(obj);
            }
        }, new Consumer() { // from class: com.ltech.smarthome.ui.control.FtRoomVM$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                FtRoomVM.this.lambda$checkMeshGatwayStatus$18((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkMeshGatwayStatus$18(Throwable th) throws Exception {
        LHomeLog.e(getClass(), "request enter throwable-->" + th);
    }

    public static class RoleData {
        private List<Role> roles;
        private long roomId;

        public RoleData(long roomId, List<Role> roles) {
            this.roomId = roomId;
            this.roles = roles;
        }

        public long getRoomId() {
            return this.roomId;
        }

        public void setRoomId(long roomId) {
            this.roomId = roomId;
        }

        public List<Role> getRoles() {
            return this.roles;
        }

        public void setRoles(List<Role> roles) {
            this.roles = roles;
        }
    }

    public static class SceneData {
        private long roomId;
        private List<Scene> scenes;

        public SceneData(long roomId, List<Scene> roles) {
            this.roomId = roomId;
            this.scenes = roles;
        }

        public long getRoomId() {
            return this.roomId;
        }

        public void setRoomId(long roomId) {
            this.roomId = roomId;
        }

        public List<Scene> getScenes() {
            return this.scenes;
        }

        public void setScenes(List<Scene> scenes) {
            this.scenes = scenes;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int getViewType(Role role) {
        char c2;
        if (role instanceof Device) {
            Device device = (Device) role;
            String productId = device.getProductId();
            switch (productId.hashCode()) {
                case -2133025272:
                    if (productId.equals(ProductId.CG485_SUB_DEVICE)) {
                        c2 = 'M';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -2133025271:
                    if (productId.equals(ProductId.CGRS8_SUB_DEVICE)) {
                        c2 = 'K';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -2126431781:
                    if (productId.equals(ProductId.ID_IR_DIY)) {
                        c2 = 15;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -2060969856:
                    if (productId.equals(ProductId.ID_AS_PANEL_UB8)) {
                        c2 = ';';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1822884084:
                    if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                        c2 = '9';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1819630261:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                        c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1817691924:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                        c2 = '/';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1805322688:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                        c2 = Matrix.MATRIX_TYPE_ZERO;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1805199680:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                        c2 = '[';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1804340546:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                        c2 = '\\';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1804278081:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                        c2 = ']';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1803448738:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                        c2 = '^';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1796419228:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                        c2 = '0';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1777527685:
                    if (productId.equals(ProductId.ID_WIFI_LIGHT_DIM)) {
                        c2 = Matrix.MATRIX_TYPE_RANDOM_UT;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1777494050:
                    if (productId.equals(ProductId.ID_WIFI_LIGHT_CT)) {
                        c2 = 'V';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1776694498:
                    if (productId.equals(ProductId.ID_WIFI_LIGHT_RGB)) {
                        c2 = 'W';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1776638760:
                    if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBW)) {
                        c2 = 'X';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1776570529:
                    if (productId.equals(ProductId.ID_WIFI_LIGHT_RGBWY)) {
                        c2 = 'Y';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1710907378:
                    if (productId.equals(ProductId.ID_BLE_KBS)) {
                        c2 = 'T';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1698123058:
                    if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                        c2 = 4;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1642166464:
                    if (productId.equals(ProductId.ID_BLE_HAM)) {
                        c2 = 'I';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1550133760:
                    if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                        c2 = '6';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1343252468:
                    if (productId.equals(ProductId.ID_RS485_BLE)) {
                        c2 = Matrix.MATRIX_TYPE_RANDOM_LT;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1309274422:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                        c2 = 24;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1308265372:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                        c2 = 23;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1287620343:
                    if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                        c2 = 17;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1281119909:
                    if (productId.equals(ProductId.ID_RC_B2)) {
                        c2 = '$';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1273434493:
                    if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                        c2 = '@';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1265646206:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                        c2 = 25;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1213322926:
                    if (productId.equals(ProductId.ID_RC_B8)) {
                        c2 = Typography.amp;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1201890867:
                    if (productId.equals(ProductId.ID_SMART_PANEL_GQ_PRO)) {
                        c2 = 'P';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1084555505:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                        c2 = '1';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1082613022:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                        c2 = '2';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -1073881216:
                    if (productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                        c2 = ':';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -969622016:
                    if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                        c2 = '4';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -961541705:
                    if (productId.equals(ProductId.ID_SMART_PANEL_S6B)) {
                        c2 = Soundex.SILENT_MARKER;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -852623517:
                    if (productId.equals(ProductId.ID_RC4S)) {
                        c2 = 30;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -835060954:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                        c2 = '\'';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -833770237:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                        c2 = '5';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -732569219:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                        c2 = '*';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -728269602:
                    if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                        c2 = 'd';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -324427448:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                        c2 = 22;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -249671171:
                    if (productId.equals(ProductId.ID_RC_B5)) {
                        c2 = '%';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -208296259:
                    if (productId.equals(ProductId.ID_RC4)) {
                        c2 = 29;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -207348713:
                    if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                        c2 = 31;
                        break;
                    }
                    c2 = 65535;
                    break;
                case -206567420:
                    if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                        c2 = ' ';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -206510721:
                    if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                        c2 = '!';
                        break;
                    }
                    c2 = 65535;
                    break;
                case -206454022:
                    if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                        c2 = '\"';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2003796:
                    if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                        c2 = 'D';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2003797:
                    if (productId.equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
                        c2 = 'F';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2003798:
                    if (productId.equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
                        c2 = 'E';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2256539:
                    if (productId.equals(ProductId.ID_IR_STB)) {
                        c2 = 6;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2256540:
                    if (productId.equals(ProductId.ID_IR_TV)) {
                        c2 = 5;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2256541:
                    if (productId.equals(ProductId.ID_IR_TV_BOX)) {
                        c2 = 7;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2256543:
                    if (productId.equals(ProductId.ID_IR_AC)) {
                        c2 = '\b';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2256544:
                    if (productId.equals(ProductId.ID_IR_PRO)) {
                        c2 = '\n';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2256546:
                    if (productId.equals(ProductId.ID_IR_FAN)) {
                        c2 = '\t';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 13862565:
                    if (productId.equals(ProductId.ID_BLE_RS8)) {
                        c2 = 'S';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 42289893:
                    if (productId.equals(ProductId.ID_SENSOR_HSD)) {
                        c2 = 'C';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 69952758:
                    if (productId.equals(ProductId.ID_IR_AIR_CLEANER)) {
                        c2 = 11;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 69952759:
                    if (productId.equals(ProductId.ID_IR_WATER_HEATER)) {
                        c2 = '\f';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 69953013:
                    if (productId.equals(ProductId.ID_IR_CURTAIN)) {
                        c2 = 14;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 69953014:
                    if (productId.equals(ProductId.ID_IR_HANGER)) {
                        c2 = '\r';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 70457728:
                    if (productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                        c2 = 'J';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 155753896:
                    if (productId.equals(ProductId.ID_DOOR_SENSOR)) {
                        c2 = 'A';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 186184655:
                    if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                        c2 = '?';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 225641606:
                    if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                        c2 = '+';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 294483828:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                        c2 = 21;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 312618751:
                    if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                        c2 = 'c';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 353722044:
                    if (productId.equals(ProductId.ID_WIFI_CAMERA)) {
                        c2 = 28;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 356111630:
                    if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                        c2 = 0;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 356193315:
                    if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                        c2 = 1;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 359647590:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                        c2 = '=';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 376429092:
                    if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                        c2 = 2;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 376488674:
                    if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                        c2 = 3;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 377377599:
                    if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                        c2 = Typography.greater;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 414687077:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                        c2 = '_';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 427686243:
                    if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                        c2 = '3';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 439998223:
                    if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                        c2 = 'b';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 534249931:
                    if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                        c2 = '7';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 612512450:
                    if (productId.equals(ProductId.ID_HOME_KIT)) {
                        c2 = 'Q';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 613226983:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                        c2 = Typography.less;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 662799966:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                        c2 = '`';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 811752507:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                        c2 = 20;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 956710656:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                        c2 = 19;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1097035898:
                    if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                        c2 = StringUtil.COMMA;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1181428532:
                    if (productId.equals(ProductId.ID_SMART_PANEL_GQ)) {
                        c2 = 'N';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1309050445:
                    if (productId.equals(ProductId.ID_SMART_PANEL_GQX)) {
                        c2 = 'O';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1378424449:
                    if (productId.equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY)) {
                        c2 = 27;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1473345811:
                    if (productId.equals(ProductId.ID_RC_B1)) {
                        c2 = '#';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1479279198:
                    if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                        c2 = 'B';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1647983530:
                    if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                        c2 = 'a';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1786777444:
                    if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                        c2 = 'H';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1861788715:
                    if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                        c2 = '8';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1921260107:
                    if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                        c2 = 18;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1951402182:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                        c2 = ')';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1951547293:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                        c2 = '(';
                        break;
                    }
                    c2 = 65535;
                    break;
                case 1976427583:
                    if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                        c2 = 16;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2002295507:
                    if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                        c2 = JSONLexer.EOI;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2061235487:
                    if (productId.equals(ProductId.ID_WIFI_SONOS)) {
                        c2 = Matrix.MATRIX_TYPE_RANDOM_REGULAR;
                        break;
                    }
                    c2 = 65535;
                    break;
                case 2088187733:
                    if (productId.equals(ProductId.ID_SMART_PANEL_G4TE)) {
                        c2 = 'G';
                        break;
                    }
                    c2 = 65535;
                    break;
                default:
                    c2 = 65535;
                    break;
            }
            switch (c2) {
                case 0:
                case 1:
                case 2:
                case 3:
                    return 0;
                case 4:
                    return 1;
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                case 15:
                case 16:
                case 17:
                    return ProductRepository.isBLeDevice(device.getProductId()) ? 4 : 3;
                case 18:
                    DryContactBleParam dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class);
                    if (dryContactBleParam == null) {
                        return 5;
                    }
                    if (dryContactBleParam.getSubType() == 0 || dryContactBleParam.getSubType() == 3) {
                        return 4;
                    }
                    if (dryContactBleParam.getSubType() == 1) {
                        return 5;
                    }
                    if (dryContactBleParam.getSubType() == 2) {
                        return 22;
                    }
                    break;
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                    return 6;
                case 26:
                case 27:
                    return 7;
                case 28:
                    return 21;
                case 29:
                case 30:
                case 31:
                case ' ':
                case '!':
                case '\"':
                case '#':
                case '$':
                case '%':
                case '&':
                    return 8;
                case '\'':
                case '(':
                case ')':
                case '*':
                case '+':
                case ',':
                case '-':
                case '.':
                case '/':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                    return 9;
                case '5':
                    return 19;
                case '6':
                case '7':
                case '8':
                case '9':
                case ':':
                case ';':
                    return 27;
                case '<':
                    return 23;
                case '=':
                    return 24;
                case '>':
                case '?':
                case '@':
                case 'A':
                case 'B':
                case 'C':
                    return 11;
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                    return 10;
                case 'H':
                    return 12;
                case 'I':
                    return 17;
                case 'J':
                    return 18;
                case 'K':
                    return 31;
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                    return 26;
                case 'T':
                    return 29;
                default:
                    return 2;
            }
        } else {
            if (!(role instanceof Group)) {
                return 2;
            }
            switch (ProductRepository.getLightColorType(role)) {
                case 8:
                case 9:
                case 10:
                case 11:
                case 18:
                case 19:
                case 21:
                    return 13;
                case 12:
                case 16:
                    return 16;
                case 13:
                case 15:
                case 25:
                    return 20;
                case 14:
                    return 15;
                case 17:
                case 20:
                default:
                    return 14;
                case 22:
                case 23:
                case 24:
                case 27:
                case 28:
                case 29:
                case 30:
                    return 28;
                case 26:
                    return 30;
            }
        }
    }

    public void search(final String keyword) {
        ((ObservableSubscribeProxy) Observable.create(new ObservableOnSubscribe<List<Role>>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.25
            boolean inSearchMode = false;

            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<List<Role>> emitter) throws Exception {
                List<Device> deviceListByPlaceId = Injection.repo().device().getDeviceListByPlaceId(FtRoomVM.this.placeId);
                List<Group> groupListByPlaceId = Injection.repo().group().getGroupListByPlaceId(FtRoomVM.this.placeId);
                ArrayList<Role> arrayList = new ArrayList();
                arrayList.addAll(deviceListByPlaceId);
                arrayList.addAll(groupListByPlaceId);
                ArrayList arrayList2 = new ArrayList();
                boolean z = keyword.length() > 0;
                this.inSearchMode = z;
                if (z) {
                    for (Role role : arrayList) {
                        if (role.getName().toLowerCase().contains(keyword.toLowerCase()) && role.getHideDevice() == 0) {
                            arrayList2.add(role);
                        }
                    }
                }
                emitter.onNext(arrayList2);
                emitter.onComplete();
            }
        }).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new DisposableObserver<List<Role>>() { // from class: com.ltech.smarthome.ui.control.FtRoomVM.24
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onNext(List<Role> roles) {
                FtRoomVM.this.roleResult.postValue(Resource.success(roles));
            }
        });
    }

    public boolean isRoomSelect(long roomId) {
        return this.selectedRoomId == roomId;
    }
}