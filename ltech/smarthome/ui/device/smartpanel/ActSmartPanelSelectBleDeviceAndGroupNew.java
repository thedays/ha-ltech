package com.ltech.smarthome.ui.device.smartpanel;

import android.content.Intent;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.databinding.ActSelect3Binding;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity;
import com.ltech.smarthome.ui.newselect.FtDeviceGroupVM;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.Utils;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectBleDeviceAndGroupNew extends BaseRoomDeviceGroupActivity<ActSelect3Binding, FtDeviceGroupVM> implements ISelectAction {
    private long controlId;
    private boolean groupControl;
    protected String productId;
    private long roleId;

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setTitle(getString(R.string.choose_device));
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.setVisibility(0);
        ((ActSelect3Binding) this.mViewBinding).title.ivSearch.getLayoutParams().width = Utils.dip2px(this, 30.0f);
        ((ActSelect3Binding) this.mViewBinding).layoutSort.setVisibility(8);
        ((ActSelect3Binding) this.mViewBinding).layoutSortAndType.setVisibility(0);
        this.listType = 4;
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        this.roleId = getIntent().getLongExtra(Constants.ROLE_ID, 0L);
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void groupClick(Group group) {
        super.groupClick(group);
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            Intent intent = new Intent();
            intent.putExtra(Constants.RELATE_ID, group.getId());
            intent.putExtra(Constants.GROUP_RELATE, 2);
            setResult(3001, intent);
            finishActivity();
            return;
        }
        int lightColorType = ProductRepository.getLightColorType((Object) group);
        if (lightColorType == 14) {
            NavUtils.destination(ActSmartPanelSelectBleCurtainRelatedAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, group.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
            return;
        }
        if (lightColorType != 16) {
            if (lightColorType != 21 && lightColorType != 26 && lightColorType != 18 && lightColorType != 19) {
                switch (lightColorType) {
                    case 7:
                        NavUtils.destination(ActSmartPanelSelectBleSwitchRelatedAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, group.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        break;
                    case 12:
                        break;
                    default:
                        NavUtils.destination(ActSmartPanelSelectRelatedActionNew.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, group.getId()).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                        break;
                }
                return;
            }
            NavUtils.destination(ActSelectRelateKey.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, group.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
            return;
        }
        NavUtils.destination(ActSmartPanelSelectTrigRelatedAction.class).withBoolean(Constants.IS_SCENE, false).withInt(Constants.SUB_TYPE, ProductRepository.getLightColorType((Object) group) != 12 ? 3 : 0).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, group.getId()).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected void deviceClick(Device device) {
        char c2;
        super.deviceClick(device);
        if (getIntent().getBooleanExtra(Constants.IS_GQ, false)) {
            if (ProductId.ID_BLE_LIGHT_CGD_PRO.equalsIgnoreCase(device.getProductId())) {
                NavUtils.destination(ActSmartPanelSelectCgdProAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withLong("device_id", device.getDeviceId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withBoolean(Constants.IS_GQ, true).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
            }
            Intent intent = new Intent();
            intent.putExtra(Constants.RELATE_ID, device.getId());
            intent.putExtra(Constants.GROUP_RELATE, 1);
            setResult(3001, intent);
            finishActivity();
            return;
        }
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
            case -1710907378:
                if (productId.equals(ProductId.ID_BLE_KBS)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -1309274422:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -1308265372:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_12S)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -1265646206:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -1084555505:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case -1082613022:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S6)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -969622016:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case -324427448:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_6S)) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case 294483828:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_4S)) {
                    c2 = 16;
                    break;
                }
                c2 = 65535;
                break;
            case 414687077:
                if (productId.equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
                    c2 = 17;
                    break;
                }
                c2 = 65535;
                break;
            case 427686243:
                if (productId.equals(ProductId.ID_SMART_PANEL_G4)) {
                    c2 = 18;
                    break;
                }
                c2 = 65535;
                break;
            case 811752507:
                if (productId.equals(ProductId.ID_ANDROID_SUPER_PANEL_MINI)) {
                    c2 = 19;
                    break;
                }
                c2 = 65535;
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 20;
                    break;
                }
                c2 = 65535;
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = 21;
                    break;
                }
                c2 = 65535;
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 22;
                    break;
                }
                c2 = 65535;
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 23;
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
            case 3:
            case 4:
            case 5:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 15:
            case 16:
            case 18:
            case 19:
            case 21:
            case 22:
                NavUtils.destination(ActSelectRelateKey.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 6:
            case 23:
                NavUtils.destination(ActSmartPanelSelectBleCurtainRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 14:
                NavUtils.destination(ActSmartPanelSelectBleSwitchRelatedAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withInt(Constants.GROUP_RELATE, 1).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 17:
                NavUtils.destination(ActSmartPanelSelectCgdProAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withLong("device_id", device.getDeviceId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 20:
                DryContactBleParam dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class);
                int subType = dryContactBleParam != null ? dryContactBleParam.getSubType() : 1;
                NavUtils.destination(ActSmartPanelSelectTrigRelatedAction.class).withBoolean(Constants.IS_SCENE, subType == 1 || subType == 2).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, device.getId()).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.SUB_TYPE, subType).withString(Constants.PRODUCT_ID, this.productId).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            default:
                NavUtils.destination(ActSmartPanelSelectRelatedActionNew.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
        }
    }

    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    protected boolean filterGroup(Group group) {
        if (isRc4s() && ProductRepository.getLightColorType((Object) group) == 7) {
            return true;
        }
        if (isGq() && ProductRepository.getLightColorType((Object) group) == 7) {
            return false;
        }
        if (group.getMaingroupid() != 0 && ProductRepository.getLightColorType((Object) group) == 7 && group.getMaingroupid() == this.roleId) {
            return false;
        }
        if (RelaySeparationHelper.isRelaySeparationSub(group)) {
            return group.getSubhide() != 1;
        }
        if (RelaySeparationHelper.isRelaySeparationDevice(group)) {
            return isRc4s();
        }
        if ("3".equalsIgnoreCase(group.getModuleType()) && !group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR03) && !group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR04) && !group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MS03) && !group.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB1) && !group.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB2) && !group.getGroupKey().equals(ProductId.BLE_GROUP_EUR_PANEL_EB5) && !group.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB1) && !group.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB2) && !group.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB4) && !group.getGroupKey().equals(ProductId.BLE_GROUP_AS_PANEL_UB5)) {
            int lightColorType = ProductRepository.getLightColorType((Object) group);
            if (ProductRepository.isDaliLightGroup(group)) {
                lightColorType = DaliProHelper.convertDaliType(group);
            }
            if (isGq() && lightColorType != 16 && lightColorType != 12 && lightColorType != 14 && lightColorType != 2 && lightColorType != 1 && lightColorType != 3 && lightColorType != 4 && lightColorType != 5 && lightColorType != 20) {
                return false;
            }
            if (this.groupControl) {
                return (group.getDeviceIds() == null || group.getId() == this.controlId) ? false : true;
            }
            if (group.getDeviceIds() != null) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0243, code lost:
    
        if (r0.equals("0A") == false) goto L97;
     */
    @Override // com.ltech.smarthome.ui.newselect.BaseRoomDeviceGroupActivity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean filterDevice(com.ltech.smarthome.model.bean.Device r14) {
        /*
            Method dump skipped, instructions count: 908
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleDeviceAndGroupNew.filterDevice(com.ltech.smarthome.model.bean.Device):boolean");
    }

    private boolean isSmartPanelDevice(Device device) {
        return device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S4) || device.getProductId().equals(ProductId.ID_SWITCH_PANEL_S4M) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            if (isGq()) {
                setResult(3001, data);
                finishActivity();
            } else {
                lambda$edit$1(this);
            }
        }
    }

    public boolean isGq() {
        return getIntent().getBooleanExtra(Constants.IS_GQ, false);
    }

    public boolean isRc4s() {
        return getIntent().getBooleanExtra(Constants.IS_RC4S, false);
    }
}