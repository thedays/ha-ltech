package com.ltech.smarthome.ui.scene;

import android.content.Intent;
import com.alibaba.fastjson.parser.JSONLexer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.adapter.DefaultAdapter;
import com.ltech.smarthome.adapter.Gloading;
import com.ltech.smarthome.base.BaseDeviceManageActivity;
import com.ltech.smarthome.databinding.ActDeviceManageBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;

/* loaded from: classes4.dex */
public class ActSelectDeviceForAction extends BaseDeviceManageActivity<ActDeviceManageBinding, ActSelectDeviceForActionVM> {
    private Boolean isExistGateway;

    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActDeviceManageBinding) this.mViewBinding).layoutSearch.setVisibility(0);
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

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.ltech.smarthome.base.BaseDeviceManageActivity
    protected boolean filterDevice(Device device) {
        Device deviceByDeviceId;
        if (this.isExistGateway == null) {
            this.isExistGateway = Boolean.valueOf(Injection.repo().device().getExistGateway() != null);
        }
        if (!this.isExistGateway.booleanValue() && ProductRepository.isBLeDevice(device.getProductId())) {
            return false;
        }
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
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1698123058:
                if (productId.equals(ProductId.ID_MESH_GATEWAY)) {
                    c2 = 3;
                    break;
                }
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 4;
                    break;
                }
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = 5;
                    break;
                }
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 6;
                    break;
                }
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = 7;
                    break;
                }
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = '\b';
                    break;
                }
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = '\t';
                    break;
                }
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = '\n';
                    break;
                }
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = 11;
                    break;
                }
                break;
            case -208296259:
                if (productId.equals(ProductId.ID_RC4)) {
                    c2 = '\f';
                    break;
                }
                break;
            case -207348713:
                if (productId.equals(ProductId.ID_KEY_SWITCH_1)) {
                    c2 = '\r';
                    break;
                }
                break;
            case -206567420:
                if (productId.equals(ProductId.ID_KEY_SWITCH_2)) {
                    c2 = 14;
                    break;
                }
                break;
            case -206510721:
                if (productId.equals(ProductId.ID_KEY_SWITCH_3)) {
                    c2 = 15;
                    break;
                }
                break;
            case -206454022:
                if (productId.equals(ProductId.ID_KEY_SWITCH_4)) {
                    c2 = 16;
                    break;
                }
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 17;
                    break;
                }
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 18;
                    break;
                }
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 19;
                    break;
                }
                break;
            case 356111630:
                if (productId.equals(ProductId.ID_AS_PANEL_U1S)) {
                    c2 = 20;
                    break;
                }
                break;
            case 356193315:
                if (productId.equals(ProductId.ID_AS_PANEL_U2S)) {
                    c2 = 21;
                    break;
                }
                break;
            case 376429092:
                if (productId.equals(ProductId.ID_AS_PANEL_U4S)) {
                    c2 = 22;
                    break;
                }
                break;
            case 376488674:
                if (productId.equals(ProductId.ID_AS_PANEL_U5S)) {
                    c2 = 23;
                    break;
                }
                break;
            case 377377599:
                if (productId.equals(ProductId.ID_BODY_SENSOR)) {
                    c2 = 24;
                    break;
                }
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 25;
                    break;
                }
                break;
            case 956710656:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 1097035898:
                if (productId.equals(ProductId.ID_SCENE_PANEL_S8)) {
                    c2 = 27;
                    break;
                }
                break;
            case 1378424449:
                if (productId.equals(ProductId.ID_CENTRE_AIR_PRO_GATEWAY)) {
                    c2 = 28;
                    break;
                }
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = 29;
                    break;
                }
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 30;
                    break;
                }
                break;
            case 2002295507:
                if (productId.equals(ProductId.ID_CENTRE_AIR_GATEWAY)) {
                    c2 = 31;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 18:
            case 29:
            case 30:
                return ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
            case 3:
            case 4:
            case 5:
            case 6:
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 31:
                return false;
            case 17:
                return !RelaySeparationHelper.isRelaySeparationSub(device) || (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getMacdeviceid())) == null || deviceByDeviceId.getParam() == null || deviceByDeviceId.getParam(BleParam.class) == null || ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getGroupId() == 0;
            default:
                return true;
        }
    }
}