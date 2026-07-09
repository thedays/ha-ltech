package com.ltech.smarthome.view.dialog;

import android.view.View;
import com.ltech.smarthome.R;
import com.ltech.smarthome.base.SmartDialog;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.command.BindingConsumer;
import com.ltech.smarthome.databinding.DialogSelectDimRangeBinding;
import com.ltech.smarthome.ltnfc.utils.BrightUtils;
import com.ltech.smarthome.view.DaliTextSeekBarView;
import com.ltech.smarthome.view.dialog.SelectDimRangeDialog;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class SelectDimRangeDialog extends SmartDialog<DialogSelectDimRangeBinding> {
    private int high;
    private int low;
    private OnSaveListener onSaveListener;
    private int curveType = 1;
    private int minProgress = 0;
    private int maxProgress = 0;

    public interface OnSaveListener {
        void onSave(int low, int high);
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected int provideLayoutId() {
        return R.layout.dialog_select_dim_range;
    }

    /* renamed from: com.ltech.smarthome.view.dialog.SelectDimRangeDialog$1, reason: invalid class name */
    class AnonymousClass1 extends SmartDialog.ViewConverter<DialogSelectDimRangeBinding, SelectDimRangeDialog> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.ltech.smarthome.base.SmartDialog.ViewConverter
        public void convertView(final DialogSelectDimRangeBinding viewBinding, final SelectDimRangeDialog dialog) {
            dialog.initView(viewBinding);
            viewBinding.setClickCommand(new BindingCommand<>(new BindingConsumer() { // from class: com.ltech.smarthome.view.dialog.SelectDimRangeDialog$1$$ExternalSyntheticLambda0
                @Override // com.ltech.smarthome.binding.command.BindingConsumer
                public final void call(Object obj) {
                    SelectDimRangeDialog.AnonymousClass1.lambda$convertView$0(SelectDimRangeDialog.this, viewBinding, (View) obj);
                }
            }));
        }

        static /* synthetic */ void lambda$convertView$0(SelectDimRangeDialog selectDimRangeDialog, DialogSelectDimRangeBinding dialogSelectDimRangeBinding, View view) {
            int id = view.getId();
            if (id == R.id.tv_cancel) {
                selectDimRangeDialog.dismissDialog();
            } else {
                if (id != R.id.tv_save) {
                    return;
                }
                if (selectDimRangeDialog.onSaveListener != null) {
                    selectDimRangeDialog.onSaveListener.onSave(dialogSelectDimRangeBinding.seekbarMinBtr.getProgress(), dialogSelectDimRangeBinding.seekbarMaxBtr.getProgress());
                }
                selectDimRangeDialog.dismissDialog();
            }
        }
    }

    public static SelectDimRangeDialog asDefault() {
        return (SelectDimRangeDialog) new SelectDimRangeDialog().setViewConverter(new AnonymousClass1()).setGravity(80).setMargin(16).setY(16).setOutCancel(false);
    }

    public SelectDimRangeDialog setCurveType(int type) {
        this.curveType = type;
        return this;
    }

    public SelectDimRangeDialog setLowPos(int low) {
        this.low = low;
        return this;
    }

    public SelectDimRangeDialog setHighPos(int high) {
        this.high = high;
        return this;
    }

    public SelectDimRangeDialog setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
        return this;
    }

    public SelectDimRangeDialog setRange(int min, int max) {
        this.minProgress = min;
        this.maxProgress = max;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView(final DialogSelectDimRangeBinding viewBinding) {
        ArrayList arrayList;
        int i = this.curveType;
        if (i == -1) {
            arrayList = new ArrayList();
        } else if (i == 1) {
            arrayList = new ArrayList(BrightUtils.getLogPercent().values());
        } else {
            arrayList = new ArrayList(BrightUtils.getLinnerPercent().values());
        }
        if (this.minProgress != 0 || this.maxProgress != 0) {
            viewBinding.seekbarMinBtr.setRange(this.minProgress, this.maxProgress);
            viewBinding.seekbarMaxBtr.setRange(this.minProgress, this.maxProgress);
        }
        viewBinding.seekbarMinBtr.setList(arrayList);
        viewBinding.seekbarMaxBtr.setList(arrayList);
        viewBinding.seekbarMinBtr.setValueAlignRight();
        viewBinding.seekbarMaxBtr.setValueAlignRight();
        viewBinding.seekbarMinBtr.setProgress(this.low);
        viewBinding.seekbarMaxBtr.setProgress(this.high);
        viewBinding.seekbarMinBtr.setOnProgressChangeListener(new DaliTextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectDimRangeDialog$$ExternalSyntheticLambda0
            @Override // com.ltech.smarthome.view.DaliTextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i2, boolean z) {
                SelectDimRangeDialog.lambda$initView$0(DialogSelectDimRangeBinding.this, i2, z);
            }
        });
        viewBinding.seekbarMaxBtr.setOnProgressChangeListener(new DaliTextSeekBarView.OnProgressChangeListener() { // from class: com.ltech.smarthome.view.dialog.SelectDimRangeDialog$$ExternalSyntheticLambda1
            @Override // com.ltech.smarthome.view.DaliTextSeekBarView.OnProgressChangeListener
            public final void onProgressChanged(int i2, boolean z) {
                SelectDimRangeDialog.lambda$initView$1(DialogSelectDimRangeBinding.this, i2, z);
            }
        });
    }

    static /* synthetic */ void lambda$initView$0(DialogSelectDimRangeBinding dialogSelectDimRangeBinding, int i, boolean z) {
        if (i > dialogSelectDimRangeBinding.seekbarMaxBtr.getProgress()) {
            dialogSelectDimRangeBinding.seekbarMinBtr.setProgress(dialogSelectDimRangeBinding.seekbarMaxBtr.getProgress());
        }
    }

    static /* synthetic */ void lambda$initView$1(DialogSelectDimRangeBinding dialogSelectDimRangeBinding, int i, boolean z) {
        if (i < dialogSelectDimRangeBinding.seekbarMinBtr.getProgress()) {
            dialogSelectDimRangeBinding.seekbarMaxBtr.setProgress(dialogSelectDimRangeBinding.seekbarMinBtr.getProgress());
        }
    }

    @Override // com.ltech.smarthome.base.BaseDialog
    protected String tag() {
        return "dialog_select_dim_range";
    }
}