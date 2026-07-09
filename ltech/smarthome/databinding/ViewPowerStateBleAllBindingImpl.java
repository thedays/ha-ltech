package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.SwitchSeekBarView;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class ViewPowerStateBleAllBindingImpl extends ViewPowerStateBleAllBinding {
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
        sparseIntArray.put(R.id.sb_rgb, 11);
        sparseIntArray.put(R.id.seekbar_brt, 12);
    }

    public ViewPowerStateBleAllBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ViewPowerStateBleAllBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CircleImageView) bindings[3], (ConstraintLayout) bindings[0], (SwitchButton) bindings[11], (SwitchSeekBarView) bindings[12], (RangeSeekBar) bindings[5], (SwitchSeekBarView) bindings[10], (ColorSeekBar) bindings[2], (SwitchSeekBarView) bindings[9], (SwitchSeekBarView) bindings[8], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[6], (View) bindings[7]);
        this.mDirtyFlags = -1L;
        this.civColor.setTag(null);
        this.layoutPowerState.setTag(null);
        this.seekbarCt.setTag(null);
        this.seekbarCwBrt.setTag(null);
        this.seekbarRgb.setTag(null);
        this.switchSeekbarCw.setTag(null);
        this.switchSeekbarW.setTag(null);
        this.tvColorTip.setTag(null);
        this.tvCtTip.setTag(null);
        this.tvCtValue.setTag(null);
        this.viewDivider.setTag(null);
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
        if (63 == variableId) {
            setRgbGone((Boolean) variable);
            return true;
        }
        if (16 == variableId) {
            setCtGone((Boolean) variable);
            return true;
        }
        if (95 == variableId) {
            setWGone((Boolean) variable);
            return true;
        }
        if (19 != variableId) {
            return false;
        }
        setCwGone((Boolean) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ViewPowerStateBleAllBinding
    public void setRgbGone(Boolean RgbGone) {
        this.mRgbGone = RgbGone;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(63);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ViewPowerStateBleAllBinding
    public void setCtGone(Boolean CtGone) {
        this.mCtGone = CtGone;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(16);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ViewPowerStateBleAllBinding
    public void setWGone(Boolean WGone) {
        this.mWGone = WGone;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(95);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ViewPowerStateBleAllBinding
    public void setCwGone(Boolean CwGone) {
        this.mCwGone = CwGone;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(19);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x008f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ViewPowerStateBleAllBindingImpl.executeBindings():void");
    }
}