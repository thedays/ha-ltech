package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActGroupCurtainSettingBindingImpl extends ActGroupCurtainSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(19);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{10}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_group_name, 11);
        sparseIntArray.put(R.id.iv_group_name_go, 12);
        sparseIntArray.put(R.id.tv_room_name, 13);
        sparseIntArray.put(R.id.iv_room_name_go, 14);
        sparseIntArray.put(R.id.tv_device_count, 15);
        sparseIntArray.put(R.id.iv_device_count_go, 16);
        sparseIntArray.put(R.id.tv_open_type, 17);
        sparseIntArray.put(R.id.iv_motor_direction_name_go, 18);
    }

    public ActGroupCurtainSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }

    private ActGroupCurtainSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[14], (ConstraintLayout) bindings[3], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[1], (ConstraintLayout) bindings[7], (LayoutTitleDefaultBinding) bindings[10], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[15], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[13], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[8]);
        this.mDirtyFlags = -1L;
        this.layoutChangeRoom.setTag(null);
        this.layoutEditGroup.setTag(null);
        this.layoutGroupName.setTag(null);
        this.layoutMotorOpenType.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvDelete.setTag(null);
        this.tvGroupTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvSetCurtainMotorTip.setTag(null);
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

    @Override // com.ltech.smarthome.databinding.ActGroupCurtainSettingBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActGroupCurtainSettingBinding
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
            ViewAdapter.onClickCommand(this.layoutChangeRoom, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutEditGroup, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutGroupName, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutMotorOpenType, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvDelete, bindingCommand, false);
        }
        if (j2 != 0) {
            this.title.setTitle(titleDefault);
        }
        if ((j & 4) != 0) {
            ViewAdapter.setTextBold(this.tvDelete, true);
            ViewAdapter.setTextBold(this.tvGroupTip, true);
            ViewAdapter.setTextBold(this.tvNameTip, true);
            ViewAdapter.setTextBold(this.tvRoomTip, true);
            ViewAdapter.setTextBold(this.tvSetCurtainMotorTip, true);
        }
        executeBindingsOn(this.title);
    }
}