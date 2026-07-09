package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;

/* loaded from: classes3.dex */
public class ItemTextAddBindingImpl extends ItemTextAddBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final AppCompatTextView mboundView2;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemTextAddBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private ItemTextAddBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[1], (RelativeLayout) bindings[0]);
        this.mDirtyFlags = -1L;
        this.ivIcon.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.rlAdd.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        if (40 == variableId) {
            setItem((String) variable);
            return true;
        }
        if (81 == variableId) {
            setTextColor((Integer) variable);
            return true;
        }
        if (39 == variableId) {
            setImageRes((Integer) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemTextAddBinding
    public void setItem(String Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemTextAddBinding
    public void setTextColor(Integer TextColor) {
        this.mTextColor = TextColor;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(81);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemTextAddBinding
    public void setImageRes(Integer ImageRes) {
        this.mImageRes = ImageRes;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(39);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemTextAddBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
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
        String str = this.mItem;
        Integer num = this.mTextColor;
        Integer num2 = this.mImageRes;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        long j2 = 17 & j;
        long j3 = 18 & j;
        int safeUnbox = j3 != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        long j4 = 20 & j;
        int safeUnbox2 = j4 != 0 ? ViewDataBinding.safeUnbox(num2) : 0;
        long j5 = 24 & j;
        if (j4 != 0) {
            ViewAdapter.setBackground(this.ivIcon, safeUnbox2);
        }
        if ((j & 16) != 0) {
            ViewAdapter.setTextBold(this.mboundView2, true);
        }
        if (j3 != 0) {
            this.mboundView2.setTextColor(safeUnbox);
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.mboundView2, str);
        }
        if (j5 != 0) {
            ViewAdapter.onClickCommand(this.rlAdd, bindingCommand, false);
        }
    }
}