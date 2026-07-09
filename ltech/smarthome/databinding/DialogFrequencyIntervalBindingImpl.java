package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public class DialogFrequencyIntervalBindingImpl extends DialogFrequencyIntervalBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private InverseBindingListener etContent2androidTextAttrChanged;
    private InverseBindingListener etContentandroidTextAttrChanged;
    private long mDirtyFlags;

    public DialogFrequencyIntervalBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private DialogFrequencyIntervalBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatEditText) bindings[2], (AppCompatEditText) bindings[4], (AppCompatImageView) bindings[3], (AppCompatImageView) bindings[5], (LinearLayout) bindings[0], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[1]);
        this.etContentandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.DialogFrequencyIntervalBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(DialogFrequencyIntervalBindingImpl.this.etContent);
                ObservableField<String> observableField = DialogFrequencyIntervalBindingImpl.this.mContent;
                if (observableField != null) {
                    observableField.set(textString);
                }
            }
        };
        this.etContent2androidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.DialogFrequencyIntervalBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(DialogFrequencyIntervalBindingImpl.this.etContent2);
                ObservableField<String> observableField = DialogFrequencyIntervalBindingImpl.this.mContent2;
                if (observableField != null) {
                    observableField.set(textString);
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.etContent.setTag(null);
        this.etContent2.setTag(null);
        this.ivDelete.setTag(null);
        this.ivDelete2.setTag(null);
        this.layoutBg.setTag(null);
        this.tvCancel.setTag(null);
        this.tvOk.setTag(null);
        this.tvTitle.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (14 != variableId) {
            return false;
        }
        setContent2((ObservableField) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.DialogFrequencyIntervalBinding
    public void setContent(ObservableField<String> Content) {
        updateRegistration(0, Content);
        this.mContent = Content;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(13);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogFrequencyIntervalBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.DialogFrequencyIntervalBinding
    public void setContent2(ObservableField<String> Content2) {
        updateRegistration(1, Content2);
        this.mContent2 = Content2;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(14);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeContent((ObservableField) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeContent2((ObservableField) object, fieldId);
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

    private boolean onChangeContent2(ObservableField<String> Content2, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x005c, code lost:
    
        if (r17 != false) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x005f  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            Method dump skipped, instructions count: 195
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.DialogFrequencyIntervalBindingImpl.executeBindings():void");
    }
}