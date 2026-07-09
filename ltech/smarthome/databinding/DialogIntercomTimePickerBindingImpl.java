package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class DialogIntercomTimePickerBindingImpl extends DialogIntercomTimePickerBinding {
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
        sparseIntArray.put(R.id.pick_view_month, 4);
        sparseIntArray.put(R.id.pick_view_day, 5);
        sparseIntArray.put(R.id.pick_view_hour, 6);
        sparseIntArray.put(R.id.pick_view_min, 7);
    }

    public DialogIntercomTimePickerBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private DialogIntercomTimePickerBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (RecyclerView) bindings[5], (RecyclerView) bindings[6], (RecyclerView) bindings[7], (RecyclerView) bindings[4], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCancel.setTag(null);
        this.tvFinish.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        if (66 == variableId) {
            setSecUnit((String) variable);
            return true;
        }
        if (96 == variableId) {
            setWithUnit((Boolean) variable);
            return true;
        }
        if (44 == variableId) {
            setMinUnit((String) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogIntercomTimePickerBinding
    public void setSecUnit(String SecUnit) {
        this.mSecUnit = SecUnit;
    }

    @Override // com.ltech.smarthome.databinding.DialogIntercomTimePickerBinding
    public void setWithUnit(Boolean WithUnit) {
        this.mWithUnit = WithUnit;
    }

    @Override // com.ltech.smarthome.databinding.DialogIntercomTimePickerBinding
    public void setMinUnit(String MinUnit) {
        this.mMinUnit = MinUnit;
    }

    @Override // com.ltech.smarthome.databinding.DialogIntercomTimePickerBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
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
        long j2 = 24 & j;
        if ((j & 16) != 0) {
            ViewAdapter.setTextBold(this.tvCancel, true);
            ViewAdapter.setTextBold(this.tvFinish, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvFinish, bindingCommand, false);
        }
    }
}