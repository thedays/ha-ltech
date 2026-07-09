package com.ltech.smarthome.databinding;

import android.text.TextUtils;
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
public class ItemDeviceMeshGatewayBindingImpl extends ItemDeviceMeshGatewayBinding {
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
        sparseIntArray.put(R.id.tv_virtual, 9);
        sparseIntArray.put(R.id.appCompatTextView17, 10);
        sparseIntArray.put(R.id.view10, 11);
        sparseIntArray.put(R.id.tv_write, 12);
    }

    public ItemDeviceMeshGatewayBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ItemDeviceMeshGatewayBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[6], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[10], (LinearLayoutCompat) bindings[7], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[1], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[12], (View) bindings[2], (View) bindings[11]);
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
            this.mDirtyFlags = 32L;
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
        if (52 == variableId) {
            setOnlineString((String) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (53 != variableId) {
            return false;
        }
        setOnlineTextBgRes((Integer) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceMeshGatewayBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceMeshGatewayBinding
    public void setDevice(Device Device) {
        this.mDevice = Device;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(23);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceMeshGatewayBinding
    public void setOnlineString(String OnlineString) {
        this.mOnlineString = OnlineString;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(52);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceMeshGatewayBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceMeshGatewayBinding
    public void setOnlineTextBgRes(Integer OnlineTextBgRes) {
        this.mOnlineTextBgRes = OnlineTextBgRes;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(53);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        String str;
        int i;
        long j2;
        int i2;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Integer num = this.mIconRes;
        Device device = this.mDevice;
        String str2 = this.mOnlineString;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        Integer num2 = this.mOnlineTextBgRes;
        int safeUnbox = (j & 33) != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        long j3 = j & 34;
        DeviceState deviceState = null;
        if (j3 != 0) {
            if (device != null) {
                String deviceName = device.getDeviceName();
                deviceState = device.getDeviceState();
                str = deviceName;
            } else {
                str = null;
            }
            boolean isFavorite = deviceState != null ? deviceState.isFavorite() : false;
            if (j3 != 0) {
                j |= isFavorite ? 128L : 64L;
            }
            i = isFavorite ? R.mipmap.ic_favorite : R.mipmap.ic_favorite_default;
        } else {
            str = null;
            i = 0;
        }
        long j4 = j & 36;
        if (j4 != 0) {
            boolean isEmpty = TextUtils.isEmpty(str2);
            if (j4 != 0) {
                j |= isEmpty ? 512L : 256L;
            }
            i2 = isEmpty ? 8 : 0;
            j2 = 0;
        } else {
            j2 = 0;
            i2 = 0;
        }
        long j5 = j & 40;
        long j6 = j & 48;
        int safeUnbox2 = j6 != j2 ? ViewDataBinding.safeUnbox(num2) : 0;
        if ((33 & j) != j2) {
            ViewAdapter.setSrc(this.appCompatImageView9, safeUnbox);
        }
        if ((j & 34) != j2) {
            TextViewBindingAdapter.setText(this.appCompatTextView15, str);
            ViewAdapter.setBackground(this.ivFavorite, i);
        }
        if ((32 & j) != j2) {
            ViewAdapter.setTextBold(this.appCompatTextView15, true);
        }
        if (j6 != j2) {
            ViewAdapter.setBackground(this.appCompatTextView16, safeUnbox2);
        }
        if ((j & 36) != j2) {
            TextViewBindingAdapter.setText(this.appCompatTextView16, str2);
            this.appCompatTextView16.setVisibility(i2);
        }
        if (j5 != j2) {
            ViewAdapter.onClickCommand(this.layoutItemBg, bindingCommand, false);
            ViewAdapter.onClickCommand(this.vFavorite, bindingCommand, false);
        }
    }
}