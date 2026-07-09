package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.VerticalRangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActCtLightVM;
import com.ltech.smarthome.view.RectProgressBar2;

/* loaded from: classes3.dex */
public class ActCtLightBindingImpl extends ActCtLightBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(11);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{2}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view15, 3);
        sparseIntArray.put(R.id.tv_wy, 4);
        sparseIntArray.put(R.id.ctsb, 5);
        sparseIntArray.put(R.id.ct_view, 6);
        sparseIntArray.put(R.id.tv_brt, 7);
        sparseIntArray.put(R.id.sb_brt, 8);
        sparseIntArray.put(R.id.rv_color, 9);
        sparseIntArray.put(R.id.rv_mode, 10);
    }

    public ActCtLightBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActCtLightBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (View) bindings[6], (VerticalRangeSeekBar) bindings[5], (AppCompatImageView) bindings[1], (RecyclerView) bindings[9], (RecyclerView) bindings[10], (RectProgressBar2) bindings[8], (LayoutTitleDefaultBinding) bindings[2], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[4], (View) bindings[3]);
        this.mDirtyFlags = -1L;
        this.ivOpen.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActCtLightVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActCtLightBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCtLightBinding
    public void setViewmodel(ActCtLightVM Viewmodel) {
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
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeViewmodelStateOn((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelStateOn(MutableLiveData<Boolean> ViewmodelStateOn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActCtLightVM actCtLightVM = this.mViewmodel;
        long j2 = j & 13;
        BindingCommand<View> bindingCommand = null;
        if (j2 != 0) {
            BindingCommand<View> bindingCommand2 = ((j & 12) == 0 || actCtLightVM == null) ? null : actCtLightVM.viewClick;
            MutableLiveData<Boolean> mutableLiveData = actCtLightVM != null ? actCtLightVM.stateOn : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 32L : 16L;
            }
            i = safeUnbox ? R.mipmap.brt_ic_power_on : R.mipmap.brt_ic_power_off;
            bindingCommand = bindingCommand2;
        } else {
            i = 0;
        }
        if ((13 & j) != 0) {
            ViewAdapter.setSrc(this.ivOpen, i);
        }
        if ((j & 12) != 0) {
            ViewAdapter.onClickCommand(this.ivOpen, bindingCommand, false);
        }
        if ((j & 10) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}