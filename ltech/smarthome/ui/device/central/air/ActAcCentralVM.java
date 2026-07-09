package com.ltech.smarthome.ui.device.central.air;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.blemesh.ISendCallback;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.model.device_param.ExtParam;
import com.ltech.smarthome.model.device_param.FloorHeatSubDeviceParam;
import com.ltech.smarthome.model.device_param.FreshAirSubDeviceParam;
import com.ltech.smarthome.ui.device.central.airpro.FreshAirInfoItem;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.IReceiveListener;
import com.smart.message.utils.LHomeLog;
import com.smart.product_agreement.productBle.CmdBle;
import com.smart.product_agreement.productBle.CmdBleFactory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActAcCentralVM extends BaseViewModel {
    public static final long BASE_DELAY = 5200;
    public static final int FLOOR_HEAT_MAX_TEMP = 90;
    public static final int FLOOR_HEAT_MIN_TEMP = 5;
    public static final int REFRESH_UI = 1;
    public static final int REPORT_STATE_AC = 3;
    public static final int SEND_CONTROL_CMD = 2;
    public static final int TEMPRATURE_MAX = 30;
    public static final int TEMPRATURE_MIN = 16;
    public static final int WIND_POS = 1;
    public String cmdName;
    public long controlId;
    public int currentMode;
    public int currentOpen;
    public int currentWindSpeed;
    public long lastReportTime;
    public long placeId;
    public boolean selectAction;
    public int selectPosMode;
    public int selectWindSpeedPos;
    public MutableLiveData<Boolean> powerOn = new MutableLiveData<>();
    public MutableLiveData<Boolean> tempControl = new MutableLiveData<>();
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public MutableLiveData<CentralAirSubDeviceParam> param = new MutableLiveData<>();
    public MutableLiveData<FreshAirSubDeviceParam> freshAirParam = new MutableLiveData<>();
    public MutableLiveData<FloorHeatSubDeviceParam> floorHeatParam = new MutableLiveData<>();
    public MutableLiveData<Integer> tempChange = new MutableLiveData<>();
    public int currentTempure = 16;
    public int[] modes = {1, 2, 4, 8};
    public int[] windSpeeds = {0, 1, 2, 4};
    public int MAX_TEMPURE = 30;
    public int MIN_TEMPURE = 16;
    public MutableLiveData<Boolean> sendStateLiveData = new MutableLiveData<>(false);
    public boolean canChange = true;
    public BindingCommand<View> viewclick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.central.air.ActAcCentralVM$$ExternalSyntheticLambda1
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActAcCentralVM.this.lambda$new$0((View) obj);
        }
    });
    private final Handler myHandler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.device.central.air.ActAcCentralVM$$ExternalSyntheticLambda2
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean lambda$new$1;
            lambda$new$1 = ActAcCentralVM.this.lambda$new$1(message);
            return lambda$new$1;
        }
    });
    ISendCallback iSendCallback = new ISendCallback(this) { // from class: com.ltech.smarthome.ui.device.central.air.ActAcCentralVM.3
        @Override // com.ltech.smarthome.blemesh.ISendCallback
        public void onSendSuccess() {
            LHomeLog.i(getClass(), "send cmd success");
        }

        @Override // com.ltech.smarthome.blemesh.ISendCallback
        public void onSendFail() {
            LHomeLog.i(getClass(), "send cmd fail");
        }
    };

    public void initAcFeature(Device device) {
        String acGatewayFeature = ((ExtParam) device.getExtParam(ExtParam.class)).getAcGatewayFeature();
        if (acGatewayFeature == null || acGatewayFeature.length() <= 20) {
            return;
        }
        AcFeatureHelper acFeatureHelper = new AcFeatureHelper(acGatewayFeature);
        this.modes = acFeatureHelper.getModeArray();
        this.windSpeeds = acFeatureHelper.getWindSpeedArray();
        this.MIN_TEMPURE = acFeatureHelper.getMinTemprature();
        this.MAX_TEMPURE = acFeatureHelper.getMaxTemprature();
    }

    public List<IrKeyItem> getKeyItemList(int mode, int windSpeed) {
        ArrayList arrayList = new ArrayList();
        IrKeyItem modeInfo = getModeInfo(mode);
        modeInfo.setEnable(this.param.getValue().getOn() > 0);
        arrayList.add(modeInfo);
        IrKeyItem windSpeedInfo = getWindSpeedInfo(windSpeed);
        windSpeedInfo.setEnable(this.param.getValue().getOn() > 0);
        arrayList.add(windSpeedInfo);
        return arrayList;
    }

    public List<IrKeyItem> getFreshAirKeyItemList(int windSpeed) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new IrKeyItem(R.mipmap.icon_ir_autowind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_1), -1, windSpeed != -1));
        arrayList.add(new IrKeyItem(R.mipmap.icon_ir_lowwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_2), -1, windSpeed != -1));
        arrayList.add(new IrKeyItem(R.mipmap.icon_ir_midwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_3), -1, windSpeed != -1));
        arrayList.add(new IrKeyItem(R.mipmap.icon_ir_highwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_4), -1, windSpeed != -1));
        if (windSpeed == 0) {
            ((IrKeyItem) arrayList.get(0)).setIconRes(R.mipmap.ic_autowind_sel);
            return arrayList;
        }
        if (windSpeed == 1) {
            ((IrKeyItem) arrayList.get(3)).setIconRes(R.mipmap.ic_highwind_sel);
            return arrayList;
        }
        if (windSpeed == 2) {
            ((IrKeyItem) arrayList.get(2)).setIconRes(R.mipmap.ic_midwind_sel);
            return arrayList;
        }
        if (windSpeed != 4) {
            return arrayList;
        }
        ((IrKeyItem) arrayList.get(1)).setIconRes(R.mipmap.ic_lowwind_sel);
        return arrayList;
    }

    public List<FreshAirInfoItem> getFreshAirInfoItemList(FreshAirSubDeviceParam freshAirSubDeviceParam) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new FreshAirInfoItem(R.mipmap.ic_temp, ActivityUtils.getTopActivity().getString(R.string.temperature), freshAirSubDeviceParam.getRoomTemp() + "℃", Boolean.valueOf(freshAirSubDeviceParam.getOn() == 0)));
        arrayList.add(new FreshAirInfoItem(R.mipmap.ic_voc, "VOC", freshAirSubDeviceParam.getVocValue() + "ppm", Boolean.valueOf(freshAirSubDeviceParam.getOn() == 0)));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        int id = view.getId();
        if (id == R.id.iv_minus) {
            if (this.param.getValue() != null) {
                int i = this.currentTempure;
                int i2 = this.MIN_TEMPURE;
                if (i < i2) {
                    this.currentTempure = i2;
                }
                int i3 = this.currentTempure;
                if (i3 > i2) {
                    this.currentTempure = i3 - 1;
                }
                this.tempChange.setValue(Integer.valueOf(this.currentTempure));
                changeTemperature();
                return;
            }
            if (this.floorHeatParam.getValue() != null) {
                if (this.currentTempure < 5) {
                    this.currentTempure = 5;
                }
                int i4 = this.currentTempure;
                if (i4 > 5) {
                    this.currentTempure = i4 - 1;
                }
                this.tempChange.setValue(Integer.valueOf(this.currentTempure));
                floorHeatChangeTemp();
                return;
            }
            return;
        }
        if (id != R.id.iv_plus) {
            if (id != R.id.view_on_off) {
                return;
            }
            setSendState();
            boolean z = !this.powerOn.getValue().booleanValue();
            if (this.param.getValue() != null) {
                this.param.getValue().setOn(z ? 1 : 0);
            } else if (this.floorHeatParam.getValue() != null) {
                this.floorHeatParam.getValue().setOn(z ? 1 : 0);
            } else if (this.freshAirParam.getValue() != null) {
                this.freshAirParam.getValue().setOn(z ? 1 : 0);
            }
            this.powerOn.setValue(Boolean.valueOf(z));
            this.tempControl.setValue(Boolean.valueOf(z));
            openOrClose(z);
            return;
        }
        if (this.param.getValue() != null) {
            int i5 = this.currentTempure;
            int i6 = this.MIN_TEMPURE;
            if (i5 < i6) {
                this.currentTempure = i6;
            }
            int i7 = this.currentTempure;
            if (i7 < this.MAX_TEMPURE) {
                this.currentTempure = i7 + 1;
            }
            this.tempChange.setValue(Integer.valueOf(this.currentTempure));
            changeTemperature();
            return;
        }
        if (this.floorHeatParam.getValue() != null) {
            if (this.currentTempure < 5) {
                this.currentTempure = 5;
            }
            int i8 = this.currentTempure;
            if (i8 < 90) {
                this.currentTempure = i8 + 1;
            }
            this.tempChange.setValue(Integer.valueOf(this.currentTempure));
            floorHeatChangeTemp();
        }
    }

    public String getStateString() {
        StringBuilder sb = new StringBuilder();
        int mode = this.param.getValue().getMode();
        int speed = this.param.getValue().getSpeed();
        if (mode == 1) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.air_mode_1));
        } else if (mode == 2) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.air_mode_5));
        } else if (mode == 4) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.air_mode_4));
        } else if (mode == 8) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.air_mode_2));
        }
        sb.append(" | ");
        if (speed == 0) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_1));
        } else if (speed == 1) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_4));
        } else if (speed == 2) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_3));
        } else if (speed == 3) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_5));
        } else if (speed == 4) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_2));
        } else if (speed == 5) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_6));
        }
        return sb.toString();
    }

    public void changeTemperature() {
        if (this.selectAction) {
            setSceneHelperCmd();
        } else {
            sendCentralCommonCmd(CmdBleFactory.changeTemperature(this.currentTempure, this.param.getValue().getOutAddr(), this.param.getValue().getInAddr()));
        }
    }

    public void changeTemperature(int temprature) {
        if (this.selectAction) {
            setSceneHelperCmd();
        } else {
            sendCentralCommonCmd(CmdBleFactory.changeTemperature(temprature, this.param.getValue().getOutAddr(), this.param.getValue().getInAddr()));
        }
    }

    public void floorHeatChangeTemp() {
        if (this.selectAction) {
            setFloorHeatSceneHelperCmd();
        } else {
            sendFloorHeatCommonCmd(CmdBleFactory.floorHeatChangeTemp(this.currentTempure, this.floorHeatParam.getValue().getOutAddr(), this.floorHeatParam.getValue().getInAddr()));
        }
    }

    public void openOrClose(boolean z) {
        this.currentOpen = z ? 1 : 0;
        if (this.selectAction) {
            if (this.param.getValue() != null) {
                setSceneHelperCmd();
                return;
            } else if (this.floorHeatParam.getValue() != null) {
                setFloorHeatSceneHelperCmd();
                return;
            } else {
                if (this.freshAirParam.getValue() != null) {
                    setFreshAirSceneHelperCmd();
                    return;
                }
                return;
            }
        }
        if (this.param.getValue() != null) {
            this.param.getValue().on = this.currentOpen;
            sendCentralCommonCmd(CmdBleFactory.openOrCloseCentralAir(z, this.param.getValue().getOutAddr(), this.param.getValue().getInAddr()));
        } else if (this.floorHeatParam.getValue() != null) {
            this.floorHeatParam.getValue().on = this.currentOpen;
            sendFloorHeatCommonCmd(CmdBleFactory.openOrCloseFloorHeat(z, this.floorHeatParam.getValue().getOutAddr(), this.floorHeatParam.getValue().getInAddr()));
        } else if (this.freshAirParam.getValue() != null) {
            this.freshAirParam.getValue().on = this.currentOpen;
            sendFreshAirCommonCmd(CmdBleFactory.openOrCloseFreshAir(z, this.freshAirParam.getValue().getOutAddr(), this.freshAirParam.getValue().getInAddr()));
        }
    }

    public void changeMode() {
        int i = this.selectPosMode;
        int[] iArr = this.modes;
        if (i >= iArr.length - 1) {
            this.selectPosMode = 0;
        } else {
            this.selectPosMode = i + 1;
        }
        this.currentMode = iArr[this.selectPosMode];
        this.param.getValue().mode = this.currentMode;
        if (this.selectAction) {
            int i2 = this.currentMode;
            if (i2 == 1) {
                ActivityUtils.getTopActivity().getString(R.string.air_mode_1);
            } else if (i2 == 2) {
                ActivityUtils.getTopActivity().getString(R.string.air_mode_5);
            } else if (i2 == 4) {
                ActivityUtils.getTopActivity().getString(R.string.air_mode_4);
            } else if (i2 == 8) {
                ActivityUtils.getTopActivity().getString(R.string.air_mode_2);
            }
            setSceneHelperCmd();
            return;
        }
        sendCentralCommonCmd(CmdBleFactory.changeMode(this.currentMode, this.param.getValue().getOutAddr(), this.param.getValue().getInAddr()));
    }

    public void selectState() {
        sendQueryCmd(CmdBleFactory.queryCentralAirState(this.param.getValue().getOutAddr(), this.param.getValue().getInAddr()), this.param.getValue().getUnicastAddress());
    }

    public void selectFreshAirState() {
        sendQueryCmd(CmdBleFactory.queryFreshAirState(this.freshAirParam.getValue().getOutAddr(), this.freshAirParam.getValue().getInAddr()), this.freshAirParam.getValue().getUnicastAddress());
    }

    public void selectFloorHeatState() {
        sendQueryCmd(CmdBleFactory.queryFloorHeatState(this.floorHeatParam.getValue().getOutAddr(), this.floorHeatParam.getValue().getInAddr()), this.floorHeatParam.getValue().getUnicastAddress());
    }

    public void sendQueryCmd(BaseCmd cmd, int unicastAddress) {
        MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(cmd).control(getCtrlPackage(unicastAddress)).sendTimes(1).resendTimes(0).timeOutTime(10000).intervalTime(0).receiveListener(new IReceiveListener(this) { // from class: com.ltech.smarthome.ui.device.central.air.ActAcCentralVM.1
            @Override // com.smart.message.base.IReceiveListener
            public void onSuccess(ResponseMsg msg) {
            }

            @Override // com.smart.message.base.IReceiveListener
            public void onTimeout() {
            }
        }).enqueue();
    }

    public void changeWindSpeed() {
        int i = this.selectWindSpeedPos;
        int[] iArr = this.windSpeeds;
        if (i >= iArr.length - 1) {
            this.selectWindSpeedPos = 0;
        } else {
            this.selectWindSpeedPos = i + 1;
        }
        this.currentWindSpeed = iArr[this.selectWindSpeedPos];
        this.param.getValue().speed = this.currentWindSpeed;
        if (this.selectAction) {
            int i2 = this.currentWindSpeed;
            if (i2 == 1) {
                ActivityUtils.getTopActivity().getString(R.string.fan_speed_4);
            } else if (i2 == 2) {
                ActivityUtils.getTopActivity().getString(R.string.fan_speed_3);
            } else if (i2 == 3) {
                ActivityUtils.getTopActivity().getString(R.string.fan_speed_5);
            } else if (i2 == 4) {
                ActivityUtils.getTopActivity().getString(R.string.fan_speed_2);
            } else if (i2 == 5) {
                ActivityUtils.getTopActivity().getString(R.string.fan_speed_6);
            }
            setSceneHelperCmd();
            return;
        }
        sendCentralCommonCmd(CmdBleFactory.changeWindSpeed(this.currentWindSpeed, this.param.getValue().getOutAddr(), this.param.getValue().getInAddr()));
    }

    public void freshAirChangeWindSpeed(int position) {
        if (position == 0) {
            this.currentWindSpeed = 0;
        } else if (position == 1) {
            this.currentWindSpeed = 4;
        } else if (position == 2) {
            this.currentWindSpeed = 2;
        } else if (position == 3) {
            this.currentWindSpeed = 1;
        }
        this.freshAirParam.getValue().speed = this.currentWindSpeed;
        if (this.selectAction) {
            setFreshAirSceneHelperCmd();
        } else {
            sendFreshAirCommonCmd(CmdBleFactory.freshAirChangeWindSpeed(this.currentWindSpeed, this.freshAirParam.getValue().getOutAddr(), this.freshAirParam.getValue().getInAddr()));
        }
    }

    private void sendCentralCommonCmd(BaseCmd cmd) {
        sendCmdWithPackage(cmd, this.param.getValue().getUnicastAddress());
    }

    private void sendFreshAirCommonCmd(BaseCmd cmd) {
        sendCmdWithPackage(cmd, this.freshAirParam.getValue().getUnicastAddress());
    }

    private void sendFloorHeatCommonCmd(BaseCmd cmd) {
        sendCmdWithPackage(cmd, this.floorHeatParam.getValue().getUnicastAddress());
    }

    private BaseCtrlPackage getCtrlPackage(int unicastAddress) {
        return CtrlPackager.getBleForCentralAirDeviceCtrlPackage(unicastAddress);
    }

    public void sendCmdWithPackage(BaseCmd cmd, int unicastAddress) {
        setRefreshDelay(5200L);
        if (this.myHandler.hasMessages(2)) {
            this.myHandler.removeMessages(2);
        }
        Message message = new Message();
        message.what = 2;
        message.obj = cmd;
        message.arg1 = unicastAddress;
        this.myHandler.sendMessageDelayed(message, 300L);
    }

    public void setRefreshDelay(long time) {
        long min = Math.min(time, 10000L);
        this.canChange = false;
        this.myHandler.removeMessages(1);
        this.myHandler.sendEmptyMessageDelayed(1, min);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1(Message message) {
        if (message.what == 1) {
            this.canChange = true;
            return false;
        }
        if (message.what == 2) {
            final BaseCmd baseCmd = (BaseCmd) message.obj;
            MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(baseCmd).control(getCtrlPackage(message.arg1)).sendTimes(1).timeOutTime(10000).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.ui.device.central.air.ActAcCentralVM.2
                @Override // com.smart.message.base.IReceiveListener
                public void onTimeout() {
                }

                @Override // com.smart.message.base.IReceiveListener
                public void onSuccess(ResponseMsg msg) {
                    if (baseCmd.getFunCode() == 208 && msg.getStateCode() == 0) {
                        ActAcCentralVM.this.sendReportState();
                    }
                }
            }).enqueue();
            return false;
        }
        if (message.what != 3 || this.param.getValue() == null) {
            return false;
        }
        this.lastReportTime = System.currentTimeMillis();
        MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(new CmdBle(getAcReportStringWithTime())).control((BaseCtrlPackage) CtrlPackager.getBleForReportCtrlPackage()).sendTimes(1).enqueue();
        return false;
    }

    public IrKeyItem getModeInfo(int mode) {
        if (mode == 1) {
            return new IrKeyItem(R.mipmap.icon_ir_cool, ActivityUtils.getTopActivity().getString(R.string.air_mode_1), -1);
        }
        if (mode == 2) {
            return new IrKeyItem(R.mipmap.icon_ir_dry, ActivityUtils.getTopActivity().getString(R.string.air_mode_5), -1);
        }
        if (mode == 4) {
            return new IrKeyItem(R.mipmap.icon_ir_highwind, ActivityUtils.getTopActivity().getString(R.string.air_mode_4), -1);
        }
        if (mode == 8) {
            return new IrKeyItem(R.mipmap.icon_ir_heat, ActivityUtils.getTopActivity().getString(R.string.air_mode_2), -1);
        }
        return new IrKeyItem(R.mipmap.icon_ir_cool, ActivityUtils.getTopActivity().getString(R.string.air_mode_1), -1);
    }

    public IrKeyItem getWindSpeedInfo(int mode) {
        if (mode == 0) {
            return new IrKeyItem(R.mipmap.icon_ir_auto, ActivityUtils.getTopActivity().getString(R.string.fan_speed_1), -1);
        }
        if (mode == 1) {
            return new IrKeyItem(R.mipmap.icon_ir_highwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_4), -1);
        }
        if (mode == 2) {
            return new IrKeyItem(R.mipmap.icon_ir_midwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_3), -1);
        }
        if (mode == 3) {
            return new IrKeyItem(R.mipmap.icon_ir_highwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_5), -1);
        }
        if (mode == 4) {
            return new IrKeyItem(R.mipmap.icon_ir_lowwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_2), -1);
        }
        if (mode == 5) {
            return new IrKeyItem(R.mipmap.icon_ir_lowwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_6), -1);
        }
        return new IrKeyItem(R.mipmap.icon_ir_highwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_4), -1);
    }

    public IrKeyItem getWindModeInfo(int mode) {
        return new IrKeyItem(R.mipmap.icon_ir_swing, ActivityUtils.getTopActivity().getString(R.string.air_swing), -1);
    }

    public void setSendState() {
        this.sendStateLiveData.setValue(true);
        getMainHandler().postDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.device.central.air.ActAcCentralVM$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActAcCentralVM.this.lambda$setSendState$2();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSendState$2() {
        this.sendStateLiveData.setValue(false);
    }

    public void setSceneHelperCmd() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, 1);
        arrayList.add(0, 0);
        arrayList.add(Integer.valueOf(this.currentOpen != 0 ? 8 : 1));
        arrayList.add(1);
        arrayList.add(Integer.valueOf(this.param.getValue().getOutAddr()));
        arrayList.add(Integer.valueOf(this.param.getValue().getInAddr()));
        arrayList.add(Integer.valueOf(this.currentOpen));
        if (this.currentOpen != 0) {
            arrayList.add(Integer.valueOf(this.currentTempure));
            arrayList.add(Integer.valueOf(this.currentMode));
            arrayList.add(Integer.valueOf(this.currentWindSpeed));
            arrayList.add(0);
            arrayList.add(0);
            arrayList.add(0);
        }
        SceneHelper.instance().baseCmd = new CmdBle(208, arrayList);
        SceneHelper.instance().dstAddress = this.param.getValue().getUnicastAddress();
    }

    public void setFreshAirSceneHelperCmd() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, 1);
        arrayList.add(0, 0);
        arrayList.add(Integer.valueOf(this.currentOpen == 0 ? 9 : 13));
        arrayList.add(1);
        arrayList.add(Integer.valueOf(this.freshAirParam.getValue().getOutAddr()));
        arrayList.add(Integer.valueOf(this.freshAirParam.getValue().getInAddr()));
        arrayList.add(Integer.valueOf(this.currentOpen));
        if (this.currentOpen != 0) {
            arrayList.add(Integer.valueOf(this.freshAirParam.getValue().temperature));
            arrayList.add(Integer.valueOf(this.freshAirParam.getValue().mode));
            arrayList.add(Integer.valueOf(this.currentWindSpeed));
        }
        SceneHelper.instance().baseCmd = new CmdBle(208, arrayList);
        SceneHelper.instance().dstAddress = this.freshAirParam.getValue().getUnicastAddress();
    }

    public void setFloorHeatSceneHelperCmd() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, 1);
        arrayList.add(0, 0);
        arrayList.add(Integer.valueOf(this.currentOpen == 0 ? 14 : 18));
        arrayList.add(1);
        arrayList.add(Integer.valueOf(this.floorHeatParam.getValue().getOutAddr()));
        arrayList.add(Integer.valueOf(this.floorHeatParam.getValue().getInAddr()));
        arrayList.add(Integer.valueOf(this.currentOpen));
        if (this.currentOpen != 0) {
            arrayList.add(Integer.valueOf(this.currentTempure));
            arrayList.add(Integer.valueOf(this.floorHeatParam.getValue().mode));
            arrayList.add(Integer.valueOf(this.floorHeatParam.getValue().antifreezeSwitch));
        }
        SceneHelper.instance().baseCmd = new CmdBle(208, arrayList);
        SceneHelper.instance().dstAddress = this.floorHeatParam.getValue().getUnicastAddress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendReportState() {
        if (System.currentTimeMillis() - this.lastReportTime > 2000) {
            this.myHandler.sendEmptyMessage(getSendCode());
            return;
        }
        if (this.myHandler.hasMessages(getSendCode())) {
            this.myHandler.removeMessages(getSendCode());
        }
        this.myHandler.sendEmptyMessageDelayed(getSendCode(), Math.max(System.currentTimeMillis() - this.lastReportTime, 1000L));
    }

    private int getSendCode() {
        return this.param.getValue() != null ? 3 : 0;
    }

    private String getAcReportStringWithTime() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(222);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(10);
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        arrayList.add(Integer.valueOf((int) (currentTimeMillis & 255)));
        arrayList.add(Integer.valueOf((int) ((currentTimeMillis >> 8) & 255)));
        arrayList.add(Integer.valueOf((int) ((currentTimeMillis >> 16) & 255)));
        arrayList.add(Integer.valueOf((int) ((currentTimeMillis >> 24) & 255)));
        int unicastAddress = this.param.getValue().getUnicastAddress();
        arrayList.add(Integer.valueOf((unicastAddress >> 8) & 255));
        arrayList.add(Integer.valueOf(unicastAddress & 255));
        arrayList.add(5);
        arrayList.add(Integer.valueOf(this.param.getValue().getOutAddr()));
        arrayList.add(Integer.valueOf(this.param.getValue().getInAddr()));
        arrayList.add(Integer.valueOf(this.currentOpen));
        arrayList.add(Integer.valueOf(this.currentTempure));
        arrayList.add(Integer.valueOf(this.currentMode));
        arrayList.add(Integer.valueOf(this.currentWindSpeed));
        arrayList.add(0);
        arrayList.add(0);
        arrayList.add(0);
        arrayList.add(0);
        return DataUtil.formatHexString(arrayList);
    }
}