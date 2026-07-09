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
import com.ltech.smarthome.binding.view.ViewAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ItemMrParamBindingImpl extends ItemMrParamBinding {
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
        sparseIntArray.put(R.id.view1, 4);
        sparseIntArray.put(R.id.first_go, 5);
        sparseIntArray.put(R.id.iv_icon_1, 6);
        sparseIntArray.put(R.id.civ_color_main, 7);
        sparseIntArray.put(R.id.civ_color_sub, 8);
        sparseIntArray.put(R.id.tv_sub_1, 9);
        sparseIntArray.put(R.id.iv_go_1, 10);
        sparseIntArray.put(R.id.second_go, 11);
        sparseIntArray.put(R.id.iv_icon_2, 12);
        sparseIntArray.put(R.id.tv_sub_2, 13);
        sparseIntArray.put(R.id.iv_go_2, 14);
    }

    public ItemMrParamBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ItemMrParamBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CircleImageView) bindings[7], (CircleImageView) bindings[8], (RelativeLayout) bindings[5], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[12], (RelativeLayout) bindings[11], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[13], (View) bindings[4]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.title.setTag(null);
        this.tvMain1.setTag(null);
        this.tvMain2.setTag(null);
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
            ViewAdapter.setTextBold(this.title, true);
            ViewAdapter.setTextBold(this.tvMain1, true);
            ViewAdapter.setTextBold(this.tvMain2, true);
        }
    }
}