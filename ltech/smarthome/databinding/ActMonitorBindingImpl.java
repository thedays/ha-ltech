package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActMonitorVM;
import com.ltech.smarthome.view.ImageTextView;

/* loaded from: classes3.dex */
public class ActMonitorBindingImpl extends ActMonitorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_top_name, 6);
        sparseIntArray.put(R.id.rl_remote_video_view, 7);
        sparseIntArray.put(R.id.guideline, 8);
        sparseIntArray.put(R.id.tv_time, 9);
        sparseIntArray.put(R.id.layout_voice, 10);
        sparseIntArray.put(R.id.layout_function, 11);
        sparseIntArray.put(R.id.itv_speak, 12);
    }

    public ActMonitorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActMonitorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Guideline) bindings[8], (ImageTextView) bindings[4], (ImageTextView) bindings[3], (ImageTextView) bindings[12], (ImageTextView) bindings[5], (ImageTextView) bindings[2], (ImageTextView) bindings[1], (ConstraintLayout) bindings[11], (ConstraintLayout) bindings[10], (RelativeLayout) bindings[7], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[6]);
        this.mDirtyFlags = -1L;
        this.itvAnswer.setTag(null);
        this.itvHangup.setTag(null);
        this.itvUnlock.setTag(null);
        this.itvVoiceMicrophone.setTag(null);
        this.itvVoiceSpeak.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActMonitorVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActMonitorBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
    }

    @Override // com.ltech.smarthome.databinding.ActMonitorBinding
    public void setViewmodel(ActMonitorVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ActMonitorVM actMonitorVM = this.mViewmodel;
        long j2 = j & 6;
        BindingCommand<View> bindingCommand = (j2 == 0 || actMonitorVM == null) ? null : actMonitorVM.viewClick;
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.itvAnswer, bindingCommand, false);
            ViewAdapter.onClickCommand(this.itvHangup, bindingCommand, false);
            ViewAdapter.onClickCommand(this.itvUnlock, bindingCommand, false);
            ViewAdapter.onClickCommand(this.itvVoiceMicrophone, bindingCommand, false);
            ViewAdapter.onClickCommand(this.itvVoiceSpeak, bindingCommand, false);
        }
    }
}