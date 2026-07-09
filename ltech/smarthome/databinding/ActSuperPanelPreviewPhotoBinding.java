package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelPreviewPhotoBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivEdit;
    public final FrameLayout layoutClip;
    public final LinearLayout layoutRv;
    public final RelativeLayout layoutTop;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final ViewPager viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ActSuperPanelPreviewPhotoBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, AppCompatImageView ivEdit, FrameLayout layoutClip, LinearLayout layoutRv, RelativeLayout layoutTop, RecyclerView rvContent, ViewPager viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.ivEdit = ivEdit;
        this.layoutClip = layoutClip;
        this.layoutRv = layoutRv;
        this.layoutTop = layoutTop;
        this.rvContent = rvContent;
        this.viewpager = viewpager;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSuperPanelPreviewPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelPreviewPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelPreviewPhotoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_preview_photo, root, attachToRoot, component);
    }

    public static ActSuperPanelPreviewPhotoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelPreviewPhotoBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelPreviewPhotoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_preview_photo, null, false, component);
    }

    public static ActSuperPanelPreviewPhotoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelPreviewPhotoBinding bind(View view, Object component) {
        return (ActSuperPanelPreviewPhotoBinding) bind(component, view, R.layout.act_super_panel_preview_photo);
    }
}