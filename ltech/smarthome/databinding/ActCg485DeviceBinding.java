package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActCg485DeviceBinding extends ViewDataBinding {
    public final View dividerLine;
    public final LinearLayout layoutBottom;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvBottom;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActCg485DeviceBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, LinearLayout layoutBottom, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvBottom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.layoutBottom = layoutBottom;
        this.rvContent = rvContent;
        this.title = title;
        this.tvBottom = tvBottom;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActCg485DeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485DeviceBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCg485DeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cg_485_device, root, attachToRoot, component);
    }

    public static ActCg485DeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485DeviceBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCg485DeviceBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cg_485_device, null, false, component);
    }

    public static ActCg485DeviceBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485DeviceBinding bind(View view, Object component) {
        return (ActCg485DeviceBinding) bind(component, view, R.layout.act_cg_485_device);
    }
}