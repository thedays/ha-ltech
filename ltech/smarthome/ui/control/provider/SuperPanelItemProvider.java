package com.ltech.smarthome.ui.control.provider;

import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
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
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanel;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.relate_assistant.RelateInfoUtils;
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
import java.util.List;

/* loaded from: classes4.dex */
public class SuperPanelItemProvider extends BaseDeviceProvider<Device> {
    public SwitchPanelState panelState;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_super_panel;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 6;
    }

    public SuperPanelItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.tv_online, data.getParam() == null || data.getParam(BleParam.class) == null || ((BleParam) data.getParam(BleParam.class)).getUnicastAddress() == 0);
        if (data.getParam() == null || data.getParam(BleParam.class) == null || ((BleParam) data.getParam(BleParam.class)).getUnicastAddress() == 0) {
            helper.setGone(R.id.appCompatTextView16, false);
            helper.setGone(R.id.appCompatTextView17, false);
        } else {
            helper.setGone(R.id.appCompatTextView16, !data.isOnline());
        }
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.SuperPanelItemProvider.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) SuperPanelItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) SuperPanelItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) SuperPanelItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) SuperPanelItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) SuperPanelItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) SuperPanelItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                SuperPanelItemProvider superPanelItemProvider = SuperPanelItemProvider.this;
                superPanelItemProvider.nav(data, superPanelItemProvider.viewModel);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        if (data.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_PRO) || data.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_4S) || data.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_6S) || data.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_12S) || data.getProductId().equals(ProductId.ID_ANDROID_SUPER_PANEL_G4MAX)) {
            if (data != null && (data.getParam() == null || data.getParam(BleParam.class) == null || ((BleParam) data.getParam(BleParam.class)).getUnicastAddress() == 0)) {
                nav(data, this.viewModel);
                return;
            } else {
                showControlDialog(data, this.viewModel);
                return;
            }
        }
        nav(data, this.viewModel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Device device, BaseViewModel viewModel) {
        viewModel.navigation(NavUtils.destination(ActSuperPanel.class).withLong(Constants.CONTROL_ID, device.getId()));
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
        asDefault.setDeviceName(item.getDeviceName()).setOnline(item.isOnline()).setPanelStatus(zArr).setRelateInfoList(RelateInfoUtils.relatedInfoList).setCallback(new SpQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.SuperPanelItemProvider.2
            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onItemClick(int position, boolean state) {
                SuperPanelItemProvider.this.sendOnOffCommand(position, state, item);
                SuperPanelItemProvider.this.updateDeviceStates(asDefault, item);
            }

            @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
            public void onMoreAction() {
                SuperPanelItemProvider.this.nav(item, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        queryDeviceStatus(item, ProductRepository.getZoneCount(item.getProductId()), asDefault);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOnOffCommand(int position, boolean state, Device device) {
        CmdAssistant.getLightCmdAssistant(device, 1 << position).sendSingleOnOff(ActivityUtils.getTopActivity(), state);
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

    public void refreshPanelState(SwitchPanelState panelState, SpQuickDialog dialog, Device device) {
        if (panelState != null) {
            this.panelState = panelState;
            for (int i = 0; i < panelState.getSwitchPanelZoneStateList().size(); i++) {
                dialog.selectArray[i] = panelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff();
            }
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

    private void queryDeviceStatus(final Device device, final int zoneCount, final SpQuickDialog dialog) {
        CmdAssistant.getQueryCmdAssistant(device, new int[0]).queryPanelState(ActivityUtils.getTopActivity(), new IAction() { // from class: com.ltech.smarthome.ui.control.provider.SuperPanelItemProvider$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                SuperPanelItemProvider.this.lambda$queryDeviceStatus$0(device, zoneCount, dialog, (ResponseMsg) obj);
            }
        }, 4);
        MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.control.provider.SuperPanelItemProvider$$ExternalSyntheticLambda1
            @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                SuperPanelItemProvider.this.lambda$queryDeviceStatus$1(device, zoneCount, dialog, responseMsg);
            }
        });
        ((ObservableSubscribeProxy) checkSingleDeviceStatus(device).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.provider.SuperPanelItemProvider.3
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
                deviceByDeviceId.getDeviceState().setOnlineState(2);
                Injection.repo().device().saveDevice(deviceByDeviceId);
                dialog.notifyDialog();
                if (SuperPanelItemProvider.this.onDataChangeListener != null) {
                    SuperPanelItemProvider.this.onDataChangeListener.onDataOfflineChange(integer.intValue());
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