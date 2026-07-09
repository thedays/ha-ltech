package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public class ItemAddInstructContentBindingImpl extends ItemAddInstructContentBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView2;
    private final AppCompatTextView mboundView3;

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
        sparseIntArray.put(R.id.layout_name, 5);
        sparseIntArray.put(R.id.et_name, 6);
        sparseIntArray.put(R.id.iv_name_go, 7);
        sparseIntArray.put(R.id.radio_input, 8);
        sparseIntArray.put(R.id.radio_library, 9);
        sparseIntArray.put(R.id.radio_learn, 10);
        sparseIntArray.put(R.id.layout_data_format, 11);
        sparseIntArray.put(R.id.radio_hex, 12);
        sparseIntArray.put(R.id.radio_ascii, 13);
        sparseIntArray.put(R.id.layout_input, 14);
        sparseIntArray.put(R.id.tv_instruct, 15);
        sparseIntArray.put(R.id.iv_instruct_go, 16);
    }

    public ItemAddInstructContentBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }

    private ItemAddInstructContentBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[6], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[7], (RelativeLayout) bindings[11], (RelativeLayout) bindings[14], (RelativeLayout) bindings[5], (RadioImageTextView) bindings[13], (RadioImageTextView) bindings[12], (RadioImageTextView) bindings[8], (RadioImageTextView) bindings[10], (RadioImageTextView) bindings[9], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        this.tvInput.setTag(null);
        this.tvName.setTag(null);
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
            ViewAdapter.setTextBold(this.mboundView2, true);
            ViewAdapter.setTextBold(this.mboundView3, true);
            ViewAdapter.setTextBold(this.tvInput, true);
            ViewAdapter.setTextBold(this.tvName, true);
        }
    }
}