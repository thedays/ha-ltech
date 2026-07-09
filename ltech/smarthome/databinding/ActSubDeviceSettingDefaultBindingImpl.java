package com.ltech.smarthome.databinding;

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
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.setting.ActSubDeviceSettingDefaultVM;

/* loaded from: classes3.dex */
public class ActSubDeviceSettingDefaultBindingImpl extends ActSubDeviceSettingDefaultBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView5;
    private final AppCompatImageView mboundView6;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(22);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{13}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_device_name, 14);
        sparseIntArray.put(R.id.iv_icon, 15);
        sparseIntArray.put(R.id.tv_subordinate_device, 16);
        sparseIntArray.put(R.id.iv_sub_device_name_go, 17);
        sparseIntArray.put(R.id.tv_room_name, 18);
        sparseIntArray.put(R.id.iv_room_name_go, 19);
        sparseIntArray.put(R.id.layout_ac_brand, 20);
        sparseIntArray.put(R.id.tv_ac_brand, 21);
    }

    public ActSubDeviceSettingDefaultBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }

    private ActSubDeviceSettingDefaultBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[15], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[17], (RelativeLayout) bindings[20], (RelativeLayout) bindings[4], (ConstraintLayout) bindings[9], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[7], (LayoutTitleDefaultBinding) bindings[13], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[14], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[18], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[16]);
        this.mDirtyFlags = -1L;
        this.ivDeviceNameGo.setTag(null);
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceName.setTag(null);
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
        this.tvBrandTitle.setTag(null);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
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
        setViewmodel((ActSubDeviceSettingDefaultVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSubDeviceSettingDefaultBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSubDeviceSettingDefaultBinding
    public void setViewmodel(ActSubDeviceSettingDefaultVM Viewmodel) {
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
        boolean z;
        boolean z2;
        int i;
        int i2;
        BindingCommand<View> bindingCommand;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActSubDeviceSettingDefaultVM actSubDeviceSettingDefaultVM = this.mViewmodel;
        long j2 = j & 6;
        BindingCommand<View> bindingCommand2 = null;
        Place place = null;
        if (j2 != 0) {
            if (actSubDeviceSettingDefaultVM != null) {
                BindingCommand<View> bindingCommand3 = actSubDeviceSettingDefaultVM.viewClick;
                place = actSubDeviceSettingDefaultVM.getCurrentPlace();
                bindingCommand = bindingCommand3;
            } else {
                bindingCommand = null;
            }
            if (place != null) {
                z = place.isOwner();
                z2 = place.isManager();
            } else {
                z = false;
                z2 = false;
            }
            if (j2 != 0) {
                j |= z ? 256L : 128L;
            }
            if ((j & 6) != 0) {
                j |= z2 ? 16L : 8L;
            }
            bindingCommand2 = bindingCommand;
        } else {
            z = false;
            z2 = false;
        }
        long j3 = j & 6;
        if (j3 != 0) {
            boolean z3 = z2 ? true : z;
            if (z) {
                z2 = true;
            }
            if (j3 != 0) {
                j |= z3 ? 64L : 32L;
            }
            if ((j & 6) != 0) {
                j |= z2 ? 5120L : 2560L;
            }
            i = 8;
            i2 = z3 ? 0 : 8;
            if (z2) {
                i = 0;
            }
        } else {
            i = 0;
            z2 = false;
            i2 = 0;
        }
        if ((6 & j) != 0) {
            this.ivDeviceNameGo.setVisibility(i2);
            this.layoutChangeIcon.setEnabled(z2);
            ViewAdapter.onClickCommand(this.layoutChangeIcon, bindingCommand2, false);
            this.layoutChangeRoom.setEnabled(z2);
            ViewAdapter.onClickCommand(this.layoutChangeRoom, bindingCommand2, false);
            this.layoutDeviceName.setEnabled(z2);
            ViewAdapter.onClickCommand(this.layoutDeviceName, bindingCommand2, false);
            this.layoutSubDevice.setEnabled(z2);
            this.mboundView6.setVisibility(i2);
            this.tvDeleteDevice.setVisibility(i);
            ViewAdapter.onClickCommand(this.tvDeleteDevice, bindingCommand2, false);
        }
        if ((4 & j) != 0) {
            ViewAdapter.setTextBold(this.mboundView5, true);
            ViewAdapter.setTextBold(this.tvBrandTitle, true);
            ViewAdapter.setTextBold(this.tvDeleteDevice, true);
            ViewAdapter.setTextBold(this.tvDeviceTip, true);
            ViewAdapter.setTextBold(this.tvNameTip, true);
            ViewAdapter.setTextBold(this.tvRoomTip, true);
        }
        if ((j & 5) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}