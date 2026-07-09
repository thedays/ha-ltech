package com.ltech.smarthome.ui.device.central.tepanel;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.G4teBleParam;
import com.ltech.smarthome.ui.device.central.airpro.FreshAirInfoItem;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.TimingSetDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCtrlPackage;
import com.smart.message.base.IReceiveListener;
import com.smart.product_agreement.productBle.CmdBle;
import com.smart.product_agreement.productBle.CmdBleFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ActTePanelVM extends BaseViewModel {
    public static final long BASE_DELAY = 5200;
    public static final int MAX_TEMPURE = 30;
    public static final int MIN_TEMPURE = 16;
    public static final int REFRESH_UI = 1;
    public static final int REPORT_STATE = 3;
    public static final int SEND_CONTROL_CMD = 2;
    public String cmdName;
    public long controlId;
    public long lastReportTime;
    public long placeId;
    public boolean queryOver;
    public boolean selectAction;
    public int selectPosMode;
    public int selectWindDirectionPos;
    public int selectWindSpeedPos;
    public MutableLiveData<Boolean> powerOn = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> airPowerOn = new MutableLiveData<>(false);
    public MutableLiveData<Device> controlObject = new MutableLiveData<>();
    public MutableLiveData<G4teBleParam> g4teParam = new MutableLiveData<>();
    public MutableLiveData<Boolean> offLine = new MutableLiveData<>(false);
    public MutableLiveData<Integer> tempChange = new MutableLiveData<>();
    private int[] modes = {1, 2, 4, 8};
    private int[] windSpeeds = {1, 2, 3, 4, 5};
    private int[] windDirections = {1, 2, 3, 4};
    public boolean isAcControl = true;
    public boolean canChange = true;
    public BindingCommand<View> viewclick = new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM$$ExternalSyntheticLambda0
        @Override // com.ltech.smarthome.binding.command.BindingConsumer
        public final void call(Object obj) {
            ActTePanelVM.this.lambda$new$0((View) obj);
        }
    });
    private final Handler myHandler = new Handler(new Handler.Callback() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM$$ExternalSyntheticLambda1
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean lambda$new$5;
            lambda$new$5 = ActTePanelVM.this.lambda$new$5(message);
            return lambda$new$5;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        switch (view.getId()) {
            case R.id.iv_minus /* 2131297136 */:
                if (getParam() != null) {
                    if (getParam().temperature < 16) {
                        getParam().temperature = 16;
                    }
                    if (getParam().temperature > 16) {
                        G4teBleParam param = getParam();
                        param.temperature--;
                    }
                    this.tempChange.setValue(Integer.valueOf(getParam().temperature));
                    changeTemperature();
                    break;
                }
                break;
            case R.id.iv_plus /* 2131297182 */:
                if (getParam() != null) {
                    if (getParam().temperature < 16) {
                        getParam().temperature = 16;
                    }
                    if (getParam().temperature < 30) {
                        getParam().temperature++;
                    }
                    this.tempChange.setValue(Integer.valueOf(getParam().temperature));
                    changeTemperature();
                    break;
                }
                break;
            case R.id.view_air_on_off /* 2131299209 */:
                openOrCloseAir();
                break;
            case R.id.view_on_off /* 2131299242 */:
                openOrCloseAc();
                break;
        }
    }

    public G4teBleParam getParam() {
        return this.g4teParam.getValue();
    }

    public void openOrCloseAc() {
        boolean z = !this.powerOn.getValue().booleanValue();
        getParam().on = z ? 1 : 0;
        if (getParam().timeOn == getParam().on && getParam().timeValue != 0) {
            getParam().timeValue = 0;
        }
        this.powerOn.setValue(Boolean.valueOf(z));
        openOrClose(z);
    }

    public void openOrCloseAir() {
        boolean z = !this.airPowerOn.getValue().booleanValue();
        getParam().airOn = z ? 1 : 0;
        if (getParam().airTimeOn == getParam().airOn && getParam().airTimeValue != 0) {
            getParam().airTimeValue = 0;
        }
        this.airPowerOn.setValue(Boolean.valueOf(z));
        openOrClose(z);
    }

    public List<Integer> getTempList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 16; i <= 30; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public List<IrKeyItem> getTeKeyItemList(G4teBleParam param) {
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        if (this.isAcControl) {
            IrKeyItem modeInfo = getModeInfo(param.mode);
            modeInfo.setEnable(this.powerOn.getValue().booleanValue());
            arrayList.add(modeInfo);
            this.windSpeeds = new int[]{0, 1, 2, 4};
            IrKeyItem windSpeedInfo = getWindSpeedInfo(param.speed);
            windSpeedInfo.setEnable(this.powerOn.getValue().booleanValue());
            arrayList.add(windSpeedInfo);
            IrKeyItem windDirection = getWindDirection(param.direction);
            if (this.powerOn.getValue().booleanValue() && param.direction != 0) {
                z = true;
            }
            windDirection.setEnable(z);
            arrayList.add(windDirection);
        } else {
            arrayList.add(new IrKeyItem(R.mipmap.icon_ir_lowwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_2), -1, param.airSpeed != -1 && this.airPowerOn.getValue().booleanValue()));
            arrayList.add(new IrKeyItem(R.mipmap.icon_ir_midwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_3), -1, param.airSpeed != -1 && this.airPowerOn.getValue().booleanValue()));
            arrayList.add(new IrKeyItem(R.mipmap.icon_ir_highwind, ActivityUtils.getTopActivity().getString(R.string.fan_speed_4), -1, param.airSpeed != -1 && this.airPowerOn.getValue().booleanValue()));
            int i = param.airSpeed;
            if (i == 1) {
                ((IrKeyItem) arrayList.get(2)).setIconRes(R.mipmap.ic_highwind_sel);
            } else if (i == 2) {
                ((IrKeyItem) arrayList.get(1)).setIconRes(R.mipmap.ic_midwind_sel);
            } else if (i == 4) {
                ((IrKeyItem) arrayList.get(0)).setIconRes(R.mipmap.ic_lowwind_sel);
            }
        }
        IrKeyItem irKeyItem = new IrKeyItem(R.mipmap.icon_ir_timing, R.string.ir_timing, -1);
        irKeyItem.setEnable(true);
        arrayList.add(irKeyItem);
        return arrayList;
    }

    public List<FreshAirInfoItem> getTeAirInfoItemList(G4teBleParam g4teBleParam) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new FreshAirInfoItem(R.mipmap.ic_temp, ActivityUtils.getTopActivity().getString(R.string.temperature), g4teBleParam.airRoomTemp + "℃", Boolean.valueOf(g4teBleParam.airOn == 0)));
        arrayList.add(new FreshAirInfoItem(R.mipmap.ic_voc, "VOC", g4teBleParam.vocValue + "ppm", Boolean.valueOf(g4teBleParam.airOn == 0)));
        return arrayList;
    }

    public String getStateString(G4teBleParam param) {
        return getStateString(param.mode, param.speed);
    }

    public String getStateString(int mode, int windSpeed) {
        StringBuilder sb = new StringBuilder();
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
        if (windSpeed == 0) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_1));
        } else if (windSpeed == 1) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_4));
        } else if (windSpeed == 2) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_3));
        } else if (windSpeed == 3) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_5));
        } else if (windSpeed == 4) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_2));
        } else if (windSpeed == 5) {
            sb.append(ActivityUtils.getTopActivity().getString(R.string.fan_speed_6));
        }
        return sb.toString();
    }

    public void queryG4teState() {
        CmdAssistant.getQueryCmdAssistant(this.controlObject.getValue(), new int[0]).queryPanelState(getContext());
    }

    public void changeTemperature() {
        if (this.selectAction) {
            setSceneHelperCmdAc();
        } else {
            sendCentralCommonCmd(CmdBleFactory.changeTemperature(getParam().temperature, 0, 0));
        }
    }

    public void openOrClose(boolean open) {
        if (this.selectAction) {
            if (this.isAcControl) {
                setSceneHelperCmdAc();
                return;
            } else {
                setSceneHelperCmdAir();
                return;
            }
        }
        if (getParam() != null) {
            sendCentralCommonCmd(CmdBleFactory.openOrCloseG4te(open, this.isAcControl));
        }
    }

    public void changeMode(G4teBleParam param) {
        int i = this.selectPosMode;
        int[] iArr = this.modes;
        if (i >= iArr.length - 1) {
            this.selectPosMode = 0;
        } else {
            this.selectPosMode = i + 1;
        }
        param.mode = iArr[this.selectPosMode];
        if (this.selectAction) {
            setSceneHelperCmdAc();
        } else {
            sendCentralCommonCmd(CmdBleFactory.changeMode(param.mode, 0, 0));
        }
    }

    public void changeWindSpeed(G4teBleParam param) {
        int i = this.selectWindSpeedPos;
        int[] iArr = this.windSpeeds;
        if (i >= iArr.length - 1) {
            this.selectWindSpeedPos = 0;
        } else {
            this.selectWindSpeedPos = i + 1;
        }
        param.speed = iArr[this.selectWindSpeedPos];
        if (this.selectAction) {
            setSceneHelperCmdAc();
        } else {
            sendCentralCommonCmd(CmdBleFactory.changeWindSpeed(param.speed, 0, 0));
        }
    }

    public void changeWindDirection(G4teBleParam param) {
        int i = this.selectWindDirectionPos;
        int[] iArr = this.windDirections;
        if (i >= iArr.length - 1) {
            this.selectWindDirectionPos = 0;
        } else {
            this.selectWindDirectionPos = i + 1;
        }
        param.direction = iArr[this.selectWindDirectionPos];
        if (this.selectAction) {
            setSceneHelperCmdAc();
        } else {
            sendCentralCommonCmd(CmdBleFactory.changeDirection(param.direction, 0, 0));
        }
    }

    public void airChangeWindSpeed(int position) {
        if (position == 0) {
            getParam().airSpeed = 4;
        } else if (position == 1) {
            getParam().airSpeed = 2;
        } else if (position == 2) {
            getParam().airSpeed = 1;
        }
        if (this.selectAction) {
            setSceneHelperCmdAir();
        } else {
            sendCentralCommonCmd(CmdBleFactory.freshAirChangeWindSpeed(getParam().airSpeed, 0, 0));
        }
    }

    public void showTimingDialog() {
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i <= 48; i++) {
            arrayList.add(String.format(Locale.US, "%.1f", Float.valueOf(i * 0.5f)));
        }
        TimingSetDialog.asDefault().setTitle(getContext().getString(R.string.timing)).setSelectList(arrayList).setSelectPosition(0).setSelectListener(new TimingSetDialog.SelectListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.dialog.TimingSetDialog.SelectListener
            public final void confirm(boolean z, int i2) {
                ActTePanelVM.this.lambda$showTimingDialog$1(z, i2);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showTimingDialog$1(boolean z, int i) {
        if (this.isAcControl) {
            getParam().timeOn = !z ? 1 : 0;
            getParam().timeValue = i;
        } else {
            getParam().airTimeOn = !z ? 1 : 0;
            getParam().airTimeValue = i;
        }
        if (this.selectAction) {
            if (this.isAcControl) {
                setSceneHelperCmdAc();
            } else {
                setSceneHelperCmdAir();
            }
            this.g4teParam.setValue(getParam());
            return;
        }
        sendTimingCmd(z, i);
    }

    public void showCancelTimingDialog() {
        MessageDialog.show((AppCompatActivity) ActivityUtils.getTopActivity(), "", getContext().getString(R.string.cancel_timing)).setOkButton(R.string.confirm, new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM$$ExternalSyntheticLambda4
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showCancelTimingDialog$2;
                lambda$showCancelTimingDialog$2 = ActTePanelVM.this.lambda$showCancelTimingDialog$2(baseDialog, view);
                return lambda$showCancelTimingDialog$2;
            }
        }).setCancelButton(R.string.cancel).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showCancelTimingDialog$2(BaseDialog baseDialog, View view) {
        if (this.isAcControl) {
            getParam().timeOn = 0;
            getParam().timeValue = 0;
        } else {
            getParam().airTimeOn = 0;
            getParam().airTimeValue = 0;
        }
        sendTimingCmd(true, 0);
        return false;
    }

    public void sendTimingCmd(boolean z, int i) {
        final BaseNormalActivity baseNormalActivity = (BaseNormalActivity) ActivityUtils.getTopActivity();
        baseNormalActivity.showLoadingDialog(getContext().getString(R.string.saving));
        CmdAssistant.getDeviceAssistant(this.controlObject.getValue(), new int[0]).sendCmdWithResult(getContext(), CmdBleFactory.setTiming(!z ? 1 : 0, i, this.isAcControl ? 80 : 81), new IAction() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActTePanelVM.this.lambda$sendTimingCmd$3(baseNormalActivity, (ResponseMsg) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendTimingCmd$3(BaseNormalActivity baseNormalActivity, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            baseNormalActivity.showSuccessDialog(getContext().getString(R.string.save_success));
            this.g4teParam.setValue(getParam());
        } else {
            baseNormalActivity.showErrorDialog(getContext().getString(R.string.save_fail));
        }
    }

    public void initDataListener(final Device device) {
        MessageManager.getInstance().setCentralAirStatusCallback(new MessageManager.CentralAirStatusCallback() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM$$ExternalSyntheticLambda2
            @Override // com.smart.message.MessageManager.CentralAirStatusCallback
            public final void onDataReceive(int i, int i2, int i3, String str) {
                ActTePanelVM.this.lambda$initDataListener$4(device, i, i2, i3, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDataListener$4(Device device, int i, int i2, int i3, String str) {
        if (this.canChange && device != null) {
            G4teBleParam g4teBleParam = (G4teBleParam) device.getParam(G4teBleParam.class);
            if (i != g4teBleParam.getUnicastAddress()) {
                return;
            }
            this.queryOver = true;
            if (str.length() >= 42) {
                setRefreshDelay(((Long.parseLong(str.substring(0, 8), 16) * 1000) + 5200) - System.currentTimeMillis());
                str = str.substring(8);
            }
            if (str.length() >= 34) {
                g4teBleParam.on = Integer.valueOf(str.substring(0, 2), 16).intValue();
                g4teBleParam.temperature = Integer.valueOf(str.substring(2, 4), 16).intValue();
                g4teBleParam.mode = Integer.valueOf(str.substring(4, 6), 16).intValue();
                g4teBleParam.speed = Integer.valueOf(str.substring(6, 8), 16).intValue();
                g4teBleParam.direction = Integer.valueOf(str.substring(8, 10), 16).intValue();
                g4teBleParam.roomTemp = Integer.valueOf(str.substring(10, 12), 16).intValue();
                g4teBleParam.errorCode = Integer.valueOf(str.substring(12, 14), 16).intValue();
                g4teBleParam.timeOn = Integer.valueOf(str.substring(14, 16), 16).intValue();
                g4teBleParam.timeValue = Integer.valueOf(str.substring(16, 18), 16).intValue();
                String substring = str.substring(18);
                g4teBleParam.airOn = Integer.valueOf(substring.substring(0, 2), 16).intValue();
                g4teBleParam.pmValue = Integer.valueOf(substring.substring(2, 4), 16).intValue();
                g4teBleParam.vocValue = Integer.valueOf(substring.substring(4, 6), 16).intValue();
                g4teBleParam.airSpeed = Integer.valueOf(substring.substring(6, 8), 16).intValue();
                g4teBleParam.airRoomTemp = Integer.valueOf(substring.substring(8, 10), 16).intValue();
                g4teBleParam.airErrorCode = Integer.valueOf(substring.substring(10, 12), 16).intValue();
                g4teBleParam.airTimeOn = Integer.valueOf(substring.substring(12, 14), 16).intValue();
                g4teBleParam.airTimeValue = Integer.valueOf(substring.substring(14, 16), 16).intValue();
            }
            this.offLine.setValue(false);
            device.getDeviceState().setOnlineState(1);
            device.setParam(g4teBleParam);
            Injection.repo().device().saveDevice(device);
            this.g4teParam.setValue(g4teBleParam);
        }
    }

    private void sendCentralCommonCmd(BaseCmd cmd) {
        sendControlCmd(cmd, this.controlObject.getValue().getUnicastAddress());
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
            return new IrKeyItem(R.mipmap.icon_ir_autowind, getContext().getString(R.string.fan_speed_1), -1);
        }
        if (mode == 1) {
            return new IrKeyItem(R.mipmap.icon_ir_highwind, getContext().getString(R.string.fan_speed_4), -1);
        }
        if (mode == 2) {
            return new IrKeyItem(R.mipmap.icon_ir_midwind, getContext().getString(R.string.fan_speed_3), -1);
        }
        if (mode == 3) {
            return new IrKeyItem(R.mipmap.icon_ir_highwind, getContext().getString(R.string.fan_speed_5), -1);
        }
        if (mode == 4) {
            return new IrKeyItem(R.mipmap.icon_ir_lowwind, getContext().getString(R.string.fan_speed_2), -1);
        }
        if (mode == 5) {
            return new IrKeyItem(R.mipmap.icon_ir_lowwind, getContext().getString(R.string.fan_speed_6), -1);
        }
        return new IrKeyItem(R.mipmap.icon_ir_highwind, getContext().getString(R.string.fan_speed_4), -1);
    }

    public IrKeyItem getWindDirection(int mode) {
        if (mode == 0) {
            return new IrKeyItem(R.mipmap.icon_wind_swing_disable, getContext().getString(R.string.air_direct), -1);
        }
        if (mode == 1) {
            return new IrKeyItem(R.mipmap.icon_wind_down, getContext().getString(R.string.wind_down), -1);
        }
        if (mode == 2) {
            return new IrKeyItem(R.mipmap.icon_wind_mid, getContext().getString(R.string.wind_mid), -1);
        }
        if (mode == 3) {
            return new IrKeyItem(R.mipmap.icon_wind_up, getContext().getString(R.string.wind_up), -1);
        }
        return new IrKeyItem(R.mipmap.icon_wind_swing, getContext().getString(R.string.wind_swing), -1);
    }

    public void setSceneHelperCmdAc() {
        G4teBleParam param = getParam();
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, 1);
        arrayList.add(0, 0);
        arrayList.add(8);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(param.on));
        arrayList.add(Integer.valueOf(param.temperature));
        arrayList.add(Integer.valueOf(param.mode));
        arrayList.add(Integer.valueOf(param.speed));
        arrayList.add(Integer.valueOf(param.direction));
        arrayList.add(Integer.valueOf(param.timeOn));
        arrayList.add(Integer.valueOf(param.timeValue));
        SceneHelper.instance().baseCmd = new CmdBle(208, arrayList);
        SceneHelper.instance().dstAddress = this.controlObject.getValue().getUnicastAddress();
    }

    public void setSceneHelperCmdAir() {
        G4teBleParam param = getParam();
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, 1);
        arrayList.add(0, 0);
        arrayList.add(13);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(param.airOn));
        arrayList.add(Integer.valueOf(param.airRoomTemp));
        arrayList.add(0);
        arrayList.add(Integer.valueOf(param.airSpeed));
        arrayList.add(Integer.valueOf(param.airTimeOn));
        arrayList.add(Integer.valueOf(param.airTimeValue));
        SceneHelper.instance().baseCmd = new CmdBle(208, arrayList);
        SceneHelper.instance().dstAddress = this.controlObject.getValue().getUnicastAddress();
    }

    private String getTeReportStringWithTime() {
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
        int unicastAddress = getParam().getUnicastAddress();
        arrayList.add(Integer.valueOf((unicastAddress >> 8) & 255));
        arrayList.add(Integer.valueOf(unicastAddress & 255));
        arrayList.add(15);
        arrayList.add(Integer.valueOf(getParam().on));
        arrayList.add(Integer.valueOf(getParam().temperature));
        arrayList.add(Integer.valueOf(getParam().mode));
        arrayList.add(Integer.valueOf(getParam().speed));
        arrayList.add(Integer.valueOf(getParam().direction));
        arrayList.add(Integer.valueOf(getParam().roomTemp));
        arrayList.add(Integer.valueOf(getParam().errorCode));
        arrayList.add(Integer.valueOf(getParam().timeOn));
        arrayList.add(Integer.valueOf(getParam().timeValue));
        arrayList.add(Integer.valueOf(getParam().airOn));
        arrayList.add(Integer.valueOf(getParam().pmValue));
        arrayList.add(Integer.valueOf(getParam().vocValue));
        arrayList.add(Integer.valueOf(getParam().airSpeed));
        arrayList.add(Integer.valueOf(getParam().airRoomTemp));
        arrayList.add(Integer.valueOf(getParam().airErrorCode));
        arrayList.add(Integer.valueOf(getParam().airTimeOn));
        arrayList.add(Integer.valueOf(getParam().airTimeValue));
        return DataUtil.formatHexString(arrayList);
    }

    public void sendControlCmd(BaseCmd cmd, int unicastAddress) {
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
    public void sendReportState() {
        if (System.currentTimeMillis() - this.lastReportTime > 2000) {
            this.myHandler.sendEmptyMessage(3);
            return;
        }
        if (this.myHandler.hasMessages(3)) {
            this.myHandler.removeMessages(3);
        }
        this.myHandler.sendEmptyMessageDelayed(3, Math.max(System.currentTimeMillis() - this.lastReportTime, 1000L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$5(Message message) {
        if (message.what == 1) {
            this.canChange = true;
            return false;
        }
        if (message.what == 2) {
            final BaseCmd baseCmd = (BaseCmd) message.obj;
            MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(baseCmd).control((BaseCtrlPackage) CtrlPackager.getBleForCentralAirDeviceCtrlPackage(message.arg1)).sendTimes(1).timeOutTime(10000).receiveListener(new IReceiveListener() { // from class: com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM.1
                @Override // com.smart.message.base.IReceiveListener
                public void onTimeout() {
                }

                @Override // com.smart.message.base.IReceiveListener
                public void onSuccess(ResponseMsg msg) {
                    if (baseCmd.getFunCode() == 208 && msg.getStateCode() == 0) {
                        ActTePanelVM.this.sendReportState();
                    }
                }
            }).enqueue();
            return false;
        }
        if (message.what != 3) {
            return false;
        }
        this.lastReportTime = System.currentTimeMillis();
        MessageManager.getInstance().create(ActivityUtils.getTopActivity()).cmd(new CmdBle(getTeReportStringWithTime())).control((BaseCtrlPackage) CtrlPackager.getBleForReportCtrlPackage()).sendTimes(1).enqueue();
        return false;
    }
}