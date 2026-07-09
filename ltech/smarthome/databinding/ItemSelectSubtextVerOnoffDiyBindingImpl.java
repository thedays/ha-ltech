package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.item.SelectItem;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class ItemSelectSubtextVerOnoffDiyBindingImpl extends ItemSelectSubtextVerOnoffDiyBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_brt, 5);
        sparseIntArray.put(R.id.sb_brt, 6);
        sparseIntArray.put(R.id.tv_brt, 7);
    }

    public ItemSelectSubtextVerOnoffDiyBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ItemSelectSubtextVerOnoffDiyBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[4], (LinearLayout) bindings[5], (ConstraintLayout) bindings[1], (LightBrtBar) bindings[6], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.ivSelect.setTag(null);
        this.layoutItem.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.tvName.setTag(null);
        this.tvSubName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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

    @Override // com.ltech.smarthome.databinding.ItemSelectSubtextVerOnoffDiyBinding
    public void setItem(SelectItem Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeItemSelect((LiveData) object, fieldId);
    }

    private boolean onChangeItemSelect(LiveData<Boolean> ItemSelect, int fieldId) {
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
        String str;
        String str2;
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        SelectItem selectItem = this.mItem;
        long j2 = j & 7;
        BindingCommand bindingCommand = null;
        if (j2 != 0) {
            LiveData<Boolean> isSelect = selectItem != null ? selectItem.isSelect() : null;
            updateLiveDataRegistration(0, isSelect);
            boolean safeUnbox = ViewDataBinding.safeUnbox(isSelect != null ? isSelect.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 16L : 8L;
            }
            i = safeUnbox ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default;
            if ((j & 6) == 0 || selectItem == null) {
                str = null;
                str2 = null;
            } else {
                bindingCommand = selectItem.getAction();
                str2 = selectItem.getName();
                str = selectItem.getSubName();
            }
        } else {
            str = null;
            str2 = null;
            i = 0;
        }
        if ((7 & j) != 0) {
            ViewAdapter.setBackground(this.ivSelect, i);
        }
        if ((j & 6) != 0) {
            ViewAdapter.onClickCommand(this.layoutItem, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvName, str2);
            TextViewBindingAdapter.setText(this.tvSubName, str);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvName, true);
            ViewAdapter.setTextBold(this.tvSubName, true);
        }
    }
}