package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.circadianlighting.ActLightPlanDetailVM;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class ActLightPlanDetailBindingImpl extends ActLightPlanDetailBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView1;
    private final AppCompatTextView mboundView4;
    private final AppCompatTextView mboundView6;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(28);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{12}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.rv, 13);
        sparseIntArray.put(R.id.node_layout, 14);
        sparseIntArray.put(R.id.cur_node_layout, 15);
        sparseIntArray.put(R.id.time_tv, 16);
        sparseIntArray.put(R.id.iv_state_go, 17);
        sparseIntArray.put(R.id.label, 18);
        sparseIntArray.put(R.id.csb_color_bar, 19);
        sparseIntArray.put(R.id.quick_rv, 20);
        sparseIntArray.put(R.id.k_tv, 21);
        sparseIntArray.put(R.id.label1, 22);
        sparseIntArray.put(R.id.sb_brt, 23);
        sparseIntArray.put(R.id.p_tv, 24);
        sparseIntArray.put(R.id.iv_state_go1, 25);
        sparseIntArray.put(R.id.gradient_tv, 26);
        sparseIntArray.put(R.id.iv_state_go2, 27);
    }

    public ActLightPlanDetailBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }

    private ActLightPlanDetailBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (RangeSeekBar) bindings[19], (RelativeLayout) bindings[15], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[11], (RelativeLayout) bindings[8], (AppCompatTextView) bindings[26], (AppCompatImageView) bindings[17], (AppCompatImageView) bindings[25], (AppCompatImageView) bindings[27], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[22], (RelativeLayout) bindings[5], (AppCompatTextView) bindings[7], (LinearLayout) bindings[14], (AppCompatTextView) bindings[24], (RecyclerView) bindings[20], (AppCompatTextView) bindings[10], (RecyclerView) bindings[13], (LightBrtBar) bindings[23], (RelativeLayout) bindings[3], (AppCompatTextView) bindings[16], (LayoutTitleDefaultBinding) bindings[12]);
        this.mDirtyFlags = -1L;
        this.deleteTv.setTag(null);
        this.factoryResetTv.setTag(null);
        this.gradientLayout.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[1];
        this.mboundView1 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[6];
        this.mboundView6 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        this.nameLayout.setTag(null);
        this.nameTv.setTag(null);
        this.reviewTv.setTag(null);
        this.timeLayout.setTag(null);
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
        setViewmodel((ActLightPlanDetailVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActLightPlanDetailBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActLightPlanDetailBinding
    public void setViewmodel(ActLightPlanDetailVM Viewmodel) {
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
        return onChangeViewmodelNameLiveData((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelNameLiveData(MutableLiveData<String> ViewmodelNameLiveData, int fieldId) {
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
        String str;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActLightPlanDetailVM actLightPlanDetailVM = this.mViewmodel;
        long j2 = 10 & j;
        BindingCommand<View> bindingCommand = null;
        long j3 = 13 & j;
        if (j3 != 0) {
            BindingCommand<View> bindingCommand2 = ((j & 12) == 0 || actLightPlanDetailVM == null) ? null : actLightPlanDetailVM.viewClick;
            MutableLiveData<String> mutableLiveData = actLightPlanDetailVM != null ? actLightPlanDetailVM.nameLiveData : null;
            updateLiveDataRegistration(0, mutableLiveData);
            str = mutableLiveData != null ? mutableLiveData.getValue() : null;
            bindingCommand = bindingCommand2;
        } else {
            str = null;
        }
        if ((j & 12) != 0) {
            ViewAdapter.onClickCommand(this.deleteTv, bindingCommand, false);
            ViewAdapter.onClickCommand(this.factoryResetTv, bindingCommand, false);
            ViewAdapter.onClickCommand(this.gradientLayout, bindingCommand, false);
            ViewAdapter.onClickCommand(this.nameLayout, bindingCommand, false);
            ViewAdapter.onClickCommand(this.reviewTv, bindingCommand, false);
            ViewAdapter.onClickCommand(this.timeLayout, bindingCommand, false);
        }
        if ((j & 8) != 0) {
            ViewAdapter.setTextBold(this.mboundView1, true);
            ViewAdapter.setTextBold(this.mboundView4, true);
            ViewAdapter.setTextBold(this.mboundView6, true);
            ViewAdapter.setTextBold(this.mboundView9, true);
        }
        if (j3 != 0) {
            TextViewBindingAdapter.setText(this.nameTv, str);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}