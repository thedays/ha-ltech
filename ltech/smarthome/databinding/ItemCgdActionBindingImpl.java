package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Scene;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ItemCgdActionBindingImpl extends ItemCgdActionBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayoutCompat mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_item_bg, 1);
        sparseIntArray.put(R.id.iv_icon, 2);
        sparseIntArray.put(R.id.iv_select, 3);
        sparseIntArray.put(R.id.tv_name, 4);
        sparseIntArray.put(R.id.tv_color, 5);
        sparseIntArray.put(R.id.civ_color, 6);
        sparseIntArray.put(R.id.tv_action, 7);
        sparseIntArray.put(R.id.tv_add, 8);
        sparseIntArray.put(R.id.group_color, 9);
    }

    public ItemCgdActionBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }

    private ItemCgdActionBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CircleImageView) bindings[6], (Group) bindings[9], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[1], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[4]);
        this.mDirtyFlags = -1L;
        LinearLayoutCompat linearLayoutCompat = (LinearLayoutCompat) bindings[0];
        this.mboundView0 = linearLayoutCompat;
        linearLayoutCompat.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ItemCgdActionBinding
    public void setItem(Scene Item) {
        this.mItem = Item;
    }

    @Override // com.ltech.smarthome.databinding.ItemCgdActionBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}