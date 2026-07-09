package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class DialogSelectDimCurveBindingImpl extends DialogSelectDimCurveBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_log, 6);
        sparseIntArray.put(R.id.iv_log_select, 7);
        sparseIntArray.put(R.id.tv_log, 8);
        sparseIntArray.put(R.id.iv_linear, 9);
        sparseIntArray.put(R.id.iv_linear_select, 10);
        sparseIntArray.put(R.id.tv_linear, 11);
    }

    public DialogSelectDimCurveBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private DialogSelectDimCurveBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatTextView) bindings[1], (AppCompatImageView) bindings[9], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[0], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[4], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView33.setTag(null);
        this.layoutBg.setTag(null);
        this.layoutLinear.setTag(null);
        this.layoutLog.setTag(null);
        this.tvCancel.setTag(null);
        this.tvSave.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (13 == variableId) {
            setContent((ObservableField) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogSelectDimCurveBinding
    public void setContent(ObservableField<String> Content) {
        this.mContent = Content;
    }

    @Override // com.ltech.smarthome.databinding.DialogSelectDimCurveBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeContent((ObservableField) object, fieldId);
    }

    private boolean onChangeContent(ObservableField<String> Content, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        long j2 = 6 & j;
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView33, true);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.layoutLinear, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutLog, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvSave, bindingCommand, false);
        }
    }
}