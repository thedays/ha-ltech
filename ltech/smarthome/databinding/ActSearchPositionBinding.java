package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActSearchPositionBinding extends ViewDataBinding {
    public final AppCompatEditText etSearch;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvResult;
    public final LayoutTitleDefaultBinding title;
    public final View view14;

    public abstract void setTitle(TitleDefault title);

    protected ActSearchPositionBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatEditText etSearch, RecyclerView rvResult, LayoutTitleDefaultBinding title, View view14) {
        super(_bindingComponent, _root, _localFieldCount);
        this.etSearch = etSearch;
        this.rvResult = rvResult;
        this.title = title;
        this.view14 = view14;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActSearchPositionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchPositionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActSearchPositionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_position, root, attachToRoot, component);
    }

    public static ActSearchPositionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchPositionBinding inflate(LayoutInflater inflater, Object component) {
        return (ActSearchPositionBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_search_position, null, false, component);
    }

    public static ActSearchPositionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActSearchPositionBinding bind(View view, Object component) {
        return (ActSearchPositionBinding) bind(component, view, R.layout.act_search_position);
    }
}