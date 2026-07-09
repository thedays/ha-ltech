package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.DaliTextSeekBarViewNew;

/* loaded from: classes3.dex */
public class DialogSelectDimFadeTimeBindingImpl extends DialogSelectDimFadeTimeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.seekbar_fade_time, 5);
        sparseIntArray.put(R.id.tv_extend_fade_time, 6);
        sparseIntArray.put(R.id.tv_fade_time_value, 7);
        sparseIntArray.put(R.id.tv_fade_time_x, 8);
        sparseIntArray.put(R.id.tv_fade_time_times, 9);
        sparseIntArray.put(R.id.tv_fade_time_equal, 10);
        sparseIntArray.put(R.id.tv_fade_time_total, 11);
    }

    public DialogSelectDimFadeTimeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private DialogSelectDimFadeTimeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatTextView) bindings[1], (ConstraintLayout) bindings[0], (LinearLayout) bindings[4], (DaliTextSeekBarViewNew) bindings[5], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView33.setTag(null);
        this.layoutBg.setTag(null);
        this.layoutExtendFadeTime.setTag(null);
        this.tvCancel.setTag(null);
        this.tvSave.setTag(null);
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
        if (13 == variableId) {
            setContent((ObservableField) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogSelectDimFadeTimeBinding
    public void setContent(ObservableField<String> Content) {
        this.mContent = Content;
    }

    @Override // com.ltech.smarthome.databinding.DialogSelectDimFadeTimeBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeContent((ObservableField) object, fieldId);
    }

    private boolean onChangeContent(ObservableField<String> Content, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        long j2 = 6 & j;
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView33, true);
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.layoutExtendFadeTime, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvSave, bindingCommand, false);
        }
    }
}