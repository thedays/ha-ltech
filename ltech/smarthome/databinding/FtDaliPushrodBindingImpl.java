package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.EditTextSeekBarView;
import com.ltech.smarthome.view.TextSeekBarView;

/* loaded from: classes3.dex */
public class FtDaliPushrodBindingImpl extends FtDaliPushrodBinding {
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
        sparseIntArray.put(R.id.seekbar_brt, 3);
        sparseIntArray.put(R.id.ct_seekbar, 4);
        sparseIntArray.put(R.id.seekbar_r, 5);
        sparseIntArray.put(R.id.seekbar_g, 6);
        sparseIntArray.put(R.id.seekbar_b, 7);
    }

    public FtDaliPushrodBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private FtDaliPushrodBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (TextSeekBarView) bindings[4], (EditTextSeekBarView) bindings[7], (TextSeekBarView) bindings[3], (EditTextSeekBarView) bindings[6], (EditTextSeekBarView) bindings[5], (AppCompatButton) bindings[1], (AppCompatButton) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvOff.setTag(null);
        this.tvOn.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.FtDaliPushrodBinding
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
        long j2 = 3 & j;
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvOff, true);
            ViewAdapter.setTextBold(this.tvOn, true);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.tvOff, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvOn, bindingCommand, false);
        }
    }
}