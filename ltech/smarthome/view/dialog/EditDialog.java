package com.ltech.smarthome.view.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogEditBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditDialog;

/* loaded from: classes4.dex */
public class EditDialog extends SmartDialog<DialogEditBinding> {
    private String actionType;
    private IAction<String> confirmAction;
    private String hint;
    private String inputEmptyTip;
    private String title;
    private int inputType = -1;
    private ObservableField<String> content = new ObservableField<>();

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_edit;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.EditDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogEditBinding, EditDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogEditBinding viewBinding, final EditDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setContent(dialog.content);
            viewBinding.etContent.setHint(dialog.hint);
            final FragmentActivity activity = dialog.getActivity();
            if (dialog.inputType != -1) {
                viewBinding.etContent.setInputType(dialog.inputType);
                if (dialog.inputType == 2) {
                    viewBinding.etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                }
            }
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.EditDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    EditDialog.AnonymousClass1.lambda$convertView$0(EditDialog.this, activity, (View) obj);
                }
            }));
            viewBinding.etContent.addTextChangedListener(new TextWatcher(this) { // from class: com.ltech.smarthome.view.dialog.EditDialog.1.1
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s.toString())) {
                        return;
                    }
                    viewBinding.etContent.requestFocus();
                }
            });
        }

        static /* synthetic */ void lambda$convertView$0(EditDialog editDialog, Context context, View view) {
            int id = view.getId();
            if (id == R.id.iv_delete) {
                editDialog.setContent("");
                return;
            }
            if (id == R.id.tv_cancel) {
                editDialog.hideKeyboard();
                editDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_ok) {
                return;
            }
            String str = (String) editDialog.content.get();
            if (str == null || TextUtils.isEmpty(str.trim())) {
                if (TextUtils.isEmpty(editDialog.inputEmptyTip)) {
                    SmartToast.showShort(context.getString(R.string.input_name));
                    return;
                } else {
                    SmartToast.showShort(editDialog.inputEmptyTip);
                    return;
                }
            }
            editDialog.hideKeyboard();
            editDialog.dismissDialog();
            if (editDialog.confirmAction != null) {
                editDialog.confirmAction.act(str);
            }
        }
    }

    public static EditDialog asDefault() {
        return (EditDialog) new EditDialog().setViewConverter(new AnonymousClass1()).setGravity(17).setAnimStyle(R.style.iOSDialogAnimStyle).setOutCancel(false).setMargin(40);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getDialog().getContext().getSystemService("input_method");
        if (inputMethodManager == null || !((DialogEditBinding) this.mViewBinding).etContent.isFocused()) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(((DialogEditBinding) this.mViewBinding).etContent.getWindowToken(), 0);
        ((DialogEditBinding) this.mViewBinding).etContent.clearFocus();
    }

    public EditDialog setContent(String content) {
        this.content.set(content);
        return this;
    }

    public EditDialog setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public EditDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public EditDialog setInputType(int inputType) {
        this.inputType = inputType;
        return this;
    }

    public EditDialog setConfirmAction(IAction<String> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    public EditDialog setInputEmptyTip(String inputEmptyTip) {
        this.inputEmptyTip = inputEmptyTip;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "edit_dialog";
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}