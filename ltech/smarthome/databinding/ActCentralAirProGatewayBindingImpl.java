package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.central.airpro.ActCentralAirProGatewayVM;

/* loaded from: classes3.dex */
public class ActCentralAirProGatewayBindingImpl extends ActCentralAirProGatewayBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView1;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(9);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{2}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.appCompatImageView11, 3);
        sparseIntArray.put(R.id.guideline2, 4);
        sparseIntArray.put(R.id.v_guide, 5);
        sparseIntArray.put(R.id.tv_search_sum, 6);
        sparseIntArray.put(R.id.tabs, 7);
        sparseIntArray.put(R.id.viewpager, 8);
    }

    public ActCentralAirProGatewayBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private ActCentralAirProGatewayBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[3], (Guideline) bindings[4], (TabLayout) bindings[7], (LayoutTitleDefaultBinding) bindings[2], (AppCompatTextView) bindings[6], (View) bindings[5], (ViewPager2) bindings[8]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[1];
        this.mboundView1 = appCompatTextView;
        appCompatTextView.setTag(null);
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
        setViewmodel((ActCentralAirProGatewayVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActCentralAirProGatewayBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCentralAirProGatewayBinding
    public void setViewmodel(ActCentralAirProGatewayVM Viewmodel) {
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
        long j2 = 5 & j;
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.mboundView1, true);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}