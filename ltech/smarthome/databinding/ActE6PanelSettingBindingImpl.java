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
import com.ltech.smarthome.ui.device.e6knob.ActE6PanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.VoisePlayingIcon;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActE6PanelSettingBindingImpl extends ActE6PanelSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView12;
    private final AppCompatImageView mboundView13;
    private final AppCompatTextView mboundView15;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView19;
    private final AppCompatTextView mboundView21;
    private final AppCompatTextView mboundView24;
    private final AppCompatTextView mboundView29;
    private final AppCompatTextView mboundView35;
    private final AppCompatTextView mboundView39;
    private final AppCompatTextView mboundView42;
    private final AppCompatTextView mboundView46;
    private final AppCompatTextView mboundView55;
    private final TextView mboundView56;
    private final AppCompatTextView mboundView57;
    private final AppCompatTextView mboundView61;
    private final AppCompatTextView mboundView70;
    private final AppCompatTextView mboundView76;
    private final AppCompatTextView mboundView78;
    private final AppCompatTextView mboundView80;
    private final AppCompatTextView mboundView87;
    private final AppCompatTextView mboundView88;
    private final AppCompatTextView mboundView9;
    private final AppCompatTextView mboundView90;
    private final AppCompatTextView mboundView91;
    private final AppCompatTextView mboundView94;
    private final AppCompatImageView mboundView95;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(124);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{98}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 99);
        sparseIntArray.put(R.id.tv_room_name, 100);
        sparseIntArray.put(R.id.layout_knob_setting, 101);
        sparseIntArray.put(R.id.tv_sensitivity, 102);
        sparseIntArray.put(R.id.layout_buzzer, 103);
        sparseIntArray.put(R.id.tv_k_range, 104);
        sparseIntArray.put(R.id.tv_state, 105);
        sparseIntArray.put(R.id.rv_light_setting, 106);
        sparseIntArray.put(R.id.tv_dim_signal, 107);
        sparseIntArray.put(R.id.layout_control_type, 108);
        sparseIntArray.put(R.id.tv_control_type_tip, 109);
        sparseIntArray.put(R.id.tv_control_type_state, 110);
        sparseIntArray.put(R.id.iv_control_type_go, 111);
        sparseIntArray.put(R.id.tv_rgb_interface, 112);
        sparseIntArray.put(R.id.tv_first_add, 113);
        sparseIntArray.put(R.id.layout_rhythms_switch, 114);
        sparseIntArray.put(R.id.layout_rhythms_state, 115);
        sparseIntArray.put(R.id.edit_plan_time_layout, 116);
        sparseIntArray.put(R.id.repeat_week_tv, 117);
        sparseIntArray.put(R.id.device_replace_go, 118);
        sparseIntArray.put(R.id.tv_related_num, 119);
        sparseIntArray.put(R.id.iv_scene_go, 120);
        sparseIntArray.put(R.id.iv_device_id_go, 121);
        sparseIntArray.put(R.id.iv_mac_address, 122);
        sparseIntArray.put(R.id.iv_product_name, 123);
    }

    public ActE6PanelSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 124, sIncludes, sViewsWithIds));
    }

    private ActE6PanelSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 19, (AppCompatImageView) bindings[118], (LinearLayout) bindings[116], (TextView) bindings[67], (AppCompatImageView) bindings[96], (AppCompatImageView) bindings[111], (AppCompatImageView) bindings[121], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[36], (AppCompatImageView) bindings[43], (AppCompatImageView) bindings[122], (VoisePlayingIcon) bindings[51], (AppCompatImageView) bindings[123], (AppCompatImageView) bindings[27], (AppCompatImageView) bindings[40], (AppCompatImageView) bindings[48], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[120], (AppCompatImageView) bindings[58], (AppCompatImageView) bindings[63], (AppCompatImageView) bindings[72], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[33], (AppCompatTextView) bindings[26], (RelativeLayout) bindings[28], (RelativeLayout) bindings[103], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[108], (RelativeLayout) bindings[23], (ConstraintLayout) bindings[83], (RelativeLayout) bindings[77], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[75], (ConstraintLayout) bindings[34], (ConstraintLayout) bindings[41], (RelativeLayout) bindings[66], (RelativeLayout) bindings[18], (LinearLayout) bindings[25], (LinearLayout) bindings[101], (RelativeLayout) bindings[86], (RelativeLayout) bindings[14], (RelativeLayout) bindings[11], (RelativeLayout) bindings[68], (RelativeLayout) bindings[59], (RelativeLayout) bindings[89], (RelativeLayout) bindings[73], (RelativeLayout) bindings[79], (ConstraintLayout) bindings[38], (RelativeLayout) bindings[44], (RelativeLayout) bindings[115], (LinearLayout) bindings[114], (RelativeLayout) bindings[81], (ConstraintLayout) bindings[8], (ConstraintLayout) bindings[31], (RelativeLayout) bindings[64], (RelativeLayout) bindings[53], (RelativeLayout) bindings[92], (AppCompatTextView) bindings[69], (AppCompatTextView) bindings[60], (AppCompatImageView) bindings[52], (SmartRefreshLayout) bindings[99], (AppCompatTextView) bindings[74], (AppCompatTextView) bindings[117], (AppCompatTextView) bindings[45], (LinearLayout) bindings[49], (AppCompatTextView) bindings[50], (RecyclerView) bindings[106], (SwitchButton) bindings[30], (SwitchButton) bindings[22], (SwitchButton) bindings[20], (SwitchButton) bindings[17], (SwitchButton) bindings[47], (TextView) bindings[65], (AppCompatTextView) bindings[54], (LayoutTitleDefaultBinding) bindings[98], (AppCompatTextView) bindings[37], (AppCompatTextView) bindings[110], (AppCompatTextView) bindings[109], (AppCompatTextView) bindings[97], (AppCompatTextView) bindings[85], (AppCompatTextView) bindings[84], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[107], (AppCompatTextView) bindings[113], (AppCompatTextView) bindings[104], (AppCompatTextView) bindings[32], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[71], (AppCompatTextView) bindings[62], (AppCompatTextView) bindings[119], (AppCompatTextView) bindings[82], (AppCompatTextView) bindings[112], (AppCompatTextView) bindings[100], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[102], (AppCompatTextView) bindings[105], (AppCompatTextView) bindings[93]);
        this.mDirtyFlags = -1L;
        this.endTimeTv.setTag(null);
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivDimSignalGo.setTag(null);
        this.ivFirstAddGo.setTag(null);
        this.ivPlayAnim.setTag(null);
        this.ivRangeGo.setTag(null);
        this.ivRgbInterfaceGo.setTag(null);
        this.ivRhythmsGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivSelected1.setTag(null);
        this.ivSelected2.setTag(null);
        this.ivSelected3.setTag(null);
        this.ivSensitivityGo.setTag(null);
        this.ivStateGo.setTag(null);
        this.kRangeLabel.setTag(null);
        this.layoutBusPower.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutCreateGroup.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceLog.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutDimSignal.setTag(null);
        this.layoutE6mAdd.setTag(null);
        this.layoutEndTime.setTag(null);
        this.layoutIndicator.setTag(null);
        this.layoutKRange.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutModeMemorize.setTag(null);
        this.layoutModeOrder.setTag(null);
        this.layoutPlan.setTag(null);
        this.layoutPlanTime.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutRepeatDate.setTag(null);
        this.layoutRestoreFactory.setTag(null);
        this.layoutRgbInterface.setTag(null);
        this.layoutRhythms.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSensitivity.setTag(null);
        this.layoutSetOnState.setTag(null);
        this.layoutStartTime.setTag(null);
        this.layoutSunset.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[13];
        this.mboundView13 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[15];
        this.mboundView15 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[16];
        this.mboundView16 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[21];
        this.mboundView21 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[29];
        this.mboundView29 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[35];
        this.mboundView35 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[39];
        this.mboundView39 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[42];
        this.mboundView42 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[46];
        this.mboundView46 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[55];
        this.mboundView55 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        TextView textView = (TextView) bindings[56];
        this.mboundView56 = textView;
        textView.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[57];
        this.mboundView57 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        AppCompatTextView appCompatTextView14 = (AppCompatTextView) bindings[61];
        this.mboundView61 = appCompatTextView14;
        appCompatTextView14.setTag(null);
        AppCompatTextView appCompatTextView15 = (AppCompatTextView) bindings[70];
        this.mboundView70 = appCompatTextView15;
        appCompatTextView15.setTag(null);
        AppCompatTextView appCompatTextView16 = (AppCompatTextView) bindings[76];
        this.mboundView76 = appCompatTextView16;
        appCompatTextView16.setTag(null);
        AppCompatTextView appCompatTextView17 = (AppCompatTextView) bindings[78];
        this.mboundView78 = appCompatTextView17;
        appCompatTextView17.setTag(null);
        AppCompatTextView appCompatTextView18 = (AppCompatTextView) bindings[80];
        this.mboundView80 = appCompatTextView18;
        appCompatTextView18.setTag(null);
        AppCompatTextView appCompatTextView19 = (AppCompatTextView) bindings[87];
        this.mboundView87 = appCompatTextView19;
        appCompatTextView19.setTag(null);
        AppCompatTextView appCompatTextView20 = (AppCompatTextView) bindings[88];
        this.mboundView88 = appCompatTextView20;
        appCompatTextView20.setTag(null);
        AppCompatTextView appCompatTextView21 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView21;
        appCompatTextView21.setTag(null);
        AppCompatTextView appCompatTextView22 = (AppCompatTextView) bindings[90];
        this.mboundView90 = appCompatTextView22;
        appCompatTextView22.setTag(null);
        AppCompatTextView appCompatTextView23 = (AppCompatTextView) bindings[91];
        this.mboundView91 = appCompatTextView23;
        appCompatTextView23.setTag(null);
        AppCompatTextView appCompatTextView24 = (AppCompatTextView) bindings[94];
        this.mboundView94 = appCompatTextView24;
        appCompatTextView24.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[95];
        this.mboundView95 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        this.planLabel.setTag(null);
        this.planTimeLabel.setTag(null);
        this.playIv.setTag(null);
        this.repeatDateLabel.setTag(null);
        this.rhythmsLabelTv.setTag(null);
        this.rhythmsSettingLayout.setTag(null);
        this.rhythmsStateLabel.setTag(null);
        this.sbBusPower.setTag(null);
        this.sbBuzzer.setTag(null);
        this.sbIndicator.setTag(null);
        this.sbModeMemorize.setTag(null);
        this.sbRhythmsText.setTag(null);
        this.startTimeTv.setTag(null);
        this.sunsetLabel.setTag(null);
        setContainedBinding(this.title);
        this.tvControlType.setTag(null);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvLightOnState.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvPlan.setTag(null);
        this.tvPlanTime.setTag(null);
        this.tvRelatedTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE;
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
        setViewmodel((ActE6PanelSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActE6PanelSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActE6PanelSettingBinding
    public void setViewmodel(ActE6PanelSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 1048576;
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
                return onChangeViewmodelBusPower((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelShowRhythmsSetting((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelRhythmsIsPlay((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelPlanText((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelSunSetText((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelStarTimeText((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelShowRhythms((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewmodelIndicator((MutableLiveData) object, fieldId);
            case 11:
                return onChangeViewmodelBuzzer((MutableLiveData) object, fieldId);
            case 12:
                return onChangeViewmodelDoubleMemorize((MutableLiveData) object, fieldId);
            case 13:
                return onChangeViewmodelPlanTimeText((MutableLiveData) object, fieldId);
            case 14:
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 15:
                return onChangeViewmodelEndTimeText((MutableLiveData) object, fieldId);
            case 16:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 17:
                return onChangeViewmodelSunRiseText((MutableLiveData) object, fieldId);
            case 18:
                return onChangeViewmodelShowRhythmsModel((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelBusPower(MutableLiveData<Boolean> ViewmodelBusPower, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythmsSetting(MutableLiveData<Boolean> ViewmodelShowRhythmsSetting, int fieldId) {
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

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    private boolean onChangeViewmodelStarTimeText(MutableLiveData<String> ViewmodelStarTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythms(MutableLiveData<Boolean> ViewmodelShowRhythms, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    private boolean onChangeViewmodelIndicator(MutableLiveData<Boolean> ViewmodelIndicator, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        return true;
    }

    private boolean onChangeViewmodelBuzzer(MutableLiveData<Boolean> ViewmodelBuzzer, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        return true;
    }

    private boolean onChangeViewmodelDoubleMemorize(MutableLiveData<Boolean> ViewmodelDoubleMemorize, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        return true;
    }

    private boolean onChangeViewmodelPlanTimeText(MutableLiveData<String> ViewmodelPlanTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8192;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16384;
        }
        return true;
    }

    private boolean onChangeViewmodelEndTimeText(MutableLiveData<String> ViewmodelEndTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 65536;
        }
        return true;
    }

    private boolean onChangeViewmodelSunRiseText(MutableLiveData<String> ViewmodelSunRiseText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythmsModel(MutableLiveData<Integer> ViewmodelShowRhythmsModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0268 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x0498  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x03cb  */
    /* JADX WARN: Removed duplicated region for block: B:397:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:430:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x019c  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 2335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActE6PanelSettingBindingImpl.executeBindings():void");
    }
}