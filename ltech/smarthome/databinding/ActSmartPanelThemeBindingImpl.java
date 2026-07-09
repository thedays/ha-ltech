package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.screenpanel.ActSmartPanelThemeVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActSmartPanelThemeBindingImpl extends ActSmartPanelThemeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView3;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(11);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_time, 5);
        sparseIntArray.put(R.id.tv_start_time, 6);
        sparseIntArray.put(R.id.iv_start_time_go, 7);
        sparseIntArray.put(R.id.sb, 8);
        sparseIntArray.put(R.id.label, 9);
        sparseIntArray.put(R.id.rv, 10);
    }

    public ActSmartPanelThemeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ActSmartPanelThemeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[7], (TextView) bindings[9], (RelativeLayout) bindings[2], (ConstraintLayout) bindings[5], (RecyclerView) bindings[10], (SwitchButton) bindings[8], (LayoutTitleDefaultBinding) bindings[4], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.layoutShow.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView;
        appCompatTextView.setTag(null);
        setContainedBinding(this.title);
        this.tvTimeTip.setTag(null);
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
        setViewmodel((ActSmartPanelThemeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelThemeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelThemeBinding
    public void setViewmodel(ActSmartPanelThemeVM Viewmodel) {
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
        return onChangeViewmodelScreensaverTime((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelScreensaverTime(MutableLiveData<Integer> ViewmodelScreensaverTime, int fieldId) {
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
        TitleDefault titleDefault = this.mTitle;
        ActSmartPanelThemeVM actSmartPanelThemeVM = this.mViewmodel;
        long j2 = j & 13;
        int i = 0;
        if (j2 != 0) {
            MutableLiveData<Integer> mutableLiveData = actSmartPanelThemeVM != null ? actSmartPanelThemeVM.screensaverTime : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean z = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null) == 65535;
            if (j2 != 0) {
                j |= z ? 32L : 16L;
            }
            if (z) {
                i = 8;
            }
        }
        if ((13 & j) != 0) {
            this.layoutShow.setVisibility(i);
        }
        if ((8 & j) != 0) {
            ViewAdapter.setTextBold(this.mboundView3, true);
            ViewAdapter.setTextBold(this.tvTimeTip, true);
        }
        if ((j & 10) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}