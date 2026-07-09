package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.view.SwitchButton;

/* loaded from: classes3.dex */
public abstract class FtPageScreenPanelDetailBinding extends ViewDataBinding {
    public final AppCompatImageView ivStartTimeGo;
    public final LinearLayout layout1;
    public final LinearLayout layout2;
    public final RelativeLayout layoutCustom;
    public final RelativeLayout layoutShow;
    public final ConstraintLayout layoutTime;
    public final RecyclerView rv;
    public final SwitchButton sb;
    public final AppCompatTextView tvShowTime;
    public final AppCompatTextView tvTimeTip;

    protected FtPageScreenPanelDetailBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivStartTimeGo, LinearLayout layout1, LinearLayout layout2, RelativeLayout layoutCustom, RelativeLayout layoutShow, ConstraintLayout layoutTime, RecyclerView rv, SwitchButton sb, AppCompatTextView tvShowTime, AppCompatTextView tvTimeTip) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivStartTimeGo = ivStartTimeGo;
        this.layout1 = layout1;
        this.layout2 = layout2;
        this.layoutCustom = layoutCustom;
        this.layoutShow = layoutShow;
        this.layoutTime = layoutTime;
        this.rv = rv;
        this.sb = sb;
        this.tvShowTime = tvShowTime;
        this.tvTimeTip = tvTimeTip;
    }

    public static FtPageScreenPanelDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtPageScreenPanelDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (FtPageScreenPanelDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_page_screen_panel_detail, root, attachToRoot, component);
    }

    public static FtPageScreenPanelDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtPageScreenPanelDetailBinding inflate(LayoutInflater inflater, Object component) {
        return (FtPageScreenPanelDetailBinding) ViewDataBinding.inflateInternal(inflater, R.layout.ft_page_screen_panel_detail, null, false, component);
    }

    public static FtPageScreenPanelDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static FtPageScreenPanelDetailBinding bind(View view, Object component) {
        return (FtPageScreenPanelDetailBinding) bind(component, view, R.layout.ft_page_screen_panel_detail);
    }
}