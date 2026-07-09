package com.ltech.smarthome.view.dialog;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.IAction;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogNumberEditBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.view.dialog.EditNumberDialog;

/* loaded from: classes4.dex */
public class EditNumberDialog extends SmartDialog<DialogNumberEditBinding> {
    private IAction<Integer> confirmAction;
    private String errorTip;
    private String hint;
    private DialogNumberEditBinding mViewBinding;
    private int maxValue;
    private int minValue;
    private String title;
    private boolean canSave = true;
    private ObservableField<String> content = new ObservableField<>();

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_number_edit;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.EditNumberDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogNumberEditBinding, EditNumberDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogNumberEditBinding viewBinding, final EditNumberDialog dialog) {
            dialog.mViewBinding = viewBinding;
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setContent(dialog.content);
            viewBinding.etContent.setHint(dialog.hint);
            viewBinding.tvError.setText(dialog.errorTip);
            viewBinding.etContent.addTextChangedListener(new TextWatcher(this) { // from class: com.ltech.smarthome.view.dialog.EditNumberDialog.1.1
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable s) {
                    if (TextUtils.isEmpty(s)) {
                        return;
                    }
                    int parseInt = Integer.parseInt(s.toString());
                    if (parseInt < dialog.minValue || parseInt > dialog.maxValue) {
                        viewBinding.etContent.setTextColor(ContextCompat.getColor(dialog.requireContext(), R.color.color_text_red));
                        viewBinding.tvError.setVisibility(0);
                        dialog.canSave = false;
                    } else {
                        viewBinding.etContent.setTextColor(ContextCompat.getColor(dialog.requireContext(), R.color.color_text_black));
                        viewBinding.tvError.setVisibility(8);
                        dialog.canSave = true;
                    }
                }
            });
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.EditNumberDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    EditNumberDialog.AnonymousClass1.lambda$convertView$0(EditNumberDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(EditNumberDialog editNumberDialog, View view) {
            int id = view.getId();
            if (id == R.id.iv_delete) {
                editNumberDialog.setContent("");
                return;
            }
            if (id != R.id.tv_ok) {
                if (id == R.id.tv_cancel) {
                    editNumberDialog.hideKeyboard();
                    editNumberDialog.dismissDialog();
                    return;
                }
                return;
            }
            if (editNumberDialog.canSave) {
                String str = (String) editNumberDialog.content.get();
                if (str == null || TextUtils.isEmpty(str.trim())) {
                    SmartToast.showShort(R.string.please_input);
                    return;
                }
                editNumberDialog.hideKeyboard();
                editNumberDialog.dismissDialog();
                if (editNumberDialog.confirmAction != null) {
                    editNumberDialog.confirmAction.act(Integer.valueOf(Integer.parseInt(str)));
                }
            }
        }
    }

    public static EditNumberDialog asDefault() {
        return (EditNumberDialog) new EditNumberDialog().setViewConverter(new AnonymousClass1()).setGravity(17).setAnimStyle(R.style.iOSDialogAnimStyle).setOutCancel(false).setMargin(40);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getDialog().getContext().getSystemService("input_method");
        if (inputMethodManager == null || !this.mViewBinding.etContent.isFocused()) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(this.mViewBinding.etContent.getWindowToken(), 0);
        this.mViewBinding.etContent.clearFocus();
    }

    public EditNumberDialog setContent(String content) {
        this.content.set(content);
        return this;
    }

    public EditNumberDialog setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public EditNumberDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public EditNumberDialog setErrorTip(String errorTip) {
        this.errorTip = errorTip;
        return this;
    }

    public EditNumberDialog setRange(int min, int max) {
        this.minValue = min;
        this.maxValue = max;
        return this;
    }

    public EditNumberDialog setConfirmAction(IAction<Integer> confirmAction) {
        this.confirmAction = confirmAction;
        return this;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }
}