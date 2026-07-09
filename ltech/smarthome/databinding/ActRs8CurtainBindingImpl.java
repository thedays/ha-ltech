package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.rs8.ActRs8VM;
import com.ltech.smarthome.view.CurtainBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.ReverseLightBrtBar;

/* loaded from: classes3.dex */
public class ActRs8CurtainBindingImpl extends ActRs8CurtainBinding {
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
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{6}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 7);
        sparseIntArray.put(R.id.bg, 8);
        sparseIntArray.put(R.id.iv_device, 9);
        sparseIntArray.put(R.id.curtain_up_down, 10);
        sparseIntArray.put(R.id.curtain_left_only, 11);
        sparseIntArray.put(R.id.curtain_left, 12);
        sparseIntArray.put(R.id.curtain_right, 13);
        sparseIntArray.put(R.id.rv_content, 14);
        sparseIntArray.put(R.id.cardview_add, 15);
    }

    public ActRs8CurtainBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private ActRs8CurtainBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[1], (ConstraintLayout) bindings[8], (CardView) bindings[15], (LightBrtBar) bindings[12], (LightBrtBar) bindings[11], (ReverseLightBrtBar) bindings[13], (CurtainBar) bindings[10], (AppCompatImageView) bindings[9], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[4], (ConstraintLayout) bindings[7], (RecyclerView) bindings[14], (LayoutTitleDefaultBinding) bindings[6], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView23.setTag(null);
        this.ivNext.setTag(null);
        this.ivUpper.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvIndex.setTag(null);
        this.tvResponse.setTag(null);
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
        setViewmodel((ActRs8VM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActRs8CurtainBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActRs8CurtainBinding
    public void setViewmodel(ActRs8VM Viewmodel) {
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
        ActRs8VM actRs8VM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || actRs8VM == null) ? null : actRs8VM.viewClick;
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView23, true);
            ViewAdapter.setTextBold(this.tvIndex, true);
            ViewAdapter.setTextBold(this.tvResponse, true);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.ivNext, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivUpper, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvResponse, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}