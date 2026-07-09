package com.ltech.smarthome.ui.control.binding;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.hzy.tvmao.KKACZipManagerV2;
import com.hzy.tvmao.KKNonACManager;
import com.hzy.tvmao.KookongSDK;
import com.kookong.app.data.IrData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DiyIrParam;
import com.ltech.smarthome.model.device_param.IrParam;
import com.ltech.smarthome.model.device_param.MotorParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainRepository;
import com.ltech.smarthome.ui.device.ir.ActSelectBrand;
import com.ltech.smarthome.ui.device.ir.Device433Repository;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.ui.device.ir.IrKeyRepository;
import com.ltech.smarthome.ui.device.ir.MotorKeyItem;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.QueryDeviceStateRunnable;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.AcQuickDialog;
import com.ltech.smarthome.view.dialog.IrQuickDialog;
import com.ltech.smarthome.view.dialog.NormalDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import java.util.ArrayList;
import java.util.Iterator;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/* loaded from: classes4.dex */
public class IrDeviceItemBinding extends BaseDeviceItemBinding {
    private long deviceId;
    private KKNonACManager kkNonACManager;
    private NormalDialog mChoiceDialog;

    @Override // com.ltech.smarthome.ui.control.binding.IBindItem
    public void bindItem(final BaseViewModel viewModel, ItemBinding itemBinding, int position, final Device item) {
        itemBinding.set(23, ProductRepository.isBLeDevice(item.getProductId()) ? R.layout.item_device_ble : R.layout.item_device_ir).bindExtra(37, Integer.valueOf(ProductRepository.getProductIcon(item))).bindExtra(10, new BindingCommand(new BindingConsumer() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                IrDeviceItemBinding.this.lambda$bindItem$0(item, viewModel, (View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0061, code lost:
    
        if (r11.equals(com.ltech.smarthome.model.product.ProductId.ID_IR_AC) == false) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00b4, code lost:
    
        if (r11.equals(com.ltech.smarthome.model.product.ProductId.ID_BLE_CURTAIN) == false) goto L62;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$bindItem$0(com.ltech.smarthome.model.bean.Device r9, com.ltech.smarthome.base.BaseViewModel r10, android.view.View r11) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.lambda$bindItem$0(com.ltech.smarthome.model.bean.Device, com.ltech.smarthome.base.BaseViewModel, android.view.View):void");
    }

    private void showBleMotorDialog(final Device device, final BaseViewModel viewModel) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP));
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_STOP));
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_DOWN));
        IrQuickDialog.motor(true).setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<MotorKeyItem>() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.1
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(MotorKeyItem item) {
                CmdAssistant.getDeviceAssistant(device, new int[0]).controlCurtain(ActivityUtils.getTopActivity(), Integer.parseInt(item.getKey()));
            }

            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onMoreAction() {
                IrDeviceItemBinding.this.nav(device, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        selectStatus(device);
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
        if (!TextUtils.isEmpty(device.getWifiMac())) {
            KookongSDK.init(Utils.getApp(), ActSelectBrand.INIT_KEY, device.getWifiMac().replaceAll(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR, "").toUpperCase());
        }
        IrData irData = ((IrParam) device.getParam(IrParam.class)).getIrDatas().get(0);
        KKACZipManagerV2 kKACZipManagerV2 = new KKACZipManagerV2();
        kKACZipManagerV2.initIRData(irData);
        if (TextUtils.isEmpty(device.getDeviceState().getAcState())) {
            kKACZipManagerV2.setACStateV2FromString("");
        } else {
            kKACZipManagerV2.setACStateV2FromString(device.getDeviceState().getAcState());
        }
        AcQuickDialog.asDefault().setTitle(device.getDeviceName()).initManager(kKACZipManagerV2).setDialogCallback(new AcQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.2
            @Override // com.ltech.smarthome.view.dialog.AcQuickDialog.OnDialogCallback
            public void onCmdSend(byte[] cmd) {
                CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrControl(ActivityUtils.getTopActivity(), cmd, new boolean[0]);
            }

            @Override // com.ltech.smarthome.view.dialog.AcQuickDialog.OnDialogCallback
            public void onMoreAction(String stateString) {
                dismiss(stateString);
                IrDeviceItemBinding.this.nav(device, viewModel);
            }

            @Override // com.ltech.smarthome.view.dialog.AcQuickDialog.OnDialogCallback
            public void dismiss(String stateString) {
                device.getDeviceState().setAcState(stateString);
                Injection.repo().device().saveDevice(device);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    private void showIrDialog(final Device device, final BaseViewModel viewModel) {
        ArrayList<IrKeyItem> arrayList;
        arrayList = new ArrayList();
        String productId = device.getProductId();
        productId.hashCode();
        switch (productId) {
            case "IR_1":
                arrayList.add(IrKeyRepository.getIrKeyItem(1));
                IrKeyItem irKeyItem = IrKeyRepository.getIrKeyItem(-2);
                StringBuilder sb = new StringBuilder(Constants.TV_POWER_KEY);
                sb.append(device.getDeviceId());
                irKeyItem.setEnable(SharedPreferenceUtil.queryByteArrayValue(sb.toString()) != null);
                arrayList.add(irKeyItem);
                arrayList.add(IrKeyRepository.getIrKeyItem(43));
                arrayList.add(IrKeyRepository.getIrKeyItem(44));
                break;
            case "IR_2":
            case "IR_6":
                arrayList.add(IrKeyRepository.getIrKeyItem(1));
                arrayList.add(IrKeyRepository.getIrKeyItem(50));
                arrayList.add(IrKeyRepository.getIrKeyItem(51));
                arrayList.add(IrKeyRepository.getIrKeyItem(106));
                break;
            case "IR_3":
                arrayList.add(IrKeyRepository.getIrKeyItem(1));
                IrKeyItem irKeyItem2 = IrKeyRepository.getIrKeyItem(-2);
                StringBuilder sb2 = new StringBuilder(Constants.TV_POWER_KEY);
                sb2.append(device.getDeviceId());
                irKeyItem2.setEnable(SharedPreferenceUtil.queryByteArrayValue(sb2.toString()) != null);
                arrayList.add(irKeyItem2);
                arrayList.add(IrKeyRepository.getIrKeyItem(50));
                arrayList.add(IrKeyRepository.getIrKeyItem(51));
                break;
            case "IR_8":
                arrayList.add(IrKeyRepository.getIrKeyItem(1));
                arrayList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_FAN_SPEED));
                arrayList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_SWING_MODE));
                arrayList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_SWING));
                break;
            case "IR_11":
                arrayList.add(IrKeyRepository.getIrKeyItem(1));
                arrayList.add(IrKeyRepository.getIrKeyItem(IrKeyRepository.ID_FAN_SPEED));
                arrayList.add(IrKeyRepository.getIrKeyItem(23));
                arrayList.add(IrKeyRepository.getIrKeyItem(31));
                break;
            case "IR_12":
                arrayList.add(IrKeyRepository.getIrKeyItem(1));
                arrayList.add(IrKeyRepository.getIrKeyItem(3));
                arrayList.add(IrKeyRepository.getIrKeyItem(4));
                arrayList.add(IrKeyRepository.getIrKeyItem(42));
                break;
            default:
                return;
        }
        IrData irData = ((IrParam) device.getParam(IrParam.class)).getIrDatas().get(0);
        for (IrKeyItem irKeyItem3 : arrayList) {
            Iterator<IrData.IrKey> it = irData.keys.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().fid == irKeyItem3.getFid()) {
                    irKeyItem3.setEnable(true);
                }
            }
        }
        IrQuickDialog.ir().setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<IrKeyItem>() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.3
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(IrKeyItem item) {
                if (item.getFid() == -2) {
                    CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrControl(ActivityUtils.getTopActivity(), SharedPreferenceUtil.queryByteArrayValue(Constants.TV_POWER_KEY + device.getDeviceId()), new boolean[0]);
                    return;
                }
                CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrControl(ActivityUtils.getTopActivity(), IrDeviceItemBinding.this.getKKNonACManager(device).getKeyIr(item.getFid()), new boolean[0]);
            }

            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onMoreAction() {
                IrDeviceItemBinding.this.nav(device, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrParam(ActivityUtils.getTopActivity(), getKKNonACManager(device).getParams());
    }

    private void showMotorDialog(final Device device, final BaseViewModel viewModel) {
        ArrayList arrayList = new ArrayList();
        String productId = device.getProductId();
        productId.hashCode();
        if (productId.equals(ProductId.ID_IR_CURTAIN)) {
            arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_MOTOR_KEY_NAME_UP));
            arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_MOTOR_KEY_NAME_STOP));
            arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_MOTOR_KEY_NAME_DOWN));
        } else {
            if (!productId.equals(ProductId.ID_IR_HANGER)) {
                return;
            }
            arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_UP));
            arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_STOP));
            arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_DOWN));
            arrayList.add(Device433Repository.getMotorKeyItem(Device433Repository.IR_AIRER_KEY_NAME_LIGHTING));
        }
        final MotorParam motorParam = (MotorParam) device.getParam(MotorParam.class);
        IrQuickDialog.motor(false).setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<MotorKeyItem>() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.4
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(MotorKeyItem item) {
                viewModel.showLoadingDialog();
                CmdAssistant.getGatewayAssistant(device, new int[0]).sendMotorControl(ActivityUtils.getTopActivity(), motorParam.getIrDatas().getCodeByKey(item.getKey()), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.4.1
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(ResponseMsg responseMsg) {
                        viewModel.dismissLoadingDialog();
                        if (responseMsg == null) {
                            viewModel.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                        }
                    }
                }, new boolean[0]);
            }

            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onMoreAction() {
                IrDeviceItemBinding.this.nav(device, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    private void showDiyKeyDialog(final Device device, final BaseViewModel viewModel) {
        DiyIrParam diyIrParam = (DiyIrParam) device.getParam(DiyIrParam.class);
        ArrayList arrayList = new ArrayList();
        if (diyIrParam != null) {
            Iterator<DiyIrParam.DiyIrKey> it = diyIrParam.getDiyIrKeyList().iterator();
            int i = 0;
            while (it.hasNext()) {
                arrayList.add(it.next());
                i++;
                if (i >= 4) {
                    break;
                }
            }
        }
        if (arrayList.isEmpty()) {
            nav(device, viewModel);
        } else {
            IrQuickDialog.diy().setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<DiyIrParam.DiyIrKey>() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.5
                @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
                public void onItemClick(DiyIrParam.DiyIrKey item) {
                    if (TextUtils.isEmpty(item.getKeyData())) {
                        CmdAssistant.getGatewayAssistant(device, new int[0]).sendDiyControl(ActivityUtils.getTopActivity(), new byte[0], new boolean[0]);
                    } else {
                        CmdAssistant.getGatewayAssistant(device, new int[0]).sendDiyControl(ActivityUtils.getTopActivity(), StringUtils.hexStringToByte(item.getKeyData()), new boolean[0]);
                    }
                }

                @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
                public void onMoreAction() {
                    IrDeviceItemBinding.this.nav(device, viewModel);
                }
            }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public KKNonACManager getKKNonACManager(Device device) {
        if (device.getDeviceId() != this.deviceId || this.kkNonACManager == null) {
            this.deviceId = device.getDeviceId();
            this.kkNonACManager = new KKNonACManager(((IrParam) device.getParam(IrParam.class)).getIrDatas().get(0));
        }
        return this.kkNonACManager;
    }

    private void selectStatus(final Device device) {
        MessageManager.getInstance().setQuickDialogStatusCallBack(new MessageManager.QuickDialogStatusCallBack() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding$$ExternalSyntheticLambda1
            @Override // com.smart.message.MessageManager.QuickDialogStatusCallBack
            public final void update(int i, boolean z) {
                IrDeviceItemBinding.lambda$selectStatus$1(Device.this, i, z);
            }
        });
        CmdAssistant.selectStatusDevice(device, new QueryDeviceStateRunnable.UpdateDeviceState(this) { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.6
            @Override // com.ltech.smarthome.utils.QueryDeviceStateRunnable.UpdateDeviceState
            public void updateDevice(Device device2) {
                device2.getDeviceState().setOnlineState(2);
                Injection.repo().device().saveDevice(device2);
            }
        });
    }

    static /* synthetic */ void lambda$selectStatus$1(Device device, int i, boolean z) {
        Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(device.getDeviceId());
        if (deviceByDeviceId == null || i != ((BleParam) deviceByDeviceId.getParam(BleParam.class)).getUnicastAddress()) {
            return;
        }
        deviceByDeviceId.setOnlineFlag(1);
        deviceByDeviceId.getDeviceState().setOnlineState(1);
        Injection.repo().device().saveDevice(device);
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
            normalDialog.setYesOnclickListener(ActivityUtils.getTopActivity().getResources().getString(R.string.go_to_setting), new NormalDialog.onYesOnclickListener() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.7
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onYesOnclickListener
                public void onYesOnclick() {
                    ActivityUtils.getTopActivity().startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 666);
                    IrDeviceItemBinding.this.mChoiceDialog.dismiss();
                }
            });
            this.mChoiceDialog.setNoOnclickListener(ActivityUtils.getTopActivity().getResources().getString(R.string.no_open), new NormalDialog.onNoOnclickListener() { // from class: com.ltech.smarthome.ui.control.binding.IrDeviceItemBinding.8
                @Override // com.ltech.smarthome.view.dialog.NormalDialog.onNoOnclickListener
                public void onNoClick() {
                    IrDeviceItemBinding.this.mChoiceDialog.dismiss();
                }
            });
        }
        if (this.mChoiceDialog.isShowing()) {
            return;
        }
        this.mChoiceDialog.show();
    }
}