package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
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
public class ItemGroupDeviceSortBindingImpl extends ItemGroupDeviceSortBinding {
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
        sparseIntArray.put(R.id.iv_location, 4);
        sparseIntArray.put(R.id.iv_sort, 5);
    }

    public ItemGroupDeviceSortBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ItemGroupDeviceSortBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[5], (ConstraintLayout) bindings[0], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
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
        if (59 != variableId) {
            return false;
        }
        setPlaceInfo((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupDeviceSortBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupDeviceSortBinding
    public void setDevice(Device Device) {
        this.mDevice = Device;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(23);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupDeviceSortBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupDeviceSortBinding
    public void setPlaceInfo(String PlaceInfo) {
        this.mPlaceInfo = PlaceInfo;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(59);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Integer num = this.mIconRes;
        Device device = this.mDevice;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        String str = this.mPlaceInfo;
        long j2 = 17 & j;
        int safeUnbox = j2 != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        long j3 = 18 & j;
        String deviceName = (j3 == 0 || device == null) ? null : device.getDeviceName();
        long j4 = 20 & j;
        long j5 = 24 & j;
        if (j2 != 0) {
            ViewAdapter.setBackground(this.ivIcon, safeUnbox);
        }
        if (j4 != 0) {
            ViewAdapter.onClickCommand(this.layoutItemBg, bindingCommand, false);
        }
        if (j3 != 0) {
            TextViewBindingAdapter.setText(this.tvDeviceName, deviceName);
        }
        if ((j & 16) != 0) {
            ViewAdapter.setTextBold(this.tvDeviceName, true);
        }
        if (j5 != 0) {
            TextViewBindingAdapter.setText(this.tvPlaceInfo, str);
        }
    }
}