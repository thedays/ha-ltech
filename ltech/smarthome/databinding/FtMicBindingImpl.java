package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.FtMicVM;
import com.ltech.smarthome.view.VoiceView;

/* loaded from: classes3.dex */
public class FtMicBindingImpl extends FtMicBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView5;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.toolbar, 7);
        sparseIntArray.put(R.id.voiceview, 8);
    }

    public FtMicBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }

    private FtMicBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[6], (Toolbar) bindings[7], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[1], (VoiceView) bindings[8]);
        this.mDirtyFlags = -1L;
        this.ivBack.setTag(null);
        this.ivEdit.setTag(null);
        this.ivStartStop.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.tvTip1.setTag(null);
        this.tvTitle.setTag(null);
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
        setViewmodel((FtMicVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtMicBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtMicBinding
    public void setViewmodel(FtMicVM Viewmodel) {
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
        String str;
        BindingCommand bindingCommand;
        BindingCommand bindingCommand2;
        int i;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        FtMicVM ftMicVM = this.mViewmodel;
        long j2 = 5 & j;
        BindingCommand<View> bindingCommand3 = null;
        if (j2 == 0 || titleDefault == null) {
            str = null;
            bindingCommand = null;
            bindingCommand2 = null;
            i = 0;
            i2 = 0;
        } else {
            i2 = titleDefault.getBackImageRes();
            str = titleDefault.getTitle();
            bindingCommand = titleDefault.getBackAction();
            bindingCommand2 = titleDefault.getEditAction();
            i = titleDefault.getEditImageRes();
        }
        long j3 = 6 & j;
        if (j3 != 0 && ftMicVM != null) {
            bindingCommand3 = ftMicVM.viewClick;
        }
        if (j2 != 0) {
            ViewAdapter.setSrc(this.ivBack, i2);
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand, false);
            ViewAdapter.setSrc(this.ivEdit, i);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand2, false);
            TextViewBindingAdapter.setText(this.tvTitle, str);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.ivStartStop, bindingCommand3, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.mboundView5, true);
            ViewAdapter.setTextBold(this.tvTip1, true);
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}