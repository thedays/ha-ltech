package com.ltech.smarthome.ui.control.provider;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.RelatedInfoExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.screenpanel.ActNewScreenPanel;
import com.ltech.smarthome.ui.device.screenpanel.ActPageScreenPanel;
import com.ltech.smarthome.ui.device.screenpanel.ActScreenPanel;
import com.ltech.smarthome.ui.device.smartpanel.ActNewSmartPanel;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanel;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class SmartPanelGroupItemProvider extends BaseDeviceProvider<Group> {
    public SwitchPanelState panelState;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_group_smart_panel;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 13;
    }

    public SmartPanelGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    public SmartPanelGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
        super(activity, recyclerView, viewModel);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Group data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setVisible(R.id.iv_device_more, true);
        helper.setGone(R.id.iv_switch, !(ProductRepository.getLightColorType((Object) data) != 8) && data.getGroupState().isOnline() && isNormalMode());
        SmartSwitch smartSwitch = (SmartSwitch) helper.getView(R.id.iv_switch);
        if (data.getGroupState() != null && data.getGroupState().getOnOffStates() != null && data.getGroupState().getOnOffStates().size() > 0) {
            smartSwitch.setChecked(data.getGroupState().getOnOffStates().get(0).booleanValue());
        } else {
            smartSwitch.setChecked(false);
        }
        smartSwitch.setOnUserCheckedChangeListener(new SmartSwitch.OnUserCheckedChangeListener() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelGroupItemProvider.1
            @Override // com.ltech.smarthome.view.SmartSwitch.OnUserCheckedChangeListener
            public void onUserCheckedChanged(SmartSwitch view, boolean isChecked) {
                SmartPanelGroupItemProvider.this.sendOnOffCommand(0, isChecked, data);
                SmartPanelGroupItemProvider.this.updateDeviceState(isChecked, data);
            }
        });
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelGroupItemProvider.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) SmartPanelGroupItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) SmartPanelGroupItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) SmartPanelGroupItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) SmartPanelGroupItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) SmartPanelGroupItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) SmartPanelGroupItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                SmartPanelGroupItemProvider smartPanelGroupItemProvider = SmartPanelGroupItemProvider.this;
                smartPanelGroupItemProvider.nav(data, smartPanelGroupItemProvider.viewModel);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Group data, int position) {
        if (ProductRepository.getLightColorType((Object) data) == 8) {
            nav(data, this.viewModel);
        } else {
            showControlDialog(data, this.viewModel);
            checkGroupStatus(data);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Group item, BaseViewModel viewModel) {
        NavUtils.Builder destination;
        if (ProductRepository.getLightColorType((Object) item) == 21) {
            destination = NavUtils.destination(ActPageScreenPanel.class);
        } else {
            if (ProductRepository.getLightColorType((Object) item) == 19) {
                if (!item.getDeviceIds().isEmpty()) {
                    Iterator<Long> it = item.getDeviceIds().iterator();
                    while (it.hasNext()) {
                        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(it.next().longValue());
                        if (deviceByDeviceId == null || deviceByDeviceId.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
                        }
                    }
                    destination = NavUtils.destination(ActNewScreenPanel.class);
                }
                destination = NavUtils.destination(ActNewSmartPanel.class);
                break;
            }
            boolean z = false;
            if (!item.getDeviceIds().isEmpty()) {
                Iterator<Long> it2 = item.getDeviceIds().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = true;
                        break;
                    }
                    Device deviceByDeviceId2 = Injection.repo().device().getDeviceByDeviceId(it2.next().longValue());
                    if (deviceByDeviceId2 != null && !deviceByDeviceId2.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) && !deviceByDeviceId2.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) && !deviceByDeviceId2.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
                        break;
                    }
                }
            }
            RelatedInfoExtParam relatedInfoExtParam = new RelatedInfoExtParam();
            relatedInfoExtParam.fillMapWithString(item.getExtParam());
            if (z || relatedInfoExtParam.getSwitchShowType() == 2) {
                if (RelaySeparationHelper.isRelaySeparationDevice(item)) {
                    destination = NavUtils.destination(ActNewScreenPanel.class);
                } else {
                    destination = NavUtils.destination(ActScreenPanel.class);
                }
            } else if (RelaySeparationHelper.isRelaySeparationDevice(item)) {
                destination = NavUtils.destination(ActNewSmartPanel.class);
            } else {
                destination = NavUtils.destination(ActSmartPanel.class);
            }
        }
        destination.withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withBoolean(Constants.GROUP_CONTROL, true);
        viewModel.navigation(destination);
    }

    private void showControlDialog(final Group item, final BaseViewModel viewModel) {
        boolean[] zArr = null;
        RelateInfoUtils.relatedInfoList = null;
        RelateInfoUtils.initRelateInfoList(item);
        if (item.getGroupState().getOnOffStates() != null && !item.getGroupState().getOnOffStates().isEmpty() && item.getGroupState().getOnOffStates().size() == RelateInfoUtils.relatedInfoList.size()) {
            List<Boolean> onOffStates = item.getGroupState().getOnOffStates();
            int size = onOffStates.size();
            boolean[] zArr2 = new boolean[size];
            for (int i = 0; i < size; i++) {
                zArr2[i] = onOffStates.get(i).booleanValue();
            }
            zArr = zArr2;
        }
        int size2 = RelateInfoUtils.relatedInfoList.size();
        ArrayList arrayList = new ArrayList();
        if (RelaySeparationHelper.isRelaySeparationDevice(item)) {
            int lightColorType = ProductRepository.getLightColorType((Object) item);
            if (lightColorType == 8) {
                size2 = 1;
            } else if (lightColorType == 9) {
                size2 = 2;
            } else if (lightColorType == 19 || lightColorType == 10) {
                size2 = 3;
            } else if (lightColorType == 18 || lightColorType == 11 || lightColorType == 21) {
                size2 = 4;
            }
            List<Group> subGroup = Injection.repo().group().getSubGroup(item.getPlaceId(), item.getGroupId());
            if (!subGroup.isEmpty()) {
                Collections.sort(subGroup, new Comparator<Group>(this) { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelGroupItemProvider.3
                    @Override // java.util.Comparator
                    public int compare(Group o1, Group o2) {
                        return o1.getSubkey() - o2.getSubkey();
                    }
                });
                for (int i2 = 0; i2 < RelateInfoUtils.relatedInfoList.size(); i2++) {
                    if (i2 < subGroup.size()) {
                        arrayList.add(new RelateInfoItem(subGroup.get(i2).getGroupName()));
                    } else {
                        arrayList.add(RelateInfoUtils.relatedInfoList.get(i2));
                    }
                }
            } else {
                arrayList.addAll(RelateInfoUtils.relatedInfoList);
            }
        } else {
            arrayList.addAll(RelateInfoUtils.relatedInfoList);
        }
        final SpQuickDialog asDefault = SpQuickDialog.asDefault();
        asDefault.setShowMax(size2);
        asDefault.setDeviceName(item.getGroupName()).setPanelStatus(zArr).setRelateInfoList(arrayList).setCallback(new SpQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelGroupItemProvider.4
            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onItemClick(int position, boolean state) {
                SmartPanelGroupItemProvider.this.sendOnOffCommand(position, state, item);
                SmartPanelGroupItemProvider.this.updateGroupStates(asDefault, item);
            }

            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onMoreAction() {
                SmartPanelGroupItemProvider.this.nav(item, viewModel);
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

    private void sendBindCommand(int position, Group group) {
        CmdAssistant.getDeviceAssistant(group, 1 << position).panelBindCmdControl(this.mContext);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceState(boolean isChecked, Group group) {
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
        Group subGroupOnOff;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < dialog.selectArray.length; i++) {
            arrayList.add(Boolean.valueOf(dialog.selectArray[i]));
            if (group.getDeviceIds().isEmpty() && (subGroupOnOff = RelaySeparationHelper.setSubGroupOnOff(group, i + 1, dialog.selectArray[i])) != null) {
                this.onDataChangeListener.onDataChange(subGroupOnOff);
            }
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
        CmdAssistant.getQueryCmdAssistant(Integer.valueOf(group.getFirstDevUniAddr()), new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelGroupItemProvider$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SmartPanelGroupItemProvider.this.lambda$queryDeviceStatus$0(device, zoneCount, dialog, group, (ResponseMsg) obj);
            }
        }, 4);
        MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelGroupItemProvider$$ExternalSyntheticLambda1
            @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                SmartPanelGroupItemProvider.this.lambda$queryDeviceStatus$1(group, zoneCount, dialog, responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceStatus$0(Device device, int i, SpQuickDialog spQuickDialog, Group group, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, i, responseMsg.getResData()), spQuickDialog, group);
        } else {
            refreshPanelState(null, spQuickDialog, group);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceStatus$1(Group group, int i, SpQuickDialog spQuickDialog, ResponseMsg responseMsg) {
        refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), group.getGroupAddress(), i, responseMsg.getResData()), spQuickDialog, group);
    }
}