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
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.control.provider.KbsItemProvider;
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
public class KbsItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_mesh_gateway;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 29;
    }

    public KbsItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setGone(R.id.appCompatTextView16, (data.getDeviceState().isOnline() || data.isVirtual()) ? false : true).setText(R.id.appCompatTextView16, ActivityUtils.getTopActivity().getString(R.string.offline)).setBackgroundRes(R.id.appCompatTextView16, R.drawable.shape_light_gray_bt);
        if (isNormalMode()) {
            helper.setVisible(R.id.iv_device_more, true);
        }
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.KbsItemProvider.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) KbsItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) KbsItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) KbsItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) KbsItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) KbsItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) KbsItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                KbsItemProvider kbsItemProvider = KbsItemProvider.this;
                kbsItemProvider.nav(data, kbsItemProvider.viewModel);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Device item, BaseViewModel viewModel) {
        viewModel.navigation(NavUtils.destination(ActKbs.class).withLong(Constants.CONTROL_ID, item.getId()).withLong(Constants.PLACE_ID, item.getPlaceId()).withBoolean(Constants.GROUP_CONTROL, false));
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        showControlDialog(data);
    }

    private void showControlDialog(Device item) {
        List<Device> subDevice = Injection.repo().device().getSubDevice(item.getPlaceId(), item.getDeviceId());
        Collections.sort(subDevice, new Comparator<Device>(this) { // from class: com.ltech.smarthome.ui.control.provider.KbsItemProvider.2
            @Override // java.util.Comparator
            public int compare(Device o1, Device o2) {
                return o1.getWifiMac().compareTo(o2.getWifiMac());
            }
        });
        boolean[] zArr = new boolean[subDevice.size()];
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < subDevice.size(); i++) {
            Device device = subDevice.get(i);
            if (device.getDeviceState() != null) {
                zArr[i] = subDevice.get(i).getDeviceState().isOn();
            } else {
                zArr[i] = false;
            }
            arrayList.add(new RelateInfoItem(device.getDeviceName()));
        }
        SpQuickDialog asDefault = SpQuickDialog.asDefault();
        asDefault.setDeviceName(item.getDeviceName()).setOnline(item.getDeviceState().isOnline()).setPanelStatus(zArr).setRelateInfoList(arrayList).setCallback(new AnonymousClass3(item)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        selectStatus(item, asDefault);
    }

    /* renamed from: com.ltech.smarthome.ui.control.provider.KbsItemProvider$3, reason: invalid class name */
    class AnonymousClass3 implements SpQuickDialog.OnDialogCallback {
        final /* synthetic */ Device val$item;

        static /* synthetic */ void lambda$onItemClick$0(Boolean bool) {
        }

        AnonymousClass3(final Device val$item) {
            this.val$item = val$item;
        }

        @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
        public void onItemClick(int position, boolean state) {
            KbsItemProvider.this.updateDeviceStates(state, this.val$item, position);
            LightAssistant lightAssistant = KbsItemProvider.this.getLightAssistant(this.val$item);
            lightAssistant.setZoneNum(1 << position);
            lightAssistant.sendOnOff(ActivityUtils.getTopActivity(), state, new IAction() { // from class: com.ltech.smarthome.ui.control.provider.KbsItemProvider$3$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    KbsItemProvider.AnonymousClass3.lambda$onItemClick$0((Boolean) obj);
                }
            });
        }

        @Override // com.ltech.smarthome.view.dialog.SpQuickDialog.OnDialogCallback
        public void onMoreAction() {
            KbsItemProvider kbsItemProvider = KbsItemProvider.this;
            kbsItemProvider.nav(this.val$item, kbsItemProvider.viewModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceStates(boolean state, Device device, int position) {
        List<Device> subDevice = Injection.repo().device().getSubDevice(device.getPlaceId(), device.getDeviceId());
        if (subDevice.isEmpty()) {
            return;
        }
        for (int i = 0; i < subDevice.size(); i++) {
            Device device2 = subDevice.get(i);
            if (RelaySeparationHelper.getRelayNum(device2) - 1 == position) {
                device2.getDeviceState().setOn(state);
                Injection.repo().device().saveDevice(device2);
                return;
            }
        }
    }

    private void selectStatus(final Device device, final SpQuickDialog dialog) {
        MessageManager.getInstance().setPanelSwitchStatusCallBack(new MessageManager.SmartPanelSwitchStatusCallBack() { // from class: com.ltech.smarthome.ui.control.provider.KbsItemProvider$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.SmartPanelSwitchStatusCallBack
            public final void onDataReceive(ResponseMsg responseMsg) {
                KbsItemProvider.this.lambda$selectStatus$0(device, dialog, responseMsg);
            }
        });
        ((ObservableSubscribeProxy) checkSingleDeviceStatus(device).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.provider.KbsItemProvider.4
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
                    dialog.setOnline(false);
                    dialog.notifyDialog();
                    if (KbsItemProvider.this.onDataChangeListener != null) {
                        KbsItemProvider.this.onDataChangeListener.onDataOfflineChange(integer.intValue());
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectStatus$0(Device device, SpQuickDialog spQuickDialog, ResponseMsg responseMsg) {
        refreshPanelState(((IPanelParser) Injection.strategy().getParserStrategy(responseMsg.getAgreementId())).parserSwitchPanelState(responseMsg.getAgreementId(), ((BleParam) device.getParam(BleParam.class)).getUnicastAddress(), 2, responseMsg.getResData()), spQuickDialog, device);
    }

    public void refreshPanelState(SwitchPanelState panelState, SpQuickDialog dialog, Device device) {
        if (panelState != null) {
            dialog.setOnline(true);
            dialog.notifyDialog();
            device.getDeviceState().setOnlineState(1);
            for (int i = 0; i < panelState.getSwitchPanelZoneStateList().size(); i++) {
                dialog.selectArray[i] = panelState.getSwitchPanelZoneStateList().get(i).isSwitchOnOff();
                updateDeviceStates(dialog.selectArray[i], device, i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LightAssistant getLightAssistant(Object controlObject) {
        return CmdAssistant.getLightCmdAssistant(controlObject, new int[0]);
    }
}