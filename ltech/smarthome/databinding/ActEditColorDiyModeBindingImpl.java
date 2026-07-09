package com.ltech.smarthome.databinding;

import android.content.res.Resources;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActEditColorDiyModeVM;
import com.ltech.smarthome.view.HorizontalSeekBar;

/* loaded from: classes3.dex */
public class ActEditColorDiyModeBindingImpl extends ActEditColorDiyModeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(26);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 9);
        sparseIntArray.put(R.id.view17, 10);
        sparseIntArray.put(R.id.cardView, 11);
        sparseIntArray.put(R.id.iv_mode, 12);
        sparseIntArray.put(R.id.tab_mode, 13);
        sparseIntArray.put(R.id.appCompatTextView25, 14);
        sparseIntArray.put(R.id.rv_color, 15);
        sparseIntArray.put(R.id.tv_speed_tip, 16);
        sparseIntArray.put(R.id.sb_speed, 17);
        sparseIntArray.put(R.id.tv_speed, 18);
        sparseIntArray.put(R.id.tv_brt_tip, 19);
        sparseIntArray.put(R.id.sb_brt, 20);
        sparseIntArray.put(R.id.tv_brt, 21);
        sparseIntArray.put(R.id.tv_w_tip, 22);
        sparseIntArray.put(R.id.sb_w, 23);
        sparseIntArray.put(R.id.tv_w, 24);
        sparseIntArray.put(R.id.group_w, 25);
    }

    public ActEditColorDiyModeBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }

    private ActEditColorDiyModeBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[7], (RelativeLayout) bindings[11], (Group) bindings[25], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[6], (ScrollView) bindings[9], (RecyclerView) bindings[15], (HorizontalSeekBar) bindings[20], (HorizontalSeekBar) bindings[17], (HorizontalSeekBar) bindings[23], (TabLayout) bindings[13], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[16], (AppCompatTextView) bindings[24], (AppCompatTextView) bindings[22], (View) bindings[4], (View) bindings[5], (View) bindings[10]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView29.setTag(null);
        this.ivPreview.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvChooseIcon.setTag(null);
        this.tvModeName.setTag(null);
        this.tvModeNameTip.setTag(null);
        this.vChangeIcon.setTag(null);
        this.vPreview.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        setViewmodel((ActEditColorDiyModeVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActEditColorDiyModeBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEditColorDiyModeBinding
    public void setViewmodel(ActEditColorDiyModeVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
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
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeViewmodelPreviewLiveData((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelPreviewLiveData(MutableLiveData<Boolean> ViewmodelPreviewLiveData, int fieldId) {
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
        BindingCommand<View> bindingCommand;
        int i;
        Resources resources;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActEditColorDiyModeVM actEditColorDiyModeVM = this.mViewmodel;
        long j2 = j & 13;
        String str = null;
        r13 = null;
        BindingCommand<View> bindingCommand2 = null;
        if (j2 != 0) {
            MutableLiveData<Boolean> mutableLiveData = actEditColorDiyModeVM != null ? actEditColorDiyModeVM.previewLiveData : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if (j2 != 0) {
                j |= safeUnbox ? 160L : 80L;
            }
            i = safeUnbox ? R.mipmap.ic_preview_pause : R.mipmap.ic_preview;
            if (safeUnbox) {
                resources = this.appCompatTextView29.getResources();
                i2 = R.string.previewing;
            } else {
                resources = this.appCompatTextView29.getResources();
                i2 = R.string.preview;
            }
            String string = resources.getString(i2);
            if ((j & 12) != 0 && actEditColorDiyModeVM != null) {
                bindingCommand2 = actEditColorDiyModeVM.clickCommand;
            }
            bindingCommand = bindingCommand2;
            str = string;
        } else {
            bindingCommand = null;
            i = 0;
        }
        if ((13 & j) != 0) {
            TextViewBindingAdapter.setText(this.appCompatTextView29, str);
            ViewAdapter.setBackground(this.ivPreview, i);
        }
        if ((8 & j) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView29, true);
            ViewAdapter.setTextBold(this.tvChooseIcon, true);
            ViewAdapter.setTextBold(this.tvModeNameTip, true);
        }
        if ((10 & j) != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 12) != 0) {
            ViewAdapter.onClickCommand(this.tvModeName, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vChangeIcon, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vPreview, bindingCommand, false);
        }
        executeBindingsOn(this.title);
    }
}