package com.ltech.smarthome.databinding;

import android.graphics.drawable.Drawable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.CompoundButtonBindingAdapter;
import androidx.databinding.adapters.ViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.rs8.ActRs8VM;

/* loaded from: classes3.dex */
public class ActRs8AddressWriteBindingImpl extends ActRs8AddressWriteBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final Group mboundView3;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(15);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{5}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv, 6);
        sparseIntArray.put(R.id.bg1, 7);
        sparseIntArray.put(R.id.tv_type, 8);
        sparseIntArray.put(R.id.bg2, 9);
        sparseIntArray.put(R.id.tv_address, 10);
        sparseIntArray.put(R.id.edit_address, 11);
        sparseIntArray.put(R.id.tv_tip, 12);
        sparseIntArray.put(R.id.iv_go, 13);
        sparseIntArray.put(R.id.bg3, 14);
    }

    public ActRs8AddressWriteBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActRs8AddressWriteBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (View) bindings[7], (View) bindings[9], (View) bindings[14], (AppCompatEditText) bindings[11], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[13], (AppCompatRadioButton) bindings[2], (AppCompatRadioButton) bindings[1], (LayoutTitleDefaultBinding) bindings[5], (AppCompatTextView) bindings[10], (TextView) bindings[4], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[8]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        Group group = (Group) bindings[3];
        this.mboundView3 = group;
        group.setTag(null);
        this.rdbtnGroup.setTag(null);
        this.rdbtnSingle.setTag(null);
        setContainedBinding(this.title);
        this.tvNext.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        setViewmodel((ActRs8VM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActRs8AddressWriteBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActRs8AddressWriteBinding
    public void setViewmodel(ActRs8VM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 8;
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
        if (localFieldId == 0) {
            return onChangeViewmodelIsGroup((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelAddress((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelIsGroup(MutableLiveData<Boolean> ViewmodelIsGroup, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelAddress(MutableLiveData<String> ViewmodelAddress, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        long j2;
        BindingCommand<View> bindingCommand;
        boolean z;
        int i;
        boolean z2;
        Drawable drawable;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActRs8VM actRs8VM = this.mViewmodel;
        Drawable drawable2 = null;
        if ((j & 27) != 0) {
            MutableLiveData<Boolean> mutableLiveData = actRs8VM != null ? actRs8VM.isGroup : null;
            updateLiveDataRegistration(0, mutableLiveData);
            z = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if ((j & 25) != 0) {
                j |= z ? 256L : 128L;
            }
            if ((j & 27) != 0) {
                j = z ? j | 17408 : j | 8704;
            }
            if ((j & 25) != 0) {
                i = z ? 8 : 0;
                z2 = ViewDataBinding.safeUnbox(Boolean.valueOf(!z));
            } else {
                i = 0;
                z2 = false;
            }
            if ((j & 24) == 0 || actRs8VM == null) {
                j2 = 27;
                bindingCommand = null;
            } else {
                j2 = 27;
                bindingCommand = actRs8VM.viewClick;
            }
        } else {
            j2 = 27;
            bindingCommand = null;
            z = false;
            i = 0;
            z2 = false;
        }
        if ((8704 & j) != 0) {
            MutableLiveData<String> mutableLiveData2 = actRs8VM != null ? actRs8VM.address : null;
            updateLiveDataRegistration(1, mutableLiveData2);
            boolean isEmpty = TextUtils.isEmpty(mutableLiveData2 != null ? mutableLiveData2.getValue() : null);
            if ((j & 8192) != 0) {
                j |= !isEmpty ? 64L : 32L;
            }
            if ((j & 512) != 0) {
                j |= !isEmpty ? PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
            }
            if ((8192 & j) != 0) {
                TextView textView = this.tvNext;
                i2 = !isEmpty ? getColorFromResource(textView, R.color.white) : getColorFromResource(textView, R.color.color_text_red);
            } else {
                i2 = 0;
            }
            if ((j & 512) != 0) {
                drawable = !isEmpty ? AppCompatResources.getDrawable(this.tvNext.getContext(), R.drawable.shape_red_bt_20) : AppCompatResources.getDrawable(this.tvNext.getContext(), R.drawable.shape_white_red_bt);
            } else {
                drawable = null;
            }
        } else {
            drawable = null;
            i2 = 0;
        }
        long j3 = j & j2;
        if (j3 != 0) {
            if (z) {
                drawable = AppCompatResources.getDrawable(this.tvNext.getContext(), R.drawable.shape_red_bt_20);
            }
            drawable2 = drawable;
            if (z) {
                i2 = getColorFromResource(this.tvNext, R.color.white);
            }
        } else {
            i2 = 0;
        }
        Drawable drawable3 = drawable2;
        if ((j & 25) != 0) {
            this.mboundView3.setVisibility(i);
            CompoundButtonBindingAdapter.setChecked(this.rdbtnGroup, z);
            CompoundButtonBindingAdapter.setChecked(this.rdbtnSingle, z2);
        }
        if ((20 & j) != 0) {
            this.title.setTitle(titleDefault);
        }
        if (j3 != 0) {
            ViewBindingAdapter.setBackground(this.tvNext, drawable3);
            this.tvNext.setTextColor(i2);
        }
        if ((j & 24) != 0) {
            ViewAdapter.onClickCommand(this.tvNext, bindingCommand, false);
        }
        executeBindingsOn(this.title);
    }
}