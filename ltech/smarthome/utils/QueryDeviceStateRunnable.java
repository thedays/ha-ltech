package com.ltech.smarthome.utils;

import android.os.Looper;
import com.ltech.smarthome.model.bean.Device;
import com.smart.message.MessageManager;
import com.smart.message.SmartUtils;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.ISendResutCallback;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.extra.Emitter;

/* loaded from: classes4.dex */
public class QueryDeviceStateRunnable implements Runnable {
    public static final int EXECTE_SUM = 3;
    public static final int INTERVAL_TIME = 3000;
    BaseCmd cmd;
    BaseCtrlPackage ctrlPackage;
    public Device device;
    private ExecFinish execFinish;
    private boolean selectReceive;
    private boolean stop;
    UpdateDeviceState updateDeviceState;
    private int sleep = 1000;
    private int querySum = 0;

    public interface ExecFinish {
        void finish(QueryDeviceStateRunnable queryDeviceStateRunable);
    }

    public interface UpdateDeviceState {
        void updateDevice(Device device);
    }

    public QueryDeviceStateRunnable(Device device, int address, BaseCtrlPackage ctrlPackage, BaseCmd cmd) {
        this.device = device;
        this.ctrlPackage = ctrlPackage;
        this.cmd = cmd;
        MessageManager.getInstance().addDeviceQueryResult(address, new MessageManager.UpdateQuery() { // from class: com.ltech.smarthome.utils.QueryDeviceStateRunnable.1
            @Override // com.smart.message.MessageManager.UpdateQuery
            public void update(int uniAddress) {
                QueryDeviceStateRunnable.this.stop = true;
                QueryDeviceStateRunnable.this.selectReceive = true;
            }
        });
    }

    @Override // java.lang.Runnable
    public void run() {
        UpdateDeviceState updateDeviceState;
        while (!this.stop) {
            try {
                LHomeLog.i(getClass(), "thread.name=" + Thread.currentThread().getName() + "___sleep=" + this.sleep + "__querySum=" + this.querySum + "deviceId=" + this.device.getDeviceId());
                Thread.sleep((long) this.sleep);
                this.sleep = 3000;
                int i = this.querySum + 1;
                this.querySum = i;
                if (i >= 3) {
                    this.stop = true;
                }
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                SmartUtils.send(Emitter.MIX_BLE_IOT, this.ctrlPackage, this.cmd, new ISendResutCallback(this) { // from class: com.ltech.smarthome.utils.QueryDeviceStateRunnable.2
                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultSuccess(boolean isIot) {
                        LHomeLog.i(getClass(), "send ok");
                    }

                    @Override // com.smart.message.base.ISendResutCallback
                    public void onResultError() {
                        LHomeLog.i(getClass(), "send fail");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        ExecFinish execFinish = this.execFinish;
        if (execFinish != null) {
            execFinish.finish(this);
        }
        if (this.selectReceive || (updateDeviceState = this.updateDeviceState) == null) {
            return;
        }
        updateDeviceState.updateDevice(this.device);
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public void setStop() {
        this.stop = true;
    }

    public boolean getStop() {
        return this.stop;
    }

    public void setExecFinish(ExecFinish execFinish) {
        this.execFinish = execFinish;
    }

    public void setUpdateDeviceState(UpdateDeviceState updateDeviceState) {
        this.updateDeviceState = updateDeviceState;
    }
}