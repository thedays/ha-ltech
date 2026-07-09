package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.ir.BaseTvVM;

/* loaded from: classes3.dex */
public class ActTvBindingImpl extends ActTvBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(15);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_ir"}, new int[]{11}, new int[]{R.layout.layout_title_ir});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 12);
        sparseIntArray.put(R.id.frame_layout, 13);
        sparseIntArray.put(R.id.cardview_add, 14);
    }

    public ActTvBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActTvBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[6], (CardView) bindings[14], (FrameLayout) bindings[13], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[9], (ConstraintLayout) bindings[12], (LayoutTitleIrBinding) bindings[11], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[8]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView23.setTag(null);
        this.ivBack.setTag(null);
        this.ivHome.setTag(null);
        this.ivNext.setTag(null);
        this.ivPower.setTag(null);
        this.ivUpper.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvIndex.setTag(null);
        this.tvMore.setTag(null);
        this.tvNum.setTag(null);
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
        setViewmodel((BaseTvVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActTvBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActTvBinding
    public void setViewmodel(BaseTvVM Viewmodel) {
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
        BindingCommand<View> bindingCommand;
        BindingCommand<View> bindingCommand2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        BaseTvVM baseTvVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        if (j3 == 0 || baseTvVM == null) {
            bindingCommand = null;
            bindingCommand2 = null;
        } else {
            bindingCommand = baseTvVM.viewClick;
            bindingCommand2 = baseTvVM.clickCommand;
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView23, true);
            ViewAdapter.setTextBold(this.tvIndex, true);
            ViewAdapter.setTextBold(this.tvMore, true);
            ViewAdapter.setTextBold(this.tvNum, true);
            ViewAdapter.setTextBold(this.tvResponse, true);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivHome, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivNext, bindingCommand2, false);
            ViewAdapter.onClickCommand(this.ivPower, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivUpper, bindingCommand2, false);
            ViewAdapter.onClickCommand(this.tvMore, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvNum, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvResponse, bindingCommand2, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}