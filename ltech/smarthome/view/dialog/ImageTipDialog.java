package com.ltech.smarthome.view.dialog;

import android.content.DialogInterface;
import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogImageTipBinding;
import com.ltech.smarthome.view.dialog.ImageTipDialog;

/* loaded from: classes4.dex */
public class ImageTipDialog extends SmartDialog<DialogImageTipBinding> {
    private OnCancelCallback cancelCallback;
    private String confirmString;
    private int imageRes;
    private OnConfirmCallback mCallback;
    private String title;

    public interface OnCancelCallback {
        void onCancel();
    }

    public interface OnConfirmCallback {
        void onConfirmClick(ImageTipDialog dialog);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_image_tip;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.ImageTipDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogImageTipBinding, ImageTipDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogImageTipBinding viewBinding, final ImageTipDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            viewBinding.ivTip.setImageResource(dialog.imageRes);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.ImageTipDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    ImageTipDialog.AnonymousClass1.lambda$convertView$0(ImageTipDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(ImageTipDialog imageTipDialog, DialogImageTipBinding dialogImageTipBinding, View view) {
            if (imageTipDialog.mCallback != null) {
                imageTipDialog.mCallback.onConfirmClick(imageTipDialog);
            }
            dialogImageTipBinding.tvConfirm.setText(R.string.connecting);
            dialogImageTipBinding.tvConfirm.setBackgroundResource(R.drawable.shape_gray_bt);
        }
    }

    public static ImageTipDialog asDefault() {
        return (ImageTipDialog) new ImageTipDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        OnCancelCallback onCancelCallback = this.cancelCallback;
        if (onCancelCallback != null) {
            onCancelCallback.onCancel();
        }
    }

    public ImageTipDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ImageTipDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public ImageTipDialog setImageRes(int imageRes) {
        this.imageRes = imageRes;
        return this;
    }

    public ImageTipDialog setCallback(OnConfirmCallback callback) {
        this.mCallback = callback;
        return this;
    }

    public ImageTipDialog setCancelCallback(OnCancelCallback callback) {
        this.cancelCallback = callback;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "image_tip_dialog";
    }
}