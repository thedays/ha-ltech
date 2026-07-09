package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActSelectSceneBindingImpl extends ActSelectSceneBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(19);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{6}, new int[]{R.layout.layout_title_default});
        includedLayouts.setIncludes(1, new String[]{"item_search_bar"}, new int[]{7}, new int[]{R.layout.item_search_bar});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_floor, 8);
        sparseIntArray.put(R.id.tabs, 9);
        sparseIntArray.put(R.id.layout_sort, 10);
        sparseIntArray.put(R.id.layout_sort_and_type, 11);
        sparseIntArray.put(R.id.ll_shadow, 12);
        sparseIntArray.put(R.id.radio_select_type, 13);
        sparseIntArray.put(R.id.radio_all, 14);
        sparseIntArray.put(R.id.radio_local, 15);
        sparseIntArray.put(R.id.radio_cloud, 16);
        sparseIntArray.put(R.id.radio_dali, 17);
        sparseIntArray.put(R.id.viewpager, 18);
    }

    public ActSelectSceneBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }

    private ActSelectSceneBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1, (LinearLayout) bindings[8], (FrameLayout) bindings[1], (LinearLayout) bindings[10], (RelativeLayout) bindings[11], (LinearLayout) bindings[12], (RadioButton) bindings[14], (RadioButton) bindings[16], (RadioButton) bindings[17], (RadioButton) bindings[15], (RadioGroup) bindings[13], (ItemSearchBarBinding) bindings[7], (TabLayout) bindings[9], (LayoutTitleDefaultBinding) bindings[6], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[4], (ViewPager2) bindings[18]);
        this.mDirtyFlags = -1L;
        this.layoutSearch.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.searchBar);
        setContainedBinding(this.title);
        this.tvBottom.setTag(null);
        this.tvFloorContent.setTag(null);
        this.tvSort.setTag(null);
        this.tvSort1.setTag(null);
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
        if (84 == variableId) {
            setTitleGone((Boolean) variable);
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

    @Override // com.ltech.smarthome.databinding.ActSelectSceneBinding
    public void setTitleGone(Boolean TitleGone) {
        this.mTitleGone = TitleGone;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(84);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSceneBinding
    public void setBottomTip(String BottomTip) {
        this.mBottomTip = BottomTip;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSceneBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSceneBinding
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

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0049, code lost:
    
        if (r15 != false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0090  */
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
            long r2 = r1.mDirtyFlags     // Catch: java.lang.Throwable -> Laa
            r4 = 0
            r1.mDirtyFlags = r4     // Catch: java.lang.Throwable -> Laa
            monitor-exit(r19)     // Catch: java.lang.Throwable -> Laa
            java.lang.Boolean r0 = r1.mTitleGone
            java.lang.String r6 = r1.mBottomTip
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r7 = r1.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r8 = r1.mTitle
            r9 = 34
            long r11 = r2 & r9
            r13 = 8
            r14 = 0
            int r15 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r15 == 0) goto L31
            boolean r0 = androidx.databinding.ViewDataBinding.safeUnbox(r0)
            if (r15 == 0) goto L2b
            if (r0 != 0) goto L28
            r11 = 512(0x200, double:2.53E-321)
            goto L2a
        L28:
            r11 = 256(0x100, double:1.265E-321)
        L2a:
            long r2 = r2 | r11
        L2b:
            if (r0 != 0) goto L2e
            goto L31
        L2e:
            r0 = 8
            goto L32
        L31:
            r0 = 0
        L32:
            r11 = 36
            long r15 = r2 & r11
            int r17 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            if (r17 == 0) goto L4c
            boolean r15 = android.text.TextUtils.isEmpty(r6)
            if (r17 == 0) goto L49
            if (r15 == 0) goto L45
            r16 = 128(0x80, double:6.32E-322)
            goto L47
        L45:
            r16 = 64
        L47:
            long r2 = r2 | r16
        L49:
            if (r15 == 0) goto L4c
            goto L4d
        L4c:
            r13 = 0
        L4d:
            r15 = 40
            long r15 = r15 & r2
            int r17 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            r15 = 48
            long r15 = r15 & r2
            int r18 = (r15 > r4 ? 1 : (r15 == r4 ? 0 : -1))
            long r9 = r9 & r2
            int r15 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r15 == 0) goto L65
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r9 = r1.title
            android.view.View r9 = r9.getRoot()
            r9.setVisibility(r0)
        L65:
            if (r18 == 0) goto L6c
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.title
            r0.setTitle(r8)
        L6c:
            long r8 = r2 & r11
            int r0 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r0 == 0) goto L7c
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvBottom
            androidx.databinding.adapters.TextViewBindingAdapter.setText(r0, r6)
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvBottom
            r0.setVisibility(r13)
        L7c:
            r8 = 32
            long r2 = r2 & r8
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto L8e
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvBottom
            r2 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r2)
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvFloorContent
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r2)
        L8e:
            if (r17 == 0) goto L9f
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvFloorContent
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r7, r14)
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvSort
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r7, r14)
            androidx.appcompat.widget.AppCompatTextView r0 = r1.tvSort1
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r0, r7, r14)
        L9f:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r1.title
            executeBindingsOn(r0)
            com.ltech.smarthome.databinding.ItemSearchBarBinding r0 = r1.searchBar
            executeBindingsOn(r0)
            return
        Laa:
            r0 = move-exception
            monitor-exit(r19)     // Catch: java.lang.Throwable -> Laa
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSelectSceneBindingImpl.executeBindings():void");
    }
}