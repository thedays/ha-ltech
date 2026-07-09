package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.item.GoItem;

/* loaded from: classes3.dex */
public class ItemGo2BindingImpl extends ItemGo2Binding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;
    private final AppCompatTextView mboundView2;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemGo2BindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private ItemGo2BindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.ivIcon.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView;
        appCompatTextView.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ItemGo2Binding
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
        int i;
        int i2;
        BindingCommand bindingCommand;
        boolean z;
        AppCompatTextView appCompatTextView;
        int i3;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        GoItem goItem = this.mItem;
        long j2 = j & 3;
        BindingCommand bindingCommand2 = null;
        String str2 = null;
        if (j2 != 0) {
            if (goItem != null) {
                i = goItem.getImageRes();
                str2 = goItem.getMainText();
                z = goItem.isSelect();
                bindingCommand = goItem.getAction();
            } else {
                bindingCommand = null;
                i = 0;
                z = false;
            }
            if (j2 != 0) {
                j |= z ? 8L : 4L;
            }
            if (z) {
                appCompatTextView = this.mboundView2;
                i3 = R.color.color_text_red;
            } else {
                appCompatTextView = this.mboundView2;
                i3 = R.color.color_text_black;
            }
            i2 = getColorFromResource(appCompatTextView, i3);
            String str3 = str2;
            bindingCommand2 = bindingCommand;
            str = str3;
        } else {
            str = null;
            i = 0;
            i2 = 0;
        }
        if ((3 & j) != 0) {
            ViewAdapter.setBackground(this.ivIcon, i);
            ViewAdapter.onClickCommand(this.mboundView0, bindingCommand2, false);
            TextViewBindingAdapter.setText(this.mboundView2, str);
            this.mboundView2.setTextColor(i2);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.mboundView2, true);
        }
    }
}