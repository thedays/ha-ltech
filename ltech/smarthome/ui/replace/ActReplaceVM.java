package com.ltech.smarthome.ui.replace;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.blemesh.IRefreshNetworkCallback;
import com.ltech.smarthome.blemesh.IRemoveNodeCallback;
import com.ltech.smarthome.blemesh.feasy.FeasyMeshNetHelper;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.LocalSceneParam;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.model.scene_param.BaseLocalParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.net.response.device.GetReplaceDataResponse;
import com.ltech.smarthome.net.response.scene.QuerySceneActionResponse;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.replace.ActReplaceVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.ResponseMsg;
import com.smart.message.base.IReceiveListener;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.bean.SmartPanelScreenState;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.smart.product_agreement.param.SettingCmdParam;
import com.smart.product_agreement.param.SmartPanelParam;
import com.smart.product_agreement.productBle.CmdBle;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActReplaceVM extends BaseViewModel {
    private Context context;
    private Map<Integer, List<String>> errorTipMap;
    private ArrayList<Integer> mSceneNums;
    public Device oldDevice;
    private ReplaceBean replaceBean;
    private Map<Long, Integer> saveSceneStep;
    public MutableLiveData<Integer> totalProgress = new MutableLiveData<>();
    public MutableLiveData<Integer> progress = new MutableLiveData<>(0);
    private GetReplaceDataResponse dataResponse = new GetReplaceDataResponse();
    private boolean needSyncSetting = true;
    private SmartErrorComsumer errorConsumer = new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.1
        @Override // com.ltech.smarthome.net.SmartErrorComsumer
        protected void action(Throwable throwable) {
            super.action(throwable);
            ActReplaceVM.this.setErrorView();
        }
    };
    private CountDownTimer timer = new CountDownTimer(40000, 1000) { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.2
        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            int i = (int) ((40000 - millisUntilFinished) / 1000);
            if (i % 2 == 0) {
                ActReplaceVM.this.progress.setValue(Integer.valueOf(i + 50));
            }
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            ActReplaceVM.this.setErrorView();
        }
    };
    private int mSyncNum = 0;
    private List<String> cmdList = new ArrayList();
    private int retryTimes = 3;

    public void setContext(Context context) {
        this.context = context;
    }

    public void startReplace(Device newDevice) {
        if (isSuperPanel()) {
            this.replaceBean = ReplaceBean.processTwo(newDevice);
            this.totalProgress.setValue(100);
        } else {
            ReplaceBean processOne = ReplaceBean.processOne(newDevice);
            this.replaceBean = processOne;
            this.totalProgress.setValue(Integer.valueOf(processOne.processList.size()));
        }
        nextStep();
    }

    private boolean isSuperPanel() {
        return ProductRepository.isSuperPanel(ReplaceHelper.instance().newDevice.getProductId());
    }

    private void replaceDevice(int state) {
        switch (state) {
            case 1:
                restoreDevice();
                break;
            case 2:
                getData();
                break;
            case 3:
                syncLocalSceneToDevice(this.replaceBean.newDevice);
                break;
            case 4:
                syncGroupData();
                break;
            case 5:
                syncSettingData();
                break;
            case 6:
                removeMesh();
                break;
            case 7:
                modifyNDID();
                break;
            case 8:
                updateParam();
                break;
            case 9:
                if (isSuperPanel()) {
                    this.timer.cancel();
                }
                refreshMeshNetwork();
                break;
            case 10:
                SharedPreferenceUtil.edit().keepShared(Constants.GROUP_CHANGED, true);
                EventBusUtils.post(new LiveBusHelper(9));
                nextStep();
                break;
            case 11:
                getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ActReplaceVM.this.progress.setValue(100);
                    }
                }, 1000L);
                break;
            case 12:
                startSuperPanelReplace();
                break;
            case 13:
                this.progress.setValue(50);
                this.timer.cancel();
                this.timer.start();
                break;
            case 14:
                updateParamLocal();
                break;
            case 15:
                setPublishAddress();
                break;
        }
    }

    public void nextStep() {
        replaceDevice(this.replaceBean.nextState());
        if (isSuperPanel()) {
            return;
        }
        this.progress.setValue(Integer.valueOf(this.replaceBean.getCurrentPos()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setErrorView() {
        this.progress.setValue(200);
    }

    private void restoreDevice() {
        CmdAssistant.getSettingCmdAssistant(this.replaceBean.newDevice, new int[0]).restoreDeviceData(this.context, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$restoreDevice$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreDevice$0(Boolean bool) {
        if (bool.booleanValue()) {
            getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.4
                @Override // java.lang.Runnable
                public void run() {
                    ActReplaceVM.this.nextStep();
                }
            }, 1000L);
        } else {
            setErrorView();
        }
    }

    private void setPublishAddress() {
        CmdAssistant.getSettingCmdAssistant(this.replaceBean.newDevice, new int[0]).setPublishAddress(this.context, EurHelper.getPublishAddress(this.oldDevice), new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda29
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$setPublishAddress$1((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPublishAddress$1(Boolean bool) {
        if (bool.booleanValue()) {
            nextStep();
        } else {
            setErrorView();
        }
    }

    private void getData() {
        ((ObservableSubscribeProxy) Injection.net().queryReplaceData(this.oldDevice.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActReplaceVM.this.lambda$getData$2((GetReplaceDataResponse) obj);
            }
        }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.5
            @Override // com.ltech.smarthome.net.SmartErrorComsumer
            protected void action(Throwable throwable) {
                SmartToast.showShort(this.errorMessage);
                ActReplaceVM.this.setErrorView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getData$2(GetReplaceDataResponse getReplaceDataResponse) throws Exception {
        LHomeLog.i(getClass(), "queryDeviceReplaceData:" + getReplaceDataResponse.toString());
        this.dataResponse = getReplaceDataResponse;
        nextStep();
    }

    private void syncLocalSceneToDevice(Device device) {
        if (this.dataResponse.getSceneData() == null || this.dataResponse.getSceneData().size() == 0) {
            nextStep();
            return;
        }
        Collections.sort(this.dataResponse.getSceneData(), new Comparator() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda15
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ActReplaceVM.lambda$syncLocalSceneToDevice$3((QuerySceneActionResponse.RowsBean) obj, (QuerySceneActionResponse.RowsBean) obj2);
            }
        });
        this.mSceneNums = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        this.saveSceneStep = new HashMap();
        for (QuerySceneActionResponse.RowsBean rowsBean : this.dataResponse.getSceneData()) {
            if (rowsBean.getScenetype() == 2) {
                BaseLocalParam executecommandObject = rowsBean.getExecutecommandObject();
                int intValue = (this.saveSceneStep.containsKey(Long.valueOf(rowsBean.getSceneid())) ? this.saveSceneStep.get(Long.valueOf(rowsBean.getSceneid())).intValue() : 0) + 1;
                this.saveSceneStep.put(Long.valueOf(rowsBean.getSceneid()), Integer.valueOf(intValue));
                if (executecommandObject.sceneAddr == this.oldDevice.getUnicastAddress()) {
                    executecommandObject.sceneAddr = this.replaceBean.newDevice.getUnicastAddress();
                }
                arrayList.add(saveTempDataWithTip(device, rowsBean.getScenenum(), rowsBean.getScenename(), new LocalSceneParam(executecommandObject.getTotalDelay(), intValue, executecommandObject.instruct, executecommandObject.sceneZone, executecommandObject.sceneAddr)));
                this.mSceneNums.add(Integer.valueOf(rowsBean.getScenenum()));
            }
        }
        batchControlWithDialog(arrayList);
    }

    static /* synthetic */ int lambda$syncLocalSceneToDevice$3(QuerySceneActionResponse.RowsBean rowsBean, QuerySceneActionResponse.RowsBean rowsBean2) {
        return rowsBean.getExecutecommandObject().getTotalDelay() - rowsBean2.getExecutecommandObject().getTotalDelay();
    }

    private Observable<ErrorTip> saveTempDataWithTip(final Device device, final int sceneNum, final String sceneName, final LocalSceneParam content) {
        return Observable.create(new ObservableOnSubscribe<ErrorTip>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.6
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<ErrorTip> emitter) throws Exception {
                List list;
                if (ActReplaceVM.this.errorTipMap.size() > 0 && ActReplaceVM.this.errorTipMap.containsKey(-1) && (list = (List) ActReplaceVM.this.errorTipMap.get(-1)) != null && list.size() >= 1) {
                    emitter.onComplete();
                } else {
                    CmdAssistant.getSceneCmdAssistant(device, new int[0]).syncLocalSceneAction(ActReplaceVM.this.context, sceneNum, content.getInstruct(), content.getStep(), content.getTime(), content.getZoneNum(), content.getAddress(), content.isCurState(), ProductRepository.getInfraredType(device.getProductId()), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.6.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(ResponseMsg responseMsg) {
                            if (responseMsg != null) {
                                ActReplaceVM.this.mSyncNum++;
                            }
                            emitter.onNext(ActReplaceVM.this.getSameDeviceByResponse(sceneNum, sceneName, responseMsg));
                            emitter.onComplete();
                        }
                    });
                }
            }
        });
    }

    private void batchControlWithDialog(List<Observable<ErrorTip>> request) {
        this.errorTipMap = new HashMap();
        this.mSyncNum = 0;
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<ErrorTip>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.7
            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(ErrorTip errorTip) {
                if (errorTip.getErrorType() != 0) {
                    if (ActReplaceVM.this.errorTipMap.containsKey(Integer.valueOf(errorTip.getErrorType()))) {
                        List list = (List) ActReplaceVM.this.errorTipMap.get(Integer.valueOf(errorTip.getErrorType()));
                        if (list == null) {
                            list = new ArrayList();
                        }
                        list.add(errorTip.getSceneName());
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(errorTip.getSceneName());
                    ActReplaceVM.this.errorTipMap.put(Integer.valueOf(errorTip.getErrorType()), arrayList);
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                ActReplaceVM.this.errorTipMap.size();
                ActReplaceVM.this.nextStep();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ErrorTip getSameDeviceByResponse(int sceneNum, String sceneName, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            if (responseMsg.getStateCode() == 0) {
                return new ErrorTip(sceneNum, sceneName, 0);
            }
            if (responseMsg.getStateCode() == 153) {
                return new ErrorTip(sceneNum, sceneName, 153);
            }
            if (responseMsg.getStateCode() == 12) {
                return new ErrorTip(sceneNum, sceneName, 12);
            }
            if (responseMsg.getStateCode() == 13) {
                return new ErrorTip(sceneNum, sceneName, 13);
            }
            if (responseMsg.getStateCode() == 15) {
                return new ErrorTip(sceneNum, sceneName, 15);
            }
            return new ErrorTip(sceneNum, sceneName, -1);
        }
        return new ErrorTip(sceneNum, sceneName, -1);
    }

    public void setStatus(final int status) {
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.8
            @Override // java.lang.Runnable
            public void run() {
                if (ActReplaceVM.this.timer != null) {
                    ActReplaceVM.this.timer.cancel();
                }
                if (status == 1) {
                    ActReplaceVM.this.nextStep();
                } else {
                    ActReplaceVM.this.setErrorView();
                }
            }
        });
    }

    static class ErrorTip {
        private int errorType;
        private String sceneName;
        private int sceneNum;

        public ErrorTip(int sceneNum, String sceneName, int errorType) {
            this.sceneNum = sceneNum;
            this.sceneName = sceneName;
            this.errorType = errorType;
        }

        public int getSceneNum() {
            return this.sceneNum;
        }

        public void setSceneNum(int sceneNum) {
            this.sceneNum = sceneNum;
        }

        public String getSceneName() {
            return this.sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public int getErrorType() {
            return this.errorType;
        }

        public void setErrorType(int errorType) {
            this.errorType = errorType;
        }
    }

    public void syncGroupData() {
        if ((this.dataResponse.getGroupData() == null || this.dataResponse.getGroupData().isEmpty()) && (this.dataResponse.getSubGroupData() == null || this.dataResponse.getSubGroupData().isEmpty())) {
            nextStep();
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (this.dataResponse.getGroupData() != null && !this.dataResponse.getGroupData().isEmpty()) {
            for (int i = 0; i < this.dataResponse.getGroupData().size(); i++) {
                GetReplaceDataResponse.RowsBean rowsBean = this.dataResponse.getGroupData().get(i);
                if (ProductRepository.isEurPanel(this.oldDevice.getProductId()) || ProductRepository.isAsPanel(this.oldDevice.getProductId())) {
                    arrayList.add(inEurGroup(this.replaceBean.newDevice, rowsBean.getGroupid(), rowsBean.getGroupAddress(), i));
                } else {
                    arrayList.add(inGroup(this.replaceBean.newDevice, rowsBean.getGroupid(), rowsBean.getGroupAddress(), i));
                }
            }
        }
        if (this.dataResponse.getSubGroupData() != null && !this.dataResponse.getSubGroupData().isEmpty()) {
            for (int i2 = 0; i2 < this.dataResponse.getSubGroupData().size(); i2++) {
                GetReplaceDataResponse.RowsBean rowsBean2 = this.dataResponse.getSubGroupData().get(i2);
                arrayList.add(inSubGroup(this.replaceBean.newDevice, rowsBean2.getGroupid(), rowsBean2.getGroupAddress(), i2, rowsBean2.getZone()));
            }
        }
        batchControlGroup(arrayList);
    }

    private Observable<Integer> inSubGroup(final Device device, final long groupId, final int groupAddress, final int index, final int zone) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda18
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActReplaceVM.this.lambda$inSubGroup$6(device, groupAddress, index, groupId, zone, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inSubGroup$6(Device device, int i, final int i2, final long j, int i3, final ObservableEmitter observableEmitter) throws Exception {
        Injection.mesh().inGroupByCmd(device, i, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda10
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$inSubGroup$5(observableEmitter, i2, j, (Boolean) obj);
            }
        }, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inSubGroup$5(ObservableEmitter observableEmitter, int i, long j, Boolean bool) {
        if (bool.booleanValue()) {
            observableEmitter.onNext(Integer.valueOf(i));
            observableEmitter.onComplete();
            return;
        }
        final Group groupByGroupId = Injection.repo().group().getGroupByGroupId(j);
        if (groupByGroupId != null && !groupByGroupId.getDeviceIds().isEmpty()) {
            groupByGroupId.getDeviceIds().remove(Long.valueOf(this.oldDevice.getDeviceId()));
            ((ObservableSubscribeProxy) Injection.net().updateGroupDevices(j, groupByGroupId.getDeviceIds()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Injection.repo().group().saveGroup(Group.this);
                }
            });
        }
        observableEmitter.onNext(Integer.valueOf(i));
        observableEmitter.onComplete();
    }

    private Observable<Integer> inGroup(final Device device, final long groupId, final int groupAddress, final int index) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda23
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActReplaceVM.this.lambda$inGroup$10(device, groupAddress, groupId, index, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inGroup$10(final Device device, int i, final long j, final int i2, final ObservableEmitter observableEmitter) throws Exception {
        Injection.mesh().inGroupByCmd(device, i, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$inGroup$9(j, device, observableEmitter, i2, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inGroup$9(long j, final Device device, final ObservableEmitter observableEmitter, final int i, Boolean bool) {
        if (bool.booleanValue()) {
            if (ProductRepository.isSmartPanelDevice(this.oldDevice.getProductId())) {
                this.needSyncSetting = false;
                final Group groupByGroupId = Injection.repo().group().getGroupByGroupId(j);
                RelateInfoUtils.initRelateInfoList(groupByGroupId);
                CmdAssistant.getSettingCmdAssistant(device, new int[0]).setGroupRelateInfo(this.context, ProductRepository.getZoneCount(this.oldDevice.getProductId()), generateRelateInfoList(groupByGroupId), new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda12
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActReplaceVM.this.lambda$inGroup$7(observableEmitter, i, groupByGroupId, device, (Boolean) obj);
                    }
                });
                return;
            }
            observableEmitter.onNext(Integer.valueOf(i));
            observableEmitter.onComplete();
            return;
        }
        final Group groupByGroupId2 = Injection.repo().group().getGroupByGroupId(j);
        if (groupByGroupId2 != null && groupByGroupId2.getDeviceIds().size() > 0) {
            groupByGroupId2.getDeviceIds().remove(Long.valueOf(this.oldDevice.getDeviceId()));
            ((ObservableSubscribeProxy) Injection.net().updateGroupDevices(j, groupByGroupId2.getDeviceIds()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda13
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Injection.repo().group().saveGroup(Group.this);
                }
            });
        }
        observableEmitter.onNext(Integer.valueOf(i));
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inGroup$7(ObservableEmitter observableEmitter, int i, Group group, Device device, Boolean bool) {
        if (bool.booleanValue()) {
            observableEmitter.onNext(Integer.valueOf(i));
            synGroupSettingToDevice(group, device, observableEmitter);
        } else {
            observableEmitter.onNext(Integer.valueOf(i));
            observableEmitter.onComplete();
        }
    }

    private Observable<Integer> inEurGroup(final Device device, final long groupId, int groupAddress, final int index) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda30
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActReplaceVM.this.lambda$inEurGroup$19(groupId, device, index, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inEurGroup$19(final long j, final Device device, final int i, final ObservableEmitter observableEmitter) throws Exception {
        final Group groupByGroupId = Injection.repo().group().getGroupByGroupId(j);
        if (ProductRepository.isEurPanel(device.getProductId())) {
            EurHelper.inGroup(groupByGroupId, device, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActReplaceVM.this.lambda$inEurGroup$14(device, observableEmitter, i, groupByGroupId, j, (Boolean) obj);
                }
            });
        } else {
            AsHelper.inGroup(groupByGroupId, device, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda6
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActReplaceVM.this.lambda$inEurGroup$18(device, observableEmitter, i, groupByGroupId, j, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inEurGroup$14(Device device, final ObservableEmitter observableEmitter, final int i, final Group group, long j, Boolean bool) {
        if (bool.booleanValue()) {
            EurHelper.clearPublishAddress(device, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda20
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    EurHelper.syncGroupSettings(new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda28
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj2) {
                            ActReplaceVM.lambda$inEurGroup$11(ObservableEmitter.this, r2, (Boolean) obj2);
                        }
                    });
                }
            });
            return;
        }
        if (group != null && group.getDeviceIds().size() > 0) {
            group.getDeviceIds().remove(Long.valueOf(this.oldDevice.getDeviceId()));
            ((ObservableSubscribeProxy) Injection.net().updateGroupDevices(j, group.getDeviceIds()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda21
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Injection.repo().group().saveGroup(Group.this);
                }
            });
        }
        observableEmitter.onNext(Integer.valueOf(i));
        observableEmitter.onComplete();
    }

    static /* synthetic */ void lambda$inEurGroup$11(ObservableEmitter observableEmitter, int i, Boolean bool) {
        observableEmitter.onNext(Integer.valueOf(i));
        observableEmitter.onComplete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inEurGroup$18(Device device, final ObservableEmitter observableEmitter, final int i, final Group group, long j, Boolean bool) {
        if (bool.booleanValue()) {
            AsHelper.clearPublishAddress(device, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda26
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    AsHelper.syncGroupSettings(new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda2
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj2) {
                            ActReplaceVM.lambda$inEurGroup$15(ObservableEmitter.this, r2, (Boolean) obj2);
                        }
                    });
                }
            });
            return;
        }
        if (group != null && group.getDeviceIds().size() > 0) {
            group.getDeviceIds().remove(Long.valueOf(this.oldDevice.getDeviceId()));
            ((ObservableSubscribeProxy) Injection.net().updateGroupDevices(j, group.getDeviceIds()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda27
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    Injection.repo().group().saveGroup(Group.this);
                }
            });
        }
        observableEmitter.onNext(Integer.valueOf(i));
        observableEmitter.onComplete();
    }

    static /* synthetic */ void lambda$inEurGroup$15(ObservableEmitter observableEmitter, int i, Boolean bool) {
        observableEmitter.onNext(Integer.valueOf(i));
        observableEmitter.onComplete();
    }

    private void batchControlGroup(List<Observable<Integer>> request) {
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.9
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
                ActReplaceVM.this.nextStep();
            }
        });
    }

    private List<SettingCmdParam.RelateInfo> generateRelateInfoList(Group group) {
        ArrayList arrayList = new ArrayList();
        RelateInfoAssistant relateInfoAssistant = new RelateInfoAssistant(group);
        List<Boolean> onOffStates = group.getGroupState().getOnOffStates();
        for (int i = 0; i < ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group)); i++) {
            SettingCmdParam.RelateInfo relateInfo = new SettingCmdParam.RelateInfo();
            RelatedInfoExtParam.RelateInfo relateInfo2 = relateInfoAssistant.getRelateInfo(i);
            int relateAddress = RelateInfoUtils.getRelateAddress(relateInfo2);
            int relateZone = RelateInfoUtils.getRelateZone(relateInfo2);
            int i2 = relateInfo2 != null ? relateInfo2.type : 0;
            int i3 = relateInfo2 != null ? relateInfo2.action : 0;
            int relateDeviceType = RelateInfoUtils.getRelateDeviceType(relateInfo2);
            int i4 = (onOffStates == null || onOffStates.isEmpty() || onOffStates.size() < ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group)) || !onOffStates.get(i).booleanValue()) ? 0 : 1;
            relateInfo.setRelateAddress(relateAddress);
            relateInfo.setRelateZone(relateZone);
            relateInfo.setRelateType(i2);
            relateInfo.setRelateExtData(i3);
            relateInfo.setRelateLightType(relateDeviceType);
            relateInfo.setZoneOnOffState(i4);
            arrayList.add(relateInfo);
        }
        return arrayList;
    }

    private void synGroupSettingToDevice(Group group, Device device, ObservableEmitter<Integer> emitter) {
        if (group.getSetting() != null) {
            SmartPanelSettingState smartPanelSettingState = (SmartPanelSettingState) GsonUtils.fromJson(group.getSetting(), new TypeToken<SmartPanelSettingState>(this) { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.10
            }.getType());
            if (smartPanelSettingState == null) {
                smartPanelSettingState = new SmartPanelSettingState();
            }
            setting(device, group, smartPanelSettingState, emitter);
            return;
        }
        if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
            settingScreenToDevice(device, group, emitter);
        } else {
            setpowerOffStatus(group, device, emitter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void settingScreenToDevice(final Device device, final Group group, final ObservableEmitter<Integer> observableEmitter) {
        ArrayList arrayList = new ArrayList();
        if (group.getScreenSetting() != null) {
            SmartPanelScreenState smartPanelScreenState = (SmartPanelScreenState) GsonUtils.fromJson(group.getScreenSetting(), new TypeToken<SmartPanelScreenState>(this) { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.11
            }.getType());
            if (smartPanelScreenState == null) {
                smartPanelScreenState = new SmartPanelScreenState();
            }
            if (smartPanelScreenState.getScreens() != null && smartPanelScreenState.getTheme() != null && smartPanelScreenState.getScreens().size() == smartPanelScreenState.getTheme().size()) {
                arrayList.add(Integer.valueOf(smartPanelScreenState.getScreensaverMode()));
                arrayList.add(Integer.valueOf((smartPanelScreenState.getScreensaverTime() >> 8) & 255));
                arrayList.add(Integer.valueOf(smartPanelScreenState.getScreensaverTime() & 255));
                for (int i = 0; i < smartPanelScreenState.getScreens().size(); i++) {
                    SmartPanelScreenState.ScreenInfo screenInfo = smartPanelScreenState.getScreens().get(i);
                    arrayList.add(Integer.valueOf(screenInfo.isShow() ? 1 : 0));
                    arrayList.add(smartPanelScreenState.getTheme().get(i));
                    arrayList.add(Integer.valueOf(!screenInfo.isScreenTimeClose() ? 1 : 0));
                    arrayList.add(Integer.valueOf(screenInfo.getStartM()));
                    arrayList.add(Integer.valueOf(screenInfo.getStartS()));
                    arrayList.add(Integer.valueOf(screenInfo.getEndM()));
                    arrayList.add(Integer.valueOf(screenInfo.getEndS()));
                }
            }
        } else {
            arrayList.add(1);
            arrayList.add(0);
            arrayList.add(30);
            arrayList.add(1);
            arrayList.add(1);
            arrayList.add(1);
            arrayList.add(8);
            arrayList.add(0);
            arrayList.add(17);
            arrayList.add(59);
            arrayList.add(1);
            arrayList.add(2);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(1);
            arrayList.add(3);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
        }
        CmdAssistant.getDeviceAssistant(device, new int[0]).syncSmartPanelGroupScreen(getContext(), arrayList, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.12
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                ActReplaceVM.this.setpowerOffStatus(group, device, observableEmitter);
            }
        });
    }

    public void setpowerOffStatus(final Group group, final Device device, final ObservableEmitter<Integer> emitter) {
        if (device != null) {
            CmdAssistant.getLightCmdAssistant(device, new int[0]).setOnStateWithoutDialog(this.context, group.getMemorizePowerOff(), 0, 0, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda16
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActReplaceVM.this.lambda$setpowerOffStatus$20(device, group, emitter, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setpowerOffStatus$20(Device device, Group group, ObservableEmitter observableEmitter, Boolean bool) {
        if (bool.booleanValue()) {
            setResult(-1);
        }
        if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO) || device.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO) || device.getProductId().equals(ProductId.ID_SMART_PANEL_G4)) {
            syncElderlyMode(device, group, observableEmitter);
        } else {
            setRelayPosition(device, group, observableEmitter);
        }
    }

    private void syncElderlyMode(final Device device, final Group group, final ObservableEmitter<Integer> emitter) {
        CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenElderlyMode(this.context, RelateInfoUtils.relateInfoAssistant.getSwitchScreenBigIcon() == 0 ? 1 : RelateInfoUtils.relateInfoAssistant.getSwitchScreenBigIcon(), new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda22
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$syncElderlyMode$21(device, group, emitter, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncElderlyMode$21(Device device, Group group, ObservableEmitter observableEmitter, ResponseMsg responseMsg) {
        syncLanguage(device, group, observableEmitter);
    }

    private void syncLanguage(final Device device, final Group group, final ObservableEmitter<Integer> emitter) {
        CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenLanguage(this.context, RelateInfoUtils.relateInfoAssistant.getSwitchScreenLanguage() == 0 ? 1 : RelateInfoUtils.relateInfoAssistant.getSwitchScreenLanguage(), new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$syncLanguage$22(device, group, emitter, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncLanguage$22(Device device, Group group, ObservableEmitter observableEmitter, ResponseMsg responseMsg) {
        setSensingDistanceOn(device, group, observableEmitter);
    }

    public void setSensingDistanceOn(final Device device, final Group group, final ObservableEmitter<Integer> emitter) {
        if (group.getScreenSetting() != null) {
            final SmartPanelSettingState smartPanelSettingState = (SmartPanelSettingState) GsonUtils.fromJson(group.getPanelSettingState(), new TypeToken<SmartPanelSettingState>(this) { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.13
            }.getType());
            if (smartPanelSettingState != null) {
                CmdAssistant.getDeviceAssistant(device, new int[0]).setWaveEnable(ActivityUtils.getTopActivity(), smartPanelSettingState.getSensingDistanceSwitch() == 1, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda31
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        ActReplaceVM.this.lambda$setSensingDistanceOn$23(device, smartPanelSettingState, group, emitter, (ResponseMsg) obj);
                    }
                });
                return;
            } else {
                setRelayPosition(device, group, emitter);
                return;
            }
        }
        setRelayPosition(device, group, emitter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensingDistanceOn$23(Device device, SmartPanelSettingState smartPanelSettingState, Group group, ObservableEmitter observableEmitter, ResponseMsg responseMsg) {
        sendSensitivity(device, smartPanelSettingState, group, observableEmitter);
    }

    private void sendSensitivity(final Device device, SmartPanelSettingState state, final Group group, final ObservableEmitter<Integer> emitter) {
        CmdAssistant.getDeviceAssistant(device, new int[0]).setSensitivity(ActivityUtils.getTopActivity(), state.getSensingDistance(), new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda19
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$sendSensitivity$24(device, group, emitter, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendSensitivity$24(Device device, Group group, ObservableEmitter observableEmitter, Boolean bool) {
        setRelayPosition(device, group, observableEmitter);
    }

    private void setRelayPosition(final Device device, final Group group, final ObservableEmitter<Integer> emitter) {
        List<RelateInfoItem> list = RelateInfoUtils.relatedInfoList;
        int[] ints = getInts(ProductRepository.getLightColorType((Object) device));
        if (ints.length > 0) {
            for (int i = 0; i < ints.length; i++) {
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    if (list.get(i2).relateInfo.switchIndex == i + 1) {
                        ints[i] = i2 + 1;
                        break;
                    }
                    i2++;
                }
            }
            CmdAssistant.getDeviceAssistant(device, new int[0]).relayMapping(getContext(), ints, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.14
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    ActReplaceVM.this.setGroupLongRelateInfo(device, group, emitter);
                }
            });
            return;
        }
        setGroupLongRelateInfo(device, group, emitter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGroupLongRelateInfo(final Device device, final Group group, final ObservableEmitter<Integer> emitter) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (RelateInfoItem relateInfoItem : RelateInfoUtils.longClickRelatedInfoList) {
            if (relateInfoItem.relateInfo != null) {
                arrayList.add(setLongClick(relateInfoItem, device, i));
            }
            i++;
        }
        if (arrayList.isEmpty()) {
            setSceneBrt(device, emitter, group);
        } else {
            ((ObservableSubscribeProxy) Observable.concat(arrayList).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Object>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.15
                @Override // io.reactivex.Observer
                public void onError(Throwable e) {
                }

                @Override // io.reactivex.Observer
                public void onNext(Object o) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable d2) {
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    ActReplaceVM.this.setSceneBrt(device, emitter, group);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSceneBrt(final Device device, final ObservableEmitter<Integer> emitter, final Group group) {
        if (RelateInfoUtils.relateInfoAssistant.getSceneDimmerBrt() != null && RelateInfoUtils.relateInfoAssistant.getSceneDimmerBrt().length() >= 6) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setBrtButton(getContext(), Math.round((Integer.parseInt(RelateInfoUtils.relateInfoAssistant.getSceneDimmerBrt().substring(4, 6), 16) * 255.0f) / 100.0f), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.16
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    ActReplaceVM.this.syncDisplayData(device, emitter, group);
                }
            });
        } else {
            syncDisplayData(device, emitter, group);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.replace.ActReplaceVM$17, reason: invalid class name */
    class AnonymousClass17 implements ObservableOnSubscribe<Object> {
        final /* synthetic */ Device val$device;
        final /* synthetic */ RelateInfoItem val$item;
        final /* synthetic */ int val$selectPosition;

        AnonymousClass17(final Device val$device, final RelateInfoItem val$item, final int val$selectPosition) {
            this.val$device = val$device;
            this.val$item = val$item;
            this.val$selectPosition = val$selectPosition;
        }

        @Override // io.reactivex.ObservableOnSubscribe
        public void subscribe(final ObservableEmitter<Object> emitter) throws Exception {
            int i;
            int i2;
            int unicastAddress = ((BleParam) this.val$device.getParam(BleParam.class)).getUnicastAddress();
            if (this.val$item.relateInfo.type == 8) {
                i = ((BleParam) Injection.repo().device().getDeviceByDeviceId(Injection.repo().scene().getSceneBySceneId(this.val$item.relateInfo.objectId).getMacdeviceid()).getParam(BleParam.class)).getUnicastAddress();
                i2 = DaliProHelper.BROADCAST_ADD;
            } else {
                i = 65025;
                i2 = 1;
            }
            CmdAssistant.getSettingCmdAssistant(this.val$device, new int[0]).subscribeInSwitchPanelScene(ActReplaceVM.this.getContext(), 1 << this.val$selectPosition, unicastAddress, i, 2, i2, this.val$item.relateInfo.action, this.val$item.relateInfo.type, this.val$item.relateInfo.keyActionExtra, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$17$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActReplaceVM.AnonymousClass17.lambda$subscribe$0(ObservableEmitter.this, (ResponseMsg) obj);
                }
            });
        }

        static /* synthetic */ void lambda$subscribe$0(ObservableEmitter observableEmitter, ResponseMsg responseMsg) {
            observableEmitter.onNext(true);
            observableEmitter.onComplete();
        }
    }

    private Observable<Object> setLongClick(RelateInfoItem item, Device device, int selectPosition) {
        return Observable.create(new AnonymousClass17(device, item, selectPosition));
    }

    private int[] getInts(int type) {
        int[] iArr = new int[0];
        if (type == 19) {
            return new int[3];
        }
        if (type == 21 || type == 18 || type == 11) {
            return new int[4];
        }
        if (type == 8) {
            return new int[1];
        }
        if (type == 9) {
            return new int[2];
        }
        return type == 10 ? new int[3] : iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncDisplayData(final Device device, final ObservableEmitter<Integer> emitter, Group group) {
        SmartPanelParam.DisplayParam displayParam;
        List<RelateInfoItem> list = RelateInfoUtils.relatedInfoList;
        ArrayList arrayList = new ArrayList();
        for (RelateInfoItem relateInfoItem : list) {
            RelatedInfoExtParam.RelateInfo relateInfo = relateInfoItem.relateInfo;
            SmartPanelParam.DisplayParam displayParam2 = null;
            if (relateInfo != null) {
                if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue()) {
                    if (relateInfo.objectId <= 0 && RelaySeparationHelper.isRelaySeparationDevice(group)) {
                        try {
                            Group relaySubGroup = RelaySeparationHelper.getRelaySubGroup(group, relateInfo.switchIndex);
                            if (relaySubGroup != null) {
                                SmartPanelParam.DisplayParam displayParam3 = new SmartPanelParam.DisplayParam();
                                String[] split = relaySubGroup.getName().split("\n");
                                displayParam3.setFirstType(1);
                                displayParam3.setFirstData(split[0].getBytes("gb2312"));
                                if (split.length > 1) {
                                    displayParam3.setSecondType(1);
                                    displayParam3.setSecondData(split[1].getBytes("gb2312"));
                                }
                                arrayList.add(displayParam3);
                            } else {
                                arrayList.add(null);
                            }
                        } catch (Exception unused) {
                            arrayList.add(null);
                        }
                    } else {
                        arrayList.add(null);
                    }
                } else if (relateInfo.objectId != 0 && relateInfoItem.infoName == null) {
                    arrayList.add(null);
                } else {
                    try {
                        displayParam = new SmartPanelParam.DisplayParam();
                    } catch (UnsupportedEncodingException e) {
                        e = e;
                    }
                    try {
                        if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
                            String[] split2 = relateInfo.screenStr.split("\n");
                            displayParam.setFirstType(1);
                            displayParam.setFirstData(split2[0].getBytes("gb2312"));
                            if (split2.length > 1) {
                                displayParam.setSecondType(1);
                                displayParam.setSecondData(split2[1].getBytes("gb2312"));
                            }
                        } else if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIcon.getValue()) {
                            displayParam.setFirstType(2);
                            displayParam.setSecondType(0);
                            int sendIconIndex = ScreenIconUtils.getSendIconIndex(relateInfo.iconIndex);
                            displayParam.setFirstData(new byte[]{(byte) ((sendIconIndex >> 8) & 255), (byte) (sendIconIndex & 255)});
                        } else if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeIconWord.getValue()) {
                            displayParam.setFirstType(2);
                            int sendIconIndex2 = ScreenIconUtils.getSendIconIndex(relateInfo.iconIndex);
                            displayParam.setFirstData(new byte[]{(byte) ((sendIconIndex2 >> 8) & 255), (byte) (sendIconIndex2 & 255)});
                            displayParam.setSecondType(1);
                            displayParam.setSecondData(relateInfo.screenStr.getBytes("gb2312"));
                        }
                        displayParam2 = displayParam;
                    } catch (UnsupportedEncodingException e2) {
                        e = e2;
                        displayParam2 = displayParam;
                        e.printStackTrace();
                        arrayList.add(displayParam2);
                    }
                }
            }
            arrayList.add(displayParam2);
        }
        CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenData(this.context, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$syncDisplayData$25(device, emitter, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$syncDisplayData$25(Device device, ObservableEmitter observableEmitter, ResponseMsg responseMsg) {
        syncLongClickDisplayData(device, observableEmitter);
    }

    private void syncLongClickDisplayData(Device device, final ObservableEmitter<Integer> emitter) {
        List<RelateInfoItem> list = RelateInfoUtils.longClickRelatedInfoList;
        ArrayList arrayList = new ArrayList();
        Iterator<RelateInfoItem> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            RelatedInfoExtParam.RelateInfo relateInfo = it.next().relateInfo;
            if (relateInfo != null) {
                try {
                    if (relateInfo.screenType == RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue()) {
                        SmartPanelParam.DisplayParam displayParam = new SmartPanelParam.DisplayParam();
                        displayParam.setZone(i);
                        String[] split = relateInfo.screenStr.split("\n");
                        displayParam.setFirstType(3);
                        displayParam.setFirstData(split[0].getBytes("gb2312"));
                        if (split.length > 1) {
                            displayParam.setSecondType(3);
                            displayParam.setSecondData(split[1].getBytes("gb2312"));
                        }
                        arrayList.add(displayParam);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
        CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenLongClickData(getContext(), arrayList, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda11
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ObservableEmitter.this.onComplete();
            }
        });
    }

    private void setting(final Device device, final Group group, SmartPanelSettingState state, final ObservableEmitter<Integer> emitter) {
        if (device != null) {
            CmdAssistant.getSettingCmdAssistant(device, new int[0]).setSmartPanelMode(this.context, state.getDoubleLight(), state.getReverseLight(), state.getNightMode(), state.getEngravedTextMode(), state.getStartH(), state.getStartM(), state.getEndH(), state.getEndM(), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.18
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    if (aBoolean.booleanValue()) {
                        ActReplaceVM.this.setResult(-1);
                    }
                    if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
                        ActReplaceVM.this.settingScreenToDevice(device, group, emitter);
                    } else {
                        ActReplaceVM.this.setpowerOffStatus(group, device, emitter);
                    }
                }
            });
        } else {
            emitter.onComplete();
        }
    }

    public void syncSettingData() {
        int i;
        String json = GsonUtils.toJson(this.dataResponse.getBackData());
        if (!this.needSyncSetting || TextUtils.isEmpty(json)) {
            nextStep();
            return;
        }
        this.cmdList.clear();
        ArrayList arrayList = new ArrayList();
        JSONObject parseObject = JSON.parseObject(json);
        Iterator<String> it = parseObject.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            String str = (String) parseObject.get(next);
            if (!TextUtils.isEmpty(str)) {
                arrayList.add(next);
                if (UpdateBackDataRequest.LIGHT_TYPE.equals(next)) {
                    this.cmdList.add(0, str);
                } else if (UpdateBackDataRequest.RELAY_POSITION.equals(next)) {
                    this.cmdList.add(0, str);
                } else if (next.startsWith(UpdateBackDataRequest.BIND)) {
                    this.cmdList.add(arrayList.contains(UpdateBackDataRequest.RELAY_POSITION) ? 1 : 0, str);
                } else {
                    this.cmdList.add(str);
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (i = 0; i < this.cmdList.size(); i++) {
            arrayList2.add(sendSettingData(this.cmdList.get(i), i));
        }
        batchControlSettings(arrayList2);
    }

    private Observable<Integer> sendSettingData(final String cmdData, final int index) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda25
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ActReplaceVM.this.lambda$sendSettingData$27(cmdData, index, observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendSettingData$27(String str, final int i, final ObservableEmitter observableEmitter) throws Exception {
        Injection.message().create(this.context).cmd(new CmdBle(str)).resendTimes(2).control(this.replaceBean.newDevice).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.19
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                if (msg != null && msg.getStateCode() != 0 && msg.getStateCode() != 153) {
                    ActReplaceVM.this.setErrorView();
                } else {
                    observableEmitter.onNext(Integer.valueOf(i));
                    observableEmitter.onComplete();
                }
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                observableEmitter.onNext(Integer.valueOf(i));
                observableEmitter.onComplete();
            }
        }).enqueue();
    }

    private void batchControlSettings(List<Observable<Integer>> request) {
        ((ObservableSubscribeProxy) Observable.concat(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.20
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
                ActReplaceVM.this.nextStep();
            }
        });
    }

    private void removeMesh() {
        Injection.mesh().resetNodeByCmd(this.oldDevice, new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda14
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$removeMesh$28((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeMesh$28(Boolean bool) {
        if (bool.booleanValue()) {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "============resetNodeByCmd Success============");
        } else {
            LHomeLog.i(Constants.MESH_LOG, getClass(), "============resetNodeByCmd Fail============");
        }
        removeNode();
    }

    private void removeNode() {
        Injection.mesh().removeNode(this.oldDevice.getUnicastAddress(), new IRemoveNodeCallback() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.21
            @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
            public void removeSuccess() {
                LHomeLog.i(Constants.MESH_LOG, ActReplaceVM.class, "============removeNode Success============");
                if (ActReplaceVM.this.replaceBean.getCurrentState() == 6) {
                    ActReplaceVM.this.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.21.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ActReplaceVM.this.nextStep();
                        }
                    }, ActReplaceVM.this.getRemoveDelayTime());
                } else {
                    ActReplaceVM.this.setErrorView();
                }
            }

            @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
            public void removeFail() {
                LHomeLog.i(Constants.MESH_LOG, ActReplaceVM.class, "============removeNode Fail============");
                ActReplaceVM.this.setErrorView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRemoveDelayTime() {
        String productId = this.oldDevice.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "3683369128495808":
            case "4249823578721536":
            case "3701704216101056":
            case "3701703750123712":
                return 8000;
            default:
                return 3000;
        }
    }

    private void modifyNDID() {
        CmdAssistant.getSettingCmdAssistant(this.replaceBean.newDevice, new int[0]).setDeviceNDID(this.context, this.oldDevice.getUnicastAddress(), new IAction() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda7
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActReplaceVM.this.lambda$modifyNDID$29((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$modifyNDID$29(Boolean bool) {
        if (bool.booleanValue()) {
            nextStep();
        } else {
            setErrorView();
        }
    }

    private void updateParam() {
        BleParam bleParam = (BleParam) GsonUtils.fromJson(this.replaceBean.newDevice.getParam(), BleParam.class);
        final BleParam bleParam2 = (BleParam) GsonUtils.fromJson(this.oldDevice.getParam(), BleParam.class);
        bleParam2.setDeviceUUID(bleParam.getDeviceUUID());
        bleParam2.setDeviceKey(bleParam.getDeviceKey());
        ((ObservableSubscribeProxy) Injection.net().updateDeviceParamByReplace(this.oldDevice.getDeviceId(), this.replaceBean.newDevice.getWifiMac(), GsonUtils.toJson(bleParam2), this.replaceBean.newDevice.getMcuversion(), this.replaceBean.newDevice.getBleversion()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActReplaceVM.this.lambda$updateParam$30(bleParam2, obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParam$30(BleParam bleParam, Object obj) throws Exception {
        this.oldDevice.setWifiMac(this.replaceBean.newDevice.getWifiMac());
        this.oldDevice.setParam(bleParam);
        this.oldDevice.setMcuversion(this.replaceBean.newDevice.getMcuversion());
        this.oldDevice.setBleversion(this.replaceBean.newDevice.getBleversion());
        Injection.repo().device().saveDevice(this.oldDevice);
        Injection.repo().device().removeDeviceFromDb(this.replaceBean.newDevice.getId());
        nextStep();
    }

    private void updateParamLocal() {
        BleParam bleParam = (BleParam) GsonUtils.fromJson(this.replaceBean.newDevice.getParam(), BleParam.class);
        BleParam bleParam2 = (BleParam) GsonUtils.fromJson(this.oldDevice.getParam(), BleParam.class);
        bleParam2.setDeviceUUID(bleParam.getDeviceUUID());
        bleParam2.setDeviceKey(bleParam.getDeviceKey());
        this.oldDevice.setWifiMac(this.replaceBean.newDevice.getWifiMac());
        this.oldDevice.setParam(bleParam2);
        this.oldDevice.setMcuversion(this.replaceBean.newDevice.getMcuversion());
        this.oldDevice.setBleversion(this.replaceBean.newDevice.getBleversion());
        Injection.repo().device().saveDevice(this.oldDevice);
        Injection.repo().device().removeDeviceFromDb(this.replaceBean.newDevice.getId());
        nextStep();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshMeshNetwork() {
        final Place selPlace = Injection.repo().home().getSelPlace();
        Injection.mesh().refreshNetwork(GsonUtils.getGson().toJson(FeasyMeshNetHelper.getMeshJsonBean(selPlace, selPlace.getNetKey(), selPlace.getAppKey(), selPlace.getProvisionerAddress())), new IRefreshNetworkCallback() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM.22
            @Override // com.ltech.smarthome.blemesh.IRefreshNetworkCallback
            public void onRefreshSuccess() {
                ActReplaceVM.this.retryTimes = 3;
                if (Injection.mesh().getConnectedDevice() == null) {
                    Injection.mesh().checkNearbyDevice(selPlace.getMeshUUID(), ActReplaceVM.this.getLifecycleOwner());
                }
                ActReplaceVM.this.nextStep();
            }

            @Override // com.ltech.smarthome.blemesh.IRefreshNetworkCallback
            public void onRefreshFail() {
                if (ActReplaceVM.this.retryTimes > 0) {
                    ActReplaceVM actReplaceVM = ActReplaceVM.this;
                    actReplaceVM.retryTimes--;
                    ActReplaceVM.this.refreshMeshNetwork();
                    return;
                }
                ActReplaceVM.this.setErrorView();
            }
        });
    }

    private void startSuperPanelReplace() {
        ((ObservableSubscribeProxy) Injection.net().superPanelReplace(this.oldDevice.getDeviceId(), this.replaceBean.newDevice.getDeviceId()).delaySubscription(200L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ActReplaceVM$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActReplaceVM.this.lambda$startSuperPanelReplace$31(obj);
            }
        }, this.errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSuperPanelReplace$31(Object obj) throws Exception {
        nextStep();
    }

    public static class ReplaceBean {
        public static final int END = 11;
        public static final int GET_DATA = 2;
        public static final int MODIFY_NDID = 7;
        public static final int REFRESH_MESH = 9;
        public static final int REMOVE_NODE = 6;
        public static final int RESTORE_NEW_DEVICE = 1;
        public static final int SET_PUBLISH_ADDRESS = 15;
        public static final int SUPER_PANEL_REPLACE_START = 12;
        public static final int SYNC_GROUP_DATA = 4;
        public static final int SYNC_SCENE_DATA = 3;
        public static final int SYNC_SETTING_DATA = 5;
        public static final int UPDATE_FAMILY_DEVICE_GROUP = 10;
        public static final int UPDATE_PARAM = 8;
        public static final int UPDATE_PARAM_LOCAL = 14;
        public static final int WAIT_FOR_REPLACE = 13;
        public Device newDevice;
        private List<Integer> processList = new ArrayList();
        private int statePos = -1;

        private ReplaceBean(Device newDevice) {
            this.newDevice = newDevice;
        }

        public static ReplaceBean processOne(Device newDevice) {
            ReplaceBean replaceBean = new ReplaceBean(newDevice);
            replaceBean.processList.add(1);
            if (EurHelper.isEb125(newDevice) || ProductRepository.isAsPanel(newDevice)) {
                replaceBean.processList.add(15);
            }
            replaceBean.processList.add(2);
            if (ProductRepository.isSmartPanelDevice(newDevice.getProductId())) {
                replaceBean.processList.add(4);
                replaceBean.processList.add(5);
            } else {
                replaceBean.processList.add(5);
                replaceBean.processList.add(4);
            }
            replaceBean.processList.add(3);
            replaceBean.processList.add(6);
            replaceBean.processList.add(7);
            replaceBean.processList.add(8);
            replaceBean.processList.add(9);
            replaceBean.processList.add(10);
            replaceBean.processList.add(11);
            return replaceBean;
        }

        public static ReplaceBean processTwo(Device newDevice) {
            ReplaceBean replaceBean = new ReplaceBean(newDevice);
            replaceBean.processList.add(12);
            replaceBean.processList.add(13);
            replaceBean.processList.add(9);
            replaceBean.processList.add(14);
            replaceBean.processList.add(11);
            return replaceBean;
        }

        public int nextState() {
            int i = this.statePos + 1;
            this.statePos = i;
            if (i >= this.processList.size()) {
                this.statePos = this.processList.size() - 1;
            }
            return this.processList.get(this.statePos).intValue();
        }

        public int getCurrentState() {
            return this.processList.get(this.statePos).intValue();
        }

        public int getCurrentPos() {
            return this.statePos;
        }
    }
}