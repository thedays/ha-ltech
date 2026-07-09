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
public class ItemBigContentRightBindingImpl extends ItemBigContentRightBinding {
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
        sparseIntArray.put(R.id.layout_sub, 1);
        sparseIntArray.put(R.id.center_line, 2);
        sparseIntArray.put(R.id.iv_sub, 3);
        sparseIntArray.put(R.id.iv_sub_play, 4);
        sparseIntArray.put(R.id.ic_playing_sub, 5);
        sparseIntArray.put(R.id.tv_morning, 6);
        sparseIntArray.put(R.id.tv_morning_sub, 7);
        sparseIntArray.put(R.id.layout_main, 8);
        sparseIntArray.put(R.id.tv_main, 9);
        sparseIntArray.put(R.id.tv_sub, 10);
        sparseIntArray.put(R.id.iv_main, 11);
        sparseIntArray.put(R.id.iv_play, 12);
        sparseIntArray.put(R.id.ic_playing_main, 13);
    }

    public ItemBigContentRightBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }

    private ItemBigContentRightBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (View) bindings[2], (WaveView) bindings[13], (WaveView) bindings[5], (ImageView) bindings[11], (ImageView) bindings[12], (ImageView) bindings[3], (ImageView) bindings[4], (LinearLayout) bindings[0], (ConstraintLayout) bindings[8], (ConstraintLayout) bindings[1], (TextView) bindings[9], (TextView) bindings[6], (TextView) bindings[7], (TextView) bindings[10]);
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

    @Override // com.ltech.smarthome.databinding.ItemBigContentRightBinding
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