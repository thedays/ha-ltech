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
public class ItemGo1BindingImpl extends ItemGo1Binding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemGo1BindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ItemGo1BindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[0], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.ivGo.setTag(null);
        this.ivSub.setTag(null);
        this.layoutItemBg.setTag(null);
        this.tvMain.setTag(null);
        this.tvSub.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ItemGo1Binding
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
        String str2;
        int i;
        int i2;
        int i3;
        BindingCommand bindingCommand;
        int i4;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        GoItem goItem = this.mItem;
        long j2 = j & 3;
        BindingCommand bindingCommand2 = null;
        String str3 = null;
        if (j2 != 0) {
            if (goItem != null) {
                str3 = goItem.getSubText();
                i4 = goItem.getGoRes();
                i2 = goItem.getSubImageRes();
                bindingCommand = goItem.getAction();
                i3 = goItem.getColor();
                str = goItem.getMainText();
            } else {
                str = null;
                bindingCommand = null;
                i4 = 0;
                i2 = 0;
                i3 = 0;
            }
            boolean z = i4 != 0;
            if (j2 != 0) {
                j |= z ? 8L : 4L;
            }
            i = z ? 0 : 8;
            str2 = str3;
            bindingCommand2 = bindingCommand;
        } else {
            str = null;
            str2 = null;
            i = 0;
            i2 = 0;
            i3 = 0;
        }
        if ((3 & j) != 0) {
            this.ivGo.setVisibility(i);
            ViewAdapter.setSrc(this.ivSub, i2);
            ViewAdapter.onClickCommand(this.layoutItemBg, bindingCommand2, false);
            TextViewBindingAdapter.setText(this.tvMain, str);
            TextViewBindingAdapter.setText(this.tvSub, str2);
            this.tvSub.setTextColor(i3);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvMain, true);
        }
    }
}