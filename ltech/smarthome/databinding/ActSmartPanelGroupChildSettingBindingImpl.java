package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelGroupChildSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActSmartPanelGroupChildSettingBindingImpl extends ActSmartPanelGroupChildSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(25);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{14}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 15);
        sparseIntArray.put(R.id.act_add_device_scroll, 16);
        sparseIntArray.put(R.id.iv_scene_name_go, 17);
        sparseIntArray.put(R.id.tv_subordinate_device, 18);
        sparseIntArray.put(R.id.iv_sub_device_name_go, 19);
        sparseIntArray.put(R.id.tv_room_name, 20);
        sparseIntArray.put(R.id.iv_room_name_go, 21);
        sparseIntArray.put(R.id.iv_icon, 22);
        sparseIntArray.put(R.id.tv_related_num, 23);
        sparseIntArray.put(R.id.iv_scene_go, 24);
    }

    public ActSmartPanelGroupChildSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 25, sIncludes, sViewsWithIds));
    }

    private ActSmartPanelGroupChildSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (NestedScrollView) bindings[16], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[24], (AppCompatImageView) bindings[17], (AppCompatImageView) bindings[19], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[6], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[12], (ConstraintLayout) bindings[4], (SmartRefreshLayout) bindings[15], (SwitchButton) bindings[11], (LayoutTitleDefaultBinding) bindings[14], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[20], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[18]);
        this.mDirtyFlags = -1L;
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutGroupName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSubDevice.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        this.sbAddToSmart.setTag(null);
        setContainedBinding(this.title);
        this.tvDeviceName.setTag(null);
        this.tvDeviceTip.setTag(null);
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActSmartPanelGroupChildSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelGroupChildSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelGroupChildSettingBinding
    public void setViewmodel(ActSmartPanelGroupChildSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 4;
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
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeViewmodelIsAddToRoom((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelIsAddToRoom(MutableLiveData<Boolean> ViewmodelIsAddToRoom, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        long j2;
        String str;
        BindingCommand<View> bindingCommand;
        boolean z;
        boolean z2;
        Place place;
        Group group;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        TitleDefault titleDefault = this.mTitle;
        ActSmartPanelGroupChildSettingVM actSmartPanelGroupChildSettingVM = this.mViewmodel;
        Place place2 = null;
        if ((j & 13) != 0) {
            long j3 = j & 12;
            if (j3 != 0) {
                if (actSmartPanelGroupChildSettingVM != null) {
                    bindingCommand = actSmartPanelGroupChildSettingVM.commonClick;
                    place = actSmartPanelGroupChildSettingVM.getCurrentPlace();
                    j2 = 13;
                    group = actSmartPanelGroupChildSettingVM.controlGroup;
                } else {
                    j2 = 13;
                    group = null;
                    bindingCommand = null;
                    place = null;
                }
                z2 = place != null ? place.isOwner() : false;
                if (j3 != 0) {
                    j = z2 ? j | 32 : j | 16;
                }
                str = group != null ? group.getGroupName() : null;
            } else {
                j2 = 13;
                str = null;
                bindingCommand = null;
                place = null;
                z2 = false;
            }
            MutableLiveData<Boolean> mutableLiveData = actSmartPanelGroupChildSettingVM != null ? actSmartPanelGroupChildSettingVM.isAddToRoom : null;
            updateLiveDataRegistration(0, mutableLiveData);
            z = ViewDataBinding.safeUnbox(mutableLiveData != null ? mutableLiveData.getValue() : null);
            place2 = place;
        } else {
            j2 = 13;
            str = null;
            bindingCommand = null;
            z = false;
            z2 = false;
        }
        boolean isManager = ((16 & j) == 0 || place2 == null) ? false : place2.isManager();
        long j4 = j & 12;
        if (j4 != 0) {
            if (z2) {
                isManager = true;
            }
            if (j4 != 0) {
                j |= isManager ? 128L : 64L;
            }
        } else {
            isManager = false;
        }
        if ((j & 12) != 0) {
            ViewAdapter.onClickCommand(this.layoutChangeIcon, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutChangeRoom, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutGroupName, bindingCommand, false);
            ViewAdapter.onClickCommand(this.layoutSceneAndAutomation, bindingCommand, false);
            this.layoutSubDevice.setEnabled(isManager);
            TextViewBindingAdapter.setText(this.tvDeviceName, str);
        }
        if ((8 & j) != 0) {
            ViewAdapter.setTextBold(this.mboundView10, true);
            ViewAdapter.setTextBold(this.mboundView13, true);
            ViewAdapter.setTextBold(this.mboundView9, true);
            ViewAdapter.setTextBold(this.tvDeviceTip, true);
            ViewAdapter.setTextBold(this.tvNameTip, true);
            ViewAdapter.setTextBold(this.tvRoomTip, true);
        }
        if ((j & j2) != 0) {
            ViewAdapter.setChecked(this.sbAddToSmart, z);
        }
        if ((j & 10) != 0) {
            this.title.setTitle(titleDefault);
        }
        executeBindingsOn(this.title);
    }
}