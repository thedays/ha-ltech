package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public class ActSelect3BindingImpl extends ActSelect3Binding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(38);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default", "view_device_manage_bottom"}, new int[]{8, 10}, new int[]{R.layout.layout_title_default, R.layout.view_device_manage_bottom});
        includedLayouts.setIncludes(1, new String[]{"item_search_bar"}, new int[]{9}, new int[]{R.layout.item_search_bar});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.layout_tab, 11);
        sparseIntArray.put(R.id.v_title_bg, 12);
        sparseIntArray.put(R.id.iv_search, 13);
        sparseIntArray.put(R.id.tab_title, 14);
        sparseIntArray.put(R.id.layout_floor, 15);
        sparseIntArray.put(R.id.tabs, 16);
        sparseIntArray.put(R.id.layout_sort, 17);
        sparseIntArray.put(R.id.layout_sort_and_type, 18);
        sparseIntArray.put(R.id.radio_select_type, 19);
        sparseIntArray.put(R.id.radio_all, 20);
        sparseIntArray.put(R.id.radio_other, 21);
        sparseIntArray.put(R.id.radio_panel, 22);
        sparseIntArray.put(R.id.radio_light, 23);
        sparseIntArray.put(R.id.layout_scene_sort_and_type, 24);
        sparseIntArray.put(R.id.radio_scene_select_type, 25);
        sparseIntArray.put(R.id.radio_scene_all, 26);
        sparseIntArray.put(R.id.radio_local, 27);
        sparseIntArray.put(R.id.radio_cloud, 28);
        sparseIntArray.put(R.id.radio_dali, 29);
        sparseIntArray.put(R.id.layout_dali_type, 30);
        sparseIntArray.put(R.id.radio_dali_type, 31);
        sparseIntArray.put(R.id.radio_scene, 32);
        sparseIntArray.put(R.id.radio_group, 33);
        sparseIntArray.put(R.id.radio_add, 34);
        sparseIntArray.put(R.id.head_view, 35);
        sparseIntArray.put(R.id.viewpager, 36);
        sparseIntArray.put(R.id.footer_view, 37);
    }

    public ActSelect3BindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 38, sIncludes, sViewsWithIds));
    }

    private ActSelect3BindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2, (ViewDeviceManageBottomBinding) bindings[10], (RelativeLayout) bindings[37], (RelativeLayout) bindings[35], (AppCompatImageView) bindings[2], (AppCompatImageView) bindings[13], (LinearLayout) bindings[30], (LinearLayout) bindings[15], (LinearLayout) bindings[24], (FrameLayout) bindings[1], (LinearLayout) bindings[17], (LinearLayout) bindings[18], (ConstraintLayout) bindings[11], (RadioButton) bindings[34], (RadioButton) bindings[20], (RadioButton) bindings[28], (RadioButton) bindings[29], (RadioGroup) bindings[31], (RadioButton) bindings[33], (RadioButton) bindings[23], (RadioButton) bindings[27], (RadioButton) bindings[21], (RadioButton) bindings[22], (RadioButton) bindings[32], (RadioButton) bindings[26], (RadioGroup) bindings[25], (RadioGroup) bindings[19], (ItemSearchBarBinding) bindings[9], (TabLayout) bindings[14], (TabLayout) bindings[16], (LayoutTitleDefaultBinding) bindings[8], (AppCompatTextView) bindings[3], (AppCompatTextView) bindings[4], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[5], (AppCompatTextView) bindings[6], (View) bindings[12], (ViewPager2) bindings[36]);
        this.mDirtyFlags = -1L;
        setContainedBinding(this.bottom);
        this.ivBack.setTag(null);
        this.layoutSearch.setTag(null);
        LinearLayout linearLayout = (LinearLayout) bindings[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        setContainedBinding(this.searchBar);
        setContainedBinding(this.title);
        this.tvEdit.setTag(null);
        this.tvFloorContent.setTag(null);
        this.tvSceneSort1.setTag(null);
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
        this.bottom.invalidateAll();
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return this.title.hasPendingBindings() || this.searchBar.hasPendingBindings() || this.bottom.hasPendingBindings();
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int variableId, Object variable) {
        if (84 == variableId) {
            setTitleGone((Boolean) variable);
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

    @Override // com.ltech.smarthome.databinding.ActSelect3Binding
    public void setTitleGone(Boolean TitleGone) {
        this.mTitleGone = TitleGone;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(84);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelect3Binding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelect3Binding
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
        this.bottom.setLifecycleOwner(lifecycleOwner);
    }

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        if (localFieldId == 0) {
            return onChangeSearchBar((ItemSearchBarBinding) object, fieldId);
        }
        if (localFieldId != 1) {
            return false;
        }
        return onChangeBottom((ViewDeviceManageBottomBinding) object, fieldId);
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

    private boolean onChangeBottom(ViewDeviceManageBottomBinding Bottom, int fieldId) {
        if (fieldId != 0) {
            return false;
        }
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0072  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r14 = this;
            monitor-enter(r14)
            long r0 = r14.mDirtyFlags     // Catch: java.lang.Throwable -> L8d
            r2 = 0
            r14.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L8d
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L8d
            java.lang.Boolean r4 = r14.mTitleGone
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r5 = r14.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r6 = r14.mTitle
            r7 = 36
            long r9 = r0 & r7
            r11 = 0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L2b
            boolean r4 = androidx.databinding.ViewDataBinding.safeUnbox(r4)
            if (r12 == 0) goto L25
            if (r4 != 0) goto L22
            r9 = 128(0x80, double:6.32E-322)
            goto L24
        L22:
            r9 = 64
        L24:
            long r0 = r0 | r9
        L25:
            if (r4 != 0) goto L28
            goto L2b
        L28:
            r4 = 8
            goto L2c
        L2b:
            r4 = 0
        L2c:
            r9 = 40
            long r9 = r9 & r0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            r9 = 48
            long r9 = r9 & r0
            int r13 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L56
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivBack
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvEdit
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvFloorContent
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvSceneSort1
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvSort
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvSort1
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
        L56:
            long r7 = r7 & r0
            int r5 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r5 == 0) goto L64
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r5 = r14.title
            android.view.View r5 = r5.getRoot()
            r5.setVisibility(r4)
        L64:
            if (r13 == 0) goto L6b
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r4 = r14.title
            r4.setTitle(r6)
        L6b:
            r4 = 32
            long r0 = r0 & r4
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L7d
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvEdit
            r1 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r14.tvFloorContent
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
        L7d:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            executeBindingsOn(r0)
            com.ltech.smarthome.databinding.ItemSearchBarBinding r0 = r14.searchBar
            executeBindingsOn(r0)
            com.ltech.smarthome.databinding.ViewDeviceManageBottomBinding r0 = r14.bottom
            executeBindingsOn(r0)
            return
        L8d:
            r0 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L8d
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSelect3BindingImpl.executeBindings():void");
    }
}