package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
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
import com.ltech.smarthome.ui.config.ActMeshScanVM;
import com.ltech.smarthome.view.SignalView;

/* loaded from: classes3.dex */
public class ItemMeshScanBindingImpl extends ItemMeshScanBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final AppCompatTextView mboundView9;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_end, 16);
    }

    public ItemMeshScanBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }

    private ItemMeshScanBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[15], (AppCompatImageView) bindings[14], (ContentLoadingProgressBar) bindings[13], (RelativeLayout) bindings[6], (ConstraintLayout) bindings[0], (LinearLayout) bindings[16], (RelativeLayout) bindings[10], (SignalView) bindings[5], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[12]);
        this.mDirtyFlags = -1L;
        this.ivAdd.setTag(null);
        this.ivIcon.setTag(null);
        this.ivUpgradeFail.setTag(null);
        this.ivUpgradeSuccess.setTag(null);
        this.ivUpgradeWaiting.setTag(null);
        this.layoutAdd.setTag(null);
        this.layoutBg.setTag(null);
        this.layoutUpgradeStatus.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.signalView.setTag(null);
        this.tvAdd.setTag(null);
        this.tvCurrentVersion.setTag(null);
        this.tvName.setTag(null);
        this.tvNewVersion.setTag(null);
        this.tvUpgradeItem.setTag(null);
        this.tvUpgradeProgress.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1024L;
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
            setItem((ActMeshScanVM.ScanItem) variable);
            return true;
        }
        if (77 == variableId) {
            setStrProgress((String) variable);
            return true;
        }
        if (37 == variableId) {
            setIconRes((Integer) variable);
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
        if (18 == variableId) {
            setCurrentVersion((String) variable);
            return true;
        }
        if (87 == variableId) {
            setUpgradeName((String) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (24 == variableId) {
            setDeviceName((String) variable);
            return true;
        }
        if (73 != variableId) {
            return false;
        }
        setSignal((Integer) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setItem(ActMeshScanVM.ScanItem Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setStrProgress(String StrProgress) {
        this.mStrProgress = StrProgress;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(77);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setNewVersion(String NewVersion) {
        this.mNewVersion = NewVersion;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(49);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setClicklocation(BindingCommand<View> Clicklocation) {
        this.mClicklocation = Clicklocation;
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setCurrentVersion(String CurrentVersion) {
        this.mCurrentVersion = CurrentVersion;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(18);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setUpgradeName(String UpgradeName) {
        this.mUpgradeName = UpgradeName;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(87);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setDeviceName(String DeviceName) {
        this.mDeviceName = DeviceName;
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        notifyPropertyChanged(24);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMeshScanBinding
    public void setSignal(Integer Signal) {
        this.mSignal = Signal;
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        notifyPropertyChanged(73);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        long j2;
        Integer num;
        Integer num2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        String str;
        int i9;
        String str2;
        int i10;
        int i11;
        int i12;
        String str3;
        int i13;
        boolean z2;
        int i14;
        boolean z3;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ActMeshScanVM.ScanItem scanItem = this.mItem;
        String str4 = this.mStrProgress;
        Integer num3 = this.mIconRes;
        String str5 = this.mNewVersion;
        String str6 = this.mCurrentVersion;
        String str7 = this.mUpgradeName;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        String str8 = this.mDeviceName;
        Integer num4 = this.mSignal;
        long j3 = j & 1025;
        if (j3 != 0) {
            if (scanItem != null) {
                i14 = scanItem.upgradeFlag;
                z3 = scanItem.isPROXY;
                z2 = scanItem.config;
            } else {
                z2 = false;
                i14 = 0;
                z3 = false;
            }
            if (j3 != 0) {
                j |= z3 ? 1073758208L : 536879104L;
            }
            if ((j & 1025) != 0) {
                j |= z2 ? 21475098624L : 10737549312L;
            }
            boolean z4 = i14 == 1;
            boolean z5 = i14 == 5;
            long j4 = j;
            boolean z6 = i14 == 2;
            boolean z7 = i14 == 3;
            boolean z8 = z6;
            boolean z9 = i14 == 4;
            int i15 = z3 ? 8 : 0;
            int i16 = z3 ? 0 : 8;
            int i17 = z2 ? 0 : 8;
            if ((j4 & 1025) != 0) {
                j4 |= z4 ? 1048576L : PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
            }
            if ((j4 & 1025) != 0) {
                j4 |= z5 ? 67108864L : 33554432L;
            }
            if ((j4 & 1025) != 0) {
                j4 |= z8 ? 16777216L : 8388608L;
            }
            if ((j4 & 1025) != 0) {
                j4 |= z7 ? 4194304L : PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE;
            }
            if ((j4 & 1025) != 0) {
                j4 |= z9 ? 268435456L : 134217728L;
            }
            int i18 = z4 ? 0 : 8;
            int i19 = z5 ? 0 : 8;
            int i20 = z8 ? 0 : 8;
            boolean z10 = z2;
            i3 = i15;
            i = i16;
            i7 = i20;
            z = z10;
            int i21 = i19;
            i4 = z7 ? 0 : 8;
            i2 = i17;
            i8 = i21;
            long j5 = j4;
            num = num3;
            num2 = num4;
            i5 = i18;
            i6 = z9 ? 0 : 8;
            j2 = j5;
        } else {
            j2 = j;
            num = num3;
            num2 = num4;
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
            i8 = 0;
            z = false;
        }
        if ((j2 & 1028) != 0) {
            int safeUnbox = ViewDataBinding.safeUnbox(num);
            str = str4;
            i9 = safeUnbox;
        } else {
            str = str4;
            i9 = 0;
        }
        if ((j2 & 1536) != 0) {
            int safeUnbox2 = ViewDataBinding.safeUnbox(num2);
            str2 = str7;
            i10 = safeUnbox2;
        } else {
            str2 = str7;
            i10 = 0;
        }
        if ((j2 & 10737418240L) != 0) {
            boolean z11 = scanItem != null ? scanItem.autoAdd : false;
            if ((j2 & 8589934592L) != 0) {
                j2 |= z11 ? PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM : PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
            }
            i12 = ((j2 & 8589934592L) == 0 || !z11) ? 0 : 8;
            long j6 = j2 & 2147483648L;
            if (j6 != 0) {
                if (j6 != 0) {
                    j2 |= !z11 ? 65536L : PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
                }
                if (!z11) {
                    i11 = 8;
                }
            }
            i11 = 0;
        } else {
            i11 = 0;
            i12 = 0;
        }
        long j7 = j2 & 1025;
        if (j7 != 0) {
            if (z) {
                i11 = 8;
            }
            str3 = str5;
            i13 = z ? 8 : i12;
        } else {
            str3 = str5;
            i11 = 0;
            i13 = 0;
        }
        if (j7 != 0) {
            this.ivAdd.setVisibility(i11);
            this.ivUpgradeFail.setVisibility(i8);
            this.ivUpgradeSuccess.setVisibility(i6);
            this.ivUpgradeWaiting.setVisibility(i7);
            this.layoutAdd.setVisibility(i3);
            this.layoutUpgradeStatus.setVisibility(i);
            this.mboundView9.setVisibility(i2);
            this.tvAdd.setVisibility(i13);
            this.tvCurrentVersion.setVisibility(i);
            this.tvNewVersion.setVisibility(i);
            this.tvUpgradeItem.setVisibility(i5);
            this.tvUpgradeProgress.setVisibility(i4);
        }
        if ((j2 & 1152) != 0) {
            ViewAdapter.onClickCommand(this.ivAdd, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBg, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvAdd, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvUpgradeItem, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvUpgradeProgress, bindingCommand, false);
        }
        if ((j2 & 1028) != 0) {
            ViewAdapter.setSrc(this.ivIcon, i9);
        }
        if ((j2 & 1536) != 0) {
            ViewAdapter.setCurSignal(this.signalView, i10);
        }
        if ((j2 & 1056) != 0) {
            TextViewBindingAdapter.setText(this.tvCurrentVersion, str6);
        }
        if ((j2 & 1280) != 0) {
            TextViewBindingAdapter.setText(this.tvName, str8);
        }
        if ((j2 & 1032) != 0) {
            TextViewBindingAdapter.setText(this.tvNewVersion, str3);
        }
        if ((j2 & 1088) != 0) {
            TextViewBindingAdapter.setText(this.tvUpgradeItem, str2);
        }
        if ((j2 & 1026) != 0) {
            TextViewBindingAdapter.setText(this.tvUpgradeProgress, str);
        }
    }
}