package com.ltech.smarthome.utils.relate_assistant;

import android.app.Activity;
import android.text.TextUtils;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.ModeContent;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.Scene;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainMotorInfoExtParam;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectActionVM;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.view.dialog.ImageTipDialog;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class RelateInfoUtils {
    public static long lastBindTime = 0;
    public static List<RelateInfoItem> longClickRelatedInfoList = null;
    public static RelateInfoAssistant relateInfoAssistant = null;
    public static List<RelateInfoItem> relatedInfoList = null;
    public static List<RelateInfoItem> relatedSceneInfoList = null;
    public static int timeToLowPower = 20;

    public static RelateInfoAssistant initSmartPanelRelateInfoList(String productId) {
        relateInfoAssistant = new RelateInfoAssistant();
        int zoneCount = ProductRepository.getZoneCount(productId);
        int i = 0;
        while (i < zoneCount) {
            int i2 = i + 1;
            RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
            if (ProductId.ID_SCENE_PANEL_S8.equals(productId) || ProductId.ID_SMART_PANEL_S6B.equals(productId) || ProductId.ID_SMART_PANEL_G4_PRO.equals(productId) || ProductId.ID_SMART_PANEL_G4.equals(productId) || ProductId.ID_SMART_SWITCH_S6_PRO.equals(productId) || ProductId.ID_SMART_SWITCH_S6.equals(productId)) {
                relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_str_key) + i2;
                if ((ProductId.ID_SMART_PANEL_G4_PRO.equals(productId) || ProductId.ID_SMART_PANEL_G4.equals(productId)) && i2 < 5) {
                    relateInfo.switchIndex = i2;
                } else {
                    if ((ProductId.ID_SMART_SWITCH_S6_PRO.equals(productId) || ProductId.ID_SMART_SWITCH_S6.equals(productId)) & (i2 < 4)) {
                        relateInfo.switchIndex = i2;
                    }
                }
            } else {
                if (ProductId.ID_SMART_SWITCH_S1C.equals(productId) || ProductId.ID_SMART_SWITCH_S1_PRO.equals(productId) || ProductId.ID_SMART_SWITCH_S2C.equals(productId) || ProductId.ID_SMART_SWITCH_S2_PRO.equals(productId) || ProductId.ID_SMART_SWITCH_S3C.equals(productId) || ProductId.ID_SMART_SWITCH_S3_PRO.equals(productId) || ProductId.ID_SMART_SWITCH_S4.equals(productId) || ProductId.ID_SWITCH_PANEL_S4M.equals(productId)) {
                    relateInfo.switchIndex = i2;
                }
                relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_str_switch) + i2;
            }
            relateInfoAssistant.setRelateInfo(i, relateInfo);
            i = i2;
        }
        relateInfoAssistant.setZoneNumber(zoneCount);
        return relateInfoAssistant;
    }

    public static RelateInfoAssistant initEurPanel(int zoneNumber, int colorType) {
        RelateInfoAssistant relateInfoAssistant2 = new RelateInfoAssistant();
        relateInfoAssistant = relateInfoAssistant2;
        relateInfoAssistant2.setZoneNumber(zoneNumber);
        relateInfoAssistant.setColorType(colorType);
        int i = 0;
        while (i < zoneNumber) {
            RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
            StringBuilder sb = new StringBuilder();
            sb.append(ActivityUtils.getTopActivity().getString(R.string.zone));
            int i2 = i + 1;
            sb.append(i2);
            relateInfo.name = sb.toString();
            relateInfoAssistant.setRelateInfo(i, relateInfo);
            i = i2;
        }
        return relateInfoAssistant;
    }

    public static RelateInfoAssistant initSmartPanelRelateInfoList(int colorType) {
        relateInfoAssistant = new RelateInfoAssistant();
        int zoneCount = ProductRepository.getZoneCount(colorType);
        int i = 0;
        while (i < zoneCount) {
            int i2 = i + 1;
            RelatedInfoExtParam.RelateInfo relateInfo = new RelatedInfoExtParam.RelateInfo();
            if (21 == colorType || 19 == colorType) {
                relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_str_key) + i2;
                if (21 != colorType || i2 >= 5) {
                    if ((19 == colorType) & (i2 < 4)) {
                        relateInfo.switchIndex = i2;
                    }
                } else {
                    relateInfo.switchIndex = i2;
                }
            } else {
                if (8 == colorType || 9 == colorType || 10 == colorType || 18 == colorType || 11 == colorType) {
                    relateInfo.switchIndex = i2;
                }
                relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_str_switch) + i2;
            }
            relateInfoAssistant.setRelateInfo(i, relateInfo);
            i = i2;
        }
        relateInfoAssistant.setZoneNumber(zoneCount);
        return relateInfoAssistant;
    }

    public static void initRelateInfoList(Object object) {
        int zoneNumber;
        int zoneNumber2;
        int i = 0;
        if (object instanceof Device) {
            Device device = (Device) object;
            relateInfoAssistant = new RelateInfoAssistant(device);
            if (device.getProductId().equals(ProductId.ID_EUR_PANEL_EB1) || device.getProductId().equals(ProductId.ID_EUR_PANEL_EB2) || device.getProductId().equals(ProductId.ID_EUR_PANEL_EB5) || device.getProductId().equals(ProductId.ID_EUR_PANEL_EB6) || device.getProductId().equals(ProductId.ID_AS_PANEL_U1S) || device.getProductId().equals(ProductId.ID_AS_PANEL_U2S) || device.getProductId().equals(ProductId.ID_AS_PANEL_U4S) || device.getProductId().equals(ProductId.ID_AS_PANEL_U5S)) {
                zoneNumber2 = relateInfoAssistant.getZoneNumber();
            } else {
                zoneNumber2 = ProductRepository.getZoneCount(device.getProductId());
            }
            List<RelateInfoItem> list = relatedInfoList;
            if (list == null || list.size() != zoneNumber2) {
                relatedInfoList = new ArrayList();
                for (int i2 = 0; i2 < zoneNumber2; i2++) {
                    RelateInfoItem relateInfoItem = new RelateInfoItem();
                    relateInfoItem.relateInfo = relateInfoAssistant.getRelateInfo(i2);
                    relateInfoItem.infoName = getRelateInfoString(relateInfoItem.relateInfo);
                    relateInfoItem.iconRes = getRelateInfoIcon(relateInfoItem.relateInfo);
                    relateInfoItem.actionInfo = getRelateInfo(relateInfoItem.relateInfo);
                    relateInfoItem.type = getRelateInfoType(relateInfoItem.relateInfo);
                    relatedInfoList.add(relateInfoItem);
                }
            } else {
                for (int i3 = 0; i3 < zoneNumber2; i3++) {
                    relatedInfoList.get(i3).relateInfo = relateInfoAssistant.getRelateInfo(i3);
                    relatedInfoList.get(i3).infoName = getRelateInfoString(relateInfoAssistant.getRelateInfo(i3));
                    relatedInfoList.get(i3).iconRes = getRelateInfoIcon(relateInfoAssistant.getRelateInfo(i3));
                    relatedInfoList.get(i3).actionInfo = getRelateInfo(relateInfoAssistant.getRelateInfo(i3));
                    relatedInfoList.get(i3).type = getRelateInfoType(relateInfoAssistant.getRelateInfo(i3));
                }
            }
            relateInfoAssistant.setZoneNumber(zoneNumber2);
            List<RelateInfoItem> list2 = longClickRelatedInfoList;
            if (list2 == null || list2.size() != zoneNumber2) {
                longClickRelatedInfoList = new ArrayList();
                for (int i4 = 0; i4 < zoneNumber2; i4++) {
                    RelateInfoItem relateInfoItem2 = new RelateInfoItem();
                    relateInfoItem2.relateInfo = relateInfoAssistant.getRelateLongClickInfo(i4);
                    relateInfoItem2.infoName = getRelateInfoString(relateInfoItem2.relateInfo);
                    relateInfoItem2.iconRes = getRelateInfoIcon(relateInfoItem2.relateInfo);
                    relateInfoItem2.actionInfo = getRelateInfo(relateInfoItem2.relateInfo);
                    relateInfoItem2.type = getRelateInfoType(relateInfoItem2.relateInfo);
                    longClickRelatedInfoList.add(relateInfoItem2);
                }
            } else {
                for (int i5 = 0; i5 < zoneNumber2; i5++) {
                    longClickRelatedInfoList.get(i5).relateInfo = relateInfoAssistant.getRelateLongClickInfo(i5);
                    longClickRelatedInfoList.get(i5).infoName = getRelateInfoString(relateInfoAssistant.getRelateInfo(i5));
                    longClickRelatedInfoList.get(i5).iconRes = getRelateInfoIcon(relateInfoAssistant.getRelateInfo(i5));
                    longClickRelatedInfoList.get(i5).actionInfo = getRelateInfo(relateInfoAssistant.getRelateInfo(i5));
                    longClickRelatedInfoList.get(i5).type = getRelateInfoType(relateInfoAssistant.getRelateInfo(i5));
                }
            }
            int sceneCount = ProductRepository.getSceneCount(device.getProductId());
            List<RelateInfoItem> list3 = relatedSceneInfoList;
            if (list3 != null && list3.size() == sceneCount) {
                while (i < sceneCount) {
                    relatedSceneInfoList.get(i).relateInfo = relateInfoAssistant.getRelateSceneInfo(i);
                    relatedSceneInfoList.get(i).infoName = getRelateInfoString(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).iconRes = getRelateInfoIcon(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).actionInfo = getRelateInfo(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).type = getRelateInfoType(relateInfoAssistant.getRelateSceneInfo(i));
                    i++;
                }
                return;
            }
            relatedSceneInfoList = new ArrayList();
            while (i < sceneCount) {
                RelateInfoItem relateInfoItem3 = new RelateInfoItem();
                relateInfoItem3.relateInfo = relateInfoAssistant.getRelateSceneInfo(i);
                relateInfoItem3.infoName = getRelateInfoString(relateInfoItem3.relateInfo);
                relateInfoItem3.iconRes = getRelateInfoIcon(relateInfoItem3.relateInfo);
                relateInfoItem3.actionInfo = getRelateInfo(relateInfoItem3.relateInfo);
                relateInfoItem3.type = getRelateInfoType(relateInfoItem3.relateInfo);
                relatedSceneInfoList.add(relateInfoItem3);
                i++;
            }
            return;
        }
        if (object instanceof Group) {
            Group group = (Group) object;
            relateInfoAssistant = new RelateInfoAssistant(group);
            if (AsHelper.isNewUb(group) || EurHelper.isEb125(group)) {
                zoneNumber = relateInfoAssistant.getZoneNumber();
            } else {
                zoneNumber = ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) group));
            }
            List<RelateInfoItem> list4 = relatedInfoList;
            if (list4 == null || list4.size() != zoneNumber) {
                relatedInfoList = new ArrayList();
                for (int i6 = 0; i6 < zoneNumber; i6++) {
                    RelateInfoItem relateInfoItem4 = new RelateInfoItem();
                    relateInfoItem4.relateInfo = relateInfoAssistant.getRelateInfo(i6);
                    relateInfoItem4.infoName = getRelateInfoString(relateInfoItem4.relateInfo);
                    relateInfoItem4.iconRes = getRelateInfoIcon(relateInfoItem4.relateInfo);
                    relateInfoItem4.actionInfo = getRelateInfo(relateInfoItem4.relateInfo);
                    relateInfoItem4.type = getRelateInfoType(relateInfoItem4.relateInfo);
                    relatedInfoList.add(relateInfoItem4);
                }
            } else {
                for (int i7 = 0; i7 < zoneNumber; i7++) {
                    relatedInfoList.get(i7).relateInfo = relateInfoAssistant.getRelateInfo(i7);
                    relatedInfoList.get(i7).infoName = getRelateInfoString(relateInfoAssistant.getRelateInfo(i7));
                    relatedInfoList.get(i7).iconRes = getRelateInfoIcon(relateInfoAssistant.getRelateInfo(i7));
                    relatedInfoList.get(i7).actionInfo = getRelateInfo(relateInfoAssistant.getRelateInfo(i7));
                    relatedInfoList.get(i7).type = getRelateInfoType(relateInfoAssistant.getRelateInfo(i7));
                }
            }
            relateInfoAssistant.setZoneNumber(zoneNumber);
            List<RelateInfoItem> list5 = longClickRelatedInfoList;
            if (list5 == null || list5.size() != zoneNumber) {
                longClickRelatedInfoList = new ArrayList();
                for (int i8 = 0; i8 < zoneNumber; i8++) {
                    RelateInfoItem relateInfoItem5 = new RelateInfoItem();
                    relateInfoItem5.relateInfo = relateInfoAssistant.getRelateLongClickInfo(i8);
                    relateInfoItem5.infoName = getRelateInfoString(relateInfoItem5.relateInfo);
                    relateInfoItem5.iconRes = getRelateInfoIcon(relateInfoItem5.relateInfo);
                    relateInfoItem5.actionInfo = getRelateInfo(relateInfoItem5.relateInfo);
                    relateInfoItem5.type = getRelateInfoType(relateInfoItem5.relateInfo);
                    longClickRelatedInfoList.add(relateInfoItem5);
                }
            } else {
                for (int i9 = 0; i9 < zoneNumber; i9++) {
                    longClickRelatedInfoList.get(i9).relateInfo = relateInfoAssistant.getRelateLongClickInfo(i9);
                    longClickRelatedInfoList.get(i9).infoName = getRelateInfoString(relateInfoAssistant.getRelateInfo(i9));
                    longClickRelatedInfoList.get(i9).iconRes = getRelateInfoIcon(relateInfoAssistant.getRelateInfo(i9));
                    longClickRelatedInfoList.get(i9).actionInfo = getRelateInfo(relateInfoAssistant.getRelateInfo(i9));
                    longClickRelatedInfoList.get(i9).type = getRelateInfoType(relateInfoAssistant.getRelateInfo(i9));
                }
            }
            int i10 = (ProductRepository.getLightColorType((Object) group) == 22 || ProductRepository.getLightColorType((Object) group) == 23 || ProductRepository.getLightColorType((Object) group) == 24 || ProductRepository.getLightColorType((Object) group) == 27 || ProductRepository.getLightColorType((Object) group) == 28 || ProductRepository.getLightColorType((Object) group) == 29 || ProductRepository.getLightColorType((Object) group) == 30) ? 4 : 0;
            List<RelateInfoItem> list6 = relatedSceneInfoList;
            if (list6 != null && list6.size() == i10) {
                while (i < i10) {
                    relatedSceneInfoList.get(i).relateInfo = relateInfoAssistant.getRelateSceneInfo(i);
                    relatedSceneInfoList.get(i).infoName = getRelateInfoString(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).iconRes = getRelateInfoIcon(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).actionInfo = getRelateInfo(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).type = getRelateInfoType(relateInfoAssistant.getRelateSceneInfo(i));
                    i++;
                }
                return;
            }
            relatedSceneInfoList = new ArrayList();
            while (i < i10) {
                RelateInfoItem relateInfoItem6 = new RelateInfoItem();
                relateInfoItem6.relateInfo = relateInfoAssistant.getRelateSceneInfo(i);
                relateInfoItem6.infoName = getRelateInfoString(relateInfoItem6.relateInfo);
                relateInfoItem6.iconRes = getRelateInfoIcon(relateInfoItem6.relateInfo);
                relateInfoItem6.actionInfo = getRelateInfo(relateInfoItem6.relateInfo);
                relateInfoItem6.type = getRelateInfoType(relateInfoItem6.relateInfo);
                relatedSceneInfoList.add(relateInfoItem6);
                i++;
            }
        }
    }

    public static void initRelateSceneInfoList(Object object) {
        int i = 0;
        if (object instanceof Device) {
            Device device = (Device) object;
            relateInfoAssistant = new RelateInfoAssistant(device);
            int sceneCount = ProductRepository.getSceneCount(device.getProductId());
            List<RelateInfoItem> list = relatedSceneInfoList;
            if (list != null && list.size() == sceneCount) {
                while (i < sceneCount) {
                    relatedSceneInfoList.get(i).relateInfo = relateInfoAssistant.getRelateSceneInfo(i);
                    relatedSceneInfoList.get(i).infoName = getRelateInfoString(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).iconRes = getRelateInfoIcon(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).actionInfo = getRelateInfo(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).type = getRelateInfoType(relateInfoAssistant.getRelateSceneInfo(i));
                    i++;
                }
                return;
            }
            relatedSceneInfoList = new ArrayList();
            while (i < sceneCount) {
                RelateInfoItem relateInfoItem = new RelateInfoItem();
                relateInfoItem.relateInfo = relateInfoAssistant.getRelateSceneInfo(i);
                relateInfoItem.infoName = getRelateInfoString(relateInfoItem.relateInfo);
                relateInfoItem.iconRes = getRelateInfoIcon(relateInfoItem.relateInfo);
                relateInfoItem.actionInfo = getRelateInfo(relateInfoItem.relateInfo);
                relateInfoItem.type = getRelateInfoType(relateInfoItem.relateInfo);
                relatedSceneInfoList.add(relateInfoItem);
                i++;
            }
            return;
        }
        if (object instanceof Group) {
            Group group = (Group) object;
            int i2 = (ProductRepository.getLightColorType((Object) group) == 22 || ProductRepository.getLightColorType((Object) group) == 23 || ProductRepository.getLightColorType((Object) group) == 24 || ProductRepository.getLightColorType((Object) group) == 27 || ProductRepository.getLightColorType((Object) group) == 28 || ProductRepository.getLightColorType((Object) group) == 29 || ProductRepository.getLightColorType((Object) group) == 30) ? 4 : 0;
            List<RelateInfoItem> list2 = relatedSceneInfoList;
            if (list2 != null && list2.size() == i2) {
                while (i < i2) {
                    relatedSceneInfoList.get(i).relateInfo = relateInfoAssistant.getRelateSceneInfo(i);
                    relatedSceneInfoList.get(i).infoName = getRelateInfoString(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).iconRes = getRelateInfoIcon(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).actionInfo = getRelateInfo(relateInfoAssistant.getRelateSceneInfo(i));
                    relatedSceneInfoList.get(i).type = getRelateInfoType(relateInfoAssistant.getRelateSceneInfo(i));
                    i++;
                }
                return;
            }
            relatedSceneInfoList = new ArrayList();
            while (i < i2) {
                RelateInfoItem relateInfoItem2 = new RelateInfoItem();
                relateInfoItem2.relateInfo = relateInfoAssistant.getRelateSceneInfo(i);
                relateInfoItem2.infoName = getRelateInfoString(relateInfoItem2.relateInfo);
                relateInfoItem2.iconRes = getRelateInfoIcon(relateInfoItem2.relateInfo);
                relateInfoItem2.actionInfo = getRelateInfo(relateInfoItem2.relateInfo);
                relateInfoItem2.type = getRelateInfoType(relateInfoItem2.relateInfo);
                relatedSceneInfoList.add(relateInfoItem2);
                i++;
            }
        }
    }

    public static void initEurPanelInfoList(RelateInfoAssistant relateInfoAssistant2) {
        int zoneNumber = relateInfoAssistant2.getZoneNumber();
        List<RelateInfoItem> list = relatedInfoList;
        int i = 0;
        if (list == null || list.size() != zoneNumber) {
            relatedInfoList = new ArrayList();
            for (int i2 = 0; i2 < zoneNumber; i2++) {
                RelateInfoItem relateInfoItem = new RelateInfoItem();
                relateInfoItem.relateInfo = relateInfoAssistant2.getRelateInfo(i2);
                relateInfoItem.infoName = getRelateInfoString(relateInfoItem.relateInfo);
                relateInfoItem.iconRes = getRelateInfoIcon(relateInfoItem.relateInfo);
                relateInfoItem.actionInfo = getRelateInfo(relateInfoItem.relateInfo);
                relateInfoItem.type = getRelateInfoType(relateInfoItem.relateInfo);
                relatedInfoList.add(relateInfoItem);
            }
        } else {
            for (int i3 = 0; i3 < zoneNumber; i3++) {
                relatedInfoList.get(i3).relateInfo = relateInfoAssistant2.getRelateInfo(i3);
                relatedInfoList.get(i3).infoName = getRelateInfoString(relateInfoAssistant2.getRelateInfo(i3));
                relatedInfoList.get(i3).iconRes = getRelateInfoIcon(relateInfoAssistant2.getRelateInfo(i3));
                relatedInfoList.get(i3).actionInfo = getRelateInfo(relateInfoAssistant2.getRelateInfo(i3));
                relatedInfoList.get(i3).type = getRelateInfoType(relateInfoAssistant2.getRelateInfo(i3));
            }
        }
        List<RelateInfoItem> list2 = relatedSceneInfoList;
        if (list2 == null || list2.size() != 4) {
            relatedSceneInfoList = new ArrayList();
            while (i < 4) {
                RelateInfoItem relateInfoItem2 = new RelateInfoItem();
                relateInfoItem2.relateInfo = relateInfoAssistant2.getRelateSceneInfo(i);
                relateInfoItem2.infoName = getRelateInfoString(relateInfoItem2.relateInfo);
                relateInfoItem2.iconRes = getRelateInfoIcon(relateInfoItem2.relateInfo);
                relateInfoItem2.actionInfo = getRelateInfo(relateInfoItem2.relateInfo);
                relateInfoItem2.type = getRelateInfoType(relateInfoItem2.relateInfo);
                relatedSceneInfoList.add(relateInfoItem2);
                i++;
            }
            return;
        }
        while (i < 4) {
            relatedSceneInfoList.get(i).relateInfo = relateInfoAssistant2.getRelateSceneInfo(i);
            relatedSceneInfoList.get(i).infoName = getRelateInfoString(relateInfoAssistant2.getRelateSceneInfo(i));
            relatedSceneInfoList.get(i).iconRes = getRelateInfoIcon(relateInfoAssistant2.getRelateSceneInfo(i));
            relatedSceneInfoList.get(i).actionInfo = getRelateInfo(relateInfoAssistant2.getRelateSceneInfo(i));
            relatedSceneInfoList.get(i).type = getRelateInfoType(relateInfoAssistant2.getRelateSceneInfo(i));
            i++;
        }
    }

    public static RelateInfoItem getRelateInfoItem(RelatedInfoExtParam.RelateInfo relateInfo) {
        RelateInfoItem relateInfoItem = new RelateInfoItem();
        relateInfoItem.relateInfo = relateInfo;
        relateInfoItem.infoName = getRelateInfoString(relateInfoItem.relateInfo);
        relateInfoItem.iconRes = getRelateInfoIcon(relateInfoItem.relateInfo);
        relateInfoItem.actionInfo = getRelateInfo(relateInfoItem.relateInfo);
        relateInfoItem.type = getRelateInfoType(relateInfoItem.relateInfo);
        return relateInfoItem;
    }

    public static int getRelateInfoType(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null) {
            return 0;
        }
        if (relateInfo.isRelateDeviceInfo()) {
            if (Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId) == null) {
                return 0;
            }
        } else if (relateInfo.isRelateGroupInfo()) {
            if (Injection.repo().group().getGroupByGroupId(relateInfo.objectId) == null) {
                return 0;
            }
        } else if (relateInfo.isRelateSceneInfo() || relateInfo.isRelateDaliSceneInfo()) {
            if (relateInfo.objectId > 0 && relateInfo.objectId <= 4) {
                return relateInfo.type;
            }
            if (Injection.repo().scene().getSceneBySceneId(relateInfo.objectId) == null) {
                return 0;
            }
        }
        return relateInfo.type;
    }

    public static String getRelateInfoString(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null) {
            return null;
        }
        if (relateInfo.isRelateDeviceInfo()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
            if (deviceByDeviceId != null) {
                return deviceByDeviceId.getDeviceName();
            }
        } else if (relateInfo.isRelateGroupInfo()) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.objectId);
            if (groupByGroupId != null) {
                return groupByGroupId.getGroupName();
            }
        } else if (relateInfo.isRelateSceneInfo() || relateInfo.isRelateDaliSceneInfo() || relateInfo.isRelateLongClickDaliSceneInfo()) {
            if (relateInfo.objectId > 0 && relateInfo.objectId <= 4) {
                return ActivityUtils.getTopActivity().getString(R.string.app_scene) + relateInfo.objectId;
            }
            Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.objectId);
            if (sceneBySceneId != null) {
                return sceneBySceneId.getSceneName();
            }
        } else if (relateInfo.type == 0) {
            return relateInfo.name;
        }
        return null;
    }

    public static Object getRelateInfoObject(RelatedInfoExtParam.RelateInfo relateInfo) {
        Scene sceneBySceneId;
        if (relateInfo == null) {
            return null;
        }
        if (relateInfo.isRelateDeviceInfo()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
            if (deviceByDeviceId != null) {
                return deviceByDeviceId;
            }
        } else if (relateInfo.isRelateGroupInfo()) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.objectId);
            if (groupByGroupId != null) {
                return groupByGroupId;
            }
        } else if ((relateInfo.isRelateSceneInfo() || relateInfo.isRelateDaliSceneInfo()) && (sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.objectId)) != null) {
            return sceneBySceneId;
        }
        return null;
    }

    public static String[] getSwitchNameArray(Object object) {
        if (ProductRepository.getLightColorType(object) == 26) {
            return new String[]{ActivityUtils.getTopActivity().getString(R.string.app_str_switch) + 1, ActivityUtils.getTopActivity().getString(R.string.app_str_switch) + 2};
        }
        relatedInfoList = null;
        initRelateInfoList(object);
        int size = relatedInfoList.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            RelateInfoItem relateInfoItem = relatedInfoList.get(i);
            if (ProductRepository.getLightColorType(object) == 21) {
                strArr[i] = ActivityUtils.getTopActivity().getString(R.string.app_str_key) + (i + 1);
            } else if (relateInfoItem.infoName == null || TextUtils.isEmpty(relateInfoItem.infoName)) {
                strArr[i] = ActivityUtils.getTopActivity().getString(R.string.app_str_switch) + (i + 1);
            } else {
                strArr[i] = relateInfoItem.infoName;
            }
        }
        return strArr;
    }

    public static boolean isPositionBind(Object object, int position) {
        relatedInfoList = null;
        initRelateInfoList(object);
        RelateInfoItem relateInfoItem = relatedInfoList.get(position);
        return (relateInfoItem.infoName == null || relateInfoItem.type == 0) ? false : true;
    }

    public static int getRelateInfoIcon(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null) {
            return R.mipmap.ic_device_none;
        }
        if (relateInfo.isRelateDeviceInfo()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
            if (deviceByDeviceId != null) {
                return ProductRepository.getProductIcon(deviceByDeviceId);
            }
        } else if (relateInfo.isRelateGroupInfo()) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.objectId);
            if (groupByGroupId != null) {
                return ProductRepository.getProductIcon(groupByGroupId);
            }
        } else if (relateInfo.isRelateSceneInfo() || relateInfo.isRelateDaliSceneInfo()) {
            Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.objectId);
            return sceneBySceneId != null ? SceneHelper.getSceneIcon(ActivityUtils.getTopActivity(), sceneBySceneId.getIconPos()) : R.mipmap.icon_scene_light_1;
        }
        return R.mipmap.ic_device_none;
    }

    public static String getRelateLongClickInfo(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null) {
            return "";
        }
        int i = relateInfo.action;
        if (i == 1) {
            return ActivityUtils.getTopActivity().getString(R.string.key_switch_action_7);
        }
        if (i == 2) {
            return ActivityUtils.getTopActivity().getString(R.string.key_switch_action_8);
        }
        if (i == 3) {
            return ActivityUtils.getTopActivity().getString(R.string.key_switch_action_9);
        }
        if (i != 4 && i != 38) {
            return "";
        }
        if (!relateInfo.isRelateSceneInfo() && !relateInfo.isRelateDaliSceneInfo() && !relateInfo.isRelateLongClickDaliSceneInfo()) {
            return "";
        }
        if (relateInfo.objectId > 0 && relateInfo.objectId <= 4) {
            return ActivityUtils.getTopActivity().getString(R.string.app_scene) + relateInfo.objectId;
        }
        Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.objectId);
        if (sceneBySceneId == null) {
            return "";
        }
        return sceneBySceneId.getSceneName();
    }

    public static String getRelateInfo(RelatedInfoExtParam.RelateInfo relateInfo) {
        List<Group> list;
        List<Device> list2;
        Device device;
        if (relateInfo == null) {
            return "";
        }
        Place value = Injection.repo().home().getSelectPlace().getValue();
        if (value != null) {
            list2 = Injection.repo().device().getDeviceListByPlaceId(value.getPlaceId());
            list = Injection.repo().group().getGroupListByPlaceId(value.getPlaceId());
        } else {
            list = null;
            list2 = null;
        }
        boolean isRelateSceneInfo = relateInfo.isRelateSceneInfo();
        int i = R.string.dali_scene;
        char c2 = 2;
        if (isRelateSceneInfo) {
            Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.objectId);
            if (sceneBySceneId != null) {
                Activity topActivity = ActivityUtils.getTopActivity();
                if (sceneBySceneId.getSceneType() == 2) {
                    i = R.string.local_scene;
                }
                return topActivity.getString(i);
            }
        } else {
            if (relateInfo.isRelateDaliSceneInfo()) {
                return ActivityUtils.getTopActivity().getString(R.string.dali_scene);
            }
            if (relateInfo.action == 0) {
                return "";
            }
        }
        int i2 = 0;
        if (!relateInfo.isRelateDeviceInfo()) {
            if (relateInfo.isRelateGroupInfo() && list != null) {
                Group group = null;
                for (Group group2 : list) {
                    if (group2.getGroupId() == relateInfo.objectId) {
                        group = group2;
                    }
                }
                if (group != null) {
                    int intValue = Integer.valueOf(group.getControlType()).intValue();
                    if (intValue == 14) {
                        int i3 = relateInfo.action;
                        if (i3 == 4) {
                            if (group.getExtParam() != null) {
                                CurtainMotorInfoExtParam curtainMotorInfoExtParam = new CurtainMotorInfoExtParam();
                                curtainMotorInfoExtParam.fillMapWithString(group.getExtParam());
                                while (i2 < 4) {
                                    if (curtainMotorInfoExtParam.getModeInfo(i2) != null && i2 == relateInfo.keyActionExtra - 1) {
                                        return curtainMotorInfoExtParam.getModeInfo(i2);
                                    }
                                    i2++;
                                }
                                return CurtainRepository.DEFAULT_MODE_LIST.get(relateInfo.keyActionExtra - 1).getName();
                            }
                            return CurtainRepository.DEFAULT_MODE_LIST.get(relateInfo.keyActionExtra - 1).getName();
                        }
                        if (i3 == 5) {
                            if (relateInfo.keyActionExtra == 0) {
                                return ActivityUtils.getTopActivity().getString(R.string.app_str_all_open);
                            }
                            if (relateInfo.keyActionExtra == 100) {
                                return ActivityUtils.getTopActivity().getString(R.string.app_str_all_close);
                            }
                            return ActivityUtils.getTopActivity().getString(R.string.app_str_curtain_open) + (100 - relateInfo.keyActionExtra) + "%";
                        }
                        return NameRepository.getCurtainActionName(ActivityUtils.getTopActivity())[relateInfo.action - 1];
                    }
                    if (intValue == 16) {
                        int i4 = relateInfo.action;
                        if (i4 != 1) {
                            if (i4 != 2) {
                                if (i4 == 3) {
                                    c2 = 1;
                                } else if (i4 == 4) {
                                    c2 = 3;
                                } else if (i4 == 8) {
                                    c2 = 5;
                                } else if (i4 == 12) {
                                    c2 = 4;
                                }
                            }
                            return NameRepository.getTrigDreamCurtainActionName(ActivityUtils.getTopActivity())[c2];
                        }
                        c2 = 0;
                        return NameRepository.getTrigDreamCurtainActionName(ActivityUtils.getTopActivity())[c2];
                    }
                    if (intValue != 21) {
                        if (intValue != 26 && intValue != 18) {
                            if (intValue == 19) {
                                return NameRepository.getSmartPanelS6ProKeyName(ActivityUtils.getTopActivity())[(int) (Math.log(relateInfo.bindingZone) / Math.log(2.0d))];
                            }
                            switch (intValue) {
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                    break;
                                case 12:
                                    int i5 = relateInfo.action;
                                    if (i5 != 1) {
                                        if (i5 == 2) {
                                            c2 = 1;
                                        } else if (i5 != 4) {
                                            if (i5 == 8) {
                                                c2 = 3;
                                            }
                                        }
                                        return NameRepository.getTrigCurtainActionName(ActivityUtils.getTopActivity())[c2];
                                    }
                                    c2 = 0;
                                    return NameRepository.getTrigCurtainActionName(ActivityUtils.getTopActivity())[c2];
                                default:
                                    int lightColorType = ProductRepository.getLightColorType((Object) group);
                                    if (relateInfo.action == 16 || relateInfo.action == 17 || relateInfo.action == 18) {
                                        return getThemeModeString(lightColorType, relateInfo.action, relateInfo.keyActionExtra);
                                    }
                                    if (relateInfo.action == 19 || relateInfo.action == 20 || relateInfo.action == 34 || relateInfo.action == 35) {
                                        return getLightStaticInfo(Integer.parseInt(relateInfo.wholeDataExtra.substring(0, 2), 16), relateInfo.action, relateInfo.wholeDataExtra, group);
                                    }
                                    if (!ProductRepository.isDaliLightGroup(group)) {
                                        return ActSmartPanelSelectActionVM.getKeyActionName(ActivityUtils.getTopActivity(), group, lightColorType, relateInfo.action);
                                    }
                                    if (!TextUtils.isEmpty(relateInfo.wholeDataExtra)) {
                                        lightColorType = Integer.parseInt(relateInfo.wholeDataExtra.substring(0, 2), 16);
                                    }
                                    return ActSmartPanelSelectActionVM.getDaliKeyActionName(ActivityUtils.getTopActivity(), lightColorType, group, relateInfo.action);
                            }
                        }
                        return NameRepository.getSmartPanelS4KeyName(ActivityUtils.getTopActivity())[(int) (Math.log(relateInfo.bindingZone) / Math.log(2.0d))];
                    }
                    return NameRepository.getSmartPanelG4KeyName(ActivityUtils.getTopActivity())[(int) (Math.log(relateInfo.bindingZone) / Math.log(2.0d))];
                }
            }
            return "";
        }
        if (list2 != null) {
            Device device2 = null;
            for (Device device3 : list2) {
                if (device3.getDeviceId() == relateInfo.objectId) {
                    device2 = device3;
                }
            }
            device = device2;
        } else {
            device = null;
        }
        if (device == null) {
            return "";
        }
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "122041818304701":
            case "3895993722014848":
            case "123050811340901":
            case "123050811353501":
            case "3683388245101248":
            case "221042516351701":
            case "123072510445601":
            case "122042815485901":
            case "221030816330401":
            case "122080911090801":
            case "121052512023201":
            case "121042516340801":
            case "121042516345401":
                return NameRepository.getSmartPanelS4KeyName(ActivityUtils.getTopActivity())[(int) (Math.log(relateInfo.bindingZone) / Math.log(2.0d))];
            case "122110809100701":
            case "121042516403901":
                int i6 = relateInfo.action;
                if (i6 == 4) {
                    if (device.getExtParam() != null) {
                        CurtainMotorInfoExtParam curtainMotorInfoExtParam2 = new CurtainMotorInfoExtParam();
                        curtainMotorInfoExtParam2.fillMapWithString(device.getExtParam());
                        while (i2 < 4) {
                            if (curtainMotorInfoExtParam2.getModeInfo(i2) != null && i2 == relateInfo.keyActionExtra - 1) {
                                return curtainMotorInfoExtParam2.getModeInfo(i2);
                            }
                            i2++;
                        }
                        return CurtainRepository.DEFAULT_MODE_LIST.get(relateInfo.keyActionExtra - 1).getName();
                    }
                    return CurtainRepository.DEFAULT_MODE_LIST.get(relateInfo.keyActionExtra - 1).getName();
                }
                if (i6 == 5) {
                    if (relateInfo.keyActionExtra == 0) {
                        return ActivityUtils.getTopActivity().getString(R.string.app_str_all_open);
                    }
                    if (relateInfo.keyActionExtra == 100) {
                        return ActivityUtils.getTopActivity().getString(R.string.app_str_all_close);
                    }
                    return ActivityUtils.getTopActivity().getString(R.string.app_str_curtain_open) + (100 - relateInfo.keyActionExtra) + "%";
                }
                return NameRepository.getCurtainActionName(ActivityUtils.getTopActivity())[relateInfo.action - 1];
            case "3683369128495808":
            case "4249823578721536":
                return NameRepository.getSmartPanelS6ProKeyName(ActivityUtils.getTopActivity())[(int) (Math.log(relateInfo.bindingZone) / Math.log(2.0d))];
            case "3701704216101056":
            case "3701703750123712":
                return NameRepository.getSmartPanelG4KeyName(ActivityUtils.getTopActivity())[(int) (Math.log(relateInfo.bindingZone) / Math.log(2.0d))];
            case "123051716231102":
                int parseInt = Integer.parseInt(relateInfo.wholeDataExtra.substring(0, 2), 16);
                if (parseInt == 0) {
                    return ActivityUtils.getTopActivity().getString(Integer.parseInt(relateInfo.wholeDataExtra.substring(2, 4), 16) == 0 ? R.string.light_off_1 : R.string.light_on_1);
                }
                return getLightStaticInfo(parseInt >> 4, relateInfo.action, relateInfo.wholeDataExtra, device);
            case "121051709233801":
                DryContactBleParam dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class);
                int subType = dryContactBleParam != null ? dryContactBleParam.getSubType() : 1;
                if (subType == 1) {
                    return NameRepository.getTrigSceneActionName(ActivityUtils.getTopActivity())[relateInfo.action - 1];
                }
                if (subType == 2) {
                    return NameRepository.getTrigScene8ActionName(ActivityUtils.getTopActivity())[relateInfo.action - 1];
                }
                if (subType == 3) {
                    int i7 = relateInfo.action;
                    if (i7 != 1) {
                        if (i7 != 2) {
                            if (i7 == 3) {
                                c2 = 1;
                            } else if (i7 == 4) {
                                c2 = 4;
                            } else if (i7 == 8) {
                                c2 = 6;
                            } else if (i7 == 9) {
                                c2 = 3;
                            } else if (i7 == 12) {
                                c2 = 5;
                            } else if (i7 == 36) {
                                c2 = 7;
                            }
                        }
                        return NameRepository.getTrigDreamCurtainActionName2(ActivityUtils.getTopActivity())[c2];
                    }
                    c2 = 0;
                    return NameRepository.getTrigDreamCurtainActionName2(ActivityUtils.getTopActivity())[c2];
                }
                int i8 = relateInfo.action;
                if (i8 != 1) {
                    if (i8 == 2) {
                        c2 = 1;
                    } else if (i8 != 4) {
                        if (i8 == 8) {
                            c2 = 3;
                        }
                    }
                    return NameRepository.getTrigCurtainActionName(ActivityUtils.getTopActivity())[c2];
                }
                c2 = 0;
                return NameRepository.getTrigCurtainActionName(ActivityUtils.getTopActivity())[c2];
            default:
                int lightColorType2 = ProductRepository.getLightColorType((Object) device);
                if (relateInfo.action == 16 || relateInfo.action == 17 || relateInfo.action == 18) {
                    return getThemeModeString(lightColorType2, relateInfo.action, relateInfo.keyActionExtra);
                }
                if (relateInfo.action == 19 || relateInfo.action == 20 || relateInfo.action == 34 || relateInfo.action == 35) {
                    return getLightStaticInfo(Integer.parseInt(relateInfo.wholeDataExtra.substring(0, 2), 16), relateInfo.action, relateInfo.wholeDataExtra, device);
                }
                if (!ProductRepository.isDaliLightGroup(device)) {
                    return ActSmartPanelSelectActionVM.getKeyActionName(ActivityUtils.getTopActivity(), device, lightColorType2, relateInfo.action);
                }
                if (!TextUtils.isEmpty(relateInfo.wholeDataExtra)) {
                    lightColorType2 = Integer.parseInt(relateInfo.wholeDataExtra.substring(0, 2), 16);
                }
                return ActSmartPanelSelectActionVM.getDaliKeyActionName(ActivityUtils.getTopActivity(), lightColorType2, device, relateInfo.action);
        }
    }

    public static String getLightStaticInfo(int lightType, int action, String wholeDataExtra, Object object) {
        String substring = wholeDataExtra.substring(2);
        Activity topActivity = ActivityUtils.getTopActivity();
        String str = "";
        if (lightType == 1) {
            int parseInt = Integer.parseInt(substring.substring(6, 8), 16);
            StringBuilder sb = new StringBuilder();
            sb.append(topActivity.getString(R.string.dim_brt));
            sb.append(Constants.COLON_SEPARATOR);
            sb.append(LightUtils.brt2PercentHasBelowZero(parseInt));
            sb.append("%");
            if (action == 20) {
                str = "&" + topActivity.getString(R.string.dim_new_action_5);
            }
            sb.append(str);
            return sb.toString();
        }
        if (lightType == 2) {
            int parseInt2 = Integer.parseInt(substring.substring(6, 8), 16);
            int parseInt3 = Integer.parseInt(substring.substring(8, 10), 16);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(LightUtils.getKValue(parseInt3, object));
            sb2.append(" ");
            sb2.append(LightUtils.brt2ProgressHasBelowZero(parseInt2));
            sb2.append("%");
            if (action == 20) {
                str = "&" + topActivity.getString(R.string.dim_new_action_5);
            }
            sb2.append(str);
            return sb2.toString();
        }
        if (lightType == 3) {
            if (action == 36) {
                return topActivity.getString(R.string.rgb_new_action_1);
            }
        } else {
            if (lightType == 16) {
                int parseInt4 = Integer.parseInt(substring.substring(0, 2), 16);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(topActivity.getString(R.string.dim_brt));
                sb3.append(Constants.COLON_SEPARATOR);
                sb3.append(LightUtils.brt2PercentHasBelowZero(parseInt4));
                sb3.append("%");
                if (action == 35) {
                    str = "&" + topActivity.getString(R.string.dim_new_action_5);
                }
                sb3.append(str);
                return sb3.toString();
            }
            if (lightType == 32) {
                int parseInt5 = Integer.parseInt(substring.substring(0, 2), 16);
                int parseInt6 = Integer.parseInt(substring.substring(2, 4), 16);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(LightUtils.getKValue(parseInt6, object));
                sb4.append(" ");
                sb4.append(LightUtils.brt2ProgressHasBelowZero(parseInt5));
                sb4.append("%");
                if (action == 35) {
                    str = "&" + topActivity.getString(R.string.dim_new_action_5);
                }
                sb4.append(str);
                return sb4.toString();
            }
            if (lightType == 48) {
                if (action == 34 || action == 35) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(topActivity.getString(R.string.rgb_new_action_1));
                    if (action == 35) {
                        str = "&" + topActivity.getString(R.string.dim_new_action_5);
                    }
                    sb5.append(str);
                    return sb5.toString();
                }
                if (action == 36) {
                    return "RGB " + ActivityUtils.getTopActivity().getString(R.string.dim_brt) + Constants.COLON_SEPARATOR + LightUtils.brt2PercentHasBelowZero(Integer.parseInt(substring.substring(0, 2), 16)) + "%";
                }
            }
        }
        return ActSmartPanelSelectActionVM.getKeyActionName(topActivity, object, lightType, action);
    }

    public static String getThemeModeString(int lightType, int action, int index) {
        Activity topActivity = ActivityUtils.getTopActivity();
        List<ModeContent> arrayList = new ArrayList<>();
        if (action == 17) {
            arrayList = Injection.repo().mode().getModeListFromDb(lightType, 2);
        } else if (action == 18) {
            arrayList = Injection.repo().mode().getModeListFromDb(lightType, 1);
        }
        for (ModeContent modeContent : arrayList) {
            if (modeContent.getModeIndex() == index + 1) {
                return modeContent.getModeName();
            }
        }
        if (lightType == 1) {
            if (action == 16) {
                return NameRepository.getDimDefaultModeName(topActivity)[index];
            }
            if (action == 17) {
                return NameRepository.getAdvancedDimModeName(topActivity)[index];
            }
            return "";
        }
        if (lightType != 2) {
            if (action == 16) {
                return NameRepository.getDefaultModeName(topActivity)[index];
            }
            if (action == 17) {
                return NameRepository.getAdvancedModeName(topActivity)[index];
            }
            return NameRepository.getGeneralModeName(topActivity)[index];
        }
        if (action == 16) {
            return NameRepository.getDimDefaultModeName(topActivity)[index];
        }
        if (action == 17) {
            return NameRepository.getAdvancedCtModeName(topActivity)[index];
        }
        if (action == 18) {
            return NameRepository.getGeneralCtModeName(topActivity)[index];
        }
        return "";
    }

    public static int getRelateZone(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null) {
            return 0;
        }
        if (relateInfo.isRelateSceneInfo()) {
            Scene sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.objectId);
            if (sceneBySceneId != null) {
                if (sceneBySceneId.getSceneType() == 2) {
                    return 1;
                }
                return DaliProHelper.BROADCAST_ADD;
            }
        } else if (relateInfo.isRelateDaliSceneInfo()) {
            return DaliProHelper.BROADCAST_ADD;
        }
        if (relateInfo.isRelateCgdAdd()) {
            return relateInfo.bindingZone;
        }
        if (relateInfo.bindingZone == 0) {
            return 1;
        }
        return relateInfo.bindingZone;
    }

    public static int getRelateAddress(RelatedInfoExtParam.RelateInfo relateInfo) {
        Scene sceneBySceneId;
        Device deviceByDeviceId;
        if (relateInfo != null) {
            if (relateInfo.isRelateGroupInfo()) {
                Group groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.objectId);
                if (groupByGroupId != null) {
                    return groupByGroupId.getGroupAddress();
                }
                return 0;
            }
            if (relateInfo.isRelateDeviceInfo()) {
                Device deviceByDeviceId2 = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
                if (deviceByDeviceId2 == null || deviceByDeviceId2.getParam(BleParam.class) == null) {
                    return 0;
                }
                return ((BleParam) deviceByDeviceId2.getParam(BleParam.class)).getUnicastAddress();
            }
            if (relateInfo.isRelateSceneInfo()) {
                Scene sceneBySceneId2 = Injection.repo().scene().getSceneBySceneId(relateInfo.objectId);
                if (sceneBySceneId2 != null) {
                    if (sceneBySceneId2.getSceneType() == 2) {
                        return 65025;
                    }
                    Device deviceByDeviceId3 = Injection.repo().device().getDeviceByDeviceId(sceneBySceneId2.getMacdeviceid());
                    if (deviceByDeviceId3 != null) {
                        return ((BleParam) deviceByDeviceId3.getParam(BleParam.class)).getUnicastAddress();
                    }
                }
            } else if (relateInfo.isRelateDaliSceneInfo() && (sceneBySceneId = Injection.repo().scene().getSceneBySceneId(relateInfo.objectId)) != null && (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(sceneBySceneId.getMacdeviceid())) != null && deviceByDeviceId.getParam(BleParam.class) != null) {
                return ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getUnicastAddress();
            }
        }
        return 0;
    }

    public static int getDelayTime(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo != null) {
            return relateInfo.delay;
        }
        return 0;
    }

    public static int getRelateDeviceType(RelatedInfoExtParam.RelateInfo relateInfo) {
        if (relateInfo == null) {
            return 0;
        }
        if (relateInfo.action == 16 || relateInfo.action == 17 || relateInfo.action == 18) {
            return 1 << relateInfo.keyActionExtra;
        }
        if (relateInfo.isRelateGroupInfo()) {
            Group groupByGroupId = Injection.repo().group().getGroupByGroupId(relateInfo.objectId);
            if (groupByGroupId != null) {
                return Integer.parseInt(groupByGroupId.getControlType());
            }
            return 0;
        }
        if (relateInfo.isRelateDeviceInfo()) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
            if (deviceByDeviceId == null || deviceByDeviceId.getParam(BleParam.class) == null) {
                return 0;
            }
            return ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getDeviceType();
        }
        if (relateInfo.isRelateSceneInfo()) {
            return relateInfo.keyActionExtra;
        }
        if (relateInfo.isRelateDaliSceneInfo()) {
            return relateInfo.keyActionExtra;
        }
        return 0;
    }

    public static void showImageTipDialog(String title, int imageRes, FragmentActivity activity, ImageTipDialog.OnConfirmCallback callback) {
        ImageTipDialog.asDefault().setTitle(title).setConfirmString(ActivityUtils.getTopActivity().getString(R.string.get_it)).setImageRes(imageRes).setCallback(callback).showDialog(activity);
    }

    public static ImageTipDialog showImageTipDialog(Device device, FragmentActivity activity, ImageTipDialog.OnConfirmCallback callback) {
        int i;
        int i2;
        String productId = device.getProductId();
        productId.hashCode();
        i = 0;
        switch (productId) {
            case "3503908278750336":
                i2 = R.mipmap.pic_click_tip_rc_b2;
                i = R.string.rc_b2_b5_click_tip;
                break;
            case "3508084028410496":
                i2 = R.mipmap.pic_click_tip_rc_b8;
                i = R.string.rc_b1_b8_click_tip;
                break;
            case "123031312002001":
                i = R.string.s6b_click_tip;
                i2 = R.mipmap.pic_click_tip_s6b;
                break;
            case "4057094887997440":
                i = R.string.app_str_rc4s_activate_tip;
                i2 = R.mipmap.rc4sble_2;
                break;
            case "3503908725640320":
                i2 = R.mipmap.pic_click_tip_rc_b5;
                i = R.string.rc_b2_b5_click_tip;
                break;
            case "4304746736451584":
                i = R.string.hsd_click_tip;
                i2 = R.mipmap.pic_add_hsd_3;
                break;
            case "3763962108692992":
                i = R.string.door_sensor_click_tip;
                i2 = R.mipmap.pic_add_cgdr_2;
                break;
            case "122110709484501":
                i = R.string.sq_click_tip;
                i2 = R.mipmap.pic_click_tip;
                break;
            case "3503907950824576":
                i2 = R.mipmap.pic_click_tip_rc_b1;
                i = R.string.rc_b1_b8_click_tip;
                break;
            default:
                i2 = 0;
                break;
        }
        ImageTipDialog callback2 = ImageTipDialog.asDefault().setTitle(activity.getString(i)).setConfirmString(activity.getString(R.string.get_it)).setImageRes(i2).setCallback(callback);
        callback2.showDialog(activity);
        return callback2;
    }

    public static boolean needShowTipDialog() {
        return System.currentTimeMillis() - lastBindTime >= ((long) timeToLowPower) * 1000;
    }
}