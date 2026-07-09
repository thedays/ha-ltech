package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActSelectSceneAllBindingImpl extends ActSelectSceneAllBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(10);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(4, new String[]{"item_search_bar"}, new int[]{6}, new int[]{R.layout.item_search_bar});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.v_title_bg, 7);
        sparseIntArray.put(R.id.tabs, 8);
        sparseIntArray.put(R.id.rv_content, 9);
    }

    public ActSelectSceneAllBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }

    private ActSelectSceneAllBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (AppCompatImageView) bindings[1], (FrameLayout) bindings[4], (RecyclerView) bindings[9], (ItemSearchBarBinding) bindings[6], (TabLayout) bindings[8], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[2], (View) bindings[7]);
        this.mDirtyFlags = -1L;
        this.ivBack.setTag(null);
        this.layoutSearch.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.searchBar);
        this.title.setTag(null);
        this.tvBottom.setTag(null);
        this.tvEdit.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16L;
        }
        this.searchBar.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.searchBar.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (10 == variableId) {
            setClickCommand((BindingCommand) variable);
            return true;
        }
        if (6 == variableId) {
            setBottomTip((String) variable);
            return true;
        }
        if (83 != variableId) {
            return false;
        }
        setTitle((TitleDefault) variable);
        return true;
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSceneAllBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSceneAllBinding
    public void setBottomTip(String BottomTip) {
        this.mBottomTip = BottomTip;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSceneAllBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.searchBar.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId != 0) {
            return false;
        }
        return onChangeSearchBar((ItemSearchBarBinding) object, fieldId);
    }

    private boolean onChangeSearchBar(ItemSearchBarBinding SearchBar, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0060  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r13 = this;
            monitor-enter(r13)
            long r0 = r13.mDirtyFlags     // Catch: java.lang.Throwable -> L70
            r2 = 0
            r13.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L70
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L70
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r4 = r13.mClickCommand
            java.lang.String r5 = r13.mBottomTip
            r6 = 20
            long r8 = r0 & r6
            r10 = 0
            int r11 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r11 == 0) goto L28
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r11 == 0) goto L23
            if (r8 == 0) goto L20
            r11 = 64
            goto L22
        L20:
            r11 = 32
        L22:
            long r0 = r0 | r11
        L23:
            if (r8 == 0) goto L28
            r8 = 8
            goto L29
        L28:
            r8 = 0
        L29:
            r11 = 18
            long r11 = r11 & r0
            int r9 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r9 == 0) goto L44
            androidx.appcompat.widget.AppCompatImageView r9 = r13.ivBack
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r10)
            androidx.appcompat.widget.AppCompatTextView r9 = r13.title
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r10)
            androidx.appcompat.widget.AppCompatTextView r9 = r13.tvBottom
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r10)
            androidx.appcompat.widget.AppCompatTextView r9 = r13.tvEdit
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r4, r10)
        L44:
            r9 = 16
            long r9 = r9 & r0
            int r4 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r4 == 0) goto L5b
            androidx.appcompat.widget.AppCompatTextView r4 = r13.title
            r9 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r4, r9)
            androidx.appcompat.widget.AppCompatTextView r4 = r13.tvBottom
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r4, r9)
            androidx.appcompat.widget.AppCompatTextView r4 = r13.tvEdit
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r4, r9)
        L5b:
            long r0 = r0 & r6
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L6a
            androidx.appcompat.widget.AppCompatTextView r0 = r13.tvBottom
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r5)
            androidx.appcompat.widget.AppCompatTextView r0 = r13.tvBottom
            r0.setVisibility(r8)
        L6a:
            com.ltech.smarthome.databinding.ItemSearchBarBinding r0 = r13.searchBar
            executeBindingsOn(r0)
            return
        L70:
            r0 = move-exception
            monitor-exit(r13)     // Catch: java.lang.Throwable -> L70
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSelectSceneAllBindingImpl.executeBindings():void");
    }
}