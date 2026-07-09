package com.ltech.smarthome.ui.config;

import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.aispeech.dca.Callback2;
import com.aispeech.dca.DcaSdk;
import com.aispeech.dca.device.bean.DeviceBean;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.device_param.CameraParam;
import com.ltech.smarthome.model.repo.DcaRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.net.response.super_panel.AddSuperPanelResponse;
import com.ltech.smarthome.ui.camera.EZManager;
import com.ltech.smarthome.ui.config.ActConfigSuccessVM;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RoomPickHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.SmartToast;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActConfigSuccessVM extends BaseViewModel {
    private long lastTime;
    public MutableLiveData<String> deviceName = new MutableLiveData<>();
    public MutableLiveData<String> roomName = new MutableLiveData<>();
    public SingleLiveEvent<Void> editNameEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> editRoomEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Long> showAddSuperPanelSuccessDialogEvent = new SingleLiveEvent<>();
    public RoomPickHelper roomPickHelper = new RoomPickHelper();
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda3
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActConfigSuccessVM.this.lambda$new$0((View) obj);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id != R.id.bt_next) {
            if (id == R.id.v_device_name) {
                this.editNameEvent.call();
                return;
            } else {
                if (id != R.id.v_own_room) {
                    return;
                }
                this.editRoomEvent.call();
            }
        }
        if (TextUtils.isEmpty(this.deviceName.getValue())) {
            SmartToast.showShort(R.string.device_name_empty);
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.lastTime > 1000) {
            this.lastTime = currentTimeMillis;
            String productId = ConfigHelper.instance().productInfo.getProductId();
            productId.hashCode();
            switch (productId) {
                case "123050811340901":
                case "123050811353501":
                case "3683388245101248":
                case "122042815485901":
                case "122080911090801":
                case "121052512023201":
                    addSuperPanelMini();
                    break;
                case "121111911552501":
                    addWifiCamera();
                    break;
                case "120010615085201":
                    addSuperPanel();
                    break;
                default:
                    addDevice();
                    break;
            }
        }
    }

    private void addDevice() {
        ((ObservableSubscribeProxy) Injection.net().addDevice(ConfigHelper.instance().wifiSingleProductData()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActConfigSuccessVM.this.lambda$addDevice$1((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActConfigSuccessVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActConfigSuccessVM.this.lambda$addDevice$2((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDevice$1(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDevice$2(AddDeviceResponse addDeviceResponse) throws Exception {
        ConfigHelper.instance().addDevice(addDeviceResponse, new String[0]);
        navigation(NavUtils.destination(ActHome.class));
    }

    private void addWifiCamera() {
        ConfigHelper.instance().param = new CameraParam(EZManager.instance().getEzDevice().getVerifyCode(), EZManager.instance().configType, EZManager.instance().cameraNum);
        ((ObservableSubscribeProxy) Injection.net().addDevice(ConfigHelper.instance().wifiCameraData(EZManager.instance().getEzDevice().getSerialNo(), EZManager.instance().getEzDevice().getVerifyCode())).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActConfigSuccessVM.this.lambda$addWifiCamera$3((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActConfigSuccessVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActConfigSuccessVM.this.lambda$addWifiCamera$4((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addWifiCamera$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addWifiCamera$4(AddDeviceResponse addDeviceResponse) throws Exception {
        ConfigHelper.instance().addDevice(addDeviceResponse, new String[0]);
        navigation(NavUtils.destination(ActHome.class));
    }

    private void addSuperPanel() {
        ((ObservableSubscribeProxy) Injection.net().addSuperPanel(ConfigHelper.instance().panelinfo, ConfigHelper.instance().deviceName, ConfigHelper.instance().productInfo.getProductId(), ConfigHelper.instance().placeId + "", ConfigHelper.instance().roomId, ConfigHelper.instance().floorId).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActConfigSuccessVM.this.lambda$addSuperPanel$5((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActConfigSuccessVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActConfigSuccessVM.this.lambda$addSuperPanel$6((AddSuperPanelResponse) obj);
            }
        }, new AnonymousClass1(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addSuperPanel$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addSuperPanel$6(AddSuperPanelResponse addSuperPanelResponse) throws Exception {
        Device device = new Device();
        device.setPlaceId(ConfigHelper.instance().placeId);
        device.setFloorId(ConfigHelper.instance().floorId);
        device.setRoomId(ConfigHelper.instance().roomId);
        device.setDevicesn(addSuperPanelResponse.getDevicesn());
        device.setProductId(addSuperPanelResponse.getProductid());
        device.setDeviceId(addSuperPanelResponse.getDeviceid());
        device.setPlatFormDeviceId(addSuperPanelResponse.getPlatformdeviceid());
        device.setOnlineFlag(addSuperPanelResponse.getOnlineflag());
        device.setWifiMac(addSuperPanelResponse.getMac());
        device.setDeviceName(addSuperPanelResponse.getDevicename());
        device.setMacfalg(addSuperPanelResponse.getMacfalg());
        device.setMacdeviceid(addSuperPanelResponse.getMacdeviceid());
        device.setDeviceState(new DeviceState());
        device.setIndex(1000);
        device.setCreatetime(addSuperPanelResponse.getCreatetime());
        device.setOnlineFlag(1);
        device.setLatitude(addSuperPanelResponse.getLatitude());
        device.setLongitude(addSuperPanelResponse.getLongitude());
        Injection.repo().device().saveDevice(device);
        this.showAddSuperPanelSuccessDialogEvent.setValue(Long.valueOf(device.getDeviceId()));
    }

    /* renamed from: com.ltech.smarthome.ui.config.ActConfigSuccessVM$1, reason: invalid class name */
    class AnonymousClass1 extends SmartErrorComsumer {
        AnonymousClass1(final ActConfigSuccessVM this$0) {
        }

        @Override // com.ltech.smarthome.net.SmartErrorComsumer
        protected void action(Throwable throwable) {
            if (23 == this.errorCode) {
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), R.string.tips, R.string.qr_code_is_overdue).setOkButton(ActivityUtils.getTopActivity().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$1$$ExternalSyntheticLambda0
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        return ActConfigSuccessVM.AnonymousClass1.lambda$action$0(baseDialog, view);
                    }
                });
            } else {
                super.action(throwable);
            }
        }

        static /* synthetic */ boolean lambda$action$0(BaseDialog baseDialog, View view) {
            NavUtils.destination(ActQrCodeScan.class).navigation(ActivityUtils.getTopActivity());
            return false;
        }
    }

    private void addSuperPanelMini() {
        Injection.dca().setAuthCallBack(new DcaRepository.AuthCallBack() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM.2
            @Override // com.ltech.smarthome.model.repo.DcaRepository.AuthCallBack
            public void onAuthFailed() {
            }

            @Override // com.ltech.smarthome.model.repo.DcaRepository.AuthCallBack
            public void onAuthSuccess(String authCode, String codeVerifier, String userId) {
                ActConfigSuccessVM.this.addSbcPanel(authCode, codeVerifier, userId);
            }
        });
        Injection.dca().login(ConfigHelper.instance().mac);
    }

    public void addSbcPanel(String s, String codeVerifier, String userId) {
        ((ObservableSubscribeProxy) Injection.net().addSbcPanel(ConfigHelper.instance().panelinfo, ConfigHelper.instance().deviceName, ConfigHelper.instance().productInfo.getProductId(), ConfigHelper.instance().placeId + "", ConfigHelper.instance().roomId, ConfigHelper.instance().floorId, s, codeVerifier, userId, ApiConstants.getDcaClientId()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActConfigSuccessVM.this.lambda$addSbcPanel$7((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new ActConfigSuccessVM$$ExternalSyntheticLambda1(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActConfigSuccessVM.this.lambda$addSbcPanel$8((AddSuperPanelResponse) obj);
            }
        }, new AnonymousClass3(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addSbcPanel$7(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addSbcPanel$8(AddSuperPanelResponse addSuperPanelResponse) throws Exception {
        SharedPreferenceUtil.edit().removeBean(String.valueOf(Injection.repo().home().getSelectPlace().getValue().getPlaceId()), DcaRepository.DcaInfo.class);
        Device device = new Device();
        device.setPlaceId(ConfigHelper.instance().placeId);
        device.setFloorId(ConfigHelper.instance().floorId);
        device.setRoomId(ConfigHelper.instance().roomId);
        device.setDevicesn(addSuperPanelResponse.getDevicesn());
        device.setProductId(addSuperPanelResponse.getProductid());
        device.setDeviceId(addSuperPanelResponse.getDeviceid());
        device.setPlatFormDeviceId(addSuperPanelResponse.getPlatformdeviceid());
        device.setOnlineFlag(addSuperPanelResponse.getOnlineflag());
        device.setWifiMac(addSuperPanelResponse.getMac());
        device.setDeviceName(addSuperPanelResponse.getDevicename());
        device.setMacfalg(addSuperPanelResponse.getMacfalg());
        device.setMacdeviceid(addSuperPanelResponse.getMacdeviceid());
        device.setDeviceState(new DeviceState());
        device.setCreatetime(addSuperPanelResponse.getCreatetime());
        device.setOnlineFlag(1);
        device.setIndex(1000);
        device.setLatitude(addSuperPanelResponse.getLatitude());
        device.setLongitude(addSuperPanelResponse.getLongitude());
        device.setOauthtype(addSuperPanelResponse.getOauthtype());
        Injection.repo().device().saveDevice(device);
        bindSbcDevice(device);
        this.showAddSuperPanelSuccessDialogEvent.setValue(Long.valueOf(device.getDeviceId()));
    }

    /* renamed from: com.ltech.smarthome.ui.config.ActConfigSuccessVM$3, reason: invalid class name */
    class AnonymousClass3 extends SmartErrorComsumer {
        AnonymousClass3(final ActConfigSuccessVM this$0) {
        }

        @Override // com.ltech.smarthome.net.SmartErrorComsumer
        protected void action(Throwable throwable) {
            if (23 == this.errorCode) {
                MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), R.string.tips, R.string.qr_code_is_overdue).setOkButton(ActivityUtils.getTopActivity().getString(R.string.ok), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM$3$$ExternalSyntheticLambda0
                    @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                    public final boolean onClick(BaseDialog baseDialog, View view) {
                        return ActConfigSuccessVM.AnonymousClass3.lambda$action$0(baseDialog, view);
                    }
                });
            } else {
                super.action(throwable);
            }
        }

        static /* synthetic */ boolean lambda$action$0(BaseDialog baseDialog, View view) {
            NavUtils.destination(ActQrCodeScan.class).navigation(ActivityUtils.getTopActivity());
            return false;
        }
    }

    private void bindSbcDevice(Device device) {
        DeviceBean deviceBean = new DeviceBean();
        deviceBean.setDeviceType("音箱");
        deviceBean.setDeviceName(device.getDevicesn());
        deviceBean.setDeviceAlias(device.getDeviceName());
        deviceBean.setProductId(device.getProductId());
        DcaSdk.getDeviceManager().bindDevice(deviceBean, new Callback2(this) { // from class: com.ltech.smarthome.ui.config.ActConfigSuccessVM.4
            @Override // com.aispeech.dca.Callback2
            public void onFailure(int i, String s) {
            }

            @Override // com.aispeech.dca.Callback2
            public void onSuccess() {
            }
        });
    }
}