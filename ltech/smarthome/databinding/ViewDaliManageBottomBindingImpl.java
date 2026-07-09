package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class ViewDaliManageBottomBindingImpl extends ViewDaliManageBottomBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_change_room, 4);
        sparseIntArray.put(R.id.tv_change_room, 5);
        sparseIntArray.put(R.id.iv_modify_param, 6);
        sparseIntArray.put(R.id.tv_start_locate, 7);
    }

    public ViewDaliManageBottomBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ViewDaliManageBottomBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (LinearLayout) bindings[2], (LinearLayout) bindings[1], (LinearLayout) bindings[3], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[6], (ConstraintLayout) bindings[0], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[7]);
        this.mDirtyFlags = -1L;
        this.btnChangeRoom.setTag(null);
        this.btnFindDevice.setTag(null);
        this.btnModifyParam.setTag(null);
        this.layoutDeviceManage.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ViewDaliManageBottomBinding
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
        if ((j & 3) != 0) {
            ViewAdapter.onClickCommand(this.btnChangeRoom, bindingCommand, false);
            ViewAdapter.onClickCommand(this.btnFindDevice, bindingCommand, false);
            ViewAdapter.onClickCommand(this.btnModifyParam, bindingCommand, false);
        }
    }
}