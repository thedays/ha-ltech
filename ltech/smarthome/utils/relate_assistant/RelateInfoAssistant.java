package com.ltech.smarthome.utils.relate_assistant;

import android.text.TextUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.smart.message.utils.LHomeLog;

/* loaded from: classes4.dex */
public class RelateInfoAssistant {
    private boolean isKeyDefault;
    private boolean isProPanel;
    private RelatedInfoExtParam relatedInfoExtParam;

    public RelateInfoAssistant() {
        this.relatedInfoExtParam = new RelatedInfoExtParam();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public RelateInfoAssistant(Device device) {
        if (device != null) {
            RelatedInfoExtParam relatedInfoExtParam = new RelatedInfoExtParam();
            this.relatedInfoExtParam = relatedInfoExtParam;
            relatedInfoExtParam.fillMapWithString(device.getExtParam());
            if (this.relatedInfoExtParam.getZoneNumber() == 0) {
                String productId = device.getProductId();
                productId.hashCode();
                switch (productId) {
                    case "122041818260301":
                    case "221042516351701":
                    case "122051609304501":
                    case "122111615282701":
                    case "122110709484501":
                        this.relatedInfoExtParam.setZoneNumber(1);
                        break;
                    case "122041818283501":
                    case "121042516345401":
                        this.relatedInfoExtParam.setZoneNumber(2);
                        break;
                    case "122041818304701":
                    case "121042516340801":
                        this.relatedInfoExtParam.setZoneNumber(3);
                        break;
                    case "3486586935738368":
                    case "3486587348451328":
                    case "3486587769094144":
                        this.relatedInfoExtParam.setZoneNumber(1);
                        break;
                    case "3683369128495808":
                    case "4249823578721536":
                        this.relatedInfoExtParam.setZoneNumber(6);
                        break;
                    case "3701704216101056":
                    case "3701703750123712":
                    case "3721596935046208":
                    case "3959367613661440":
                        this.relatedInfoExtParam.setZoneNumber(12);
                        break;
                    case "123031312002001":
                        this.isKeyDefault = true;
                        this.relatedInfoExtParam.setZoneNumber(6);
                        break;
                    case "123072510445601":
                    case "221030816330401":
                    case "120042616091901":
                    case "120042616094101":
                    case "120042616101901":
                    case "120042616103901":
                        this.relatedInfoExtParam.setZoneNumber(4);
                        break;
                    case "121031814513301":
                        this.isKeyDefault = true;
                        this.relatedInfoExtParam.setZoneNumber(8);
                        this.relatedInfoExtParam.setZoneNumber(1);
                        break;
                }
            }
            if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO) || device.getProductId().equals(ProductId.ID_SMART_PANEL_G4) || device.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                this.isProPanel = true;
            }
        }
    }

    public RelateInfoAssistant(Group group) {
        this.relatedInfoExtParam = new RelatedInfoExtParam();
        LHomeLog.i(getClass(), "group.getExtParam()=" + group.getExtParam());
        this.relatedInfoExtParam.fillMapWithString(group.getExtParam());
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (this.relatedInfoExtParam.getZoneNumber() == 0) {
            if (lightColorType != 18) {
                if (lightColorType == 19) {
                    this.relatedInfoExtParam.setZoneNumber(6);
                } else if (lightColorType != 21) {
                    switch (lightColorType) {
                        case 8:
                            this.relatedInfoExtParam.setZoneNumber(1);
                            break;
                        case 9:
                            this.relatedInfoExtParam.setZoneNumber(2);
                            break;
                        case 10:
                            this.relatedInfoExtParam.setZoneNumber(3);
                            break;
                        default:
                            switch (lightColorType) {
                            }
                        case 11:
                            this.relatedInfoExtParam.setZoneNumber(4);
                            break;
                    }
                } else {
                    this.relatedInfoExtParam.setZoneNumber(12);
                }
            }
            this.relatedInfoExtParam.setZoneNumber(4);
        }
        if (lightColorType == 19 || lightColorType == 21 || this.relatedInfoExtParam.getSwitchShowType() == 2) {
            this.isProPanel = true;
        }
    }

    public RelatedInfoExtParam getExtParam() {
        return this.relatedInfoExtParam;
    }

    public String getExtParamString() {
        return this.relatedInfoExtParam.getRelateParamMapString();
    }

    public void setRelateInfo(int position, RelatedInfoExtParam.RelateInfo relateInfo) {
        if (TextUtils.isEmpty(relateInfo.name)) {
            if (this.isKeyDefault) {
                relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_str_key) + (position + 1);
            } else {
                relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_str_switch) + (position + 1);
            }
        }
        int i = position + 1;
        RelatedInfoExtParam.RelateInfo relateInfo2 = this.relatedInfoExtParam.getRelateInfo(i);
        if (relateInfo2 != null) {
            relateInfo.switchIndex = relateInfo2.switchIndex;
        }
        this.relatedInfoExtParam.setRelateInfo(i, relateInfo);
    }

    public void setRelateLongClickInfo(int position, RelatedInfoExtParam.RelateInfo relateInfo) {
        if (TextUtils.isEmpty(relateInfo.name)) {
            if (this.isKeyDefault) {
                relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_str_key) + (position + 1);
            } else {
                relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_str_switch) + (position + 1);
            }
        }
        this.relatedInfoExtParam.setRelateLongClickInfo(position + 1, relateInfo);
    }

    public void setEurRelateInfo(int position, RelatedInfoExtParam.RelateInfo relateInfo) {
        this.relatedInfoExtParam.setRelateInfo(position + 1, relateInfo);
    }

    public void setSceneRelateInfo(int position, RelatedInfoExtParam.RelateInfo relateInfo) {
        if (TextUtils.isEmpty(relateInfo.name)) {
            relateInfo.name = ActivityUtils.getTopActivity().getString(R.string.app_scene) + (position + 1);
        }
        this.relatedInfoExtParam.setRelateSceneInfo(position + 1, relateInfo);
    }

    public void setRelateSceneInfo(int position, RelatedInfoExtParam.RelateInfo relateInfo) {
        this.relatedInfoExtParam.setRelateSceneInfo(position + 1, relateInfo);
    }

    public void resetSmartPanelRelateInfo(int position) {
        resetSmartPanelRelateInfo("", position);
    }

    public void resetSmartPanelRelateInfo(String productId, int position) {
        int i = position + 1;
        RelatedInfoExtParam.RelateInfo relateInfo = this.relatedInfoExtParam.getRelateInfo(i);
        RelatedInfoExtParam.RelateInfo relateInfo2 = new RelatedInfoExtParam.RelateInfo();
        if (ProductId.ID_SCENE_PANEL_S8.equals(productId) || ProductId.ID_SMART_PANEL_S6B.equals(productId) || ProductId.ID_SMART_PANEL_G4_PRO.equals(productId) || ProductId.ID_SMART_PANEL_G4.equals(productId) || ProductId.ID_SMART_SWITCH_S6_PRO.equals(productId)) {
            relateInfo2.name = ActivityUtils.getTopActivity().getString(R.string.app_str_key) + i;
        } else {
            relateInfo2.name = ActivityUtils.getTopActivity().getString(R.string.app_str_switch) + i;
        }
        if (relateInfo != null) {
            relateInfo2.switchIndex = relateInfo.switchIndex;
        }
        this.relatedInfoExtParam.setRelateInfo(i, relateInfo2);
    }

    public void eurSetRelateInfo(int position, String name) {
        int i = position + 1;
        RelatedInfoExtParam.RelateInfo relateInfo = getRelateInfo(position);
        if (relateInfo == null) {
            relateInfo = new RelatedInfoExtParam.RelateInfo();
        }
        relateInfo.name = name;
        this.relatedInfoExtParam.setRelateInfo(i, relateInfo);
    }

    public void resetRelateInfo(int position) {
        this.relatedInfoExtParam.setRelateInfo(position + 1, new RelatedInfoExtParam.RelateInfo());
    }

    public void resetRelateSceneInfo(int position) {
        this.relatedInfoExtParam.setRelateSceneInfo(position + 1, new RelatedInfoExtParam.RelateInfo());
    }

    public void resetRelateInfo(int position, RelatedInfoExtParam.RelateInfo relateInfo) {
        int size = this.relatedInfoExtParam.getRelateParamMap().size();
        for (int i = 0; i < size; i++) {
            RelatedInfoExtParam.RelateInfo relateInfo2 = this.relatedInfoExtParam.getRelateInfo(i);
            if (relateInfo2 != null && relateInfo.type == relateInfo2.type && relateInfo.objectId == relateInfo2.objectId) {
                resetRelateInfo(i);
            }
        }
        this.relatedInfoExtParam.setRelateInfo(position + 1, relateInfo);
    }

    public RelatedInfoExtParam.RelateInfo getRelateInfo(int position) {
        return this.relatedInfoExtParam.getRelateInfo(position + 1);
    }

    public RelatedInfoExtParam.RelateInfo getRelateLongClickInfo(int position) {
        return this.relatedInfoExtParam.getRelateLongClickInfo(position + 1);
    }

    public RelatedInfoExtParam.RelateInfo getRelateSceneInfo(int position) {
        return this.relatedInfoExtParam.getRelateSceneInfo(position + 1);
    }

    public RelatedInfoExtParam.CurtainRelateInfo getCurtainRelateInfo(int position) {
        return this.relatedInfoExtParam.getCurtainRelateInfo(position + 1);
    }

    public void setZoneNumber(int zoneNumber) {
        this.relatedInfoExtParam.setZoneNumber(zoneNumber);
    }

    public int getSwitchShowType() {
        return this.relatedInfoExtParam.getSwitchShowType();
    }

    public void setSwitchShowType(int showType) {
        this.relatedInfoExtParam.setSwitchShowType(showType);
    }

    public boolean isProPanel() {
        return this.isProPanel;
    }

    public int getSwitchScreenBigIcon() {
        return this.relatedInfoExtParam.getSwitchScreenBigIcon();
    }

    public void setSwitchScreenBigIcon(int switchScreenBigIcon) {
        this.relatedInfoExtParam.setSwitchScreenBigIcon(switchScreenBigIcon);
    }

    public int getSwitchScreenLanguage() {
        return this.relatedInfoExtParam.getSwitchScreenLanguage();
    }

    public void setSwitchScreenLanguage(int switchScreenLanguage) {
        this.relatedInfoExtParam.setSwitchScreenLanguage(switchScreenLanguage);
    }

    public int getBattery() {
        return this.relatedInfoExtParam.getBattery();
    }

    public void setBattery(int Battery) {
        this.relatedInfoExtParam.setBattery(Battery);
    }

    public int getColorType() {
        return this.relatedInfoExtParam.getColorType();
    }

    public void setColorType(int colorType) {
        this.relatedInfoExtParam.setColorType(colorType);
    }

    public long getPanelId() {
        return this.relatedInfoExtParam.getPanelId();
    }

    public void setPanelId(long panelId) {
        this.relatedInfoExtParam.setPanelId(panelId);
    }

    public int getBuzzerState() {
        return this.relatedInfoExtParam.getBuzzerState();
    }

    public void setBuzzerState(int buzzerState) {
        this.relatedInfoExtParam.setBuzzerState(buzzerState);
    }

    public String getSceneDimmerBrt() {
        return this.relatedInfoExtParam.getSceneDimmerBrt();
    }

    public void setSceneDimmerBrt(String sceneDimmerBrt) {
        this.relatedInfoExtParam.setSceneDimmerBrt(sceneDimmerBrt);
    }

    public int getSceneLongPress() {
        return this.relatedInfoExtParam.getSceneLongPress();
    }

    public void setSceneLongPress(int sceneLongPress) {
        this.relatedInfoExtParam.setSceneLongPress(sceneLongPress);
    }

    public void setSensitive(int v) {
        this.relatedInfoExtParam.setSensitive(v);
    }

    public void setReportTime(int v) {
        this.relatedInfoExtParam.setReportTime(v);
    }

    public int getSensitive() {
        return this.relatedInfoExtParam.getSensitive();
    }

    public int getReportTime() {
        return this.relatedInfoExtParam.getReportTime();
    }

    public int getSwitchSort0() {
        return this.relatedInfoExtParam.getSwitchSort0();
    }

    public int getSwitchSort1() {
        return this.relatedInfoExtParam.getSwitchSort1();
    }

    public int getSwitchSort2() {
        return this.relatedInfoExtParam.getSwitchSort2();
    }

    public int getSwitchPowerMemory() {
        return this.relatedInfoExtParam.getSwitchPowerMemory();
    }

    public int getZoneNumber() {
        return this.relatedInfoExtParam.getZoneNumber();
    }

    public boolean isShowKRange() {
        int size = this.relatedInfoExtParam.getRelateParamMap().size();
        int i = 0;
        while (i < size) {
            i++;
            if (isShowKRange(this.relatedInfoExtParam.getRelateInfo(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean isShowKRange(RelatedInfoExtParam.RelateInfo info) {
        if (info == null || info.action <= 9) {
            return false;
        }
        if (info.type == 1) {
            return ProductRepository.getLightColorType((Object) Injection.repo().device().getDeviceByDeviceId(info.objectId)) == 2;
        }
        if (info.type == 2) {
            return ProductRepository.getLightColorType((Object) Injection.repo().group().getGroupByGroupId(info.objectId)) == 2;
        }
        if (info.type != 8) {
            return false;
        }
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(info.objectId);
        return deviceByDeviceId != null ? ProductRepository.getLightColorType((Object) deviceByDeviceId) == 102 : DaliProHelper.isSupportCt(Injection.repo().group().getGroupByGroupId(info.objectId));
    }

    public boolean isKnobShowKRange() {
        int size = this.relatedInfoExtParam.getRelateParamMap().size();
        int i = 0;
        while (i < size) {
            i++;
            RelatedInfoExtParam.RelateInfo relateInfo = this.relatedInfoExtParam.getRelateInfo(i);
            if (relateInfo != null) {
                if (relateInfo.type == 1) {
                    if (ProductRepository.getLightColorType((Object) Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId)) == 2) {
                        return true;
                    }
                } else if (relateInfo.type == 2) {
                    if (ProductRepository.getLightColorType((Object) Injection.repo().group().getGroupByGroupId(relateInfo.objectId)) == 2) {
                        return true;
                    }
                } else if (relateInfo.type != 8) {
                    continue;
                } else {
                    Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(relateInfo.objectId);
                    if (deviceByDeviceId != null) {
                        if (DaliProHelper.isSupportCt(deviceByDeviceId)) {
                            return true;
                        }
                    } else if (DaliProHelper.isSupportCt(Injection.repo().group().getGroupByGroupId(relateInfo.objectId))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}