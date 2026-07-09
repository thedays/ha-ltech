package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.databinding.DialogLoadingProgressBinding;

/* loaded from: classes4.dex */
public class LoadingProgressDialog extends SmartDialog<DialogLoadingProgressBinding> {
    private CallBack callBack;
    private int max;
    private int progress;

    public interface CallBack {
        void onCancel();
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    public int initTheme() {
        return R.style.LoadingDialog;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_loading_progress;
    }

    public static LoadingProgressDialog asDefault(String content) {
        return asDefault(content, false);
    }

    public static LoadingProgressDialog asDefault(final String content, final boolean showCancel) {
        return (LoadingProgressDialog) new LoadingProgressDialog().setViewConverter(new SmartDialog.ViewConverter<DialogLoadingProgressBinding, LoadingProgressDialog>() { // from class: com.ltech.smarthome.view.dialog.LoadingProgressDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
            public void convertView(DialogLoadingProgressBinding viewBinding, final LoadingProgressDialog dialog) {
                viewBinding.tvContent.setText(content);
                viewBinding.donutProgress.setProgress(dialog.progress);
                int ceil = (int) Math.ceil((dialog.progress * 100.0f) / dialog.max);
                viewBinding.donutProgress.setText(ceil + "%");
                if (showCancel) {
                    viewBinding.layoutCancel.setVisibility(0);
                    viewBinding.layoutCancel.setOnClickListener(new View.OnClickListener(this) { // from class: com.ltech.smarthome.view.dialog.LoadingProgressDialog.1.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            if (dialog.callBack != null) {
                                dialog.callBack.onCancel();
                            }
                        }
                    });
                }
            }
        }).setOutCancel(false).setWidth(-1).setGravity(17);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "LoadingProgressDialog";
    }

    public void setProgress(int i) {
        this.progress = i;
        if (isAdded()) {
            notifyDialog();
        }
    }

    public LoadingProgressDialog setMax(int m2) {
        this.max = m2;
        return this;
    }

    public LoadingProgressDialog setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }
}