package com.ltech.smarthome.ui.replace;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.fastjson.JSONObject;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.nfc.WriteVirtualHelper;
import com.ltech.smarthome.singleton.Singleton;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.SettingAssistant;
import com.smart.message.base.BaseCmd;
import com.smart.message.base.BaseCmdParam;
import com.smart.message.utils.StringUtils;
import com.smart.product_agreement.bean.SmartPanelSettingState;
import com.sun.jna.platform.win32.WinError;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ReplaceHelper {
    public Device newDevice;
    private Device parseDevice;
    private HashMap<String, BaseCmdParam> cmdMap = new HashMap<>();
    private List<UpdateBackDataRequest.RowsBean> updateList = new ArrayList();

    static /* synthetic */ void lambda$backupEurPanelDefault$1(Object obj) throws Exception {
    }

    public static ReplaceHelper instance() {
        return (ReplaceHelper) Singleton.getSingleton(ReplaceHelper.class);
    }

    private String getConvertCmd(BaseCmdParam cmdParam) {
        if (cmdParam != null) {
            BaseCmd convert2cmd = Injection.strategy().getCmdConvertStrategy(2).convert2cmd(cmdParam);
            return StringUtils.demToHexDouble(convert2cmd.getFunCode()) + StringUtils.byte2Str(convert2cmd.value(new Object[0]));
        }
        return "";
    }

    private void update(LifecycleOwner owner) {
        if (this.updateList.size() > 0) {
            ((ObservableSubscribeProxy) Injection.net().backupDeviceData(this.updateList).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe();
            ArrayList arrayList = new ArrayList();
            Iterator<UpdateBackDataRequest.RowsBean> it = this.updateList.iterator();
            while (it.hasNext()) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().getDeviceId());
                if (deviceByDeviceId != null && deviceByDeviceId.isVirtual()) {
                    arrayList.add(Long.valueOf(deviceByDeviceId.getDeviceId()));
                }
            }
            if (!arrayList.isEmpty()) {
                WriteVirtualHelper.instance().updateDeviceWritable(arrayList);
            }
        }
        this.cmdMap.clear();
        this.updateList.clear();
    }

    public boolean dataEmpty() {
        return this.cmdMap.isEmpty();
    }

    public void addBackupData(String flag, BaseCmdParam cmdParam) {
        this.cmdMap.put(flag, cmdParam);
    }

    public void addBackupIndexData(String flag, BaseCmdParam cmdParam, int index) {
        this.cmdMap.put(flag + index, cmdParam);
    }

    public void addBackupIndexAndTypeData(String flag, BaseCmdParam cmdParam, int index, int type) {
        this.cmdMap.put(flag + index + ProductId.SPLIT + type, cmdParam);
    }

    public void backupData(LifecycleOwner owner, long deviceId, String flag, BaseCmdParam cmdParam) {
        addBackupData(flag, cmdParam);
        backupData(owner, deviceId);
    }

    public void backupIndexData(LifecycleOwner owner, long deviceId, String flag, BaseCmdParam cmdParam, int index) {
        addBackupData(flag, cmdParam);
        backupIndexData(owner, deviceId, index);
    }

    public void backupData(LifecycleOwner owner, long deviceId) {
        for (String str : this.cmdMap.keySet()) {
            this.updateList.add(new UpdateBackDataRequest.RowsBean(deviceId, str, getConvertCmd(this.cmdMap.get(str))));
        }
        update(owner);
    }

    public void backupData(LifecycleOwner owner, List<Long> deviceIds) {
        for (String str : this.cmdMap.keySet()) {
            String convertCmd = getConvertCmd(this.cmdMap.get(str));
            Iterator<Long> it = deviceIds.iterator();
            while (it.hasNext()) {
                this.updateList.add(new UpdateBackDataRequest.RowsBean(it.next().longValue(), str, convertCmd));
            }
        }
        update(owner);
    }

    public void backupIndexData(LifecycleOwner owner, long deviceId, int index) {
        for (String str : this.cmdMap.keySet()) {
            this.updateList.add(new UpdateBackDataRequest.RowsBean(deviceId, str, getConvertCmd(this.cmdMap.get(str)), index));
        }
        update(owner);
    }

    public void backupIndexData(LifecycleOwner owner, List<Long> deviceIds, int index) {
        for (String str : this.cmdMap.keySet()) {
            String convertCmd = getConvertCmd(this.cmdMap.get(str));
            Iterator<Long> it = deviceIds.iterator();
            while (it.hasNext()) {
                this.updateList.add(new UpdateBackDataRequest.RowsBean(it.next().longValue(), str, convertCmd, index));
            }
        }
        update(owner);
    }

    public void backupPlaceData(LifecycleOwner owner, String flag, BaseCmdParam cmdParam) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(flag, (Object) getConvertCmd(cmdParam));
        ((ObservableSubscribeProxy) Injection.net().backupPlaceData(Injection.repo().home().getSelPlace().getPlaceId(), jSONObject).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe();
    }

    public void backupSmartPanelDefault(final LifecycleOwner owner, final Device device) {
        ((ObservableSubscribeProxy) Injection.net().clearBackupData(device.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ReplaceHelper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ReplaceHelper.this.lambda$backupSmartPanelDefault$0(device, owner, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$backupSmartPanelDefault$0(Device device, LifecycleOwner lifecycleOwner, Object obj) throws Exception {
        SettingAssistant settingCmdAssistant = CmdAssistant.getSettingCmdAssistant(device, new int[0]);
        SmartPanelSettingState smartPanelSettingState = new SmartPanelSettingState();
        backupData(lifecycleOwner, device.getDeviceId(), UpdateBackDataRequest.NIGHT_MODE, settingCmdAssistant.setSmartPanelMode(smartPanelSettingState.getDoubleLight(), smartPanelSettingState.getReverseLight(), smartPanelSettingState.getNightMode(), smartPanelSettingState.getEngravedTextMode(), smartPanelSettingState.getStartH(), smartPanelSettingState.getStartM(), smartPanelSettingState.getEndH(), smartPanelSettingState.getEndM()));
    }

    public void backupEurPanelDefault(LifecycleOwner owner, Device device) {
        ((ObservableSubscribeProxy) Injection.net().clearBackupData(device.getDeviceId()).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.replace.ReplaceHelper$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ReplaceHelper.lambda$backupEurPanelDefault$1(obj);
            }
        });
    }

    private boolean isPanel() {
        Device device = this.parseDevice;
        if (device != null) {
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId) {
                case "4249823578721536":
                case "4002207473371776":
                case "4002205681371776":
                case "4002206372514432":
                case "4002206816422528":
                    return true;
            }
        }
        return false;
    }

    public int[] parseBackupData(JSONObject jsonObject, String key) {
        return parseBackupData(jsonObject, key, null);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public int[] parseBackupData(JSONObject jsonObject, String key, Device device) {
        int i;
        int i2;
        int i3;
        this.parseDevice = device;
        String str = (String) jsonObject.get(key);
        key.hashCode();
        char c2 = 65535;
        switch (key.hashCode()) {
            case -2005724787:
                if (key.equals(UpdateBackDataRequest.RGB_TYPE)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1810055904:
                if (key.equals("trigger_delay_1_1")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1810055903:
                if (key.equals("trigger_delay_1_2")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1734947331:
                if (key.equals(UpdateBackDataRequest.CT_DIM_TYPE)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1141280031:
                if (key.equals(UpdateBackDataRequest.INDICATOR_STATUS)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1135038327:
                if (key.equals(UpdateBackDataRequest.CT_RANGE)) {
                    c2 = 5;
                    break;
                }
                break;
            case -999214251:
                if (key.equals(UpdateBackDataRequest.NIGHT_MODE)) {
                    c2 = 6;
                    break;
                }
                break;
            case -385774284:
                if (key.equals(UpdateBackDataRequest.KNOB_SORT)) {
                    c2 = 7;
                    break;
                }
                break;
            case -140841207:
                if (key.equals(UpdateBackDataRequest.OUTPUT_TYPE)) {
                    c2 = '\b';
                    break;
                }
                break;
            case 388394921:
                if (key.equals("trigger_scene_1_1")) {
                    c2 = '\t';
                    break;
                }
                break;
            case 388394922:
                if (key.equals("trigger_scene_1_2")) {
                    c2 = '\n';
                    break;
                }
                break;
            case 440773975:
                if (key.equals(UpdateBackDataRequest.POWER_STATUS)) {
                    c2 = 11;
                    break;
                }
                break;
            case 525336425:
                if (key.equals(UpdateBackDataRequest.FADE_TIME)) {
                    c2 = '\f';
                    break;
                }
                break;
            case 685686128:
                if (key.equals(UpdateBackDataRequest.LIGHT_TYPE)) {
                    c2 = '\r';
                    break;
                }
                break;
            case 817078994:
                if (key.equals(UpdateBackDataRequest.BUZZER_STATUS)) {
                    c2 = 14;
                    break;
                }
                break;
            case 1340583030:
                if (key.equals(UpdateBackDataRequest.DALI_ON_OFF)) {
                    c2 = 15;
                    break;
                }
                break;
            case 1556367761:
                if (key.equals(UpdateBackDataRequest.DIM_DEPTH)) {
                    c2 = 16;
                    break;
                }
                break;
            case 1569175563:
                if (key.equals(UpdateBackDataRequest.DIM_RANGE)) {
                    c2 = 17;
                    break;
                }
                break;
            case 1588258248:
                if (key.equals(UpdateBackDataRequest.KNOB_DOUBLE_MEMORY)) {
                    c2 = 18;
                    break;
                }
                break;
            case 1852676677:
                if (key.equals(UpdateBackDataRequest.GATEWAY_NIGHT_MODE)) {
                    c2 = 19;
                    break;
                }
                break;
            case 1883641742:
                if (key.equals(UpdateBackDataRequest.POWER_FADE_TIME)) {
                    c2 = 20;
                    break;
                }
                break;
            case 1949704881:
                if (key.equals(UpdateBackDataRequest.CONTROL_INPUT_TYPE)) {
                    c2 = 21;
                    break;
                }
                break;
            case 1988243477:
                if (key.equals(UpdateBackDataRequest.SCENE_FADE_TIME)) {
                    c2 = 22;
                    break;
                }
                break;
            case 2111894089:
                if (key.equals(UpdateBackDataRequest.KNOB_SENSITIVITY)) {
                    c2 = 23;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case '\b':
            case 18:
                return new int[]{str != null ? Integer.parseInt(str.substring(8, 10), 16) : 0};
            case 1:
            case 2:
                int[] iArr = {0};
                if (str != null) {
                    iArr[0] = Integer.parseInt(str.substring(14, 16), 16);
                }
                return iArr;
            case 3:
            case 15:
                return new int[]{str != null ? Integer.parseInt(str.substring(8, 10), 16) : 1};
            case 4:
            case 14:
                if (str == null) {
                    return new int[]{1};
                }
                return new int[]{Integer.parseInt(str.substring(10, 12), 16)};
            case 5:
                if (str != null) {
                    i = Integer.parseInt(str.substring(8, 12), 16);
                    i2 = Integer.parseInt(str.substring(12, 16), 16);
                } else {
                    i = 2700;
                    i2 = 6500;
                }
                return new int[]{i, i2};
            case 6:
            case 19:
                int[] iArr2 = {0, 22, 0, 8, 0, 0};
                if (str != null) {
                    iArr2[0] = Integer.parseInt(str.substring(12, 14), 16);
                    iArr2[1] = Integer.parseInt(str.substring(16, 18), 16);
                    iArr2[2] = Integer.parseInt(str.substring(18, 20), 16);
                    iArr2[3] = Integer.parseInt(str.substring(20, 22), 16);
                    iArr2[4] = Integer.parseInt(str.substring(22, 24), 16);
                    iArr2[5] = Integer.parseInt(str.substring(14, 16), 16);
                }
                return iArr2;
            case 7:
                int[] iArr3 = {1, 2, 3};
                if (str != null) {
                    iArr3[0] = Integer.parseInt(str.substring(8, 10), 16);
                    iArr3[1] = Integer.parseInt(str.substring(10, 12), 16);
                    iArr3[2] = Integer.parseInt(str.substring(12, 14), 16);
                }
                return iArr3;
            case '\t':
            case '\n':
                int[] iArr4 = {0};
                if (str != null) {
                    iArr4[0] = Integer.parseInt(str.substring(12, 14), 16);
                }
                return iArr4;
            case 11:
                int i4 = isPanel() ? 3 : 1;
                if (str != null) {
                    return DataUtil.hexStringToInts(str.substring(10));
                }
                return new int[]{i4, 0, 0};
            case '\f':
                return new int[]{str != null ? Integer.parseInt(str.substring(8, 12), 16) * 100 : 3000, str != null ? Integer.parseInt(str.substring(12, 16), 16) * 100 : 3000};
            case '\r':
                return new int[]{str != null ? Integer.parseInt(str.substring(8, 10), 16) : 1};
            case 16:
                if (str != null) {
                    r7 = Integer.parseInt(str.substring(8, 10), 16);
                    r8 = Integer.parseInt(str.substring(10, 12), 16);
                }
                return new int[]{r7, r8};
            case 17:
                if (str != null) {
                    r7 = Integer.parseInt(str.substring(8, 10), 16);
                    i3 = Integer.parseInt(str.substring(10, 12), 16);
                } else {
                    i3 = 255;
                }
                return new int[]{r7, i3};
            case 20:
                return new int[]{str == null ? WinError.ERROR_VOLUME_NOT_SIS_ENABLED : Integer.parseInt(str.substring(8, 12), 16) * 100};
            case 21:
                int[] iArr5 = {1};
                if (str != null) {
                    iArr5[0] = Integer.parseInt(str.substring(8, 10), 16);
                }
                return iArr5;
            case 22:
                return new int[]{str != null ? Integer.parseInt(str.substring(8, 12), 16) * 100 : 3000};
            case 23:
                return new int[]{str == null ? 2 : Integer.parseInt(str.substring(8, 10), 16)};
            default:
                return new int[1];
        }
    }
}