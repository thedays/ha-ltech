package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.intercom.ActIntercomVM;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActIntercomBindingImpl extends ActIntercomBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(33);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 9);
        sparseIntArray.put(R.id.iv_qrcode, 10);
        sparseIntArray.put(R.id.guideline, 11);
        sparseIntArray.put(R.id.intercom_qrcode_tip, 12);
        sparseIntArray.put(R.id.group_click, 13);
        sparseIntArray.put(R.id.group_qrcode, 14);
        sparseIntArray.put(R.id.iv_bluetooth, 15);
        sparseIntArray.put(R.id.iv_search, 16);
        sparseIntArray.put(R.id.tv_bluetooth, 17);
        sparseIntArray.put(R.id.iv_bluetooth_go, 18);
        sparseIntArray.put(R.id.bluetooth_guideline, 19);
        sparseIntArray.put(R.id.iv_door, 20);
        sparseIntArray.put(R.id.iv_door_go, 21);
        sparseIntArray.put(R.id.door_guideline, 22);
        sparseIntArray.put(R.id.door_line, 23);
        sparseIntArray.put(R.id.layout_door, 24);
        sparseIntArray.put(R.id.rv_door, 25);
        sparseIntArray.put(R.id.ll_door, 26);
        sparseIntArray.put(R.id.iv_password, 27);
        sparseIntArray.put(R.id.iv_password_go, 28);
        sparseIntArray.put(R.id.password_guideline, 29);
        sparseIntArray.put(R.id.iv_record, 30);
        sparseIntArray.put(R.id.iv_record_go, 31);
        sparseIntArray.put(R.id.record_guideline, 32);
    }

    public ActIntercomBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 33, sIncludes, sViewsWithIds));
    }

    private ActIntercomBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Guideline) bindings[19], (Guideline) bindings[22], (View) bindings[23], (Group) bindings[13], (Group) bindings[14], (Guideline) bindings[11], (AppCompatImageView) bindings[4], (AppCompatTextView) bindings[12], (AppCompatImageView) bindings[15], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[27], (AppCompatImageView) bindings[28], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[30], (AppCompatImageView) bindings[31], (AppCompatImageView) bindings[16], (ConstraintLayout) bindings[5], (LinearLayout) bindings[24], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[7], (ConstraintLayout) bindings[6], (LinearLayout) bindings[26], (Guideline) bindings[29], (Guideline) bindings[32], (SmartRefreshLayout) bindings[9], (RecyclerView) bindings[25], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[2]);
        this.mDirtyFlags = -1L;
        this.intercomQrcode.setTag(null);
        this.ivQrcodeGo.setTag(null);
        this.layoutBluetooth.setTag(null);
        this.layoutQrcode.setTag(null);
        this.layoutRecord.setTag(null);
        this.layoutTempKey.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvQrcode.setTag(null);
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActIntercomVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActIntercomBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActIntercomBinding
    public void setViewmodel(ActIntercomVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(92);
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
        TitleDefault titleDefault = this.mTitle;
        ActIntercomVM actIntercomVM = this.mViewmodel;
        long j2 = 5 & j;
        long j3 = j & 6;
        BindingCommand<View> bindingCommand = (j3 == 0 || actIntercomVM == null) ? null : actIntercomVM.viewClick;
        if (j3 != 0) {
            ViewAdapter.onClickCommand(this.intercomQrcode, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivQrcodeGo, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBluetooth, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutQrcode, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutRecord, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutTempKey, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvQrcode, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}