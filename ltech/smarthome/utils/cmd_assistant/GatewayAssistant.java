package com.ltech.smarthome.utils.cmd_assistant;

import android.content.Context;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BaseIrParam;
import com.smart.message.DataPackage;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.base.IReceiveListener;
import com.smart.product_agreement.param.MeshGatewayParam;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class GatewayAssistant extends BaseAssistant {
    public MeshGatewayParam sendIrParam(Context context, byte[] irParam, boolean... send) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(2);
        meshGatewayParam.setCmdData(irParam);
        if (send.length > 0 && !send[0]) {
            meshGatewayParam.setScene(true);
            return meshGatewayParam;
        }
        meshGatewayParam.setScene(false);
        Injection.message().create(context).cmdParam(meshGatewayParam).control(this.controlObject).sendTimes(1).enqueue();
        return meshGatewayParam;
    }

    public MeshGatewayParam sendIrControl(Context context, byte[] irData, boolean... send) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(1);
        meshGatewayParam.setCmdData(irData);
        if (send.length > 0 && !send[0]) {
            return meshGatewayParam;
        }
        Injection.message().create(context).cmdParam(meshGatewayParam).control(this.controlObject).sendTimes(1).enqueue();
        return meshGatewayParam;
    }

    public void sendIrParam(Context context, byte[] irParam) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(2);
        meshGatewayParam.setCmdData(irParam);
        Injection.message().create(context).cmdParam(meshGatewayParam).control(this.controlObject).sendTimes(1).enqueue();
    }

    private MeshGatewayParam sendIrComboControlByBle(Context context, byte[] activateData, byte[] irData, IAction<Boolean> iAction, boolean... send) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(2);
        meshGatewayParam.setCmdData(activateData);
        MeshGatewayParam meshGatewayParam2 = new MeshGatewayParam();
        meshGatewayParam2.setCmdType(1);
        meshGatewayParam2.setCmdData(irData);
        if (send.length > 0 && !send[0]) {
            if (iAction != null) {
                iAction.act(true);
            }
            meshGatewayParam2.setScene(true);
            return meshGatewayParam2;
        }
        meshGatewayParam2.setScene(false);
        Injection.message().create(context).cmdParam(meshGatewayParam).control(this.controlObject).resendTimes(2).receiveListener(new AnonymousClass1(context, meshGatewayParam2, iAction)).enqueue();
        return meshGatewayParam2;
    }

    /* renamed from: com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant$1, reason: invalid class name */
    class AnonymousClass1 implements IReceiveListener {
        final /* synthetic */ MeshGatewayParam val$cmdParam;
        final /* synthetic */ Context val$context;
        final /* synthetic */ IAction val$iAction;

        AnonymousClass1(final Context val$context, final MeshGatewayParam val$cmdParam, final IAction val$iAction) {
            this.val$context = val$context;
            this.val$cmdParam = val$cmdParam;
            this.val$iAction = val$iAction;
        }

        @Override // com.smart.message.base.IReceiveListener
        public void onSuccess(ResponseMsg msg) {
            Single.timer(200L, TimeUnit.MILLISECONDS).subscribe(new SingleObserver<Long>() { // from class: com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant.1.1
                @Override // io.reactivex.SingleObserver
                public void onSubscribe(Disposable d2) {
                }

                @Override // io.reactivex.SingleObserver
                public void onSuccess(Long aLong) {
                    Injection.message().create(AnonymousClass1.this.val$context).cmdParam(AnonymousClass1.this.val$cmdParam).control(GatewayAssistant.this.controlObject).resendTimes(2).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant.1.1.1
                        @Override // com.smart.message.base.IReceiveListener
                        public void onSuccess(ResponseMsg msg2) {
                            if (AnonymousClass1.this.val$iAction != null) {
                                AnonymousClass1.this.val$iAction.act(true);
                            }
                        }

                        @Override // com.smart.message.base.IReceiveListener
                        public void onTimeout() {
                            if (AnonymousClass1.this.val$iAction != null) {
                                AnonymousClass1.this.val$iAction.act(false);
                            }
                        }
                    }).enqueue();
                }

                @Override // io.reactivex.SingleObserver
                public void onError(Throwable e) {
                    if (AnonymousClass1.this.val$iAction != null) {
                        AnonymousClass1.this.val$iAction.act(false);
                    }
                }
            });
        }

        @Override // com.smart.message.base.IReceiveListener
        public void onTimeout() {
            Injection.message().create(this.val$context).cmdParam(this.val$cmdParam).control(GatewayAssistant.this.controlObject).resendTimes(2).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant.1.2
                @Override // com.smart.message.base.IReceiveListener
                public void onSuccess(ResponseMsg msg) {
                    if (AnonymousClass1.this.val$iAction != null) {
                        AnonymousClass1.this.val$iAction.act(true);
                    }
                }

                @Override // com.smart.message.base.IReceiveListener
                public void onTimeout() {
                    if (AnonymousClass1.this.val$iAction != null) {
                        AnonymousClass1.this.val$iAction.act(false);
                    }
                }
            }).enqueue();
        }
    }

    public MeshGatewayParam sendIrComboControl(final Context context, byte[] activateData, byte[] irData, IAction<Boolean> iAction, boolean... send) {
        BaseIrParam baseIrParam;
        if ((this.controlObject instanceof Device) && (baseIrParam = (BaseIrParam) ((Device) this.controlObject).getParam(BaseIrParam.class)) != null && baseIrParam.getUnicastAddress() > 0) {
            return sendIrComboControlByBle(context, activateData, irData, iAction, send);
        }
        sendIrParam(context, activateData);
        final MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(1);
        meshGatewayParam.setCmdData(irData);
        if (iAction != null) {
            iAction.act(true);
        }
        if (send.length > 0 && !send[0]) {
            return meshGatewayParam;
        }
        Single.timer(200L, TimeUnit.MILLISECONDS).subscribe(new SingleObserver<Long>() { // from class: com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant.2
            @Override // io.reactivex.SingleObserver
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.SingleObserver
            public void onSuccess(Long aLong) {
                Injection.message().create(context).cmdParam(meshGatewayParam).control(GatewayAssistant.this.controlObject).sendTimes(1).enqueue();
            }
        });
        return meshGatewayParam;
    }

    public MeshGatewayParam sendMotorControl(Context context, byte[] cmdData, IAction<ResponseMsg> iAction, boolean... send) {
        BaseIrParam baseIrParam;
        if ((this.controlObject instanceof Device) && (baseIrParam = (BaseIrParam) ((Device) this.controlObject).getParam(BaseIrParam.class)) != null && baseIrParam.getUnicastAddress() > 0) {
            return sendMotorControlByBle(context, cmdData, iAction, send);
        }
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(3);
        meshGatewayParam.setCmdData(cmdData);
        if (iAction != null) {
            iAction.act(new ResponseMsg());
        }
        if (send.length > 0 && !send[0]) {
            return meshGatewayParam;
        }
        Injection.message().create(context).cmdParam(meshGatewayParam).control(this.controlObject).sendTimes(1).enqueue();
        return meshGatewayParam;
    }

    private MeshGatewayParam sendMotorControlByBle(Context context, byte[] cmdData, final IAction<ResponseMsg> result, boolean... send) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(3);
        meshGatewayParam.setCmdData(cmdData);
        if (send.length > 0 && !send[0]) {
            return meshGatewayParam;
        }
        Injection.message().create(context).cmdParam(meshGatewayParam).control(this.controlObject).sendTimes(1).resendTimes(2).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant.3
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
        return meshGatewayParam;
    }

    public MeshGatewayParam sendDiyControl(Context context, byte[] cmdData, boolean... send) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(5);
        meshGatewayParam.setCmdData(cmdData);
        if (send.length > 0 && !send[0]) {
            return meshGatewayParam;
        }
        Injection.message().create(context).cmdParam(meshGatewayParam).control(this.controlObject).sendTimes(1).enqueue();
        return meshGatewayParam;
    }

    public MeshGatewayParam sendAmbientLightColor(Context context, byte[] cmdData, boolean finish) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(6);
        meshGatewayParam.setCmdData(cmdData);
        DataPackage.DataPacketBuilder intervalTime = Injection.message().create(context).cmdParam(meshGatewayParam).timeOutTime(5000).resendTimes(0).control(this.controlObject).intervalTime(100);
        if (finish) {
            intervalTime.sendTimes(3).enqueue();
            return meshGatewayParam;
        }
        intervalTime.sendTimes(1).filterEnqueue(150);
        return meshGatewayParam;
    }

    public MeshGatewayParam sendAmbientLightBrt(Context context, byte[] cmdData, boolean finish) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(7);
        meshGatewayParam.setCmdData(cmdData);
        DataPackage.DataPacketBuilder intervalTime = Injection.message().create(context).cmdParam(meshGatewayParam).timeOutTime(5000).resendTimes(0).control(this.controlObject).intervalTime(100);
        if (finish) {
            intervalTime.sendTimes(3).enqueue();
            return meshGatewayParam;
        }
        intervalTime.sendTimes(1).filterEnqueue(150);
        return meshGatewayParam;
    }

    public MeshGatewayParam sendAmbientLightOnOff(Context context, boolean z, IAction<Boolean>... iActionArr) {
        byte[] bArr = {z ? (byte) 1 : (byte) 0};
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(8);
        meshGatewayParam.setCmdData(bArr);
        Injection.message().create(context).cmdParam(meshGatewayParam).control(this.controlObject).intervalTime(0).resendTimes(0).enqueue();
        return meshGatewayParam;
    }

    public MeshGatewayParam queryAmbientLightStatus(Context context, IAction<ResponseMsg> result) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(9);
        sendWithResult(context, meshGatewayParam, result);
        return meshGatewayParam;
    }

    public MeshGatewayParam queryWifiProductVersion(Context context, IAction<ResponseMsg> result) {
        MeshGatewayParam meshGatewayParam = new MeshGatewayParam();
        meshGatewayParam.setCmdType(10);
        sendWithResult(context, meshGatewayParam, result);
        return meshGatewayParam;
    }

    @Override // com.ltech.smarthome.utils.cmd_assistant.BaseAssistant
    protected int sendWithResult(Context context, BaseCmdParam cmdParam, final IAction<ResponseMsg> result) {
        return Injection.message().create(context).cmdParam(cmdParam).resendTimes(0).control(this.controlObject).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant.4
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
                result.act(msg);
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
                result.act(null);
            }
        }).enqueue();
    }
}