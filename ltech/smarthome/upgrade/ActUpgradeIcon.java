package com.ltech.smarthome.upgrade;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.util.ActivityUtils;
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
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.ScreenIconUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.smart.message.ResponseMsg;
import com.smart.message.SmartUtils;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.productBle.MeshUpgradeThemeHelper;
import com.smart.product_agreement.upgrade.BaseUpgradeHelper;
import com.smart.product_agreement.utils.GZipUtil;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActUpgradeIcon extends BaseNormalActivity<ActUpgradeBinding> {
    private static final int MSG_WAIT_SEQ = 1;
    private static final int MSG_WAIT_SEQ_TIME_OUT = 2;
    private static final int UPGRADE_FAIL = 3;
    private static final int UPGRADE_NEW = 1;
    private static final int UPGRADE_SUCCESS = 2;
    private static final int UPGRADING = 4;
    private int curType;
    private String currentVersion;
    private int displayType;
    private byte[] iconData;
    private String latestVersion = "";
    private Device mDevice;
    private UpdateHandler mUpdateHandler;
    private int revFailTime;
    private int startIndex;
    private int totalNum;
    private BaseUpgradeHelper upgradeHelper;

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
            queryIconVersion();
        }
        ((ActUpgradeBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.upgrade.ActUpgradeIcon$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActUpgradeIcon.this.lambda$initView$0((View) obj);
            }
        }));
        getWindow().addFlags(128);
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
        queryIconVersion();
    }

    private void queryIconVersion() {
        showLoading();
        if (this.mUpdateHandler == null) {
            this.mUpdateHandler = new UpdateHandler();
        }
        this.mUpdateHandler.removeMessages(1);
        this.mUpdateHandler.removeMessages(2);
        this.mUpdateHandler.sendEmptyMessage(1);
        this.mUpdateHandler.sendEmptyMessageDelayed(2, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryDeviceIconVersion() {
        CmdAssistant.getQueryCmdAssistant(this.mDevice, new int[0]).queryScreenPanelIconStart(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.upgrade.ActUpgradeIcon$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActUpgradeIcon.this.lambda$queryDeviceIconVersion$1((ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceIconVersion$1(ResponseMsg responseMsg) {
        LHomeLog.i(getClass(), "getQueryCmdAssistant=" + responseMsg);
        if (responseMsg == null) {
            if (this.latestVersion.equals("")) {
                showError();
                return;
            }
            return;
        }
        if (Integer.parseInt(responseMsg.getDeviceFlag(), 16) != ((BleParam) this.mDevice.getParam(BleParam.class)).getUnicastAddress()) {
            queryDeviceIconVersion();
            return;
        }
        if (responseMsg.getResData().length() < 20) {
            showError();
        } else {
            this.startIndex = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16);
            this.currentVersion = String.format(Locale.US, "V%.1f", Float.valueOf(ScreenIconUtils.getVersionNum(this.startIndex)));
        }
        if (this.currentVersion == null) {
            showError();
            return;
        }
        showContent();
        this.latestVersion = ScreenIconUtils.LATEST_VERSION;
        if (this.currentVersion.compareTo(ScreenIconUtils.LATEST_VERSION) >= 0) {
            setUpgradeView(2);
        } else {
            setUpgradeView(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpgradeView(int type) {
        this.curType = type;
        if (this.mViewBinding == 0) {
            return;
        }
        if (type == 1) {
            setBackImage(R.mipmap.icon_back);
            ((ActUpgradeBinding) this.mViewBinding).progressBar.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvProgress.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_upgrade_new);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setText(String.format("%s%s", getString(R.string.new_icon_version), this.latestVersion));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setText(String.format("%s%s", getString(R.string.cur_icon_version), this.currentVersion));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setBackgroundResource(R.drawable.shape_red_bt);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setTextColor(ContextCompat.getColor(this, R.color.white));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setText(getString(R.string.upgrade_now));
            return;
        }
        if (type == 2) {
            setBackImage(R.mipmap.icon_back);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).progressBar.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvProgress.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_success);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setText(getString(R.string.latest_icon_version));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setText(String.format("%s%s", getString(R.string.cur_icon_version), this.latestVersion));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setVisibility(8);
            return;
        }
        if (type == 3) {
            setBackImage(R.mipmap.icon_back);
            ((ActUpgradeBinding) this.mViewBinding).progressBar.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvProgress.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setImageResource(R.mipmap.icon_fail);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setText(getString(R.string.upgrade_fail));
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setVisibility(8);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setVisibility(0);
            ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setText(getString(R.string.upgrading_fail_tip));
            return;
        }
        if (type != 4) {
            return;
        }
        setBackImage(0);
        ((ActUpgradeBinding) this.mViewBinding).progressBar.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).tvProgress.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%.2f%%", Float.valueOf(0.0f)));
        ((ActUpgradeBinding) this.mViewBinding).progressBar.setup(R.color.color_text_red);
        ((ActUpgradeBinding) this.mViewBinding).ivUpgrade.setVisibility(4);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip1.setText(getString(R.string.upgrading_tip));
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip2.setVisibility(8);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgradeTip3.setText(getString(R.string.upgrading_attention_tip));
        ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setVisibility(0);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setBackgroundResource(R.drawable.shape_light_gray_bt);
        ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setTextColor(ContextCompat.getColor(this, R.color.color_text_light_black));
        ((ActUpgradeBinding) this.mViewBinding).tvUpgrade.setText(getString(R.string.wait));
    }

    private void startUpgrade() {
        this.displayType = 1;
        byte[] g4SmallIconData = isG4(this.mDevice) ? ScreenIconUtils.getG4SmallIconData(this.startIndex) : ScreenIconUtils.getSmallIconData(this.startIndex);
        this.iconData = g4SmallIconData;
        this.totalNum = 152 - this.startIndex;
        if (g4SmallIconData != null) {
            setUpgradeView(4);
            setIconData(this.startIndex);
        } else {
            setUpgradeView(3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIconData(int iconIndex) {
        if (this.mDevice.getProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4) || this.mDevice.getProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4_PRO)) {
            setNewIconData(iconIndex);
        } else {
            CmdAssistant.getDeviceAssistant(this.mDevice, new int[0]).setIconData(this, this.displayType, iconIndex, this.iconData, new IAction() { // from class: com.ltech.smarthome.upgrade.ActUpgradeIcon$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ActUpgradeIcon.this.lambda$setIconData$2((ResponseMsg) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIconData$2(ResponseMsg responseMsg) {
        if (responseMsg == null || responseMsg.getResData().length() < 20) {
            int i = this.revFailTime + 1;
            this.revFailTime = i;
            if (i > 2) {
                setUpgradeView(3);
                return;
            }
            return;
        }
        if (Integer.parseInt(responseMsg.getDeviceFlag(), 16) != ((BleParam) this.mDevice.getParam(BleParam.class)).getUnicastAddress()) {
            return;
        }
        int parseInt = Integer.parseInt(responseMsg.getResData().substring(16, 20), 16);
        this.revFailTime = 0;
        if (this.displayType == 1) {
            this.displayType = 2;
            this.iconData = isG4(this.mDevice) ? ScreenIconUtils.getG4BigIconData(this.startIndex) : ScreenIconUtils.getBigIconData(this.startIndex);
            setIconData(this.startIndex);
            return;
        }
        float f = 1.0f - ((152 - parseInt) / this.totalNum);
        if (this.mViewBinding != 0) {
            ((ActUpgradeBinding) this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%.2f%%", Float.valueOf(f * 100.0f)));
        }
        if (parseInt < 152) {
            this.startIndex = parseInt;
            this.displayType = 1;
            this.iconData = isG4(this.mDevice) ? ScreenIconUtils.getG4SmallIconData(this.startIndex) : ScreenIconUtils.getSmallIconData(this.startIndex);
            setIconData(this.startIndex);
            return;
        }
        setUpgradeView(2);
        EventBusUtils.post(new LiveBusHelper(7));
    }

    private void setNewIconData(int iconIndex) {
        BaseUpgradeHelper upgradeHelper = UpgradeFactory.getUpgradeHelper(this, 5, GZipUtil.compress(this.iconData), getMainHandler());
        this.upgradeHelper = upgradeHelper;
        MeshUpgradeThemeHelper meshUpgradeThemeHelper = (MeshUpgradeThemeHelper) upgradeHelper;
        int i = this.displayType;
        meshUpgradeThemeHelper.setHeader(1, iconIndex, i == 2 ? 36 : 28, i == 2 ? 36 : 28, 8, true);
        CtrlPackage ctrlPackage = (CtrlPackage) SmartUtils.getICtrlConverter().convert(this.mDevice);
        if (ProductRepository.isBLeDevice(this.mDevice.getProductId())) {
            ctrlPackage.setAddress(((BleParam) this.mDevice.getParam(BleParam.class)).getUnicastAddress());
        }
        this.upgradeHelper.setCtrlPackage(ctrlPackage);
        this.upgradeHelper.setUpgradeCallback(new BaseUpgradeHelper.IUpgradeCallback() { // from class: com.ltech.smarthome.upgrade.ActUpgradeIcon.1
            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgrading(float progress) {
            }

            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgradeSuccess(int nextNum) {
                if (ActUpgradeIcon.this.displayType == 1) {
                    ActUpgradeIcon.this.displayType = 2;
                    ActUpgradeIcon actUpgradeIcon = ActUpgradeIcon.this;
                    actUpgradeIcon.iconData = actUpgradeIcon.isG4(actUpgradeIcon.mDevice) ? ScreenIconUtils.getG4BigIconData(ActUpgradeIcon.this.startIndex) : ScreenIconUtils.getBigIconData(ActUpgradeIcon.this.startIndex);
                    ActUpgradeIcon actUpgradeIcon2 = ActUpgradeIcon.this;
                    actUpgradeIcon2.setIconData(actUpgradeIcon2.startIndex);
                    return;
                }
                float g4IconLength = 1.0f - ((ScreenIconUtils.getG4IconLength() - nextNum) / ActUpgradeIcon.this.totalNum);
                if (ActUpgradeIcon.this.mViewBinding != null) {
                    ((ActUpgradeBinding) ActUpgradeIcon.this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%.2f%%", Float.valueOf(g4IconLength * 100.0f)));
                }
                if (nextNum < ScreenIconUtils.getG4IconLength()) {
                    ActUpgradeIcon.this.startIndex = nextNum;
                    ActUpgradeIcon.this.displayType = 1;
                    ActUpgradeIcon actUpgradeIcon3 = ActUpgradeIcon.this;
                    actUpgradeIcon3.iconData = actUpgradeIcon3.isG4(actUpgradeIcon3.mDevice) ? ScreenIconUtils.getG4SmallIconData(ActUpgradeIcon.this.startIndex) : ScreenIconUtils.getSmallIconData(ActUpgradeIcon.this.startIndex);
                    ActUpgradeIcon actUpgradeIcon4 = ActUpgradeIcon.this;
                    actUpgradeIcon4.setIconData(actUpgradeIcon4.startIndex);
                    return;
                }
                ActUpgradeIcon.this.setUpgradeView(2);
                EventBusUtils.post(new LiveBusHelper(7));
            }

            @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
            public void onUpgradeFail() {
                LHomeLog.i(getClass(), "**********onUpgradeFail**********");
                ActUpgradeIcon.this.setUpgradeView(3);
            }
        });
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.upgrade.ActUpgradeIcon.2
            @Override // java.lang.Runnable
            public void run() {
                ActUpgradeIcon.this.upgradeHelper.reset();
                if (ActUpgradeIcon.this.upgradeHelper.startUpgrade()) {
                    ProductRepository.isBLeDevice(ActUpgradeIcon.this.mDevice.getProductId());
                }
            }
        }, 200L);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.curType == 4) {
            return;
        }
        Injection.mesh().setUpgradeStart(false);
        super.onBackPressed();
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
                    ActUpgradeIcon.this.queryDeviceIconVersion();
                    return;
                } else {
                    sendEmptyMessageDelayed(1, 1000L);
                    return;
                }
            }
            if (msg.what == 2) {
                ActUpgradeIcon.this.showError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isG4(Device device) {
        return device.getProductId().equals(ProductId.ID_SMART_PANEL_G4) || device.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO);
    }
}