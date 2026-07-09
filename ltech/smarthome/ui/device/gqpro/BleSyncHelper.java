package com.ltech.smarthome.ui.device.gqpro;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import com.airoha.btdlib.core.AirohaLink;
import com.airoha.libfota.core.AirohaOtaMgr;
import com.airoha.libfota.core.OnAirohaOtaEventListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.LoadingProgressDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.productBle.BleSegmentSyncGqDataHelper;
import com.smart.product_agreement.productBle.BleSyncGqDataHelper;
import com.smart.product_agreement.upgrade.BaseUpgradeHelper;
import com.smart.product_agreement.utils.GZipUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class BleSyncHelper {
    protected static final int MSG_TIME_OUT = 3;
    protected static final int UPGRADE_CANCEL = 5;
    protected static final int UPGRADE_FAIL = 3;
    protected static final int UPGRADE_NEW = 1;
    protected static final int UPGRADE_SUCCESS = 2;
    protected static final int UPGRADING = 4;
    protected final BaseNormalActivity activity;
    private int curType;
    private Device device;
    protected List<BleSyncData> downloadData;
    protected int downloadType;
    protected int failNum;
    protected List<byte[]> frameData;
    protected AirohaLink mAirohaLink;
    protected AirohaOtaMgr mAirohaOtaMgr;
    private LoadingProgressDialog mDialog;
    protected final Handler mainHandler;
    protected int progress;
    protected BaseUpgradeHelper upgradeHelper;
    private int time = 180;
    private MutableLiveData<Integer> timeEvent = new MutableLiveData<>();
    protected int curData = 0;
    protected boolean isZip = true;
    BaseUpgradeHelper.IUpgradeCallback mIUpgradeCallback = new BaseUpgradeHelper.IUpgradeCallback() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.5
        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgradeSuccess(int nextNum) {
            BleSyncHelper.this.progress++;
            BleSyncHelper bleSyncHelper = BleSyncHelper.this;
            bleSyncHelper.failNum--;
            BleSyncHelper.this.sync();
        }

        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgradeFail() {
            if (BleSyncHelper.this.upgradeHelper instanceof BleSyncGqDataHelper) {
                BleSyncHelper.this.progress += ((BleSyncGqDataHelper) BleSyncHelper.this.upgradeHelper).getCurRemainingFrameCount();
            }
            BleSyncHelper.this.sync();
        }

        @Override // com.smart.product_agreement.upgrade.BaseUpgradeHelper.IUpgradeCallback
        public void onUpgrading(float p) {
            BleSyncHelper.this.progress++;
            if (BleSyncHelper.this.upgradeHelper instanceof BleSyncGqDataHelper) {
                ((BleSyncGqDataHelper) BleSyncHelper.this.upgradeHelper).setProgress(BleSyncHelper.this.progress);
            } else if (BleSyncHelper.this.upgradeHelper instanceof BleSegmentSyncGqDataHelper) {
                ((BleSegmentSyncGqDataHelper) BleSyncHelper.this.upgradeHelper).setProgress(BleSyncHelper.this.progress);
            }
        }
    };
    private final OnAirohaOtaEventListener mOnAirohaOtaEventListener = new OnAirohaOtaEventListener() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.6
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
            BleSyncHelper.this.mAirohaLink.cancelTimer();
            BleSyncHelper.this.sync();
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnHandleOtaDisabled(final byte errorCode) {
            BleSyncHelper.this.setUpgradeView(3);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnBinFileParseException() {
            BleSyncHelper.this.setUpgradeView(3);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnRetryFailed() {
            BleSyncHelper.this.setUpgradeView(3);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void OnStatusError(final byte errorCode, final String errorMsg) {
            BleSyncHelper.this.setUpgradeView(3);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onCheckFwVersionFailed() {
            BleSyncHelper.this.setUpgradeView(3);
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onDataCallback(final byte[] s) {
            if (BleSyncHelper.this.upgradeHelper != null) {
                LHomeLog.e(getClass(), "rec:" + StringUtils.byte2Str(s).trim());
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BleSyncHelper.this.upgradeHelper.recUpgradeCmd(StringUtils.byte2Str(s));
                    }
                });
            }
        }

        @Override // com.airoha.libfota.core.OnAirohaOtaEventListener
        public void onWaitNewOtaShow() {
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.6.2
                @Override // java.lang.Runnable
                public void run() {
                    BleSyncHelper.this.startBleConnect();
                }
            }, 2000L);
        }
    };
    protected final Handler handler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.8
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message msg) {
            if (msg.what != 3) {
                return false;
            }
            BleSyncHelper.this.mAirohaLink.disconnect();
            BleSyncHelper.this.setUpgradeView(3);
            return false;
        }
    });

    public BleSyncHelper(BaseNormalActivity activity, Device device, Handler mainHandler) {
        this.device = device;
        this.activity = activity;
        this.mainHandler = mainHandler;
        this.mAirohaLink = new AirohaLink(activity);
        this.mAirohaOtaMgr = new AirohaOtaMgr(this.mAirohaLink);
    }

    public void setDownloadData(List<BleSyncData> data, int type, boolean isZip) {
        this.downloadData = data;
        this.downloadType = type;
        this.isZip = isZip;
    }

    public void setDownloadData(List<BleSyncData> data, int type) {
        this.downloadData = data;
        this.downloadType = type;
        this.frameData = new ArrayList();
        for (BleSyncData bleSyncData : data) {
            if (bleSyncData.getData() != null) {
                this.frameData.add(GZipUtil.compress(bleSyncData.getData().getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

    public void startSync() {
        List<BleSyncData> list;
        setUpgradeView(0);
        LoadingProgressDialog callBack = LoadingProgressDialog.asDefault(this.activity.getString(R.string.app_str_gqpro_syning_tip), this.downloadType == 3).setMax(100).setCallBack(new LoadingProgressDialog.CallBack() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.1
            @Override // com.ltech.smarthome.view.dialog.LoadingProgressDialog.CallBack
            public void onCancel() {
                BleSyncHelper.this.setUpgradeView(5);
            }
        });
        this.mDialog = callBack;
        callBack.showDialog(this.activity);
        this.curData = 0;
        if (this.device == null || (list = this.downloadData) == null || list.isEmpty()) {
            setUpgradeView(3);
            this.mDialog.dismissDialog();
            return;
        }
        this.failNum = this.downloadData.size();
        this.progress = 1;
        if (this.mAirohaLink.isConnected()) {
            sync();
            setUpgradeView(4);
        } else {
            Injection.mesh().disconnect();
            ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.2
                @Override // java.lang.Runnable
                public void run() {
                    BleSyncHelper.this.startBleConnect();
                }
            }, 2000L);
        }
    }

    protected void sync() {
        this.handler.removeMessages(3);
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.3
            @Override // java.lang.Runnable
            public void run() {
                int i = 2;
                if (BleSyncHelper.this.curData >= BleSyncHelper.this.downloadData.size()) {
                    if (BleSyncHelper.this.failNum == 0) {
                        BleSyncHelper.this.setUpgradeView(2);
                        return;
                    } else {
                        BleSyncHelper.this.setUpgradeView(3);
                        return;
                    }
                }
                String data = BleSyncHelper.this.downloadData.get(BleSyncHelper.this.curData).getData();
                int type = BleSyncHelper.this.downloadData.get(BleSyncHelper.this.curData).getType();
                String name = BleSyncHelper.this.downloadData.get(BleSyncHelper.this.curData).getName();
                LHomeLog.e(getClass(), "当前同步:" + name);
                BleSyncHelper bleSyncHelper = BleSyncHelper.this;
                bleSyncHelper.curData = bleSyncHelper.curData + 1;
                if (BleSyncHelper.this.downloadData.size() == 1) {
                    i = 3;
                } else if (BleSyncHelper.this.curData == 1) {
                    i = 0;
                } else if (BleSyncHelper.this.curData == BleSyncHelper.this.downloadData.size()) {
                    i = 1;
                }
                BleSyncHelper bleSyncHelper2 = BleSyncHelper.this;
                bleSyncHelper2.upgradeHelper = UpgradeFactory.getUpgradeHelper(bleSyncHelper2.activity, 6, GZipUtil.compress(data.getBytes(StandardCharsets.UTF_8)), BleSyncHelper.this.mainHandler);
                if (BleSyncHelper.this.upgradeHelper instanceof BleSyncGqDataHelper) {
                    ((BleSyncGqDataHelper) BleSyncHelper.this.upgradeHelper).setDataFlag(i);
                    ((BleSyncGqDataHelper) BleSyncHelper.this.upgradeHelper).setProgress(BleSyncHelper.this.progress);
                    ((BleSyncGqDataHelper) BleSyncHelper.this.upgradeHelper).getAllTotalFrame(BleSyncHelper.this.frameData);
                    ((BleSyncGqDataHelper) BleSyncHelper.this.upgradeHelper).setHeader(type, BleSyncHelper.this.isZip, data.getBytes(StandardCharsets.UTF_8));
                    if (BleSyncHelper.this.upgradeHelper != null) {
                        ((BleSyncGqDataHelper) BleSyncHelper.this.upgradeHelper).setOnDataSendCallBack(new BleSyncGqDataHelper.OnDataSendCallBack() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.3.1
                            @Override // com.smart.product_agreement.productBle.BleSyncGqDataHelper.OnDataSendCallBack
                            public void sendUpgradeCmd(byte[] data2) {
                                if (BleSyncHelper.this.mAirohaOtaMgr == null || data2 == null) {
                                    return;
                                }
                                BleSyncHelper.this.mAirohaOtaMgr.writeAtCharc(data2);
                                LHomeLog.e(getClass(), "send:" + StringUtils.byte2Str(data2).trim());
                            }

                            @Override // com.smart.product_agreement.productBle.BleSyncGqDataHelper.OnDataSendCallBack
                            public void realProgress(int p) {
                                BleSyncHelper.this.setProgress(p);
                            }
                        });
                        BleSyncHelper.this.upgradeHelper.setUpgradeCallback(BleSyncHelper.this.mIUpgradeCallback);
                        BleSyncHelper.this.upgradeHelper.reset();
                        BleSyncHelper.this.upgradeHelper.startUpgrade();
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBleConnect() {
        this.mAirohaOtaMgr.setListener(this.mOnAirohaOtaEventListener);
        this.mAirohaLink.connect(this.device.getWifiMac().replaceAll("..(?!$)", "$0:"));
        this.handler.removeMessages(3);
        this.handler.sendEmptyMessageDelayed(3, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
    }

    public void delete(int cmd) {
        BaseNormalActivity baseNormalActivity = this.activity;
        baseNormalActivity.showLoadingDialog(baseNormalActivity.getString(R.string.app_str_gqpro_syning_tip));
        CmdAssistant.getSyncDataAssistant(this.device, new int[0]).syncDeviceData(this.activity, cmd, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.4
            @Override // com.ltech.smarthome.base.IAction
            public void act(Boolean aBoolean) {
                BleSyncHelper.this.activity.dismissLoadingDialog();
                if (aBoolean.booleanValue()) {
                    SmartToast.showCenterShort(BleSyncHelper.this.activity.getString(R.string.sync_success));
                } else {
                    SmartToast.showCenterShort(BleSyncHelper.this.activity.getString(R.string.app_str_gqpro_sync_failed));
                }
            }
        });
    }

    /* renamed from: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper$7, reason: invalid class name */
    class AnonymousClass7 implements Runnable {
        final /* synthetic */ int val$type;

        AnonymousClass7(final int val$type) {
            this.val$type = val$type;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BleSyncHelper.this.curType != this.val$type) {
                if (BleSyncHelper.this.curType == 5 && this.val$type == 3) {
                    return;
                }
                BleSyncHelper.this.curType = this.val$type;
                int i = this.val$type;
                if (i == 2) {
                    BleSyncHelper.this.activity.setResult(-1);
                    BleSyncHelper.this.dismissDialog();
                    BleSyncHelper.this.activity.dismissLoadingDialog();
                    SmartToast.showCenterShort(BleSyncHelper.this.activity.getString(R.string.sync_success));
                    return;
                }
                if (i != 3) {
                    if (i != 5) {
                        return;
                    }
                    BleSyncHelper.this.activity.dismissLoadingDialog();
                    MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), BleSyncHelper.this.activity.getString(R.string.app_str_gqpro_cancel_sync_title), BleSyncHelper.this.activity.getString(R.string.app_str_gqpro_cancel_sync_tip), BleSyncHelper.this.activity.getString(R.string.app_str_gqpro_sync_continue), BleSyncHelper.this.activity.getString(R.string.app_str_gqpro_cancel_sync)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.7.3
                        @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                        public boolean onClick(BaseDialog baseDialog, View v) {
                            BleSyncHelper.this.setUpgradeView(4);
                            return false;
                        }
                    }).setCancelButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.7.2
                        @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                        public boolean onClick(BaseDialog baseDialog, View v) {
                            BleSyncHelper.this.dismissDialog();
                            BleSyncHelper.this.mAirohaLink.disconnect();
                            if (!BleSyncHelper.this.isBlePermissionOk()) {
                                return false;
                            }
                            Injection.mesh().checkNearbyDevice(BleSyncHelper.this.activity);
                            return false;
                        }
                    }).setCancelable(false).show();
                    return;
                }
                final String string = BleSyncHelper.this.activity.getString(R.string.device);
                if (BleSyncHelper.this.downloadType == 2) {
                    string = BleSyncHelper.this.activity.getString(R.string.scenes);
                } else if (BleSyncHelper.this.downloadType == 3) {
                    string = BleSyncHelper.this.activity.getString(R.string.theme);
                } else if (BleSyncHelper.this.downloadType == 4) {
                    string = BleSyncHelper.this.activity.getString(R.string.device) + "/" + BleSyncHelper.this.activity.getString(R.string.scenes);
                }
                BleSyncHelper.this.dismissDialog();
                BleSyncHelper.this.activity.dismissLoadingDialog();
                ThreadUtils.getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), BleSyncHelper.this.activity.getString(R.string.local_scene_sync_fail), String.format(BleSyncHelper.this.activity.getString(R.string.app_str_gqpro_sync_failed), Integer.valueOf(BleSyncHelper.this.failNum), string), BleSyncHelper.this.activity.getString(R.string.app_str_gqpro_sync_continue), BleSyncHelper.this.activity.getString(R.string.cancel)).setOkButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.7.1.2
                            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                BleSyncHelper.this.startSync();
                                return false;
                            }
                        }).setCancelButton(new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.7.1.1
                            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                BleSyncHelper.this.mAirohaLink.disconnect();
                                if (!BleSyncHelper.this.isBlePermissionOk()) {
                                    return false;
                                }
                                Injection.mesh().checkNearbyDevice(BleSyncHelper.this.activity);
                                return false;
                            }
                        }).setCancelable(false).show();
                    }
                }, 1000L);
            }
        }
    }

    protected void setUpgradeView(int type) {
        ThreadUtils.runOnUiThread(new AnonymousClass7(type));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog() {
        LoadingProgressDialog loadingProgressDialog = this.mDialog;
        if (loadingProgressDialog == null || loadingProgressDialog.isStateSaved()) {
            return;
        }
        this.mDialog.dismissDialog();
    }

    protected boolean isBlePermissionOk() {
        return (Build.VERSION.SDK_INT < 23 || Build.VERSION.SDK_INT >= 31) ? Build.VERSION.SDK_INT < 31 || AndPermission.hasPermissions(ActivityUtils.getTopActivity(), "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT") : AndPermission.hasPermissions(ActivityUtils.getTopActivity(), Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION);
    }

    public static class BleSyncData {
        private byte[] byteData;
        private String data;
        private String name;
        private int type;

        public BleSyncData(String data, int type, String name) {
            this.data = data;
            this.type = type;
            this.name = name;
        }

        public BleSyncData(byte[] byteData, int type, String name) {
            this.type = type;
            this.byteData = byteData;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public byte[] getByteData() {
            return this.byteData;
        }

        public String getData() {
            return this.data;
        }

        public int getType() {
            return this.type;
        }
    }

    public void disconnect() {
        this.mAirohaLink.disconnect();
        if (isBlePermissionOk()) {
            Injection.mesh().checkNearbyDevice(this.activity);
        }
    }

    public void setProgress(final int p) {
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSyncHelper.9
            @Override // java.lang.Runnable
            public void run() {
                if (BleSyncHelper.this.mDialog != null) {
                    BleSyncHelper.this.mDialog.setProgress(p);
                }
            }
        });
    }
}