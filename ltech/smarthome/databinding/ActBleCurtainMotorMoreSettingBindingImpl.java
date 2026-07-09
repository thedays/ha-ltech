package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.curtain_motor.ActBleCurtainMotorMoreSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActBleCurtainMotorMoreSettingBindingImpl extends ActBleCurtainMotorMoreSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView25;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(39);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{26}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_motor_direction, 27);
        sparseIntArray.put(R.id.iv_adjust_go, 28);
        sparseIntArray.put(R.id.tv_open_type, 29);
        sparseIntArray.put(R.id.iv_motor_direction_name_go, 30);
        sparseIntArray.put(R.id.layout_set_soft_start, 31);
        sparseIntArray.put(R.id.layout_set_manually_pull, 32);
        sparseIntArray.put(R.id.layout_set_sync_manual_operation, 33);
        sparseIntArray.put(R.id.iv_tv_when_to_stop_go, 34);
        sparseIntArray.put(R.id.layout_set_limit_position, 35);
        sparseIntArray.put(R.id.layout_memorize_curtain_position, 36);
        sparseIntArray.put(R.id.tv_motor_speed, 37);
        sparseIntArray.put(R.id.iv_motor_speed_go, 38);
    }

    public ActBleCurtainMotorMoreSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 39, sIncludes, sViewsWithIds));
    }

    private ActBleCurtainMotorMoreSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6, (AppCompatButton) bindings[6], (AppCompatButton) bindings[3], (AppCompatImageView) bindings[28], (AppCompatImageView) bindings[27], (AppCompatImageView) bindings[30], (AppCompatImageView) bindings[38], (AppCompatImageView) bindings[34], (ConstraintLayout) bindings[4], (RelativeLayout) bindings[36], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[7], (RelativeLayout) bindings[22], (ConstraintLayout) bindings[24], (RelativeLayout) bindings[35], (RelativeLayout) bindings[32], (RelativeLayout) bindings[31], (RelativeLayout) bindings[33], (ConstraintLayout) bindings[15], (SwitchButton) bindings[12], (SwitchButton) bindings[21], (SwitchButton) bindings[19], (SwitchButton) bindings[10], (SwitchButton) bindings[14], (LayoutTitleDefaultBinding) bindings[26], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[16]);
        this.mDirtyFlags = -1L;
        this.btnAdjust.setTag(null);
        this.btnMotorDirection.setTag(null);
        this.layoutAdjust.setTag(null);
        this.layoutMotorDirection.setTag(null);
        this.layoutMotorOpenType.setTag(null);
        this.layoutMotorSpeed.setTag(null);
        this.layoutRemoteControl.setTag(null);
        this.layoutWhenToStop.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[25];
        this.mboundView25 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.sbManuallyPull.setTag(null);
        this.sbMemorizeCurtainPosition.setTag(null);
        this.sbSetLimitPosition.setTag(null);
        this.sbSoftStart.setTag(null);
        this.sbSyncManualOperation.setTag(null);
        setContainedBinding(this.title);
        this.tvAdjustTip.setTag(null);
        this.tvMemorizeCurtainPositionTip.setTag(null);
        this.tvMotorDirectionTip.setTag(null);
        this.tvMotorSpeedTip.setTag(null);
        this.tvSetCurtainMotorTip.setTag(null);
        this.tvSetLimitPositionTip.setTag(null);
        this.tvSetManuallyPullTip.setTag(null);
        this.tvSetSoftStartTip.setTag(null);
        this.tvSetSyncManualOperationTip.setTag(null);
        this.tvWhenToStop.setTag(null);
        this.tvWhenToStopTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 256L;
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
        setViewmodel((ActBleCurtainMotorMoreSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActBleCurtainMotorMoreSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActBleCurtainMotorMoreSettingBinding
    public void setViewmodel(ActBleCurtainMotorMoreSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 128;
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
        if (localFieldId == 0) {
            return onChangeViewmodelSyncManual((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelMemorizePosition((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelSoftStart((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelWhenMotorStop((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 4) {
            return onChangeViewmodelLimitPosition((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 5) {
            return false;
        }
        return onChangeViewmodelManuallyPull((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelSyncManual(MutableLiveData<Boolean> ViewmodelSyncManual, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelMemorizePosition(MutableLiveData<Boolean> ViewmodelMemorizePosition, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelSoftStart(MutableLiveData<Boolean> ViewmodelSoftStart, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelWhenMotorStop(MutableLiveData<Boolean> ViewmodelWhenMotorStop, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelLimitPosition(MutableLiveData<Boolean> ViewmodelLimitPosition, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelManuallyPull(MutableLiveData<Boolean> ViewmodelManuallyPull, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        long j2;
        long j3;
        long j4;
        BindingCommand<View> bindingCommand;
        String str;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActBleCurtainMotorMoreSettingVM actBleCurtainMotorMoreSettingVM = this.mViewmodel;
        if ((447 & j) != 0) {
            if ((j & 385) != 0) {
                MutableLiveData<Boolean> mutableLiveData = actBleCurtainMotorMoreSettingVM != null ? actBleCurtainMotorMoreSettingVM.syncManual : null;
                updateLiveDataRegistration(0, mutableLiveData);
                z = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            } else {
                z = false;
            }
            if ((j & 386) != 0) {
                MutableLiveData<Boolean> mutableLiveData2 = actBleCurtainMotorMoreSettingVM != null ? actBleCurtainMotorMoreSettingVM.memorizePosition : null;
                updateLiveDataRegistration(1, mutableLiveData2);
                z2 = ViewDataBinding.safeUnbox(mutableLiveData2 != null ? mutableLiveData2.getValue() : null);
            } else {
                z2 = false;
            }
            j4 = 400;
            if ((j & 388) != 0) {
                MutableLiveData<Boolean> mutableLiveData3 = actBleCurtainMotorMoreSettingVM != null ? actBleCurtainMotorMoreSettingVM.softStart : null;
                updateLiveDataRegistration(2, mutableLiveData3);
                z3 = ViewDataBinding.safeUnbox(mutableLiveData3 != null ? mutableLiveData3.getValue() : null);
            } else {
                z3 = false;
            }
            long j5 = j & 392;
            j2 = 392;
            if (j5 != 0) {
                MutableLiveData<Boolean> mutableLiveData4 = actBleCurtainMotorMoreSettingVM != null ? actBleCurtainMotorMoreSettingVM.whenMotorStop : null;
                updateLiveDataRegistration(3, mutableLiveData4);
                boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData4 != null ? mutableLiveData4.getValue() : null);
                if (j5 != 0) {
                    j |= safeUnbox ? 1024L : 512L;
                }
                str = this.tvWhenToStop.getResources().getString(safeUnbox ? R.string.app_str_stop_while_limit_position : R.string.app_str_stop_while_meet_resistance);
            } else {
                str = null;
            }
            if ((j & 400) != 0) {
                MutableLiveData<Boolean> mutableLiveData5 = actBleCurtainMotorMoreSettingVM != null ? actBleCurtainMotorMoreSettingVM.limitPosition : null;
                updateLiveDataRegistration(4, mutableLiveData5);
                z4 = ViewDataBinding.safeUnbox(mutableLiveData5 != null ? mutableLiveData5.getValue() : null);
            } else {
                z4 = false;
            }
            if ((j & 416) != 0) {
                MutableLiveData<Boolean> mutableLiveData6 = actBleCurtainMotorMoreSettingVM != null ? actBleCurtainMotorMoreSettingVM.manuallyPull : null;
                j3 = 388;
                updateLiveDataRegistration(5, mutableLiveData6);
                z5 = ViewDataBinding.safeUnbox(mutableLiveData6 != null ? mutableLiveData6.getValue() : null);
            } else {
                j3 = 388;
                z5 = false;
            }
            bindingCommand = ((j & 384) == 0 || actBleCurtainMotorMoreSettingVM == null) ? null : actBleCurtainMotorMoreSettingVM.viewClick;
        } else {
            j2 = 392;
            j3 = 388;
            j4 = 400;
            bindingCommand = null;
            str = null;
            z = false;
            z2 = false;
            z3 = false;
            z4 = false;
            z5 = false;
        }
        if ((j & 384) != 0) {
            ViewAdapter.onClickCommand(this.btnAdjust, bindingCommand, false);
            ViewAdapter.onClickCommand(this.btnMotorDirection, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutAdjust, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutMotorDirection, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutMotorOpenType, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutMotorSpeed, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutRemoteControl, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutWhenToStop, bindingCommand, false);
        }
        if ((256 & j) != 0) {
            ViewAdapter.setTextBold(this.mboundView25, true);
            ViewAdapter.setTextBold(this.tvAdjustTip, true);
            ViewAdapter.setTextBold(this.tvMemorizeCurtainPositionTip, true);
            ViewAdapter.setTextBold(this.tvMotorDirectionTip, true);
            ViewAdapter.setTextBold(this.tvMotorSpeedTip, true);
            ViewAdapter.setTextBold(this.tvSetCurtainMotorTip, true);
            ViewAdapter.setTextBold(this.tvSetLimitPositionTip, true);
            ViewAdapter.setTextBold(this.tvSetManuallyPullTip, true);
            ViewAdapter.setTextBold(this.tvSetSoftStartTip, true);
            ViewAdapter.setTextBold(this.tvSetSyncManualOperationTip, true);
            ViewAdapter.setTextBold(this.tvWhenToStopTip, true);
        }
        if ((j & 416) != 0) {
            ViewAdapter.setChecked(this.sbManuallyPull, z5);
        }
        if ((j & 386) != 0) {
            ViewAdapter.setChecked(this.sbMemorizeCurtainPosition, z2);
        }
        if ((j & j4) != 0) {
            ViewAdapter.setChecked(this.sbSetLimitPosition, z4);
        }
        if ((j & j3) != 0) {
            ViewAdapter.setChecked(this.sbSoftStart, z3);
        }
        if ((j & 385) != 0) {
            ViewAdapter.setChecked(this.sbSyncManualOperation, z);
        }
        if ((320 & j) != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & j2) != 0) {
            TextViewBindingAdapter.setText(this.tvWhenToStop, str);
        }
        executeBindingsOn(this.title);
    }
}