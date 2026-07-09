package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActCg485Binding extends ViewDataBinding {
    public final View dividerLine;
    public final CardView layout485;
    public final CardView layoutBle;
    public final LinearLayout layoutBottom;
    public final NestedScrollView layoutContent;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rv485ToBle;
    public final RecyclerView rvBleTo485;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tv485;
    public final AppCompatTextView tvBle;
    public final AppCompatTextView tvBottom;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActCg485Binding(Object _bindingComponent, View _root, int _localFieldCount, View dividerLine, CardView layout485, CardView layoutBle, LinearLayout layoutBottom, NestedScrollView layoutContent, RecyclerView rv485ToBle, RecyclerView rvBleTo485, LayoutTitleDefaultBinding title, AppCompatTextView tv485, AppCompatTextView tvBle, AppCompatTextView tvBottom) {
        super(_bindingComponent, _root, _localFieldCount);
        this.dividerLine = dividerLine;
        this.layout485 = layout485;
        this.layoutBle = layoutBle;
        this.layoutBottom = layoutBottom;
        this.layoutContent = layoutContent;
        this.rv485ToBle = rv485ToBle;
        this.rvBleTo485 = rvBleTo485;
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

    public static ActCg485Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485Binding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActCg485Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cg_485, root, attachToRoot, component);
    }

    public static ActCg485Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485Binding inflate(LayoutInflater inflater, Object component) {
        return (ActCg485Binding) ViewDataBinding.inflateInternal(inflater, R.layout.act_cg_485, null, false, component);
    }

    public static ActCg485Binding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActCg485Binding bind(View view, Object component) {
        return (ActCg485Binding) bind(component, view, R.layout.act_cg_485);
    }
}