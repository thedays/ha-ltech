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

/* loaded from: classes3.dex */
public class ViewDeviceManageBottomBindingImpl extends ViewDeviceManageBottomBinding {
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
        sparseIntArray.put(R.id.btn_cancel, 1);
        sparseIntArray.put(R.id.btn_find_device, 2);
        sparseIntArray.put(R.id.btn_start_locate, 3);
        sparseIntArray.put(R.id.iv_start_locate, 4);
        sparseIntArray.put(R.id.tv_start_locate, 5);
        sparseIntArray.put(R.id.btn_change_room, 6);
        sparseIntArray.put(R.id.iv_change_room, 7);
        sparseIntArray.put(R.id.tv_change_room, 8);
    }

    public ViewDeviceManageBottomBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private ViewDeviceManageBottomBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (LinearLayout) bindings[1], (LinearLayout) bindings[6], (LinearLayout) bindings[2], (LinearLayout) bindings[3], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[4], (ConstraintLayout) bindings[0], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[5]);
        this.mDirtyFlags = -1L;
        this.layoutDeviceManage.setTag(null);
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