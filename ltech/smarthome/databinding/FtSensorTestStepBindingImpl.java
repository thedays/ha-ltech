package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.CountDownProgressBar;

/* loaded from: classes3.dex */
public class FtSensorTestStepBindingImpl extends FtSensorTestStepBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView2;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_steps, 5);
        sparseIntArray.put(R.id.tv_steps, 6);
        sparseIntArray.put(R.id.bar_countdown, 7);
        sparseIntArray.put(R.id.iv_step, 8);
        sparseIntArray.put(R.id.layout_success_tip, 9);
        sparseIntArray.put(R.id.tv_sensitivity, 10);
        sparseIntArray.put(R.id.iv_sensitivity_go, 11);
        sparseIntArray.put(R.id.tv_fail_tip, 12);
        sparseIntArray.put(R.id.layout_bottom, 13);
    }

    public FtSensorTestStepBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private FtSensorTestStepBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CountDownProgressBar) bindings[7], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[3], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[8], (ImageView) bindings[5], (LinearLayout) bindings[13], (ConstraintLayout) bindings[1], (LinearLayout) bindings[9], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[6]);
        this.mDirtyFlags = -1L;
        this.btNext.setTag(null);
        this.btPrevious.setTag(null);
        this.layoutSetSensitivity.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView;
        appCompatTextView.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtSensorTestStepBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        if ((3 & j) != 0) {
            ViewAdapter.onClickCommand(this.btNext, bindingCommand, false);
            ViewAdapter.onClickCommand(this.btPrevious, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutSetSensitivity, bindingCommand, false);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.mboundView2, true);
        }
    }
}