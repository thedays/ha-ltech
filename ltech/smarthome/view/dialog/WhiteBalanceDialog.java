package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogWhiteBalanceBinding;
import com.ltech.smarthome.utils.LightUtils;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.dialog.WhiteBalanceDialog;

/* loaded from: classes4.dex */
public final class WhiteBalanceDialog extends SmartDialog<DialogWhiteBalanceBinding> {
    private Object controlObject;
    private SelectListener mSelectListener;
    private int r = 255;
    private int g = 255;

    /* renamed from: b, reason: collision with root package name */
    private int f6283b = 255;

    public interface SelectListener {
        void confirm(int r, int g, int b2);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_white_balance;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.WhiteBalanceDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogWhiteBalanceBinding, WhiteBalanceDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(DialogWhiteBalanceBinding viewBinding, final WhiteBalanceDialog dialog) {
            dialog.initView(viewBinding);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.WhiteBalanceDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    WhiteBalanceDialog.AnonymousClass1.lambda$convertView$0(WhiteBalanceDialog.this, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(WhiteBalanceDialog whiteBalanceDialog, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                whiteBalanceDialog.dismissDialog();
            } else {
                if (id != R.id.tv_finish) {
                    return;
                }
                if (whiteBalanceDialog.mSelectListener != null) {
                    whiteBalanceDialog.mSelectListener.confirm(whiteBalanceDialog.r, whiteBalanceDialog.g, whiteBalanceDialog.f6283b);
                }
                whiteBalanceDialog.dismissDialog();
            }
        }
    }

    public static WhiteBalanceDialog asDefault() {
        return (WhiteBalanceDialog) new WhiteBalanceDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setOutCancel(true).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(final DialogWhiteBalanceBinding viewBinding) {
        viewBinding.layoutR.setRange(1, 100);
        viewBinding.layoutR.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.WhiteBalanceDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                WhiteBalanceDialog.this.lambda$initView$0(viewBinding, i, z, z2);
            }
        });
        viewBinding.layoutR.setProgress(LightUtils.brt2Progress(this.r));
        viewBinding.layoutG.setRange(1, 100);
        viewBinding.layoutG.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.WhiteBalanceDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                WhiteBalanceDialog.this.lambda$initView$1(viewBinding, i, z, z2);
            }
        });
        viewBinding.layoutG.setProgress(LightUtils.brt2Progress(this.g));
        viewBinding.layoutB.setRange(1, 100);
        viewBinding.layoutB.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.WhiteBalanceDialog$$ExternalSyntheticLambda2
            @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
            public final void onProgressChanged(int i, boolean z, boolean z2) {
                WhiteBalanceDialog.this.lambda$initView$2(viewBinding, i, z, z2);
            }
        });
        viewBinding.layoutB.setProgress(LightUtils.brt2Progress(this.f6283b));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(DialogWhiteBalanceBinding dialogWhiteBalanceBinding, int i, boolean z, boolean z2) {
        this.r = LightUtils.progress2Brt(i);
        dialogWhiteBalanceBinding.layoutR.setValue(i + "%");
        if (z2) {
            sendPreview(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(DialogWhiteBalanceBinding dialogWhiteBalanceBinding, int i, boolean z, boolean z2) {
        this.g = LightUtils.progress2Brt(i);
        dialogWhiteBalanceBinding.layoutG.setValue(i + "%");
        if (z2) {
            sendPreview(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$2(DialogWhiteBalanceBinding dialogWhiteBalanceBinding, int i, boolean z, boolean z2) {
        this.f6283b = LightUtils.progress2Brt(i);
        dialogWhiteBalanceBinding.layoutB.setValue(i + "%");
        if (z2) {
            sendPreview(z);
        }
    }

    private void sendPreview(boolean finish) {
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).previewWhiteBalance(getContext(), this.r, this.g, this.f6283b, finish);
    }

    public WhiteBalanceDialog setSelectListener(SelectListener selectListener) {
        this.mSelectListener = selectListener;
        return this;
    }

    public WhiteBalanceDialog setControlObject(Object controlObject) {
        this.controlObject = controlObject;
        return this;
    }

    public WhiteBalanceDialog setValue(int value) {
        if (value != 0) {
            this.r = (value >> 16) & 255;
            this.g = (value >> 8) & 255;
            this.f6283b = value & 255;
        }
        return this;
    }
}