package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public class ItemSelectLuxCtBindingImpl extends ItemSelectLuxCtBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_name, 1);
        sparseIntArray.put(R.id.iv_select, 2);
        sparseIntArray.put(R.id.layout_select_diy, 3);
        sparseIntArray.put(R.id.radio_group, 4);
        sparseIntArray.put(R.id.radio_less, 5);
        sparseIntArray.put(R.id.radio_more, 6);
        sparseIntArray.put(R.id.et_value, 7);
        sparseIntArray.put(R.id.tv_unit, 8);
        sparseIntArray.put(R.id.seekbar_lux, 9);
        sparseIntArray.put(R.id.seekbar_ct, 10);
        sparseIntArray.put(R.id.tv_min, 11);
    }

    public ItemSelectLuxCtBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private ItemSelectLuxCtBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatEditText) bindings[7], (AppCompatImageView) bindings[2], (ConstraintLayout) bindings[3], (RadioGroup) bindings[4], (RadioButton) bindings[5], (RadioButton) bindings[6], (StepSetView) bindings[10], (StepSetView) bindings[9], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[8]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
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
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectLuxCtBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}