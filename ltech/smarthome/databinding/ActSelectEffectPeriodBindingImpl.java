package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.automation.ActSelectEffectPeriodVM;

/* loaded from: classes3.dex */
public class ActSelectEffectPeriodBindingImpl extends ActSelectEffectPeriodBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(13);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{6}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.view3, 7);
        sparseIntArray.put(R.id.tv_start_time_tip, 8);
        sparseIntArray.put(R.id.tv_end_time_tip, 9);
        sparseIntArray.put(R.id.view22, 10);
        sparseIntArray.put(R.id.appCompatImageView17, 11);
        sparseIntArray.put(R.id.tv_repeat_time, 12);
    }

    public ActSelectEffectPeriodBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActSelectEffectPeriodBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatImageView) bindings[11], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[3], (LayoutTitleDefaultBinding) bindings[6], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[9], (AppCompatTextView) bindings[12], (AppCompatTextView) bindings[1], (AppCompatTextView) bindings[8], (View) bindings[4], (View) bindings[10], (View) bindings[7]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView24.setTag(null);
        this.date.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvEndTime.setTag(null);
        this.tvStartTime.setTag(null);
        this.vSelectTime.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        setViewmodel((ActSelectEffectPeriodVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSelectEffectPeriodBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectEffectPeriodBinding
    public void setViewmodel(ActSelectEffectPeriodVM Viewmodel) {
        this.mViewmodel = Viewmodel;
        synchronized (this) {
            this.mDirtyFlags |= 8;
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
            return onChangeViewmodelEndTime((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelStartTime((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelEndTime(MutableLiveData<String> ViewmodelEndTime, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelStartTime(MutableLiveData<String> ViewmodelStartTime, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0050  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r22 = this;
            r1 = r22
            monitor-enter(r22)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lb8
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lb8
            monitor-exit(r22)     // Catch: java.lang.Throwable -> Lb8
            com.ltech.smarthome.model.bean.TitleDefault r0 = r1.mTitle
            com.ltech.smarthome.ui.automation.ActSelectEffectPeriodVM r6 = r1.mViewmodel
            r7 = 20
            long r7 = r7 & r2
            r9 = 26
            r11 = 25
            r13 = 24
            r15 = 0
            r16 = r4
            r4 = 1
            r5 = 0
            int r18 = (r7 > r16 ? 1 : (r7 == r16 ? 0 : -1))
            r7 = 27
            long r7 = r7 & r2
            int r19 = (r7 > r16 ? 1 : (r7 == r16 ? 0 : -1))
            if (r19 == 0) goto L64
            long r7 = r2 & r13
            int r19 = (r7 > r16 ? 1 : (r7 == r16 ? 0 : -1))
            if (r19 == 0) goto L30
            if (r6 == 0) goto L30
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r7 = r6.clickCommand
            goto L31
        L30:
            r7 = r5
        L31:
            long r19 = r2 & r11
            int r8 = (r19 > r16 ? 1 : (r19 == r16 ? 0 : -1))
            if (r8 == 0) goto L49
            if (r6 == 0) goto L3c
            androidx.lifecycle.MutableLiveData<java.lang.String> r8 = r6.endTime
            goto L3d
        L3c:
            r8 = r5
        L3d:
            r1.updateLiveDataRegistration(r15, r8)
            if (r8 == 0) goto L49
            java.lang.Object r8 = r8.getValue()
            java.lang.String r8 = (java.lang.String) r8
            goto L4a
        L49:
            r8 = r5
        L4a:
            long r19 = r2 & r9
            int r21 = (r19 > r16 ? 1 : (r19 == r16 ? 0 : -1))
            if (r21 == 0) goto L61
            if (r6 == 0) goto L55
            androidx.lifecycle.MutableLiveData<java.lang.String> r6 = r6.startTime
            goto L56
        L55:
            r6 = r5
        L56:
            r1.updateLiveDataRegistration(r4, r6)
            if (r6 == 0) goto L61
            java.lang.Object r5 = r6.getValue()
            java.lang.String r5 = (java.lang.String) r5
        L61:
            r6 = r5
            r5 = r8
            goto L66
        L64:
            r6 = r5
            r7 = r6
        L66:
            r19 = 16
            long r19 = r2 & r19
            int r8 = (r19 > r16 ? 1 : (r19 == r16 ? 0 : -1))
            if (r8 == 0) goto L82
            androidx.appcompat.widget.AppCompatTextView r8 = r1.appCompatTextView24
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r8, r4)
            androidx.appcompat.widget.AppCompatTextView r8 = r1.date
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r8, r4)
            androidx.appcompat.widget.AppCompatTextView r8 = r1.tvEndTime
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r8, r4)
            androidx.appcompat.widget.AppCompatTextView r8 = r1.tvStartTime
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r8, r4)
        L82:
            if (r18 == 0) goto L89
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r4 = r1.title
            r4.setTitle(r0)
        L89:
            long r11 = r11 & r2
            int r0 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1))
            if (r0 == 0) goto L93
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvEndTime
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r5)
        L93:
            long r4 = r2 & r13
            int r0 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r0 == 0) goto La8
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvEndTime
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r7, r15)
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvStartTime
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r7, r15)
            android.view.View r0 = r1.vSelectTime
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r7, r15)
        La8:
            long r2 = r2 & r9
            int r0 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r0 == 0) goto Lb2
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvStartTime
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r6)
        Lb2:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.title
            executeBindingsOn(r0)
            return
        Lb8:
            r0 = move-exception
            monitor-exit(r22)     // Catch: java.lang.Throwable -> Lb8
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSelectEffectPeriodBindingImpl.executeBindings():void");
    }
}