package com.ltech.smarthome.ui.device.remote_controller;

import android.content.Intent;
import com.ltech.smarthome.R;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction;
import com.ltech.smarthome.ui.device.smartpanel.ActSelectRelateKey;
import com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;

/* loaded from: classes4.dex */
public class ActSelectBleLightDeviceAndGroup extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> {
    private long controlId;
    private String productId;

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        this.listType = 4;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    public void groupClick(Group group) {
        switch (ProductRepository.getLightColorType((Object) group)) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 18:
                NavUtils.destination(ActSelectRelateKey.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, group.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 12:
            case 16:
                NavUtils.destination(ActSelectTrigRelatedAction.class).withBoolean(Constants.IS_SCENE, false).withInt(Constants.SUB_TYPE, ProductRepository.getLightColorType((Object) group) != 12 ? 3 : 0).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, group.getId()).withBoolean(Constants.GROUP_CONTROL, true).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            case 13:
            case 15:
            case 17:
            default:
                NavUtils.destination(ActSelectRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, group.getId()).withBoolean(Constants.GROUP_CONTROL, true).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) group)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            case 14:
                NavUtils.destination(ActSelectBleCurtainRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, group.getId()).withBoolean(Constants.GROUP_CONTROL, true).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    public void deviceClick(Device device) {
        char c2;
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId.hashCode()) {
            case -1819630261:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1_PRO)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -1817691924:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2_PRO)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1796419228:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case '\b':
            case '\t':
                NavUtils.destination(ActSelectRelateKey.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 3:
            case '\n':
                NavUtils.destination(ActSelectBleCurtainRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            case 7:
                DryContactBleParam dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class);
                int subType = dryContactBleParam != null ? dryContactBleParam.getSubType() : 1;
                NavUtils.destination(ActSelectTrigRelatedAction.class).withBoolean(Constants.IS_SCENE, subType == 1 || subType == 2).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.SUB_TYPE, subType).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            default:
                NavUtils.destination(ActSelectRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) device)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterDevice(Device device) {
        ProductInfo productInfoByPid = ProductRepository.getProductInfoByPid(device.getProductId());
        if (productInfoByPid == null) {
            return false;
        }
        if ((!"02".equalsIgnoreCase(productInfoByPid.getProductKey()) && !"04".equalsIgnoreCase(productInfoByPid.getProductKey()) && !"07".equalsIgnoreCase(productInfoByPid.getProductKey())) || device.getProductId().equals(ProductId.ID_SCENE_PANEL_S8)) {
            return false;
        }
        if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S4) || device.getProductId().equals(ProductId.ID_SWITCH_PANEL_S4M) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
            return ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0;
        }
        if (Injection.repo().device().getDeviceById(this.controlId).getProductId().equals(ProductId.ID_RC4) && (device.getProductId().equals(ProductId.ID_BLE_CURTAIN) || device.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT))) {
            return false;
        }
        return ((Injection.repo().device().getDeviceById(this.controlId).getProductId().equals(ProductId.ID_RC4) && device.getProductId().equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQ) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQB) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_SQ_PRO) || device.getProductId().equals(ProductId.ID_DRY_CONTACT_TO_BLE)) ? false : true;
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        return (!"3".equalsIgnoreCase(group.getModuleType()) || group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR03) || group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR04) || group.getDeviceIds() == null) ? false : true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            finishActivity();
        }
    }
}