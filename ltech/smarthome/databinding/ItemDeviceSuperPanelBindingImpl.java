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
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Device;

/* loaded from: classes3.dex */
public class ItemDeviceSuperPanelBindingImpl extends ItemDeviceSuperPanelBinding {
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
        sparseIntArray.put(R.id.appCompatTextView17, 10);
    }

    public ItemDeviceSuperPanelBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ItemDeviceSuperPanelBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[8], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[10], (LinearLayoutCompat) bindings[9], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[1], (AppCompatTextView) bindings[6], (View) bindings[2]);
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
        this.tvOnline.setTag(null);
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
        if (33 == variableId) {
            setHasMeshNetworkFun((Boolean) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSuperPanelBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSuperPanelBinding
    public void setDevice(Device Device) {
        this.mDevice = Device;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(23);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSuperPanelBinding
    public void setHasMeshNetworkFun(Boolean HasMeshNetworkFun) {
        this.mHasMeshNetworkFun = HasMeshNetworkFun;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(33);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemDeviceSuperPanelBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00d6  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ItemDeviceSuperPanelBindingImpl.executeBindings():void");
    }
}