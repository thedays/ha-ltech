package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogButtonTipBinding;
import com.ltech.smarthome.view.dialog.ButtonTipDialog;

/* loaded from: classes4.dex */
public class ButtonTipDialog extends SmartDialog<DialogButtonTipBinding> {
    private String confirmString;
    private int imageRes;
    private OnConfirmCallback mCallback;
    private String title;

    public interface OnConfirmCallback {
        void onConfirmClick(ButtonTipDialog dialog);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_button_tip;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.ButtonTipDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogButtonTipBinding, ButtonTipDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogButtonTipBinding viewBinding, final ButtonTipDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.ButtonTipDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ButtonTipDialog.AnonymousClass1.lambda$convertView$0(ButtonTipDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(ButtonTipDialog buttonTipDialog, View view) {
            if (buttonTipDialog.mCallback != null) {
                buttonTipDialog.mCallback.onConfirmClick(buttonTipDialog);
            }
            buttonTipDialog.dismissDialog();
        }
    }

    public static ButtonTipDialog asDefault() {
        return (ButtonTipDialog) new ButtonTipDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    public ButtonTipDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ButtonTipDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public ButtonTipDialog setImageRes(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    public ButtonTipDialog setCallback(OnConfirmCallback callback) {
        this.mCallback = callback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "button_tip_dialog";
    }
}