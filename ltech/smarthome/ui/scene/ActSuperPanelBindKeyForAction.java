package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import android.text.TextUtils;
import androidx.fragment.app.FragmentActivity;
import com.alibaba.fastjson.parser.JSONLexer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.extra.MaskType;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.cg485.Cg485Helper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;
import io.netty.util.internal.StringUtil;
import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes4.dex */
public class ActSuperPanelBindKeyForAction extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
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

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected Gloading createGLoading() {
        return Gloading.from(new DefaultAdapter().emptyImageRes(R.mipmap.pic_empty_1).emptyStringRes(Injection.repo().device().getSuperPanel() == null ? R.string.no_super_panel : R.string.device_choice_list_empty));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (3001 == resultCode) {
            setResult(3001);
            finishActivity();
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        clickItem(device);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void groupClick(Group group) {
        clickItem(group);
    }

    private void clickItem(Role role) {
        SceneHelper.instance().bindingType = 2;
        boolean z = role instanceof Device;
        if (z) {
            Device device = (Device) role;
            if (device.getProductId().equals(ProductId.ID_RS485_BLE)) {
                SceneHelper.instance().maskType = MaskType.DEVICE;
                SceneHelper.instance().controlObject = role;
                Cg485Helper.showSceneActionDialog((FragmentActivity) ActivityUtils.getTopActivity(), device, false);
                return;
            }
        }
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(SceneHelper.instance().panelid);
        NavUtils.Builder goSelectAction = SceneHelper.instance().goSelectAction(role, !TextUtils.isEmpty(deviceByDeviceId.getMcuversion()));
        if (goSelectAction != null) {
            if (!z) {
                goSelectAction.withInt(Constants.ADDRESS, ((Group) role).getGroupAddress());
            } else {
                Device device2 = (Device) role;
                goSelectAction.withInt(Constants.ADDRESS, device2.getUnicastAddress());
                if (SceneHelper.instance().selectDeviceParam != null && SceneHelper.instance().selectDeviceParam.deviceid == device2.getDeviceId()) {
                    goSelectAction.withString(SceneHelper.SCENE_PARAM_EXT, SceneHelper.instance().selectDeviceParam.sceneParamExt);
                }
            }
            goSelectAction.withLong(Constants.CONTROL_ID, role.getId()).withBoolean(Constants.KEY_SET, !TextUtils.isEmpty(deviceByDeviceId.getMcuversion())).withDefaultRequestCode();
            goSelectAction.navigation(this);
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 13 || lightColorType == 15) {
            return false;
        }
        switch (lightColorType) {
            case 22:
            case 23:
            case 24:
            case 25:
                return false;
            default:
                switch (lightColorType) {
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                        return false;
                    default:
                        return true;
                }
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
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
            case -1698123058:
                if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1642166464:
                if (productId.equals(ProductId.ID_BLE_HAM)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1550133760:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB1)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1343252468:
                if (productId.equals(ProductId.ID_RS485_BLE)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1281119909:
                if (productId.equals(ProductId.ID_RC_B2)) {
                    c2 = 11;
                    break;
                }
                break;
            case -1273434493:
                if (productId.equals(ProductId.ID_SENSOR_MR04)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -1213322926:
                if (productId.equals(ProductId.ID_RC_B8)) {
                    c2 = 14;
                    break;
                }
                break;
            case -1201890867:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ_PRO)) {
                    c2 = 15;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 16;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = 17;
                    break;
                }
                break;
            case -1073881216:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB8)) {
                    c2 = 18;
                    break;
                }
                break;
            case -852623517:
                if (productId.equals(ProductId.ID_RC4S)) {
                    c2 = 19;
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 20;
                    break;
                }
                break;
            case -833770237:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ)) {
                    c2 = 21;
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 22;
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 23;
                    break;
                }
                break;
            case -249671171:
                if (productId.equals(ProductId.ID_RC_B5)) {
                    c2 = 24;
                    break;
                }
                break;
            case -208296259:
                if (productId.equals(ProductId.ID_RC4)) {
                    c2 = 25;
                    break;
                }
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = 27;
                    break;
                }
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = 28;
                    break;
                }
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = 29;
                    break;
                }
                break;
            case 13862565:
                if (productId.equals(ProductId.ID_BLE_RS8)) {
                    c2 = 30;
                    break;
                }
                break;
            case 70457728:
                if (productId.equals(ProductId.ID_DRY_CONTACT_TO_BLE)) {
                    c2 = 31;
                    break;
                }
                break;
            case 155753896:
                if (productId.equals(ProductId.ID_DOOR_SENSOR)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 186184655:
                if (productId.equals(ProductId.ID_SENSOR_MR03)) {
                    c2 = '!';
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = '\"';
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = '#';
                    break;
                }
                break;
            case 353722044:
                if (productId.equals(ProductId.ID_WIFI_CAMERA)) {
                    c2 = '$';
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = '%';
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 359647590:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQ_PRO)) {
                    c2 = '\'';
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = '(';
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = ')';
                    break;
                }
                break;
            case 377377599:
                if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = '*';
                    break;
                }
                break;
            case 534249931:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB2)) {
                    c2 = '+';
                    break;
                }
                break;
            case 613226983:
                if (productId.equals(ProductId.ID_SMART_SWITCH_SQB)) {
                    c2 = StringUtil.COMMA;
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = Soundex.SILENT_MARKER;
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = ClassUtils.PACKAGE_SEPARATOR_CHAR;
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = '/';
                    break;
                }
                break;
            case 1181428532:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQ)) {
                    c2 = '0';
                    break;
                }
                break;
            case 1309050445:
                if (productId.equals(ProductId.ID_SMART_PANEL_GQX)) {
                    c2 = '1';
                    break;
                }
                break;
            case 1378424449:
                if (productId.equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY)) {
                    c2 = '2';
                    break;
                }
                break;
            case 1473345811:
                if (productId.equals(ProductId.ID_RC_B1)) {
                    c2 = '3';
                    break;
                }
                break;
            case 1786777444:
                if (productId.equals(ProductId.ID_BLE_MUSIC_PLAYER)) {
                    c2 = '4';
                    break;
                }
                break;
            case 1861788715:
                if (productId.equals(ProductId.ID_EUR_PANEL_EB5)) {
                    c2 = '5';
                    break;
                }
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = '6';
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '7';
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '8';
                    break;
                }
                break;
            case 2002295507:
                if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                    c2 = '9';
                    break;
                }
                break;
            case 2061235487:
                if (productId.equals(ProductId.ID_WIFI_SONOS)) {
                    c2 = ':';
                    break;
                }
                break;
        }
        switch (c2) {
            case 2:
            case 3:
            case 4:
            case 16:
            case 17:
            case 20:
            case 22:
            case '\"':
            case '6':
            case '7':
            case '8':
                if (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                    return true;
                }
            case 0:
            case 1:
            case 5:
            case 6:
            case 7:
            case '\b':
            case 11:
            case '\f':
            case 14:
            case 15:
            case 18:
            case 19:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '9':
            case ':':
                return false;
            case '\t':
            case '\n':
            case '\r':
            case 23:
            case '#':
            case '-':
                return !TextUtils.isEmpty(device.getMcuversion());
            default:
                return true;
        }
    }
}