package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class LayoutTitleIrBindingImpl extends LayoutTitleIrBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final Toolbar mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_tip, 6);
    }

    public LayoutTitleIrBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private LayoutTitleIrBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[3], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[6], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView8.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.LayoutTitleIrBinding
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
        BindingCommand bindingCommand;
        String str;
        String str2;
        BindingCommand bindingCommand2;
        int i;
        int i2;
        int i3;
        int i4;
        String str3;
        String str4;
        BindingCommand bindingCommand3;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        long j3 = j & 3;
        String str5 = null;
        if (j3 != 0) {
            if (titleDefault != null) {
                str5 = titleDefault.getEditString();
                i4 = titleDefault.getBackImageRes();
                str3 = titleDefault.getTitle();
                str4 = titleDefault.getBackString();
                bindingCommand2 = titleDefault.getBackAction();
                bindingCommand3 = titleDefault.getEditAction();
                i = titleDefault.getEditImageRes();
            } else {
                str3 = null;
                str4 = null;
                bindingCommand2 = null;
                bindingCommand3 = null;
                i = 0;
                i4 = 0;
            }
            boolean z = i4 != 0;
            boolean z2 = i != 0;
            if (j3 != 0) {
                j |= z ? 8L : 4L;
            }
            if ((j & 3) != 0) {
                j |= z2 ? 32L : 16L;
            }
            int i5 = z ? 0 : 8;
            String str6 = str4;
            str2 = str3;
            bindingCommand = bindingCommand3;
            str = str5;
            str5 = str6;
            i2 = z2 ? 0 : 8;
            i3 = i5;
            j2 = 0;
        } else {
            j2 = 0;
            bindingCommand = null;
            str = null;
            str2 = null;
            bindingCommand2 = null;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        if ((3 & j) != j2) {
            TextViewBindingAdapter.setText(this.appCompatTextView8, str5);
            ViewAdapter.onClickCommand(this.appCompatTextView8, bindingCommand2, false);
            ViewAdapter.setSrc(this.ivBack, i4);
            this.ivBack.setVisibility(i3);
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand2, false);
            ViewAdapter.setSrc(this.ivEdit, i);
            this.ivEdit.setVisibility(i2);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvEdit, str);
            ViewAdapter.onClickCommand(this.tvEdit, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvTitle, str2);
        }
        if ((j & 2) != j2) {
            ViewAdapter.setTextBold(this.appCompatTextView8, true);
            ViewAdapter.setTextBold(this.tvEdit, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}