package com.ltech.smarthome.ui.device.smartpanel;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalActivity;
import com.ltech.smarthome.base.BaseSelectDeviceGroupActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Role;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.scene.ISelectAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSmartPanelSelectBleDeviceAndGroup extends BaseSelectDeviceGroupActivity implements ISelectAction {
    private long controlId;
    private boolean groupControl;

    @Override // com.ltech.smarthome.base.BaseList2Activity
    protected int itemLayout() {
        return R.layout.item_device_manage;
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    public /* synthetic */ void saveAction(BaseNormalActivity baseNormalActivity, boolean z) {
        ISelectAction.CC.$default$saveAction(this, baseNormalActivity, z);
    }

    @Override // com.ltech.smarthome.ui.scene.ISelectAction
    /* renamed from: setSelectResult */
    public /* synthetic */ void lambda$edit$1(BaseNormalActivity baseNormalActivity) {
        ISelectAction.CC.$default$setSelectResult(this, baseNormalActivity);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseList2Activity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.groupControl = getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSmartPanelSelectBleDeviceAndGroup.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Role role = (Role) this.mAdapter.getData().get(i);
        if (role instanceof Device) {
            deviceClick((Device) role);
        } else if (role instanceof Group) {
            groupClick((Group) role);
        }
    }

    private void groupClick(Group group) {
        switch (ProductRepository.getLightColorType((Object) group)) {
            case 7:
                NavUtils.destination(ActSmartPanelSelectBleSwitchRelatedAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, group.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 18:
                NavUtils.destination(ActSelectRelateKey.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, group.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 12:
            case 16:
                NavUtils.destination(ActSmartPanelSelectTrigRelatedAction.class).withBoolean(Constants.IS_SCENE, false).withInt(Constants.SUB_TYPE, ProductRepository.getLightColorType((Object) group) != 12 ? 3 : 0).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, group.getId()).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            case 13:
            case 15:
            case 17:
            default:
                NavUtils.destination(ActSmartPanelSelectRelatedAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, group.getId()).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 14:
                NavUtils.destination(ActSmartPanelSelectBleCurtainRelatedAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, group.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 2).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, getIntent().getBooleanExtra(Constants.GROUP_CONTROL, false)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void deviceClick(Device device) {
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
            case 166485422:
                if (productId.equals(ProductId.ID_BLE_SWITCH)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 11;
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
            case 7:
            case '\t':
            case '\n':
                NavUtils.destination(ActSelectRelateKey.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 3:
            case 11:
                NavUtils.destination(ActSmartPanelSelectBleCurtainRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 6:
                NavUtils.destination(ActSmartPanelSelectBleSwitchRelatedAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withInt(Constants.GROUP_RELATE, 1).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case '\b':
                DryContactBleParam dryContactBleParam = (DryContactBleParam) device.getParam(DryContactBleParam.class);
                int subType = dryContactBleParam != null ? dryContactBleParam.getSubType() : 1;
                NavUtils.destination(ActSmartPanelSelectTrigRelatedAction.class).withBoolean(Constants.IS_SCENE, subType == 1 || subType == 2).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, device.getId()).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withInt(Constants.SUB_TYPE, subType).withString(Constants.PRODUCT_ID, this.productId).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            default:
                NavUtils.destination(ActSmartPanelSelectRelatedAction.class).withLong(Constants.CONTROL_ID, this.controlId).withLong(Constants.RELATE_ID, device.getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, 1).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withBoolean(Constants.GROUP_CONTROL, this.groupControl).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseList2Activity
    public void convertView(BaseViewHolder helper, Role item) {
        helper.setText(R.id.tv_device_name, item.getName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId()));
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceGroupActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.roleList, new IAction<List<Role>>() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleDeviceAndGroup.1
            @Override // com.ltech.smarthome.base.IAction
            public void act(List<Role> roles) {
                ArrayList arrayList = new ArrayList();
                for (Role role : roles) {
                    if (role instanceof Device) {
                        ActSmartPanelSelectBleDeviceAndGroup.this.addDevice((Device) role, arrayList);
                    } else if (role instanceof Group) {
                        ActSmartPanelSelectBleDeviceAndGroup.this.addGroup((Group) role, arrayList);
                    }
                }
                if (arrayList.isEmpty()) {
                    ActSmartPanelSelectBleDeviceAndGroup.this.showEmpty();
                } else {
                    ActSmartPanelSelectBleDeviceAndGroup.this.allData = arrayList;
                    ActSmartPanelSelectBleDeviceAndGroup.this.setDataList(arrayList);
                }
            }
        });
        this.refreshData.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelSelectBleDeviceAndGroup$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSmartPanelSelectBleDeviceAndGroup.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        setDataList(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addGroup(Group group, List<Role> validDeviceList) {
        if (!"3".equalsIgnoreCase(group.getModuleType()) || group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR03) || group.getGroupKey().equals(ProductId.BLE_GROUP_WAVE_SENSOR_MR04)) {
            return;
        }
        if (this.groupControl) {
            if (group.getDeviceIds() == null || group.getId() == this.controlId) {
                return;
            }
            validDeviceList.add(group);
            return;
        }
        if (group.getDeviceIds() != null) {
            validDeviceList.add(group);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDevice(Device device, List<Role> validDeviceList) {
        ProductInfo productInfoByPid = ProductRepository.getProductInfoByPid(device.getProductId());
        if (productInfoByPid != null) {
            if ("02".equalsIgnoreCase(productInfoByPid.getProductKey()) || "07".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                validDeviceList.add(device);
            }
            if ("04".equalsIgnoreCase(productInfoByPid.getProductKey())) {
                String subProductKey = productInfoByPid.getSubProductKey();
                subProductKey.hashCode();
                switch (subProductKey) {
                    case "05":
                    case "06":
                    case "09":
                    case "0A":
                    case "0B":
                    case "0C":
                    case "0E":
                    case "0F":
                    case "10":
                    case "11":
                    case "12":
                    case "13":
                    case "18":
                        if (this.groupControl) {
                            Group groupById = Injection.repo().group().getGroupById(this.controlId);
                            if (groupById != null && !groupById.getDeviceIds().contains(Long.valueOf(device.getDeviceId()))) {
                                if (!isSmartPanelDevice(device)) {
                                    validDeviceList.add(device);
                                    break;
                                } else if (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                                    validDeviceList.add(device);
                                    break;
                                }
                            }
                        } else if (Injection.repo().device().getDeviceById(this.controlId).getProductId().equals(ProductId.ID_SCENE_PANEL_S8)) {
                            if (!isSmartPanelDevice(device)) {
                                if (device.getId() != this.controlId) {
                                    validDeviceList.add(device);
                                    break;
                                }
                            } else if (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                                validDeviceList.add(device);
                                break;
                            }
                        } else if (!isSmartPanelDevice(device)) {
                            if (device.getId() != this.controlId) {
                                validDeviceList.add(device);
                                break;
                            }
                        } else if (device.getId() != this.controlId && ((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                            validDeviceList.add(device);
                            break;
                        }
                        break;
                }
            }
        }
    }

    private boolean isSmartPanelDevice(Device device) {
        return device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S4) || device.getProductId().equals(ProductId.ID_SWITCH_PANEL_S4M) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            lambda$edit$1(this);
        }
    }
}