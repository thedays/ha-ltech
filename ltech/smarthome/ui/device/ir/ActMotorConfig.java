package com.ltech.smarthome.ui.device.ir;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActMotorPairBinding;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.MotorParam;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.response.device.AddDeviceResponse;
import com.ltech.smarthome.ui.config.ConfigHelper;
import com.ltech.smarthome.ui.control.ActHome;
import com.ltech.smarthome.ui.device.ir.Device433Repository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.GatewayAssistant;
import com.ltech.smarthome.view.dialog.EditDialog;
import com.ltech.smarthome.view.dialog.PairDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActMotorConfig extends BaseNormalActivity<ActMotorPairBinding> {
    private String brandName;
    private Device device;
    private Device433Repository device433Repository;
    private Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (ConfigHelper.instance().unicastAddress > 0) {
                int i = msg.what;
                if (i == 1) {
                    ActMotorConfig.this.showLoadingDialog("");
                    GatewayAssistant gatewayAssistant = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                    ActMotorConfig actMotorConfig = ActMotorConfig.this;
                    gatewayAssistant.sendMotorControl(actMotorConfig, actMotorConfig.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_CONFIG), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.2.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(ResponseMsg responseMsg) {
                            if (responseMsg != null) {
                                sendEmptyMessageDelayed(2, 2500L);
                            } else {
                                ActMotorConfig.this.dismissLoadingDialog();
                                ActMotorConfig.this.showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                            }
                        }
                    }, new boolean[0]);
                    return;
                }
                if (i == 2) {
                    GatewayAssistant gatewayAssistant2 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                    ActMotorConfig actMotorConfig2 = ActMotorConfig.this;
                    gatewayAssistant2.sendMotorControl(actMotorConfig2, actMotorConfig2.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_CONFIG), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.2.2
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(ResponseMsg responseMsg) {
                            if (responseMsg != null) {
                                sendEmptyMessageDelayed(3, 2500L);
                            } else {
                                ActMotorConfig.this.dismissLoadingDialog();
                                ActMotorConfig.this.showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                            }
                        }
                    }, new boolean[0]);
                    return;
                } else if (i == 3) {
                    GatewayAssistant gatewayAssistant3 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                    ActMotorConfig actMotorConfig3 = ActMotorConfig.this;
                    gatewayAssistant3.sendMotorControl(actMotorConfig3, actMotorConfig3.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_UP), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.2.3
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(ResponseMsg responseMsg) {
                            if (responseMsg != null) {
                                sendEmptyMessageDelayed(4, 2500L);
                            } else {
                                ActMotorConfig.this.dismissLoadingDialog();
                                ActMotorConfig.this.showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                            }
                        }
                    }, new boolean[0]);
                    return;
                } else {
                    if (i != 4) {
                        return;
                    }
                    GatewayAssistant gatewayAssistant4 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                    ActMotorConfig actMotorConfig4 = ActMotorConfig.this;
                    gatewayAssistant4.sendMotorControl(actMotorConfig4, actMotorConfig4.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_STOP), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.2.4
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(ResponseMsg responseMsg) {
                            ActMotorConfig.this.dismissLoadingDialog();
                            if (responseMsg != null) {
                                return;
                            }
                            ActMotorConfig.this.showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                        }
                    }, new boolean[0]);
                    return;
                }
            }
            int i2 = msg.what;
            if (i2 == 1) {
                GatewayAssistant gatewayAssistant5 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                ActMotorConfig actMotorConfig5 = ActMotorConfig.this;
                gatewayAssistant5.sendMotorControl(actMotorConfig5, actMotorConfig5.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_CONFIG), null, new boolean[0]);
                sendEmptyMessageDelayed(2, 2500L);
                return;
            }
            if (i2 == 2) {
                GatewayAssistant gatewayAssistant6 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                ActMotorConfig actMotorConfig6 = ActMotorConfig.this;
                gatewayAssistant6.sendMotorControl(actMotorConfig6, actMotorConfig6.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_CONFIG), null, new boolean[0]);
                sendEmptyMessageDelayed(3, 2500L);
                return;
            }
            if (i2 == 3) {
                GatewayAssistant gatewayAssistant7 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                ActMotorConfig actMotorConfig7 = ActMotorConfig.this;
                gatewayAssistant7.sendMotorControl(actMotorConfig7, actMotorConfig7.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_UP), null, new boolean[0]);
                sendEmptyMessageDelayed(4, 2500L);
                return;
            }
            if (i2 != 4) {
                return;
            }
            GatewayAssistant gatewayAssistant8 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
            ActMotorConfig actMotorConfig8 = ActMotorConfig.this;
            gatewayAssistant8.sendMotorControl(actMotorConfig8, actMotorConfig8.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_STOP), null, new boolean[0]);
        }
    };
    private Device433Repository.MotorCodeLib motorCodeLib;
    private PairDialog pairDialog;

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_motor_pair;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        this.brandName = getIntent().getStringExtra(Constants.BRAND_NAME);
        Device device = new Device();
        this.device = device;
        device.setProductId(ConfigHelper.instance().productInfo.getProductId());
        this.device.setMacdeviceid(ConfigHelper.instance().macdeviceid);
        this.device.setMacfalg(2);
        if (ConfigHelper.instance().unicastAddress != 0) {
            MotorParam motorParam = new MotorParam();
            motorParam.setUnicastAddress(ConfigHelper.instance().unicastAddress);
            this.device.setParam(motorParam);
        }
        ((ActMotorPairBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActMotorConfig.this.lambda$initView$0((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() == R.id.bt_next && !TextUtils.isEmpty(this.brandName)) {
            if (this.device433Repository == null) {
                this.device433Repository = new Device433Repository();
            }
            if (ConfigHelper.instance().unicastAddress != 0) {
                this.motorCodeLib = this.device433Repository.getCodeLib(this, this.brandName, true);
            } else {
                this.motorCodeLib = this.device433Repository.getCodeLib(this, this.brandName, false);
            }
            if (this.motorCodeLib != null) {
                showPairDialog();
            }
        }
    }

    private void showPairDialog() {
        PairDialog dialogCallback = PairDialog.asDefault().setPairString(getString(R.string.pair)).setSuccessString(getString(R.string.pair_success)).setFailString(getString(getString(R.string.motor_duya).equals(this.brandName) ? R.string.pair_fail_retry : R.string.pair_fail)).setTitle(getString(R.string.pair_tip_1)).setTip(getString(R.string.pair_tip_2)).setDialogCallback(new PairDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.1
            @Override // com.ltech.smarthome.view.dialog.PairDialog.OnDialogCallback
            public void pair() {
                if (ActMotorConfig.this.getString(R.string.motor_duya).equals(ActMotorConfig.this.brandName) || ActMotorConfig.this.getString(R.string.motor_sien).equals(ActMotorConfig.this.brandName) || ActMotorConfig.this.getString(R.string.motor_baizhen).equals(ActMotorConfig.this.brandName)) {
                    ActMotorConfig.this.mHandler.removeMessages(1);
                    ActMotorConfig.this.mHandler.sendEmptyMessage(1);
                    return;
                }
                if (ActMotorConfig.this.getString(R.string.motor_lansen).equals(ActMotorConfig.this.brandName) || ActMotorConfig.this.getString(R.string.motor_chuangming).equals(ActMotorConfig.this.brandName)) {
                    if (ConfigHelper.instance().unicastAddress > 0) {
                        ActMotorConfig.this.showLoadingDialog("");
                        GatewayAssistant gatewayAssistant = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                        ActMotorConfig actMotorConfig = ActMotorConfig.this;
                        gatewayAssistant.sendMotorControl(actMotorConfig, actMotorConfig.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_CONFIG), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.1.1
                            @Override // com.ltech.smarthome.base.IAction
                            public void act(ResponseMsg responseMsg) {
                                ActMotorConfig.this.dismissLoadingDialog();
                                if (responseMsg == null) {
                                    ActMotorConfig.this.showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                                }
                            }
                        }, new boolean[0]);
                        return;
                    }
                    GatewayAssistant gatewayAssistant2 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                    ActMotorConfig actMotorConfig2 = ActMotorConfig.this;
                    gatewayAssistant2.sendMotorControl(actMotorConfig2, actMotorConfig2.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_CONFIG), null, new boolean[0]);
                    return;
                }
                if (ActMotorConfig.this.getString(R.string.motor_aoke).equals(ActMotorConfig.this.brandName) || ActMotorConfig.this.getString(R.string.motor_idemo).equals(ActMotorConfig.this.brandName)) {
                    if (ConfigHelper.instance().unicastAddress > 0) {
                        ActMotorConfig.this.showLoadingDialog("");
                        GatewayAssistant gatewayAssistant3 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                        ActMotorConfig actMotorConfig3 = ActMotorConfig.this;
                        gatewayAssistant3.sendMotorControl(actMotorConfig3, actMotorConfig3.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_CONFIG), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.1.2
                            @Override // com.ltech.smarthome.base.IAction
                            public void act(ResponseMsg responseMsg) {
                                ActMotorConfig.this.dismissLoadingDialog();
                                if (responseMsg == null) {
                                    ActMotorConfig.this.showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                                }
                            }
                        }, new boolean[0]);
                        return;
                    }
                    GatewayAssistant gatewayAssistant4 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                    ActMotorConfig actMotorConfig4 = ActMotorConfig.this;
                    gatewayAssistant4.sendMotorControl(actMotorConfig4, actMotorConfig4.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_UP), null, new boolean[0]);
                    return;
                }
                if (ActMotorConfig.this.getString(R.string.hanger_lbest).equals(ActMotorConfig.this.brandName)) {
                    if (ConfigHelper.instance().unicastAddress > 0) {
                        ActMotorConfig.this.showLoadingDialog("");
                        GatewayAssistant gatewayAssistant5 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                        ActMotorConfig actMotorConfig5 = ActMotorConfig.this;
                        gatewayAssistant5.sendMotorControl(actMotorConfig5, actMotorConfig5.motorCodeLib.getCodeByKey(Device433Repository.IR_MOTOR_KEY_NAME_CONFIG), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig.1.3
                            @Override // com.ltech.smarthome.base.IAction
                            public void act(ResponseMsg responseMsg) {
                                ActMotorConfig.this.dismissLoadingDialog();
                                if (responseMsg == null) {
                                    ActMotorConfig.this.showErrorDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                                }
                            }
                        }, new boolean[0]);
                        return;
                    }
                    GatewayAssistant gatewayAssistant6 = CmdAssistant.getGatewayAssistant(ActMotorConfig.this.device, new int[0]);
                    ActMotorConfig actMotorConfig6 = ActMotorConfig.this;
                    gatewayAssistant6.sendMotorControl(actMotorConfig6, actMotorConfig6.motorCodeLib.getCodeByKey(Device433Repository.IR_AIRER_KEY_NAME_UP), null, new boolean[0]);
                }
            }

            @Override // com.ltech.smarthome.view.dialog.PairDialog.OnDialogCallback
            public void confirm() {
                ActMotorConfig.this.pairDialog.dismissDialog();
                ActMotorConfig actMotorConfig = ActMotorConfig.this;
                actMotorConfig.showEditDialog(actMotorConfig.brandName);
            }

            @Override // com.ltech.smarthome.view.dialog.PairDialog.OnDialogCallback
            public void cancel() {
                if (ActMotorConfig.this.getString(R.string.motor_duya).equals(ActMotorConfig.this.brandName) && !ActMotorConfig.this.device433Repository.isChangeLast()) {
                    if (ConfigHelper.instance().unicastAddress != 0) {
                        ActMotorConfig actMotorConfig = ActMotorConfig.this;
                        Device433Repository device433Repository = actMotorConfig.device433Repository;
                        ActMotorConfig actMotorConfig2 = ActMotorConfig.this;
                        actMotorConfig.motorCodeLib = device433Repository.getCodeLib(actMotorConfig2, actMotorConfig2.brandName, true);
                    } else {
                        ActMotorConfig actMotorConfig3 = ActMotorConfig.this;
                        Device433Repository device433Repository2 = actMotorConfig3.device433Repository;
                        ActMotorConfig actMotorConfig4 = ActMotorConfig.this;
                        actMotorConfig3.motorCodeLib = device433Repository2.getCodeLib(actMotorConfig4, actMotorConfig4.brandName, false);
                    }
                    ActMotorConfig.this.mHandler.sendEmptyMessage(1);
                    if (ActMotorConfig.this.device433Repository.isChangeLast()) {
                        ActMotorConfig.this.pairDialog.setFailString(ActMotorConfig.this.getString(R.string.pair_fail));
                        ActMotorConfig.this.pairDialog.notifyDialog();
                        return;
                    }
                    return;
                }
                ActMotorConfig.this.pairDialog.dismissDialog();
            }
        });
        this.pairDialog = dialogCallback;
        dialogCallback.showDialog(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditDialog(String content) {
        EditDialog.asDefault().setContent(content).setTitle(getString(R.string.user_name)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActMotorConfig.this.lambda$showEditDialog$1((String) obj);
            }
        }).showDialog(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDialog$1(String str) {
        if (TextUtils.isEmpty(str)) {
            SmartToast.showShort(getString(R.string.input_name));
        } else {
            addDevice(str);
        }
    }

    public void addDevice(String deviceName) {
        MotorParam motorParam = new MotorParam();
        motorParam.setIrDatas(this.motorCodeLib);
        motorParam.setUnicastAddress(ConfigHelper.instance().unicastAddress);
        ConfigHelper.instance().subProductName = this.brandName + this.device433Repository.getIdString();
        ConfigHelper.instance().deviceName = deviceName;
        ConfigHelper.instance().subManufacturerName = this.brandName;
        ConfigHelper.instance().param = motorParam;
        CtrlPackage subDeviceCtrlPackage = CtrlPackager.getSubDeviceCtrlPackage(this.device);
        ConfigHelper.instance().codeLibrary = ConfigHelper.instance().getMotorCodeLib(ConfigHelper.instance().productInfo.getProductId(), subDeviceCtrlPackage.getAgreementId(), this.motorCodeLib);
        ConfigHelper.instance().macCode = "";
        if (motorParam.getUnicastAddress() != 0) {
            subDeviceCtrlPackage.setAddress(motorParam.getUnicastAddress());
            ConfigHelper.instance().codeLibrary = ConfigHelper.instance().getMotorCodeLib(ConfigHelper.instance().productInfo.getProductId(), 2, this.motorCodeLib, subDeviceCtrlPackage.getAddress());
        }
        ((ObservableSubscribeProxy) Injection.net().addDevice(ConfigHelper.instance().irProductData()).delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMotorConfig.this.lambda$addDevice$2((Disposable) obj);
            }
        }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Action
            public final void run() {
                ActMotorConfig.this.dismissLoadingDialog();
            }
        }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.ir.ActMotorConfig$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActMotorConfig.this.lambda$addDevice$3((AddDeviceResponse) obj);
            }
        }, new SmartErrorComsumer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDevice$2(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.adding));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDevice$3(AddDeviceResponse addDeviceResponse) throws Exception {
        ConfigHelper.instance().addDevice(addDeviceResponse, ConfigHelper.instance().productInfo.getProductId());
        NavUtils.destination(ActHome.class).navigation(this);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mHandler = null;
    }
}