package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import com.alibaba.fastjson.parser.JSONLexer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.automation.trigger.ActSelectSensorTriggerCondition;
import com.ltech.smarthome.ui.device.aspanel.AsHelper;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;
import com.smart.message.utils.LHomeLog;
import io.netty.util.internal.StringUtil;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes4.dex */
public class ActSelectDeviceForCondition extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        setSortType(1);
        this.listType = 4;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            setResult(3003);
            finishActivity();
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        if (Injection.repo().device().getExistGateway() == null) {
            return false;
        }
        String productId = device.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -2060969856:
                if (productId.equals(ProductId.ID_AS_PANEL_UB8)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1822884084:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1343252468:
                if (productId.equals(ProductId.ID_RS485_BLE)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1281119909:
                if (productId.equals(ProductId.ID_RC_B2)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1213322926:
                if (productId.equals(ProductId.ID_RC_B8)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 14;
                    break;
                }
                break;
            case -1073881216:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                    c2 = 15;
                    break;
                }
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = 16;
                    break;
                }
                break;
            case -961541705:
                if (productId.equals(ProductId.ID_SMART_PANEL_S6B)) {
                    c2 = 17;
                    break;
                }
                break;
            case -852623517:
                if (productId.equals(ProductId.ID_RC4S)) {
                    c2 = 18;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 19;
                    break;
                }
                break;
            case -833770237:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = 20;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 21;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 22;
                    break;
                }
                break;
            case -249671171:
                if (productId.equals(ProductId.ID_RC_B5)) {
                    c2 = 23;
                    break;
                }
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = 24;
                    break;
                }
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = 25;
                    break;
                }
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = 27;
                    break;
                }
                break;
            case 42289893:
                if (productId.equals(ProductId.ID_SENSOR_HSD)) {
                    c2 = 28;
                    break;
                }
                break;
            case 70457728:
                if (productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                    c2 = 29;
                    break;
                }
                break;
            case 155753896:
                if (productId.equals(ProductId.ID_DOOR_SENSOR)) {
                    c2 = 30;
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = 31;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = '!';
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 359647590:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = '#';
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = '$';
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = '%';
                    break;
                }
                break;
            case 377377599:
                if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = '(';
                    break;
                }
                break;
            case 613226983:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                    c2 = ')';
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = '*';
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '+';
                    break;
                }
                break;
            case 1181428532:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
            case 1309050445:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQX)) {
                    c2 = Soundex.SILENT_MARKER;
                    break;
                }
                break;
            case 1473345811:
                if (productId.equals(ProductId.ID_RC_B1)) {
                    c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                    break;
                }
                break;
            case 1479279198:
                if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = '/';
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = '0';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '1';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '2';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = '3';
                    break;
                }
                break;
        }
        switch (c2) {
            case 2:
            case 3:
            case 4:
            case 5:
            case '\r':
            case 14:
            case 16:
            case 19:
            case 21:
            case ' ':
            case '\'':
            case '(':
            case '1':
            case '2':
                if (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                }
                break;
            case '!':
            case '\"':
            case '$':
            case '%':
                if (!AsHelper.isNewUb(device) || ((BleParam) device.getParam(BleParam.class)).getGroupId() != 0) {
                }
                break;
            case '0':
                if (((BleParam) device.getParam(BleParam.class)).getGroupId() != 0 || !EurHelper.hasPositionBind(device)) {
                }
                break;
        }
        return false;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    public void deviceClick(Device device) {
        NavUtils.Builder destination;
        LHomeLog.i(getClass(), "deviceClick=" + device.getProductId() + "__deviceId=" + device.getDeviceId() + ProductId.SPLIT + device.getDeviceName() + "__" + device.getFloorName() + "__=" + device.getRoomName());
        if (ProductId.ID_KEY_SWITCH_1.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_KEY_SWITCH_2.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_KEY_SWITCH_3.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_KEY_SWITCH_4.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_BODY_SENSOR.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorConditionAction.class);
            destination.withInt(Constants.SENSOR_TYPE_NUM, Integer.parseInt("01"));
        } else if (ProductId.ID_SENSOR_MR03.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorConditionAction.class);
            destination.withInt(Constants.SENSOR_TYPE_NUM, Integer.parseInt("03"));
        } else if (ProductId.ID_SENSOR_MR04.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorConditionAction.class);
            destination.withInt(Constants.SENSOR_TYPE_NUM, Integer.parseInt("04"));
        } else if (ProductId.ID_SENSOR_MS03.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorTriggerCondition.class);
        } else if (ProductId.ID_SENSOR_HSD.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSensorTriggerCondition.class);
        } else if (ProductId.ID_SMART_SWITCH_S1C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_SMART_SWITCH_S2C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_SMART_SWITCH_S3C.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_SMART_SWITCH_S4.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_SWITCH_PANEL_S4M.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_SMART_SWITCH_S6_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionActionDialog.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 6);
        } else if (ProductId.ID_SMART_SWITCH_S6.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionActionDialog.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 6);
        } else if (ProductId.ID_SCENE_PANEL_S8.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 8);
        } else if (ProductId.ID_SMART_PANEL_S6B.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionActionDialog.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 6);
        } else if (ProductId.ID_SMART_SWITCH_S1_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_SMART_SWITCH_S2_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_SMART_SWITCH_S3_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 12);
        } else if (ProductId.ID_SMART_SWITCH_SQ.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_SQB.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_SQ_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_DRY_CONTACT_TO_BLE.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            if (device.getParam(DryContactBleParam.class) != null) {
                if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() - 1 == 0) {
                    destination.withInt(Constants.SWITCH_KEY_SUM, 16);
                } else if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() - 1 == 1) {
                    destination.withInt(Constants.SWITCH_KEY_SUM, 17);
                } else {
                    destination.withInt(Constants.SWITCH_KEY_SUM, 15);
                }
            } else {
                destination.withInt(Constants.SWITCH_KEY_SUM, 15);
            }
        } else if (ProductId.ID_ANDROID_SUPER_PANEL.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 8);
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else if (ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else {
            if (ProductId.ID_RS485_BLE.equals(device.getProductId())) {
                Cg485Helper.showConditionDialog(this, device, 0);
            } else if (ProductRepository.isAsPanel(device.getProductId()) || ProductRepository.isEurPanel(device.getProductId()) || ProductRepository.isRcB(device.getProductId())) {
                destination = NavUtils.destination(ActSelecEurConditionAction.class);
            } else if (ProductId.ID_DOOR_SENSOR.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectDefaultConditionDialog.class);
            } else if (ProductId.ID_SMART_PANEL_GQ.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_GQX.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 1);
            } else if (ProductId.ID_RC4S.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectZoneConditionAction.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 4);
            } else if (ProductId.ID_BLE_CURTAIN.equals(device.getProductId()) || ProductId.ID_BLE_CURTAIN_CG_CURH3.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectCurtainConditionAction.class);
            }
            destination = null;
        }
        if (destination != null) {
            destination.withLong("device_id", device.getDeviceId()).withString(Constants.FLOOR_NAME, device.getFloorName()).withString(Constants.ROOM_NAME, device.getRoomName()).withString("device_name", device.getDeviceName()).withString(Constants.PRODUCT_ID, device.getProductId()).withString(Constants.MAC_ADDRESS, device.getWifiMac()).withDefaultRequestCode().navigation(this);
        }
    }
}