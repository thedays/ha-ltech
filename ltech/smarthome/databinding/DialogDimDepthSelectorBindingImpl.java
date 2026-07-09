package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public class DialogDimDepthSelectorBindingImpl extends DialogDimDepthSelectorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.rv_content, 4);
        sparseIntArray.put(R.id.sb_dim_depth, 5);
        sparseIntArray.put(R.id.layout_depth_view, 6);
        sparseIntArray.put(R.id.view_depth_1, 7);
        sparseIntArray.put(R.id.view_depth_2, 8);
        sparseIntArray.put(R.id.view_depth_3, 9);
        sparseIntArray.put(R.id.view_depth_4, 10);
        sparseIntArray.put(R.id.view_depth_5, 11);
        sparseIntArray.put(R.id.view_depth_6, 12);
        sparseIntArray.put(R.id.view_depth_7, 13);
        sparseIntArray.put(R.id.view_depth_8, 14);
        sparseIntArray.put(R.id.view_depth_9, 15);
        sparseIntArray.put(R.id.view_depth_10, 16);
        sparseIntArray.put(R.id.view_depth_11, 17);
        sparseIntArray.put(R.id.view_depth_12, 18);
        sparseIntArray.put(R.id.view_depth_13, 19);
        sparseIntArray.put(R.id.view_depth_14, 20);
        sparseIntArray.put(R.id.view_depth_15, 21);
        sparseIntArray.put(R.id.view_depth_16, 22);
        sparseIntArray.put(R.id.view_depth_17, 23);
        sparseIntArray.put(R.id.view_depth_18, 24);
        sparseIntArray.put(R.id.view_depth_19, 25);
        sparseIntArray.put(R.id.view_depth_20, 26);
        sparseIntArray.put(R.id.view_depth_21, 27);
        sparseIntArray.put(R.id.layout_depth_num, 28);
        sparseIntArray.put(R.id.tv_num_1, 29);
        sparseIntArray.put(R.id.tv_num_2, 30);
        sparseIntArray.put(R.id.tv_num_3, 31);
        sparseIntArray.put(R.id.tv_num_4, 32);
        sparseIntArray.put(R.id.tv_num_5, 33);
        sparseIntArray.put(R.id.layout_depth_tip, 34);
        sparseIntArray.put(R.id.tv_tip_1, 35);
        sparseIntArray.put(R.id.tv_tip_2, 36);
        sparseIntArray.put(R.id.tv_tip_3, 37);
        sparseIntArray.put(R.id.tv_tip_4, 38);
        sparseIntArray.put(R.id.tv_tip_5, 39);
    }

    public DialogDimDepthSelectorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 40, sIncludes, sViewsWithIds));
    }

    private DialogDimDepthSelectorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatTextView) bindings[1], (ConstraintLayout) bindings[0], (ConstraintLayout) bindings[28], (ConstraintLayout) bindings[34], (ConstraintLayout) bindings[6], (RecyclerView) bindings[4], (HorizontalSeekBar) bindings[5], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[33], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[36], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[38], (AppCompatTextView) bindings[39], (View) bindings[7], (View) bindings[16], (View) bindings[17], (View) bindings[18], (View) bindings[19], (View) bindings[20], (View) bindings[21], (View) bindings[22], (View) bindings[23], (View) bindings[24], (View) bindings[25], (View) bindings[8], (View) bindings[26], (View) bindings[27], (View) bindings[9], (View) bindings[10], (View) bindings[11], (View) bindings[12], (View) bindings[13], (View) bindings[14], (View) bindings[15]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView33.setTag(null);
        this.layoutBg.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.DialogDimDepthSelectorBinding
    public void setContent(ObservableField<String> Content) {
        this.mContent = Content;
    }

    @Override // com.ltech.smarthome.databinding.DialogDimDepthSelectorBinding
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
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvSave, bindingCommand, false);
        }
    }
}