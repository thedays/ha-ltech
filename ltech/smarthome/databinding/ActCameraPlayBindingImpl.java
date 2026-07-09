package com.ltech.smarthome.databinding;

import android.util.SparseIntArray;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;
import com.videogo.widget.HandleView;

/* loaded from: classes3.dex */
public class ActCameraPlayBindingImpl extends ActCameraPlayBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    static {
        ViewDataBinding.IncludedLayouts includedLayouts = new ViewDataBinding.IncludedLayouts(41);
        sIncludes = includedLayouts;
        includedLayouts.setIncludes(0, new String[]{"layout_title_default"}, new int[]{19}, new int[]{R.layout.layout_title_default});
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(R.id.linearLayout2, 20);
        sparseIntArray.put(R.id.surface_view, 21);
        sparseIntArray.put(R.id.surface_view_2, 22);
        sparseIntArray.put(R.id.iv_record_time, 23);
        sparseIntArray.put(R.id.tv_record_time, 24);
        sparseIntArray.put(R.id.tv_speed, 25);
        sparseIntArray.put(R.id.tv_loading, 26);
        sparseIntArray.put(R.id.layout_tab, 27);
        sparseIntArray.put(R.id.progress_area, 28);
        sparseIntArray.put(R.id.begin_time_tv, 29);
        sparseIntArray.put(R.id.progress_seekbar, 30);
        sparseIntArray.put(R.id.end_time_tv, 31);
        sparseIntArray.put(R.id.layout_controller, 32);
        sparseIntArray.put(R.id.viewpager, 33);
        sparseIntArray.put(R.id.handle_view_full, 34);
        sparseIntArray.put(R.id.talk_view_full, 35);
        sparseIntArray.put(R.id.spreadview_talk, 36);
        sparseIntArray.put(R.id.spreadview_talk_stop, 37);
        sparseIntArray.put(R.id.iv_talk, 38);
        sparseIntArray.put(R.id.guideline3, 39);
        sparseIntArray.put(R.id.tabs, 40);
    }

    public ActCameraPlayBindingImpl(DataBindingComponent bindingComponent, View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 41, sIncludes, sViewsWithIds));
    }

    private ActCameraPlayBindingImpl(DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0, (TextView) bindings[29], (TextView) bindings[31], (Guideline) bindings[39], (HandleView) bindings[34], (AppCompatImageView) bindings[8], (AppCompatImageView) bindings[4], (AppCompatImageView) bindings[10], (AppCompatImageView) bindings[14], (AppCompatImageView) bindings[13], (AppCompatImageView) bindings[17], (AppCompatImageView) bindings[16], (AppCompatImageView) bindings[11], (AppCompatImageView) bindings[15], (AppCompatImageView) bindings[1], (AppCompatImageView) bindings[5], (AppCompatImageView) bindings[23], (ImageView) bindings[18], (AppCompatImageView) bindings[6], (AppCompatImageView) bindings[38], (ConstraintLayout) bindings[32], (ConstraintLayout) bindings[9], (LinearLayout) bindings[27], (LinearLayout) bindings[20], (LinearLayout) bindings[28], (SeekBar) bindings[30], (SpreadView) bindings[36], (AppCompatImageView) bindings[37], (SurfaceView) bindings[21], (SurfaceView) bindings[22], (TabLayout) bindings[40], (RelativeLayout) bindings[35], (LayoutTitleDefaultBinding) bindings[19], (AppCompatTextView) bindings[12], (TextView) bindings[26], (AppCompatTextView) bindings[7], (AppCompatTextView) bindings[24], (TextView) bindings[25], (AppCompatTextView) bindings[2], (AppCompatTextView) bindings[3], (ViewPager2) bindings[33]);
        this.mDirtyFlags = -1L;
        this.ivFull.setTag(null);
        this.ivFullBack.setTag(null);
        this.ivFullPlay.setTag(null);
        this.ivFullPlayback.setTag(null);
        this.ivFullPtz.setTag(null);
        this.ivFullRecord.setTag(null);
        this.ivFullShot.setTag(null);
        this.ivFullSound.setTag(null);
        this.ivFullTalk.setTag(null);
        this.ivOffline.setTag(null);
        this.ivPlay.setTag(null);
        this.ivSmall.setTag(null);
        this.ivSound.setTag(null);
        this.layoutController3.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) bindings[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        setContainedBinding(this.title);
        this.tvFullQuality.setTag(null);
        this.tvQuality.setTag(null);
        this.tvVideo1.setTag(null);
        this.tvVideo2.setTag(null);
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
        if (72 == variableId) {
            setShowTitle((Boolean) variable);
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

    @Override // com.ltech.smarthome.databinding.ActCameraPlayBinding
    public void setShowTitle(Boolean ShowTitle) {
        this.mShowTitle = ShowTitle;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(72);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCameraPlayBinding
    public void setClickCommand(BindingCommand<View> ClickCommand) {
        this.mClickCommand = ClickCommand;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(10);
        super.requestRebind();
    }

    @Override // com.ltech.smarthome.databinding.ActCameraPlayBinding
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a2  */
    @Override // androidx.databinding.ViewDataBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void executeBindings() {
        /*
            r14 = this;
            monitor-enter(r14)
            long r0 = r14.mDirtyFlags     // Catch: java.lang.Throwable -> Lad
            r2 = 0
            r14.mDirtyFlags = r2     // Catch: java.lang.Throwable -> Lad
            monitor-exit(r14)     // Catch: java.lang.Throwable -> Lad
            java.lang.Boolean r4 = r14.mShowTitle
            com.ltech.smarthome.binding.command.BindingCommand<android.view.View> r5 = r14.mClickCommand
            com.ltech.smarthome.model.bean.TitleDefault r6 = r14.mTitle
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
            goto L2b
        L28:
            r4 = 8
            goto L2c
        L2b:
            r4 = 0
        L2c:
            r9 = 10
            long r9 = r9 & r0
            int r12 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            r9 = 12
            long r9 = r9 & r0
            int r13 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r12 == 0) goto L92
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFull
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFullBack
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFullPlay
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFullPlayback
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFullPtz
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFullRecord
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFullShot
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFullSound
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivFullTalk
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivOffline
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivPlay
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            android.widget.ImageView r9 = r14.ivSmall
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatImageView r9 = r14.ivSound
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.constraintlayout.widget.ConstraintLayout r9 = r14.layoutController3
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvFullQuality
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvQuality
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvVideo1
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
            androidx.appcompat.widget.AppCompatTextView r9 = r14.tvVideo2
            com.ltech.smarthome.binding.view.ViewAdapter.onClickCommand(r9, r5, r11)
        L92:
            long r0 = r0 & r7
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 == 0) goto La0
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            android.view.View r0 = r0.getRoot()
            r0.setVisibility(r4)
        La0:
            if (r13 == 0) goto La7
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            r0.setTitle(r6)
        La7:
            com.ltech.smarthome.databinding.LayoutTitleDefaultBinding r0 = r14.title
            executeBindingsOn(r0)
            return
        Lad:
            r0 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> Lad
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ltech.smarthome.databinding.ActCameraPlayBindingImpl.executeBindings():void");
    }
}