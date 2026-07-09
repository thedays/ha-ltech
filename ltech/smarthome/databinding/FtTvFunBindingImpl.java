package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.DirectionView;

/* loaded from: classes3.dex */
public class FtTvFunBindingImpl extends FtTvFunBinding {
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
        sparseIntArray.put(R.id.directionView, 8);
        sparseIntArray.put(R.id.tv_channel, 9);
        sparseIntArray.put(R.id.appCompatTextView40, 10);
    }

    public FtTvFunBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private FtTvFunBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[10], (DirectionView) bindings[8], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[4], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[5]);
        this.mDirtyFlags = -1L;
        this.ivChPlus.setTag(null);
        this.ivChReduce.setTag(null);
        this.ivHome.setTag(null);
        this.ivMenu.setTag(null);
        this.ivVolPlus.setTag(null);
        this.ivVolReduce.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvSignal.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.FtTvFunBinding
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
            ViewAdapter.onClickCommand(this.ivChPlus, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivChReduce, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivHome, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivMenu, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivVolPlus, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivVolReduce, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvSignal, bindingCommand, false);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvSignal, true);
        }
    }
}