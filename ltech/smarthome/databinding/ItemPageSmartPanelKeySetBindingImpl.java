package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;

/* loaded from: classes3.dex */
public class ItemPageSmartPanelKeySetBindingImpl extends ItemPageSmartPanelKeySetBinding {
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
        sparseIntArray.put(R.id.tv_main, 1);
        sparseIntArray.put(R.id.tv, 2);
        sparseIntArray.put(R.id.tv_sub_text, 3);
        sparseIntArray.put(R.id.iv_go, 4);
        sparseIntArray.put(R.id.layout_display, 5);
        sparseIntArray.put(R.id.iv_display, 6);
        sparseIntArray.put(R.id.tv_display, 7);
        sparseIntArray.put(R.id.iv_go2, 8);
        sparseIntArray.put(R.id.layout_time, 9);
        sparseIntArray.put(R.id.tv_long_click_tip, 10);
        sparseIntArray.put(R.id.tv_time, 11);
        sparseIntArray.put(R.id.iv_go3, 12);
    }

    public ItemPageSmartPanelKeySetBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ItemPageSmartPanelKeySetBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[12], (RelativeLayout) bindings[5], (RelativeLayout) bindings[9], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[11]);
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