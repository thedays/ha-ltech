package com.ltech.smarthome.ui.control.binding;

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
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
import com.ltech.smarthome.view.SmartSwitch;
import com.ltech.smarthome.view.dialog.SpQuickDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class SmartPanelGroupItemBinding implements IBindItem<Group> {
    public SwitchPanelState panelState;

    @Override // com.ltech.smarthome.ui.control.binding.IBindItem
    public void bindItem(final BaseViewModel viewModel, ItemBinding itemBinding, int position, final Group item) {
        itemBinding.set(32, R.layout.item_group_smart_panel).bindExtra(34, false).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(item))).bindExtra(35, Boolean.valueOf(ProductRepository.getLightColorType((Object) item) != 8)).bindExtra(8, new SmartSwitch.OnUserCheckedChangeListener() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelGroupItemBinding$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.SmartSwitch.OnUserCheckedChangeListener
            public final void onUserCheckedChanged(SmartSwitch smartSwitch, boolean z) {
                SmartPanelGroupItemBinding.this.lambda$bindItem$0(item, smartSwitch, z);
            }
        }).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelGroupItemBinding$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                SmartPanelGroupItemBinding.this.lambda$bindItem$1(item, viewModel, (View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindItem$0(Group group, SmartSwitch smartSwitch, boolean z) {
        sendOnOffCommand(0, z, group);
        updateDeviceState(z, group);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindItem$1(Group group, BaseViewModel baseViewModel, View view) {
        int id = view.getId();
        if (id == R.id.iv_device_more) {
            nav(group, baseViewModel);
        } else {
            if (id != R.id.layout_item_bg) {
                return;
            }
            if (ProductRepository.getLightColorType((Object) group) == 8) {
                nav(group, baseViewModel);
            } else {
                showControlDialog(group, baseViewModel);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Group item, BaseViewModel viewModel) {
        viewModel.navigation(NavUtils.destination(ActSmartPanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withBoolean(Constants.GROUP_CONTROL, true));
    }

    private void showControlDialog(final Group item, final BaseViewModel viewModel) {
        boolean[] zArr = null;
        RelateInfoUtils.relatedInfoList = null;
        RelateInfoUtils.initRelateInfoList(item);
        if (item.getGroupState().getOnOffStates() != null && !item.getGroupState().getOnOffStates().isEmpty() && item.getGroupState().getOnOffStates().size() == RelateInfoUtils.relatedInfoList.size()) {
            List<Boolean> onOffStates = item.getGroupState().getOnOffStates();
            boolean[] zArr2 = new boolean[onOffStates.size()];
            for (int i = 0; i < onOffStates.size(); i++) {
                zArr2[i] = onOffStates.get(i).booleanValue();
            }
            zArr = zArr2;
        }
        final SpQuickDialog asDefault = SpQuickDialog.asDefault();
        asDefault.setDeviceName(item.getGroupName()).setPanelStatus(zArr).setRelateInfoList(RelateInfoUtils.relatedInfoList).setCallback(new SpQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelGroupItemBinding.1
            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onItemClick(int position, boolean state) {
                SmartPanelGroupItemBinding.this.sendOnOffCommand(position, state, item);
                SmartPanelGroupItemBinding.this.updateGroupStates(asDefault, item);
            }

            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onMoreAction() {
                SmartPanelGroupItemBinding.this.nav(item, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        int zoneCount = ProductRepository.getZoneCount(ProductRepository.getLightColorType((Object) item));
        if (item.getDeviceIds().isEmpty()) {
            return;
        }
        queryDeviceStatus(Injection.repo().device().getDeviceByDeviceId(item.getDeviceIds().get(0).longValue()), zoneCount, asDefault, item);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOnOffCommand(int position, boolean state, Group group) {
        CmdAssistant.getLightCmdAssistant(group, 1 << position).sendSingleOnOff(ActivityUtils.getTopActivity(), state);
    }

    public void refreshPanelState(SwitchPanelState panelState, SpQuickDialog dialog, Group group) {
        if (panelState != null) {
            this.panelState = panelState;
            for (int i = 0; i < panelState.getSwitchPanelZoneStateList().size(); i++) {
                dialog.selectArray[i] = panelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff();
            }
            dialog.notifyDialog();
            updateGroupStates(dialog, group);
        }
    }

    private void updateDeviceState(boolean isChecked, Group group) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Boolean.valueOf(isChecked));
        group.getGroupState().setOnOffStates(arrayList);
        Injection.repo().group().saveGroup(group);
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            long longValue = it.next().longValue();
            List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
            for (int i = 0; i < value.size(); i++) {
                if (longValue == value.get(i).getDeviceId()) {
                    value.get(i).getDeviceState().setOnOffStates(arrayList);
                    Injection.repo().device().saveDevice(value.get(i));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateGroupStates(SpQuickDialog dialog, Group group) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < dialog.selectArray.length; i++) {
            arrayList.add(Boolean.valueOf(dialog.selectArray[i]));
        }
        group.getGroupState().setOnOffStates(arrayList);
        Injection.repo().group().saveGroup(group);
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            long longValue = it.next().longValue();
            List<Device> value = Injection.repo().device().getDeviceListCache().getValue();
            for (int i2 = 0; i2 < value.size(); i2++) {
                if (longValue == value.get(i2).getDeviceId()) {
                    value.get(i2).getDeviceState().setOnOffStates(arrayList);
                    Injection.repo().device().saveDevice(value.get(i2));
                }
            }
        }
    }

    private void queryDeviceStatus(final Device device, final int zoneCount, final SpQuickDialog dialog, final Group group) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelGroupItemBinding$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SmartPanelGroupItemBinding.this.lambda$queryDeviceStatus$2(device, zoneCount, dialog, group, (ResponseMsg) obj);
            }
        }, 4);
        MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.control.binding.SmartPanelGroupItemBinding$$ExternalSyntheticLambda3
            @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                SmartPanelGroupItemBinding.this.lambda$queryDeviceStatus$3(device, zoneCount, dialog, group, responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceStatus$2(Device device, int i, SpQuickDialog spQuickDialog, Group group, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, i, responseMsg.getResData()), spQuickDialog, group);
        } else {
            refreshPanelState(null, spQuickDialog, group);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceStatus$3(Device device, int i, SpQuickDialog spQuickDialog, Group group, ResponseMsg responseMsg) {
        refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), i, responseMsg.getResData()), spQuickDialog, group);
    }
}