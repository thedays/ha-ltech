package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomSettingVM;

/* loaded from: classes3.dex */
public class ActIntercomSettingBindingImpl extends ActIntercomSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(15);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{7}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_write_address_tip, 8);
        sparseIntArray.put(R.id.tv_write_address_status, 9);
        sparseIntArray.put(R.id.iv_write_address_go, 10);
        sparseIntArray.put(R.id.tv_unlock, 11);
        sparseIntArray.put(R.id.iv_unlock_go, 12);
        sparseIntArray.put(R.id.tv_face, 13);
        sparseIntArray.put(R.id.iv_face_go, 14);
    }

    public ActIntercomSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActIntercomSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[10], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[3], (ConstraintLayout) bindings[1], (LayoutTitleDefaultBinding) bindings[7], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[8]);
        this.mDirtyFlags = -1L;
        this.layoutFace.setTag(null);
        this.layoutUnlock.setTag(null);
        this.layoutWriteAddress.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvFaceTip.setTag(null);
        this.tvUnlockTip.setTag(null);
        this.tvWriteAddress.setTag(null);
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
        setViewmodel((ActIntercomSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActIntercomSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActIntercomSettingBinding
    public void setViewmodel(ActIntercomSettingVM Viewmodel) {
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
        ActIntercomSettingVM actIntercomSettingVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = 6 & j;
        BindingCommand<View> bindingCommand = (j3 == 0 || actIntercomSettingVM == null) ? null : actIntercomSettingVM.commonClick;
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.layoutFace, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutUnlock, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutWriteAddress, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvFaceTip, true);
            ViewAdapter.setTextBold(this.tvUnlockTip, true);
            ViewAdapter.setTextBold(this.tvWriteAddress, true);
        }
        executeBindingsOn(this.title);
    }
}