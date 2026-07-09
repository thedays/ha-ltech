package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActPanelSwitchPositionSetBindingImpl extends ActPanelSwitchPositionSetBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(27);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{1}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 2);
        sparseIntArray.put(R.id.tv_tab1, 3);
        sparseIntArray.put(R.id.tv_tab2, 4);
        sparseIntArray.put(R.id.tv_tab3, 5);
        sparseIntArray.put(R.id.view_tab1, 6);
        sparseIntArray.put(R.id.view_tab2, 7);
        sparseIntArray.put(R.id.view_tab3, 8);
        sparseIntArray.put(R.id.layout_s4, 9);
        sparseIntArray.put(R.id.layout_screen, 10);
        sparseIntArray.put(R.id.view_guide, 11);
        sparseIntArray.put(R.id.iv_screen, 12);
        sparseIntArray.put(R.id.rv_screen_info, 13);
        sparseIntArray.put(R.id.iv_S4, 14);
        sparseIntArray.put(R.id.rv_key_info, 15);
        sparseIntArray.put(R.id.rv_key_info2, 16);
        sparseIntArray.put(R.id.iv_switch_5, 17);
        sparseIntArray.put(R.id.iv_shadow_left, 18);
        sparseIntArray.put(R.id.iv_shadow_right, 19);
        sparseIntArray.put(R.id.line, 20);
        sparseIntArray.put(R.id.rv_display_key, 21);
        sparseIntArray.put(R.id.rv_display, 22);
        sparseIntArray.put(R.id.group_tab, 23);
        sparseIntArray.put(R.id.group_page1, 24);
        sparseIntArray.put(R.id.group_page2, 25);
        sparseIntArray.put(R.id.group_page3, 26);
    }

    public ActPanelSwitchPositionSetBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }

    private ActPanelSwitchPositionSetBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Group) bindings[24], (Group) bindings[25], (Group) bindings[26], (Group) bindings[23], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[17], (ConstraintLayout) bindings[2], (LinearLayout) bindings[9], (RelativeLayout) bindings[10], (View) bindings[20], (RecyclerView) bindings[22], (RecyclerView) bindings[21], (RecyclerView) bindings[15], (RecyclerView) bindings[16], (RecyclerView) bindings[13], (LayoutTitleDefaultBinding) bindings[1], (TextView) bindings[3], (TextView) bindings[4], (TextView) bindings[5], (View) bindings[11], (View) bindings[6], (View) bindings[7], (View) bindings[8]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
        }
        this.title.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActPanelSwitchPositionSetBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        if ((j & 3) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}