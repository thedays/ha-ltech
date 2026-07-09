package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;

/* loaded from: classes3.dex */
public abstract class ItemAdvancedModeAddBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView7;

    @Bindable
    protected BindingCommand<View> mClickCommand;
    public final View view26;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    protected ItemAdvancedModeAddBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView7, View view26) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView7 = appCompatImageView7;
        this.view26 = view26;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ItemAdvancedModeAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAdvancedModeAddBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ItemAdvancedModeAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_advanced_mode_add, root, attachToRoot, component);
    }

    public static ItemAdvancedModeAddBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAdvancedModeAddBinding inflate(LayoutInflater inflater, Object component) {
        return (ItemAdvancedModeAddBinding) ViewDataBinding.inflateInternal(inflater, R.layout.item_advanced_mode_add, null, false, component);
    }

    public static ItemAdvancedModeAddBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ItemAdvancedModeAddBinding bind(View view, Object component) {
        return (ItemAdvancedModeAddBinding) bind(component, view, R.layout.item_advanced_mode_add);
    }
}