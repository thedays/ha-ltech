package com.ltech.smarthome.ui.matter;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.google.gson.reflect.TypeToken;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.CgKitExt;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.MatterDeviceResponse;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.MatterQrDialog;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class ActMatterSubVM extends BaseViewModel {
    public long controlId;
    public Device device;
    private MatterQrDialog dialog;
    public boolean isEnable;
    public List<Fragment> fragmentList = new ArrayList();
    public List<Long> deviceIds = new ArrayList();
    public List<Long> groupIds = new ArrayList();
    public List<Long> scenesIds = new ArrayList();
    public MutableLiveData<List<Fabric>> fabricData = new MutableLiveData<>();
    public MutableLiveData<Boolean> fabricShow = new MutableLiveData<>();
    public MutableLiveData<List<MatterDeviceResponse.MatterDevice>> matterDevices = new MutableLiveData<>();
    public MutableLiveData<List<MatterDeviceResponse.MatterDevice>> matterScenes = new MutableLiveData<>();
    public MutableLiveData<Boolean> offline = new MutableLiveData<>();
    public MutableLiveData<Integer> chooseTabEvent = new MutableLiveData<>(0);
    public MutableLiveData<Boolean> empty = new MutableLiveData<>();

    public void checkQrCode(final IAction<String> iAction) {
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).queryMatterCode(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    String resData = responseMsg.getResData();
                    int parseInt = (Integer.parseInt(resData.substring(16, 18), 16) * 2) + 18;
                    String hexString2String = StringUtils.hexString2String(resData.substring(18, parseInt));
                    StringBuilder sb = new StringBuilder(StringUtils.hexString2String(resData.substring(parseInt)));
                    sb.insert(4, Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                    sb.insert(8, Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                    String sb2 = sb.toString();
                    LHomeLog.e("checkQrCode", getClass(), hexString2String);
                    iAction.act(hexString2String + MqttTopic.MULTI_LEVEL_WILDCARD + sb2);
                    return;
                }
                iAction.act(null);
            }
        });
    }

    public void checkFabric() {
        CgKitExt cgKitExt = (CgKitExt) this.device.getExtParam(CgKitExt.class);
        if (cgKitExt != null) {
            this.isEnable = cgKitExt.getFabricsCount() > 0;
        }
        this.fabricShow.setValue(Boolean.valueOf(this.isEnable));
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).queryMatterFabric(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.2
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code restructure failed: missing block: B:38:0x0084, code lost:
            
                if (r8.equals("1349") == false) goto L16;
             */
            @Override // com.ltech.smarthome.base.IAction
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void act(com.smart.message.ResponseMsg r14) {
                /*
                    Method dump skipped, instructions count: 336
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.matter.ActMatterSubVM.AnonymousClass2.act(com.smart.message.ResponseMsg):void");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateExt(int count) {
        final CgKitExt cgKitExt = (CgKitExt) this.device.getExtParam(CgKitExt.class);
        cgKitExt.setFabricsCount(count);
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(this.device.getDeviceId(), GsonUtils.toJson(cgKitExt)).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActMatterSubVM.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMatterSubVM.this.lambda$updateExt$0(cgKitExt, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExt$0(CgKitExt cgKitExt, Object obj) throws Exception {
        this.device.setExtParam(GsonUtils.toJson(cgKitExt));
        Injection.repo().device().saveDevice(this.device);
    }

    public void remove(int i, final IAction<Boolean> iAction) {
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).removeMatterFabric(ActivityUtils.getTopActivity(), i, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.3
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg != null && responseMsg.getStateCode() == 0) {
                    iAction.act(true);
                } else {
                    iAction.act(false);
                }
            }
        });
    }

    public void showDialog(BaseNormalActivity activity) {
        MatterQrDialog onMatterCallBack = MatterQrDialog.asDefault().setOnMatterCallBack(new MatterQrDialog.OnMatterCallBack() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.4
            @Override // com.ltech.smarthome.view.dialog.MatterQrDialog.OnMatterCallBack
            public void onRefresh() {
                ActMatterSubVM.this.checkQrCode(new IAction<String>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.4.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(String s) {
                        if (s == null) {
                            ActMatterSubVM.this.dialog.showFail();
                        } else {
                            String[] split = s.split(MqttTopic.MULTI_LEVEL_WILDCARD);
                            ActMatterSubVM.this.dialog.showSuccess(split[0], split[1]);
                        }
                    }
                });
            }
        });
        this.dialog = onMatterCallBack;
        onMatterCallBack.showDialog(activity);
        this.dialog.setDismissRunnable(new Runnable() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.5
            @Override // java.lang.Runnable
            public void run() {
                ActMatterSubVM.this.checkFabric();
            }
        });
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.6
            @Override // java.lang.Runnable
            public void run() {
                ActMatterSubVM.this.checkQrCode(new IAction<String>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.6.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(String s) {
                        if (s == null) {
                            ActMatterSubVM.this.dialog.showFail();
                        } else {
                            String[] split = s.split(MqttTopic.MULTI_LEVEL_WILDCARD);
                            ActMatterSubVM.this.dialog.showSuccess(split[0], split[1]);
                        }
                    }
                });
            }
        }, 500L);
    }

    public void loadFabric(Intent intent) {
        List<Fabric> list = (List) GsonUtils.fromJson(intent.getStringExtra(com.ltech.smarthome.utils.Constants.MODE_DATA), new TypeToken<List<Fabric>>(this) { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.7
        }.getType());
        if (list != null) {
            this.fabricData.setValue(list);
        } else {
            checkFabric();
        }
    }

    public void loadDevice() {
        ((ObservableSubscribeProxy) Injection.net().getCgKitSyncDeviceByMatter(this.device.getDeviceId(), this.device.getWifiMac()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer<MatterDeviceResponse>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.8
            @Override // io.reactivex.functions.Consumer
            public void accept(MatterDeviceResponse response) throws Exception {
                ArrayList arrayList = new ArrayList();
                List<MatterDeviceResponse.MatterDevice> devices = response.getDevices();
                List<MatterDeviceResponse.MatterDevice> groups = response.getGroups();
                List<MatterDeviceResponse.MatterDevice> scenes = response.getScenes();
                ActMatterSubVM.this.deviceIds = new ArrayList();
                ActMatterSubVM.this.groupIds = new ArrayList();
                ActMatterSubVM.this.scenesIds = new ArrayList();
                boolean z = true;
                if (devices != null) {
                    for (MatterDeviceResponse.MatterDevice matterDevice : devices) {
                        arrayList.add(ActMatterSubVM.this.getMatterDeviceData(matterDevice));
                        ActMatterSubVM.this.deviceIds.add(Long.valueOf(matterDevice.getObjectid()));
                        z = false;
                    }
                }
                if (groups != null) {
                    for (MatterDeviceResponse.MatterDevice matterDevice2 : groups) {
                        arrayList.add(ActMatterSubVM.this.getMatterDeviceData(matterDevice2));
                        ActMatterSubVM.this.groupIds.add(Long.valueOf(matterDevice2.getObjectid()));
                        z = false;
                    }
                }
                if (scenes != null) {
                    ArrayList arrayList2 = new ArrayList();
                    for (MatterDeviceResponse.MatterDevice matterDevice3 : scenes) {
                        arrayList2.add(ActMatterSubVM.this.getMatterDeviceData(matterDevice3));
                        ActMatterSubVM.this.scenesIds.add(Long.valueOf(matterDevice3.getObjectid()));
                        z = false;
                    }
                    ActMatterSubVM.this.matterScenes.setValue(arrayList2);
                }
                ActMatterSubVM.this.matterDevices.setValue(arrayList);
                ActMatterSubVM.this.empty.setValue(Boolean.valueOf(z));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MatterDeviceResponse.MatterDevice getMatterDeviceData(MatterDeviceResponse.MatterDevice item) {
        Scene sceneBySceneId;
        if (item.getObjecttype() == 1) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(item.getObjectid());
            if (deviceByDeviceId != null) {
                item.setIcon(ProductRepository.getProductIcon(deviceByDeviceId));
                item.setName(deviceByDeviceId.getName());
                item.setSub(deviceByDeviceId.getFloorName() + " " + deviceByDeviceId.getRoomName());
                item.setNum(ProductRepository.getRelayCount(ProductRepository.getLightColorType((Object) deviceByDeviceId)));
                return item;
            }
        } else if (item.getObjecttype() == 2) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(item.getObjectid());
            if (groupByGroupId != null) {
                item.setIcon(ProductRepository.getProductIcon(groupByGroupId));
                item.setName(groupByGroupId.getName());
                item.setSub(groupByGroupId.getFloorName() + " " + groupByGroupId.getRoomName());
                item.setNum(ProductRepository.getRelayCount(ProductRepository.getLightColorType((Object) groupByGroupId)));
                return item;
            }
        } else if (item.getObjecttype() == 3 && (sceneBySceneId = Injection.repo().scene().getSceneBySceneId(item.getObjectid())) != null) {
            item.setIcon(SceneHelper.getSceneIcon(getContext(), sceneBySceneId.getIconPos()));
            item.setName(sceneBySceneId.getName());
            item.setSub(sceneBySceneId.getFloorName() + " " + sceneBySceneId.getRoomName());
        }
        return item;
    }

    public Place getCurrentPlace() {
        return Injection.repo().home().getSelectPlace().getValue();
    }

    public void quitMatter() {
        CmdAssistant.getSettingCmdAssistant(this.device, new int[0]).quitMatter(ActivityUtils.getTopActivity(), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.matter.ActMatterSubVM.9
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActMatterSubVM.this.finishActivity(-1, null);
            }
        });
    }

    public static class Fabric {
        private int img;
        private int index;
        private String name;
        private String sub;

        public Fabric(int img, String name, String sub, int index) {
            this.img = img;
            this.name = name;
            this.sub = sub;
            this.index = index;
        }

        public int getImg() {
            return this.img;
        }

        public String getName() {
            return this.name;
        }

        public String getSub() {
            return this.sub;
        }

        public int getIndex() {
            return this.index;
        }
    }
}