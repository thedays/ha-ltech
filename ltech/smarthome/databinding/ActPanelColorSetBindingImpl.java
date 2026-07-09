package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM;

/* loaded from: classes3.dex */
public class ActPanelColorSetBindingImpl extends ActPanelColorSetBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(13);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{1}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 2);
        sparseIntArray.put(R.id.layout_s4, 3);
        sparseIntArray.put(R.id.layout_iv, 4);
        sparseIntArray.put(R.id.iv_S4, 5);
        sparseIntArray.put(R.id.iv_switch_5, 6);
        sparseIntArray.put(R.id.iv_screen, 7);
        sparseIntArray.put(R.id.iv_shadow_left, 8);
        sparseIntArray.put(R.id.iv_shadow_right, 9);
        sparseIntArray.put(R.id.ll_bottom, 10);
        sparseIntArray.put(R.id.tv_color_name, 11);
        sparseIntArray.put(R.id.rv, 12);
    }

    public ActPanelColorSetBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActPanelColorSetBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[9], (AppCompatImageView) bindings[6], (ConstraintLayout) bindings[2], (ConstraintLayout) bindings[4], (RelativeLayout) bindings[3], (LinearLayout) bindings[10], (RecyclerView) bindings[12], (LayoutTitleDefaultBinding) bindings[1], (TextView) bindings[11]);
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
            this.mDirtyFlags = 8L;
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
        if (61 == variableId) {
            setProductId((ProductId) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActSmartPanelSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActPanelColorSetBinding
    public void setProductId(ProductId ProductId) {
        this.mProductId = ProductId;
    }

    @Override // com.ltech.smarthome.databinding.ActPanelColorSetBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActPanelColorSetBinding
    public void setViewmodel(ActSmartPanelSettingVM Viewmodel) {
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
        if ((j & 10) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}