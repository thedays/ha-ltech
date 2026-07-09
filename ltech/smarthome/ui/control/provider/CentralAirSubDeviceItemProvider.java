package com.ltech.smarthome.ui.control.provider;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.message.CtrlPackager;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.CentralAirSubDeviceParam;
import com.ltech.smarthome.model.device_param.G4teBleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.SmartSwitch;
import com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog;
import com.ltech.smarthome.view.dialog.CentralFloorHeatDialog;
import com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog;
import com.smart.product_agreement.productBle.CmdBleFactory;

/* loaded from: classes4.dex */
public class CentralAirSubDeviceItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_central_air_sub_device;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 10;
    }

    public CentralAirSubDeviceItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, !data.getDeviceState().isOnline());
        helper.setGone(R.id.sb, data.getProductId().equals(ProductId.CENTRAL_AIR_SUB_DEVICE) && data.getDeviceState().isOnline());
        if (data.getProductId().equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
            helper.setImageResource(R.id.appCompatImageView9, R.mipmap.ic_device_ac);
            final CentralAirSubDeviceParam centralAirSubDeviceParam = (CentralAirSubDeviceParam) data.getParam(CentralAirSubDeviceParam.class);
            SmartSwitch smartSwitch = (SmartSwitch) helper.getView(R.id.sb);
            smartSwitch.setChecked(centralAirSubDeviceParam.getOn() > 0);
            smartSwitch.setOnUserCheckedChangeListener(new SmartSwitch.OnUserCheckedChangeListener(this) { // from class: com.ltech.smarthome.ui.control.provider.CentralAirSubDeviceItemProvider.1
                @Override // com.ltech.smarthome.view.SmartSwitch.OnUserCheckedChangeListener
                public void onUserCheckedChanged(SmartSwitch view, boolean isChecked) {
                    CmdAssistant.getDeviceAssistant(data, new int[0]).sendOnOffWithoutResult(ActivityUtils.getTopActivity(), CmdBleFactory.openOrCloseCentralAir(isChecked, centralAirSubDeviceParam.getOutAddr(), centralAirSubDeviceParam.getInAddr()), CtrlPackager.getBleForCentralAirDeviceCtrlPackage(centralAirSubDeviceParam.getUnicastAddress()), 3);
                }
            });
        } else if (data.getProductId().equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
            helper.setImageResource(R.id.appCompatImageView9, R.mipmap.ic_device_floor_heat);
        } else if (data.getProductId().equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
            helper.setImageResource(R.id.appCompatImageView9, R.mipmap.ic_device_newair);
        } else if (data.getProductId().equals(ProductId.ID_SMART_PANEL_G4TE)) {
            G4teBleParam g4teBleParam = (G4teBleParam) data.getParam(G4teBleParam.class);
            if (g4teBleParam.errorCode == 0 && g4teBleParam.airErrorCode == 0) {
                r0 = false;
            }
            helper.setGone(R.id.appCompatTextView18, r0);
        }
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.CentralAirSubDeviceItemProvider.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) CentralAirSubDeviceItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) CentralAirSubDeviceItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) CentralAirSubDeviceItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) CentralAirSubDeviceItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) CentralAirSubDeviceItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) CentralAirSubDeviceItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                CentralAirSubDeviceItemProvider centralAirSubDeviceItemProvider = CentralAirSubDeviceItemProvider.this;
                centralAirSubDeviceItemProvider.nav(data, centralAirSubDeviceItemProvider.viewModel);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        showAcDialog(data, this.viewModel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Device device, BaseViewModel viewModel) {
        NavUtils.Builder irNavBuilder = NavHelper.getIrNavBuilder(device.getProductId());
        if (irNavBuilder != null) {
            irNavBuilder.withLong(Constants.CONTROL_ID, device.getId());
            if (device.getProductId().equals(ProductId.ID_SMART_PANEL_G4TE)) {
                irNavBuilder.withInt(Constants.SUB_TYPE, ((G4teBleParam) device.getParam(G4teBleParam.class)).getSubType());
            }
            viewModel.navigation(irNavBuilder);
        }
    }

    private void showAcDialog(final Device device, final BaseViewModel viewModel) {
        if (device.getProductId().equals(ProductId.CENTRAL_AIR_SUB_DEVICE)) {
            CentralAirAcQuickDialog.asDefault(device).setTitle(device.getDeviceName()).setDialogCallback(new CentralAirAcQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.CentralAirSubDeviceItemProvider.3
                @Override // com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.OnDialogCallback
                public void onCmdSend(byte[] cmd) {
                    CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrControl(ActivityUtils.getTopActivity(), cmd, new boolean[0]);
                }

                @Override // com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.OnDialogCallback
                public void onMoreAction(String stateString) {
                    dismiss(stateString);
                    CentralAirSubDeviceItemProvider.this.nav(device, viewModel);
                }

                @Override // com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.OnDialogCallback
                public void dismiss(String stateString) {
                    Injection.repo().device().saveDevice(device);
                }
            }).selectState().showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            return;
        }
        if (device.getProductId().equals(ProductId.FRESH_AIR_SUB_DEVICE)) {
            CentralFreshAirQuickDialog.asDefault(device).setTitle(device.getDeviceName()).setDialogCallback(new CentralFreshAirQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.CentralAirSubDeviceItemProvider.4
                @Override // com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog.OnDialogCallback
                public void onCmdSend(byte[] cmd) {
                }

                @Override // com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog.OnDialogCallback
                public void onMoreAction(String stateString) {
                    dismiss(stateString);
                    CentralAirSubDeviceItemProvider.this.nav(device, viewModel);
                }

                @Override // com.ltech.smarthome.view.dialog.CentralFreshAirQuickDialog.OnDialogCallback
                public void dismiss(String stateString) {
                    Injection.repo().device().saveDevice(device);
                }
            }).selectState().showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            return;
        }
        if (device.getProductId().equals(ProductId.FLOOR_HEAT_SUB_DEVICE)) {
            CentralFloorHeatDialog.asDefault(device).setTitle(device.getDeviceName()).setDialogCallback(new CentralFloorHeatDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.CentralAirSubDeviceItemProvider.5
                @Override // com.ltech.smarthome.view.dialog.CentralFloorHeatDialog.OnDialogCallback
                public void onCmdSend(byte[] cmd) {
                }

                @Override // com.ltech.smarthome.view.dialog.CentralFloorHeatDialog.OnDialogCallback
                public void onMoreAction(String stateString) {
                    dismiss(stateString);
                    CentralAirSubDeviceItemProvider.this.nav(device, viewModel);
                }

                @Override // com.ltech.smarthome.view.dialog.CentralFloorHeatDialog.OnDialogCallback
                public void dismiss(String stateString) {
                    Injection.repo().device().saveDevice(device);
                }
            }).selectState().showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        } else if (device.getProductId().equals(ProductId.ID_SMART_PANEL_G4TE)) {
            TePanelQuickDialog.asDefault(device).setTitle(device.getDeviceName()).setSubType(((G4teBleParam) device.getParam(G4teBleParam.class)).getSubType()).setDialogCallback(new TePanelQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.CentralAirSubDeviceItemProvider.6
                @Override // com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog.OnDialogCallback
                public void onMoreAction() {
                    CentralAirSubDeviceItemProvider.this.nav(device, viewModel);
                }

                @Override // com.ltech.smarthome.ui.device.central.tepanel.TePanelQuickDialog.OnDialogCallback
                public void dismiss() {
                    Injection.repo().device().saveDevice(device);
                }
            }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        }
    }
}