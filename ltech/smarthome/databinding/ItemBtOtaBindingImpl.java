package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.upgrade.ActBtOtaVM;

/* loaded from: classes3.dex */
public class ItemBtOtaBindingImpl extends ItemBtOtaBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final AppCompatTextView mboundView2;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_upgrade_status, 7);
    }

    public ItemBtOtaBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ItemBtOtaBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[5], (ContentLoadingProgressBar) bindings[4], (ConstraintLayout) bindings[0], (RelativeLayout) bindings[7], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.ivUpgradeFail.setTag(null);
        this.ivUpgradeSuccess.setTag(null);
        this.ivUpgradeWaiting.setTag(null);
        this.layoutBg.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.tvName.setTag(null);
        this.tvUpgradeProgress.setTag(null);
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
        if (40 == variableId) {
            setItem((ActBtOtaVM.ScanItem) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (7 == variableId) {
            setBtnName((String) variable);
            return true;
        }
        if (24 == variableId) {
            setDeviceName((String) variable);
            return true;
        }
        if (77 != variableId) {
            return false;
        }
        setStrProgress((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemBtOtaBinding
    public void setItem(ActBtOtaVM.ScanItem Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemBtOtaBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemBtOtaBinding
    public void setBtnName(String BtnName) {
        this.mBtnName = BtnName;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(7);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemBtOtaBinding
    public void setDeviceName(String DeviceName) {
        this.mDeviceName = DeviceName;
    }

    @Override // com.ltech.smarthome.databinding.ItemBtOtaBinding
    public void setStrProgress(String StrProgress) {
        this.mStrProgress = StrProgress;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        long j2;
        long j3;
        String str;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Device device;
        int i6;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ActBtOtaVM.ScanItem scanItem = this.mItem;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        String str2 = this.mBtnName;
        long j4 = j & 33;
        if (j4 != 0) {
            if (scanItem != null) {
                device = scanItem.getDevice();
                str = scanItem.getProgress();
                i6 = scanItem.getUpgradeFlag();
            } else {
                device = null;
                str = null;
                i6 = 0;
            }
            r12 = device != null ? device.getName() : null;
            boolean z = i6 == 1;
            j2 = 0;
            boolean z2 = i6 == 3;
            boolean z3 = i6 == 0;
            j3 = 33;
            boolean z4 = i6 == 2;
            boolean z5 = i6 == 4;
            if (j4 != 0) {
                j |= z ? 512L : 256L;
            }
            if ((j & 33) != 0) {
                j |= z2 ? 8192L : PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
            }
            if ((j & 33) != 0) {
                j |= z3 ? PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH : 1024L;
            }
            if ((j & 33) != 0) {
                j |= z4 ? PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID : 16384L;
            }
            if ((j & 33) != 0) {
                j |= z5 ? 128L : 64L;
            }
            i4 = 8;
            i5 = z ? 0 : 8;
            i = z2 ? 0 : 8;
            i2 = z3 ? 0 : 8;
            i3 = z4 ? 0 : 8;
            if (z5) {
                i4 = 0;
            }
        } else {
            j2 = 0;
            j3 = 33;
            str = null;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
        }
        long j5 = j & 34;
        long j6 = j & 36;
        if ((j & j3) != j2) {
            this.ivUpgradeFail.setVisibility(i4);
            this.ivUpgradeSuccess.setVisibility(i);
            this.ivUpgradeWaiting.setVisibility(i5);
            this.mboundView2.setVisibility(i2);
            TextViewBindingAdapter.setText(this.tvName, r12);
            TextViewBindingAdapter.setText(this.tvUpgradeProgress, str);
            this.tvUpgradeProgress.setVisibility(i3);
        }
        if (j5 != j2) {
            ViewAdapter.onClickCommand(this.layoutBg, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvUpgradeProgress, bindingCommand, false);
        }
        if (j6 != j2) {
            TextViewBindingAdapter.setText(this.mboundView2, str2);
        }
        if ((j & 32) != j2) {
            ViewAdapter.setTextBold(this.tvName, true);
        }
    }
}