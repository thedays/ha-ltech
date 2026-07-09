package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.mode.BaseEditAdvancedModeVM;
import com.ltech.smarthome.utils.selectedCountryLib.demo.RoundImageView;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public class ActEditAdvancedModeBindingImpl extends ActEditAdvancedModeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView7;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(28);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{17}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 18);
        sparseIntArray.put(R.id.tv_device_name, 19);
        sparseIntArray.put(R.id.icon_more, 20);
        sparseIntArray.put(R.id.iv_, 21);
        sparseIntArray.put(R.id.icon_more1, 22);
        sparseIntArray.put(R.id.tv_times_number, 23);
        sparseIntArray.put(R.id.icon_more2, 24);
        sparseIntArray.put(R.id.rv_content, 25);
        sparseIntArray.put(R.id.view23, 26);
        sparseIntArray.put(R.id.guideline, 27);
    }

    public ActEditAdvancedModeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }

    private ActEditAdvancedModeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[4], (Guideline) bindings[27], (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[24], (ConstraintLayout) bindings[3], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[5], (RoundImageView) bindings[21], (ScrollView) bindings[18], (AppCompatTextView) bindings[10], (SwipeRecyclerView) bindings[25], (LayoutTitleDefaultBinding) bindings[17], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[16], (View) bindings[9], (LinearLayout) bindings[11], (LinearLayout) bindings[14], (View) bindings[26]);
        this.mDirtyFlags = -1L;
        this.changeIcon.setTag(null);
        this.itemIcon.setTag(null);
        this.itemName.setTag(null);
        this.itemTimes.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[7];
        this.mboundView7 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.reset.setTag(null);
        setContainedBinding(this.title);
        this.tvImportMode.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvPlayTimes.setTag(null);
        this.tvPreview.setTag(null);
        this.tvPreview1.setTag(null);
        this.tvTotalTime.setTag(null);
        this.tvTotalTime1.setTag(null);
        this.vPreview1.setTag(null);
        this.vPreview3.setTag(null);
        this.vPreview3Off.setTag(null);
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
        setViewmodel((BaseEditAdvancedModeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActEditAdvancedModeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEditAdvancedModeBinding
    public void setViewmodel(BaseEditAdvancedModeVM Viewmodel) {
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
        BaseEditAdvancedModeVM baseEditAdvancedModeVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || baseEditAdvancedModeVM == null) ? null : baseEditAdvancedModeVM.clickCommand;
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.changeIcon, true);
            ViewAdapter.setTextBold(this.mboundView7, true);
            ViewAdapter.setTextBold(this.reset, true);
            ViewAdapter.setTextBold(this.tvNameTip, true);
            ViewAdapter.setTextBold(this.tvPlayTimes, true);
            ViewAdapter.setTextBold(this.tvPreview, true);
            ViewAdapter.setTextBold(this.tvPreview1, true);
            ViewAdapter.setTextBold(this.tvTotalTime, true);
            ViewAdapter.setTextBold(this.tvTotalTime1, true);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.itemIcon, bindingCommand, false);
            ViewAdapter.onClickCommand(this.itemName, bindingCommand, false);
            ViewAdapter.onClickCommand(this.itemTimes, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvImportMode, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vPreview1, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vPreview3, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vPreview3Off, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}