package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class FtTvNumBindingImpl extends FtTvNumBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.guideline3, 11);
    }

    public FtTvNumBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private FtTvNumBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Guideline) bindings[11], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[9]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tv0.setTag(null);
        this.tv1.setTag(null);
        this.tv2.setTag(null);
        this.tv3.setTag(null);
        this.tv4.setTag(null);
        this.tv5.setTag(null);
        this.tv6.setTag(null);
        this.tv7.setTag(null);
        this.tv8.setTag(null);
        this.tv9.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtTvNumBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        long j2 = 3 & j;
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tv0, true);
            ViewAdapter.setTextBold(this.tv1, true);
            ViewAdapter.setTextBold(this.tv2, true);
            ViewAdapter.setTextBold(this.tv3, true);
            ViewAdapter.setTextBold(this.tv4, true);
            ViewAdapter.setTextBold(this.tv5, true);
            ViewAdapter.setTextBold(this.tv6, true);
            ViewAdapter.setTextBold(this.tv7, true);
            ViewAdapter.setTextBold(this.tv8, true);
            ViewAdapter.setTextBold(this.tv9, true);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.tv0, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv1, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv2, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv3, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv4, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv5, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv6, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv7, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv8, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tv9, bindingCommand, false);
        }
    }
}