package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.ui.item.MusicItem;
import com.ltech.smarthome.view.WaveView;

/* loaded from: classes3.dex */
public class ItemBigContentLeftBindingImpl extends ItemBigContentLeftBinding {
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
        sparseIntArray.put(R.id.layout_main, 1);
        sparseIntArray.put(R.id.tv_main, 2);
        sparseIntArray.put(R.id.tv_sub, 3);
        sparseIntArray.put(R.id.iv_main, 4);
        sparseIntArray.put(R.id.iv_play, 5);
        sparseIntArray.put(R.id.ic_playing_main, 6);
        sparseIntArray.put(R.id.layout_sub, 7);
        sparseIntArray.put(R.id.center_line, 8);
        sparseIntArray.put(R.id.iv_sub, 9);
        sparseIntArray.put(R.id.iv_sub_play, 10);
        sparseIntArray.put(R.id.ic_playing_sub, 11);
        sparseIntArray.put(R.id.tv_morning, 12);
        sparseIntArray.put(R.id.tv_morning_sub, 13);
    }

    public ItemBigContentLeftBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private ItemBigContentLeftBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (View) bindings[8], (WaveView) bindings[6], (WaveView) bindings[11], (ImageView) bindings[4], (ImageView) bindings[5], (ImageView) bindings[9], (ImageView) bindings[10], (LinearLayout) bindings[0], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[7], (TextView) bindings[2], (TextView) bindings[12], (TextView) bindings[13], (TextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.layoutItemBg.setTag(null);
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
        setItem((MusicItem) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemBigContentLeftBinding
    public void setItem(MusicItem Item) {
        this.mItem = Item;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}