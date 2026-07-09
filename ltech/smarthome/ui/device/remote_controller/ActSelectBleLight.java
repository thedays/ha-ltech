package com.ltech.smarthome.ui.device.remote_controller;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseSelectDeviceActivity;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.product.ProductInfo;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.curtain_motor.ActSelectBleCurtainRelatedAction;
import com.ltech.smarthome.ui.device.smartpanel.ActSelectRelateKey;
import com.ltech.smarthome.ui.device.trig.ActSelectTrigRelatedAction;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSelectBleLight extends BaseSelectDeviceActivity {
    private long controlId;

    @Override // com.ltech.smarthome.base.BaseListActivity
    protected int itemLayout() {
        return R.layout.item_device_manage;
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseListActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        this.controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectBleLight$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ActSelectBleLight.this.lambda$initView$0(baseQuickAdapter, view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public /* synthetic */ void lambda$initView$0(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        char c2;
        String productId = ((Device) this.mAdapter.getData().get(i)).getProductId();
        productId.hashCode();
        switch (productId.hashCode()) {
            case -1287620343:
                if (productId.equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case -835060954:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S1C)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -732569219:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S4)) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 225641606:
                if (productId.equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 1921260107:
                if (productId.equals(ProductId.ID_BLE_DRY_CONTACT)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 1951402182:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S3C)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 1951547293:
                if (productId.equals(ProductId.ID_SMART_SWITCH_S2C)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 1976427583:
                if (productId.equals(ProductId.ID_BLE_CURTAIN)) {
                    c2 = 7;
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
            case 7:
                NavUtils.destination(ActSelectBleCurtainRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, ((Device) this.mAdapter.getData().get(i)).getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
                NavUtils.destination(ActSelectRelateKey.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, ((Device) this.mAdapter.getData().get(i)).getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withInt(Constants.GROUP_RELATE, getIntent().getIntExtra(Constants.GROUP_RELATE, -1)).withInt(Constants.LIGHT_TYPE, getIntent().getIntExtra(Constants.LIGHT_TYPE, 0)).withString(Constants.PRODUCT_ID, this.productId).withDefaultRequestCode().navigation(this);
                break;
            case 4:
                DryContactBleParam dryContactBleParam = (DryContactBleParam) ((Device) this.mAdapter.getData().get(i)).getParam(DryContactBleParam.class);
                int subType = dryContactBleParam != null ? dryContactBleParam.getSubType() : 1;
                NavUtils.destination(ActSelectTrigRelatedAction.class).withBoolean(Constants.IS_SCENE, subType == 1 || subType == 2).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, ((Device) this.mAdapter.getData().get(i)).getId()).withInt(Constants.SUB_TYPE, subType).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
            default:
                NavUtils.destination(ActSelectRelatedAction.class).withLong(Constants.CONTROL_ID, getIntent().getLongExtra(Constants.CONTROL_ID, -1L)).withLong(Constants.RELATE_ID, ((Device) this.mAdapter.getData().get(i)).getId()).withInt(Constants.RELATED_POSITION, getIntent().getIntExtra(Constants.RELATED_POSITION, -1)).withDefaultRequestCode().navigation(this);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.ltech.smarthome.base.BaseListActivity
    public void convertView(BaseViewHolder helper, Device item) {
        helper.setText(R.id.tv_device_name, item.getDeviceName()).setImageResource(R.id.iv_icon, ProductRepository.getProductIcon(item)).setText(R.id.tv_place_info, getPlaceInfo(item.getFloorId(), item.getRoomId()));
        ((AppCompatTextView) helper.getView(R.id.tv_device_name)).getPaint().setFakeBoldText(true);
    }

    @Override // com.ltech.smarthome.base.BaseSelectDeviceActivity, com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        super.startObserve();
        handleData(this.deviceResult, new IAction() { // from class: com.ltech.smarthome.ui.device.remote_controller.ActSelectBleLight$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSelectBleLight.this.lambda$startObserve$1((List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Device device = (Device) it.next();
            ProductInfo productInfoByPid = ProductRepository.getProductInfoByPid(device.getProductId());
            if (productInfoByPid != null && ("02".equalsIgnoreCase(productInfoByPid.getProductKey()) || "04".equalsIgnoreCase(productInfoByPid.getProductKey()) || "07".equalsIgnoreCase(productInfoByPid.getProductKey()))) {
                if (!device.getProductId().equals(ProductId.ID_SCENE_PANEL_S8)) {
                    if (device.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S2C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S3C) || device.getProductId().equals(ProductId.ID_SMART_SWITCH_S4) || device.getProductId().equals(ProductId.ID_SWITCH_PANEL_S4M)) {
                        if (((BleParam) device.getParam(BleParam.class)).getGroupId() == 0) {
                            arrayList.add(device);
                        }
                    } else if (!Injection.repo().device().getDeviceById(this.controlId).getProductId().equals(ProductId.ID_RC4) || !device.getProductId().equals(ProductId.ID_BLE_CURTAIN)) {
                        if (!Injection.repo().device().getDeviceById(this.controlId).getProductId().equals(ProductId.ID_RC4) || !device.getProductId().equals(ProductId.ID_BLE_CURTAIN_CG_CURH3)) {
                            if (device.getProductId().equals(ProductId.ID_BLE_DRY_CONTACT)) {
                                arrayList.add(device);
                            } else {
                                arrayList.add(device);
                            }
                        }
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            showEmpty();
        } else {
            setDataList(arrayList);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3001) {
            finishActivity();
        }
    }
}