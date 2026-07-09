package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.WaveView;

/* loaded from: classes3.dex */
public class ItemMusicDialogListBindingImpl extends ItemMusicDialogListBinding {
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
        sparseIntArray.put(R.id.ic_playing, 1);
        sparseIntArray.put(R.id.tv_number, 2);
        sparseIntArray.put(R.id.layout_main, 3);
        sparseIntArray.put(R.id.tv_main, 4);
        sparseIntArray.put(R.id.tv_sub, 5);
        sparseIntArray.put(R.id.layout_icon, 6);
        sparseIntArray.put(R.id.iv_favorite, 7);
        sparseIntArray.put(R.id.layout_sort_icon, 8);
    }

    public ItemMusicDialogListBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private ItemMusicDialogListBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (WaveView) bindings[1], (AppCompatImageView) bindings[7], (LinearLayout) bindings[6], (LinearLayout) bindings[3], (ConstraintLayout) bindings[0], (LinearLayout) bindings[8], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[5]);
        this.mDirtyFlags = -1L;
        this.layoutNormal.setTag(null);
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