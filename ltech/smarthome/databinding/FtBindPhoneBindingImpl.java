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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.login.FtBindPhoneVM;

/* loaded from: classes3.dex */
public class FtBindPhoneBindingImpl extends FtBindPhoneBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etAccountandroidTextAttrChanged;
    private InverseBindingListener etCountryandroidTextAttrChanged;
    private InverseBindingListener etPwdandroidTextAttrChanged;
    private InverseBindingListener etVerificationandroidTextAttrChanged;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private InverseBindingListener tvErrorTipandroidTextAttrChanged;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(18);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default", "layout_protocol_default"}, new int[]{10, 11}, new int[]{R.layout.layout_title_default, R.layout.layout_protocol_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.textView, 12);
        sparseIntArray.put(R.id.til_country, 13);
        sparseIntArray.put(R.id.iv_rigth, 14);
        sparseIntArray.put(R.id.til_account, 15);
        sparseIntArray.put(R.id.til_verification, 16);
        sparseIntArray.put(R.id.til_pwd, 17);
    }

    public FtBindPhoneBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }

    private FtBindPhoneBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 7, (Button) bindings[8], (TextInputEditText) bindings[2], (TextInputEditText) bindings[1], (TextInputEditText) bindings[5], (TextInputEditText) bindings[3], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[14], (LayoutProtocolDefaultBinding) bindings[11], (TextView) bindings[12], (TextInputLayout) bindings[15], (TextInputLayout) bindings[13], (TextInputLayout) bindings[17], (TextInputLayout) bindings[16], (LayoutTitleDefaultBinding) bindings[10], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[4]);
        this.etAccountandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtBindPhoneBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtBindPhoneBindingImpl.this.etAccount);
                FtBindPhoneVM ftBindPhoneVM = FtBindPhoneBindingImpl.this.mViewmodel;
                if (ftBindPhoneVM == null || (observableField = ftBindPhoneVM.account) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etCountryandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtBindPhoneBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtBindPhoneBindingImpl.this.etCountry);
                FtBindPhoneVM ftBindPhoneVM = FtBindPhoneBindingImpl.this.mViewmodel;
                if (ftBindPhoneVM == null || (observableField = ftBindPhoneVM.country) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etPwdandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtBindPhoneBindingImpl.3
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtBindPhoneBindingImpl.this.etPwd);
                FtBindPhoneVM ftBindPhoneVM = FtBindPhoneBindingImpl.this.mViewmodel;
                if (ftBindPhoneVM == null || (observableField = ftBindPhoneVM.password) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etVerificationandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtBindPhoneBindingImpl.4
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtBindPhoneBindingImpl.this.etVerification);
                FtBindPhoneVM ftBindPhoneVM = FtBindPhoneBindingImpl.this.mViewmodel;
                if (ftBindPhoneVM == null || (observableField = ftBindPhoneVM.verificationCode) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.tvErrorTipandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtBindPhoneBindingImpl.5
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtBindPhoneBindingImpl.this.tvErrorTip);
                FtBindPhoneVM ftBindPhoneVM = FtBindPhoneBindingImpl.this.mViewmodel;
                if (ftBindPhoneVM == null || (observableField = ftBindPhoneVM.error_tips) == null) {
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
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.protocol);
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
            this.mDirtyFlags = 1024L;
        }
        this.title.invalidateAll();
        this.protocol.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings() || this.protocol.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (62 == variableId) {
            setProtocol((ProtocolDefault) variable);
            return true;
        }
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((FtBindPhoneVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtBindPhoneBinding
    public void setProtocol(ProtocolDefault Protocol) {
        this.mProtocol = Protocol;
        synchronized (this) {
            this.mDirtyFlags |= 128;
        }
        notifyPropertyChanged(62);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtBindPhoneBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 256;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtBindPhoneBinding
    public void setViewmodel(FtBindPhoneVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 512;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
        this.protocol.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeViewmodelCountry((ObservableField) object, fieldId);
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

    private boolean onChangeViewmodelCountry(ObservableField<String> ViewmodelCountry, int fieldId) {
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f0  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 457
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.FtBindPhoneBindingImpl.executeBindings():void");
    }
}