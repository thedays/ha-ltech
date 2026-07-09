package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.amap.api.maps2d.MapView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActHomePositionBinding extends ViewDataBinding {
    public final AppCompatImageView ivLocalMy;
    public final LinearLayout layoutSearch;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final MapView mapView;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvAddress;
    public final View view14;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActHomePositionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView ivLocalMy, LinearLayout layoutSearch, MapView mapView, LayoutTitleDefaultBinding title, AppCompatTextView tvAddress, View view14) {
        super(_bindingComponent, _root, _localFieldCount);
        this.ivLocalMy = ivLocalMy;
        this.layoutSearch = layoutSearch;
        this.mapView = mapView;
        this.title = title;
        this.tvAddress = tvAddress;
        this.view14 = view14;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActHomePositionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomePositionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActHomePositionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_position, root, attachToRoot, component);
    }

    public static ActHomePositionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomePositionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActHomePositionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_home_position, null, false, component);
    }

    public static ActHomePositionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActHomePositionBinding bind(View view, Object component) {
        return (ActHomePositionBinding) bind(component, view, R.layout.act_home_position);
    }
}