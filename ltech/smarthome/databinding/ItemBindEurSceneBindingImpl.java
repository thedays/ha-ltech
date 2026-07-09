package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Scene;

/* loaded from: classes3.dex */
public class ItemBindEurSceneBindingImpl extends ItemBindEurSceneBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_item_bg, 2);
        sparseIntArray.put(R.id.iv_icon, 3);
        sparseIntArray.put(R.id.iv_edit, 4);
        sparseIntArray.put(R.id.tv_state, 5);
        sparseIntArray.put(R.id.tv_position, 6);
    }

    public ItemBindEurSceneBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ItemBindEurSceneBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[3], (LinearLayoutCompat) bindings[0], (ConstraintLayout) bindings[2], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[5]);
        this.mDirtyFlags = -1L;
        this.layoutItem.setTag(null);
        this.tvName.setTag(null);
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
        if (37 == variableId) {
            setIconRes((Integer) variable);
            return true;
        }
        if (40 == variableId) {
            setItem((Scene) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemBindEurSceneBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
    }

    @Override // com.ltech.smarthome.databinding.ItemBindEurSceneBinding
    public void setItem(Scene Item) {
        this.mItem = Item;
    }

    @Override // com.ltech.smarthome.databinding.ItemBindEurSceneBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        if ((j & 8) != 0) {
            ViewAdapter.setTextBold(this.tvName, true);
        }
    }
}