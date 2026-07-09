package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.setting.ActRs8SubDeviceSettingVM;

/* loaded from: classes3.dex */
public class ActRs8SubDeviceSettingBindingImpl extends ActRs8SubDeviceSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView5;
    private final AppCompatImageView mboundView6;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(24);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{14}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_device_name, 15);
        sparseIntArray.put(R.id.iv_icon, 16);
        sparseIntArray.put(R.id.tv_subordinate_device, 17);
        sparseIntArray.put(R.id.iv_sub_device_name_go, 18);
        sparseIntArray.put(R.id.tv_room_name, 19);
        sparseIntArray.put(R.id.iv_room_name_go, 20);
        sparseIntArray.put(R.id.tv_more, 21);
        sparseIntArray.put(R.id.iv_more_go, 22);
        sparseIntArray.put(R.id.rv, 23);
    }

    public ActRs8SubDeviceSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }

    private ActRs8SubDeviceSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[18], (RelativeLayout) bindings[4], (ConstraintLayout) bindings[9], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[11], (ConstraintLayout) bindings[7], (RecyclerView) bindings[23], (LayoutTitleDefaultBinding) bindings[14], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[17]);
        this.mDirtyFlags = -1L;
        this.ivDeviceNameGo.setTag(null);
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutMore.setTag(null);
        this.layoutSubDevice.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[6];
        this.mboundView6 = appCompatImageView;
        appCompatImageView.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceTip.setTag(null);
        this.tvMoreTitle.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
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
        setViewmodel((ActRs8SubDeviceSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActRs8SubDeviceSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActRs8SubDeviceSettingBinding
    public void setViewmodel(ActRs8SubDeviceSettingVM Viewmodel) {
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
        return onChangeViewmodelShowMore((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelShowMore(MutableLiveData<Boolean> ViewmodelShowMore, int fieldId) {
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
        int i;
        boolean z;
        boolean z2;
        long j2;
        int i2;
        int i3;
        BindingCommand<View> bindingCommand;
        Place place;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActRs8SubDeviceSettingVM actRs8SubDeviceSettingVM = this.mViewmodel;
        BindingCommand<View> bindingCommand2 = null;
        if ((j & 13) != 0) {
            long j3 = j & 12;
            if (j3 != 0) {
                if (actRs8SubDeviceSettingVM != null) {
                    bindingCommand = actRs8SubDeviceSettingVM.viewClick;
                    place = actRs8SubDeviceSettingVM.getCurrentPlace();
                } else {
                    bindingCommand = null;
                    place = null;
                }
                if (place != null) {
                    z2 = place.isOwner();
                    z = place.isManager();
                } else {
                    z = false;
                    z2 = false;
                }
                if (j3 != 0) {
                    j |= z2 ? PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH : 1024L;
                }
                if ((j & 12) != 0) {
                    j |= z ? 32L : 16L;
                }
            } else {
                bindingCommand = null;
                z = false;
                z2 = false;
            }
            MutableLiveData<Boolean> mutableLiveData = actRs8SubDeviceSettingVM != null ? actRs8SubDeviceSettingVM.showMore : null;
            updateLiveDataRegistration(0, mutableLiveData);
            boolean safeUnbox = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            if ((j & 13) != 0) {
                j |= safeUnbox ? 512L : 256L;
            }
            i = safeUnbox ? 0 : 8;
            bindingCommand2 = bindingCommand;
        } else {
            i = 0;
            z = false;
            z2 = false;
        }
        long j4 = j & 12;
        if (j4 != 0) {
            boolean z3 = z ? true : z2;
            if (z2) {
                z = true;
            }
            if (j4 != 0) {
                j |= z3 ? 128L : 64L;
            }
            if ((j & 12) != 0) {
                j |= z ? 40960L : 20480L;
            }
            int i4 = z3 ? 0 : 8;
            i3 = z ? 0 : 8;
            i2 = i4;
            j2 = 0;
        } else {
            j2 = 0;
            i2 = 0;
            z = false;
            i3 = 0;
        }
        if ((12 & j) != j2) {
            this.ivDeviceNameGo.setVisibility(i2);
            this.layoutChangeIcon.setEnabled(z);
            ViewAdapter.onClickCommand(this.layoutChangeIcon, bindingCommand2, false);
            this.layoutChangeRoom.setEnabled(z);
            ViewAdapter.onClickCommand(this.layoutChangeRoom, bindingCommand2, false);
            this.layoutDeviceName.setEnabled(z);
            ViewAdapter.onClickCommand(this.layoutDeviceName, bindingCommand2, false);
            ViewAdapter.onClickCommand(this.layoutMore, bindingCommand2, false);
            this.layoutSubDevice.setEnabled(z);
            this.mboundView6.setVisibility(i2);
            this.tvDeleteDevice.setVisibility(i3);
            ViewAdapter.onClickCommand(this.tvDeleteDevice, bindingCommand2, false);
        }
        if ((j & 13) != j2) {
            this.layoutMore.setVisibility(i);
        }
        if ((8 & j) != j2) {
            ViewAdapter.setTextBold(this.mboundView5, true);
            ViewAdapter.setTextBold(this.tvDeleteDevice, true);
            ViewAdapter.setTextBold(this.tvDeviceTip, true);
            ViewAdapter.setTextBold(this.tvMoreTitle, true);
            ViewAdapter.setTextBold(this.tvNameTip, true);
            ViewAdapter.setTextBold(this.tvRoomTip, true);
        }
        if ((j & 10) != j2) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}