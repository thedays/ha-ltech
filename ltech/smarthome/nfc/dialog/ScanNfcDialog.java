package com.ltech.smarthome.nfc.dialog;

import android.content.DialogInterface;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogScanNfcBinding;
import com.ltech.smarthome.nfc.dialog.ScanNfcDialog;

/* loaded from: classes4.dex */
public class ScanNfcDialog extends SmartDialog<DialogScanNfcBinding> {
    private OnDialogCallback onDialogCallback;

    public interface OnDialogCallback {
        void onDismiss();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_scan_nfc;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return null;
    }

    /* renamed from: com.ltech.smarthome.nfc.dialog.ScanNfcDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogScanNfcBinding, ScanNfcDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogScanNfcBinding viewBinding, final ScanNfcDialog dialog) {
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.nfc.dialog.ScanNfcDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ScanNfcDialog.AnonymousClass1.lambda$convertView$0(ScanNfcDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(ScanNfcDialog scanNfcDialog, View view) {
            if (view.getId() != R.id.tv_cancel) {
                return;
            }
            scanNfcDialog.dismissDialog();
        }
    }

    public static ScanNfcDialog asDefault() {
        return (ScanNfcDialog) new ScanNfcDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setY(16).setMargin(16).setOutCancel(false);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        OnDialogCallback onDialogCallback = this.onDialogCallback;
        if (onDialogCallback != null) {
            onDialogCallback.onDismiss();
        }
    }

    public ScanNfcDialog setOnDialogCallback(OnDialogCallback onDialogCallback) {
        this.onDialogCallback = onDialogCallback;
        return this;
    }
}