package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActColorLightVM;
import com.ltech.smarthome.view.CircleColorPickerView;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class FtSpiColorBindingImpl extends FtSpiColorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.ccpv, 2);
        sparseIntArray.put(R.id.rv_color, 3);
        sparseIntArray.put(R.id.tv_brt_tip, 4);
        sparseIntArray.put(R.id.sb_brt, 5);
        sparseIntArray.put(R.id.tv_brt, 6);
    }

    public FtSpiColorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private FtSpiColorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (CircleColorPickerView) bindings[2], (RecyclerView) bindings[3], (LightBrtBar) bindings[5], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[4], (View) bindings[1]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.vOpen.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        setViewmodel((ActColorLightVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtSpiColorBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
    }

    @Override // com.ltech.smarthome.databinding.FtSpiColorBinding
    public void setViewmodel(ActColorLightVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
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
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ActColorLightVM actColorLightVM = this.mViewmodel;
        long j2 = j & 13;
        int i = 0;
        if (j2 != 0) {
            MutableLiveData<Boolean> mutableLiveData = actColorLightVM != null ? actColorLightVM.stateOn : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 32L : 16L;
            }
            if (safeUnbox) {
                i = 8;
            }
        }
        if ((j & 13) != 0) {
            this.vOpen.setVisibility(i);
        }
    }
}