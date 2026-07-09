package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActTestModeMainBinding extends ViewDataBinding {

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final AppCompatTextView tvConfigTitle;
    public final LinearLayout tvTestMode1;
    public final LinearLayout tvTestMode2;
    public final AppCompatTextView tvTestMode3;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActTestModeMainBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView tvConfigTitle, LinearLayout tvTestMode1, LinearLayout tvTestMode2, AppCompatTextView tvTestMode3) {
        super(_bindingComponent, _root, _localFieldCount);
        this.tvConfigTitle = tvConfigTitle;
        this.tvTestMode1 = tvTestMode1;
        this.tvTestMode2 = tvTestMode2;
        this.tvTestMode3 = tvTestMode3;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActTestModeMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestModeMainBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActTestModeMainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_test_mode_main, root, attachToRoot, component);
    }

    public static ActTestModeMainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestModeMainBinding inflate(LayoutInflater inflater, Object component) {
        return (ActTestModeMainBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_test_mode_main, null, false, component);
    }

    public static ActTestModeMainBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActTestModeMainBinding bind(View view, Object component) {
        return (ActTestModeMainBinding) bind(component, view, R.layout.act_test_mode_main);
    }
}