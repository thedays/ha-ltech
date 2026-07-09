package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
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
import com.ltech.smarthome.ui.login.FtFindPhonePwdVM;
import com.ltech.smarthome.view.ClearEditText;

/* loaded from: classes3.dex */
public class FtFindPhonePwdBindingImpl extends FtFindPhonePwdBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etAccountandroidTextAttrChanged;
    private InverseBindingListener etPwdandroidTextAttrChanged;
    private InverseBindingListener etVerificationandroidTextAttrChanged;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private InverseBindingListener tvErrorTipandroidTextAttrChanged;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(17);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{12}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.textView, 13);
        sparseIntArray.put(R.id.til_account, 14);
        sparseIntArray.put(R.id.til_verification, 15);
        sparseIntArray.put(R.id.til_pwd, 16);
    }

    public FtFindPhonePwdBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }

    private FtFindPhonePwdBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 7, (Button) bindings[10], (ClearEditText) bindings[4], (TextInputEditText) bindings[2], (ClearEditText) bindings[7], (TextInputEditText) bindings[5], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[3], (TextView) bindings[13], (TextInputLayout) bindings[14], (TextInputLayout) bindings[1], (TextInputLayout) bindings[16], (TextInputLayout) bindings[15], (LayoutTitleDefaultBinding) bindings[12], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[6]);
        this.etAccountandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtFindPhonePwdBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtFindPhonePwdBindingImpl.this.etAccount);
                FtFindPhonePwdVM ftFindPhonePwdVM = FtFindPhonePwdBindingImpl.this.mViewmodel;
                if (ftFindPhonePwdVM == null || (observableField = ftFindPhonePwdVM.account) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etPwdandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtFindPhonePwdBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtFindPhonePwdBindingImpl.this.etPwd);
                FtFindPhonePwdVM ftFindPhonePwdVM = FtFindPhonePwdBindingImpl.this.mViewmodel;
                if (ftFindPhonePwdVM == null || (observableField = ftFindPhonePwdVM.password) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etVerificationandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtFindPhonePwdBindingImpl.3
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtFindPhonePwdBindingImpl.this.etVerification);
                FtFindPhonePwdVM ftFindPhonePwdVM = FtFindPhonePwdBindingImpl.this.mViewmodel;
                if (ftFindPhonePwdVM == null || (observableField = ftFindPhonePwdVM.verificationCode) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.tvErrorTipandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtFindPhonePwdBindingImpl.4
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtFindPhonePwdBindingImpl.this.tvErrorTip);
                FtFindPhonePwdVM ftFindPhonePwdVM = FtFindPhonePwdBindingImpl.this.mViewmodel;
                if (ftFindPhonePwdVM == null || (observableField = ftFindPhonePwdVM.error_tips) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.btRegister.setTag(null);
        this.etAccount.setTag(null);
        this.etCountry.setTag(null);
        this.etPwd.setTag(null);
        this.etVerification.setTag(null);
        this.ivPassword.setTag(null);
        this.ivRigth.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tilCountry.setTag(null);
        setContainedBinding(this.title);
        this.tvChange.setTag(null);
        this.tvErrorTip.setTag(null);
        this.tvVerification.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 512L;
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
        setViewmodel((FtFindPhonePwdVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtFindPhonePwdBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtFindPhonePwdBinding
    public void setViewmodel(FtFindPhonePwdVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 256;
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
                return onChangeViewmodelCountryStr((MutableLiveData) object, fieldId);
            case 1:
                return onChangeViewmodelAccount((ObservableField) object, fieldId);
            case 2:
                return onChangeViewmodelVerificationTextBg((ObservableField) object, fieldId);
            case 3:
                return onChangeViewmodelPassword((ObservableField) object, fieldId);
            case 4:
                return onChangeViewmodelVerificationCode((ObservableField) object, fieldId);
            case 5:
                return onChangeViewmodelGetVerificationCountdown((ObservableField) object, fieldId);
            case 6:
                return onChangeViewmodelErrorTips((ObservableField) object, fieldId);
            default:
                return false;
        }
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

    private boolean onChangeViewmodelAccount(ObservableField<String> ViewmodelAccount, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelVerificationTextBg(ObservableField<Integer> ViewmodelVerificationTextBg, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelPassword(ObservableField<String> ViewmodelPassword, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    private boolean onChangeViewmodelVerificationCode(ObservableField<String> ViewmodelVerificationCode, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        return true;
    }

    private boolean onChangeViewmodelGetVerificationCountdown(ObservableField<String> ViewmodelGetVerificationCountdown, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        return true;
    }

    private boolean onChangeViewmodelErrorTips(ObservableField<String> ViewmodelErrorTips, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 64;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00dd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x00d4  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.FtFindPhonePwdBindingImpl.executeBindings():void");
    }
}