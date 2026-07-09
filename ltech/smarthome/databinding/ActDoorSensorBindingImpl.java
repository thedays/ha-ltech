package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActDoorSensorBindingImpl extends ActDoorSensorBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView5;
    private final AppCompatTextView mboundView6;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(23);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_content, 9);
        sparseIntArray.put(R.id.layout_pic, 10);
        sparseIntArray.put(R.id.iv_door_left, 11);
        sparseIntArray.put(R.id.iv_door_right, 12);
        sparseIntArray.put(R.id.tv_gateway_state, 13);
        sparseIntArray.put(R.id.iv_battery, 14);
        sparseIntArray.put(R.id.tv_battery, 15);
        sparseIntArray.put(R.id.iv_auto, 16);
        sparseIntArray.put(R.id.layout_log, 17);
        sparseIntArray.put(R.id.iv_log, 18);
        sparseIntArray.put(R.id.tv_date, 19);
        sparseIntArray.put(R.id.rv_log, 20);
        sparseIntArray.put(R.id.tv_no_log, 21);
        sparseIntArray.put(R.id.group_log, 22);
    }

    public ActDoorSensorBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }

    private ActDoorSensorBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Group) bindings[22], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[18], (RelativeLayout) bindings[4], (ConstraintLayout) bindings[2], (ConstraintLayout) bindings[9], (ConstraintLayout) bindings[17], (LinearLayout) bindings[10], (RecyclerView) bindings[20], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.layoutAuto.setTag(null);
        this.layoutBattery.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[6];
        this.mboundView6 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        setContainedBinding(this.title);
        this.tvBatteryTip.setTag(null);
        this.tvBottom.setTag(null);
        this.tvState.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
        }
        this.title.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActDoorSensorBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActDoorSensorBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        BindingCommand<View> bindingCommand = this.mClickCommand;
        TitleDefault titleDefault = this.mTitle;
        long j2 = 6 & j;
        if ((5 & j) != 0) {
            ViewAdapter.onClickCommand(this.layoutAuto, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBattery, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvBottom, bindingCommand, false);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.mboundView5, true);
            ViewAdapter.setTextBold(this.mboundView6, true);
            ViewAdapter.setTextBold(this.tvBatteryTip, true);
            ViewAdapter.setTextBold(this.tvBottom, true);
            ViewAdapter.setTextBold(this.tvState, true);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}