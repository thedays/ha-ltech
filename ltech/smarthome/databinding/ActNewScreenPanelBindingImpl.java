package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.smartpanel.ActSmartPanelVM;

/* loaded from: classes3.dex */
public class ActNewScreenPanelBindingImpl extends ActNewScreenPanelBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(16);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{1}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 2);
        sparseIntArray.put(R.id.layout_content, 3);
        sparseIntArray.put(R.id.layout_s4, 4);
        sparseIntArray.put(R.id.view_guide, 5);
        sparseIntArray.put(R.id.iv_screen, 6);
        sparseIntArray.put(R.id.rv_screen_info, 7);
        sparseIntArray.put(R.id.iv_S4, 8);
        sparseIntArray.put(R.id.rv_key_info, 9);
        sparseIntArray.put(R.id.iv_switch_5, 10);
        sparseIntArray.put(R.id.iv_shadow_left, 11);
        sparseIntArray.put(R.id.iv_shadow_right, 12);
        sparseIntArray.put(R.id.ll_bottom, 13);
        sparseIntArray.put(R.id.tab_layout, 14);
        sparseIntArray.put(R.id.vp, 15);
    }

    public ActNewScreenPanelBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private ActNewScreenPanelBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[10], (ConstraintLayout) bindings[3], (LinearLayout) bindings[2], (LinearLayout) bindings[4], (RelativeLayout) bindings[13], (RecyclerView) bindings[9], (RecyclerView) bindings[7], (TabLayout) bindings[14], (LayoutTitleDefaultBinding) bindings[1], (View) bindings[5], (ViewPager2) bindings[15]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
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
        setViewmodel((ActSmartPanelVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActNewScreenPanelBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActNewScreenPanelBinding
    public void setViewmodel(ActSmartPanelVM Viewmodel) {
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
        if ((j & 5) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}