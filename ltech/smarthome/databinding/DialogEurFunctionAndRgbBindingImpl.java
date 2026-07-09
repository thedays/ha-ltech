package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.SwitchSeekBarView;

/* loaded from: classes3.dex */
public class DialogEurFunctionAndRgbBindingImpl extends DialogEurFunctionAndRgbBinding {
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
        sparseIntArray.put(R.id.tv_title, 1);
        sparseIntArray.put(R.id.line, 2);
        sparseIntArray.put(R.id.switch_seekbar_rgb_brt, 3);
        sparseIntArray.put(R.id.switch_seekbar_r, 4);
        sparseIntArray.put(R.id.switch_seekbar_g, 5);
        sparseIntArray.put(R.id.switch_seekbar_b, 6);
        sparseIntArray.put(R.id.line_2, 7);
        sparseIntArray.put(R.id.tv_second_title, 8);
        sparseIntArray.put(R.id.sb_function, 9);
        sparseIntArray.put(R.id.tv_first, 10);
        sparseIntArray.put(R.id.seekbar, 11);
        sparseIntArray.put(R.id.tv_value, 12);
        sparseIntArray.put(R.id.tv_second, 13);
        sparseIntArray.put(R.id.seekbar_second, 14);
        sparseIntArray.put(R.id.tv_value_second, 15);
        sparseIntArray.put(R.id.group_second, 16);
    }

    public DialogEurFunctionAndRgbBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }

    private DialogEurFunctionAndRgbBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Group) bindings[16], (View) bindings[2], (View) bindings[7], (SwitchButton) bindings[9], (LightBrtBar) bindings[11], (AppCompatSeekBar) bindings[14], (SwitchSeekBarView) bindings[6], (SwitchSeekBarView) bindings[5], (SwitchSeekBarView) bindings[4], (SwitchSeekBarView) bindings[3], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[15]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.DialogEurFunctionAndRgbBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}