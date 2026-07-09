package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActRhythmsSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.VoisePlayingIcon;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActGroupSettingBindingImpl extends ActGroupSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView20;
    private final TextView mboundView21;
    private final AppCompatTextView mboundView22;
    private final AppCompatTextView mboundView26;
    private final AppCompatTextView mboundView35;
    private final AppCompatTextView mboundView41;
    private final AppCompatTextView mboundView43;
    private final AppCompatTextView mboundView6;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(58);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{45}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 46);
        sparseIntArray.put(R.id.tv_group_name, 47);
        sparseIntArray.put(R.id.iv_group_name_go, 48);
        sparseIntArray.put(R.id.tv_room_name, 49);
        sparseIntArray.put(R.id.iv_room_name_go, 50);
        sparseIntArray.put(R.id.iv_icon, 51);
        sparseIntArray.put(R.id.tv_device_count, 52);
        sparseIntArray.put(R.id.iv_device_count_go, 53);
        sparseIntArray.put(R.id.layout_rhythms_switch, 54);
        sparseIntArray.put(R.id.layout_rhythms_state, 55);
        sparseIntArray.put(R.id.edit_plan_time_layout, 56);
        sparseIntArray.put(R.id.repeat_week_tv, 57);
    }

    public ActGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 58, sIncludes, sViewsWithIds));
    }

    private ActGroupSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 10, (LinearLayout) bindings[56], (TextView) bindings[32], (AppCompatImageView) bindings[53], (AppCompatImageView) bindings[48], (AppCompatImageView) bindings[51], (VoisePlayingIcon) bindings[16], (AppCompatImageView) bindings[13], (AppCompatImageView) bindings[50], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[28], (AppCompatImageView) bindings[37], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[3], (ConstraintLayout) bindings[7], (RelativeLayout) bindings[31], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[42], (RelativeLayout) bindings[33], (RelativeLayout) bindings[24], (RelativeLayout) bindings[38], (RelativeLayout) bindings[9], (RelativeLayout) bindings[55], (LinearLayout) bindings[54], (RelativeLayout) bindings[40], (RelativeLayout) bindings[29], (RelativeLayout) bindings[18], (AppCompatTextView) bindings[34], (AppCompatTextView) bindings[25], (AppCompatImageView) bindings[17], (SmartRefreshLayout) bindings[46], (AppCompatTextView) bindings[39], (AppCompatTextView) bindings[57], (AppCompatTextView) bindings[10], (LinearLayout) bindings[14], (AppCompatTextView) bindings[15], (SwitchButton) bindings[12], (TextView) bindings[30], (AppCompatTextView) bindings[19], (LayoutTitleDefaultBinding) bindings[45], (AppCompatTextView) bindings[44], (AppCompatTextView) bindings[52], (AppCompatTextView) bindings[47], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[36], (AppCompatTextView) bindings[27], (AppCompatTextView) bindings[49], (AppCompatTextView) bindings[4]);
        this.mDirtyFlags = -1L;
        this.endTimeTv.setTag(null);
        this.ivPlayAnim.setTag(null);
        this.ivRhythmsGo.setTag(null);
        this.ivSelected1.setTag(null);
        this.ivSelected2.setTag(null);
        this.ivSelected3.setTag(null);
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutEditGroup.setTag(null);
        this.layoutEndTime.setTag(null);
        this.layoutGroupName.setTag(null);
        this.layoutOnOffTime.setTag(null);
        this.layoutPlan.setTag(null);
        this.layoutPlanTime.setTag(null);
        this.layoutRepeatDate.setTag(null);
        this.layoutRhythms.setTag(null);
        this.layoutSetOnState.setTag(null);
        this.layoutStartTime.setTag(null);
        this.layoutSunset.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[11];
        this.mboundView11 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        TextView textView = (TextView) bindings[21];
        this.mboundView21 = textView;
        textView.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[22];
        this.mboundView22 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[35];
        this.mboundView35 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[41];
        this.mboundView41 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[43];
        this.mboundView43 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[6];
        this.mboundView6 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        this.planLabel.setTag(null);
        this.planTimeLabel.setTag(null);
        this.playIv.setTag(null);
        this.repeatDateLabel.setTag(null);
        this.rhythmsLabelTv.setTag(null);
        this.rhythmsSettingLayout.setTag(null);
        this.rhythmsStateLabel.setTag(null);
        this.sbRhythmsText.setTag(null);
        this.startTimeTv.setTag(null);
        this.sunsetLabel.setTag(null);
        setContainedBinding(this.title);
        this.tvDelete.setTag(null);
        this.tvGroupTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvPlan.setTag(null);
        this.tvPlanTime.setTag(null);
        this.tvRoomTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16384L;
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 == variableId) {
            setViewmodel((ActRhythmsSettingVM) variable);
            return true;
        }
        if (43 != variableId) {
            return false;
        }
        setManagerOrOwner((Boolean) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActGroupSettingBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActGroupSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActGroupSettingBinding
    public void setViewmodel(ActRhythmsSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActGroupSettingBinding
    public void setManagerOrOwner(Boolean ManagerOrOwner) {
        this.mManagerOrOwner = ManagerOrOwner;
        synchronized (this) {
            this.mDirtyFlags |= 8192;
        }
        notifyPropertyChanged(43);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeViewmodelShowRhythmsSetting((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelPlanTimeText((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelRhythmsIsPlay((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelPlanText((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelSunSetText((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelStarTimeText((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelEndTimeText((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelSunRiseText((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelShowRhythms((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelShowRhythmsModel((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelShowRhythmsSetting(MutableLiveData<Boolean> ViewmodelShowRhythmsSetting, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelPlanTimeText(MutableLiveData<String> ViewmodelPlanTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelRhythmsIsPlay(MutableLiveData<Boolean> ViewmodelRhythmsIsPlay, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelPlanText(MutableLiveData<String> ViewmodelPlanText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelSunSetText(MutableLiveData<String> ViewmodelSunSetText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelStarTimeText(MutableLiveData<String> ViewmodelStarTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelEndTimeText(MutableLiveData<String> ViewmodelEndTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeViewmodelSunRiseText(MutableLiveData<String> ViewmodelSunRiseText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythms(MutableLiveData<Boolean> ViewmodelShowRhythms, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythmsModel(MutableLiveData<Integer> ViewmodelShowRhythmsModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0162  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 1066
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActGroupSettingBindingImpl.executeBindings():void");
    }
}