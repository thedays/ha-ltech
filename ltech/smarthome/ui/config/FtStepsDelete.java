package com.ltech.smarthome.ui.config;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.blemesh.IRemoveNodeCallback;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.Step;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.DelFailDialog;
import com.smart.dialog.interfaces.OnDialogButtonClickListener;
import com.smart.dialog.util.BaseDialog;
import com.smart.dialog.v3.MessageDialog;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class FtStepsDelete extends FtStepsIntroduction {
    private Device mDevice;

    public static FtStepsDelete create(Step step, int curStep, int totalSteps) {
        FtStepsDelete ftStepsDelete = new FtStepsDelete();
        Bundle bundle = new Bundle();
        bundle.putString("data", GsonUtils.toJson(step));
        bundle.putInt("curStep", curStep);
        bundle.putInt("totalSteps", totalSteps);
        ftStepsDelete.setArguments(bundle);
        return ftStepsDelete;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        if (getActivity() != null) {
            this.mDevice = Injection.repo().device().getDeviceById(getActivity().getIntent().getLongExtra(Constants.CONTROL_ID, -1L));
        }
    }

    @Override // com.ltech.smarthome.ui.config.FtStepsIntroduction
    protected String getLastBtnName() {
        return getString(R.string.delete);
    }

    @Override // com.ltech.smarthome.ui.config.FtStepsIntroduction
    public void lastBtnClick() {
        if (this.mDevice != null) {
            deleteDevice();
        }
    }

    public void deleteDevice() {
        showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
        Injection.mesh().resetNodeByCmd(this.mDevice, new IAction() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtStepsDelete.this.lambda$deleteDevice$0((Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDevice$0(Boolean bool) {
        if (bool.booleanValue()) {
            dismissLoadingDialog();
            deleteDeviceFromNet();
        } else {
            dismissLoadingDialog();
            showForceDeleteTipDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteDeviceFromNet() {
        Observable<Object> deleteDevice;
        LHomeLog.i(getClass(), "deleteDeviceFromNet() enter");
        if (isDeleteByMeshCmd()) {
            Device device = this.mDevice;
            if (device == null) {
                dismissLoadingDialog();
                return;
            }
            BluetoothDevice connectedDevice = Injection.mesh().getConnectedDevice();
            if (connectedDevice != null && connectedDevice.getAddress().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").equals(this.mDevice.getWifiMac())) {
                Injection.mesh().disconnect();
            }
        }
        final Device device2 = this.mDevice;
        if (device2 == null) {
            dismissLoadingDialog();
            return;
        }
        BleParam bleParam = (BleParam) device2.getParam(BleParam.class);
        if (bleParam == null || bleParam.getGroupId() <= 0) {
            LHomeLog.i(getClass(), "no bleParam, delete device");
            deleteDevice = Injection.net().deleteDevice(device2.getDeviceId());
        } else if (bleParam.getPublicationAddress() == 0) {
            LHomeLog.i(getClass(), "no bleParam, delete device");
            deleteDevice = Injection.net().deleteDevice(device2.getDeviceId());
        } else {
            LHomeLog.i(getClass(), "bleParam != null && bleParam.getGroupId() > 0 delete device");
            deleteDevice = Injection.net().deleteDevice(device2.getDeviceId(), bleParam.getGroupId());
        }
        if (deleteDevice != null) {
            ((ObservableSubscribeProxy) deleteDevice.delaySubscription(500L, TimeUnit.MILLISECONDS).doOnSubscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtStepsDelete.this.lambda$deleteDeviceFromNet$1((Disposable) obj);
                }
            }).compose(RxUtils.io_main()).doFinally(new Action() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() {
                    FtStepsDelete.this.dismissLoadingDialog();
                }
            }).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    FtStepsDelete.this.lambda$deleteDeviceFromNet$3(device2, obj);
                }
            }, new SmartErrorComsumer() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete.1
                @Override // com.ltech.smarthome.net.SmartErrorComsumer
                protected void action(Throwable throwable) {
                    SmartToast.showShort(this.errorMessage);
                    FtStepsDelete.this.dismissLoadingDialog();
                }
            });
        } else {
            dismissLoadingDialog();
        }
        unSubscribePublicationAddress(device2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDeviceFromNet$1(Disposable disposable) throws Exception {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete.2
                @Override // java.lang.Runnable
                public void run() {
                    FtStepsDelete.this.showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDeviceFromNet$3(Device device, Object obj) throws Exception {
        Injection.repo().device().removeDeviceFromDb(device.getId());
        LHomeLog.i(getClass(), "delete device--->" + device);
        if (getActivity() != null) {
            getActivity().setResult(5001);
            getActivity().finish();
        }
    }

    private void unSubscribePublicationAddress(Device device) {
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "122041818260301":
            case "122041818283501":
            case "120033108255901":
            case "120033108263401":
            case "120033108265701":
            case "120033108272201":
            case "122041818304701":
            case "122110809100701":
            case "221042516351701":
            case "123072510445601":
            case "120042314364601":
            case "120042314375001":
            case "120042314380701":
            case "120042314382401":
            case "120042314384101":
            case "02":
            case "221030816330401":
            case "121051709233801":
            case "121042516340801":
            case "121042516345401":
            case "121042516403901":
                CmdAssistant.getSettingCmdAssistant(null, new int[0]).unSubscribePublishAddress(ActivityUtils.getTopActivity(), ((BleParam) device.getParam(BleParam.class)).getPublicationAddress(), ProductRepository.getAgreementIdByPid(device.getProductId()), new int[0]);
                break;
        }
    }

    public void forceDelDevice() {
        if (isDeleteByMeshCmd()) {
            showLoadingDialog(ActivityUtils.getTopActivity().getString(R.string.removing));
            Injection.mesh().removeNode(((BleParam) this.mDevice.getParam(BleParam.class)).getUnicastAddress(), new IRemoveNodeCallback() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete.3
                @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
                public void removeSuccess() {
                    FtStepsDelete.this.deleteDeviceFromNet();
                }

                @Override // com.ltech.smarthome.blemesh.IRemoveNodeCallback
                public void removeFail() {
                    FtStepsDelete.this.dismissLoadingDialog();
                    SmartToast.showShort(R.string.remove_fail);
                }
            });
        }
    }

    public boolean isDeleteByMeshCmd() {
        return ProductRepository.isBLeDevice(this.mDevice.getProductId());
    }

    protected void showForceDeleteTipDialog() {
        DelFailDialog.asDefault().setTitle(getString(R.string.del_ble_device_fail)).setContent(getString(R.string.del_ble_device_reason)).setContent2(getString(R.string.force_del_tip)).setConfirmString(getString(R.string.force_del)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                FtStepsDelete.this.lambda$showForceDeleteTipDialog$4((Void) obj);
            }
        }).setCancelString(getString(R.string.cancel)).showDialog(getChildFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showForceDeleteTipDialog$4(Void r1) {
        showForceDeleteDialog();
    }

    protected void showForceDeleteDialog() {
        if (getActivity() == null) {
            return;
        }
        MessageDialog.show((AppCompatActivity) getActivity(), getString(R.string.tips), getString(R.string.tip_force_del_device)).setOkButton(getString(R.string.confirm), new OnDialogButtonClickListener() { // from class: com.ltech.smarthome.ui.config.FtStepsDelete$$ExternalSyntheticLambda3
            @Override // com.smart.dialog.interfaces.OnDialogButtonClickListener
            public final boolean onClick(BaseDialog baseDialog, View view) {
                boolean lambda$showForceDeleteDialog$5;
                lambda$showForceDeleteDialog$5 = FtStepsDelete.this.lambda$showForceDeleteDialog$5(baseDialog, view);
                return lambda$showForceDeleteDialog$5;
            }
        }).setCancelButton(getString(R.string.cancel));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$showForceDeleteDialog$5(BaseDialog baseDialog, View view) {
        forceDelDevice();
        return false;
    }
}