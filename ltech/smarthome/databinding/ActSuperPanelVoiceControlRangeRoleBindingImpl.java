package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVoiceControlRangeVM;

/* loaded from: classes3.dex */
public class ActSuperPanelVoiceControlRangeRoleBindingImpl extends ActSuperPanelVoiceControlRangeRoleBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_tab, 3);
        sparseIntArray.put(R.id.v_title_bg, 4);
        sparseIntArray.put(R.id.tab_title, 5);
        sparseIntArray.put(R.id.viewpager, 6);
    }

    public ActSuperPanelVoiceControlRangeRoleBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ActSuperPanelVoiceControlRangeRoleBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[2], (ConstraintLayout) bindings[3], (TabLayout) bindings[5], (View) bindings[4], (ViewPager2) bindings[6]);
        this.mDirtyFlags = -1L;
        this.ivBack.setTag(null);
        this.ivEdit.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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

    @Override // com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeRoleBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
    }

    @Override // com.ltech.smarthome.databinding.ActSuperPanelVoiceControlRangeRoleBinding
    public void setViewmodel(ActSuperPanelVoiceControlRangeVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
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
        ActSuperPanelVoiceControlRangeVM actSuperPanelVoiceControlRangeVM = this.mViewmodel;
        long j2 = j & 6;
        BindingCommand<View> bindingCommand = (j2 == 0 || actSuperPanelVoiceControlRangeVM == null) ? null : actSuperPanelVoiceControlRangeVM.viewClick;
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand, false);
        }
    }
}