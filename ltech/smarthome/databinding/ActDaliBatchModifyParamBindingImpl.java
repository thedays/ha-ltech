package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActDaliBatchModifyParamVM;
import com.ltech.smarthome.ui.device.dalipro.CgdActionSelectView;
import com.ltech.smarthome.view.DaliTextSeekBarView;
import com.ltech.smarthome.view.DaliTextSeekBarViewNew;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public class ActDaliBatchModifyParamBindingImpl extends ActDaliBatchModifyParamBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(43);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{5}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.act_add_device_scroll, 6);
        sparseIntArray.put(R.id.iv_scene_name_go, 7);
        sparseIntArray.put(R.id.layout_dim_curve, 8);
        sparseIntArray.put(R.id.tv_dim_curve, 9);
        sparseIntArray.put(R.id.radio_linear, 10);
        sparseIntArray.put(R.id.radio_log, 11);
        sparseIntArray.put(R.id.iv_linear_log, 12);
        sparseIntArray.put(R.id.layout_dim_range, 13);
        sparseIntArray.put(R.id.seekbar_min_btr, 14);
        sparseIntArray.put(R.id.seekbar_max_btr, 15);
        sparseIntArray.put(R.id.layout_fade_time, 16);
        sparseIntArray.put(R.id.seekbar_fade_time, 17);
        sparseIntArray.put(R.id.tv_extend_fade_time, 18);
        sparseIntArray.put(R.id.tv_fade_time_value, 19);
        sparseIntArray.put(R.id.tv_fade_time_x, 20);
        sparseIntArray.put(R.id.tv_fade_time_times, 21);
        sparseIntArray.put(R.id.tv_fade_time_equal, 22);
        sparseIntArray.put(R.id.tv_fade_time_total, 23);
        sparseIntArray.put(R.id.layout_ct_range, 24);
        sparseIntArray.put(R.id.radio_ct_default, 25);
        sparseIntArray.put(R.id.radio_ct_custom, 26);
        sparseIntArray.put(R.id.sb_brt, 27);
        sparseIntArray.put(R.id.layout_brt, 28);
        sparseIntArray.put(R.id.tv_k_right, 29);
        sparseIntArray.put(R.id.tv_k_left, 30);
        sparseIntArray.put(R.id.layout_light_on_state, 31);
        sparseIntArray.put(R.id.radio_light_on_default, 32);
        sparseIntArray.put(R.id.radio_light_on_not_light, 33);
        sparseIntArray.put(R.id.radio_light_on_memory, 34);
        sparseIntArray.put(R.id.radio_light_on_custom, 35);
        sparseIntArray.put(R.id.light_on_action_view, 36);
        sparseIntArray.put(R.id.layout_failure_state, 37);
        sparseIntArray.put(R.id.radio_failure_default, 38);
        sparseIntArray.put(R.id.radio_failure_not_light, 39);
        sparseIntArray.put(R.id.radio_failure_memory, 40);
        sparseIntArray.put(R.id.radio_failure_custom, 41);
        sparseIntArray.put(R.id.failure_action_view, 42);
    }

    public ActDaliBatchModifyParamBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 43, sIncludes, sViewsWithIds));
    }

    private ActDaliBatchModifyParamBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (NestedScrollView) bindings[6], (CgdActionSelectView) bindings[42], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[7], (ConstraintLayout) bindings[28], (LinearLayout) bindings[24], (ConstraintLayout) bindings[8], (LinearLayout) bindings[13], (LinearLayout) bindings[2], (LinearLayout) bindings[16], (LinearLayout) bindings[37], (LinearLayout) bindings[31], (LinearLayout) bindings[3], (ConstraintLayout) bindings[1], (CgdActionSelectView) bindings[36], (RadioImageTextView) bindings[26], (RadioImageTextView) bindings[25], (RadioImageTextView) bindings[41], (RadioImageTextView) bindings[38], (RadioImageTextView) bindings[40], (RadioImageTextView) bindings[39], (RadioImageTextView) bindings[35], (RadioImageTextView) bindings[32], (RadioImageTextView) bindings[34], (RadioImageTextView) bindings[33], (RadioImageTextView) bindings[10], (RadioImageTextView) bindings[11], (RangeSeekBar) bindings[27], (DaliTextSeekBarViewNew) bindings[17], (DaliTextSeekBarView) bindings[15], (DaliTextSeekBarView) bindings[14], (LayoutTitleDefaultBinding) bindings[5], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[22], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[29]);
        this.mDirtyFlags = -1L;
        this.layoutExtendFadeTime.setTag(null);
        this.layoutSave.setTag(null);
        this.layoutSelectModifyParam.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvBottom.setTag(null);
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
        setViewmodel((ActDaliBatchModifyParamVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDaliBatchModifyParamBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDaliBatchModifyParamBinding
    public void setViewmodel(ActDaliBatchModifyParamVM Viewmodel) {
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
        ActDaliBatchModifyParamVM actDaliBatchModifyParamVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || actDaliBatchModifyParamVM == null) ? null : actDaliBatchModifyParamVM.clickCommand;
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.layoutExtendFadeTime, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutSave, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutSelectModifyParam, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvBottom, true);
        }
        executeBindingsOn(this.title);
    }
}