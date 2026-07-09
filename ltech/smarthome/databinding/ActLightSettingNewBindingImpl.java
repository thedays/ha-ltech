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
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.light.ActLightSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.VoisePlayingIcon;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActLightSettingNewBindingImpl extends ActLightSettingNewBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private long mDirtyFlags_1;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView104;
    private final AppCompatTextView mboundView105;
    private final AppCompatTextView mboundView107;
    private final AppCompatTextView mboundView108;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView110;
    private final AppCompatTextView mboundView111;
    private final AppCompatTextView mboundView114;
    private final AppCompatImageView mboundView115;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView38;
    private final AppCompatTextView mboundView40;
    private final AppCompatImageView mboundView42;
    private final AppCompatTextView mboundView44;
    private final AppCompatTextView mboundView49;
    private final AppCompatTextView mboundView51;
    private final AppCompatTextView mboundView53;
    private final AppCompatTextView mboundView59;
    private final AppCompatTextView mboundView65;
    private final AppCompatTextView mboundView74;
    private final TextView mboundView75;
    private final AppCompatTextView mboundView76;
    private final AppCompatTextView mboundView80;
    private final AppCompatTextView mboundView89;
    private final AppCompatTextView mboundView9;
    private final AppCompatTextView mboundView95;
    private final AppCompatTextView mboundView97;
    private final AppCompatTextView mboundView99;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(144);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{118}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 119);
        sparseIntArray.put(R.id.tv_room_name, 120);
        sparseIntArray.put(R.id.iv_icon, 121);
        sparseIntArray.put(R.id.tv_current, 122);
        sparseIntArray.put(R.id.tv_pwm_frequency, 123);
        sparseIntArray.put(R.id.tv_state, 124);
        sparseIntArray.put(R.id.rv_light_setting, 125);
        sparseIntArray.put(R.id.tv_dmx_type, 126);
        sparseIntArray.put(R.id.iv_dmx_go, 127);
        sparseIntArray.put(R.id.tv_dim_depth, 128);
        sparseIntArray.put(R.id.iv_depth_go, 129);
        sparseIntArray.put(R.id.tv_line, 130);
        sparseIntArray.put(R.id.iv_line_go, 131);
        sparseIntArray.put(R.id.tv_dbatch_set, 132);
        sparseIntArray.put(R.id.iv_batch_go, 133);
        sparseIntArray.put(R.id.tv_k_range, 134);
        sparseIntArray.put(R.id.layout_rhythms_switch, 135);
        sparseIntArray.put(R.id.layout_rhythms_state, 136);
        sparseIntArray.put(R.id.edit_plan_time_layout, 137);
        sparseIntArray.put(R.id.repeat_week_tv, 138);
        sparseIntArray.put(R.id.device_replace_go, 139);
        sparseIntArray.put(R.id.device_log_go, 140);
        sparseIntArray.put(R.id.iv_device_id_go, 141);
        sparseIntArray.put(R.id.iv_mac_address, 142);
        sparseIntArray.put(R.id.iv_product_name, 143);
    }

    public ActLightSettingNewBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 144, sIncludes, sViewsWithIds));
    }

    private ActLightSettingNewBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 26, (AppCompatImageView) bindings[140], (AppCompatImageView) bindings[139], (LinearLayout) bindings[137], (TextView) bindings[86], (AppCompatImageView) bindings[116], (AppCompatImageView) bindings[133], (AppCompatImageView) bindings[57], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[129], (AppCompatImageView) bindings[141], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[127], (AppCompatImageView) bindings[121], (AppCompatImageView) bindings[131], (AppCompatImageView) bindings[142], (VoisePlayingIcon) bindings[70], (AppCompatImageView) bindings[36], (AppCompatImageView) bindings[32], (AppCompatImageView) bindings[28], (AppCompatImageView) bindings[24], (AppCompatImageView) bindings[143], (AppCompatImageView) bindings[17], (AppCompatImageView) bindings[62], (AppCompatImageView) bindings[67], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[77], (AppCompatImageView) bindings[82], (AppCompatImageView) bindings[91], (AppCompatImageView) bindings[20], (AppCompatTextView) bindings[61], (RelativeLayout) bindings[58], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[5], (RelativeLayout) bindings[45], (ConstraintLayout) bindings[54], (RelativeLayout) bindings[10], (ConstraintLayout) bindings[12], (ConstraintLayout) bindings[100], (RelativeLayout) bindings[96], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[94], (ConstraintLayout) bindings[43], (LinearLayout) bindings[39], (RelativeLayout) bindings[52], (RelativeLayout) bindings[85], (LinearLayout) bindings[60], (RelativeLayout) bindings[48], (RelativeLayout) bindings[103], (RelativeLayout) bindings[109], (RelativeLayout) bindings[87], (RelativeLayout) bindings[78], (ConstraintLayout) bindings[29], (ConstraintLayout) bindings[33], (ConstraintLayout) bindings[21], (ConstraintLayout) bindings[25], (RelativeLayout) bindings[106], (ConstraintLayout) bindings[15], (RelativeLayout) bindings[92], (RelativeLayout) bindings[98], (RelativeLayout) bindings[63], (RelativeLayout) bindings[136], (LinearLayout) bindings[135], (RelativeLayout) bindings[37], (ConstraintLayout) bindings[18], (RelativeLayout) bindings[83], (RelativeLayout) bindings[72], (RelativeLayout) bindings[112], (RelativeLayout) bindings[50], (AppCompatTextView) bindings[88], (AppCompatTextView) bindings[79], (AppCompatImageView) bindings[71], (SmartRefreshLayout) bindings[119], (AppCompatTextView) bindings[93], (AppCompatTextView) bindings[138], (AppCompatTextView) bindings[64], (LinearLayout) bindings[68], (AppCompatTextView) bindings[69], (RecyclerView) bindings[125], (SwitchButton) bindings[47], (SwitchButton) bindings[66], (TextView) bindings[84], (AppCompatTextView) bindings[73], (LayoutTitleDefaultBinding) bindings[118], (AppCompatTextView) bindings[46], (AppCompatTextView) bindings[55], (AppCompatTextView) bindings[56], (AppCompatTextView) bindings[122], (AppCompatTextView) bindings[132], (AppCompatTextView) bindings[117], (AppCompatTextView) bindings[102], (AppCompatTextView) bindings[101], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[128], (AppCompatTextView) bindings[41], (AppCompatTextView) bindings[126], (AppCompatTextView) bindings[134], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[130], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[90], (AppCompatTextView) bindings[81], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[34], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[22], (AppCompatTextView) bindings[26], (AppCompatTextView) bindings[23], (AppCompatTextView) bindings[27], (AppCompatTextView) bindings[123], (AppCompatTextView) bindings[120], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[124], (AppCompatTextView) bindings[113]);
        this.mDirtyFlags = -1L;
        this.mDirtyFlags_1 = -1L;
        this.endTimeTv.setTag(null);
        this.iv.setTag(null);
        this.ivControlTypeGo.setTag(null);
        this.ivCurrentGo.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivPlayAnim.setTag(null);
        this.ivPowerOffSceneDelayGo.setTag(null);
        this.ivPowerOffSceneGo.setTag(null);
        this.ivPowerOnSceneDelayGo.setTag(null);
        this.ivPowerOnSceneGo.setTag(null);
        this.ivPwmGo.setTag(null);
        this.ivRangeGo.setTag(null);
        this.ivRhythmsGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivSelected1.setTag(null);
        this.ivSelected2.setTag(null);
        this.ivSelected3.setTag(null);
        this.ivStateGo.setTag(null);
        this.kRangeLabel.setTag(null);
        this.layoutBatchSet.setTag(null);
        this.layoutChangeIcon.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutConstantPower.setTag(null);
        this.layoutControlType.setTag(null);
        this.layoutCreateGroup.setTag(null);
        this.layoutCurrent.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceLog.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutDimDepth.setTag(null);
        this.layoutDimRange.setTag(null);
        this.layoutDuv.setTag(null);
        this.layoutEndTime.setTag(null);
        this.layoutKRange.setTag(null);
        this.layoutLineSet.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutOutput.setTag(null);
        this.layoutPlan.setTag(null);
        this.layoutPlanTime.setTag(null);
        this.layoutPowerOffScene.setTag(null);
        this.layoutPowerOffSceneDelay.setTag(null);
        this.layoutPowerOnScene.setTag(null);
        this.layoutPowerOnSceneDelay.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutPwmFrequency.setTag(null);
        this.layoutRepeatDate.setTag(null);
        this.layoutRestoreFactory.setTag(null);
        this.layoutRhythms.setTag(null);
        this.layoutSetDmxType.setTag(null);
        this.layoutSetOnState.setTag(null);
        this.layoutStartTime.setTag(null);
        this.layoutSunset.setTag(null);
        this.layoutUpgrade.setTag(null);
        this.layoutWhiteBalance.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[104];
        this.mboundView104 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[105];
        this.mboundView105 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[107];
        this.mboundView107 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[108];
        this.mboundView108 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[11];
        this.mboundView11 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[110];
        this.mboundView110 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[111];
        this.mboundView111 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[114];
        this.mboundView114 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[115];
        this.mboundView115 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[16];
        this.mboundView16 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[38];
        this.mboundView38 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[40];
        this.mboundView40 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[42];
        this.mboundView42 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[44];
        this.mboundView44 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        AppCompatTextView appCompatTextView14 = (AppCompatTextView) bindings[49];
        this.mboundView49 = appCompatTextView14;
        appCompatTextView14.setTag(null);
        AppCompatTextView appCompatTextView15 = (AppCompatTextView) bindings[51];
        this.mboundView51 = appCompatTextView15;
        appCompatTextView15.setTag(null);
        AppCompatTextView appCompatTextView16 = (AppCompatTextView) bindings[53];
        this.mboundView53 = appCompatTextView16;
        appCompatTextView16.setTag(null);
        AppCompatTextView appCompatTextView17 = (AppCompatTextView) bindings[59];
        this.mboundView59 = appCompatTextView17;
        appCompatTextView17.setTag(null);
        AppCompatTextView appCompatTextView18 = (AppCompatTextView) bindings[65];
        this.mboundView65 = appCompatTextView18;
        appCompatTextView18.setTag(null);
        AppCompatTextView appCompatTextView19 = (AppCompatTextView) bindings[74];
        this.mboundView74 = appCompatTextView19;
        appCompatTextView19.setTag(null);
        TextView textView = (TextView) bindings[75];
        this.mboundView75 = textView;
        textView.setTag(null);
        AppCompatTextView appCompatTextView20 = (AppCompatTextView) bindings[76];
        this.mboundView76 = appCompatTextView20;
        appCompatTextView20.setTag(null);
        AppCompatTextView appCompatTextView21 = (AppCompatTextView) bindings[80];
        this.mboundView80 = appCompatTextView21;
        appCompatTextView21.setTag(null);
        AppCompatTextView appCompatTextView22 = (AppCompatTextView) bindings[89];
        this.mboundView89 = appCompatTextView22;
        appCompatTextView22.setTag(null);
        AppCompatTextView appCompatTextView23 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView23;
        appCompatTextView23.setTag(null);
        AppCompatTextView appCompatTextView24 = (AppCompatTextView) bindings[95];
        this.mboundView95 = appCompatTextView24;
        appCompatTextView24.setTag(null);
        AppCompatTextView appCompatTextView25 = (AppCompatTextView) bindings[97];
        this.mboundView97 = appCompatTextView25;
        appCompatTextView25.setTag(null);
        AppCompatTextView appCompatTextView26 = (AppCompatTextView) bindings[99];
        this.mboundView99 = appCompatTextView26;
        appCompatTextView26.setTag(null);
        this.planLabel.setTag(null);
        this.planTimeLabel.setTag(null);
        this.playIv.setTag(null);
        this.repeatDateLabel.setTag(null);
        this.rhythmsLabelTv.setTag(null);
        this.rhythmsSettingLayout.setTag(null);
        this.rhythmsStateLabel.setTag(null);
        this.sbConstantPower.setTag(null);
        this.sbRhythmsText.setTag(null);
        this.startTimeTv.setTag(null);
        this.sunsetLabel.setTag(null);
        setContainedBinding(this.title);
        this.tvConstantPower.setTag(null);
        this.tvControlType.setTag(null);
        this.tvControlTypeState.setTag(null);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvDimRange.setTag(null);
        this.tvLightOnState.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvPlan.setTag(null);
        this.tvPlanTime.setTag(null);
        this.tvPowerOffScene.setTag(null);
        this.tvPowerOffSceneDelay.setTag(null);
        this.tvPowerOffSceneName.setTag(null);
        this.tvPowerOffSceneNameDelay.setTag(null);
        this.tvPowerOnScene.setTag(null);
        this.tvPowerOnSceneDelay.setTag(null);
        this.tvPowerOnSceneName.setTag(null);
        this.tvPowerOnSceneNameDelay.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 268435456L;
            this.mDirtyFlags_1 = 0L;
        }
        this.title.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags == 0 && this.mDirtyFlags_1 == 0) {
                return this.title.hasPendingBindings();
            }
            return true;
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
        setViewmodel((ActLightSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActLightSettingNewBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 67108864;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActLightSettingNewBinding
    public void setViewmodel(ActLightSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 134217728;
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
        switch (localFieldId) {
            case 0:
                return onChangeViewmodelShowRhythmsSetting((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelRhythmsIsPlay((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelPlanText((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelSunSetText((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelOutputType((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelControlType((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelStarTimeText((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewmodelShowPowerOnOffScene((MutableLiveData) object, fieldId);
            case 11:
                return onChangeViewmodelShowRhythms((MutableLiveData) object, fieldId);
            case 12:
                return onChangeViewmodelHasOutputType((MutableLiveData) object, fieldId);
            case 13:
                return onChangeViewmodelShowPowerOnSceneDelayStr((MutableLiveData) object, fieldId);
            case 14:
                return onChangeViewmodelShowLog((MutableLiveData) object, fieldId);
            case 15:
                return onChangeViewmodelShowPowerOnScene((MutableLiveData) object, fieldId);
            case 16:
                return onChangeViewmodelDimmingRange((MutableLiveData) object, fieldId);
            case 17:
                return onChangeViewmodelPlanTimeText((MutableLiveData) object, fieldId);
            case 18:
                return onChangeViewmodelShowPowerOffSceneDelayStr((MutableLiveData) object, fieldId);
            case 19:
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 20:
                return onChangeViewmodelEndTimeText((MutableLiveData) object, fieldId);
            case 21:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 22:
                return onChangeViewmodelConstantPowerCheck((MutableLiveData) object, fieldId);
            case 23:
                return onChangeViewmodelShowPowerOffScene((MutableLiveData) object, fieldId);
            case 24:
                return onChangeViewmodelSunRiseText((MutableLiveData) object, fieldId);
            case 25:
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

    private boolean onChangeViewmodelRhythmsIsPlay(MutableLiveData<Boolean> ViewmodelRhythmsIsPlay, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelPlanText(MutableLiveData<String> ViewmodelPlanText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelSunSetText(MutableLiveData<String> ViewmodelSunSetText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelOutputType(MutableLiveData<String> ViewmodelOutputType, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelControlType(MutableLiveData<Integer> ViewmodelControlType, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeViewmodelStarTimeText(MutableLiveData<String> ViewmodelStarTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOnOffScene(MutableLiveData<Boolean> ViewmodelShowPowerOnOffScene, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythms(MutableLiveData<Boolean> ViewmodelShowRhythms, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        return true;
    }

    private boolean onChangeViewmodelHasOutputType(MutableLiveData<Boolean> ViewmodelHasOutputType, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOnSceneDelayStr(MutableLiveData<String> ViewmodelShowPowerOnSceneDelayStr, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8192;
        }
        return true;
    }

    private boolean onChangeViewmodelShowLog(MutableLiveData<Boolean> ViewmodelShowLog, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16384;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOnScene(MutableLiveData<String> ViewmodelShowPowerOnScene, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
        }
        return true;
    }

    private boolean onChangeViewmodelDimmingRange(MutableLiveData<String> ViewmodelDimmingRange, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 65536;
        }
        return true;
    }

    private boolean onChangeViewmodelPlanTimeText(MutableLiveData<String> ViewmodelPlanTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOffSceneDelayStr(MutableLiveData<String> ViewmodelShowPowerOffSceneDelayStr, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
        }
        return true;
    }

    private boolean onChangeViewmodelEndTimeText(MutableLiveData<String> ViewmodelEndTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1048576;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE;
        }
        return true;
    }

    private boolean onChangeViewmodelConstantPowerCheck(MutableLiveData<Boolean> ViewmodelConstantPowerCheck, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4194304;
        }
        return true;
    }

    private boolean onChangeViewmodelShowPowerOffScene(MutableLiveData<String> ViewmodelShowPowerOffScene, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8388608;
        }
        return true;
    }

    private boolean onChangeViewmodelSunRiseText(MutableLiveData<String> ViewmodelSunRiseText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16777216;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythmsModel(MutableLiveData<Integer> ViewmodelShowRhythmsModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 33554432;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0371  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0410  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0432  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x049e  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x04bc  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x04de  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0505  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0548  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x074d  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x077f  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x07a3  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x07c7  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x07e9  */
    /* JADX WARN: Removed duplicated region for block: B:422:0x0813  */
    /* JADX WARN: Removed duplicated region for block: B:425:0x0823  */
    /* JADX WARN: Removed duplicated region for block: B:428:0x0838  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x09a2  */
    /* JADX WARN: Removed duplicated region for block: B:434:0x09b2  */
    /* JADX WARN: Removed duplicated region for block: B:437:0x09cc  */
    /* JADX WARN: Removed duplicated region for block: B:440:0x0aa3  */
    /* JADX WARN: Removed duplicated region for block: B:443:0x0ab3  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x0ac0  */
    /* JADX WARN: Removed duplicated region for block: B:449:0x0ad2  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x0adf  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x0aef  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x0aff  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0b0f  */
    /* JADX WARN: Removed duplicated region for block: B:464:0x0b2d  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0b3a  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x0b4a  */
    /* JADX WARN: Removed duplicated region for block: B:473:0x0b5a  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x0b67  */
    /* JADX WARN: Removed duplicated region for block: B:479:0x0b74  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x0b90  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x0ba0  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x0bb0  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x0bbd  */
    /* JADX WARN: Removed duplicated region for block: B:494:0x0bcd  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x0bda  */
    /* JADX WARN: Removed duplicated region for block: B:500:0x0bea  */
    /* JADX WARN: Removed duplicated region for block: B:503:0x0bfa  */
    /* JADX WARN: Removed duplicated region for block: B:506:0x0c0a  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x0c1a  */
    /* JADX WARN: Removed duplicated region for block: B:512:0x0c2a  */
    /* JADX WARN: Removed duplicated region for block: B:516:0x081b  */
    /* JADX WARN: Removed duplicated region for block: B:518:0x0808  */
    /* JADX WARN: Removed duplicated region for block: B:520:0x07e1  */
    /* JADX WARN: Removed duplicated region for block: B:523:0x07bf  */
    /* JADX WARN: Removed duplicated region for block: B:525:0x079a  */
    /* JADX WARN: Removed duplicated region for block: B:529:0x0766  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x0640  */
    /* JADX WARN: Removed duplicated region for block: B:549:0x0537  */
    /* JADX WARN: Removed duplicated region for block: B:552:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:555:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:561:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:564:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:567:0x0426  */
    /* JADX WARN: Removed duplicated region for block: B:572:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:581:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:589:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:592:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:595:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:598:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:602:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:607:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0177  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 3130
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActLightSettingNewBindingImpl.executeBindings():void");
    }
}