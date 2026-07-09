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
import com.ltech.smarthome.ui.login.FtFindMailPwdVM;

/* loaded from: classes3.dex */
public class FtFindMailPwdBindingImpl extends FtFindMailPwdBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etAccountandroidTextAttrChanged;
    private InverseBindingListener etPwdandroidTextAttrChanged;
    private InverseBindingListener etVerificationandroidTextAttrChanged;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(13);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{8}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.textView, 9);
        sparseIntArray.put(R.id.til_account, 10);
        sparseIntArray.put(R.id.til_verification, 11);
        sparseIntArray.put(R.id.til_pwd, 12);
    }

    public FtFindMailPwdBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private FtFindMailPwdBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 5, (Button) bindings[6], (TextInputEditText) bindings[1], (TextInputEditText) bindings[4], (TextInputEditText) bindings[2], (AppCompatImageView) bindings[5], (TextView) bindings[9], (TextInputLayout) bindings[10], (TextInputLayout) bindings[12], (TextInputLayout) bindings[11], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[3]);
        this.etAccountandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtFindMailPwdBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtFindMailPwdBindingImpl.this.etAccount);
                FtFindMailPwdVM ftFindMailPwdVM = FtFindMailPwdBindingImpl.this.mViewmodel;
                if (ftFindMailPwdVM == null || (observableField = ftFindMailPwdVM.account) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etPwdandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtFindMailPwdBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtFindMailPwdBindingImpl.this.etPwd);
                FtFindMailPwdVM ftFindMailPwdVM = FtFindMailPwdBindingImpl.this.mViewmodel;
                if (ftFindMailPwdVM == null || (observableField = ftFindMailPwdVM.password) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etVerificationandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.FtFindMailPwdBindingImpl.3
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(FtFindMailPwdBindingImpl.this.etVerification);
                FtFindMailPwdVM ftFindMailPwdVM = FtFindMailPwdBindingImpl.this.mViewmodel;
                if (ftFindMailPwdVM == null || (observableField = ftFindMailPwdVM.verificationCode) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.btRegister.setTag(null);
        this.etAccount.setTag(null);
        this.etPwd.setTag(null);
        this.etVerification.setTag(null);
        this.ivPassword.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvChange.setTag(null);
        this.tvVerification.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 128L;
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
        setViewmodel((FtFindMailPwdVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.FtFindMailPwdBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.FtFindMailPwdBinding
    public void setViewmodel(FtFindMailPwdVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 64;
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
            return onChangeViewmodelGetVerificationCountdown((ObservableField) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelAccount((ObservableField) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelVerificationTextBg((ObservableField) object, fieldId);
        }
        if (localFieldId == 3) {
            return onChangeViewmodelPassword((ObservableField) object, fieldId);
        }
        if (localFieldId != 4) {
            return false;
        }
        return onChangeViewmodelVerificationCode((ObservableField) object, fieldId);
    }

    private boolean onChangeViewmodelGetVerificationCountdown(ObservableField<String> ViewmodelGetVerificationCountdown, int fieldId) {
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x008a  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.FtFindMailPwdBindingImpl.executeBindings():void");
    }
}