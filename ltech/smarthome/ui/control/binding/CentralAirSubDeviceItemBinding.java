package com.ltech.smarthome.ui.control.binding;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class CentralAirSubDeviceItemBinding extends BaseDeviceItemBinding {
    private long deviceId;

    @Override // com.ltech.smarthome.ui.control.binding.IBindItem
    public void bindItem(final BaseViewModel viewModel, ItemBinding itemBinding, int position, final Device item) {
        itemBinding.set(23, R.layout.item_central_air_sub_device).bindExtra(37, Integer.valueOf(R.mipmap.ic_device_ac)).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.binding.CentralAirSubDeviceItemBinding$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                CentralAirSubDeviceItemBinding.this.lambda$bindItem$0(item, viewModel, (View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindItem$0(Device device, BaseViewModel baseViewModel, View view) {
        int id = view.getId();
        if (id == R.id.iv_device_more) {
            nav(device, baseViewModel);
        } else if (id == R.id.layout_item_bg) {
            showAcDialog(device, baseViewModel);
        } else {
            if (id != R.id.v_favorite) {
                return;
            }
            setDeviceFavourite(device);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Device device, BaseViewModel viewModel) {
        NavUtils.Builder irNavBuilder = NavHelper.getIrNavBuilder(device.getProductId());
        if (irNavBuilder != null) {
            irNavBuilder.withLong(Constants.CONTROL_ID, device.getId());
            viewModel.navigation(irNavBuilder);
        }
    }

    private void showAcDialog(final Device device, final BaseViewModel viewModel) {
        CentralAirAcQuickDialog.asDefault(device).setTitle(device.getDeviceName()).setDialogCallback(new CentralAirAcQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.binding.CentralAirSubDeviceItemBinding.1
            @Override // com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.OnDialogCallback
            public void onCmdSend(byte[] cmd) {
                CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrControl(ActivityUtils.getTopActivity(), cmd, new boolean[0]);
            }

            @Override // com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.OnDialogCallback
            public void onMoreAction(String stateString) {
                dismiss(stateString);
                CentralAirSubDeviceItemBinding.this.nav(device, viewModel);
            }

            @Override // com.ltech.smarthome.view.dialog.CentralAirAcQuickDialog.OnDialogCallback
            public void dismiss(String stateString) {
                Injection.repo().device().saveDevice(device);
            }
        }).selectState().showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }
}