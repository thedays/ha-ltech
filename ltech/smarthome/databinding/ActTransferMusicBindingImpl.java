package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM;

/* loaded from: classes3.dex */
public class ActTransferMusicBindingImpl extends ActTransferMusicBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(8);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.bg1, 5);
        sparseIntArray.put(R.id.bg2, 6);
        sparseIntArray.put(R.id.bg3, 7);
    }

    public ActTransferMusicBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }

    private ActTransferMusicBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (View) bindings[5], (View) bindings[6], (View) bindings[7], (AppCompatImageView) bindings[2], (LayoutTitleDefaultBinding) bindings[4], (TextView) bindings[1], (TextView) bindings[3]);
        this.mDirtyFlags = -1L;
        this.ivCopy.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvAddress.setTag(null);
        this.tvSize.setTag(null);
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
        setViewmodel((ActDcaMusicListVM) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActTransferMusicBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActTransferMusicBinding
    public void setViewmodel(ActDcaMusicListVM Viewmodel) {
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
            return onChangeViewmodelIpStr((MutableLiveData) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeViewmodelSizeStr((MutableLiveData) object, fieldId);
    }

    private boolean onChangeViewmodelIpStr(MutableLiveData<String> ViewmodelIpStr, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    private boolean onChangeViewmodelSizeStr(MutableLiveData<String> ViewmodelSizeStr, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0069  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r21 = this;
            r1 = r21
            monitor-enter(r21)
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> La6
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> La6
            monitor-exit(r21)     // Catch: java.lang.Throwable -> La6
            com.ltech.smarthome.model.bean.TitleDefault r0 = r1.mTitle
            com.ltech.smarthome.ui.device.super_panel.music.ActDcaMusicListVM r6 = r1.mViewmodel
            r7 = 20
            long r7 = r7 & r2
            r9 = 26
            r11 = 25
            r13 = 24
            r15 = 0
            r16 = 0
            int r17 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            r7 = 27
            long r7 = r7 & r2
            int r18 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r18 == 0) goto L6e
            long r7 = r2 & r11
            int r18 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r18 == 0) goto L3c
            if (r6 == 0) goto L2e
            androidx.lifecycle.MutableLiveData<java.lang.String> r7 = r6.ipStr
            goto L30
        L2e:
            r7 = r16
        L30:
            r1.updateLiveDataRegistration(r15, r7)
            if (r7 == 0) goto L3c
            java.lang.Object r7 = r7.getValue()
            java.lang.String r7 = (java.lang.String) r7
            goto L3e
        L3c:
            r7 = r16
        L3e:
            long r18 = r2 & r13
            int r8 = (r18 > r4 ? 1 : (r18 == r4 ? 0 : -1))
            if (r8 == 0) goto L49
            if (r6 == 0) goto L49
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r8 = r6.viewClick
            goto L4b
        L49:
            r8 = r16
        L4b:
            long r18 = r2 & r9
            int r20 = (r18 > r4 ? 1 : (r18 == r4 ? 0 : -1))
            if (r20 == 0) goto L69
            if (r6 == 0) goto L56
            androidx.lifecycle.MutableLiveData<java.lang.String> r6 = r6.sizeStr
            goto L58
        L56:
            r6 = r16
        L58:
            r18 = r4
            r4 = 1
            r1.updateLiveDataRegistration(r4, r6)
            if (r6 == 0) goto L6b
            java.lang.Object r4 = r6.getValue()
            r16 = r4
            java.lang.String r16 = (java.lang.String) r16
            goto L6b
        L69:
            r18 = r4
        L6b:
            r4 = r16
            goto L74
        L6e:
            r18 = r4
            r4 = r16
            r7 = r4
            r8 = r7
        L74:
            long r5 = r2 & r13
            int r13 = (r5 > r18 ? 1 : (r5 == r18 ? 0 : -1))
            if (r13 == 0) goto L84
            androidx.appcompat.widget.AppCompatImageView r5 = r1.ivCopy
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r8, r15)
            android.widget.TextView r5 = r1.tvAddress
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r5, r8, r15)
        L84:
            if (r17 == 0) goto L8b
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r5 = r1.title
            r5.setTitle(r0)
        L8b:
            long r5 = r2 & r11
            int r0 = (r5 > r18 ? 1 : (r5 == r18 ? 0 : -1))
            if (r0 == 0) goto L96
            android.widget.TextView r0 = r1.tvAddress
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r7)
        L96:
            long r2 = r2 & r9
            int r0 = (r2 > r18 ? 1 : (r2 == r18 ? 0 : -1))
            if (r0 == 0) goto La0
            android.widget.TextView r0 = r1.tvSize
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r4)
        La0:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.title
            executeBindingsOn(r0)
            return
        La6:
            r0 = move-exception
            monitor-exit(r21)     // Catch: java.lang.Throwable -> La6
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActTransferMusicBindingImpl.executeBindings():void");
    }
}