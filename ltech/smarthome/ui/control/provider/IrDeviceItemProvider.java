package com.ltech.smarthome.ui.control.provider;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hzy.tvmao.KKACZipManagerV2;
import com.hzy.tvmao.KKNonACManager;
import com.hzy.tvmao.KookongSDK;
import com.hzy.tvmao.ir.ac.ACStateV2;
import com.kookong.app.data.IrData;
import com.ltech.nfc.utils.DataUtil;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseViewModel;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.device_param.BaseIrParam;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.model.device_param.DiyIrParam;
import com.ltech.smarthome.model.device_param.DryContactBleParam;
import com.ltech.smarthome.model.device_param.IrParam;
import com.ltech.smarthome.model.device_param.MotorParam;
import com.ltech.smarthome.model.device_param.TrigExtParam;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.model.repo.ProductRepository;
import com.ltech.smarthome.net.SmartErrorComsumer;
import com.ltech.smarthome.ui.control.FtRoomVM;
import com.ltech.smarthome.ui.control.provider.BaseDeviceProvider;
import com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider;
import com.ltech.smarthome.ui.device.curtain_motor.CurtainRepository;
import com.ltech.smarthome.ui.device.ir.ActSelectBrand;
import com.ltech.smarthome.ui.device.ir.Device433Repository;
import com.ltech.smarthome.ui.device.ir.IrKeyItem;
import com.ltech.smarthome.ui.device.ir.IrKeyRepository;
import com.ltech.smarthome.ui.device.ir.MotorKeyItem;
import com.ltech.smarthome.ui.device.trig.TrigRepository;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NavHelper;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.RxUtils;
import com.ltech.smarthome.utils.SharedPreferenceUtil;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.AcQuickDialog;
import com.ltech.smarthome.view.dialog.IrQuickDialog;
import com.smart.message.MessageManager;
import com.smart.message.ResponseMsg;
import com.smart.message.utils.StringUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class IrDeviceItemProvider extends BaseDeviceProvider<Device> {
    public static final int REPORT_STATE = 1;
    private Device acDevice;
    private long deviceId;
    private ReportHandler handler;
    private KKNonACManager kkNonACManager;
    private long lastReportTime;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_ir;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 3;
    }

    public IrDeviceItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    public IrDeviceItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
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
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) IrDeviceItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) IrDeviceItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) IrDeviceItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) IrDeviceItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) IrDeviceItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) IrDeviceItemProvider.this.viewModel).selectRoleList.getValue());
                }
                String productId = data.getProductId();
                productId.hashCode();
                switch (productId) {
                    case "122110809100701":
                    case "121051709233801":
                    case "121042516403901":
                        IrDeviceItemProvider irDeviceItemProvider = IrDeviceItemProvider.this;
                        irDeviceItemProvider.nav(data, irDeviceItemProvider.viewModel);
                        break;
                    default:
                        IrDeviceItemProvider irDeviceItemProvider2 = IrDeviceItemProvider.this;
                        irDeviceItemProvider2.nav(data, irDeviceItemProvider2.viewModel);
                        break;
                }
            }
        });
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        String productId = data.getProductId();
        productId.hashCode();
        switch (productId) {
            case "IR_100":
                showDiyKeyDialog(data, this.viewModel);
                break;
            case "122110809100701":
            case "121042516403901":
                showBleMotorDialog(data, this.viewModel);
                break;
            case "IR_5":
                showAcDialog(data, this.viewModel);
                break;
            case "IR_98":
            case "IR_99":
                showMotorDialog(data, this.viewModel);
                break;
            case "121051709233801":
                showDryCurtainDialog(data, this.viewModel);
                break;
            default:
                showIrDialog(data, this.viewModel);
                break;
        }
    }

    private void showBleMotorDialog(final Device device, final BaseViewModel viewModel) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_UP));
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_STOP));
        arrayList.add(CurtainRepository.getMotorKeyItem(CurtainRepository.BLE_MOTOR_KEY_NAME_DOWN));
        IrQuickDialog.motor(true).setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<MotorKeyItem>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.2
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(MotorKeyItem item) {
                CmdAssistant.getDeviceAssistant(device, new int[0]).controlCurtain(ActivityUtils.getTopActivity(), Integer.parseInt(item.getKey()));
            }

            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onMoreAction() {
                IrDeviceItemProvider.this.nav(device, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        selectStatus(device);
    }

    private void showDryCurtainDialog(final Device device, final BaseViewModel viewModel) {
        ArrayList arrayList = new ArrayList();
        if (((DryContactBleParam) device.getParam(DryContactBleParam.class)).getSubType() == 0) {
            if (!TextUtils.isEmpty(device.getExtParam())) {
                TrigExtParam trigExtParam = new TrigExtParam();
                trigExtParam.fillMapWithString(device.getExtParam());
                if (trigExtParam.getCurtainType() == 1) {
                    arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_open_2, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
                    arrayList.add(new TrigRepository.TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 2));
                    arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_close_2, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 4));
                }
            }
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_open_3, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 2));
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 4));
        } else {
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_open_3, ActivityUtils.getTopActivity().getString(R.string.open_curtain), 1));
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.cgcur_icon_stop, ActivityUtils.getTopActivity().getString(R.string.preview_stop), 3));
            arrayList.add(new TrigRepository.TrigItem(R.mipmap.icon_close_4, ActivityUtils.getTopActivity().getString(R.string.close_curtain), 2));
        }
        IrQuickDialog.dryCurtain(true).setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<TrigRepository.TrigItem>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.3
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(TrigRepository.TrigItem item) {
                CmdAssistant.getDeviceAssistant(device, new int[0]).setOpenCloseVar(ActivityUtils.getTopActivity(), item.spanCount);
            }

            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onMoreAction() {
                IrDeviceItemProvider.this.nav(device, viewModel);
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
        final KKACZipManagerV2 kKACZipManagerV2 = new KKACZipManagerV2();
        kKACZipManagerV2.initIRData(irData);
        if (TextUtils.isEmpty(device.getDeviceState().getAcState())) {
            kKACZipManagerV2.setACStateV2FromString("");
        } else {
            kKACZipManagerV2.setACStateV2FromString(device.getDeviceState().getAcState());
        }
        this.acDevice = device;
        this.handler = new ReportHandler();
        AcQuickDialog.asDefault().setTitle(device.getDeviceName()).initManager(kKACZipManagerV2).setDevice(device).setDialogCallback(new AcQuickDialog.OnDialogCallback() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.4
            @Override // com.ltech.smarthome.view.dialog.AcQuickDialog.OnDialogCallback
            public void onCmdSend(byte[] cmd) {
                viewModel.showLoadingDialog();
                CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrComboControl(ActivityUtils.getTopActivity(), kKACZipManagerV2.getAcParams(), cmd, new AnonymousClass1(), new boolean[0]);
            }

            /* renamed from: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider$4$1, reason: invalid class name */
            class AnonymousClass1 implements IAction<Boolean> {
                AnonymousClass1() {
                }

                @Override // com.ltech.smarthome.base.IAction
                public void act(final Boolean aBoolean) {
                    final BaseViewModel baseViewModel = viewModel;
                    final KKACZipManagerV2 kKACZipManagerV2 = kKACZipManagerV2;
                    ThreadUtils.runOnUiThreadDelayed(new Runnable() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider$4$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            IrDeviceItemProvider.AnonymousClass4.AnonymousClass1.this.lambda$act$0(baseViewModel, aBoolean, kKACZipManagerV2);
                        }
                    }, 200L);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$act$0(BaseViewModel baseViewModel, Boolean bool, KKACZipManagerV2 kKACZipManagerV2) {
                    baseViewModel.dismissLoadingDialog();
                    if (bool.booleanValue()) {
                        if (IrDeviceItemProvider.this.getUnicastAddress() != 0) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = IrDeviceItemProvider.this.getReportString(kKACZipManagerV2);
                            if (System.currentTimeMillis() - IrDeviceItemProvider.this.lastReportTime > 3000) {
                                IrDeviceItemProvider.this.handler.sendMessage(message);
                                return;
                            }
                            if (IrDeviceItemProvider.this.handler.hasMessages(1)) {
                                IrDeviceItemProvider.this.handler.removeMessages(1);
                            }
                            IrDeviceItemProvider.this.handler.sendMessageDelayed(message, Math.max(System.currentTimeMillis() - IrDeviceItemProvider.this.lastReportTime, 1000L));
                            return;
                        }
                        return;
                    }
                    baseViewModel.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                }
            }

            @Override // com.ltech.smarthome.view.dialog.AcQuickDialog.OnDialogCallback
            public void onMoreAction(String stateString) {
                dismiss(stateString);
                IrDeviceItemProvider.this.nav(device, viewModel);
            }

            @Override // com.ltech.smarthome.view.dialog.AcQuickDialog.OnDialogCallback
            public void dismiss(String stateString) {
                device.getDeviceState().setAcState(stateString);
                Injection.repo().device().saveDevice(device);
                MessageManager.getInstance().setReceiveDataCallBack(null);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrParam(ActivityUtils.getTopActivity(), kKACZipManagerV2.getAcParams());
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ReportHandler extends Handler {
        private ReportHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                IrDeviceItemProvider.this.lastReportTime = System.currentTimeMillis();
                final String str = (String) msg.obj;
                ((ObservableSubscribeProxy) Injection.net().updateReportInstruct(IrDeviceItemProvider.this.acDevice.getDeviceId(), str).compose(RxUtils.io_main()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) ActivityUtils.getTopActivity(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Consumer() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider$ReportHandler$$ExternalSyntheticLambda0
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        IrDeviceItemProvider.ReportHandler.this.lambda$handleMessage$0(str, obj);
                    }
                }, new SmartErrorComsumer());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(String str, Object obj) throws Exception {
            IrDeviceItemProvider.this.acDevice.setReportinstruct(str);
        }
    }

    public int getUnicastAddress() {
        BaseIrParam baseIrParam;
        Device device = this.acDevice;
        if (device == null || device.getParam() == null || (baseIrParam = (BaseIrParam) this.acDevice.getParam(BaseIrParam.class)) == null || baseIrParam.getUnicastAddress() == 0) {
            return 0;
        }
        return baseIrParam.getUnicastAddress();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getReportString(KKACZipManagerV2 kkacZipManagerV2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(193);
        arrayList.add(0);
        arrayList.add(1);
        int unicastAddress = getUnicastAddress();
        arrayList.add(Integer.valueOf((unicastAddress >> 8) & 255));
        arrayList.add(Integer.valueOf(unicastAddress & 255));
        arrayList.add(205);
        arrayList.add(1);
        ACStateV2 aCStateV2 = (ACStateV2) GsonUtils.fromJson(kkacZipManagerV2.getACStateV2InString(), ACStateV2.class);
        arrayList.add(Integer.valueOf(aCStateV2.getCurPowerState()));
        arrayList.add(Integer.valueOf(aCStateV2.getCurTemp()));
        arrayList.add(Integer.valueOf(aCStateV2.getCurModelType()));
        arrayList.add(Integer.valueOf(aCStateV2.getCurWindSpeed()));
        arrayList.add(Integer.valueOf(aCStateV2.getCurUDDirect()));
        return DataUtil.formatHexString(arrayList);
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
        IrQuickDialog.ir().setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<IrKeyItem>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.5
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(IrKeyItem item) {
                if (item.getFid() == -2) {
                    viewModel.showLoadingDialog();
                    CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrComboControl(ActivityUtils.getTopActivity(), IrDeviceItemProvider.this.getKKNonACManager(device).getParams(), SharedPreferenceUtil.queryByteArrayValue(Constants.TV_POWER_KEY + device.getDeviceId()), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.5.1
                        @Override // com.ltech.smarthome.base.IAction
                        public void act(Boolean aBoolean) {
                            viewModel.dismissLoadingDialog();
                            if (aBoolean.booleanValue()) {
                                return;
                            }
                            viewModel.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                        }
                    }, new boolean[0]);
                    return;
                }
                viewModel.showLoadingDialog();
                CmdAssistant.getGatewayAssistant(device, new int[0]).sendIrComboControl(ActivityUtils.getTopActivity(), IrDeviceItemProvider.this.getKKNonACManager(device).getParams(), IrDeviceItemProvider.this.getKKNonACManager(device).getKeyIr(item.getFid()), new IAction<Boolean>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.5.2
                    @Override // com.ltech.smarthome.base.IAction
                    public void act(Boolean aBoolean) {
                        viewModel.dismissLoadingDialog();
                        if (aBoolean.booleanValue()) {
                            return;
                        }
                        viewModel.showErrorTipDialog(ActivityUtils.getTopActivity().getString(R.string.app_str_control_timeout));
                    }
                }, new boolean[0]);
            }

            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onMoreAction() {
                IrDeviceItemProvider.this.nav(device, viewModel);
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
        IrQuickDialog.motor(false).setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<MotorKeyItem>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.6
            @Override // com.ltech.smarthome.view.dialog.IrQuickDialog.OnDialogCallback
            public void onItemClick(MotorKeyItem item) {
                viewModel.showLoadingDialog("");
                CmdAssistant.getGatewayAssistant(device, new int[0]).sendMotorControl(ActivityUtils.getTopActivity(), motorParam.getIrDatas().getCodeByKey(item.getKey()), new IAction<ResponseMsg>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.6.1
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
                IrDeviceItemProvider.this.nav(device, viewModel);
            }
        }).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
    }

    private void showDiyKeyDialog(final Device device, final BaseViewModel viewModel) {
        DiyIrParam diyIrParam = (DiyIrParam) device.getParam(DiyIrParam.class);
        ArrayList arrayList = new ArrayList();
        if (diyIrParam != null) {
            Iterator<DiyIrParam.DiyIrKey> it = diyIrParam.getDiyIrKeyList().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        }
        if (arrayList.isEmpty()) {
            nav(device, viewModel);
        } else {
            IrQuickDialog.diy().setTitle(device.getDeviceName()).setList(arrayList).setDialogCallback(new IrQuickDialog.OnDialogCallback<DiyIrParam.DiyIrKey>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.7
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
                    IrDeviceItemProvider.this.nav(device, viewModel);
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
        MessageManager.getInstance().setQuickDialogStatusCallBack(new MessageManager.QuickDialogStatusCallBack() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider$$ExternalSyntheticLambda0
            @Override // com.smart.message.MessageManager.QuickDialogStatusCallBack
            public final void update(int i, boolean z) {
                IrDeviceItemProvider.lambda$selectStatus$0(Device.this, i, z);
            }
        });
        ((ObservableSubscribeProxy) checkSingleDeviceStatus(device).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.provider.IrDeviceItemProvider.8
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
                    if (IrDeviceItemProvider.this.onDataChangeListener != null) {
                        IrDeviceItemProvider.this.onDataChangeListener.onDataOfflineChange(integer.intValue());
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