package com.ltech.smarthome.view.dialog;

import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogLoadingBinding;

/* loaded from: classes4.dex */
public class LoadingDialog extends SmartDialog<DialogLoadingBinding> {
    @Override // com.ltech.smarthome.base.BaseDialog
    public int initTheme() {
        return R.style.LoadingDialog;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_loading;
    }

    public static LoadingDialog asDefault(final String content) {
        return (LoadingDialog) new LoadingDialog().setViewConverter(new SmartDialog.ViewConverter<DialogLoadingBinding, LoadingDialog>() { // from class: com.ltech.smarthome.view.dialog.LoadingDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogLoadingBinding viewBinding, LoadingDialog dialog) {
                dialog.getDialog().getWindow().setType(2038);
                viewBinding.tvContent.setText(content);
            }
        }).setOutCancel(false).setWidth(-1).setGravity(17);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "loading_dialog";
    }
}