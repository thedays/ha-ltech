package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.MySpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActChildMcuUpgradeBindingImpl extends ActChildMcuUpgradeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(19);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{2}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.top_bg, 3);
        sparseIntArray.put(R.id.tv_label1, 4);
        sparseIntArray.put(R.id.tv_label, 5);
        sparseIntArray.put(R.id.sb_auto, 6);
        sparseIntArray.put(R.id.tv_label2, 7);
        sparseIntArray.put(R.id.tv_auto_time, 8);
        sparseIntArray.put(R.id.group_top, 9);
        sparseIntArray.put(R.id.view, 10);
        sparseIntArray.put(R.id.view2, 11);
        sparseIntArray.put(R.id.spinner_floor, 12);
        sparseIntArray.put(R.id.spinner_room, 13);
        sparseIntArray.put(R.id.tv_num, 14);
        sparseIntArray.put(R.id.tv_select, 15);
        sparseIntArray.put(R.id.line, 16);
        sparseIntArray.put(R.id.tv_stop, 17);
        sparseIntArray.put(R.id.tv_ok, 18);
    }

    public ActChildMcuUpgradeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }

    private ActChildMcuUpgradeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Group) bindings[9], (View) bindings[16], (RecyclerView) bindings[1], (SwitchButton) bindings[6], (MySpinner) bindings[12], (MySpinner) bindings[13], (LayoutTitleDefaultBinding) bindings[2], (View) bindings[3], (AppCompatTextView) bindings[8], (TextView) bindings[5], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[17], (View) bindings[10], (View) bindings[11]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.rv.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ActChildMcuUpgradeBinding
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
        long j2 = 3 & j;
        if ((j & 2) != 0) {
            this.rv.setItemAnimator(null);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}