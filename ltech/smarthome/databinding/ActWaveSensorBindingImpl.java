package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.microwave_sensor.ActWaveSensorVM;
import com.ltech.smarthome.view.SpreadView;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActWaveSensorBindingImpl extends ActWaveSensorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView15;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(35);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{18}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 19);
        sparseIntArray.put(R.id.layout_content, 20);
        sparseIntArray.put(R.id.wave_view, 21);
        sparseIntArray.put(R.id.tv_state, 22);
        sparseIntArray.put(R.id.cardview, 23);
        sparseIntArray.put(R.id.iv_illumincance, 24);
        sparseIntArray.put(R.id.guideline, 25);
        sparseIntArray.put(R.id.iv_sensitivity, 26);
        sparseIntArray.put(R.id.layout_sensor_param_1, 27);
        sparseIntArray.put(R.id.rv_sensor_params_1, 28);
        sparseIntArray.put(R.id.layout_sensor_param_2, 29);
        sparseIntArray.put(R.id.sb_sensor_2, 30);
        sparseIntArray.put(R.id.rv_sensor_params_2, 31);
        sparseIntArray.put(R.id.layout_sensor_param_3, 32);
        sparseIntArray.put(R.id.sb_sensor_3, 33);
        sparseIntArray.put(R.id.rv_sensor_params_3, 34);
    }

    public ActWaveSensorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 35, sIncludes, sViewsWithIds));
    }

    private ActWaveSensorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatTextView) bindings[17], (ConstraintLayout) bindings[23], (View) bindings[25], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[14], (ImageView) bindings[24], (ImageView) bindings[26], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[20], (RelativeLayout) bindings[4], (RelativeLayout) bindings[6], (LinearLayout) bindings[27], (LinearLayout) bindings[29], (LinearLayout) bindings[32], (SmartRefreshLayout) bindings[19], (RecyclerView) bindings[28], (RecyclerView) bindings[31], (RecyclerView) bindings[34], (SwitchButton) bindings[30], (SwitchButton) bindings[33], (SpreadView) bindings[2], (LayoutTitleDefaultBinding) bindings[18], (AppCompatTextView) bindings[5], (LinearLayout) bindings[1], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[22], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[16], (RelativeLayout) bindings[21]);
        this.mDirtyFlags = -1L;
        this.btSmart.setTag(null);
        this.ivArrow1.setTag(null);
        this.ivArrow2.setTag(null);
        this.ivArrow3.setTag(null);
        this.ivSensorClose.setTag(null);
        this.layoutIllumilance.setTag(null);
        this.layoutSensitivity.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[15];
        this.mboundView15 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        this.spreadviewSensor.setTag(null);
        setContainedBinding(this.title);
        this.tvIlluminance.setTag(null);
        this.tvOfflineTip.setTag(null);
        this.tvSensitivity.setTag(null);
        this.tvTime1.setTag(null);
        this.tvTime2.setTag(null);
        this.tvTime3.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 128L;
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
        if (69 == variableId) {
            setSensitivity((String) variable);
            return true;
        }
        if (38 == variableId) {
            setIlluminance((String) variable);
            return true;
        }
        if (76 == variableId) {
            setStateOn((Boolean) variable);
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
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActWaveSensorVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorBinding
    public void setSensitivity(String Sensitivity) {
        this.mSensitivity = Sensitivity;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(69);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorBinding
    public void setIlluminance(String Illuminance) {
        this.mIlluminance = Illuminance;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(38);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorBinding
    public void setStateOn(Boolean StateOn) {
        this.mStateOn = StateOn;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(76);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorBinding
    public void setViewmodel(ActWaveSensorVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 64;
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
        return onChangeViewmodelOffline((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelOffline(MutableLiveData<Boolean> ViewmodelOffline, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x007a, code lost:
    
        if (r10 != false) goto L44;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00b6  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActWaveSensorBindingImpl.executeBindings():void");
    }
}