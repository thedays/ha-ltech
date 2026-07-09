package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActModuleSwitchVM;

/* loaded from: classes3.dex */
public class ActModuleSwitchBindingImpl extends ActModuleSwitchBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.title, 6);
    }

    public ActModuleSwitchBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ActModuleSwitchBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[3], (AppCompatTextView) bindings[4], (Toolbar) bindings[6], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.ivBack.setTag(null);
        this.ivEdit.setTag(null);
        this.ivSwitchStatus.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvSwitchStatus.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        setViewmodel((ActModuleSwitchVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActModuleSwitchBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActModuleSwitchBinding
    public void setViewmodel(ActModuleSwitchVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeViewmodelStateOn((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelStateOn(MutableLiveData<Boolean> ViewmodelStateOn, int fieldId) {
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
        long j2;
        BindingCommand bindingCommand;
        String str;
        BindingCommand bindingCommand2;
        int i;
        int i2;
        int i3;
        int i4;
        long j3;
        int i5;
        int i6;
        BindingCommand<View> bindingCommand3;
        BindingCommand bindingCommand4;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActModuleSwitchVM actModuleSwitchVM = this.mViewmodel;
        long j4 = j & 10;
        BindingCommand<View> bindingCommand5 = null;
        if (j4 != 0) {
            if (titleDefault != null) {
                i3 = titleDefault.getBackImageRes();
                str = titleDefault.getTitle();
                bindingCommand2 = titleDefault.getBackAction();
                bindingCommand4 = titleDefault.getEditAction();
                i = titleDefault.getEditImageRes();
            } else {
                str = null;
                bindingCommand2 = null;
                bindingCommand4 = null;
                i = 0;
                i3 = 0;
            }
            boolean z = i3 != 0;
            boolean z2 = i != 0;
            if (j4 != 0) {
                j |= z ? 128L : 64L;
            }
            if ((j & 10) != 0) {
                j |= z2 ? PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH : 1024L;
            }
            int i7 = z ? 0 : 8;
            i4 = z2 ? 0 : 8;
            bindingCommand = bindingCommand4;
            i2 = i7;
            j2 = 0;
        } else {
            j2 = 0;
            bindingCommand = null;
            str = null;
            bindingCommand2 = null;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        long j5 = j & 13;
        if (j5 != j2) {
            if ((j & 12) == j2 || actModuleSwitchVM == null) {
                j3 = 10;
                bindingCommand3 = null;
            } else {
                j3 = 10;
                bindingCommand3 = actModuleSwitchVM.viewClick;
            }
            MutableLiveData<Boolean> mutableLiveData = actModuleSwitchVM != null ? actModuleSwitchVM.stateOn : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if (j5 != j2) {
                j |= safeUnbox ? 544L : 272L;
            }
            i6 = safeUnbox ? R.string.on : R.string.off;
            i5 = safeUnbox ? R.mipmap.icon_power_on : R.mipmap.icon_power_off;
            bindingCommand5 = bindingCommand3;
        } else {
            j3 = 10;
            i5 = 0;
            i6 = 0;
        }
        if ((j & j3) != j2) {
            ViewAdapter.setSrc(this.ivBack, i3);
            this.ivBack.setVisibility(i2);
            ViewAdapter.onClickCommand(this.ivBack, bindingCommand2, false);
            ViewAdapter.setSrc(this.ivEdit, i);
            this.ivEdit.setVisibility(i4);
            ViewAdapter.onClickCommand(this.ivEdit, bindingCommand, false);
            TextViewBindingAdapter.setText(this.tvTitle, str);
        }
        if ((j & 13) != j2) {
            ViewAdapter.setBackground(this.ivSwitchStatus, i5);
            this.tvSwitchStatus.setText(i6);
        }
        if ((j & 12) != j2) {
            ViewAdapter.onClickCommand(this.ivSwitchStatus, bindingCommand5, false);
        }
        if ((j & 8) != j2) {
            ViewAdapter.setTextBold(this.tvTitle, true);
        }
    }
}