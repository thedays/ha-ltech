package com.ltech.smarthome.upgrade;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import com.airoha.btdlib.core.AirohaLink;
import com.airoha.libfota.core.AirohaOtaMgr;
import com.airoha.libfota.core.OnAirohaOtaEventListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActUpgradeBinding;
import com.ltech.smarthome.message.CtrlPackage;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.singleton.MeshOta671;
import com.ltech.smarthome.upgrade.ota671.DfuCallback;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.SmartUtils;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.ProductVersionInfo;
import com.smart.product_agreement.parser.IUpgradeParser;
import com.smart.product_agreement.productBle.BleUpgradeHelper;
import com.smart.product_agreement.productBle.BleZipUpgradeHelper;
import com.smart.product_agreement.upgrade.BaseUpgradeHelper;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.reactivex.functions.Consumer;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ActUpgrade extends BaseNormalActivity<ActUpgradeBinding> {
    private static final int MSG_TIME_OUT = 3;
    private static final int MSG_WAIT_SEQ = 1;
    private static final int MSG_WAIT_SEQ_TIME_OUT = 2;
    private static final int UPGRADE_FAIL = 3;
    private static final int UPGRADE_NEW = 1;
    private static final int UPGRADE_SUCCESS = 2;
    private static final int UPGRADING = 4;
    private int curType;
    private String mAddress;
    private AirohaLink mAirohaLink;
    private AirohaOtaMgr mAirohaOtaMgr;
    private Device mDevice;
    private UpdateHandler mUpdateHandler;
    private float progress;
    private BaseUpgradeHelper upgradeHelper;
    private UpgradeInfo upgradeInfo;
    private ProductVersionInfo versionInfo;
    private boolean waitSync;
    private String latestVersion = "";
    private boolean canBleUpgrade = false;
    private boolean isNewMeshModule = false;
    private boolean canBleZipUpgrade = false;
    BaseUpgradeHelper.IUpgradeCallback mIUpgradeCallback = new BaseUpgradeHelper.IUpgradeCallback() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.10
        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgradeSuccess(int nextNum) {
            if (!ActUpgrade.this.canBleUpgrade && ProductRepository.isBLeDevice(ActUpgrade.this.mDevice.getProductId())) {
                Injection.mesh().setNodeTtl(16);
            }
            ActUpgrade.this.setUpgradeView(2);
            EventBusUtils.post(new LiveBusHelper(6));
        }

        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgradeFail() {
            if (!ActUpgrade.this.canBleUpgrade && ProductRepository.isBLeDevice(ActUpgrade.this.mDevice.getProductId())) {
                Injection.mesh().setNodeTtl(16);
            }
            ActUpgrade.this.setUpgradeView(3);
        }

        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgrading(float progress) {
            if (ActUpgrade.this.mViewBinding != null) {
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%.2f%%", Float.valueOf(progress)));
            }
        }
    };
    private final DfuCallback newOtaCallback = new DfuCallback() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.11
        @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
        public void DfuStart() {
        }

        @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
        public void DfuSuccess() {
            ActUpgrade.this.setUpgradeView(2);
            ThreadUtils.getMainHandler().postDelayed(new Runnable(this) { // from class: com.ltech.smarthome.upgrade.ActUpgrade.11.1
                @Override // java.lang.Runnable
                public void run() {
                    MeshOta671.getInstance().disconnect();
                }
            }, 5000L);
        }

        @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
        public void DfuFail(String var1) {
            ActUpgrade.this.setUpgradeView(3);
        }

        @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
        public void DfuProgress(double progress) {
            if (ActUpgrade.this.mViewBinding != null) {
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%.2f%%", Double.valueOf(progress)));
            }
        }

        @Override // com.ltech.smarthome.upgrade.ota671.DfuCallback
        public void Msg(String var1) {
            LHomeLog.d(getClass(), var1);
        }
    };
    private final OnAirohaOtaEventListener mOnAirohaOtaEventListener = new AnonymousClass12();
    private final Handler handle = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.14
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            int i = msg.what;
            if (i == 1) {
                ActUpgrade.this.handle.removeMessages(1);
                ActUpgrade.this.handle.sendEmptyMessageDelayed(1, 10000L);
                CmdAssistant.getQueryCmdAssistant(ActUpgrade.this.mDevice, new int[0]).queryPanelState(ActUpgrade.this.activity, new IAction<ResponseMsg>(this) { // from class: com.ltech.smarthome.upgrade.ActUpgrade.14.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg msg2) {
                    }
                }, 0);
            } else if (i == 2) {
                if (ActUpgrade.this.mViewBinding != null) {
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%.2f%%", Float.valueOf(ActUpgrade.this.progress)));
                }
                if (ActUpgrade.this.progress <= 98.0f) {
                    ActUpgrade.this.progress += 1.0f;
                }
                ActUpgrade.this.handle.sendEmptyMessageDelayed(2, 2000L);
            }
            return false;
        }
    });

    static /* synthetic */ void lambda$updateDeviceParam$3(Object obj) throws Exception {
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_upgrade;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        Device deviceById = Injection.repo().device().getDeviceById(getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        if (deviceById != null) {
            setTitle(deviceById.getDeviceName());
            this.mDevice = deviceById;
            setTitle(deviceById.getDeviceName());
            queryVersion(this.mDevice);
            this.isNewMeshModule = ProductRepository.isNewBleModule(this.mDevice);
        }
        ((ActUpgradeBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.upgrade.ActUpgrade$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActUpgrade.this.lambda$initView$0((View) obj);
            }
        }));
        getWindow().addFlags(128);
        this.mAirohaLink = new AirohaLink(this);
        this.mAirohaOtaMgr = new AirohaOtaMgr(this.mAirohaLink);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() == R.id.tv_upgrade && 1 == this.curType) {
            startUpgrade();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        queryVersion(this.mDevice);
    }

    private void queryVersion(Device device) {
        showLoading();
        String productId = device.getProductId();
        productId.hashCode();
        if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
            queryMeshGatewayVersion();
            return;
        }
        if (productId.equals(ProductId.ID_HOME_KIT)) {
            queryBleDeviceVersion();
            return;
        }
        if (this.mUpdateHandler == null) {
            this.mUpdateHandler = new UpdateHandler();
        }
        this.mUpdateHandler.removeMessages(1);
        this.mUpdateHandler.removeMessages(2);
        this.mUpdateHandler.sendEmptyMessage(1);
        this.mUpdateHandler.sendEmptyMessageDelayed(2, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryBleDeviceVersion() {
        CmdAssistant.getQueryCmdAssistant(this.mDevice, new int[0]).queryProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.upgrade.ActUpgrade$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActUpgrade.this.lambda$queryBleDeviceVersion$1((ResponseMsg) obj);
            }
        });
        CmdAssistant.getQueryCmdAssistant(this.mDevice, new int[0]).querySupportBleUpgrade(this.activity, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                if (responseMsg == null || responseMsg.getResData().length() < 18) {
                    return;
                }
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
                ActUpgrade.this.canBleUpgrade = parseInt == 2 || parseInt == 3;
                ActUpgrade.this.canBleZipUpgrade = parseInt == 3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryBleDeviceVersion$1(ResponseMsg responseMsg) {
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg);
        if (responseMsg == null) {
            if (this.latestVersion.equals("")) {
                showError();
                return;
            }
            return;
        }
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg.getAgreementId());
        if (responseMsg.getResData().length() < 16) {
            LHomeLog.i(getClass(), "msg.getResData().length()<16 resData-->" + responseMsg.getResData());
            showError();
        } else {
            LHomeLog.i(getClass(), "msg.getResData() current, resData-->" + responseMsg.getResData());
            this.versionInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(StringUtils.hexStringToByte(responseMsg.getResData().substring(16))));
        }
        if (this.versionInfo == null) {
            showError();
            return;
        }
        if (Integer.parseInt(responseMsg.getDeviceFlag(), 16) != ((BleParam) this.mDevice.getParam(BleParam.class)).getUnicastAddress()) {
            queryBleDeviceVersion();
            return;
        }
        showContent();
        LHomeLog.i(getClass(), "versionInfo.getDeviceModel() =" + this.versionInfo.getDeviceModel() + "  " + this.versionInfo.gethVer() + "   " + this.versionInfo.getsVer());
        UpgradeInfo upgradeInfo = UpgradeFactory.getUpgradeInfo(this.versionInfo);
        this.upgradeInfo = upgradeInfo;
        if (upgradeInfo == null || upgradeInfo.getsVer().compareTo(this.versionInfo.getsVer().toUpperCase()) <= 0) {
            this.latestVersion = UpgradeInfo.getSoftwareVersion(this.versionInfo.getsVer());
            setUpgradeView(2);
        } else {
            this.latestVersion = UpgradeInfo.getSoftwareVersion(this.upgradeInfo.getsVer());
            setUpgradeView(1);
        }
    }

    private void queryMeshGatewayVersion() {
        CmdAssistant.getGatewayAssistant(this.mDevice, new int[0]).queryWifiProductVersion(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.upgrade.ActUpgrade$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActUpgrade.this.lambda$queryMeshGatewayVersion$2((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryMeshGatewayVersion$2(ResponseMsg responseMsg) {
        LHomeLog.i(getClass(), "queryMeshGatewayVersion=" + responseMsg);
        if (responseMsg == null) {
            showError();
            return;
        }
        LHomeLog.i(getClass(), "queryMeshGatewayVersion=" + responseMsg.getAgreementId());
        ProductVersionInfo parserUpgradeInfo = ((IUpgradeParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserUpgradeInfo(new String(StringUtils.hexStringToByte(responseMsg.getResData())));
        this.versionInfo = parserUpgradeInfo;
        if (parserUpgradeInfo == null) {
            showError();
            return;
        }
        showContent();
        LHomeLog.i(getClass(), "versionInfo.getDeviceModel() =" + this.versionInfo.getDeviceModel() + "  " + this.versionInfo.gethVer() + "   " + this.versionInfo.getsVer());
        UpgradeInfo upgradeInfo = UpgradeFactory.getUpgradeInfo(this.versionInfo);
        this.upgradeInfo = upgradeInfo;
        if (upgradeInfo == null || upgradeInfo.getsVer().compareTo(this.versionInfo.getsVer().toUpperCase()) <= 0) {
            this.latestVersion = UpgradeInfo.getSoftwareVersion(this.versionInfo.getsVer());
            setUpgradeView(2);
        } else {
            this.latestVersion = UpgradeInfo.getSoftwareVersion(this.upgradeInfo.getsVer());
            setUpgradeView(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpgradeView(final int type) {
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.2
            @Override // java.lang.Runnable
            public void run() {
                ActUpgrade.this.dismissLoadingDialog();
                ActUpgrade.this.curType = type;
                if (ActUpgrade.this.mViewBinding == null) {
                    return;
                }
                int i = type;
                if (i == 1) {
                    ActUpgrade.this.setBackImage(R.mipmap.icon_back);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).progressBar.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvProgress.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).ivUpgrade.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_upgrade_new);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip1.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip1.setText(String.format("%sSVer%s", ActUpgrade.this.getString(R.string.new_version), ActUpgrade.this.latestVersion));
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip2.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip2.setText(String.format("%sSVer%s", ActUpgrade.this.getString(R.string.cur_version), UpgradeInfo.getSoftwareVersion(ActUpgrade.this.versionInfo.getsVer())));
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip3.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setBackgroundResource(R.drawable.shape_red_bt);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setTextColor(ContextCompat.getColor(ActUpgrade.this.activity, R.color.white));
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setText(ActUpgrade.this.getString(R.string.upgrade_now));
                    return;
                }
                if (i == 2) {
                    Injection.mesh().setBleFwUpgradeStart(false);
                    ActUpgrade.this.setBackImage(R.mipmap.icon_back);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).progressBar.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvProgress.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).ivUpgrade.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_success);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip1.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip1.setText(ActUpgrade.this.getString(R.string.latest_version));
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip2.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip2.setText(String.format("%sSVer%s", ActUpgrade.this.getString(R.string.cur_version), ActUpgrade.this.latestVersion));
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip3.setVisibility(8);
                    EventBusUtils.post(new LiveBusHelper(6));
                    ActUpgrade.this.asDataUpdate();
                    return;
                }
                if (i == 3) {
                    Injection.mesh().setBleFwUpgradeStart(false);
                    ActUpgrade.this.setBackImage(R.mipmap.icon_back);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).progressBar.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvProgress.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).ivUpgrade.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_fail);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip1.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip1.setText(ActUpgrade.this.getString(R.string.upgrade_fail));
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip2.setVisibility(8);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip3.setVisibility(0);
                    ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip3.setText(ActUpgrade.this.getString(R.string.upgrading_fail_tip));
                    return;
                }
                if (i != 4) {
                    return;
                }
                ActUpgrade.this.setBackImage(R.mipmap.icon_back);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).progressBar.setVisibility(0);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvProgress.setVisibility(0);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%.2f%%", Float.valueOf(0.0f)));
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).progressBar.setup(R.color.color_text_red);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).ivUpgrade.setVisibility(4);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip1.setVisibility(0);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip1.setText(ActUpgrade.this.getString(R.string.upgrading_tip));
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip2.setVisibility(8);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip3.setVisibility(0);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgradeTip3.setText(ActUpgrade.this.getString(R.string.upgrading_attention_tip));
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setVisibility(0);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setBackgroundResource(R.drawable.shape_light_gray_bt);
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setTextColor(ContextCompat.getColor(ActUpgrade.this.activity, R.color.color_text_light_black));
                ((ActUpgradeBinding) ActUpgrade.this.mViewBinding).tvUpgrade.setText(ActUpgrade.this.getString(R.string.wait));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void asDataUpdate() {
        BleParam bleParam;
        if (!ProductRepository.isAsPanel(this.mDevice.getProductId()) || (bleParam = (BleParam) this.mDevice.getParam(BleParam.class)) == null || bleParam.getPublicationId() != 0 || bleParam.getPublicationAddress() <= 0 || bleParam.getGroupId() <= 0) {
            return;
        }
        bleParam.setPublicationId(bleParam.getGroupId());
        bleParam.setGroupId(0L);
        this.mDevice.setParam(bleParam);
        Injection.repo().device().saveDevice(this.mDevice);
        updateDeviceParam(this.mDevice, bleParam);
    }

    private void updateDeviceParam(Device mDevice, BleParam param) {
        ((ObservableSubscribeProxy) Injection.net().updateParam(mDevice.getDeviceId(), GsonUtils.toJson(param)).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActUpgrade$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ActUpgrade.lambda$updateDeviceParam$3(obj);
            }
        }, new SmartErrorComsumer());
    }

    private void startUpgrade() {
        showLoadingDialog("");
        this.mAddress = this.mDevice.getWifiMac().replaceAll("..(?!$)", "$0:");
        if (ProductId.ID_HOME_KIT.equals(this.mDevice.getProductId())) {
            dismissLoadingDialog();
            this.waitSync = false;
            MessageManager.getInstance().setCgKitCallBack(new MessageManager.CgKitCallBack() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.3
                @Override // com.smart.message.MessageManager.CgKitCallBack
                public void onDataReceive(ResponseMsg msg) {
                    if (ActUpgrade.this.mDevice == null || msg == null) {
                        return;
                    }
                    if (Integer.parseInt(msg.getResData().substring(6, 10), 16) == ((BleParam) ActUpgrade.this.mDevice.getParam(BleParam.class)).getUnicastAddress()) {
                        ActUpgrade.this.checkState(msg);
                    }
                }
            });
            setUpgradeView(4);
            CmdAssistant.getSettingCmdAssistant(this.mDevice, new int[0]).starUpgrade(this, new IAction<Boolean>() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.4
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    if (aBoolean.booleanValue()) {
                        ActUpgrade.this.waitSync = true;
                        ActUpgrade.this.progress = 0.0f;
                        ActUpgrade.this.handle.removeMessages(1);
                        ActUpgrade.this.handle.sendEmptyMessage(1);
                        return;
                    }
                    ActUpgrade.this.handle.removeMessages(1);
                    ActUpgrade.this.handle.removeMessages(2);
                    ActUpgrade.this.setUpgradeView(3);
                }
            });
            return;
        }
        if (this.isNewMeshModule) {
            Injection.mesh().setBleFwUpgradeStart(true);
            LHomeLog.e("startUpgrade", getClass(), "new module upgrade");
            MeshOta671.getInstance().init(this, this.newOtaCallback);
            MeshOta671.getInstance().setFile(this.upgradeInfo.getFirmwareData());
            Injection.mesh().disconnect();
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.5
                @Override // java.lang.Runnable
                public void run() {
                    if (!MeshOta671.getInstance().connect(ActUpgrade.this.mAddress)) {
                        ActUpgrade.this.setUpgradeView(3);
                    } else {
                        ActUpgrade.this.setUpgradeView(4);
                    }
                }
            }, 4000L);
            return;
        }
        if (this.canBleUpgrade && !this.upgradeInfo.isMeshUpdate()) {
            Injection.mesh().setBleFwUpgradeStart(true);
            if (this.canBleZipUpgrade) {
                this.upgradeHelper = UpgradeFactory.getUpgradeHelper(this, 7, this.upgradeInfo.getFirmwareData(), getMainHandler());
            } else {
                this.upgradeHelper = UpgradeFactory.getUpgradeHelper(this, 3, this.upgradeInfo.getFirmwareData(), getMainHandler());
            }
            Injection.mesh().disconnect();
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.6
                @Override // java.lang.Runnable
                public void run() {
                    ActUpgrade.this.startBleConnect();
                }
            }, 2000L);
            BaseUpgradeHelper baseUpgradeHelper = this.upgradeHelper;
            if (baseUpgradeHelper instanceof BleUpgradeHelper) {
                ((BleUpgradeHelper) baseUpgradeHelper).setOnDataSendCallBack(new BleUpgradeHelper.OnDataSendCallBack() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.7
                    @Override // com.smart.product_agreement.productBle.BleUpgradeHelper.OnDataSendCallBack
                    public void sendUpgradeCmd(byte[] data) {
                        ActUpgrade.this.mAirohaOtaMgr.writeAtCharc(data);
                        LHomeLog.e(getClass(), "send:" + StringUtils.byte2Str(data).trim());
                    }
                });
            } else if (baseUpgradeHelper instanceof BleZipUpgradeHelper) {
                ((BleZipUpgradeHelper) baseUpgradeHelper).setOnDataSendCallBack(new BleZipUpgradeHelper.OnDataSendCallBack() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.8
                    @Override // com.smart.product_agreement.productBle.BleZipUpgradeHelper.OnDataSendCallBack
                    public void sendUpgradeCmd(byte[] data) {
                        ActUpgrade.this.mAirohaOtaMgr.writeAtCharc(data);
                        LHomeLog.e(getClass(), "send:" + StringUtils.byte2Str(data).trim());
                    }
                });
            }
            this.upgradeHelper.setUpgradeCallback(this.mIUpgradeCallback);
            return;
        }
        LHomeLog.e("startUpgrade", getClass(), "mesh upgrade");
        this.upgradeHelper = UpgradeFactory.getUpgradeHelper(this, this.upgradeInfo.getHelperId(), this.upgradeInfo.getFirmwareData(), getMainHandler());
        CtrlPackage ctrlPackage = (CtrlPackage) SmartUtils.getICtrlConverter().convert(this.mDevice);
        if (ProductRepository.isBLeDevice(this.mDevice.getProductId())) {
            ctrlPackage.setAddress(((BleParam) this.mDevice.getParam(BleParam.class)).getUnicastAddress());
        }
        this.upgradeHelper.setCtrlPackage(ctrlPackage);
        this.upgradeHelper.setUpgradeCallback(this.mIUpgradeCallback);
        if (ProductRepository.isBLeDevice(this.mDevice.getProductId()) && filterDevice(this.mDevice)) {
            CmdAssistant.getSettingCmdAssistant(this.mDevice, new int[0]).starUpgrade(this);
        }
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.9
            @Override // java.lang.Runnable
            public void run() {
                ActUpgrade.this.upgradeHelper.reset();
                if (ActUpgrade.this.upgradeHelper.startUpgrade()) {
                    if (ProductRepository.isBLeDevice(ActUpgrade.this.mDevice.getProductId())) {
                        Injection.mesh().setNodeTtl(1);
                    }
                    ActUpgrade.this.setUpgradeView(4);
                }
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBleConnect() {
        this.mAirohaOtaMgr.setListener(this.mOnAirohaOtaEventListener);
        this.mAirohaLink.connect(this.mAddress);
        setUpgradeView(4);
        this.mUpdateHandler.removeMessages(3);
        this.mUpdateHandler.sendEmptyMessageDelayed(3, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
    }

    /* renamed from: com.ltech.smarthome.upgrade.ActUpgrade$12, reason: invalid class name */
    class AnonymousClass12 implements OnAirohaOtaEventListener {
        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBatteryLevel(final int batteryLevel) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattDisconnected() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleBootCodeNotMatching() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleCodeAreaAddrNotMatching() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnReportProgramThroughput(final float throughput) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnUpdateProgrammingProgress(final float progress) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnWorkingAreaChanged() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnWorkingAreaStatus(final String workingArea, final String area1Rev, final boolean isArea1Valid, final String area2Rev, final boolean isArea2Valid) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onFwVersion(String ver) {
        }

        AnonymousClass12() {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnRequestMtuChangeStatus(boolean isAccepted) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnNewMtu(final int mtu) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattConnected() {
            ActUpgrade.this.mUpdateHandler.removeMessages(3);
            ActUpgrade.this.upgradeHelper.reset(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.12.1
                @Override // java.lang.Runnable
                public void run() {
                    ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.12.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (!ActUpgrade.this.upgradeHelper.startUpgrade()) {
                                ActUpgrade.this.setUpgradeView(3);
                            } else {
                                ActUpgrade.this.mAirohaLink.cancelTimer();
                                LHomeLog.e("startUpgrade", getClass(), "ble upgrade");
                            }
                        }
                    }, 200L);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleOtaDisabled(final byte errorCode) {
            ActUpgrade.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.12.2
                @Override // java.lang.Runnable
                public void run() {
                    ActUpgrade.this.mAirohaLink.disconnect();
                    ActUpgrade.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBinFileParseException() {
            ActUpgrade.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.12.3
                @Override // java.lang.Runnable
                public void run() {
                    ActUpgrade.this.mAirohaLink.disconnect();
                    ActUpgrade.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnRetryFailed() {
            ActUpgrade.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.12.4
                @Override // java.lang.Runnable
                public void run() {
                    ActUpgrade.this.mAirohaLink.disconnect();
                    ActUpgrade.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnStatusError(final byte errorCode, final String errorMsg) {
            ActUpgrade.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.12.5
                @Override // java.lang.Runnable
                public void run() {
                    ActUpgrade.this.setUpgradeView(3);
                    ActUpgrade.this.mAirohaLink.disconnect();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onCheckFwVersionFailed() {
            ActUpgrade.this.mAirohaLink.disconnect();
            ActUpgrade.this.setUpgradeView(3);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onDataCallback(final byte[] s) {
            LHomeLog.e(getClass(), "rec:" + StringUtils.byte2Str(s).trim());
            ActUpgrade.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.12.6
                @Override // java.lang.Runnable
                public void run() {
                    if (ActUpgrade.this.upgradeHelper instanceof BleUpgradeHelper) {
                        ((BleUpgradeHelper) ActUpgrade.this.upgradeHelper).recUpgradeCmd(StringUtils.byte2Str(s));
                    } else if (ActUpgrade.this.upgradeHelper instanceof BleZipUpgradeHelper) {
                        ((BleZipUpgradeHelper) ActUpgrade.this.upgradeHelper).recUpgradeCmd(StringUtils.byte2Str(s));
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onWaitNewOtaShow() {
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgrade.12.7
                @Override // java.lang.Runnable
                public void run() {
                    ActUpgrade.this.startBleConnect();
                }
            }, 2000L);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.curType == 4) {
            showUpgradingDialog();
        } else {
            super.back();
        }
    }

    private void showUpgradingDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", getString(R.string.app_str_stop_upgrading_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.upgrade.ActUpgrade.13
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.upgrade.ActUpgrade$$ExternalSyntheticLambda1
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUpgradingDialog$4;
                lambda$showUpgradingDialog$4 = ActUpgrade.this.lambda$showUpgradingDialog$4(baseDialog, view);
                return lambda$showUpgradingDialog$4;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUpgradingDialog$4(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }

    private class UpdateHandler extends Handler {
        private UpdateHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (Injection.mesh().isSeqSuccess()) {
                    removeMessages(2);
                    ActUpgrade.this.queryBleDeviceVersion();
                    return;
                } else {
                    sendEmptyMessageDelayed(1, 1000L);
                    return;
                }
            }
            if (msg.what == 2) {
                ActUpgrade.this.showError();
            } else if (msg.what == 3) {
                ActUpgrade.this.setUpgradeView(3);
            }
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Device device;
        super.onDestroy();
        Injection.mesh().setBleFwUpgradeStart(false);
        Injection.mesh().setUpgradeStart(false);
        if (!this.canBleUpgrade && (device = this.mDevice) != null && ProductRepository.isBLeDevice(device.getProductId())) {
            Injection.mesh().setNodeTtl(16);
        }
        AirohaOtaMgr airohaOtaMgr = this.mAirohaOtaMgr;
        if (airohaOtaMgr != null) {
            airohaOtaMgr.close();
        }
        AirohaLink airohaLink = this.mAirohaLink;
        if (airohaLink != null) {
            airohaLink.disconnect();
            this.mAirohaLink.close();
        }
        MeshOta671.getInstance().disconnect();
    }

    private boolean filterDevice(Device device) {
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "221042516351701":
            case "123072510445601":
            case "221030816330401":
            case "121031814513301":
            case "121042516340801":
            case "121042516345401":
                return true;
            default:
                return false;
        }
    }

    public void checkState(ResponseMsg msg) {
        if (this.waitSync) {
            int parseInt = Integer.parseInt(msg.getResData().substring(16, 18), 16);
            if (parseInt == 8 || parseInt == 37 || parseInt == 0) {
                this.handle.removeMessages(1);
                this.handle.removeMessages(2);
                this.waitSync = false;
                setUpgradeView(2);
                EventBusUtils.post(new LiveBusHelper(6));
                return;
            }
            if (parseInt == 9 || parseInt == 1) {
                this.handle.removeMessages(1);
                this.handle.removeMessages(2);
                setUpgradeView(3);
                return;
            }
            this.handle.sendEmptyMessageDelayed(2, 1000L);
        }
    }
}