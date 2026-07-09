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
public abstract class ActCg485OldBinding extends ViewDataBinding {
    public final View dividerLine;
    public final LinearLayout layoutBottom;
    public final LinearLayout layoutTab;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tv485;
    public final AppCompatTextView tvBle;
    public final AppCompatTextView tvBottom;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActCg485OldBinding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, LinearLayout layoutBottom, LinearLayout layoutTab, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tv485, AppCompatTextView tvBle, AppCompatTextView tvBottom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.layoutBottom = layoutBottom;
        this.layoutTab = layoutTab;
        this.rvContent = rvContent;
        this.title = title;
        this.tv485 = tv485;
        this.tvBle = tvBle;
        this.tvBottom = tvBottom;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActCg485OldBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485OldBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCg485OldBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cg_485_old, root, attachToRoot, component);
    }

    public static ActCg485OldBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485OldBinding inflate(LayoutInflater inflater, Object component) {
        return (ActCg485OldBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cg_485_old, null, false, component);
    }

    public static ActCg485OldBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485OldBinding bind(View view, Object component) {
        return (ActCg485OldBinding) bind(component, view, R.layout.act_cg_485_old);
    }
}