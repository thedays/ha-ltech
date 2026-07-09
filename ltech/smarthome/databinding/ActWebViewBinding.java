package com.ltech.smarthome.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.ltech.smarthome.R;
import com.ltech.smarthome.model.bean.TitleDefault;

/* loaded from: classes3.dex */
public abstract class ActWebViewBinding extends ViewDataBinding {
    public final LinearLayout controlBar;
    public final ImageButton goBack;
    public final ImageButton goForward;

    @Bindable
    protected TitleDefault mTitle;
    public final LayoutTitleDefaultBinding title;
    public final WebView webView;
    public final WebView webView1;

    public abstract void setTitle(TitleDefault title);

    protected ActWebViewBinding(Object _bindingComponent, View _root, int _localFieldCount, LinearLayout controlBar, ImageButton goBack, ImageButton goForward, LayoutTitleDefaultBinding title, WebView webView, WebView webView1) {
        super(_bindingComponent, _root, _localFieldCount);
        this.controlBar = controlBar;
        this.goBack = goBack;
        this.goForward = goForward;
        this.title = title;
        this.webView = webView;
        this.webView1 = webView1;
    }

    public TitleDefault getTitle() {
        return this.mTitle;
    }

    public static ActWebViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWebViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, Object component) {
        return (ActWebViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_web_view, root, attachToRoot, component);
    }

    public static ActWebViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWebViewBinding inflate(LayoutInflater inflater, Object component) {
        return (ActWebViewBinding) ViewDataBinding.inflateInternal(inflater, R.layout.act_web_view, null, false, component);
    }

    public static ActWebViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    @Deprecated
    public static ActWebViewBinding bind(View view, Object component) {
        return (ActWebViewBinding) bind(component, view, R.layout.act_web_view);
    }
}