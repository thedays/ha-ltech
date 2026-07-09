package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActAddIrKeyBinding extends ViewDataBinding {
    public final AppCompatTextView appCompatTextView3;
    public final AppCompatEditText etKeyName;

    @Bindable
    protected TitleDefault mTitle;
    public final RecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final View view3;
    public final View view4;
    public final AppCompatTextView view7;

    public abstract void setTitle(TitleDefault title);

    protected ActAddIrKeyBinding(Object _bindingComponent, View _root, int _localFieldCount, AppCompatTextView appCompatTextView3, AppCompatEditText etKeyName, RecyclerView rvContent, LayoutTitleDefaultBinding title, View view3, View view4, AppCompatTextView view7) {
        super(_bindingComponent, _root, _localFieldCount);
        this.appCompatTextView3 = appCompatTextView3;
        this.etKeyName = etKeyName;
        this.rvContent = rvContent;
        this.title = title;
        this.view3 = view3;
        this.view4 = view4;
        this.view7 = view7;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActAddIrKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddIrKeyBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActAddIrKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_ir_key, root, attachToRoot, component);
    }

    public static ActAddIrKeyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddIrKeyBinding inflate(LayoutInflater inflater, Object component) {
        return (ActAddIrKeyBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_add_ir_key, null, false, component);
    }

    public static ActAddIrKeyBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActAddIrKeyBinding bind(View view, Object component) {
        return (ActAddIrKeyBinding) bind(component, view, R.layout.act_add_ir_key);
    }
}