package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.binding.command.BindingCommand;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSelectBrandBinding extends ViewDataBinding {
    public final AppCompatImageView appCompatImageView18;
    public final AppCompatEditText etSearch;
    public final ConstraintLayout layoutLoad;

    @Bindable
    protected BindingCommand<View> mClickCommand;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvSearch;
    public final View view10;
    public final View view11;

    public abstract void setClickCommand(BindingCommand<View> clickCommand);

    public abstract void setTitle(TitleDefault title);

    protected ActSelectBrandBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatImageView appCompatImageView18, AppCompatEditText etSearch, ConstraintLayout layoutLoad, RecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvSearch, View view10, View view11) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatImageView18 = appCompatImageView18;
        this.etSearch = etSearch;
        this.layoutLoad = layoutLoad;
        this.rvContent = rvContent;
        this.title = title;
        this.tvSearch = tvSearch;
        this.view10 = view10;
        this.view11 = view11;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public BindingCommand<View> getClickCommand() {
        return this.mClickCommand;
    }

    public static ActSelectBrandBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBrandBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSelectBrandBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_brand, root, attachToRoot, component);
    }

    public static ActSelectBrandBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBrandBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSelectBrandBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_select_brand, null, false, component);
    }

    public static ActSelectBrandBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSelectBrandBinding bind(View view, Object component) {
        return (ActSelectBrandBinding) bind(component, view, R.layout.act_select_brand);
    }
}