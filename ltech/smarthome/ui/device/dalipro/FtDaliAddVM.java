package com.ltech.smarthome.ui.device.dalipro;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.bean.Floor;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.bean.Room;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.scene_param.LocalDeviceParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.device.ListDaliDeviceResponse;
import com.ltech.smarthome.net.response.group.ListDaliGroupResponse;
import com.ltech.smarthome.net.response.scene.ListDaliSceneResponse;
import com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM;
import com.ltech.smarthome.ui.newselect.BaseRoomPageVM;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SceneAssistant;
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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtDaliAddVM extends BaseRoomPageVM {
    public int listType;
    public Disposable mDisposable;
    public Observer<Boolean> mObserver;
    private HashMap<Long, Role> needCheckMap;
    private SafeHandler safeHandler;
    public Scene scene;
    public boolean selectAll;
    public int showType;
    public List<DaliAddItem> mRoomList = new ArrayList();
    public List<Role> allRoleData = new ArrayList();
    public HashSet<Long> selectGroupIds = new HashSet<>();
    public HashSet<Long> selectDeviceIds = new HashSet<>();
    public MutableLiveData<Integer> selectCountLiveData = new MutableLiveData<>();
    public Map<Long, LocalDeviceParam> actionMap = new LinkedHashMap();
    private List<Observable<Integer>> request = new ArrayList();
    public SingleLiveEvent<Boolean> saveFinishEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> refreshPageEvent = new SingleLiveEvent<>();
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public MutableLiveData<Role> freshDeviceData = new MutableLiveData<>();
    private boolean isChecking = false;
    public RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration(this) { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.1
        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(ConvertUtils.dp2px(5.0f), 0, ConvertUtils.dp2px(5.0f), ConvertUtils.dp2px(8.0f));
        }
    };

    public interface OnCgpProGetDataListener {
        void onDismissDialog();

        void onGetAllData();

        void onGetFirstFrameData();

        void updateProgress(int progress);
    }

    public void initRoomList(List<Room> roomList, List<Floor> floorList, long deviceId) {
        ArrayList arrayList = new ArrayList();
        if (this.selectFloor.getValue().getFloorId() == -1) {
            for (Floor floor : floorList) {
                arrayList.add(new DaliAddItem(this, this.placeId.getValue().longValue(), floor.getFloorId(), -1L, floor.getIndex(), floor.getFloorName(), deviceId));
            }
        } else {
            for (Room room : roomList) {
                arrayList.add(new DaliAddItem(this, this.placeId.getValue().longValue(), room.getFloorId(), room.getRoomId(), room.getIndex(), room.getRoomName(), deviceId));
            }
        }
        this.mRoomList = arrayList;
    }

    public boolean hasUnConfigRole(long floorId, long roomId, long deviceId) {
        return Injection.repo().device().getSubDeviceListFromDb(this.placeId.getValue().longValue(), floorId, roomId, deviceId).size() > 0 || Injection.repo().group().getSubGroupListFromDb(this.placeId.getValue().longValue(), floorId, roomId, deviceId).size() > 0;
    }

    public void selectAll() {
        if (this.allRoleData.isEmpty()) {
            return;
        }
        if (!this.selectAll) {
            for (Role role : this.allRoleData) {
                if (role instanceof Group) {
                    this.selectGroupIds.add(Long.valueOf(role.getObjectId()));
                } else {
                    this.selectDeviceIds.add(Long.valueOf(role.getObjectId()));
                }
            }
        } else {
            for (Role role2 : this.allRoleData) {
                if (role2 instanceof Group) {
                    this.selectGroupIds.remove(Long.valueOf(role2.getObjectId()));
                } else {
                    this.selectDeviceIds.remove(Long.valueOf(role2.getObjectId()));
                }
            }
        }
        this.selectCountLiveData.setValue(Integer.valueOf(this.selectDeviceIds.size() + this.selectGroupIds.size()));
    }

    public Observable<Integer> delTempScene(final Context context) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                FtDaliAddVM.this.lambda$delTempScene$1(context, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delTempScene$1(Context context, final ObservableEmitter observableEmitter) throws Exception {
        getCmdHelper().delDaliScene(context, DaliProHelper.getSceneNum(this.scene), new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtDaliAddVM.lambda$delTempScene$0(ObservableEmitter.this, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$delTempScene$0(ObservableEmitter observableEmitter, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            observableEmitter.onNext(0);
            observableEmitter.onComplete();
        } else {
            observableEmitter.onError(new Throwable());
        }
    }

    public void checkDeviceStatus(List<Role> roleList) {
        if (roleList == null || roleList.isEmpty()) {
            return;
        }
        Disposable disposable = this.mDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.isChecking = false;
        this.needCheckMap = new HashMap<>();
        for (Role role : roleList) {
            if (role instanceof Device) {
                this.needCheckMap.put(Long.valueOf(role.getObjectId()), role);
            } else if (!((Group) role).getDeviceIds().isEmpty()) {
                this.needCheckMap.put(Long.valueOf(role.getObjectId()), role);
            }
        }
        if (this.needCheckMap.isEmpty()) {
            return;
        }
        if (this.safeHandler == null) {
            this.safeHandler = new SafeHandler(this);
        }
        if (this.isChecking) {
            return;
        }
        mergeCheckStatusRequest();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mergeCheckStatusRequest() {
        this.isChecking = true;
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Long, Role>> it = this.needCheckMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(checkSingleDeviceStatus(it.next().getValue()));
        }
        LHomeLog.e("checkDEV", getClass(), "need=" + this.needCheckMap.size());
        this.mObserver = Observable.concat(arrayList).subscribeWith(new Observer<Boolean>() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.2
            @Override // io.reactivex.Observer
            public void onNext(Boolean aBoolean) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
                FtDaliAddVM.this.mDisposable = d2;
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                FtDaliAddVM.this.isChecking = false;
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                FtDaliAddVM.this.isChecking = false;
                if (FtDaliAddVM.this.needCheckMap.size() > 0) {
                    FtDaliAddVM.this.mergeCheckStatusRequest();
                    return;
                }
                LHomeLog.e("checkDEV", getClass(), "end=" + FtDaliAddVM.this.needCheckMap.size());
                Iterator it2 = FtDaliAddVM.this.needCheckMap.entrySet().iterator();
                while (it2.hasNext()) {
                    Role role = (Role) ((Map.Entry) it2.next()).getValue();
                    if (role instanceof Device) {
                        Device deviceById = Injection.repo().device().getDeviceById(((Device) role).getId());
                        if (deviceById != null) {
                            if (deviceById.getDeviceState() == null) {
                                deviceById.setDeviceState(new DeviceState());
                            }
                            deviceById.getDeviceState().setOnlineState(2);
                            Injection.repo().device().saveDevice(deviceById);
                            FtDaliAddVM.this.freshDeviceData.setValue(deviceById);
                        }
                    } else {
                        Group groupById = Injection.repo().group().getGroupById(((Group) role).getId());
                        if (groupById != null) {
                            if (groupById.getDeviceState() == null) {
                                groupById.setDeviceState(new DeviceState());
                            }
                            groupById.getDeviceState().setOnlineState(2);
                            Injection.repo().group().saveGroup(groupById);
                            FtDaliAddVM.this.freshDeviceData.setValue(groupById);
                        }
                    }
                }
            }
        });
    }

    private Observable<Boolean> checkSingleDeviceStatus(final Role value) {
        final Observable create = Observable.create(new ObservableOnSubscribe<Boolean>() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.3
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                final Role role = value;
                BaseCtrlPackage convert = SmartUtils.getICtrlConverter().convert(role);
                BaseCmd queryAmbientLightStatus = CmdBleFactory.queryAmbientLightStatus(DaliProHelper.getZoneNum(role));
                MessageManager.getInstance().addRoleQueryResult(DaliProHelper.getZoneNum(role), new MessageManager.DaliUpdateQuery() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.3.1
                    @Override // com.smart.message.MessageManager.DaliUpdateQuery
                    public void update(int uniAddress, int zoneNum) {
                        if (FtDaliAddVM.this.needCheckMap.containsKey(Long.valueOf(role.getObjectId()))) {
                            MessageManager.getInstance().removeRoleQueryResult(zoneNum);
                            FtDaliAddVM.this.needCheckMap.remove(Long.valueOf(role.getObjectId()));
                            LHomeLog.e("checkDEV", getClass(), "return=" + zoneNum);
                            FtDaliAddVM.this.safeHandler.removeMessages(zoneNum);
                            Message message = new Message();
                            Role role2 = role;
                            if (role2 instanceof Device) {
                                ((Device) role2).getDeviceState().setOnlineState(1);
                            } else {
                                ((Group) role2).getDeviceState().setOnlineState(1);
                            }
                            message.obj = role;
                            message.what = zoneNum;
                            FtDaliAddVM.this.safeHandler.sendMessage(message);
                        }
                    }
                });
                LHomeLog.e("checkDEV", getClass(), "send=" + DaliProHelper.getZoneNum(role));
                SmartUtils.send(Emitter.MIX_BLE_IOT, convert, queryAmbientLightStatus, new ISendResutCallback() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.3.2
                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultError() {
                    }

                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultSuccess(boolean isIot) {
                        FtDaliAddVM.this.safeHandler.removeMessages(DaliProHelper.getZoneNum(role));
                        Message message = new Message();
                        Role role2 = role;
                        if (role2 instanceof Device) {
                            ((Device) role2).getDeviceState().setOnlineState(2);
                        } else {
                            ((Group) role2).getDeviceState().setOnlineState(2);
                        }
                        message.obj = role;
                        message.what = DaliProHelper.getZoneNum(role);
                        FtDaliAddVM.this.safeHandler.sendMessageDelayed(message, 1000L);
                    }
                });
                FtDaliAddVM.this.safeHandler.setCurRole(role);
                FtDaliAddVM.this.safeHandler.setEmitter(emitter);
            }
        });
        return Observable.timer(150L, TimeUnit.MILLISECONDS).flatMap(new Function<Long, ObservableSource<Boolean>>(this) { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.4
            @Override // io.reactivex.functions.Function
            public ObservableSource<Boolean> apply(Long aLong) throws Exception {
                return create;
            }
        });
    }

    public Observable<Integer> saveSceneAction(final Context context, final int zoneNum, final String instruct, final int step) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                FtDaliAddVM.this.lambda$saveSceneAction$3(context, zoneNum, instruct, step, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSceneAction$3(Context context, int i, String str, final int i2, final ObservableEmitter observableEmitter) throws Exception {
        getCmdHelper().saveDaliSceneAction(context, DaliProHelper.getSceneNum(this.scene), i, str, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtDaliAddVM.lambda$saveSceneAction$2(ObservableEmitter.this, i2, (ResponseMsg) obj);
            }
        });
    }

    static /* synthetic */ void lambda$saveSceneAction$2(ObservableEmitter observableEmitter, int i, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            observableEmitter.onNext(Integer.valueOf(i));
            observableEmitter.onComplete();
        } else {
            observableEmitter.onError(new Throwable());
        }
    }

    public void batchControl(Context context) {
        this.request.clear();
        this.request.add(delTempScene(context));
        Iterator<Long> it = this.actionMap.keySet().iterator();
        while (it.hasNext()) {
            if (!this.selectDeviceIds.contains(it.next())) {
                it.remove();
            }
        }
        int i = 0;
        for (Long l : this.actionMap.keySet()) {
            long longValue = l.longValue();
            LocalDeviceParam localDeviceParam = this.actionMap.get(l);
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(longValue);
            if (localDeviceParam != null) {
                this.request.add(saveSceneAction(context, DaliProHelper.getDeviceAddress(deviceByDeviceId), localDeviceParam.instruct, i));
                i++;
            }
        }
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
        ((ObservableSubscribeProxy) Observable.concat(this.request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM.5
            @Override // io.reactivex.Observer
            public void onNext(Integer integer) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                FtDaliAddVM.this.saveFinishEvent.setValue(false);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                SmartToast.showShort(R.string.save_success);
                FtDaliAddVM.this.dismissLoadingDialog();
                FtDaliAddVM.this.saveFinishEvent.setValue(true);
            }
        });
    }

    public class DaliAddItem extends BaseRoomPageVM.RoomPageItem {
        private FtRoomDaliAdd ftDaliAdd;

        public DaliAddItem(final FtDaliAddVM this$0, long placeId, long floorId, long roomId, int roomIndex, String roomName, long deviceId) {
            this.placeId = placeId;
            this.floorId = floorId;
            this.roomId = roomId;
            this.roomIndex = roomIndex;
            this.roomName = roomName;
            this.ftDaliAdd = FtRoomDaliAdd.newInstance(placeId, roomId, floorId, deviceId);
        }

        public FtRoomDaliAdd getFtDaliAdd() {
            return this.ftDaliAdd;
        }
    }

    public SceneAssistant getCmdHelper() {
        return CmdAssistant.getSceneCmdAssistant(Injection.repo().device().getDeviceByDeviceId(this.scene.getMacdeviceid()), new int[0]);
    }

    public void initData(final Boolean isCleanData, final OnCgpProGetDataListener onCgpProGetDataListener) {
        CgdProDataManager.getInstance().clearDeviceByteList();
        CgdProDataManager.getInstance().clearGroupPositionList();
        getLightCmdHelper().queryCgdLight(ActivityUtils.getTopActivity(), 0, isCleanData.booleanValue() ? 2 : 1, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtDaliAddVM.this.lambda$initData$5(onCgpProGetDataListener, isCleanData, (ResponseMsg) obj);
            }
        }, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtDaliAddVM.lambda$initData$6(FtDaliAddVM.OnCgpProGetDataListener.this, (Integer) obj);
            }
        }, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtDaliAddVM.lambda$initData$7(FtDaliAddVM.OnCgpProGetDataListener.this, (Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$5(final OnCgpProGetDataListener onCgpProGetDataListener, final Boolean bool, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 21) {
                LHomeLog.e(getClass(), "DALI主控无设备");
                if (onCgpProGetDataListener != null) {
                    onCgpProGetDataListener.onDismissDialog();
                }
                showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.dali_no_devices));
                return;
            }
            final List<Device> deviceList = CgdProDataManager.getInstance().getDeviceList(this.controlDevice.getValue(), bool.booleanValue());
            if (deviceList == null || deviceList.size() <= 0) {
                return;
            }
            ((ObservableSubscribeProxy) Injection.net(30).updateDaliDeviceList(this.controlDevice.getValue(), deviceList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtDaliAddVM.this.lambda$initData$4(deviceList, onCgpProGetDataListener, bool, (ListDaliDeviceResponse) obj);
                }
            }, new SmartErrorComsumer());
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.local_scene_sync_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$4(List list, OnCgpProGetDataListener onCgpProGetDataListener, Boolean bool, ListDaliDeviceResponse listDaliDeviceResponse) throws Exception {
        if (listDaliDeviceResponse != null) {
            for (int i = 0; i < listDaliDeviceResponse.getDevices().size(); i++) {
                ((Device) list.get(i)).setDeviceId(listDaliDeviceResponse.getDevices().get(i).getDeviceid().longValue());
                ((Device) list.get(i)).setDeviceName(listDaliDeviceResponse.getDevices().get(i).getDevicename());
                ((Device) list.get(i)).setWifiMac(listDaliDeviceResponse.getDevices().get(i).getMac());
            }
            Injection.boxStore().boxFor(Device.class).put((Collection) list);
            updateGroup(list, onCgpProGetDataListener, bool.booleanValue());
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    static /* synthetic */ void lambda$initData$6(OnCgpProGetDataListener onCgpProGetDataListener, Integer num) {
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.updateProgress(num.intValue());
        }
    }

    static /* synthetic */ void lambda$initData$7(OnCgpProGetDataListener onCgpProGetDataListener, Integer num) {
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onGetFirstFrameData();
        }
    }

    private void updateGroup(List<Device> deviceList, final OnCgpProGetDataListener onCgpProGetDataListener, final boolean isCleanData) {
        final List<Group> groupList = CgdProDataManager.getInstance().getGroupList(this.controlDevice.getValue(), deviceList, isCleanData);
        if (groupList != null && groupList.size() > 0) {
            ((ObservableSubscribeProxy) Injection.net().updateDaliGroupList(this.controlDevice.getValue(), groupList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtDaliAddVM.this.lambda$updateGroup$8(groupList, onCgpProGetDataListener, isCleanData, (ListDaliGroupResponse) obj);
                }
            });
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateGroup$8(List list, OnCgpProGetDataListener onCgpProGetDataListener, boolean z, ListDaliGroupResponse listDaliGroupResponse) throws Exception {
        if (listDaliGroupResponse != null && listDaliGroupResponse.getRet() == 0) {
            for (int i = 0; i < listDaliGroupResponse.getData().size(); i++) {
                ((Group) list.get(i)).setGroupId(listDaliGroupResponse.getData().get(i).getGroupid());
                ((Group) list.get(i)).setGroupName(listDaliGroupResponse.getData().get(i).getGroupname());
            }
            Injection.boxStore().boxFor(Group.class).put((Collection) list);
            updateScene(onCgpProGetDataListener, z);
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    private void updateScene(final OnCgpProGetDataListener onCgpProGetDataListener, final boolean isCleanData) {
        CgdProDataManager.getInstance().clearScenePositionList();
        getLightCmdHelper().queryCgdScene(ActivityUtils.getTopActivity(), 1, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtDaliAddVM.this.lambda$updateScene$10(isCleanData, onCgpProGetDataListener, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateScene$10(boolean z, final OnCgpProGetDataListener onCgpProGetDataListener, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 22) {
                final List<Scene> sceneList = CgdProDataManager.getInstance().getSceneList(this.controlDevice.getValue(), z);
                ((ObservableSubscribeProxy) Injection.net().updateDaliSceneList(this.controlDevice.getValue(), sceneList).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliAddVM$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        FtDaliAddVM.this.lambda$updateScene$9(sceneList, onCgpProGetDataListener, (ListDaliSceneResponse) obj);
                    }
                });
                return;
            } else {
                if (onCgpProGetDataListener != null) {
                    onCgpProGetDataListener.onDismissDialog();
                }
                showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.local_scene_sync_fail));
                return;
            }
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.local_scene_sync_fail));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateScene$9(List list, OnCgpProGetDataListener onCgpProGetDataListener, ListDaliSceneResponse listDaliSceneResponse) throws Exception {
        if (listDaliSceneResponse != null && listDaliSceneResponse.getRet() == 0) {
            for (int i = 0; i < listDaliSceneResponse.getData().size(); i++) {
                ((Scene) list.get(i)).setSceneId(listDaliSceneResponse.getData().get(i).getSceneid());
                ((Scene) list.get(i)).setSceneName(listDaliSceneResponse.getData().get(i).getScenename());
            }
            Injection.boxStore().boxFor(Scene.class).put((Collection) list);
            this.refreshPageEvent.call();
            if (onCgpProGetDataListener != null) {
                onCgpProGetDataListener.onGetAllData();
                return;
            }
            return;
        }
        if (onCgpProGetDataListener != null) {
            onCgpProGetDataListener.onDismissDialog();
        }
        showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.save_fail));
    }

    public LightAssistant getLightCmdHelper() {
        return CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]);
    }

    private static class SafeHandler extends Handler {
        private Role checkRole;
        private ObservableEmitter<Boolean> mEmitter;
        private boolean pause;
        private final FtDaliAddVM vm;

        public SafeHandler(FtDaliAddVM vm) {
            this.vm = vm;
        }

        @Override // android.os.Handler
        public void handleMessage(final Message msg) {
            Role role = (Role) msg.obj;
            if (role instanceof Device) {
                Device device = (Device) role;
                Device deviceById = Injection.repo().device().getDeviceById(device.getId());
                if (device.getDeviceState() != null && device.getDeviceState().isOnline() && deviceById != null && device.getDeviceState() != null) {
                    if (deviceById.getDeviceState() != null) {
                        deviceById.getDeviceState().setOnlineState(device.getDeviceState().getOnlineState());
                    }
                    Injection.repo().device().saveDevice(deviceById);
                    this.vm.freshDeviceData.setValue(deviceById);
                }
            } else {
                Group group = (Group) role;
                Group groupById = Injection.repo().group().getGroupById(group.getId());
                if (group.getDeviceState() != null && group.getDeviceState().isOnline() && groupById != null && group.getDeviceState() != null) {
                    if (groupById.getDeviceState() != null) {
                        groupById.getDeviceState().setOnlineState(group.getDeviceState().getOnlineState());
                    }
                    Injection.repo().group().saveGroup(groupById);
                    this.vm.freshDeviceData.setValue(groupById);
                }
            }
            Role role2 = this.checkRole;
            if (role2 == null || role2.getObjectId() != role.getObjectId()) {
                return;
            }
            this.checkRole = null;
            next();
        }

        public void setEmitter(ObservableEmitter<Boolean> emitter) {
            this.mEmitter = emitter;
        }

        public void setCurRole(Role role) {
            this.checkRole = role;
        }

        public void setPause(boolean b2) {
            this.pause = b2;
        }

        public void next() {
            ObservableEmitter<Boolean> observableEmitter = this.mEmitter;
            if (observableEmitter == null || this.pause) {
                return;
            }
            observableEmitter.onNext(true);
            this.mEmitter.onComplete();
            this.mEmitter = null;
        }
    }
}