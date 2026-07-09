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
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.view.SmartSwitch;

/* loaded from: classes3.dex */
public class ItemGroupSmartPanelBindingImpl extends ItemGroupSmartPanelBinding {
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
        sparseIntArray.put(R.id.bg, 8);
        sparseIntArray.put(R.id.appCompatTextView17, 9);
        sparseIntArray.put(R.id.view10, 10);
    }

    public ItemGroupSmartPanelBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private ItemGroupSmartPanelBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[7], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[9], (LinearLayoutCompat) bindings[8], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[3], (SmartSwitch) bindings[6], (ConstraintLayout) bindings[1], (View) bindings[2], (View) bindings[10]);
        this.mDirtyFlags = -1L;
        this.appCompatImageView9.setTag(null);
        this.appCompatTextView15.setTag(null);
        this.ivDeviceMore.setTag(null);
        this.ivFavorite.setTag(null);
        this.ivSwitch.setTag(null);
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
            this.mDirtyFlags = 64L;
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
        if (32 == variableId) {
            setGroup((Group) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (34 == variableId) {
            setHideMore((Boolean) variable);
            return true;
        }
        if (35 == variableId) {
            setHideSwitch((Boolean) variable);
            return true;
        }
        if (8 != variableId) {
            return false;
        }
        setCheckedChangeListener((SmartSwitch.OnUserCheckedChangeListener) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupSmartPanelBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupSmartPanelBinding
    public void setGroup(Group Group) {
        this.mGroup = Group;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(32);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupSmartPanelBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupSmartPanelBinding
    public void setHideMore(Boolean HideMore) {
        this.mHideMore = HideMore;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(34);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupSmartPanelBinding
    public void setHideSwitch(Boolean HideSwitch) {
        this.mHideSwitch = HideSwitch;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(35);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupSmartPanelBinding
    public void setCheckedChangeListener(SmartSwitch.OnUserCheckedChangeListener CheckedChangeListener) {
        this.mCheckedChangeListener = CheckedChangeListener;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(8);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ItemGroupSmartPanelBindingImpl.executeBindings():void");
    }
}