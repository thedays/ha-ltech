package com.ltech.smarthome.ui.device.smartpanel;

import android.app.Activity;
import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.blankj.utilcode.util.StringUtils;
import com.ltech.smarthome.MyApplication;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.smart.message.ResponseMsg;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yuyh.library.imgsel.utils.LogUtils;
import io.reactivex.functions.Consumer;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class RelaySeparationHelper {
    private static final String TAG = "RelaySeparationHelper";

    public static int getZoneNum(Object object) {
        int subkey;
        if (object instanceof Device) {
            Device device = (Device) object;
            if (device.getWifiMac().contains(ProductId.SPLIT)) {
                String[] split = device.getWifiMac().split(ProductId.SPLIT);
                if (split.length <= 1) {
                    return 1;
                }
                subkey = Integer.parseInt(split[1]);
                return 1 << (subkey - 1);
            }
            return 1;
        }
        if (object instanceof Group) {
            subkey = ((Group) object).getSubkey();
            return 1 << (subkey - 1);
        }
        return 1;
    }

    public static boolean isRelaySeparationSub(Object object) {
        if (object instanceof Device) {
            Device device = (Device) object;
            return device.isSubDevice() && device.getProductId().equalsIgnoreCase(ProductId.ID_BLE_SWITCH) && device.getMainProductId() != null && (device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_BLE_KBS) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S6_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S1C) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S2C) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S3_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S3C) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S4) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SWITCH_PANEL_S4M) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S6));
        }
        if (object instanceof Group) {
            Group group = (Group) object;
            if (group.getMaingroupid() > 0 && (ProductRepository.getMainGroupLightColorType(group) == 21 || ProductRepository.getMainGroupLightColorType(group) == 19 || ProductRepository.getMainGroupLightColorType(group) == 18 || ProductRepository.getMainGroupLightColorType(group) == 11 || ProductRepository.getMainGroupLightColorType(group) == 8 || ProductRepository.getMainGroupLightColorType(group) == 9 || ProductRepository.getMainGroupLightColorType(group) == 10)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRelaySeparationDevice(Object control) {
        Device deviceByDeviceId;
        int lightColorType = ProductRepository.getLightColorType(control);
        if (lightColorType == 21 || lightColorType == 19 || lightColorType == 26) {
            return true;
        }
        if (lightColorType == 8 || lightColorType == 9 || lightColorType == 10 || lightColorType == 18 || lightColorType == 11) {
            if (control instanceof Device) {
                BleParam bleParam = (BleParam) ((Device) control).getParam(BleParam.class);
                if (1068 == bleParam.getBleType() || 1069 == bleParam.getBleType() || 1070 == bleParam.getBleType() || 1064 == bleParam.getBleType() || 1065 == bleParam.getBleType() || 1066 == bleParam.getBleType() || 1067 == bleParam.getBleType() || 1071 == bleParam.getBleType()) {
                    return true;
                }
            } else if (control instanceof Group) {
                Group group = (Group) control;
                if (!group.getDeviceIds().isEmpty() && (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue())) != null) {
                    BleParam bleParam2 = (BleParam) deviceByDeviceId.getParam(BleParam.class);
                    if (1068 == bleParam2.getBleType() || 1069 == bleParam2.getBleType() || 1070 == bleParam2.getBleType() || 1064 == bleParam2.getBleType() || 1065 == bleParam2.getBleType() || 1066 == bleParam2.getBleType() || 1067 == bleParam2.getBleType() || 1071 == bleParam2.getBleType()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isRelaySeparationPanelDevice(Object control) {
        Device deviceByDeviceId;
        int lightColorType = ProductRepository.getLightColorType(control);
        if (lightColorType == 21 || lightColorType == 19) {
            return true;
        }
        if (lightColorType == 8 || lightColorType == 9 || lightColorType == 10 || lightColorType == 18 || lightColorType == 11) {
            if (control instanceof Device) {
                BleParam bleParam = (BleParam) ((Device) control).getParam(BleParam.class);
                if (1068 == bleParam.getBleType() || 1069 == bleParam.getBleType() || 1070 == bleParam.getBleType() || 1064 == bleParam.getBleType() || 1065 == bleParam.getBleType() || 1066 == bleParam.getBleType() || 1067 == bleParam.getBleType() || 1071 == bleParam.getBleType()) {
                    return true;
                }
            } else if (control instanceof Group) {
                Group group = (Group) control;
                if (!group.getDeviceIds().isEmpty() && (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue())) != null) {
                    BleParam bleParam2 = (BleParam) deviceByDeviceId.getParam(BleParam.class);
                    if (1068 == bleParam2.getBleType() || 1069 == bleParam2.getBleType() || 1070 == bleParam2.getBleType() || 1064 == bleParam2.getBleType() || 1065 == bleParam2.getBleType() || 1066 == bleParam2.getBleType() || 1067 == bleParam2.getBleType() || 1071 == bleParam2.getBleType()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isPanelRelay(Object object) {
        if (object instanceof Device) {
            Device device = (Device) object;
            return device.isSubDevice() && device.getMainProductId() != null && (device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_PANEL_G4) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S6_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S6) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S4) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S1C) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S2C) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S3_PRO) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SMART_SWITCH_S3C) || device.getMainProductId().equalsIgnoreCase(ProductId.ID_SWITCH_PANEL_S4M));
        }
        if (object instanceof Group) {
            Group group = (Group) object;
            if (group.getMaingroupid() > 0 && (ProductRepository.getMainGroupLightColorType(group) == 21 || ProductRepository.getMainGroupLightColorType(group) == 19 || ProductRepository.getMainGroupLightColorType(group) == 18 || ProductRepository.getMainGroupLightColorType(group) == 11 || ProductRepository.getMainGroupLightColorType(group) == 8 || ProductRepository.getMainGroupLightColorType(group) == 9 || ProductRepository.getMainGroupLightColorType(group) == 10)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSwitchRelay(Object object) {
        if (!(object instanceof Device)) {
            return false;
        }
        Device device = (Device) object;
        return device.isSubDevice() && device.getMainProductId() != null && device.getMainProductId().equalsIgnoreCase(ProductId.ID_BLE_KBS);
    }

    public static int getRelayNum(Object object) {
        try {
            if (object instanceof Device) {
                Device device = (Device) object;
                if (device.getWifiMac().contains(ProductId.SPLIT)) {
                    String[] split = device.getWifiMac().split(ProductId.SPLIT);
                    if (split.length > 1) {
                        return Integer.parseInt(split[1]);
                    }
                }
            } else if (object instanceof Group) {
                Group group = (Group) object;
                if (group.getMaingroupid() > 0) {
                    return group.getSubkey();
                }
            }
            return 0;
        } catch (Exception e) {
            LogUtils.e(TAG, e);
            return 0;
        }
    }

    public static Device getRelaySubDevice(Device mainDevice, int index) {
        try {
            int i = 0;
            for (Device device : Injection.repo().device().getSubDevice(mainDevice.getPlaceId(), mainDevice.getDeviceId())) {
                if (device.getWifiMac().contains(ProductId.SPLIT)) {
                    String[] split = device.getWifiMac().split(ProductId.SPLIT);
                    if (split.length > 1) {
                        i = Integer.parseInt(split[1]);
                    }
                }
                if (index == i) {
                    return device;
                }
            }
            return null;
        } catch (Exception e) {
            LogUtils.e(TAG, e);
            return null;
        }
    }

    public static String[] getRelaySubNameArray(Object mainObject) {
        int i = 0;
        if (mainObject instanceof Device) {
            Device device = (Device) mainObject;
            List<Device> subDevice = Injection.repo().device().getSubDevice(device.getPlaceId(), device.getDeviceId());
            Collections.sort(subDevice, new Comparator<Device>() { // from class: com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper.1
                @Override // java.util.Comparator
                public int compare(Device o1, Device o2) {
                    return o1.getWifiMac().compareTo(o2.getWifiMac());
                }
            });
            int size = subDevice.size();
            String[] strArr = new String[size];
            while (i < size) {
                strArr[i] = subDevice.get(i).getDeviceName();
                i++;
            }
            return strArr;
        }
        if (!(mainObject instanceof Group)) {
            return null;
        }
        Group group = (Group) mainObject;
        List<Group> subGroup = Injection.repo().group().getSubGroup(group.getPlaceId(), group.getGroupId());
        Collections.sort(subGroup, new Comparator<Group>() { // from class: com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper.2
            @Override // java.util.Comparator
            public int compare(Group o1, Group o2) {
                return o1.getSubkey() - o2.getSubkey();
            }
        });
        int size2 = subGroup.size();
        String[] strArr2 = new String[size2];
        while (i < size2) {
            strArr2[i] = subGroup.get(i).getGroupName();
            i++;
        }
        return strArr2;
    }

    public static Group getRelaySubGroup(Group mainGroup, int index) {
        int i = 0;
        for (Group group : Injection.repo().group().getSubGroup(mainGroup.getPlaceId(), mainGroup.getGroupId())) {
            if (group.getMaingroupid() > 0) {
                i = group.getSubkey();
            }
            if (index == i) {
                return group;
            }
        }
        return null;
    }

    public static void cleanScreenData(Activity context, int position, Object object, RelateInfoAssistant relateInfoAssistant, boolean needUpdate, IAction<Integer> iAction) {
        setScreenData(context, position, "", object, relateInfoAssistant, 0, 0, needUpdate, iAction);
    }

    public static void setScreenData(Activity context, int position, String string, Object object, RelateInfoAssistant relateInfoAssistant, boolean needUpdate, IAction<Integer> iAction) {
        setScreenData(context, position, string, object, relateInfoAssistant, 1, 1, needUpdate, iAction);
    }

    public static void setScreenData(final Activity context, final int position, String string, final Object object, final RelateInfoAssistant relateInfoAssistant, final int firstType, final int secondType, final boolean needUpdate, final IAction<Integer> iAction) {
        byte[] bArr;
        final String string2 = getString(string, relateInfoAssistant != null && relateInfoAssistant.getSwitchScreenBigIcon() == 2);
        byte[] bArr2 = null;
        try {
            String[] split = string2.split("\n");
            bArr = split[0].getBytes("gb2312");
            try {
                if (split.length > 1) {
                    bArr2 = split[1].getBytes("gb2312");
                }
            } catch (UnsupportedEncodingException e) {
                e = e;
                e.printStackTrace();
                final byte[] bArr3 = bArr2;
                final byte[] bArr4 = bArr;
                iAction.act(0);
                CmdAssistant.getDeviceAssistant(object, new int[0]).setPanelScreenData(context, position, firstType, bArr4, secondType, bArr3, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper$$ExternalSyntheticLambda0
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        RelaySeparationHelper.lambda$setScreenData$0(IAction.this, relateInfoAssistant, position, string2, object, needUpdate, context, firstType, bArr4, secondType, bArr3, (ResponseMsg) obj);
                    }
                });
            }
        } catch (UnsupportedEncodingException e2) {
            e = e2;
            bArr = null;
        }
        final byte[] bArr32 = bArr2;
        final byte[] bArr42 = bArr;
        iAction.act(0);
        CmdAssistant.getDeviceAssistant(object, new int[0]).setPanelScreenData(context, position, firstType, bArr42, secondType, bArr32, new IAction() { // from class: com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                RelaySeparationHelper.lambda$setScreenData$0(IAction.this, relateInfoAssistant, position, string2, object, needUpdate, context, firstType, bArr42, secondType, bArr32, (ResponseMsg) obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$setScreenData$0(IAction iAction, RelateInfoAssistant relateInfoAssistant, int i, String str, Object obj, boolean z, Activity activity, int i2, byte[] bArr, int i3, byte[] bArr2, ResponseMsg responseMsg) {
        int value;
        if (responseMsg != null && (responseMsg.getStateCode() == 0 || responseMsg.getStateCode() == 153)) {
            iAction.act(1);
            if (relateInfoAssistant != null) {
                RelatedInfoExtParam.RelateInfo relateInfo = relateInfoAssistant.getRelateInfo(i);
                if (relateInfo == null) {
                    relateInfo = new RelatedInfoExtParam.RelateInfo();
                }
                if (StringUtils.isEmpty(str)) {
                    value = RelatedInfoExtParam.ScreenType.ScreenTypeNone.getValue();
                } else {
                    value = RelatedInfoExtParam.ScreenType.ScreenTypeWord.getValue();
                }
                relateInfo.screenType = value;
                relateInfo.screenStr = str;
                relateInfo.iconIndex = 0;
                relateInfoAssistant.setRelateInfo(i, relateInfo);
                if (obj instanceof Device) {
                    Device device = (Device) obj;
                    if (z) {
                        updateParamExt(activity, device, relateInfoAssistant.getExtParamString());
                    }
                    ReplaceHelper.instance().addBackupData(UpdateBackDataRequest.TEXT, CmdAssistant.getDeviceAssistant(device, new int[0]).setPanelScreenData(i, i2, bArr, i3, bArr2));
                    ReplaceHelper.instance().backupIndexData((LifecycleOwner) activity, device.getDeviceId(), i + 1);
                    return;
                }
                if (obj instanceof Group) {
                    Group group = (Group) obj;
                    if (z) {
                        updateParamExt(activity, group, relateInfoAssistant.getExtParamString());
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        iAction.act(2);
    }

    private static String getString(String input, boolean isOpenElderLyMode) {
        int i = isOpenElderLyMode ? 9 : 12;
        if (getEncodeLength(input) <= i) {
            return input;
        }
        if (!input.contains("\n")) {
            for (int i2 = 1; i2 <= input.length(); i2++) {
                if (getEncodeLength(input.substring(0, i2)) > i) {
                    StringBuilder sb = new StringBuilder();
                    int i3 = i2 - 1;
                    sb.append(input.substring(0, i3));
                    sb.append("\n");
                    sb.append(input.substring(i3));
                    return sb.toString();
                }
            }
            return input;
        }
        String[] split = input.split("\n");
        if (split.length <= 1 || getEncodeLength(split[1]) <= i) {
            return input;
        }
        return split[0] + "\n" + com.smart.message.utils.StringUtils.getSecondLine(split[1], isOpenElderLyMode);
    }

    private static int getEncodeLength(String input) {
        try {
            return input.getBytes("gbk").length;
        } catch (UnsupportedEncodingException unused) {
            return input.length();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void updateParamExt(Activity context, final Device device, final String extParam) {
        ((ObservableSubscribeProxy) Injection.net().updateParamExt(device.getDeviceId(), extParam).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) context, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RelaySeparationHelper.lambda$updateParamExt$1(Device.this, extParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    static /* synthetic */ void lambda$updateParamExt$1(Device device, String str, Object obj) throws Exception {
        device.setExtParam(str);
        Injection.repo().device().saveDevice(device);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void updateParamExt(Activity context, final Group group, final String extParam) {
        ((ObservableSubscribeProxy) Injection.net().updateGroupParamExt(group.getGroupId(), extParam).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) context, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RelaySeparationHelper.lambda$updateParamExt$2(Group.this, extParam, obj);
            }
        }, new SmartErrorComsumer());
    }

    static /* synthetic */ void lambda$updateParamExt$2(Group group, String str, Object obj) throws Exception {
        group.setExtParam(str);
        Injection.repo().group().saveGroup(group);
    }

    public static void removeSub(Device device) {
        if (device != null) {
            Iterator<Device> it = Injection.repo().device().getSubDevice(device.getPlaceId(), device.getDeviceId()).iterator();
            while (it.hasNext()) {
                Injection.repo().device().removeDeviceFromDb(it.next().getId());
            }
        }
    }

    public static void removeSub(Group group) {
        if (group != null) {
            Injection.repo().group().removeSubGroupFromDb(group.getPlaceId(), group.getGroupId());
        }
    }

    public static void resetSubName(Context context, Device device) {
        for (Device device2 : Injection.repo().device().getSubDevice(device.getPlaceId(), device.getDeviceId())) {
            changeDeviceName(context, device2, MyApplication.getContext().getString(R.string.on_off) + getRelayNum(device2));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void changeDeviceName(Context context, final Device device, final String name) {
        ((ObservableSubscribeProxy) Injection.net().updateDeviceNameAndImgIndex(device.getDeviceId(), name, 0).delaySubscription(500L, TimeUnit.MILLISECONDS).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) context, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RelaySeparationHelper.lambda$changeDeviceName$3(Device.this, name, obj);
            }
        }, new SmartErrorComsumer());
    }

    static /* synthetic */ void lambda$changeDeviceName$3(Device device, String str, Object obj) throws Exception {
        device.setDeviceName(str);
        Injection.repo().device().saveDevice(device);
    }

    public static Group setSubGroupOnOff(Group group, int i, boolean b2) {
        Group relaySubGroup = getRelaySubGroup(group, i);
        if (relaySubGroup != null && relaySubGroup.getGroupState() != null && relaySubGroup.getGroupState().isOn() != b2) {
            relaySubGroup.getGroupState().setOn(b2);
            Injection.repo().group().saveGroup(relaySubGroup);
        }
        return relaySubGroup;
    }

    public static void setSubGroupOnOff(Group group, boolean b2) {
        for (Group group2 : Injection.repo().group().getSubGroup(group.getPlaceId(), group.getGroupId())) {
            if (group2 != null && group2.getGroupState() != null && group2.getGroupState().isOn() != b2) {
                group2.getGroupState().setOn(b2);
                Injection.repo().group().saveGroup(group2);
            }
        }
    }

    public static Group getMainGroup(Group group) {
        return Injection.repo().group().getGroupByGroupId(group.getMaingroupid());
    }

    public static Group changeMainGroupRelayOnOff(Group group) {
        int relayNum = getRelayNum(group);
        Group mainGroup = getMainGroup(group);
        if (mainGroup == null || !mainGroup.getDeviceIds().isEmpty()) {
            return null;
        }
        int i = relayNum - 1;
        if (mainGroup.getGroupState().getOnOffStates().get(i).booleanValue() == group.getGroupState().isOn()) {
            return null;
        }
        mainGroup.getGroupState().getOnOffStates().set(i, Boolean.valueOf(group.getGroupState().isOn()));
        Injection.repo().group().saveGroup(mainGroup);
        return mainGroup;
    }
}