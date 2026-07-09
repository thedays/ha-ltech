package com.ltech.smarthome.ui.device.microwave_sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.WaveSensorExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.EventBusUtils;
import com.ltech.smarthome.utils.LiveBusHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.DeviceAssistant;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.LHomeLog;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.param.LightCmdParam;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class WaveSensorHelper {
    public BaseCmdParam cmdParam;
    public Object controlObject;
    public WaveSensorExtParam extParam;
    public int index;
    private boolean isBleSwitch;
    public boolean isChangeAll;
    public Object relateObject;
    public int setDataType;

    private WaveSensorHelper() {
    }

    private static class Holder {
        private static WaveSensorHelper INSTANCE = new WaveSensorHelper();

        private Holder() {
        }
    }

    public static WaveSensorHelper instance() {
        return Holder.INSTANCE;
    }

    public void setSensorRelateParam(int trigType, int optionValue) {
        int i = this.setDataType;
        String str = "00";
        if (i == 1) {
            this.isChangeAll = true;
            this.extParam.getDelayParam(this.index).objectId = 0L;
            this.extParam.getDelayParam(this.index).destAddress = 1;
            if (this.extParam.getDelayParam(this.index).actionType == 0) {
                this.extParam.getDelayParam(this.index).option = String.valueOf(optionValue);
            }
            this.extParam.getDelayParam(this.index).optionValue = optionValue;
            this.extParam.getDelayParam(this.index).actionPart = 1;
            this.extParam.getDelayParam(this.index).smartType = 0;
            this.extParam.getDelayParam(this.index).actionType = trigType;
            if (trigType != 255) {
                str = "01" + StringUtils.demToHexDouble(optionValue);
            }
            this.extParam.getDelayParam(this.index).instruct = "0001" + StringUtils.demToHexDouble(trigType) + "01" + str;
        } else if (i == 2) {
            this.extParam.getStayParam(this.index).objectId = 0L;
            if (this.extParam.getStayParam(this.index).actionType == 0) {
                this.extParam.getStayParam(this.index).option = String.valueOf(optionValue);
            }
            this.extParam.getStayParam(this.index).optionValue = optionValue;
            this.extParam.getStayParam(this.index).destAddress = 1;
            this.extParam.getStayParam(this.index).smartType = 0;
            this.extParam.getStayParam(this.index).actionPart = 2;
            this.extParam.getStayParam(this.index).actionType = trigType;
            if ((trigType != 0 && trigType != 255) || this.extParam.getDelayParam(this.index).actionType == 4) {
                this.extParam.getStayParam(this.index).destAddress = 1;
                if (trigType != 255) {
                    str = "01" + StringUtils.demToHexDouble(optionValue);
                }
                this.extParam.getStayParam(this.index).instruct = "0001" + StringUtils.demToHexDouble(trigType) + "02" + str;
            } else {
                this.extParam.getStayParam(this.index).destAddress = 0;
                this.extParam.getStayParam(this.index).instruct = "";
            }
        } else if (i == 3) {
            this.extParam.setExitParam(this.index, new WaveSensorExtParam.SensorParam());
            this.extParam.getExitParam(this.index).destAddress = 1;
            if (this.extParam.getExitParam(this.index).actionType == 0) {
                this.extParam.getExitParam(this.index).option = String.valueOf(optionValue);
            }
            this.extParam.getExitParam(this.index).optionValue = optionValue;
            this.extParam.getExitParam(this.index).actionPart = 3;
            this.extParam.getExitParam(this.index).actionType = trigType;
            if (trigType != 255) {
                str = "01" + StringUtils.demToHexDouble(optionValue);
            }
            this.extParam.getExitParam(this.index).instruct = "0001" + StringUtils.demToHexDouble(trigType) + "03" + str;
        }
        if (!this.isChangeAll) {
            sendRelateParam(1, trigType);
        } else if (this.setDataType == 3) {
            this.isChangeAll = false;
            getCmdHelper().setRelateParamAll(ActivityUtils.getTopActivity(), 1, trigType, StringUtils.hexStringToByte(this.extParam.getDelayParam(this.index).instruct.substring(10)), this.extParam.getStayParam(this.index).destAddress == 0 ? null : StringUtils.hexStringToByte(this.extParam.getStayParam(this.index).instruct.substring(10)), StringUtils.hexStringToByte(this.extParam.getExitParam(this.index).instruct.substring(10)), new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper$$ExternalSyntheticLambda3
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    WaveSensorHelper.this.lambda$setSensorRelateParam$0((Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensorRelateParam$0(Boolean bool) {
        if (bool.booleanValue()) {
            updateWaveSensorExt(this.extParam, false);
        }
    }

    public String getConvertCmd() {
        BaseCmd convert2cmd = Injection.strategy().getCmdConvertStrategy(2).convert2cmd(this.cmdParam);
        return StringUtils.demToHexDouble(convert2cmd.getFunCode()) + StringUtils.byte2Str(convert2cmd.value(new Object[0]));
    }

    private void sendRelateParam(int destAdd, final int trigType) {
        String substring;
        int i = this.setDataType;
        if (i == 1) {
            substring = this.extParam.getDelayParam(this.index).instruct.substring(10);
        } else if (i == 2) {
            substring = this.extParam.getStayParam(this.index).instruct.substring(10);
        } else {
            substring = this.extParam.getExitParam(this.index).instruct.substring(10);
        }
        getCmdHelper().setRelateParam(ActivityUtils.getTopActivity(), destAdd, trigType, this.setDataType, StringUtils.hexStringToByte(substring), new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                WaveSensorHelper.this.lambda$sendRelateParam$1(trigType, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendRelateParam$1(int i, Boolean bool) {
        if (bool.booleanValue()) {
            updateWaveSensorExt(this.extParam, i == 3 || i == 4);
        }
    }

    private void finish() {
        if (!this.isBleSwitch) {
            ActivityUtils.getTopActivity().setResult(3001);
        }
        ActivityUtils.getTopActivity().finish();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0036, code lost:
    
        if (r9.equals(com.ltech.smarthome.model.product.ProductId.ID_BLE_SWITCH) == false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isStayEnable(java.lang.Object r9) {
        /*
            r8 = this;
            r0 = 0
            r8.isBleSwitch = r0
            boolean r1 = r9 instanceof com.ltech.smarthome.model.bean.Device
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L77
            com.ltech.smarthome.model.bean.Device r9 = (com.ltech.smarthome.model.bean.Device) r9
            com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper r1 = instance()
            r1.relateObject = r9
            java.lang.String r9 = r9.getProductId()
            r9.hashCode()
            int r1 = r9.hashCode()
            r7 = -1
            switch(r1) {
                case -1805322688: goto L65;
                case -1805199680: goto L5a;
                case -1804340546: goto L4f;
                case -1804278081: goto L44;
                case -1803448738: goto L39;
                case 166485422: goto L30;
                case 662799966: goto L25;
                default: goto L23;
            }
        L23:
            r2 = -1
            goto L6f
        L25:
            java.lang.String r1 = "122112209430401"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L2e
            goto L23
        L2e:
            r2 = 6
            goto L6f
        L30:
            java.lang.String r1 = "120122111301201"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L6f
            goto L23
        L39:
            java.lang.String r1 = "120033108272201"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L42
            goto L23
        L42:
            r2 = 4
            goto L6f
        L44:
            java.lang.String r1 = "120033108265701"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L4d
            goto L23
        L4d:
            r2 = 3
            goto L6f
        L4f:
            java.lang.String r1 = "120033108263401"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L58
            goto L23
        L58:
            r2 = 2
            goto L6f
        L5a:
            java.lang.String r1 = "120033108255901"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L63
            goto L23
        L63:
            r2 = 1
            goto L6f
        L65:
            java.lang.String r1 = "120033108251501"
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L6e
            goto L23
        L6e:
            r2 = 0
        L6f:
            switch(r2) {
                case 0: goto L76;
                case 1: goto L76;
                case 2: goto L76;
                case 3: goto L76;
                case 4: goto L76;
                case 5: goto L73;
                case 6: goto L76;
                default: goto L72;
            }
        L72:
            goto L75
        L73:
            r8.isBleSwitch = r6
        L75:
            return r0
        L76:
            return r6
        L77:
            com.ltech.smarthome.model.bean.Group r9 = (com.ltech.smarthome.model.bean.Group) r9
            int r1 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r9)
            com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper r7 = instance()
            r7.relateObject = r9
            if (r1 == r6) goto L94
            if (r1 == r5) goto L94
            if (r1 == r4) goto L94
            if (r1 == r3) goto L94
            if (r1 == r2) goto L94
            r9 = 7
            if (r1 == r9) goto L91
            goto L93
        L91:
            r8.isBleSwitch = r6
        L93:
            return r0
        L94:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper.isStayEnable(java.lang.Object):boolean");
    }

    public void setSensorRelateBleParam() {
        long groupId;
        int groupAddress;
        int i;
        String convertCmd = instance().getConvertCmd();
        LHomeLog.i(WaveSensorHelper.class, convertCmd);
        Object obj = this.relateObject;
        if (obj instanceof Device) {
            Device device = (Device) obj;
            groupId = device.getDeviceId();
            groupAddress = ((BleParam) device.getParam(BleParam.class)).getUnicastAddress();
            i = 1;
        } else {
            Group group = (Group) obj;
            groupId = group.getGroupId();
            groupAddress = group.getGroupAddress();
            i = 2;
        }
        String str = StringUtils.demToHexDouble((groupAddress >> 8) & 255) + StringUtils.demToHexDouble(groupAddress & 255);
        int i2 = this.setDataType;
        if (i2 == 1) {
            this.isChangeAll = true;
            this.extParam.getDelayParam(this.index).objectId = groupId;
            this.extParam.getDelayParam(this.index).destAddress = groupAddress;
            this.extParam.getDelayParam(this.index).smartType = i;
            this.extParam.getDelayParam(this.index).instruct = str + "0301" + StringUtils.demToHexDouble(convertCmd.length() / 2) + convertCmd;
            this.extParam.getDelayParam(this.index).actionPart = 1;
            this.extParam.getDelayParam(this.index).actionType = 3;
            this.extParam.getDelayParam(this.index).option = (String) this.cmdParam.getExtParam(SceneHelper.OPTION);
            if (this.cmdParam.getExtParam(SceneHelper.OPTION_VALUE) != null) {
                this.extParam.getDelayParam(this.index).optionValue = ((Integer) this.cmdParam.getExtParam(SceneHelper.OPTION_VALUE)).intValue();
            }
        } else if (i2 == 2) {
            this.extParam.setStayParam(this.index, new WaveSensorExtParam.SensorParam());
            if (!this.isChangeAll || isStayEnable(this.relateObject)) {
                this.extParam.getStayParam(this.index).objectId = groupId;
                this.extParam.getStayParam(this.index).destAddress = groupAddress;
                this.extParam.getStayParam(this.index).smartType = i;
                this.extParam.getStayParam(this.index).actionPart = 2;
                this.extParam.getStayParam(this.index).actionType = 3;
                this.extParam.getStayParam(this.index).instruct = str + "0302" + StringUtils.demToHexDouble(convertCmd.length() / 2) + convertCmd;
                this.extParam.getStayParam(this.index).option = (String) this.cmdParam.getExtParam(SceneHelper.OPTION);
                if (this.cmdParam.getExtParam(SceneHelper.OPTION_VALUE) != null) {
                    this.extParam.getStayParam(this.index).optionValue = ((Integer) this.cmdParam.getExtParam(SceneHelper.OPTION_VALUE)).intValue();
                }
            } else {
                this.extParam.getStayParam(this.index).destAddress = 0;
                this.extParam.getStayParam(this.index).instruct = "";
            }
        } else if (i2 == 3) {
            this.extParam.getExitParam(this.index).objectId = groupId;
            this.extParam.getExitParam(this.index).destAddress = groupAddress;
            this.extParam.getExitParam(this.index).smartType = i;
            this.extParam.getExitParam(this.index).instruct = str + "0303" + StringUtils.demToHexDouble(convertCmd.length() / 2) + convertCmd;
            this.extParam.getExitParam(this.index).actionPart = 3;
            this.extParam.getExitParam(this.index).actionType = 3;
            this.extParam.getExitParam(this.index).option = (String) this.cmdParam.getExtParam(SceneHelper.OPTION);
            if (this.cmdParam.getExtParam(SceneHelper.OPTION_VALUE) != null) {
                this.extParam.getExitParam(this.index).optionValue = ((Integer) this.cmdParam.getExtParam(SceneHelper.OPTION_VALUE)).intValue();
            }
        }
        if (!this.isChangeAll) {
            sendRelateParam(groupAddress, 3);
        } else if (this.setDataType == 3) {
            this.isChangeAll = false;
            getCmdHelper().setRelateParamAll(ActivityUtils.getTopActivity(), groupAddress, 3, StringUtils.hexStringToByte(this.extParam.getDelayParam(this.index).instruct.substring(10)), (this.extParam.getStayParam(this.index).destAddress == 0 || "".equals(this.extParam.getStayParam(this.index).instruct)) ? null : StringUtils.hexStringToByte(this.extParam.getStayParam(this.index).instruct.substring(10)), StringUtils.hexStringToByte(this.extParam.getExitParam(this.index).instruct.substring(10)), new IAction() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj2) {
                    WaveSensorHelper.this.lambda$setSensorRelateBleParam$2((Boolean) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSensorRelateBleParam$2(Boolean bool) {
        if (bool.booleanValue()) {
            updateWaveSensorExt(this.extParam, true);
        }
    }

    public void setSensorRelateSceneParam() {
        String convertCmd = instance().getConvertCmd();
        LHomeLog.i(WaveSensorHelper.class, convertCmd);
        String str = StringUtils.demToHexDouble(254) + StringUtils.demToHexDouble(1);
        Scene scene = (Scene) this.relateObject;
        int i = this.setDataType;
        if (i == 1) {
            this.extParam.getDelayParam(this.index).objectId = scene.getSceneId();
            this.extParam.getDelayParam(this.index).destAddress = 65025;
            this.extParam.getDelayParam(this.index).instruct = str + "0401" + StringUtils.demToHexDouble(convertCmd.length() / 2) + convertCmd;
            this.extParam.getDelayParam(this.index).actionPart = 1;
            this.extParam.getDelayParam(this.index).actionType = 4;
        } else if (i == 2) {
            this.extParam.getStayParam(this.index).objectId = scene.getSceneId();
            this.extParam.getStayParam(this.index).destAddress = 65025;
            this.extParam.getStayParam(this.index).instruct = str + "0402" + StringUtils.demToHexDouble(convertCmd.length() / 2) + convertCmd;
            this.extParam.getStayParam(this.index).actionPart = 2;
            this.extParam.getStayParam(this.index).actionType = 4;
        } else if (i == 3) {
            this.extParam.getExitParam(this.index).objectId = scene.getSceneId();
            this.extParam.getExitParam(this.index).destAddress = 65025;
            this.extParam.getExitParam(this.index).instruct = str + "0403" + StringUtils.demToHexDouble(convertCmd.length() / 2) + convertCmd;
            this.extParam.getExitParam(this.index).actionPart = 3;
            this.extParam.getExitParam(this.index).actionType = 4;
        }
        sendRelateParam(65025, 4);
    }

    private void updateWaveSensorExt(final WaveSensorExtParam extParam, final boolean back) {
        Object obj = this.controlObject;
        if (obj instanceof Device) {
            final Device device = (Device) obj;
            ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), extParam.getSensorParamMapString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj2) {
                    WaveSensorHelper.this.lambda$updateWaveSensorExt$3(device, extParam, back, obj2);
                }
            }, new SmartErrorComsumer());
        } else {
            final Group group = (Group) obj;
            ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), extParam.getSensorParamMapString()).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((AppCompatActivity) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj2) {
                    WaveSensorHelper.this.lambda$updateWaveSensorExt$4(group, extParam, back, obj2);
                }
            }, new SmartErrorComsumer());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateWaveSensorExt$3(Device device, WaveSensorExtParam waveSensorExtParam, boolean z, Object obj) throws Exception {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId());
        deviceByDeviceId.setExtParam(waveSensorExtParam.getSensorParamMapString());
        Injection.repo().device().saveDevice(deviceByDeviceId);
        if (z) {
            finish();
        } else {
            EventBusUtils.post(new LiveBusHelper(8));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateWaveSensorExt$4(Group group, WaveSensorExtParam waveSensorExtParam, boolean z, Object obj) throws Exception {
        Group groupByGroupId = Injection.repo().group().getGroupByGroupId(group.getGroupId());
        groupByGroupId.setExtParam(waveSensorExtParam.getSensorParamMapString());
        Injection.repo().group().saveGroup(groupByGroupId);
        if (z) {
            finish();
        } else {
            EventBusUtils.post(new LiveBusHelper(8));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x006a, code lost:
    
        if (r15.equals(com.ltech.smarthome.model.product.ProductId.ID_KNOB_PANEL_E6A) == false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void goSelectAction(android.app.Activity r19, java.lang.Object r20) {
        /*
            Method dump skipped, instructions count: 886
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.microwave_sensor.WaveSensorHelper.goSelectAction(android.app.Activity, java.lang.Object):void");
    }

    public boolean isSuppportRelaySet(int index, WaveSensorExtParam extParam) {
        return extParam.getDelayParam(index).actionType > 2;
    }

    private void saveSwitchData() {
        LightCmdParam lightCmdParam = new LightCmdParam();
        lightCmdParam.setZoneNum(getSwitchZoneNum());
        lightCmdParam.setCmdType(1);
        lightCmdParam.setOn(true);
        lightCmdParam.addExtParam(SceneHelper.OPTION, "7");
        instance().cmdParam = lightCmdParam;
        instance().setDataType = 1;
        instance().setSensorRelateBleParam();
        if (instance().isChangeAll) {
            instance().setDataType = 2;
            instance().setSensorRelateBleParam();
            LightCmdParam lightCmdParam2 = new LightCmdParam();
            lightCmdParam2.setZoneNum(getSwitchZoneNum());
            lightCmdParam2.setCmdType(1);
            lightCmdParam2.setOn(false);
            lightCmdParam2.addExtParam(SceneHelper.OPTION, "4");
            instance().cmdParam = lightCmdParam2;
            instance().setDataType = 3;
            instance().setSensorRelateBleParam();
        }
    }

    private int getSwitchZoneNum() {
        if (ProductRepository.isRelaySeparationSub(this.relateObject)) {
            return RelaySeparationHelper.getZoneNum(this.relateObject);
        }
        return 1;
    }

    public DeviceAssistant getCmdHelper() {
        return CmdAssistant.getDeviceAssistant(this.controlObject, 1 << this.index);
    }
}