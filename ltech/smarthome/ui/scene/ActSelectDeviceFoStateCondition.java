package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import com.alibaba.fastjson.parser.JSONLexer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.automation.ActSelectAcStatusDetailCondition;
import com.ltech.smarthome.ui.automation.ActSelectCurtainStatusDetailCondition;
import com.ltech.smarthome.ui.automation.ActSelectLightStatusDetailCondition;
import com.ltech.smarthome.ui.automation.ActSelectRelayStatusDetailCondition;
import com.ltech.smarthome.ui.automation.ActSelectSensorStatusDetailCondition;
import com.ltech.smarthome.ui.automation.trigger.ActSelectSensorTriggerCondition;
import com.ltech.smarthome.ui.device.eurpanel.EurHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;
import com.smart.message.utils.LHomeLog;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
public class ActSelectDeviceFoStateCondition extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private boolean isStateCondition;

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
        ((ActSelect3Binding) this.mViewBinding).tvSort.setText(R.string.app_sort_by_time);
        this.selectSortType = 1;
        this.listType = 4;
        this.isStateCondition = getIntent().getBooleanExtra(Constants.STATE_CONDITION, false);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            setResult(3003);
            finishActivity();
        } else if (5013 == resultCode) {
            setResult(5013);
            finishActivity();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        char c2;
        char c3;
        if (Injection.repo().device().getExistGateway() == null) {
            return false;
        }
        if (this.isStateCondition) {
            String productId = device.getProductId();
            productId.hashCode();
            switch (productId.hashCode()) {
                case -1819630261:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                        c3 = 0;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1817691924:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                        c3 = 1;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1805322688:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                        c3 = 2;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1805199680:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                        c3 = 3;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1804340546:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                        c3 = 4;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1804278081:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                        c3 = 5;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1803448738:
                    if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                        c3 = 6;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1796419228:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                        c3 = 7;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1309274422:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                        c3 = '\b';
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1308265372:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                        c3 = '\t';
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1287620343:
                    if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                        c3 = '\n';
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1273434493:
                    if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                        c3 = 11;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1265646206:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                        c3 = '\f';
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1084555505:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                        c3 = '\r';
                        break;
                    }
                    c3 = 65535;
                    break;
                case -1082613022:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                        c3 = 14;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -969622016:
                    if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                        c3 = 15;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -835060954:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                        c3 = 16;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -732569219:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                        c3 = 17;
                        break;
                    }
                    c3 = 65535;
                    break;
                case -324427448:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                        c3 = 18;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 2003796:
                    if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                        c3 = 19;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 42289893:
                    if (productId.equals(ProductId.ID_SENSOR_HSD)) {
                        c3 = 20;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 166485422:
                    if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                        c3 = 21;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 186184655:
                    if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                        c3 = 22;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 225641606:
                    if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                        c3 = 23;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 294483828:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                        c3 = 24;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 427686243:
                    if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                        c3 = 25;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 956710656:
                    if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                        c3 = JSONLexer.EOI;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 1479279198:
                    if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                        c3 = 27;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 1951402182:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                        c3 = 28;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 1951547293:
                    if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                        c3 = 29;
                        break;
                    }
                    c3 = 65535;
                    break;
                case 1976427583:
                    if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                        c3 = 30;
                        break;
                    }
                    c3 = 65535;
                    break;
                default:
                    c3 = 65535;
                    break;
            }
            switch (c3) {
                case 0:
                case 1:
                case 7:
                case '\r':
                case 14:
                case 15:
                case 16:
                case 17:
                case 23:
                case 25:
                case 28:
                case 29:
                    if (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                    }
                    break;
            }
            return false;
        }
        String productId2 = device.getProductId();
        productId2.hashCode();
        switch (productId2.hashCode()) {
            case -1822884084:
                if (productId2.equals(ProductId.ID_EUR_PANEL_EB6)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1819630261:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1817691924:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -1796419228:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -1550133760:
                if (productId2.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1343252468:
                if (productId2.equals(ProductId.ID_RS485_BLE)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -1309274422:
                if (productId2.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -1281119909:
                if (productId2.equals(ProductId.ID_RC_B2)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -1273434493:
                if (productId2.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case -1265646206:
                if (productId2.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -1213322926:
                if (productId2.equals(ProductId.ID_RC_B8)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case -1084555505:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -1073881216:
                if (productId2.equals(ProductId.ID_EUR_PANEL_EB8)) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case -969622016:
                if (productId2.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case -961541705:
                if (productId2.equals(ProductId.ID_SMART_PANEL_S6B)) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case -835060954:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case -833770237:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = 16;
                    break;
                }
                c2 = 65535;
                break;
            case -732569219:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 17;
                    break;
                }
                c2 = 65535;
                break;
            case -324427448:
                if (productId2.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 18;
                    break;
                }
                c2 = 65535;
                break;
            case -249671171:
                if (productId2.equals(ProductId.ID_RC_B5)) {
                    c2 = 19;
                    break;
                }
                c2 = 65535;
                break;
            case -207348713:
                if (productId2.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = 20;
                    break;
                }
                c2 = 65535;
                break;
            case -206567420:
                if (productId2.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = 21;
                    break;
                }
                c2 = 65535;
                break;
            case -206510721:
                if (productId2.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = 22;
                    break;
                }
                c2 = 65535;
                break;
            case -206454022:
                if (productId2.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = 23;
                    break;
                }
                c2 = 65535;
                break;
            case 70457728:
                if (productId2.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                    c2 = 24;
                    break;
                }
                c2 = 65535;
                break;
            case 155753896:
                if (productId2.equals(ProductId.ID_DOOR_SENSOR)) {
                    c2 = 25;
                    break;
                }
                c2 = 65535;
                break;
            case 186184655:
                if (productId2.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                c2 = 65535;
                break;
            case 225641606:
                if (productId2.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 27;
                    break;
                }
                c2 = 65535;
                break;
            case 359647590:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = 28;
                    break;
                }
                c2 = 65535;
                break;
            case 377377599:
                if (productId2.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = 29;
                    break;
                }
                c2 = 65535;
                break;
            case 427686243:
                if (productId2.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = 30;
                    break;
                }
                c2 = 65535;
                break;
            case 534249931:
                if (productId2.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = 31;
                    break;
                }
                c2 = 65535;
                break;
            case 613226983:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                    c2 = ' ';
                    break;
                }
                c2 = 65535;
                break;
            case 1097035898:
                if (productId2.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '!';
                    break;
                }
                c2 = 65535;
                break;
            case 1181428532:
                if (productId2.equals(ProductId.ID_SMART_PANEL_GQ)) {
                    c2 = '\"';
                    break;
                }
                c2 = 65535;
                break;
            case 1309050445:
                if (productId2.equals(ProductId.ID_SMART_PANEL_GQX)) {
                    c2 = '#';
                    break;
                }
                c2 = 65535;
                break;
            case 1473345811:
                if (productId2.equals(ProductId.ID_RC_B1)) {
                    c2 = '$';
                    break;
                }
                c2 = 65535;
                break;
            case 1479279198:
                if (productId2.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = '%';
                    break;
                }
                c2 = 65535;
                break;
            case 1861788715:
                if (productId2.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = Typography.amp;
                    break;
                }
                c2 = 65535;
                break;
            case 1951402182:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '\'';
                    break;
                }
                c2 = 65535;
                break;
            case 1951547293:
                if (productId2.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '(';
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 11:
            case '\r':
            case 15:
            case 17:
            case 27:
            case 30:
            case 31:
            case '\'':
            case '(':
                if (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                }
                break;
            case '&':
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
        if (this.isStateCondition) {
            if (ProductId.ID_BLE_LIGHT_DIM.equals(device.getProductId()) || ProductId.ID_BLE_LIGHT_CT.equals(device.getProductId()) || ProductId.ID_BLE_LIGHT_RGB.equals(device.getProductId()) || ProductId.ID_BLE_LIGHT_RGBW.equals(device.getProductId()) || ProductId.ID_BLE_LIGHT_RGBWY.equals(device.getProductId()) || ProductId.ID_BLE_SWITCH.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectLightStatusDetailCondition.class);
            } else if (ProductId.ID_BODY_SENSOR.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectSensorStatusDetailCondition.class);
                destination.withInt(Constants.SENSOR_TYPE_NUM, Integer.parseInt("01"));
            } else if (ProductId.ID_SENSOR_MR03.equals(device.getProductId()) || ProductId.ID_SENSOR_MR04.equals(device.getProductId()) || ProductId.ID_SENSOR_MS03.equals(device.getProductId()) || ProductId.ID_SENSOR_HSD.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectSensorStatusDetailCondition.class);
            } else if (ProductId.ID_SMART_SWITCH_S1C.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S1_PRO.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 1);
            } else if (ProductId.ID_SMART_SWITCH_S2C.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S2_PRO.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_4S.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 2);
            } else if (ProductId.ID_SMART_SWITCH_S3C.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S3_PRO.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S6_PRO.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S6.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 3);
            } else if (ProductId.ID_SMART_SWITCH_S4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId()) || ProductId.ID_SWITCH_PANEL_S4M.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 4);
            } else if (ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectRelayStatusDetailCondition.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 4);
            } else if (ProductId.ID_BLE_CURTAIN.equals(device.getProductId()) || ProductId.ID_BLE_CURTAIN_CG_CURH3.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectCurtainStatusDetailCondition.class);
            } else {
                if (ProductId.CENTRAL_AIR_SUB_DEVICE.equals(device.getProductId())) {
                    destination = NavUtils.destination(ActSelectAcStatusDetailCondition.class);
                }
                destination = null;
            }
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
        } else if (ProductId.ID_SMART_SWITCH_S1C.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S1_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchStateConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 1);
        } else if (ProductId.ID_SMART_SWITCH_S2C.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S2_PRO.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_6S.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_12S.equals(device.getProductId()) || ProductId.ID_ANDROID_SUPER_PANEL_G4MAX.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchStateConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 2);
        } else if (ProductId.ID_SMART_SWITCH_S3C.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S3_PRO.equals(device.getProductId()) || ProductId.ID_SMART_SWITCH_S6_PRO.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchStateConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 3);
        } else if (ProductId.ID_SMART_SWITCH_S4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4.equals(device.getProductId()) || ProductId.ID_SMART_PANEL_G4_PRO.equals(device.getProductId()) || ProductId.ID_SWITCH_PANEL_S4M.equals(device.getProductId())) {
            destination = NavUtils.destination(ActSelectSwitchStateConditionAction.class);
            destination.withInt(Constants.SWITCH_KEY_SUM, 4);
        } else {
            if (ProductId.ID_ANDROID_SUPER_PANEL_PRO.equals(device.getProductId())) {
                destination = NavUtils.destination(ActSelectSwitchConditionAction.class);
                destination.withInt(Constants.SWITCH_KEY_SUM, 4);
            }
            destination = null;
        }
        if (destination != null) {
            destination.withLong("device_id", device.getDeviceId()).withString(Constants.FLOOR_NAME, device.getFloorName()).withString(Constants.ROOM_NAME, device.getRoomName()).withString("device_name", device.getDeviceName()).withString(Constants.PRODUCT_ID, device.getProductId()).withString(Constants.MAC_ADDRESS, device.getWifiMac()).withDefaultRequestCode().navigation(this);
        }
    }
}