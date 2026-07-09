package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActChoiceLightTypeVM;

/* loaded from: classes3.dex */
public class ActChoiceLightTypeBindingImpl extends ActChoiceLightTypeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView2;
    private final AppCompatTextView mboundView4;
    private final AppCompatTextView mboundView6;
    private final AppCompatTextView mboundView8;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(12);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{11}, new int[]{R.layout.layout_title_default});
        sViewsWithIds = null;
    }

    public ActChoiceLightTypeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private ActChoiceLightTypeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (RelativeLayout) bindings[3], (RelativeLayout) bindings[1], (RelativeLayout) bindings[5], (RelativeLayout) bindings[7], (RelativeLayout) bindings[9], (LayoutTitleDefaultBinding) bindings[11]);
        this.mDirtyFlags = -1L;
        this.lightTypeCt.setTag(null);
        this.lightTypeDim.setTag(null);
        this.lightTypeRgb.setTag(null);
        this.lightTypeRgbW.setTag(null);
        this.lightTypeRgbWy.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[6];
        this.mboundView6 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[8];
        this.mboundView8 = appCompatTextView5;
        appCompatTextView5.setTag(null);
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
        setViewmodel((ActChoiceLightTypeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActChoiceLightTypeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActChoiceLightTypeBinding
    public void setViewmodel(ActChoiceLightTypeVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(92);
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
        ActChoiceLightTypeVM actChoiceLightTypeVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || actChoiceLightTypeVM == null) ? null : actChoiceLightTypeVM.viewClick;
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.lightTypeCt, bindingCommand, false);
            ViewAdapter.onClickCommand(this.lightTypeDim, bindingCommand, false);
            ViewAdapter.onClickCommand(this.lightTypeRgb, bindingCommand, false);
            ViewAdapter.onClickCommand(this.lightTypeRgbW, bindingCommand, false);
            ViewAdapter.onClickCommand(this.lightTypeRgbWy, bindingCommand, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.mboundView10, true);
            ViewAdapter.setTextBold(this.mboundView2, true);
            ViewAdapter.setTextBold(this.mboundView4, true);
            ViewAdapter.setTextBold(this.mboundView6, true);
            ViewAdapter.setTextBold(this.mboundView8, true);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}