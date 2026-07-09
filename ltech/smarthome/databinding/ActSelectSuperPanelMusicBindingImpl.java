package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.LightBrtBar;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public class ActSelectSuperPanelMusicBindingImpl extends ActSelectSuperPanelMusicBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final AppCompatTextView mboundView10;
    private final AppCompatTextView mboundView11;
    private final AppCompatTextView mboundView14;
    private final AppCompatTextView mboundView2;
    private final AppCompatTextView mboundView4;
    private final AppCompatTextView mboundView6;
    private final AppCompatTextView mboundView8;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(29);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{15}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.iv_play_start, 16);
        sparseIntArray.put(R.id.tv_local_music, 17);
        sparseIntArray.put(R.id.iv_local_music, 18);
        sparseIntArray.put(R.id.tv_collect, 19);
        sparseIntArray.put(R.id.iv_collect_music, 20);
        sparseIntArray.put(R.id.tv_online_music, 21);
        sparseIntArray.put(R.id.iv_online_music, 22);
        sparseIntArray.put(R.id.iv_play_pause, 23);
        sparseIntArray.put(R.id.layout_volume, 24);
        sparseIntArray.put(R.id.sb_volume, 25);
        sparseIntArray.put(R.id.tv_volume, 26);
        sparseIntArray.put(R.id.layout_loop, 27);
        sparseIntArray.put(R.id.sb_loop_mode, 28);
    }

    public ActSelectSuperPanelMusicBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds));
    }

    private ActSelectSuperPanelMusicBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (AppCompatImageView) bindings[20], (AppCompatImageView) bindings[18], (AppCompatImageView) bindings[22], (AppCompatImageView) bindings[23], (AppCompatImageView) bindings[16], (LinearLayout) bindings[13], (LinearLayout) bindings[5], (LinearLayout) bindings[3], (RelativeLayout) bindings[27], (LinearLayout) bindings[7], (RelativeLayout) bindings[9], (RelativeLayout) bindings[1], (RelativeLayout) bindings[24], (SwitchButton) bindings[12], (SwitchButton) bindings[28], (LightBrtBar) bindings[25], (LayoutTitleDefaultBinding) bindings[15], (AppCompatTextView) bindings[19], (AppCompatTextView) bindings[17], (AppCompatTextView) bindings[21], (AppCompatTextView) bindings[26]);
        this.mDirtyFlags = -1L;
        this.layoutBar.setTag(null);
        this.layoutCollectMusic.setTag(null);
        this.layoutLocalMusic.setTag(null);
        this.layoutOnlineMusic.setTag(null);
        this.layoutPlayPause.setTag(null);
        this.layoutPlayStart.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        AppCompatTextView appCompatTextView = (AppCompatTextView) bindings[10];
        this.mboundView10 = appCompatTextView;
        appCompatTextView.setTag(null);
        AppCompatTextView appCompatTextView2 = (AppCompatTextView) bindings[11];
        this.mboundView11 = appCompatTextView2;
        appCompatTextView2.setTag(null);
        AppCompatTextView appCompatTextView3 = (AppCompatTextView) bindings[14];
        this.mboundView14 = appCompatTextView3;
        appCompatTextView3.setTag(null);
        AppCompatTextView appCompatTextView4 = (AppCompatTextView) bindings[2];
        this.mboundView2 = appCompatTextView4;
        appCompatTextView4.setTag(null);
        AppCompatTextView appCompatTextView5 = (AppCompatTextView) bindings[4];
        this.mboundView4 = appCompatTextView5;
        appCompatTextView5.setTag(null);
        AppCompatTextView appCompatTextView6 = (AppCompatTextView) bindings[6];
        this.mboundView6 = appCompatTextView6;
        appCompatTextView6.setTag(null);
        AppCompatTextView appCompatTextView7 = (AppCompatTextView) bindings[8];
        this.mboundView8 = appCompatTextView7;
        appCompatTextView7.setTag(null);
        this.sbDiyVolume.setTag(null);
        setContainedBinding(this.title);
        setRootTag(root);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8L;
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
        if (26 == variableId) {
            setDiyVolume((Boolean) variable);
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

    @Override // com.ltech.smarthome.databinding.ActSelectSuperPanelMusicBinding
    public void setDiyVolume(Boolean DiyVolume) {
        this.mDiyVolume = DiyVolume;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(26);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSuperPanelMusicBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActSelectSuperPanelMusicBinding
    public void setTitle(TitleDefault Title) {
        this.mTitle = Title;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(83);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        this.title.setLifecycleOwner(lifecycleOwner);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008e  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r15 = this;
            monitor-enter(r15)
            long r0 = r15.mDirtyFlags     // Catch: java.lang.Throwable -> L99
            r2 = 0
            r15.mDirtyFlags = r2     // Catch: java.lang.Throwable -> L99
            monitor-exit(r15)     // Catch: java.lang.Throwable -> L99
            java.lang.Boolean r4 = r15.mDiyVolume
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r5 = r15.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r6 = r15.mTitle
            r7 = 9
            long r9 = r0 & r7
            r11 = 0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L2b
            boolean r4 = androidx.databinding.ViewDataBinding.safeUnbox(r4)
            if (r12 == 0) goto L25
            if (r4 == 0) goto L22
            r9 = 32
            goto L24
        L22:
            r9 = 16
        L24:
            long r0 = r0 | r9
        L25:
            if (r4 == 0) goto L28
            goto L2c
        L28:
            r9 = 8
            goto L2d
        L2b:
            r4 = 0
        L2c:
            r9 = 0
        L2d:
            r12 = 10
            long r12 = r12 & r0
            int r10 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            r12 = 12
            long r12 = r12 & r0
            int r14 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            long r7 = r7 & r0
            int r12 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r12 == 0) goto L46
            android.widget.LinearLayout r7 = r15.layoutBar
            r7.setVisibility(r9)
            com.ltech.smarthome.view.SwitchButton r7 = r15.sbDiyVolume
            com.ltech.smarthome.binding.view.ViewAdapter.setChecked(r7, r4)
        L46:
            if (r10 == 0) goto L61
            android.widget.LinearLayout r4 = r15.layoutCollectMusic
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            android.widget.LinearLayout r4 = r15.layoutLocalMusic
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            android.widget.LinearLayout r4 = r15.layoutOnlineMusic
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            android.widget.RelativeLayout r4 = r15.layoutPlayPause
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
            android.widget.RelativeLayout r4 = r15.layoutPlayStart
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r4, r5, r11)
        L61:
            r4 = 8
            long r0 = r0 & r4
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L8c
            androidx.appcompat.widget.AppCompatTextView r0 = r15.mboundView10
            r1 = 1
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r15.mboundView11
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r15.mboundView14
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r15.mboundView2
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r15.mboundView4
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r15.mboundView6
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
            androidx.appcompat.widget.AppCompatTextView r0 = r15.mboundView8
            com.ltech.smarthome.binding.view.ViewAdapter.setTextBold(r0, r1)
        L8c:
            if (r14 == 0) goto L93
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r15.title
            r0.setTitle(r6)
        L93:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r15.title
            executeBindingsOn(r0)
            return
        L99:
            r0 = move-exception
            monitor-exit(r15)     // Catch: java.lang.Throwable -> L99
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActSelectSuperPanelMusicBindingImpl.executeBindings():void");
    }
}