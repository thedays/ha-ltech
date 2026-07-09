package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.mode.ActAddModeColorVM;
import com.ltech.smarthome.view.ColorEditText;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.VerticalSeekBar;

/* loaded from: classes3.dex */
public class ActAddModeColorBindingImpl extends ActAddModeColorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(33);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{3}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view28, 4);
        sparseIntArray.put(R.id.tv_brt_tip, 5);
        sparseIntArray.put(R.id.sb_brt, 6);
        sparseIntArray.put(R.id.tv_brt, 7);
        sparseIntArray.put(R.id.sb_w, 8);
        sparseIntArray.put(R.id.tv_w, 9);
        sparseIntArray.put(R.id.tv_w_tip, 10);
        sparseIntArray.put(R.id.v_color, 11);
        sparseIntArray.put(R.id.et_red, 12);
        sparseIntArray.put(R.id.et_green, 13);
        sparseIntArray.put(R.id.et_blue, 14);
        sparseIntArray.put(R.id.et_wy, 15);
        sparseIntArray.put(R.id.tv_r, 16);
        sparseIntArray.put(R.id.tv_g, 17);
        sparseIntArray.put(R.id.tv_b, 18);
        sparseIntArray.put(R.id.tv_wy, 19);
        sparseIntArray.put(R.id.vsb_red, 20);
        sparseIntArray.put(R.id.vsb_green, 21);
        sparseIntArray.put(R.id.vsb_blue, 22);
        sparseIntArray.put(R.id.vsb_wy, 23);
        sparseIntArray.put(R.id.group_wy, 24);
        sparseIntArray.put(R.id.group_w, 25);
        sparseIntArray.put(R.id.rv_color, 26);
        sparseIntArray.put(R.id.view29, 27);
        sparseIntArray.put(R.id.view30, 28);
        sparseIntArray.put(R.id.pick_view_hour, 29);
        sparseIntArray.put(R.id.pick_view_min, 30);
        sparseIntArray.put(R.id.pick_view_sec, 31);
        sparseIntArray.put(R.id.pick_view_ms, 32);
    }

    public ActAddModeColorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }

    private ActAddModeColorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ColorEditText) bindings[14], (ColorEditText) bindings[13], (ColorEditText) bindings[12], (ColorEditText) bindings[15], (Group) bindings[25], (Group) bindings[24], (RecyclerView) bindings[29], (RecyclerView) bindings[30], (RecyclerView) bindings[32], (RecyclerView) bindings[31], (RecyclerView) bindings[26], (LightBrtBar) bindings[6], (LightBrtBar) bindings[8], (LayoutTitleDefaultBinding) bindings[3], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[19], (View) bindings[11], (View) bindings[4], (View) bindings[27], (View) bindings[28], (VerticalSeekBar) bindings[22], (VerticalSeekBar) bindings[21], (VerticalSeekBar) bindings[20], (VerticalSeekBar) bindings[23]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvSetColorTip.setTag(null);
        this.tvSetTime.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActAddModeColorVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAddModeColorBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAddModeColorBinding
    public void setViewmodel(ActAddModeColorVM Viewmodel) {
        this.mViewmodel = Viewmodel;
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
        if ((5 & j) != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvSetColorTip, true);
            ViewAdapter.setTextBold(this.tvSetTime, true);
        }
        executeBindingsOn(this.title);
    }
}