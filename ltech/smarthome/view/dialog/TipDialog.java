package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogTipBinding;
import com.ltech.smarthome.view.dialog.TipDialog;

/* loaded from: classes4.dex */
public class TipDialog extends SmartDialog<DialogTipBinding> {
    private String confirmString;
    private IAction<View> iAction;
    private String tip;
    private String title;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_tip;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.TipDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogTipBinding, TipDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogTipBinding viewBinding, final TipDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvTip.setText(dialog.tip);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.TipDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    TipDialog.AnonymousClass1.lambda$convertView$0(TipDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(TipDialog tipDialog, View view) {
            tipDialog.dismissDialog();
            if (tipDialog.iAction != null) {
                tipDialog.iAction.act(view);
            }
        }
    }

    public static TipDialog asDefault() {
        return (TipDialog) new TipDialog().setViewConverter(new AnonymousClass1()).setMargin(30).setGravity(17);
    }

    public TipDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public TipDialog setTip(String tip) {
        this.tip = tip;
        return this;
    }

    public TipDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "tip_dialog";
    }

    public TipDialog setConfirm(IAction<View> iAction) {
        this.iAction = iAction;
        return this;
    }
}