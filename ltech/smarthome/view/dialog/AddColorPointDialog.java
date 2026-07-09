package com.ltech.smarthome.view.dialog;

import android.text.TextUtils;
import android.view.View;
import androidx.databinding.ObservableField;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogAddColorPointBinding;
import com.ltech.smarthome.utils.SmartToast;
import com.ltech.smarthome.utils.cmd_assistant.CmdAssistant;
import com.ltech.smarthome.view.StepSetView;
import com.ltech.smarthome.view.dialog.AddColorPointDialog;

/* loaded from: classes4.dex */
public class AddColorPointDialog extends SmartDialog<DialogAddColorPointBinding> {
    private Object controlObject;
    private float duv;
    private OnSaveListener onSaveListener;
    private String title;
    private ObservableField<String> content = new ObservableField<>("");
    private int K = 1000;

    public interface OnSaveListener {
        void onSave(String name, float duv);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_add_color_point;
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return getClass().getSimpleName();
    }

    /* renamed from: com.ltech.smarthome.view.dialog.AddColorPointDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogAddColorPointBinding, AddColorPointDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogAddColorPointBinding viewBinding, final AddColorPointDialog dialog) {
            viewBinding.tvTitle.setText(dialog.title);
            viewBinding.setContent(dialog.content);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.AddColorPointDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    AddColorPointDialog.AnonymousClass1.lambda$convertView$0(AddColorPointDialog.this, (View) obj);
                }
            }));
            viewBinding.tvCt.setText(dialog.K + "K");
            viewBinding.seekbarDuv.setRange(-20, 20);
            viewBinding.seekbarDuv.setOnProgressChangeListener(new StepSetView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.AddColorPointDialog$1$$ExternalSyntheticLambda1
                @Override // com.ltech.smarthome.view.StepSetView.OnProgressChangeListener
                public final void onProgressChanged(int i, boolean z, boolean z2) {
                    AddColorPointDialog.AnonymousClass1.lambda$convertView$1(AddColorPointDialog.this, viewBinding, i, z, z2);
                }
            });
            viewBinding.seekbarDuv.setProgress((int) (dialog.duv * 1000.0f));
            viewBinding.seekbarDuv.setValue(String.format("%.3f", Float.valueOf(dialog.duv)));
        }

        static /* synthetic */ void lambda$convertView$0(AddColorPointDialog addColorPointDialog, View view) {
            int id = view.getId();
            if (id == R.id.iv_clear) {
                addColorPointDialog.content.set("");
                return;
            }
            if (id == R.id.tv_cancel) {
                addColorPointDialog.dismissDialog();
                return;
            }
            if (id != R.id.tv_save) {
                return;
            }
            if (TextUtils.isEmpty((CharSequence) addColorPointDialog.content.get())) {
                SmartToast.showShort(R.string.input_name);
                return;
            }
            if (addColorPointDialog.onSaveListener != null) {
                addColorPointDialog.onSaveListener.onSave(((String) addColorPointDialog.content.get()).trim(), addColorPointDialog.duv);
            }
            addColorPointDialog.dismissDialog();
        }

        static /* synthetic */ void lambda$convertView$1(AddColorPointDialog addColorPointDialog, DialogAddColorPointBinding dialogAddColorPointBinding, int i, boolean z, boolean z2) {
            addColorPointDialog.duv = i * 0.001f;
            dialogAddColorPointBinding.seekbarDuv.setValue(String.format("%.3f", Float.valueOf(addColorPointDialog.duv)));
            if (z2) {
                addColorPointDialog.sendCCT(z);
            }
        }
    }

    public static AddColorPointDialog asDefault() {
        return (AddColorPointDialog) new AddColorPointDialog().setViewConverter(new AnonymousClass1()).setMargin(16).setY(16).setGravity(80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendCCT(boolean finish) {
        CmdAssistant.getLightCmdAssistant(this.controlObject, new int[0]).sendCCT(getContext(), this.K, (int) (this.duv * 10000.0f), 255, finish);
    }

    public AddColorPointDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public AddColorPointDialog setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        return this;
    }

    public AddColorPointDialog setControlObject(Object object) {
        this.controlObject = object;
        return this;
    }

    public AddColorPointDialog setK(int k) {
        this.K = k;
        return this;
    }
}