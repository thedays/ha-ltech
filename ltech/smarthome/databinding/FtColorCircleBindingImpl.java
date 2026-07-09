package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.jaygoo.widget.RangeSeekBar;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.device.light.ActColorLightVM;
import com.ltech.smarthome.view.ColorPickerPointView;
import com.ltech.smarthome.view.DeliverTouchLayout;
import com.ltech.smarthome.view.TextSeekBarView;

/* loaded from: classes3.dex */
public class FtColorCircleBindingImpl extends FtColorCircleBinding {
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
        sparseIntArray.put(R.id.layout_normal, 10);
        sparseIntArray.put(R.id.view_bottom, 11);
        sparseIntArray.put(R.id.layout_ct, 12);
        sparseIntArray.put(R.id.line, 13);
        sparseIntArray.put(R.id.tv_value, 14);
        sparseIntArray.put(R.id.ct1_seekbar, 15);
        sparseIntArray.put(R.id.ct2_seekbar, 16);
        sparseIntArray.put(R.id.brt_seekbar, 17);
        sparseIntArray.put(R.id.rv_color, 18);
        sparseIntArray.put(R.id.layout_ccpv, 19);
        sparseIntArray.put(R.id.ccpv, 20);
        sparseIntArray.put(R.id.line_bottom, 21);
        sparseIntArray.put(R.id.line_top, 22);
        sparseIntArray.put(R.id.layout_gradient, 23);
        sparseIntArray.put(R.id.layout_ccpv2, 24);
        sparseIntArray.put(R.id.ccpv2, 25);
        sparseIntArray.put(R.id.line_bottom2, 26);
        sparseIntArray.put(R.id.line_top2, 27);
        sparseIntArray.put(R.id.tv_tip, 28);
        sparseIntArray.put(R.id.tv_label, 29);
        sparseIntArray.put(R.id.rv_devices, 30);
        sparseIntArray.put(R.id.iv_empty_devices, 31);
        sparseIntArray.put(R.id.tv_empty_devices, 32);
    }

    public FtColorCircleBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }

    private FtColorCircleBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (TextSeekBarView) bindings[17], (ColorPickerPointView) bindings[20], (ColorPickerPointView) bindings[25], (TextSeekBarView) bindings[15], (RangeSeekBar) bindings[16], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[31], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[9], (DeliverTouchLayout) bindings[19], (DeliverTouchLayout) bindings[24], (RelativeLayout) bindings[12], (ConstraintLayout) bindings[23], (ConstraintLayout) bindings[10], (View) bindings[13], (View) bindings[21], (View) bindings[26], (View) bindings[22], (View) bindings[27], (RecyclerView) bindings[18], (RecyclerView) bindings[30], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[28], (AppCompatTextView) bindings[14], (View) bindings[11]);
        this.mDirtyFlags = -1L;
        this.ivChangeBrt.setTag(null);
        this.ivChangePic.setTag(null);
        this.ivCwBrt.setTag(null);
        this.ivGradient.setTag(null);
        this.ivNormal.setTag(null);
        this.ivRgbBrt.setTag(null);
        this.ivSort.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvCt1.setTag(null);
        this.tvCt2.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActColorLightVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtColorCircleBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtColorCircleBinding
    public void setViewmodel(ActColorLightVM Viewmodel) {
        this.mViewmodel = Viewmodel;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        if ((j & 5) != 0) {
            ViewAdapter.onClickCommand(this.ivChangeBrt, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivChangePic, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivCwBrt, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivGradient, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivNormal, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivRgbBrt, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivSort, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvCt1, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvCt2, bindingCommand, false);
        }
    }
}