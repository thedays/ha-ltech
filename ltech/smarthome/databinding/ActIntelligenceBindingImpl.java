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
public class ActIntelligenceBindingImpl extends ActIntelligenceBinding {
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
        sparseIntArray.put(R.id.v_title_bg, 3);
        sparseIntArray.put(R.id.tabs, 4);
        sparseIntArray.put(R.id.viewpager, 5);
    }

    public ActIntelligenceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ActIntelligenceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[2], (TabLayout) bindings[4], (View) bindings[3], (ViewPager2) bindings[5]);
        this.mDirtyFlags = -1L;
        this.ivAdd.setTag(null);
        this.ivBack.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ActIntelligenceBinding
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
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand, false);
        }
    }
}