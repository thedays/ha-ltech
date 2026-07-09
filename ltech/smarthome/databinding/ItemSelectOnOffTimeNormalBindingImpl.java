package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.item.SelectItem;

/* loaded from: classes3.dex */
public class ItemSelectOnOffTimeNormalBindingImpl extends ItemSelectOnOffTimeNormalBinding {
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
        sparseIntArray.put(R.id.iv_select, 3);
    }

    public ItemSelectOnOffTimeNormalBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }

    private ItemSelectOnOffTimeNormalBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[3], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvName.setTag(null);
        this.tvSubName.setTag(null);
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
        setItem((SelectItem) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemSelectOnOffTimeNormalBinding
    public void setItem(SelectItem Item) {
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
        String str2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        SelectItem selectItem = this.mItem;
        long j2 = 3 & j;
        if (j2 == 0 || selectItem == null) {
            str = null;
            bindingCommand = null;
            str2 = null;
        } else {
            str = selectItem.getName();
            str2 = selectItem.getSubName();
            bindingCommand = selectItem.getAction();
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.mboundView0, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvName, str);
            TextViewBindingAdapter.setText(this.tvSubName, str2);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvName, true);
            ViewAdapter.setTextBold(this.tvSubName, true);
        }
    }
}