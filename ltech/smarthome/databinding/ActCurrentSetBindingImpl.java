package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class ActCurrentSetBindingImpl extends ActCurrentSetBinding {
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
        sparseIntArray.put(R.id.layout_current_set, 8);
        sparseIntArray.put(R.id.tv_output_param, 9);
        sparseIntArray.put(R.id.tv_unit, 10);
    }

    public ActCurrentSetBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActCurrentSetBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[7], (LinearLayout) bindings[8], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3], (AppCompatEditText) bindings[6], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[10]);
        this.mDirtyFlags = -1L;
        this.ivMinus.setTag(null);
        this.ivPlus.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCancel.setTag(null);
        this.tvConfirm.setTag(null);
        this.tvCurrent.setTag(null);
        this.tvSetTitle.setTag(null);
        this.tvTitle.setTag(null);
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
        if (17 == variableId) {
            setCurrent((String) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActCurrentSetBinding
    public void setCurrent(String Current) {
        this.mCurrent = Current;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(17);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCurrentSetBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
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
        String str = this.mCurrent;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        long j2 = 5 & j;
        if ((6 & j) != 0) {
            ViewAdapter.onClickCommand(this.ivMinus, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivPlus, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvConfirm, bindingCommand, false);
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.tvCurrent, str);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvCurrent, true);
            ViewAdapter.setTextBold(this.tvSetTitle, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}