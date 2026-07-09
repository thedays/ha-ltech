package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActEditNameBindingImpl extends ActEditNameBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etNameandroidTextAttrChanged;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(7);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{5}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view9, 6);
    }

    public ActEditNameBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }

    private ActEditNameBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatEditText) bindings[2], (LayoutTitleDefaultBinding) bindings[5], (AppCompatImageView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[1], (View) bindings[6]);
        this.etNameandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActEditNameBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActEditNameBindingImpl.this.etName);
                MutableLiveData<String> mutableLiveData = ActEditNameBindingImpl.this.mName;
                if (mutableLiveData != null) {
                    mutableLiveData.setValue(textString);
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.etName.setTag(null);
        setContainedBinding(this.include);
        this.ivClear.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvDelete.setTag(null);
        this.tvEditTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64L;
        }
        this.include.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.include.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (48 == variableId) {
            setNameTip((String) variable);
            return true;
        }
        if (22 == variableId) {
            setDeleteTip((String) variable);
            return true;
        }
        if (46 == variableId) {
            setName((MutableLiveData) variable);
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

    @Override // com.ltech.smarthome.databinding.ActEditNameBinding
    public void setNameTip(String NameTip) {
        this.mNameTip = NameTip;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(48);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEditNameBinding
    public void setDeleteTip(String DeleteTip) {
        this.mDeleteTip = DeleteTip;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(22);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEditNameBinding
    public void setName(MutableLiveData<String> Name) {
        updateLiveDataRegistration(1, Name);
        this.mName = Name;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(46);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEditNameBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActEditNameBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.include.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeInclude((LayoutTitleDefaultBinding) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeName((MutableLiveData) object, fieldId);
    }

    private boolean onChangeInclude(LayoutTitleDefaultBinding Include, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeName(MutableLiveData<String> Name, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0081  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r20 = this;
            r1 = r20
            monitor-enter(r20)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lb8
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lb8
            monitor-exit(r20)     // Catch: java.lang.Throwable -> Lb8
            java.lang.String r0 = r1.mNameTip
            java.lang.String r6 = r1.mDeleteTip
            androidx.lifecycle.MutableLiveData<java.lang.String> r7 = r1.mName
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r8 = r1.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r9 = r1.mTitle
            r10 = 72
            long r12 = r2 & r10
            r14 = 0
            int r15 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r15 == 0) goto L30
            boolean r12 = android.text.TextUtils.isEmpty(r6)
            if (r15 == 0) goto L2b
            if (r12 == 0) goto L28
            r15 = 256(0x100, double:1.265E-321)
            goto L2a
        L28:
            r15 = 128(0x80, double:6.32E-322)
        L2a:
            long r2 = r2 | r15
        L2b:
            if (r12 == 0) goto L30
            r12 = 8
            goto L31
        L30:
            r12 = 0
        L31:
            r15 = 66
            long r15 = r15 & r2
            r13 = 0
            int r17 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r17 == 0) goto L42
            if (r7 == 0) goto L42
            java.lang.Object r7 = r7.getValue()
            java.lang.String r7 = (java.lang.String) r7
            goto L43
        L42:
            r7 = r13
        L43:
            r15 = 80
            long r15 = r15 & r2
            int r18 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            r15 = 96
            long r15 = r15 & r2
            int r19 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r17 == 0) goto L54
            androidx.appcompat.widget.AppCompatEditText r15 = r1.etName
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r15, r7)
        L54:
            r15 = 64
            long r15 = r15 & r2
            int r7 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r7 == 0) goto L81
            androidx.appcompat.widget.AppCompatEditText r7 = r1.etName
            r15 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r7, r15)
            androidx.appcompat.widget.AppCompatEditText r7 = r1.etName
            r16 = r13
            androidx.databinding.adapters.TextViewBindingAdapter$BeforeTextChanged r16 = (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged) r16
            r16 = r13
            androidx.databinding.adapters.TextViewBindingAdapter$OnTextChanged r16 = (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged) r16
            r16 = r13
            androidx.databinding.adapters.TextViewBindingAdapter$AfterTextChanged r16 = (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged) r16
            r16 = r4
            androidx.databinding.InverseBindingListener r4 = r1.etNameandroidTextAttrChanged
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(r7, r13, r13, r13, r4)
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvDelete
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r4, r15)
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvEditTip
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r4, r15)
            goto L83
        L81:
            r16 = r4
        L83:
            if (r19 == 0) goto L8a
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r4 = r1.include
            r4.setTitle(r9)
        L8a:
            if (r18 == 0) goto L96
            androidx.appcompat.widget.AppCompatImageView r4 = r1.ivClear
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r8, r14)
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvDelete
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r8, r14)
        L96:
            long r4 = r2 & r10
            int r7 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r7 == 0) goto La6
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvDelete
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r4, r6)
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvDelete
            r4.setVisibility(r12)
        La6:
            r4 = 68
            long r2 = r2 & r4
            int r4 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r4 == 0) goto Lb2
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvEditTip
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r2, r0)
        Lb2:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.include
            executeBindingsOn(r0)
            return
        Lb8:
            r0 = move-exception
            monitor-exit(r20)     // Catch: java.lang.Throwable -> Lb8
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActEditNameBindingImpl.executeBindings():void");
    }
}