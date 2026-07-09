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
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.dalipro.ActCgdProLightSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.ltech.smarthome.view.VoisePlayingIcon;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActCgdProLightSettingBindingImpl extends ActCgdProLightSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView24;
    private final TextView mboundView25;
    private final AppCompatTextView mboundView26;
    private final AppCompatTextView mboundView30;
    private final AppCompatTextView mboundView39;
    private final AppCompatTextView mboundView48;
    private final AppCompatTextView mboundView49;
    private final AppCompatTextView mboundView51;
    private final AppCompatTextView mboundView52;
    private final AppCompatTextView mboundView55;
    private final AppCompatImageView mboundView56;
    private final AppCompatTextView mboundView9;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(75);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{59}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 60);
        sparseIntArray.put(R.id.tv_room_name, 61);
        sparseIntArray.put(R.id.tv_dbatch_set, 62);
        sparseIntArray.put(R.id.iv_batch_go, 63);
        sparseIntArray.put(R.id.iv_batch_hide_go, 64);
        sparseIntArray.put(R.id.tv_search_address, 65);
        sparseIntArray.put(R.id.iv_search_address_go, 66);
        sparseIntArray.put(R.id.layout_rhythms_switch, 67);
        sparseIntArray.put(R.id.rhythms_setting_layout, 68);
        sparseIntArray.put(R.id.layout_rhythms_state, 69);
        sparseIntArray.put(R.id.edit_plan_time_layout, 70);
        sparseIntArray.put(R.id.repeat_week_tv, 71);
        sparseIntArray.put(R.id.iv_device_id_go, 72);
        sparseIntArray.put(R.id.iv_mac_address, 73);
        sparseIntArray.put(R.id.iv_product_name, 74);
    }

    public ActCgdProLightSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 75, sIncludes, sViewsWithIds));
    }

    private ActCgdProLightSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 14, (LinearLayout) bindings[70], (TextView) bindings[36], (AppCompatImageView) bindings[57], (AppCompatImageView) bindings[63], (AppCompatImageView) bindings[64], (AppCompatImageView) bindings[72], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[73], (VoisePlayingIcon) bindings[20], (AppCompatImageView) bindings[74], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[66], (AppCompatImageView) bindings[27], (AppCompatImageView) bindings[32], (AppCompatImageView) bindings[41], (RelativeLayout) bindings[10], (RelativeLayout) bindings[8], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[44], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[35], (RelativeLayout) bindings[47], (RelativeLayout) bindings[37], (RelativeLayout) bindings[28], (RelativeLayout) bindings[50], (RelativeLayout) bindings[42], (RelativeLayout) bindings[14], (RelativeLayout) bindings[69], (LinearLayout) bindings[67], (RelativeLayout) bindings[12], (RelativeLayout) bindings[33], (RelativeLayout) bindings[22], (RelativeLayout) bindings[53], (AppCompatTextView) bindings[38], (AppCompatTextView) bindings[29], (AppCompatImageView) bindings[21], (SmartRefreshLayout) bindings[60], (AppCompatTextView) bindings[43], (AppCompatTextView) bindings[71], (AppCompatTextView) bindings[15], (LinearLayout) bindings[68], (AppCompatTextView) bindings[19], (SwitchButton) bindings[17], (TextView) bindings[34], (AppCompatTextView) bindings[23], (LayoutTitleDefaultBinding) bindings[59], (AppCompatTextView) bindings[62], (AppCompatTextView) bindings[58], (AppCompatTextView) bindings[46], (AppCompatTextView) bindings[45], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[40], (AppCompatTextView) bindings[31], (AppCompatTextView) bindings[61], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[65], (AppCompatTextView) bindings[54]);
        this.mDirtyFlags = -1L;
        this.endTimeTv.setTag(null);
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivPlayAnim.setTag(null);
        this.ivRhythmsGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivSelected1.setTag(null);
        this.ivSelected2.setTag(null);
        this.ivSelected3.setTag(null);
        this.layoutBatchHideManage.setTag(null);
        this.layoutBatchSet.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutEndTime.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutPlan.setTag(null);
        this.layoutPlanTime.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutRepeatDate.setTag(null);
        this.layoutRhythms.setTag(null);
        this.layoutSearchAddress.setTag(null);
        this.layoutStartTime.setTag(null);
        this.layoutSunset.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[11];
        this.mboundView11 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[16];
        this.mboundView16 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        TextView textView = (TextView) bindings[25];
        this.mboundView25 = textView;
        textView.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[30];
        this.mboundView30 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[39];
        this.mboundView39 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[48];
        this.mboundView48 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[49];
        this.mboundView49 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[51];
        this.mboundView51 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[52];
        this.mboundView52 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[55];
        this.mboundView55 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[56];
        this.mboundView56 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[9];
        this.mboundView9 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        this.planLabel.setTag(null);
        this.planTimeLabel.setTag(null);
        this.playIv.setTag(null);
        this.repeatDateLabel.setTag(null);
        this.rhythmsLabelTv.setTag(null);
        this.rhythmsStateLabel.setTag(null);
        this.sbRhythmsText.setTag(null);
        this.startTimeTv.setTag(null);
        this.sunsetLabel.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvPlan.setTag(null);
        this.tvPlanTime.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 65536L;
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
        setViewmodel((ActCgdProLightSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActCgdProLightSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 16384;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCgdProLightSettingBinding
    public void setViewmodel(ActCgdProLightSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
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
                return onChangeViewmodelPlanTimeText((MutableLiveData) object, fieldId);
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
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelEndTimeText((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 11:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 12:
                return onChangeViewmodelSunRiseText((MutableLiveData) object, fieldId);
            case 13:
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

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelEndTimeText(MutableLiveData<String> ViewmodelEndTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        return true;
    }

    private boolean onChangeViewmodelSunRiseText(MutableLiveData<String> ViewmodelSunRiseText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        return true;
    }

    private boolean onChangeViewmodelShowRhythmsModel(MutableLiveData<Integer> ViewmodelShowRhythmsModel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8192;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02d2  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x048a  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x04b2  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x04ed  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x04f8  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x05b4  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x05c4  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x05de  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x05ee  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0661  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x066e  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x067b  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0699  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x06a9  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x06b6  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x06cd  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x06dd  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x06ea  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x06f7  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x04df  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:327:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01c4  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 1799
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActCgdProLightSettingBindingImpl.executeBindings():void");
    }
}