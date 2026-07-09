package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class DialogWyBindingImpl extends DialogWyBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

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
        sparseIntArray.put(R.id.view33, 5);
        sparseIntArray.put(R.id.sb_rgb_on, 6);
        sparseIntArray.put(R.id.sb_wy_on, 7);
        sparseIntArray.put(R.id.csb_ct_bar, 8);
        sparseIntArray.put(R.id.tv_ct, 9);
        sparseIntArray.put(R.id.tv_brt, 10);
        sparseIntArray.put(R.id.sb_brt, 11);
        sparseIntArray.put(R.id.group_wy, 12);
    }

    public DialogWyBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private DialogWyBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ColorSeekBar) bindings[8], (Group) bindings[12], (LightBrtBar) bindings[11], (SwitchButton) bindings[6], (SwitchButton) bindings[7], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (View) bindings[5]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvBrtTip.setTag(null);
        this.tvCtTip.setTag(null);
        this.tvRgbOnOff.setTag(null);
        this.tvWyOnOff.setTag(null);
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
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        if ((j & 1) != 0) {
            ViewAdapter.setTextBold(this.tvBrtTip, true);
            ViewAdapter.setTextBold(this.tvCtTip, true);
            ViewAdapter.setTextBold(this.tvRgbOnOff, true);
            ViewAdapter.setTextBold(this.tvWyOnOff, true);
        }
    }
}