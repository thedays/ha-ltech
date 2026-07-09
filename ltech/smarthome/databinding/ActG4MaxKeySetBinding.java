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
import com.ltech.smarthome.model.bean.TitleDefault;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/* loaded from: classes3.dex */
public abstract class ActG4MaxKeySetBinding extends ViewDataBinding {
    public final LinearLayout layoutLoad;
    public final LinearLayout llBottom;

    @Bindable
    protected TitleDefault mTitle;
    public final SwipeRecyclerView rvContent;
    public final LayoutTitleDefaultBinding title;
    public final AppCompatTextView tvTipMessage;
    public final AppCompatTextView tvTipTitle;

    public abstract void setTitle(TitleDefault title);

    protected ActG4MaxKeySetBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout layoutLoad, LinearLayout llBottom, SwipeRecyclerView rvContent, LayoutTitleDefaultBinding title, AppCompatTextView tvTipMessage, AppCompatTextView tvTipTitle) {
        super(_bindingComponent, _root, _localFieldCount);
        this.layoutLoad = layoutLoad;
        this.llBottom = llBottom;
        this.rvContent = rvContent;
        this.title = title;
        this.tvTipMessage = tvTipMessage;
        this.tvTipTitle = tvTipTitle;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActG4MaxKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActG4MaxKeySetBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActG4MaxKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_g4_max_key_set, root, attachToRoot, component);
    }

    public static ActG4MaxKeySetBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActG4MaxKeySetBinding inflate(LayoutInflater inflater, Object component) {
        return (ActG4MaxKeySetBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_g4_max_key_set, null, false, component);
    }

    public static ActG4MaxKeySetBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActG4MaxKeySetBinding bind(View view, Object component) {
        return (ActG4MaxKeySetBinding) bind(component, view, R.layout.act_g4_max_key_set);
    }
}