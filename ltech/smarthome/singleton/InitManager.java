package com.ltech.smarthome.singleton;

import android.content.Context;
import android.util.ArrayMap;
import android.widget.ImageView;
import com.aispeech.dca.DcaSdk;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.bumptech.glide.Glide;
import com.hzy.tvmao.KookongSDK;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.blemesh.ISendCallback;
import com.ltech.smarthome.crash.CrashManager;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BaseIrParam;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.api.ApiConstants;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.dialog.util.DialogSettings;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.SmartUtils;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.ICtrlConverter;
import com.smart.message.base.IHandleReportMessage;
import com.smart.message.base.ISendResutCallback;
import com.smart.message.base.IStateConverter;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.LogCatHelper;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.AgreementUtils;
import com.smart.product_agreement.extra.CmdConstants;
import com.smart.product_agreement.extra.Emitter;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

/* loaded from: classes4.dex */
public class InitManager {

    public static class CrashReportTree extends Timber.Tree {
        @Override // timber.log.Timber.Tree
        protected void log(int priority, String tag, String message, Throwable t) {
        }
    }

    private void initMap(Context context) {
    }

    private InitManager() {
    }

    public static InitManager getInstance() {
        return (InitManager) Singleton.getSingleton(InitManager.class);
    }

    public void init(Context context, Context applicationContext) {
        Injection.push().init(applicationContext);
        Timber.plant(new CrashReportTree());
        CrashManager.getInstance(context);
        PathManager.createDir(applicationContext);
        SharedPreferenceUtil.init(context);
        Injection.init(context);
        Gloading.initDefault(new DefaultAdapter());
        RxJavaPlugins.setErrorHandler(new Consumer() { // from class: com.ltech.smarthome.singleton.InitManager$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ((Throwable) obj).printStackTrace();
            }
        });
        setStrategy();
        setDialogStyle(context);
        ISNav.getInstance().init(new ImageLoader() { // from class: com.ltech.smarthome.singleton.InitManager.1
            @Override // com.yuyh.library.imgsel.common.ImageLoader
            public void displayImage(Context context2, String path, ImageView imageView) {
                Glide.with(context2).load(path).into(imageView);
            }
        });
    }

    public void delayInit(Context context, Context applicationContext) {
        Injection.state().registerNetworkReceiver(context);
        initMap(applicationContext);
        KookongSDK.setDebugMode(false);
        DcaSdk.initialize(context, ApiConstants.getDcaApiKey(), ApiConstants.getDcaApiSecret());
    }

    private void initLogcatHelper(Context applicationContext) {
        LogCatHelper.getInstance(applicationContext).start();
    }

    private void setDialogStyle(Context context) {
        DialogSettings.init();
        DialogSettings.isUseBlur = false;
        DialogSettings.autoShowInputKeyboard = true;
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        DialogSettings.theme = DialogSettings.THEME.LIGHT;
        DialogSettings.tipTheme = DialogSettings.THEME.LIGHT;
    }

    private void setStrategy() {
        SmartUtils.set().withCtrlConverter(new ICtrlConverter(this) { // from class: com.ltech.smarthome.singleton.InitManager.5
            @Override // com.smart.message.base.ICtrlConverter
            public void unableHandleAction(BaseCtrlPackage ctrlPacket) {
            }

            @Override // com.smart.message.base.ICtrlConverter
            public BaseCtrlPackage convert(Object o) {
                if (o instanceof Device) {
                    Device device = (Device) o;
                    if (ProductRepository.isBLeDevice(device.getProductId())) {
                        return CtrlPackager.getBleDeviceCtrlPackage(device);
                    }
                    if (ProductRepository.isWifiBleDevice(device.getProductId())) {
                        return CtrlPackager.getWifiBleDeviceCtrlPackage(device);
                    }
                    if (2 == device.getMacfalg()) {
                        if (device.getParam() != null && ((BaseIrParam) device.getParam(BaseIrParam.class)).getUnicastAddress() != 0) {
                            CtrlPackage bleDeviceCtrlPackage = CtrlPackager.getBleDeviceCtrlPackage(device);
                            bleDeviceCtrlPackage.setAgreementId(2);
                            return bleDeviceCtrlPackage;
                        }
                        return CtrlPackager.getSubDeviceCtrlPackage(device);
                    }
                    return CtrlPackager.getWifiDeviceCtrlPackage(device);
                }
                if (!(o instanceof Group)) {
                    return null;
                }
                Group group = (Group) o;
                if (ProductRepository.isBleGroup(group.getModuleType())) {
                    return CtrlPackager.getBleGroupCtrlPackage(group);
                }
                return CtrlPackager.getWifiGroupCtrlPackage(group);
            }
        }).withHandleReportMessage(new IHandleReportMessage() { // from class: com.ltech.smarthome.singleton.InitManager$$ExternalSyntheticLambda0
            @Override // com.smart.message.base.IHandleReportMessage
            public final void handleReportMessage(ResponseMsg responseMsg) {
                MessageManager.getInstance().handleReportData(responseMsg);
            }
        }).addEmitter(Emitter.IOT, new SmartUtils.IDataSend() { // from class: com.ltech.smarthome.singleton.InitManager$$ExternalSyntheticLambda1
            @Override // com.smart.message.SmartUtils.IDataSend
            public final void send(BaseCtrlPackage baseCtrlPackage, BaseCmd baseCmd, ISendResutCallback iSendResutCallback) {
                InitManager.this.lambda$setStrategy$1((CtrlPackage) baseCtrlPackage, baseCmd, iSendResutCallback);
            }
        }).addEmitter(Emitter.BLUETOOTH, new SmartUtils.IDataSend() { // from class: com.ltech.smarthome.singleton.InitManager$$ExternalSyntheticLambda2
            @Override // com.smart.message.SmartUtils.IDataSend
            public final void send(BaseCtrlPackage baseCtrlPackage, BaseCmd baseCmd, ISendResutCallback iSendResutCallback) {
                InitManager.this.lambda$setStrategy$2((CtrlPackage) baseCtrlPackage, baseCmd, iSendResutCallback);
            }
        }).addEmitter(Emitter.MIX_BLE_IOT, new SmartUtils.IDataSend() { // from class: com.ltech.smarthome.singleton.InitManager$$ExternalSyntheticLambda3
            @Override // com.smart.message.SmartUtils.IDataSend
            public final void send(BaseCtrlPackage baseCtrlPackage, BaseCmd baseCmd, ISendResutCallback iSendResutCallback) {
                InitManager.this.lambda$setStrategy$3((CtrlPackage) baseCtrlPackage, baseCmd, iSendResutCallback);
            }
        }).init();
        AgreementUtils.init(ProductRepository.AGREEMENT_SET);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStrategy$1(final CtrlPackage ctrlPackage, BaseCmd baseCmd, final ISendResutCallback iSendResutCallback) {
        if (baseCmd.getExtDatas() == null) {
            Injection.iot().connectAndSendData2(ctrlPackage.getDeviceIds(), baseCmd.value(new Object[0]), new IConnectSendListener(this) { // from class: com.ltech.smarthome.singleton.InitManager.3
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) {
                    iSendResutCallback.onResultSuccess(true);
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) {
                    iSendResutCallback.onResultError();
                }
            });
            return;
        }
        if (baseCmd.getExtDatas().get(CmdConstants.DST_ADDRESS) != null) {
            LHomeLog.i(getClass(), "Emitter.IOT  ctrlPacket.getAddress()" + ctrlPackage.getAddress() + "   真实add = " + baseCmd.getExtDatas().get(CmdConstants.DST_ADDRESS));
        }
        if (baseCmd.getFunCode() == 196 || baseCmd.getFunCode() == 195) {
            baseCmd.getExtDatas().put(CmdConstants.DST_ADDRESS, Integer.valueOf(ctrlPackage.getUnicastAddress()));
        }
        LHomeLog.i(getClass(), "cmd.value() = " + StringUtils.btye2Str2(baseCmd.value(new Object[0])));
        LHomeLog.e(getClass(), String.format("ALI==>Request:\nfuncode=%s\ncmd=%s\next=%s", StringUtils.demToHex(baseCmd.getFunCode()), StringUtils.btye2Str2(baseCmd.value(new Object[0])), baseCmd.getExtDatas(), true));
        Injection.iot().connectAndSendData2(ctrlPackage.getDeviceIds(), ((Integer) baseCmd.getExtDatas().get(CmdConstants.DST_ADDRESS)).intValue(), baseCmd.value(new Object[0]), new IConnectSendListener(this) { // from class: com.ltech.smarthome.singleton.InitManager.4
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onResponse(ARequest aRequest, AResponse aResponse) {
                if (aResponse.getData() != null) {
                    LHomeLog.e(getClass(), String.format("BLE==>Response:%s", aResponse.getData().toString()));
                }
                LHomeLog.i(getClass(), "阿里发送数据发送成功  ctrlPacket.getAddress()" + ctrlPackage.getAddress());
                ctrlPackage.getAddress();
                iSendResutCallback.onResultSuccess(true);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
            public void onFailure(ARequest aRequest, AError aError) {
                LHomeLog.i(getClass(), "阿里发送数据发送失败");
                iSendResutCallback.onResultError();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStrategy$2(CtrlPackage ctrlPackage, BaseCmd baseCmd, final ISendResutCallback iSendResutCallback) {
        byte[] value = baseCmd.value(Integer.valueOf(ctrlPackage.getAddress()));
        if (baseCmd.getExtDatas() == null) {
            ArrayMap<String, Object> arrayMap = new ArrayMap<>();
            arrayMap.put(CmdConstants.DST_ADDRESS, Integer.valueOf(ctrlPackage.getAddress()));
            baseCmd.setExtDatas(arrayMap);
        } else if (baseCmd.getExtDatas().get(CmdConstants.DST_ADDRESS) == null) {
            baseCmd.getExtDatas().put(CmdConstants.DST_ADDRESS, Integer.valueOf(ctrlPackage.getAddress()));
        } else if ((baseCmd.getFunCode() == 196 || baseCmd.getFunCode() == 195) && ctrlPackage.getUnicastAddress() > 0) {
            baseCmd.getExtDatas().put(CmdConstants.DST_ADDRESS, Integer.valueOf(ctrlPackage.getUnicastAddress()));
        }
        Injection.mesh().sendVendorModelMessage(((Integer) baseCmd.getExtDatas().get(CmdConstants.DST_ADDRESS)).intValue(), baseCmd.getFunCode(), value, false, new ISendCallback(this) { // from class: com.ltech.smarthome.singleton.InitManager.2
            @Override // com.ltech.smarthome.blemesh.ISendCallback
            public void onSendSuccess() {
                iSendResutCallback.onResultSuccess(false);
            }

            @Override // com.ltech.smarthome.blemesh.ISendCallback
            public void onSendFail() {
                iSendResutCallback.onResultError();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStrategy$3(CtrlPackage ctrlPackage, BaseCmd baseCmd, ISendResutCallback iSendResutCallback) {
        int funCode = baseCmd.getFunCode();
        if (!Injection.mesh().isSeqSuccess() || !Injection.state().isBluetoothEnabled()) {
            LHomeLog.i(getClass(), "cmd.value()  MIX_BLE_IOT = blue is disable");
            gotoEmitterIot(ctrlPackage, baseCmd, iSendResutCallback);
            return;
        }
        LHomeLog.i(getClass(), "blue is open is useBle=" + Injection.mesh().useBle() + "__funCode=" + funCode);
        if (Injection.mesh().useBle()) {
            SmartUtils.send(Emitter.BLUETOOTH, ctrlPackage, baseCmd, iSendResutCallback);
        } else {
            gotoEmitterIot(ctrlPackage, baseCmd, iSendResutCallback);
        }
    }

    private void gotoEmitterIot(CtrlPackage ctrlPacket, BaseCmd cmd, ISendResutCallback callback) {
        Device onlineGateway = Injection.repo().device().getOnlineGateway(ctrlPacket.getAddress());
        LHomeLog.i(getClass(), "__ctrlPacket=" + ctrlPacket.toString() + "__cmd=" + cmd.toString() + "device=" + onlineGateway);
        if (onlineGateway != null && onlineGateway.getParam() != null && onlineGateway.getParam(BleParam.class) != null) {
            try {
                LHomeLog.i(getClass(), "cmd.value()  MIX_BLE_IOT = " + StringUtils.btye2Str2(cmd.value(new Object[0])));
                BaseCtrlPackage convert = SmartUtils.getICtrlConverter().convert(onlineGateway);
                IStateConverter cmdConvertStrategy = Injection.strategy().getCmdConvertStrategy(convert.getAgreementId());
                if (cmdConvertStrategy == null) {
                    LHomeLog.e(getClass(), "MIX_BLE_IOT error= iStateConverter==null");
                    callback.onResultError();
                    return;
                }
                BaseCmd convert2cmd = cmdConvertStrategy.convert2cmd(cmd, Integer.valueOf(ctrlPacket.getAddress()));
                ArrayMap<String, Object> arrayMap = new ArrayMap<>();
                arrayMap.put(CmdConstants.DST_ADDRESS, Integer.valueOf(ctrlPacket.getAddress()));
                convert2cmd.setExtDatas(arrayMap);
                SmartUtils.send(Emitter.IOT, convert, convert2cmd, callback);
                return;
            } catch (Exception e) {
                LHomeLog.e(getClass(), "MIX_BLE_IOT error= " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
        if (Injection.mesh().useBle() && Injection.state().isBluetoothEnabled()) {
            SmartUtils.send(Emitter.BLUETOOTH, ctrlPacket, cmd, callback);
        } else {
            LHomeLog.i(getClass(), "网关设备不在线，发送云查询失败");
            callback.onResultError();
        }
    }
}