package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.binding.view.ViewAdapter;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActNetConfigBindingImpl extends ActNetConfigBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etPwdandroidTextAttrChanged;
    private InverseBindingListener etRouterNameandroidTextAttrChanged;
    private long mDirtyFlags;
    private final LayoutTitleDefaultBinding mboundView0;
    private final LinearLayout mboundView01;
    private final AppCompatTextView mboundView1;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(13);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{7}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_pic, 8);
        sparseIntArray.put(R.id.til_router_name, 9);
        sparseIntArray.put(R.id.til_pwd, 10);
        sparseIntArray.put(R.id.v_focus, 11);
        sparseIntArray.put(R.id.tv_question, 12);
    }

    public ActNetConfigBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActNetConfigBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatButton) bindings[6], (AppCompatEditText) bindings[4], (AppCompatEditText) bindings[2], (AppCompatImageView) bindings[5], (LinearLayout) bindings[8], (TextInputLayout) bindings[10], (TextInputLayout) bindings[9], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[12], (View) bindings[11]);
        this.etPwdandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActNetConfigBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActNetConfigBindingImpl.this.etPwd);
                MutableLiveData<String> mutableLiveData = ActNetConfigBindingImpl.this.mPassword;
                if (mutableLiveData != null) {
                    mutableLiveData.setValue(textString);
                }
            }
        };
        this.etRouterNameandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActNetConfigBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActNetConfigBindingImpl.this.etRouterName);
                MutableLiveData<String> mutableLiveData = ActNetConfigBindingImpl.this.mSsid;
                if (mutableLiveData != null) {
                    mutableLiveData.setValue(textString);
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.btNext.setTag(null);
        this.etPwd.setTag(null);
        this.etRouterName.setTag(null);
        this.ivEye.setTag(null);
        LayoutTitleDefaultBinding layoutTitleDefaultBinding = (LayoutTitleDefaultBinding) bindings[7];
        this.mboundView0 = layoutTitleDefaultBinding;
        setContainedBinding(layoutTitleDefaultBinding);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView01 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[1];
        this.mboundView1 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.tvChangeNetwork.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
        }
        this.mboundView0.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.mboundView0.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (74 == variableId) {
            setSsid((MutableLiveData) variable);
            return true;
        }
        if (56 == variableId) {
            setPassword((MutableLiveData) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActNetConfigBinding
    public void setSsid(MutableLiveData<String> Ssid) {
        updateLiveDataRegistration(0, Ssid);
        this.mSsid = Ssid;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(74);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActNetConfigBinding
    public void setPassword(MutableLiveData<String> Password) {
        updateLiveDataRegistration(1, Password);
        this.mPassword = Password;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(56);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActNetConfigBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActNetConfigBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.mboundView0.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeSsid((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangePassword((MutableLiveData) object, fieldId);
    }

    private boolean onChangeSsid(MutableLiveData<String> Ssid, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangePassword(MutableLiveData<String> Password, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        MutableLiveData<String> mutableLiveData = this.mSsid;
        MutableLiveData<String> mutableLiveData2 = this.mPassword;
        BindingCommand<View> bindingCommand = this.mClickCommand;
        TitleDefault titleDefault = this.mTitle;
        long j2 = 17 & j;
        String value = (j2 == 0 || mutableLiveData == null) ? null : mutableLiveData.getValue();
        long j3 = 18 & j;
        String value2 = (j3 == 0 || mutableLiveData2 == null) ? null : mutableLiveData2.getValue();
        long j4 = 24 & j;
        if ((20 & j) != 0) {
            ViewAdapter.onClickCommand(this.btNext, bindingCommand, false);
            ViewAdapter.onClickCommand(this.ivEye, bindingCommand, false);
            ViewAdapter.onClickCommand(this.tvChangeNetwork, bindingCommand, false);
        }
        if (j3 != 0) {
            TextViewBindingAdapter.setText(this.etPwd, value2);
        }
        if ((j & 16) != 0) {
            TextViewBindingAdapter.setTextWatcher(this.etPwd, null, null, null, this.etPwdandroidTextAttrChanged);
            TextViewBindingAdapter.setTextWatcher(this.etRouterName, null, null, null, this.etRouterNameandroidTextAttrChanged);
            ViewAdapter.setTextBold(this.mboundView1, true);
        }
        if (j2 != 0) {
            TextViewBindingAdapter.setText(this.etRouterName, value);
        }
        if (j4 != 0) {
            this.mboundView0.setTitle(titleDefault);
        }
        executeBindingsOn(this.mboundView0);
    }
}