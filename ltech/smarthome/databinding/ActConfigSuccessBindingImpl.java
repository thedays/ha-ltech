package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.config.ActConfigSuccessVM;

/* loaded from: classes3.dex */
public class ActConfigSuccessBindingImpl extends ActConfigSuccessBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LayoutTitleDefaultBinding mboundView0;
    private final LinearLayout mboundView01;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(15);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{9}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.appCompatTextView10, 10);
        sparseIntArray.put(R.id.appCompatImageView4, 11);
        sparseIntArray.put(R.id.appCompatImageView5, 12);
        sparseIntArray.put(R.id.appCompatImageView6, 13);
        sparseIntArray.put(R.id.group_set_room, 14);
    }

    public ActConfigSuccessBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }

    private ActConfigSuccessBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[12], (AppCompatImageView) bindings[13], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[6], (AppCompatTextView) bindings[1], (AppCompatButton) bindings[8], (Group) bindings[14], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[7], (View) bindings[2], (View) bindings[3]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView13.setTag(null);
        this.appCompatTextView14.setTag(null);
        this.appCompatTextView9.setTag(null);
        this.btNext.setTag(null);
        LayoutTitleDefaultBinding layoutTitleDefaultBinding = (LayoutTitleDefaultBinding) bindings[9];
        this.mboundView0 = layoutTitleDefaultBinding;
        setContainedBinding(layoutTitleDefaultBinding);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView01 = linearLayout;
        linearLayout.setTag(null);
        this.tvDeviceName.setTag(null);
        this.tvRoom.setTag(null);
        this.vDeviceName.setTag(null);
        this.vOwnRoom.setTag(null);
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
        if (83 == variableId) {
            setTitle((TitleDefault) variable);
            return true;
        }
        if (92 != variableId) {
            return false;
        }
        setViewmodel((ActConfigSuccessVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActConfigSuccessBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActConfigSuccessBinding
    public void setViewmodel(ActConfigSuccessVM Viewmodel) {
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
        this.mboundView0.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeViewmodelDeviceName((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelRoomName((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelDeviceName(MutableLiveData<String> ViewmodelDeviceName, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelRoomName(MutableLiveData<String> ViewmodelRoomName, int fieldId) {
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
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lb3
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lb3
            monitor-exit(r22)     // Catch: java.lang.Throwable -> Lb3
            com.ltech.smarthome.model.bean.TitleDefault r0 = r1.mTitle
            com.ltech.smarthome.ui.config.ActConfigSuccessVM r6 = r1.mViewmodel
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
            androidx.lifecycle.MutableLiveData<java.lang.String> r8 = r6.deviceName
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
            androidx.lifecycle.MutableLiveData<java.lang.String> r6 = r6.roomName
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
            r5 = r7
            goto L66
        L64:
            r6 = r5
            r8 = r6
        L66:
            r19 = 16
            long r19 = r2 & r19
            int r7 = (r19 > r16 ? 1 : (r19 == r16 ? 0 : -1))
            if (r7 == 0) goto L7d
            androidx.appcompat.widget.AppCompatTextView r7 = r1.appCompatTextView13
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r7, r4)
            androidx.appcompat.widget.AppCompatTextView r7 = r1.appCompatTextView14
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r7, r4)
            androidx.appcompat.widget.AppCompatTextView r7 = r1.appCompatTextView9
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r7, r4)
        L7d:
            long r13 = r13 & r2
            int r4 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r4 == 0) goto L91
            androidx.appcompat.widget.AppCompatButton r4 = r1.btNext
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r15)
            android.view.View r4 = r1.vDeviceName
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r15)
            android.view.View r4 = r1.vOwnRoom
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r15)
        L91:
            if (r18 == 0) goto L98
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r4 = r1.mboundView0
            r4.setTitle(r0)
        L98:
            long r4 = r2 & r11
            int r0 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r0 == 0) goto La3
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvDeviceName
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r8)
        La3:
            long r2 = r2 & r9
            int r0 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r0 == 0) goto Lad
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvRoom
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r6)
        Lad:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.mboundView0
            executeBindingsOn(r0)
            return
        Lb3:
            r0 = move-exception
            monitor-exit(r22)     // Catch: java.lang.Throwable -> Lb3
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActConfigSuccessBindingImpl.executeBindings():void");
    }
}