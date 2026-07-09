package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActLightOnOffTimeBindingImpl extends ActLightOnOffTimeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LayoutTitleDefaultBinding mboundView0;
    private final LinearLayout mboundView01;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(13);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 9);
        sparseIntArray.put(R.id.tv_power_on_time, 10);
        sparseIntArray.put(R.id.tv_on_time, 11);
        sparseIntArray.put(R.id.tv_off_time, 12);
    }

    public ActLightOnOffTimeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActLightOnOffTimeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (RelativeLayout) bindings[5], (RelativeLayout) bindings[3], (RelativeLayout) bindings[1], (ConstraintLayout) bindings[9], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[7]);
        this.mDirtyFlags = -1L;
        this.layoutLightOff.setTag(null);
        this.layoutLightOn.setTag(null);
        this.layoutLightPowerOn.setTag(null);
        LayoutTitleDefaultBinding layoutTitleDefaultBinding = (LayoutTitleDefaultBinding) bindings[8];
        this.mboundView0 = layoutTitleDefaultBinding;
        setContainedBinding(layoutTitleDefaultBinding);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView01 = linearLayout;
        linearLayout.setTag(null);
        this.tvOffTip.setTag(null);
        this.tvOnTip.setTag(null);
        this.tvPowerOnTip.setTag(null);
        this.tvReset.setTag(null);
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActLightOnOffTimeBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActLightOnOffTimeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
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
        BindingCommand<View> bindingCommand = this.mClickCommand;
        TitleDefault titleDefault = this.mTitle;
        long j2 = 6 & j;
        if ((5 & j) != 0) {
            ViewAdapter.onClickCommand(this.layoutLightOff, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutLightOn, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutLightPowerOn, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvReset, bindingCommand, false);
        }
        if (j2 != 0) {
            this.mboundView0.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvOffTip, true);
            ViewAdapter.setTextBold(this.tvOnTip, true);
            ViewAdapter.setTextBold(this.tvPowerOnTip, true);
            ViewAdapter.setTextBold(this.tvReset, true);
        }
        executeBindingsOn(this.mboundView0);
    }
}