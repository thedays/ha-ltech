package com.ltech.smarthome.ui.device.screenpanel;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airoha.btdlib.core.AirohaLink;
import com.airoha.libfota.core.AirohaOtaMgr;
import com.airoha.libfota.core.OnAirohaOtaEventListener;
import com.akuvox.mobile.libcommon.defined.MediaDefined;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.databinding.ActThemeDownloadBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.DateUtils;
import com.ltech.smarthome.view.dialog.TipDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.productBle.BleUpgradeThemeHelper;
import com.smart.product_agreement.upgrade.BaseUpgradeHelper;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActSmartPanelDownloadTheme extends VMActivity<ActThemeDownloadBinding, ActSmartPanelThemeVM> {
    private static final int MSG_TIME_OUT = 3;
    private static final int UPGRADE_FAIL = 3;
    private static final int UPGRADE_NEW = 1;
    private static final int UPGRADE_NEXT = 5;
    private static final int UPGRADE_SUCCESS = 2;
    private static final int UPGRADING = 4;
    private int curType;
    private AirohaLink mAirohaLink;
    private AirohaOtaMgr mAirohaOtaMgr;
    private int maxCount;
    private int maxStep;
    private BaseUpgradeHelper upgradeHelper;
    private int time = 180;
    Runnable runnable = new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.1
        @Override // java.lang.Runnable
        public void run() {
            ActSmartPanelDownloadTheme.this.time--;
            if (ActSmartPanelDownloadTheme.this.time > 0) {
                ((ActThemeDownloadBinding) ActSmartPanelDownloadTheme.this.mViewBinding).tvDownloadTitle.setText(String.format(ActSmartPanelDownloadTheme.this.getString(R.string.app_str_theme_download_title), DateUtils.getStrTime(Integer.valueOf(ActSmartPanelDownloadTheme.this.time))));
                ActSmartPanelDownloadTheme.this.handler.postDelayed(ActSmartPanelDownloadTheme.this.runnable, 1000L);
            }
        }
    };
    BaseUpgradeHelper.IUpgradeCallback mIUpgradeCallback = new BaseUpgradeHelper.IUpgradeCallback() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.7
        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgradeSuccess(int nextNum) {
            if (!((ActSmartPanelThemeVM) ActSmartPanelDownloadTheme.this.mViewModel).groupControl) {
                ((ActThemeDownloadBinding) ActSmartPanelDownloadTheme.this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%s%%", 99));
                ((ActThemeDownloadBinding) ActSmartPanelDownloadTheme.this.mViewBinding).superview.setSelect(355);
            }
            ActSmartPanelDownloadTheme.this.setResult(-1);
            ActSmartPanelDownloadTheme.this.mAirohaLink.disconnect();
            if (!((ActSmartPanelThemeVM) ActSmartPanelDownloadTheme.this.mViewModel).groupControl) {
                if (ActSmartPanelDownloadTheme.this.isBlePermissionOk()) {
                    Injection.mesh().checkNearbyDevice(ActSmartPanelDownloadTheme.this.activity);
                }
                ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ActSmartPanelDownloadTheme.this.setUpgradeView(2);
                    }
                }, 1500L);
                return;
            }
            ActSmartPanelDownloadTheme.this.setUpgradeView(5);
        }

        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgradeFail() {
            ActSmartPanelDownloadTheme.this.setUpgradeView(3);
        }

        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgrading(float progress) {
            if (ActSmartPanelDownloadTheme.this.mViewBinding == null || ((ActSmartPanelThemeVM) ActSmartPanelDownloadTheme.this.mViewModel).groupControl) {
                return;
            }
            if (Math.ceil(progress) > 100.0d) {
                progress = 100.0f;
            }
            ((ActThemeDownloadBinding) ActSmartPanelDownloadTheme.this.mViewBinding).superview.setShowSelect(false);
            ((ActThemeDownloadBinding) ActSmartPanelDownloadTheme.this.mViewBinding).superview.setSelect((int) ((progress * 360.0f) / 100.0f));
            ((ActThemeDownloadBinding) ActSmartPanelDownloadTheme.this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%s%%", Integer.valueOf((int) progress)));
        }
    };
    private final OnAirohaOtaEventListener mOnAirohaOtaEventListener = new OnAirohaOtaEventListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.8
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

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnRequestMtuChangeStatus(boolean isAccepted) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public synchronized void OnNewMtu(final int mtu) {
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnGattConnected() {
            ActSmartPanelDownloadTheme.this.handler.removeMessages(3);
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.8.1
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelDownloadTheme.this.dismissLoadingDialog();
                    ActSmartPanelDownloadTheme.this.handler.post(ActSmartPanelDownloadTheme.this.runnable);
                    ActSmartPanelDownloadTheme.this.upgradeHelper.reset();
                    if (!ActSmartPanelDownloadTheme.this.upgradeHelper.startUpgrade()) {
                        ActSmartPanelDownloadTheme.this.setUpgradeView(3);
                    } else {
                        ActSmartPanelDownloadTheme.this.mAirohaLink.cancelTimer();
                    }
                }
            }, 200L);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleOtaDisabled(final byte errorCode) {
            ActSmartPanelDownloadTheme.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.8.2
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelDownloadTheme.this.mAirohaLink.disconnect();
                    ActSmartPanelDownloadTheme.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBinFileParseException() {
            ActSmartPanelDownloadTheme.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.8.3
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelDownloadTheme.this.mAirohaLink.disconnect();
                    ActSmartPanelDownloadTheme.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnRetryFailed() {
            ActSmartPanelDownloadTheme.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.8.4
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelDownloadTheme.this.mAirohaLink.disconnect();
                    ActSmartPanelDownloadTheme.this.setUpgradeView(3);
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnStatusError(final byte errorCode, final String errorMsg) {
            ActSmartPanelDownloadTheme.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.8.5
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelDownloadTheme.this.setUpgradeView(3);
                    ActSmartPanelDownloadTheme.this.mAirohaLink.disconnect();
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onCheckFwVersionFailed() {
            ActSmartPanelDownloadTheme.this.mAirohaLink.disconnect();
            ActSmartPanelDownloadTheme.this.setUpgradeView(3);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onDataCallback(final byte[] s) {
            LHomeLog.e(getClass(), "rec:" + StringUtils.byte2Str(s).trim());
            ActSmartPanelDownloadTheme.this.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.8.6
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelDownloadTheme.this.dismissLoadingDialog();
                    if (ActSmartPanelDownloadTheme.this.upgradeHelper instanceof BleUpgradeThemeHelper) {
                        ((BleUpgradeThemeHelper) ActSmartPanelDownloadTheme.this.upgradeHelper).recUpgradeCmd(StringUtils.byte2Str(s));
                    }
                }
            });
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onWaitNewOtaShow() {
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.8.7
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelDownloadTheme.this.startBleConnect();
                }
            }, 2000L);
        }
    };
    private final Handler handler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.9
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (msg.what != 3) {
                return false;
            }
            ActSmartPanelDownloadTheme.this.setUpgradeView(3);
            return false;
        }
    });

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_theme_download;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.app_str_theme_downloading_title));
        setBackImage(R.mipmap.icon_back);
        ((ActSmartPanelThemeVM) this.mViewModel).curScreen = getIntent().getIntExtra(Constants.SCENE_NUM, 0);
        ((ActSmartPanelThemeVM) this.mViewModel).num = getIntent().getIntExtra(Constants.SELECT_THEME, 0);
        ((ActSmartPanelThemeVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActSmartPanelThemeVM) this.mViewModel).groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        if (((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
            Group groupById = Injection.repo().group().getGroupById(((ActSmartPanelThemeVM) this.mViewModel).controlId);
            ((ActSmartPanelThemeVM) this.mViewModel).controlObject.setValue(groupById);
            if (!groupById.getDeviceIds().isEmpty()) {
                for (Device device : Injection.repo().device().getDevicesByIds(groupById.getDeviceIds())) {
                    ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.add(new ActSmartPanelThemeVM.NeedDownloadDevice(device, device.getWifiMac().replaceAll("..(?!$)", "$0:")));
                }
                this.maxStep = MediaDefined.DEGRESS_360 / ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.size();
                this.maxCount = ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.size();
            }
            setGroupProgress();
        } else {
            Device deviceById = Injection.repo().device().getDeviceById(((ActSmartPanelThemeVM) this.mViewModel).controlId);
            ((ActSmartPanelThemeVM) this.mViewModel).controlObject.setValue(deviceById);
            ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.add(new ActSmartPanelThemeVM.NeedDownloadDevice(deviceById, deviceById.getWifiMac().replaceAll("..(?!$)", "$0:")));
        }
        ((ActThemeDownloadBinding) this.mViewBinding).iv.setImageResource(((ActSmartPanelThemeVM) this.mViewModel).themePic[getIntent().getIntExtra(Constants.SELECT_THEME, 0)]);
        ((ActThemeDownloadBinding) this.mViewBinding).tvName.setText(((ActSmartPanelThemeVM) this.mViewModel).themeNames[getIntent().getIntExtra(Constants.SELECT_THEME, 0)]);
        ((ActThemeDownloadBinding) this.mViewBinding).tvDownloadTitle.setText(String.format(getString(R.string.app_str_theme_download_title), DateUtils.getStrTime(Integer.valueOf(this.time * ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.size()))));
        this.mAirohaLink = new AirohaLink(this);
        this.mAirohaOtaMgr = new AirohaOtaMgr(this.mAirohaLink);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGroupProgress() {
        ((ActThemeDownloadBinding) this.mViewBinding).superview.setShowSelect(false);
        ((ActThemeDownloadBinding) this.mViewBinding).superview.setSelect(360 - (this.maxStep * ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.size()));
        ((ActThemeDownloadBinding) this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%s/%s", Integer.valueOf(this.maxCount - ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.size()), Integer.valueOf(this.maxCount)));
    }

    @Override // com.ltech.smarthome.base.VMActivity, com.ltech.smarthome.base.BaseNormalActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacks(this.runnable);
        AirohaOtaMgr airohaOtaMgr = this.mAirohaOtaMgr;
        if (airohaOtaMgr != null) {
            airohaOtaMgr.close();
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        prepare();
        ready();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ready() {
        if (((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.2
                @Override // java.lang.Runnable
                public void run() {
                    ActSmartPanelDownloadTheme actSmartPanelDownloadTheme = ActSmartPanelDownloadTheme.this;
                    actSmartPanelDownloadTheme.showLoadingDialog(actSmartPanelDownloadTheme.getString(R.string.loading));
                }
            }, 500L);
            ((ActSmartPanelThemeVM) this.mViewModel).setGroupTheme(((ActSmartPanelThemeVM) this.mViewModel).num + 1, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.3
                @Override // com.ltech.smarthome.base.IAction
                public void act(Boolean aBoolean) {
                    if (aBoolean.booleanValue()) {
                        ActSmartPanelDownloadTheme.this.dismissLoadingDialog();
                        ActSmartPanelDownloadTheme.this.start();
                    } else {
                        ActSmartPanelDownloadTheme.this.setGroupProgress();
                    }
                }
            });
        } else {
            start();
        }
    }

    private void prepare() {
        BaseUpgradeHelper upgradeHelper = UpgradeFactory.getUpgradeHelper(this, 4, ((ActSmartPanelThemeVM) this.mViewModel).getFirmwareData(((ActSmartPanelThemeVM) this.mViewModel).num), getMainHandler());
        this.upgradeHelper = upgradeHelper;
        if (upgradeHelper instanceof BleUpgradeThemeHelper) {
            ((BleUpgradeThemeHelper) upgradeHelper).setHeader(2, ((ActSmartPanelThemeVM) this.mViewModel).num + 1, 440, 108, 16);
            BaseUpgradeHelper baseUpgradeHelper = this.upgradeHelper;
            if (baseUpgradeHelper != null) {
                ((BleUpgradeThemeHelper) baseUpgradeHelper).setOnDataSendCallBack(new BleUpgradeThemeHelper.OnDataSendCallBack() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.4
                    @Override // com.smart.product_agreement.productBle.BleUpgradeThemeHelper.OnDataSendCallBack
                    public void sendUpgradeCmd(byte[] data) {
                        if (ActSmartPanelDownloadTheme.this.mAirohaOtaMgr == null || data == null) {
                            return;
                        }
                        ActSmartPanelDownloadTheme.this.mAirohaOtaMgr.writeAtCharc(data);
                        LHomeLog.e(getClass(), "send:" + StringUtils.byte2Str(data).trim());
                    }
                });
                this.upgradeHelper.setUpgradeCallback(this.mIUpgradeCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start() {
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.5
            @Override // java.lang.Runnable
            public void run() {
                ActSmartPanelDownloadTheme actSmartPanelDownloadTheme = ActSmartPanelDownloadTheme.this;
                actSmartPanelDownloadTheme.showLoadingDialog(actSmartPanelDownloadTheme.getString(R.string.connecting));
            }
        }, 500L);
        Injection.mesh().disconnect();
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.6
            @Override // java.lang.Runnable
            public void run() {
                ActSmartPanelDownloadTheme.this.startBleConnect();
            }
        }, 2000L);
    }

    public static void saveByteArrayToFile(byte[] data, String filePath) {
        String btye2Str3 = StringUtils.btye2Str3(data);
        File file = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        fileOutputStream2.write(btye2Str3.getBytes());
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (IOException e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBleConnect() {
        if (!((ActSmartPanelThemeVM) this.mViewModel).connectAddress.isEmpty()) {
            this.mAirohaOtaMgr.setListener(this.mOnAirohaOtaEventListener);
            this.mAirohaLink.connect(((ActSmartPanelThemeVM) this.mViewModel).connectAddress.get(0).getAddress());
            setUpgradeView(4);
            this.handler.removeMessages(3);
            this.handler.sendEmptyMessageDelayed(3, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
            return;
        }
        setUpgradeView(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpgradeView(int type) {
        if (this.mViewBinding == 0) {
            return;
        }
        this.curType = type;
        if (type == 2) {
            dismissLoadingDialog();
            this.handler.removeCallbacks(this.runnable);
            ((ActThemeDownloadBinding) this.mViewBinding).tvStatus.setText(R.string.app_str_theme_download_finish);
            ((ActThemeDownloadBinding) this.mViewBinding).tvProgress.setVisibility(8);
            ((ActThemeDownloadBinding) this.mViewBinding).ivSuccess.setVisibility(0);
            ((ActThemeDownloadBinding) this.mViewBinding).tvDownloadTitle.setText(R.string.app_str_theme_download_finish);
            ((ActThemeDownloadBinding) this.mViewBinding).superview.setSelect(MediaDefined.DEGRESS_360);
            if (((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
                ((ActThemeDownloadBinding) this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%s/%s", Integer.valueOf(this.maxCount), Integer.valueOf(this.maxCount)));
                if (((ActSmartPanelThemeVM) this.mViewModel).failDevices.isEmpty()) {
                    return;
                }
                showFailDialog();
                return;
            }
            return;
        }
        if (type == 3) {
            this.mAirohaLink.disconnect();
            dismissLoadingDialog();
            this.handler.removeCallbacks(this.runnable);
            if (((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
                ((ActSmartPanelThemeVM) this.mViewModel).failDevices.add(new ActSmartPanelThemeVM.FailDevice(((ActSmartPanelThemeVM) this.mViewModel).connectAddress.get(0).getDevice(), ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.get(0).getAddress()));
                if (!((ActSmartPanelThemeVM) this.mViewModel).connectAddress.isEmpty()) {
                    ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.remove(0);
                }
                if (!((ActSmartPanelThemeVM) this.mViewModel).connectAddress.isEmpty()) {
                    ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.10
                        @Override // java.lang.Runnable
                        public void run() {
                            ActSmartPanelDownloadTheme.this.startBleConnect();
                        }
                    }, 2000L);
                    return;
                }
                ((ActThemeDownloadBinding) this.mViewBinding).superview.setSelect(MediaDefined.DEGRESS_360);
                ((ActThemeDownloadBinding) this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%s/%s", Integer.valueOf(this.maxCount), Integer.valueOf(this.maxCount)));
                showFailDialog();
                return;
            }
            if (isBlePermissionOk()) {
                Injection.mesh().checkNearbyDevice(this);
            }
            TipDialog.asDefault().setTitle(getString(R.string.app_str_theme_download_failed)).setTip(getString(R.string.app_str_theme_download_failed_tip)).setConfirmString(getString(R.string.ok)).setConfirm(new IAction<View>() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.11
                @Override // com.ltech.smarthome.base.IAction
                public void act(View view) {
                    ActSmartPanelDownloadTheme.this.finishActivity();
                }
            }).showDialog(this);
            return;
        }
        if (type == 4) {
            ((ActThemeDownloadBinding) this.mViewBinding).tvProgress.setVisibility(0);
            ((ActThemeDownloadBinding) this.mViewBinding).ivSuccess.setVisibility(8);
            if (((ActSmartPanelThemeVM) this.mViewModel).groupControl) {
                ((ActThemeDownloadBinding) this.mViewBinding).superview.setShowSelect(false);
                ((ActThemeDownloadBinding) this.mViewBinding).superview.setSelect(MediaDefined.DEGRESS_360 - (this.maxStep * ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.size()));
                ((ActThemeDownloadBinding) this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%s/%s", Integer.valueOf(this.maxCount - ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.size()), Integer.valueOf(this.maxCount)));
                return;
            }
            ((ActThemeDownloadBinding) this.mViewBinding).tvProgress.setText(String.format(Locale.US, "%s%%", 0));
            return;
        }
        if (type != 5) {
            return;
        }
        Injection.mesh().disconnect();
        if (!((ActSmartPanelThemeVM) this.mViewModel).connectAddress.isEmpty()) {
            ((ActSmartPanelThemeVM) this.mViewModel).connectAddress.remove(0);
        }
        if (((ActSmartPanelThemeVM) this.mViewModel).connectAddress.isEmpty()) {
            if (isBlePermissionOk()) {
                Injection.mesh().checkNearbyDevice(this);
            }
            setUpgradeView(2);
            return;
        }
        ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.12
            @Override // java.lang.Runnable
            public void run() {
                ActSmartPanelDownloadTheme.this.startBleConnect();
            }
        }, 2000L);
    }

    private void showFailDialog() {
        MessageDialog.show(this.activity, getString(R.string.app_str_theme_download_failed), getString(R.string.app_str_local_scene_device_offline)).setCustomView(getCustomView(((ActSmartPanelThemeVM) this.mViewModel).failDevices)).setCancelButton(getString(R.string.cancel)).setOkButton(getString(R.string.retry)).setCancelButton(new OnDialogButtonClickListener(this) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.14
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                return false;
            }
        }).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.13
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public boolean onClick(BaseDialog baseDialog, View v) {
                ((ActThemeDownloadBinding) ActSmartPanelDownloadTheme.this.mViewBinding).tvProgress.setVisibility(0);
                ((ActThemeDownloadBinding) ActSmartPanelDownloadTheme.this.mViewBinding).ivSuccess.setVisibility(8);
                ((ActSmartPanelThemeVM) ActSmartPanelDownloadTheme.this.mViewModel).connectAddress.clear();
                for (ActSmartPanelThemeVM.FailDevice failDevice : ((ActSmartPanelThemeVM) ActSmartPanelDownloadTheme.this.mViewModel).failDevices) {
                    ((ActSmartPanelThemeVM) ActSmartPanelDownloadTheme.this.mViewModel).connectAddress.add(new ActSmartPanelThemeVM.NeedDownloadDevice(failDevice.getDevice(), failDevice.getAddress()));
                }
                ActSmartPanelDownloadTheme actSmartPanelDownloadTheme = ActSmartPanelDownloadTheme.this;
                actSmartPanelDownloadTheme.maxCount = ((ActSmartPanelThemeVM) actSmartPanelDownloadTheme.mViewModel).connectAddress.size();
                ((ActSmartPanelThemeVM) ActSmartPanelDownloadTheme.this.mViewModel).failDevices.clear();
                ActSmartPanelDownloadTheme.this.setGroupProgress();
                ActSmartPanelDownloadTheme.this.ready();
                return false;
            }
        }).setCancelable(false);
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void back() {
        if (this.curType == 4) {
            this.upgradeHelper.setPause(true);
            MessageDialog.show(this, getString(R.string.tips), getString(R.string.app_str_theme_download_interrupt), getString(R.string.confirm), getString(R.string.cancel)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.16
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    ActSmartPanelDownloadTheme.this.mAirohaLink.disconnect();
                    if (ActSmartPanelDownloadTheme.this.isBlePermissionOk()) {
                        Injection.mesh().checkNearbyDevice(ActSmartPanelDownloadTheme.this.activity);
                    }
                    ActSmartPanelDownloadTheme.this.curType = 3;
                    ActSmartPanelDownloadTheme.this.back();
                    return false;
                }
            }).setCancelButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.15
                @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                public boolean onClick(BaseDialog baseDialog, View v) {
                    ActSmartPanelDownloadTheme.this.upgradeHelper.setPause(false);
                    return false;
                }
            });
        } else {
            super.back();
        }
    }

    private View getCustomView(List<ActSmartPanelThemeVM.FailDevice> devices) {
        View inflate = LayoutInflater.from(this.activity).inflate(R.layout.dialog_custom_list, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<ActSmartPanelThemeVM.FailDevice, BaseViewHolder>(this, R.layout.item_scene_no_action_tip, devices) { // from class: com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelDownloadTheme.17
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder helper, ActSmartPanelThemeVM.FailDevice item) {
                helper.setText(R.id.tv_room, item.getDevice().getFloorName() + " " + item.getDevice().getRoomName());
                helper.setText(R.id.tv_content, item.getDevice().getDeviceName());
            }
        });
        return inflate;
    }
}