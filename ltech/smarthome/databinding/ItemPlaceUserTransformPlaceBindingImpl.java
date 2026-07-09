package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Place;
import com.ltech.smarthome.model.bean.PlaceUser;

/* loaded from: classes3.dex */
public class ItemPlaceUserTransformPlaceBindingImpl extends ItemPlaceUserTransformPlaceBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    public ItemPlaceUserTransformPlaceBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }

    private ItemPlaceUserTransformPlaceBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[1]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView6.setTag(null);
        this.appCompatTextView7.setTag(null);
        this.ivGo.setTag(null);
        this.ivIcon.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
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
        if (89 == variableId) {
            setUser((PlaceUser) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (57 == variableId) {
            setPlace((Place) variable);
            return true;
        }
        if (64 != variableId) {
            return false;
        }
        setRole((String) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserTransformPlaceBinding
    public void setUser(PlaceUser User) {
        this.mUser = User;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(89);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserTransformPlaceBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserTransformPlaceBinding
    public void setPlace(Place Place) {
        this.mPlace = Place;
    }

    @Override // com.ltech.smarthome.databinding.ItemPlaceUserTransformPlaceBinding
    public void setRole(String Role) {
        this.mRole = Role;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(64);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
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
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> L82
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> L82
            monitor-exit(r20)     // Catch: java.lang.Throwable -> L82
            com.ltech.smarthome.model.bean.PlaceUser r0 = r1.mUser
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r6 = r1.mClickCommand
            java.lang.String r7 = r1.mRole
            r8 = 17
            long r10 = r2 & r8
            int r12 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r12 == 0) goto L23
            if (r0 == 0) goto L23
            java.lang.String r10 = r0.getUserName()
            java.lang.String r0 = r0.getHeadUrl()
            goto L25
        L23:
            r10 = 0
            r0 = r10
        L25:
            r11 = 18
            long r13 = r2 & r11
            r15 = 1
            r16 = r4
            r4 = 0
            int r5 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r5 == 0) goto L47
            if (r6 == 0) goto L35
            r13 = 1
            goto L36
        L35:
            r13 = 0
        L36:
            if (r5 == 0) goto L41
            if (r13 == 0) goto L3d
            r18 = 64
            goto L3f
        L3d:
            r18 = 32
        L3f:
            long r2 = r2 | r18
        L41:
            if (r13 == 0) goto L44
            goto L47
        L44:
            r5 = 8
            goto L48
        L47:
            r5 = 0
        L48:
            r13 = 24
            long r13 = r13 & r2
            int r18 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            long r8 = r8 & r2
            int r13 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1))
            if (r13 == 0) goto L5f
            androidx.appcompat.widget.AppCompatTextView r8 = r1.appCompatTextView6
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r8, r10)
            androidx.appcompat.widget.AppCompatImageView r8 = r1.ivIcon
            r9 = 2131624509(0x7f0e023d, float:1.88762E38)
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r8, r0, r9, r9, r15)
        L5f:
            r8 = 16
            long r8 = r8 & r2
            int r0 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1))
            if (r0 == 0) goto L6b
            androidx.appcompat.widget.AppCompatTextView r0 = r1.appCompatTextView6
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r15)
        L6b:
            if (r18 == 0) goto L72
            androidx.appcompat.widget.AppCompatTextView r0 = r1.appCompatTextView7
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r7)
        L72:
            long r2 = r2 & r11
            int r0 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1))
            if (r0 == 0) goto L81
            androidx.appcompat.widget.AppCompatImageView r0 = r1.ivGo
            r0.setVisibility(r5)
            androidx.constraintlayout.widget.ConstraintLayout r0 = r1.mboundView0
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r6, r4)
        L81:
            return
        L82:
            r0 = move-exception
            monitor-exit(r20)     // Catch: java.lang.Throwable -> L82
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ItemPlaceUserTransformPlaceBindingImpl.executeBindings():void");
    }
}