package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LiveData;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.item.SwitchItem;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ItemSwitchBindingImpl extends ItemSwitchBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;

    public ItemSwitchBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }

    private ItemSwitchBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (ConstraintLayout) bindings[0], (SwitchButton) bindings[2], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.layoutItemBg.setTag(null);
        this.sbTalk.setTag(null);
        this.tvMain.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
            setItem((SwitchItem) variable);
            return true;
        }
        if (8 != variableId) {
            return false;
        }
        setCheckedChangeListener((SwitchButton.OnCheckedChangeListener) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemSwitchBinding
    public void setItem(SwitchItem Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemSwitchBinding
    public void setCheckedChangeListener(SwitchButton.OnCheckedChangeListener CheckedChangeListener) {
        this.mCheckedChangeListener = CheckedChangeListener;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(8);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeItemIsChecked((LiveData) object, fieldId);
    }

    private boolean onChangeItemIsChecked(LiveData<Boolean> ItemIsChecked, int fieldId) {
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
        SwitchItem switchItem = this.mItem;
        SwitchButton.OnCheckedChangeListener onCheckedChangeListener = this.mCheckedChangeListener;
        long j2 = 11 & j;
        boolean z = false;
        if (j2 != 0) {
            LiveData<Boolean> isChecked = switchItem != null ? switchItem.isChecked() : null;
            updateLiveDataRegistration(0, isChecked);
            z = ViewDataBinding.safeUnbox(isChecked != null ? isChecked.getValue() : null);
        }
        long j3 = 12 & j;
        if (j2 != 0) {
            ViewAdapter.setChecked(this.sbTalk, z);
        }
        if (j3 != 0) {
            ViewAdapter.setCheckdChangeListener(this.sbTalk, onCheckedChangeListener);
        }
        if ((j & 8) != 0) {
            ViewAdapter.setTextBold(this.tvMain, true);
        }
    }
}