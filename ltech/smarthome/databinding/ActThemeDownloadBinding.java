package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SuperCircleView;

/* loaded from: classes3.dex */
public abstract class ActThemeDownloadBinding extends ViewDataBinding {
    public final AppCompatImageView iv;
    public final AppCompatImageView ivSuccess;

    @Bindable
    protected TitleDefault mTitle;
    public final SuperCircleView superview;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvDownloadTip;
    public final TextView tvDownloadTitle;
    public final TextView tvName;
    public final AppCompatTextView tvProgress;
    public final TextView tvStatus;

    public abstract void setTitle(TitleDefault title);

    protected ActThemeDownloadBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView iv, AppCompatImageView ivSuccess, SuperCircleView superview, LayoutTitleDefaultBinding title, TextView tvDownloadTip, TextView tvDownloadTitle, TextView tvName, AppCompatTextView tvProgress, TextView tvStatus) {
        super(_bindingComponent, _root, _localFieldCount);
        this.iv = iv;
        this.ivSuccess = ivSuccess;
        this.superview = superview;
        this.title = title;
        this.tvDownloadTip = tvDownloadTip;
        this.tvDownloadTitle = tvDownloadTitle;
        this.tvName = tvName;
        this.tvProgress = tvProgress;
        this.tvStatus = tvStatus;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActThemeDownloadBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActThemeDownloadBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActThemeDownloadBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_theme_download, root, attachToRoot, component);
    }

    public static ActThemeDownloadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActThemeDownloadBinding inflate(LayoutInflater inflater, Object component) {
        return (ActThemeDownloadBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_theme_download, null, false, component);
    }

    public static ActThemeDownloadBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActThemeDownloadBinding bind(View view, Object component) {
        return (ActThemeDownloadBinding) bind(component, view, R.layout.act_theme_download);
    }
}