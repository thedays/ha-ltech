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
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.DeviceState;

/* loaded from: classes3.dex */
public class ItemDeviceSensorBindingImpl extends ItemDeviceSensorBinding {
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
        sparseIntArray.put(R.id.bg, 7);
        sparseIntArray.put(R.id.iv_device_more, 8);
        sparseIntArray.put(R.id.appCompatTextView17, 9);
    }

    public ItemDeviceSensorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }

    private ItemDeviceSensorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[6], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[9], (LinearLayoutCompat) bindings[7], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[1], (View) bindings[2]);
        this.mDirtyFlags = -1L;
        this.appCompatImageView9.setTag(null);
        this.appCompatTextView15.setTag(null);
        this.appCompatTextView16.setTag(null);
        this.ivFavorite.setTag(null);
        this.layoutItemBg.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
        this.vFavorite.setTag(null);
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
        if (37 == variableId) {
            setIconRes((Integer) variable);
            return true;
        }
        if (23 == variableId) {
            setDevice((Device) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSensorBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSensorBinding
    public void setDevice(Device Device) {
        this.mDevice = Device;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(23);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSensorBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        int i2;
        String str;
        boolean z;
        boolean z2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Integer num = this.mIconRes;
        Device device = this.mDevice;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        int safeUnbox = (j & 9) != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        long j2 = j & 10;
        String str2 = null;
        DeviceState deviceState = null;
        if (j2 != 0) {
            if (device != null) {
                String deviceName = device.getDeviceName();
                deviceState = device.getDeviceState();
                str = deviceName;
            } else {
                str = null;
            }
            if (deviceState != null) {
                z = deviceState.isOnline();
                z2 = deviceState.isFavorite();
            } else {
                z = false;
                z2 = false;
            }
            if (j2 != 0) {
                j |= z ? 128L : 64L;
            }
            if ((j & 10) != 0) {
                j |= z2 ? 32L : 16L;
            }
            i = z ? 8 : 0;
            i2 = z2 ? R.mipmap.ic_favorite : R.mipmap.ic_favorite_default;
            str2 = str;
        } else {
            i = 0;
            i2 = 0;
        }
        long j3 = j & 12;
        if ((9 & j) != 0) {
            ViewAdapter.setSrc(this.appCompatImageView9, safeUnbox);
        }
        if ((j & 10) != 0) {
            TextViewBindingAdapter.setText(this.appCompatTextView15, str2);
            this.appCompatTextView16.setVisibility(i);
            ViewAdapter.setBackground(this.ivFavorite, i2);
        }
        if ((j & 8) != 0) {
            ViewAdapter.setTextBold(this.appCompatTextView15, true);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.layoutItemBg, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vFavorite, bindingCommand, false);
        }
    }
}