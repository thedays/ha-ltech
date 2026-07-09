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
import com.ltech.smarthome.ui.item.SettingItem;

/* loaded from: classes3.dex */
public class ItemSettingBindingImpl extends ItemSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ItemSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.ivGo.setTag(null);
        this.ivIcon.setTag(null);
        this.ivTipDot.setTag(null);
        this.ivTipText.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
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
        setItem((SettingItem) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemSettingBinding
    public void setItem(SettingItem Item) {
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
        long j2;
        String str;
        BindingCommand bindingCommand;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        String str2;
        boolean z;
        boolean z2;
        boolean z3;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        SettingItem settingItem = this.mItem;
        long j3 = j & 3;
        String str3 = null;
        if (j3 != 0) {
            if (settingItem != null) {
                i2 = settingItem.getImageRes();
                str3 = settingItem.getSubText();
                z2 = settingItem.isMainResShow();
                i5 = settingItem.getGoRes();
                i6 = settingItem.getSubImageRes();
                bindingCommand = settingItem.getAction();
                z3 = settingItem.isSubTextShow();
                str2 = settingItem.getMainText();
                z = settingItem.isSubResShow();
            } else {
                bindingCommand = null;
                str2 = null;
                z = false;
                i2 = 0;
                z2 = false;
                i5 = 0;
                i6 = 0;
                z3 = false;
            }
            if (j3 != 0) {
                j |= z2 ? 32L : 16L;
            }
            if ((j & 3) != 0) {
                j |= z3 ? 8L : 4L;
            }
            if ((j & 3) != 0) {
                j |= z ? 128L : 64L;
            }
            i3 = z2 ? 0 : 8;
            int i7 = z3 ? 0 : 8;
            i4 = z ? 0 : 8;
            str = str2;
            i = i7;
            j2 = 0;
        } else {
            j2 = 0;
            str = null;
            bindingCommand = null;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
        }
        if ((3 & j) != j2) {
            ViewAdapter.setSrc(this.ivGo, i5);
            ViewAdapter.setBackground(this.ivIcon, i2);
            this.ivIcon.setVisibility(i3);
            ViewAdapter.setSrc(this.ivTipDot, i6);
            this.ivTipDot.setVisibility(i4);
            TextViewBindingAdapter.setText(this.ivTipText, str3);
            this.ivTipText.setVisibility(i);
            ViewAdapter.onClickCommand(this.mboundView0, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvMain, str);
        }
        if ((j & 2) != j2) {
            ViewAdapter.setTextBold(this.tvMain, true);
        }
    }
}