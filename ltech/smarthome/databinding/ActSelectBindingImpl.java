package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActSelectBindingImpl extends ActSelectBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(13);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{4}, new int[]{R.layout.layout_title_default});
        includedLayouts.setIncludes(1, new String[]{"item_search_bar"}, new int[]{5}, new int[]{R.layout.item_search_bar});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.tv_tip, 6);
        sparseIntArray.put(R.id.scrollView, 7);
        sparseIntArray.put(R.id.rv_content, 8);
        sparseIntArray.put(R.id.view_sync, 9);
        sparseIntArray.put(R.id.iv_sync, 10);
        sparseIntArray.put(R.id.tv_sync, 11);
        sparseIntArray.put(R.id.group_sync, 12);
    }

    public ActSelectBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }

    private ActSelectBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (Group) bindings[12], (AppCompatImageView) bindings[10], (ConstraintLayout) bindings[0], (FrameLayout) bindings[1], (RecyclerView) bindings[8], (NestedScrollView) bindings[7], (ItemSearchBarBinding) bindings[5], (LayoutTitleDefaultBinding) bindings[4], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[11], (AppCompatTextView) bindings[6], (View) bindings[9]);
        this.mDirtyFlags = -1L;
        this.layoutRoot.setTag(null);
        this.layoutSearch.setTag(null);
        setContainedBinding(this.searchBar);
        setContainedBinding(this.title);
        this.tvBottom.setTag(null);
        this.tvBottomTip.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32L;
        }
        this.title.invalidateAll();
        this.searchBar.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings() || this.searchBar.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (5 == variableId) {
            setBottomText((String) variable);
            return true;
        }
        if (6 == variableId) {
            setBottomTip((String) variable);
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

    @Override // com.ltech.smarthome.databinding.ActSelectBinding
    public void setBottomText(String BottomText) {
        this.mBottomText = BottomText;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(5);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectBinding
    public void setBottomTip(String BottomTip) {
        this.mBottomTip = BottomTip;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectBinding
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
        this.title.setLifecycleOwner(lifecycleOwner);
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

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0048, code lost:
    
        if (r17 != false) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008a  */
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
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> L9f
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> L9f
            monitor-exit(r20)     // Catch: java.lang.Throwable -> L9f
            java.lang.String r0 = r1.mBottomText
            java.lang.String r6 = r1.mBottomTip
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r7 = r1.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r8 = r1.mTitle
            r9 = 34
            long r11 = r2 & r9
            r13 = 8
            r14 = 0
            int r15 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r15 == 0) goto L30
            boolean r11 = android.text.TextUtils.isEmpty(r0)
            if (r15 == 0) goto L2b
            if (r11 == 0) goto L28
            r15 = 128(0x80, double:6.32E-322)
            goto L2a
        L28:
            r15 = 64
        L2a:
            long r2 = r2 | r15
        L2b:
            if (r11 == 0) goto L30
            r11 = 8
            goto L31
        L30:
            r11 = 0
        L31:
            r15 = 36
            long r17 = r2 & r15
            int r12 = (r17 > r4 ? 1 : (r17 == r4 ? 0 : -1))
            if (r12 == 0) goto L4b
            boolean r17 = android.text.TextUtils.isEmpty(r6)
            if (r12 == 0) goto L48
            if (r17 == 0) goto L44
            r18 = 512(0x200, double:2.53E-321)
            goto L46
        L44:
            r18 = 256(0x100, double:1.265E-321)
        L46:
            long r2 = r2 | r18
        L48:
            if (r17 == 0) goto L4b
            goto L4c
        L4b:
            r13 = 0
        L4c:
            r17 = 40
            long r17 = r2 & r17
            int r12 = (r17 > r4 ? 1 : (r17 == r4 ? 0 : -1))
            r17 = 48
            long r17 = r2 & r17
            int r19 = (r17 > r4 ? 1 : (r17 == r4 ? 0 : -1))
            r17 = r4
            if (r19 == 0) goto L61
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r4 = r1.title
            r4.setTitle(r8)
        L61:
            long r4 = r2 & r15
            int r8 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r8 == 0) goto L71
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvBottom
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r4, r6)
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvBottom
            r4.setVisibility(r13)
        L71:
            r4 = 32
            long r4 = r4 & r2
            int r6 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r6 == 0) goto L7e
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvBottom
            r5 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r4, r5)
        L7e:
            if (r12 == 0) goto L85
            androidx.appcompat.widget.AppCompatTextView r4 = r1.tvBottom
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r7, r14)
        L85:
            long r2 = r2 & r9
            int r4 = (r2 > r17 ? 1 : (r2 == r17 ? 0 : -1))
            if (r4 == 0) goto L94
            androidx.appcompat.widget.AppCompatTextView r2 = r1.tvBottomTip
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r2, r0)
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvBottomTip
            r0.setVisibility(r11)
        L94:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.title
            executeBindingsOn(r0)
            com.ltech.smarthome.databinding.ItemSearchBarBinding r0 = r1.searchBar
            executeBindingsOn(r0)
            return
        L9f:
            r0 = move-exception
            monitor-exit(r20)     // Catch: java.lang.Throwable -> L9f
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSelectBindingImpl.executeBindings():void");
    }
}