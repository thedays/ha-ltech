package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectKDuvBinding;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.dialog.SelectKDuvDialog;

/* loaded from: classes4.dex */
public class SelectKDuvDialog extends SmartDialog<DialogSelectKDuvBinding> {
    private int K = 1000;
    private Object controlObject;
    private int duv;
    private OnSaveListener onSaveListener;
    private String title;

    public interface OnSaveListener {
        void onSave(int K, int duv);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_k_duv;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectKDuvDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectKDuvBinding, SelectKDuvDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSelectKDuvBinding viewBinding, final SelectKDuvDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectKDuvDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectKDuvDialog.AnonymousClass1.lambda$convertView$0(SelectKDuvDialog.this, (View) obj);
                }
            }));
            viewBinding.seekbarK.setRange(1000, 20000);
            viewBinding.seekbarK.setStep(50);
            viewBinding.seekbarK.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectKDuvDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
                public final void onProgressChanged(int i, boolean z, boolean z2) {
                    SelectKDuvDialog.AnonymousClass1.lambda$convertView$1(SelectKDuvDialog.this, viewBinding, i, z, z2);
                }
            });
            viewBinding.seekbarK.setProgress(dialog.K);
            viewBinding.seekbarK.setValue(dialog.K + "K");
            viewBinding.seekbarDuv.setRange(-20, 20);
            viewBinding.seekbarDuv.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectKDuvDialog$1$$ExternalSyntheticLambda2
                @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
                public final void onProgressChanged(int i, boolean z, boolean z2) {
                    SelectKDuvDialog.AnonymousClass1.lambda$convertView$2(SelectKDuvDialog.this, viewBinding, i, z, z2);
                }
            });
            viewBinding.seekbarDuv.setProgress(dialog.duv);
            viewBinding.seekbarDuv.setValue(String.format("%.3f", Float.valueOf(dialog.duv * 0.001f)));
        }

        static /* synthetic */ void lambda$convertView$0(SelectKDuvDialog selectKDuvDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectKDuvDialog.dismissDialog();
            } else {
                if (id != R.id.tv_confirm) {
                    return;
                }
                if (selectKDuvDialog.onSaveListener != null) {
                    selectKDuvDialog.onSaveListener.onSave(selectKDuvDialog.K, selectKDuvDialog.duv);
                }
                selectKDuvDialog.dismissDialog();
            }
        }

        static /* synthetic */ void lambda$convertView$1(SelectKDuvDialog selectKDuvDialog, DialogSelectKDuvBinding dialogSelectKDuvBinding, int i, boolean z, boolean z2) {
            selectKDuvDialog.K = i;
            dialogSelectKDuvBinding.seekbarK.setValue(i + "K");
            if (z2) {
                selectKDuvDialog.sendCCT(z);
            }
        }

        static /* synthetic */ void lambda$convertView$2(SelectKDuvDialog selectKDuvDialog, DialogSelectKDuvBinding dialogSelectKDuvBinding, int i, boolean z, boolean z2) {
            selectKDuvDialog.duv = i;
            dialogSelectKDuvBinding.seekbarDuv.setValue(String.format("%.3f", Float.valueOf(selectKDuvDialog.duv * 0.001f)));
            if (z2) {
                selectKDuvDialog.sendCCT(z);
            }
        }
    }

    public static SelectKDuvDialog asDefault() {
        return (SelectKDuvDialog) new SelectKDuvDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCCT(boolean finish) {
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).sendCCT(getContext(), this.K, (int) (this.duv * 0.001f * 10000.0f), 255, finish);
    }

    public SelectKDuvDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public SelectKDuvDialog setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        return this;
    }

    public SelectKDuvDialog setControlObject(Object object) {
        this.controlObject = object;
        return this;
    }

    public SelectKDuvDialog setK(int k) {
        this.K = k;
        return this;
    }

    public SelectKDuvDialog setDuv(int duv) {
        this.duv = duv;
        return this;
    }
}