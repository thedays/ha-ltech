package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeVM;

/* loaded from: classes3.dex */
public class ActSuperPanelVoiceControlRangeBindingImpl extends ActSuperPanelVoiceControlRangeBinding {
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
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.all_icon, 5);
        sparseIntArray.put(R.id.all_main_text, 6);
        sparseIntArray.put(R.id.all_sub_text, 7);
        sparseIntArray.put(R.id.part_icon, 8);
        sparseIntArray.put(R.id.part_main_text, 9);
        sparseIntArray.put(R.id.part_sub_text, 10);
        sparseIntArray.put(R.id.diy_icon, 11);
        sparseIntArray.put(R.id.diy_main_text, 12);
        sparseIntArray.put(R.id.diy_sub_text, 13);
        sparseIntArray.put(R.id.diy_go_icon, 14);
        sparseIntArray.put(R.id.diy_count_text, 15);
    }

    public ActSuperPanelVoiceControlRangeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private ActSuperPanelVoiceControlRangeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[5], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[15], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[11], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[13], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[3], (ConstraintLayout) bindings[2], (AppCompatImageView) bindings[8], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[10], (LayoutTitleDefaultBinding) bindings[4]);
        this.mDirtyFlags = -1L;
        this.layoutAll.setTag(null);
        this.layoutDiy.setTag(null);
        this.layoutPart.setTag(null);
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActSuperPanelVoiceControlRangeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeBinding
    public void setViewmodel(ActSuperPanelVoiceControlRangeVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
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
        ActSuperPanelVoiceControlRangeVM actSuperPanelVoiceControlRangeVM = this.mViewmodel;
        long j2 = 10 & j;
        long j3 = j & 12;
        BindingCommand<View> bindingCommand = (j3 == 0 || actSuperPanelVoiceControlRangeVM == null) ? null : actSuperPanelVoiceControlRangeVM.viewClick;
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.layoutAll, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutDiy, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutPart, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}