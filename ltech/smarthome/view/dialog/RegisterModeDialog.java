package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogRegModeBinding;
import com.ltech.smarthome.view.dialog.RegisterModeDialog;

/* loaded from: classes4.dex */
public class RegisterModeDialog extends SmartDialog<DialogRegModeBinding> {
    private ClickListener mClickListener;

    public interface ClickListener {
        void email();

        void phone();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_reg_mode;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.RegisterModeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogRegModeBinding, RegisterModeDialog> {
        final /* synthetic */ ClickListener val$listener;

        AnonymousClass1(final ClickListener val$listener) {
            this.val$listener = val$listener;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogRegModeBinding viewBinding, final RegisterModeDialog dialog) {
            final ClickListener clickListener = this.val$listener;
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.RegisterModeDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    RegisterModeDialog.AnonymousClass1.lambda$convertView$0(RegisterModeDialog.this, clickListener, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(RegisterModeDialog registerModeDialog, ClickListener clickListener, View view) {
            int id = view.getId();
            if (id == R.id.tv_email) {
                if (registerModeDialog.getClickListener() != null) {
                    clickListener.email();
                }
                registerModeDialog.dismissDialog();
            } else {
                if (id != R.id.tv_phone) {
                    return;
                }
                if (registerModeDialog.getClickListener() != null) {
                    clickListener.phone();
                }
                registerModeDialog.dismissDialog();
            }
        }
    }

    public static RegisterModeDialog asDefault(ClickListener listener) {
        return (RegisterModeDialog) new RegisterModeDialog(listener).setViewConverter(new AnonymousClass1(listener)).setGravity(17).setOutCancel(true).setWidth(-1);
    }

    public RegisterModeDialog(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "register_dialog";
    }

    public ClickListener getClickListener() {
        return this.mClickListener;
    }
}