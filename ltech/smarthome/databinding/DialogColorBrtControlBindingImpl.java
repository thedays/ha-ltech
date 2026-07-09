package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.VerticalSeekBar;

/* loaded from: classes3.dex */
public class DialogColorBrtControlBindingImpl extends DialogColorBrtControlBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        return true;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.bar_brt1, 1);
        sparseIntArray.put(R.id.tv_brt1, 2);
        sparseIntArray.put(R.id.bar_ct, 3);
        sparseIntArray.put(R.id.tv_label3, 4);
        sparseIntArray.put(R.id.tv_label1, 5);
        sparseIntArray.put(R.id.tv_ct, 6);
        sparseIntArray.put(R.id.bar_brt2, 7);
        sparseIntArray.put(R.id.tv_label2, 8);
        sparseIntArray.put(R.id.tv_brt2, 9);
    }

    public DialogColorBrtControlBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }

    private DialogColorBrtControlBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (VerticalSeekBar) bindings[1], (VerticalSeekBar) bindings[7], (VerticalSeekBar) bindings[3], (ConstraintLayout) bindings[0], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[4]);
        this.mDirtyFlags = -1L;
        this.bg.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1L;
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
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}