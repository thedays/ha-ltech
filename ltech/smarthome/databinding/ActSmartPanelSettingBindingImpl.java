package com.ltech.smarthome.databinding;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatButton;
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
import com.ltech.smarthome.model.product.ProductId;
import com.ltech.smarthome.ui.device.setting.ActSmartPanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActSmartPanelSettingBindingImpl extends ActSmartPanelSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private long mDirtyFlags_1;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView15;
    private final AppCompatTextView mboundView17;
    private final AppCompatTextView mboundView19;
    private final AppCompatTextView mboundView20;
    private final RelativeLayout mboundView23;
    private final AppCompatTextView mboundView24;
    private final AppCompatTextView mboundView26;
    private final AppCompatTextView mboundView37;
    private final AppCompatTextView mboundView41;
    private final AppCompatTextView mboundView44;
    private final AppCompatTextView mboundView47;
    private final AppCompatImageView mboundView48;
    private final AppCompatTextView mboundView50;
    private final AppCompatTextView mboundView53;
    private final RelativeLayout mboundView55;
    private final AppCompatTextView mboundView56;
    private final RelativeLayout mboundView57;
    private final AppCompatTextView mboundView58;
    private final RelativeLayout mboundView60;
    private final AppCompatTextView mboundView61;
    private final AppCompatTextView mboundView64;
    private final AppCompatTextView mboundView67;
    private final AppCompatTextView mboundView69;
    private final AppCompatTextView mboundView71;
    private final AppCompatTextView mboundView77;
    private final AppCompatTextView mboundView78;
    private final AppCompatTextView mboundView80;
    private final AppCompatTextView mboundView81;
    private final AppCompatTextView mboundView84;
    private final AppCompatImageView mboundView85;
    private final AppCompatTextView mboundView89;
    private final AppCompatImageView mboundView90;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(110);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{93}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 94);
        sparseIntArray.put(R.id.tv_room_name, 95);
        sparseIntArray.put(R.id.layout_more, 96);
        sparseIntArray.put(R.id.tv_battery_tip, 97);
        sparseIntArray.put(R.id.battery_go, 98);
        sparseIntArray.put(R.id.sb_layout, 99);
        sparseIntArray.put(R.id.tv_night_mode_tip, 100);
        sparseIntArray.put(R.id.tv_language_tip, 101);
        sparseIntArray.put(R.id.tv_elderly_mode_tip, 102);
        sparseIntArray.put(R.id.tv_panel_back_tip, 103);
        sparseIntArray.put(R.id.sb_touch_volume, 104);
        sparseIntArray.put(R.id.device_log_go, 105);
        sparseIntArray.put(R.id.tv_related_num, 106);
        sparseIntArray.put(R.id.iv_device_id_go, 107);
        sparseIntArray.put(R.id.iv_mac_address, 108);
        sparseIntArray.put(R.id.iv_product_name, 109);
    }

    public ActSmartPanelSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 110, sIncludes, sViewsWithIds));
    }

    private ActSmartPanelSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 25, (AppCompatImageView) bindings[98], (AppCompatButton) bindings[54], (AppCompatImageView) bindings[105], (AppCompatImageView) bindings[65], (AppCompatImageView) bindings[39], (AppCompatImageView) bindings[45], (AppCompatImageView) bindings[86], (AppCompatImageView) bindings[107], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[35], (AppCompatImageView) bindings[91], (AppCompatImageView) bindings[108], (AppCompatImageView) bindings[109], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[72], (AppCompatImageView) bindings[31], (AppCompatImageView) bindings[42], (RelativeLayout) bindings[16], (RelativeLayout) bindings[46], (RelativeLayout) bindings[40], (ConstraintLayout) bindings[5], (RelativeLayout) bindings[14], (ConstraintLayout) bindings[73], (RelativeLayout) bindings[66], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[63], (RelativeLayout) bindings[43], (ConstraintLayout) bindings[32], (RelativeLayout) bindings[36], (RelativeLayout) bindings[87], (RelativeLayout) bindings[76], (LinearLayout) bindings[96], (RelativeLayout) bindings[49], (RelativeLayout) bindings[79], (RelativeLayout) bindings[68], (RelativeLayout) bindings[70], (RelativeLayout) bindings[18], (RelativeLayout) bindings[52], (ConstraintLayout) bindings[28], (RelativeLayout) bindings[12], (RelativeLayout) bindings[10], (RelativeLayout) bindings[82], (AppCompatImageView) bindings[51], (SmartRefreshLayout) bindings[94], (RecyclerView) bindings[9], (SwitchButton) bindings[27], (SwitchButton) bindings[21], (SwitchButton) bindings[59], (LinearLayout) bindings[99], (SwitchButton) bindings[62], (SwitchButton) bindings[25], (SwitchButton) bindings[104], (LayoutTitleDefaultBinding) bindings[93], (AppCompatTextView) bindings[97], (AppCompatTextView) bindings[92], (AppCompatTextView) bindings[75], (AppCompatTextView) bindings[74], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[102], (AppCompatTextView) bindings[34], (AppCompatTextView) bindings[33], (AppCompatTextView) bindings[38], (AppCompatTextView) bindings[88], (AppCompatTextView) bindings[101], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[100], (AppCompatTextView) bindings[103], (AppCompatTextView) bindings[106], (AppCompatTextView) bindings[95], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[30], (AppCompatTextView) bindings[29], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[83]);
        this.mDirtyFlags = -1L;
        this.mDirtyFlags_1 = -1L;
        this.btnAdjust.setTag(null);
        this.deviceReplaceGo.setTag(null);
        this.elderlyGetUpNightModeGo.setTag(null);
        this.elderlyModeGo.setTag(null);
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivDistanceGo.setTag(null);
        this.ivEndTimeGo.setTag(null);
        this.ivIconGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivSceneGo.setTag(null);
        this.ivStartTimeGo.setTag(null);
        this.languageGo.setTag(null);
        this.layoutBattery.setTag(null);
        this.layoutBrtButton.setTag(null);
        this.layoutChangeLanguage.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutCreateGroup.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceLog.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutDeviceReplace.setTag(null);
        this.layoutElderlyMode.setTag(null);
        this.layoutEndTime.setTag(null);
        this.layoutGetUpNightMode.setTag(null);
        this.layoutIconUpgrade.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutPanelBack.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutRestoreFactory.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSensitivity.setTag(null);
        this.layoutSetKRange.setTag(null);
        this.layoutStartTime.setTag(null);
        this.layoutSwitchPositionSetting.setTag(null);
        this.layoutSwitchSetting.setTag(null);
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
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[15];
        this.mboundView15 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[17];
        this.mboundView17 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[19];
        this.mboundView19 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[23];
        this.mboundView23 = relativeLayout;
        relativeLayout.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[24];
        this.mboundView24 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[37];
        this.mboundView37 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[41];
        this.mboundView41 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[44];
        this.mboundView44 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[47];
        this.mboundView47 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[48];
        this.mboundView48 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[50];
        this.mboundView50 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        AppCompatTextView appCompatTextView14 = (AppCompatTextView) bindings[53];
        this.mboundView53 = appCompatTextView14;
        appCompatTextView14.setTag(null);
        RelativeLayout relativeLayout2 = (RelativeLayout) bindings[55];
        this.mboundView55 = relativeLayout2;
        relativeLayout2.setTag(null);
        AppCompatTextView appCompatTextView15 = (AppCompatTextView) bindings[56];
        this.mboundView56 = appCompatTextView15;
        appCompatTextView15.setTag(null);
        RelativeLayout relativeLayout3 = (RelativeLayout) bindings[57];
        this.mboundView57 = relativeLayout3;
        relativeLayout3.setTag(null);
        AppCompatTextView appCompatTextView16 = (AppCompatTextView) bindings[58];
        this.mboundView58 = appCompatTextView16;
        appCompatTextView16.setTag(null);
        RelativeLayout relativeLayout4 = (RelativeLayout) bindings[60];
        this.mboundView60 = relativeLayout4;
        relativeLayout4.setTag(null);
        AppCompatTextView appCompatTextView17 = (AppCompatTextView) bindings[61];
        this.mboundView61 = appCompatTextView17;
        appCompatTextView17.setTag(null);
        AppCompatTextView appCompatTextView18 = (AppCompatTextView) bindings[64];
        this.mboundView64 = appCompatTextView18;
        appCompatTextView18.setTag(null);
        AppCompatTextView appCompatTextView19 = (AppCompatTextView) bindings[67];
        this.mboundView67 = appCompatTextView19;
        appCompatTextView19.setTag(null);
        AppCompatTextView appCompatTextView20 = (AppCompatTextView) bindings[69];
        this.mboundView69 = appCompatTextView20;
        appCompatTextView20.setTag(null);
        AppCompatTextView appCompatTextView21 = (AppCompatTextView) bindings[71];
        this.mboundView71 = appCompatTextView21;
        appCompatTextView21.setTag(null);
        AppCompatTextView appCompatTextView22 = (AppCompatTextView) bindings[77];
        this.mboundView77 = appCompatTextView22;
        appCompatTextView22.setTag(null);
        AppCompatTextView appCompatTextView23 = (AppCompatTextView) bindings[78];
        this.mboundView78 = appCompatTextView23;
        appCompatTextView23.setTag(null);
        AppCompatTextView appCompatTextView24 = (AppCompatTextView) bindings[80];
        this.mboundView80 = appCompatTextView24;
        appCompatTextView24.setTag(null);
        AppCompatTextView appCompatTextView25 = (AppCompatTextView) bindings[81];
        this.mboundView81 = appCompatTextView25;
        appCompatTextView25.setTag(null);
        AppCompatTextView appCompatTextView26 = (AppCompatTextView) bindings[84];
        this.mboundView84 = appCompatTextView26;
        appCompatTextView26.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[85];
        this.mboundView85 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        AppCompatTextView appCompatTextView27 = (AppCompatTextView) bindings[89];
        this.mboundView89 = appCompatTextView27;
        appCompatTextView27.setTag(null);
        AppCompatImageView appCompatImageView3 = (AppCompatImageView) bindings[90];
        this.mboundView90 = appCompatImageView3;
        appCompatImageView3.setTag(null);
        this.panelBackGo.setTag(null);
        this.rvKeys.setTag(null);
        this.sbAutoTurnOff.setTag(null);
        this.sbDistance.setTag(null);
        this.sbEngravedText.setTag(null);
        this.sbMemorizePowerOnTip.setTag(null);
        this.sbNightMode.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvEndTime.setTag(null);
        this.tvEndTimeTip.setTag(null);
        this.tvGetUpNightModeTip.setTag(null);
        this.tvIconTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvStartTime.setTag(null);
        this.tvStartTimeTip.setTag(null);
        this.tvTip.setTag(null);
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
        if (61 == variableId) {
            setProductId((ProductId) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActSmartPanelSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 33554432;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelSettingBinding
    public void setProductId(ProductId ProductId) {
        this.mProductId = ProductId;
    }

    @Override // com.ltech.smarthome.databinding.ActSmartPanelSettingBinding
    public void setViewmodel(ActSmartPanelSettingVM Viewmodel) {
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
                return onChangeViewmodelIsShowIconVersion((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelNightMode((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelMemorizePowerOff((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelNewIconVersion((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelIsOld((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelStarTimeText((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelRvKeysShow((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelSwitchSettingShow((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 11:
                return onChangeViewmodelEngravedText((MutableLiveData) object, fieldId);
            case 12:
                return onChangeViewmodelDistanceText((MutableLiveData) object, fieldId);
            case 13:
                return onChangeViewmodelShowLog((MutableLiveData) object, fieldId);
            case 14:
                return onChangeViewmodelIsShowBindKRange((MutableLiveData) object, fieldId);
            case 15:
                return onChangeViewmodelSwitchOn((MutableLiveData) object, fieldId);
            case 16:
                return onChangeViewmodelDistanceSetting((MutableLiveData) object, fieldId);
            case 17:
                return onChangeViewmodelIconVersion((MutableLiveData) object, fieldId);
            case 18:
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 19:
                return onChangeViewmodelEndTimeText((MutableLiveData) object, fieldId);
            case 20:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 21:
                return onChangeViewmodelAutoTurnOff((MutableLiveData) object, fieldId);
            case 22:
                return onChangeViewmodelIsProPanel((MutableLiveData) object, fieldId);
            case 23:
                return onChangeViewmodelSwitchPositionSetting((MutableLiveData) object, fieldId);
            case 24:
                return onChangeViewmodelDistanceOn((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelIsShowIconVersion(MutableLiveData<Boolean> ViewmodelIsShowIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelNightMode(MutableLiveData<Boolean> ViewmodelNightMode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelMemorizePowerOff(MutableLiveData<Boolean> ViewmodelMemorizePowerOff, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelNewIconVersion(MutableLiveData<Boolean> ViewmodelNewIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelControlDevice(MutableLiveData<Device> ViewmodelControlDevice, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelIsOld(MutableLiveData<Boolean> ViewmodelIsOld, int fieldId) {
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

    private boolean onChangeViewmodelRvKeysShow(MutableLiveData<Boolean> ViewmodelRvKeysShow, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelSwitchSettingShow(MutableLiveData<Boolean> ViewmodelSwitchSettingShow, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        return true;
    }

    private boolean onChangeViewmodelEngravedText(MutableLiveData<Boolean> ViewmodelEngravedText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        return true;
    }

    private boolean onChangeViewmodelDistanceText(MutableLiveData<String> ViewmodelDistanceText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        return true;
    }

    private boolean onChangeViewmodelShowLog(MutableLiveData<Boolean> ViewmodelShowLog, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8192;
        }
        return true;
    }

    private boolean onChangeViewmodelIsShowBindKRange(MutableLiveData<Boolean> ViewmodelIsShowBindKRange, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16384;
        }
        return true;
    }

    private boolean onChangeViewmodelSwitchOn(MutableLiveData<Boolean> ViewmodelSwitchOn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
        }
        return true;
    }

    private boolean onChangeViewmodelDistanceSetting(MutableLiveData<Boolean> ViewmodelDistanceSetting, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 65536;
        }
        return true;
    }

    private boolean onChangeViewmodelIconVersion(MutableLiveData<String> ViewmodelIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        return true;
    }

    private boolean onChangeViewmodelEndTimeText(MutableLiveData<String> ViewmodelEndTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1048576;
        }
        return true;
    }

    private boolean onChangeViewmodelAutoTurnOff(MutableLiveData<Boolean> ViewmodelAutoTurnOff, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE;
        }
        return true;
    }

    private boolean onChangeViewmodelIsProPanel(MutableLiveData<Boolean> ViewmodelIsProPanel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4194304;
        }
        return true;
    }

    private boolean onChangeViewmodelSwitchPositionSetting(MutableLiveData<Boolean> ViewmodelSwitchPositionSetting, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8388608;
        }
        return true;
    }

    private boolean onChangeViewmodelDistanceOn(MutableLiveData<Boolean> ViewmodelDistanceOn, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16777216;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:133:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0324  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x042d  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x046d  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x04fe  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0538  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x05be  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x05de  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0602  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x062b  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x067d  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x06b9  */
    /* JADX WARN: Removed duplicated region for block: B:454:0x0904  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0928  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x094f  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0970  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x098b  */
    /* JADX WARN: Removed duplicated region for block: B:515:0x09de  */
    /* JADX WARN: Removed duplicated region for block: B:520:0x09f3  */
    /* JADX WARN: Removed duplicated region for block: B:532:0x0a1d  */
    /* JADX WARN: Removed duplicated region for block: B:535:0x0b24  */
    /* JADX WARN: Removed duplicated region for block: B:538:0x0b39  */
    /* JADX WARN: Removed duplicated region for block: B:541:0x0b53  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x0b68  */
    /* JADX WARN: Removed duplicated region for block: B:547:0x0b75  */
    /* JADX WARN: Removed duplicated region for block: B:550:0x0bc4  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x0bd1  */
    /* JADX WARN: Removed duplicated region for block: B:556:0x0bde  */
    /* JADX WARN: Removed duplicated region for block: B:559:0x0bf7  */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0c07  */
    /* JADX WARN: Removed duplicated region for block: B:565:0x0c1e  */
    /* JADX WARN: Removed duplicated region for block: B:568:0x0c2e  */
    /* JADX WARN: Removed duplicated region for block: B:571:0x0c3e  */
    /* JADX WARN: Removed duplicated region for block: B:574:0x0c4e  */
    /* JADX WARN: Removed duplicated region for block: B:577:0x0c5e  */
    /* JADX WARN: Removed duplicated region for block: B:580:0x0c6e  */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0d13  */
    /* JADX WARN: Removed duplicated region for block: B:586:0x0d23  */
    /* JADX WARN: Removed duplicated region for block: B:589:0x0d33  */
    /* JADX WARN: Removed duplicated region for block: B:592:0x0d43  */
    /* JADX WARN: Removed duplicated region for block: B:595:0x0d58  */
    /* JADX WARN: Removed duplicated region for block: B:598:0x0d68  */
    /* JADX WARN: Removed duplicated region for block: B:601:0x0d78  */
    /* JADX WARN: Removed duplicated region for block: B:604:0x0d85  */
    /* JADX WARN: Removed duplicated region for block: B:607:0x0d95  */
    /* JADX WARN: Removed duplicated region for block: B:610:0x0da5  */
    /* JADX WARN: Removed duplicated region for block: B:613:0x0db5  */
    /* JADX WARN: Removed duplicated region for block: B:616:0x0dc2  */
    /* JADX WARN: Removed duplicated region for block: B:620:0x0b1a  */
    /* JADX WARN: Removed duplicated region for block: B:622:0x0a14  */
    /* JADX WARN: Removed duplicated region for block: B:624:0x09ea  */
    /* JADX WARN: Removed duplicated region for block: B:629:0x09ce  */
    /* JADX WARN: Removed duplicated region for block: B:631:0x0946  */
    /* JADX WARN: Removed duplicated region for block: B:645:0x0729  */
    /* JADX WARN: Removed duplicated region for block: B:649:0x06ad  */
    /* JADX WARN: Removed duplicated region for block: B:653:0x0670  */
    /* JADX WARN: Removed duplicated region for block: B:656:0x0620  */
    /* JADX WARN: Removed duplicated region for block: B:659:0x05f5  */
    /* JADX WARN: Removed duplicated region for block: B:665:0x05ad  */
    /* JADX WARN: Removed duplicated region for block: B:670:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:677:0x04f1  */
    /* JADX WARN: Removed duplicated region for block: B:686:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:689:0x03c9  */
    /* JADX WARN: Removed duplicated region for block: B:692:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:695:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:699:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:703:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:709:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:717:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x015f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 3538
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSmartPanelSettingBindingImpl.executeBindings():void");
    }
}