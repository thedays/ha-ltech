package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogPairBinding;
import com.ltech.smarthome.view.dialog.PairDialog;

/* loaded from: classes4.dex */
public class PairDialog extends SmartDialog<DialogPairBinding> {
    private String failString;
    private OnDialogCallback mDialogCallback;
    private String pairString;
    private String successString;
    private String tip;
    private String title;

    public interface OnDialogCallback {
        void cancel();

        void confirm();

        void pair();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_pair;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.PairDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogPairBinding, PairDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogPairBinding viewBinding, final PairDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvTip.setText(dialog.tip);
            viewBinding.tvPair.setText(dialog.pairString);
            viewBinding.tvConfirm.setText(dialog.successString);
            viewBinding.tvCancel.setText(dialog.failString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.PairDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    PairDialog.AnonymousClass1.lambda$convertView$0(PairDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(PairDialog pairDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                if (pairDialog.mDialogCallback != null) {
                    pairDialog.mDialogCallback.cancel();
                }
            } else {
                if (id != R.id.tv_confirm) {
                    if (id == R.id.tv_pair && pairDialog.mDialogCallback != null) {
                        pairDialog.mDialogCallback.pair();
                        return;
                    }
                    return;
                }
                if (pairDialog.mDialogCallback != null) {
                    pairDialog.mDialogCallback.confirm();
                }
            }
        }
    }

    public static PairDialog asDefault() {
        return (PairDialog) new PairDialog().setViewConverter(new AnonymousClass1()).setMargin(30).setGravity(17);
    }

    public PairDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public PairDialog setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public PairDialog setPairString(String pairString) {
        this.pairString = pairString;
        return this;
    }

    public PairDialog setSuccessString(String successString) {
        this.successString = successString;
        return this;
    }

    public PairDialog setFailString(String failString) {
        this.failString = failString;
        return this;
    }

    public PairDialog setDialogCallback(OnDialogCallback mDialogCallback) {
        this.mDialogCallback = mDialogCallback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "pair_dialog";
    }
}