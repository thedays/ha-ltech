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
import com.ltech.smarthome.ui.device.musicplayer.ActBleMusicPlayerVM;

/* loaded from: classes3.dex */
public class ActBleMusicPlayerBindingImpl extends ActBleMusicPlayerBinding {
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

    public ActBleMusicPlayerBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ActBleMusicPlayerBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[1], (TabLayout) bindings[4], (View) bindings[3], (ViewPager2) bindings[5]);
        this.mDirtyFlags = -1L;
        this.ivBack.setTag(null);
        this.ivSetting.setTag(null);
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
        setViewmodel((ActBleMusicPlayerVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActBleMusicPlayerBinding
    public void setViewmodel(ActBleMusicPlayerVM Viewmodel) {
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
        ActBleMusicPlayerVM actBleMusicPlayerVM = this.mViewmodel;
        long j2 = j & 3;
        BindingCommand<View> bindingCommand = (j2 == 0 || actBleMusicPlayerVM == null) ? null : actBleMusicPlayerVM.viewClick;
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivSetting, bindingCommand, false);
        }
    }
}