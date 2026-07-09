package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActWaveSensorEffectPeriodBindingImpl extends ActWaveSensorEffectPeriodBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(17);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view3, 9);
        sparseIntArray.put(R.id.tv_start_time_tip, 10);
        sparseIntArray.put(R.id.tv_end_time_tip, 11);
        sparseIntArray.put(R.id.view22, 12);
        sparseIntArray.put(R.id.appCompatImageView18, 13);
        sparseIntArray.put(R.id.tv_trigger_time, 14);
        sparseIntArray.put(R.id.appCompatImageView17, 15);
        sparseIntArray.put(R.id.tv_repeat_time, 16);
    }

    public ActWaveSensorEffectPeriodBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }

    private ActWaveSensorEffectPeriodBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[15], (AppCompatImageView) bindings[13], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[3], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[14], (View) bindings[6], (View) bindings[4], (View) bindings[12], (View) bindings[9]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView24.setTag(null);
        this.appCompatTextView25.setTag(null);
        this.date.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvEndTime.setTag(null);
        this.tvStartTime.setTag(null);
        this.vSelectTime.setTag(null);
        this.vSetTriggerTimes.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        if (27 == variableId) {
            setEndTime((String) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (75 != variableId) {
            return false;
        }
        setStartTime((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorEffectPeriodBinding
    public void setEndTime(String EndTime) {
        this.mEndTime = EndTime;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(27);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorEffectPeriodBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorEffectPeriodBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorEffectPeriodBinding
    public void setStartTime(String StartTime) {
        this.mStartTime = StartTime;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(75);
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
        String str = this.mEndTime;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        TitleDefault titleDefault = this.mTitle;
        String str2 = this.mStartTime;
        long j2 = 17 & j;
        long j3 = 18 & j;
        long j4 = 20 & j;
        long j5 = 24 & j;
        if ((j & 16) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView24, true);
            ViewAdapter.setTextBold(this.appCompatTextView25, true);
            ViewAdapter.setTextBold(this.date, true);
            ViewAdapter.setTextBold(this.tvEndTime, true);
            ViewAdapter.setTextBold(this.tvStartTime, true);
        }
        if (j4 != 0) {
            this.title.setTitle(titleDefault);
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.tvEndTime, str);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.tvEndTime, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvStartTime, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vSelectTime, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vSetTriggerTimes, bindingCommand, false);
        }
        if (j5 != 0) {
            TextViewBindingAdapter.setText(this.tvStartTime, str2);
        }
        executeBindingsOn(this.title);
    }
}