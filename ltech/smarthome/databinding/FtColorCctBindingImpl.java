package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.CCTCoordinateView;
import com.ltech.smarthome.view.StepSetView;

/* loaded from: classes3.dex */
public class FtColorCctBindingImpl extends FtColorCctBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ScrollView mboundView0;

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
        sparseIntArray.put(R.id.layout_view, 1);
        sparseIntArray.put(R.id.cct_coordinate_view, 2);
        sparseIntArray.put(R.id.layout_ct, 3);
        sparseIntArray.put(R.id.layout_point, 4);
        sparseIntArray.put(R.id.tv_color_title, 5);
        sparseIntArray.put(R.id.tv_delete_tip, 6);
        sparseIntArray.put(R.id.tv_add, 7);
        sparseIntArray.put(R.id.rv_color_point, 8);
        sparseIntArray.put(R.id.layout_duv, 9);
        sparseIntArray.put(R.id.layout_brt, 10);
    }

    public FtColorCctBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private FtColorCctBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CCTCoordinateView) bindings[2], (StepSetView) bindings[10], (StepSetView) bindings[3], (StepSetView) bindings[9], (ConstraintLayout) bindings[4], (FrameLayout) bindings[1], (RecyclerView) bindings[8], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[6]);
        this.mDirtyFlags = -1L;
        ScrollView scrollView = (ScrollView) bindings[0];
        this.mboundView0 = scrollView;
        scrollView.setTag(null);
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