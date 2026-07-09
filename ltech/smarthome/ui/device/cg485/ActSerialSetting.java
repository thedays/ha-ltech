package com.ltech.smarthome.ui.device.cg485;

import android.view.View;
import androidx.lifecycle.Observer;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.VMActivity;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.ActSerialSettingBinding;
import com.ltech.smarthome.model.Injection;
import com.ltech.smarthome.net.request.device.UpdateBackDataRequest;
import com.ltech.smarthome.ui.replace.ReplaceHelper;
import com.ltech.smarthome.utils.Constants;
import com.ltech.smarthome.utils.NameRepository;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.dialog.SelectListDialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActSerialSetting extends VMActivity<ActSerialSettingBinding, ActCg485SettingVM> {
    private int getBaudRateIndex(int baudRate) {
        if (baudRate == 1) {
            return 0;
        }
        if (baudRate != 8) {
            return baudRate != 9 ? baudRate : baudRate - 1;
        }
        return 1;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected int provideLayoutId() {
        return R.layout.act_serial_setting;
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void initView() {
        super.initView();
        setBackImage(R.mipmap.icon_back);
        setTitle(getString(R.string.serial_setting));
        ((ActCg485SettingVM) this.mViewModel).baudRate = getIntent().getIntExtra(Constants.BAUD_RATE, 1);
        ((ActCg485SettingVM) this.mViewModel).parity = getIntent().getIntExtra(Constants.PARITY, 1);
        ((ActCg485SettingVM) this.mViewModel).dataBits = getIntent().getIntExtra(Constants.DATA_BITS, 1);
        ((ActCg485SettingVM) this.mViewModel).stopBits = getIntent().getIntExtra(Constants.STOP_BITS, 1);
        ((ActSerialSettingBinding) this.mViewBinding).setOwnerOrManager(Boolean.valueOf(isOwnerOrManager()));
        ((ActSerialSettingBinding) this.mViewBinding).setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda9
            @Override // com.ltech.smarthome.binding.command.BindingConsumer
            public final void call(Object obj) {
                ActSerialSetting.this.lambda$initView$8((View) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$8(View view) {
        if (isOwnerOrManager()) {
            switch (view.getId()) {
                case R.id.layout_baud_rate /* 2131297364 */:
                    ArrayList arrayList = new ArrayList(Arrays.asList(NameRepository.getBaudRate(this)));
                    final List asList = Arrays.asList(1, 8, 2, 3, 4, 5, 6, 7, 9);
                    SelectListDialog.asDefault(true).setTitle(getString(R.string.select_baud_rate)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(getBaudRateIndex(((ActCg485SettingVM) this.mViewModel).baudRate)).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda1
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActSerialSetting.this.lambda$initView$1(asList, (Integer) obj);
                        }
                    }).setSelectList(arrayList).showDialog(this);
                    break;
                case R.id.layout_data_bits /* 2131297421 */:
                    SelectListDialog.asDefault(true).setTitle(getString(R.string.select_data_bits)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(((ActCg485SettingVM) this.mViewModel).dataBits - 1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda3
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActSerialSetting.this.lambda$initView$5((Integer) obj);
                        }
                    }).setSelectList(new ArrayList(Arrays.asList(NameRepository.getDataBits(this)))).showDialog(this);
                    break;
                case R.id.layout_parity /* 2131297567 */:
                    SelectListDialog.asDefault(true).setTitle(getString(R.string.select_parity)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(((ActCg485SettingVM) this.mViewModel).parity - 1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda2
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActSerialSetting.this.lambda$initView$3((Integer) obj);
                        }
                    }).setSelectList(new ArrayList(Arrays.asList(NameRepository.getParity(this)))).showDialog(this);
                    break;
                case R.id.layout_stop_bits /* 2131297661 */:
                    SelectListDialog.asDefault(true).setTitle(getString(R.string.select_stop_bits)).setCancelString(getString(R.string.cancel)).setConfirmString(getString(R.string.confirm)).setSelectPosition(((ActCg485SettingVM) this.mViewModel).stopBits - 1).setConfirmAction(new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda4
                        @Override // com.ltech.smarthome.base.IAction
                        public final void act(Object obj) {
                            ActSerialSetting.this.lambda$initView$7((Integer) obj);
                        }
                    }).setSelectList(new ArrayList(Arrays.asList(NameRepository.getStopBits(this)))).showDialog(this);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(final List list, final Integer num) {
        CmdAssistant.getDeviceAssistant(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRs485Serial(this, 2, ((Integer) list.get(num.intValue())).intValue(), new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSerialSetting.this.lambda$initView$0(num, list, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(Integer num, List list, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this.activity, ((ActCg485SettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.SERIAL_PORT_BPS, CmdAssistant.getDeviceAssistant(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRs485Serial(2, num.intValue() + 1));
            ((ActCg485SettingVM) this.mViewModel).baudRate = ((Integer) list.get(num.intValue())).intValue();
            ((ActSerialSettingBinding) this.mViewBinding).tvBaudRate.setText(NameRepository.getBaudRate(this)[num.intValue()]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(final Integer num) {
        CmdAssistant.getDeviceAssistant(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRs485Serial(this, 3, num.intValue() + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda8
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSerialSetting.this.lambda$initView$2(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this.activity, ((ActCg485SettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.SERIAL_PORT_CHECK, CmdAssistant.getDeviceAssistant(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRs485Serial(3, num.intValue() + 1));
            ((ActCg485SettingVM) this.mViewModel).parity = num.intValue() + 1;
            ((ActSerialSettingBinding) this.mViewBinding).tvParity.setText(NameRepository.getParity(this)[num.intValue()]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$5(final Integer num) {
        CmdAssistant.getDeviceAssistant(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRs485Serial(this, 4, num.intValue() + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda6
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSerialSetting.this.lambda$initView$4(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$4(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this.activity, ((ActCg485SettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.SERIAL_PORT_DATA_BIT, CmdAssistant.getDeviceAssistant(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRs485Serial(4, num.intValue() + 1));
            ((ActCg485SettingVM) this.mViewModel).dataBits = num.intValue() + 1;
            ((ActSerialSettingBinding) this.mViewBinding).tvDataBits.setText(NameRepository.getDataBits(this)[num.intValue()]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$7(final Integer num) {
        CmdAssistant.getDeviceAssistant(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRs485Serial(this, 5, num.intValue() + 1, new IAction() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda5
            @Override // com.ltech.smarthome.base.IAction
            public final void act(Object obj) {
                ActSerialSetting.this.lambda$initView$6(num, (Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$6(Integer num, Boolean bool) {
        if (bool.booleanValue()) {
            ReplaceHelper.instance().backupData(this.activity, ((ActCg485SettingVM) this.mViewModel).controlDevice.getValue().getDeviceId(), UpdateBackDataRequest.SERIAL_PORT_END_BIT, CmdAssistant.getDeviceAssistant(((ActCg485SettingVM) this.mViewModel).controlDevice.getValue(), new int[0]).setRs485Serial(5, num.intValue() + 1));
            ((ActCg485SettingVM) this.mViewModel).stopBits = num.intValue() + 1;
            ((ActSerialSettingBinding) this.mViewBinding).tvStopBits.setText(NameRepository.getStopBits(this)[num.intValue()]);
        }
    }

    @Override // com.ltech.smarthome.base.BaseNormalActivity
    protected void startObserve() {
        ((ActCg485SettingVM) this.mViewModel).controlId = getIntent().getLongExtra(Constants.CONTROL_ID, -1L);
        ((ActCg485SettingVM) this.mViewModel).controlDevice.setValue(Injection.repo().device().getDeviceById(((ActCg485SettingVM) this.mViewModel).controlId));
        ((ActCg485SettingVM) this.mViewModel).queryParamResult.observe(this, new Observer() { // from class: com.ltech.smarthome.ui.device.cg485.ActSerialSetting$$ExternalSyntheticLambda7
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ActSerialSetting.this.lambda$startObserve$9((Boolean) obj);
            }
        });
        ((ActCg485SettingVM) this.mViewModel).queryParamResult.setValue(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startObserve$9(Boolean bool) {
        ((ActSerialSettingBinding) this.mViewBinding).tvBaudRate.setText(NameRepository.getBaudRate(this)[getBaudRateIndex(((ActCg485SettingVM) this.mViewModel).baudRate)]);
        ((ActSerialSettingBinding) this.mViewBinding).tvParity.setText(NameRepository.getParity(this)[((ActCg485SettingVM) this.mViewModel).parity - 1]);
        ((ActSerialSettingBinding) this.mViewBinding).tvDataBits.setText(NameRepository.getDataBits(this)[((ActCg485SettingVM) this.mViewModel).dataBits - 1]);
        ((ActSerialSettingBinding) this.mViewBinding).tvStopBits.setText(NameRepository.getStopBits(this)[((ActCg485SettingVM) this.mViewModel).stopBits - 1]);
    }
}