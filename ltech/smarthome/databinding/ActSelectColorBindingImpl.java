package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.ColorPickerView;
import com.ltech.smarthome.view.ColorSeekBar2;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class ActSelectColorBindingImpl extends ActSelectColorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(22);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{1}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.v_control, 2);
        sparseIntArray.put(R.id.sb_brt, 3);
        sparseIntArray.put(R.id.tv_brt, 4);
        sparseIntArray.put(R.id.sb_w, 5);
        sparseIntArray.put(R.id.tv_w, 6);
        sparseIntArray.put(R.id.appCompatTextView21, 7);
        sparseIntArray.put(R.id.tv_w_tip, 8);
        sparseIntArray.put(R.id.rv_color, 9);
        sparseIntArray.put(R.id.group_rgb, 10);
        sparseIntArray.put(R.id.group_rgbw, 11);
        sparseIntArray.put(R.id.v_color, 12);
        sparseIntArray.put(R.id.cpv, 13);
        sparseIntArray.put(R.id.tv_red, 14);
        sparseIntArray.put(R.id.seek_bar_wy, 15);
        sparseIntArray.put(R.id.et_red, 16);
        sparseIntArray.put(R.id.tv_green, 17);
        sparseIntArray.put(R.id.et_green, 18);
        sparseIntArray.put(R.id.tv_blue, 19);
        sparseIntArray.put(R.id.et_blue, 20);
        sparseIntArray.put(R.id.group_rgbwy, 21);
    }

    public ActSelectColorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }

    private ActSelectColorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[7], (ColorPickerView) bindings[13], (ColorEditText) bindings[20], (ColorEditText) bindings[18], (ColorEditText) bindings[16], (Group) bindings[10], (Group) bindings[11], (Group) bindings[21], (RecyclerView) bindings[9], (LightBrtBar) bindings[3], (LightBrtBar) bindings[5], (ColorSeekBar2) bindings[15], (LayoutTitleDefaultBinding) bindings[1], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[8], (View) bindings[12], (ConstraintLayout) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
        }
        this.title.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings();
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

    @Override // com.ltech.smarthome.databinding.ActSelectColorBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        if ((j & 3) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}