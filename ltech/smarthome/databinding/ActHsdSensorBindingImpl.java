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
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public class ActHsdSensorBindingImpl extends ActHsdSensorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(22);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{10}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 11);
        sparseIntArray.put(R.id.wave_view, 12);
        sparseIntArray.put(R.id.tv_state, 13);
        sparseIntArray.put(R.id.tv_time, 14);
        sparseIntArray.put(R.id.tv_lux, 15);
        sparseIntArray.put(R.id.tv_lux_tip, 16);
        sparseIntArray.put(R.id.cardview, 17);
        sparseIntArray.put(R.id.guideline2, 18);
        sparseIntArray.put(R.id.iv_sensitivity, 19);
        sparseIntArray.put(R.id.iv_battery, 20);
        sparseIntArray.put(R.id.tv_battery, 21);
    }

    public ActHsdSensorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }

    private ActHsdSensorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[9], (ConstraintLayout) bindings[17], (View) bindings[18], (ImageView) bindings[20], (ImageView) bindings[19], (AppCompatImageView) bindings[2], (ConstraintLayout) bindings[7], (ConstraintLayout) bindings[11], (RelativeLayout) bindings[5], (SpreadView) bindings[1], (LayoutTitleDefaultBinding) bindings[10], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[14], (RelativeLayout) bindings[12]);
        this.mDirtyFlags = -1L;
        this.btSmart.setTag(null);
        this.ivSensorClose.setTag(null);
        this.layoutBattery.setTag(null);
        this.layoutSenseSetting.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.spreadviewSensor.setTag(null);
        setContainedBinding(this.title);
        this.tvBatteryTip.setTag(null);
        this.tvEnvironmentLog.setTag(null);
        this.tvSenseRecord.setTag(null);
        this.tvSensitivity.setTag(null);
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
        if (51 == variableId) {
            setOffline((Boolean) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActHsdSensorBinding
    public void setOffline(Boolean Offline) {
        this.mOffline = Offline;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(51);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActHsdSensorBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActHsdSensorBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
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
        int i;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Boolean bool = this.mOffline;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        TitleDefault titleDefault = this.mTitle;
        long j2 = j & 9;
        if (j2 != 0) {
            boolean safeUnbox = ViewDataBinding.safeUnbox(bool);
            if (j2 != 0) {
                j |= safeUnbox ? 160L : 80L;
            }
            i = 8;
            i2 = safeUnbox ? 8 : 0;
            if (safeUnbox) {
                i = 0;
            }
        } else {
            i = 0;
            i2 = 0;
        }
        long j3 = 10 & j;
        long j4 = 12 & j;
        if ((8 & j) != 0) {
            ViewAdapter.setTextBold(this.btSmart, true);
            ViewAdapter.setTextBold(this.tvBatteryTip, true);
            ViewAdapter.setTextBold(this.tvEnvironmentLog, true);
            ViewAdapter.setTextBold(this.tvSenseRecord, true);
            ViewAdapter.setTextBold(this.tvSensitivity, true);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.btSmart, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBattery, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutSenseSetting, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvEnvironmentLog, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvSenseRecord, bindingCommand, false);
        }
        if ((j & 9) != 0) {
            this.ivSensorClose.setVisibility(i);
            this.spreadviewSensor.setVisibility(i2);
        }
        if (j4 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}