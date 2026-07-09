package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Device;

/* loaded from: classes3.dex */
public class ItemDeviceSelectBindingImpl extends ItemDeviceSelectBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_edit, 5);
        sparseIntArray.put(R.id.iv_edit, 6);
    }

    public ItemDeviceSelectBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ItemDeviceSelectBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[1], (RelativeLayout) bindings[5], (ConstraintLayout) bindings[0], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.ivGo.setTag(null);
        this.ivIcon.setTag(null);
        this.layoutItemBg.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvPlaceInfo.setTag(null);
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (67 == variableId) {
            setSelect((Boolean) variable);
            return true;
        }
        if (59 != variableId) {
            return false;
        }
        setPlaceInfo((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSelectBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSelectBinding
    public void setDevice(Device Device) {
        this.mDevice = Device;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(23);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSelectBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSelectBinding
    public void setSelect(Boolean Select) {
        this.mSelect = Select;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(67);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSelectBinding
    public void setPlaceInfo(String PlaceInfo) {
        this.mPlaceInfo = PlaceInfo;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(59);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Integer num = this.mIconRes;
        Device device = this.mDevice;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        Boolean bool = this.mSelect;
        String str = this.mPlaceInfo;
        int safeUnbox = (j & 33) != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        String deviceName = ((j & 34) == 0 || device == null) ? null : device.getDeviceName();
        long j2 = j & 40;
        if (j2 != 0) {
            boolean safeUnbox2 = ViewDataBinding.safeUnbox(bool);
            if (j2 != 0) {
                j |= safeUnbox2 ? 128L : 64L;
            }
            i = safeUnbox2 ? R.mipmap.ic_tick_sel : R.mipmap.ic_tick_default;
        } else {
            i = 0;
        }
        long j3 = j & 48;
        if ((40 & j) != 0) {
            ViewAdapter.setSrc(this.ivGo, i);
        }
        if ((33 & j) != 0) {
            ViewAdapter.setBackground(this.ivIcon, safeUnbox);
        }
        if ((36 & j) != 0) {
            ViewAdapter.onClickCommand(this.layoutItemBg, bindingCommand, false);
        }
        if ((j & 34) != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceName, deviceName);
        }
        if ((j & 32) != 0) {
            ViewAdapter.setTextBold(this.tvDeviceName, true);
        }
        if (j3 != 0) {
            TextViewBindingAdapter.setText(this.tvPlaceInfo, str);
        }
    }
}