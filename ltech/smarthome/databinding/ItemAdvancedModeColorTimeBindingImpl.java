package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.MultiColorView;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ItemAdvancedModeColorTimeBindingImpl extends ItemAdvancedModeColorTimeBinding {
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
        sparseIntArray.put(R.id.view261, 1);
        sparseIntArray.put(R.id.line_top1, 2);
        sparseIntArray.put(R.id.line_bottom1, 3);
        sparseIntArray.put(R.id.circleImageView21, 4);
        sparseIntArray.put(R.id.layout_num1, 5);
        sparseIntArray.put(R.id.multiColorView1, 6);
        sparseIntArray.put(R.id.tv_num1, 7);
        sparseIntArray.put(R.id.iv_time1, 8);
        sparseIntArray.put(R.id.view271, 9);
        sparseIntArray.put(R.id.tv_time_tip1, 10);
        sparseIntArray.put(R.id.tv_time1, 11);
        sparseIntArray.put(R.id.view26, 12);
        sparseIntArray.put(R.id.line_top, 13);
        sparseIntArray.put(R.id.line_bottom, 14);
        sparseIntArray.put(R.id.circleImageView2, 15);
        sparseIntArray.put(R.id.layout_num, 16);
        sparseIntArray.put(R.id.multiColorView, 17);
        sparseIntArray.put(R.id.tv_num, 18);
        sparseIntArray.put(R.id.iv_time, 19);
        sparseIntArray.put(R.id.view27, 20);
        sparseIntArray.put(R.id.tv_time_tip, 21);
        sparseIntArray.put(R.id.tv_time, 22);
    }

    public ItemAdvancedModeColorTimeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }

    private ItemAdvancedModeColorTimeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CircleImageView) bindings[15], (CircleImageView) bindings[4], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[8], (FrameLayout) bindings[16], (FrameLayout) bindings[5], (View) bindings[14], (View) bindings[3], (View) bindings[13], (View) bindings[2], (MultiColorView) bindings[17], (MultiColorView) bindings[6], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[22], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[10], (View) bindings[12], (View) bindings[1], (View) bindings[20], (View) bindings[9]);
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