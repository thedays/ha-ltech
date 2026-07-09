package com.ltech.smarthome.view.dialog;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogRgbFunctionTipBinding;

/* loaded from: classes4.dex */
public class RgbFunctionTipDialog extends SmartDialog<DialogRgbFunctionTipBinding> {
    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_rgb_function_tip;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.RgbFunctionTipDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogRgbFunctionTipBinding, RgbFunctionTipDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogRgbFunctionTipBinding viewBinding, final RgbFunctionTipDialog dialog) {
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.RgbFunctionTipDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    RgbFunctionTipDialog.this.dismiss();
                }
            }));
        }
    }

    public static RgbFunctionTipDialog asDefault() {
        return (RgbFunctionTipDialog) new RgbFunctionTipDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }
}