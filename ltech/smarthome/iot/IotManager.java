package com.ltech.smarthome.iot;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.dm.api.IoTApiClientConfig;
import com.aliyun.alink.linkkit.api.ILinkKitConnectListener;
import com.aliyun.alink.linkkit.api.IoTDMConfig;
import com.aliyun.alink.linkkit.api.IoTH2Config;
import com.aliyun.alink.linkkit.api.IoTMqttClientConfig;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linkkit.api.LinkKitInitParams;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.log.IDGenerater;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.Utils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.login.ActSelectCountry;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.smart.message.RecPackage;
import com.smart.message.base.BaseCmd;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.annotation.Nonnull;
import timber.log.Timber;

/* loaded from: classes3.dex */
public class IotManager {
    private String[] controlIds;
    private long[] deviceIds;
    private boolean isConnecting;
    private boolean isInit;
    private IConnectNotifyListener mNotifyListener = new IConnectNotifyListener(this) { // from class: com.ltech.smarthome.iot.IotManager.5
        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
        public boolean shouldHandle(String s, String s1) {
            return true;
        }

        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
        public void onNotify(String s, String s1, AMessage aMessage) {
            String valueOf;
            if (aMessage != null) {
                try {
                    if (aMessage.data instanceof byte[]) {
                        valueOf = new String((byte[]) aMessage.data, StandardCharsets.UTF_8);
                        RecDataBean recDataBean = (RecDataBean) GsonUtils.getGson().fromJson(String.format("{%s}", valueOf), RecDataBean.class);
                        Device deviceFromDb = Injection.repo().device().getDeviceFromDb(recDataBean.getProductkey(), recDataBean.getDevicename());
                        if (deviceFromDb == null) {
                            return;
                        }
                        RecPackage recPackage = new RecPackage();
                        recPackage.setAgreementId(ProductRepository.getAgreementIdByPid(deviceFromDb.getProductId()));
                        recPackage.setRecData(recDataBean.getPayload());
                        recPackage.setDeviceFlag(recDataBean.getDevicename());
                        recPackage.setRecSource(1);
                        if (AppUtils.isAppDebug()) {
                            if (Injection.mesh().isSeqSuccess() && recPackage.getAgreementId() != 1) {
                                LHomeLog.i(Constants.MESH_LOG, getClass(), "mesh网络畅通，不处理IOT状态");
                            } else {
                                Injection.message().handleMessage(recPackage);
                            }
                        } else {
                            Injection.message().handleMessage(recPackage);
                        }
                    } else {
                        valueOf = aMessage.data instanceof String ? (String) aMessage.data : String.valueOf(aMessage.data);
                    }
                    Timber.i("==iot rec data==" + valueOf, new Object[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
        public void onConnectStateChange(String s, ConnectState connectState) {
            Timber.d(connectState + "=======onConnectStateChange========" + s, new Object[0]);
        }
    };

    private IotManager() {
    }

    public void init() {
        if (this.isInit) {
            return;
        }
        init(Utils.getApp(), Injection.repo().user().getProductKey(), Injection.repo().user().getDeviceName(), Injection.repo().user().getDeviceSecret(), "", new IConnectCallback() { // from class: com.ltech.smarthome.iot.IotManager.1
            @Override // com.aliyun.alink.linkkit.api.ILinkKitConnectListener
            public void onError(AError aError) {
                IotManager.this.isInit = false;
            }

            @Override // com.aliyun.alink.linkkit.api.ILinkKitConnectListener
            public void onInitDone(Object o) {
                IotManager.this.isInit = true;
                IotManager.this.registerNotifyListener();
            }
        });
    }

    private void init(Context context, String productKey, String deviceName, String deviceSecret, String productSecret, final IConnectCallback callback) {
        Timber.d("=====iot init=======", new Object[0]);
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.productKey = productKey;
        deviceInfo.deviceName = deviceName;
        deviceInfo.deviceSecret = deviceSecret;
        deviceInfo.productSecret = productSecret;
        IoTApiClientConfig ioTApiClientConfig = new IoTApiClientConfig();
        HashMap hashMap = new HashMap(4);
        LinkKitInitParams linkKitInitParams = new LinkKitInitParams();
        linkKitInitParams.deviceInfo = deviceInfo;
        linkKitInitParams.propertyValues = hashMap;
        linkKitInitParams.connectConfig = ioTApiClientConfig;
        IoTH2Config ioTH2Config = new IoTH2Config();
        ioTH2Config.clientId = "client-id";
        ioTH2Config.endPoint = "https://" + productKey + ioTH2Config.endPoint;
        linkKitInitParams.iotH2InitParams = ioTH2Config;
        IoTMqttClientConfig ioTMqttClientConfig = new IoTMqttClientConfig(productKey, deviceName, deviceSecret);
        String queryValue = SharedPreferenceUtil.queryValue(Constants.IOT_INSTANCE);
        if (TextUtils.isEmpty(queryValue)) {
            String queryValue2 = SharedPreferenceUtil.queryValue(ActSelectCountry.SELECT_COUNTRY_EN_KEY);
            if (!TextUtils.isEmpty(queryValue2) && !"China".equals(queryValue2)) {
                ioTMqttClientConfig.channelHost = "iot-600a65gi.mqtt.iothub.aliyuncs.com:1883";
            } else {
                ioTMqttClientConfig.channelHost = "iot-060a5shm.mqtt.iothub.aliyuncs.com:1883";
            }
        } else {
            ioTMqttClientConfig.channelHost = queryValue + com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR + SharedPreferenceUtil.queryIntValue(Constants.IOT_PORT);
        }
        linkKitInitParams.mqttClientConfig = ioTMqttClientConfig;
        IoTDMConfig ioTDMConfig = new IoTDMConfig();
        ioTDMConfig.enableNotify = true;
        linkKitInitParams.ioTDMConfig = ioTDMConfig;
        LinkKit.getInstance().init(context, linkKitInitParams, new ILinkKitConnectListener(this) { // from class: com.ltech.smarthome.iot.IotManager.2
            @Override // com.aliyun.alink.linkkit.api.ILinkKitConnectListener
            public void onError(AError error) {
                Timber.d("==========onInitError=======", new Object[0]);
                IConnectCallback iConnectCallback = callback;
                if (iConnectCallback != null) {
                    iConnectCallback.onError(error);
                }
            }

            @Override // com.aliyun.alink.linkkit.api.ILinkKitConnectListener
            public void onInitDone(Object data) {
                Timber.d("==========onInitDone=======", new Object[0]);
                IConnectCallback iConnectCallback = callback;
                if (iConnectCallback != null) {
                    iConnectCallback.onInitDone(data);
                }
            }
        });
    }

    public void deInit() {
        Timber.d("======iot deInit=====", new Object[0]);
        unregisterNotifyListener();
        LinkKit.getInstance().deinit();
        this.isInit = false;
    }

    private void subscribe() {
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.isSubscribe = true;
        mqttSubscribeRequest.topic = "/" + Injection.repo().user().getProductKey() + "/" + Injection.repo().user().getDeviceName() + "/user/get";
        LinkKit.getInstance().subscribe(mqttSubscribeRequest, new IConnectSubscribeListener(this) { // from class: com.ltech.smarthome.iot.IotManager.3
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
            public void onSuccess() {
                Timber.d("======subscribe success=====", new Object[0]);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
            public void onFailure(AError aError) {
                Timber.d("======subscribe fail=====", new Object[0]);
            }
        });
    }

    private void unSubscribe() {
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.isSubscribe = false;
        mqttSubscribeRequest.topic = "/" + Injection.repo().user().getProductKey() + "/" + Injection.repo().user().getDeviceName() + "/user/get";
        LinkKit.getInstance().unsubscribe(mqttSubscribeRequest, new IConnectUnscribeListener(this) { // from class: com.ltech.smarthome.iot.IotManager.4
            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
            public void onSuccess() {
                Timber.d("======unSubscribe success=====", new Object[0]);
            }

            @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
            public void onFailure(AError aError) {
                Timber.d("======unSubscribe fail=====", new Object[0]);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerNotifyListener() {
        subscribe();
        LinkKit.getInstance().registerOnPushListener(this.mNotifyListener);
    }

    private void unregisterNotifyListener() {
        unSubscribe();
        LinkKit.getInstance().unRegisterOnPushListener(this.mNotifyListener);
    }

    public void connectAndSendData(final String[] platFormDeviceIds, final byte[] data, @Nonnull final IConnectSendListener listener) {
        if (!this.isInit) {
            init();
            listener.onFailure(null, null);
        } else if (this.isConnecting) {
            listener.onFailure(null, null);
        } else if (Arrays.equals(this.controlIds, platFormDeviceIds)) {
            sendData(data, listener);
        } else {
            requestDeviceControl(platFormDeviceIds, new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IotManager.this.lambda$connectAndSendData$0(platFormDeviceIds, data, listener, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IotManager.this.lambda$connectAndSendData$1(listener, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectAndSendData$0(String[] strArr, byte[] bArr, IConnectSendListener iConnectSendListener, Object obj) throws Exception {
        this.isConnecting = false;
        this.controlIds = strArr;
        sendData(bArr, iConnectSendListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectAndSendData$1(IConnectSendListener iConnectSendListener, Throwable th) throws Exception {
        this.isConnecting = false;
        this.controlIds = null;
        iConnectSendListener.onFailure(null, null);
    }

    public void connectAndSendData(final long[] deviceIds, final byte[] data, @Nonnull final IConnectSendListener listener) {
        if (!this.isInit) {
            init();
            listener.onFailure(null, null);
        } else if (this.isConnecting) {
            listener.onFailure(null, null);
        } else if (Arrays.equals(this.deviceIds, deviceIds)) {
            sendData(data, listener);
        } else {
            requestDeviceControl(deviceIds, new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IotManager.this.lambda$connectAndSendData$2(deviceIds, data, listener, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IotManager.this.lambda$connectAndSendData$3(listener, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectAndSendData$2(long[] jArr, byte[] bArr, IConnectSendListener iConnectSendListener, Object obj) throws Exception {
        this.isConnecting = false;
        this.deviceIds = jArr;
        sendData(bArr, iConnectSendListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectAndSendData$3(IConnectSendListener iConnectSendListener, Throwable th) throws Exception {
        this.isConnecting = false;
        this.deviceIds = null;
        iConnectSendListener.onFailure(null, null);
    }

    public void connectAndSendData2(final long[] deviceIds, final int address, final byte[] data, @Nonnull final IConnectSendListener listener) {
        if (!this.isInit) {
            init();
            listener.onFailure(null, null);
        } else if (this.isConnecting) {
            listener.onFailure(null, null);
        } else if (Arrays.equals(this.deviceIds, deviceIds)) {
            sendData(postscape(data, hexStringToByte(String.format("%04x", Integer.valueOf(address)))), listener);
        } else {
            requestDeviceControl(deviceIds, new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IotManager.this.lambda$connectAndSendData2$4(deviceIds, address, data, listener, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IotManager.this.lambda$connectAndSendData2$5(listener, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectAndSendData2$4(long[] jArr, int i, byte[] bArr, IConnectSendListener iConnectSendListener, Object obj) throws Exception {
        this.isConnecting = false;
        this.deviceIds = jArr;
        sendData(postscape(bArr, hexStringToByte(String.format("%04x", Integer.valueOf(i)))), iConnectSendListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectAndSendData2$5(IConnectSendListener iConnectSendListener, Throwable th) throws Exception {
        this.isConnecting = false;
        this.deviceIds = null;
        iConnectSendListener.onFailure(null, null);
    }

    public void connectAndSendData2(final long[] deviceIds, final byte[] data, @Nonnull final IConnectSendListener listener) {
        if (!this.isInit) {
            init();
            listener.onFailure(null, null);
        } else if (this.isConnecting) {
            listener.onFailure(null, null);
        } else if (Arrays.equals(this.deviceIds, deviceIds)) {
            sendData(data, listener);
        } else {
            requestDeviceControl(deviceIds, new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IotManager.this.lambda$connectAndSendData2$6(deviceIds, data, listener, obj);
                }
            }, new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    IotManager.this.lambda$connectAndSendData2$7(listener, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectAndSendData2$6(long[] jArr, byte[] bArr, IConnectSendListener iConnectSendListener, Object obj) throws Exception {
        this.isConnecting = false;
        this.deviceIds = jArr;
        sendData(bArr, iConnectSendListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectAndSendData2$7(IConnectSendListener iConnectSendListener, Throwable th) throws Exception {
        this.isConnecting = false;
        this.deviceIds = null;
        iConnectSendListener.onFailure(null, null);
    }

    public byte[] connectAndSendData3(int address, byte[] data) {
        return postscape(data, hexStringToByte(String.format("%04x", Integer.valueOf(address))));
    }

    public byte[] connectAndSendData4(int address, byte[] data) {
        return postscapeNotRandomCode(data, hexStringToByte(String.format("%04x", Integer.valueOf(address))));
    }

    private void requestDeviceControl(String[] platFormDeviceIds, Consumer<Object> successConsumer, Consumer<Throwable> errorConsumer) {
        Injection.net().requestDeviceControl(platFormDeviceIds).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                IotManager.this.lambda$requestDeviceControl$8((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).subscribe(successConsumer, errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDeviceControl$8(Disposable disposable) throws Exception {
        this.isConnecting = true;
    }

    private void requestDeviceControl(long[] deviceIds, Consumer<Object> successConsumer, Consumer<Throwable> errorConsumer) {
        Injection.net().requestDeviceControl(deviceIds).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.iot.IotManager$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                IotManager.this.lambda$requestDeviceControl$9((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).subscribe(successConsumer, errorConsumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDeviceControl$9(Disposable disposable) throws Exception {
        this.isConnecting = true;
    }

    private void sendData(byte[] data, IConnectSendListener listener) {
        MqttPublishRequest mqttPublishRequest = new MqttPublishRequest();
        mqttPublishRequest.qos = 0;
        mqttPublishRequest.isRPC = false;
        mqttPublishRequest.topic = "/" + Injection.repo().user().getProductKey() + "/" + Injection.repo().user().getDeviceName() + "/user/update";
        mqttPublishRequest.payloadObj = data;
        mqttPublishRequest.msgId = String.valueOf(IDGenerater.generateId());
        LHomeLog.i(getClass(), "阿里发送数据" + StringUtils.btye2Str2(data) + "   " + mqttPublishRequest.toString());
        LinkKit.getInstance().publish(mqttPublishRequest, listener);
    }

    private byte[] postscape(byte[] data, byte[] add) {
        if (data != null && data.length >= 2) {
            ArrayList arrayList = new ArrayList();
            int length = data.length - 8;
            LHomeLog.i(getClass(), "postscape:cmdDataLength-> " + length);
            int[] iArr = new int[2];
            if (length > 0) {
                iArr = BaseCmd.toHex(length);
            }
            for (int i = 0; i < data.length; i++) {
                if (i == 7) {
                    arrayList.add(Byte.valueOf((byte) iArr[0]));
                } else if (i == 8) {
                    arrayList.add(Byte.valueOf((byte) iArr[1]));
                    if (add != null) {
                        for (byte b2 : add) {
                            arrayList.add(Byte.valueOf(b2));
                        }
                    }
                } else {
                    arrayList.add(Byte.valueOf(data[i]));
                }
            }
            data = new byte[arrayList.size()];
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                byte byteValue = ((Byte) arrayList.get(i2)).byteValue();
                data[i2] = byteValue;
                sb.append(String.format("%02x", Byte.valueOf(byteValue)));
            }
            LHomeLog.i(getClass(), "postscape: cmd hex string->" + sb.toString());
        }
        return data;
    }

    private byte[] postscapeNotRandomCode(byte[] data, byte[] add) {
        if (data != null && data.length >= 2) {
            ArrayList arrayList = new ArrayList();
            int length = data.length - 8;
            LHomeLog.i(getClass(), "postscape:cmdDataLength-> " + length);
            int[] iArr = new int[2];
            if (length > 0) {
                iArr = BaseCmd.toHex(length);
            }
            for (int i = 0; i < data.length; i++) {
                if (i == 7) {
                    arrayList.add(Byte.valueOf((byte) iArr[0]));
                } else if (i == 8) {
                    arrayList.add(Byte.valueOf((byte) iArr[1]));
                    if (add != null) {
                        for (byte b2 : add) {
                            arrayList.add(Byte.valueOf(b2));
                        }
                    }
                } else {
                    arrayList.add(Byte.valueOf(data[i]));
                }
            }
            data = new byte[arrayList.size()];
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (i2 == 15 || i2 == 16) {
                    data[i2] = 0;
                } else {
                    data[i2] = ((Byte) arrayList.get(i2)).byteValue();
                }
                sb.append(String.format("%02x", Byte.valueOf(data[i2])));
            }
            LHomeLog.i(getClass(), "postscape: cmd hex string->" + sb.toString());
        }
        return data;
    }

    private byte[] insertBleAddressToSendData(byte[] data, byte[] add) {
        if (data != null && data.length >= 2) {
            ArrayList arrayList = new ArrayList();
            int length = (data.length - 10) + add.length;
            LHomeLog.i(getClass(), "postscape:cmdDataLength-> " + length);
            int i = 0;
            while (i < data.length) {
                if (i == 7) {
                    if (length > 255) {
                        arrayList.add(Byte.valueOf((byte) (length / 256)));
                        arrayList.add(Byte.valueOf((byte) (length % 256)));
                    } else {
                        arrayList.add((byte) 0);
                        arrayList.add(Byte.valueOf((byte) length));
                    }
                    arrayList.add(Byte.valueOf(add[0]));
                    LHomeLog.i(getClass(), ((int) add[0]) + "  " + ((int) add[1]));
                    arrayList.add(Byte.valueOf(add[1]));
                    i++;
                } else {
                    arrayList.add(Byte.valueOf(data[i]));
                }
                i++;
            }
            data = new byte[arrayList.size()];
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                byte byteValue = ((Byte) arrayList.get(i2)).byteValue();
                data[i2] = byteValue;
                sb.append(String.format("%02x", Byte.valueOf(byteValue)));
            }
            LHomeLog.i(getClass(), "insertBleAddressToSendData: cmd hex string->" + sb.toString());
        }
        return data;
    }

    public static byte[] hexStringToByte(String hex) {
        int length = hex.length() / 2;
        byte[] bArr = new byte[length];
        char[] charArray = hex.toCharArray();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (toByte(charArray[i2 + 1]) | (toByte(charArray[i2]) << 4));
        }
        return bArr;
    }

    private static byte toByte(char c2) {
        return (byte) "0123456789abcdef".indexOf(c2);
    }
}