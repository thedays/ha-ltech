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
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.ColorPickerView;
import com.ltech.smarthome.view.HorizontalSeekBar;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class DialogSelectColorBindingImpl extends DialogSelectColorBinding {
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
        sparseIntArray.put(R.id.v_color, 4);
        sparseIntArray.put(R.id.tv_color_1, 5);
        sparseIntArray.put(R.id.et_color_1, 6);
        sparseIntArray.put(R.id.tv_color_2, 7);
        sparseIntArray.put(R.id.et_color_2, 8);
        sparseIntArray.put(R.id.tv_color_3, 9);
        sparseIntArray.put(R.id.et_color_3, 10);
        sparseIntArray.put(R.id.cpv, 11);
        sparseIntArray.put(R.id.view19, 12);
        sparseIntArray.put(R.id.rv_color, 13);
        sparseIntArray.put(R.id.tv_brt_tip, 14);
        sparseIntArray.put(R.id.sb_brt, 15);
        sparseIntArray.put(R.id.tv_w_tip, 16);
        sparseIntArray.put(R.id.sb_w, 17);
    }

    public DialogSelectColorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }

    private DialogSelectColorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[2], (ColorPickerView) bindings[11], (ColorEditText) bindings[6], (ColorEditText) bindings[8], (ColorEditText) bindings[10], (RecyclerView) bindings[13], (LightBrtBar) bindings[15], (HorizontalSeekBar) bindings[17], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[16], (View) bindings[4], (View) bindings[12]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView26.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCancel.setTag(null);
        this.tvConfirm.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.DialogSelectColorBinding
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
            ViewAdapter.setTextBold(this.appCompatTextView26, true);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvConfirm, bindingCommand, false);
        }
    }
}