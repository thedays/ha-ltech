package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public class DialogAddCg485DeviceBindingImpl extends DialogAddCg485DeviceBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etNameandroidTextAttrChanged;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view_divider, 8);
        sparseIntArray.put(R.id.rv_icon, 9);
        sparseIntArray.put(R.id.group_add, 10);
    }

    public DialogAddCg485DeviceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }

    private DialogAddCg485DeviceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatEditText) bindings[5], (Group) bindings[10], (AppCompatImageView) bindings[6], (ConstraintLayout) bindings[0], (RecyclerView) bindings[9], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[1], (View) bindings[8]);
        this.etNameandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.DialogAddCg485DeviceBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(DialogAddCg485DeviceBindingImpl.this.etName);
                ObservableField<String> observableField = DialogAddCg485DeviceBindingImpl.this.mContent;
                if (observableField != null) {
                    observableField.set(textString);
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.etName.setTag(null);
        this.ivClear.setTag(null);
        this.layoutBg.setTag(null);
        this.tvCancel.setTag(null);
        this.tvIcon.setTag(null);
        this.tvLabel.setTag(null);
        this.tvSave.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (13 == variableId) {
            setContent((ObservableField) variable);
            return true;
        }
        if (10 != variableId) {
            return false;
        }
        setClickCommand((BindingCommand) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogAddCg485DeviceBinding
    public void setContent(ObservableField<String> Content) {
        updateRegistration(0, Content);
        this.mContent = Content;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(13);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogAddCg485DeviceBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeContent((ObservableField) object, fieldId);
    }

    private boolean onChangeContent(ObservableField<String> Content, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r14 = this;
            monitor-enter(r14)
            long r0 = r14.mDirtyFlags     // Catch: java.lang.Throwable -> L82
            r2 = 0
            r14.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L82
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L82
            androidx.databinding.ObservableField<java.lang.String> r4 = r14.mContent
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r5 = r14.mClickCommand
            r6 = 5
            long r8 = r0 & r6
            r10 = 0
            r11 = 0
            int r12 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r12 == 0) goto L33
            if (r4 == 0) goto L1f
            java.lang.Object r4 = r4.get()
            java.lang.String r4 = (java.lang.String) r4
            goto L20
        L1f:
            r4 = r10
        L20:
            boolean r8 = android.text.TextUtils.isEmpty(r4)
            if (r12 == 0) goto L2e
            if (r8 == 0) goto L2b
            r12 = 16
            goto L2d
        L2b:
            r12 = 8
        L2d:
            long r0 = r0 | r12
        L2e:
            if (r8 == 0) goto L34
            r8 = 8
            goto L35
        L33:
            r4 = r10
        L34:
            r8 = 0
        L35:
            r12 = 6
            long r12 = r12 & r0
            int r9 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            long r6 = r6 & r0
            int r12 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r12 == 0) goto L49
            androidx.appcompat.widget.AppCompatEditText r6 = r14.etName
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r6, r4)
            androidx.appcompat.widget.AppCompatImageView r4 = r14.ivClear
            r4.setVisibility(r8)
        L49:
            r6 = 4
            long r0 = r0 & r6
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L70
            androidx.appcompat.widget.AppCompatEditText r0 = r14.etName
            r1 = r10
            androidx.databinding.adapters.TextViewBindingAdapter$BeforeTextChanged r1 = (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged) r1
            r1 = r10
            androidx.databinding.adapters.TextViewBindingAdapter$OnTextChanged r1 = (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged) r1
            r1 = r10
            androidx.databinding.adapters.TextViewBindingAdapter$AfterTextChanged r1 = (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged) r1
            androidx.databinding.InverseBindingListener r1 = r14.etNameandroidTextAttrChanged
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(r0, r10, r10, r10, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvIcon
            r1 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvLabel
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvTitle
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
        L70:
            if (r9 == 0) goto L81
            androidx.appcompat.widget.AppCompatImageView r0 = r14.ivClear
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvCancel
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvSave
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r5, r11)
        L81:
            return
        L82:
            r0 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L82
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.DialogAddCg485DeviceBindingImpl.executeBindings():void");
    }
}