package com.ltech.smarthome.ui.device.gqpro;

import android.os.Handler;
import com.blankj.utilcode.util.ThreadUtils;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.ui.device.gqpro.BleSyncHelper;
import com.ltech.smarthome.upgrade.UpgradeFactory;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.productBle.BleSegmentSyncGqDataHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class BleSegmentSyncHelper extends BleSyncHelper {
    public BleSegmentSyncHelper(BaseNormalActivity activity, Device device, Handler mainHandler) {
        super(activity, device, mainHandler);
    }

    @Override // com.ltech.smarthome.ui.device.gqpro.BleSyncHelper
    public void setDownloadData(List<BleSyncHelper.BleSyncData> data, int type) {
        this.downloadData = data;
        this.downloadType = type;
        this.frameData = new ArrayList();
        for (BleSyncHelper.BleSyncData bleSyncData : data) {
            if (bleSyncData.getByteData() != null) {
                this.frameData.add(bleSyncData.getByteData());
            }
        }
    }

    @Override // com.ltech.smarthome.ui.device.gqpro.BleSyncHelper
    protected void sync() {
        this.handler.removeMessages(3);
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSegmentSyncHelper.1
            @Override // java.lang.Runnable
            public void run() {
                if (BleSegmentSyncHelper.this.curData >= BleSegmentSyncHelper.this.downloadData.size()) {
                    if (BleSegmentSyncHelper.this.failNum == 0) {
                        BleSegmentSyncHelper.this.setUpgradeView(2);
                        return;
                    } else {
                        BleSegmentSyncHelper.this.setUpgradeView(3);
                        return;
                    }
                }
                byte[] byteData = BleSegmentSyncHelper.this.downloadData.get(BleSegmentSyncHelper.this.curData).getByteData();
                int type = BleSegmentSyncHelper.this.downloadData.get(BleSegmentSyncHelper.this.curData).getType();
                BleSegmentSyncHelper.this.curData++;
                BleSegmentSyncHelper bleSegmentSyncHelper = BleSegmentSyncHelper.this;
                bleSegmentSyncHelper.upgradeHelper = UpgradeFactory.getUpgradeHelper(bleSegmentSyncHelper.activity, 8, byteData, BleSegmentSyncHelper.this.mainHandler, BleSegmentSyncHelper.this.downloadData.size(), BleSegmentSyncHelper.this.curData - 1);
                if (BleSegmentSyncHelper.this.upgradeHelper instanceof BleSegmentSyncGqDataHelper) {
                    ((BleSegmentSyncGqDataHelper) BleSegmentSyncHelper.this.upgradeHelper).setProgress(BleSegmentSyncHelper.this.progress);
                    ((BleSegmentSyncGqDataHelper) BleSegmentSyncHelper.this.upgradeHelper).getAllTotalFrame(BleSegmentSyncHelper.this.frameData);
                    ((BleSegmentSyncGqDataHelper) BleSegmentSyncHelper.this.upgradeHelper).setHeader(type);
                    if (BleSegmentSyncHelper.this.upgradeHelper != null) {
                        ((BleSegmentSyncGqDataHelper) BleSegmentSyncHelper.this.upgradeHelper).setOnDataSendCallBack(new BleSegmentSyncGqDataHelper.OnDataSendCallBack() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSegmentSyncHelper.1.1
                            @Override // com.smart.product_agreement.productBle.BleSegmentSyncGqDataHelper.OnDataSendCallBack
                            public void sendUpgradeCmd(byte[] data) {
                                if (BleSegmentSyncHelper.this.mAirohaOtaMgr == null || data == null) {
                                    return;
                                }
                                BleSegmentSyncHelper.this.mAirohaOtaMgr.writeAtCharc(data);
                                LHomeLog.e(getClass(), "send:" + StringUtils.byte2Str(data).trim());
                            }

                            @Override // com.smart.product_agreement.productBle.BleSegmentSyncGqDataHelper.OnDataSendCallBack
                            public void realProgress(int p) {
                                BleSegmentSyncHelper.this.setProgress(p);
                            }
                        });
                        BleSegmentSyncHelper.this.upgradeHelper.setUpgradeCallback(BleSegmentSyncHelper.this.mIUpgradeCallback);
                        BleSegmentSyncHelper.this.upgradeHelper.reset(new Runnable() { // from class: com.ltech.smarthome.ui.device.gqpro.BleSegmentSyncHelper.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                BleSegmentSyncHelper.this.upgradeHelper.startUpgrade();
                            }
                        });
                    }
                }
            }
        });
    }
}