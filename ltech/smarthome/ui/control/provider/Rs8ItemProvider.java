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
import com.ltech.smarthome.model.device_param.Rs8ExtParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.response.rs8.Rs8CodeLibResponse;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.device.ir.MotorKeyItem;
import com.ltech.smarthome.ui.device.rs8.ActRs8Curtain;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LanguageUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.IrQuickDialog;
import com.smart.message.MessageManager;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class Rs8ItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_ir;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 31;
    }

    public Rs8ItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    public Rs8ItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
        super(activity, recyclerView, viewModel);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        helper.setImageResource(R.id.appCompatImageView9, ProductRepository.getProductIcon(data));
        boolean z = true;
        if (!ProductRepository.isBLeDevice(data.getProductId()) ? data.isOnline() : data.getDeviceState().isOnline()) {
            z = false;
        }
        helper.setGone(R.id.appCompatTextView16, z);
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.Rs8ItemProvider.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) Rs8ItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) Rs8ItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) Rs8ItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) Rs8ItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) Rs8ItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) Rs8ItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                Rs8ItemProvider rs8ItemProvider = Rs8ItemProvider.this;
                rs8ItemProvider.nav(data, rs8ItemProvider.viewModel);
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        showBleMotorDialog(data, this.viewModel);
    }

    private void showBleMotorDialog(final Device device, final BaseViewModel viewModel) {
        Rs8CodeLibResponse.CodeLib codeLib = (Rs8CodeLibResponse.CodeLib) device.getExtParam(Rs8CodeLibResponse.CodeLib.class);
        if (codeLib != null) {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            for (Rs8CodeLibResponse.CodeLib.Action action : codeLib.getActionlist()) {
                arrayList.add(new MotorKeyItem(getImg(device, action.getIcon()), LanguageUtils.isChinese(ActivityUtils.getTopActivity()) ? action.getCname() : action.getEname(), action.getInstruct(), action.getReplyinstruct()));
                i++;
                if (i >= 4) {
                    break;
                }
            }
            if (!arrayList.isEmpty()) {
                IrQuickDialog.motor(true).setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<MotorKeyItem>() { // from class: com.ltech.smarthome.ui.control.provider.Rs8ItemProvider.2
                    @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
                    public void onItemClick(MotorKeyItem item) {
                        viewModel.showLoadingDialog();
                        CmdAssistant.getDeviceAssistant(device, new int[0]).runRs485DataWithReply(Rs8ItemProvider.this.mContext, item.getKey(), item.getExtraData(), 1, new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.control.provider.Rs8ItemProvider.2.1
                            @Override // com.ltech.smarthome.base.IAction
                            public void act(Boolean aBoolean) {
                                if (aBoolean.booleanValue()) {
                                    viewModel.showSuccessTipDialog(Rs8ItemProvider.this.mContext.getString(R.string.send_to_device_success));
                                } else {
                                    viewModel.showErrorTipDialog(Rs8ItemProvider.this.mContext.getString(R.string.send_to_device_fail));
                                }
                            }
                        });
                    }

                    @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
                    public void onMoreAction() {
                        Rs8ItemProvider.this.nav(device, viewModel);
                    }
                }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
                selectStatus(device);
                return;
            }
        }
        nav(device, viewModel);
    }

    private int getImg(Device device, String img) {
        Rs8ExtParam rs8ExtParam = (Rs8ExtParam) device.getExtParam(Rs8ExtParam.class);
        if (rs8ExtParam == null || rs8ExtParam.getIcon() == null) {
            return R.mipmap.cgcur_icon_on;
        }
        img.hashCode();
        switch (img) {
        }
        return R.mipmap.cgcur_icon_on;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nav(Device device, BaseViewModel viewModel) {
        viewModel.navigation(NavUtils.destination(ActRs8Curtain.class).withLong(Constants.PLACE_ID, device.getPlaceId()).withLong(Constants.CONTROL_ID, device.getId()));
    }

    private void selectStatus(final Device device) {
        MessageManager.getInstance().setQuickDialogStatusCallBack(new MessageManager.QuickDialogStatusCallBack() { // from class: com.ltech.smarthome.ui.control.provider.Rs8ItemProvider$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.QuickDialogStatusCallBack
            public final void update(int i, boolean z) {
                Rs8ItemProvider.lambda$selectStatus$0(Device.this, i, z);
            }
        });
        ((ObservableSubscribeProxy) checkSingleDeviceStatus(device).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.provider.Rs8ItemProvider.3
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
                    if (Rs8ItemProvider.this.onDataChangeListener != null) {
                        Rs8ItemProvider.this.onDataChangeListener.onDataOfflineChange(integer.intValue());
                    }
                }
            }
        });
    }

    static /* synthetic */ void lambda$selectStatus$0(Device device, int i, boolean z) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId());
        if (deviceByDeviceId == null || i != ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getUnicastAddress()) {
            return;
        }
        deviceByDeviceId.getDeviceState().setOnlineState(1);
        deviceByDeviceId.setOnlineFlag(1);
        Injection.repo().device().saveDevice(deviceByDeviceId);
    }
}