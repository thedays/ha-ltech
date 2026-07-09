package com.ltech.smarthome.ui.control.binding;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.remote_controller.ActScenePanel;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.QueryDeviceStateRunnable;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SmartSwitch;
import com.ltech.smarthome.view.dialog.NormalDialog;
import com.ltech.smarthome.view.dialog.SpQuickDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.ArrayList;
import java.util.List;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class SmartPanelItemBinding extends BaseDeviceItemBinding {
    private NormalDialog mChoiceDialog;
    public SwitchPanelState panelState;

    @Override // com.ltech.smarthome.ui.control.binding.IBindItem
    public void bindItem(final BaseViewModel viewModel, ItemBinding itemBinding, int position, final Device item) {
        itemBinding.set(23, R.layout.item_smart_panel).bindExtra(34, Boolean.valueOf(item.getProductId().equals(ProductId.ID_SCENE_PANEL_S8))).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(item))).bindExtra(35, Boolean.valueOf(!item.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C))).bindExtra(8, new SmartSwitch.OnUserCheckedChangeListener() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.SmartSwitch.OnUserCheckedChangeListener
            public final void onUserCheckedChanged(SmartSwitch smartSwitch, boolean z) {
                SmartPanelItemBinding.this.lambda$bindItem$0(item, smartSwitch, z);
            }
        }).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                SmartPanelItemBinding.this.lambda$bindItem$1(item, viewModel, (View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindItem$0(Device device, SmartSwitch smartSwitch, boolean z) {
        sendOnOffCommand(0, z, device);
        updateDeviceState(z, device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindItem$1(Device device, BaseViewModel baseViewModel, View view) {
        int id = view.getId();
        if (id != R.id.iv_device_more) {
            if (id == R.id.layout_item_bg && bleIsOk()) {
                if (device.getProductId().equals(ProductId.ID_SCENE_PANEL_S8) || ProductRepository.getLightColorType((Object) device) == 8) {
                    nav(device, baseViewModel);
                    return;
                } else {
                    showControlDialog(device, baseViewModel);
                    return;
                }
            }
            return;
        }
        if (bleIsOk()) {
            nav(device, baseViewModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Device item, BaseViewModel viewModel) {
        if (item.getProductId().equals(ProductId.ID_SCENE_PANEL_S8)) {
            viewModel.navigation(NavUtils.destination(ActScenePanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
        } else {
            viewModel.navigation(NavUtils.destination(ActSmartPanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
        }
    }

    private void showControlDialog(final Device item, final BaseViewModel viewModel) {
        boolean[] zArr = null;
        RelateInfoUtils.relatedInfoList = null;
        RelateInfoUtils.initRelateInfoList(item);
        if (item.getDeviceState().getOnOffStates() != null && !item.getDeviceState().getOnOffStates().isEmpty() && item.getDeviceState().getOnOffStates().size() == RelateInfoUtils.relatedInfoList.size()) {
            List<Boolean> onOffStates = item.getDeviceState().getOnOffStates();
            boolean[] zArr2 = new boolean[onOffStates.size()];
            for (int i = 0; i < onOffStates.size(); i++) {
                zArr2[i] = onOffStates.get(i).booleanValue();
            }
            zArr = zArr2;
        }
        final SpQuickDialog asDefault = SpQuickDialog.asDefault();
        asDefault.setDeviceName(item.getDeviceName()).setOnline(item.getDeviceState().isOnline()).setPanelStatus(zArr).setRelateInfoList(RelateInfoUtils.relatedInfoList).setCallback(new SpQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding.1
            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onItemClick(int position, boolean state) {
                SmartPanelItemBinding.this.sendOnOffCommand(position, state, item);
                SmartPanelItemBinding.this.updateDeviceStates(asDefault, item);
            }

            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onMoreAction() {
                SmartPanelItemBinding.this.nav(item, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        queryDeviceStatus(item, ProductRepository.getZoneCount(item.getProductId()), asDefault);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOnOffCommand(int position, boolean state, Device device) {
        CmdAssistant.getLightCmdAssistant(device, 1 << position).sendSingleOnOff(ActivityUtils.getTopActivity(), state);
    }

    public void refreshPanelState(SwitchPanelState panelState, SpQuickDialog dialog, Device device) {
        if (panelState != null) {
            this.panelState = panelState;
            for (int i = 0; i < panelState.getSwitchPanelZoneStateList().size(); i++) {
                dialog.selectArray[i] = panelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff();
            }
            dialog.setOnline(true);
            dialog.notifyDialog();
            device.getDeviceState().setOnlineState(1);
            updateDeviceStates(dialog, device);
        }
    }

    private void updateDeviceState(boolean isChecked, Device device) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Boolean.valueOf(isChecked));
        device.getDeviceState().setOnOffStates(arrayList);
        Injection.repo().device().saveDevice(device);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceStates(SpQuickDialog dialog, Device device) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < dialog.selectArray.length; i++) {
            arrayList.add(Boolean.valueOf(dialog.selectArray[i]));
        }
        device.getDeviceState().setOnOffStates(arrayList);
        Injection.repo().device().saveDevice(device);
    }

    private void queryDeviceStatus(final Device device, final int zoneCount, final SpQuickDialog dialog) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SmartPanelItemBinding.this.lambda$queryDeviceStatus$2(device, zoneCount, dialog, (ResponseMsg) obj);
            }
        }, 4);
        MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding$$ExternalSyntheticLambda1
            @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                SmartPanelItemBinding.this.lambda$queryDeviceStatus$3(device, zoneCount, dialog, responseMsg);
            }
        });
        CmdAssistant.selectStatusDevice(device, new QueryDeviceStateRunnable.UpdateDeviceState(this) { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding.2
            @Override // com.ltech.smarthome.utils.QueryDeviceStateRunnable.UpdateDeviceState
            public void updateDevice(Device device2) {
                ActivityUtils.getTopActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        dialog.setOnline(false);
                        dialog.notifyDialog();
                    }
                });
                device2.getDeviceState().setOnlineState(2);
                Injection.repo().device().saveDevice(device2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceStatus$2(Device device, int i, SpQuickDialog spQuickDialog, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, i, responseMsg.getResData()), spQuickDialog, device);
        } else {
            refreshPanelState(null, spQuickDialog, device);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceStatus$3(Device device, int i, SpQuickDialog spQuickDialog, ResponseMsg responseMsg) {
        refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), i, responseMsg.getResData()), spQuickDialog, device);
    }

    private boolean bleIsOk() {
        if (!Injection.state().isBluetoothEnabled()) {
            ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 0);
            return false;
        }
        boolean isLocationEnabled = Injection.state().isLocationEnabled(ActivityUtils.getTopActivity());
        boolean hasPermissions = AndPermission.hasPermissions(ActivityUtils.getTopActivity(), Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasPermissions) {
                ActivityUtils.getTopActivity().requestPermissions(new String[]{Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION}, 0);
                return false;
            }
            if (!isLocationEnabled) {
                showResetDialog();
                return false;
            }
        }
        return true;
    }

    private void showResetDialog() {
        if (this.mChoiceDialog == null) {
            NormalDialog normalDialog = new NormalDialog(ActivityUtils.getTopActivity(), R.style.MyDialog);
            this.mChoiceDialog = normalDialog;
            normalDialog.setYesOnclickListener(ActivityUtils.getTopActivity().getResources().getString(R.string.go_to_setting), new NormalDialog.onYesOnclickListener() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding.3
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onYesOnclickListener
                public void onYesOnclick() {
                    ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 666);
                    SmartPanelItemBinding.this.mChoiceDialog.dismiss();
                }
            });
            this.mChoiceDialog.setNoOnclickListener(ActivityUtils.getTopActivity().getResources().getString(R.string.no_open), new NormalDialog.onNoOnclickListener() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelItemBinding.4
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onNoOnclickListener
                public void onNoClick() {
                    SmartPanelItemBinding.this.mChoiceDialog.dismiss();
                }
            });
        }
        if (this.mChoiceDialog.isShowing()) {
            return;
        }
        this.mChoiceDialog.show();
    }
}