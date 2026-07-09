package com.ltech.smarthome.view.dialog;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogLoadingSuccessBinding;

/* loaded from: classes4.dex */
public class LoadingSuccessDialog extends SmartDialog<DialogLoadingSuccessBinding> {
    @Override // com.ltech.smarthome.base.BaseDialog
    public int initTheme() {
        return R.style.LoadingDialog;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_loading_success;
    }

    public static LoadingSuccessDialog asDefault(final String content) {
        return (LoadingSuccessDialog) new LoadingSuccessDialog().setViewConverter(new SmartDialog.ViewConverter<DialogLoadingSuccessBinding, LoadingSuccessDialog>() { // from class: com.ltech.smarthome.view.dialog.LoadingSuccessDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogLoadingSuccessBinding viewBinding, LoadingSuccessDialog dialog) {
                dialog.getDialog().getWindow().setType(2038);
                viewBinding.tvContent.setText(content);
            }
        }).setOutCancel(false).setWidth(-1).setGravity(17);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "loading_success_dialog";
    }
}