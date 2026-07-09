package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.ui.device.gqx.ActGqxVM;

/* loaded from: classes3.dex */
public class FtGqxActionBindingImpl extends FtGqxActionBinding {
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
        sparseIntArray.put(R.id.tv_dev_name, 4);
        sparseIntArray.put(R.id.tv_dev_tip, 5);
        sparseIntArray.put(R.id.layout_action, 6);
        sparseIntArray.put(R.id.iv_bind_1, 7);
        sparseIntArray.put(R.id.tv_bind_1, 8);
        sparseIntArray.put(R.id.tv_bind_action_1, 9);
        sparseIntArray.put(R.id.tv_bind_action_more_1, 10);
        sparseIntArray.put(R.id.iv_bind_2, 11);
        sparseIntArray.put(R.id.tv_bind_2, 12);
        sparseIntArray.put(R.id.tv_bind_action_2, 13);
        sparseIntArray.put(R.id.tv_bind_action_sub_2, 14);
        sparseIntArray.put(R.id.tv_bind_action_more_2, 15);
        sparseIntArray.put(R.id.iv_bind_3, 16);
        sparseIntArray.put(R.id.tv_bind_3, 17);
        sparseIntArray.put(R.id.tv_bind_action_3, 18);
        sparseIntArray.put(R.id.tv_bind_action_more_3, 19);
        sparseIntArray.put(R.id.tv_intro_title, 20);
        sparseIntArray.put(R.id.tv_intro_tip1, 21);
        sparseIntArray.put(R.id.tv_intro_tip2, 22);
        sparseIntArray.put(R.id.iv1, 23);
        sparseIntArray.put(R.id.tv1, 24);
        sparseIntArray.put(R.id.iv2, 25);
        sparseIntArray.put(R.id.tv2, 26);
        sparseIntArray.put(R.id.iv3, 27);
        sparseIntArray.put(R.id.tv3, 28);
        sparseIntArray.put(R.id.group_action, 29);
        sparseIntArray.put(R.id.group_intro, 30);
    }

    public FtGqxActionBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 31, sIncludes, sViewsWithIds));
    }

    private FtGqxActionBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (Group) bindings[29], (Group) bindings[30], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[25], (AppCompatImageView) bindings[27], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[16], (LinearLayout) bindings[6], (RelativeLayout) bindings[1], (RelativeLayout) bindings[2], (RelativeLayout) bindings[3], (TextView) bindings[24], (TextView) bindings[26], (TextView) bindings[28], (TextView) bindings[8], (TextView) bindings[12], (TextView) bindings[17], (TextView) bindings[9], (TextView) bindings[13], (TextView) bindings[18], (ImageView) bindings[10], (ImageView) bindings[15], (ImageView) bindings[19], (TextView) bindings[14], (TextView) bindings[4], (TextView) bindings[5], (TextView) bindings[21], (TextView) bindings[22], (AppCompatTextView) bindings[20]);
        this.mDirtyFlags = -1L;
        this.layoutBind1.setTag(null);
        this.layoutBind2.setTag(null);
        this.layoutBind3.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActGqxVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtGqxActionBinding
    public void setViewmodel(ActGqxVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        ActGqxVM actGqxVM = this.mViewmodel;
        long j2 = j & 3;
        BindingCommand<View> bindingCommand = (j2 == 0 || actGqxVM == null) ? null : actGqxVM.viewClick;
        if (j2 != 0) {
            ViewAdapter.onClickCommand(this.layoutBind1, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBind2, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutBind3, bindingCommand, false);
        }
    }
}