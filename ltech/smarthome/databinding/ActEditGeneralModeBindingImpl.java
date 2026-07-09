package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.mode.BaseEditGeneralModeVM;
import com.ltech.smarthome.utils.selectedCountryLib.demo.RoundImageView;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.HorizontalSeekBar;
import com.ltech.smarthome.view.LightBrtBar;

/* loaded from: classes3.dex */
public class ActEditGeneralModeBindingImpl extends ActEditGeneralModeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(39);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{18}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 19);
        sparseIntArray.put(R.id.tv_device_name, 20);
        sparseIntArray.put(R.id.icon_more, 21);
        sparseIntArray.put(R.id.iv_, 22);
        sparseIntArray.put(R.id.icon_more1, 23);
        sparseIntArray.put(R.id.tv_times_number, 24);
        sparseIntArray.put(R.id.icon_more2, 25);
        sparseIntArray.put(R.id.tab_mode, 26);
        sparseIntArray.put(R.id.rv_color, 27);
        sparseIntArray.put(R.id.sb_speed, 28);
        sparseIntArray.put(R.id.tv_speed, 29);
        sparseIntArray.put(R.id.sb_brt, 30);
        sparseIntArray.put(R.id.tv_brt, 31);
        sparseIntArray.put(R.id.sb_w, 32);
        sparseIntArray.put(R.id.tv_w, 33);
        sparseIntArray.put(R.id.guideline, 34);
        sparseIntArray.put(R.id.csb_wy_bar, 35);
        sparseIntArray.put(R.id.tv_wy, 36);
        sparseIntArray.put(R.id.group_w, 37);
        sparseIntArray.put(R.id.group_wy, 38);
    }

    public ActEditGeneralModeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 39, sIncludes, sViewsWithIds));
    }

    private ActEditGeneralModeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[4], (ColorSeekBar) bindings[35], (Group) bindings[37], (Group) bindings[38], (View) bindings[34], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[25], (ConstraintLayout) bindings[3], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[5], (RoundImageView) bindings[22], (ScrollView) bindings[19], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[16], (RecyclerView) bindings[27], (LightBrtBar) bindings[30], (HorizontalSeekBar) bindings[28], (LightBrtBar) bindings[32], (TabLayout) bindings[26], (LayoutTitleDefaultBinding) bindings[18], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[24], (AppCompatTextView) bindings[33], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[36], (AppCompatTextView) bindings[17], (View) bindings[15], (View) bindings[11], (View) bindings[12]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView25.setTag(null);
        this.changeIcon.setTag(null);
        this.itemIcon.setTag(null);
        this.itemName.setTag(null);
        this.itemTimes.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        this.playBack.setTag(null);
        this.playBack1.setTag(null);
        this.reset.setTag(null);
        setContainedBinding(this.title);
        this.tvBrtTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvPlayTimes.setTag(null);
        this.tvSpeedTip.setTag(null);
        this.tvWTip.setTag(null);
        this.tvWyTip.setTag(null);
        this.vPreview1.setTag(null);
        this.vPreview2.setTag(null);
        this.vPreviewOff.setTag(null);
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
        setViewmodel((BaseEditGeneralModeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActEditGeneralModeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEditGeneralModeBinding
    public void setViewmodel(BaseEditGeneralModeVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(92);
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
        BaseEditGeneralModeVM baseEditGeneralModeVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || baseEditGeneralModeVM == null) ? null : baseEditGeneralModeVM.clickCommand;
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView25, true);
            ViewAdapter.setTextBold(this.changeIcon, true);
            ViewAdapter.setTextBold(this.playBack, true);
            ViewAdapter.setTextBold(this.playBack1, true);
            ViewAdapter.setTextBold(this.reset, true);
            ViewAdapter.setTextBold(this.tvBrtTip, true);
            ViewAdapter.setTextBold(this.tvNameTip, true);
            ViewAdapter.setTextBold(this.tvPlayTimes, true);
            ViewAdapter.setTextBold(this.tvSpeedTip, true);
            ViewAdapter.setTextBold(this.tvWTip, true);
            ViewAdapter.setTextBold(this.tvWyTip, true);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.itemIcon, bindingCommand, false);
            ViewAdapter.onClickCommand(this.itemName, bindingCommand, false);
            ViewAdapter.onClickCommand(this.itemTimes, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vPreview1, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vPreview2, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vPreviewOff, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}