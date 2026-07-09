package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.share.ActAddPlaceUserVM;

/* loaded from: classes3.dex */
public class ActAddPlaceUserBindingImpl extends ActAddPlaceUserBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView12;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView16;
    private final AppCompatTextView mboundView18;
    private final AppCompatEditText mboundView2;
    private InverseBindingListener mboundView2androidTextAttrChanged;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(27);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{19}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_load, 20);
        sparseIntArray.put(R.id.iv_go_room, 21);
        sparseIntArray.put(R.id.iv_go_device, 22);
        sparseIntArray.put(R.id.iv_go_set_permission, 23);
        sparseIntArray.put(R.id.iv_go_scene, 24);
        sparseIntArray.put(R.id.tv_time, 25);
        sparseIntArray.put(R.id.iv_go_time, 26);
    }

    public ActAddPlaceUserBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }

    private ActAddPlaceUserBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[21], (AppCompatImageView) bindings[24], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[26], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[7], (RelativeLayout) bindings[11], (ScrollView) bindings[20], (RelativeLayout) bindings[3], (RelativeLayout) bindings[6], (RelativeLayout) bindings[9], (RelativeLayout) bindings[15], (RelativeLayout) bindings[17], (RelativeLayout) bindings[13], (LayoutTitleDefaultBinding) bindings[19], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[8], (AppCompatTextView) bindings[25]);
        this.mboundView2androidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActAddPlaceUserBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                MutableLiveData<String> mutableLiveData;
                String textString = TextViewBindingAdapter.getTextString(ActAddPlaceUserBindingImpl.this.mboundView2);
                ActAddPlaceUserVM actAddPlaceUserVM = ActAddPlaceUserBindingImpl.this.mViewmodel;
                if (actAddPlaceUserVM == null || (mutableLiveData = actAddPlaceUserVM.account) == null) {
                    return;
                }
                mutableLiveData.setValue(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.ivMangerTick.setTag(null);
        this.ivMemberTick.setTag(null);
        this.layoutDevice.setTag(null);
        this.layoutManager.setTag(null);
        this.layoutMember.setTag(null);
        this.layoutRoom.setTag(null);
        this.layoutScene.setTag(null);
        this.layoutTime.setTag(null);
        this.lightGroupPermission.setTag(null);
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
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[18];
        this.mboundView18 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatEditText appCompatEditText = (AppCompatEditText) bindings[2];
        this.mboundView2 = appCompatEditText;
        appCompatEditText.setTag(null);
        setContainedBinding(this.title);
        this.tvAccountTip.setTag(null);
        this.tvManager.setTag(null);
        this.tvMember.setTag(null);
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
        setViewmodel((ActAddPlaceUserVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActAddPlaceUserBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActAddPlaceUserBinding
    public void setViewmodel(ActAddPlaceUserVM Viewmodel) {
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
            return onChangeViewmodelPlace((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelAccount((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeViewmodelShareManagerPermission((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelPlace(MutableLiveData<Place> ViewmodelPlace, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelAccount(MutableLiveData<String> ViewmodelAccount, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelShareManagerPermission(MutableLiveData<Boolean> ViewmodelShareManagerPermission, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00c6  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActAddPlaceUserBindingImpl.executeBindings():void");
    }
}