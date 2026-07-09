package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.ui.device.light.ActAddMusicVM;

/* loaded from: classes3.dex */
public class ItemMusicBindingImpl extends ItemMusicBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.v_delete, 4);
        sparseIntArray.put(R.id.iv_delete, 5);
    }

    public ItemMusicBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }

    private ItemMusicBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[3], (ConstraintLayout) bindings[0], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[1], (View) bindings[4]);
        this.mDirtyFlags = -1L;
        this.ivSelect.setTag(null);
        this.layoutBg.setTag(null);
        this.tvArtist.setTag(null);
        this.tvMusicName.setTag(null);
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
        if (40 == variableId) {
            setItem((ActAddMusicVM.ItemMusic) variable);
            return true;
        }
        if (68 == variableId) {
            setSelectRes((Integer) variable);
            return true;
        }
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (21 != variableId) {
            return false;
        }
        setDefaultRes((Integer) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ItemMusicBinding
    public void setItem(ActAddMusicVM.ItemMusic Item) {
        this.mItem = Item;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(40);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMusicBinding
    public void setSelectRes(Integer SelectRes) {
        this.mSelectRes = SelectRes;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(68);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMusicBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ItemMusicBinding
    public void setDefaultRes(Integer DefaultRes) {
        this.mDefaultRes = DefaultRes;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(21);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeItemSelect((MutableLiveData) object, fieldId);
    }

    private boolean onChangeItemSelect(MutableLiveData<Boolean> ItemSelect, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x003f  */
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
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> La8
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> La8
            monitor-exit(r19)     // Catch: java.lang.Throwable -> La8
            com.ltech.smarthome.ui.device.light.ActAddMusicVM$ItemMusic r0 = r1.mItem
            java.lang.Integer r6 = r1.mSelectRes
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r7 = r1.mClickCommand
            java.lang.Integer r8 = r1.mDefaultRes
            r9 = 55
            long r11 = r2 & r9
            r13 = 34
            r15 = 0
            r16 = 0
            int r17 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r17 == 0) goto L5e
            long r11 = r2 & r13
            int r18 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r18 == 0) goto L37
            if (r0 == 0) goto L2a
            com.ltech.smarthome.preference_bean.MusicBean r11 = r0.musicBean
            goto L2c
        L2a:
            r11 = r16
        L2c:
            if (r11 == 0) goto L37
            java.lang.String r12 = r11.getArtist()
            java.lang.String r11 = r11.getTitle()
            goto L3a
        L37:
            r11 = r16
            r12 = r11
        L3a:
            if (r0 == 0) goto L3f
            androidx.lifecycle.MutableLiveData<java.lang.Boolean> r0 = r0.select
            goto L41
        L3f:
            r0 = r16
        L41:
            r1.updateLiveDataRegistration(r15, r0)
            if (r0 == 0) goto L4e
            java.lang.Object r0 = r0.getValue()
            r16 = r0
            java.lang.Boolean r16 = (java.lang.Boolean) r16
        L4e:
            boolean r0 = androidx.databinding.ViewDataBinding.safeUnbox(r16)
            if (r17 == 0) goto L62
            if (r0 == 0) goto L59
            r16 = 128(0x80, double:6.32E-322)
            goto L5b
        L59:
            r16 = 64
        L5b:
            long r2 = r2 | r16
            goto L62
        L5e:
            r11 = r16
            r12 = r11
            r0 = 0
        L62:
            r16 = 40
            long r16 = r2 & r16
            int r18 = (r16 > r4 ? 1 : (r16 == r4 ? 0 : -1))
            long r9 = r9 & r2
            int r16 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r16 == 0) goto L76
            if (r0 == 0) goto L70
            goto L71
        L70:
            r6 = r8
        L71:
            int r0 = androidx.databinding.ViewDataBinding.safeUnbox(r6)
            goto L77
        L76:
            r0 = 0
        L77:
            if (r16 == 0) goto L7e
            androidx.appcompat.widget.AppCompatImageView r6 = r1.ivSelect
            com.ltech.smarthome.binding.view.ViewAdapter.setBackground(r6, r0)
        L7e:
            if (r18 == 0) goto L85
            androidx.constraintlayout.widget.ConstraintLayout r0 = r1.layoutBg
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r7, r15)
        L85:
            long r6 = r2 & r13
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 == 0) goto L95
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvArtist
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r12)
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvMusicName
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r11)
        L95:
            r6 = 32
            long r2 = r2 & r6
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto La7
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvArtist
            r2 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r2)
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvMusicName
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r2)
        La7:
            return
        La8:
            r0 = move-exception
            monitor-exit(r19)     // Catch: java.lang.Throwable -> La8
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ItemMusicBindingImpl.executeBindings():void");
    }
}