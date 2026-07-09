package com.ltech.smarthome.ui.control.provider;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.gqx.ActGqx;
import com.ltech.smarthome.ui.device.remote_controller.ActScenePanel;
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
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class SmartPanelItemProvider extends BaseDeviceProvider<Device> {
    public SwitchPanelState panelState;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_smart_panel;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 9;
    }

    public SmartPanelItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    public SmartPanelItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
        super(activity, recyclerView, viewModel);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        if (data.getProductId().equals(ProductId.ID_SMART_PANEL_S6B)) {
            helper.setGone(R.id.appCompatTextView16, false);
            if (isNormalMode()) {
                helper.setVisible(R.id.iv_device_more, false);
            }
            helper.setGone(R.id.iv_switch, false);
        } else {
            helper.setGone(R.id.appCompatTextView16, (data.getDeviceState().isOnline() || data.isVirtual()) ? false : true);
            if (isNormalMode()) {
                helper.setVisible(R.id.iv_device_more, !data.getProductId().equals(ProductId.ID_SCENE_PANEL_S8));
            }
            helper.setGone(R.id.iv_switch, !(!data.getProductId().equals(ProductId.ID_SMART_SWITCH_S1C) && !data.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO)) && data.getDeviceState().isOnline() && isNormalMode());
        }
        SmartSwitch smartSwitch = (SmartSwitch) helper.getView(R.id.iv_switch);
        if (data.getDeviceState() != null && data.getDeviceState().getOnOffStates() != null && data.getDeviceState().getOnOffStates().size() > 0) {
            smartSwitch.setChecked(data.getDeviceState().getOnOffStates().get(0).booleanValue());
        } else {
            smartSwitch.setChecked(false);
        }
        smartSwitch.setOnUserCheckedChangeListener(new SmartSwitch.OnUserCheckedChangeListener() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider.1
            @Override // com.ltech.smarthome.view.SmartSwitch.OnUserCheckedChangeListener
            public void onUserCheckedChanged(SmartSwitch view, boolean isChecked) {
                RelateInfoUtils.relatedInfoList = null;
                RelateInfoUtils.initRelateInfoList(data);
                if (data.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO) && !StringUtils.isEmpty(RelateInfoUtils.relatedInfoList.get(0).actionInfo)) {
                    SmartPanelItemProvider.this.sendBindCommand(0, data);
                } else {
                    SmartPanelItemProvider.this.sendOnOffCommand(0, isChecked, data);
                }
                SmartPanelItemProvider.this.updateDeviceState(isChecked, data);
            }
        });
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) SmartPanelItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) SmartPanelItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) SmartPanelItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) SmartPanelItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) SmartPanelItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) SmartPanelItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                SmartPanelItemProvider smartPanelItemProvider = SmartPanelItemProvider.this;
                smartPanelItemProvider.nav(data, smartPanelItemProvider.viewModel);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        if (data.getProductId().equals(ProductId.ID_SCENE_PANEL_S8) || data.getProductId().equals(ProductId.ID_SMART_PANEL_S6B) || ProductRepository.getLightColorType((Object) data) == 8) {
            nav(data, this.viewModel);
        } else {
            showControlDialog(data, this.viewModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Device item, BaseViewModel viewModel) {
        if (item.getProductId().equals(ProductId.ID_SCENE_PANEL_S8) || item.getProductId().equals(ProductId.ID_SMART_PANEL_S6B)) {
            viewModel.navigation(NavUtils.destination(ActScenePanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
            return;
        }
        if (item.getProductId().equals(ProductId.ID_SMART_SWITCH_S1_PRO) || item.getProductId().equals(ProductId.ID_SMART_SWITCH_S2_PRO) || item.getProductId().equals(ProductId.ID_SMART_SWITCH_S3_PRO)) {
            if (RelaySeparationHelper.isRelaySeparationDevice(item)) {
                viewModel.navigation(NavUtils.destination(ActNewScreenPanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
                return;
            } else {
                viewModel.navigation(NavUtils.destination(ActScreenPanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
                return;
            }
        }
        if (item.getProductId().equals(ProductId.ID_SMART_SWITCH_S6_PRO)) {
            viewModel.navigation(NavUtils.destination(ActNewScreenPanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
            return;
        }
        if (item.getProductId().equals(ProductId.ID_SMART_PANEL_G4) || item.getProductId().equals(ProductId.ID_SMART_PANEL_G4_PRO)) {
            viewModel.navigation(NavUtils.destination(ActPageScreenPanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
            return;
        }
        if (item.getProductId().equals(ProductId.ID_SMART_PANEL_GQX) || item.getProductId().equals(ProductId.ID_SMART_PANEL_GQ)) {
            viewModel.navigation(NavUtils.destination(ActGqx.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
        } else if (RelaySeparationHelper.isRelaySeparationDevice(item)) {
            viewModel.navigation(NavUtils.destination(ActNewSmartPanel.class).withLong(Constants.PLACE_ID, item.getPlaceId()).withLong(Constants.CONTROL_ID, item.getId()).withInt(Constants.LIGHT_TYPE, ProductRepository.getLightColorType((Object) item)).withString(Constants.PRODUCT_ID, item.getProductId()));
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
            List<Device> subDevice = Injection.repo().device().getSubDevice(item.getPlaceId(), item.getDeviceId());
            if (!subDevice.isEmpty()) {
                Collections.sort(subDevice, new Comparator<Device>(this) { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider.3
                    @Override // java.util.Comparator
                    public int compare(Device o1, Device o2) {
                        return o1.getWifiMac().compareTo(o2.getWifiMac());
                    }
                });
                for (int i2 = 0; i2 < RelateInfoUtils.relatedInfoList.size(); i2++) {
                    if (i2 < subDevice.size()) {
                        arrayList.add(new RelateInfoItem(subDevice.get(i2).getDeviceName()));
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
        asDefault.setDeviceName(item.getDeviceName()).setOnline(item.getDeviceState().isOnline()).setPanelStatus(zArr).setRelateInfoList(arrayList).setCallback(new SpQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider.4
            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onItemClick(int position, boolean state) {
                SmartPanelItemProvider.this.sendOnOffCommand(position, state, item);
                SmartPanelItemProvider.this.updateDeviceStates(asDefault, item);
            }

            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onMoreAction() {
                SmartPanelItemProvider.this.nav(item, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        queryDeviceStatus(item, ProductRepository.getZoneCount(item.getProductId()), asDefault);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOnOffCommand(int position, boolean state, Device device) {
        CmdAssistant.getLightCmdAssistant(device, 1 << position).sendSingleOnOff(ActivityUtils.getTopActivity(), state);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBindCommand(int position, Device device) {
        CmdAssistant.getDeviceAssistant(device, 1 << position).panelBindCmdControl(this.mContext);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceState(boolean isChecked, Device device) {
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
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SmartPanelItemProvider.this.lambda$queryDeviceStatus$0(device, zoneCount, dialog, (ResponseMsg) obj);
            }
        }, 4);
        MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider$$ExternalSyntheticLambda1
            @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                SmartPanelItemProvider.this.lambda$queryDeviceStatus$1(device, zoneCount, dialog, responseMsg);
            }
        });
        ((ObservableSubscribeProxy) checkSingleDeviceStatus(device).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.provider.SmartPanelItemProvider.5
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d2) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Integer integer) {
                Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId());
                if (System.currentTimeMillis() - deviceByDeviceId.getCheckTime() > 180000) {
                    deviceByDeviceId.getDeviceState().setOnlineState(2);
                    Injection.repo().device().saveDevice(deviceByDeviceId);
                    for (Device device2 : Injection.repo().device().queryDevicesByMacDeviceId(Long.valueOf(deviceByDeviceId.getDeviceId()))) {
                        if (device2.isSubDevice()) {
                            device2.getDeviceState().setOnlineState(deviceByDeviceId.getDeviceState().getOnlineState());
                            Injection.repo().device().saveDevice(device2);
                        }
                    }
                    dialog.setOnline(false);
                    dialog.notifyDialog();
                    if (SmartPanelItemProvider.this.onDataChangeListener != null) {
                        SmartPanelItemProvider.this.onDataChangeListener.onDataOfflineChange(integer.intValue());
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceStatus$0(Device device, int i, SpQuickDialog spQuickDialog, ResponseMsg responseMsg) {
        if (responseMsg != null) {
            BleParam bleParam = (BleParam) device.getParam(BleParam.class);
            refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), bleParam != null ? bleParam.getUnicastAddress() : 0, i, responseMsg.getResData()), spQuickDialog, device);
        } else {
            refreshPanelState(null, spQuickDialog, device);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$queryDeviceStatus$1(Device device, int i, SpQuickDialog spQuickDialog, ResponseMsg responseMsg) {
        refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), i, responseMsg.getResData()), spQuickDialog, device);
    }
}