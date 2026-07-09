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
import com.ltech.smarthome.binding.view.ViewAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ItemSceneActionBindingImpl extends ItemSceneActionBinding {
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
        sparseIntArray.put(R.id.v_action, 3);
        sparseIntArray.put(R.id.iv_icon, 4);
        sparseIntArray.put(R.id.layout_state, 5);
        sparseIntArray.put(R.id.tv_location, 6);
        sparseIntArray.put(R.id.civ_color, 7);
        sparseIntArray.put(R.id.tv_state, 8);
        sparseIntArray.put(R.id.tv_virtual, 9);
        sparseIntArray.put(R.id.tv_action_tip, 10);
        sparseIntArray.put(R.id.appCompatImageView13, 11);
        sparseIntArray.put(R.id.ivDrag, 12);
        sparseIntArray.put(R.id.view20, 13);
    }

    public ItemSceneActionBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private ItemSceneActionBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ConstraintLayout) bindings[0], (AppCompatImageView) bindings[11], (CircleImageView) bindings[7], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[4], (LinearLayout) bindings[5], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[9], (View) bindings[3], (View) bindings[13]);
        this.mDirtyFlags = -1L;
        this.addSceneColayout.setTag(null);
        this.tvDelayTime.setTag(null);
        this.tvDeviceName.setTag(null);
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
            ViewAdapter.setTextBold(this.tvDelayTime, true);
            ViewAdapter.setTextBold(this.tvDeviceName, true);
        }
    }
}