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
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ItemLightGroupControlSubDeviceBindingImpl extends ItemLightGroupControlSubDeviceBinding {
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
        sparseIntArray.put(R.id.csb_color_bar, 3);
        sparseIntArray.put(R.id.civ_color, 4);
        sparseIntArray.put(R.id.tv_ct_percent, 5);
        sparseIntArray.put(R.id.tv_brt, 6);
        sparseIntArray.put(R.id.sb_brt, 7);
        sparseIntArray.put(R.id.group_color, 8);
    }

    public ItemLightGroupControlSubDeviceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private ItemLightGroupControlSubDeviceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[2], (CircleImageView) bindings[4], (ColorSeekBar) bindings[3], (Group) bindings[8], (LightBrtBar) bindings[7], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[5]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView27.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvColorTip.setTag(null);
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
            ViewAdapter.setTextBold(this.appCompatTextView27, true);
            ViewAdapter.setTextBold(this.tvColorTip, true);
        }
    }
}