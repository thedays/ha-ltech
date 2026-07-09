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
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.central.air.ActAcCentralVM;

/* loaded from: classes3.dex */
public class ActFreshAirBindingImpl extends ActFreshAirBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView3;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(18);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_ir"}, new int[]{11}, new int[]{R.layout.layout_title_ir});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 12);
        sparseIntArray.put(R.id.layout_circleBar, 13);
        sparseIntArray.put(R.id.iv_circle, 14);
        sparseIntArray.put(R.id.rv_info, 15);
        sparseIntArray.put(R.id.rv_content, 16);
        sparseIntArray.put(R.id.cardview_add, 17);
    }

    public ActFreshAirBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }

    private ActFreshAirBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[6], (CardView) bindings[17], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[9], (LinearLayout) bindings[13], (ConstraintLayout) bindings[12], (RecyclerView) bindings[16], (RecyclerView) bindings[15], (LayoutTitleIrBinding) bindings[11], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[2], (View) bindings[5]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView19.setTag(null);
        this.appCompatTextView23.setTag(null);
        this.ivNext.setTag(null);
        this.ivUpper.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView;
        appCompatTextView.setTag(null);
        setContainedBinding(this.title);
        this.tvIndex.setTag(null);
        this.tvResponse.setTag(null);
        this.tvState.setTag(null);
        this.tvTemp.setTag(null);
        this.viewOnOff.setTag(null);
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
        setViewmodel((ActAcCentralVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActFreshAirBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActFreshAirBinding
    public void setViewmodel(ActAcCentralVM Viewmodel) {
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
        return onChangeViewmodelPowerOn((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelPowerOn(MutableLiveData<Boolean> ViewmodelPowerOn, int fieldId) {
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
        BindingCommand<View> bindingCommand;
        int i;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActAcCentralVM actAcCentralVM = this.mViewmodel;
        long j2 = j & 13;
        if (j2 != 0) {
            bindingCommand = ((j & 12) == 0 || actAcCentralVM == null) ? null : actAcCentralVM.viewclick;
            MutableLiveData<Boolean> mutableLiveData = actAcCentralVM != null ? actAcCentralVM.powerOn : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 160L : 80L;
            }
            i = 8;
            i2 = safeUnbox ? 0 : 8;
            if (!safeUnbox) {
                i = 0;
            }
        } else {
            bindingCommand = null;
            i = 0;
            i2 = 0;
        }
        if ((13 & j) != 0) {
            this.appCompatTextView19.setVisibility(i2);
            this.mboundView3.setVisibility(i);
            this.tvState.setVisibility(i2);
            this.tvTemp.setVisibility(i2);
        }
        if ((8 & j) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView23, true);
            ViewAdapter.setTextBold(this.mboundView3, true);
            ViewAdapter.setTextBold(this.tvIndex, true);
            ViewAdapter.setTextBold(this.tvResponse, true);
            ViewAdapter.setTextBold(this.tvTemp, true);
        }
        if ((j & 12) != 0) {
            ViewAdapter.onClickCommand(this.ivNext, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivUpper, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvResponse, bindingCommand, false);
            ViewAdapter.onClickCommand(this.viewOnOff, bindingCommand, false);
        }
        if ((j & 10) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}