package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogCallInviteBinding;
import com.ltech.smarthome.view.dialog.CallInviteDialog;

/* loaded from: classes4.dex */
public class CallInviteDialog extends SmartDialog<DialogCallInviteBinding> {
    private String callName;
    private String inviteTip;
    private int logoResId;
    private OnConfirmListener onConfirmListener;
    private DialogCallInviteBinding viewBinding;

    public interface OnConfirmListener {
        void onCancel();

        void onConfirm();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    public int initTheme() {
        return R.style.MyBaseDialog;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_call_invite;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.CallInviteDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogCallInviteBinding, CallInviteDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogCallInviteBinding viewBinding, final CallInviteDialog dialog) {
            dialog.getDialog().getWindow().setType(2038);
            dialog.initView(viewBinding);
            viewBinding.callLogo.setImageResource(dialog.logoResId);
            viewBinding.tvCallName.setText(dialog.callName);
            viewBinding.tvInvite.setText(dialog.inviteTip);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.CallInviteDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    CallInviteDialog.AnonymousClass1.lambda$convertView$0(CallInviteDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(CallInviteDialog callInviteDialog, View view) {
            switch (view.getId()) {
                case R.id.call_answer /* 2131296508 */:
                    if (callInviteDialog.onConfirmListener != null) {
                        callInviteDialog.onConfirmListener.onConfirm();
                    }
                    callInviteDialog.dismissDialog();
                    break;
                case R.id.call_hangup /* 2131296509 */:
                    if (callInviteDialog.onConfirmListener != null) {
                        callInviteDialog.onConfirmListener.onCancel();
                    }
                    callInviteDialog.dismissDialog();
                    break;
            }
        }
    }

    public static CallInviteDialog asDefault() {
        return (CallInviteDialog) new CallInviteDialog().setViewConverter(new AnonymousClass1()).setOutCancel(false).setMargin(16).setHeight(110).setWidth(0).setGravity(48);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(DialogCallInviteBinding viewBinding) {
        this.viewBinding = viewBinding;
    }

    public CallInviteDialog setLogoResId(int logoResId) {
        this.logoResId = logoResId;
        return this;
    }

    public CallInviteDialog setCallName(String callName) {
        this.callName = callName;
        return this;
    }

    public void setCallNameUi(String callName) {
        DialogCallInviteBinding dialogCallInviteBinding = this.viewBinding;
        if (dialogCallInviteBinding != null) {
            dialogCallInviteBinding.tvCallName.setText(callName);
        }
    }

    public void setInviteTipUi(String inviteTip) {
        DialogCallInviteBinding dialogCallInviteBinding = this.viewBinding;
        if (dialogCallInviteBinding != null) {
            dialogCallInviteBinding.tvInvite.setText(inviteTip);
        }
    }

    public void setLogoResIdUi(int logoResId) {
        DialogCallInviteBinding dialogCallInviteBinding = this.viewBinding;
        if (dialogCallInviteBinding != null) {
            dialogCallInviteBinding.callLogo.setImageResource(logoResId);
        }
    }

    public CallInviteDialog setInviteTip(String inviteTip) {
        this.inviteTip = inviteTip;
        return this;
    }

    public CallInviteDialog setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "call_invite_dialog";
    }
}