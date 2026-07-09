package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.WaveView;

/* loaded from: classes3.dex */
public class DialogMatterQrcodeBindingImpl extends DialogMatterQrcodeBinding {
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
        sparseIntArray.put(R.id.bg, 2);
        sparseIntArray.put(R.id.tv_qrcode_tip, 3);
        sparseIntArray.put(R.id.iv_qr_code_bg, 4);
        sparseIntArray.put(R.id.iv_qr_code, 5);
        sparseIntArray.put(R.id.tv_copy, 6);
        sparseIntArray.put(R.id.tv_refresh, 7);
        sparseIntArray.put(R.id.iv_refresh, 8);
        sparseIntArray.put(R.id.tv_refresh_2, 9);
        sparseIntArray.put(R.id.view_close, 10);
        sparseIntArray.put(R.id.progress, 11);
        sparseIntArray.put(R.id.group_loading, 12);
        sparseIntArray.put(R.id.group_fail, 13);
        sparseIntArray.put(R.id.group_success, 14);
    }

    public DialogMatterQrcodeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private DialogMatterQrcodeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (View) bindings[2], (Group) bindings[13], (Group) bindings[12], (Group) bindings[14], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[8], (WaveView) bindings[11], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[1], (TextView) bindings[3], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[9], (View) bindings[10]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvNum.setTag(null);
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
            ViewAdapter.setTextBold(this.tvNum, true);
        }
    }
}