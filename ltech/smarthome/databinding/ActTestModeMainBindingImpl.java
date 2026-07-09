package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActTestModeMainBindingImpl extends ActTestModeMainBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LayoutTitleDefaultBinding mboundView0;
    private final LinearLayout mboundView01;
    private final AppCompatTextView mboundView3;
    private final AppCompatTextView mboundView5;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(8);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{7}, new int[]{R.layout.layout_title_default});
        sViewsWithIds = null;
    }

    public ActTestModeMainBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActTestModeMainBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[1], (LinearLayout) bindings[2], (LinearLayout) bindings[4], (AppCompatTextView) bindings[6]);
        this.mDirtyFlags = -1L;
        LayoutTitleDefaultBinding layoutTitleDefaultBinding = (LayoutTitleDefaultBinding) bindings[7];
        this.mboundView0 = layoutTitleDefaultBinding;
        setContainedBinding(layoutTitleDefaultBinding);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView01 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        this.tvConfigTitle.setTag(null);
        this.tvTestMode1.setTag(null);
        this.tvTestMode2.setTag(null);
        this.tvTestMode3.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
        }
        this.mboundView0.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.mboundView0.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActTestModeMainBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActTestModeMainBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.mboundView0.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        long j2 = 6 & j;
        if ((5 & j) != 0) {
            this.mboundView0.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.mboundView3, true);
            ViewAdapter.setTextBold(this.mboundView5, true);
            ViewAdapter.setTextBold(this.tvConfigTitle, true);
            ViewAdapter.setTextBold(this.tvTestMode3, true);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.mboundView3, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvTestMode1, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvTestMode2, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvTestMode3, bindingCommand, false);
        }
        executeBindingsOn(this.mboundView0);
    }
}