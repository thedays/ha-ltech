package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActRecordListBinding extends ViewDataBinding {
    public final LinearLayout layoutBackground;
    public final LinearLayout layoutLoading;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActRecordListBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutBackground, LinearLayout layoutLoading, RecyclerView rvContent) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutBackground = layoutBackground;
        this.layoutLoading = layoutLoading;
        this.rvContent = rvContent;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActRecordListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRecordListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActRecordListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_record_list, root, attachToRoot, component);
    }

    public static ActRecordListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRecordListBinding inflate(LayoutInflater inflater, Object component) {
        return (ActRecordListBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_record_list, null, false, component);
    }

    public static ActRecordListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActRecordListBinding bind(View view, Object component) {
        return (ActRecordListBinding) bind(component, view, R.layout.act_record_list);
    }
}