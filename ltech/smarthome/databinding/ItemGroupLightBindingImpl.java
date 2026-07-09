package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.Group;
import com.ltech.smarthome.view.SmartSwitch;

/* loaded from: classes3.dex */
public class ItemGroupLightBindingImpl extends ItemGroupLightBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final RelativeLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.bg, 7);
        sparseIntArray.put(R.id.tv_virtual, 8);
        sparseIntArray.put(R.id.appCompatTextView18, 9);
        sparseIntArray.put(R.id.appCompatTextView17, 10);
        sparseIntArray.put(R.id.appCompatImageView9, 11);
        sparseIntArray.put(R.id.view10, 12);
    }

    public ItemGroupLightBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ItemGroupLightBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[11], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[10], (AppCompatTextView) bindings[9], (LinearLayoutCompat) bindings[7], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[1], (SmartSwitch) bindings[6], (AppCompatTextView) bindings[8], (View) bindings[2], (View) bindings[12]);
        this.mDirtyFlags = -1L;
        this.appCompatTextView15.setTag(null);
        this.ivDeviceMore.setTag(null);
        this.ivFavorite.setTag(null);
        this.layoutItemBg.setTag(null);
        RelativeLayout relativeLayout = (RelativeLayout) bindings[0];
        this.mboundView0 = relativeLayout;
        relativeLayout.setTag(null);
        this.sb.setTag(null);
        this.vFavorite.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
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
        if (37 == variableId) {
            setIconRes((Integer) variable);
            return true;
        }
        if (32 == variableId) {
            setGroup((Group) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (34 == variableId) {
            setHideMore((Boolean) variable);
            return true;
        }
        if (8 != variableId) {
            return false;
        }
        setCheckedChangeListener((SmartSwitch.OnUserCheckedChangeListener) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupLightBinding
    public void setIconRes(Integer IconRes) {
        this.mIconRes = IconRes;
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupLightBinding
    public void setGroup(Group Group) {
        this.mGroup = Group;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(32);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupLightBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupLightBinding
    public void setHideMore(Boolean HideMore) {
        this.mHideMore = HideMore;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(34);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemGroupLightBinding
    public void setCheckedChangeListener(SmartSwitch.OnUserCheckedChangeListener CheckedChangeListener) {
        this.mCheckedChangeListener = CheckedChangeListener;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(8);
        super.requestRebind();
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
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
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Lbd
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Lbd
            monitor-exit(r20)     // Catch: java.lang.Throwable -> Lbd
            com.ltech.smarthome.model.bean.Group r0 = r1.mGroup
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r6 = r1.mClickCommand
            java.lang.Boolean r7 = r1.mHideMore
            com.ltech.smarthome.view.SmartSwitch$OnUserCheckedChangeListener r8 = r1.mCheckedChangeListener
            r9 = 34
            long r11 = r2 & r9
            r13 = 0
            r14 = 0
            int r15 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r15 == 0) goto L4f
            if (r0 == 0) goto L2c
            java.lang.String r13 = r0.getGroupName()
            com.ltech.smarthome.model.bean.DeviceState r0 = r0.getGroupState()
            r19 = r13
            r13 = r0
            r0 = r19
            goto L2d
        L2c:
            r0 = r13
        L2d:
            if (r13 == 0) goto L38
            boolean r11 = r13.isOn()
            boolean r12 = r13.isFavorite()
            goto L3a
        L38:
            r11 = 0
            r12 = 0
        L3a:
            if (r15 == 0) goto L44
            if (r12 == 0) goto L41
            r15 = 128(0x80, double:6.32E-322)
            goto L43
        L41:
            r15 = 64
        L43:
            long r2 = r2 | r15
        L44:
            if (r12 == 0) goto L4a
            r12 = 2131624382(0x7f0e01be, float:1.8875942E38)
            goto L4d
        L4a:
            r12 = 2131624383(0x7f0e01bf, float:1.8875944E38)
        L4d:
            r13 = r0
            goto L51
        L4f:
            r11 = 0
            r12 = 0
        L51:
            r15 = 40
            long r17 = r2 & r15
            int r0 = (r17 > r4 ? 1 : (r17 == r4 ? 0 : -1))
            if (r0 == 0) goto L6c
            boolean r7 = androidx.databinding.ViewDataBinding.safeUnbox(r7)
            if (r0 == 0) goto L68
            if (r7 == 0) goto L64
            r17 = 512(0x200, double:2.53E-321)
            goto L66
        L64:
            r17 = 256(0x100, double:1.265E-321)
        L66:
            long r2 = r2 | r17
        L68:
            if (r7 == 0) goto L6c
            r0 = 4
            goto L6d
        L6c:
            r0 = 0
        L6d:
            r17 = 48
            long r17 = r2 & r17
            int r7 = (r17 > r4 ? 1 : (r17 == r4 ? 0 : -1))
            long r9 = r9 & r2
            int r17 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r17 == 0) goto L87
            androidx.appcompat.widget.AppCompatTextView r9 = r1.appCompatTextView15
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r9, r13)
            androidx.appcompat.widget.AppCompatImageView r9 = r1.ivFavorite
            com.ltech.smarthome.binding.view.ViewAdapter.setBackground(r9, r12)
            com.ltech.smarthome.view.SmartSwitch r9 = r1.sb
            androidx.databinding.adapters.CompoundButtonBindingAdapter.setChecked(r9, r11)
        L87:
            r9 = 32
            long r9 = r9 & r2
            int r11 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r11 == 0) goto L94
            androidx.appcompat.widget.AppCompatTextView r9 = r1.appCompatTextView15
            r10 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r9, r10)
        L94:
            long r9 = r2 & r15
            int r11 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r11 == 0) goto L9f
            androidx.appcompat.widget.AppCompatImageView r9 = r1.ivDeviceMore
            r9.setVisibility(r0)
        L9f:
            r9 = 36
            long r2 = r2 & r9
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto Lb5
            androidx.appcompat.widget.AppCompatImageView r0 = r1.ivDeviceMore
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r6, r14)
            androidx.constraintlayout.widget.ConstraintLayout r0 = r1.layoutItemBg
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r6, r14)
            android.view.View r0 = r1.vFavorite
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r6, r14)
        Lb5:
            if (r7 == 0) goto Lbc
            com.ltech.smarthome.view.SmartSwitch r0 = r1.sb
            com.ltech.smarthome.binding.view.ViewAdapter.setOnUserCheckedChangeListener(r0, r8)
        Lbc:
            return
        Lbd:
            r0 = move-exception
            monitor-exit(r20)     // Catch: java.lang.Throwable -> Lbd
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ItemGroupLightBindingImpl.executeBindings():void");
    }
}