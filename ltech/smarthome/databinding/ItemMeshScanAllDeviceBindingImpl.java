package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.view.CircularProgressView;
import com.ltech.smarthome.view.SignalView;

/* loaded from: classes3.dex */
public class ItemMeshScanAllDeviceBindingImpl extends ItemMeshScanAllDeviceBinding {
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
        sparseIntArray.put(R.id.iv_upgrade_waiting, 4);
        sparseIntArray.put(R.id.iv_upgrade_fail, 5);
        sparseIntArray.put(R.id.iv_upgrade_success, 6);
    }

    public ItemMeshScanAllDeviceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ItemMeshScanAllDeviceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[6], (CircularProgressView) bindings[4], (RelativeLayout) bindings[0], (SignalView) bindings[3], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.ivIcon.setTag(null);
        this.layoutBg.setTag(null);
        this.signalView.setTag(null);
        this.tvName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 512L;
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
        if (40 == variableId) {
            setItem((ActMeshScanVM.ScanItem) variable);
            return true;
        }
        if (49 == variableId) {
            setNewVersion((String) variable);
            return true;
        }
        if (11 == variableId) {
            setClicklocation((BindingCommand) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (18 == variableId) {
            setCurrentVersion((String) variable);
            return true;
        }
        if (24 == variableId) {
            setDeviceName((String) variable);
            return true;
        }
        if (73 == variableId) {
            setSignal((Integer) variable);
            return true;
        }
        if (77 != variableId) {
            return false;
        }
        setStrProgress((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setItem(ActMeshScanVM.ScanItem Item) {
        this.mItem = Item;
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setNewVersion(String NewVersion) {
        this.mNewVersion = NewVersion;
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setClicklocation(BindingCommand<View> Clicklocation) {
        this.mClicklocation = Clicklocation;
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setCurrentVersion(String CurrentVersion) {
        this.mCurrentVersion = CurrentVersion;
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setDeviceName(String DeviceName) {
        this.mDeviceName = DeviceName;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(24);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setSignal(Integer Signal) {
        this.mSignal = Signal;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(73);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanAllDeviceBinding
    public void setStrProgress(String StrProgress) {
        this.mStrProgress = StrProgress;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Integer num = this.mIconRes;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        String str = this.mDeviceName;
        Integer num2 = this.mSignal;
        long j2 = 513 & j;
        int safeUnbox = j2 != 0 ? ViewDataBinding.safeUnbox(num) : 0;
        long j3 = 528 & j;
        long j4 = 576 & j;
        long j5 = j & 640;
        int safeUnbox2 = j5 != 0 ? ViewDataBinding.safeUnbox(num2) : 0;
        if (j2 != 0) {
            ViewAdapter.setSrc(this.ivIcon, safeUnbox);
        }
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.layoutBg, bindingCommand, false);
        }
        if (j5 != 0) {
            ViewAdapter.setCurSignal(this.signalView, safeUnbox2);
        }
        if (j4 != 0) {
            TextViewBindingAdapter.setText(this.tvName, str);
        }
    }
}