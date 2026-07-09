package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.PlaceUser;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.share.ActPlaceUserSettingVM;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActPlaceUserSettingBindingImpl extends ActPlaceUserSettingBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView3;
    private final AppCompatTextView mboundView5;
    private final RelativeLayout mboundView6;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(25);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{18}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 19);
        sparseIntArray.put(R.id.iv_go_name, 20);
        sparseIntArray.put(R.id.iv_go_room, 21);
        sparseIntArray.put(R.id.iv_go_device, 22);
        sparseIntArray.put(R.id.iv_go_group, 23);
        sparseIntArray.put(R.id.iv_go_scene, 24);
    }

    public ActPlaceUserSettingBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 25, sIncludes, sViewsWithIds));
    }

    private ActPlaceUserSettingBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[24], (RelativeLayout) bindings[11], (RelativeLayout) bindings[13], (LinearLayout) bindings[19], (RelativeLayout) bindings[1], (RelativeLayout) bindings[9], (RelativeLayout) bindings[15], (SwitchButton) bindings[8], (LayoutTitleDefaultBinding) bindings[18], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[7]);
        this.mDirtyFlags = -1L;
        this.layoutDevice.setTag(null);
        this.layoutGroup.setTag(null);
        this.layoutName.setTag(null);
        this.layoutRoom.setTag(null);
        this.layoutScene.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[12];
        this.mboundView12 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[14];
        this.mboundView14 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[16];
        this.mboundView16 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[3];
        this.mboundView3 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[5];
        this.mboundView5 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[6];
        this.mboundView6 = relativeLayout;
        relativeLayout.setTag(null);
        this.sb.setTag(null);
        setContainedBinding(this.title);
        this.tvAccountTip.setTag(null);
        this.tvNameTip.setTag(null);
        this.tvRemove.setTag(null);
        this.tvSetManagerTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
        setViewmodel((ActPlaceUserSettingVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActPlaceUserSettingBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActPlaceUserSettingBinding
    public void setViewmodel(ActPlaceUserSettingVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 16;
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
        if (localFieldId == 0) {
            return onChangeViewmodelBManager((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelPOwner((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeViewmodelPlaceUser((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelBManager(MutableLiveData<Boolean> ViewmodelBManager, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelPOwner(MutableLiveData<Boolean> ViewmodelPOwner, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelPlaceUser(MutableLiveData<PlaceUser> ViewmodelPlaceUser, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00cb  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActPlaceUserSettingBindingImpl.executeBindings():void");
    }
}