package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.login.ActChangePhoneVM;

/* loaded from: classes3.dex */
public class ActChangePhoneBindingImpl extends ActChangePhoneBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etAccountandroidTextAttrChanged;
    private InverseBindingListener etCountryandroidTextAttrChanged;
    private InverseBindingListener etVerificationandroidTextAttrChanged;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private InverseBindingListener tvErrorTipandroidTextAttrChanged;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(12);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.textView, 9);
        sparseIntArray.put(R.id.til_account, 10);
        sparseIntArray.put(R.id.til_verification, 11);
    }

    public ActChangePhoneBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private ActChangePhoneBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6, (Button) bindings[7], (TextInputEditText) bindings[3], (TextInputEditText) bindings[2], (TextInputEditText) bindings[4], (TextView) bindings[9], (TextInputLayout) bindings[10], (TextInputLayout) bindings[1], (TextInputLayout) bindings[11], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[5]);
        this.etAccountandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActChangePhoneBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActChangePhoneBindingImpl.this.etAccount);
                ActChangePhoneVM actChangePhoneVM = ActChangePhoneBindingImpl.this.mViewmodel;
                if (actChangePhoneVM == null || (observableField = actChangePhoneVM.account) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etCountryandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActChangePhoneBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                MutableLiveData<String> mutableLiveData;
                String textString = TextViewBindingAdapter.getTextString(ActChangePhoneBindingImpl.this.etCountry);
                ActChangePhoneVM actChangePhoneVM = ActChangePhoneBindingImpl.this.mViewmodel;
                if (actChangePhoneVM == null || (mutableLiveData = actChangePhoneVM.countryStr) == null) {
                    return;
                }
                mutableLiveData.setValue(textString);
            }
        };
        this.etVerificationandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActChangePhoneBindingImpl.3
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActChangePhoneBindingImpl.this.etVerification);
                ActChangePhoneVM actChangePhoneVM = ActChangePhoneBindingImpl.this.mViewmodel;
                if (actChangePhoneVM == null || (observableField = actChangePhoneVM.verificationCode) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.tvErrorTipandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActChangePhoneBindingImpl.4
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActChangePhoneBindingImpl.this.tvErrorTip);
                ActChangePhoneVM actChangePhoneVM = ActChangePhoneBindingImpl.this.mViewmodel;
                if (actChangePhoneVM == null || (observableField = actChangePhoneVM.error_tips) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.btRegister.setTag(null);
        this.etAccount.setTag(null);
        this.etCountry.setTag(null);
        this.etVerification.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tilCountry.setTag(null);
        setContainedBinding(this.title);
        this.tvErrorTip.setTag(null);
        this.tvVerification.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 256L;
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
        setViewmodel((ActChangePhoneVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActChangePhoneBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActChangePhoneBinding
    public void setViewmodel(ActChangePhoneVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 128;
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
            return onChangeViewmodelCountryStr((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelGetVerificationCountdown((ObservableField) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelAccount((ObservableField) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelVerificationTextBg((ObservableField) object, fieldId);
        }
        if (localFieldId == 4) {
            return onChangeViewmodelErrorTips((ObservableField) object, fieldId);
        }
        if (localFieldId != 5) {
            return false;
        }
        return onChangeViewmodelVerificationCode((ObservableField) object, fieldId);
    }

    private boolean onChangeViewmodelCountryStr(MutableLiveData<String> ViewmodelCountryStr, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelGetVerificationCountdown(ObservableField<String> ViewmodelGetVerificationCountdown, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelAccount(ObservableField<String> ViewmodelAccount, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelVerificationTextBg(ObservableField<Integer> ViewmodelVerificationTextBg, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelErrorTips(ObservableField<String> ViewmodelErrorTips, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelVerificationCode(ObservableField<String> ViewmodelVerificationCode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0063 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x005a  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActChangePhoneBindingImpl.executeBindings():void");
    }
}