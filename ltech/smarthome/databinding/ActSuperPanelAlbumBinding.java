package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.ui.device.super_panel.ActSuperPanelVM;
import com.youth.banner.Banner;

/* loaded from: classes3.dex */
public abstract class ActSuperPanelAlbumBinding extends ViewDataBinding {
    public final Banner banner;
    public final LinearLayout content;
    public final AppCompatImageView ivPreview;
    public final FrameLayout layoutBg;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;

    @Bindable
    protected ActSuperPanelVM mViewmodel;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvDelete;
    public final AppCompatTextView tvUpload;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    public abstract void setViewmodel(ActSuperPanelVM viewmodel);

    protected ActSuperPanelAlbumBinding(Object _bindingComponent, View _root, int _localFieldCount, Banner banner, LinearLayout content, AppCompatImageView ivPreview, FrameLayout layoutBg, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvDelete, AppCompatTextView tvUpload) {
        super(_bindingComponent, _root, _localFieldCount);
        this.banner = banner;
        this.content = content;
        this.ivPreview = ivPreview;
        this.layoutBg = layoutBg;
        this.rvContent = rvContent;
        this.title = title;
        this.tvDelete = tvDelete;
        this.tvUpload = tvUpload;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public ActSuperPanelVM getViewmodel() {
        return this.mViewmodel;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSuperPanelAlbumBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelAlbumBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSuperPanelAlbumBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_album, root, attachToRoot, component);
    }

    public static ActSuperPanelAlbumBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelAlbumBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSuperPanelAlbumBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_super_panel_album, null, false, component);
    }

    public static ActSuperPanelAlbumBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSuperPanelAlbumBinding bind(View view, Object component) {
        return (ActSuperPanelAlbumBinding) bind(component, view, R.layout.act_super_panel_album);
    }
}