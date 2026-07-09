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
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Device;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.knobpanel.ActknobPanelSettingVM;
import com.ltech.smarthome.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/* loaded from: classes3.dex */
public class ActKnobPanelSettingBindingImpl extends ActKnobPanelSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private long mDirtyFlags_1;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView13;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView18;
    private final AppCompatTextView mboundView20;
    private final AppCompatTextView mboundView22;
    private final AppCompatTextView mboundView23;
    private final AppCompatTextView mboundView26;
    private final RelativeLayout mboundView28;
    private final AppCompatTextView mboundView29;
    private final RelativeLayout mboundView31;
    private final AppCompatTextView mboundView32;
    private final AppCompatTextView mboundView43;
    private final AppCompatTextView mboundView48;
    private final AppCompatTextView mboundView49;
    private final AppCompatTextView mboundView51;
    private final AppCompatTextView mboundView52;
    private final AppCompatTextView mboundView55;
    private final AppCompatImageView mboundView56;
    private final AppCompatTextView mboundView60;
    private final AppCompatImageView mboundView61;
    private final LinearLayout mboundView8;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(81);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{64}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.refreshLayout, 65);
        sparseIntArray.put(R.id.tv_room_name, 66);
        sparseIntArray.put(R.id.tv_sensitivity, 67);
        sparseIntArray.put(R.id.iv_sensitivity_go, 68);
        sparseIntArray.put(R.id.layout_mode_memorize, 69);
        sparseIntArray.put(R.id.tv_battery_tip, 70);
        sparseIntArray.put(R.id.battery_go, 71);
        sparseIntArray.put(R.id.tv_language_tip, 72);
        sparseIntArray.put(R.id.language_go, 73);
        sparseIntArray.put(R.id.tv_elderly_mode_tip, 74);
        sparseIntArray.put(R.id.elderly_mode_go, 75);
        sparseIntArray.put(R.id.tv_related_num, 76);
        sparseIntArray.put(R.id.iv_scene_go, 77);
        sparseIntArray.put(R.id.iv_device_id_go, 78);
        sparseIntArray.put(R.id.iv_mac_address, 79);
        sparseIntArray.put(R.id.iv_product_name, 80);
    }

    public ActKnobPanelSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 81, sIncludes, sViewsWithIds));
    }

    private ActKnobPanelSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 18, (AppCompatImageView) bindings[71], (AppCompatButton) bindings[27], (AppCompatImageView) bindings[75], (AppCompatImageView) bindings[57], (AppCompatImageView) bindings[78], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[41], (AppCompatImageView) bindings[62], (AppCompatImageView) bindings[79], (AppCompatImageView) bindings[80], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[77], (AppCompatImageView) bindings[68], (AppCompatImageView) bindings[37], (AppCompatImageView) bindings[73], (RelativeLayout) bindings[15], (RelativeLayout) bindings[19], (ConstraintLayout) bindings[5], (ConstraintLayout) bindings[44], (ConstraintLayout) bindings[1], (RelativeLayout) bindings[21], (ConstraintLayout) bindings[38], (RelativeLayout) bindings[58], (RelativeLayout) bindings[47], (RelativeLayout) bindings[69], (RelativeLayout) bindings[11], (LinearLayout) bindings[17], (RelativeLayout) bindings[50], (RelativeLayout) bindings[42], (RelativeLayout) bindings[9], (RelativeLayout) bindings[25], (ConstraintLayout) bindings[34], (RelativeLayout) bindings[53], (SmartRefreshLayout) bindings[65], (SwitchButton) bindings[33], (SwitchButton) bindings[24], (SwitchButton) bindings[14], (SwitchButton) bindings[30], (LayoutTitleDefaultBinding) bindings[64], (AppCompatTextView) bindings[70], (AppCompatTextView) bindings[63], (AppCompatTextView) bindings[46], (AppCompatTextView) bindings[45], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[74], (AppCompatTextView) bindings[40], (AppCompatTextView) bindings[39], (AppCompatTextView) bindings[59], (AppCompatTextView) bindings[72], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[76], (AppCompatTextView) bindings[66], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[67], (AppCompatTextView) bindings[36], (AppCompatTextView) bindings[35], (AppCompatTextView) bindings[54]);
        this.mDirtyFlags = -1L;
        this.mDirtyFlags_1 = -1L;
        this.btnAdjust.setTag(null);
        this.iv.setTag(null);
        this.ivDeviceNameGo.setTag(null);
        this.ivEndTimeGo.setTag(null);
        this.ivIconGo.setTag(null);
        this.ivRoomNameGo.setTag(null);
        this.ivStartTimeGo.setTag(null);
        this.layoutBattery.setTag(null);
        this.layoutChangeLanguage.setTag(null);
        this.layoutChangeRoom.setTag(null);
        this.layoutDeviceId.setTag(null);
        this.layoutDeviceName.setTag(null);
        this.layoutElderlyMode.setTag(null);
        this.layoutEndTimeKnob.setTag(null);
        this.layoutIconUpgrade.setTag(null);
        this.layoutMacAddress.setTag(null);
        this.layoutModeOrder.setTag(null);
        this.layoutMore.setTag(null);
        this.layoutProductName.setTag(null);
        this.layoutSceneAndAutomation.setTag(null);
        this.layoutSensitivity.setTag(null);
        this.layoutSetKRange.setTag(null);
        this.layoutStartTimeKnob.setTag(null);
        this.layoutUpgrade.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[13];
        this.mboundView13 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[16];
        this.mboundView16 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[18];
        this.mboundView18 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[20];
        this.mboundView20 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[22];
        this.mboundView22 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        AppCompatTextView appCompatTextView8 = (AppCompatTextView) bindings[23];
        this.mboundView23 = appCompatTextView8;
        appCompatTextView8.setTag(null);
        AppCompatTextView appCompatTextView9 = (AppCompatTextView) bindings[26];
        this.mboundView26 = appCompatTextView9;
        appCompatTextView9.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[28];
        this.mboundView28 = relativeLayout;
        relativeLayout.setTag(null);
        AppCompatTextView appCompatTextView10 = (AppCompatTextView) bindings[29];
        this.mboundView29 = appCompatTextView10;
        appCompatTextView10.setTag(null);
        RelativeLayout relativeLayout2 = (RelativeLayout) bindings[31];
        this.mboundView31 = relativeLayout2;
        relativeLayout2.setTag(null);
        AppCompatTextView appCompatTextView11 = (AppCompatTextView) bindings[32];
        this.mboundView32 = appCompatTextView11;
        appCompatTextView11.setTag(null);
        AppCompatTextView appCompatTextView12 = (AppCompatTextView) bindings[43];
        this.mboundView43 = appCompatTextView12;
        appCompatTextView12.setTag(null);
        AppCompatTextView appCompatTextView13 = (AppCompatTextView) bindings[48];
        this.mboundView48 = appCompatTextView13;
        appCompatTextView13.setTag(null);
        AppCompatTextView appCompatTextView14 = (AppCompatTextView) bindings[49];
        this.mboundView49 = appCompatTextView14;
        appCompatTextView14.setTag(null);
        AppCompatTextView appCompatTextView15 = (AppCompatTextView) bindings[51];
        this.mboundView51 = appCompatTextView15;
        appCompatTextView15.setTag(null);
        AppCompatTextView appCompatTextView16 = (AppCompatTextView) bindings[52];
        this.mboundView52 = appCompatTextView16;
        appCompatTextView16.setTag(null);
        AppCompatTextView appCompatTextView17 = (AppCompatTextView) bindings[55];
        this.mboundView55 = appCompatTextView17;
        appCompatTextView17.setTag(null);
        AppCompatImageView appCompatImageView = (AppCompatImageView) bindings[56];
        this.mboundView56 = appCompatImageView;
        appCompatImageView.setTag(null);
        AppCompatTextView appCompatTextView18 = (AppCompatTextView) bindings[60];
        this.mboundView60 = appCompatTextView18;
        appCompatTextView18.setTag(null);
        AppCompatImageView appCompatImageView2 = (AppCompatImageView) bindings[61];
        this.mboundView61 = appCompatImageView2;
        appCompatImageView2.setTag(null);
        LinearLayout linearLayout2 = (LinearLayout) bindings[8];
        this.mboundView8 = linearLayout2;
        linearLayout2.setTag(null);
        this.sbAutoTurnOff.setTag(null);
        this.sbEngravedText.setTag(null);
        this.sbModeMemorize.setTag(null);
        this.sbNightMode.setTag(null);
        setContainedBinding(this.title);
        this.tvDeleteDevice.setTag(null);
        this.tvDeviceIdName.setTag(null);
        this.tvDeviceIdTip.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvEndTime.setTag(null);
        this.tvEndTimeTip.setTag(null);
        this.tvIconTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRoomTip.setTag(null);
        this.tvStartTime.setTag(null);
        this.tvStartTimeTip.setTag(null);
        this.tvUpgradeTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1048576L;
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
        setViewmodel((ActknobPanelSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActKnobPanelSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActKnobPanelSettingBinding
    public void setViewmodel(ActknobPanelSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED;
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
                return onChangeViewmodelMemorizeMode((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelIsShowBindKRange((MutableLiveData) object, fieldId);
            case 2:
                return onChangeViewmodelIsShowIconVersion((MutableLiveData) object, fieldId);
            case 3:
                return onChangeViewmodelNightMode((MutableLiveData) object, fieldId);
            case 4:
                return onChangeViewmodelShowMore((MutableLiveData) object, fieldId);
            case 5:
                return onChangeViewmodelNewIconVersion((MutableLiveData) object, fieldId);
            case 6:
                return onChangeViewmodelNewVersion((MutableLiveData) object, fieldId);
            case 7:
                return onChangeViewmodelControlDevice((MutableLiveData) object, fieldId);
            case 8:
                return onChangeViewmodelIsOld((MutableLiveData) object, fieldId);
            case 9:
                return onChangeViewmodelStarTimeText((MutableLiveData) object, fieldId);
            case 10:
                return onChangeViewmodelIconVersion((MutableLiveData) object, fieldId);
            case 11:
                return onChangeViewmodelHasProductName((MutableLiveData) object, fieldId);
            case 12:
                return onChangeViewmodelEndTimeText((MutableLiveData) object, fieldId);
            case 13:
                return onChangeViewmodelProductName((MutableLiveData) object, fieldId);
            case 14:
                return onChangeViewmodelAutoTurnOff((MutableLiveData) object, fieldId);
            case 15:
                return onChangeViewmodelVersion((MutableLiveData) object, fieldId);
            case 16:
                return onChangeViewmodelIsProPanel((MutableLiveData) object, fieldId);
            case 17:
                return onChangeViewmodelEngravedText((MutableLiveData) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeViewmodelMemorizeMode(MutableLiveData<Boolean> ViewmodelMemorizeMode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelIsShowBindKRange(MutableLiveData<Boolean> ViewmodelIsShowBindKRange, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelIsShowIconVersion(MutableLiveData<Boolean> ViewmodelIsShowIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelNightMode(MutableLiveData<Boolean> ViewmodelNightMode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelShowMore(MutableLiveData<Boolean> ViewmodelShowMore, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelNewIconVersion(MutableLiveData<Boolean> ViewmodelNewIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelNewVersion(MutableLiveData<Boolean> ViewmodelNewVersion, int fieldId) {
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

    private boolean onChangeViewmodelIsOld(MutableLiveData<Boolean> ViewmodelIsOld, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        return true;
    }

    private boolean onChangeViewmodelStarTimeText(MutableLiveData<String> ViewmodelStarTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        return true;
    }

    private boolean onChangeViewmodelIconVersion(MutableLiveData<String> ViewmodelIconVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1024;
        }
        return true;
    }

    private boolean onChangeViewmodelHasProductName(MutableLiveData<Boolean> ViewmodelHasProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        }
        return true;
    }

    private boolean onChangeViewmodelEndTimeText(MutableLiveData<String> ViewmodelEndTimeText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        return true;
    }

    private boolean onChangeViewmodelProductName(MutableLiveData<String> ViewmodelProductName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8192;
        }
        return true;
    }

    private boolean onChangeViewmodelAutoTurnOff(MutableLiveData<Boolean> ViewmodelAutoTurnOff, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16384;
        }
        return true;
    }

    private boolean onChangeViewmodelVersion(MutableLiveData<String> ViewmodelVersion, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID;
        }
        return true;
    }

    private boolean onChangeViewmodelIsProPanel(MutableLiveData<Boolean> ViewmodelIsProPanel, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 65536;
        }
        return true;
    }

    private boolean onChangeViewmodelEngravedText(MutableLiveData<Boolean> ViewmodelEngravedText, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x03dc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0572  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x05be  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x05f3  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0615  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0623  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x06a6  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x06be  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x06d8  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x06e8  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x0702  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x071b  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x072b  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x073b  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x0750  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x075d  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x076d  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x07e7  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x07f7  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0815  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0825  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x0835  */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0845  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0852  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x0862  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x0872  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0882  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x069c  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x061c  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x05ea  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x05b2  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x033d  */
    /* JADX WARN: Removed duplicated region for block: B:429:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:444:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:462:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:466:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x016b  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 2194
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActKnobPanelSettingBindingImpl.executeBindings():void");
    }
}