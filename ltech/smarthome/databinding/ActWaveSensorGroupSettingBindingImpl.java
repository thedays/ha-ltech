package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActWaveSensorGroupSettingBindingImpl extends ActWaveSensorGroupSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView18;
    private final AppCompatTextView mboundView21;
    private final AppCompatTextView mboundView24;
    private final AppCompatTextView mboundView26;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(42);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{28}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 29);
        sparseIntArray.put(R.id.tv_group_name, 30);
        sparseIntArray.put(R.id.tv_room_name, 31);
        sparseIntArray.put(R.id.tv_device_count, 32);
        sparseIntArray.put(R.id.iv_device_count_go, 33);
        sparseIntArray.put(R.id.tv_illuminance, 34);
        sparseIntArray.put(R.id.tv_sensitivity, 35);
        sparseIntArray.put(R.id.tv_lux_value, 36);
        sparseIntArray.put(R.id.tv_ct_value, 37);
        sparseIntArray.put(R.id.tv_related_num, 38);
        sparseIntArray.put(R.id.iv_scene_go, 39);
        sparseIntArray.put(R.id.tv_delay, 40);
        sparseIntArray.put(R.id.iv_delay_go, 41);
    }

    public ActWaveSensorGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 42, sIncludes, sViewsWithIds));
    }

    private ActWaveSensorGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[41], (AppCompatImageView) bindings[33], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[19], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[39], (AppCompatImageView) bindings[14], (RelativeLayout) bindings[25], (ConstraintLayout) bindings[4], (ConstraintLayout) bindings[7], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[23], (ConstraintLayout) bindings[20], (ConstraintLayout) bindings[9], (ConstraintLayout) bindings[17], (ConstraintLayout) bindings[12], (RelativeLayout) bindings[15], (SmartRefreshLayout) bindings[29], (LayoutTitleDefaultBinding) bindings[28], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[40], (AppCompatTextView) bindings[27], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[34], (AppCompatTextView) bindings[36], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[38], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[35]);
        this.mDirtyFlags = -1L;
        this.ivCtGo.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivIlluminanceGo.setTag(null);
        this.ivLuxGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivSensitivityGo.setTag(null);
        this.layoutAutomationDelay.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutEditGroup.setTag(null);
        this.layoutGroupName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSetCt.setTag(null);
        this.layoutSetIlluminance.setTag(null);
        this.layoutSetLux.setTag(null);
        this.layoutSetSensitivity.setTag(null);
        this.layoutTestMode.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[16];
        this.mboundView16 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[18];
        this.mboundView18 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[21];
        this.mboundView21 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        setContainedBinding(this.title);
        this.tvDelete.setTag(null);
        this.tvGroupTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        if (43 == variableId) {
            setManagerOrOwner((Boolean) variable);
            return true;
        }
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

    @Override // com.ltech.smarthome.databinding.ActWaveSensorGroupSettingBinding
    public void setManagerOrOwner(Boolean ManagerOrOwner) {
        this.mManagerOrOwner = ManagerOrOwner;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(43);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorGroupSettingBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActWaveSensorGroupSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f7  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActWaveSensorGroupSettingBindingImpl.executeBindings():void");
    }
}