package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class DialogProgressBindingImpl extends DialogProgressBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.bkg, 3);
        sparseIntArray.put(R.id.txt_dialog_title, 4);
        sparseIntArray.put(R.id.txt_dialog_content_tip, 5);
        sparseIntArray.put(R.id.progress_bar, 6);
        sparseIntArray.put(R.id.tv_progress, 7);
        sparseIntArray.put(R.id.box_button, 8);
        sparseIntArray.put(R.id.btn_selectOther, 9);
    }

    public DialogProgressBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }

    private DialogProgressBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (RelativeLayout) bindings[3], (LinearLayout) bindings[8], (RelativeLayout) bindings[0], (TextView) bindings[1], (TextView) bindings[9], (TextView) bindings[2], (ProgressBar) bindings[6], (TextView) bindings[7], (TextView) bindings[5], (TextView) bindings[4]);
        this.mDirtyFlags = -1L;
        this.boxRoot.setTag(null);
        this.btnSelectNegative.setTag(null);
        this.btnSelectPositive.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.DialogProgressBinding
    public void setContent(ObservableField<String> Content) {
        this.mContent = Content;
    }

    @Override // com.ltech.smarthome.databinding.DialogProgressBinding
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
        if ((j & 6) != 0) {
            ViewAdapter.onClickCommand(this.btnSelectNegative, bindingCommand, false);
            ViewAdapter.onClickCommand(this.btnSelectPositive, bindingCommand, false);
        }
    }
}