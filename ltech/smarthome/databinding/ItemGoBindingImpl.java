package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.item.GoItem;

/* loaded from: classes3.dex */
public class ItemGoBindingImpl extends ItemGoBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemGoBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ItemGoBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.ivGo.setTag(null);
        this.ivIcon.setTag(null);
        this.ivTipDot.setTag(null);
        this.ivTipText.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvMain.setTag(null);
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
        if (40 != variableId) {
            return false;
        }
        setItem((GoItem) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemGoBinding
    public void setItem(GoItem Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        BindingCommand bindingCommand;
        int i;
        int i2;
        int i3;
        int i4;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        GoItem goItem = this.mItem;
        long j2 = j & 3;
        String str2 = null;
        if (j2 != 0) {
            if (goItem != null) {
                i = goItem.getImageRes();
                str2 = goItem.getSubText();
                i2 = goItem.getGoRes();
                i4 = goItem.getSubImageRes();
                bindingCommand = goItem.getAction();
                str = goItem.getMainText();
            } else {
                str = null;
                bindingCommand = null;
                i = 0;
                i2 = 0;
                i4 = 0;
            }
            boolean z = i == 0;
            if (j2 != 0) {
                j |= z ? 8L : 4L;
            }
            i3 = z ? 8 : 0;
        } else {
            str = null;
            bindingCommand = null;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        if ((3 & j) != 0) {
            ViewAdapter.setSrc(this.ivGo, i2);
            ViewAdapter.setBackground(this.ivIcon, i);
            this.ivIcon.setVisibility(i3);
            ViewAdapter.setSrc(this.ivTipDot, i4);
            TextViewBindingAdapter.setText(this.ivTipText, str2);
            ViewAdapter.onClickCommand(this.mboundView0, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvMain, str);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvMain, true);
        }
    }
}