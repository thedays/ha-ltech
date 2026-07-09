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
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class DialogSetScreenDisplayBindingImpl extends DialogSetScreenDisplayBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_top, 4);
        sparseIntArray.put(R.id.tabs_pattern, 5);
        sparseIntArray.put(R.id.layout_content, 6);
        sparseIntArray.put(R.id.layout_input, 7);
        sparseIntArray.put(R.id.input_edtTxt, 8);
        sparseIntArray.put(R.id.tabs_type, 9);
        sparseIntArray.put(R.id.viewpager, 10);
    }

    public DialogSetScreenDisplayBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private DialogSetScreenDisplayBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[1], (AppCompatEditText) bindings[8], (AppCompatImageView) bindings[2], (LinearLayout) bindings[0], (ConstraintLayout) bindings[6], (LinearLayout) bindings[7], (LinearLayout) bindings[4], (AppCompatTextView) bindings[3], (TabLayout) bindings[5], (TabLayout) bindings[9], (ViewPager2) bindings[10]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView33.setTag(null);
        this.ivClean.setTag(null);
        this.layoutBg.setTag(null);
        this.saveBtn.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.DialogSetScreenDisplayBinding
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
            ViewAdapter.setTextBold(this.appCompatTextView33, true);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.ivClean, bindingCommand, false);
            ViewAdapter.onClickCommand(this.saveBtn, bindingCommand, false);
        }
    }
}