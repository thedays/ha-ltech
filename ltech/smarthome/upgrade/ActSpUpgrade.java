package com.ltech.smarthome.upgrade;

import android.text.Html;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActUpgradeBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ActSpUpgrade extends BaseNormalActivity<ActUpgradeBinding> {
    private static final int UPGRADE_FAIL = 3;
    private static final int UPGRADE_NEW = 1;
    private static final int UPGRADE_SUCCESS = 2;
    private static final int UPGRADING = 4;
    private int cmdType;
    private int curType;
    private Device mDevice;
    private String version = "";
    private String newVersion = "";

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
            this.mDevice = deviceById;
            this.cmdType = getIntent().getIntExtra(Constants.COMMAND_TYPE, -1);
            this.version = getIntent().getStringExtra("version");
            String stringExtra = getIntent().getStringExtra(Constants.NEW_VERSION);
            this.newVersion = stringExtra;
            String str = this.version;
            if (str != null && stringExtra != null) {
                if (str.compareTo(stringExtra) >= 0) {
                    setUpgradeView(2);
                } else {
                    queryBleDevice();
                }
            }
            setTitle(getString(this.cmdType == 0 ? R.string.firmware_upgrade : R.string.app_str_app_upgradeg));
        }
        ((ActUpgradeBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSpUpgrade.this.lambda$initView$0((View) obj);
            }
        }));
        getWindow().addFlags(128);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        if (view.getId() != R.id.tv_upgrade) {
            return;
        }
        int i = this.curType;
        if (1 == i) {
            startUpgrade();
            return;
        }
        if (4 == i) {
            showUpgradingDialog();
        } else if (3 == i) {
            setUpgradeView(1);
            startUpgrade();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void onRetry() {
        super.onRetry();
        queryBleDevice();
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyStringRes(R.string.app_str_device_not_support));
    }

    private void queryBleDevice() {
        showLoadingDialog("");
        CmdAssistant.getDeviceAssistant(this.mDevice, new int[0]).querySuperPanelIsUpgrade(this, new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(ResponseMsg responseMsg) {
                ActSpUpgrade.this.dismissLoadingDialog();
                if (responseMsg == null || responseMsg.getStateCode() != 0 || responseMsg.getResData() == null || responseMsg.getResData().length() <= 16) {
                    ActSpUpgrade.this.showEmpty();
                    return;
                }
                int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 18), 16);
                if (ActSpUpgrade.this.cmdType == 1 && parseInt == 1) {
                    ActSpUpgrade.this.setUpgradeView(4);
                    return;
                }
                if (ActSpUpgrade.this.cmdType == 0 && parseInt == 2) {
                    ActSpUpgrade.this.setUpgradeView(4);
                    return;
                }
                if ((ActSpUpgrade.this.cmdType == 1 && parseInt == 2) || (ActSpUpgrade.this.cmdType == 0 && parseInt == 1)) {
                    ActSpUpgrade.this.showAppUpgradingDialog();
                } else {
                    ActSpUpgrade.this.setUpgradeView(1);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpgradeView(int type) {
        dismissLoadingDialog();
        this.curType = type;
        if (this.mViewBinding == 0) {
            return;
        }
        if (type == 1) {
            setBackImage(R.mipmap.icon_back);
            ((ActUpgradeBinding) this.mViewBinding).progressBar.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_upgrade_new);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setText(Html.fromHtml(String.format("%sSVer%s", this.cmdType == 0 ? getString(R.string.new_version) : getString(R.string.app_str_new_app_version), this.newVersion)));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setText(String.format("%sSVer%s", getString(R.string.cur_version), this.version));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setText(getString(R.string.app_str_sp_upgrading_attention_tip));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setBackgroundResource(R.drawable.shape_red_bt);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setTextColor(ContextCompat.getColor(this, R.color.white));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setText(getString(R.string.upgrade_now));
            return;
        }
        if (type == 2) {
            setBackImage(R.mipmap.icon_back);
            ((ActUpgradeBinding) this.mViewBinding).progressBar.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_success);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setText(getString(R.string.app_str_latest_app_version));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setText(String.format("%sSVer%s", getString(R.string.cur_version), this.version));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setVisibility(8);
            EventBusUtils.post(new LiveBusHelper(6));
            return;
        }
        if (type == 3) {
            setBackImage(R.mipmap.icon_back);
            ((ActUpgradeBinding) this.mViewBinding).progressBar.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_fail);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setText(getString(R.string.upgrade_fail));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setText(getString(R.string.upgrading_fail_tip));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setBackgroundResource(R.drawable.shape_red_bt);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setTextColor(ContextCompat.getColor(this, R.color.white));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setText(getString(R.string.app_str_retry_upgrade));
            return;
        }
        if (type != 4) {
            return;
        }
        setBackImage(R.mipmap.icon_back);
        ((ActUpgradeBinding) this.mViewBinding).progressBar.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).progressBar.setup(R.color.color_text_red);
        ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setVisibility(4);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setText(getString(R.string.upgrading_tip));
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setVisibility(8);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setText(getString(R.string.app_str_sp_upgrading_attention_tip));
        ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setBackgroundResource(R.drawable.shape_light_gray_bt);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setTextColor(ContextCompat.getColor(this, R.color.color_text_light_black));
        ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setText(getString(R.string.app_str_cancel_upgradeg));
    }

    private void startUpgrade() {
        showLoadingDialog("");
        if (this.cmdType == 0) {
            firmwareUpdate();
        } else {
            appUpdate();
        }
    }

    private void showUpgradingDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", getString(this.cmdType == 0 ? R.string.app_str_stop_firmware_upgrading_tip : R.string.app_str_stop_app_upgrading_tip)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade.2
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showUpgradingDialog$1;
                lambda$showUpgradingDialog$1 = ActSpUpgrade.this.lambda$showUpgradingDialog$1(baseDialog, view);
                return lambda$showUpgradingDialog$1;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showUpgradingDialog$1(BaseDialog baseDialog, View view) {
        if (this.cmdType == 0) {
            stopFirmwareUpdate();
            return false;
        }
        stopAppUpdate();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAppUpgradingDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", getString(this.cmdType == 0 ? R.string.app_str_stop_firmware_upgrading_tip2 : R.string.app_str_stop_app_upgrading_tip2)).setCancelButton(ActivityUtils.getTopActivity().getString(R.string.cancel), new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade.3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(ActivityUtils.getTopActivity().getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda0
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showAppUpgradingDialog$2;
                lambda$showAppUpgradingDialog$2 = ActSpUpgrade.this.lambda$showAppUpgradingDialog$2(baseDialog, view);
                return lambda$showAppUpgradingDialog$2;
            }
        }).setCancelable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showAppUpgradingDialog$2(BaseDialog baseDialog, View view) {
        super.back();
        return false;
    }

    public void firmwareUpdate() {
        JSONObject jSONObject = new JSONObject();
        try {
            String str = Injection.repo().user().getUserId() + "";
            if (str.length() == 15) {
                str = "0" + str;
            }
            jSONObject.put("CharSwitch", "66BB00C00000F7000A0103" + str + "EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.mDevice.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSpUpgrade.this.lambda$firmwareUpdate$3((Disposable) obj);
                }
            }).doFinally(new ActSpUpgrade$$ExternalSyntheticLambda5(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSpUpgrade.this.lambda$firmwareUpdate$4(obj);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$firmwareUpdate$3(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$firmwareUpdate$4(Object obj) throws Exception {
        setUpgradeView(4);
        SmartToast.showShort(R.string.encrypt_password_open_success);
    }

    public void appUpdate() {
        JSONObject jSONObject = new JSONObject();
        try {
            String str = Injection.repo().user().getUserId() + "";
            if (str.length() == 15) {
                str = "0" + str;
            }
            jSONObject.put("CharSwitch", "66BB00C00000F7000A0102" + str + "EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.mDevice.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda9
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSpUpgrade.this.lambda$appUpdate$5((Disposable) obj);
                }
            }).doFinally(new ActSpUpgrade$$ExternalSyntheticLambda5(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSpUpgrade.this.lambda$appUpdate$6(obj);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appUpdate$5(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$appUpdate$6(Object obj) throws Exception {
        setUpgradeView(4);
        SmartToast.showShort(R.string.encrypt_password_open_success);
    }

    public void stopFirmwareUpdate() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("CharSwitch", "66BB00C00000F700020109EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.mDevice.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSpUpgrade.this.lambda$stopFirmwareUpdate$7((Disposable) obj);
                }
            }).doFinally(new ActSpUpgrade$$ExternalSyntheticLambda5(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSpUpgrade.this.lambda$stopFirmwareUpdate$8(obj);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopFirmwareUpdate$7(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopFirmwareUpdate$8(Object obj) throws Exception {
        setUpgradeView(1);
        SmartToast.showShort(R.string.encrypt_password_open_success);
    }

    public void stopAppUpdate() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("CharSwitch", "66BB00C00000F700020108EB");
            ((ObservableSubscribeProxy) Injection.net().deviceController(this.mDevice.getDeviceId(), jSONObject.toString()).compose(RxUtils.io_main()).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSpUpgrade.this.lambda$stopAppUpdate$9((Disposable) obj);
                }
            }).doFinally(new ActSpUpgrade$$ExternalSyntheticLambda5(this)).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.upgrade.ActSpUpgrade$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ActSpUpgrade.this.lambda$stopAppUpdate$10(obj);
                }
            }, new SmartErrorComsumer());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopAppUpdate$9(Disposable disposable) throws Exception {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.loading));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopAppUpdate$10(Object obj) throws Exception {
        setUpgradeView(1);
        SmartToast.showShort(R.string.encrypt_password_open_success);
    }

    public void refresh(boolean b2, int i) {
        if (this.cmdType == i) {
            setUpgradeView(b2 ? 2 : 3);
        }
    }
}