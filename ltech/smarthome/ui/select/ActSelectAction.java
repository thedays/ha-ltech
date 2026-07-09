package com.ltech.smarthome.ui.select;

import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.parser.JSONLexer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.SceneHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;

/* loaded from: classes4.dex */
public class ActSelectAction extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        this.listType = 4;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void roleClick(Role role) {
        NavUtils.Builder goSelectLocalAction = SceneHelper.instance().goSelectLocalAction(role);
        if (goSelectLocalAction != null) {
            goSelectLocalAction.withLong(Constants.CONTROL_ID, role.getId()).withDefaultRequestCode().navigation(this);
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        String productId = device.getProductId();
        productId.hashCode();
        char c2 = 65535;
        switch (productId.hashCode()) {
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1805322688:
                if (productId.equals(ProductId.ID_BLE_LIGHT_DIM)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1805199680:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CT)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1804340546:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGB)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1804278081:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBW)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1803448738:
                if (productId.equals(ProductId.ID_BLE_LIGHT_RGBWY)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 14;
                    break;
                }
                break;
            case -728269602:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6T)) {
                    c2 = 15;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 16;
                    break;
                }
                break;
            case 2003796:
                if (productId.equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
                    c2 = 17;
                    break;
                }
                break;
            case 2003797:
                if (productId.equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
                    c2 = 18;
                    break;
                }
                break;
            case 2003798:
                if (productId.equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
                    c2 = 19;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 20;
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = 21;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 22;
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 23;
                    break;
                }
                break;
            case 312618751:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6M)) {
                    c2 = 24;
                    break;
                }
                break;
            case 414687077:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                    c2 = 25;
                    break;
                }
                break;
            case 439998223:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6D)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 662799966:
                if (productId.equals(ProductId.ID_BLE_LIGHT_SPI)) {
                    c2 = 27;
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 28;
                    break;
                }
                break;
            case 1479279198:
                if (productId.equals(ProductId.ID_SENSOR_MS03)) {
                    c2 = 29;
                    break;
                }
                break;
            case 1647983530:
                if (productId.equals(ProductId.ID_KNOB_PANEL_E6A)) {
                    c2 = 30;
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = 31;
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '!';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = '#';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 7:
            case 11:
            case '\r':
            case 14:
            case 21:
            case 22:
            case 29:
            case '!':
            case '\"':
                return ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case '\n':
            case 15:
            case 17:
            case 18:
            case 19:
            case 20:
            case 24:
            case 25:
            case 26:
            case 27:
            case 30:
            case 31:
            case ' ':
            case '#':
                return true;
            case '\b':
            case '\t':
            case '\f':
            case 16:
            case 23:
            case 28:
                return !TextUtils.isEmpty(device.getMcuversion());
            default:
                return false;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            setResult(3001);
            finishActivity();
        }
    }
}