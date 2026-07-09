package com.ltech.smarthome.ui.control.provider;

import android.app.Activity;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ThreadUtils;
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
import com.ltech.smarthome.ui.control.provider.LightItemProvider;
import com.ltech.smarthome.ui.device.dalipro.DaliProHelper;
import com.ltech.smarthome.ui.device.light.ActLight512;
import com.ltech.smarthome.ui.device.light.ActModuleSwitch;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.NavUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.utils.cmd_assistant.LightAssistant;
import com.ltech.smarthome.view.SmartSwitch;
import com.ltech.smarthome.view.dialog.LightQuickDialog;
import com.smart.message.MessageManager;
import com.smart.message.utils.LHomeLog;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/* loaded from: classes4.dex */
public class LightItemProvider extends BaseDeviceProvider<Device> {
    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int layout() {
        return R.layout.item_device_light;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int viewType() {
        return 2;
    }

    public LightItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel, BaseDeviceProvider.OnDataChangeListener onDataChangeListener) {
        super(activity, recyclerView, viewModel, onDataChangeListener);
    }

    public LightItemProvider(FragmentActivity activity, RecyclerView recyclerView, BaseViewModel viewModel) {
        super(activity, recyclerView, viewModel);
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider, com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(final BaseViewHolder helper, final Device data, int position) {
        super.convert(helper, (BaseViewHolder) data, position);
        if (isNormalMode()) {
            helper.setVisible(R.id.iv_device_more, (ProductRepository.getLightColorType((Object) data) == 7 || ProductRepository.getLightColorType((Object) data) == 31) ? false : true);
        }
        helper.setGone(R.id.appCompatTextView16, (data.getDeviceState().isOnline() || data.isVirtual()) ? false : true);
        helper.setGone(R.id.appCompatTextView18, ProductRepository.isDaliLightGroup(data));
        helper.setGone(R.id.appCompatTextView20, data.getDeviceState().isOnline() && data.getDeviceState().isOn() && data.getDeviceState().isRhythmPlay());
        SmartSwitch smartSwitch = (SmartSwitch) helper.getView(R.id.sb);
        smartSwitch.setChecked(data.getDeviceState().isOn());
        int lightColorType = ProductRepository.getLightColorType((Object) data);
        int i = 8;
        if (data.isVirtual() || ProductRepository.isE6Panel(data.getProductId()) || lightColorType == 31) {
            smartSwitch.setVisibility(8);
        } else {
            if (data.getDeviceState().isOnline() && isNormalMode()) {
                i = 0;
            }
            smartSwitch.setVisibility(i);
            smartSwitch.setOnUserCheckedChangeListener(new AnonymousClass1(data));
        }
        helper.getView(R.id.iv_device_more).setOnClickListener(new View.OnClickListener() { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (((FtRoomVM) LightItemProvider.this.viewModel).editRoleMode.getValue().booleanValue()) {
                    if (((FtRoomVM) LightItemProvider.this.viewModel).selectRoleList.getValue().contains(data)) {
                        ((FtRoomVM) LightItemProvider.this.viewModel).selectRoleList.getValue().remove(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_default);
                    } else {
                        ((FtRoomVM) LightItemProvider.this.viewModel).selectRoleList.getValue().add(data);
                        helper.setImageResource(R.id.iv_device_more, R.mipmap.ic_tick_sel);
                    }
                    ((FtRoomVM) LightItemProvider.this.viewModel).selectRoleList.setValue(((FtRoomVM) LightItemProvider.this.viewModel).selectRoleList.getValue());
                    return;
                }
                LightItemProvider lightItemProvider = LightItemProvider.this;
                lightItemProvider.nav(data, lightItemProvider.viewModel);
            }
        });
        if (data.getProductId().equals(ProductId.ID_BLE_LIGHT_CGD_PRO) && isNormalMode()) {
            helper.setGone(R.id.sb, false);
            helper.setVisible(R.id.iv_device_more, false);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.control.provider.LightItemProvider$1, reason: invalid class name */
    class AnonymousClass1 implements SmartSwitch.OnUserCheckedChangeListener {
        final /* synthetic */ Device val$data;

        AnonymousClass1(final Device val$data) {
            this.val$data = val$data;
        }

        @Override // com.ltech.smarthome.view.SmartSwitch.OnUserCheckedChangeListener
        public void onUserCheckedChanged(SmartSwitch view, boolean isChecked) {
            LightItemProvider.this.getLightAssistant(this.val$data).sendOnOff(ActivityUtils.getTopActivity(), isChecked, new IAction() { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    ((Boolean) obj).booleanValue();
                }
            });
        }
    }

    @Override // com.ltech.smarthome.ui.control.provider.BaseDeviceProvider
    public void onItemClick(BaseViewHolder helper, Device data, int position) {
        if (data.getProductId().equals(ProductId.ID_BLE_LIGHT_CGD_PRO)) {
            nav(data, this.viewModel);
        } else {
            showQuickDialog(data, this.viewModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void nav(com.ltech.smarthome.model.bean.Device r9, com.ltech.smarthome.base.BaseViewModel r10) {
        /*
            r8 = this;
            int r0 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r9)
            java.lang.String r1 = r9.getProductId()
            boolean r1 = com.ltech.smarthome.model.repo.ProductRepository.isE6Panel(r1)
            java.lang.String r2 = "light_type"
            java.lang.String r3 = "control_id"
            java.lang.String r4 = "place_id"
            if (r1 == 0) goto L40
            java.lang.Class<com.ltech.smarthome.ui.device.e6knob.ActE6Panel> r1 = com.ltech.smarthome.ui.device.e6knob.ActE6Panel.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            long r5 = r9.getPlaceId()
            com.ltech.smarthome.utils.NavUtils$Builder r1 = r1.withLong(r4, r5)
            long r4 = r9.getId()
            com.ltech.smarthome.utils.NavUtils$Builder r1 = r1.withLong(r3, r4)
            int r4 = com.ltech.smarthome.model.repo.ProductRepository.getLightColorType(r9)
            com.ltech.smarthome.utils.NavUtils$Builder r1 = r1.withInt(r2, r4)
            java.lang.String r4 = "product_id"
            java.lang.String r5 = r9.getProductId()
            com.ltech.smarthome.utils.NavUtils$Builder r1 = r1.withString(r4, r5)
            r10.navigation(r1)
            goto L61
        L40:
            r1 = 1
            if (r0 == r1) goto Lbd
            r1 = 2
            if (r0 == r1) goto Lb6
            r1 = 3
            if (r0 == r1) goto Laf
            r1 = 4
            if (r0 == r1) goto Laf
            r1 = 5
            if (r0 == r1) goto Laf
            r1 = 7
            if (r0 == r1) goto La8
            r1 = 17
            if (r0 == r1) goto L89
            r1 = 20
            if (r0 == r1) goto L82
            r1 = 10009(0x2719, float:1.4026E-41)
            if (r0 == r1) goto L7b
            switch(r0) {
                case 101: goto L63;
                case 102: goto L63;
                case 103: goto L63;
                case 104: goto L63;
                case 105: goto L63;
                default: goto L61;
            }
        L61:
            r1 = 0
            goto Lc3
        L63:
            java.lang.Class<com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroup> r1 = com.ltech.smarthome.ui.device.dalipro.ActDaliLightOrGroup.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            long r5 = r9.getPlaceId()
            com.ltech.smarthome.utils.NavUtils$Builder r4 = r1.withLong(r4, r5)
            java.lang.String r5 = "device_id"
            long r6 = r9.getMacdeviceid()
            r4.withLong(r5, r6)
            goto Lc3
        L7b:
            java.lang.Class<com.ltech.smarthome.ui.device.dalipro.ActCgdProLight> r1 = com.ltech.smarthome.ui.device.dalipro.ActCgdProLight.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            goto Lc3
        L82:
            java.lang.Class<com.ltech.smarthome.ui.device.light.ActColorCCLight> r1 = com.ltech.smarthome.ui.device.light.ActColorCCLight.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            goto Lc3
        L89:
            java.lang.Class<com.ltech.smarthome.model.device_param.SpiLightExtParam> r1 = com.ltech.smarthome.model.device_param.SpiLightExtParam.class
            java.lang.Object r1 = r9.getExtParam(r1)
            com.ltech.smarthome.model.device_param.SpiLightExtParam r1 = (com.ltech.smarthome.model.device_param.SpiLightExtParam) r1
            if (r1 == 0) goto La1
            int r1 = r1.getPixel()
            if (r1 != 0) goto L9a
            goto La1
        L9a:
            java.lang.Class<com.ltech.smarthome.ui.device.spicontroller.ActSpiController> r1 = com.ltech.smarthome.ui.device.spicontroller.ActSpiController.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            goto Lc3
        La1:
            java.lang.Class<com.ltech.smarthome.ui.device.spicontroller.ActSpiLightSetting> r1 = com.ltech.smarthome.ui.device.spicontroller.ActSpiLightSetting.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            goto Lc3
        La8:
            java.lang.Class<com.ltech.smarthome.ui.device.light.ActModuleSwitch> r1 = com.ltech.smarthome.ui.device.light.ActModuleSwitch.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            goto Lc3
        Laf:
            java.lang.Class<com.ltech.smarthome.ui.device.light.ActColorLight> r1 = com.ltech.smarthome.ui.device.light.ActColorLight.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            goto Lc3
        Lb6:
            java.lang.Class<com.ltech.smarthome.ui.device.light.ActCtLight> r1 = com.ltech.smarthome.ui.device.light.ActCtLight.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
            goto Lc3
        Lbd:
            java.lang.Class<com.ltech.smarthome.ui.device.light.ActDimLight> r1 = com.ltech.smarthome.ui.device.light.ActDimLight.class
            com.ltech.smarthome.utils.NavUtils$Builder r1 = com.ltech.smarthome.utils.NavUtils.destination(r1)
        Lc3:
            if (r1 == 0) goto Ldb
            long r4 = r9.getId()
            com.ltech.smarthome.utils.NavUtils$Builder r9 = r1.withLong(r3, r4)
            com.ltech.smarthome.utils.NavUtils$Builder r9 = r9.withInt(r2, r0)
            java.lang.String r0 = "group_control"
            r1 = 0
            com.ltech.smarthome.utils.NavUtils$Builder r9 = r9.withBoolean(r0, r1)
            r10.navigation(r9)
        Ldb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.ui.control.provider.LightItemProvider.nav(com.ltech.smarthome.model.bean.Device, com.ltech.smarthome.base.BaseViewModel):void");
    }

    private void showQuickDialog(Device device, BaseViewModel viewModel) {
        int lightColorType = ProductRepository.getLightColorType((Object) device);
        if (ProductRepository.isDaliLightGroup(device)) {
            lightColorType = DaliProHelper.convertDaliType(device);
        }
        if (lightColorType == 1) {
            LightQuickDialog dim = LightQuickDialog.dim();
            dim.setLightName(device.getDeviceName()).setOnline(device.getDeviceState().isOnline()).setSwitchOn(device.getDeviceState().isOn()).setRgbBrt(device.getDeviceState().getWyBrt()).setCallback(new AnonymousClass5(device, dim, viewModel)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(device, dim);
            return;
        }
        if (lightColorType == 2) {
            LHomeLog.e(getClass(), "LightItem.wyBrt=" + device.getDeviceState().getWyBrt());
            LightQuickDialog ct = LightQuickDialog.ct();
            ct.setLightName(device.getDeviceName()).setOnline(device.getDeviceState().isOnline()).setWy(device.getDeviceState().getWy()).setControlObject(device).setRgbBrt(device.getDeviceState().getWyBrt()).setSwitchOn(device.getDeviceState().isOn()).setCallback(new AnonymousClass3(device, ct, viewModel)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
            selectStatus(device, ct);
            return;
        }
        if (lightColorType != 3 && lightColorType != 4 && lightColorType != 5) {
            if (lightColorType == 7) {
                viewModel.navigation(NavUtils.destination(ActModuleSwitch.class).withLong(Constants.CONTROL_ID, device.getId()).withInt(Constants.LIGHT_TYPE, lightColorType).withBoolean(Constants.GROUP_CONTROL, false));
                return;
            } else if (lightColorType != 17 && lightColorType != 20) {
                if (lightColorType != 31) {
                    return;
                }
                viewModel.navigation(NavUtils.destination(ActLight512.class).withLong(Constants.CONTROL_ID, device.getId()).withInt(Constants.LIGHT_TYPE, lightColorType).withBoolean(Constants.GROUP_CONTROL, false));
                return;
            }
        }
        LightQuickDialog rgb = LightQuickDialog.rgb();
        rgb.setLightName(device.getDeviceName()).setOnline(device.getDeviceState().isOnline()).setSwitchOn(device.getDeviceState().isOn()).setRgbBrt(device.getDeviceState().getRgbBrt()).setCallback(new AnonymousClass4(device, rgb, viewModel)).showDialog((FragmentActivity) ActivityUtils.getTopActivity());
        selectStatus(device, rgb);
    }

    /* renamed from: com.ltech.smarthome.ui.control.provider.LightItemProvider$3, reason: invalid class name */
    class AnonymousClass3 implements LightQuickDialog.OnDialogCallback {
        final /* synthetic */ LightQuickDialog val$ctDialog;
        final /* synthetic */ Device val$device;
        final /* synthetic */ BaseViewModel val$viewModel;

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onColorChanged(float xProgress, int color, boolean finish) {
        }

        AnonymousClass3(final Device val$device, final LightQuickDialog val$ctDialog, final BaseViewModel val$viewModel) {
            this.val$device = val$device;
            this.val$ctDialog = val$ctDialog;
            this.val$viewModel = val$viewModel;
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onBrtChanged(int progress, boolean finish) {
            LHomeLog.e(getClass(), "onBrtChanged.progress=" + progress);
            LightItemProvider.this.getLightAssistant(this.val$device).sendCtBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
            LightItemProvider.this.getLightAssistant(this.val$device).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onSwitch(final boolean on) {
            LightAssistant lightAssistant = LightItemProvider.this.getLightAssistant(this.val$device);
            Activity topActivity = ActivityUtils.getTopActivity();
            final LightQuickDialog lightQuickDialog = this.val$ctDialog;
            lightAssistant.sendOnOff(topActivity, on, new IAction() { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider$3$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    LightItemProvider.AnonymousClass3.lambda$onSwitch$0(LightQuickDialog.this, on, (Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$onSwitch$0(LightQuickDialog lightQuickDialog, boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            lightQuickDialog.setSwitchOn(!z);
            lightQuickDialog.notifyDialog();
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onMoreAction() {
            LightItemProvider.this.nav(this.val$device, this.val$viewModel);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onCtChanged(float xProgress, int color, boolean finish) {
            LHomeLog.i(getClass(), "onColorChanged.progress=" + xProgress);
            LightItemProvider.this.getLightAssistant(this.val$device).sendCW(ActivityUtils.getTopActivity(), LightUtils.percent2C(xProgress), finish);
            LightItemProvider.this.getLightAssistant(this.val$device).setState(true);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.control.provider.LightItemProvider$4, reason: invalid class name */
    class AnonymousClass4 implements LightQuickDialog.OnDialogCallback {
        final /* synthetic */ Device val$device;
        final /* synthetic */ LightQuickDialog val$rgbDialog;
        final /* synthetic */ BaseViewModel val$viewModel;

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onCtChanged(float xProgress, int color, boolean finish) {
        }

        AnonymousClass4(final Device val$device, final LightQuickDialog val$rgbDialog, final BaseViewModel val$viewModel) {
            this.val$device = val$device;
            this.val$rgbDialog = val$rgbDialog;
            this.val$viewModel = val$viewModel;
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onColorChanged(float xProgress, int color, boolean finish) {
            LightItemProvider.this.getLightAssistant(this.val$device).sendRgb(ActivityUtils.getTopActivity(), color, finish);
            LightItemProvider.this.getLightAssistant(this.val$device).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onBrtChanged(int progress, boolean finish) {
            LightItemProvider.this.getLightAssistant(this.val$device).sendRgbBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
            LightItemProvider.this.getLightAssistant(this.val$device).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onSwitch(final boolean on) {
            LightAssistant lightAssistant = LightItemProvider.this.getLightAssistant(this.val$device);
            Activity topActivity = ActivityUtils.getTopActivity();
            final LightQuickDialog lightQuickDialog = this.val$rgbDialog;
            lightAssistant.sendOnOff(topActivity, on, new IAction() { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider$4$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    LightItemProvider.AnonymousClass4.lambda$onSwitch$0(LightQuickDialog.this, on, (Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$onSwitch$0(LightQuickDialog lightQuickDialog, boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            lightQuickDialog.setSwitchOn(!z);
            lightQuickDialog.notifyDialog();
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onMoreAction() {
            LightItemProvider.this.nav(this.val$device, this.val$viewModel);
        }
    }

    /* renamed from: com.ltech.smarthome.ui.control.provider.LightItemProvider$5, reason: invalid class name */
    class AnonymousClass5 implements LightQuickDialog.OnDialogCallback {
        final /* synthetic */ Device val$device;
        final /* synthetic */ LightQuickDialog val$dimDialog;
        final /* synthetic */ BaseViewModel val$viewModel;

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onColorChanged(float xProgress, int color, boolean finish) {
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onCtChanged(float xProgress, int color, boolean finish) {
        }

        AnonymousClass5(final Device val$device, final LightQuickDialog val$dimDialog, final BaseViewModel val$viewModel) {
            this.val$device = val$device;
            this.val$dimDialog = val$dimDialog;
            this.val$viewModel = val$viewModel;
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onBrtChanged(int progress, boolean finish) {
            LightItemProvider.this.getLightAssistant(this.val$device).sendDimBrtHas1to9(ActivityUtils.getTopActivity(), progress, finish);
            LightItemProvider.this.getLightAssistant(this.val$device).setState(true);
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onSwitch(final boolean on) {
            LightAssistant lightAssistant = LightItemProvider.this.getLightAssistant(this.val$device);
            Activity topActivity = ActivityUtils.getTopActivity();
            final LightQuickDialog lightQuickDialog = this.val$dimDialog;
            lightAssistant.sendOnOff(topActivity, on, new IAction() { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider$5$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.base.IAction
                public final void act(Object obj) {
                    LightItemProvider.AnonymousClass5.lambda$onSwitch$0(LightQuickDialog.this, on, (Boolean) obj);
                }
            });
        }

        static /* synthetic */ void lambda$onSwitch$0(LightQuickDialog lightQuickDialog, boolean z, Boolean bool) {
            if (bool.booleanValue()) {
                return;
            }
            lightQuickDialog.setSwitchOn(!z);
            lightQuickDialog.notifyDialog();
        }

        @Override // com.ltech.smarthome.view.dialog.LightQuickDialog.OnDialogCallback
        public void onMoreAction() {
            LightItemProvider.this.nav(this.val$device, this.val$viewModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LightAssistant getLightAssistant(Object controlObject) {
        return CmdAssistant.getLightCmdAssistant(controlObject, new int[0]);
    }

    private void selectStatus(final Device device, final LightQuickDialog lightQuickDialog) {
        int lightColorType = ProductRepository.getLightColorType((Object) device);
        if (lightColorType == 101 || lightColorType == 102 || lightColorType == 103 || lightColorType == 104 || lightColorType == 105) {
            MessageManager.getInstance().setDaliLightStatusCallBack(new MessageManager.DaliLightStatusCallBack(this) { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider.6
                @Override // com.smart.message.MessageManager.DaliLightStatusCallBack
                public void onDataReceive(int zoneNum, int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean supportK, int rgbColor) {
                    Device deviceById = Injection.repo().device().getDeviceById(device.getId());
                    if (deviceById == null || zoneNum != DaliProHelper.getDeviceAddress(device)) {
                        return;
                    }
                    deviceById.getDeviceState().setOn(isOn);
                    deviceById.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                    deviceById.getDeviceState().setWy(wy);
                    deviceById.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                    deviceById.setOnlineFlag(1);
                    deviceById.getDeviceState().setOnlineState(1);
                    Injection.repo().device().saveDevice(deviceById);
                    lightQuickDialog.setOnline(true).setSwitchOn(isOn);
                    lightQuickDialog.setWy(wy);
                    lightQuickDialog.setRgb(rgbColor);
                    int lightColorType2 = ProductRepository.getLightColorType((Object) deviceById);
                    if (lightColorType2 == 101 || lightColorType2 == 102) {
                        lightQuickDialog.setRgbBrt(deviceById.getDeviceState().getWyBrt());
                    } else {
                        lightQuickDialog.setRgbBrt(deviceById.getDeviceState().getRgbBrt());
                    }
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            lightQuickDialog.notifyDialog();
                        }
                    });
                }
            });
        } else {
            MessageManager.getInstance().setLightStatusCallBack(new MessageManager.LightStatusCallBack(this) { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider.7
                @Override // com.smart.message.MessageManager.LightStatusCallBack
                public void onDataReceive(int deviceAddress, boolean isOn, int wyBrt, int wy, int rgbBrt, boolean rgbOn, boolean supportK, boolean rhythmPlay, int rgbColor) {
                    Device deviceById = Injection.repo().device().getDeviceById(device.getId());
                    if (deviceById == null || ((BleParam) deviceById.getParam(BleParam.class)).getUnicastAddress() != deviceAddress) {
                        return;
                    }
                    deviceById.getDeviceState().setOn(isOn);
                    deviceById.getDeviceState().setWyBrt(LightUtils.brt2ProgressHasBelowZero(wyBrt));
                    deviceById.getDeviceState().setWy(wy);
                    deviceById.getDeviceState().setRgbBrt(LightUtils.brt2ProgressHasBelowZero(rgbBrt));
                    deviceById.setOnlineFlag(1);
                    deviceById.getDeviceState().setOnlineState(1);
                    Injection.repo().device().saveDevice(deviceById);
                    lightQuickDialog.setOnline(true).setSwitchOn(isOn);
                    lightQuickDialog.setWy(wy);
                    lightQuickDialog.setRgb(rgbColor);
                    int lightColorType2 = ProductRepository.getLightColorType((Object) deviceById);
                    if (lightColorType2 == 1 || lightColorType2 == 2) {
                        lightQuickDialog.setRgbBrt(deviceById.getDeviceState().getWyBrt());
                    } else {
                        lightQuickDialog.setRgbBrt(deviceById.getDeviceState().getRgbBrt());
                    }
                    ThreadUtils.runOnUiThread(new Runnable() { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            lightQuickDialog.notifyDialog();
                        }
                    });
                }
            });
        }
        ((ObservableSubscribeProxy) checkSingleDeviceStatus(device).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this.viewModel.getLifecycleOwner(), Lifecycle.Event.ON_DESTROY)))).subscribe(new Observer<Integer>() { // from class: com.ltech.smarthome.ui.control.provider.LightItemProvider.8
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
                    lightQuickDialog.setOnline(false);
                    lightQuickDialog.notifyDialog();
                    if (LightItemProvider.this.onDataChangeListener != null) {
                        LightItemProvider.this.onDataChangeListener.onDataOfflineChange(integer.intValue());
                    }
                }
            }
        });
    }
}