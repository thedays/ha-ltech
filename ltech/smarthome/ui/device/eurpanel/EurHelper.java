package com.ltech.smarthome.ui.device.eurpanel;

import android.content.Context;
import android.os.SystemClock;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.EurPanelGroupParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.EurPanelSettingState;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class EurHelper {
    public static final int MULTI_ZONE = 15;
    public static final int ON_OFF_ZONE = 0;
    private static Device device;
    public static EurPanelGroupParam eurGroupParam;
    public static String paramExt;
    public static RelateInfoAssistant relateInfoAssistant;
    public static EurPanelSettingState settingState;

    public static String getEurProductId(Object object) {
        if (object instanceof Device) {
            return ((Device) object).getProductId();
        }
        if (object instanceof Group) {
            switch (ProductRepository.getLightColorType(object)) {
                case 22:
                    return ProductId.ID_EUR_PANEL_EB1;
                case 23:
                    return ProductId.ID_EUR_PANEL_EB2;
                case 24:
                    return ProductId.ID_EUR_PANEL_EB5;
                default:
                    return "";
            }
        }
        return "";
    }

    public static boolean isEb125(Object object) {
        int lightColorType = ProductRepository.getLightColorType(object);
        return lightColorType == 22 || lightColorType == 23 || lightColorType == 24;
    }

    public static boolean needPublishAddress(Object object) {
        if (object instanceof Device) {
            Device device2 = (Device) object;
            if (ProductId.ID_RC_B1.equals(device2.getProductId()) || ProductId.ID_RC_B2.equals(device2.getProductId()) || ProductId.ID_RC_B5.equals(device2.getProductId())) {
                return true;
            }
        }
        return isEb125(object);
    }

    public static int getUnicastAddress(Object object) {
        if (object instanceof Device) {
            return ((BleParam) ((Device) object).getParam(BleParam.class)).getUnicastAddress();
        }
        if (object instanceof Group) {
            return ((Group) object).getGroupAddress();
        }
        return 0;
    }

    public static int getPublishAddress(Object object) {
        if (object instanceof Device) {
            return ((BleParam) ((Device) object).getParam(BleParam.class)).getPublicationAddress();
        }
        if (object instanceof Group) {
            return ((EurPanelGroupParam) ((Group) object).getParam(EurPanelGroupParam.class)).getPublicationAddress();
        }
        return 0;
    }

    public static int convertType(Object object) {
        RelatedInfoExtParam relatedInfoExtParam = new RelatedInfoExtParam();
        if (object instanceof Device) {
            Device device2 = (Device) object;
            if (ProductId.ID_EUR_PANEL_EB1.equals(device2.getProductId()) || ProductId.ID_EUR_PANEL_EB2.equals(device2.getProductId()) || ProductId.ID_EUR_PANEL_EB5.equals(device2.getProductId())) {
                relatedInfoExtParam.fillMapWithString(device2.getExtParam());
            } else {
                return ProductRepository.getLightColorType((Object) device2);
            }
        } else if (object instanceof Group) {
            relatedInfoExtParam.fillMapWithString(((Group) object).getExtParam());
        }
        return relatedInfoExtParam.getColorType();
    }

    public static int getZoneNum(Object object) {
        RelatedInfoExtParam relatedInfoExtParam = new RelatedInfoExtParam();
        if (object instanceof Device) {
            relatedInfoExtParam.fillMapWithString(((Device) object).getExtParam());
        } else if (object instanceof Group) {
            relatedInfoExtParam.fillMapWithString(((Group) object).getExtParam());
        }
        return relatedInfoExtParam.getZoneNumber();
    }

    public static boolean isPositionBindScene(Object object, int position) {
        RelateInfoUtils.initRelateInfoList(object);
        RelateInfoItem relateInfoItem = RelateInfoUtils.relatedSceneInfoList.get(position);
        return (relateInfoItem.infoName == null || relateInfoItem.type == 0) ? false : true;
    }

    public static boolean hasBindNotEbLight(Object object) {
        Group groupByGroupId;
        RelateInfoUtils.initRelateInfoList(object);
        int zoneNumber = RelateInfoUtils.relateInfoAssistant.getZoneNumber();
        for (int i = 0; i < zoneNumber; i++) {
            RelateInfoItem relateInfoItem = RelateInfoUtils.relatedInfoList.get(i);
            if (relateInfoItem.relateInfo != null) {
                if (relateInfoItem.relateInfo.isRelateDeviceInfo()) {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfoItem.relateInfo.objectId);
                    if (deviceByDeviceId != null && !ProductRepository.isEbSupportLight(deviceByDeviceId)) {
                        return true;
                    }
                } else if (relateInfoItem.relateInfo.isRelateGroupInfo() && (groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfoItem.relateInfo.objectId)) != null && !ProductRepository.isEbSupportLight(groupByGroupId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasPositionBind(Object object) {
        RelateInfoUtils.initRelateSceneInfoList(object);
        for (int i = 0; i < 3; i++) {
            RelateInfoItem relateInfoItem = RelateInfoUtils.relatedSceneInfoList.get(i);
            if (relateInfoItem.type == 3 && relateInfoItem.relateInfo != null) {
                return (relateInfoItem.relateInfo.objectId > 0 && relateInfoItem.relateInfo.objectId <= 4) || relateInfoItem.infoName != null;
            }
        }
        return false;
    }

    public static void subscribePublishAdd(Context context, Object object, Object relateObject, int relatePosition, final IAction<Boolean> action) {
        int publishAddress = getPublishAddress(object);
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).subscribe(context, getUnicastAddress(relateObject), publishAddress, 2, 1 << relatePosition, 0, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.EurHelper$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                IAction.this.act(true);
            }
        });
    }

    public static void clearPublishAddress(Context context, Object object, int relatePosition) {
        int publishAddress = getPublishAddress(object);
        if (publishAddress != 0) {
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(context, publishAddress, 2, 1 << relatePosition);
        }
    }

    public static boolean isBindByRcb(Device ebDevice) {
        for (Device device2 : Injection.repo().device().getDeviceListByPlaceId(ebDevice.getPlaceId())) {
            if (ProductRepository.isRcB(device2.getProductId())) {
                RelatedInfoExtParam relatedInfoExtParam = new RelatedInfoExtParam();
                relatedInfoExtParam.fillMapWithString(device2.getExtParam());
                if (relatedInfoExtParam.getPanelId() == ebDevice.getDeviceId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void inGroup(Object object, final Device controlDevice, final IAction<Boolean> action) {
        int i;
        if (object instanceof Device) {
            Device device2 = (Device) object;
            relateInfoAssistant = new RelateInfoAssistant(device2);
            i = device2.getUnicastAddress();
        } else if (object instanceof Group) {
            Group group = (Group) object;
            relateInfoAssistant = new RelateInfoAssistant(group);
            i = group.getGroupAddress();
        } else {
            i = 0;
        }
        Injection.mesh().inGroupByCmd(controlDevice, i, getPublishAddress(object), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.EurHelper$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                EurHelper.lambda$inGroup$1(Device.this, action, (Boolean) obj);
            }
        });
    }

    static /* synthetic */ void lambda$inGroup$1(Device device2, IAction iAction, Boolean bool) {
        if (bool.booleanValue()) {
            setType(device2, iAction);
        } else {
            iAction.act(false);
        }
    }

    public static void setType(Device device2, final IAction<Boolean> action) {
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).sendEurDeviceType(ActivityUtils.getTopActivity(), CtrlPackager.getBleDeviceCtrlPackage(device2), relateInfoAssistant.getZoneNumber(), relateInfoAssistant.getColorType(), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.EurHelper$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                EurHelper.lambda$setType$2(IAction.this, (Boolean) obj);
            }
        });
    }

    static /* synthetic */ void lambda$setType$2(IAction iAction, Boolean bool) {
        if (iAction != null) {
            iAction.act(bool);
        }
    }

    public static void setRcbType(Device device2, final IAction<Boolean> action) {
        CmdAssistant.getSettingCmdAssistant(null, new int[0]).sendEurDeviceType(ActivityUtils.getTopActivity(), CtrlPackager.getBleDeviceCtrlPackage(device2), relateInfoAssistant.getZoneNumber(), ProductRepository.getLightColorType((Object) device2), new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.EurHelper$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                EurHelper.lambda$setRcbType$3(IAction.this, (Boolean) obj);
            }
        });
    }

    static /* synthetic */ void lambda$setRcbType$3(IAction iAction, Boolean bool) {
        if (iAction != null) {
            iAction.act(bool);
        }
    }

    public static void clearPublishAddress(Device controlDevice, IAction<Boolean> action) {
        device = controlDevice;
        int publishAddress = getPublishAddress(controlDevice);
        if (publishAddress != 0) {
            CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), publishAddress, ProductRepository.getAgreementIdByPid(device.getProductId()), new int[0]);
            SystemClock.sleep(500L);
            eurPanelSubscribe(relateInfoAssistant, -1, action);
        }
    }

    private static void eurPanelSubscribe(final RelateInfoAssistant relateInfoAssistant2, int relatePosition, final IAction<Boolean> action) {
        final int i = relatePosition + 1;
        if (i > 3) {
            if (action != null) {
                action.act(true);
                return;
            }
            return;
        }
        RelatedInfoExtParam.RelateInfo relateSceneInfo = relateInfoAssistant2.getRelateSceneInfo(i);
        if (relateSceneInfo != null && relateSceneInfo.objectId != 0) {
            boolean z = relateSceneInfo.objectId > 0 && relateSceneInfo.objectId <= 4;
            Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateSceneInfo.objectId);
            if (sceneBySceneId != null || z) {
                boolean z2 = z || sceneBySceneId.getSceneType() == 2;
                ArrayList arrayList = new ArrayList();
                arrayList.add(Integer.valueOf(z2 ? 3 : 2));
                arrayList.add(Integer.valueOf(relateSceneInfo.keyActionExtra));
                CmdAssistant.getSettingCmdAssistant(null, new int[0]).subscribeInEurPanel(ActivityUtils.getTopActivity(), 1 << i, ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), RelateInfoUtils.getRelateAddress(relateSceneInfo), 2, RelateInfoUtils.getRelateZone(relateSceneInfo), 2, 3, arrayList, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.EurHelper$$ExternalSyntheticLambda1
                    @Override // com.ltech.smarthome.base.IAction
                    public final void act(Object obj) {
                        EurHelper.lambda$eurPanelSubscribe$4(RelateInfoAssistant.this, i, action, (ResponseMsg) obj);
                    }
                });
                return;
            }
            eurPanelSubscribe(relateInfoAssistant2, i, action);
            return;
        }
        eurPanelSubscribe(relateInfoAssistant2, i, action);
    }

    static /* synthetic */ void lambda$eurPanelSubscribe$4(RelateInfoAssistant relateInfoAssistant2, int i, IAction iAction, ResponseMsg responseMsg) {
        if (responseMsg != null && responseMsg.getStateCode() == 0) {
            eurPanelSubscribe(relateInfoAssistant2, i, iAction);
        } else if (iAction != null) {
            iAction.act(false);
        }
    }

    public static void syncGroupSettings(final IAction<Boolean> action) {
        EurPanelSettingState eurPanelSettingState = settingState;
        if (eurPanelSettingState != null && eurPanelSettingState.dmxType != 0) {
            CmdAssistant.getLightCmdAssistant(device, new int[0]).setCtLightMode(ActivityUtils.getTopActivity(), settingState.dmxType, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.EurHelper$$ExternalSyntheticLambda5
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    EurHelper.lambda$syncGroupSettings$5(IAction.this, (Boolean) obj);
                }
            });
        } else {
            setBuzzerState(action);
        }
    }

    static /* synthetic */ void lambda$syncGroupSettings$5(IAction iAction, Boolean bool) {
        if (bool.booleanValue()) {
            setBuzzerState(iAction);
        }
    }

    public static void setBuzzerState(final IAction<Boolean> action) {
        EurPanelSettingState eurPanelSettingState = settingState;
        if (eurPanelSettingState != null && eurPanelSettingState.buzzerState != -1) {
            CmdAssistant.getLightCmdAssistant(device, new int[0]).setBuzzerState(ActivityUtils.getTopActivity(), 1, settingState.buzzerState, new IAction() { // from class: com.ltech.smarthome.ui.device.eurpanel.EurHelper$$ExternalSyntheticLambda4
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    IAction.this.act(Boolean.valueOf(r1 != null && r1.getStateCode() == 0));
                }
            });
        } else {
            action.act(false);
        }
    }
}