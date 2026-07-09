package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelClipPhotoBinding extends ViewDataBinding {
    public final AppCompatImageView ivBack;
    public final AppCompatImageView ivCrop;
    public final AppCompatImageView ivEdit;
    public final ConstraintLayout layoutClip;
    public final LinearLayout layoutRv;
    public final RelativeLayout layoutTop;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final RecyclerView rvContent;
    public final AppCompatTextView tvUpload;
    public final ViewPager2 viewpager;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ActSuperPanelClipPhotoBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivBack, AppCompatImageView ivCrop, AppCompatImageView ivEdit, ConstraintLayout layoutClip, LinearLayout layoutRv, RelativeLayout layoutTop, RecyclerView rvContent, AppCompatTextView tvUpload, ViewPager2 viewpager) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivBack = ivBack;
        this.ivCrop = ivCrop;
        this.ivEdit = ivEdit;
        this.layoutClip = layoutClip;
        this.layoutRv = layoutRv;
        this.layoutTop = layoutTop;
        this.rvContent = rvContent;
        this.tvUpload = tvUpload;
        this.viewpager = viewpager;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSuperPanelClipPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelClipPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelClipPhotoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_clip_photo, root, attachToRoot, component);
    }

    public static ActSuperPanelClipPhotoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelClipPhotoBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelClipPhotoBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_clip_photo, null, false, component);
    }

    public static ActSuperPanelClipPhotoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelClipPhotoBinding bind(View view, Object component) {
        return (ActSuperPanelClipPhotoBinding) bind(component, view, R.layout.act_super_panel_clip_photo);
    }
}