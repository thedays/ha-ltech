package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.RadioImageTextView;

/* loaded from: classes3.dex */
public class DialogAddSceneBindingImpl extends DialogAddSceneBinding {
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
        sparseIntArray.put(R.id.et_name, 7);
        sparseIntArray.put(R.id.radio_local, 8);
        sparseIntArray.put(R.id.radio_cloud, 9);
        sparseIntArray.put(R.id.view_divider, 10);
        sparseIntArray.put(R.id.pick_view_floor, 11);
        sparseIntArray.put(R.id.picker_view_room, 12);
    }

    public DialogAddSceneBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private DialogAddSceneBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatEditText) bindings[7], (AppCompatImageView) bindings[5], (ConstraintLayout) bindings[0], (RecyclerView) bindings[11], (RecyclerView) bindings[12], (RadioImageTextView) bindings[9], (RadioImageTextView) bindings[8], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[6], (View) bindings[10]);
        this.mDirtyFlags = -1L;
        this.ivClear.setTag(null);
        this.layoutBg.setTag(null);
        this.tvCancel.setTag(null);
        this.tvLabel.setTag(null);
        this.tvSave.setTag(null);
        this.tvTitle.setTag(null);
        this.tvType.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.DialogAddSceneBinding
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
            ViewAdapter.onClickCommand(this.ivClear, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvSave, bindingCommand, false);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvLabel, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
            ViewAdapter.setTextBold(this.tvType, true);
        }
    }
}