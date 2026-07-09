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
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.mode.ActAddModeColorVM;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class ActAddModeCtDimBindingImpl extends ActAddModeCtDimBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(18);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view28, 5);
        sparseIntArray.put(R.id.constraintLayout2, 6);
        sparseIntArray.put(R.id.csb_ct_bar, 7);
        sparseIntArray.put(R.id.tv_ct, 8);
        sparseIntArray.put(R.id.tv_brt, 9);
        sparseIntArray.put(R.id.sb_brt, 10);
        sparseIntArray.put(R.id.group_ct, 11);
        sparseIntArray.put(R.id.view29, 12);
        sparseIntArray.put(R.id.view30, 13);
        sparseIntArray.put(R.id.pick_view_hour, 14);
        sparseIntArray.put(R.id.pick_view_min, 15);
        sparseIntArray.put(R.id.pick_view_sec, 16);
        sparseIntArray.put(R.id.pick_view_ms, 17);
    }

    public ActAddModeCtDimBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }

    private ActAddModeCtDimBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ConstraintLayout) bindings[6], (RangeSeekBar) bindings[7], (Group) bindings[11], (RecyclerView) bindings[14], (RecyclerView) bindings[15], (RecyclerView) bindings[17], (RecyclerView) bindings[16], (LightBrtBar) bindings[10], (LayoutTitleDefaultBinding) bindings[4], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3], (View) bindings[5], (View) bindings[12], (View) bindings[13]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvBrtTip.setTag(null);
        this.tvSetColorTip.setTag(null);
        this.tvSetTime.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActAddModeColorVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAddModeCtDimBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAddModeCtDimBinding
    public void setViewmodel(ActAddModeColorVM Viewmodel) {
        this.mViewmodel = Viewmodel;
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
        if ((5 & j) != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvBrtTip, true);
            ViewAdapter.setTextBold(this.tvSetColorTip, true);
            ViewAdapter.setTextBold(this.tvSetTime, true);
        }
        executeBindingsOn(this.title);
    }
}