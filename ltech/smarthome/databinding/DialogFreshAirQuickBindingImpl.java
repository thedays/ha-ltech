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
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class DialogFreshAirQuickBindingImpl extends DialogFreshAirQuickBinding {
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
        sparseIntArray.put(R.id.sb, 3);
        sparseIntArray.put(R.id.iv_send_tip, 4);
        sparseIntArray.put(R.id.divider_line, 5);
        sparseIntArray.put(R.id.layout_info, 6);
        sparseIntArray.put(R.id.layout_pm_info, 7);
        sparseIntArray.put(R.id.iv_pm_icon, 8);
        sparseIntArray.put(R.id.tv_pm_name, 9);
        sparseIntArray.put(R.id.tv_pm_value, 10);
        sparseIntArray.put(R.id.layout_temp_info, 11);
        sparseIntArray.put(R.id.iv_temp_icon, 12);
        sparseIntArray.put(R.id.tv_temp_name, 13);
        sparseIntArray.put(R.id.tv_temp_value, 14);
        sparseIntArray.put(R.id.rv_content, 15);
    }

    public DialogFreshAirQuickBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private DialogFreshAirQuickBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (View) bindings[5], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[12], (LinearLayout) bindings[6], (LinearLayout) bindings[7], (LinearLayout) bindings[11], (RecyclerView) bindings[15], (SwitchButton) bindings[3], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.ivDeviceMore.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvTitle.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.DialogFreshAirQuickBinding
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
            ViewAdapter.onClickCommand(this.ivDeviceMore, bindingCommand, false);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}