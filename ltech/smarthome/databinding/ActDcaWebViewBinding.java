package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActDcaWebViewBinding extends ViewDataBinding {

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final LinearLayout webView;

    public abstract void setTitle(TitleDefault title);

    protected ActDcaWebViewBinding(Object _bindingComponent, View _root, int _localFieldCount, LayoutTitleDefaultBinding title, LinearLayout webView) {
        super(_bindingComponent, _root, _localFieldCount);
        this.title = title;
        this.webView = webView;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActDcaWebViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaWebViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActDcaWebViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dca_web_view, root, attachToRoot, component);
    }

    public static ActDcaWebViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaWebViewBinding inflate(LayoutInflater inflater, Object component) {
        return (ActDcaWebViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_dca_web_view, null, false, component);
    }

    public static ActDcaWebViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActDcaWebViewBinding bind(View view, Object component) {
        return (ActDcaWebViewBinding) bind(component, view, R.layout.act_dca_web_view);
    }
}