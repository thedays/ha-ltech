package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.imageclip.ClipViewLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomSettingVM;

/* loaded from: classes3.dex */
public class ActIntercomSetFaceBindingImpl extends ActIntercomSetFaceBinding {
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
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{3}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_record_start, 4);
        sparseIntArray.put(R.id.iv_face, 5);
        sparseIntArray.put(R.id.tv_face_tip, 6);
        sparseIntArray.put(R.id.layout_recording, 7);
        sparseIntArray.put(R.id.container, 8);
        sparseIntArray.put(R.id.clipViewLayout, 9);
        sparseIntArray.put(R.id.iv_face_bg, 10);
        sparseIntArray.put(R.id.tv_face_success_tip, 11);
        sparseIntArray.put(R.id.layout_record_success, 12);
        sparseIntArray.put(R.id.iv_face_success, 13);
        sparseIntArray.put(R.id.tv_face_success, 14);
    }

    public ActIntercomSetFaceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActIntercomSetFaceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (ClipViewLayout) bindings[9], (FrameLayout) bindings[8], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[13], (ConstraintLayout) bindings[4], (ConstraintLayout) bindings[12], (ConstraintLayout) bindings[7], (LayoutTitleDefaultBinding) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteFace.setTag(null);
        this.tvStartRecord.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ActIntercomSetFaceBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActIntercomSetFaceBinding
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
        long j3 = j & 6;
        BindingCommand<View> bindingCommand = (j3 == 0 || actIntercomSettingVM == null) ? null : actIntercomSettingVM.commonClick;
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.tvDeleteFace, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvStartRecord, bindingCommand, false);
        }
        executeBindingsOn(this.title);
    }
}