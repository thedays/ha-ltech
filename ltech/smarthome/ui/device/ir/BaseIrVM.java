package com.ltech.smarthome.ui.device.ir;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.Utils;
import com.hzy.tvmao.KKACZipManagerV2;
import com.hzy.tvmao.KKNonACManager;
import com.hzy.tvmao.KookongSDK;
import com.hzy.tvmao.interf.IRequestResult;
import com.kookong.app.data.IrData;
import com.kookong.app.data.IrDataList;
import com.kookong.app.data.RemoteList;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SingleLiveEvent;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.IrParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.ViewHelpUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class BaseIrVM extends BaseViewModel {
    public int areaId;
    public int brandId;
    public String brandName;
    public boolean changeIr;
    public String cmdName;
    public long controlId;
    protected IrData irData;
    public BaseCmdParam mIrCmdParam;
    private BaseCmdParam mIrParam;
    protected KKNonACManager mKKNonACManager;
    protected KKACZipManagerV2 mKKacZipManagerV2;
    protected byte[] mParams;
    protected List<Integer> rids;
    public boolean selectAction;
    public int spId;
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM.1
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            BaseIrVM.this.sendStateLiveData.setValue(true);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            BaseIrVM.this.sendStateLiveData.setValue(false);
        }
    };
    public MutableLiveData<Integer> currentRcCodePos = new MutableLiveData<>();
    protected List<IrData> irDatas = new ArrayList();
    public MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    public SingleLiveEvent<Void> showEditNameDialogEvent = new SingleLiveEvent<>();
    public MutableLiveData<Boolean> sendStateLiveData = new MutableLiveData<>(false);
    public MutableLiveData<Integer> ridLiveData = new MutableLiveData<>(-1);
    public BindingCommand<View> clickCommand = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM$$ExternalSyntheticLambda4
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            BaseIrVM.this.lambda$new$0((View) obj);
        }
    });

    protected void addKeyItem(List<IrKeyItem> keyItemList) {
    }

    public void clickAnimate(View view) {
        ViewHelpUtil.zoomInZoomOut(view, this.animationListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_next) {
            if (this.currentRcCodePos.getValue() == null || this.currentRcCodePos.getValue().intValue() >= this.rids.size() - 1) {
                return;
            }
            MutableLiveData<Integer> mutableLiveData = this.currentRcCodePos;
            mutableLiveData.setValue(Integer.valueOf(mutableLiveData.getValue().intValue() + 1));
            return;
        }
        if (id != R.id.iv_upper) {
            if (id != R.id.tv_response) {
                return;
            }
            this.showEditNameDialogEvent.call();
        } else {
            if (this.currentRcCodePos.getValue() == null || this.currentRcCodePos.getValue().intValue() <= 0) {
                return;
            }
            this.currentRcCodePos.setValue(Integer.valueOf(r2.getValue().intValue() - 1));
        }
    }

    public void getAllRemoteIds(int deviceType, final IAction<Boolean> result) {
        KookongSDK.getAllRemoteIds(deviceType, this.brandId, this.spId, this.areaId, new IRequestResult<RemoteList>() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM.2
            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onSuccess(String s, RemoteList remoteList) {
                BaseIrVM.this.rids = remoteList.rids;
                result.act(true);
            }

            @Override // com.hzy.tvmao.interf.IRequestResult
            public void onFail(Integer integer, String s) {
                result.act(false);
            }
        });
    }

    public void getRcCode(int ridPos, int deviceType, final IAction<Boolean> result) {
        if (this.rids.isEmpty()) {
            result.act(false);
        } else {
            KookongSDK.getIRDataById(String.valueOf(this.rids.get(ridPos)), deviceType, true, new IRequestResult<IrDataList>() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM.3
                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onSuccess(String s, IrDataList irDataList) {
                    BaseIrVM.this.irDatas.clear();
                    BaseIrVM.this.irDatas.addAll(irDataList.getIrDataList());
                    result.act(true);
                }

                @Override // com.hzy.tvmao.interf.IRequestResult
                public void onFail(Integer integer, String s) {
                    result.act(false);
                }
            });
        }
    }

    public void setSendState() {
        this.sendStateLiveData.setValue(true);
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseIrVM.this.lambda$setSendState$1();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSendState$1() {
        this.sendStateLiveData.setValue(false);
    }

    public void initManager(IrData irData) {
        if (irData == null) {
            this.irData = this.irDatas.get(0);
        } else {
            this.irData = irData;
        }
        if (this.ridLiveData.getValue().intValue() != this.irData.rid) {
            this.ridLiveData.setValue(Integer.valueOf(this.irData.rid));
        }
        if (ProductId.ID_IR_AC.equals(this.controlDevice.getValue().getProductId())) {
            if (!TextUtils.isEmpty(this.controlDevice.getValue().getWifiMac())) {
                KookongSDK.init(Utils.getApp(), ActSelectBrand.INIT_KEY, this.controlDevice.getValue().getWifiMac().replaceAll(Constants.COLON_SEPARATOR, "").toUpperCase());
            }
            KKACZipManagerV2 kKACZipManagerV2 = new KKACZipManagerV2();
            this.mKKacZipManagerV2 = kKACZipManagerV2;
            kKACZipManagerV2.initIRData(this.irData);
            this.mParams = this.mKKacZipManagerV2.getAcParams();
            sendRcParams();
            return;
        }
        KKNonACManager kKNonACManager = new KKNonACManager(this.irData);
        this.mKKNonACManager = kKNonACManager;
        this.mParams = kKNonACManager.getParams();
        sendRcParams();
    }

    protected void sendRcParams() {
        this.mIrParam = CmdAssistant.getGatewayAssistant(this.controlDevice.getValue(), new int[0]).sendIrParam(ActivityUtils.getTopActivity(), this.mParams, !this.selectAction);
    }

    public List<IrKeyItem> getKeyItemList() {
        ArrayList arrayList = new ArrayList();
        addKeyItem(arrayList);
        setKeyEnable(arrayList);
        return arrayList;
    }

    protected void setKeyEnable(List<IrKeyItem> keyItemList) {
        for (IrKeyItem irKeyItem : keyItemList) {
            if (irKeyItem.getFid() < 0) {
                irKeyItem.setEnable(true);
            } else {
                Iterator<IrData.IrKey> it = this.irData.keys.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (irKeyItem.getFid() == it.next().fid) {
                            irKeyItem.setEnable(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void sendIrControl(int fid) {
        KKNonACManager kKNonACManager = this.mKKNonACManager;
        if (kKNonACManager != null && fid >= 0) {
            Iterator<IrData.IrKey> it = kKNonACManager.getAllKeys().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                IrData.IrKey next = it.next();
                if (next.fid == fid) {
                    this.cmdName = next.fname;
                    break;
                }
            }
            byte[] keyIr = this.mKKNonACManager.getKeyIr(fid);
            sendRcParams();
            showLoadingDialog();
            GatewayAssistant gatewayAssistant = CmdAssistant.getGatewayAssistant(this.controlDevice.getValue(), new int[0]);
            Activity topActivity = ActivityUtils.getTopActivity();
            byte[] bArr = this.mParams;
            if (keyIr == null) {
                keyIr = new byte[0];
            }
            this.mIrCmdParam = gatewayAssistant.sendIrComboControl(topActivity, bArr, keyIr, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM.4
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    BaseIrVM.this.dismissLoadingDialog();
                    if (aBoolean.booleanValue()) {
                        return;
                    }
                    BaseIrVM.this.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                }
            }, !this.selectAction);
        }
    }

    public void addIrDevice(String deviceName) {
        IrParam irParam = new IrParam();
        irParam.setBrandId(this.brandId);
        irParam.setIrDatas(this.irDatas);
        irParam.setUnicastAddress(ConfigHelper.instance().unicastAddress);
        ConfigHelper.instance().subProductName = this.brandName + ConfigHelper.instance().productInfo.getDefaultName(ActivityUtils.getTopActivity()) + this.currentRcCodePos.getValue();
        ConfigHelper.instance().deviceName = deviceName;
        ConfigHelper.instance().subManufacturerName = this.brandName;
        ConfigHelper.instance().param = irParam;
        CtrlPackage subDeviceCtrlPackage = CtrlPackager.getSubDeviceCtrlPackage(this.controlDevice.getValue());
        ConfigHelper.instance().macCode = SceneHelper.createCmdData(Injection.strategy().getCmdConvertStrategy(subDeviceCtrlPackage.getAgreementId()).convert2cmd(this.mIrParam).valueString(new Object[0]));
        if (irParam.getUnicastAddress() != 0) {
            CtrlPackage bleDeviceCtrlPackage = CtrlPackager.getBleDeviceCtrlPackage(this.controlDevice.getValue());
            bleDeviceCtrlPackage.setAgreementId(2);
            ConfigHelper.instance().macCode = SceneHelper.createCmdData(StringUtils.byte2Str(Injection.iot().connectAndSendData3(bleDeviceCtrlPackage.getAddress(), Injection.strategy().getCmdConvertStrategy(3).convert2cmd(Injection.strategy().getCmdConvertStrategy(2).convert2cmd(CmdAssistant.getGatewayAssistant(this.controlDevice.getValue(), new int[0]).sendIrParam(ActivityUtils.getTopActivity(), this.mParams, false)), Integer.valueOf(bleDeviceCtrlPackage.getAddress())).value(new Object[0]))));
            ConfigHelper.instance().codeLibrary = ConfigHelper.instance().getIrCodeLibrary(ConfigHelper.instance().productInfo.getProductId(), 2, this.irData, bleDeviceCtrlPackage.getAddress());
        } else {
            ConfigHelper.instance().codeLibrary = ConfigHelper.instance().getIrCodeLibrary(ConfigHelper.instance().productInfo.getProductId(), subDeviceCtrlPackage.getAgreementId(), this.irData);
        }
        ((ObservableSubscribeProxy) Injection.net().addDevice(ConfigHelper.instance().irProductData()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseIrVM.this.lambda$addIrDevice$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new BaseIrVM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseIrVM.this.lambda$addIrDevice$3((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addIrDevice$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addIrDevice$3(AddDeviceResponse addDeviceResponse) throws Exception {
        ConfigHelper.instance().addDevice(addDeviceResponse, ConfigHelper.instance().productInfo.getProductId());
        navigation(NavUtils.destination(ActHome.class));
    }

    public void changeIr() {
        final IrParam irParam = (IrParam) this.controlDevice.getValue().getParam(IrParam.class);
        irParam.setBrandId(this.brandId);
        irParam.setIrDatas(this.irDatas);
        ((ObservableSubscribeProxy) Injection.net().updateParam(this.controlDevice.getValue().getDeviceId(), GsonUtils.toJson(irParam)).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseIrVM.this.lambda$changeIr$4((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new BaseIrVM$$ExternalSyntheticLambda2(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.BaseIrVM$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BaseIrVM.this.lambda$changeIr$5(irParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeIr$4(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.saving));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$changeIr$5(IrParam irParam, Object obj) throws Exception {
        this.controlDevice.getValue().setParam(irParam);
        Injection.repo().device().saveDevice(this.controlDevice.getValue());
        SmartToast.showShort(R.string.save_success);
        finishActivity();
    }
}