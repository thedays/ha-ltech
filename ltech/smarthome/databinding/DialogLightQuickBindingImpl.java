package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.view.ColorSeekBar;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;
import de.hdodenhof.circleimageview.CircleImageView;

/* loaded from: classes3.dex */
public class DialogLightQuickBindingImpl extends DialogLightQuickBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.sb, 6);
        sparseIntArray.put(R.id.view16, 7);
        sparseIntArray.put(R.id.sb_ct, 8);
        sparseIntArray.put(R.id.tv_ct_percent, 9);
        sparseIntArray.put(R.id.csb_color_bar, 10);
        sparseIntArray.put(R.id.civ_color, 11);
        sparseIntArray.put(R.id.tv_brt, 12);
        sparseIntArray.put(R.id.sb_brt, 13);
        sparseIntArray.put(R.id.group_ct, 14);
        sparseIntArray.put(R.id.group_color, 15);
    }

    public DialogLightQuickBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private DialogLightQuickBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (CircleImageView) bindings[11], (ColorSeekBar) bindings[10], (Group) bindings[15], (Group) bindings[14], (AppCompatImageView) bindings[2], (SwitchButton) bindings[6], (LightBrtBar) bindings[13], (RangeSeekBar) bindings[8], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[1], (View) bindings[7]);
        this.mDirtyFlags = -1L;
        this.ivDeviceMore.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvBrtTip.setTag(null);
        this.tvColorTip.setTag(null);
        this.tvCtTip.setTag(null);
        this.tvName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
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
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogLightQuickBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        if ((3 & j) != 0) {
            ViewAdapter.onClickCommand(this.ivDeviceMore, bindingCommand, false);
        }
        if ((j & 2) != 0) {
            ViewAdapter.setTextBold(this.tvBrtTip, true);
            ViewAdapter.setTextBold(this.tvColorTip, true);
            ViewAdapter.setTextBold(this.tvCtTip, true);
            ViewAdapter.setTextBold(this.tvName, true);
        }
    }
}