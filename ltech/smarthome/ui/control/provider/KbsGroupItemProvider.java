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
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.control.provider.KbsGroupItemProvider;
import com.ltech.smarthome.ui.device.kbs.ActKbs;
import com.ltech.smarthome.ui.device.smartpanel.RelateInfoItem;
import com.ltech.smarthome.ui.device.smartpanel.RelaySeparationHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.dialog.SpQuickDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.product_agreement.bean.SwitchPanelState;
import com.smart.product_agreement.parser.IPanelParser;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class KbsGroupItemProvider extends BaseDeviceProvider<Group> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_mesh_gateway;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 30;
    }

    public KbsGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    public KbsGroupItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
        super(activity, recyclerView, viewModel);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Group data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, !data.getDeviceState().isOnline());
        if (isNormalMode()) {
            helper.setVisible(R.id.iv_device_more, true);
        }
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.KbsGroupItemProvider.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) KbsGroupItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) KbsGroupItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) KbsGroupItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) KbsGroupItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) KbsGroupItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) KbsGroupItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                KbsGroupItemProvider kbsGroupItemProvider = KbsGroupItemProvider.this;
                kbsGroupItemProvider.nav(data, kbsGroupItemProvider.viewModel);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Group group, BaseViewModel viewModel) {
        viewModel.navigation(NavUtils.destination(ActKbs.class).withLong(Constants.CONTROL_ID, group.getId()).withLong(Constants.PLACE_ID, group.getPlaceId()).withBoolean(Constants.GROUP_CONTROL, true));
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Group data, int position) {
        showQuickDialog(data, this.viewModel);
        checkGroupStatus(data);
    }

    private void showQuickDialog(Group group, BaseViewModel viewModel) {
        boolean[] zArr = new boolean[2];
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                zArr[0] = group.getGroupState().getOnOffStates().get(0).booleanValue();
                arrayList.add(new RelateInfoItem(this.mContext.getString(R.string.app_str_smart_panel_switch1)));
            } else {
                zArr[1] = group.getGroupState().getOnOffStates().get(1).booleanValue();
                arrayList.add(new RelateInfoItem(this.mContext.getString(R.string.app_str_smart_panel_switch2)));
            }
        }
        SpQuickDialog asDefault = SpQuickDialog.asDefault();
        asDefault.setDeviceName(group.getGroupName()).setOnline(group.getDeviceState().isOnline()).setPanelStatus(zArr).setRelateInfoList(arrayList).setCallback(new AnonymousClass2(group, viewModel)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        if (group.getDeviceIds().size() > 0) {
            selectStatus(Injection.repo().device().getDeviceByDeviceId(group.getDeviceIds().get(0).longValue()), group, asDefault);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.control.provider.KbsGroupItemProvider$2, reason: invalid class name */
    class AnonymousClass2 implements SpQuickDialog.OnDialogCallback {
        final /* synthetic */ Group val$group;
        final /* synthetic */ BaseViewModel val$viewModel;

        static /* synthetic */ void lambda$onItemClick$0(Boolean bool) {
        }

        AnonymousClass2(final Group val$group, final BaseViewModel val$viewModel) {
            this.val$group = val$group;
            this.val$viewModel = val$viewModel;
        }

        @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
        public void onItemClick(int position, boolean state) {
            KbsGroupItemProvider.this.updateStateLight(this.val$group, state, position);
            LightAssistant lightAssistant = KbsGroupItemProvider.this.getLightAssistant(this.val$group);
            lightAssistant.setZoneNum(1 << position);
            lightAssistant.sendOnOff(ActivityUtils.getTopActivity(), state, new IAction() { // from class: com.ltech.smarthome.ui.control.provider.KbsGroupItemProvider$2$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    KbsGroupItemProvider.AnonymousClass2.lambda$onItemClick$0((Boolean) obj);
                }
            });
        }

        @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
        public void onMoreAction() {
            KbsGroupItemProvider.this.nav(this.val$group, this.val$viewModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LightAssistant getLightAssistant(Object controlObject) {
        return CmdAssistant.getLightCmdAssistant(controlObject, new int[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStateLight(Group group, boolean isChecked, int position) {
        if (position == 0) {
            group.getGroupState().getOnOffStates().set(0, Boolean.valueOf(isChecked));
        } else {
            group.getGroupState().getOnOffStates().set(1, Boolean.valueOf(isChecked));
        }
        Injection.repo().group().saveGroup(group);
        Iterator<Long> it = group.getDeviceIds().iterator();
        while (it.hasNext()) {
            for (Device device : Injection.repo().device().getSubDevice(group.getPlaceId(), it.next().longValue())) {
                if (RelaySeparationHelper.getRelayNum(device) - 1 == position) {
                    device.getDeviceState().setOn(isChecked);
                    Injection.repo().device().saveDevice(device);
                }
            }
        }
        if (this.onDataChangeListener != null) {
            this.onDataChangeListener.onDataOfflineChange(group.getFirstDevUniAddr());
        }
    }

    private void selectStatus(final Device device, final Group group, final SpQuickDialog dialog) {
        CmdAssistant.getQueryCmdAssistant(Integer.valueOf(group.getFirstDevUniAddr()), new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.control.provider.KbsGroupItemProvider$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                KbsGroupItemProvider.this.lambda$selectStatus$0(device, dialog, group, (ResponseMsg) obj);
            }
        }, 4);
        MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.control.provider.KbsGroupItemProvider$$ExternalSyntheticLambda1
            @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                KbsGroupItemProvider.this.lambda$selectStatus$1(group, dialog, responseMsg);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectStatus$0(Device device, SpQuickDialog spQuickDialog, Group group, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, 2, responseMsg.getResData()), spQuickDialog, group);
        } else {
            refreshPanelState(null, spQuickDialog, group);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectStatus$1(Group group, SpQuickDialog spQuickDialog, ResponseMsg responseMsg) {
        refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), group.getFirstDevUniAddr(), 2, responseMsg.getResData()), spQuickDialog, group);
    }

    public void refreshPanelState(SwitchPanelState panelState, SpQuickDialog dialog, Group group) {
        if (panelState != null) {
            dialog.setOnline(true);
            for (int i = 0; i < panelState.getSwitchPanelZoneStateList().size(); i++) {
                dialog.selectArray[i] = panelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff();
                group.getGroupState().getOnOffStates().set(i, Boolean.valueOf(dialog.selectArray[i]));
            }
            Injection.repo().group().saveGroup(group);
            dialog.notifyDialog();
            Iterator<Long> it = group.getDeviceIds().iterator();
            while (it.hasNext()) {
                for (Device device : Injection.repo().device().getSubDevice(group.getPlaceId(), it.next().longValue())) {
                    int relayNum = RelaySeparationHelper.getRelayNum(device);
                    if (relayNum == 1) {
                        device.getDeviceState().setOn(dialog.selectArray[0]);
                    } else if (relayNum == 2) {
                        device.getDeviceState().setOn(dialog.selectArray[1]);
                    }
                    Injection.repo().device().saveDevice(device);
                }
            }
        }
    }
}