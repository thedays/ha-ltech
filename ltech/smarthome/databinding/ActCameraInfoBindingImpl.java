package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import com.google.android.material.textfield.TextInputLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActCameraInfoBindingImpl extends ActCameraInfoBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private InverseBindingListener etSerialNumandroidTextAttrChanged;
    private InverseBindingListener etVerifyCodeandroidTextAttrChanged;
    private long mDirtyFlags;
    private final LayoutTitleDefaultBinding mboundView0;
    private final LinearLayout mboundView01;
    private final AppCompatTextView mboundView1;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(16);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{6}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_pic, 7);
        sparseIntArray.put(R.id.til_serial_num, 8);
        sparseIntArray.put(R.id.til_verify_code, 9);
        sparseIntArray.put(R.id.v_focus, 10);
        sparseIntArray.put(R.id.tv_question, 11);
        sparseIntArray.put(R.id.iv_camera, 12);
        sparseIntArray.put(R.id.tv_camera_serial, 13);
        sparseIntArray.put(R.id.tv_scan_tip, 14);
        sparseIntArray.put(R.id.seriesNumberEt, 15);
    }

    public ActCameraInfoBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }

    private ActCameraInfoBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatButton) bindings[4], (AppCompatEditText) bindings[2], (AppCompatEditText) bindings[3], (AppCompatImageView) bindings[12], (LinearLayout) bindings[7], (RelativeLayout) bindings[5], (EditText) bindings[15], (TextInputLayout) bindings[8], (TextInputLayout) bindings[9], (TextView) bindings[13], (AppCompatTextView) bindings[11], (TextView) bindings[14], (View) bindings[10]);
        this.etSerialNumandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActCameraInfoBindingImpl.1
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActCameraInfoBindingImpl.this.etSerialNum);
                String str = ActCameraInfoBindingImpl.this.mSerialNum;
                ActCameraInfoBindingImpl actCameraInfoBindingImpl = ActCameraInfoBindingImpl.this;
                if (actCameraInfoBindingImpl != null) {
                    actCameraInfoBindingImpl.setSerialNum(textString);
                }
            }
        };
        this.etVerifyCodeandroidTextAttrChanged = new InverseBindingListener() { // from class: com.ltech.smarthome.databinding.ActCameraInfoBindingImpl.2
            @Override // androidx.databinding.InverseBindingListener
            public void onChange() {
                String textString = TextViewBindingAdapter.getTextString(ActCameraInfoBindingImpl.this.etVerifyCode);
                String str = ActCameraInfoBindingImpl.this.mVerifyCode;
                ActCameraInfoBindingImpl actCameraInfoBindingImpl = ActCameraInfoBindingImpl.this;
                if (actCameraInfoBindingImpl != null) {
                    actCameraInfoBindingImpl.setVerifyCode(textString);
                }
            }
        };
        this.mDirtyFlags = -1L;
        this.btNext.setTag(null);
        this.etSerialNum.setTag(null);
        this.etVerifyCode.setTag(null);
        LayoutTitleDefaultBinding layoutTitleDefaultBinding = (LayoutTitleDefaultBinding) bindings[6];
        this.mboundView0 = layoutTitleDefaultBinding;
        setContainedBinding(layoutTitleDefaultBinding);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView01 = linearLayout;
        linearLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[1];
        this.mboundView1 = appCompatTextView;
        appCompatTextView.setTag(null);
        this.serialEditTextContainer.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
        if (71 == variableId) {
            setShowInput((Boolean) variable);
            return true;
        }
        if (70 == variableId) {
            setSerialNum((String) variable);
            return true;
        }
        if (90 == variableId) {
            setVerifyCode((String) variable);
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

    @Override // com.ltech.smarthome.databinding.ActCameraInfoBinding
    public void setShowInput(Boolean ShowInput) {
        this.mShowInput = ShowInput;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(71);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCameraInfoBinding
    public void setSerialNum(String SerialNum) {
        this.mSerialNum = SerialNum;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(70);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCameraInfoBinding
    public void setVerifyCode(String VerifyCode) {
        this.mVerifyCode = VerifyCode;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(90);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCameraInfoBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCameraInfoBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.mboundView0.setLifecycleOwner(lifecycleOwner);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x008c  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r19 = this;
            r1 = r19
            monitor-enter(r19)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> L97
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> L97
            monitor-exit(r19)     // Catch: java.lang.Throwable -> L97
            java.lang.Boolean r0 = r1.mShowInput
            java.lang.String r6 = r1.mSerialNum
            java.lang.String r7 = r1.mVerifyCode
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r8 = r1.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r9 = r1.mTitle
            r10 = 33
            long r12 = r2 & r10
            r14 = 0
            int r15 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r15 == 0) goto L31
            boolean r0 = androidx.databinding.ViewDataBinding.safeUnbox(r0)
            if (r15 == 0) goto L2b
            if (r0 == 0) goto L28
            r12 = 128(0x80, double:6.32E-322)
            goto L2a
        L28:
            r12 = 64
        L2a:
            long r2 = r2 | r12
        L2b:
            if (r0 == 0) goto L2e
            goto L31
        L2e:
            r0 = 8
            goto L32
        L31:
            r0 = 0
        L32:
            r12 = 34
            long r12 = r12 & r2
            int r15 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            r12 = 36
            long r12 = r12 & r2
            int r16 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            r12 = 40
            long r12 = r12 & r2
            int r17 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            r12 = 48
            long r12 = r12 & r2
            int r18 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r17 == 0) goto L4d
            androidx.appcompat.widget.AppCompatButton r12 = r1.btNext
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r12, r8, r14)
        L4d:
            if (r15 == 0) goto L54
            androidx.appcompat.widget.AppCompatEditText r8 = r1.etSerialNum
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r8, r6)
        L54:
            r12 = 32
            long r12 = r12 & r2
            int r6 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r6 == 0) goto L79
            androidx.appcompat.widget.AppCompatEditText r6 = r1.etSerialNum
            r8 = 0
            r12 = r8
            androidx.databinding.adapters.TextViewBindingAdapter$BeforeTextChanged r12 = (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged) r12
            r12 = r8
            androidx.databinding.adapters.TextViewBindingAdapter$OnTextChanged r12 = (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged) r12
            r12 = r8
            androidx.databinding.adapters.TextViewBindingAdapter$AfterTextChanged r12 = (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged) r12
            androidx.databinding.InverseBindingListener r12 = r1.etSerialNumandroidTextAttrChanged
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(r6, r8, r8, r8, r12)
            androidx.appcompat.widget.AppCompatEditText r6 = r1.etVerifyCode
            androidx.databinding.InverseBindingListener r12 = r1.etVerifyCodeandroidTextAttrChanged
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(r6, r8, r8, r8, r12)
            androidx.appcompat.widget.AppCompatTextView r6 = r1.mboundView1
            r8 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r6, r8)
        L79:
            if (r16 == 0) goto L80
            androidx.appcompat.widget.AppCompatEditText r6 = r1.etVerifyCode
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r6, r7)
        L80:
            if (r18 == 0) goto L87
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r6 = r1.mboundView0
            r6.setTitle(r9)
        L87:
            long r2 = r2 & r10
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L91
            android.widget.RelativeLayout r2 = r1.serialEditTextContainer
            r2.setVisibility(r0)
        L91:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.mboundView0
            executeBindingsOn(r0)
            return
        L97:
            r0 = move-exception
            monitor-exit(r19)     // Catch: java.lang.Throwable -> L97
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActCameraInfoBindingImpl.executeBindings():void");
    }
}