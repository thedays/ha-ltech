package com.ltech.smarthome.view.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.databinding.ObservableField;
import com.blankj.utilcode.util.ActivityUtils;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogEditCopyBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditCmdCopyDialog;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class EditCmdCopyDialog extends SmartDialog<DialogEditCopyBinding> {
    private String cancelString;
    private IAction<String> confirmAction;
    private String confirmString;
    private String contentTip;
    private String inputEmptyTip;
    private String title;
    private ObservableField<String> content = new ObservableField<>();
    private boolean hexFormat = true;

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_edit_copy;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.EditCmdCopyDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogEditCopyBinding, EditCmdCopyDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogEditCopyBinding viewBinding, final EditCmdCopyDialog dialog) {
            final String str;
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.tvCancel.setText(dialog.cancelString);
            viewBinding.tvConfirm.setText(dialog.confirmString);
            if (!TextUtils.isEmpty(dialog.contentTip)) {
                viewBinding.tvContentTip.setVisibility(0);
                viewBinding.tvContentTip.setText(dialog.contentTip);
            }
            if (!TextUtils.isEmpty(dialog.inputEmptyTip)) {
                viewBinding.etContent.setHint(dialog.inputEmptyTip);
            }
            if (!dialog.hexFormat) {
                str = "^[\\x00-\\x7F]+$";
            } else {
                str = "^[0-9a-fA-F ]+$";
            }
            viewBinding.etContent.setFilters(new InputFilter[]{new InputFilter(this) { // from class: com.ltech.smarthome.view.dialog.EditCmdCopyDialog.1.1
                @Override // android.text.InputFilter
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (Pattern.compile(str).matcher(source).matches()) {
                        return null;
                    }
                    return "";
                }
            }});
            viewBinding.setContent(dialog.content);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.EditCmdCopyDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    EditCmdCopyDialog.AnonymousClass1.lambda$convertView$0(EditCmdCopyDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(EditCmdCopyDialog editCmdCopyDialog, View view) {
            switch (view.getId()) {
                case R.id.iv_copy /* 2131296989 */:
                    editCmdCopyDialog.copyText();
                    break;
                case R.id.iv_delete /* 2131297006 */:
                    editCmdCopyDialog.setContent("");
                    break;
                case R.id.tv_cancel /* 2131298504 */:
                    editCmdCopyDialog.hideKeyboard();
                    editCmdCopyDialog.dismissDialog();
                    break;
                case R.id.tv_confirm /* 2131298530 */:
                    String str = (String) editCmdCopyDialog.content.get();
                    if (str == null || TextUtils.isEmpty(str.trim())) {
                        SmartToast.showShort(editCmdCopyDialog.getActivity().getString(R.string.please_input_instruct));
                        break;
                    } else {
                        editCmdCopyDialog.hideKeyboard();
                        editCmdCopyDialog.dismissDialog();
                        if (editCmdCopyDialog.confirmAction != null) {
                            editCmdCopyDialog.confirmAction.act(str);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public static EditCmdCopyDialog asDefault() {
        return (EditCmdCopyDialog) new EditCmdCopyDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setY(16).setMargin(16);
    }

    public void copyText() {
        if (this.content.get() == null && TextUtils.isEmpty(this.content.get())) {
            return;
        }
        ((ClipboardManager) ActivityUtils.getTopActivity().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Label", this.content.get()));
        SmartToast.showShort(R.string.copy_success);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getDialog().getContext().getSystemService("input_method");
        if (inputMethodManager == null || !((DialogEditCopyBinding) this.mViewBinding).etContent.isFocused()) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(((DialogEditCopyBinding) this.mViewBinding).etContent.getWindowToken(), 0);
        ((DialogEditCopyBinding) this.mViewBinding).etContent.clearFocus();
    }

    public EditCmdCopyDialog setContent(String content) {
        this.content.set(content);
        return this;
    }

    public EditCmdCopyDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public EditCmdCopyDialog setConfirmAction(IAction<String> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public EditCmdCopyDialog setCancelString(String cancelString) {
        this.cancelString = cancelString;
        return this;
    }

    public EditCmdCopyDialog setConfirmString(String confirmString) {
        this.confirmString = confirmString;
        return this;
    }

    public EditCmdCopyDialog setContentTip(String contentTip) {
        this.contentTip = contentTip;
        return this;
    }

    public EditCmdCopyDialog setInputEmptyTip(String inputEmptyTip) {
        this.inputEmptyTip = inputEmptyTip;
        return this;
    }

    public EditCmdCopyDialog setHexFormat(boolean hexFormat) {
        this.hexFormat = hexFormat;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "edit_copy_dialog";
    }
}