package com.ltech.smarthome.ui.device.dalipro;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.BaseNormalFragment;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.FtDaliPushrodBinding;
import com.ltech.smarthome.ltnfc.utils.BrightUtils;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.model.device_param.BleParam;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.EditTextSeekBarView;
import com.ltech.smarthome.view.TextSeekBarView;
import com.smart.message.MessageManager;

/* loaded from: classes4.dex */
public class FtDaliPushrod extends BaseNormalFragment<FtDaliPushrodBinding> {
    private int blue;
    private MutableLiveData<Device> controlDevice = new MutableLiveData<>();
    private int green;
    private Device mDevice;
    private int red;

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected int provideLayoutId() {
        return R.layout.ft_dali_pushrod;
    }

    public static FtDaliPushrod newInstance(long deviceId) {
        FtDaliPushrod ftDaliPushrod = new FtDaliPushrod();
        Bundle bundle = new Bundle();
        bundle.putLong("device_id", deviceId);
        ftDaliPushrod.setArguments(bundle);
        return ftDaliPushrod;
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initData() {
        super.initData();
        Bundle arguments = getArguments();
        if (arguments != null) {
            Device deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(arguments.getLong("device_id"));
            this.mDevice = deviceByDeviceId;
            this.controlDevice.setValue(deviceByDeviceId);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void initView() {
        initListener();
    }

    @Override // com.ltech.smarthome.base.BaseNormalFragment
    protected void startObserve() {
        super.startObserve();
        this.controlDevice.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliPushrod$$ExternalSyntheticLambda6
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                FtDaliPushrod.this.lambda$startObserve$0((Device) obj);
            }
        });
        MessageManager.getInstance().setCgdProStatusCallBack(new MessageManager.CgdProStatusCallBack() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliPushrod$$ExternalSyntheticLambda7
            @Override // com.smart.message.MessageManager.CgdProStatusCallBack
            public final void onDataReceive(int i, int i2, boolean z, int i3, int i4, int i5, int i6, boolean z2) {
                FtDaliPushrod.this.lambda$startObserve$1(i, i2, z, i3, i4, i5, i6, z2);
            }
        });
        CmdAssistant.getQueryCmdAssistant(this.mDevice, DaliProHelper.BROADCAST_ADD).queryLightState(ActivityUtils.getTopActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$0(Device device) {
        DeviceState deviceState = device.getDeviceState();
        this.red = deviceState.getRed();
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarR.setProgress(deviceState.getRed());
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarR.setValue(String.valueOf(deviceState.getRed()));
        this.green = deviceState.getGreen();
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarG.setProgress(deviceState.getGreen());
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarG.setValue(String.valueOf(deviceState.getGreen()));
        this.blue = deviceState.getBlue();
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarB.setProgress(deviceState.getBlue());
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarB.setValue(String.valueOf(deviceState.getBlue()));
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarBrt.setProgress(deviceState.getWyBrt());
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarBrt.setValue(BrightUtils.getLogPercent().get(Integer.valueOf(deviceState.getWyBrt())));
        ((FtDaliPushrodBinding) this.mViewBinding).ctSeekbar.setProgress(255 - deviceState.getWy());
        ((FtDaliPushrodBinding) this.mViewBinding).ctSeekbar.setValue(LightUtils.ctY2K(deviceState.getWy()) + "K");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$1(int i, int i2, boolean z, int i3, int i4, int i5, int i6, boolean z2) {
        Device deviceByDeviceId;
        if (i2 == ((BleParam) this.mDevice.getParam(BleParam.class)).getUnicastAddress() && i == DaliProHelper.BROADCAST_ADD && (deviceByDeviceId = Injection.repo().device().getDeviceByDeviceId(this.mDevice.getDeviceId())) != null) {
            deviceByDeviceId.getDeviceState().setOn(z);
            deviceByDeviceId.getDeviceState().setWyBrt(i4);
            deviceByDeviceId.getDeviceState().setWy(i5);
            deviceByDeviceId.getDeviceState().setRed((i3 >> 16) & 255);
            deviceByDeviceId.getDeviceState().setGreen((i3 >> 8) & 255);
            deviceByDeviceId.getDeviceState().setBlue(i3 & 255);
            Injection.repo().device().saveDevice(deviceByDeviceId);
            this.controlDevice.setValue(deviceByDeviceId);
        }
    }

    private void initListener() {
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarBrt.setRange(1, 255, 1);
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarBrt.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliPushrod$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtDaliPushrod.this.lambda$initListener$2(i, z);
            }
        });
        ((FtDaliPushrodBinding) this.mViewBinding).ctSeekbar.setRange(0, 255, 1);
        ((FtDaliPushrodBinding) this.mViewBinding).ctSeekbar.setOnProgressChangeListener(new TextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliPushrod$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.TextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtDaliPushrod.this.lambda$initListener$3(i, z);
            }
        });
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarR.setOnProgressChangeListener(new EditTextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliPushrod$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.EditTextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtDaliPushrod.this.lambda$initListener$4(i, z);
            }
        });
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarG.setOnProgressChangeListener(new EditTextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliPushrod$$ExternalSyntheticLambda3
            @Override // com.ltech.smarthome.view.EditTextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtDaliPushrod.this.lambda$initListener$5(i, z);
            }
        });
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarB.setOnProgressChangeListener(new EditTextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliPushrod$$ExternalSyntheticLambda4
            @Override // com.ltech.smarthome.view.EditTextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z) {
                FtDaliPushrod.this.lambda$initListener$6(i, z);
            }
        });
        ((FtDaliPushrodBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.dalipro.FtDaliPushrod$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                FtDaliPushrod.this.lambda$initListener$7((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$2(int i, boolean z) {
        ((FtDaliPushrodBinding) this.mViewBinding).seekbarBrt.setValue(BrightUtils.getLogPercent().get(Integer.valueOf(i)));
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).sendDimBrtD1Has0to255(getContext(), i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$3(int i, boolean z) {
        ((FtDaliPushrodBinding) this.mViewBinding).ctSeekbar.setValue(LightUtils.ctY2K(255 - i) + "K");
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).sendCW(getContext(), i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$4(int i, boolean z) {
        this.red = i;
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).sendRgb(getContext(), this.red, this.green, this.blue, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$5(int i, boolean z) {
        this.green = i;
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).sendRgb(getContext(), this.red, this.green, this.blue, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$6(int i, boolean z) {
        this.blue = i;
        CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).sendRgb(getContext(), this.red, this.green, this.blue, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$7(View view) {
        int id = view.getId();
        if (id == R.id.tv_off) {
            CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).sendOnOff(getContext(), false);
        } else {
            if (id != R.id.tv_on) {
                return;
            }
            CmdAssistant.getLightCmdAssistant(this.controlDevice.getValue(), new int[0]).sendOnOff(getContext(), true);
        }
    }
}