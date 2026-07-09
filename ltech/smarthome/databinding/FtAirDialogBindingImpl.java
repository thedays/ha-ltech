package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.device.central.tepanel.ActTePanelVM;

/* loaded from: classes3.dex */
public class FtAirDialogBindingImpl extends FtAirDialogBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_info, 1);
        sparseIntArray.put(R.id.layout_pm_info, 2);
        sparseIntArray.put(R.id.iv_pm_icon, 3);
        sparseIntArray.put(R.id.tv_pm_name, 4);
        sparseIntArray.put(R.id.tv_pm_value, 5);
        sparseIntArray.put(R.id.layout_temp_info, 6);
        sparseIntArray.put(R.id.iv_temp_icon, 7);
        sparseIntArray.put(R.id.tv_temp_name, 8);
        sparseIntArray.put(R.id.tv_temp_value, 9);
        sparseIntArray.put(R.id.rv_content, 10);
    }

    public FtAirDialogBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private FtAirDialogBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[7], (LinearLayout) bindings[1], (LinearLayout) bindings[2], (LinearLayout) bindings[6], (RecyclerView) bindings[10], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[9]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActTePanelVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtAirDialogBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
    }

    @Override // com.ltech.smarthome.databinding.FtAirDialogBinding
    public void setViewmodel(ActTePanelVM Viewmodel) {
        this.mViewmodel = Viewmodel;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}