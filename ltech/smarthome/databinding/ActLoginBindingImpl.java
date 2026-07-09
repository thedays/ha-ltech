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
import com.ltech.smarthome.model.extra.ProtocolDefault;
import com.ltech.smarthome.ui.login.ActLoginVM;
import com.ltech.smarthome.view.ClearEditText;

/* loaded from: classes3.dex */
public class ActLoginBindingImpl extends ActLoginBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etAccountandroidTextAttrChanged;
    private InverseBindingListener etPwdandroidTextAttrChanged;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private InverseBindingListener textView4androidTextAttrChanged;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(23);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_protocol_default"}, new int[]{15}, new int[]{R.layout.layout_protocol_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.textView, 16);
        sparseIntArray.put(R.id.textView2, 17);
        sparseIntArray.put(R.id.til_account, 18);
        sparseIntArray.put(R.id.til_pwd, 19);
        sparseIntArray.put(R.id.appCompatTextView, 20);
        sparseIntArray.put(R.id.view, 21);
        sparseIntArray.put(R.id.view2, 22);
    }

    public ActLoginBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds));
    }

    private ActLoginBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4, (AppCompatTextView) bindings[20], (Button) bindings[9], (ClearEditText) bindings[5], (TextInputEditText) bindings[3], (ClearEditText) bindings[6], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[7], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[13], (AppCompatImageView) bindings[10], (LayoutProtocolDefaultBinding) bindings[15], (TextView) bindings[16], (TextView) bindings[17], (TextView) bindings[14], (TextInputLayout) bindings[18], (TextInputLayout) bindings[2], (TextInputLayout) bindings[19], (TextView) bindings[8], (TextView) bindings[1], (View) bindings[21], (View) bindings[22]);
        this.etAccountandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActLoginBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActLoginBindingImpl.this.etAccount);
                ActLoginVM actLoginVM = ActLoginBindingImpl.this.mViewmodel;
                if (actLoginVM == null || (observableField = actLoginVM.account) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etPwdandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActLoginBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActLoginBindingImpl.this.etPwd);
                ActLoginVM actLoginVM = ActLoginBindingImpl.this.mViewmodel;
                if (actLoginVM == null || (observableField = actLoginVM.password) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.textView4androidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActLoginBindingImpl.3
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActLoginBindingImpl.this.textView4);
                ActLoginVM actLoginVM = ActLoginBindingImpl.this.mViewmodel;
                if (actLoginVM == null || (observableField = actLoginVM.errorTips) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.btLogin.setTag(null);
        this.etAccount.setTag(null);
        this.etCountry.setTag(null);
        this.etPwd.setTag(null);
        this.ivFacebook.setTag(null);
        this.ivPassword.setTag(null);
        this.ivQq.setTag(null);
        this.ivRigth.setTag(null);
        this.ivTwitter.setTag(null);
        this.ivWechat.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.protocol);
        this.textView4.setTag(null);
        this.tilCountry.setTag(null);
        this.tvFindBackPwd.setTag(null);
        this.tvRegister.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
        }
        this.protocol.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.protocol.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (62 == variableId) {
            setProtocol((ProtocolDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActLoginVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActLoginBinding
    public void setProtocol(ProtocolDefault Protocol) {
        this.mProtocol = Protocol;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(62);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActLoginBinding
    public void setViewmodel(ActLoginVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(92);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.protocol.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelCountryStr((MutableLiveData) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelAccount((ObservableField) object, fieldId);
        }
        if (localFieldId == 2) {
            return onChangeViewmodelPassword((ObservableField) object, fieldId);
        }
        if (localFieldId != 3) {
            return false;
        }
        return onChangeViewmodelErrorTips((ObservableField) object, fieldId);
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

    private boolean onChangeViewmodelPassword(ObservableField<String> ViewmodelPassword, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    private boolean onChangeViewmodelErrorTips(ObservableField<String> ViewmodelErrorTips, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x005a  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActLoginBindingImpl.executeBindings():void");
    }
}