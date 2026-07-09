package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.control.FtIntelligenceVM;

/* loaded from: classes3.dex */
public class FtIntelligenceBindingImpl extends FtIntelligenceBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.v_title_bg, 4);
        sparseIntArray.put(R.id.tabs, 5);
        sparseIntArray.put(R.id.viewpager, 6);
    }

    public FtIntelligenceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private FtIntelligenceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[3], (TabLayout) bindings[5], (View) bindings[4], (ViewPager2) bindings[6]);
        this.mDirtyFlags = -1L;
        this.ivAdd.setTag(null);
        this.ivSearch.setTag(null);
        this.ivSort.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
        if (92 != variableId) {
            return false;
        }
        setViewmodel((FtIntelligenceVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtIntelligenceBinding
    public void setViewmodel(FtIntelligenceVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        FtIntelligenceVM ftIntelligenceVM = this.mViewmodel;
        long j2 = j & 3;
        BindingCommand<View> bindingCommand = (j2 == 0 || ftIntelligenceVM == null) ? null : ftIntelligenceVM.viewClick;
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.ivAdd, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivSearch, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivSort, bindingCommand, false);
        }
    }
}