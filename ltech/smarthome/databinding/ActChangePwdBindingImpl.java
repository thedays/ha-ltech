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
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.login.ActChangePwdVM;
import com.ltech.smarthome.view.ClearEditText;

/* loaded from: classes3.dex */
public class ActChangePwdBindingImpl extends ActChangePwdBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etNewPwdandroidTextAttrChanged;
    private InverseBindingListener etOldPwdandroidTextAttrChanged;
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
        sparseIntArray.put(R.id.til_old_pwd, 10);
        sparseIntArray.put(R.id.til_new_pwd, 11);
    }

    public ActChangePwdBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }

    private ActChangePwdBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3, (Button) bindings[7], (Button) bindings[6], (ClearEditText) bindings[3], (ClearEditText) bindings[1], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[2], (TextView) bindings[9], (TextInputLayout) bindings[11], (TextInputLayout) bindings[10], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[5]);
        this.etNewPwdandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActChangePwdBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActChangePwdBindingImpl.this.etNewPwd);
                ActChangePwdVM actChangePwdVM = ActChangePwdBindingImpl.this.mViewmodel;
                if (actChangePwdVM == null || (observableField = actChangePwdVM.newPwd) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.etOldPwdandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActChangePwdBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActChangePwdBindingImpl.this.etOldPwd);
                ActChangePwdVM actChangePwdVM = ActChangePwdBindingImpl.this.mViewmodel;
                if (actChangePwdVM == null || (observableField = actChangePwdVM.oldPwd) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.tvErrorTipandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActChangePwdBindingImpl.3
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                ObservableField<String> observableField;
                String textString = TextViewBindingAdapter.getTextString(ActChangePwdBindingImpl.this.tvErrorTip);
                ActChangePwdVM actChangePwdVM = ActChangePwdBindingImpl.this.mViewmodel;
                if (actChangePwdVM == null || (observableField = actChangePwdVM.error_tips) == null) {
                    return;
                }
                observableField.set(textString);
            }
        };
        this.mDirtyFlags = -1L;
        this.btForget.setTag(null);
        this.btRegister.setTag(null);
        this.etNewPwd.setTag(null);
        this.etOldPwd.setTag(null);
        this.ivNewPassword.setTag(null);
        this.ivOldPassword.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvErrorTip.setTag(null);
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
        setViewmodel((ActChangePwdVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActChangePwdBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActChangePwdBinding
    public void setViewmodel(ActChangePwdVM Viewmodel) {
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
            return onChangeViewmodelOldPwd((ObservableField) object, fieldId);
        }
        if (localFieldId == 1) {
            return onChangeViewmodelNewPwd((ObservableField) object, fieldId);
        }
        if (localFieldId != 2) {
            return false;
        }
        return onChangeViewmodelErrorTips((ObservableField) object, fieldId);
    }

    private boolean onChangeViewmodelOldPwd(ObservableField<String> ViewmodelOldPwd, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelNewPwd(ObservableField<String> ViewmodelNewPwd, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    private boolean onChangeViewmodelErrorTips(ObservableField<String> ViewmodelErrorTips, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006b  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActChangePwdBindingImpl.executeBindings():void");
    }
}