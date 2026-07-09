package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.CompoundButtonBindingAdapter;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;
import com.ltech.smarthome.view.SmartSwitch;

/* loaded from: classes3.dex */
public class ItemCentralAirSubDeviceBindingImpl extends ItemCentralAirSubDeviceBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.bg, 9);
        sparseIntArray.put(R.id.appCompatTextView18, 10);
        sparseIntArray.put(R.id.appCompatTextView17, 11);
    }

    public ItemCentralAirSubDeviceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private ItemCentralAirSubDeviceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[7], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[10], (LinearLayoutCompat) bindings[9], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[1], (SmartSwitch) bindings[8], (View) bindings[2]);
        this.mDirtyFlags = -1L;
        this.appCompatImageView9.setTag(null);
        this.appCompatTextView15.setTag(null);
        this.appCompatTextView16.setTag(null);
        this.ivDeviceMore.setTag(null);
        this.ivFavorite.setTag(null);
        this.layoutItemBg.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
        this.sb.setTag(null);
        this.vFavorite.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        if (37 == variableId) {
            setIconRes((Integer) variable);
            return true;
        }
        if (23 == variableId) {
            setDevice((Device) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (8 != variableId) {
            return false;
        }
        setCheckedChangeListener((SmartSwitch.OnUserCheckedChangeListener) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemCentralAirSubDeviceBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemCentralAirSubDeviceBinding
    public void setDevice(Device Device) {
        this.mDevice = Device;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(23);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemCentralAirSubDeviceBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemCentralAirSubDeviceBinding
    public void setCheckedChangeListener(SmartSwitch.OnUserCheckedChangeListener CheckedChangeListener) {
        this.mCheckedChangeListener = CheckedChangeListener;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(8);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        int i;
        int i2;
        long j2;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Integer num = this.mIconRes;
        Device device = this.mDevice;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        SmartSwitch.OnUserCheckedChangeListener onUserCheckedChangeListener = this.mCheckedChangeListener;
        int safeUnbox = (j & 17) != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        long j3 = j & 18;
        DeviceState deviceState = null;
        if (j3 != 0) {
            if (device != null) {
                String deviceName = device.getDeviceName();
                deviceState = device.getDeviceState();
                str = deviceName;
            } else {
                str = null;
            }
            if (deviceState != null) {
                z2 = deviceState.isOnline();
                z3 = deviceState.isFavorite();
                z4 = deviceState.isOn();
            } else {
                z2 = false;
                z3 = false;
                z4 = false;
            }
            if (j3 != 0) {
                j |= z2 ? 256L : 128L;
            }
            if ((j & 18) != 0) {
                j |= z3 ? 64L : 32L;
            }
            i = z2 ? 8 : 0;
            i2 = z3 ? R.mipmap.ic_favorite : R.mipmap.ic_favorite_default;
            z = z4;
            j2 = 0;
        } else {
            str = null;
            i = 0;
            i2 = 0;
            j2 = 0;
            z = false;
        }
        long j4 = j & 20;
        long j5 = j & 24;
        if ((17 & j) != j2) {
            ViewAdapter.setSrc(this.appCompatImageView9, safeUnbox);
        }
        if ((j & 18) != j2) {
            TextViewBindingAdapter.setText(this.appCompatTextView15, str);
            this.appCompatTextView16.setVisibility(i);
            ViewAdapter.setBackground(this.ivFavorite, i2);
            CompoundButtonBindingAdapter.setChecked(this.sb, z);
        }
        if ((j & 16) != j2) {
            ViewAdapter.setTextBold(this.appCompatTextView15, true);
        }
        if (j4 != j2) {
            ViewAdapter.onClickCommand(this.ivDeviceMore, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutItemBg, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vFavorite, bindingCommand, false);
        }
        if (j5 != j2) {
            ViewAdapter.setOnUserCheckedChangeListener(this.sb, onUserCheckedChangeListener);
        }
    }
}