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
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.item.SelectItem;

/* loaded from: classes3.dex */
public class ItemSelectOnOffTimeDiyBindingImpl extends ItemSelectOnOffTimeDiyBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final ConstraintLayout mboundView1;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_select, 19);
        sparseIntArray.put(R.id.iv_scene_select, 20);
        sparseIntArray.put(R.id.iv_elec_select, 21);
        sparseIntArray.put(R.id.iv_on_select, 22);
        sparseIntArray.put(R.id.iv_off_select, 23);
    }

    public ItemSelectOnOffTimeDiyBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }

    private ItemSelectOnOffTimeDiyBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[19], (ConstraintLayout) bindings[7], (ConstraintLayout) bindings[15], (ConstraintLayout) bindings[11], (ConstraintLayout) bindings[3], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[4]);
        this.mDirtyFlags = -1L;
        this.layoutElec.setTag(null);
        this.layoutOff.setTag(null);
        this.layoutOn.setTag(null);
        this.layoutScene.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[1];
        this.mboundView1 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvElecName.setTag(null);
        this.tvElecSubName.setTag(null);
        this.tvElecTitleName.setTag(null);
        this.tvName.setTag(null);
        this.tvOffName.setTag(null);
        this.tvOffSubName.setTag(null);
        this.tvOffTitleName.setTag(null);
        this.tvOnName.setTag(null);
        this.tvOnSubName.setTag(null);
        this.tvOnTitleName.setTag(null);
        this.tvSceneName.setTag(null);
        this.tvSceneSubName.setTag(null);
        this.tvSceneTitleName.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ItemSelectOnOffTimeDiyBinding
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
        String str2;
        BindingCommand bindingCommand;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        SelectItem selectItem = this.mItem;
        long j2 = 3 & j;
        if (j2 == 0 || selectItem == null) {
            str = null;
            str2 = null;
            bindingCommand = null;
        } else {
            str = selectItem.getSubName();
            bindingCommand = selectItem.getAction();
            str2 = selectItem.getName();
        }
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.layoutElec, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutOff, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutOn, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutScene, bindingCommand, false);
            ViewAdapter.onClickCommand(this.mboundView1, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvElecSubName, str);
            TextViewBindingAdapter.setText(this.tvName, str2);
            TextViewBindingAdapter.setText(this.tvOffSubName, str);
            TextViewBindingAdapter.setText(this.tvOnSubName, str);
            TextViewBindingAdapter.setText(this.tvSceneSubName, str);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvElecName, true);
            ViewAdapter.setTextBold(this.tvElecSubName, true);
            ViewAdapter.setTextBold(this.tvElecTitleName, true);
            ViewAdapter.setTextBold(this.tvName, true);
            ViewAdapter.setTextBold(this.tvOffName, true);
            ViewAdapter.setTextBold(this.tvOffSubName, true);
            ViewAdapter.setTextBold(this.tvOffTitleName, true);
            ViewAdapter.setTextBold(this.tvOnName, true);
            ViewAdapter.setTextBold(this.tvOnSubName, true);
            ViewAdapter.setTextBold(this.tvOnTitleName, true);
            ViewAdapter.setTextBold(this.tvSceneName, true);
            ViewAdapter.setTextBold(this.tvSceneSubName, true);
            ViewAdapter.setTextBold(this.tvSceneTitleName, true);
        }
    }
}