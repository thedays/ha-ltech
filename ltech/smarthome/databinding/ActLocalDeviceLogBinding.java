package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;
import com.ltech.smarthome.view.SpreadView;

/* loaded from: classes3.dex */
public abstract class ActLocalDeviceLogBinding extends ViewDataBinding {
    public final ImageView ivStatus;
    public final ConstraintLayout layoutRoot;

    @Bindable
    protected String mTipText;

    @Bindable
    protected TitleDefault mTitle;
    public final RelativeLayout rlTipsLayout;
    public final RecyclerView rvContent;
    public final FrameLayout searchFrameLayout;
    public final AppCompatImageView searchImage;
    public final SpreadView searchSpreadView;
    public final LayoutTitleDefaultBinding title;
    public final TextView tvEmpty;
    public final AppCompatTextView tvTip;

    public abstract void setTipText(String tipText);

    public abstract void setTitle(TitleDefault title);

    protected ActLocalDeviceLogBinding(Object _bindingComponent, View _root, int _localFieldCount, ImageView ivStatus, ConstraintLayout layoutRoot, RelativeLayout rlTipsLayout, RecyclerView rvContent, FrameLayout searchFrameLayout, AppCompatImageView searchImage, SpreadView searchSpreadView, LayoutTitleDefaultBinding title, TextView tvEmpty, AppCompatTextView tvTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivStatus = ivStatus;
        this.layoutRoot = layoutRoot;
        this.rlTipsLayout = rlTipsLayout;
        this.rvContent = rvContent;
        this.searchFrameLayout = searchFrameLayout;
        this.searchImage = searchImage;
        this.searchSpreadView = searchSpreadView;
        this.title = title;
        this.tvEmpty = tvEmpty;
        this.tvTip = tvTip;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public String getTipText() {
        return this.mTipText;
    }

    public static ActLocalDeviceLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLocalDeviceLogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActLocalDeviceLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_local_device_log, root, attachToRoot, component);
    }

    public static ActLocalDeviceLogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLocalDeviceLogBinding inflate(LayoutInflater inflater, Object component) {
        return (ActLocalDeviceLogBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_local_device_log, null, false, component);
    }

    public static ActLocalDeviceLogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActLocalDeviceLogBinding bind(View view, Object component) {
        return (ActLocalDeviceLogBinding) bind(component, view, R.layout.act_local_device_log);
    }
}