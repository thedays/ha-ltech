package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class LayoutTitleTranBindingImpl extends LayoutTitleTranBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final Toolbar mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public LayoutTitleTranBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private LayoutTitleTranBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.ivBack.setTag(null);
        this.ivEdit.setTag(null);
        Toolbar toolbar = (Toolbar) bindings[0];
        this.mboundView0 = toolbar;
        toolbar.setTag(null);
        this.tvEdit.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.LayoutTitleTranBinding
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
        long j2;
        String str;
        String str2;
        BindingCommand bindingCommand;
        int i;
        int i2;
        int i3;
        int i4;
        BindingCommand bindingCommand2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        long j3 = j & 3;
        BindingCommand bindingCommand3 = null;
        String str3 = null;
        if (j3 != 0) {
            if (titleDefault != null) {
                i3 = titleDefault.getBackImageRes();
                str3 = titleDefault.getTitle();
                str = titleDefault.getEditString();
                bindingCommand2 = titleDefault.getBackAction();
                bindingCommand = titleDefault.getEditAction();
                i = titleDefault.getEditImageRes();
            } else {
                str = null;
                bindingCommand2 = null;
                bindingCommand = null;
                i = 0;
                i3 = 0;
            }
            boolean z = i3 != 0;
            boolean z2 = i != 0;
            if (j3 != 0) {
                j |= z ? 8L : 4L;
            }
            if ((j & 3) != 0) {
                j |= z2 ? 32L : 16L;
            }
            i2 = z ? 0 : 8;
            j2 = 0;
            str2 = str3;
            bindingCommand3 = bindingCommand2;
            i4 = z2 ? 0 : 8;
        } else {
            j2 = 0;
            str = null;
            str2 = null;
            bindingCommand = null;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        if ((3 & j) != j2) {
            ViewAdapter.setSrc(this.ivBack, i3);
            this.ivBack.setVisibility(i2);
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand3, false);
            ViewAdapter.setSrc(this.ivEdit, i);
            this.ivEdit.setVisibility(i4);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvEdit, str);
            ViewAdapter.onClickCommand(this.tvEdit, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvTitle, str2);
        }
        if ((j & 2) != j2) {
            ViewAdapter.setTextBold(this.tvEdit, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}