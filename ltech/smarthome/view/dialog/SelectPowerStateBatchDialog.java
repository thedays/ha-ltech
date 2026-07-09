package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogPowerStateBatchBinding;
import com.ltech.smarthome.ui.device.light.PowerState;
import com.ltech.smarthome.view.RadioImageTextView;
import com.ltech.smarthome.view.dialog.SelectPowerStateBatchDialog;
import com.ltech.smarthome.view.helper.PowerStateViewHelper;

/* loaded from: classes4.dex */
public class SelectPowerStateBatchDialog extends SmartDialog<DialogPowerStateBatchBinding> {
    private OnSaveListener mOnSaveListener;
    private PowerState powerState = new PowerState();
    private String saveText;
    private PowerStateViewHelper viewHelper;

    public interface OnSaveListener {
        boolean onSave(PowerState powerState);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_power_state_batch;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectPowerStateBatchDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogPowerStateBatchBinding, SelectPowerStateBatchDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogPowerStateBatchBinding viewBinding, final SelectPowerStateBatchDialog dialog) {
            if (dialog.saveText != null) {
                viewBinding.tvSave.setText(dialog.saveText);
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectPowerStateBatchDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectPowerStateBatchDialog.AnonymousClass1.lambda$convertView$0(SelectPowerStateBatchDialog.this, (View) obj);
                }
            }));
            dialog.viewHelper = new PowerStateViewHelper(dialog.getActivity(), viewBinding.layoutState, dialog.powerState, null);
            dialog.viewHelper.initPowerState();
            dialog.initListeners(viewBinding, viewBinding.radioLightOnDefault);
            dialog.initListeners(viewBinding, viewBinding.radioLightOnNotLight);
            dialog.initListeners(viewBinding, viewBinding.radioLightOnMemory);
            dialog.initListeners(viewBinding, viewBinding.radioLightOnCustom);
            dialog.refreshCheck(viewBinding);
            dialog.viewHelper.changePowerState(dialog.powerState.state == 4);
        }

        static /* synthetic */ void lambda$convertView$0(SelectPowerStateBatchDialog selectPowerStateBatchDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectPowerStateBatchDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_save) {
                return;
            }
            if (selectPowerStateBatchDialog.mOnSaveListener != null) {
                if (selectPowerStateBatchDialog.mOnSaveListener.onSave(selectPowerStateBatchDialog.powerState)) {
                    selectPowerStateBatchDialog.dismissDialog();
                    return;
                }
                return;
            }
            selectPowerStateBatchDialog.dismissDialog();
        }
    }

    public static SelectPowerStateBatchDialog asDefault() {
        return (SelectPowerStateBatchDialog) new SelectPowerStateBatchDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setY(16).setMargin(16).setOutCancel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initListeners(final DialogPowerStateBatchBinding viewBinding, final RadioImageTextView radioImageTextView) {
        radioImageTextView.setListener(new RadioImageTextView.OnCheckChangedListener() { // from class: com.ltech.smarthome.view.dialog.SelectPowerStateBatchDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.RadioImageTextView.OnCheckChangedListener
            public final void onCheckChanged(RadioImageTextView radioImageTextView2, boolean z) {
                SelectPowerStateBatchDialog.this.lambda$initListeners$0(radioImageTextView, viewBinding, radioImageTextView2, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListeners$0(RadioImageTextView radioImageTextView, DialogPowerStateBatchBinding dialogPowerStateBatchBinding, RadioImageTextView radioImageTextView2, boolean z) {
        switch (radioImageTextView.getId()) {
            case R.id.radio_light_on_custom /* 2131297955 */:
                this.powerState.state = 4;
                break;
            case R.id.radio_light_on_default /* 2131297956 */:
                this.powerState.state = 1;
                break;
            case R.id.radio_light_on_memory /* 2131297957 */:
                this.powerState.state = 3;
                break;
            case R.id.radio_light_on_not_light /* 2131297958 */:
                this.powerState.state = 2;
                break;
        }
        refreshCheck(dialogPowerStateBatchBinding);
        this.viewHelper.changePowerState(this.powerState.state == 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshCheck(DialogPowerStateBatchBinding viewBinding) {
        viewBinding.radioLightOnDefault.setCheck(this.powerState.state == 1);
        viewBinding.radioLightOnNotLight.setCheck(this.powerState.state == 2);
        viewBinding.radioLightOnMemory.setCheck(this.powerState.state == 3);
        viewBinding.radioLightOnCustom.setCheck(this.powerState.state == 4);
    }

    public SelectPowerStateBatchDialog setOnSaveListener(OnSaveListener mOnSaveListener) {
        this.mOnSaveListener = mOnSaveListener;
        return this;
    }

    public SelectPowerStateBatchDialog setSaveText(String saveText) {
        this.saveText = saveText;
        return this;
    }

    public SelectPowerStateBatchDialog setPowerState(PowerState state) {
        if (state != null) {
            this.powerState = state;
        }
        return this;
    }
}