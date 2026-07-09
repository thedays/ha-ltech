package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActSelectSonosActionBindingImpl extends ActSelectSonosActionBinding {
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
        sparseIntArray.put(R.id.rv_content, 4);
    }

    public ActSelectSonosActionBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ActSelectSonosActionBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (RecyclerView) bindings[4], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCancel.setTag(null);
        this.tvConfirm.setTag(null);
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
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSonosActionBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        BindingCommand bindingCommand;
        String str2;
        String str3;
        BindingCommand bindingCommand2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        long j2 = 3 & j;
        if (j2 == 0 || titleDefault == null) {
            str = null;
            bindingCommand = null;
            str2 = null;
            str3 = null;
            bindingCommand2 = null;
        } else {
            str = titleDefault.getTitle();
            str2 = titleDefault.getBackString();
            str3 = titleDefault.getEditString();
            bindingCommand2 = titleDefault.getBackAction();
            bindingCommand = titleDefault.getEditAction();
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.tvCancel, str2);
            ViewAdapter.onClickCommand(this.tvCancel, bindingCommand2, false);
            TextViewBindingAdapter.setText(this.tvConfirm, str3);
            ViewAdapter.onClickCommand(this.tvConfirm, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvTitle, str);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}